package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.CombatEvent;
import org.quartzpowered.protocol.packet.play.client.CombatEventPacket;

public class CombatEventCodec implements Codec<CombatEventPacket> {
    @Override
    public void encode(Buffer buffer, CombatEventPacket packet) {
        buffer.writeVarInt(packet.getEvent().getId());

        switch (packet.getEvent()) {
            case ENTER_COMBAT:
                break;
            case END_COMBAT:
                buffer.writeVarInt(packet.getDuration());
                buffer.writeInt(packet.getEntityId());
                break;
            case ENTITY_DEAD:
                buffer.writeVarInt(packet.getPlayerId());
                buffer.writeInt(packet.getEntityId());
                buffer.writeString(packet.getMessage());
                break;
        }
    }

    @Override
    public void decode(Buffer buffer, CombatEventPacket packet) {
        packet.setEvent(CombatEvent.fromId(buffer.readVarInt()));

        switch (packet.getEvent()) {
            case ENTER_COMBAT:
                break;
            case END_COMBAT:
                packet.setDuration(buffer.readVarInt());
                packet.setEntityId(buffer.readInt());
                break;
            case ENTITY_DEAD:
                packet.setPlayerId(buffer.readVarInt());
                packet.setEntityId(buffer.readInt());
                packet.setMessage(buffer.readString());
                break;
        }
    }
}
