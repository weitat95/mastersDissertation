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
import java.util.concurrent.TimeUnit;

public class DailyBPChartAdapter
extends BaseCursorChartAdapter {
    private static final long PERIOD = TimeUnit.DAYS.toMillis(1L);
    private SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd", locale);
    private SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMM", locale);
    private Date date = new Date();

    public void changeLocale() {
        locale = Utils.getLocale();
        this.DAY_FORMAT = new SimpleDateFormat("dd", locale);
        this.MONTH_FORMAT = new SimpleDateFormat("MMM", locale);
    }

    @Override
    protected long cutDate(long l) {
        this.calendar.setTimeInMillis(l);
        this.calendar.set(14, 0);
        this.calendar.set(13, 0);
        this.calendar.set(12, 0);
        this.calendar.set(11, 12);
        return this.calendar.getTimeInMillis();
    }

    @Override
    protected String[] generateLabel(int n) {
        this.date.setTime(this.startDate + PERIOD * (long)n);
        return new String[]{this.DAY_FORMAT.format(this.date), Convert.abbreviateMonthIfNecessary(Convert.changeLettersCaseIfNecessary(this.MONTH_FORMAT.format(this.date), locale), locale)};
    }

    @Override
    public int getChartsCount() {
        return 2;
    }

    @Override
    public long getMeasurementDate(int n) {
        return this.startDate + PERIOD * (long)n;
    }

    @Override
    protected void setData(Cursor cursor) {
        cursor.moveToLast();
        int n = this.getDayNumber(MeasurementHelper.BloodPressure.parseAverageBPMeasurement((Cursor)cursor).measureDate.getTime());
        cursor.moveToFirst();
        int[] arrn = MeasurementHelper.BloodPressure.parseAverageBPMeasurement(cursor);
        this.startDate = this.cutDate(arrn.measureDate.getTime());
        int n2 = this.getDayNumber(arrn.measureDate.getTime());
        this.min = Integer.MAX_VALUE;
        this.max = Integer.MIN_VALUE;
        n = this.measuresCount = n - n2 + 1;
        this.values = (int[][])Array.newInstance(Integer.TYPE, 2, n);
        for (n = 0; n < 2; ++n) {
            for (int i = 0; i < this.measuresCount; ++i) {
                this.values[n][i] = -1;
            }
        }
        this.clearValuesCount(2);
        while (!cursor.isAfterLast()) {
            arrn = MeasurementHelper.BloodPressure.parseAverageBPMeasurement(cursor);
            n = this.getDayNumber(arrn.measureDate.getTime()) - n2;
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

