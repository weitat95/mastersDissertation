/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.RectF
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.buffer.HorizontalBarBuffer;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class HorizontalBarChartRenderer
extends BarChartRenderer {
    private RectF mBarShadowRectBuffer = new RectF();

    public HorizontalBarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(barDataProvider, chartAnimator, viewPortHandler);
        this.mValuePaint.setTextAlign(Paint.Align.LEFT);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
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
                this.mBarShadowRectBuffer.top = f4 - f3;
                this.mBarShadowRectBuffer.bottom = f4 + f3;
                transformer.rectValueToPixel(this.mBarShadowRectBuffer);
                if (!this.mViewPortHandler.isInBoundsTop(this.mBarShadowRectBuffer.bottom)) continue;
                if (!this.mViewPortHandler.isInBoundsBottom(this.mBarShadowRectBuffer.top)) break;
                this.mBarShadowRectBuffer.left = this.mViewPortHandler.contentLeft();
                this.mBarShadowRectBuffer.right = this.mViewPortHandler.contentRight();
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
        n2 = 0;
        while (n2 < barBuffer.size() && this.mViewPortHandler.isInBoundsTop(barBuffer.buffer[n2 + 3])) {
            if (this.mViewPortHandler.isInBoundsBottom(barBuffer.buffer[n2 + 1])) {
                if (n == 0) {
                    this.mRenderPaint.setColor(iBarDataSet.getColor(n2 / 4));
                }
                canvas.drawRect(barBuffer.buffer[n2], barBuffer.buffer[n2 + 1], barBuffer.buffer[n2 + 2], barBuffer.buffer[n2 + 3], this.mRenderPaint);
                if (bl) {
                    canvas.drawRect(barBuffer.buffer[n2], barBuffer.buffer[n2 + 1], barBuffer.buffer[n2 + 2], barBuffer.buffer[n2 + 3], this.mBarBorderPaint);
                }
            }
            n2 += 4;
        }
        return;
    }

    protected void drawValue(Canvas canvas, String string2, float f, float f2, int n) {
        this.mValuePaint.setColor(n);
        canvas.drawText(string2, f, f2, this.mValuePaint);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void drawValues(Canvas var1_1) {
        block23: {
            block22: {
                block21: {
                    block20: {
                        block19: {
                            if (this.isDrawingValuesAllowed(this.mChart) == false) return;
                            var19_2 = this.mChart.getBarData().getDataSets();
                            var2_3 = Utils.convertDpToPixel(5.0f);
                            var17_4 = this.mChart.isDrawValueAboveBarEnabled();
                            var12_5 = 0;
                            block0 : while (var12_5 < this.mChart.getBarData().getDataSetCount()) {
                                var20_20 = (IBarDataSet)var19_2.get(var12_5);
                                if (!this.shouldDrawValues(var20_20)) ** GOTO lbl23
                                var18_19 = this.mChart.isInverted(var20_20.getAxisDependency());
                                this.applyValueTextStyle(var20_20);
                                var7_10 = (float)Utils.calcTextHeight(this.mValuePaint, "10") / 2.0f;
                                var21_21 = var20_20.getValueFormatter();
                                var22_22 = this.mBarBuffers[var12_5];
                                var8_11 = this.mAnimator.getPhaseY();
                                if (!var20_20.isStacked()) {
                                    var11_14 = 0;
                                    break block19;
                                }
                                var23_23 = this.mChart.getTransformer(var20_20.getAxisDependency());
                                var11_14 = 0;
                                var13_15 = 0;
lbl21:
                                // 3 sources
                                do {
                                    if ((float)var13_15 < (float)var20_20.getEntryCount() * this.mAnimator.getPhaseX()) break block20;
lbl23:
                                    // 5 sources
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
                            if (!((float)var11_14 < (float)var22_22.buffer.length * this.mAnimator.getPhaseX())) ** GOTO lbl23
                            var8_11 = (var22_22.buffer[var11_14 + 1] + var22_22.buffer[var11_14 + 3]) / 2.0f;
                            if (!this.mViewPortHandler.isInBoundsTop(var22_22.buffer[var11_14 + 1])) ** GOTO lbl23
                            if (this.mViewPortHandler.isInBoundsX(var22_22.buffer[var11_14]) && this.mViewPortHandler.isInBoundsBottom(var22_22.buffer[var11_14 + 1])) {
                                var23_23 = (BarEntry)var20_20.getEntryForIndex(var11_14 / 4);
                                var9_12 = var23_23.getY();
                                var23_23 = var21_21.getFormattedValue(var9_12, (Entry)var23_23, var12_5, this.mViewPortHandler);
                                var10_13 = Utils.calcTextWidth(this.mValuePaint, (String)var23_23);
                                var3_6 = var17_4 != false ? var2_3 : -(var10_13 + var2_3);
                                var5_8 = var17_4 != false ? -(var10_13 + var2_3) : var2_3;
                                var6_9 = var5_8;
                                var4_7 = var3_6;
                                if (var18_19) {
                                    var4_7 = -var3_6 - var10_13;
                                    var6_9 = -var5_8 - var10_13;
                                }
                                var3_6 = var22_22.buffer[var11_14 + 2];
                                if (!(var9_12 >= 0.0f)) {
                                    var4_7 = var6_9;
                                }
                                this.drawValue(var1_1, (String)var23_23, var3_6 + var4_7, var8_11 + var7_10, var20_20.getValueTextColor(var11_14 / 2));
                            }
                            var11_14 += 4;
                        } while (true);
                    }
                    var24_24 = (BarEntry)var20_20.getEntryForIndex(var13_15);
                    var16_18 = var20_20.getValueTextColor(var13_15);
                    var25_25 = var24_24.getYVals();
                    if (var25_25 != null) break block21;
                    ** while (!this.mViewPortHandler.isInBoundsTop((float)var22_22.buffer[var11_14 + 1]))
lbl56:
                    // 1 sources
                    if (!this.mViewPortHandler.isInBoundsX(var22_22.buffer[var11_14]) || !this.mViewPortHandler.isInBoundsBottom(var22_22.buffer[var11_14 + 1])) ** GOTO lbl21
                    var26_27 = var21_21.getFormattedValue(var24_24.getY(), var24_24, var12_5, this.mViewPortHandler);
                    var9_12 = Utils.calcTextWidth(this.mValuePaint, var26_27);
                    var3_6 = var17_4 != false ? var2_3 : -(var9_12 + var2_3);
                    var5_8 = var17_4 != false ? -(var9_12 + var2_3) : var2_3;
                    var6_9 = var5_8;
                    var4_7 = var3_6;
                    if (var18_19) {
                        var4_7 = -var3_6 - var9_12;
                        var6_9 = -var5_8 - var9_12;
                    }
                    var3_6 = var22_22.buffer[var11_14 + 2];
                    if (!(var24_24.getY() >= 0.0f)) {
                        var4_7 = var6_9;
                    }
                    this.drawValue(var1_1, var26_27, var3_6 + var4_7, var22_22.buffer[var11_14 + 1] + var7_10, var16_18);
                    break block22;
                }
                var26_28 = new float[var25_25.length * 2];
                var4_7 = 0.0f;
                var3_6 = -var24_24.getNegativeSum();
                var14_16 = 0;
                break block23;
            }
lbl78:
            // 3 sources
            do {
                var11_14 = var25_25 == null ? (var11_14 += 4) : (var11_14 += var25_25.length * 4);
                ++var13_15;
                ** continue;
                break;
            } while (true);
        }
        for (var15_17 = 0; var15_17 < var26_28.length; var15_17 += 2, ++var14_16) {
            var6_9 = var25_25[var14_16];
            if (var6_9 >= 0.0f) {
                var5_8 = var4_7 += var6_9;
            } else {
                var5_8 = var3_6;
                var3_6 -= var6_9;
            }
            var26_28[var15_17] = var5_8 * var8_11;
        }
        var23_23.pointValuesToPixel(var26_28);
        var14_16 = 0;
        do {
            if (var14_16 >= var26_28.length) ** GOTO lbl78
            var9_12 = var25_25[var14_16 / 2];
            var27_29 = var21_21.getFormattedValue(var9_12, var24_24, var12_5, this.mViewPortHandler);
            var10_13 = Utils.calcTextWidth(this.mValuePaint, var27_29);
            var3_6 = var17_4 != false ? var2_3 : -(var10_13 + var2_3);
            var5_8 = var17_4 != false ? -(var10_13 + var2_3) : var2_3;
            var6_9 = var5_8;
            var4_7 = var3_6;
            if (var18_19) {
                var4_7 = -var3_6 - var10_13;
                var6_9 = -var5_8 - var10_13;
            }
            var3_6 = var26_28[var14_16];
            if (!(var9_12 >= 0.0f)) {
                var4_7 = var6_9;
            }
            var3_6 += var4_7;
            if (!this.mViewPortHandler.isInBoundsTop(var4_7 = (var22_22.buffer[var11_14 + 1] + var22_22.buffer[var11_14 + 3]) / 2.0f)) ** continue;
            if (this.mViewPortHandler.isInBoundsX(var3_6) && this.mViewPortHandler.isInBoundsBottom(var4_7)) {
                this.drawValue(var1_1, var27_29, var3_6, var4_7 + var7_10, var16_18);
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
        this.mBarBuffers = new HorizontalBarBuffer[barData.getDataSetCount()];
        int n = 0;
        while (n < this.mBarBuffers.length) {
            IBarDataSet iBarDataSet = (IBarDataSet)barData.getDataSetByIndex(n);
            BarBuffer[] arrbarBuffer = this.mBarBuffers;
            int n2 = iBarDataSet.getEntryCount();
            int n3 = iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1;
            arrbarBuffer[n] = new HorizontalBarBuffer(n3 * (n2 * 4), barData.getDataSetCount(), iBarDataSet.isStacked());
            ++n;
        }
        return;
    }

    @Override
    protected boolean isDrawingValuesAllowed(ChartInterface chartInterface) {
        return (float)chartInterface.getData().getEntryCount() < (float)chartInterface.getMaxVisibleCount() * this.mViewPortHandler.getScaleY();
    }

    @Override
    protected void prepareBarHighlight(float f, float f2, float f3, float f4, Transformer transformer) {
        this.mBarRect.set(f2, f - f4, f3, f + f4);
        transformer.rectToPixelPhaseHorizontal(this.mBarRect, this.mAnimator.getPhaseY());
    }

    @Override
    protected void setHighlightDrawPos(Highlight highlight, RectF rectF) {
        highlight.setDraw(rectF.centerY(), rectF.right);
    }
}

