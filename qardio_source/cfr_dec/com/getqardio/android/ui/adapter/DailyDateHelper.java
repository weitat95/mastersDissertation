/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.adapter;

import com.getqardio.android.ui.adapter.WeightChartAdapter;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.Convert;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DailyDateHelper
implements WeightChartAdapter.DateHelper {
    private static final long PERIOD = TimeUnit.DAYS.toMillis(1L);
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dayFormat;
    private Locale locale = Utils.getLocale();
    private SimpleDateFormat monthFormat;
    private long startDate;

    public DailyDateHelper() {
        this.dayFormat = new SimpleDateFormat("dd", this.locale);
        this.monthFormat = new SimpleDateFormat("MMM", this.locale);
    }

    @Override
    public void changeLocale() {
        this.locale = Utils.getLocale();
        this.dayFormat = new SimpleDateFormat("dd", this.locale);
        this.monthFormat = new SimpleDateFormat("MMM", this.locale);
    }

    @Override
    public String[] generateLabel(int n) {
        long l = this.startDate + PERIOD * (long)n;
        return new String[]{this.dayFormat.format(l), Convert.abbreviateMonthIfNecessary(Convert.changeLettersCaseIfNecessary(this.monthFormat.format(l), this.locale), this.locale)};
    }

    protected int getDayNumber(long l) {
        this.calendar.setTimeInMillis(l);
        return DateUtils.getDayNumber(this.calendar.get(1), this.calendar.get(2) + 1, this.calendar.get(5));
    }

    @Override
    public long getMeasurementDate(int n) {
        return this.startDate + PERIOD * (long)n;
    }

    @Override
    public int getMeasurementIndex(long l) {
        return this.getDayNumber(l) - this.getDayNumber(this.startDate);
    }

    @Override
    public int getMeasurementsCount(long l) {
        return this.getDayNumber(l) - this.getDayNumber(this.startDate) + 1;
    }

    @Override
    public void setStartDate(long l) {
        this.calendar.setTimeInMillis(l);
        this.calendar.set(14, 0);
        this.calendar.set(13, 0);
        this.calendar.set(12, 0);
        this.calendar.set(11, 12);
        this.startDate = this.calendar.getTimeInMillis();
    }
}

