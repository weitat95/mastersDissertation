/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import java.util.HashMap;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.IllegalInstantException;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.BaseDurationField;

public final class ZonedChronology
extends AssembledChronology {
    private ZonedChronology(Chronology chronology, DateTimeZone dateTimeZone) {
        super(chronology, dateTimeZone);
    }

    private DateTimeField convertField(DateTimeField dateTimeField, HashMap<Object, Object> hashMap) {
        if (dateTimeField == null || !dateTimeField.isSupported()) {
            return dateTimeField;
        }
        if (hashMap.containsKey(dateTimeField)) {
            return (DateTimeField)hashMap.get(dateTimeField);
        }
        ZonedDateTimeField zonedDateTimeField = new ZonedDateTimeField(dateTimeField, this.getZone(), this.convertField(dateTimeField.getDurationField(), hashMap), this.convertField(dateTimeField.getRangeDurationField(), hashMap), this.convertField(dateTimeField.getLeapDurationField(), hashMap));
        hashMap.put(dateTimeField, zonedDateTimeField);
        return zonedDateTimeField;
    }

    private DurationField convertField(DurationField durationField, HashMap<Object, Object> hashMap) {
        if (durationField == null || !durationField.isSupported()) {
            return durationField;
        }
        if (hashMap.containsKey(durationField)) {
            return (DurationField)hashMap.get(durationField);
        }
        ZonedDurationField zonedDurationField = new ZonedDurationField(durationField, this.getZone());
        hashMap.put(durationField, zonedDurationField);
        return zonedDurationField;
    }

    public static ZonedChronology getInstance(Chronology chronology, DateTimeZone dateTimeZone) {
        if (chronology == null) {
            throw new IllegalArgumentException("Must supply a chronology");
        }
        if ((chronology = chronology.withUTC()) == null) {
            throw new IllegalArgumentException("UTC chronology must not be null");
        }
        if (dateTimeZone == null) {
            throw new IllegalArgumentException("DateTimeZone must not be null");
        }
        return new ZonedChronology(chronology, dateTimeZone);
    }

    static boolean useTimeArithmetic(DurationField durationField) {
        return durationField != null && durationField.getUnitMillis() < 43200000L;
    }

    @Override
    protected void assemble(AssembledChronology.Fields fields) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        fields.eras = this.convertField(fields.eras, hashMap);
        fields.centuries = this.convertField(fields.centuries, hashMap);
        fields.years = this.convertField(fields.years, hashMap);
        fields.months = this.convertField(fields.months, hashMap);
        fields.weekyears = this.convertField(fields.weekyears, hashMap);
        fields.weeks = this.convertField(fields.weeks, hashMap);
        fields.days = this.convertField(fields.days, hashMap);
        fields.halfdays = this.convertField(fields.halfdays, hashMap);
        fields.hours = this.convertField(fields.hours, hashMap);
        fields.minutes = this.convertField(fields.minutes, hashMap);
        fields.seconds = this.convertField(fields.seconds, hashMap);
        fields.millis = this.convertField(fields.millis, hashMap);
        fields.year = this.convertField(fields.year, hashMap);
        fields.yearOfEra = this.convertField(fields.yearOfEra, hashMap);
        fields.yearOfCentury = this.convertField(fields.yearOfCentury, hashMap);
        fields.centuryOfEra = this.convertField(fields.centuryOfEra, hashMap);
        fields.era = this.convertField(fields.era, hashMap);
        fields.dayOfWeek = this.convertField(fields.dayOfWeek, hashMap);
        fields.dayOfMonth = this.convertField(fields.dayOfMonth, hashMap);
        fields.dayOfYear = this.convertField(fields.dayOfYear, hashMap);
        fields.monthOfYear = this.convertField(fields.monthOfYear, hashMap);
        fields.weekOfWeekyear = this.convertField(fields.weekOfWeekyear, hashMap);
        fields.weekyear = this.convertField(fields.weekyear, hashMap);
        fields.weekyearOfCentury = this.convertField(fields.weekyearOfCentury, hashMap);
        fields.millisOfSecond = this.convertField(fields.millisOfSecond, hashMap);
        fields.millisOfDay = this.convertField(fields.millisOfDay, hashMap);
        fields.secondOfMinute = this.convertField(fields.secondOfMinute, hashMap);
        fields.secondOfDay = this.convertField(fields.secondOfDay, hashMap);
        fields.minuteOfHour = this.convertField(fields.minuteOfHour, hashMap);
        fields.minuteOfDay = this.convertField(fields.minuteOfDay, hashMap);
        fields.hourOfDay = this.convertField(fields.hourOfDay, hashMap);
        fields.hourOfHalfday = this.convertField(fields.hourOfHalfday, hashMap);
        fields.clockhourOfDay = this.convertField(fields.clockhourOfDay, hashMap);
        fields.clockhourOfHalfday = this.convertField(fields.clockhourOfHalfday, hashMap);
        fields.halfdayOfDay = this.convertField(fields.halfdayOfDay, hashMap);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof ZonedChronology)) {
                    return false;
                }
                object = (ZonedChronology)object;
                if (!this.getBase().equals(((AssembledChronology)object).getBase()) || !this.getZone().equals(((ZonedChronology)object).getZone())) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public DateTimeZone getZone() {
        return (DateTimeZone)this.getParam();
    }

    public int hashCode() {
        return 326565 + this.getZone().hashCode() * 11 + this.getBase().hashCode() * 7;
    }

    public String toString() {
        return "ZonedChronology[" + this.getBase() + ", " + this.getZone().getID() + ']';
    }

    @Override
    public Chronology withUTC() {
        return this.getBase();
    }

    @Override
    public Chronology withZone(DateTimeZone dateTimeZone) {
        DateTimeZone dateTimeZone2 = dateTimeZone;
        if (dateTimeZone == null) {
            dateTimeZone2 = DateTimeZone.getDefault();
        }
        if (dateTimeZone2 == this.getParam()) {
            return this;
        }
        if (dateTimeZone2 == DateTimeZone.UTC) {
            return this.getBase();
        }
        return new ZonedChronology(this.getBase(), dateTimeZone2);
    }

    static final class ZonedDateTimeField
    extends BaseDateTimeField {
        final DurationField iDurationField;
        final DateTimeField iField;
        final DurationField iLeapDurationField;
        final DurationField iRangeDurationField;
        final boolean iTimeField;
        final DateTimeZone iZone;

        ZonedDateTimeField(DateTimeField dateTimeField, DateTimeZone dateTimeZone, DurationField durationField, DurationField durationField2, DurationField durationField3) {
            super(dateTimeField.getType());
            if (!dateTimeField.isSupported()) {
                throw new IllegalArgumentException();
            }
            this.iField = dateTimeField;
            this.iZone = dateTimeZone;
            this.iDurationField = durationField;
            this.iTimeField = ZonedChronology.useTimeArithmetic(durationField);
            this.iRangeDurationField = durationField2;
            this.iLeapDurationField = durationField3;
        }

        private int getOffsetToAdd(long l) {
            int n = this.iZone.getOffset(l);
            if (((long)n + l ^ l) < 0L && ((long)n ^ l) >= 0L) {
                throw new ArithmeticException("Adding time zone offset caused overflow");
            }
            return n;
        }

        @Override
        public long add(long l, int n) {
            if (this.iTimeField) {
                int n2 = this.getOffsetToAdd(l);
                return this.iField.add((long)n2 + l, n) - (long)n2;
            }
            long l2 = this.iZone.convertUTCToLocal(l);
            l2 = this.iField.add(l2, n);
            return this.iZone.convertLocalToUTC(l2, false, l);
        }

        @Override
        public long add(long l, long l2) {
            if (this.iTimeField) {
                int n = this.getOffsetToAdd(l);
                return this.iField.add((long)n + l, l2) - (long)n;
            }
            long l3 = this.iZone.convertUTCToLocal(l);
            l2 = this.iField.add(l3, l2);
            return this.iZone.convertLocalToUTC(l2, false, l);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (this == object) break block4;
                    if (!(object instanceof ZonedDateTimeField)) {
                        return false;
                    }
                    object = (ZonedDateTimeField)object;
                    if (!this.iField.equals(((ZonedDateTimeField)object).iField) || !this.iZone.equals(((ZonedDateTimeField)object).iZone) || !this.iDurationField.equals(((ZonedDateTimeField)object).iDurationField) || !this.iRangeDurationField.equals(((ZonedDateTimeField)object).iRangeDurationField)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public int get(long l) {
            l = this.iZone.convertUTCToLocal(l);
            return this.iField.get(l);
        }

        @Override
        public String getAsShortText(int n, Locale locale) {
            return this.iField.getAsShortText(n, locale);
        }

        @Override
        public String getAsShortText(long l, Locale locale) {
            l = this.iZone.convertUTCToLocal(l);
            return this.iField.getAsShortText(l, locale);
        }

        @Override
        public String getAsText(int n, Locale locale) {
            return this.iField.getAsText(n, locale);
        }

        @Override
        public String getAsText(long l, Locale locale) {
            l = this.iZone.convertUTCToLocal(l);
            return this.iField.getAsText(l, locale);
        }

        @Override
        public final DurationField getDurationField() {
            return this.iDurationField;
        }

        @Override
        public final DurationField getLeapDurationField() {
            return this.iLeapDurationField;
        }

        @Override
        public int getMaximumTextLength(Locale locale) {
            return this.iField.getMaximumTextLength(locale);
        }

        @Override
        public int getMaximumValue() {
            return this.iField.getMaximumValue();
        }

        @Override
        public int getMaximumValue(long l) {
            l = this.iZone.convertUTCToLocal(l);
            return this.iField.getMaximumValue(l);
        }

        @Override
        public int getMinimumValue() {
            return this.iField.getMinimumValue();
        }

        @Override
        public final DurationField getRangeDurationField() {
            return this.iRangeDurationField;
        }

        public int hashCode() {
            return this.iField.hashCode() ^ this.iZone.hashCode();
        }

        @Override
        public boolean isLeap(long l) {
            l = this.iZone.convertUTCToLocal(l);
            return this.iField.isLeap(l);
        }

        @Override
        public long remainder(long l) {
            l = this.iZone.convertUTCToLocal(l);
            return this.iField.remainder(l);
        }

        @Override
        public long roundCeiling(long l) {
            if (this.iTimeField) {
                int n = this.getOffsetToAdd(l);
                return this.iField.roundCeiling((long)n + l) - (long)n;
            }
            long l2 = this.iZone.convertUTCToLocal(l);
            l2 = this.iField.roundCeiling(l2);
            return this.iZone.convertLocalToUTC(l2, false, l);
        }

        @Override
        public long roundFloor(long l) {
            if (this.iTimeField) {
                int n = this.getOffsetToAdd(l);
                return this.iField.roundFloor((long)n + l) - (long)n;
            }
            long l2 = this.iZone.convertUTCToLocal(l);
            l2 = this.iField.roundFloor(l2);
            return this.iZone.convertLocalToUTC(l2, false, l);
        }

        @Override
        public long set(long l, int n) {
            long l2 = this.iZone.convertUTCToLocal(l);
            l = this.iZone.convertLocalToUTC(l2 = this.iField.set(l2, n), false, l);
            if (this.get(l) != n) {
                IllegalInstantException illegalInstantException = new IllegalInstantException(l2, this.iZone.getID());
                IllegalFieldValueException illegalFieldValueException = new IllegalFieldValueException(this.iField.getType(), n, illegalInstantException.getMessage());
                illegalFieldValueException.initCause(illegalInstantException);
                throw illegalFieldValueException;
            }
            return l;
        }

        @Override
        public long set(long l, String string2, Locale locale) {
            long l2 = this.iZone.convertUTCToLocal(l);
            l2 = this.iField.set(l2, string2, locale);
            return this.iZone.convertLocalToUTC(l2, false, l);
        }
    }

    static class ZonedDurationField
    extends BaseDurationField {
        final DurationField iField;
        final boolean iTimeField;
        final DateTimeZone iZone;

        ZonedDurationField(DurationField durationField, DateTimeZone dateTimeZone) {
            super(durationField.getType());
            if (!durationField.isSupported()) {
                throw new IllegalArgumentException();
            }
            this.iField = durationField;
            this.iTimeField = ZonedChronology.useTimeArithmetic(durationField);
            this.iZone = dateTimeZone;
        }

        private int getOffsetFromLocalToSubtract(long l) {
            int n = this.iZone.getOffsetFromLocal(l);
            if ((l - (long)n ^ l) < 0L && ((long)n ^ l) < 0L) {
                throw new ArithmeticException("Subtracting time zone offset caused overflow");
            }
            return n;
        }

        private int getOffsetToAdd(long l) {
            int n = this.iZone.getOffset(l);
            if (((long)n + l ^ l) < 0L && ((long)n ^ l) >= 0L) {
                throw new ArithmeticException("Adding time zone offset caused overflow");
            }
            return n;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public long add(long l, int n) {
            int n2 = this.getOffsetToAdd(l);
            l = this.iField.add((long)n2 + l, n);
            if (this.iTimeField) {
                n = n2;
                do {
                    return l - (long)n;
                    break;
                } while (true);
            }
            n = this.getOffsetFromLocalToSubtract(l);
            return l - (long)n;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public long add(long l, long l2) {
            int n = this.getOffsetToAdd(l);
            l = this.iField.add((long)n + l, l2);
            if (this.iTimeField) {
                do {
                    return l - (long)n;
                    break;
                } while (true);
            }
            n = this.getOffsetFromLocalToSubtract(l);
            return l - (long)n;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (this == object) break block4;
                    if (!(object instanceof ZonedDurationField)) {
                        return false;
                    }
                    object = (ZonedDurationField)object;
                    if (!this.iField.equals(((ZonedDurationField)object).iField) || !this.iZone.equals(((ZonedDurationField)object).iZone)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public long getUnitMillis() {
            return this.iField.getUnitMillis();
        }

        public int hashCode() {
            return this.iField.hashCode() ^ this.iZone.hashCode();
        }

        @Override
        public boolean isPrecise() {
            if (this.iTimeField) {
                return this.iField.isPrecise();
            }
            return this.iField.isPrecise() && this.iZone.isFixed();
        }
    }

}

