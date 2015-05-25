package org.quartzpowered.common.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps integer based ids to values.
 *
 * @param <T>
 */
public class Registry<T> {
    protected final Map<Integer, T> idToValueMap = new HashMap<>();
    protected final Map<T, Integer> valueToIdMap = new HashMap<>();

    public void register(int id, T value) {
        idToValueMap.put(id, value);
        valueToIdMap.put(value, id);
    }

    public T lookup(int id) {
        return idToValueMap.get(id);
    }

    public int getId(T value) {
        return valueToIdMap.get(value);
    }
}
