/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.PieRadarHighlighter;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

public class RadarHighlighter
extends PieRadarHighlighter<RadarChart> {
    public RadarHighlighter(RadarChart radarChart) {
        super(radarChart);
    }

    @Override
    protected Highlight getClosestHighlight(int n, float f, float f2) {
        List<Highlight> list = this.getHighlightsAtIndex(n);
        float f3 = ((RadarChart)this.mChart).distanceToCenter(f, f2) / ((RadarChart)this.mChart).getFactor();
        Highlight highlight = null;
        f = Float.MAX_VALUE;
        for (n = 0; n < list.size(); ++n) {
            Highlight highlight2 = list.get(n);
            float f4 = Math.abs(highlight2.getY() - f3);
            f2 = f;
            if (f4 < f) {
                highlight = highlight2;
                f2 = f4;
            }
            f = f2;
        }
        return highlight;
    }

    protected List<Highlight> getHighlightsAtIndex(int n) {
        this.mHighlightBuffer.clear();
        float f = ((RadarChart)this.mChart).getAnimator().getPhaseX();
        float f2 = ((RadarChart)this.mChart).getAnimator().getPhaseY();
        float f3 = ((RadarChart)this.mChart).getSliceAngle();
        float f4 = ((RadarChart)this.mChart).getFactor();
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        for (int i = 0; i < ((RadarData)((RadarChart)this.mChart).getData()).getDataSetCount(); ++i) {
            Object t = ((RadarData)((RadarChart)this.mChart).getData()).getDataSetByIndex(i);
            Object t2 = t.getEntryForIndex(n);
            float f5 = ((BaseEntry)t2).getY();
            float f6 = ((RadarChart)this.mChart).getYChartMin();
            MPPointF mPPointF2 = ((RadarChart)this.mChart).getCenterOffsets();
            float f7 = n;
            Utils.getPosition(mPPointF2, (f5 - f6) * f4 * f2, ((RadarChart)this.mChart).getRotationAngle() + f7 * f3 * f, mPPointF);
            this.mHighlightBuffer.add(new Highlight(n, ((BaseEntry)t2).getY(), mPPointF.x, mPPointF.y, i, t.getAxisDependency()));
        }
        return this.mHighlightBuffer;
    }
}

