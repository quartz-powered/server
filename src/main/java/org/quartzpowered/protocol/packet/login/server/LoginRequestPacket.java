package org.quartzpowered.protocol.packet.login.server;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequestPacket extends Packet {
    private String username;
}
