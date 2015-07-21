package org.quartzpowered.protocol.data;

import lombok.Data;

@Data
public class RelativeBlockRecord {
    private int x;
    private int z;
    private int y;
    private int id;
    private int data;
}
