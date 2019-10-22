/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 */
package com.getqardio.android.ui.adapter;

import android.database.Cursor;
import com.getqardio.android.datamodel.AverageBPMeasurement;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.adapter.BaseCursorChartAdapter;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.Convert;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthlyBPChartAdapter
extends BaseCursorChartAdapter {
    private static SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("LLL", locale);
    private static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", locale);

    private int monthsDifference(long l, long l2) {
        this.calendar.setTimeInMillis(l);
        int n = this.calendar.get(2);
        int n2 = this.calendar.get(1);
        this.calendar.setTimeInMillis(l2);
        return (this.calendar.get(1) - n2) * 12 + (this.calendar.get(2) - n);
    }

    public void changeLocale() {
        locale = Utils.getLocale();
        MONTH_FORMAT = new SimpleDateFormat("LLL", locale);
        YEAR_FORMAT = new SimpleDateFormat("yyyy", locale);
    }

    @Override
    protected long cutDate(long l) {
        return l;
    }

    @Override
    protected String[] generateLabel(int n) {
        this.calendar.setTimeInMillis(this.startDate);
        this.calendar.set(5, 1);
        this.calendar.add(2, n);
        return new String[]{Convert.abbreviateMonthIfNecessary(Convert.changeLettersCaseIfNecessary(MONTH_FORMAT.format(this.calendar.getTime()), locale), locale), YEAR_FORMAT.format(this.calendar.getTime())};
    }

    @Override
    public int getChartsCount() {
        return 2;
    }

    @Override
    public long getMeasurementDate(int n) {
        this.calendar.setTimeInMillis(this.startDate);
        this.calendar.set(5, 1);
        this.calendar.add(2, n);
        return this.calendar.getTimeInMillis();
    }

    @Override
    protected void setData(Cursor cursor) {
        cursor.moveToLast();
        long l = MeasurementHelper.BloodPressure.parseAverageBPMeasurement((Cursor)cursor).measureDate.getTime();
        cursor.moveToFirst();
        this.startDate = MeasurementHelper.BloodPressure.parseAverageBPMeasurement((Cursor)cursor).measureDate.getTime();
        this.min = Integer.MAX_VALUE;
        this.max = Integer.MIN_VALUE;
        int n = this.measuresCount = this.monthsDifference(this.startDate, l) + 1;
        this.values = (int[][])Array.newInstance(Integer.TYPE, 2, n);
        for (n = 0; n < 2; ++n) {
            for (int i = 0; i < this.measuresCount; ++i) {
                this.values[n][i] = -1;
            }
        }
        this.clearValuesCount(2);
        while (!cursor.isAfterLast()) {
            int[] arrn = MeasurementHelper.BloodPressure.parseAverageBPMeasurement(cursor);
            n = this.monthsDifference(this.startDate, arrn.measureDate.getTime());
            if (arrn.sys != null) {
                this.values[0][n] = Math.round(arrn.sys.floatValue());
                int[] arrn2 = this.valuesCount;
                arrn2[0] = arrn2[0] + 1;
                this.min = Math.min(this.min, this.values[0][n]);
                this.max = Math.max(this.max, this.values[0][n]);
            }
            if (arrn.dia != null) {
                this.values[1][n] = Math.round(arrn.dia.floatValue());
                arrn = this.valuesCount;
                arrn[1] = arrn[1] + 1;
                this.min = Math.min(this.min, this.values[1][n]);
                this.max = Math.max(this.max, this.values[1][n]);
            }
            cursor.moveToNext();
        }
    }
}

