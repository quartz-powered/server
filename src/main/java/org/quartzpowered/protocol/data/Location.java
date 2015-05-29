package org.quartzpowered.protocol.data;

import lombok.Data;

@Data
public class Location {
    double x;
    double y;
    double z;
    double yaw;
    double pitch;

    public Location(int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }

    public Location(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }

    public Location(int x, int y, int z, float yaw, float pitch) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;

    }

    public Location(double x, double y, double z, float yaw, float pitch) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;

    }

}
