package org.quartzpowered.network.session.profile;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PlayerProfile {
    public static final int MAX_USERNAME_LENGTH = 16;

    private final String name;
    private final UUID uniqueId;
    private final List<PlayerProperty> properties;

    
}
