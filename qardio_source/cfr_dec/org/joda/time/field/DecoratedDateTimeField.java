/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.BaseDateTimeField;

public abstract class DecoratedDateTimeField
extends BaseDateTimeField {
    private final DateTimeField iField;

    protected DecoratedDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType) {
        super(dateTimeFieldType);
        if (dateTimeField == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        if (!dateTimeField.isSupported()) {
            throw new IllegalArgumentException("The field must be supported");
        }
        this.iField = dateTimeField;
    }

    @Override
    public int get(long l) {
        return this.iField.get(l);
    }

    @Override
    public DurationField getDurationField() {
        return this.iField.getDurationField();
    }

    @Override
    public int getMaximumValue() {
        return this.iField.getMaximumValue();
    }

    @Override
    public int getMinimumValue() {
        return this.iField.getMinimumValue();
    }

    @Override
    public DurationField getRangeDurationField() {
        return this.iField.getRangeDurationField();
    }

    public final DateTimeField getWrappedField() {
        return this.iField;
    }

    @Override
    public long roundFloor(long l) {
        return this.iField.roundFloor(l);
    }

    @Override
    public long set(long l, int n) {
        return this.iField.set(l, n);
    }
}

