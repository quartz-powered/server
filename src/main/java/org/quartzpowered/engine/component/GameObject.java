package org.quartzpowered.engine.component;

import org.quartzpowered.common.factory.FactoryRegistry;
import org.quartzpowered.engine.observe.Observable;
import org.quartzpowered.engine.observe.Observer;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameObject implements Observable {
    @Inject private FactoryRegistry factoryRegistry;

    private final List<Component> components = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();

    @Inject
    private GameObject() {
        addComponent(Transform.class);
    }

    public <T extends Component> T addComponent(Class<T> type) {
        T component = factoryRegistry.get(type).create();
        component.setGameObject(this);
        components.add(component);
        observers.forEach(component::startObserving);
        return component;
    }

    public void removeComponent(Component component) {
        if (components.remove(component)) {
            observers.forEach(component::endObserving);
        }
    }

    public <T extends Component> T getComponent(Class<T> type) {
        for (Component component : components) {
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

    public Transform getTransform() {
        return getComponent(Transform.class);
    }

    private <T extends Component> void getComponents(Class<T> type, Collection<T> result) {
        for (Component component : components) {
            if (type.isInstance(component)) {
                result.add(type.cast(component));
            }
        }
    }

    private <T extends Component> void getComponentsInChildren(Class<T> type, Collection<T> result) {
        getComponents(type, result);
        Transform transform = getTransform();
        if (transform != null) {
            for (Transform childTransform : transform.getChildren()) {
                GameObject child = childTransform.getGameObject();

                getComponentsInChildren(type, result);
            }
        }
    }

    private <T extends Component> void getComponentsInParent(Class<T> type, Collection<T> result) {
        getComponents(type, result);
        Transform transform = getTransform();
        if (transform != null) {
            Transform parentTransform = transform.getParent();

            if (parentTransform != null) {
                GameObject parent = parentTransform.getGameObject();

                parent.getComponentsInParent(type, result);
            }
        }
    }

    @Override
    public void startObserving(Observer observer) {
        observers.add(observer);
        components.forEach(component -> component.startObserving(observer));
    }

    @Override
    public void endObserving(Observer observer) {
        observers.remove(observer);
        components.forEach(component -> component.endObserving(observer));
    }
}
