/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import java.util.Locale;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.IllegalFieldValueException;

public abstract class BaseDateTimeField
extends DateTimeField {
    private final DateTimeFieldType iType;

    protected BaseDateTimeField(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The type must not be null");
        }
        this.iType = dateTimeFieldType;
    }

    @Override
    public long add(long l, int n) {
        return this.getDurationField().add(l, n);
    }

    @Override
    public long add(long l, long l2) {
        return this.getDurationField().add(l, l2);
    }

    protected int convertText(String string2, Locale locale) {
        try {
            int n = Integer.parseInt(string2);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            throw new IllegalFieldValueException(this.getType(), string2);
        }
    }

    @Override
    public abstract int get(long var1);

    @Override
    public String getAsShortText(int n, Locale locale) {
        return this.getAsText(n, locale);
    }

    @Override
    public String getAsShortText(long l, Locale locale) {
        return this.getAsShortText(this.get(l), locale);
    }

    @Override
    public String getAsText(int n, Locale locale) {
        return Integer.toString(n);
    }

    @Override
    public String getAsText(long l, Locale locale) {
        return this.getAsText(this.get(l), locale);
    }

    @Override
    public abstract DurationField getDurationField();

    @Override
    public DurationField getLeapDurationField() {
        return null;
    }

    @Override
    public int getMaximumTextLength(Locale locale) {
        int n = this.getMaximumValue();
        if (n >= 0) {
            if (n < 10) {
                return 1;
            }
            if (n < 100) {
                return 2;
            }
            if (n < 1000) {
                return 3;
            }
        }
        return Integer.toString(n).length();
    }

    @Override
    public abstract int getMaximumValue();

    @Override
    public int getMaximumValue(long l) {
        return this.getMaximumValue();
    }

    @Override
    public final String getName() {
        return this.iType.getName();
    }

    @Override
    public final DateTimeFieldType getType() {
        return this.iType;
    }

    @Override
    public boolean isLeap(long l) {
        return false;
    }

    @Override
    public final boolean isSupported() {
        return true;
    }

    @Override
    public long remainder(long l) {
        return l - this.roundFloor(l);
    }

    @Override
    public long roundCeiling(long l) {
        long l2 = this.roundFloor(l);
        long l3 = l;
        if (l2 != l) {
            l3 = this.add(l2, 1);
        }
        return l3;
    }

    @Override
    public abstract long roundFloor(long var1);

    @Override
    public long roundHalfCeiling(long l) {
        long l2 = this.roundFloor(l);
        long l3 = this.roundCeiling(l);
        if (l3 - l <= l - l2) {
            return l3;
        }
        return l2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public long roundHalfEven(long l) {
        long l2;
        block5: {
            long l3;
            block4: {
                l3 = this.roundFloor(l);
                l2 = this.roundCeiling(l);
                long l4 = l - l3;
                if (l4 < (l = l2 - l)) break block4;
                if (l < l4) {
                    return l2;
                }
                if ((this.get(l2) & 1) == 0) break block5;
            }
            return l3;
        }
        return l2;
    }

    @Override
    public long roundHalfFloor(long l) {
        long l2;
        long l3 = this.roundFloor(l);
        if (l - l3 <= (l2 = this.roundCeiling(l)) - l) {
            return l3;
        }
        return l2;
    }

    @Override
    public abstract long set(long var1, int var3);

    @Override
    public long set(long l, String string2, Locale locale) {
        return this.set(l, this.convertText(string2, locale));
    }

    public String toString() {
        return "DateTimeField[" + this.getName() + ']';
    }
}

