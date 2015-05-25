package org.quartzpowered.engine.observe;

import com.google.inject.assistedinject.Assisted;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.session.Session;

import javax.inject.Inject;

public class SessionObserver implements Observer {
    @Inject @Assisted
    Session session;

    @Override
    public void observe(Packet packet) {

    }
}
