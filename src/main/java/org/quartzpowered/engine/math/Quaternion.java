/*
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.quartzpowered.engine.math;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Math.*;
import static org.quartzpowered.engine.math.MathUtil.degreesToRadians;
import static org.quartzpowered.engine.math.MathUtil.radiansToDegrees;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quaternion {
    private double x, y, z, w;

    public Quaternion(Quaternion other) {
        set(other);
    }

    public void set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void setAxis(double x, double y, double z, double angle) {
        angle *= degreesToRadians;

        double length = Vector3.length(x, y, z);
        if (length < MathUtil.DOUBLE_ROUNDING_ERROR) {
            this.x = 0.0f;
            this.y = 0.0f;
            this.z = 0.0f;
            this.w = 1.0f;
        } else {
            double sin_half_angle = sin(angle / 2.);
            this.x = sin_half_angle * x / length;
            this.y = sin_half_angle * y / length;
            this.z = sin_half_angle * z / length;
            this.w = cos(angle / 2.);
        }
    }

    public void setEuler(double pitch, double yaw, double roll) {
        Quaternion qx = fromAxis(1, 0, 0, pitch);
        Quaternion qy = fromAxis(0, 1, 0, yaw);
        Quaternion qz = fromAxis(0, 0, 1, roll);
        set(qy);
        multiply(qz);
        multiply(qx);
    }

    public Vector3 getEuler() {
        double pitch, yaw, roll;
        double test = x * y + z * w;
        if (test > 0.499) {
            yaw = 2 * atan2(x, w);
            roll = PI / 2;
            pitch = 0;
            return new Vector3(pitch * radiansToDegrees, yaw * radiansToDegrees, roll * radiansToDegrees);
        }
        if (test < -0.499) {
            yaw = -2 * atan2(x, w);
            roll = -PI / 2;
            pitch = 0;
            return new Vector3(pitch * radiansToDegrees, yaw * radiansToDegrees, roll * radiansToDegrees);
        }
        double sqx = x * x;
        double sqy = y * y;
        double sqz = z * z;
        yaw = atan2(2 * y * w - 2 * x * z, 1 - 2 * sqy - 2 * sqz);
        roll = asin(2 * test);
        pitch = atan2(2 * x * w - 2 * y * z, 1 - 2 * sqx - 2 * sqz);
        return new Vector3(pitch * radiansToDegrees, yaw * radiansToDegrees, roll * radiansToDegrees);
    }

    public void multiply(Quaternion other) {
        final double newX = this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y;
        final double newY = this.w * other.y + this.y * other.w + this.z * other.x - this.x * other.z;
        final double newZ = this.w * other.z + this.z * other.w + this.x * other.y - this.y * other.x;
        final double newW = this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
    }

    public static Quaternion fromEuler(double pitch, double yaw, double roll) {
        Quaternion quaternion = new Quaternion();
        quaternion.setEuler(pitch, yaw, roll);
        return quaternion;
    }

    public static Quaternion fromAxis(double x, double y, double z, double angle) {
        Quaternion quaternion = new Quaternion();
        quaternion.setAxis(x, y, z, angle);
        return quaternion;
    }

    public final double normalize() {
        double norm = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
        if (norm > 0.0f) {
            this.x /= norm;
            this.y /= norm;
            this.z /= norm;
            this.w /= norm;
        } else {
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
            this.w = 1.0;
        }
        return norm;
    }

    @Override
    public String toString() {
        Vector3 euler = getEuler();
        return "Quaternion(pitch=" + euler.getX() + ", yaw=" + euler.getY() + ", roll=" + euler.getZ() + ")";
    }

    public void set(Quaternion other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
    }

    public static Quaternion identity() {
        return new Quaternion(0, 0, 0, 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Quaternion) {
            Quaternion quaternion = (Quaternion) other;
            return this.getEuler().equals(quaternion.getEuler());
        }
        return false;
    }
}
