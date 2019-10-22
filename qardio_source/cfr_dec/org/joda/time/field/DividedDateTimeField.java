/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.ScaledDurationField;

public class DividedDateTimeField
extends DecoratedDateTimeField {
    final int iDivisor;
    final DurationField iDurationField;
    private final int iMax;
    private final int iMin;
    final DurationField iRangeDurationField;

    public DividedDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int n) {
        this(dateTimeField, dateTimeField.getRangeDurationField(), dateTimeFieldType, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public DividedDateTimeField(DateTimeField dateTimeField, DurationField durationField, DateTimeFieldType dateTimeFieldType, int n) {
        super(dateTimeField, dateTimeFieldType);
        if (n < 2) {
            throw new IllegalArgumentException("The divisor must be at least 2");
        }
        DurationField durationField2 = dateTimeField.getDurationField();
        this.iDurationField = durationField2 == null ? null : new ScaledDurationField(durationField2, dateTimeFieldType.getDurationType(), n);
        this.iRangeDurationField = durationField;
        this.iDivisor = n;
        int n2 = dateTimeField.getMinimumValue();
        n2 = n2 >= 0 ? (n2 /= n) : (n2 + 1) / n - 1;
        int n3 = dateTimeField.getMaximumValue();
        n = n3 >= 0 ? n3 / n : (n3 + 1) / n - 1;
        this.iMin = n2;
        this.iMax = n;
    }

    private int getRemainder(int n) {
        if (n >= 0) {
            return n % this.iDivisor;
        }
        return this.iDivisor - 1 + (n + 1) % this.iDivisor;
    }

    @Override
    public long add(long l, int n) {
        return this.getWrappedField().add(l, this.iDivisor * n);
    }

    @Override
    public long add(long l, long l2) {
        return this.getWrappedField().add(l, (long)this.iDivisor * l2);
    }

    @Override
    public int get(long l) {
        int n = this.getWrappedField().get(l);
        if (n >= 0) {
            return n / this.iDivisor;
        }
        return (n + 1) / this.iDivisor - 1;
    }

    @Override
    public DurationField getDurationField() {
        return this.iDurationField;
    }

    @Override
    public int getMaximumValue() {
        return this.iMax;
    }

    @Override
    public int getMinimumValue() {
        return this.iMin;
    }

    @Override
    public DurationField getRangeDurationField() {
        if (this.iRangeDurationField != null) {
            return this.iRangeDurationField;
        }
        return super.getRangeDurationField();
    }

    @Override
    public long remainder(long l) {
        return this.set(l, this.get(this.getWrappedField().remainder(l)));
    }

    @Override
    public long roundFloor(long l) {
        DateTimeField dateTimeField = this.getWrappedField();
        return dateTimeField.roundFloor(dateTimeField.set(l, this.get(l) * this.iDivisor));
    }

    @Override
    public long set(long l, int n) {
        FieldUtils.verifyValueBounds(this, n, this.iMin, this.iMax);
        int n2 = this.getRemainder(this.getWrappedField().get(l));
        return this.getWrappedField().set(l, n2 + this.iDivisor * n);
    }
}

