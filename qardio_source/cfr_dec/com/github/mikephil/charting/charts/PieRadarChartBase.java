/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.PieRadarChartTouchListener;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class PieRadarChartBase<T extends ChartData<? extends IDataSet<? extends Entry>>>
extends Chart<T> {
    protected float mMinOffset = 0.0f;
    private float mRawRotationAngle = 270.0f;
    protected boolean mRotateEnabled = true;
    private float mRotationAngle = 270.0f;

    public PieRadarChartBase(Context context) {
        super(context);
    }

    public PieRadarChartBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PieRadarChartBase(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    protected void calcMinMax() {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void calculateOffsets() {
        Object object;
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = f5;
        float f10 = f;
        float f11 = f3;
        float f12 = f7;
        if (this.mLegend != null) {
            f9 = f5;
            f10 = f;
            f11 = f3;
            f12 = f7;
            if (this.mLegend.isEnabled()) {
                f9 = f5;
                f10 = f;
                f11 = f3;
                f12 = f7;
                if (!this.mLegend.isDrawInsideEnabled()) {
                    f9 = Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent());
                    block0 : switch (this.mLegend.getOrientation()) {
                        default: {
                            f12 = f8;
                            f10 = f4;
                            f9 = f2;
                            f11 = f6;
                            break;
                        }
                        case VERTICAL: {
                            f12 = 0.0f;
                            f11 = 0.0f;
                            if (this.mLegend.getHorizontalAlignment() == Legend.LegendHorizontalAlignment.LEFT || this.mLegend.getHorizontalAlignment() == Legend.LegendHorizontalAlignment.RIGHT) {
                                if (this.mLegend.getVerticalAlignment() == Legend.LegendVerticalAlignment.CENTER) {
                                    f11 = f9 + Utils.convertDpToPixel(13.0f);
                                } else {
                                    f10 = this.mLegend.mNeededHeight;
                                    f = this.mLegend.mTextHeightMax;
                                    object = this.getCenter();
                                    f11 = this.mLegend.getHorizontalAlignment() == Legend.LegendHorizontalAlignment.RIGHT ? (float)this.getWidth() - f9 + 15.0f : (f9 += Utils.convertDpToPixel(8.0f)) - 15.0f;
                                    f = f10 + f + 15.0f;
                                    f10 = this.distanceToCenter(f11, f);
                                    MPPointF mPPointF = this.getPosition((MPPointF)object, this.getRadius(), this.getAngleForPoint(f11, f));
                                    f3 = this.distanceToCenter(mPPointF.x, mPPointF.y);
                                    f5 = Utils.convertDpToPixel(5.0f);
                                    if (f >= ((MPPointF)object).y && (float)this.getHeight() - f9 > (float)this.getWidth()) {
                                        f11 = f9;
                                    } else {
                                        f11 = f12;
                                        if (f10 < f3) {
                                            f11 = f5 + (f3 - f10);
                                        }
                                    }
                                    MPPointF.recycleInstance((MPPointF)object);
                                    MPPointF.recycleInstance(mPPointF);
                                }
                            }
                            switch (this.mLegend.getHorizontalAlignment()) {
                                default: {
                                    f11 = f6;
                                    f9 = f2;
                                    f10 = f4;
                                    f12 = f8;
                                    break block0;
                                }
                                case LEFT: {
                                    f9 = f11;
                                    f11 = f6;
                                    f10 = f4;
                                    f12 = f8;
                                    break block0;
                                }
                                case RIGHT: {
                                    f10 = f11;
                                    f11 = f6;
                                    f9 = f2;
                                    f12 = f8;
                                    break block0;
                                }
                                case CENTER: 
                            }
                            switch (2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()]) {
                                default: {
                                    f11 = f6;
                                    f9 = f2;
                                    f10 = f4;
                                    f12 = f8;
                                    break block0;
                                }
                                case 1: {
                                    f12 = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                    f11 = f6;
                                    f9 = f2;
                                    f10 = f4;
                                    break block0;
                                }
                                case 2: 
                            }
                            f11 = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                            f9 = f2;
                            f10 = f4;
                            f12 = f8;
                            break;
                        }
                        case HORIZONTAL: {
                            if (this.mLegend.getVerticalAlignment() != Legend.LegendVerticalAlignment.TOP) {
                                f11 = f6;
                                f9 = f2;
                                f10 = f4;
                                f12 = f8;
                                if (this.mLegend.getVerticalAlignment() != Legend.LegendVerticalAlignment.BOTTOM) break;
                            }
                            f11 = this.getRequiredLegendOffset();
                            f12 = Math.min(this.mLegend.mNeededHeight + f11, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                            switch (2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()]) {
                                default: {
                                    f11 = f6;
                                    f9 = f2;
                                    f10 = f4;
                                    f12 = f8;
                                    break block0;
                                }
                                case 1: {
                                    f11 = f6;
                                    f9 = f2;
                                    f10 = f4;
                                    break block0;
                                }
                                case 2: 
                            }
                            f11 = f12;
                            f9 = f2;
                            f10 = f4;
                            f12 = f8;
                        }
                    }
                    f12 += this.getRequiredBaseOffset();
                    f2 = f11 + this.getRequiredBaseOffset();
                    f11 = f10 += this.getRequiredBaseOffset();
                    f10 = f9 += this.getRequiredBaseOffset();
                    f9 = f2;
                }
            }
        }
        f2 = f4 = Utils.convertDpToPixel(this.mMinOffset);
        if (this instanceof RadarChart) {
            object = this.getXAxis();
            f2 = f4;
            if (((ComponentBase)object).isEnabled()) {
                f2 = f4;
                if (((AxisBase)object).isDrawLabelsEnabled()) {
                    f2 = Math.max(f4, (float)((XAxis)object).mLabelRotatedWidth);
                }
            }
        }
        f8 = this.getExtraTopOffset();
        f6 = this.getExtraRightOffset();
        f4 = this.getExtraBottomOffset();
        f10 = Math.max(f2, f10 + this.getExtraLeftOffset());
        f12 = Math.max(f2, f12 + f8);
        f11 = Math.max(f2, f11 + f6);
        f9 = Math.max(f2, Math.max(this.getRequiredBaseOffset(), f9 + f4));
        this.mViewPortHandler.restrainViewPort(f10, f12, f11, f9);
        if (this.mLogEnabled) {
            Log.i((String)"MPAndroidChart", (String)("offsetLeft: " + f10 + ", offsetTop: " + f12 + ", offsetRight: " + f11 + ", offsetBottom: " + f9));
        }
    }

    public void computeScroll() {
        if (this.mChartTouchListener instanceof PieRadarChartTouchListener) {
            ((PieRadarChartTouchListener)this.mChartTouchListener).computeScroll();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public float distanceToCenter(float f, float f2) {
        MPPointF mPPointF = this.getCenterOffsets();
        f = f > mPPointF.x ? (f -= mPPointF.x) : mPPointF.x - f;
        f2 = f2 > mPPointF.y ? (f2 -= mPPointF.y) : mPPointF.y - f2;
        f = (float)Math.sqrt(Math.pow(f, 2.0) + Math.pow(f2, 2.0));
        MPPointF.recycleInstance(mPPointF);
        return f;
    }

    public float getAngleForPoint(float f, float f2) {
        float f3;
        MPPointF mPPointF = this.getCenterOffsets();
        double d = f - mPPointF.x;
        double d2 = f2 - mPPointF.y;
        f2 = f3 = (float)Math.toDegrees(Math.acos(d2 / Math.sqrt(d * d + d2 * d2)));
        if (f > mPPointF.x) {
            f2 = 360.0f - f3;
        }
        f = f2 += 90.0f;
        if (f2 > 360.0f) {
            f = f2 - 360.0f;
        }
        MPPointF.recycleInstance(mPPointF);
        return f;
    }

    public float getDiameter() {
        RectF rectF = this.mViewPortHandler.getContentRect();
        rectF.left += this.getExtraLeftOffset();
        rectF.top += this.getExtraTopOffset();
        rectF.right -= this.getExtraRightOffset();
        rectF.bottom -= this.getExtraBottomOffset();
        return Math.min(rectF.width(), rectF.height());
    }

    public abstract int getIndexForAngle(float var1);

    @Override
    public int getMaxVisibleCount() {
        return this.mData.getEntryCount();
    }

    public float getMinOffset() {
        return this.mMinOffset;
    }

    public MPPointF getPosition(MPPointF mPPointF, float f, float f2) {
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        this.getPosition(mPPointF, f, f2, mPPointF2);
        return mPPointF2;
    }

    public void getPosition(MPPointF mPPointF, float f, float f2, MPPointF mPPointF2) {
        mPPointF2.x = (float)((double)mPPointF.x + (double)f * Math.cos(Math.toRadians(f2)));
        mPPointF2.y = (float)((double)mPPointF.y + (double)f * Math.sin(Math.toRadians(f2)));
    }

    public abstract float getRadius();

    public float getRawRotationAngle() {
        return this.mRawRotationAngle;
    }

    protected abstract float getRequiredBaseOffset();

    protected abstract float getRequiredLegendOffset();

    public float getRotationAngle() {
        return this.mRotationAngle;
    }

    public float getYChartMax() {
        return 0.0f;
    }

    public float getYChartMin() {
        return 0.0f;
    }

    @Override
    protected void init() {
        super.init();
        this.mChartTouchListener = new PieRadarChartTouchListener(this);
    }

    public boolean isRotationEnabled() {
        return this.mRotateEnabled;
    }

    @Override
    public void notifyDataSetChanged() {
        if (this.mData == null) {
            return;
        }
        this.calcMinMax();
        if (this.mLegend != null) {
            this.mLegendRenderer.computeLegend(this.mData);
        }
        this.calculateOffsets();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mTouchEnabled && this.mChartTouchListener != null) {
            return this.mChartTouchListener.onTouch((View)this, motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setMinOffset(float f) {
        this.mMinOffset = f;
    }

    public void setRotationAngle(float f) {
        this.mRawRotationAngle = f;
        this.mRotationAngle = Utils.getNormalizedAngle(this.mRawRotationAngle);
    }

    public void setRotationEnabled(boolean bl) {
        this.mRotateEnabled = bl;
    }

}

