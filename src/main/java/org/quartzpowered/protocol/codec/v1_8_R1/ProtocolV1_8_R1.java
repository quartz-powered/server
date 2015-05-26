package org.quartzpowered.protocol.codec.v1_8_R1;

import org.quartzpowered.protocol.codec.indentifier.IdentifierProtocol;
import org.quartzpowered.protocol.codec.indentifier.common.client.KickCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.common.client.CompressionCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.client.EncryptionRequestCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.client.LoginResponseCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.server.EncryptionResponseCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.server.LoginRequestCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.play.client.*;
import org.quartzpowered.protocol.codec.v1_8_R1.play.shared.KeepAliveCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.status.client.PongCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.status.client.StatusResponseCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.status.server.PingCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.status.server.StatusRequestCodec;
import org.quartzpowered.protocol.packet.common.client.CompressionPacket;
import org.quartzpowered.protocol.packet.common.client.KickPacket;
import org.quartzpowered.protocol.packet.login.client.EncryptionRequestPacket;
import org.quartzpowered.protocol.packet.login.client.LoginResponsePacket;
import org.quartzpowered.protocol.packet.login.server.EncryptionResponsePacket;
import org.quartzpowered.protocol.packet.login.server.LoginRequestPacket;
import org.quartzpowered.protocol.packet.play.client.*;
import org.quartzpowered.protocol.packet.play.shared.KeepAlivePacket;
import org.quartzpowered.protocol.packet.status.client.PongPacket;
import org.quartzpowered.protocol.packet.status.client.StatusResponsePacket;
import org.quartzpowered.protocol.packet.status.server.PingPacket;
import org.quartzpowered.protocol.packet.status.server.StatusRequestPacket;

import static org.quartzpowered.network.protocol.ProtocolState.*;

public class ProtocolV1_8_R1 extends IdentifierProtocol {
    @Override
    public String getName() {
        return "1.8";
    }

    @Override
    public int getVersion() {
        return 47;
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();

        serverBoundPacket(STATUS, 0x00, StatusRequestPacket.class, new StatusRequestCodec());
        serverBoundPacket(STATUS, 0x01, PingPacket.class, new PingCodec());

        clientBoundPacket(STATUS, 0x00, StatusResponsePacket.class, new StatusResponseCodec());
        clientBoundPacket(STATUS, 0x01, PongPacket.class, new PongCodec());

        serverBoundPacket(LOGIN, 0x00, LoginRequestPacket.class, new LoginRequestCodec());
        serverBoundPacket(LOGIN, 0x01, EncryptionResponsePacket.class, new EncryptionResponseCodec());

        clientBoundPacket(LOGIN, 0x01, EncryptionRequestPacket.class, new EncryptionRequestCodec());
        clientBoundPacket(LOGIN, 0x02, LoginResponsePacket.class, new LoginResponseCodec());

        serverBoundPacket(PLAY, 0x00, KeepAlivePacket.class, new KeepAliveCodec());

        clientBoundPacket(PLAY, 0x00, KeepAlivePacket.class, new KeepAliveCodec());
        clientBoundPacket(PLAY, 0x01, JoinGamePacket.class, new JoinGameCodec());
        clientBoundPacket(PLAY, 0x02, ChatMessagePacket.class, new ChatMessageCodec());
        clientBoundPacket(PLAY, 0x06, UpdateHealthPacket.class, new UpdateHealthCodec());
        clientBoundPacket(PLAY, 0x08, PlayerPositionAndLookPacket.class, new PlayerPositionAndLookCodec());
        clientBoundPacket(PLAY, 0x21, ChunkDataPacket.class, new ChunkDataCodec());
        clientBoundPacket(PLAY, 0x26, ChunkBulkPacket.class, new ChunkBulkCodec());
        clientBoundPacket(PLAY, 0x40, KickPacket.class, new KickCodec());
        clientBoundPacket(PLAY, 0x46, CompressionPacket.class, new CompressionCodec());

    }
}
