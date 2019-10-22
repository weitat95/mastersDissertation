/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CombinedData
extends BarLineScatterCandleBubbleData<IBarLineScatterCandleBubbleDataSet<? extends Entry>> {
    private BarData mBarData;
    private BubbleData mBubbleData;
    private CandleData mCandleData;
    private LineData mLineData;
    private ScatterData mScatterData;

    @Override
    public void calcMinMax() {
        if (this.mDataSets == null) {
            this.mDataSets = new ArrayList();
        }
        this.mDataSets.clear();
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = Float.MAX_VALUE;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = Float.MAX_VALUE;
        for (ChartData chartData : this.getAllData()) {
            chartData.calcMinMax();
            List list = chartData.getDataSets();
            this.mDataSets.addAll(list);
            if (chartData.getYMax() > this.mYMax) {
                this.mYMax = chartData.getYMax();
            }
            if (chartData.getYMin() < this.mYMin) {
                this.mYMin = chartData.getYMin();
            }
            if (chartData.getXMax() > this.mXMax) {
                this.mXMax = chartData.getXMax();
            }
            if (chartData.getXMin() < this.mXMin) {
                this.mXMin = chartData.getXMin();
            }
            if (chartData.mLeftAxisMax > this.mLeftAxisMax) {
                this.mLeftAxisMax = chartData.mLeftAxisMax;
            }
            if (chartData.mLeftAxisMin < this.mLeftAxisMin) {
                this.mLeftAxisMin = chartData.mLeftAxisMin;
            }
            if (chartData.mRightAxisMax > this.mRightAxisMax) {
                this.mRightAxisMax = chartData.mRightAxisMax;
            }
            if (!(chartData.mRightAxisMin < this.mRightAxisMin)) continue;
            this.mRightAxisMin = chartData.mRightAxisMin;
        }
    }

    public List<BarLineScatterCandleBubbleData> getAllData() {
        ArrayList<BarLineScatterCandleBubbleData> arrayList = new ArrayList<BarLineScatterCandleBubbleData>();
        if (this.mLineData != null) {
            arrayList.add(this.mLineData);
        }
        if (this.mBarData != null) {
            arrayList.add(this.mBarData);
        }
        if (this.mScatterData != null) {
            arrayList.add(this.mScatterData);
        }
        if (this.mCandleData != null) {
            arrayList.add(this.mCandleData);
        }
        if (this.mBubbleData != null) {
            arrayList.add(this.mBubbleData);
        }
        return arrayList;
    }

    public BarData getBarData() {
        return this.mBarData;
    }

    public BubbleData getBubbleData() {
        return this.mBubbleData;
    }

    public CandleData getCandleData() {
        return this.mCandleData;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Entry getEntryForHighlight(Highlight highlight) {
        Entry entry;
        Object object = this.getAllData();
        if (highlight.getDataIndex() >= object.size()) {
            return null;
        }
        object = object.get(highlight.getDataIndex());
        if (highlight.getDataSetIndex() >= ((ChartData)object).getDataSetCount()) {
            return null;
        }
        Iterator iterator = ((ChartData)object).getDataSetByIndex(highlight.getDataSetIndex()).getEntriesForXValue(highlight.getX()).iterator();
        do {
            if (!iterator.hasNext()) return null;
            entry = (Entry)iterator.next();
            object = entry;
            if (entry.getY() == highlight.getY()) return object;
        } while (!Float.isNaN(highlight.getY()));
        return entry;
    }

    public LineData getLineData() {
        return this.mLineData;
    }

    public ScatterData getScatterData() {
        return this.mScatterData;
    }

    @Override
    public void notifyDataChanged() {
        if (this.mLineData != null) {
            this.mLineData.notifyDataChanged();
        }
        if (this.mBarData != null) {
            this.mBarData.notifyDataChanged();
        }
        if (this.mCandleData != null) {
            this.mCandleData.notifyDataChanged();
        }
        if (this.mScatterData != null) {
            this.mScatterData.notifyDataChanged();
        }
        if (this.mBubbleData != null) {
            this.mBubbleData.notifyDataChanged();
        }
        this.calcMinMax();
    }
}

