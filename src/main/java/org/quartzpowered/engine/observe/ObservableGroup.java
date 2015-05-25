package org.quartzpowered.engine.observe;

import lombok.Getter;
import org.quartzpowered.network.protocol.packet.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ObservableGroup implements Observable, Observer {
    @Getter
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void startObserving(Observer observer) {
        forEachChild(child -> child.startObserving(observer));
        observers.add(observer);
    }

    @Override
    public void endObserving(Observer observer) {
        forEachChild(child -> child.endObserving(observer));
    }

    @Override
    public void observe(Packet packet) {
        observers.forEach(observer -> observer.observe(packet));
    }

    protected abstract void forEachChild(Consumer<Observable> action);
}
