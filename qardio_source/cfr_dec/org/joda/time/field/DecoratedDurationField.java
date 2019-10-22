/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.field.BaseDurationField;

public class DecoratedDurationField
extends BaseDurationField {
    private final DurationField iField;

    public DecoratedDurationField(DurationField durationField, DurationFieldType durationFieldType) {
        super(durationFieldType);
        if (durationField == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        if (!durationField.isSupported()) {
            throw new IllegalArgumentException("The field must be supported");
        }
        this.iField = durationField;
    }

    @Override
    public long add(long l, int n) {
        return this.iField.add(l, n);
    }

    @Override
    public long add(long l, long l2) {
        return this.iField.add(l, l2);
    }

    @Override
    public long getUnitMillis() {
        return this.iField.getUnitMillis();
    }

    public final DurationField getWrappedField() {
        return this.iField;
    }

    @Override
    public boolean isPrecise() {
        return this.iField.isPrecise();
    }
}

