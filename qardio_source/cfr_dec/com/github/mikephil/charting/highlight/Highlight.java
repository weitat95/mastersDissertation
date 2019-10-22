/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;

public class Highlight {
    private YAxis.AxisDependency axis;
    private int mDataIndex = -1;
    private int mDataSetIndex;
    private float mDrawX;
    private float mDrawY;
    private int mStackIndex = -1;
    private float mX = Float.NaN;
    private float mXPx;
    private float mY = Float.NaN;
    private float mYPx;

    public Highlight(float f, float f2, float f3, float f4, int n, int n2, YAxis.AxisDependency axisDependency) {
        this(f, f2, f3, f4, n, axisDependency);
        this.mStackIndex = n2;
    }

    public Highlight(float f, float f2, float f3, float f4, int n, YAxis.AxisDependency axisDependency) {
        this.mX = f;
        this.mY = f2;
        this.mXPx = f3;
        this.mYPx = f4;
        this.mDataSetIndex = n;
        this.axis = axisDependency;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equalTo(Highlight highlight) {
        return highlight != null && this.mDataSetIndex == highlight.mDataSetIndex && this.mX == highlight.mX && this.mStackIndex == highlight.mStackIndex && this.mDataIndex == highlight.mDataIndex;
    }

    public YAxis.AxisDependency getAxis() {
        return this.axis;
    }

    public int getDataIndex() {
        return this.mDataIndex;
    }

    public int getDataSetIndex() {
        return this.mDataSetIndex;
    }

    public float getDrawX() {
        return this.mDrawX;
    }

    public float getDrawY() {
        return this.mDrawY;
    }

    public int getStackIndex() {
        return this.mStackIndex;
    }

    public float getX() {
        return this.mX;
    }

    public float getXPx() {
        return this.mXPx;
    }

    public float getY() {
        return this.mY;
    }

    public float getYPx() {
        return this.mYPx;
    }

    public void setDataIndex(int n) {
        this.mDataIndex = n;
    }

    public void setDraw(float f, float f2) {
        this.mDrawX = f;
        this.mDrawY = f2;
    }

    public String toString() {
        return "Highlight, x: " + this.mX + ", y: " + this.mY + ", dataSetIndex: " + this.mDataSetIndex + ", stackIndex (only stacked barentry): " + this.mStackIndex;
    }
}

