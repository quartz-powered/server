package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.ScoreboardPosition;
import org.quartzpowered.protocol.packet.play.client.DisplayScoreboardPacket;

public class DisplayScoreboardCodec implements Codec<DisplayScoreboardPacket> {
    @Override
    public void encode(Buffer buffer, DisplayScoreboardPacket packet) {
        buffer.writeByte(packet.getPosition().getId());
        buffer.writeString(packet.getScoreName());
    }

    @Override
    public void decode(Buffer buffer, DisplayScoreboardPacket packet) {
        packet.setPosition(ScoreboardPosition.fromId(buffer.readByte()));
        packet.setScoreName(buffer.readString());
    }
}
