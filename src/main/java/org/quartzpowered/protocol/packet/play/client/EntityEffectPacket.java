package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.PotionEffect;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityEffectPacket extends Packet {
    private int entityId;
    private PotionEffect effect;
    private int amplifier;
    private int duration;
    private boolean hideParticles;
}
