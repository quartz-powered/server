package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.Difficulty;
import org.quartzpowered.protocol.data.Dimension;
import org.quartzpowered.protocol.data.Gamemode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JoinGamePacket extends Packet {
    private int entityId;
    private Gamemode gamemode;
    private boolean hardcore;
    private Dimension dimension;
    private Difficulty difficulty;
    private int maxPlayers;
    private String levelType;
    private boolean reducedDebugInfo;
}
