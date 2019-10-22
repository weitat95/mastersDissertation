/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import org.joda.time.DurationFieldType;

public abstract class DurationField
implements Comparable<DurationField> {
    public abstract long add(long var1, int var3);

    public abstract long add(long var1, long var3);

    public abstract DurationFieldType getType();

    public abstract long getUnitMillis();

    public abstract boolean isPrecise();

    public abstract boolean isSupported();
}

