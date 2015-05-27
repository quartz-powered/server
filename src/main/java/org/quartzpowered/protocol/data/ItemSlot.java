package org.quartzpowered.protocol.data;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartzpowered.network.buffer.Buffer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSlot {
    private int itemId;
    private int count;
    private int damage;

    public void write(Buffer buffer) {
        buffer.writeShort(itemId);
        buffer.writeByte(count);
        buffer.writeShort(damage);
        buffer.writeByte(0);
    }
}
