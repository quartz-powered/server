package org.quartzpowered.server.network.auth;

import org.quartzpowered.network.session.Session;

public interface AuthRunnableFactory {
    public AuthRunnable create(Session session, String hash);
}
