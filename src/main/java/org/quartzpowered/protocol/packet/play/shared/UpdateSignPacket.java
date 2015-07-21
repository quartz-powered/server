package org.quartzpowered.protocol.packet.play.shared;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.BlockPosition;
import org.quartzpowered.protocol.data.chat.component.BaseComponent;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateSignPacket extends Packet {
    private BlockPosition location;
    private BaseComponent line1;
    private BaseComponent line2;
    private BaseComponent line3;
    private BaseComponent line4;
}
