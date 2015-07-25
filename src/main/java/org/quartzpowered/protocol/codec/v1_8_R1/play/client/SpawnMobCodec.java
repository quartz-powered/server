package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.metadata.Metadata;
import org.quartzpowered.protocol.packet.play.client.SpawnMobPacket;

public class SpawnMobCodec implements Codec<SpawnMobPacket> {
    @Override
    public void encode(Buffer buffer, SpawnMobPacket packet) {
        buffer.writeVarInt(packet.getEntityId());
        buffer.writeByte(packet.getType());
        buffer.writeFixedPointInt(packet.getX());
        buffer.writeFixedPointInt(packet.getY());
        buffer.writeFixedPointInt(packet.getZ());
        buffer.writeAngle(packet.getYaw());
        buffer.writeAngle(packet.getPitch());
        buffer.writeAngle(packet.getHeadPitch());
        buffer.writeFixedPointShort(packet.getVelocityX());
        buffer.writeFixedPointShort(packet.getVelocityY());
        buffer.writeFixedPointShort(packet.getVelocityZ());
        packet.getMetadata().write(buffer);
    }

    @Override
    public void decode(Buffer buffer, SpawnMobPacket packet) {
        packet.setEntityId(buffer.readVarInt());
        packet.setType(buffer.readUnsignedByte());
        packet.setX(buffer.readFixedPointInt());
        packet.setY(buffer.readFixedPointInt());
        packet.setZ(buffer.readFixedPointInt());
        packet.setYaw(buffer.readAngle());
        packet.setPitch(buffer.readAngle());
        packet.setHeadPitch(buffer.readAngle());
        packet.setVelocityX(buffer.readFixedPointShort());
        packet.setVelocityY(buffer.readFixedPointShort());
        packet.setVelocityZ(buffer.readFixedPointShort());
        packet.setMetadata(new Metadata().read(buffer));
    }
}
