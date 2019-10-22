/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.interfaces.datasets;

import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet;

public interface IRadarDataSet
extends ILineRadarDataSet<RadarEntry> {
    public int getHighlightCircleFillColor();

    public float getHighlightCircleInnerRadius();

    public float getHighlightCircleOuterRadius();

    public int getHighlightCircleStrokeAlpha();

    public int getHighlightCircleStrokeColor();

    public float getHighlightCircleStrokeWidth();

    public boolean isDrawHighlightCircleEnabled();
}

