/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeUtils;
import org.joda.time.DurationFieldType;

public abstract class DateTimeFieldType
implements Serializable {
    private static final DateTimeFieldType CENTURY_OF_ERA_TYPE;
    private static final DateTimeFieldType CLOCKHOUR_OF_DAY_TYPE;
    private static final DateTimeFieldType CLOCKHOUR_OF_HALFDAY_TYPE;
    private static final DateTimeFieldType DAY_OF_MONTH_TYPE;
    private static final DateTimeFieldType DAY_OF_WEEK_TYPE;
    private static final DateTimeFieldType DAY_OF_YEAR_TYPE;
    private static final DateTimeFieldType ERA_TYPE;
    private static final DateTimeFieldType HALFDAY_OF_DAY_TYPE;
    private static final DateTimeFieldType HOUR_OF_DAY_TYPE;
    private static final DateTimeFieldType HOUR_OF_HALFDAY_TYPE;
    private static final DateTimeFieldType MILLIS_OF_DAY_TYPE;
    private static final DateTimeFieldType MILLIS_OF_SECOND_TYPE;
    private static final DateTimeFieldType MINUTE_OF_DAY_TYPE;
    private static final DateTimeFieldType MINUTE_OF_HOUR_TYPE;
    private static final DateTimeFieldType MONTH_OF_YEAR_TYPE;
    private static final DateTimeFieldType SECOND_OF_DAY_TYPE;
    private static final DateTimeFieldType SECOND_OF_MINUTE_TYPE;
    private static final DateTimeFieldType WEEKYEAR_OF_CENTURY_TYPE;
    private static final DateTimeFieldType WEEKYEAR_TYPE;
    private static final DateTimeFieldType WEEK_OF_WEEKYEAR_TYPE;
    private static final DateTimeFieldType YEAR_OF_CENTURY_TYPE;
    private static final DateTimeFieldType YEAR_OF_ERA_TYPE;
    private static final DateTimeFieldType YEAR_TYPE;
    private final String iName;

    static {
        ERA_TYPE = new StandardDateTimeFieldType("era", 1, DurationFieldType.eras(), null);
        YEAR_OF_ERA_TYPE = new StandardDateTimeFieldType("yearOfEra", 2, DurationFieldType.years(), DurationFieldType.eras());
        CENTURY_OF_ERA_TYPE = new StandardDateTimeFieldType("centuryOfEra", 3, DurationFieldType.centuries(), DurationFieldType.eras());
        YEAR_OF_CENTURY_TYPE = new StandardDateTimeFieldType("yearOfCentury", 4, DurationFieldType.years(), DurationFieldType.centuries());
        YEAR_TYPE = new StandardDateTimeFieldType("year", 5, DurationFieldType.years(), null);
        DAY_OF_YEAR_TYPE = new StandardDateTimeFieldType("dayOfYear", 6, DurationFieldType.days(), DurationFieldType.years());
        MONTH_OF_YEAR_TYPE = new StandardDateTimeFieldType("monthOfYear", 7, DurationFieldType.months(), DurationFieldType.years());
        DAY_OF_MONTH_TYPE = new StandardDateTimeFieldType("dayOfMonth", 8, DurationFieldType.days(), DurationFieldType.months());
        WEEKYEAR_OF_CENTURY_TYPE = new StandardDateTimeFieldType("weekyearOfCentury", 9, DurationFieldType.weekyears(), DurationFieldType.centuries());
        WEEKYEAR_TYPE = new StandardDateTimeFieldType("weekyear", 10, DurationFieldType.weekyears(), null);
        WEEK_OF_WEEKYEAR_TYPE = new StandardDateTimeFieldType("weekOfWeekyear", 11, DurationFieldType.weeks(), DurationFieldType.weekyears());
        DAY_OF_WEEK_TYPE = new StandardDateTimeFieldType("dayOfWeek", 12, DurationFieldType.days(), DurationFieldType.weeks());
        HALFDAY_OF_DAY_TYPE = new StandardDateTimeFieldType("halfdayOfDay", 13, DurationFieldType.halfdays(), DurationFieldType.days());
        HOUR_OF_HALFDAY_TYPE = new StandardDateTimeFieldType("hourOfHalfday", 14, DurationFieldType.hours(), DurationFieldType.halfdays());
        CLOCKHOUR_OF_HALFDAY_TYPE = new StandardDateTimeFieldType("clockhourOfHalfday", 15, DurationFieldType.hours(), DurationFieldType.halfdays());
        CLOCKHOUR_OF_DAY_TYPE = new StandardDateTimeFieldType("clockhourOfDay", 16, DurationFieldType.hours(), DurationFieldType.days());
        HOUR_OF_DAY_TYPE = new StandardDateTimeFieldType("hourOfDay", 17, DurationFieldType.hours(), DurationFieldType.days());
        MINUTE_OF_DAY_TYPE = new StandardDateTimeFieldType("minuteOfDay", 18, DurationFieldType.minutes(), DurationFieldType.days());
        MINUTE_OF_HOUR_TYPE = new StandardDateTimeFieldType("minuteOfHour", 19, DurationFieldType.minutes(), DurationFieldType.hours());
        SECOND_OF_DAY_TYPE = new StandardDateTimeFieldType("secondOfDay", 20, DurationFieldType.seconds(), DurationFieldType.days());
        SECOND_OF_MINUTE_TYPE = new StandardDateTimeFieldType("secondOfMinute", 21, DurationFieldType.seconds(), DurationFieldType.minutes());
        MILLIS_OF_DAY_TYPE = new StandardDateTimeFieldType("millisOfDay", 22, DurationFieldType.millis(), DurationFieldType.days());
        MILLIS_OF_SECOND_TYPE = new StandardDateTimeFieldType("millisOfSecond", 23, DurationFieldType.millis(), DurationFieldType.seconds());
    }

    protected DateTimeFieldType(String string2) {
        this.iName = string2;
    }

    public static DateTimeFieldType centuryOfEra() {
        return CENTURY_OF_ERA_TYPE;
    }

    public static DateTimeFieldType clockhourOfDay() {
        return CLOCKHOUR_OF_DAY_TYPE;
    }

    public static DateTimeFieldType clockhourOfHalfday() {
        return CLOCKHOUR_OF_HALFDAY_TYPE;
    }

    public static DateTimeFieldType dayOfMonth() {
        return DAY_OF_MONTH_TYPE;
    }

    public static DateTimeFieldType dayOfWeek() {
        return DAY_OF_WEEK_TYPE;
    }

    public static DateTimeFieldType dayOfYear() {
        return DAY_OF_YEAR_TYPE;
    }

    public static DateTimeFieldType era() {
        return ERA_TYPE;
    }

    public static DateTimeFieldType halfdayOfDay() {
        return HALFDAY_OF_DAY_TYPE;
    }

    public static DateTimeFieldType hourOfDay() {
        return HOUR_OF_DAY_TYPE;
    }

    public static DateTimeFieldType hourOfHalfday() {
        return HOUR_OF_HALFDAY_TYPE;
    }

    public static DateTimeFieldType millisOfDay() {
        return MILLIS_OF_DAY_TYPE;
    }

    public static DateTimeFieldType millisOfSecond() {
        return MILLIS_OF_SECOND_TYPE;
    }

    public static DateTimeFieldType minuteOfDay() {
        return MINUTE_OF_DAY_TYPE;
    }

    public static DateTimeFieldType minuteOfHour() {
        return MINUTE_OF_HOUR_TYPE;
    }

    public static DateTimeFieldType monthOfYear() {
        return MONTH_OF_YEAR_TYPE;
    }

    public static DateTimeFieldType secondOfDay() {
        return SECOND_OF_DAY_TYPE;
    }

    public static DateTimeFieldType secondOfMinute() {
        return SECOND_OF_MINUTE_TYPE;
    }

    public static DateTimeFieldType weekOfWeekyear() {
        return WEEK_OF_WEEKYEAR_TYPE;
    }

    public static DateTimeFieldType weekyear() {
        return WEEKYEAR_TYPE;
    }

    public static DateTimeFieldType weekyearOfCentury() {
        return WEEKYEAR_OF_CENTURY_TYPE;
    }

    public static DateTimeFieldType year() {
        return YEAR_TYPE;
    }

    public static DateTimeFieldType yearOfCentury() {
        return YEAR_OF_CENTURY_TYPE;
    }

    public static DateTimeFieldType yearOfEra() {
        return YEAR_OF_ERA_TYPE;
    }

    public abstract DurationFieldType getDurationType();

    public abstract DateTimeField getField(Chronology var1);

    public String getName() {
        return this.iName;
    }

    public String toString() {
        return this.getName();
    }

    private static class StandardDateTimeFieldType
    extends DateTimeFieldType {
        private final byte iOrdinal;
        private final transient DurationFieldType iRangeType;
        private final transient DurationFieldType iUnitType;

        StandardDateTimeFieldType(String string2, byte by, DurationFieldType durationFieldType, DurationFieldType durationFieldType2) {
            super(string2);
            this.iOrdinal = by;
            this.iUnitType = durationFieldType;
            this.iRangeType = durationFieldType2;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (this == object) break block4;
                    if (!(object instanceof StandardDateTimeFieldType)) {
                        return false;
                    }
                    if (this.iOrdinal != ((StandardDateTimeFieldType)object).iOrdinal) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public DurationFieldType getDurationType() {
            return this.iUnitType;
        }

        @Override
        public DateTimeField getField(Chronology chronology) {
            chronology = DateTimeUtils.getChronology(chronology);
            switch (this.iOrdinal) {
                default: {
                    throw new InternalError();
                }
                case 1: {
                    return chronology.era();
                }
                case 2: {
                    return chronology.yearOfEra();
                }
                case 3: {
                    return chronology.centuryOfEra();
                }
                case 4: {
                    return chronology.yearOfCentury();
                }
                case 5: {
                    return chronology.year();
                }
                case 6: {
                    return chronology.dayOfYear();
                }
                case 7: {
                    return chronology.monthOfYear();
                }
                case 8: {
                    return chronology.dayOfMonth();
                }
                case 9: {
                    return chronology.weekyearOfCentury();
                }
                case 10: {
                    return chronology.weekyear();
                }
                case 11: {
                    return chronology.weekOfWeekyear();
                }
                case 12: {
                    return chronology.dayOfWeek();
                }
                case 13: {
                    return chronology.halfdayOfDay();
                }
                case 14: {
                    return chronology.hourOfHalfday();
                }
                case 15: {
                    return chronology.clockhourOfHalfday();
                }
                case 16: {
                    return chronology.clockhourOfDay();
                }
                case 17: {
                    return chronology.hourOfDay();
                }
                case 18: {
                    return chronology.minuteOfDay();
                }
                case 19: {
                    return chronology.minuteOfHour();
                }
                case 20: {
                    return chronology.secondOfDay();
                }
                case 21: {
                    return chronology.secondOfMinute();
                }
                case 22: {
                    return chronology.millisOfDay();
                }
                case 23: 
            }
            return chronology.millisOfSecond();
        }

        public int hashCode() {
            return 1 << this.iOrdinal;
        }
    }

}

