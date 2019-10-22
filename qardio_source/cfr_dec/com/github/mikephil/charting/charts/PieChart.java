/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.text.TextPaint
 *  android.util.AttributeSet
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.highlight.PieHighlighter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.renderer.PieChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class PieChart
extends PieRadarChartBase<PieData> {
    private float[] mAbsoluteAngles;
    private CharSequence mCenterText = "";
    private MPPointF mCenterTextOffset;
    private float mCenterTextRadiusPercent = 100.0f;
    private RectF mCircleBox = new RectF();
    private float[] mDrawAngles = new float[1];
    private boolean mDrawCenterText = true;
    private boolean mDrawEntryLabels = true;
    private boolean mDrawHole = true;
    private boolean mDrawRoundedSlices = false;
    private boolean mDrawSlicesUnderHole = false;
    private float mHoleRadiusPercent = 50.0f;
    protected float mMaxAngle = 360.0f;
    protected float mTransparentCircleRadiusPercent = 55.0f;
    private boolean mUsePercentValues = false;

    public PieChart(Context context) {
        super(context);
        this.mAbsoluteAngles = new float[1];
        this.mCenterTextOffset = MPPointF.getInstance(0.0f, 0.0f);
    }

    public PieChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAbsoluteAngles = new float[1];
        this.mCenterTextOffset = MPPointF.getInstance(0.0f, 0.0f);
    }

    public PieChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mAbsoluteAngles = new float[1];
        this.mCenterTextOffset = MPPointF.getInstance(0.0f, 0.0f);
    }

    private float calcAngle(float f, float f2) {
        return f / f2 * this.mMaxAngle;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calcAngles() {
        int n;
        int n2 = ((PieData)this.mData).getEntryCount();
        if (this.mDrawAngles.length != n2) {
            this.mDrawAngles = new float[n2];
        } else {
            for (n = 0; n < n2; ++n) {
                this.mDrawAngles[n] = 0.0f;
            }
        }
        if (this.mAbsoluteAngles.length != n2) {
            this.mAbsoluteAngles = new float[n2];
        } else {
            for (n = 0; n < n2; ++n) {
                this.mAbsoluteAngles[n] = 0.0f;
            }
        }
        float f = ((PieData)this.mData).getYValueSum();
        List list = ((PieData)this.mData).getDataSets();
        n2 = 0;
        n = 0;
        while (n < ((PieData)this.mData).getDataSetCount()) {
            IPieDataSet iPieDataSet = (IPieDataSet)list.get(n);
            for (int i = 0; i < iPieDataSet.getEntryCount(); ++n2, ++i) {
                this.mDrawAngles[n2] = this.calcAngle(Math.abs(((PieEntry)iPieDataSet.getEntryForIndex(i)).getY()), f);
                this.mAbsoluteAngles[n2] = n2 == 0 ? this.mDrawAngles[n2] : this.mAbsoluteAngles[n2 - 1] + this.mDrawAngles[n2];
            }
            ++n;
        }
        return;
    }

    @Override
    protected void calcMinMax() {
        this.calcAngles();
    }

    @Override
    public void calculateOffsets() {
        super.calculateOffsets();
        if (this.mData == null) {
            return;
        }
        float f = this.getDiameter() / 2.0f;
        MPPointF mPPointF = this.getCenterOffsets();
        float f2 = ((PieData)this.mData).getDataSet().getSelectionShift();
        this.mCircleBox.set(mPPointF.x - f + f2, mPPointF.y - f + f2, mPPointF.x + f - f2, mPPointF.y + f - f2);
        MPPointF.recycleInstance(mPPointF);
    }

    public float[] getAbsoluteAngles() {
        return this.mAbsoluteAngles;
    }

    public MPPointF getCenterCircleBox() {
        return MPPointF.getInstance(this.mCircleBox.centerX(), this.mCircleBox.centerY());
    }

    public CharSequence getCenterText() {
        return this.mCenterText;
    }

    public MPPointF getCenterTextOffset() {
        return MPPointF.getInstance(this.mCenterTextOffset.x, this.mCenterTextOffset.y);
    }

    public float getCenterTextRadiusPercent() {
        return this.mCenterTextRadiusPercent;
    }

    public RectF getCircleBox() {
        return this.mCircleBox;
    }

    public float[] getDrawAngles() {
        return this.mDrawAngles;
    }

    public float getHoleRadius() {
        return this.mHoleRadiusPercent;
    }

    @Override
    public int getIndexForAngle(float f) {
        f = Utils.getNormalizedAngle(f - this.getRotationAngle());
        for (int i = 0; i < this.mAbsoluteAngles.length; ++i) {
            if (!(this.mAbsoluteAngles[i] > f)) continue;
            return i;
        }
        return -1;
    }

    @Override
    protected float[] getMarkerPosition(Highlight highlight) {
        MPPointF mPPointF = this.getCenterCircleBox();
        float f = this.getRadius();
        float f2 = f / 10.0f * 3.6f;
        if (this.isDrawHoleEnabled()) {
            f2 = (f - f / 100.0f * this.getHoleRadius()) / 2.0f;
        }
        f -= f2;
        float f3 = this.getRotationAngle();
        int n = (int)highlight.getX();
        float f4 = this.mDrawAngles[n] / 2.0f;
        f2 = (float)((double)f * Math.cos(Math.toRadians((this.mAbsoluteAngles[n] + f3 - f4) * this.mAnimator.getPhaseY())) + (double)mPPointF.x);
        f = (float)((double)f * Math.sin(Math.toRadians((this.mAbsoluteAngles[n] + f3 - f4) * this.mAnimator.getPhaseY())) + (double)mPPointF.y);
        MPPointF.recycleInstance(mPPointF);
        return new float[]{f2, f};
    }

    public float getMaxAngle() {
        return this.mMaxAngle;
    }

    @Override
    public float getRadius() {
        if (this.mCircleBox == null) {
            return 0.0f;
        }
        return Math.min(this.mCircleBox.width() / 2.0f, this.mCircleBox.height() / 2.0f);
    }

    @Override
    protected float getRequiredBaseOffset() {
        return 0.0f;
    }

    @Override
    protected float getRequiredLegendOffset() {
        return this.mLegendRenderer.getLabelPaint().getTextSize() * 2.0f;
    }

    public float getTransparentCircleRadius() {
        return this.mTransparentCircleRadiusPercent;
    }

    @Deprecated
    @Override
    public XAxis getXAxis() {
        throw new RuntimeException("PieChart has no XAxis");
    }

    @Override
    protected void init() {
        super.init();
        this.mRenderer = new PieChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.mXAxis = null;
        this.mHighlighter = new PieHighlighter(this);
    }

    public boolean isDrawCenterTextEnabled() {
        return this.mDrawCenterText;
    }

    public boolean isDrawEntryLabelsEnabled() {
        return this.mDrawEntryLabels;
    }

    public boolean isDrawHoleEnabled() {
        return this.mDrawHole;
    }

    public boolean isDrawSlicesUnderHoleEnabled() {
        return this.mDrawSlicesUnderHole;
    }

    public boolean isUsePercentValuesEnabled() {
        return this.mUsePercentValues;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean needsHighlight(int n) {
        if (this.valuesToHighlight()) {
            for (int i = 0; i < this.mIndicesToHighlight.length; ++i) {
                if ((int)this.mIndicesToHighlight[i].getX() != n) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (this.mRenderer != null && this.mRenderer instanceof PieChartRenderer) {
            ((PieChartRenderer)this.mRenderer).releaseBitmap();
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mData == null) {
            return;
        }
        this.mRenderer.drawData(canvas);
        if (this.valuesToHighlight()) {
            this.mRenderer.drawHighlighted(canvas, this.mIndicesToHighlight);
        }
        this.mRenderer.drawExtras(canvas);
        this.mRenderer.drawValues(canvas);
        this.mLegendRenderer.renderLegend(canvas);
        this.drawDescription(canvas);
        this.drawMarkers(canvas);
    }

    public void setCenterText(CharSequence charSequence) {
        if (charSequence == null) {
            this.mCenterText = "";
            return;
        }
        this.mCenterText = charSequence;
    }

    public void setCenterTextColor(int n) {
        ((PieChartRenderer)this.mRenderer).getPaintCenterText().setColor(n);
    }

    public void setCenterTextOffset(float f, float f2) {
        this.mCenterTextOffset.x = Utils.convertDpToPixel(f);
        this.mCenterTextOffset.y = Utils.convertDpToPixel(f2);
    }

    public void setCenterTextRadiusPercent(float f) {
        this.mCenterTextRadiusPercent = f;
    }

    public void setCenterTextSize(float f) {
        ((PieChartRenderer)this.mRenderer).getPaintCenterText().setTextSize(Utils.convertDpToPixel(f));
    }

    public void setCenterTextSizePixels(float f) {
        ((PieChartRenderer)this.mRenderer).getPaintCenterText().setTextSize(f);
    }

    public void setCenterTextTypeface(Typeface typeface) {
        ((PieChartRenderer)this.mRenderer).getPaintCenterText().setTypeface(typeface);
    }

    public void setDrawCenterText(boolean bl) {
        this.mDrawCenterText = bl;
    }

    public void setDrawEntryLabels(boolean bl) {
        this.mDrawEntryLabels = bl;
    }

    public void setDrawHoleEnabled(boolean bl) {
        this.mDrawHole = bl;
    }

    @Deprecated
    public void setDrawSliceText(boolean bl) {
        this.mDrawEntryLabels = bl;
    }

    public void setDrawSlicesUnderHole(boolean bl) {
        this.mDrawSlicesUnderHole = bl;
    }

    public void setEntryLabelColor(int n) {
        ((PieChartRenderer)this.mRenderer).getPaintEntryLabels().setColor(n);
    }

    public void setEntryLabelTextSize(float f) {
        ((PieChartRenderer)this.mRenderer).getPaintEntryLabels().setTextSize(Utils.convertDpToPixel(f));
    }

    public void setEntryLabelTypeface(Typeface typeface) {
        ((PieChartRenderer)this.mRenderer).getPaintEntryLabels().setTypeface(typeface);
    }

    public void setHoleColor(int n) {
        ((PieChartRenderer)this.mRenderer).getPaintHole().setColor(n);
    }

    public void setHoleRadius(float f) {
        this.mHoleRadiusPercent = f;
    }

    public void setMaxAngle(float f) {
        float f2 = f;
        if (f > 360.0f) {
            f2 = 360.0f;
        }
        f = f2;
        if (f2 < 90.0f) {
            f = 90.0f;
        }
        this.mMaxAngle = f;
    }

    public void setTransparentCircleAlpha(int n) {
        ((PieChartRenderer)this.mRenderer).getPaintTransparentCircle().setAlpha(n);
    }

    public void setTransparentCircleColor(int n) {
        Paint paint = ((PieChartRenderer)this.mRenderer).getPaintTransparentCircle();
        int n2 = paint.getAlpha();
        paint.setColor(n);
        paint.setAlpha(n2);
    }

    public void setTransparentCircleRadius(float f) {
        this.mTransparentCircleRadiusPercent = f;
    }

    public void setUsePercentValues(boolean bl) {
        this.mUsePercentValues = bl;
    }
}

