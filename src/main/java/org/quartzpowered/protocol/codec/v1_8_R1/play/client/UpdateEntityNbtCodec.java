package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import com.flowpowered.nbt.stream.NBTInputStream;
import com.flowpowered.nbt.stream.NBTOutputStream;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.protocol.packet.play.client.UpdateEntityNbtPacket;

import java.io.IOException;

public class UpdateEntityNbtCodec implements Codec<UpdateEntityNbtPacket> {
    @Override
    public void encode(Buffer buffer, UpdateEntityNbtPacket packet) {
        buffer.writeVarInt(packet.getEntityId());

        try {
            NBTOutputStream nos = new NBTOutputStream(new ByteBufOutputStream(buffer));
            nos.writeTag(packet.getTag());
            nos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void decode(Buffer buffer, UpdateEntityNbtPacket packet) {
        packet.setEntityId(buffer.readVarInt());

        try {
            NBTInputStream nis = new NBTInputStream(new ByteBufInputStream(buffer));
            packet.setTag(nis.readTag());
            nis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
