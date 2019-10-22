/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.Log
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.CombinedHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CombinedDataProvider;
import com.github.mikephil.charting.renderer.CombinedChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CombinedChart
extends BarLineChartBase<CombinedData>
implements CombinedDataProvider {
    private boolean mDrawBarShadow = false;
    protected DrawOrder[] mDrawOrder;
    private boolean mDrawValueAboveBar = true;
    protected boolean mHighlightFullBarEnabled = false;

    public CombinedChart(Context context) {
        super(context);
    }

    public CombinedChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CombinedChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    public BarData getBarData() {
        if (this.mData == null) {
            return null;
        }
        return ((CombinedData)this.mData).getBarData();
    }

    @Override
    public BubbleData getBubbleData() {
        if (this.mData == null) {
            return null;
        }
        return ((CombinedData)this.mData).getBubbleData();
    }

    @Override
    public CandleData getCandleData() {
        if (this.mData == null) {
            return null;
        }
        return ((CombinedData)this.mData).getCandleData();
    }

    @Override
    public CombinedData getCombinedData() {
        return (CombinedData)this.mData;
    }

    public DrawOrder[] getDrawOrder() {
        return this.mDrawOrder;
    }

    @Override
    public Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData == null) {
            Log.e((String)"MPAndroidChart", (String)"Can't select by touch. No data set.");
            return null;
        }
        Highlight highlight = this.getHighlighter().getHighlight(f, f2);
        if (highlight == null || !this.isHighlightFullBarEnabled()) {
            return highlight;
        }
        return new Highlight(highlight.getX(), highlight.getY(), highlight.getXPx(), highlight.getYPx(), highlight.getDataSetIndex(), -1, highlight.getAxis());
    }

    @Override
    public LineData getLineData() {
        if (this.mData == null) {
            return null;
        }
        return ((CombinedData)this.mData).getLineData();
    }

    @Override
    public ScatterData getScatterData() {
        if (this.mData == null) {
            return null;
        }
        return ((CombinedData)this.mData).getScatterData();
    }

    @Override
    protected void init() {
        super.init();
        this.mDrawOrder = new DrawOrder[]{DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.LINE, DrawOrder.CANDLE, DrawOrder.SCATTER};
        this.setHighlighter(new CombinedHighlighter(this, this));
        this.setHighlightFullBarEnabled(true);
        this.mRenderer = new CombinedChartRenderer(this, this.mAnimator, this.mViewPortHandler);
    }

    @Override
    public boolean isDrawBarShadowEnabled() {
        return this.mDrawBarShadow;
    }

    @Override
    public boolean isDrawValueAboveBarEnabled() {
        return this.mDrawValueAboveBar;
    }

    @Override
    public boolean isHighlightFullBarEnabled() {
        return this.mHighlightFullBarEnabled;
    }

    @Override
    public void setData(CombinedData combinedData) {
        super.setData(combinedData);
        this.setHighlighter(new CombinedHighlighter(this, this));
        ((CombinedChartRenderer)this.mRenderer).createRenderers();
        this.mRenderer.initBuffers();
    }

    public void setDrawBarShadow(boolean bl) {
        this.mDrawBarShadow = bl;
    }

    public void setDrawOrder(DrawOrder[] arrdrawOrder) {
        if (arrdrawOrder == null || arrdrawOrder.length <= 0) {
            return;
        }
        this.mDrawOrder = arrdrawOrder;
    }

    public void setDrawValueAboveBar(boolean bl) {
        this.mDrawValueAboveBar = bl;
    }

    public void setHighlightFullBarEnabled(boolean bl) {
        this.mHighlightFullBarEnabled = bl;
    }

    public static enum DrawOrder {
        BAR,
        BUBBLE,
        LINE,
        CANDLE,
        SCATTER;

    }

}

