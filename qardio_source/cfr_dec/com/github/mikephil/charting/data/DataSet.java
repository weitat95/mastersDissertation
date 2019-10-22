/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BaseDataSet;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class DataSet<T extends Entry>
extends BaseDataSet<T> {
    protected List<T> mValues = null;
    protected float mXMax = -3.4028235E38f;
    protected float mXMin = Float.MAX_VALUE;
    protected float mYMax = -3.4028235E38f;
    protected float mYMin = Float.MAX_VALUE;

    public DataSet(List<T> list, String string2) {
        super(string2);
        this.mValues = list;
        if (this.mValues == null) {
            this.mValues = new ArrayList<T>();
        }
        this.calcMinMax();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void calcMinMax() {
        if (this.mValues != null && !this.mValues.isEmpty()) {
            this.mYMax = -3.4028235E38f;
            this.mYMin = Float.MAX_VALUE;
            this.mXMax = -3.4028235E38f;
            this.mXMin = Float.MAX_VALUE;
            Iterator<T> iterator = this.mValues.iterator();
            while (iterator.hasNext()) {
                this.calcMinMax((Entry)iterator.next());
            }
        }
    }

    protected void calcMinMax(T t) {
        if (t == null) {
            return;
        }
        this.calcMinMaxX(t);
        this.calcMinMaxY(t);
    }

    protected void calcMinMaxX(T t) {
        if (((Entry)t).getX() < this.mXMin) {
            this.mXMin = ((Entry)t).getX();
        }
        if (((Entry)t).getX() > this.mXMax) {
            this.mXMax = ((Entry)t).getX();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void calcMinMaxY(float f, float f2) {
        if (this.mValues != null && !this.mValues.isEmpty()) {
            this.mYMax = -3.4028235E38f;
            this.mYMin = Float.MAX_VALUE;
            int n = this.getEntryIndex(f2, Float.NaN, Rounding.UP);
            for (int i = this.getEntryIndex(f, Float.NaN, Rounding.DOWN); i <= n; ++i) {
                this.calcMinMaxY((Entry)this.mValues.get(i));
            }
        }
    }

    protected void calcMinMaxY(T t) {
        if (((BaseEntry)t).getY() < this.mYMin) {
            this.mYMin = ((BaseEntry)t).getY();
        }
        if (((BaseEntry)t).getY() > this.mYMax) {
            this.mYMax = ((BaseEntry)t).getY();
        }
    }

    @Override
    public List<T> getEntriesForXValue(float f) {
        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        int n = 0;
        int n2 = this.mValues.size() - 1;
        while (n <= n2) {
            int n3 = (n2 + n) / 2;
            Entry entry = (Entry)this.mValues.get(n3);
            if (f == entry.getX()) {
                for (n = n3; n > 0 && ((Entry)this.mValues.get(n - 1)).getX() == f; --n) {
                }
                n2 = this.mValues.size();
                while (n < n2 && (entry = (Entry)this.mValues.get(n)).getX() == f) {
                    arrayList.add(entry);
                    ++n;
                }
                break;
            }
            if (f > entry.getX()) {
                n = n3 + 1;
                continue;
            }
            n2 = n3 - 1;
        }
        return arrayList;
    }

    @Override
    public int getEntryCount() {
        return this.mValues.size();
    }

    @Override
    public T getEntryForIndex(int n) {
        return (T)((Entry)this.mValues.get(n));
    }

    @Override
    public T getEntryForXValue(float f, float f2) {
        return this.getEntryForXValue(f, f2, Rounding.CLOSEST);
    }

    @Override
    public T getEntryForXValue(float f, float f2, Rounding rounding) {
        int n = this.getEntryIndex(f, f2, rounding);
        if (n > -1) {
            return (T)((Entry)this.mValues.get(n));
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getEntryIndex(float f, float f2, Rounding object) {
        int n;
        int n2;
        float f3;
        if (this.mValues == null) return -1;
        if (this.mValues.isEmpty()) {
            return -1;
        }
        int n3 = 0;
        int n4 = n2 = this.mValues.size() - 1;
        while (n3 < n2) {
            n4 = (n3 + n2) / 2;
            f3 = ((Entry)this.mValues.get(n4)).getX() - f;
            float f4 = ((Entry)this.mValues.get(n4 + 1)).getX();
            float f5 = Math.abs(f3);
            if ((f4 = Math.abs(f4 - f)) < f5) {
                n3 = n4 + 1;
                n = n2;
            } else if (f5 < f4) {
                n = n4;
            } else if ((double)f3 >= 0.0) {
                n = n4;
            } else {
                n = n2;
                if ((double)f3 < 0.0) {
                    n3 = n4 + 1;
                    n = n2;
                }
            }
            n4 = n;
            n2 = n;
        }
        n3 = n4;
        if (n4 == -1) return n3;
        f3 = ((Entry)this.mValues.get(n4)).getX();
        if (object == Rounding.UP) {
            n = n4;
            if (f3 < f) {
                n = n4;
                if (n4 < this.mValues.size() - 1) {
                    n = n4 + 1;
                }
            }
        } else {
            n = n4;
            if (object == Rounding.DOWN) {
                n = n4;
                if (f3 > f) {
                    n = n4;
                    if (n4 > 0) {
                        n = n4 - 1;
                    }
                }
            }
        }
        n3 = n;
        if (Float.isNaN(f2)) return n3;
        while (n > 0 && ((Entry)this.mValues.get(n - 1)).getX() == f3) {
            --n;
        }
        f = ((Entry)this.mValues.get(n)).getY();
        n4 = n;
        while ((n3 = n + 1) < this.mValues.size()) {
            object = (Entry)this.mValues.get(n3);
            if (((Entry)object).getX() != f3) {
                return n4;
            }
            n = n3;
            if (!(Math.abs(((BaseEntry)object).getY() - f2) < Math.abs(f - f2))) continue;
            f = f2;
            n4 = n3;
            n = n3;
        }
        return n4;
    }

    @Override
    public int getEntryIndex(Entry entry) {
        return this.mValues.indexOf(entry);
    }

    @Override
    public float getXMax() {
        return this.mXMax;
    }

    @Override
    public float getXMin() {
        return this.mXMin;
    }

    @Override
    public float getYMax() {
        return this.mYMax;
    }

    @Override
    public float getYMin() {
        return this.mYMin;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toSimpleString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder().append("DataSet, label: ");
        String string2 = this.getLabel() == null ? "" : this.getLabel();
        stringBuffer.append(stringBuilder.append(string2).append(", entries: ").append(this.mValues.size()).append("\n").toString());
        return stringBuffer.toString();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.toSimpleString());
        for (int i = 0; i < this.mValues.size(); ++i) {
            stringBuffer.append(((Entry)this.mValues.get(i)).toString() + " ");
        }
        return stringBuffer.toString();
    }

    public static enum Rounding {
        UP,
        DOWN,
        CLOSEST;

    }

}

