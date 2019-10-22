/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DurationFieldType;
import org.joda.time.field.BaseDurationField;
import org.joda.time.field.FieldUtils;

public class PreciseDurationField
extends BaseDurationField {
    private final long iUnitMillis;

    public PreciseDurationField(DurationFieldType durationFieldType, long l) {
        super(durationFieldType);
        this.iUnitMillis = l;
    }

    @Override
    public long add(long l, int n) {
        return FieldUtils.safeAdd(l, (long)n * this.iUnitMillis);
    }

    @Override
    public long add(long l, long l2) {
        return FieldUtils.safeAdd(l, FieldUtils.safeMultiply(l2, this.iUnitMillis));
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof PreciseDurationField)) {
                    return false;
                }
                object = (PreciseDurationField)object;
                if (this.getType() != ((BaseDurationField)object).getType() || this.iUnitMillis != ((PreciseDurationField)object).iUnitMillis) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public final long getUnitMillis() {
        return this.iUnitMillis;
    }

    public int hashCode() {
        long l = this.iUnitMillis;
        return (int)(l ^ l >>> 32) + this.getType().hashCode();
    }

    @Override
    public final boolean isPrecise() {
        return true;
    }
}

