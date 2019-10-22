/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.chrono.GregorianChronology;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.FieldUtils;

class ISOYearOfEraDateTimeField
extends DecoratedDateTimeField {
    static final DateTimeField INSTANCE = new ISOYearOfEraDateTimeField();

    private ISOYearOfEraDateTimeField() {
        super(GregorianChronology.getInstanceUTC().year(), DateTimeFieldType.yearOfEra());
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
        if (n < 0) {
            n2 = -n;
        }
        return n2;
    }

    @Override
    public int getMaximumValue() {
        return this.getWrappedField().getMaximumValue();
    }

    @Override
    public int getMinimumValue() {
        return 0;
    }

    @Override
    public DurationField getRangeDurationField() {
        return GregorianChronology.getInstanceUTC().eras();
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
    public long set(long l, int n) {
        FieldUtils.verifyValueBounds(this, n, 0, this.getMaximumValue());
        int n2 = n;
        if (this.getWrappedField().get(l) < 0) {
            n2 = -n;
        }
        return super.set(l, n2);
    }
}

