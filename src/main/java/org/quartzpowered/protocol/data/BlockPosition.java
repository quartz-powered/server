package org.quartzpowered.protocol.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockPosition {
    private int x, y, z;

    private void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
