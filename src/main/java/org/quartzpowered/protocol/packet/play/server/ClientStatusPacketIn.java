package org.quartzpowered.protocol.packet.play.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.ClientStatusAction;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientStatusPacketIn extends Packet {
    private ClientStatusAction actionId;
}
