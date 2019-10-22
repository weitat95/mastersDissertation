/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.util.Log
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.dataprovider.ScatterDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.renderer.LineScatterCandleRadarRenderer;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class ScatterChartRenderer
extends LineScatterCandleRadarRenderer {
    protected ScatterDataProvider mChart;
    float[] mPixelBuffer = new float[2];

    public ScatterChartRenderer(ScatterDataProvider scatterDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = scatterDataProvider;
    }

    @Override
    public void drawData(Canvas canvas) {
        for (IScatterDataSet iScatterDataSet : this.mChart.getScatterData().getDataSets()) {
            if (!iScatterDataSet.isVisible()) continue;
            this.drawDataSet(canvas, iScatterDataSet);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void drawDataSet(Canvas canvas, IScatterDataSet iScatterDataSet) {
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        Transformer transformer = this.mChart.getTransformer(iScatterDataSet.getAxisDependency());
        float f = this.mAnimator.getPhaseY();
        IShapeRenderer iShapeRenderer = iScatterDataSet.getShapeRenderer();
        if (iShapeRenderer == null) {
            Log.i((String)"MISSING", (String)"There's no IShapeRenderer specified for ScatterDataSet");
            return;
        }
        int n = (int)Math.min(Math.ceil((float)iScatterDataSet.getEntryCount() * this.mAnimator.getPhaseX()), (double)iScatterDataSet.getEntryCount());
        int n2 = 0;
        while (n2 < n) {
            Object t = iScatterDataSet.getEntryForIndex(n2);
            this.mPixelBuffer[0] = ((Entry)t).getX();
            this.mPixelBuffer[1] = ((BaseEntry)t).getY() * f;
            transformer.pointValuesToPixel(this.mPixelBuffer);
            if (!viewPortHandler.isInBoundsRight(this.mPixelBuffer[0])) return;
            if (viewPortHandler.isInBoundsLeft(this.mPixelBuffer[0]) && viewPortHandler.isInBoundsY(this.mPixelBuffer[1])) {
                this.mRenderPaint.setColor(iScatterDataSet.getColor(n2 / 2));
                iShapeRenderer.renderShape(canvas, iScatterDataSet, this.mViewPortHandler, this.mPixelBuffer[0], this.mPixelBuffer[1], this.mRenderPaint);
            }
            ++n2;
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
        ScatterData scatterData = this.mChart.getScatterData();
        int n = arrhighlight.length;
        int n2 = 0;
        while (n2 < n) {
            Object t;
            Highlight highlight = arrhighlight[n2];
            IScatterDataSet iScatterDataSet = (IScatterDataSet)scatterData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iScatterDataSet != null && iScatterDataSet.isHighlightEnabled() && this.isInBoundsX((Entry)(t = iScatterDataSet.getEntryForXValue(highlight.getX(), highlight.getY())), iScatterDataSet)) {
                MPPointD mPPointD = this.mChart.getTransformer(iScatterDataSet.getAxisDependency()).getPixelForValues(((Entry)t).getX(), ((BaseEntry)t).getY() * this.mAnimator.getPhaseY());
                highlight.setDraw((float)mPPointD.x, (float)mPPointD.y);
                this.drawHighlightLines(canvas, (float)mPPointD.x, (float)mPPointD.y, iScatterDataSet);
            }
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void drawValues(Canvas canvas) {
        if (this.isDrawingValuesAllowed(this.mChart)) {
            List list = this.mChart.getScatterData().getDataSets();
            block0: for (int i = 0; i < this.mChart.getScatterData().getDataSetCount(); ++i) {
                IScatterDataSet iScatterDataSet = (IScatterDataSet)list.get(i);
                if (!this.shouldDrawValues(iScatterDataSet)) continue;
                this.applyValueTextStyle(iScatterDataSet);
                this.mXBounds.set(this.mChart, iScatterDataSet);
                float[] arrf = this.mChart.getTransformer(iScatterDataSet.getAxisDependency()).generateTransformedValuesScatter(iScatterDataSet, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
                float f = Utils.convertDpToPixel(iScatterDataSet.getScatterShapeSize());
                int n = 0;
                do {
                    if (n >= arrf.length || !this.mViewPortHandler.isInBoundsRight(arrf[n])) {
                        continue block0;
                    }
                    if (this.mViewPortHandler.isInBoundsLeft(arrf[n]) && this.mViewPortHandler.isInBoundsY(arrf[n + 1])) {
                        Object t = iScatterDataSet.getEntryForIndex(n / 2 + this.mXBounds.min);
                        this.drawValue(canvas, iScatterDataSet.getValueFormatter(), ((BaseEntry)t).getY(), (Entry)t, i, arrf[n], arrf[n + 1] - f, iScatterDataSet.getValueTextColor(n / 2 + this.mXBounds.min));
                    }
                    n += 2;
                } while (true);
            }
        }
    }

    @Override
    public void initBuffers() {
    }
}

