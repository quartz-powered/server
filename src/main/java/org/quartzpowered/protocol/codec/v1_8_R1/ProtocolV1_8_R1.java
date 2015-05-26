/*
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.quartzpowered.protocol.codec.v1_8_R1;

import org.quartzpowered.protocol.codec.indentifier.IdentifierProtocol;
import org.quartzpowered.protocol.codec.indentifier.common.client.KickCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.common.client.CompressionCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.client.EncryptionRequestCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.client.LoginResponseCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.server.EncryptionResponseCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.server.LoginRequestCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.play.client.*;
import org.quartzpowered.protocol.codec.v1_8_R1.play.server.*;
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
import org.quartzpowered.protocol.packet.play.server.*;
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
        serverBoundPacket(PLAY, 0x01, ChatMessagePacketIn.class, new ChatMessageCodecIn());
        serverBoundPacket(PLAY, 0x03, PlayerPacketIn.class, new PlayerCodecIn());
        serverBoundPacket(PLAY, 0x04, PlayerPositionPacketIn.class, new PlayerPositionCodecIn());
        serverBoundPacket(PLAY, 0x05, PlayerLookPacketIn.class, new PlayerLookCodecIn());
        serverBoundPacket(PLAY, 0x06, PlayerPositionLookPacketIn.class, new PlayerPositionLookCodecIn());
        serverBoundPacket(PLAY, 0x09, HeldItemChangePacketIn.class, new HeldItemChangeCodecIn());
        serverBoundPacket(PLAY, 0x0D, CloseWindowPacketIn.class, new CloseWindowCodecIn());
        serverBoundPacket(PLAY, 0x13, PlayerAbilitiesPacketIn.class, new PlayerAbilitiesCodecIn());
        serverBoundPacket(PLAY, 0x15, ClientSettingsPacketIn.class, new ClientSettingsCodecIn());
        serverBoundPacket(PLAY, 0x16, ClientStatusPacketIn.class, new ClientStatusCodecIn());
        serverBoundPacket(PLAY, 0x17, PluginMessagePacketIn.class, new PluginMessageCodecIn());


        clientBoundPacket(PLAY, 0x00, KeepAlivePacket.class, new KeepAliveCodec());
        clientBoundPacket(PLAY, 0x01, JoinGamePacketOut.class, new JoinGameCodecOut());
        clientBoundPacket(PLAY, 0x02, ChatMessagePacketOut.class, new ChatMessageCodecOut());
        clientBoundPacket(PLAY, 0x06, UpdateHealthPacketOut.class, new UpdateHealthCodecOut());
        clientBoundPacket(PLAY, 0x08, PlayerPositionLookPacketOut.class, new PlayerPositionLookCodecOut());
        serverBoundPacket(PLAY, 0x09, HeldItemChangePacketOut.class, new HeldItemChangeCodecOut());
        clientBoundPacket(PLAY, 0x21, ChunkDataPacketOut.class, new ChunkDataCodecOut());
        clientBoundPacket(PLAY, 0x26, ChunkBulkPacketOut.class, new ChunkBulkCodecOut());
        clientBoundPacket(PLAY, 0x40, KickPacket.class, new KickCodec());
        clientBoundPacket(PLAY, 0x46, CompressionPacket.class, new CompressionCodec());

    }
}
