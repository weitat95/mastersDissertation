/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Typeface;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class XAxisRendererHorizontalBarChart
extends XAxisRenderer {
    protected BarChart mChart;
    protected Path mRenderLimitLinesPathBuffer = new Path();

    public XAxisRendererHorizontalBarChart(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer transformer, BarChart barChart) {
        super(viewPortHandler, xAxis, transformer);
        this.mChart = barChart;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void computeAxis(float f, float f2, boolean bl) {
        float f3 = f;
        float f4 = f2;
        if (this.mViewPortHandler.contentWidth() > 10.0f) {
            f3 = f;
            f4 = f2;
            if (!this.mViewPortHandler.isFullyZoomedOutY()) {
                MPPointD mPPointD = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
                MPPointD mPPointD2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
                if (bl) {
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
        this.computeAxisValues(f3, f4);
    }

    @Override
    protected void computeSize() {
        this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
        Object object = this.mXAxis.getLongestLabel();
        object = Utils.calcTextSize(this.mAxisLabelPaint, (String)object);
        float f = (int)(((FSize)object).width + this.mXAxis.getXOffset() * 3.5f);
        float f2 = ((FSize)object).height;
        object = Utils.getSizeOfRotatedRectangleByDegrees(((FSize)object).width, f2, this.mXAxis.getLabelRotationAngle());
        this.mXAxis.mLabelWidth = Math.round(f);
        this.mXAxis.mLabelHeight = Math.round(f2);
        this.mXAxis.mLabelRotatedWidth = (int)(((FSize)object).width + this.mXAxis.getXOffset() * 3.5f);
        this.mXAxis.mLabelRotatedHeight = Math.round(((FSize)object).height);
        FSize.recycleInstance((FSize)object);
    }

    @Override
    protected void drawGridLine(Canvas canvas, float f, float f2, Path path) {
        path.moveTo(this.mViewPortHandler.contentRight(), f2);
        path.lineTo(this.mViewPortHandler.contentLeft(), f2);
        canvas.drawPath(path, this.mGridPaint);
        path.reset();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        int n;
        float f2 = this.mXAxis.getLabelRotationAngle();
        boolean bl = this.mXAxis.isCenterAxisLabelsEnabled();
        float[] arrf = new float[this.mXAxis.mEntryCount * 2];
        for (n = 0; n < arrf.length; n += 2) {
            arrf[n + 1] = bl ? this.mXAxis.mCenteredEntries[n / 2] : this.mXAxis.mEntries[n / 2];
        }
        this.mTrans.pointValuesToPixel(arrf);
        n = 0;
        while (n < arrf.length) {
            float f3 = arrf[n + 1];
            if (this.mViewPortHandler.isInBoundsY(f3)) {
                this.drawLabel(canvas, this.mXAxis.getValueFormatter().getFormattedValue(this.mXAxis.mEntries[n / 2], this.mXAxis), f, f3, mPPointF, f2);
            }
            n += 2;
        }
        return;
    }

    @Override
    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(0.0f, -this.mAxis.getGridLineWidth());
        return this.mGridClippingRect;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void renderAxisLabels(Canvas canvas) {
        if (!this.mXAxis.isEnabled() || !this.mXAxis.isDrawLabelsEnabled()) {
            return;
        }
        float f = this.mXAxis.getXOffset();
        this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mXAxis.getTextColor());
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
            mPPointF.x = 0.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentRight() + f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE) {
            mPPointF.x = 1.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentRight() - f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
            mPPointF.x = 1.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentLeft() - f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
            mPPointF.x = 1.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentLeft() + f, mPPointF);
        } else {
            mPPointF.x = 0.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentRight() + f, mPPointF);
            mPPointF.x = 1.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentLeft() - f, mPPointF);
        }
        MPPointF.recycleInstance(mPPointF);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void renderAxisLine(Canvas canvas) {
        block5: {
            block4: {
                if (!this.mXAxis.isDrawAxisLineEnabled() || !this.mXAxis.isEnabled()) break block4;
                this.mAxisLinePaint.setColor(this.mXAxis.getAxisLineColor());
                this.mAxisLinePaint.setStrokeWidth(this.mXAxis.getAxisLineWidth());
                if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP || this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
                    canvas.drawLine(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
                }
                if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) break block5;
            }
            return;
        }
        canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> list = this.mXAxis.getLimitLines();
        if (list != null && list.size() > 0) {
            float[] arrf = this.mRenderLimitLinesBuffer;
            arrf[0] = 0.0f;
            arrf[1] = 0.0f;
            Path path = this.mRenderLimitLinesPathBuffer;
            path.reset();
            for (int i = 0; i < list.size(); ++i) {
                LimitLine limitLine = list.get(i);
                if (!limitLine.isEnabled()) continue;
                int n = canvas.save();
                this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                this.mLimitLineClippingRect.inset(0.0f, -limitLine.getLineWidth());
                canvas.clipRect(this.mLimitLineClippingRect);
                this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
                this.mLimitLinePaint.setColor(limitLine.getLineColor());
                this.mLimitLinePaint.setStrokeWidth(limitLine.getLineWidth());
                this.mLimitLinePaint.setPathEffect((PathEffect)limitLine.getDashPathEffect());
                arrf[1] = limitLine.getLimit();
                this.mTrans.pointValuesToPixel(arrf);
                path.moveTo(this.mViewPortHandler.contentLeft(), arrf[1]);
                path.lineTo(this.mViewPortHandler.contentRight(), arrf[1]);
                canvas.drawPath(path, this.mLimitLinePaint);
                path.reset();
                String string2 = limitLine.getLabel();
                if (string2 != null && !string2.equals("")) {
                    this.mLimitLinePaint.setStyle(limitLine.getTextStyle());
                    this.mLimitLinePaint.setPathEffect(null);
                    this.mLimitLinePaint.setColor(limitLine.getTextColor());
                    this.mLimitLinePaint.setStrokeWidth(0.5f);
                    this.mLimitLinePaint.setTextSize(limitLine.getTextSize());
                    float f = Utils.calcTextHeight(this.mLimitLinePaint, string2);
                    float f2 = Utils.convertDpToPixel(4.0f) + limitLine.getXOffset();
                    float f3 = limitLine.getLineWidth() + f + limitLine.getYOffset();
                    LimitLine.LimitLabelPosition limitLabelPosition = limitLine.getLabelPosition();
                    if (limitLabelPosition == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(string2, this.mViewPortHandler.contentRight() - f2, arrf[1] - f3 + f, this.mLimitLinePaint);
                    } else if (limitLabelPosition == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(string2, this.mViewPortHandler.contentRight() - f2, arrf[1] + f3, this.mLimitLinePaint);
                    } else if (limitLabelPosition == LimitLine.LimitLabelPosition.LEFT_TOP) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(string2, this.mViewPortHandler.contentLeft() + f2, arrf[1] - f3 + f, this.mLimitLinePaint);
                    } else {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(string2, this.mViewPortHandler.offsetLeft() + f2, arrf[1] + f3, this.mLimitLinePaint);
                    }
                }
                canvas.restoreToCount(n);
            }
        }
    }
}

