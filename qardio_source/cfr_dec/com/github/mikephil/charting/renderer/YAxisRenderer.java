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
import com.github.mikephil.charting.renderer.AxisRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRenderer
extends AxisRenderer {
    protected Path mDrawZeroLinePath;
    protected float[] mGetTransformedPositionsBuffer;
    protected RectF mGridClippingRect;
    protected RectF mLimitLineClippingRect;
    protected Path mRenderGridLinesPath = new Path();
    protected Path mRenderLimitLines;
    protected float[] mRenderLimitLinesBuffer;
    protected YAxis mYAxis;
    protected RectF mZeroLineClippingRect;
    protected Paint mZeroLinePaint;

    public YAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer transformer) {
        super(viewPortHandler, transformer, yAxis);
        this.mGridClippingRect = new RectF();
        this.mGetTransformedPositionsBuffer = new float[2];
        this.mDrawZeroLinePath = new Path();
        this.mZeroLineClippingRect = new RectF();
        this.mRenderLimitLines = new Path();
        this.mRenderLimitLinesBuffer = new float[2];
        this.mLimitLineClippingRect = new RectF();
        this.mYAxis = yAxis;
        if (this.mViewPortHandler != null) {
            this.mAxisLabelPaint.setColor(-16777216);
            this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0f));
            this.mZeroLinePaint = new Paint(1);
            this.mZeroLinePaint.setColor(-7829368);
            this.mZeroLinePaint.setStrokeWidth(1.0f);
            this.mZeroLinePaint.setStyle(Paint.Style.STROKE);
        }
    }

    protected void drawYLabels(Canvas canvas, float f, float[] arrf, float f2) {
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
            canvas.drawText(string2, f, arrf[n * 2 + 1] + f2, this.mAxisLabelPaint);
            ++n;
        } while (true);
    }

    protected void drawZeroLine(Canvas canvas) {
        int n = canvas.save();
        this.mZeroLineClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mZeroLineClippingRect.inset(0.0f, -this.mYAxis.getZeroLineWidth());
        canvas.clipRect(this.mZeroLineClippingRect);
        MPPointD mPPointD = this.mTrans.getPixelForValues(0.0f, 0.0f);
        this.mZeroLinePaint.setColor(this.mYAxis.getZeroLineColor());
        this.mZeroLinePaint.setStrokeWidth(this.mYAxis.getZeroLineWidth());
        Path path = this.mDrawZeroLinePath;
        path.reset();
        path.moveTo(this.mViewPortHandler.contentLeft(), (float)mPPointD.y);
        path.lineTo(this.mViewPortHandler.contentRight(), (float)mPPointD.y);
        canvas.drawPath(path, this.mZeroLinePaint);
        canvas.restoreToCount(n);
    }

    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(0.0f, -this.mAxis.getGridLineWidth());
        return this.mGridClippingRect;
    }

    protected float[] getTransformedPositions() {
        if (this.mGetTransformedPositionsBuffer.length != this.mYAxis.mEntryCount * 2) {
            this.mGetTransformedPositionsBuffer = new float[this.mYAxis.mEntryCount * 2];
        }
        float[] arrf = this.mGetTransformedPositionsBuffer;
        for (int i = 0; i < arrf.length; i += 2) {
            arrf[i + 1] = this.mYAxis.mEntries[i / 2];
        }
        this.mTrans.pointValuesToPixel(arrf);
        return arrf;
    }

    protected Path linePath(Path path, int n, float[] arrf) {
        path.moveTo(this.mViewPortHandler.offsetLeft(), arrf[n + 1]);
        path.lineTo(this.mViewPortHandler.contentRight(), arrf[n + 1]);
        return path;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void renderAxisLabels(Canvas canvas) {
        if (!this.mYAxis.isEnabled() || !this.mYAxis.isDrawLabelsEnabled()) {
            return;
        }
        float[] arrf = this.getTransformedPositions();
        this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
        float f = this.mYAxis.getXOffset();
        float f2 = (float)Utils.calcTextHeight(this.mAxisLabelPaint, "A") / 2.5f;
        float f3 = this.mYAxis.getYOffset();
        YAxis.AxisDependency axisDependency = this.mYAxis.getAxisDependency();
        YAxis.YAxisLabelPosition yAxisLabelPosition = this.mYAxis.getLabelPosition();
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            if (yAxisLabelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                this.mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
                f = this.mViewPortHandler.offsetLeft() - f;
            } else {
                this.mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
                f = this.mViewPortHandler.offsetLeft() + f;
            }
        } else if (yAxisLabelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
            this.mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
            f = this.mViewPortHandler.contentRight() + f;
        } else {
            this.mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
            f = this.mViewPortHandler.contentRight() - f;
        }
        this.drawYLabels(canvas, f, arrf, f2 + f3);
    }

    public void renderAxisLine(Canvas canvas) {
        if (!this.mYAxis.isEnabled() || !this.mYAxis.isDrawAxisLineEnabled()) {
            return;
        }
        this.mAxisLinePaint.setColor(this.mYAxis.getAxisLineColor());
        this.mAxisLinePaint.setStrokeWidth(this.mYAxis.getAxisLineWidth());
        if (this.mYAxis.getAxisDependency() == YAxis.AxisDependency.LEFT) {
            canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
            return;
        }
        canvas.drawLine(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void renderGridLines(Canvas canvas) {
        block6: {
            block5: {
                if (!this.mYAxis.isEnabled()) break block5;
                if (this.mYAxis.isDrawGridLinesEnabled()) {
                    int n = canvas.save();
                    canvas.clipRect(this.getGridClippingRect());
                    float[] arrf = this.getTransformedPositions();
                    this.mGridPaint.setColor(this.mYAxis.getGridColor());
                    this.mGridPaint.setStrokeWidth(this.mYAxis.getGridLineWidth());
                    this.mGridPaint.setPathEffect((PathEffect)this.mYAxis.getGridDashPathEffect());
                    Path path = this.mRenderGridLinesPath;
                    path.reset();
                    for (int i = 0; i < arrf.length; i += 2) {
                        canvas.drawPath(this.linePath(path, i, arrf), this.mGridPaint);
                        path.reset();
                    }
                    canvas.restoreToCount(n);
                }
                if (this.mYAxis.isDrawZeroLineEnabled()) break block6;
            }
            return;
        }
        this.drawZeroLine(canvas);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> list = this.mYAxis.getLimitLines();
        if (list != null && list.size() > 0) {
            float[] arrf = this.mRenderLimitLinesBuffer;
            arrf[0] = 0.0f;
            arrf[1] = 0.0f;
            Path path = this.mRenderLimitLines;
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
                    this.mLimitLinePaint.setTypeface(limitLine.getTypeface());
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

