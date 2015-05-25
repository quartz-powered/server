package org.quartzpowered.engine.level;

import lombok.Getter;
import org.quartzpowered.engine.component.GameObject;
import org.quartzpowered.engine.component.GameObjectFactory;

import javax.inject.Inject;

public class Level {
    @Getter
    private final GameObject root;

    @Inject
    private Level(GameObjectFactory gameObjectFactory) {
        this.root = gameObjectFactory.create();
    }
}
