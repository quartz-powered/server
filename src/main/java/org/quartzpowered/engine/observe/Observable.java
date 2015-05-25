package org.quartzpowered.engine.observe;

public interface Observable {
    void startObserving(Observer observer);
    void endObserving(Observer observer);
}
