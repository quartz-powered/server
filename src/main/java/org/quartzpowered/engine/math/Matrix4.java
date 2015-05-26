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

import lombok.Getter;

import java.util.Arrays;

import static org.quartzpowered.engine.math.Matrix4.Constants.*;

public class Matrix4 implements Cloneable {
    protected static class Constants {
        public static final int M00 = 0;
        public static final int M01 = 4;
        public static final int M02 = 8;
        public static final int M03 = 12;
        public static final int M10 = 1;
        public static final int M11 = 5;
        public static final int M12 = 9;
        public static final int M13 = 13;
        public static final int M20 = 2;
        public static final int M21 = 6;
        public static final int M22 = 10;
        public static final int M23 = 14;
        public static final int M30 = 3;
        public static final int M31 = 7;
        public static final int M32 = 11;
        public static final int M33 = 15;
    }

    public static Matrix4 identity() {
        return new Matrix4(new double[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
    }

    @Getter
    private final double[] values;

    private Matrix4(double[] values) {
        assert (values.length == 16);
        this.values = values;
    }

    public Matrix4(Matrix4 other) {
        this(Arrays.copyOf(other.values, other.values.length));
    }

    public void set(Vector3 translation, Quaternion rotation, Vector3 scale) {
        final double translationX = translation.getX();
        final double translationY = translation.getY();
        final double translationZ = translation.getZ();

        final double rotationX = rotation.getX();
        final double rotationY = rotation.getY();
        final double rotationZ = rotation.getZ();
        final double rotationW = rotation.getW();

        final double scaleX = scale.getX();
        final double scaleY = scale.getY();
        final double scaleZ = scale.getZ();

        final double xs = rotationX * 2., ys = rotationY * 2., zs = rotationZ * 2.;
        final double wx = rotationW * xs, wy = rotationW * ys, wz = rotationW * zs;
        final double xx = rotationX * xs, xy = rotationX * ys, xz = rotationX * zs;
        final double yy = rotationZ * xs, yz = rotationZ * ys, zz = rotationZ * zs;

        this.values[M00] = scaleX * (1.0f - (yy + zz));
        this.values[M01] = scaleY * (xy - wz);
        this.values[M02] = scaleZ * (xz + wy);
        this.values[M03] = translationX;

        this.values[M10] = scaleX * (xy + wz);
        this.values[M11] = scaleY * (1.0f - (xx + zz));
        this.values[M12] = scaleZ * (yz - wx);
        this.values[M13] = translationY;

        this.values[M20] = scaleX * (xz - wy);
        this.values[M21] = scaleY * (yz + wx);
        this.values[M22] = scaleZ * (1.0f - (xx + yy));
        this.values[M23] = translationZ;

        this.values[M30] = 0.f;
        this.values[M31] = 0.f;
        this.values[M32] = 0.f;
        this.values[M33] = 1.0f;
    }

    public void setTranslation(Vector3 vector) {
        this.values[M03] = vector.getX();
        this.values[M13] = vector.getY();
        this.values[M23] = vector.getZ();
    }

    public void setTranslation(float x, float y, float z) {
        this.values[M03] = x;
        this.values[M13] = y;
        this.values[M23] = z;
    }

    public Vector3 getTranslation() {
        return new Vector3(
                this.values[M03],
                this.values[M13],
                this.values[M23]
        );
    }

    public void setRotation(Quaternion rotation) {
        set(getTranslation(), rotation, getScale());
    }

    public Quaternion getRotation() {
        double xx = this.values[M00];
        double xy = this.values[M01];
        double xz = this.values[M02];
        double yx = this.values[M10];
        double yy = this.values[M11];
        double yz = this.values[M12];
        double zx = this.values[M20];
        double zy = this.values[M21];
        double zz = this.values[M22];

        final double lx = 1f / Vector3.length(xx, xy, xz);
        final double ly = 1f / Vector3.length(yx, yy, yz);
        final double lz = 1f / Vector3.length(zx, zy, zz);
        xx *= lx;
        xy *= lx;
        xz *= lx;
        yz *= ly;
        yy *= ly;
        yz *= ly;
        zx *= lz;
        zy *= lz;
        zz *= lz;
        // the trace is the sum of the diagonal elements; see
        // http://mathworld.wolfram.com/MatrixTrace.html
        final double t = xx + yy + zz;

        double x, y, z, w;

        // we protect the division by s by ensuring that s>=1
        if (t >= 0) { // |w| >= .5
            float s = (float) Math.sqrt(t + 1); // |s|>=1 ...
            w = 0.5f * s;
            s = 0.5f / s; // so this division isn't bad
            x = (zy - yz) * s;
            y = (xz - zx) * s;
            z = (yx - xy) * s;
        } else if ((xx > yy) && (xx > zz)) {
            float s = (float) Math.sqrt(1.0 + xx - yy - zz); // |s|>=1
            x = s * 0.5f; // |x| >= .5
            s = 0.5f / s;
            y = (yx + xy) * s;
            z = (xz + zx) * s;
            w = (zy - yz) * s;
        } else if (yy > zz) {
            float s = (float) Math.sqrt(1.0 + yy - xx - zz); // |s|>=1
            y = s * 0.5f; // |y| >= .5
            s = 0.5f / s;
            x = (yx + xy) * s;
            z = (zy + yz) * s;
            w = (xz - zx) * s;
        } else {
            float s = (float) Math.sqrt(1.0 + zz - xx - yy); // |s|>=1
            z = s * 0.5f; // |z| >= .5
            s = 0.5f / s;
            x = (xz + zx) * s;
            y = (zy + yz) * s;
            w = (yx - xy) * s;
        }

        return new Quaternion(x, y, z, w);
    }

    public Vector3 getScale() {
        return new Vector3(
                (MathUtil.isZero(this.values[M01]) && MathUtil.isZero(this.values[M02])) ?
                        Math.abs(this.values[M00]) :
                        Math.sqrt(this.values[M00] * this.values[M00] + this.values[M01] * this.values[M01] + this.values[M02] * this.values[M02]),
                (MathUtil.isZero(this.values[M10]) && MathUtil.isZero(this.values[M12])) ?
                        Math.abs(this.values[M11]) :
                        Math.sqrt(this.values[M10] * this.values[M10] + this.values[M11] * this.values[M11] + this.values[M12] * this.values[M12]),
                (MathUtil.isZero(this.values[M20]) && MathUtil.isZero(this.values[M21])) ?
                        Math.abs(this.values[M22]) :
                        Math.sqrt(this.values[M20] * this.values[M20] + this.values[M21] * this.values[M21] + values[M22] * this.values[M22])
        );
    }

    public void setScale(Vector3 scale) {
        set(getTranslation(), getRotation(), scale);
    }

    public void multiply(Matrix4 other) {
        final double tmpM00 = this.values[M00] * other.values[M00] + this.values[M01] * other.values[M10] + this.values[M02] * other.values[M20] + this.values[M03] * other.values[M30];
        final double tmpM01 = this.values[M00] * other.values[M01] + this.values[M01] * other.values[M11] + this.values[M02] * other.values[M21] + this.values[M03] * other.values[M31];
        final double tmpM02 = this.values[M00] * other.values[M02] + this.values[M01] * other.values[M12] + this.values[M02] * other.values[M22] + this.values[M03] * other.values[M32];
        final double tmpM03 = this.values[M00] * other.values[M03] + this.values[M01] * other.values[M13] + this.values[M02] * other.values[M23] + this.values[M03] * other.values[M33];
        final double tmpM10 = this.values[M10] * other.values[M00] + this.values[M11] * other.values[M10] + this.values[M12] * other.values[M20] + this.values[M13] * other.values[M30];
        final double tmpM11 = this.values[M10] * other.values[M01] + this.values[M11] * other.values[M11] + this.values[M12] * other.values[M21] + this.values[M13] * other.values[M31];
        final double tmpM12 = this.values[M10] * other.values[M02] + this.values[M11] * other.values[M12] + this.values[M12] * other.values[M22] + this.values[M13] * other.values[M32];
        final double tmpM13 = this.values[M10] * other.values[M03] + this.values[M11] * other.values[M13] + this.values[M12] * other.values[M23] + this.values[M13] * other.values[M33];
        final double tmpM20 = this.values[M20] * other.values[M00] + this.values[M21] * other.values[M10] + this.values[M22] * other.values[M20] + this.values[M23] * other.values[M30];
        final double tmpM21 = this.values[M20] * other.values[M01] + this.values[M21] * other.values[M11] + this.values[M22] * other.values[M21] + this.values[M23] * other.values[M31];
        final double tmpM22 = this.values[M20] * other.values[M02] + this.values[M21] * other.values[M12] + this.values[M22] * other.values[M22] + this.values[M23] * other.values[M32];
        final double tmpM23 = this.values[M20] * other.values[M03] + this.values[M21] * other.values[M13] + this.values[M22] * other.values[M23] + this.values[M23] * other.values[M33];
        final double tmpM30 = this.values[M30] * other.values[M00] + this.values[M31] * other.values[M10] + this.values[M32] * other.values[M20] + this.values[M33] * other.values[M30];
        final double tmpM31 = this.values[M30] * other.values[M01] + this.values[M31] * other.values[M11] + this.values[M32] * other.values[M21] + this.values[M33] * other.values[M31];
        final double tmpM32 = this.values[M30] * other.values[M02] + this.values[M31] * other.values[M12] + this.values[M32] * other.values[M22] + this.values[M33] * other.values[M32];
        final double tmpM33 = this.values[M30] * other.values[M03] + this.values[M31] * other.values[M13] + this.values[M32] * other.values[M23] + this.values[M33] * other.values[M33];
        this.values[M00] = tmpM00;
        this.values[M01] = tmpM01;
        this.values[M02] = tmpM02;
        this.values[M03] = tmpM03;
        this.values[M10] = tmpM10;
        this.values[M11] = tmpM11;
        this.values[M12] = tmpM12;
        this.values[M13] = tmpM13;
        this.values[M20] = tmpM20;
        this.values[M21] = tmpM21;
        this.values[M22] = tmpM22;
        this.values[M23] = tmpM23;
        this.values[M30] = tmpM30;
        this.values[M31] = tmpM31;
        this.values[M32] = tmpM32;
        this.values[M33] = tmpM33;
    }

    public void invert() {
        double l_det = values[M30] * values[M21] * values[M12] * values[M03] - values[M20] * values[M31] * values[M12] * values[M03] - values[M30] * values[M11]
                * values[M22] * values[M03] + values[M10] * values[M31] * values[M22] * values[M03] + values[M20] * values[M11] * values[M32] * values[M03] - values[M10]
                * values[M21] * values[M32] * values[M03] - values[M30] * values[M21] * values[M02] * values[M13] + values[M20] * values[M31] * values[M02] * values[M13]
                + values[M30] * values[M01] * values[M22] * values[M13] - values[M00] * values[M31] * values[M22] * values[M13] - values[M20] * values[M01] * values[M32]
                * values[M13] + values[M00] * values[M21] * values[M32] * values[M13] + values[M30] * values[M11] * values[M02] * values[M23] - values[M10] * values[M31]
                * values[M02] * values[M23] - values[M30] * values[M01] * values[M12] * values[M23] + values[M00] * values[M31] * values[M12] * values[M23] + values[M10]
                * values[M01] * values[M32] * values[M23] - values[M00] * values[M11] * values[M32] * values[M23] - values[M20] * values[M11] * values[M02] * values[M33]
                + values[M10] * values[M21] * values[M02] * values[M33] + values[M20] * values[M01] * values[M12] * values[M33] - values[M00] * values[M21] * values[M12]
                * values[M33] - values[M10] * values[M01] * values[M22] * values[M33] + values[M00] * values[M11] * values[M22] * values[M33];
        if (l_det == 0f) throw new RuntimeException("non-invertible matrix");
        double inv_det = 1.0f / l_det;
        double tmpM00 = values[M12] * values[M23] * values[M31] - values[M13] * values[M22] * values[M31] + values[M13] * values[M21] * values[M32] - values[M11]
                * values[M23] * values[M32] - values[M12] * values[M21] * values[M33] + values[M11] * values[M22] * values[M33];
        double tmpM01 = values[M03] * values[M22] * values[M31] - values[M02] * values[M23] * values[M31] - values[M03] * values[M21] * values[M32] + values[M01]
                * values[M23] * values[M32] + values[M02] * values[M21] * values[M33] - values[M01] * values[M22] * values[M33];
        double tmpM02 = values[M02] * values[M13] * values[M31] - values[M03] * values[M12] * values[M31] + values[M03] * values[M11] * values[M32] - values[M01]
                * values[M13] * values[M32] - values[M02] * values[M11] * values[M33] + values[M01] * values[M12] * values[M33];
        double tmpM03 = values[M03] * values[M12] * values[M21] - values[M02] * values[M13] * values[M21] - values[M03] * values[M11] * values[M22] + values[M01]
                * values[M13] * values[M22] + values[M02] * values[M11] * values[M23] - values[M01] * values[M12] * values[M23];
        double tmpM10 = values[M13] * values[M22] * values[M30] - values[M12] * values[M23] * values[M30] - values[M13] * values[M20] * values[M32] + values[M10]
                * values[M23] * values[M32] + values[M12] * values[M20] * values[M33] - values[M10] * values[M22] * values[M33];
        double tmpM11 = values[M02] * values[M23] * values[M30] - values[M03] * values[M22] * values[M30] + values[M03] * values[M20] * values[M32] - values[M00]
                * values[M23] * values[M32] - values[M02] * values[M20] * values[M33] + values[M00] * values[M22] * values[M33];
        double tmpM12 = values[M03] * values[M12] * values[M30] - values[M02] * values[M13] * values[M30] - values[M03] * values[M10] * values[M32] + values[M00]
                * values[M13] * values[M32] + values[M02] * values[M10] * values[M33] - values[M00] * values[M12] * values[M33];
        double tmpM13 = values[M02] * values[M13] * values[M20] - values[M03] * values[M12] * values[M20] + values[M03] * values[M10] * values[M22] - values[M00]
                * values[M13] * values[M22] - values[M02] * values[M10] * values[M23] + values[M00] * values[M12] * values[M23];
        double tmpM20 = values[M11] * values[M23] * values[M30] - values[M13] * values[M21] * values[M30] + values[M13] * values[M20] * values[M31] - values[M10]
                * values[M23] * values[M31] - values[M11] * values[M20] * values[M33] + values[M10] * values[M21] * values[M33];
        double tmpM21 = values[M03] * values[M21] * values[M30] - values[M01] * values[M23] * values[M30] - values[M03] * values[M20] * values[M31] + values[M00]
                * values[M23] * values[M31] + values[M01] * values[M20] * values[M33] - values[M00] * values[M21] * values[M33];
        double tmpM22 = values[M01] * values[M13] * values[M30] - values[M03] * values[M11] * values[M30] + values[M03] * values[M10] * values[M31] - values[M00]
                * values[M13] * values[M31] - values[M01] * values[M10] * values[M33] + values[M00] * values[M11] * values[M33];
        double tmpM23 = values[M03] * values[M11] * values[M20] - values[M01] * values[M13] * values[M20] - values[M03] * values[M10] * values[M21] + values[M00]
                * values[M13] * values[M21] + values[M01] * values[M10] * values[M23] - values[M00] * values[M11] * values[M23];
        double tmpM30 = values[M12] * values[M21] * values[M30] - values[M11] * values[M22] * values[M30] - values[M12] * values[M20] * values[M31] + values[M10]
                * values[M22] * values[M31] + values[M11] * values[M20] * values[M32] - values[M10] * values[M21] * values[M32];
        double tmpM31 = values[M01] * values[M22] * values[M30] - values[M02] * values[M21] * values[M30] + values[M02] * values[M20] * values[M31] - values[M00]
                * values[M22] * values[M31] - values[M01] * values[M20] * values[M32] + values[M00] * values[M21] * values[M32];
        double tmpM32 = values[M02] * values[M11] * values[M30] - values[M01] * values[M12] * values[M30] - values[M02] * values[M10] * values[M31] + values[M00]
                * values[M12] * values[M31] + values[M01] * values[M10] * values[M32] - values[M00] * values[M11] * values[M32];
        double tmpM33 = values[M01] * values[M12] * values[M20] - values[M02] * values[M11] * values[M20] + values[M02] * values[M10] * values[M21] - values[M00]
                * values[M12] * values[M21] - values[M01] * values[M10] * values[M22] + values[M00] * values[M11] * values[M22];
        values[M00] = tmpM00 * inv_det;
        values[M01] = tmpM01 * inv_det;
        values[M02] = tmpM02 * inv_det;
        values[M03] = tmpM03 * inv_det;
        values[M10] = tmpM10 * inv_det;
        values[M11] = tmpM11 * inv_det;
        values[M12] = tmpM12 * inv_det;
        values[M13] = tmpM13 * inv_det;
        values[M20] = tmpM20 * inv_det;
        values[M21] = tmpM21 * inv_det;
        values[M22] = tmpM22 * inv_det;
        values[M23] = tmpM23 * inv_det;
        values[M30] = tmpM30 * inv_det;
        values[M31] = tmpM31 * inv_det;
        values[M32] = tmpM32 * inv_det;
        values[M33] = tmpM33 * inv_det;
    }

    public Vector4 multiply(Vector4 vector) {
        return new Vector4(
                vector.x * values[M00] + vector.y * values[M01] + vector.z * values[M02] + vector.w * values[M03],
                vector.x * values[M10] + vector.y * values[M11] + vector.z * values[M12] + vector.w * values[M13],
                vector.x * values[M20] + vector.y * values[M21] + vector.z * values[M22] + vector.w * values[M23],
                vector.x * values[M30] + vector.y * values[M31] + vector.z * values[M32] + vector.w * values[M33]
        );
    }

    public Vector3 multiply(Vector3 vector) {
        return new Vector3(
                vector.x * values[M00] + vector.y * values[M01] + vector.z * values[M02] + values[M03],
                vector.x * values[M10] + vector.y * values[M11] + vector.z * values[M12] + values[M13],
                vector.x * values[M20] + vector.y * values[M21] + vector.z * values[M22] + values[M23]
        );
    }

    public Quaternion multiply(Quaternion quaternion) {
        Quaternion rotation = getRotation();
        rotation.multiply(quaternion);
        return rotation;
    }
}
