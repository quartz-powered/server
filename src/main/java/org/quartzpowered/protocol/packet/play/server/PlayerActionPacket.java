package org.quartzpowered.protocol.packet.play.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.BlockPosition;
import org.quartzpowered.protocol.data.PlayerAction;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerActionPacket extends Packet {
    private PlayerAction action;
    private BlockPosition location;
    private int face;
}
