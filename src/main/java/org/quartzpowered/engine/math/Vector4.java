package org.quartzpowered.engine.math;

import lombok.Data;

@Data
public class Vector4 {
    protected double x, y, z, w;

    public Vector4() {

    }

    public  Vector4(double x, double y, double z, double w) {
        set(x, y, z, w);
    }

    public Vector4 set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vector3 toVector3() {
        return new Vector3(x, y, z);
    }
}
