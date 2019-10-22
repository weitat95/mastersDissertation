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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BarChart
extends BarLineChartBase<BarData>
implements BarDataProvider {
    private boolean mDrawBarShadow = false;
    private boolean mDrawValueAboveBar = true;
    private boolean mFitBars = false;
    protected boolean mHighlightFullBarEnabled = false;

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BarChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void calcMinMax() {
        if (this.mFitBars) {
            XAxis xAxis = this.mXAxis;
            float f = ((BarData)this.mData).getXMin();
            float f2 = ((BarData)this.mData).getBarWidth() / 2.0f;
            float f3 = ((BarData)this.mData).getXMax();
            xAxis.calculate(f - f2, ((BarData)this.mData).getBarWidth() / 2.0f + f3);
        } else {
            this.mXAxis.calculate(((BarData)this.mData).getXMin(), ((BarData)this.mData).getXMax());
        }
        this.mAxisLeft.calculate(((BarData)this.mData).getYMin(YAxis.AxisDependency.LEFT), ((BarData)this.mData).getYMax(YAxis.AxisDependency.LEFT));
        this.mAxisRight.calculate(((BarData)this.mData).getYMin(YAxis.AxisDependency.RIGHT), ((BarData)this.mData).getYMax(YAxis.AxisDependency.RIGHT));
    }

    @Override
    public BarData getBarData() {
        return (BarData)this.mData;
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
    protected void init() {
        super.init();
        this.mRenderer = new BarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.setHighlighter(new BarHighlighter(this));
        this.getXAxis().setSpaceMin(0.5f);
        this.getXAxis().setSpaceMax(0.5f);
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

    public void setDrawBarShadow(boolean bl) {
        this.mDrawBarShadow = bl;
    }

    public void setDrawValueAboveBar(boolean bl) {
        this.mDrawValueAboveBar = bl;
    }

    public void setFitBars(boolean bl) {
        this.mFitBars = bl;
    }

    public void setHighlightFullBarEnabled(boolean bl) {
        this.mHighlightFullBarEnabled = bl;
    }
}

