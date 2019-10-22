/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.interfaces.datasets;

import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;

public interface ILineRadarDataSet<T extends Entry>
extends ILineScatterCandleRadarDataSet<T> {
    public int getFillAlpha();

    public int getFillColor();

    public Drawable getFillDrawable();

    public float getLineWidth();

    public boolean isDrawFilledEnabled();
}

