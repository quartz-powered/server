package org.quartzpowered.protocol.packet.play.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpectatePacket extends Packet {
    private UUID targetPlayer;
}
