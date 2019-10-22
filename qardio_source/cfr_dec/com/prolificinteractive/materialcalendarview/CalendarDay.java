/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.prolificinteractive.materialcalendarview;

import android.os.Parcel;
import android.os.Parcelable;
import com.prolificinteractive.materialcalendarview.CalendarUtils;
import java.util.Calendar;
import java.util.Date;

public final class CalendarDay
implements Parcelable {
    public static final Parcelable.Creator<CalendarDay> CREATOR = new Parcelable.Creator<CalendarDay>(){

        public CalendarDay createFromParcel(Parcel parcel) {
            return new CalendarDay(parcel);
        }

        public CalendarDay[] newArray(int n) {
            return new CalendarDay[n];
        }
    };
    private transient Calendar _calendar;
    private transient Date _date;
    private final int day;
    private final int month;
    private final int year;

    @Deprecated
    public CalendarDay() {
        this(CalendarUtils.getInstance());
    }

    @Deprecated
    public CalendarDay(int n, int n2, int n3) {
        this.year = n;
        this.month = n2;
        this.day = n3;
    }

    public CalendarDay(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    @Deprecated
    public CalendarDay(Calendar calendar) {
        this(CalendarUtils.getYear(calendar), CalendarUtils.getMonth(calendar), CalendarUtils.getDay(calendar));
    }

    public static CalendarDay from(int n, int n2, int n3) {
        return new CalendarDay(n, n2, n3);
    }

    public static CalendarDay from(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return CalendarDay.from(CalendarUtils.getYear(calendar), CalendarUtils.getMonth(calendar), CalendarUtils.getDay(calendar));
    }

    public static CalendarDay from(Date date) {
        if (date == null) {
            return null;
        }
        return CalendarDay.from(CalendarUtils.getInstance(date));
    }

    private static int hashCode(int n, int n2, int n3) {
        return n * 10000 + n2 * 100 + n3;
    }

    public static CalendarDay today() {
        return CalendarDay.from(CalendarUtils.getInstance());
    }

    public void copyTo(Calendar calendar) {
        calendar.clear();
        calendar.set(this.year, this.month, this.day);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (CalendarDay)object;
                if (this.day != ((CalendarDay)object).day || this.month != ((CalendarDay)object).month || this.year != ((CalendarDay)object).year) break block5;
            }
            return true;
        }
        return false;
    }

    public Calendar getCalendar() {
        if (this._calendar == null) {
            this._calendar = CalendarUtils.getInstance();
            this.copyTo(this._calendar);
        }
        return this._calendar;
    }

    public Date getDate() {
        if (this._date == null) {
            this._date = this.getCalendar().getTime();
        }
        return this._date;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public int hashCode() {
        return CalendarDay.hashCode(this.year, this.month, this.day);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isAfter(CalendarDay calendarDay) {
        if (calendarDay == null) {
            throw new IllegalArgumentException("other cannot be null");
        }
        if (this.year == calendarDay.year) {
            if (!(this.month == calendarDay.month ? this.day <= calendarDay.day : this.month <= calendarDay.month)) return true;
            return false;
        }
        if (this.year <= calendarDay.year) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isBefore(CalendarDay calendarDay) {
        if (calendarDay == null) {
            throw new IllegalArgumentException("other cannot be null");
        }
        if (this.year == calendarDay.year) {
            if (!(this.month == calendarDay.month ? this.day >= calendarDay.day : this.month >= calendarDay.month)) return true;
            return false;
        }
        if (this.year >= calendarDay.year) return false;
        return true;
    }

    public boolean isInRange(CalendarDay calendarDay, CalendarDay calendarDay2) {
        return !(calendarDay != null && calendarDay.isAfter(this) || calendarDay2 != null && calendarDay2.isBefore(this));
    }

    public String toString() {
        return "CalendarDay{" + this.year + "-" + this.month + "-" + this.day + "}";
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.year);
        parcel.writeInt(this.month);
        parcel.writeInt(this.day);
    }

}

