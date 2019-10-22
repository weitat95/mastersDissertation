/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

public final class UnsupportedDateTimeField
extends DateTimeField
implements Serializable {
    private static HashMap<DateTimeFieldType, UnsupportedDateTimeField> cCache;
    private final DurationField iDurationField;
    private final DateTimeFieldType iType;

    private UnsupportedDateTimeField(DateTimeFieldType dateTimeFieldType, DurationField durationField) {
        if (dateTimeFieldType == null || durationField == null) {
            throw new IllegalArgumentException();
        }
        this.iType = dateTimeFieldType;
        this.iDurationField = durationField;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static UnsupportedDateTimeField getInstance(DateTimeFieldType dateTimeFieldType, DurationField durationField) {
        synchronized (UnsupportedDateTimeField.class) {
            UnsupportedDateTimeField unsupportedDateTimeField;
            void var1_1;
            UnsupportedDateTimeField unsupportedDateTimeField2;
            if (cCache == null) {
                cCache = new HashMap(7);
                unsupportedDateTimeField = null;
            } else {
                unsupportedDateTimeField = unsupportedDateTimeField2 = cCache.get(dateTimeFieldType);
                if (unsupportedDateTimeField2 != null) {
                    DurationField durationField2 = unsupportedDateTimeField2.getDurationField();
                    unsupportedDateTimeField = unsupportedDateTimeField2;
                    if (durationField2 != var1_1) {
                        unsupportedDateTimeField = null;
                    }
                }
            }
            unsupportedDateTimeField2 = unsupportedDateTimeField;
            if (unsupportedDateTimeField == null) {
                unsupportedDateTimeField2 = new UnsupportedDateTimeField(dateTimeFieldType, (DurationField)var1_1);
                cCache.put(dateTimeFieldType, unsupportedDateTimeField2);
            }
            return unsupportedDateTimeField2;
        }
    }

    private UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException(this.iType + " field is unsupported");
    }

    @Override
    public long add(long l, int n) {
        return this.getDurationField().add(l, n);
    }

    @Override
    public long add(long l, long l2) {
        return this.getDurationField().add(l, l2);
    }

    @Override
    public int get(long l) {
        throw this.unsupported();
    }

    @Override
    public String getAsShortText(int n, Locale locale) {
        throw this.unsupported();
    }

    @Override
    public String getAsShortText(long l, Locale locale) {
        throw this.unsupported();
    }

    @Override
    public String getAsText(int n, Locale locale) {
        throw this.unsupported();
    }

    @Override
    public String getAsText(long l, Locale locale) {
        throw this.unsupported();
    }

    @Override
    public DurationField getDurationField() {
        return this.iDurationField;
    }

    @Override
    public DurationField getLeapDurationField() {
        return null;
    }

    @Override
    public int getMaximumTextLength(Locale locale) {
        throw this.unsupported();
    }

    @Override
    public int getMaximumValue() {
        throw this.unsupported();
    }

    @Override
    public int getMaximumValue(long l) {
        throw this.unsupported();
    }

    @Override
    public int getMinimumValue() {
        throw this.unsupported();
    }

    @Override
    public String getName() {
        return this.iType.getName();
    }

    @Override
    public DurationField getRangeDurationField() {
        return null;
    }

    @Override
    public DateTimeFieldType getType() {
        return this.iType;
    }

    @Override
    public boolean isLeap(long l) {
        throw this.unsupported();
    }

    @Override
    public boolean isSupported() {
        return false;
    }

    @Override
    public long remainder(long l) {
        throw this.unsupported();
    }

    @Override
    public long roundCeiling(long l) {
        throw this.unsupported();
    }

    @Override
    public long roundFloor(long l) {
        throw this.unsupported();
    }

    @Override
    public long roundHalfCeiling(long l) {
        throw this.unsupported();
    }

    @Override
    public long roundHalfEven(long l) {
        throw this.unsupported();
    }

    @Override
    public long roundHalfFloor(long l) {
        throw this.unsupported();
    }

    @Override
    public long set(long l, int n) {
        throw this.unsupported();
    }

    @Override
    public long set(long l, String string2, Locale locale) {
        throw this.unsupported();
    }

    public String toString() {
        return "UnsupportedDateTimeField";
    }
}

