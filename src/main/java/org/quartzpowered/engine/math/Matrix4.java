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

    public void setTranslation(Vector3 vector) {
        this.values[M03] = vector.getX();
        this.values[M13] = vector.getY();
        this.values[M23] = vector.getZ();
    }

    public void set(Vector3 translation, Quaternion rotation, Vector3 scale) {
        double q00 = 2.0f * rotation.getX() * rotation.getX();
        double q11 = 2.0f * rotation.getY() * rotation.getY();
        double q22 = 2.0f * rotation.getZ() * rotation.getZ();

        double q01 = 2.0f * rotation.getX() * rotation.getY();
        double q02 = 2.0f * rotation.getX() * rotation.getZ();
        double q03 = 2.0f * rotation.getX() * rotation.getW();

        double q12 = 2.0f * rotation.getY() * rotation.getZ();
        double q13 = 2.0f * rotation.getY() * rotation.getW();

        double q23 = 2.0f * rotation.getZ() * rotation.getW();

        double sx = scale.getX();
        double sy = scale.getY();
        double sz = scale.getZ();

        double tx = translation.getX();
        double ty = translation.getY();
        double tz = translation.getZ();

        values[M00] = sx * (1.0f - q11 - q22);
        values[M01] = sy * (q01 - q23);
        values[M02] = sz * (q02 + q13);
        values[M03] = tx;

        values[M10] = sx * (q01 + q23);
        values[M11] = sy * (1.0f - q22 - q00);
        values[M12] = sz * (q12 - q03);
        values[M13] = ty;

        values[M20] = sx * (q02 - q13);
        values[M21] = sy * (q12 + q03);
        values[M22] = sz * (1.0f - q11 - q00);
        values[M23] = tz;

        values[M30] = 0;
        values[M31] = 0;
        values[M32] = 0;
        values[M33] = 1;
    }

    public Vector3 getTranslation() {
        return new Vector3(
                this.values[M03],
                this.values[M13],
                this.values[M23]
        );
    }

    public Quaternion getRotation() {
        double onePlusTrace = 1. + values[M00] + values[M11] + values[M22];

        Quaternion rotation;
        if (onePlusTrace > MathUtil.DOUBLE_ROUNDING_ERROR) {
            double s = Math.sqrt(onePlusTrace) * 2.0f;
            rotation = new Quaternion(
                    (values[M21] - values[M12]) / s,
                    (values[M02] - values[M20]) / s,
                    (values[M10] - values[M01]) / s,
                    0.25f * s
            );
        } else {
            if ((values[M00] > values[M11]) & (values[M00] > values[M22])) {
                double s = Math.sqrt(1.0f + values[M00] - values[M11] - values[M22]) * 2.0f;
                rotation = new Quaternion(
                        0.25f * s,
                        (values[M01] + values[M10]) / s,
                        (values[M02] + values[M20]) / s,
                        (values[M12] - values[M21]) / s
                );
            } else if (values[M11] > values[M22]) {
                double s = Math.sqrt(1.0f + values[M11] - values[M00] - values[M22]) * 2.0f;
                rotation = new Quaternion(
                        (values[M01] + values[M10]) / s,
                        0.25f * s,
                        (values[M12] + values[M21]) / s,
                        (values[M02] - values[M20]) / s
                );
            } else {
                double s = Math.sqrt(1.0f + values[M22] - values[M00] - values[M11]) * 2.0f;
                rotation = new Quaternion(
                        (values[M02] + values[M20]) / s,
                        (values[M12] + values[M21]) / s,
                        0.25f * s,
                        (values[M01] - values[M10]) / s
                );
            }
        }
        rotation.normalize();

        return rotation;
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
