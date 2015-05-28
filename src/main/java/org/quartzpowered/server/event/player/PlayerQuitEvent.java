package org.quartzpowered.server.event.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.server.event.Event;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerQuitEvent extends Event {
    private final Session session;
}
