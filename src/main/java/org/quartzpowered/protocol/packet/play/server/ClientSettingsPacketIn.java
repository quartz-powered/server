package org.quartzpowered.protocol.packet.play.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.ChatMode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientSettingsPacketIn extends Packet {
    private String locale;
    private int viewDistance;
    private ChatMode chatMode;
    private boolean chatColors;
    private int displayedSkinParts;
}
