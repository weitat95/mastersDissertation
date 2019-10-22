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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererHorizontalBarChart
extends YAxisRenderer {
    protected Path mDrawZeroLinePathBuffer = new Path();
    protected float[] mRenderLimitLinesBuffer;
    protected Path mRenderLimitLinesPathBuffer = new Path();

    public YAxisRendererHorizontalBarChart(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer transformer) {
        super(viewPortHandler, yAxis, transformer);
        this.mRenderLimitLinesBuffer = new float[4];
        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void computeAxis(float f, float f2, boolean bl) {
        float f3 = f;
        float f4 = f2;
        if (this.mViewPortHandler.contentHeight() > 10.0f) {
            f3 = f;
            f4 = f2;
            if (!this.mViewPortHandler.isFullyZoomedOutX()) {
                MPPointD mPPointD = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
                MPPointD mPPointD2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop());
                if (!bl) {
                    f = (float)mPPointD.x;
                    f2 = (float)mPPointD2.x;
                } else {
                    f = (float)mPPointD2.x;
                    f2 = (float)mPPointD.x;
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
    protected void drawYLabels(Canvas canvas, float f, float[] arrf, float f2) {
        this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
        int n = 0;
        do {
            String string2;
            block4: {
                block3: {
                    if (n >= this.mYAxis.mEntryCount) break block3;
                    string2 = this.mYAxis.getFormattedLabel(n);
                    if (this.mYAxis.isDrawTopYLabelEntryEnabled() || n < this.mYAxis.mEntryCount - 1) break block4;
                }
                return;
            }
            canvas.drawText(string2, arrf[n * 2], f - f2, this.mAxisLabelPaint);
            ++n;
        } while (true);
    }

    @Override
    protected void drawZeroLine(Canvas canvas) {
        int n = canvas.save();
        this.mZeroLineClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mZeroLineClippingRect.inset(-this.mYAxis.getZeroLineWidth(), 0.0f);
        canvas.clipRect(this.mLimitLineClippingRect);
        MPPointD mPPointD = this.mTrans.getPixelForValues(0.0f, 0.0f);
        this.mZeroLinePaint.setColor(this.mYAxis.getZeroLineColor());
        this.mZeroLinePaint.setStrokeWidth(this.mYAxis.getZeroLineWidth());
        Path path = this.mDrawZeroLinePathBuffer;
        path.reset();
        path.moveTo((float)mPPointD.x - 1.0f, this.mViewPortHandler.contentTop());
        path.lineTo((float)mPPointD.x - 1.0f, this.mViewPortHandler.contentBottom());
        canvas.drawPath(path, this.mZeroLinePaint);
        canvas.restoreToCount(n);
    }

    @Override
    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(-this.mAxis.getGridLineWidth(), 0.0f);
        return this.mGridClippingRect;
    }

    @Override
    protected float[] getTransformedPositions() {
        if (this.mGetTransformedPositionsBuffer.length != this.mYAxis.mEntryCount * 2) {
            this.mGetTransformedPositionsBuffer = new float[this.mYAxis.mEntryCount * 2];
        }
        float[] arrf = this.mGetTransformedPositionsBuffer;
        for (int i = 0; i < arrf.length; i += 2) {
            arrf[i] = this.mYAxis.mEntries[i / 2];
        }
        this.mTrans.pointValuesToPixel(arrf);
        return arrf;
    }

    @Override
    protected Path linePath(Path path, int n, float[] arrf) {
        path.moveTo(arrf[n], this.mViewPortHandler.contentTop());
        path.lineTo(arrf[n], this.mViewPortHandler.contentBottom());
        return path;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void renderAxisLabels(Canvas canvas) {
        if (!this.mYAxis.isEnabled() || !this.mYAxis.isDrawLabelsEnabled()) {
            return;
        }
        float[] arrf = this.getTransformedPositions();
        this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
        this.mAxisLabelPaint.setTextAlign(Paint.Align.CENTER);
        float f = Utils.convertDpToPixel(2.5f);
        float f2 = Utils.calcTextHeight(this.mAxisLabelPaint, "Q");
        YAxis.AxisDependency axisDependency = this.mYAxis.getAxisDependency();
        YAxis.YAxisLabelPosition yAxisLabelPosition = this.mYAxis.getLabelPosition();
        f = axisDependency == YAxis.AxisDependency.LEFT ? (yAxisLabelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART ? this.mViewPortHandler.contentTop() - f : this.mViewPortHandler.contentTop() - f) : (yAxisLabelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART ? this.mViewPortHandler.contentBottom() + f2 + f : this.mViewPortHandler.contentBottom() + f2 + f);
        this.drawYLabels(canvas, f, arrf, this.mYAxis.getYOffset());
    }

    @Override
    public void renderAxisLine(Canvas canvas) {
        if (!this.mYAxis.isEnabled() || !this.mYAxis.isDrawAxisLineEnabled()) {
            return;
        }
        this.mAxisLinePaint.setColor(this.mYAxis.getAxisLineColor());
        this.mAxisLinePaint.setStrokeWidth(this.mYAxis.getAxisLineWidth());
        if (this.mYAxis.getAxisDependency() == YAxis.AxisDependency.LEFT) {
            canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mAxisLinePaint);
            return;
        }
        canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> list = this.mYAxis.getLimitLines();
        if (list != null && list.size() > 0) {
            float[] arrf = this.mRenderLimitLinesBuffer;
            arrf[0] = 0.0f;
            arrf[1] = 0.0f;
            arrf[2] = 0.0f;
            arrf[3] = 0.0f;
            Path path = this.mRenderLimitLinesPathBuffer;
            path.reset();
            for (int i = 0; i < list.size(); ++i) {
                LimitLine limitLine = list.get(i);
                if (!limitLine.isEnabled()) continue;
                int n = canvas.save();
                this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                this.mLimitLineClippingRect.inset(-limitLine.getLineWidth(), 0.0f);
                canvas.clipRect(this.mLimitLineClippingRect);
                arrf[0] = limitLine.getLimit();
                arrf[2] = limitLine.getLimit();
                this.mTrans.pointValuesToPixel(arrf);
                arrf[1] = this.mViewPortHandler.contentTop();
                arrf[3] = this.mViewPortHandler.contentBottom();
                path.moveTo(arrf[0], arrf[1]);
                path.lineTo(arrf[2], arrf[3]);
                this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
                this.mLimitLinePaint.setColor(limitLine.getLineColor());
                this.mLimitLinePaint.setPathEffect((PathEffect)limitLine.getDashPathEffect());
                this.mLimitLinePaint.setStrokeWidth(limitLine.getLineWidth());
                canvas.drawPath(path, this.mLimitLinePaint);
                path.reset();
                String string2 = limitLine.getLabel();
                if (string2 != null && !string2.equals("")) {
                    float f;
                    this.mLimitLinePaint.setStyle(limitLine.getTextStyle());
                    this.mLimitLinePaint.setPathEffect(null);
                    this.mLimitLinePaint.setColor(limitLine.getTextColor());
                    this.mLimitLinePaint.setTypeface(limitLine.getTypeface());
                    this.mLimitLinePaint.setStrokeWidth(0.5f);
                    this.mLimitLinePaint.setTextSize(limitLine.getTextSize());
                    float f2 = limitLine.getLineWidth() + limitLine.getXOffset();
                    float f3 = Utils.convertDpToPixel(2.0f) + limitLine.getYOffset();
                    LimitLine.LimitLabelPosition limitLabelPosition = limitLine.getLabelPosition();
                    if (limitLabelPosition == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                        f = Utils.calcTextHeight(this.mLimitLinePaint, string2);
                        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(string2, arrf[0] + f2, this.mViewPortHandler.contentTop() + f3 + f, this.mLimitLinePaint);
                    } else if (limitLabelPosition == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(string2, arrf[0] + f2, this.mViewPortHandler.contentBottom() - f3, this.mLimitLinePaint);
                    } else if (limitLabelPosition == LimitLine.LimitLabelPosition.LEFT_TOP) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                        f = Utils.calcTextHeight(this.mLimitLinePaint, string2);
                        canvas.drawText(string2, arrf[0] - f2, this.mViewPortHandler.contentTop() + f3 + f, this.mLimitLinePaint);
                    } else {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(string2, arrf[0] - f2, this.mViewPortHandler.contentBottom() - f3, this.mLimitLinePaint);
                    }
                }
                canvas.restoreToCount(n);
            }
        }
    }
}

