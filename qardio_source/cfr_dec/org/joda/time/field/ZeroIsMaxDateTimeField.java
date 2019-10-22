/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.FieldUtils;

public final class ZeroIsMaxDateTimeField
extends DecoratedDateTimeField {
    public ZeroIsMaxDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType) {
        super(dateTimeField, dateTimeFieldType);
        if (dateTimeField.getMinimumValue() != 0) {
            throw new IllegalArgumentException("Wrapped field's minumum value must be zero");
        }
    }

    @Override
    public long add(long l, int n) {
        return this.getWrappedField().add(l, n);
    }

    @Override
    public long add(long l, long l2) {
        return this.getWrappedField().add(l, l2);
    }

    @Override
    public int get(long l) {
        int n;
        int n2 = n = this.getWrappedField().get(l);
        if (n == 0) {
            n2 = this.getMaximumValue();
        }
        return n2;
    }

    @Override
    public DurationField getLeapDurationField() {
        return this.getWrappedField().getLeapDurationField();
    }

    @Override
    public int getMaximumValue() {
        return this.getWrappedField().getMaximumValue() + 1;
    }

    @Override
    public int getMaximumValue(long l) {
        return this.getWrappedField().getMaximumValue(l) + 1;
    }

    @Override
    public int getMinimumValue() {
        return 1;
    }

    @Override
    public boolean isLeap(long l) {
        return this.getWrappedField().isLeap(l);
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
        int n2 = this.getMaximumValue();
        FieldUtils.verifyValueBounds(this, n, 1, n2);
        int n3 = n;
        if (n == n2) {
            n3 = 0;
        }
        return this.getWrappedField().set(l, n3);
    }
}

