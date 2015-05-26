package org.quartzpowered.engine.math;

import lombok.Data;

@Data
public class Vector3 {
    public static Vector3 back() {
        return new Vector3(0, 0, -1);
    }

    public static Vector3 forward() {
        return new Vector3(0, 0, 1);
    }

    public static Vector3 down() {
        return new Vector3(0, -1, 0);
    }

    public static Vector3 up() {
        return new Vector3(0, 1, 0);
    }

    public static Vector3 left() {
        return new Vector3(-1, 0, 0);
    }

    public static Vector3 right() {
        return new Vector3(1, 0, 0);
    }

    public static Vector3 zero() {
        return new Vector3(0, 0, 0);
    }

    public static Vector3 one() {
        return new Vector3(1, 1, 1);
    }

    public static double length(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    protected double x, y, z;

    public Vector3() {

    }

    public Vector3(double x, double y, double z) {
        set(x, y, z);
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
