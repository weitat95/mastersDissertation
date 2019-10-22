/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import java.io.Serializable;
import java.util.HashMap;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

public final class UnsupportedDurationField
extends DurationField
implements Serializable {
    private static HashMap<DurationFieldType, UnsupportedDurationField> cCache;
    private final DurationFieldType iType;

    private UnsupportedDurationField(DurationFieldType durationFieldType) {
        this.iType = durationFieldType;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static UnsupportedDurationField getInstance(DurationFieldType durationFieldType) {
        synchronized (UnsupportedDurationField.class) {
            UnsupportedDurationField unsupportedDurationField;
            if (cCache == null) {
                cCache = new HashMap(7);
                unsupportedDurationField = null;
            } else {
                unsupportedDurationField = cCache.get(durationFieldType);
            }
            UnsupportedDurationField unsupportedDurationField2 = unsupportedDurationField;
            if (unsupportedDurationField == null) {
                unsupportedDurationField2 = new UnsupportedDurationField(durationFieldType);
                cCache.put(durationFieldType, unsupportedDurationField2);
            }
            return unsupportedDurationField2;
        }
    }

    private UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException(this.iType + " field is unsupported");
    }

    @Override
    public long add(long l, int n) {
        throw this.unsupported();
    }

    @Override
    public long add(long l, long l2) {
        throw this.unsupported();
    }

    @Override
    public int compareTo(DurationField durationField) {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6: {
            block5: {
                if (this == object) break block5;
                if (!(object instanceof UnsupportedDurationField)) {
                    return false;
                }
                if (((UnsupportedDurationField)(object = (UnsupportedDurationField)object)).getName() != null) {
                    return ((UnsupportedDurationField)object).getName().equals(this.getName());
                }
                if (this.getName() != null) break block6;
            }
            return true;
        }
        return false;
    }

    public String getName() {
        return this.iType.getName();
    }

    @Override
    public final DurationFieldType getType() {
        return this.iType;
    }

    @Override
    public long getUnitMillis() {
        return 0L;
    }

    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public boolean isPrecise() {
        return true;
    }

    @Override
    public boolean isSupported() {
        return false;
    }

    public String toString() {
        return "UnsupportedDurationField[" + this.getName() + ']';
    }
}

