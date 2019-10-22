/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.field.BaseDurationField;
import org.joda.time.field.DecoratedDurationField;
import org.joda.time.field.FieldUtils;

public class ScaledDurationField
extends DecoratedDurationField {
    private final int iScalar;

    public ScaledDurationField(DurationField durationField, DurationFieldType durationFieldType, int n) {
        super(durationField, durationFieldType);
        if (n == 0 || n == 1) {
            throw new IllegalArgumentException("The scalar must not be 0 or 1");
        }
        this.iScalar = n;
    }

    @Override
    public long add(long l, int n) {
        long l2 = n;
        long l3 = this.iScalar;
        return this.getWrappedField().add(l, l2 * l3);
    }

    @Override
    public long add(long l, long l2) {
        l2 = FieldUtils.safeMultiply(l2, this.iScalar);
        return this.getWrappedField().add(l, l2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof ScaledDurationField)) {
                    return false;
                }
                object = (ScaledDurationField)object;
                if (!this.getWrappedField().equals(((DecoratedDurationField)object).getWrappedField()) || this.getType() != ((BaseDurationField)object).getType() || this.iScalar != ((ScaledDurationField)object).iScalar) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public long getUnitMillis() {
        return this.getWrappedField().getUnitMillis() * (long)this.iScalar;
    }

    public int hashCode() {
        long l = this.iScalar;
        return (int)(l ^ l >>> 32) + this.getType().hashCode() + this.getWrappedField().hashCode();
    }
}

