package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.ScoreboardPosition;

@Data
@EqualsAndHashCode(callSuper = true)
public class DisplayScoreboardPacket extends Packet {
    private ScoreboardPosition position;
    private String scoreName;
}
