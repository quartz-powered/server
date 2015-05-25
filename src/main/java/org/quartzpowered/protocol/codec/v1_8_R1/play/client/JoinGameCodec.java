package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.Difficulty;
import org.quartzpowered.protocol.data.Dimension;
import org.quartzpowered.protocol.data.Gamemode;
import org.quartzpowered.protocol.packet.play.client.JoinGamePacket;

public class JoinGameCodec implements Codec<JoinGamePacket> {
    @Override
    public void encode(Buffer buffer, JoinGamePacket packet) {
        buffer.writeInt(packet.getEntityId());
        buffer.writeByte(packet.getGamemode().getId() | (packet.isHardcore() ? 0x8 : 0));
        buffer.writeByte(packet.getDimension().getId());
        buffer.writeByte(packet.getDifficulty().getId());
        buffer.writeByte(packet.getMaxPlayers());
        buffer.writeString(packet.getLevelType());
        buffer.writeBoolean(packet.isReducedDebugInfo());
    }

    @Override
    public void decode(Buffer buffer, JoinGamePacket packet) {
        packet.setEntityId(buffer.readInt());

        int gamemode = buffer.readByte();

        packet.setGamemode(Gamemode.fromId(gamemode & 0x7));
        packet.setHardcore((gamemode & 0x8) != 0);

        packet.setDimension(Dimension.fromId(buffer.readByte()));
        packet.setDifficulty(Difficulty.fromId(buffer.readByte()));
        packet.setMaxPlayers(buffer.readByte());
        packet.setLevelType(buffer.readString());
        packet.setReducedDebugInfo(buffer.readBoolean());
    }
}
