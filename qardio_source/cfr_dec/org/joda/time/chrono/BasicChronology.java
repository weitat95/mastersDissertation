/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.chrono.BasicDayOfMonthDateTimeField;
import org.joda.time.chrono.BasicDayOfYearDateTimeField;
import org.joda.time.chrono.BasicWeekOfWeekyearDateTimeField;
import org.joda.time.chrono.BasicWeekyearDateTimeField;
import org.joda.time.chrono.BasicYearDateTimeField;
import org.joda.time.chrono.GJDayOfWeekDateTimeField;
import org.joda.time.chrono.GJEraDateTimeField;
import org.joda.time.chrono.GJLocaleSymbols;
import org.joda.time.chrono.GJMonthOfYearDateTimeField;
import org.joda.time.chrono.GJYearOfEraDateTimeField;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.OffsetDateTimeField;
import org.joda.time.field.PreciseDateTimeField;
import org.joda.time.field.PreciseDurationField;
import org.joda.time.field.RemainderDateTimeField;
import org.joda.time.field.ZeroIsMaxDateTimeField;

abstract class BasicChronology
extends AssembledChronology {
    private static final DateTimeField cClockhourOfDayField;
    private static final DateTimeField cClockhourOfHalfdayField;
    private static final DurationField cDaysField;
    private static final DateTimeField cHalfdayOfDayField;
    private static final DurationField cHalfdaysField;
    private static final DateTimeField cHourOfDayField;
    private static final DateTimeField cHourOfHalfdayField;
    private static final DurationField cHoursField;
    private static final DurationField cMillisField;
    private static final DateTimeField cMillisOfDayField;
    private static final DateTimeField cMillisOfSecondField;
    private static final DateTimeField cMinuteOfDayField;
    private static final DateTimeField cMinuteOfHourField;
    private static final DurationField cMinutesField;
    private static final DateTimeField cSecondOfDayField;
    private static final DateTimeField cSecondOfMinuteField;
    private static final DurationField cSecondsField;
    private static final DurationField cWeeksField;
    private final int iMinDaysInFirstWeek;
    private final transient YearInfo[] iYearInfoCache = new YearInfo[1024];

    static {
        cMillisField = MillisDurationField.INSTANCE;
        cSecondsField = new PreciseDurationField(DurationFieldType.seconds(), 1000L);
        cMinutesField = new PreciseDurationField(DurationFieldType.minutes(), 60000L);
        cHoursField = new PreciseDurationField(DurationFieldType.hours(), 3600000L);
        cHalfdaysField = new PreciseDurationField(DurationFieldType.halfdays(), 43200000L);
        cDaysField = new PreciseDurationField(DurationFieldType.days(), 86400000L);
        cWeeksField = new PreciseDurationField(DurationFieldType.weeks(), 604800000L);
        cMillisOfSecondField = new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), cMillisField, cSecondsField);
        cMillisOfDayField = new PreciseDateTimeField(DateTimeFieldType.millisOfDay(), cMillisField, cDaysField);
        cSecondOfMinuteField = new PreciseDateTimeField(DateTimeFieldType.secondOfMinute(), cSecondsField, cMinutesField);
        cSecondOfDayField = new PreciseDateTimeField(DateTimeFieldType.secondOfDay(), cSecondsField, cDaysField);
        cMinuteOfHourField = new PreciseDateTimeField(DateTimeFieldType.minuteOfHour(), cMinutesField, cHoursField);
        cMinuteOfDayField = new PreciseDateTimeField(DateTimeFieldType.minuteOfDay(), cMinutesField, cDaysField);
        cHourOfDayField = new PreciseDateTimeField(DateTimeFieldType.hourOfDay(), cHoursField, cDaysField);
        cHourOfHalfdayField = new PreciseDateTimeField(DateTimeFieldType.hourOfHalfday(), cHoursField, cHalfdaysField);
        cClockhourOfDayField = new ZeroIsMaxDateTimeField(cHourOfDayField, DateTimeFieldType.clockhourOfDay());
        cClockhourOfHalfdayField = new ZeroIsMaxDateTimeField(cHourOfHalfdayField, DateTimeFieldType.clockhourOfHalfday());
        cHalfdayOfDayField = new HalfdayField();
    }

    BasicChronology(Chronology chronology, Object object, int n) {
        super(chronology, object);
        if (n < 1 || n > 7) {
            throw new IllegalArgumentException("Invalid min days in first week: " + n);
        }
        this.iMinDaysInFirstWeek = n;
    }

    private YearInfo getYearInfo(int n) {
        YearInfo yearInfo;
        block3: {
            block2: {
                YearInfo yearInfo2 = this.iYearInfoCache[n & 0x3FF];
                if (yearInfo2 == null) break block2;
                yearInfo = yearInfo2;
                if (yearInfo2.iYear == n) break block3;
            }
            this.iYearInfoCache[n & 1023] = yearInfo = new YearInfo(n, this.calculateFirstDayOfYearMillis(n));
        }
        return yearInfo;
    }

    @Override
    protected void assemble(AssembledChronology.Fields fields) {
        fields.millis = cMillisField;
        fields.seconds = cSecondsField;
        fields.minutes = cMinutesField;
        fields.hours = cHoursField;
        fields.halfdays = cHalfdaysField;
        fields.days = cDaysField;
        fields.weeks = cWeeksField;
        fields.millisOfSecond = cMillisOfSecondField;
        fields.millisOfDay = cMillisOfDayField;
        fields.secondOfMinute = cSecondOfMinuteField;
        fields.secondOfDay = cSecondOfDayField;
        fields.minuteOfHour = cMinuteOfHourField;
        fields.minuteOfDay = cMinuteOfDayField;
        fields.hourOfDay = cHourOfDayField;
        fields.hourOfHalfday = cHourOfHalfdayField;
        fields.clockhourOfDay = cClockhourOfDayField;
        fields.clockhourOfHalfday = cClockhourOfHalfdayField;
        fields.halfdayOfDay = cHalfdayOfDayField;
        fields.year = new BasicYearDateTimeField(this);
        fields.yearOfEra = new GJYearOfEraDateTimeField(fields.year, this);
        fields.centuryOfEra = new DividedDateTimeField(new OffsetDateTimeField(fields.yearOfEra, 99), DateTimeFieldType.centuryOfEra(), 100);
        fields.centuries = fields.centuryOfEra.getDurationField();
        fields.yearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField((DividedDateTimeField)fields.centuryOfEra), DateTimeFieldType.yearOfCentury(), 1);
        fields.era = new GJEraDateTimeField(this);
        fields.dayOfWeek = new GJDayOfWeekDateTimeField(this, fields.days);
        fields.dayOfMonth = new BasicDayOfMonthDateTimeField(this, fields.days);
        fields.dayOfYear = new BasicDayOfYearDateTimeField(this, fields.days);
        fields.monthOfYear = new GJMonthOfYearDateTimeField(this);
        fields.weekyear = new BasicWeekyearDateTimeField(this);
        fields.weekOfWeekyear = new BasicWeekOfWeekyearDateTimeField(this, fields.weeks);
        fields.weekyearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField(fields.weekyear, fields.centuries, DateTimeFieldType.weekyearOfCentury(), 100), DateTimeFieldType.weekyearOfCentury(), 1);
        fields.years = fields.year.getDurationField();
        fields.months = fields.monthOfYear.getDurationField();
        fields.weekyears = fields.weekyear.getDurationField();
    }

    abstract long calculateFirstDayOfYearMillis(int var1);

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block4: {
                block2: {
                    if (this == object) break block2;
                    if (object == null || this.getClass() != object.getClass()) break block3;
                    object = (BasicChronology)object;
                    if (this.getMinimumDaysInFirstWeek() != ((BasicChronology)object).getMinimumDaysInFirstWeek() || !this.getZone().equals(((BasicChronology)object).getZone())) break block4;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    abstract long getApproxMillisAtEpochDividedByTwo();

    abstract long getAverageMillisPerMonth();

    abstract long getAverageMillisPerYear();

    abstract long getAverageMillisPerYearDividedByTwo();

    int getDayOfMonth(long l) {
        int n = this.getYear(l);
        return this.getDayOfMonth(l, n, this.getMonthOfYear(l, n));
    }

    int getDayOfMonth(long l, int n) {
        return this.getDayOfMonth(l, n, this.getMonthOfYear(l, n));
    }

    int getDayOfMonth(long l, int n, int n2) {
        return (int)((l - (this.getYearMillis(n) + this.getTotalMillisByYearMonth(n, n2))) / 86400000L) + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    int getDayOfWeek(long l) {
        if (l >= 0L) {
            l /= 86400000L;
            return (int)((l + 3L) % 7L) + 1;
        } else {
            long l2;
            l = l2 = (l - 86399999L) / 86400000L;
            if (l2 >= -3L) return (int)((l + 3L) % 7L) + 1;
            return (int)((l2 + 4L) % 7L) + 7;
        }
    }

    int getDayOfYear(long l) {
        return this.getDayOfYear(l, this.getYear(l));
    }

    int getDayOfYear(long l, int n) {
        return (int)((l - this.getYearMillis(n)) / 86400000L) + 1;
    }

    int getDaysInMonthMax() {
        return 31;
    }

    int getDaysInMonthMax(long l) {
        int n = this.getYear(l);
        return this.getDaysInYearMonth(n, this.getMonthOfYear(l, n));
    }

    int getDaysInMonthMaxForSet(long l, int n) {
        return this.getDaysInMonthMax(l);
    }

    int getDaysInYear(int n) {
        if (this.isLeapYear(n)) {
            return 366;
        }
        return 365;
    }

    int getDaysInYearMax() {
        return 366;
    }

    abstract int getDaysInYearMonth(int var1, int var2);

    long getFirstWeekOfYearMillis(int n) {
        long l = this.getYearMillis(n);
        n = this.getDayOfWeek(l);
        if (n > 8 - this.iMinDaysInFirstWeek) {
            return l + (long)(8 - n) * 86400000L;
        }
        return l - (long)(n - 1) * 86400000L;
    }

    int getMaxMonth() {
        return 12;
    }

    abstract int getMaxYear();

    int getMillisOfDay(long l) {
        if (l >= 0L) {
            return (int)(l % 86400000L);
        }
        return 86399999 + (int)((1L + l) % 86400000L);
    }

    abstract int getMinYear();

    public int getMinimumDaysInFirstWeek() {
        return this.iMinDaysInFirstWeek;
    }

    int getMonthOfYear(long l) {
        return this.getMonthOfYear(l, this.getYear(l));
    }

    abstract int getMonthOfYear(long var1, int var3);

    abstract long getTotalMillisByYearMonth(int var1, int var2);

    int getWeekOfWeekyear(long l) {
        return this.getWeekOfWeekyear(l, this.getYear(l));
    }

    int getWeekOfWeekyear(long l, int n) {
        long l2 = this.getFirstWeekOfYearMillis(n);
        if (l < l2) {
            return this.getWeeksInYear(n - 1);
        }
        if (l >= this.getFirstWeekOfYearMillis(n + 1)) {
            return 1;
        }
        return (int)((l - l2) / 604800000L) + 1;
    }

    int getWeeksInYear(int n) {
        long l = this.getFirstWeekOfYearMillis(n);
        return (int)((this.getFirstWeekOfYearMillis(n + 1) - l) / 604800000L);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int getWeekyear(long l) {
        int n = this.getYear(l);
        int n2 = this.getWeekOfWeekyear(l, n);
        if (n2 == 1) {
            return this.getYear(604800000L + l);
        }
        if (n2 <= 51) return n;
        return this.getYear(l - 1209600000L);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int getYear(long l) {
        long l2;
        long l3 = 31536000000L;
        long l4 = this.getAverageMillisPerYearDividedByTwo();
        long l5 = l2 = (l >> 1) + this.getApproxMillisAtEpochDividedByTwo();
        if (l2 < 0L) {
            l5 = l2 - l4 + 1L;
        }
        int n = (int)(l5 / l4);
        l2 = this.getYearMillis(n);
        l5 = l - l2;
        if (l5 < 0L) {
            return n - 1;
        }
        int n2 = n;
        if (l5 < 31536000000L) return n2;
        l5 = l3;
        if (this.isLeapYear(n)) {
            l5 = 31622400000L;
        }
        n2 = n;
        if (l5 + l2 > l) return n2;
        return n + 1;
    }

    long getYearMillis(int n) {
        return this.getYearInfo((int)n).iFirstDayMillis;
    }

    long getYearMonthDayMillis(int n, int n2, int n3) {
        return this.getYearMillis(n) + this.getTotalMillisByYearMonth(n, n2) + (long)(n3 - 1) * 86400000L;
    }

    long getYearMonthMillis(int n, int n2) {
        return this.getYearMillis(n) + this.getTotalMillisByYearMonth(n, n2);
    }

    @Override
    public DateTimeZone getZone() {
        Chronology chronology = this.getBase();
        if (chronology != null) {
            return chronology.getZone();
        }
        return DateTimeZone.UTC;
    }

    public int hashCode() {
        return this.getClass().getName().hashCode() * 11 + this.getZone().hashCode() + this.getMinimumDaysInFirstWeek();
    }

    boolean isLeapDay(long l) {
        return false;
    }

    abstract boolean isLeapYear(int var1);

    abstract long setYear(long var1, int var3);

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(60);
        String string2 = this.getClass().getName();
        int n = string2.lastIndexOf(46);
        Object object = string2;
        if (n >= 0) {
            object = string2.substring(n + 1);
        }
        stringBuilder.append((String)object);
        stringBuilder.append('[');
        object = this.getZone();
        if (object != null) {
            stringBuilder.append(((DateTimeZone)object).getID());
        }
        if (this.getMinimumDaysInFirstWeek() != 4) {
            stringBuilder.append(",mdfw=");
            stringBuilder.append(this.getMinimumDaysInFirstWeek());
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private static class HalfdayField
    extends PreciseDateTimeField {
        HalfdayField() {
            super(DateTimeFieldType.halfdayOfDay(), cHalfdaysField, cDaysField);
        }

        @Override
        public String getAsText(int n, Locale locale) {
            return GJLocaleSymbols.forLocale(locale).halfdayValueToText(n);
        }

        @Override
        public int getMaximumTextLength(Locale locale) {
            return GJLocaleSymbols.forLocale(locale).getHalfdayMaxTextLength();
        }

        @Override
        public long set(long l, String string2, Locale locale) {
            return this.set(l, GJLocaleSymbols.forLocale(locale).halfdayTextToValue(string2));
        }
    }

    private static class YearInfo {
        public final long iFirstDayMillis;
        public final int iYear;

        YearInfo(int n, long l) {
            this.iYear = n;
            this.iFirstDayMillis = l;
        }
    }

}

