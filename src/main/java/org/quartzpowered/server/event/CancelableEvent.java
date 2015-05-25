package org.quartzpowered.server.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CancelableEvent extends Event {
    private boolean canceled;
}
