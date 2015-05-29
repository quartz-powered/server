package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
@Data
@EqualsAndHashCode(callSuper = true)
public class SetExperiencePacket extends Packet {
    private float experienceBar;
    private int level;
    private int totalExperience;
}
