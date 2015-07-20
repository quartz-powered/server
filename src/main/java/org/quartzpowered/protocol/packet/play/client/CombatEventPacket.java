package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.CombatEvent;

@Data
@EqualsAndHashCode(callSuper = true)
public class CombatEventPacket extends Packet {
    private CombatEvent event;
    private int duration;
    private int playerId;
    private int entityId;
    private String message;
}
