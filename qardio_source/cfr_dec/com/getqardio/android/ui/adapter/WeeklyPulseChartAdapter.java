/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 */
package com.getqardio.android.ui.adapter;

import android.database.Cursor;
import com.getqardio.android.datamodel.AveragePulsMeasurement;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.adapter.BaseCursorChartAdapter;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.Convert;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class WeeklyPulseChartAdapter
extends BaseCursorChartAdapter {
    private static SimpleDateFormat DAY_FORMAT;
    private static SimpleDateFormat MONTH_FORMAT;
    private static final long PERIOD;
    private Date date;
    private TimeZone timezone = TimeZone.getDefault();

    static {
        PERIOD = TimeUnit.DAYS.toMillis(7L);
        DAY_FORMAT = new SimpleDateFormat("dd", locale);
        MONTH_FORMAT = new SimpleDateFormat("MMM", locale);
    }

    public WeeklyPulseChartAdapter() {
        this.date = new Date();
    }

    private long getFirstDayOfWeek(long l) {
        this.calendar.setTimeInMillis(l);
        this.calendar.set(14, 0);
        this.calendar.set(13, 0);
        this.calendar.set(12, 0);
        this.calendar.set(11, 12);
        this.calendar.set(7, this.calendar.getFirstDayOfWeek());
        return this.calendar.getTimeInMillis();
    }

    public void changeLocale() {
        locale = Utils.getLocale();
        DAY_FORMAT = new SimpleDateFormat("dd", locale);
        MONTH_FORMAT = new SimpleDateFormat("MMM", locale);
    }

    @Override
    protected long cutDate(long l) {
        return this.getFirstDayOfWeek(l);
    }

    @Override
    protected String[] generateLabel(int n) {
        this.date.setTime(this.startDate + PERIOD * (long)n);
        String string2 = DAY_FORMAT.format(this.date) + "-";
        String string3 = MONTH_FORMAT.format(this.date);
        this.date.setTime(this.startDate + PERIOD * (long)n + TimeUnit.DAYS.toMillis(6L));
        return new String[]{string2 + DAY_FORMAT.format(this.date), Convert.changeLettersCaseIfNecessary(string3, locale)};
    }

    @Override
    public int getChartsCount() {
        return 1;
    }

    @Override
    public long getMeasurementDate(int n) {
        return this.startDate + PERIOD * (long)n;
    }

    @Override
    protected void setData(Cursor cursor) {
        cursor.moveToLast();
        long l = this.getFirstDayOfWeek(MeasurementHelper.BloodPressure.parseAveragePulsMeasurement((Cursor)cursor).measureDate.getTime());
        cursor.moveToFirst();
        this.startDate = this.getFirstDayOfWeek(MeasurementHelper.BloodPressure.parseAveragePulsMeasurement((Cursor)cursor).measureDate.getTime());
        this.min = Integer.MAX_VALUE;
        this.max = Integer.MIN_VALUE;
        int n = this.measuresCount = (int)((l - this.startDate + (long)this.timezone.getDSTSavings()) / PERIOD) + 1;
        this.values = (int[][])Array.newInstance(Integer.TYPE, 1, n);
        for (n = 0; n < 1; ++n) {
            for (int i = 0; i < this.measuresCount; ++i) {
                this.values[n][i] = -1;
            }
        }
        this.clearValuesCount(1);
        while (!cursor.isAfterLast()) {
            int[] arrn = MeasurementHelper.BloodPressure.parseAveragePulsMeasurement(cursor);
            n = (int)((this.getFirstDayOfWeek(arrn.measureDate.getTime()) - this.startDate + (long)this.timezone.getDSTSavings()) / PERIOD);
            if (arrn.puls != null) {
                this.values[0][n] = Math.round(arrn.puls.floatValue());
                arrn = this.valuesCount;
                arrn[0] = arrn[0] + 1;
                this.min = Math.min(this.min, this.values[0][n]);
                this.max = Math.max(this.max, this.values[0][n]);
            }
            cursor.moveToNext();
        }
    }
}

