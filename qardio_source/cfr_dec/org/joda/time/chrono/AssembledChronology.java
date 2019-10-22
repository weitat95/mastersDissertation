/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.chrono.BaseChronology;

public abstract class AssembledChronology
extends BaseChronology {
    private final Chronology iBase;
    private transient int iBaseFlags;
    private transient DurationField iCenturies;
    private transient DateTimeField iCenturyOfEra;
    private transient DateTimeField iClockhourOfDay;
    private transient DateTimeField iClockhourOfHalfday;
    private transient DateTimeField iDayOfMonth;
    private transient DateTimeField iDayOfWeek;
    private transient DateTimeField iDayOfYear;
    private transient DurationField iDays;
    private transient DateTimeField iEra;
    private transient DurationField iEras;
    private transient DateTimeField iHalfdayOfDay;
    private transient DurationField iHalfdays;
    private transient DateTimeField iHourOfDay;
    private transient DateTimeField iHourOfHalfday;
    private transient DurationField iHours;
    private transient DurationField iMillis;
    private transient DateTimeField iMillisOfDay;
    private transient DateTimeField iMillisOfSecond;
    private transient DateTimeField iMinuteOfDay;
    private transient DateTimeField iMinuteOfHour;
    private transient DurationField iMinutes;
    private transient DateTimeField iMonthOfYear;
    private transient DurationField iMonths;
    private final Object iParam;
    private transient DateTimeField iSecondOfDay;
    private transient DateTimeField iSecondOfMinute;
    private transient DurationField iSeconds;
    private transient DateTimeField iWeekOfWeekyear;
    private transient DurationField iWeeks;
    private transient DateTimeField iWeekyear;
    private transient DateTimeField iWeekyearOfCentury;
    private transient DurationField iWeekyears;
    private transient DateTimeField iYear;
    private transient DateTimeField iYearOfCentury;
    private transient DateTimeField iYearOfEra;
    private transient DurationField iYears;

    protected AssembledChronology(Chronology chronology, Object object) {
        this.iBase = chronology;
        this.iParam = object;
        this.setFields();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setFields() {
        int n = 0;
        int n2 = 0;
        Fields fields = new Fields();
        if (this.iBase != null) {
            fields.copyFieldsFrom(this.iBase);
        }
        this.assemble(fields);
        Object object = fields.millis;
        if (object == null) {
            object = super.millis();
        }
        this.iMillis = object;
        object = fields.seconds;
        if (object == null) {
            object = super.seconds();
        }
        this.iSeconds = object;
        object = fields.minutes;
        if (object == null) {
            object = super.minutes();
        }
        this.iMinutes = object;
        object = fields.hours;
        if (object == null) {
            object = super.hours();
        }
        this.iHours = object;
        object = fields.halfdays;
        if (object == null) {
            object = super.halfdays();
        }
        this.iHalfdays = object;
        object = fields.days;
        if (object == null) {
            object = super.days();
        }
        this.iDays = object;
        object = fields.weeks;
        if (object == null) {
            object = super.weeks();
        }
        this.iWeeks = object;
        object = fields.weekyears;
        if (object == null) {
            object = super.weekyears();
        }
        this.iWeekyears = object;
        object = fields.months;
        if (object == null) {
            object = super.months();
        }
        this.iMonths = object;
        object = fields.years;
        if (object == null) {
            object = super.years();
        }
        this.iYears = object;
        object = fields.centuries;
        if (object == null) {
            object = super.centuries();
        }
        this.iCenturies = object;
        object = fields.eras;
        if (object == null) {
            object = super.eras();
        }
        this.iEras = object;
        object = fields.millisOfSecond;
        if (object == null) {
            object = super.millisOfSecond();
        }
        this.iMillisOfSecond = object;
        object = fields.millisOfDay;
        if (object == null) {
            object = super.millisOfDay();
        }
        this.iMillisOfDay = object;
        object = fields.secondOfMinute;
        if (object == null) {
            object = super.secondOfMinute();
        }
        this.iSecondOfMinute = object;
        object = fields.secondOfDay;
        if (object == null) {
            object = super.secondOfDay();
        }
        this.iSecondOfDay = object;
        object = fields.minuteOfHour;
        if (object == null) {
            object = super.minuteOfHour();
        }
        this.iMinuteOfHour = object;
        object = fields.minuteOfDay;
        if (object == null) {
            object = super.minuteOfDay();
        }
        this.iMinuteOfDay = object;
        object = fields.hourOfDay;
        if (object == null) {
            object = super.hourOfDay();
        }
        this.iHourOfDay = object;
        object = fields.clockhourOfDay;
        if (object == null) {
            object = super.clockhourOfDay();
        }
        this.iClockhourOfDay = object;
        object = fields.hourOfHalfday;
        if (object == null) {
            object = super.hourOfHalfday();
        }
        this.iHourOfHalfday = object;
        object = fields.clockhourOfHalfday;
        if (object == null) {
            object = super.clockhourOfHalfday();
        }
        this.iClockhourOfHalfday = object;
        object = fields.halfdayOfDay;
        if (object == null) {
            object = super.halfdayOfDay();
        }
        this.iHalfdayOfDay = object;
        object = fields.dayOfWeek;
        if (object == null) {
            object = super.dayOfWeek();
        }
        this.iDayOfWeek = object;
        object = fields.dayOfMonth;
        if (object == null) {
            object = super.dayOfMonth();
        }
        this.iDayOfMonth = object;
        object = fields.dayOfYear;
        if (object == null) {
            object = super.dayOfYear();
        }
        this.iDayOfYear = object;
        object = fields.weekOfWeekyear;
        if (object == null) {
            object = super.weekOfWeekyear();
        }
        this.iWeekOfWeekyear = object;
        object = fields.weekyear;
        if (object == null) {
            object = super.weekyear();
        }
        this.iWeekyear = object;
        object = fields.weekyearOfCentury;
        if (object == null) {
            object = super.weekyearOfCentury();
        }
        this.iWeekyearOfCentury = object;
        object = fields.monthOfYear;
        if (object == null) {
            object = super.monthOfYear();
        }
        this.iMonthOfYear = object;
        object = fields.year;
        if (object == null) {
            object = super.year();
        }
        this.iYear = object;
        object = fields.yearOfEra;
        if (object == null) {
            object = super.yearOfEra();
        }
        this.iYearOfEra = object;
        object = fields.yearOfCentury;
        if (object == null) {
            object = super.yearOfCentury();
        }
        this.iYearOfCentury = object;
        object = fields.centuryOfEra;
        if (object == null) {
            object = super.centuryOfEra();
        }
        this.iCenturyOfEra = object;
        object = fields.era;
        if (object == null) {
            object = super.era();
        }
        this.iEra = object;
        if (this.iBase != null) {
            n2 = this.iHourOfDay == this.iBase.hourOfDay() && this.iMinuteOfHour == this.iBase.minuteOfHour() && this.iSecondOfMinute == this.iBase.secondOfMinute() && this.iMillisOfSecond == this.iBase.millisOfSecond() ? 1 : 0;
            int n3 = this.iMillisOfDay == this.iBase.millisOfDay() ? 2 : 0;
            int n4 = n;
            if (this.iYear == this.iBase.year()) {
                n4 = n;
                if (this.iMonthOfYear == this.iBase.monthOfYear()) {
                    n4 = n;
                    if (this.iDayOfMonth == this.iBase.dayOfMonth()) {
                        n4 = 4;
                    }
                }
            }
            n2 = n4 | (n2 | n3);
        }
        this.iBaseFlags = n2;
    }

    protected abstract void assemble(Fields var1);

    @Override
    public final DurationField centuries() {
        return this.iCenturies;
    }

    @Override
    public final DateTimeField centuryOfEra() {
        return this.iCenturyOfEra;
    }

    @Override
    public final DateTimeField clockhourOfDay() {
        return this.iClockhourOfDay;
    }

    @Override
    public final DateTimeField clockhourOfHalfday() {
        return this.iClockhourOfHalfday;
    }

    @Override
    public final DateTimeField dayOfMonth() {
        return this.iDayOfMonth;
    }

    @Override
    public final DateTimeField dayOfWeek() {
        return this.iDayOfWeek;
    }

    @Override
    public final DateTimeField dayOfYear() {
        return this.iDayOfYear;
    }

    @Override
    public final DurationField days() {
        return this.iDays;
    }

    @Override
    public final DateTimeField era() {
        return this.iEra;
    }

    @Override
    public final DurationField eras() {
        return this.iEras;
    }

    protected final Chronology getBase() {
        return this.iBase;
    }

    protected final Object getParam() {
        return this.iParam;
    }

    @Override
    public DateTimeZone getZone() {
        Chronology chronology = this.iBase;
        if (chronology != null) {
            return chronology.getZone();
        }
        return null;
    }

    @Override
    public final DateTimeField halfdayOfDay() {
        return this.iHalfdayOfDay;
    }

    @Override
    public final DurationField halfdays() {
        return this.iHalfdays;
    }

    @Override
    public final DateTimeField hourOfDay() {
        return this.iHourOfDay;
    }

    @Override
    public final DateTimeField hourOfHalfday() {
        return this.iHourOfHalfday;
    }

    @Override
    public final DurationField hours() {
        return this.iHours;
    }

    @Override
    public final DurationField millis() {
        return this.iMillis;
    }

    @Override
    public final DateTimeField millisOfDay() {
        return this.iMillisOfDay;
    }

    @Override
    public final DateTimeField millisOfSecond() {
        return this.iMillisOfSecond;
    }

    @Override
    public final DateTimeField minuteOfDay() {
        return this.iMinuteOfDay;
    }

    @Override
    public final DateTimeField minuteOfHour() {
        return this.iMinuteOfHour;
    }

    @Override
    public final DurationField minutes() {
        return this.iMinutes;
    }

    @Override
    public final DateTimeField monthOfYear() {
        return this.iMonthOfYear;
    }

    @Override
    public final DurationField months() {
        return this.iMonths;
    }

    @Override
    public final DateTimeField secondOfDay() {
        return this.iSecondOfDay;
    }

    @Override
    public final DateTimeField secondOfMinute() {
        return this.iSecondOfMinute;
    }

    @Override
    public final DurationField seconds() {
        return this.iSeconds;
    }

    @Override
    public final DateTimeField weekOfWeekyear() {
        return this.iWeekOfWeekyear;
    }

    @Override
    public final DurationField weeks() {
        return this.iWeeks;
    }

    @Override
    public final DateTimeField weekyear() {
        return this.iWeekyear;
    }

    @Override
    public final DateTimeField weekyearOfCentury() {
        return this.iWeekyearOfCentury;
    }

    @Override
    public final DurationField weekyears() {
        return this.iWeekyears;
    }

    @Override
    public final DateTimeField year() {
        return this.iYear;
    }

    @Override
    public final DateTimeField yearOfCentury() {
        return this.iYearOfCentury;
    }

    @Override
    public final DateTimeField yearOfEra() {
        return this.iYearOfEra;
    }

    @Override
    public final DurationField years() {
        return this.iYears;
    }

    public static final class Fields {
        public DurationField centuries;
        public DateTimeField centuryOfEra;
        public DateTimeField clockhourOfDay;
        public DateTimeField clockhourOfHalfday;
        public DateTimeField dayOfMonth;
        public DateTimeField dayOfWeek;
        public DateTimeField dayOfYear;
        public DurationField days;
        public DateTimeField era;
        public DurationField eras;
        public DateTimeField halfdayOfDay;
        public DurationField halfdays;
        public DateTimeField hourOfDay;
        public DateTimeField hourOfHalfday;
        public DurationField hours;
        public DurationField millis;
        public DateTimeField millisOfDay;
        public DateTimeField millisOfSecond;
        public DateTimeField minuteOfDay;
        public DateTimeField minuteOfHour;
        public DurationField minutes;
        public DateTimeField monthOfYear;
        public DurationField months;
        public DateTimeField secondOfDay;
        public DateTimeField secondOfMinute;
        public DurationField seconds;
        public DateTimeField weekOfWeekyear;
        public DurationField weeks;
        public DateTimeField weekyear;
        public DateTimeField weekyearOfCentury;
        public DurationField weekyears;
        public DateTimeField year;
        public DateTimeField yearOfCentury;
        public DateTimeField yearOfEra;
        public DurationField years;

        Fields() {
        }

        private static boolean isSupported(DateTimeField dateTimeField) {
            if (dateTimeField == null) {
                return false;
            }
            return dateTimeField.isSupported();
        }

        private static boolean isSupported(DurationField durationField) {
            if (durationField == null) {
                return false;
            }
            return durationField.isSupported();
        }

        public void copyFieldsFrom(Chronology object) {
            Object object2 = ((Chronology)object).millis();
            if (Fields.isSupported((DurationField)object2)) {
                this.millis = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).seconds()))) {
                this.seconds = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).minutes()))) {
                this.minutes = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).hours()))) {
                this.hours = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).halfdays()))) {
                this.halfdays = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).days()))) {
                this.days = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).weeks()))) {
                this.weeks = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).weekyears()))) {
                this.weekyears = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).months()))) {
                this.months = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).years()))) {
                this.years = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).centuries()))) {
                this.centuries = object2;
            }
            if (Fields.isSupported((DurationField)(object2 = ((Chronology)object).eras()))) {
                this.eras = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).millisOfSecond()))) {
                this.millisOfSecond = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).millisOfDay()))) {
                this.millisOfDay = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).secondOfMinute()))) {
                this.secondOfMinute = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).secondOfDay()))) {
                this.secondOfDay = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).minuteOfHour()))) {
                this.minuteOfHour = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).minuteOfDay()))) {
                this.minuteOfDay = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).hourOfDay()))) {
                this.hourOfDay = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).clockhourOfDay()))) {
                this.clockhourOfDay = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).hourOfHalfday()))) {
                this.hourOfHalfday = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).clockhourOfHalfday()))) {
                this.clockhourOfHalfday = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).halfdayOfDay()))) {
                this.halfdayOfDay = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).dayOfWeek()))) {
                this.dayOfWeek = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).dayOfMonth()))) {
                this.dayOfMonth = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).dayOfYear()))) {
                this.dayOfYear = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).weekOfWeekyear()))) {
                this.weekOfWeekyear = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).weekyear()))) {
                this.weekyear = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).weekyearOfCentury()))) {
                this.weekyearOfCentury = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).monthOfYear()))) {
                this.monthOfYear = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).year()))) {
                this.year = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).yearOfEra()))) {
                this.yearOfEra = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).yearOfCentury()))) {
                this.yearOfCentury = object2;
            }
            if (Fields.isSupported((DateTimeField)(object2 = ((Chronology)object).centuryOfEra()))) {
                this.centuryOfEra = object2;
            }
            if (Fields.isSupported((DateTimeField)(object = ((Chronology)object).era()))) {
                this.era = object;
            }
        }
    }

}

