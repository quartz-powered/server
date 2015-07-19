package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.GameState;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeGameStatePacket extends Packet {
    private GameState reason;
    private float value;
}
