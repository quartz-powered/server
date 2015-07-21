package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.server.TabCompletePacket;

public class TabCompleteCodec implements Codec<TabCompletePacket> {
    @Override
    public void encode(Buffer buffer, TabCompletePacket packet) {
        buffer.writeString(packet.getText());
        buffer.writeBoolean(packet.isHasPosition());

        if (packet.isHasPosition()) {
           buffer.writeBlockPosition(packet.getLookedAtBlock());
        }
    }

    @Override
    public void decode(Buffer buffer, TabCompletePacket packet) {
        packet.setText(buffer.readString());

        boolean hasPosition = buffer.readBoolean();
        packet.setHasPosition(hasPosition);

        if (hasPosition) {
            packet.setLookedAtBlock(buffer.readBlockPosition());
        }
    }
}
