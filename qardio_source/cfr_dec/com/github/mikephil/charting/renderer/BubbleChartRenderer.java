/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class BubbleChartRenderer
extends BarLineScatterCandleBubbleRenderer {
    private float[] _hsvBuffer;
    protected BubbleDataProvider mChart;
    private float[] pointBuffer;
    private float[] sizeBuffer = new float[4];

    public BubbleChartRenderer(BubbleDataProvider bubbleDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.pointBuffer = new float[2];
        this._hsvBuffer = new float[3];
        this.mChart = bubbleDataProvider;
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(1.5f));
    }

    @Override
    public void drawData(Canvas canvas) {
        for (IBubbleDataSet iBubbleDataSet : this.mChart.getBubbleData().getDataSets()) {
            if (!iBubbleDataSet.isVisible()) continue;
            this.drawDataSet(canvas, iBubbleDataSet);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawDataSet(Canvas canvas, IBubbleDataSet iBubbleDataSet) {
        Transformer transformer = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency());
        float f = this.mAnimator.getPhaseY();
        this.mXBounds.set(this.mChart, iBubbleDataSet);
        this.sizeBuffer[0] = 0.0f;
        this.sizeBuffer[2] = 1.0f;
        transformer.pointValuesToPixel(this.sizeBuffer);
        boolean bl = iBubbleDataSet.isNormalizeSizeEnabled();
        float f2 = Math.abs(this.sizeBuffer[2] - this.sizeBuffer[0]);
        f2 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), f2);
        for (int i = this.mXBounds.min; i <= this.mXBounds.range + this.mXBounds.min; ++i) {
            BubbleEntry bubbleEntry = (BubbleEntry)iBubbleDataSet.getEntryForIndex(i);
            this.pointBuffer[0] = bubbleEntry.getX();
            this.pointBuffer[1] = bubbleEntry.getY() * f;
            transformer.pointValuesToPixel(this.pointBuffer);
            float f3 = this.getShapeSize(bubbleEntry.getSize(), iBubbleDataSet.getMaxSize(), f2, bl) / 2.0f;
            if (!this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + f3) || !this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - f3) || !this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + f3)) continue;
            if (!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - f3)) break;
            int n = iBubbleDataSet.getColor((int)bubbleEntry.getX());
            this.mRenderPaint.setColor(n);
            canvas.drawCircle(this.pointBuffer[0], this.pointBuffer[1], f3, this.mRenderPaint);
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
        BubbleData bubbleData = this.mChart.getBubbleData();
        float f = this.mAnimator.getPhaseY();
        for (Highlight highlight : arrhighlight) {
            BubbleEntry bubbleEntry;
            IBubbleDataSet iBubbleDataSet = (IBubbleDataSet)bubbleData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iBubbleDataSet == null || !iBubbleDataSet.isHighlightEnabled() || (bubbleEntry = (BubbleEntry)iBubbleDataSet.getEntryForXValue(highlight.getX(), highlight.getY())).getY() != highlight.getY() || !this.isInBoundsX(bubbleEntry, iBubbleDataSet)) continue;
            Transformer transformer = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency());
            this.sizeBuffer[0] = 0.0f;
            this.sizeBuffer[2] = 1.0f;
            transformer.pointValuesToPixel(this.sizeBuffer);
            boolean bl = iBubbleDataSet.isNormalizeSizeEnabled();
            float f2 = Math.abs(this.sizeBuffer[2] - this.sizeBuffer[0]);
            f2 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), f2);
            this.pointBuffer[0] = bubbleEntry.getX();
            this.pointBuffer[1] = bubbleEntry.getY() * f;
            transformer.pointValuesToPixel(this.pointBuffer);
            highlight.setDraw(this.pointBuffer[0], this.pointBuffer[1]);
            f2 = this.getShapeSize(bubbleEntry.getSize(), iBubbleDataSet.getMaxSize(), f2, bl) / 2.0f;
            if (!this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + f2) || !this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - f2) || !this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + f2)) continue;
            if (!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - f2)) break;
            int n = iBubbleDataSet.getColor((int)bubbleEntry.getX());
            Color.RGBToHSV((int)Color.red((int)n), (int)Color.green((int)n), (int)Color.blue((int)n), (float[])this._hsvBuffer);
            float[] arrf = this._hsvBuffer;
            arrf[2] = arrf[2] * 0.5f;
            n = Color.HSVToColor((int)Color.alpha((int)n), (float[])this._hsvBuffer);
            this.mHighlightPaint.setColor(n);
            this.mHighlightPaint.setStrokeWidth(iBubbleDataSet.getHighlightCircleWidth());
            canvas.drawCircle(this.pointBuffer[0], this.pointBuffer[1], f2, this.mHighlightPaint);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void drawValues(Canvas var1_1) {
        var9_2 = this.mChart.getBubbleData();
        if (var9_2 == null) {
            return;
        }
        if (this.isDrawingValuesAllowed(this.mChart) == false) return;
        var9_2 = var9_2.getDataSets();
        var4_3 = Utils.calcTextHeight(this.mValuePaint, "1");
        var6_4 = 0;
        block0: do {
            if (var6_4 >= var9_2.size()) return;
            var10_10 = (IBubbleDataSet)var9_2.get(var6_4);
            if (!this.shouldDrawValues(var10_10)) ** GOTO lbl22
            this.applyValueTextStyle(var10_10);
            var2_5 = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
            var3_6 = this.mAnimator.getPhaseY();
            this.mXBounds.set(this.mChart, var10_10);
            var11_11 = this.mChart.getTransformer(var10_10.getAxisDependency()).generateTransformedValuesBubble(var10_10, var3_6, this.mXBounds.min, this.mXBounds.max);
            if (var2_5 == 1.0f) {
                var2_5 = var3_6;
            }
            var7_8 = 0;
            do {
                block9: {
                    if (var7_8 < var11_11.length) break block9;
lbl22:
                    // 3 sources
                    do {
                        ++var6_4;
                        continue block0;
                        break;
                    } while (true);
                }
                var8_9 = var10_10.getValueTextColor(var7_8 / 2 + this.mXBounds.min);
                var8_9 = Color.argb((int)Math.round(255.0f * var2_5), (int)Color.red((int)var8_9), (int)Color.green((int)var8_9), (int)Color.blue((int)var8_9));
                var3_6 = var11_11[var7_8];
                var5_7 = var11_11[var7_8 + 1];
                if (!this.mViewPortHandler.isInBoundsRight(var3_6)) ** continue;
                if (this.mViewPortHandler.isInBoundsLeft(var3_6) && this.mViewPortHandler.isInBoundsY(var5_7)) {
                    var12_12 = (BubbleEntry)var10_10.getEntryForIndex(var7_8 / 2 + this.mXBounds.min);
                    this.drawValue(var1_1, var10_10.getValueFormatter(), var12_12.getSize(), var12_12, var6_4, var3_6, var5_7 + 0.5f * var4_3, var8_9);
                }
                var7_8 += 2;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected float getShapeSize(float f, float f2, float f3, boolean bl) {
        block4: {
            block3: {
                if (!bl) break block3;
                if (f2 != 0.0f) break block4;
                f = 1.0f;
            }
            do {
                return f3 * f;
                break;
            } while (true);
        }
        f = (float)Math.sqrt(f / f2);
        return f3 * f;
    }

    @Override
    public void initBuffers() {
    }
}

