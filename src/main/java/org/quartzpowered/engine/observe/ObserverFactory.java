package org.quartzpowered.engine.observe;

import org.quartzpowered.network.session.Session;

import javax.inject.Named;

public interface ObserverFactory {
    @Named("session")
    public Observer create(Session session);
}
