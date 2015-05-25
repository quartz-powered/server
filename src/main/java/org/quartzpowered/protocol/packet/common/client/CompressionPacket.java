package org.quartzpowered.protocol.packet.common.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompressionPacket extends Packet {
    int threshold;
}
