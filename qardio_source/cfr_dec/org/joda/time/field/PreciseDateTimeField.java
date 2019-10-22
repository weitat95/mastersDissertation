/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.PreciseDurationDateTimeField;

public class PreciseDateTimeField
extends PreciseDurationDateTimeField {
    private final int iRange;
    private final DurationField iRangeField;

    public PreciseDateTimeField(DateTimeFieldType dateTimeFieldType, DurationField durationField, DurationField durationField2) {
        super(dateTimeFieldType, durationField);
        if (!durationField2.isPrecise()) {
            throw new IllegalArgumentException("Range duration field must be precise");
        }
        this.iRange = (int)(durationField2.getUnitMillis() / this.getUnitMillis());
        if (this.iRange < 2) {
            throw new IllegalArgumentException("The effective range must be at least 2");
        }
        this.iRangeField = durationField2;
    }

    @Override
    public int get(long l) {
        if (l >= 0L) {
            return (int)(l / this.getUnitMillis() % (long)this.iRange);
        }
        return this.iRange - 1 + (int)((1L + l) / this.getUnitMillis() % (long)this.iRange);
    }

    @Override
    public int getMaximumValue() {
        return this.iRange - 1;
    }

    @Override
    public DurationField getRangeDurationField() {
        return this.iRangeField;
    }

    @Override
    public long set(long l, int n) {
        FieldUtils.verifyValueBounds(this, n, this.getMinimumValue(), this.getMaximumValue());
        return (long)(n - this.get(l)) * this.iUnitMillis + l;
    }
}

