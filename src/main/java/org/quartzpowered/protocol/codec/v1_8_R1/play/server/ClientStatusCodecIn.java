package org.quartzpowered.protocol.codec.v1_8_R1.play.server;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.data.ClientStatusAction;
import org.quartzpowered.protocol.packet.play.server.ClientStatusPacketIn;

public class ClientStatusCodecIn implements Codec<ClientStatusPacketIn> {
    @Override
    public void encode(Buffer buffer, ClientStatusPacketIn packet) {
        buffer.writeVarInt(packet.getActionId().getId());
    }

    @Override
    public void decode(Buffer buffer, ClientStatusPacketIn packet) {
        packet.setActionId(ClientStatusAction.fromId(buffer.readVarInt()));
    }
}
