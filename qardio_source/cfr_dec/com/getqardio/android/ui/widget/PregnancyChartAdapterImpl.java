/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.database.DataSetObservable
 *  android.database.DataSetObserver
 *  android.util.SparseArray
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.util.SparseArray;
import com.getqardio.android.ui.widget.PregnancyChartAdapter;

public class PregnancyChartAdapterImpl
implements PregnancyChartAdapter {
    private Context context;
    private final DataSetObservable dataSetObservable = new DataSetObservable();
    private float maxValue;
    private float minValue;
    private SparseArray<Float> weightByMonths;

    public PregnancyChartAdapterImpl(Context context) {
        this.context = context;
    }

    @Override
    public float getMaxValue() {
        return this.maxValue + 15.0f;
    }

    @Override
    public float getMinValue() {
        return this.minValue;
    }

    @Override
    public int getTrimesterColor(int n) {
        if (n == 0) {
            return this.context.getResources().getColor(2131689487);
        }
        if (n == 1) {
            return this.context.getResources().getColor(2131689622);
        }
        if (n == 2) {
            return this.context.getResources().getColor(2131689548);
        }
        return 0;
    }

    @Override
    public String getTrimesterLabel(int n) {
        return this.context.getString(2131362191, new Object[]{n + 1});
    }

    @Override
    public float getValue(int n) {
        if (this.weightByMonths != null) {
            return ((Float)this.weightByMonths.get(n)).floatValue();
        }
        return 0.0f;
    }

    @Override
    public String getValueLabel(int n) {
        int n2 = this.weightByMonths.indexOfKey(n);
        float f = ((Float)this.weightByMonths.get(n)).floatValue() - ((Float)this.weightByMonths.valueAt(n2 - 1)).floatValue();
        if (f >= 0.0f) {
            return String.format("+%.1f", Float.valueOf(f));
        }
        return String.format("%.1f", Float.valueOf(f));
    }

    @Override
    public boolean hasValue(int n) {
        return this.weightByMonths != null && this.weightByMonths.get(n) != null;
    }

    public void notifyDataSetChanged() {
        this.dataSetObservable.notifyChanged();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.dataSetObservable.registerObserver((Object)dataSetObserver);
    }

    public void setWeight(SparseArray<Float> sparseArray) {
        this.weightByMonths = sparseArray;
        if (sparseArray != null && sparseArray.size() > 0) {
            this.maxValue = this.minValue = ((Float)sparseArray.valueAt(0)).floatValue();
            for (int i = 0; i < sparseArray.size(); ++i) {
                this.maxValue = Math.max(this.maxValue, ((Float)sparseArray.valueAt(i)).floatValue());
                this.minValue = Math.min(this.minValue, ((Float)sparseArray.valueAt(i)).floatValue());
            }
        }
        this.notifyDataSetChanged();
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.dataSetObservable.unregisterObserver((Object)dataSetObserver);
    }
}

