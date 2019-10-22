/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  android.util.SparseArray
 */
package com.getqardio.android.ui.adapter;

import android.database.Cursor;
import android.util.SparseArray;
import com.getqardio.android.ui.widget.MeasurementsChartAdapter;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.Utils;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public abstract class BaseCursorChartAdapter
extends MeasurementsChartAdapter {
    protected static Locale locale = Utils.getLocale();
    protected Calendar calendar = Calendar.getInstance();
    private SparseArray<String[]> labelsCache;
    protected int max = 0;
    protected int measuresCount = 0;
    protected int min = 0;
    protected long startDate = this.cutDate(System.currentTimeMillis());
    protected int[][] values;
    protected int[] valuesCount;

    public BaseCursorChartAdapter() {
        int n = this.getChartsCount();
        this.values = (int[][])Array.newInstance(Integer.TYPE, n, 0);
        this.clearValuesCount(this.getChartsCount());
        this.labelsCache = new SparseArray();
    }

    protected void clearValuesCount(int n) {
        if (this.valuesCount == null || this.valuesCount.length != n) {
            this.valuesCount = new int[n];
        }
        Arrays.fill(this.valuesCount, 0);
    }

    protected abstract long cutDate(long var1);

    protected abstract String[] generateLabel(int var1);

    protected int getDayNumber(long l) {
        this.calendar.setTimeInMillis(l);
        return DateUtils.getDayNumber(this.calendar.get(1), this.calendar.get(2) + 1, this.calendar.get(5));
    }

    @Override
    public String[] getLabels(int n) {
        String[] arrstring;
        String[] arrstring2 = arrstring = (String[])this.labelsCache.get(n);
        if (arrstring == null) {
            arrstring2 = this.generateLabel(n);
            this.labelsCache.put(n, (Object)arrstring2);
        }
        return arrstring2;
    }

    @Override
    public int getMaxValue() {
        return this.max;
    }

    @Override
    public int getMeasuresCount() {
        return this.measuresCount;
    }

    @Override
    public int getMinValue() {
        return this.min;
    }

    @Override
    public int getValue(int n, int n2) {
        return this.values[n][n2];
    }

    @Override
    public int getValuesCount(int n) {
        return this.valuesCount[n];
    }

    @Override
    public boolean hasValue(int n, int n2) {
        boolean bl;
        boolean bl2 = bl = false;
        if (n < this.values.length) {
            bl2 = bl;
            if (n2 < this.values[n].length) {
                bl2 = bl;
                if (this.values[n][n2] != -1) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCursor(Cursor cursor) {
        synchronized (this) {
            int n = this.measuresCount;
            if (cursor != null && cursor.getCount() > 0) {
                this.setData(cursor);
            } else {
                this.measuresCount = 0;
                this.startDate = this.cutDate(System.currentTimeMillis());
                int n2 = this.getChartsCount();
                int n3 = this.measuresCount;
                this.values = (int[][])Array.newInstance(Integer.TYPE, n2, n3);
                this.clearValuesCount(this.getChartsCount());
                this.max = 0;
                this.min = 0;
            }
            this.labelsCache.clear();
            if (this.onDataChangedListener != null) {
                this.onDataChangedListener.onDataChanged(n, this.measuresCount);
            }
            return;
        }
    }

    protected abstract void setData(Cursor var1);
}

