/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChartHighlighter<T extends BarLineScatterCandleBubbleDataProvider>
implements IHighlighter {
    protected T mChart;
    protected List<Highlight> mHighlightBuffer = new ArrayList<Highlight>();

    public ChartHighlighter(T t) {
        this.mChart = t;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected List<Highlight> buildHighlights(IDataSet iDataSet, int n, float f, DataSet.Rounding object) {
        ArrayList<Highlight> arrayList = new ArrayList<Highlight>();
        Object object2 = iDataSet.getEntriesForXValue(f);
        List list2 = object2;
        if (object2.size() == 0) {
            object = iDataSet.getEntryForXValue(f, Float.NaN, (DataSet.Rounding)((Object)object));
            list2 = object2;
            if (object != null) {
                list2 = iDataSet.getEntriesForXValue(((Entry)object).getX());
            }
        }
        if (list2.size() != 0) {
            for (List list2 : list2) {
                object2 = this.mChart.getTransformer(iDataSet.getAxisDependency()).getPixelForValues(((Entry)((Object)list2)).getX(), ((BaseEntry)((Object)list2)).getY());
                arrayList.add(new Highlight(((Entry)((Object)list2)).getX(), ((BaseEntry)((Object)list2)).getY(), (float)((MPPointD)object2).x, (float)((MPPointD)object2).y, n, iDataSet.getAxisDependency()));
            }
        }
        return arrayList;
    }

    public Highlight getClosestHighlightByPixel(List<Highlight> list, float f, float f2, YAxis.AxisDependency axisDependency, float f3) {
        Highlight highlight = null;
        for (int i = 0; i < list.size(); ++i) {
            float f4;
            Highlight highlight2;
            block6: {
                Highlight highlight3;
                block5: {
                    highlight3 = list.get(i);
                    if (axisDependency == null) break block5;
                    highlight2 = highlight;
                    f4 = f3;
                    if (highlight3.getAxis() != axisDependency) break block6;
                }
                float f5 = this.getDistance(f, f2, highlight3.getXPx(), highlight3.getYPx());
                highlight2 = highlight;
                f4 = f3;
                if (f5 < f3) {
                    highlight2 = highlight3;
                    f4 = f5;
                }
            }
            highlight = highlight2;
            f3 = f4;
        }
        return highlight;
    }

    protected BarLineScatterCandleBubbleData getData() {
        return this.mChart.getData();
    }

    protected float getDistance(float f, float f2, float f3, float f4) {
        return (float)Math.hypot(f - f3, f2 - f4);
    }

    @Override
    public Highlight getHighlight(float f, float f2) {
        MPPointD mPPointD = this.getValsForTouch(f, f2);
        float f3 = (float)mPPointD.x;
        MPPointD.recycleInstance(mPPointD);
        return this.getHighlightForX(f3, f, f2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Highlight getHighlightForX(float f, float f2, float f3) {
        YAxis.AxisDependency axisDependency;
        List<Highlight> list = this.getHighlightsAtXValue(f, f2, f3);
        if (list.isEmpty()) {
            return null;
        }
        if (this.getMinimumDistance(list, f3, YAxis.AxisDependency.LEFT) < this.getMinimumDistance(list, f3, YAxis.AxisDependency.RIGHT)) {
            axisDependency = YAxis.AxisDependency.LEFT;
            do {
                return this.getClosestHighlightByPixel(list, f2, f3, axisDependency, this.mChart.getMaxHighlightDistance());
                break;
            } while (true);
        }
        axisDependency = YAxis.AxisDependency.RIGHT;
        return this.getClosestHighlightByPixel(list, f2, f3, axisDependency, this.mChart.getMaxHighlightDistance());
    }

    protected float getHighlightPos(Highlight highlight) {
        return highlight.getYPx();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected List<Highlight> getHighlightsAtXValue(float f, float f2, float f3) {
        this.mHighlightBuffer.clear();
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData = this.getData();
        if (barLineScatterCandleBubbleData == null) {
            return this.mHighlightBuffer;
        }
        int n = 0;
        int n2 = barLineScatterCandleBubbleData.getDataSetCount();
        while (n < n2) {
            Object t = barLineScatterCandleBubbleData.getDataSetByIndex(n);
            if (t.isHighlightEnabled()) {
                this.mHighlightBuffer.addAll(this.buildHighlights((IDataSet)t, n, f, DataSet.Rounding.CLOSEST));
            }
            ++n;
        }
        return this.mHighlightBuffer;
    }

    protected float getMinimumDistance(List<Highlight> list, float f, YAxis.AxisDependency axisDependency) {
        float f2 = Float.MAX_VALUE;
        for (int i = 0; i < list.size(); ++i) {
            Highlight highlight = list.get(i);
            float f3 = f2;
            if (highlight.getAxis() == axisDependency) {
                float f4 = Math.abs(this.getHighlightPos(highlight) - f);
                f3 = f2;
                if (f4 < f2) {
                    f3 = f4;
                }
            }
            f2 = f3;
        }
        return f2;
    }

    protected MPPointD getValsForTouch(float f, float f2) {
        return this.mChart.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(f, f2);
    }
}

