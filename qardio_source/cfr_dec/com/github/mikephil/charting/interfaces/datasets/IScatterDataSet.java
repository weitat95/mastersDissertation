/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.interfaces.datasets;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;

public interface IScatterDataSet
extends ILineScatterCandleRadarDataSet<Entry> {
    public float getScatterShapeSize();

    public IShapeRenderer getShapeRenderer();
}

