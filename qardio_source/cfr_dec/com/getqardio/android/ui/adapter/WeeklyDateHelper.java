/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.adapter;

import com.getqardio.android.ui.adapter.WeightChartAdapter;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.Convert;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class WeeklyDateHelper
implements WeightChartAdapter.DateHelper {
    private static final long PERIOD = TimeUnit.DAYS.toMillis(7L);
    private Calendar calendar;
    private SimpleDateFormat dayFormat;
    private Locale locale;
    private SimpleDateFormat monthFormat;
    private long startDate;
    private TimeZone timezone = TimeZone.getDefault();

    public WeeklyDateHelper() {
        this.calendar = Calendar.getInstance();
        this.locale = Utils.getLocale();
        this.dayFormat = new SimpleDateFormat("dd", this.locale);
        this.monthFormat = new SimpleDateFormat("MMM", this.locale);
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

    @Override
    public void changeLocale() {
        this.locale = Utils.getLocale();
        this.dayFormat = new SimpleDateFormat("dd", this.locale);
        this.monthFormat = new SimpleDateFormat("MMM", this.locale);
    }

    @Override
    public String[] generateLabel(int n) {
        String string2 = this.dayFormat.format(this.startDate + PERIOD * (long)n) + "-";
        String string3 = this.monthFormat.format(this.startDate + PERIOD * (long)n);
        return new String[]{string2 + this.dayFormat.format(this.startDate + PERIOD * (long)n + TimeUnit.DAYS.toMillis(6L)), Convert.changeLettersCaseIfNecessary(string3, this.locale)};
    }

    @Override
    public long getMeasurementDate(int n) {
        return this.startDate + PERIOD * (long)n;
    }

    @Override
    public int getMeasurementIndex(long l) {
        return (int)((this.getFirstDayOfWeek(l) - this.startDate + (long)this.timezone.getDSTSavings()) / PERIOD);
    }

    @Override
    public int getMeasurementsCount(long l) {
        return (int)((this.getFirstDayOfWeek(l) - this.startDate + (long)this.timezone.getDSTSavings()) / PERIOD) + 1;
    }

    @Override
    public void setStartDate(long l) {
        this.startDate = this.getFirstDayOfWeek(l);
    }
}

