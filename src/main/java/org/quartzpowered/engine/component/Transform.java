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
package org.quartzpowered.engine.component;

import lombok.Getter;
import org.quartzpowered.engine.math.Matrix4;
import org.quartzpowered.engine.math.Quaternion;
import org.quartzpowered.engine.math.Vector3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Transform extends Component {
    private final List<Transform> children = new ArrayList<>();

    @Getter
    private Transform parent;

    private final Matrix4 matrix = Matrix4.identity();

    public Vector3 getLocalPosition() {
        return matrix.getTranslation();
    }

    public void setLocalPosition(Vector3 position) {
        matrix.setTranslation(position);
    }

    public Quaternion getLocalRotation() {
        return matrix.getRotation();
    }

    public void setLocalRotation(Quaternion rotation) {
        matrix.setRotation(rotation);
    }

    public Vector3 getLocalScale() {
        return matrix.getScale();
    }

    public void setLocalScale(Vector3 scale) {
        matrix.setScale(scale);
    }

    public Vector3 getPosition() {
        return getLocalToWorldMatrix().getTranslation();
    }

    public void setPosition(Vector3 position) {
        setLocalPosition(getWorldToLocalMatrix().multiply(position));
    }

    public Quaternion getRotation() {
        return getLocalToWorldMatrix().getRotation();
    }

    public void setRotation(Quaternion rotation) {
        setLocalRotation(getWorldToLocalMatrix().multiply(rotation));
    }

    public Vector3 getScale() {
        return getLocalToWorldMatrix().getScale();
    }

    public Matrix4 getLocalToWorldMatrix() {
        if (parent == null) {
            return new Matrix4(matrix);
        } else {
            Matrix4 localToWorldMatrix = parent.getLocalToWorldMatrix();
            localToWorldMatrix.multiply(matrix);
            return localToWorldMatrix;
        }
    }

    public Matrix4 getWorldToLocalMatrix() {
        Matrix4 localToWorldMatrix = getLocalToWorldMatrix();
        localToWorldMatrix.invert();
        return localToWorldMatrix;
    }

    public Collection<Transform> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    public void setParent(Transform parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }

        this.parent = parent;
        this.parent.children.add(this);
    }

}
