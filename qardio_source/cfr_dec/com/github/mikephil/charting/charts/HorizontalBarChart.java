/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.HorizontalBarHighlighter;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.renderer.XAxisRendererHorizontalBarChart;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.renderer.YAxisRendererHorizontalBarChart;
import com.github.mikephil.charting.utils.HorizontalViewPortHandler;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.TransformerHorizontalBarChart;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class HorizontalBarChart
extends BarChart {
    protected float[] mGetPositionBuffer;
    private RectF mOffsetsBuffer = new RectF();

    public HorizontalBarChart(Context context) {
        super(context);
        this.mGetPositionBuffer = new float[2];
    }

    public HorizontalBarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mGetPositionBuffer = new float[2];
    }

    public HorizontalBarChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mGetPositionBuffer = new float[2];
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void calculateOffsets() {
        this.calculateLegendOffsets(this.mOffsetsBuffer);
        float f = 0.0f + this.mOffsetsBuffer.left;
        float f2 = 0.0f + this.mOffsetsBuffer.top;
        float f3 = 0.0f + this.mOffsetsBuffer.right;
        float f4 = 0.0f + this.mOffsetsBuffer.bottom;
        float f5 = f2;
        if (this.mAxisLeft.needsOffset()) {
            f5 = f2 + this.mAxisLeft.getRequiredHeightSpace(this.mAxisRendererLeft.getPaintAxisLabels());
        }
        float f6 = f4;
        if (this.mAxisRight.needsOffset()) {
            f6 = f4 + this.mAxisRight.getRequiredHeightSpace(this.mAxisRendererRight.getPaintAxisLabels());
        }
        float f7 = this.mXAxis.mLabelRotatedWidth;
        f4 = f;
        f2 = f3;
        if (this.mXAxis.isEnabled()) {
            if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
                f4 = f + f7;
                f2 = f3;
            } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
                f2 = f3 + f7;
                f4 = f;
            } else {
                f4 = f;
                f2 = f3;
                if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
                    f4 = f + f7;
                    f2 = f3 + f7;
                }
            }
        }
        f3 = Utils.convertDpToPixel(this.mMinOffset);
        this.mViewPortHandler.restrainViewPort(Math.max(f3, f4 += this.getExtraLeftOffset()), Math.max(f3, f5 += this.getExtraTopOffset()), Math.max(f3, f2 += this.getExtraRightOffset()), Math.max(f3, f6 += this.getExtraBottomOffset()));
        if (this.mLogEnabled) {
            Log.i((String)"MPAndroidChart", (String)("offsetLeft: " + f4 + ", offsetTop: " + f5 + ", offsetRight: " + f2 + ", offsetBottom: " + f6));
            Log.i((String)"MPAndroidChart", (String)("Content: " + this.mViewPortHandler.getContentRect().toString()));
        }
        this.prepareOffsetMatrix();
        this.prepareValuePxMatrix();
    }

    @Override
    public float getHighestVisibleX() {
        this.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.posForGetHighestVisibleX);
        return (float)Math.min((double)this.mXAxis.mAxisMaximum, this.posForGetHighestVisibleX.y);
    }

    @Override
    public Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData == null) {
            if (this.mLogEnabled) {
                Log.e((String)"MPAndroidChart", (String)"Can't select by touch. No data set.");
            }
            return null;
        }
        return this.getHighlighter().getHighlight(f2, f);
    }

    @Override
    public float getLowestVisibleX() {
        this.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.posForGetLowestVisibleX);
        return (float)Math.max((double)this.mXAxis.mAxisMinimum, this.posForGetLowestVisibleX.y);
    }

    @Override
    protected float[] getMarkerPosition(Highlight highlight) {
        return new float[]{highlight.getDrawY(), highlight.getDrawX()};
    }

    @Override
    protected void init() {
        this.mViewPortHandler = new HorizontalViewPortHandler();
        super.init();
        this.mLeftAxisTransformer = new TransformerHorizontalBarChart(this.mViewPortHandler);
        this.mRightAxisTransformer = new TransformerHorizontalBarChart(this.mViewPortHandler);
        this.mRenderer = new HorizontalBarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.setHighlighter(new HorizontalBarHighlighter(this));
        this.mAxisRendererLeft = new YAxisRendererHorizontalBarChart(this.mViewPortHandler, this.mAxisLeft, this.mLeftAxisTransformer);
        this.mAxisRendererRight = new YAxisRendererHorizontalBarChart(this.mViewPortHandler, this.mAxisRight, this.mRightAxisTransformer);
        this.mXAxisRenderer = new XAxisRendererHorizontalBarChart(this.mViewPortHandler, this.mXAxis, this.mLeftAxisTransformer, this);
    }

    @Override
    protected void prepareValuePxMatrix() {
        this.mRightAxisTransformer.prepareMatrixValuePx(this.mAxisRight.mAxisMinimum, this.mAxisRight.mAxisRange, this.mXAxis.mAxisRange, this.mXAxis.mAxisMinimum);
        this.mLeftAxisTransformer.prepareMatrixValuePx(this.mAxisLeft.mAxisMinimum, this.mAxisLeft.mAxisRange, this.mXAxis.mAxisRange, this.mXAxis.mAxisMinimum);
    }

    @Override
    public void setVisibleXRange(float f, float f2) {
        f = this.mXAxis.mAxisRange / f;
        f2 = this.mXAxis.mAxisRange / f2;
        this.mViewPortHandler.setMinMaxScaleY(f, f2);
    }

    @Override
    public void setVisibleXRangeMaximum(float f) {
        f = this.mXAxis.mAxisRange / f;
        this.mViewPortHandler.setMinimumScaleY(f);
    }

    @Override
    public void setVisibleXRangeMinimum(float f) {
        f = this.mXAxis.mAxisRange / f;
        this.mViewPortHandler.setMaximumScaleY(f);
    }

    @Override
    public void setVisibleYRange(float f, float f2, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        f2 = this.getAxisRange(axisDependency) / f2;
        this.mViewPortHandler.setMinMaxScaleX(f, f2);
    }

    @Override
    public void setVisibleYRangeMaximum(float f, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        this.mViewPortHandler.setMinimumScaleX(f);
    }

    @Override
    public void setVisibleYRangeMinimum(float f, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        this.mViewPortHandler.setMaximumScaleX(f);
    }
}

