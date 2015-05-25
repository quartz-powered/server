package org.quartzpowered.protocol.data;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Dimension {
    NETHER(-1),
    OVERWORLD(0),
    END(1);

    @Getter
    private final int id;

    Dimension(int id) {
        this.id = id;
    }

    private static final Map<Integer, Dimension> idMap = new HashMap<>();
    static {
        for (Dimension dimension : values()) {
            idMap.put(dimension.id, dimension);
        }
    }

    public static Dimension fromId(int id) {
        return idMap.get(id);
    }
}
