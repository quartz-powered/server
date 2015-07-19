package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.GameState;
import org.quartzpowered.protocol.packet.play.client.ChangeGameStatePacket;

public class ChangeGameStateCodec implements Codec<ChangeGameStatePacket> {
    @Override
    public void encode(Buffer buffer, ChangeGameStatePacket packet) {
        buffer.writeByte(packet.getReason().getId());

        if (packet.getReason().hasWriteValue()) {
            buffer.writeFloat(packet.getValue());
        }
    }

    @Override
    public void decode(Buffer buffer, ChangeGameStatePacket packet) {
        GameState gameState = GameState.fromId(buffer.readByte());
        packet.setReason(gameState);

        if (gameState.hasWriteValue()){
            packet.setValue(buffer.readFloat());
        }
    }
}
