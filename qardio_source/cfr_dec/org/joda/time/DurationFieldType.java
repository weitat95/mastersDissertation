/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.DurationField;

public abstract class DurationFieldType
implements Serializable {
    static final DurationFieldType CENTURIES_TYPE;
    static final DurationFieldType DAYS_TYPE;
    static final DurationFieldType ERAS_TYPE;
    static final DurationFieldType HALFDAYS_TYPE;
    static final DurationFieldType HOURS_TYPE;
    static final DurationFieldType MILLIS_TYPE;
    static final DurationFieldType MINUTES_TYPE;
    static final DurationFieldType MONTHS_TYPE;
    static final DurationFieldType SECONDS_TYPE;
    static final DurationFieldType WEEKS_TYPE;
    static final DurationFieldType WEEKYEARS_TYPE;
    static final DurationFieldType YEARS_TYPE;
    private final String iName;

    static {
        ERAS_TYPE = new StandardDurationFieldType("eras", 1);
        CENTURIES_TYPE = new StandardDurationFieldType("centuries", 2);
        WEEKYEARS_TYPE = new StandardDurationFieldType("weekyears", 3);
        YEARS_TYPE = new StandardDurationFieldType("years", 4);
        MONTHS_TYPE = new StandardDurationFieldType("months", 5);
        WEEKS_TYPE = new StandardDurationFieldType("weeks", 6);
        DAYS_TYPE = new StandardDurationFieldType("days", 7);
        HALFDAYS_TYPE = new StandardDurationFieldType("halfdays", 8);
        HOURS_TYPE = new StandardDurationFieldType("hours", 9);
        MINUTES_TYPE = new StandardDurationFieldType("minutes", 10);
        SECONDS_TYPE = new StandardDurationFieldType("seconds", 11);
        MILLIS_TYPE = new StandardDurationFieldType("millis", 12);
    }

    protected DurationFieldType(String string2) {
        this.iName = string2;
    }

    public static DurationFieldType centuries() {
        return CENTURIES_TYPE;
    }

    public static DurationFieldType days() {
        return DAYS_TYPE;
    }

    public static DurationFieldType eras() {
        return ERAS_TYPE;
    }

    public static DurationFieldType halfdays() {
        return HALFDAYS_TYPE;
    }

    public static DurationFieldType hours() {
        return HOURS_TYPE;
    }

    public static DurationFieldType millis() {
        return MILLIS_TYPE;
    }

    public static DurationFieldType minutes() {
        return MINUTES_TYPE;
    }

    public static DurationFieldType months() {
        return MONTHS_TYPE;
    }

    public static DurationFieldType seconds() {
        return SECONDS_TYPE;
    }

    public static DurationFieldType weeks() {
        return WEEKS_TYPE;
    }

    public static DurationFieldType weekyears() {
        return WEEKYEARS_TYPE;
    }

    public static DurationFieldType years() {
        return YEARS_TYPE;
    }

    public abstract DurationField getField(Chronology var1);

    public String getName() {
        return this.iName;
    }

    public String toString() {
        return this.getName();
    }

    private static class StandardDurationFieldType
    extends DurationFieldType {
        private final byte iOrdinal;

        StandardDurationFieldType(String string2, byte by) {
            super(string2);
            this.iOrdinal = by;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (this == object) break block4;
                    if (!(object instanceof StandardDurationFieldType)) {
                        return false;
                    }
                    if (this.iOrdinal != ((StandardDurationFieldType)object).iOrdinal) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public DurationField getField(Chronology chronology) {
            chronology = DateTimeUtils.getChronology(chronology);
            switch (this.iOrdinal) {
                default: {
                    throw new InternalError();
                }
                case 1: {
                    return chronology.eras();
                }
                case 2: {
                    return chronology.centuries();
                }
                case 3: {
                    return chronology.weekyears();
                }
                case 4: {
                    return chronology.years();
                }
                case 5: {
                    return chronology.months();
                }
                case 6: {
                    return chronology.weeks();
                }
                case 7: {
                    return chronology.days();
                }
                case 8: {
                    return chronology.halfdays();
                }
                case 9: {
                    return chronology.hours();
                }
                case 10: {
                    return chronology.minutes();
                }
                case 11: {
                    return chronology.seconds();
                }
                case 12: 
            }
            return chronology.millis();
        }

        public int hashCode() {
            return 1 << this.iOrdinal;
        }
    }

}

