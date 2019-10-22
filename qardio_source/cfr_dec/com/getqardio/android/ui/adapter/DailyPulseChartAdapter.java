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
import java.util.concurrent.TimeUnit;

public class DailyPulseChartAdapter
extends BaseCursorChartAdapter {
    private static SimpleDateFormat DAY_FORMAT;
    private static SimpleDateFormat MONTH_FORMAT;
    private static final long PERIOD;
    private Date date = new Date();

    static {
        PERIOD = TimeUnit.DAYS.toMillis(1L);
        DAY_FORMAT = new SimpleDateFormat("dd", locale);
        MONTH_FORMAT = new SimpleDateFormat("MMM", locale);
    }

    public void changeLocale() {
        locale = Utils.getLocale();
        DAY_FORMAT = new SimpleDateFormat("dd", locale);
        MONTH_FORMAT = new SimpleDateFormat("MMM", locale);
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
        return new String[]{DAY_FORMAT.format(this.date), Convert.abbreviateMonthIfNecessary(Convert.changeLettersCaseIfNecessary(MONTH_FORMAT.format(this.date), locale), locale)};
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
        int n = this.getDayNumber(MeasurementHelper.BloodPressure.parseAveragePulsMeasurement((Cursor)cursor).measureDate.getTime());
        cursor.moveToFirst();
        int[] arrn = MeasurementHelper.BloodPressure.parseAveragePulsMeasurement(cursor);
        this.startDate = this.cutDate(arrn.measureDate.getTime());
        int n2 = this.getDayNumber(arrn.measureDate.getTime());
        this.min = Integer.MAX_VALUE;
        this.max = Integer.MIN_VALUE;
        n = this.measuresCount = n - n2 + 1;
        this.values = (int[][])Array.newInstance(Integer.TYPE, 1, n);
        for (n = 0; n < 1; ++n) {
            for (int i = 0; i < this.measuresCount; ++i) {
                this.values[n][i] = -1;
            }
        }
        this.clearValuesCount(1);
        while (!cursor.isAfterLast()) {
            arrn = MeasurementHelper.BloodPressure.parseAveragePulsMeasurement(cursor);
            n = this.getDayNumber(arrn.measureDate.getTime()) - n2;
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

