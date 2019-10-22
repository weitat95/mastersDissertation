/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.Path
 *  android.graphics.RectF
 */
package com.github.mikephil.charting.utils;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class Transformer {
    private Matrix mMBuffer1;
    private Matrix mMBuffer2;
    protected Matrix mMatrixOffset;
    protected Matrix mMatrixValueToPx = new Matrix();
    protected Matrix mPixelToValueMatrixBuffer;
    protected ViewPortHandler mViewPortHandler;
    float[] ptsBuffer;
    protected float[] valuePointsForGenerateTransformedValuesBubble;
    protected float[] valuePointsForGenerateTransformedValuesCandle;
    protected float[] valuePointsForGenerateTransformedValuesLine;
    protected float[] valuePointsForGenerateTransformedValuesScatter;

    public Transformer(ViewPortHandler viewPortHandler) {
        this.mMatrixOffset = new Matrix();
        this.valuePointsForGenerateTransformedValuesScatter = new float[1];
        this.valuePointsForGenerateTransformedValuesBubble = new float[1];
        this.valuePointsForGenerateTransformedValuesLine = new float[1];
        this.valuePointsForGenerateTransformedValuesCandle = new float[1];
        this.mPixelToValueMatrixBuffer = new Matrix();
        this.ptsBuffer = new float[2];
        this.mMBuffer1 = new Matrix();
        this.mMBuffer2 = new Matrix();
        this.mViewPortHandler = viewPortHandler;
    }

    /*
     * Enabled aggressive block sorting
     */
    public float[] generateTransformedValuesBubble(IBubbleDataSet iBubbleDataSet, float f, int n, int n2) {
        int n3 = (n2 - n + 1) * 2;
        if (this.valuePointsForGenerateTransformedValuesBubble.length != n3) {
            this.valuePointsForGenerateTransformedValuesBubble = new float[n3];
        }
        float[] arrf = this.valuePointsForGenerateTransformedValuesBubble;
        n2 = 0;
        do {
            if (n2 >= n3) {
                this.getValueToPixelMatrix().mapPoints(arrf);
                return arrf;
            }
            Object t = iBubbleDataSet.getEntryForIndex(n2 / 2 + n);
            if (t != null) {
                arrf[n2] = ((Entry)t).getX();
                arrf[n2 + 1] = ((BaseEntry)t).getY() * f;
            } else {
                arrf[n2] = 0.0f;
                arrf[n2 + 1] = 0.0f;
            }
            n2 += 2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public float[] generateTransformedValuesCandle(ICandleDataSet iCandleDataSet, float f, float f2, int n, int n2) {
        int n3 = (int)((float)(n2 - n) * f + 1.0f) * 2;
        if (this.valuePointsForGenerateTransformedValuesCandle.length != n3) {
            this.valuePointsForGenerateTransformedValuesCandle = new float[n3];
        }
        float[] arrf = this.valuePointsForGenerateTransformedValuesCandle;
        n2 = 0;
        do {
            if (n2 >= n3) {
                this.getValueToPixelMatrix().mapPoints(arrf);
                return arrf;
            }
            CandleEntry candleEntry = (CandleEntry)iCandleDataSet.getEntryForIndex(n2 / 2 + n);
            if (candleEntry != null) {
                arrf[n2] = candleEntry.getX();
                arrf[n2 + 1] = candleEntry.getHigh() * f2;
            } else {
                arrf[n2] = 0.0f;
                arrf[n2 + 1] = 0.0f;
            }
            n2 += 2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public float[] generateTransformedValuesLine(ILineDataSet iLineDataSet, float f, float f2, int n, int n2) {
        int n3 = ((int)((float)(n2 - n) * f) + 1) * 2;
        if (this.valuePointsForGenerateTransformedValuesLine.length != n3) {
            this.valuePointsForGenerateTransformedValuesLine = new float[n3];
        }
        float[] arrf = this.valuePointsForGenerateTransformedValuesLine;
        n2 = 0;
        do {
            if (n2 >= n3) {
                this.getValueToPixelMatrix().mapPoints(arrf);
                return arrf;
            }
            Object t = iLineDataSet.getEntryForIndex(n2 / 2 + n);
            if (t != null) {
                arrf[n2] = ((Entry)t).getX();
                arrf[n2 + 1] = ((BaseEntry)t).getY() * f2;
            } else {
                arrf[n2] = 0.0f;
                arrf[n2 + 1] = 0.0f;
            }
            n2 += 2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public float[] generateTransformedValuesScatter(IScatterDataSet iScatterDataSet, float f, float f2, int n, int n2) {
        int n3 = (int)((float)(n2 - n) * f + 1.0f) * 2;
        if (this.valuePointsForGenerateTransformedValuesScatter.length != n3) {
            this.valuePointsForGenerateTransformedValuesScatter = new float[n3];
        }
        float[] arrf = this.valuePointsForGenerateTransformedValuesScatter;
        n2 = 0;
        do {
            if (n2 >= n3) {
                this.getValueToPixelMatrix().mapPoints(arrf);
                return arrf;
            }
            Object t = iScatterDataSet.getEntryForIndex(n2 / 2 + n);
            if (t != null) {
                arrf[n2] = ((Entry)t).getX();
                arrf[n2 + 1] = ((BaseEntry)t).getY() * f2;
            } else {
                arrf[n2] = 0.0f;
                arrf[n2 + 1] = 0.0f;
            }
            n2 += 2;
        } while (true);
    }

    public MPPointD getPixelForValues(float f, float f2) {
        this.ptsBuffer[0] = f;
        this.ptsBuffer[1] = f2;
        this.pointValuesToPixel(this.ptsBuffer);
        return MPPointD.getInstance(this.ptsBuffer[0], this.ptsBuffer[1]);
    }

    public Matrix getValueToPixelMatrix() {
        this.mMBuffer1.set(this.mMatrixValueToPx);
        this.mMBuffer1.postConcat(this.mViewPortHandler.mMatrixTouch);
        this.mMBuffer1.postConcat(this.mMatrixOffset);
        return this.mMBuffer1;
    }

    public MPPointD getValuesByTouchPoint(float f, float f2) {
        MPPointD mPPointD = MPPointD.getInstance(0.0, 0.0);
        this.getValuesByTouchPoint(f, f2, mPPointD);
        return mPPointD;
    }

    public void getValuesByTouchPoint(float f, float f2, MPPointD mPPointD) {
        this.ptsBuffer[0] = f;
        this.ptsBuffer[1] = f2;
        this.pixelsToValue(this.ptsBuffer);
        mPPointD.x = this.ptsBuffer[0];
        mPPointD.y = this.ptsBuffer[1];
    }

    public void pathValueToPixel(Path path) {
        path.transform(this.mMatrixValueToPx);
        path.transform(this.mViewPortHandler.getMatrixTouch());
        path.transform(this.mMatrixOffset);
    }

    public void pixelsToValue(float[] arrf) {
        Matrix matrix = this.mPixelToValueMatrixBuffer;
        matrix.reset();
        this.mMatrixOffset.invert(matrix);
        matrix.mapPoints(arrf);
        this.mViewPortHandler.getMatrixTouch().invert(matrix);
        matrix.mapPoints(arrf);
        this.mMatrixValueToPx.invert(matrix);
        matrix.mapPoints(arrf);
    }

    public void pointValuesToPixel(float[] arrf) {
        this.mMatrixValueToPx.mapPoints(arrf);
        this.mViewPortHandler.getMatrixTouch().mapPoints(arrf);
        this.mMatrixOffset.mapPoints(arrf);
    }

    public void prepareMatrixOffset(boolean bl) {
        this.mMatrixOffset.reset();
        if (!bl) {
            this.mMatrixOffset.postTranslate(this.mViewPortHandler.offsetLeft(), this.mViewPortHandler.getChartHeight() - this.mViewPortHandler.offsetBottom());
            return;
        }
        this.mMatrixOffset.setTranslate(this.mViewPortHandler.offsetLeft(), -this.mViewPortHandler.offsetTop());
        this.mMatrixOffset.postScale(1.0f, -1.0f);
    }

    public void prepareMatrixValuePx(float f, float f2, float f3, float f4) {
        float f5 = this.mViewPortHandler.contentWidth() / f2;
        float f6 = this.mViewPortHandler.contentHeight() / f3;
        f2 = f5;
        if (Float.isInfinite(f5)) {
            f2 = 0.0f;
        }
        f3 = f6;
        if (Float.isInfinite(f6)) {
            f3 = 0.0f;
        }
        this.mMatrixValueToPx.reset();
        this.mMatrixValueToPx.postTranslate(-f, -f4);
        this.mMatrixValueToPx.postScale(f2, -f3);
    }

    public void rectToPixelPhase(RectF rectF, float f) {
        rectF.top *= f;
        rectF.bottom *= f;
        this.mMatrixValueToPx.mapRect(rectF);
        this.mViewPortHandler.getMatrixTouch().mapRect(rectF);
        this.mMatrixOffset.mapRect(rectF);
    }

    public void rectToPixelPhaseHorizontal(RectF rectF, float f) {
        rectF.left *= f;
        rectF.right *= f;
        this.mMatrixValueToPx.mapRect(rectF);
        this.mViewPortHandler.getMatrixTouch().mapRect(rectF);
        this.mMatrixOffset.mapRect(rectF);
    }

    public void rectValueToPixel(RectF rectF) {
        this.mMatrixValueToPx.mapRect(rectF);
        this.mViewPortHandler.getMatrixTouch().mapRect(rectF);
        this.mMatrixOffset.mapRect(rectF);
    }
}

