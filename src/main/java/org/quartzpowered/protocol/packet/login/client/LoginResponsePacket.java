package org.quartzpowered.protocol.packet.login.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponsePacket extends Packet {
    private UUID uuid;
    private String username;
}
