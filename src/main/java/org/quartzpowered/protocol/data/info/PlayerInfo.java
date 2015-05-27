package org.quartzpowered.protocol.data.info;

import lombok.Data;
import org.quartzpowered.network.session.profile.PlayerProfile;
import org.quartzpowered.protocol.data.Gamemode;
import org.quartzpowered.protocol.data.component.TextComponent;

@Data
public class PlayerInfo {
    private PlayerProfile profile;
    private Gamemode gamemode;
    private int ping;
    private TextComponent displayName;

    public boolean hasDisplayName() {
        return displayName != null;
    }
}
