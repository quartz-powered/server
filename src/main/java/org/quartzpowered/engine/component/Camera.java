package org.quartzpowered.engine.component;

import org.quartzpowered.engine.observe.Observer;
import org.quartzpowered.network.session.attribute.AttributeKey;
import org.quartzpowered.network.session.attribute.AttributeRegistry;
import org.quartzpowered.network.session.attribute.AttributeStorage;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Camera extends Component {
    public static final AttributeKey<Camera> CAMERA_ATTRIBUTE = AttributeKey.create();

    @Inject private AttributeRegistry attributeRegistry;

    private final List<Observer> observers = new ArrayList<>();

    public void removeViewer(Observer observer) {
        if (observers.remove(observer)) {
            attributeRegistry.get(observer).set(CAMERA_ATTRIBUTE, null);
        }
    }

    public void addViewer(Observer observer) {
        AttributeStorage attributes = attributeRegistry.get(observer);

        Camera previousCamera = attributes.get(CAMERA_ATTRIBUTE);
        if (previousCamera != null) {
            previousCamera.removeViewer(observer);
        }

        observers.add(observer);
        attributes.set(CAMERA_ATTRIBUTE, this);
    }
}
