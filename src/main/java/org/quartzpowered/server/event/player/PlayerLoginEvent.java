package org.quartzpowered.server.event.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.server.event.CancelableEvent;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerLoginEvent extends CancelableEvent {
    private final Session session;
}
