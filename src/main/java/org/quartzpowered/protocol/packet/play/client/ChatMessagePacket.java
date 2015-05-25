package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.ChatPosition;
import org.quartzpowered.protocol.data.component.TextComponent;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatMessagePacket extends Packet {
    private TextComponent message;
    private ChatPosition position;
}
