package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.EntityAction;
import org.quartzpowered.protocol.packet.play.server.EntityActionPacket;

public class EntityActionCodec implements Codec<EntityActionPacket> {
    @Override
    public void encode(Buffer buffer, EntityActionPacket packet) {
        buffer.writeVarInt(packet.getEntityID());
        buffer.writeVarInt(packet.getActionId().getId());
        buffer.writeVarInt(packet.getHorseJumpBoost());
    }

    @Override
    public void decode(Buffer buffer, EntityActionPacket packet) {
        packet.setEntityID(buffer.readVarInt());
        packet.setActionId(EntityAction.fromId(buffer.readVarInt()));
        packet.setHorseJumpBoost(buffer.readVarInt());
    }
}
