/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.renderer.LineScatterCandleRadarRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class CandleStickChartRenderer
extends LineScatterCandleRadarRenderer {
    private float[] mBodyBuffers;
    protected CandleDataProvider mChart;
    private float[] mCloseBuffers;
    private float[] mOpenBuffers;
    private float[] mRangeBuffers;
    private float[] mShadowBuffers = new float[8];

    public CandleStickChartRenderer(CandleDataProvider candleDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mBodyBuffers = new float[4];
        this.mRangeBuffers = new float[4];
        this.mOpenBuffers = new float[4];
        this.mCloseBuffers = new float[4];
        this.mChart = candleDataProvider;
    }

    @Override
    public void drawData(Canvas canvas) {
        for (ICandleDataSet iCandleDataSet : this.mChart.getCandleData().getDataSets()) {
            if (!iCandleDataSet.isVisible()) continue;
            this.drawDataSet(canvas, iCandleDataSet);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawDataSet(Canvas canvas, ICandleDataSet iCandleDataSet) {
        Transformer transformer = this.mChart.getTransformer(iCandleDataSet.getAxisDependency());
        float f = this.mAnimator.getPhaseY();
        float f2 = iCandleDataSet.getBarSpace();
        boolean bl = iCandleDataSet.getShowCandleBar();
        this.mXBounds.set(this.mChart, iCandleDataSet);
        this.mRenderPaint.setStrokeWidth(iCandleDataSet.getShadowWidth());
        int n = this.mXBounds.min;
        while (n <= this.mXBounds.range + this.mXBounds.min) {
            CandleEntry candleEntry = (CandleEntry)iCandleDataSet.getEntryForIndex(n);
            if (candleEntry != null) {
                int n2;
                float f3 = candleEntry.getX();
                float f4 = candleEntry.getOpen();
                float f5 = candleEntry.getClose();
                float f6 = candleEntry.getHigh();
                float f7 = candleEntry.getLow();
                if (bl) {
                    this.mShadowBuffers[0] = f3;
                    this.mShadowBuffers[2] = f3;
                    this.mShadowBuffers[4] = f3;
                    this.mShadowBuffers[6] = f3;
                    if (f4 > f5) {
                        this.mShadowBuffers[1] = f6 * f;
                        this.mShadowBuffers[3] = f4 * f;
                        this.mShadowBuffers[5] = f7 * f;
                        this.mShadowBuffers[7] = f5 * f;
                    } else if (f4 < f5) {
                        this.mShadowBuffers[1] = f6 * f;
                        this.mShadowBuffers[3] = f5 * f;
                        this.mShadowBuffers[5] = f7 * f;
                        this.mShadowBuffers[7] = f4 * f;
                    } else {
                        this.mShadowBuffers[1] = f6 * f;
                        this.mShadowBuffers[3] = f4 * f;
                        this.mShadowBuffers[5] = f7 * f;
                        this.mShadowBuffers[7] = this.mShadowBuffers[3];
                    }
                    transformer.pointValuesToPixel(this.mShadowBuffers);
                    if (iCandleDataSet.getShadowColorSameAsCandle()) {
                        if (f4 > f5) {
                            candleEntry = this.mRenderPaint;
                            n2 = iCandleDataSet.getDecreasingColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getDecreasingColor();
                            candleEntry.setColor(n2);
                        } else if (f4 < f5) {
                            candleEntry = this.mRenderPaint;
                            n2 = iCandleDataSet.getIncreasingColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getIncreasingColor();
                            candleEntry.setColor(n2);
                        } else {
                            candleEntry = this.mRenderPaint;
                            n2 = iCandleDataSet.getNeutralColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getNeutralColor();
                            candleEntry.setColor(n2);
                        }
                    } else {
                        candleEntry = this.mRenderPaint;
                        n2 = iCandleDataSet.getShadowColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getShadowColor();
                        candleEntry.setColor(n2);
                    }
                    this.mRenderPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawLines(this.mShadowBuffers, this.mRenderPaint);
                    this.mBodyBuffers[0] = f3 - 0.5f + f2;
                    this.mBodyBuffers[1] = f5 * f;
                    this.mBodyBuffers[2] = 0.5f + f3 - f2;
                    this.mBodyBuffers[3] = f4 * f;
                    transformer.pointValuesToPixel(this.mBodyBuffers);
                    if (f4 > f5) {
                        if (iCandleDataSet.getDecreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet.getColor(n));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getDecreasingColor());
                        }
                        this.mRenderPaint.setStyle(iCandleDataSet.getDecreasingPaintStyle());
                        canvas.drawRect(this.mBodyBuffers[0], this.mBodyBuffers[3], this.mBodyBuffers[2], this.mBodyBuffers[1], this.mRenderPaint);
                    } else if (f4 < f5) {
                        if (iCandleDataSet.getIncreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet.getColor(n));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getIncreasingColor());
                        }
                        this.mRenderPaint.setStyle(iCandleDataSet.getIncreasingPaintStyle());
                        canvas.drawRect(this.mBodyBuffers[0], this.mBodyBuffers[1], this.mBodyBuffers[2], this.mBodyBuffers[3], this.mRenderPaint);
                    } else {
                        if (iCandleDataSet.getNeutralColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet.getColor(n));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getNeutralColor());
                        }
                        canvas.drawLine(this.mBodyBuffers[0], this.mBodyBuffers[1], this.mBodyBuffers[2], this.mBodyBuffers[3], this.mRenderPaint);
                    }
                } else {
                    this.mRangeBuffers[0] = f3;
                    this.mRangeBuffers[1] = f6 * f;
                    this.mRangeBuffers[2] = f3;
                    this.mRangeBuffers[3] = f7 * f;
                    this.mOpenBuffers[0] = f3 - 0.5f + f2;
                    this.mOpenBuffers[1] = f4 * f;
                    this.mOpenBuffers[2] = f3;
                    this.mOpenBuffers[3] = f4 * f;
                    this.mCloseBuffers[0] = 0.5f + f3 - f2;
                    this.mCloseBuffers[1] = f5 * f;
                    this.mCloseBuffers[2] = f3;
                    this.mCloseBuffers[3] = f5 * f;
                    transformer.pointValuesToPixel(this.mRangeBuffers);
                    transformer.pointValuesToPixel(this.mOpenBuffers);
                    transformer.pointValuesToPixel(this.mCloseBuffers);
                    n2 = f4 > f5 ? (iCandleDataSet.getDecreasingColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getDecreasingColor()) : (f4 < f5 ? (iCandleDataSet.getIncreasingColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getIncreasingColor()) : (iCandleDataSet.getNeutralColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getNeutralColor()));
                    this.mRenderPaint.setColor(n2);
                    canvas.drawLine(this.mRangeBuffers[0], this.mRangeBuffers[1], this.mRangeBuffers[2], this.mRangeBuffers[3], this.mRenderPaint);
                    canvas.drawLine(this.mOpenBuffers[0], this.mOpenBuffers[1], this.mOpenBuffers[2], this.mOpenBuffers[3], this.mRenderPaint);
                    canvas.drawLine(this.mCloseBuffers[0], this.mCloseBuffers[1], this.mCloseBuffers[2], this.mCloseBuffers[3], this.mRenderPaint);
                }
            }
            ++n;
        }
        return;
    }

    @Override
    public void drawExtras(Canvas canvas) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        CandleData candleData = this.mChart.getCandleData();
        int n = arrhighlight.length;
        int n2 = 0;
        while (n2 < n) {
            Object object;
            Highlight highlight = arrhighlight[n2];
            ICandleDataSet iCandleDataSet = (ICandleDataSet)candleData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iCandleDataSet != null && iCandleDataSet.isHighlightEnabled() && this.isInBoundsX((Entry)(object = (CandleEntry)iCandleDataSet.getEntryForXValue(highlight.getX(), highlight.getY())), iCandleDataSet)) {
                float f = (((CandleEntry)object).getLow() * this.mAnimator.getPhaseY() + ((CandleEntry)object).getHigh() * this.mAnimator.getPhaseY()) / 2.0f;
                object = this.mChart.getTransformer(iCandleDataSet.getAxisDependency()).getPixelForValues(((Entry)object).getX(), f);
                highlight.setDraw((float)((MPPointD)object).x, (float)((MPPointD)object).y);
                this.drawHighlightLines(canvas, (float)((MPPointD)object).x, (float)((MPPointD)object).y, iCandleDataSet);
            }
            ++n2;
        }
        return;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void drawValues(Canvas var1_1) {
        if (this.isDrawingValuesAllowed(this.mChart) == false) return;
        var7_2 = this.mChart.getCandleData().getDataSets();
        var5_3 = 0;
        block0: do {
            if (var5_3 >= var7_2.size()) return;
            var8_8 = (ICandleDataSet)var7_2.get(var5_3);
            if (!this.shouldDrawValues(var8_8)) ** GOTO lbl16
            this.applyValueTextStyle(var8_8);
            var9_9 = this.mChart.getTransformer(var8_8.getAxisDependency());
            this.mXBounds.set(this.mChart, var8_8);
            var9_9 = var9_9.generateTransformedValuesCandle(var8_8, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
            var2_4 = Utils.convertDpToPixel(5.0f);
            var6_7 = 0;
            do {
                block7: {
                    if (var6_7 < var9_9.length) break block7;
lbl16:
                    // 3 sources
                    do {
                        ++var5_3;
                        continue block0;
                        break;
                    } while (true);
                }
                var3_5 = var9_9[var6_7];
                var4_6 = var9_9[var6_7 + 1];
                if (!this.mViewPortHandler.isInBoundsRight(var3_5)) ** continue;
                if (this.mViewPortHandler.isInBoundsLeft(var3_5) && this.mViewPortHandler.isInBoundsY(var4_6)) {
                    var10_10 = (CandleEntry)var8_8.getEntryForIndex(var6_7 / 2 + this.mXBounds.min);
                    this.drawValue(var1_1, var8_8.getValueFormatter(), var10_10.getHigh(), var10_10, var5_3, var3_5, var4_6 - var2_4, var8_8.getValueTextColor(var6_7 / 2));
                }
                var6_7 += 2;
            } while (true);
            break;
        } while (true);
    }

    @Override
    public void initBuffers() {
    }
}

