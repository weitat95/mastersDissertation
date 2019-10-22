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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.renderer.AxisRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class XAxisRenderer
extends AxisRenderer {
    protected RectF mGridClippingRect;
    protected RectF mLimitLineClippingRect;
    private Path mLimitLinePath;
    float[] mLimitLineSegmentsBuffer;
    protected float[] mRenderGridLinesBuffer;
    protected Path mRenderGridLinesPath = new Path();
    protected float[] mRenderLimitLinesBuffer;
    protected XAxis mXAxis;

    public XAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer transformer) {
        super(viewPortHandler, transformer, xAxis);
        this.mRenderGridLinesBuffer = new float[2];
        this.mGridClippingRect = new RectF();
        this.mRenderLimitLinesBuffer = new float[2];
        this.mLimitLineClippingRect = new RectF();
        this.mLimitLineSegmentsBuffer = new float[4];
        this.mLimitLinePath = new Path();
        this.mXAxis = xAxis;
        this.mAxisLabelPaint.setColor(-16777216);
        this.mAxisLabelPaint.setTextAlign(Paint.Align.CENTER);
        this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0f));
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
            if (!this.mViewPortHandler.isFullyZoomedOutX()) {
                MPPointD mPPointD = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
                MPPointD mPPointD2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop());
                if (bl) {
                    f = (float)mPPointD2.x;
                    f2 = (float)mPPointD.x;
                } else {
                    f = (float)mPPointD.x;
                    f2 = (float)mPPointD2.x;
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
    protected void computeAxisValues(float f, float f2) {
        super.computeAxisValues(f, f2);
        this.computeSize();
    }

    protected void computeSize() {
        Object object = this.mXAxis.getLongestLabel();
        this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
        object = Utils.calcTextSize(this.mAxisLabelPaint, (String)object);
        float f = ((FSize)object).width;
        float f2 = Utils.calcTextHeight(this.mAxisLabelPaint, "Q");
        FSize fSize = Utils.getSizeOfRotatedRectangleByDegrees(f, f2, this.mXAxis.getLabelRotationAngle());
        this.mXAxis.mLabelWidth = Math.round(f);
        this.mXAxis.mLabelHeight = Math.round(f2);
        this.mXAxis.mLabelRotatedWidth = Math.round(fSize.width);
        this.mXAxis.mLabelRotatedHeight = Math.round(fSize.height);
        FSize.recycleInstance(fSize);
        FSize.recycleInstance((FSize)object);
    }

    protected void drawGridLine(Canvas canvas, float f, float f2, Path path) {
        path.moveTo(f, this.mViewPortHandler.contentBottom());
        path.lineTo(f, this.mViewPortHandler.contentTop());
        canvas.drawPath(path, this.mGridPaint);
        path.reset();
    }

    protected void drawLabel(Canvas canvas, String string2, float f, float f2, MPPointF mPPointF, float f3) {
        Utils.drawXAxisValue(canvas, string2, f, f2, this.mAxisLabelPaint, mPPointF, f3);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        int n;
        float f2 = this.mXAxis.getLabelRotationAngle();
        boolean bl = this.mXAxis.isCenterAxisLabelsEnabled();
        float[] arrf = new float[this.mXAxis.mEntryCount * 2];
        for (n = 0; n < arrf.length; n += 2) {
            arrf[n] = bl ? this.mXAxis.mCenteredEntries[n / 2] : this.mXAxis.mEntries[n / 2];
        }
        this.mTrans.pointValuesToPixel(arrf);
        n = 0;
        while (n < arrf.length) {
            float f3 = arrf[n];
            if (this.mViewPortHandler.isInBoundsX(f3)) {
                String string2 = this.mXAxis.getValueFormatter().getFormattedValue(this.mXAxis.mEntries[n / 2], this.mXAxis);
                float f4 = f3;
                if (this.mXAxis.isAvoidFirstLastClippingEnabled()) {
                    if (n == this.mXAxis.mEntryCount - 1 && this.mXAxis.mEntryCount > 1) {
                        float f5 = Utils.calcTextWidth(this.mAxisLabelPaint, string2);
                        f4 = f3;
                        if (f5 > this.mViewPortHandler.offsetRight() * 2.0f) {
                            f4 = f3;
                            if (f3 + f5 > this.mViewPortHandler.getChartWidth()) {
                                f4 = f3 - f5 / 2.0f;
                            }
                        }
                    } else {
                        f4 = f3;
                        if (n == 0) {
                            f4 = f3 + (float)Utils.calcTextWidth(this.mAxisLabelPaint, string2) / 2.0f;
                        }
                    }
                }
                this.drawLabel(canvas, string2, f4, f, mPPointF, f2);
            }
            n += 2;
        }
        return;
    }

    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(-this.mAxis.getGridLineWidth(), 0.0f);
        return this.mGridClippingRect;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void renderAxisLabels(Canvas canvas) {
        if (!this.mXAxis.isEnabled() || !this.mXAxis.isDrawLabelsEnabled()) {
            return;
        }
        float f = this.mXAxis.getYOffset();
        this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mXAxis.getTextColor());
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
            mPPointF.x = 0.5f;
            mPPointF.y = 1.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentTop() - f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE) {
            mPPointF.x = 0.5f;
            mPPointF.y = 1.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentTop() + f + (float)this.mXAxis.mLabelRotatedHeight, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
            mPPointF.x = 0.5f;
            mPPointF.y = 0.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentBottom() + f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
            mPPointF.x = 0.5f;
            mPPointF.y = 0.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentBottom() - f - (float)this.mXAxis.mLabelRotatedHeight, mPPointF);
        } else {
            mPPointF.x = 0.5f;
            mPPointF.y = 1.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentTop() - f, mPPointF);
            mPPointF.x = 0.5f;
            mPPointF.y = 0.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentBottom() + f, mPPointF);
        }
        MPPointF.recycleInstance(mPPointF);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void renderAxisLine(Canvas canvas) {
        block5: {
            block4: {
                if (!this.mXAxis.isDrawAxisLineEnabled() || !this.mXAxis.isEnabled()) break block4;
                this.mAxisLinePaint.setColor(this.mXAxis.getAxisLineColor());
                this.mAxisLinePaint.setStrokeWidth(this.mXAxis.getAxisLineWidth());
                this.mAxisLinePaint.setPathEffect((PathEffect)this.mXAxis.getAxisLineDashPathEffect());
                if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP || this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
                    canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mAxisLinePaint);
                }
                if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) break block5;
            }
            return;
        }
        canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
    }

    public void renderGridLines(Canvas canvas) {
        int n;
        if (!this.mXAxis.isDrawGridLinesEnabled() || !this.mXAxis.isEnabled()) {
            return;
        }
        int n2 = canvas.save();
        canvas.clipRect(this.getGridClippingRect());
        if (this.mRenderGridLinesBuffer.length != this.mAxis.mEntryCount * 2) {
            this.mRenderGridLinesBuffer = new float[this.mXAxis.mEntryCount * 2];
        }
        float[] arrf = this.mRenderGridLinesBuffer;
        for (n = 0; n < arrf.length; n += 2) {
            arrf[n] = this.mXAxis.mEntries[n / 2];
            arrf[n + 1] = this.mXAxis.mEntries[n / 2];
        }
        this.mTrans.pointValuesToPixel(arrf);
        this.setupGridPaint();
        Path path = this.mRenderGridLinesPath;
        path.reset();
        for (n = 0; n < arrf.length; n += 2) {
            this.drawGridLine(canvas, arrf[n], arrf[n + 1], path);
        }
        canvas.restoreToCount(n2);
    }

    public void renderLimitLineLabel(Canvas canvas, LimitLine object, float[] arrf, float f) {
        String string2;
        float f2;
        block6: {
            block5: {
                string2 = object.getLabel();
                if (string2 == null || string2.equals("")) break block5;
                this.mLimitLinePaint.setStyle(object.getTextStyle());
                this.mLimitLinePaint.setPathEffect(null);
                this.mLimitLinePaint.setColor(object.getTextColor());
                this.mLimitLinePaint.setStrokeWidth(0.5f);
                this.mLimitLinePaint.setTextSize(object.getTextSize());
                f2 = object.getLineWidth() + object.getXOffset();
                object = object.getLabelPosition();
                if (object != LimitLine.LimitLabelPosition.RIGHT_TOP) break block6;
                float f3 = Utils.calcTextHeight(this.mLimitLinePaint, string2);
                this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(string2, arrf[0] + f2, this.mViewPortHandler.contentTop() + f + f3, this.mLimitLinePaint);
            }
            return;
        }
        if (object == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
            this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(string2, arrf[0] + f2, this.mViewPortHandler.contentBottom() - f, this.mLimitLinePaint);
            return;
        }
        if (object == LimitLine.LimitLabelPosition.LEFT_TOP) {
            this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
            float f4 = Utils.calcTextHeight(this.mLimitLinePaint, string2);
            canvas.drawText(string2, arrf[0] - f2, this.mViewPortHandler.contentTop() + f + f4, this.mLimitLinePaint);
            return;
        }
        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(string2, arrf[0] - f2, this.mViewPortHandler.contentBottom() - f, this.mLimitLinePaint);
    }

    public void renderLimitLineLine(Canvas canvas, LimitLine limitLine, float[] arrf) {
        this.mLimitLineSegmentsBuffer[0] = arrf[0];
        this.mLimitLineSegmentsBuffer[1] = this.mViewPortHandler.contentTop();
        this.mLimitLineSegmentsBuffer[2] = arrf[0];
        this.mLimitLineSegmentsBuffer[3] = this.mViewPortHandler.contentBottom();
        this.mLimitLinePath.reset();
        this.mLimitLinePath.moveTo(this.mLimitLineSegmentsBuffer[0], this.mLimitLineSegmentsBuffer[1]);
        this.mLimitLinePath.lineTo(this.mLimitLineSegmentsBuffer[2], this.mLimitLineSegmentsBuffer[3]);
        this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
        this.mLimitLinePaint.setColor(limitLine.getLineColor());
        this.mLimitLinePaint.setStrokeWidth(limitLine.getLineWidth());
        this.mLimitLinePaint.setPathEffect((PathEffect)limitLine.getDashPathEffect());
        canvas.drawPath(this.mLimitLinePath, this.mLimitLinePaint);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> list = this.mXAxis.getLimitLines();
        if (list != null && list.size() > 0) {
            float[] arrf = this.mRenderLimitLinesBuffer;
            arrf[0] = 0.0f;
            arrf[1] = 0.0f;
            for (int i = 0; i < list.size(); ++i) {
                LimitLine limitLine = list.get(i);
                if (!limitLine.isEnabled()) continue;
                int n = canvas.save();
                this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                this.mLimitLineClippingRect.inset(-limitLine.getLineWidth(), 0.0f);
                canvas.clipRect(this.mLimitLineClippingRect);
                arrf[0] = limitLine.getLimit();
                arrf[1] = 0.0f;
                this.mTrans.pointValuesToPixel(arrf);
                this.renderLimitLineLine(canvas, limitLine, arrf);
                this.renderLimitLineLabel(canvas, limitLine, arrf, 2.0f + limitLine.getYOffset());
                canvas.restoreToCount(n);
            }
        }
    }

    protected void setupGridPaint() {
        this.mGridPaint.setColor(this.mXAxis.getGridColor());
        this.mGridPaint.setStrokeWidth(this.mXAxis.getGridLineWidth());
        this.mGridPaint.setPathEffect((PathEffect)this.mXAxis.getGridDashPathEffect());
    }
}

