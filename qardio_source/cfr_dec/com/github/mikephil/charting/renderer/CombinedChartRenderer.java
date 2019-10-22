/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ScatterDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.BubbleChartRenderer;
import com.github.mikephil.charting.renderer.CandleStickChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.renderer.ScatterChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CombinedChartRenderer
extends DataRenderer {
    protected WeakReference<Chart> mChart;
    protected List<Highlight> mHighlightBuffer;
    protected List<DataRenderer> mRenderers = new ArrayList<DataRenderer>(5);

    public CombinedChartRenderer(CombinedChart combinedChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mHighlightBuffer = new ArrayList<Highlight>();
        this.mChart = new WeakReference<CombinedChart>(combinedChart);
        this.createRenderers();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void createRenderers() {
        this.mRenderers.clear();
        CombinedChart combinedChart = (CombinedChart)this.mChart.get();
        if (combinedChart != null) {
            block7: for (CombinedChart.DrawOrder drawOrder : combinedChart.getDrawOrder()) {
                switch (1.$SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder[drawOrder.ordinal()]) {
                    case 1: {
                        if (combinedChart.getBarData() == null) continue block7;
                        this.mRenderers.add(new BarChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                        continue block7;
                    }
                    case 2: {
                        if (combinedChart.getBubbleData() == null) continue block7;
                        this.mRenderers.add(new BubbleChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                        continue block7;
                    }
                    case 3: {
                        if (combinedChart.getLineData() == null) continue block7;
                        this.mRenderers.add(new LineChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                        continue block7;
                    }
                    case 4: {
                        if (combinedChart.getCandleData() == null) continue block7;
                        this.mRenderers.add(new CandleStickChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                        continue block7;
                    }
                    case 5: {
                        if (combinedChart.getScatterData() == null) continue block7;
                        this.mRenderers.add(new ScatterChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                        continue block7;
                    }
                }
            }
        }
    }

    @Override
    public void drawData(Canvas canvas) {
        Iterator<DataRenderer> iterator = this.mRenderers.iterator();
        while (iterator.hasNext()) {
            iterator.next().drawData(canvas);
        }
    }

    @Override
    public void drawExtras(Canvas canvas) {
        Iterator<DataRenderer> iterator = this.mRenderers.iterator();
        while (iterator.hasNext()) {
            iterator.next().drawExtras(canvas);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        Chart chart = (Chart)this.mChart.get();
        if (chart != null) {
            for (DataRenderer dataRenderer : this.mRenderers) {
                void var6_11;
                Object var6_9 = null;
                if (dataRenderer instanceof BarChartRenderer) {
                    BarData barData = ((BarChartRenderer)dataRenderer).mChart.getBarData();
                } else if (dataRenderer instanceof LineChartRenderer) {
                    LineData lineData = ((LineChartRenderer)dataRenderer).mChart.getLineData();
                } else if (dataRenderer instanceof CandleStickChartRenderer) {
                    CandleData candleData = ((CandleStickChartRenderer)dataRenderer).mChart.getCandleData();
                } else if (dataRenderer instanceof ScatterChartRenderer) {
                    ScatterData scatterData = ((ScatterChartRenderer)dataRenderer).mChart.getScatterData();
                } else if (dataRenderer instanceof BubbleChartRenderer) {
                    BubbleData bubbleData = ((BubbleChartRenderer)dataRenderer).mChart.getBubbleData();
                }
                int n = var6_11 == null ? -1 : ((CombinedData)chart.getData()).getAllData().indexOf(var6_11);
                this.mHighlightBuffer.clear();
                for (Highlight highlight : arrhighlight) {
                    if (highlight.getDataIndex() != n && highlight.getDataIndex() != -1) continue;
                    this.mHighlightBuffer.add(highlight);
                }
                dataRenderer.drawHighlighted(canvas, this.mHighlightBuffer.toArray(new Highlight[this.mHighlightBuffer.size()]));
            }
        }
    }

    @Override
    public void drawValues(Canvas canvas) {
        Iterator<DataRenderer> iterator = this.mRenderers.iterator();
        while (iterator.hasNext()) {
            iterator.next().drawValues(canvas);
        }
    }

    @Override
    public void initBuffers() {
        Iterator<DataRenderer> iterator = this.mRenderers.iterator();
        while (iterator.hasNext()) {
            iterator.next().initBuffers();
        }
    }

}

