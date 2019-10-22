/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.RectF
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class BarChartRenderer
extends BarLineScatterCandleBubbleRenderer {
    protected Paint mBarBorderPaint;
    protected BarBuffer[] mBarBuffers;
    protected RectF mBarRect = new RectF();
    private RectF mBarShadowRectBuffer = new RectF();
    protected BarDataProvider mChart;
    protected Paint mShadowPaint;

    public BarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = barDataProvider;
        this.mHighlightPaint = new Paint(1);
        this.mHighlightPaint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setColor(Color.rgb((int)0, (int)0, (int)0));
        this.mHighlightPaint.setAlpha(120);
        this.mShadowPaint = new Paint(1);
        this.mShadowPaint.setStyle(Paint.Style.FILL);
        this.mBarBorderPaint = new Paint(1);
        this.mBarBorderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void drawData(Canvas canvas) {
        BarData barData = this.mChart.getBarData();
        for (int i = 0; i < barData.getDataSetCount(); ++i) {
            IBarDataSet iBarDataSet = (IBarDataSet)barData.getDataSetByIndex(i);
            if (!iBarDataSet.isVisible()) continue;
            this.drawDataSet(canvas, iBarDataSet, i);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawDataSet(Canvas canvas, IBarDataSet iBarDataSet, int n) {
        int n2;
        Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
        this.mBarBorderPaint.setColor(iBarDataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(iBarDataSet.getBarBorderWidth()));
        boolean bl = iBarDataSet.getBarBorderWidth() > 0.0f;
        float f = this.mAnimator.getPhaseX();
        float f2 = this.mAnimator.getPhaseY();
        if (this.mChart.isDrawBarShadowEnabled()) {
            this.mShadowPaint.setColor(iBarDataSet.getBarShadowColor());
            float f3 = this.mChart.getBarData().getBarWidth() / 2.0f;
            int n3 = Math.min((int)Math.ceil((float)iBarDataSet.getEntryCount() * f), iBarDataSet.getEntryCount());
            for (n2 = 0; n2 < n3; ++n2) {
                float f4 = ((BarEntry)iBarDataSet.getEntryForIndex(n2)).getX();
                this.mBarShadowRectBuffer.left = f4 - f3;
                this.mBarShadowRectBuffer.right = f4 + f3;
                transformer.rectValueToPixel(this.mBarShadowRectBuffer);
                if (!this.mViewPortHandler.isInBoundsLeft(this.mBarShadowRectBuffer.right)) continue;
                if (!this.mViewPortHandler.isInBoundsRight(this.mBarShadowRectBuffer.left)) break;
                this.mBarShadowRectBuffer.top = this.mViewPortHandler.contentTop();
                this.mBarShadowRectBuffer.bottom = this.mViewPortHandler.contentBottom();
                canvas.drawRect(this.mBarShadowRectBuffer, this.mShadowPaint);
            }
        }
        BarBuffer barBuffer = this.mBarBuffers[n];
        barBuffer.setPhases(f, f2);
        barBuffer.setDataSet(n);
        barBuffer.setInverted(this.mChart.isInverted(iBarDataSet.getAxisDependency()));
        barBuffer.setBarWidth(this.mChart.getBarData().getBarWidth());
        barBuffer.feed(iBarDataSet);
        transformer.pointValuesToPixel(barBuffer.buffer);
        n = iBarDataSet.getColors().size() == 1 ? 1 : 0;
        if (n != 0) {
            this.mRenderPaint.setColor(iBarDataSet.getColor());
        }
        for (n2 = 0; n2 < barBuffer.size(); n2 += 4) {
            if (!this.mViewPortHandler.isInBoundsLeft(barBuffer.buffer[n2 + 2])) continue;
            if (!this.mViewPortHandler.isInBoundsRight(barBuffer.buffer[n2])) break;
            if (n == 0) {
                this.mRenderPaint.setColor(iBarDataSet.getColor(n2 / 4));
            }
            canvas.drawRect(barBuffer.buffer[n2], barBuffer.buffer[n2 + 1], barBuffer.buffer[n2 + 2], barBuffer.buffer[n2 + 3], this.mRenderPaint);
            if (!bl) continue;
            canvas.drawRect(barBuffer.buffer[n2], barBuffer.buffer[n2 + 1], barBuffer.buffer[n2 + 2], barBuffer.buffer[n2 + 3], this.mBarBorderPaint);
        }
    }

    @Override
    public void drawExtras(Canvas canvas) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        BarData barData = this.mChart.getBarData();
        int n = arrhighlight.length;
        int n2 = 0;
        while (n2 < n) {
            BarEntry barEntry;
            Highlight highlight = arrhighlight[n2];
            Object object = (IBarDataSet)barData.getDataSetByIndex(highlight.getDataSetIndex());
            if (object != null && object.isHighlightEnabled() && this.isInBoundsX(barEntry = (BarEntry)object.getEntryForXValue(highlight.getX(), highlight.getY()), (IBarLineScatterCandleBubbleDataSet)object)) {
                float f;
                float f2;
                Transformer transformer = this.mChart.getTransformer(object.getAxisDependency());
                this.mHighlightPaint.setColor(object.getHighLightColor());
                this.mHighlightPaint.setAlpha(object.getHighLightAlpha());
                boolean bl = highlight.getStackIndex() >= 0 && barEntry.isStacked();
                if (bl) {
                    if (this.mChart.isHighlightFullBarEnabled()) {
                        f = barEntry.getPositiveSum();
                        f2 = -barEntry.getNegativeSum();
                    } else {
                        object = barEntry.getRanges()[highlight.getStackIndex()];
                        f = ((Range)object).from;
                        f2 = ((Range)object).to;
                    }
                } else {
                    f = barEntry.getY();
                    f2 = 0.0f;
                }
                this.prepareBarHighlight(barEntry.getX(), f, f2, barData.getBarWidth() / 2.0f, transformer);
                this.setHighlightDrawPos(highlight, this.mBarRect);
                canvas.drawRect(this.mBarRect, this.mHighlightPaint);
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
        block18: {
            block17: {
                block16: {
                    block15: {
                        block14: {
                            if (this.isDrawingValuesAllowed(this.mChart) == false) return;
                            var19_2 = this.mChart.getBarData().getDataSets();
                            var8_3 = Utils.convertDpToPixel(4.5f);
                            var17_4 = this.mChart.isDrawValueAboveBarEnabled();
                            var12_5 = 0;
                            block0 : while (var12_5 < this.mChart.getBarData().getDataSetCount()) {
                                var20_20 = (IBarDataSet)var19_2.get(var12_5);
                                if (!this.shouldDrawValues(var20_20)) ** GOTO lbl29
                                this.applyValueTextStyle(var20_20);
                                var18_19 = this.mChart.isInverted(var20_20.getAxisDependency());
                                var6_10 = Utils.calcTextHeight(this.mValuePaint, "8");
                                var4_8 = var17_4 != false ? -var8_3 : var6_10 + var8_3;
                                var5_9 = var17_4 != false ? var6_10 + var8_3 : -var8_3;
                                var3_7 = var5_9;
                                var2_6 = var4_8;
                                if (var18_19) {
                                    var2_6 = -var4_8 - var6_10;
                                    var3_7 = -var5_9 - var6_10;
                                }
                                var21_21 = this.mBarBuffers[var12_5];
                                var9_12 = this.mAnimator.getPhaseY();
                                if (!var20_20.isStacked()) {
                                    var11_14 = 0;
                                    break block14;
                                }
                                var22_22 = this.mChart.getTransformer(var20_20.getAxisDependency());
                                var11_14 = 0;
                                var13_15 = 0;
lbl27:
                                // 3 sources
                                do {
                                    if ((float)var13_15 < (float)var20_20.getEntryCount() * this.mAnimator.getPhaseX()) break block15;
lbl29:
                                    // 4 sources
                                    do {
                                        ++var12_5;
                                        continue block0;
                                        break;
                                    } while (true);
                                    break;
                                } while (true);
                            }
                            return;
                        }
                        do {
                            if (!((float)var11_14 < (float)var21_21.buffer.length * this.mAnimator.getPhaseX()) || !this.mViewPortHandler.isInBoundsRight(var5_9 = (var21_21.buffer[var11_14] + var21_21.buffer[var11_14 + 2]) / 2.0f)) ** GOTO lbl29
                            if (this.mViewPortHandler.isInBoundsY(var21_21.buffer[var11_14 + 1]) && this.mViewPortHandler.isInBoundsLeft(var5_9)) {
                                var22_22 = (BarEntry)var20_20.getEntryForIndex(var11_14 / 4);
                                var6_10 = var22_22.getY();
                                var23_23 = var20_20.getValueFormatter();
                                var4_8 = var6_10 >= 0.0f ? var21_21.buffer[var11_14 + 1] + var2_6 : var21_21.buffer[var11_14 + 3] + var3_7;
                                this.drawValue(var1_1, (IValueFormatter)var23_23, var6_10, (Entry)var22_22, var12_5, var5_9, var4_8, var20_20.getValueTextColor(var11_14 / 4));
                            }
                            var11_14 += 4;
                        } while (true);
                    }
                    var23_23 = (BarEntry)var20_20.getEntryForIndex(var13_15);
                    var24_24 = var23_23.getYVals();
                    var10_13 = (var21_21.buffer[var11_14] + var21_21.buffer[var11_14 + 2]) / 2.0f;
                    var16_18 = var20_20.getValueTextColor(var13_15);
                    if (var24_24 != null) break block16;
                    ** while (!this.mViewPortHandler.isInBoundsRight((float)var10_13))
lbl51:
                    // 1 sources
                    if (!this.mViewPortHandler.isInBoundsY(var21_21.buffer[var11_14 + 1]) || !this.mViewPortHandler.isInBoundsLeft(var10_13)) ** GOTO lbl27
                    var25_26 = var20_20.getValueFormatter();
                    var5_9 = var23_23.getY();
                    var6_10 = var21_21.buffer[var11_14 + 1];
                    var4_8 = var23_23.getY() >= 0.0f ? var2_6 : var3_7;
                    this.drawValue(var1_1, var25_26, var5_9, (Entry)var23_23, var12_5, var10_13, var6_10 + var4_8, var16_18);
                    break block17;
                }
                var25_27 = new float[var24_24.length * 2];
                var5_9 = 0.0f;
                var4_8 = -var23_23.getNegativeSum();
                var14_16 = 0;
                break block18;
            }
lbl65:
            // 3 sources
            do {
                var11_14 = var24_24 == null ? (var11_14 += 4) : (var11_14 += var24_24.length * 4);
                ++var13_15;
                ** continue;
                break;
            } while (true);
        }
        for (var15_17 = 0; var15_17 < var25_27.length; var15_17 += 2, ++var14_16) {
            var7_11 = var24_24[var14_16];
            if (var7_11 >= 0.0f) {
                var5_9 = var7_11 = var5_9 + var7_11;
                var6_10 = var4_8;
                var4_8 = var5_9;
            } else {
                var6_10 = var4_8;
                var7_11 = var4_8 - var7_11;
                var4_8 = var6_10;
                var6_10 = var7_11;
                var7_11 = var5_9;
            }
            var25_27[var15_17 + 1] = var4_8 * var9_12;
            var4_8 = var6_10;
            var5_9 = var7_11;
        }
        var22_22.pointValuesToPixel(var25_27);
        var14_16 = 0;
        do {
            if (var14_16 >= var25_27.length) ** GOTO lbl65
            var5_9 = var25_27[var14_16 + 1];
            var4_8 = var24_24[var14_16 / 2] >= 0.0f ? var2_6 : var3_7;
            var4_8 = var5_9 + var4_8;
            if (!this.mViewPortHandler.isInBoundsRight(var10_13)) ** continue;
            if (this.mViewPortHandler.isInBoundsY(var4_8) && this.mViewPortHandler.isInBoundsLeft(var10_13)) {
                this.drawValue(var1_1, var20_20.getValueFormatter(), var24_24[var14_16 / 2], (Entry)var23_23, var12_5, var10_13, var4_8, var16_18);
            }
            var14_16 += 2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new BarBuffer[barData.getDataSetCount()];
        int n = 0;
        while (n < this.mBarBuffers.length) {
            IBarDataSet iBarDataSet = (IBarDataSet)barData.getDataSetByIndex(n);
            BarBuffer[] arrbarBuffer = this.mBarBuffers;
            int n2 = iBarDataSet.getEntryCount();
            int n3 = iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1;
            arrbarBuffer[n] = new BarBuffer(n3 * (n2 * 4), barData.getDataSetCount(), iBarDataSet.isStacked());
            ++n;
        }
        return;
    }

    protected void prepareBarHighlight(float f, float f2, float f3, float f4, Transformer transformer) {
        this.mBarRect.set(f - f4, f2, f + f4, f3);
        transformer.rectToPixelPhase(this.mBarRect, this.mAnimator.getPhaseY());
    }

    protected void setHighlightDrawPos(Highlight highlight, RectF rectF) {
        highlight.setDraw(rectF.centerX(), rectF.top);
    }
}

