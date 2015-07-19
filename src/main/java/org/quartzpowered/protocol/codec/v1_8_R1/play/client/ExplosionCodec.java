package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.BlockPosition;
import org.quartzpowered.protocol.packet.play.client.ExplosionPacket;

import java.util.List;

public class ExplosionCodec implements Codec<ExplosionPacket> {
    @Override
    public void encode(Buffer buffer, ExplosionPacket packet) {
        buffer.writeFloat((float) packet.getX());
        buffer.writeFloat((float) packet.getY());
        buffer.writeFloat((float) packet.getZ());
        buffer.writeFloat((float) packet.getRadius());

        List<BlockPosition> relativeBlockChanges = packet.getRelativeBlockChanges();
        buffer.writeInt(relativeBlockChanges.size());
        for (BlockPosition pos : relativeBlockChanges) {
            buffer.writeByte(pos.getX());
            buffer.writeByte(pos.getY());
            buffer.writeByte(pos.getZ());
        }

        buffer.writeFloat((float) packet.getPlayerMotionX());
        buffer.writeFloat((float) packet.getPlayerMotionY());
        buffer.writeFloat((float) packet.getPlayerMotionZ());
    }

    @Override
    public void decode(Buffer buffer, ExplosionPacket packet) {
        packet.setX(buffer.readFloat());
        packet.setY(buffer.readFloat());
        packet.setZ(buffer.readFloat());
        packet.setRadius(buffer.readFloat());

        List<BlockPosition> relativeBlockChanges = packet.getRelativeBlockChanges();
        int count = buffer.readInt();
        for (int i = 0; i < count; i++) {
            relativeBlockChanges.add(new BlockPosition(
                    buffer.readByte(),
                    buffer.readByte(),
                    buffer.readByte()
            ));
        }

        packet.setPlayerMotionX(buffer.readFloat());
        packet.setPlayerMotionY(buffer.readFloat());
        packet.setPlayerMotionZ(buffer.readFloat());
    }
}
