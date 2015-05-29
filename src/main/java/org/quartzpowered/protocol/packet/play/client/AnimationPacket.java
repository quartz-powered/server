package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.Animation;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnimationPacket extends Packet {
    private int entityId;
    private Animation animation;
}
