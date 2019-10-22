/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

public class PieDataSet
extends DataSet<PieEntry>
implements IPieDataSet {
    private boolean mAutomaticallyDisableSliceSpacing;
    private float mShift;
    private float mSliceSpace;
    private int mValueLineColor;
    private float mValueLinePart1Length;
    private float mValueLinePart1OffsetPercentage;
    private float mValueLinePart2Length;
    private boolean mValueLineVariableLength;
    private float mValueLineWidth;
    private ValuePosition mXValuePosition;
    private ValuePosition mYValuePosition;

    @Override
    protected void calcMinMax(PieEntry pieEntry) {
        if (pieEntry == null) {
            return;
        }
        this.calcMinMaxY(pieEntry);
    }

    @Override
    public float getSelectionShift() {
        return this.mShift;
    }

    @Override
    public float getSliceSpace() {
        return this.mSliceSpace;
    }

    @Override
    public int getValueLineColor() {
        return this.mValueLineColor;
    }

    @Override
    public float getValueLinePart1Length() {
        return this.mValueLinePart1Length;
    }

    @Override
    public float getValueLinePart1OffsetPercentage() {
        return this.mValueLinePart1OffsetPercentage;
    }

    @Override
    public float getValueLinePart2Length() {
        return this.mValueLinePart2Length;
    }

    @Override
    public float getValueLineWidth() {
        return this.mValueLineWidth;
    }

    @Override
    public ValuePosition getXValuePosition() {
        return this.mXValuePosition;
    }

    @Override
    public ValuePosition getYValuePosition() {
        return this.mYValuePosition;
    }

    @Override
    public boolean isAutomaticallyDisableSliceSpacingEnabled() {
        return this.mAutomaticallyDisableSliceSpacing;
    }

    @Override
    public boolean isValueLineVariableLength() {
        return this.mValueLineVariableLength;
    }

    public static enum ValuePosition {
        INSIDE_SLICE,
        OUTSIDE_SLICE;

    }

}

