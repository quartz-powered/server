/*
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.quartzpowered.engine.object;

import org.quartzpowered.common.factory.FactoryRegistry;
import org.quartzpowered.common.reflector.Reflector;
import org.quartzpowered.common.reflector.ReflectorRegistry;
import org.quartzpowered.engine.object.annotation.MessageHandler;
import org.quartzpowered.engine.object.component.Transform;
import org.quartzpowered.engine.observe.Observable;
import org.quartzpowered.engine.observe.Observer;
import org.quartzpowered.network.protocol.packet.Packet;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GameObject implements Observable, Observer {
    @Inject private Logger logger;
    @Inject private FactoryRegistry factoryRegistry;
    @Inject private ReflectorRegistry reflectorRegistry;

    private final List<Component> components = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();

    @Inject
    private GameObject(FactoryRegistry factoryRegistry) {
        this.factoryRegistry = factoryRegistry;

        addComponent(Transform.class);
    }

    public <T extends Component> T addComponent(Class<T> type) {
        T component = this.factoryRegistry.get(type).create();

        component.setObject(this);
        sendMessageToComponent(component, "init");

        components.add(component);
        observers.forEach(observer -> sendMessageToComponent(component, "startObserving", observer));

        return component;
    }

    public void removeComponent(Component component) {
        if (this.components.remove(component)) {
            this.observers.forEach(observer -> sendMessageToComponent(component, "stopObserving", observer));
        }
    }

    public <T extends Component> T getComponent(Class<T> type) {
        for (Component component : this.components) {
            if (type.isInstance(component)) {
                return type.cast(component);
            }
        }

        return null;
    }

    public <T extends Component> T getComponentInChildren(Class<T> type) {
        T component = getComponent(type);
        if (component != null) {
            return component;
        }

        Transform transform = getTransform();
        if (transform != null) {
            for (Transform childTransform : transform.getChildren()) {
                GameObject child = childTransform.getGameObject();

                component = child.getComponentInChildren(type);
                if (component != null) {
                    return component;
                }
            }
        }

        return null;
    }

    public <T extends Component> T getComponentInParent(Class<T> type) {
        T component = getComponent(type);
        if (component != null) {
            return component;
        }

        Transform transform = getTransform();
        if (transform != null) {
            Transform parentTransform = transform.getParent();

            if (parentTransform != null) {
                GameObject parent = parentTransform.getGameObject();

                component = parent.getComponentInParent(type);
                if (component != null) {
                    return component;
                }
            }
        }

        return null;
    }

    public <T extends Component> Collection<T> getComponents(Class<T> type) {
        List<T> result = new ArrayList<>();
        getComponents(type, result);
        return result;
    }

    public <T extends Component> Collection<T> getComponentsInChildren(Class<T> type) {
        List<T> result = new ArrayList<>();
        getComponentsInChildren(type, result);
        return result;
    }

    public <T extends Component> Collection<T> getComponentsInParent(Class<T> type) {
        List<T> result = new ArrayList<>();
        getComponentsInParent(type, result);
        return result;
    }

    public void sendMessage(String name, Object... args) {
        for (Component component : components) {
            sendMessageToComponent(component, name, args);
        }
    }

    private void sendMessageToComponent(Component component, String name, Object... args) {
        Class<? extends Component> componentType = component.getClass();

        Method[] methods = componentType.getMethods();
        for (Method method : methods) {

            if (method.getName().equals(name) &&
                    method.getAnnotation(MessageHandler.class) != null) {

                Class<?>[] parameters = method.getParameterTypes();
                if (matchParameters(parameters, args)) {
                    Reflector<Component> reflector = reflectorRegistry.get(componentType);
                    reflector.invoke(component, name, args);
                    break;
                } else {
                    logger.warn("@MessageHandler found with invalid signature {} in {}", method, componentType);
                }
            }
        }
    }

    public void broadcastMessage(String name, Object... args) {
        sendMessage(name, args);
        forEachChild(child -> child.broadcastMessage(name, args));
    }

    public void sendMessageToParent(String name, Object... args) {
        sendMessage(name, args);
        forParent(parent -> parent.sendMessageToParent(name, args));
    }

    private void forEachChild(Consumer<GameObject> consumer) {
        final Transform transform = getTransform();
        if (transform != null) {
            for (Transform childTransform : transform.getChildren()) {
                final GameObject child = childTransform.getGameObject();

                consumer.accept(child);
            }
        }
    }

    private void forParent(Consumer<GameObject> consumer) {
        Transform transform = getTransform();
        if (transform != null) {
            Transform parentTransform = transform.getParent();

            if (parentTransform != null) {
                GameObject parent = parentTransform.getGameObject();

                consumer.accept(parent);
            }
        }
    }

    public Transform getTransform() {
        return getComponent(Transform.class);
    }

    private <T extends Component> void getComponents(Class<T> type, Collection<T> result) {
        for (Component component : this.components) {
            if (type.isInstance(component)) {
                result.add(type.cast(component));
            }
        }
    }

    private <T extends Component> void getComponentsInChildren(Class<T> type, Collection<T> result) {
        getComponents(type, result);
        forEachChild(child -> child.getComponentsInChildren(type, result));
    }

    private <T extends Component> void getComponentsInParent(Class<T> type, Collection<T> result) {
        getComponents(type, result);
        forParent(parent -> parent.getComponentsInParent(type, result));
    }

    private boolean matchParameters(Class<?>[] types, Object[] args) {
        for (int i = 0; i < types.length; i++) {
            if (!types[i].isInstance(args[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void startObserving(Observer observer) {
        observers.add(observer);
        sendMessage("startObserving", observer);
    }

    @Override
    public void stopObserving(Observer observer) {
        observers.remove(observer);
        sendMessage("stopObserving", observer);
    }

    public boolean hasObserver(Observer observer) {
        return observers.contains(observer);
    }

    public void observe(Packet packet) {
        observers.forEach(observer -> observer.observe(packet));
    }

    public GameObject getParent() {
        Transform transform = getTransform();
        if (transform == null) {
            return null;
        }

        Transform parent = transform.getParent();
        if (parent == null) {
            return null;
        }

        return parent.getGameObject();
    }

    public GameObject getRoot() {
        Transform transform = getTransform();
        if (transform == null) {
            return this;
        }

        return transform.getRoot().getGameObject();
    }

    public Collection<GameObject> getChildren() {
        Transform transform = getTransform();
        if (transform == null) {
            return null;
        }

        return transform.getChildren().stream()
                .map(Transform::getGameObject)
                .collect(Collectors.toList());
    }

    public void setParent(GameObject parent) {
        Transform transform = getTransform();
        Transform parentTransform = parent.getTransform();

        if (transform == null || parentTransform == null) {
            throw new NullPointerException();
        }

        transform.setParent(parentTransform);
    }

    public static GameObject none() {
        return null;
    }
}
