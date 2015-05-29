package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.Difficulty;
import org.quartzpowered.protocol.data.Dimension;
import org.quartzpowered.protocol.data.Gamemode;
import org.quartzpowered.protocol.packet.play.client.RespawnPacket;

public class RespawnCodec implements Codec<RespawnPacket> {

    @Override
    public void encode(Buffer buffer, RespawnPacket packet) {
        buffer.writeInt(packet.getDimension().getId());
        buffer.writeByte(packet.getDifficulty().getId());
        buffer.writeByte(packet.getGamemode().getId());
        buffer.writeString(packet.getLevelType());
    }

    @Override
    public void decode(Buffer buffer, RespawnPacket packet) {
        packet.setDimension(Dimension.fromId(buffer.readInt()));
        packet.setDifficulty(Difficulty.fromId(buffer.readInt()));
        packet.setGamemode(Gamemode.fromId(buffer.readInt()));
        packet.setLevelType(buffer.readString());
    }
}
