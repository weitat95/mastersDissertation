/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.FieldUtils;

public class OffsetDateTimeField
extends DecoratedDateTimeField {
    private final int iMax;
    private final int iMin;
    private final int iOffset;

    /*
     * Enabled aggressive block sorting
     */
    public OffsetDateTimeField(DateTimeField dateTimeField, int n) {
        DateTimeFieldType dateTimeFieldType = dateTimeField == null ? null : dateTimeField.getType();
        this(dateTimeField, dateTimeFieldType, n, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public OffsetDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int n) {
        this(dateTimeField, dateTimeFieldType, n, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /*
     * Enabled aggressive block sorting
     */
    public OffsetDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType, int n, int n2, int n3) {
        super(dateTimeField, dateTimeFieldType);
        if (n == 0) {
            throw new IllegalArgumentException("The offset cannot be zero");
        }
        this.iOffset = n;
        this.iMin = n2 < dateTimeField.getMinimumValue() + n ? dateTimeField.getMinimumValue() + n : n2;
        if (n3 > dateTimeField.getMaximumValue() + n) {
            this.iMax = dateTimeField.getMaximumValue() + n;
            return;
        }
        this.iMax = n3;
    }

    @Override
    public long add(long l, int n) {
        l = super.add(l, n);
        FieldUtils.verifyValueBounds(this, this.get(l), this.iMin, this.iMax);
        return l;
    }

    @Override
    public long add(long l, long l2) {
        l = super.add(l, l2);
        FieldUtils.verifyValueBounds(this, this.get(l), this.iMin, this.iMax);
        return l;
    }

    @Override
    public int get(long l) {
        return super.get(l) + this.iOffset;
    }

    @Override
    public DurationField getLeapDurationField() {
        return this.getWrappedField().getLeapDurationField();
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
        FieldUtils.verifyValueBounds(this, n, this.iMin, this.iMax);
        return super.set(l, n - this.iOffset);
    }
}

