/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.FieldUtils;

public abstract class PreciseDurationDateTimeField
extends BaseDateTimeField {
    private final DurationField iUnitField;
    final long iUnitMillis;

    public PreciseDurationDateTimeField(DateTimeFieldType dateTimeFieldType, DurationField durationField) {
        super(dateTimeFieldType);
        if (!durationField.isPrecise()) {
            throw new IllegalArgumentException("Unit duration field must be precise");
        }
        this.iUnitMillis = durationField.getUnitMillis();
        if (this.iUnitMillis < 1L) {
            throw new IllegalArgumentException("The unit milliseconds must be at least 1");
        }
        this.iUnitField = durationField;
    }

    @Override
    public DurationField getDurationField() {
        return this.iUnitField;
    }

    protected int getMaximumValueForSet(long l, int n) {
        return this.getMaximumValue(l);
    }

    @Override
    public int getMinimumValue() {
        return 0;
    }

    public final long getUnitMillis() {
        return this.iUnitMillis;
    }

    @Override
    public long remainder(long l) {
        if (l >= 0L) {
            return l % this.iUnitMillis;
        }
        return (l + 1L) % this.iUnitMillis + this.iUnitMillis - 1L;
    }

    @Override
    public long roundCeiling(long l) {
        if (l > 0L) {
            return --l - l % this.iUnitMillis + this.iUnitMillis;
        }
        return l - l % this.iUnitMillis;
    }

    @Override
    public long roundFloor(long l) {
        if (l >= 0L) {
            return l - l % this.iUnitMillis;
        }
        l = 1L + l;
        return l - l % this.iUnitMillis - this.iUnitMillis;
    }

    @Override
    public long set(long l, int n) {
        FieldUtils.verifyValueBounds(this, n, this.getMinimumValue(), this.getMaximumValueForSet(l, n));
        return (long)(n - this.get(l)) * this.iUnitMillis + l;
    }
}

