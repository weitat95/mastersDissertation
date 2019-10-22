/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Paint;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.renderer.Renderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class AxisRenderer
extends Renderer {
    protected AxisBase mAxis;
    protected Paint mAxisLabelPaint;
    protected Paint mAxisLinePaint;
    protected Paint mGridPaint;
    protected Paint mLimitLinePaint;
    protected Transformer mTrans;

    public AxisRenderer(ViewPortHandler viewPortHandler, Transformer transformer, AxisBase axisBase) {
        super(viewPortHandler);
        this.mTrans = transformer;
        this.mAxis = axisBase;
        if (this.mViewPortHandler != null) {
            this.mAxisLabelPaint = new Paint(1);
            this.mGridPaint = new Paint();
            this.mGridPaint.setColor(-7829368);
            this.mGridPaint.setStrokeWidth(1.0f);
            this.mGridPaint.setStyle(Paint.Style.STROKE);
            this.mGridPaint.setAlpha(90);
            this.mAxisLinePaint = new Paint();
            this.mAxisLinePaint.setColor(-16777216);
            this.mAxisLinePaint.setStrokeWidth(1.0f);
            this.mAxisLinePaint.setStyle(Paint.Style.STROKE);
            this.mLimitLinePaint = new Paint(1);
            this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void computeAxis(float f, float f2, boolean bl) {
        float f3 = f;
        float f4 = f2;
        if (this.mViewPortHandler != null) {
            f3 = f;
            f4 = f2;
            if (this.mViewPortHandler.contentWidth() > 10.0f) {
                f3 = f;
                f4 = f2;
                if (!this.mViewPortHandler.isFullyZoomedOutY()) {
                    MPPointD mPPointD = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
                    MPPointD mPPointD2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
                    if (!bl) {
                        f = (float)mPPointD2.y;
                        f2 = (float)mPPointD.y;
                    } else {
                        f = (float)mPPointD.y;
                        f2 = (float)mPPointD2.y;
                    }
                    MPPointD.recycleInstance(mPPointD);
                    MPPointD.recycleInstance(mPPointD2);
                    f4 = f2;
                    f3 = f;
                }
            }
        }
        this.computeAxisValues(f3, f4);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void computeAxisValues(float f, float f2) {
        int n = this.mAxis.getLabelCount();
        double d = Math.abs(f2 - f);
        if (n == 0 || d <= 0.0 || Double.isInfinite(d)) {
            this.mAxis.mEntries = new float[0];
            this.mAxis.mCenteredEntries = new float[0];
            this.mAxis.mEntryCount = 0;
            return;
        } else {
            double d2;
            double d3 = d2 = (double)Utils.roundToNextSignificant(d / (double)n);
            if (this.mAxis.isGranularityEnabled()) {
                d3 = d2;
                if (d2 < (double)this.mAxis.getGranularity()) {
                    d3 = this.mAxis.getGranularity();
                }
            }
            double d4 = Utils.roundToNextSignificant(Math.pow(10.0, (int)Math.log10(d3)));
            d2 = d3;
            if ((int)(d3 / d4) > 5) {
                d2 = Math.floor(10.0 * d4);
            }
            int n2 = this.mAxis.isCenterAxisLabelsEnabled() ? 1 : 0;
            if (this.mAxis.isForceLabelsEnabled()) {
                d = (float)d / (float)(n - 1);
                this.mAxis.mEntryCount = n;
                if (this.mAxis.mEntries.length < n) {
                    this.mAxis.mEntries = new float[n];
                }
                for (n2 = 0; n2 < n; ++n2) {
                    this.mAxis.mEntries[n2] = f;
                    f = (float)((double)f + d);
                }
                n2 = n;
            } else {
                d = d2 == 0.0 ? 0.0 : Math.ceil((double)f / d2) * d2;
                d3 = d;
                if (this.mAxis.isCenterAxisLabelsEnabled()) {
                    d3 = d - d2;
                }
                d = d2 == 0.0 ? 0.0 : Utils.nextUp(Math.floor((double)f2 / d2) * d2);
                n = n2;
                if (d2 != 0.0) {
                    d4 = d3;
                    do {
                        n = n2++;
                        if (!(d4 <= d)) break;
                        d4 += d2;
                    } while (true);
                }
                this.mAxis.mEntryCount = n;
                if (this.mAxis.mEntries.length < n) {
                    this.mAxis.mEntries = new float[n];
                }
                int n3 = 0;
                do {
                    d = d2;
                    n2 = n;
                    if (n3 >= n) break;
                    d = d3;
                    if (d3 == 0.0) {
                        d = 0.0;
                    }
                    this.mAxis.mEntries[n3] = (float)d;
                    d3 = d + d2;
                    ++n3;
                } while (true);
            }
            this.mAxis.mDecimals = d < 1.0 ? (int)Math.ceil(-Math.log10(d)) : 0;
            if (!this.mAxis.isCenterAxisLabelsEnabled()) return;
            {
                if (this.mAxis.mCenteredEntries.length < n2) {
                    this.mAxis.mCenteredEntries = new float[n2];
                }
                f = (float)d / 2.0f;
                for (n = 0; n < n2; ++n) {
                    this.mAxis.mCenteredEntries[n] = this.mAxis.mEntries[n] + f;
                }
            }
        }
    }

    public Paint getPaintAxisLabels() {
        return this.mAxisLabelPaint;
    }
}

