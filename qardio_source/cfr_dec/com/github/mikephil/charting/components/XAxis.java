/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.components;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.Utils;

public class XAxis
extends AxisBase {
    private boolean mAvoidFirstLastClipping = false;
    public int mLabelHeight = 1;
    public int mLabelRotatedHeight = 1;
    public int mLabelRotatedWidth = 1;
    protected float mLabelRotationAngle = 0.0f;
    public int mLabelWidth = 1;
    private XAxisPosition mPosition = XAxisPosition.TOP;

    public XAxis() {
        this.mYOffset = Utils.convertDpToPixel(4.0f);
    }

    public float getLabelRotationAngle() {
        return this.mLabelRotationAngle;
    }

    public XAxisPosition getPosition() {
        return this.mPosition;
    }

    public boolean isAvoidFirstLastClippingEnabled() {
        return this.mAvoidFirstLastClipping;
    }

    public void setPosition(XAxisPosition xAxisPosition) {
        this.mPosition = xAxisPosition;
    }

    public static enum XAxisPosition {
        TOP,
        BOTTOM,
        BOTH_SIDED,
        TOP_INSIDE,
        BOTTOM_INSIDE;

    }

}

