/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.RectF
 */
package com.getqardio.android.mvp.common.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class RoundedBarchart
extends BarChartRenderer {
    private RectF mBarShadowRectBuffer = new RectF();

    public RoundedBarchart(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(barDataProvider, chartAnimator, viewPortHandler);
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
                this.mBarShadowRectBuffer.left = f4 - f3;
                this.mBarShadowRectBuffer.right = f4 + f3;
                transformer.rectValueToPixel(this.mBarShadowRectBuffer);
                if (!this.mViewPortHandler.isInBoundsLeft(this.mBarShadowRectBuffer.right)) continue;
                if (!this.mViewPortHandler.isInBoundsRight(this.mBarShadowRectBuffer.left)) break;
                this.mBarShadowRectBuffer.top = this.mViewPortHandler.contentTop();
                this.mBarShadowRectBuffer.bottom = this.mViewPortHandler.contentBottom();
                canvas.drawRoundRect(this.mBarShadowRectBuffer, iBarDataSet.getBarBorderWidth(), iBarDataSet.getBarBorderWidth(), this.mShadowPaint);
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
            canvas.drawRoundRect(new RectF(barBuffer.buffer[n2], barBuffer.buffer[n2 + 1], barBuffer.buffer[n2 + 2], barBuffer.buffer[n2 + 3]), 30.0f, 30.0f, this.mRenderPaint);
            if (!bl) continue;
            canvas.drawRoundRect(new RectF(barBuffer.buffer[n2], barBuffer.buffer[n2 + 1], barBuffer.buffer[n2 + 2], barBuffer.buffer[n2 + 3]), 30.0f, 30.0f, this.mBarBorderPaint);
        }
    }
}

