/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import java.util.Locale;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;

public abstract class DateTimeField {
    public abstract long add(long var1, int var3);

    public abstract long add(long var1, long var3);

    public abstract int get(long var1);

    public abstract String getAsShortText(int var1, Locale var2);

    public abstract String getAsShortText(long var1, Locale var3);

    public abstract String getAsText(int var1, Locale var2);

    public abstract String getAsText(long var1, Locale var3);

    public abstract DurationField getDurationField();

    public abstract DurationField getLeapDurationField();

    public abstract int getMaximumTextLength(Locale var1);

    public abstract int getMaximumValue();

    public abstract int getMaximumValue(long var1);

    public abstract int getMinimumValue();

    public abstract String getName();

    public abstract DurationField getRangeDurationField();

    public abstract DateTimeFieldType getType();

    public abstract boolean isLeap(long var1);

    public abstract boolean isSupported();

    public abstract long remainder(long var1);

    public abstract long roundCeiling(long var1);

    public abstract long roundFloor(long var1);

    public abstract long roundHalfCeiling(long var1);

    public abstract long roundHalfEven(long var1);

    public abstract long roundHalfFloor(long var1);

    public abstract long set(long var1, int var3);

    public abstract long set(long var1, String var3, Locale var4);

    public long setExtended(long l, int n) {
        return this.set(l, n);
    }
}

