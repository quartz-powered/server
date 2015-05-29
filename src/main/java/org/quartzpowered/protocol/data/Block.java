package org.quartzpowered.protocol.data;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class Block {
    int id;
    int count;
    short damage;

    public Block(int id, int count, short damage) {

        this.id = id;
        this.count = count;
        this.damage = damage;

    }

}
