/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;

public class BarHighlighter
extends ChartHighlighter<BarDataProvider> {
    public BarHighlighter(BarDataProvider barDataProvider) {
        super(barDataProvider);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected int getClosestStackIndex(Range[] arrrange, float f) {
        if (arrrange == null) return 0;
        if (arrrange.length == 0) {
            return 0;
        }
        int n = 0;
        int n2 = arrrange.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = n++;
            if (arrrange[i].contains(f)) return n3;
        }
        n = Math.max(arrrange.length - 1, 0);
        if (!(f > arrrange[n].to)) return 0;
        return n;
    }

    @Override
    protected BarLineScatterCandleBubbleData getData() {
        return ((BarDataProvider)this.mChart).getBarData();
    }

    @Override
    protected float getDistance(float f, float f2, float f3, float f4) {
        return Math.abs(f - f3);
    }

    @Override
    public Highlight getHighlight(float f, float f2) {
        Highlight highlight = super.getHighlight(f, f2);
        if (highlight == null) {
            return null;
        }
        MPPointD mPPointD = this.getValsForTouch(f, f2);
        IBarDataSet iBarDataSet = (IBarDataSet)((BarDataProvider)this.mChart).getBarData().getDataSetByIndex(highlight.getDataSetIndex());
        if (iBarDataSet.isStacked()) {
            return this.getStackedHighlight(highlight, iBarDataSet, (float)mPPointD.x, (float)mPPointD.y);
        }
        MPPointD.recycleInstance(mPPointD);
        return highlight;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Highlight getStackedHighlight(Highlight arrrange, IBarDataSet object, float f, float f2) {
        void var3_4;
        void var4_5;
        MPPointD mPPointD;
        BarEntry barEntry = (BarEntry)mPPointD.getEntryForXValue((float)var3_4, (float)var4_5);
        if (barEntry == null) {
            return null;
        }
        Range[] arrrange2 = arrrange;
        if (barEntry.getYVals() == null) return arrrange2;
        arrrange2 = barEntry.getRanges();
        if (arrrange2.length <= 0) return null;
        int n = this.getClosestStackIndex(arrrange2, (float)var4_5);
        mPPointD = ((BarDataProvider)this.mChart).getTransformer(mPPointD.getAxisDependency()).getPixelForValues(arrrange.getX(), arrrange2[n].to);
        Highlight highlight = new Highlight(barEntry.getX(), barEntry.getY(), (float)mPPointD.x, (float)mPPointD.y, arrrange.getDataSetIndex(), n, arrrange.getAxis());
        MPPointD.recycleInstance(mPPointD);
        return highlight;
    }
}

