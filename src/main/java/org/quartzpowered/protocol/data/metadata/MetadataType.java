package org.quartzpowered.protocol.data.metadata;

import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.protocol.data.BlockPosition;
import org.quartzpowered.protocol.data.ItemSlot;

import java.io.IOException;
import java.util.Vector;

public enum MetadataType {
    BYTE((buf, obj) -> buf.writeByte((Byte) obj)),
    SHORT((buf, obj) -> buf.writeShort((Short) obj)),
    INT((buf, obj) -> buf.writeInt((Integer) obj)),
    FLOAT((buf, obj) -> buf.writeFloat((Float) obj)),
    STRING((buf, obj) -> buf.writeString((String) obj)),
    ITEM_SLOT((buf, obj) -> ((ItemSlot) obj).write(buf)),
    BLOCK_POSITION((buf, obj) -> {
        BlockPosition position = (BlockPosition) obj;
        buf.writeInt(position.getX());
        buf.writeInt(position.getY());
        buf.writeInt(position.getZ());
    }),
    ROTATION((buf, obj) -> {
        Vector3 vec = (Vector3) obj;
        buf.writeFloat((float) vec.getX());
        buf.writeFloat((float) vec.getY());
        buf.writeFloat((float) vec.getZ());
    });

    private WriteHandler writeHandler;

    MetadataType(WriteHandler writeHandler) {
        this.writeHandler = writeHandler;
    }

    private static interface WriteHandler {
        public void write(Buffer buf, Object obj) throws IOException;
    }

    public void write(Buffer buf, Object obj) {
        try {
            writeHandler.write(buf, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return ordinal();
    }

    public static MetadataType fromId(int id) {
        return values()[id];
    }
}
