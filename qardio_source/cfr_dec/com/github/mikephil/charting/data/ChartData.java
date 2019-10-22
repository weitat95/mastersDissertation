/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ChartData<T extends IDataSet<? extends Entry>> {
    protected List<T> mDataSets;
    protected float mLeftAxisMax = -3.4028235E38f;
    protected float mLeftAxisMin = Float.MAX_VALUE;
    protected float mRightAxisMax = -3.4028235E38f;
    protected float mRightAxisMin = Float.MAX_VALUE;
    protected float mXMax = -3.4028235E38f;
    protected float mXMin = Float.MAX_VALUE;
    protected float mYMax = -3.4028235E38f;
    protected float mYMin = Float.MAX_VALUE;

    public ChartData() {
        this.mDataSets = new ArrayList<T>();
    }

    public ChartData(T ... arrT) {
        this.mDataSets = this.arrayToList(arrT);
        this.notifyDataChanged();
    }

    private List<T> arrayToList(T[] arrT) {
        ArrayList<T> arrayList = new ArrayList<T>();
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrT[i]);
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void calcMinMax() {
        if (this.mDataSets != null) {
            T t;
            this.mYMax = -3.4028235E38f;
            this.mYMin = Float.MAX_VALUE;
            this.mXMax = -3.4028235E38f;
            this.mXMin = Float.MAX_VALUE;
            Iterator<T> iterator = this.mDataSets.iterator();
            while (iterator.hasNext()) {
                this.calcMinMax((IDataSet)iterator.next());
            }
            this.mLeftAxisMax = -3.4028235E38f;
            this.mLeftAxisMin = Float.MAX_VALUE;
            this.mRightAxisMax = -3.4028235E38f;
            this.mRightAxisMin = Float.MAX_VALUE;
            T t2 = this.getFirstLeft(this.mDataSets);
            if (t2 != null) {
                this.mLeftAxisMax = t2.getYMax();
                this.mLeftAxisMin = t2.getYMin();
                for (IDataSet iDataSet : this.mDataSets) {
                    if (iDataSet.getAxisDependency() != YAxis.AxisDependency.LEFT) continue;
                    if (iDataSet.getYMin() < this.mLeftAxisMin) {
                        this.mLeftAxisMin = iDataSet.getYMin();
                    }
                    if (!(iDataSet.getYMax() > this.mLeftAxisMax)) continue;
                    this.mLeftAxisMax = iDataSet.getYMax();
                }
            }
            if ((t = this.getFirstRight(this.mDataSets)) != null) {
                this.mRightAxisMax = t.getYMax();
                this.mRightAxisMin = t.getYMin();
                for (IDataSet iDataSet : this.mDataSets) {
                    if (iDataSet.getAxisDependency() != YAxis.AxisDependency.RIGHT) continue;
                    if (iDataSet.getYMin() < this.mRightAxisMin) {
                        this.mRightAxisMin = iDataSet.getYMin();
                    }
                    if (!(iDataSet.getYMax() > this.mRightAxisMax)) continue;
                    this.mRightAxisMax = iDataSet.getYMax();
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void calcMinMax(T t) {
        if (this.mYMax < t.getYMax()) {
            this.mYMax = t.getYMax();
        }
        if (this.mYMin > t.getYMin()) {
            this.mYMin = t.getYMin();
        }
        if (this.mXMax < t.getXMax()) {
            this.mXMax = t.getXMax();
        }
        if (this.mXMin > t.getXMin()) {
            this.mXMin = t.getXMin();
        }
        if (t.getAxisDependency() == YAxis.AxisDependency.LEFT) {
            if (this.mLeftAxisMax < t.getYMax()) {
                this.mLeftAxisMax = t.getYMax();
            }
            if (!(this.mLeftAxisMin > t.getYMin())) return;
            {
                this.mLeftAxisMin = t.getYMin();
                return;
            }
        } else {
            if (this.mRightAxisMax < t.getYMax()) {
                this.mRightAxisMax = t.getYMax();
            }
            if (!(this.mRightAxisMin > t.getYMin())) return;
            {
                this.mRightAxisMin = t.getYMin();
                return;
            }
        }
    }

    public void calcMinMaxY(float f, float f2) {
        Iterator<T> iterator = this.mDataSets.iterator();
        while (iterator.hasNext()) {
            ((IDataSet)iterator.next()).calcMinMaxY(f, f2);
        }
        this.calcMinMax();
    }

    public T getDataSetByIndex(int n) {
        if (this.mDataSets == null || n < 0 || n >= this.mDataSets.size()) {
            return null;
        }
        return (T)((IDataSet)this.mDataSets.get(n));
    }

    public int getDataSetCount() {
        if (this.mDataSets == null) {
            return 0;
        }
        return this.mDataSets.size();
    }

    public List<T> getDataSets() {
        return this.mDataSets;
    }

    public int getEntryCount() {
        int n = 0;
        Iterator<T> iterator = this.mDataSets.iterator();
        while (iterator.hasNext()) {
            n += ((IDataSet)iterator.next()).getEntryCount();
        }
        return n;
    }

    public Entry getEntryForHighlight(Highlight highlight) {
        if (highlight.getDataSetIndex() >= this.mDataSets.size()) {
            return null;
        }
        return ((IDataSet)this.mDataSets.get(highlight.getDataSetIndex())).getEntryForXValue(highlight.getX(), highlight.getY());
    }

    protected T getFirstLeft(List<T> object) {
        object = object.iterator();
        while (object.hasNext()) {
            IDataSet iDataSet = (IDataSet)object.next();
            if (iDataSet.getAxisDependency() != YAxis.AxisDependency.LEFT) continue;
            return (T)iDataSet;
        }
        return null;
    }

    public T getFirstRight(List<T> object) {
        object = object.iterator();
        while (object.hasNext()) {
            IDataSet iDataSet = (IDataSet)object.next();
            if (iDataSet.getAxisDependency() != YAxis.AxisDependency.RIGHT) continue;
            return (T)iDataSet;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public T getMaxEntryCountSet() {
        IDataSet iDataSet;
        if (this.mDataSets == null || this.mDataSets.isEmpty()) {
            iDataSet = null;
            return (T)iDataSet;
        } else {
            IDataSet iDataSet2 = (IDataSet)this.mDataSets.get(0);
            Iterator<T> iterator = this.mDataSets.iterator();
            do {
                iDataSet = iDataSet2;
                if (!iterator.hasNext()) return (T)iDataSet;
                iDataSet = (IDataSet)iterator.next();
                if (iDataSet.getEntryCount() <= iDataSet2.getEntryCount()) continue;
                iDataSet2 = iDataSet;
            } while (true);
        }
    }

    public float getXMax() {
        return this.mXMax;
    }

    public float getXMin() {
        return this.mXMin;
    }

    public float getYMax() {
        return this.mYMax;
    }

    public float getYMax(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            if (this.mLeftAxisMax == -3.4028235E38f) {
                return this.mRightAxisMax;
            }
            return this.mLeftAxisMax;
        }
        if (this.mRightAxisMax == -3.4028235E38f) {
            return this.mLeftAxisMax;
        }
        return this.mRightAxisMax;
    }

    public float getYMin() {
        return this.mYMin;
    }

    public float getYMin(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            if (this.mLeftAxisMin == Float.MAX_VALUE) {
                return this.mRightAxisMin;
            }
            return this.mLeftAxisMin;
        }
        if (this.mRightAxisMin == Float.MAX_VALUE) {
            return this.mLeftAxisMin;
        }
        return this.mRightAxisMin;
    }

    public void notifyDataChanged() {
        this.calcMinMax();
    }

    public void setHighlightEnabled(boolean bl) {
        Iterator<T> iterator = this.mDataSets.iterator();
        while (iterator.hasNext()) {
            ((IDataSet)iterator.next()).setHighlightEnabled(bl);
        }
    }
}

