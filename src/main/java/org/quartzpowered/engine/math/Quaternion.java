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

import lombok.Data;

@Data
public class Quaternion {
    private double x, y, z, w;

    public Quaternion() {

    }

    public Quaternion(double x, double y, double z, double w) {
        set(x, y, z, w);
    }

    private void set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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

    public int getGimbalPole() {
        final float t = (float) (y * x + z * w);
        return t > 0.499f ? 1 : (t < -0.499f ? -1 : 0);
    }

    public float getPitch() {
        final int pole = getGimbalPole();
        final float radians = pole == 0 ? (float) Math.asin(MathUtil.clamp((float) (2f * (w * x - z * y)), -1f, 1f)) : ((float) pole * MathUtil.FLOAT_PI * 0.5f);
        return radians * MathUtil.radiansToDegrees;
    }

    public float getYaw() {
        final int pole = getGimbalPole();
        final float radians = pole == 0 ? (float) Math.atan2(2f * (y * w + x * z), 1f - 2f * (y * y + x * x)) : 0f;
        return radians * MathUtil.radiansToDegrees;
    }

    public float getRoll() {
        final int pole = getGimbalPole();
        final float radians = pole == 0 ? (float) Math.atan2(2f * (w * z + y * x), 1f - 2f * (x * x + z * z)) : (float) pole * 2f * (float) Math.atan2(y, w);
        return radians * MathUtil.radiansToDegrees;
    }
}
