/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.BaseDurationField;

public abstract class ImpreciseDateTimeField
extends BaseDateTimeField {
    private final DurationField iDurationField;
    final long iUnitMillis;

    public ImpreciseDateTimeField(DateTimeFieldType dateTimeFieldType, long l) {
        super(dateTimeFieldType);
        this.iUnitMillis = l;
        this.iDurationField = new LinkedDurationField(dateTimeFieldType.getDurationType());
    }

    @Override
    public abstract long add(long var1, int var3);

    @Override
    public abstract long add(long var1, long var3);

    @Override
    public final DurationField getDurationField() {
        return this.iDurationField;
    }

    private final class LinkedDurationField
    extends BaseDurationField {
        LinkedDurationField(DurationFieldType durationFieldType) {
            super(durationFieldType);
        }

        @Override
        public long add(long l, int n) {
            return ImpreciseDateTimeField.this.add(l, n);
        }

        @Override
        public long add(long l, long l2) {
            return ImpreciseDateTimeField.this.add(l, l2);
        }

        @Override
        public long getUnitMillis() {
            return ImpreciseDateTimeField.this.iUnitMillis;
        }

        @Override
        public boolean isPrecise() {
            return false;
        }
    }

}

