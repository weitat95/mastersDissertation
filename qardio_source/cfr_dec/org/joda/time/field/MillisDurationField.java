/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import java.io.Serializable;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.field.FieldUtils;

public final class MillisDurationField
extends DurationField
implements Serializable {
    public static final DurationField INSTANCE = new MillisDurationField();

    private MillisDurationField() {
    }

    @Override
    public long add(long l, int n) {
        return FieldUtils.safeAdd(l, (long)n);
    }

    @Override
    public long add(long l, long l2) {
        return FieldUtils.safeAdd(l, l2);
    }

    @Override
    public int compareTo(DurationField durationField) {
        long l = durationField.getUnitMillis();
        long l2 = this.getUnitMillis();
        if (l2 == l) {
            return 0;
        }
        if (l2 < l) {
            return -1;
        }
        return 1;
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object instanceof MillisDurationField) {
            bl2 = bl;
            if (this.getUnitMillis() == ((MillisDurationField)object).getUnitMillis()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @Override
    public DurationFieldType getType() {
        return DurationFieldType.millis();
    }

    @Override
    public final long getUnitMillis() {
        return 1L;
    }

    public int hashCode() {
        return (int)this.getUnitMillis();
    }

    @Override
    public final boolean isPrecise() {
        return true;
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    public String toString() {
        return "DurationField[millis]";
    }
}

