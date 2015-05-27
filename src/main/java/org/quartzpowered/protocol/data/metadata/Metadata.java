package org.quartzpowered.protocol.data.metadata;

import org.quartzpowered.engine.math.Vector3;
import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.protocol.data.BlockPosition;
import org.quartzpowered.protocol.data.ItemSlot;

public class Metadata {
    private final Object[] values = new Object[0x1F];

    public void set(int index, Object value) {
        values[index] = value;
    }

    public void setByte(int index, int value) {
        set(index, (byte) value);
    }

    public void setInt(int index, int value) {
        set(index, value);
    }

    public void setFloat(int index, float value) {
        set(index, value);
    }

    public void setString(int index, String value) {
        set(index, value);
    }

    public void setItemSlot(int index, ItemSlot value) {
        set(index, value);
    }

    public void setBlockPosition(int index, BlockPosition value) {
        set(index, value);
    }

    public void setRotation(int index, Vector3 value) {
        set(index, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        return (T) values[index];
    }

    public int getByte(int index) {
        return this.<Byte>get(index);
    }

    public int getInt(int index) {
        return this.<Integer>get(index);
    }

    public String getString(int index) {
        return this.<String>get(index);
    }

    public ItemSlot getItemSlot(int index) {
        return this.<ItemSlot>get(index);
    }

    public BlockPosition getBlockPosition(int index) {
        return this.<BlockPosition>get(index);
    }

    public Vector3 getRotation(int index) {
        return this.<Vector3>get(index);
    }

    public void write(Buffer buffer) {
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            if (value == null) {
                continue;
            }
            MetadataType type = typeOf(value);

            int item = i | type.ordinal() << 5;
            buffer.writeByte(item);
            type.write(buffer, value);
        }
        buffer.writeByte(0x7F);
    }

    public void read(Buffer buffer) {
        while (true) {
            int item = buffer.readUnsignedByte();
            if (item == 0x7F) {
                break;
            }

            int index = item & 0x1F;
            MetadataType type = MetadataType.fromId(item >> 5);

            // todo type.read
        }
    }

    private MetadataType typeOf(Object value) {
        if (value instanceof Byte) {
            return MetadataType.BYTE;
        } else if (value instanceof Short) {
            return MetadataType.SHORT;
        } else if (value instanceof Integer) {
            return MetadataType.INT;
        } else if (value instanceof Float) {
            return MetadataType.FLOAT;
        } else if (value instanceof String) {
            return MetadataType.STRING;
        } else if (value instanceof ItemSlot) {
            return MetadataType.ITEM_SLOT;
        } else if (value instanceof BlockPosition) {
            return MetadataType.BLOCK_POSITION;
        } else if (value instanceof Vector3) {
            return MetadataType.ROTATION;
        }
        return null;
    }
}
