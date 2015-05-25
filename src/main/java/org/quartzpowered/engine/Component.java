package org.quartzpowered.engine;

import com.google.inject.assistedinject.Assisted;
import lombok.Getter;

import javax.inject.Inject;

public class Component {
    @Getter
    private GameObject gameObject;

    public void setGameObject(GameObject gameObject) {
        if (this.gameObject != null) {
            throw new IllegalStateException("gameObject already set");
        }
        this.gameObject = gameObject;
    }

    
}
