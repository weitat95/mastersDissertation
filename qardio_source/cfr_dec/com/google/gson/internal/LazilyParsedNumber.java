/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal;

import java.math.BigDecimal;

public final class LazilyParsedNumber
extends Number {
    private final String value;

    public LazilyParsedNumber(String string2) {
        this.value = string2;
    }

    @Override
    public double doubleValue() {
        return Double.parseDouble(this.value);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl = false;
        if (this == object) {
            return true;
        }
        boolean bl2 = bl;
        if (!(object instanceof LazilyParsedNumber)) return bl2;
        object = (LazilyParsedNumber)object;
        if (this.value == ((LazilyParsedNumber)object).value) return true;
        bl2 = bl;
        if (!this.value.equals(((LazilyParsedNumber)object).value)) return bl2;
        return true;
    }

    @Override
    public float floatValue() {
        return Float.parseFloat(this.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public int intValue() {
        try {
            int n = Integer.parseInt(this.value);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            long l;
            try {
                l = Long.parseLong(this.value);
            }
            catch (NumberFormatException numberFormatException2) {
                return new BigDecimal(this.value).intValue();
            }
            return (int)l;
        }
    }

    @Override
    public long longValue() {
        try {
            long l = Long.parseLong(this.value);
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            return new BigDecimal(this.value).longValue();
        }
    }

    public String toString() {
        return this.value;
    }
}

