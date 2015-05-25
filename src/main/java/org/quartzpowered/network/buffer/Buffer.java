package org.quartzpowered.network.buffer;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Delegate;

import static java.nio.charset.StandardCharsets.UTF_8;

@Data
@EqualsAndHashCode(callSuper = false)
public class Buffer extends ByteBuf {
    @Delegate
    private final ByteBuf buf;

    public int readVarInt() {
        int result = 0;
        int count = 0;
        while (true) {
            byte in = readByte();
            result |= (in & 0x7f) << (count++ * 7);
            if (count > 5) {
                throw new BufferException("VarInt byte count > 5");
            }
            if ((in & 0x80) != 0x80) {
                break;
            }
        }
        return result;
    }

    public void writeVarInt(int value) {
        while (true) {
            byte part = (byte) (value & 0x7f);
            value >>>= 7;
            if (value != 0) {
                part |= 0x80;
            }
            writeByte(part);
            if (value == 0) {
                break;
            }
        }
    }

    public byte[] readByteArray() {
        int size = readVarInt();
        byte[] result = new byte[size];
        readBytes(result);
        return result;
    }

    public void writeByteArray(@NonNull byte[] value) {
        writeVarInt(value.length);
        writeBytes(value);
    }

    public String readString() {
        return new String(readByteArray(), UTF_8);
    }

    public void writeString(String value) {
        writeByteArray(value.getBytes(UTF_8));
    }
}
