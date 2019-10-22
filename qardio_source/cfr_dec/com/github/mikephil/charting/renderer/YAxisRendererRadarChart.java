/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererRadarChart
extends YAxisRenderer {
    private RadarChart mChart;
    private Path mRenderLimitLinesPathBuffer = new Path();

    public YAxisRendererRadarChart(ViewPortHandler viewPortHandler, YAxis yAxis, RadarChart radarChart) {
        super(viewPortHandler, yAxis, null);
        this.mChart = radarChart;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void computeAxisValues(float f, float f2) {
        boolean bl;
        double d;
        int n = this.mAxis.getLabelCount();
        double d2 = Math.abs(f2 - f);
        if (n == 0 || d2 <= 0.0 || Double.isInfinite(d2)) {
            this.mAxis.mEntries = new float[0];
            this.mAxis.mCenteredEntries = new float[0];
            this.mAxis.mEntryCount = 0;
            return;
        }
        double d3 = d = (double)Utils.roundToNextSignificant(d2 / (double)n);
        if (this.mAxis.isGranularityEnabled()) {
            d3 = d;
            if (d < (double)this.mAxis.getGranularity()) {
                d3 = this.mAxis.getGranularity();
            }
        }
        d = Utils.roundToNextSignificant(Math.pow(10.0, (int)Math.log10(d3)));
        double d4 = d3;
        if ((int)(d3 / d) > 5) {
            d4 = Math.floor(10.0 * d);
        }
        int n2 = (bl = this.mAxis.isCenterAxisLabelsEnabled()) ? 1 : 0;
        if (this.mAxis.isForceLabelsEnabled()) {
            f2 = (float)d2 / (float)(n - 1);
            this.mAxis.mEntryCount = n;
            if (this.mAxis.mEntries.length < n) {
                this.mAxis.mEntries = new float[n];
            }
            for (n2 = 0; n2 < n; f += f2, ++n2) {
                this.mAxis.mEntries[n2] = f;
            }
            n2 = n;
        } else {
            int n3;
            d = d4 == 0.0 ? 0.0 : Math.ceil((double)f / d4) * d4;
            d3 = d;
            if (bl) {
                d3 = d - d4;
            }
            d = d4 == 0.0 ? 0.0 : Utils.nextUp(Math.floor((double)f2 / d4) * d4);
            n = n2;
            if (d4 != 0.0) {
                d2 = d3;
                do {
                    n = n2++;
                    if (!(d2 <= d)) break;
                    d2 += d4;
                } while (true);
            }
            this.mAxis.mEntryCount = n3 = n + 1;
            if (this.mAxis.mEntries.length < n3) {
                this.mAxis.mEntries = new float[n3];
            }
            n = 0;
            do {
                n2 = n3;
                if (n >= n3) break;
                d = d3;
                if (d3 == 0.0) {
                    d = 0.0;
                }
                this.mAxis.mEntries[n] = (float)d;
                d3 = d + d4;
                ++n;
            } while (true);
        }
        this.mAxis.mDecimals = d4 < 1.0 ? (int)Math.ceil(-Math.log10(d4)) : 0;
        if (bl) {
            if (this.mAxis.mCenteredEntries.length < n2) {
                this.mAxis.mCenteredEntries = new float[n2];
            }
            f = (this.mAxis.mEntries[1] - this.mAxis.mEntries[0]) / 2.0f;
            for (n = 0; n < n2; ++n) {
                this.mAxis.mCenteredEntries[n] = this.mAxis.mEntries[n] + f;
            }
        }
        this.mAxis.mAxisMinimum = this.mAxis.mEntries[0];
        this.mAxis.mAxisMaximum = this.mAxis.mEntries[n2 - 1];
        this.mAxis.mAxisRange = Math.abs(this.mAxis.mAxisMaximum - this.mAxis.mAxisMinimum);
    }

    @Override
    public void renderAxisLabels(Canvas canvas) {
        if (!this.mYAxis.isEnabled() || !this.mYAxis.isDrawLabelsEnabled()) {
            return;
        }
        this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
        MPPointF mPPointF = this.mChart.getCenterOffsets();
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        float f = this.mChart.getFactor();
        int n = this.mYAxis.mEntryCount;
        int n2 = 0;
        do {
            if (n2 >= n || n2 == n - 1 && !this.mYAxis.isDrawTopYLabelEntryEnabled()) {
                MPPointF.recycleInstance(mPPointF);
                MPPointF.recycleInstance(mPPointF2);
                return;
            }
            Utils.getPosition(mPPointF, (this.mYAxis.mEntries[n2] - this.mYAxis.mAxisMinimum) * f, this.mChart.getRotationAngle(), mPPointF2);
            canvas.drawText(this.mYAxis.getFormattedLabel(n2), mPPointF2.x + 10.0f, mPPointF2.y, this.mAxisLabelPaint);
            ++n2;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void renderLimitLines(Canvas var1_1) {
        var8_2 = this.mYAxis.getLimitLines();
        if (var8_2 == null) {
            return;
        }
        var2_3 = this.mChart.getSliceAngle();
        var3_4 = this.mChart.getFactor();
        var9_5 = this.mChart.getCenterOffsets();
        var10_6 = MPPointF.getInstance(0.0f, 0.0f);
        var6_7 = 0;
        block0: do {
            if (var6_7 >= var8_2.size()) {
                MPPointF.recycleInstance(var9_5);
                MPPointF.recycleInstance(var10_6);
                return;
            }
            var11_11 = var8_2.get(var6_7);
            if (var11_11.isEnabled()) {
                this.mLimitLinePaint.setColor(var11_11.getLineColor());
                this.mLimitLinePaint.setPathEffect((PathEffect)var11_11.getDashPathEffect());
                this.mLimitLinePaint.setStrokeWidth(var11_11.getLineWidth());
                var4_8 = var11_11.getLimit();
                var5_9 = this.mChart.getYChartMin();
                var11_11 = this.mRenderLimitLinesPathBuffer;
                var11_11.reset();
                break;
            }
lbl25:
            // 3 sources
            do {
                ++var6_7;
                continue block0;
                break;
            } while (true);
            break;
        } while (true);
        for (var7_10 = 0; var7_10 < ((IRadarDataSet)((RadarData)this.mChart.getData()).getMaxEntryCountSet()).getEntryCount(); ++var7_10) {
            Utils.getPosition(var9_5, (var4_8 - var5_9) * var3_4, (float)var7_10 * var2_3 + this.mChart.getRotationAngle(), var10_6);
            if (var7_10 == 0) {
                var11_11.moveTo(var10_6.x, var10_6.y);
                continue;
            }
            var11_11.lineTo(var10_6.x, var10_6.y);
        }
        var11_11.close();
        var1_1.drawPath((Path)var11_11, this.mLimitLinePaint);
        ** while (true)
    }
}

