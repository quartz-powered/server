package org.quartzpowered.network.session.attribute;

import com.google.common.collect.Maps;
import com.sun.istack.internal.Nullable;

import java.util.Map;

public class AttributeStorage {
    private Map<AttributeKey<?>, Object> attributes = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T get(AttributeKey<T> key) {
        return (T) attributes.get(key);
    }

    public <T> AttributeStorage set(AttributeKey<T> key, @Nullable T value) {
        attributes.put(key, value);
        return this;
    }
}
