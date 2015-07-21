package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.UpdateScorePacket;

public class UpdateScoreCodec implements Codec<UpdateScorePacket> {
    @Override
    public void encode(Buffer buffer, UpdateScorePacket packet) {
        buffer.writeString(packet.getScoreName());
        buffer.writeByte(packet.getAction());
        buffer.writeString(packet.getObjectiveName());

        if (packet.getAction() != 1) {
            buffer.writeVarInt(packet.getValue());
        }
    }

    @Override
    public void decode(Buffer buffer, UpdateScorePacket packet) {
        packet.setScoreName(buffer.readString());
        int action = buffer.readByte();
        packet.setAction(action);
        packet.setObjectiveName(buffer.readString());

        if (action != 1) {
            packet.setValue(buffer.readVarInt());
        }
    }
}
