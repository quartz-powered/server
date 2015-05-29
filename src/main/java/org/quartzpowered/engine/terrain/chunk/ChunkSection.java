package org.quartzpowered.engine.terrain.chunk;

public class ChunkSection {
    private final byte[] types = new byte[16 * 16 * 16 * 2];

    public static int getIndex(int x, int y, int z) {
        return ((y << 8) | (z << 4) | x) << 1;
    }

    public static int getType(int id, int meta) {
        return (id << 4) | meta;
    }

    public static int getId(int type) {
        return (type >> 4);
    }

    public static int getMeta(int type) {
        return type & 0xf;
    }

    public void setType(int x, int y, int z, int type) {
        final int index = getIndex(x, y, z);

        types[index] = (byte) (type & 0xff);
        types[index + 1] = (byte) ((type >> 8) & 0xff);
    }

    public int getType(int x, int y, int z) {
        final int index = getIndex(x, y, z);

        return types[index] & 0xff |
                ((types[index + 1] & 0xff) << 8);
    }
}
