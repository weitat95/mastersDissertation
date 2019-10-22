/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.adapter;

import com.getqardio.android.ui.adapter.WeightChartAdapter;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.Convert;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthlyDateHelper
implements WeightChartAdapter.DateHelper {
    Calendar calendar = Calendar.getInstance();
    private Locale locale = Utils.getLocale();
    private SimpleDateFormat monthFormat = new SimpleDateFormat("LLL", this.locale);
    private long startDate;
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", this.locale);

    private int monthsDifference(long l, long l2) {
        this.calendar.setTimeInMillis(l);
        int n = this.calendar.get(2);
        int n2 = this.calendar.get(1);
        this.calendar.setTimeInMillis(l2);
        return (this.calendar.get(1) - n2) * 12 + (this.calendar.get(2) - n);
    }

    @Override
    public void changeLocale() {
        this.locale = Utils.getLocale();
        this.monthFormat = new SimpleDateFormat("LLL", this.locale);
        this.yearFormat = new SimpleDateFormat("yyyy", this.locale);
    }

    @Override
    public String[] generateLabel(int n) {
        this.calendar.setTimeInMillis(this.startDate);
        this.calendar.set(5, 1);
        this.calendar.add(2, n);
        return new String[]{Convert.abbreviateMonthIfNecessary(Convert.changeLettersCaseIfNecessary(this.monthFormat.format(this.calendar.getTime()), this.locale), this.locale), this.yearFormat.format(this.calendar.getTime())};
    }

    @Override
    public long getMeasurementDate(int n) {
        this.calendar.setTimeInMillis(this.startDate);
        this.calendar.set(5, 1);
        this.calendar.add(2, n);
        return this.calendar.getTimeInMillis();
    }

    @Override
    public int getMeasurementIndex(long l) {
        return this.monthsDifference(this.startDate, l);
    }

    @Override
    public int getMeasurementsCount(long l) {
        return this.monthsDifference(this.startDate, l) + 1;
    }

    @Override
    public void setStartDate(long l) {
        this.startDate = l;
    }
}

