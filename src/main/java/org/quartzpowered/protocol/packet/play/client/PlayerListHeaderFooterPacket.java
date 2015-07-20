package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.chat.component.BaseComponent;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerListHeaderFooterPacket extends Packet {
    private BaseComponent header;
    private BaseComponent footer;
}
