/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 */
package com.github.mikephil.charting.data;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineRadarDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.List;

public class LineDataSet
extends LineRadarDataSet<Entry>
implements ILineDataSet {
    private int mCircleColorHole;
    private List<Integer> mCircleColors;
    private float mCircleHoleRadius;
    private float mCircleRadius;
    private float mCubicIntensity;
    private DashPathEffect mDashPathEffect;
    private boolean mDrawCircleHole;
    private boolean mDrawCircles;
    private IFillFormatter mFillFormatter;
    private Mode mMode;

    @Override
    public int getCircleColor(int n) {
        return this.mCircleColors.get(n);
    }

    @Override
    public int getCircleColorCount() {
        return this.mCircleColors.size();
    }

    @Override
    public int getCircleHoleColor() {
        return this.mCircleColorHole;
    }

    @Override
    public float getCircleHoleRadius() {
        return this.mCircleHoleRadius;
    }

    @Override
    public float getCircleRadius() {
        return this.mCircleRadius;
    }

    @Override
    public float getCubicIntensity() {
        return this.mCubicIntensity;
    }

    @Override
    public DashPathEffect getDashPathEffect() {
        return this.mDashPathEffect;
    }

    @Override
    public IFillFormatter getFillFormatter() {
        return this.mFillFormatter;
    }

    @Override
    public Mode getMode() {
        return this.mMode;
    }

    @Override
    public boolean isDashedLineEnabled() {
        return this.mDashPathEffect != null;
    }

    @Override
    public boolean isDrawCircleHoleEnabled() {
        return this.mDrawCircleHole;
    }

    @Override
    public boolean isDrawCirclesEnabled() {
        return this.mDrawCircles;
    }

    @Deprecated
    @Override
    public boolean isDrawSteppedEnabled() {
        return this.mMode == Mode.STEPPED;
    }

    public static enum Mode {
        LINEAR,
        STEPPED,
        CUBIC_BEZIER,
        HORIZONTAL_BEZIER;

    }

}

