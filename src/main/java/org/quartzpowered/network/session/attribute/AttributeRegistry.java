package org.quartzpowered.network.session.attribute;

import javax.inject.Singleton;
import java.util.Map;
import java.util.WeakHashMap;

@Singleton
public class AttributeRegistry {
    private final Map<Object, AttributeStorage> storage = new WeakHashMap<>();

    public AttributeStorage get(Object object) {
        return storage.computeIfAbsent(object, o -> new AttributeStorage());
    }
}
