/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import java.io.Serializable;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

public abstract class BaseDurationField
extends DurationField
implements Serializable {
    private final DurationFieldType iType;

    protected BaseDurationField(DurationFieldType durationFieldType) {
        if (durationFieldType == null) {
            throw new IllegalArgumentException("The type must not be null");
        }
        this.iType = durationFieldType;
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

    public final String getName() {
        return this.iType.getName();
    }

    @Override
    public final DurationFieldType getType() {
        return this.iType;
    }

    @Override
    public final boolean isSupported() {
        return true;
    }

    public String toString() {
        return "DurationField[" + this.getName() + ']';
    }
}

