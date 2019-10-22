/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.FieldUtils;

public class RemainderDateTimeField
extends DecoratedDateTimeField {
    final int iDivisor;
    final DurationField iDurationField;
    final DurationField iRangeField;

    public RemainderDateTimeField(DateTimeField dateTimeField, DurationField durationField, DateTimeFieldType dateTimeFieldType, int n) {
        super(dateTimeField, dateTimeFieldType);
        if (n < 2) {
            throw new IllegalArgumentException("The divisor must be at least 2");
        }
        this.iRangeField = durationField;
        this.iDurationField = dateTimeField.getDurationField();
        this.iDivisor = n;
    }

    public RemainderDateTimeField(DividedDateTimeField dividedDateTimeField) {
        this(dividedDateTimeField, dividedDateTimeField.getType());
    }

    public RemainderDateTimeField(DividedDateTimeField dividedDateTimeField, DateTimeFieldType dateTimeFieldType) {
        this(dividedDateTimeField, dividedDateTimeField.getWrappedField().getDurationField(), dateTimeFieldType);
    }

    public RemainderDateTimeField(DividedDateTimeField dividedDateTimeField, DurationField durationField, DateTimeFieldType dateTimeFieldType) {
        super(dividedDateTimeField.getWrappedField(), dateTimeFieldType);
        this.iDivisor = dividedDateTimeField.iDivisor;
        this.iDurationField = durationField;
        this.iRangeField = dividedDateTimeField.iDurationField;
    }

    private int getDivided(int n) {
        if (n >= 0) {
            return n / this.iDivisor;
        }
        return (n + 1) / this.iDivisor - 1;
    }

    @Override
    public int get(long l) {
        int n = this.getWrappedField().get(l);
        if (n >= 0) {
            return n % this.iDivisor;
        }
        int n2 = this.iDivisor;
        return (n + 1) % this.iDivisor + (n2 - 1);
    }

    @Override
    public DurationField getDurationField() {
        return this.iDurationField;
    }

    @Override
    public int getMaximumValue() {
        return this.iDivisor - 1;
    }

    @Override
    public int getMinimumValue() {
        return 0;
    }

    @Override
    public DurationField getRangeDurationField() {
        return this.iRangeField;
    }

    @Override
    public long remainder(long l) {
        return this.getWrappedField().remainder(l);
    }

    @Override
    public long roundCeiling(long l) {
        return this.getWrappedField().roundCeiling(l);
    }

    @Override
    public long roundFloor(long l) {
        return this.getWrappedField().roundFloor(l);
    }

    @Override
    public long roundHalfCeiling(long l) {
        return this.getWrappedField().roundHalfCeiling(l);
    }

    @Override
    public long roundHalfEven(long l) {
        return this.getWrappedField().roundHalfEven(l);
    }

    @Override
    public long roundHalfFloor(long l) {
        return this.getWrappedField().roundHalfFloor(l);
    }

    @Override
    public long set(long l, int n) {
        FieldUtils.verifyValueBounds(this, n, 0, this.iDivisor - 1);
        int n2 = this.getDivided(this.getWrappedField().get(l));
        return this.getWrappedField().set(l, n2 * this.iDivisor + n);
    }
}

