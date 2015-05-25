package org.quartzpowered.server.event.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.server.event.CancelableEvent;
import org.quartzpowered.server.event.Event;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerLoginEvent extends CancelableEvent {
    private final Session session;
}
