package org.quartzpowered.engine.component;

import lombok.Getter;
import org.quartzpowered.engine.observe.Observable;
import org.quartzpowered.engine.observe.Observer;

public abstract class Component implements Observable {
    @Getter
    private GameObject gameObject;

    public void setGameObject(GameObject gameObject) {
        if (this.gameObject != null) {
            throw new IllegalStateException("gameObject already set");
        }
        this.gameObject = gameObject;
    }

    @Override
    public void startObserving(Observer observer) {

    }

    @Override
    public void endObserving(Observer observer) {

    }
}
