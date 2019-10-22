/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import java.util.ArrayList;
import java.util.List;

public abstract class PieRadarHighlighter<T extends PieRadarChartBase>
implements IHighlighter {
    protected T mChart;
    protected List<Highlight> mHighlightBuffer = new ArrayList<Highlight>();

    public PieRadarHighlighter(T t) {
        this.mChart = t;
    }

    protected abstract Highlight getClosestHighlight(int var1, float var2, float var3);

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Highlight getHighlight(float f, float f2) {
        int n;
        block5: {
            block4: {
                float f3;
                if (((PieRadarChartBase)this.mChart).distanceToCenter(f, f2) > ((PieRadarChartBase)this.mChart).getRadius()) break block4;
                float f4 = f3 = ((PieRadarChartBase)this.mChart).getAngleForPoint(f, f2);
                if (this.mChart instanceof PieChart) {
                    f4 = f3 / ((Chart)this.mChart).getAnimator().getPhaseY();
                }
                if ((n = ((PieRadarChartBase)this.mChart).getIndexForAngle(f4)) >= 0 && n < ((ChartData)((Chart)this.mChart).getData()).getMaxEntryCountSet().getEntryCount()) break block5;
            }
            return null;
        }
        return this.getClosestHighlight(n, f, f2);
    }
}

