package org.quartzpowered.network.session.profile;

import lombok.Data;

@Data
public final class PlayerProperty {
    private final String name;
    private final String value;
    private final String signature;
}
