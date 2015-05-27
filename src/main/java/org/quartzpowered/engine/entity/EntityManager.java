package org.quartzpowered.engine.entity;

import javax.inject.Singleton;

@Singleton
public class EntityManager {
    private int entityId;

    public int nextEntityId() {
        return entityId++;
    }
}
