package org.quartzpowered.protocol.data;

import lombok.Data;

@Data
public class BlockPosition {
    private int x, y, z;

    public BlockPosition() {
        set(0, 0, 0);
    }

    public BlockPosition(int x, int y, int z) {
        set(x, y, z);
    }

    private void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
