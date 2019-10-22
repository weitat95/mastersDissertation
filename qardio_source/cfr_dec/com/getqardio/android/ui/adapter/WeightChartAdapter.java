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
import com.getqardio.android.datamodel.AverageWeightMeasurement;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.widget.MeasurementsChartAdapter;
import com.getqardio.android.utils.MetricUtils;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;

public class WeightChartAdapter
extends MeasurementsChartAdapter {
    private int chartNumber;
    private DateHelper dateHelper;
    private SparseArray<String[]> labelsCache;
    protected int max;
    private int maxBmi = Integer.MIN_VALUE;
    private int maxFat = Integer.MIN_VALUE;
    private int maxWeight = Integer.MIN_VALUE;
    protected int measuresCount;
    private int minBmi = Integer.MAX_VALUE;
    private int minFat = Integer.MAX_VALUE;
    private int minWeight = Integer.MAX_VALUE;
    protected Float[][] values;
    protected int[] valuesCount;

    public WeightChartAdapter(DateHelper dateHelper) {
        if (dateHelper == null) {
            throw new IllegalArgumentException("DateHelper can't be null");
        }
        this.dateHelper = dateHelper;
        this.dateHelper.setStartDate(System.currentTimeMillis());
        this.chartNumber = 1;
        this.max = 0;
        this.measuresCount = 0;
        this.values = (Float[][])Array.newInstance(Float.class, 4, 0);
        this.clearValuesCount(4);
        this.labelsCache = new SparseArray();
    }

    private void calculateMinMax(Float f, Float f2, Float f3) {
        if (f != null) {
            this.minWeight = Math.min(this.minWeight, Math.round(f.floatValue()));
            this.maxWeight = Math.max(this.maxWeight, Math.round(f.floatValue()));
        }
        if (f2 != null) {
            this.minBmi = Math.min(this.minBmi, Math.round(f2.floatValue()));
            this.maxBmi = Math.max(this.maxBmi, Math.round(f2.floatValue()));
        }
        if (f3 != null) {
            this.minFat = Math.min(this.minFat, Math.round(f3.floatValue()));
            this.maxFat = Math.max(this.maxFat, Math.round(f3.floatValue()));
        }
    }

    private void clearValuesCount(int n) {
        if (this.valuesCount == null || this.valuesCount.length != n) {
            this.valuesCount = new int[n];
        }
        Arrays.fill(this.valuesCount, 0);
    }

    private int getActualChartNumber() {
        return this.chartNumber;
    }

    private void setData(Cursor cursor, int n) {
        int n2;
        cursor.moveToLast();
        long l = MeasurementHelper.Weight.parseAverageWeightMeasurement((Cursor)cursor).measureDate.getTime();
        cursor.moveToFirst();
        int[] arrn = MeasurementHelper.Weight.parseAverageWeightMeasurement(cursor);
        this.dateHelper.setStartDate(arrn.measureDate.getTime());
        this.max = Integer.MIN_VALUE;
        this.measuresCount = this.dateHelper.getMeasurementsCount(l);
        this.values = (Float[][])Array.newInstance(Float.class, 4, this.measuresCount);
        for (n2 = 0; n2 < 4; ++n2) {
            for (int i = 0; i < this.measuresCount; ++i) {
                this.values[n2][i] = null;
            }
        }
        this.clearValuesCount(4);
        while (!cursor.isAfterLast()) {
            arrn = MeasurementHelper.Weight.parseAverageWeightMeasurement(cursor);
            n2 = this.dateHelper.getMeasurementIndex(arrn.measureDate.getTime());
            this.values[0][n2] = arrn.weight;
            this.values[1][n2] = arrn.bmi;
            this.values[2][n2] = arrn.fat;
            if (this.values[0][n2] != null) {
                arrn = this.valuesCount;
                arrn[0] = arrn[0] + 1;
            }
            if (this.values[1][n2] != null) {
                arrn = this.valuesCount;
                arrn[1] = arrn[1] + 1;
            }
            if (this.values[2][n2] != null) {
                arrn = this.valuesCount;
                arrn[2] = arrn[2] + 1;
            }
            if (this.values[0][n2] != null) {
                this.values[0][n2] = Float.valueOf(MetricUtils.convertWeight(0, n, this.values[0][n2].floatValue()));
            }
            this.calculateMinMax(this.values[0][n2], this.values[1][n2], this.values[2][n2]);
            cursor.moveToNext();
        }
    }

    public void changeLocale() {
        this.dateHelper.changeLocale();
    }

    @Override
    public int getChartsCount() {
        return 1;
    }

    @Override
    public String[] getLabels(int n) {
        String[] arrstring;
        String[] arrstring2 = arrstring = (String[])this.labelsCache.get(n);
        if (arrstring == null) {
            arrstring2 = this.dateHelper.generateLabel(n);
            this.labelsCache.put(n, (Object)arrstring2);
        }
        return arrstring2;
    }

    @Override
    public int getMaxValue() {
        switch (this.chartNumber) {
            default: {
                return 0;
            }
            case 1: {
                return this.maxBmi;
            }
            case 2: {
                return this.maxFat;
            }
            case 0: 
        }
        return this.maxWeight;
    }

    @Override
    public long getMeasurementDate(int n) {
        return this.dateHelper.getMeasurementDate(n);
    }

    @Override
    public int getMeasuresCount() {
        return this.measuresCount;
    }

    @Override
    public int getMinValue() {
        switch (this.chartNumber) {
            default: {
                return 0;
            }
            case 1: {
                return this.minBmi;
            }
            case 2: {
                return this.minFat;
            }
            case 0: 
        }
        return this.minWeight;
    }

    @Override
    public int getValue(int n, int n2) {
        n = this.getActualChartNumber();
        return Math.round(this.values[n][n2].floatValue());
    }

    @Override
    public String getValueLabel(int n, int n2) {
        n = this.getActualChartNumber();
        if (n == 0 || n == 1) {
            return String.format("%.1f", this.values[n][n2]);
        }
        return super.getValueLabel(n, n2);
    }

    @Override
    public int getValuesCount(int n) {
        n = this.getActualChartNumber();
        return this.valuesCount[n];
    }

    @Override
    public boolean hasValue(int n, int n2) {
        boolean bl = false;
        n = this.getActualChartNumber();
        boolean bl2 = bl;
        if (n < this.values.length) {
            bl2 = bl;
            if (n2 < this.values[n].length) {
                bl2 = bl;
                if (this.values[n][n2] != null) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    public void setChartNumber(int n) {
        synchronized (this) {
            this.chartNumber = n;
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCursor(Cursor cursor, int n) {
        synchronized (this) {
            int n2 = this.measuresCount;
            if (cursor != null && cursor.getCount() > 0) {
                void var2_2;
                this.setData(cursor, (int)var2_2);
            } else {
                this.measuresCount = 0;
                this.dateHelper.setStartDate(System.currentTimeMillis());
                this.values = (Float[][])Array.newInstance(Float.class, 4, this.measuresCount);
                this.clearValuesCount(4);
                this.max = 0;
            }
            this.labelsCache.clear();
            if (this.onDataChangedListener != null) {
                this.onDataChangedListener.onDataChanged(n2, this.measuresCount);
            }
            return;
        }
    }

    public static interface DateHelper {
        public void changeLocale();

        public String[] generateLabel(int var1);

        public long getMeasurementDate(int var1);

        public int getMeasurementIndex(long var1);

        public int getMeasurementsCount(long var1);

        public void setStartDate(long var1);
    }

}

