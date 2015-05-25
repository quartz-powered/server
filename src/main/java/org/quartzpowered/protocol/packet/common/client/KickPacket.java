package org.quartzpowered.protocol.packet.common.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.component.TextComponent;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KickPacket extends Packet {
    private TextComponent message;
}
