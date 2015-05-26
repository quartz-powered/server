package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

/**
 * Created by Ryan on 5/25/2015
 * <p>
 * Project: server
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateHealthPacket extends Packet {
    float health;
    int foodLevel;
    float saturation;
}
