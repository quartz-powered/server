package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.WorldBorderAction;
import org.quartzpowered.protocol.packet.play.client.WorldBorderPacket;

public class WorldBorderCodec implements Codec<WorldBorderPacket> {
    @Override
    public void encode(Buffer buffer, WorldBorderPacket packet) {
        buffer.writeVarInt(packet.getAction().getId());

        switch (packet.getAction()) {
            case SET_SIZE:
                buffer.writeDouble(packet.getNewRadius());
                break;
            case LERP_SIZE:
                buffer.writeDouble(packet.getOldRadius());
                buffer.writeDouble(packet.getNewRadius());
                buffer.writeVarLong(packet.getSpeed());
                break;
            case SET_CENTER:
                buffer.writeDouble(packet.getX());
                buffer.writeDouble(packet.getZ());
                break;
            case INITIALIZE:
                buffer.writeDouble(packet.getX());
                buffer.writeDouble(packet.getZ());
                buffer.writeDouble(packet.getOldRadius());
                buffer.writeDouble(packet.getNewRadius());
                buffer.writeVarLong(packet.getSpeed());
                buffer.writeVarInt(packet.getPortalTeleportBoundary());
                buffer.writeVarInt(packet.getWarningTime());
                buffer.writeVarInt(packet.getWarningBlocks());
                break;
            case SET_WARNING_TIME:
                buffer.writeVarInt(packet.getWarningTime());
                break;
            case SET_WARNING_BLOCKS:
                buffer.writeVarInt(packet.getWarningBlocks());
                break;
        }
    }

    @Override
    public void decode(Buffer buffer, WorldBorderPacket packet) {
        packet.setAction(WorldBorderAction.fromId(buffer.readVarInt()));

        switch (packet.getAction()) {
            case SET_SIZE:
                packet.setNewRadius(buffer.readDouble());
                break;
            case LERP_SIZE:
                packet.setOldRadius(buffer.readDouble());
                packet.setNewRadius(buffer.readDouble());
                packet.setSpeed(buffer.readVarLong());
                break;
            case SET_CENTER:
                packet.setX(buffer.readDouble());
                packet.setZ(buffer.readDouble());
                break;
            case INITIALIZE:
                packet.setX(buffer.readDouble());
                packet.setZ(buffer.readDouble());
                packet.setOldRadius(buffer.readDouble());
                packet.setNewRadius(buffer.readDouble());
                packet.setSpeed(buffer.readVarLong());
                packet.setPortalTeleportBoundary(buffer.readVarInt());
                packet.setWarningTime(buffer.readVarInt());
                packet.setWarningBlocks(buffer.readVarInt());
                break;
            case SET_WARNING_TIME:
                packet.setWarningTime(buffer.readVarInt());
                break;
            case SET_WARNING_BLOCKS:
                packet.setWarningBlocks(buffer.readVarInt());
                break;
        }
    }
}
