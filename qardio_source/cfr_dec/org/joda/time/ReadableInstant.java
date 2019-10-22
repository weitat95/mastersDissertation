/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import org.joda.time.Chronology;

public interface ReadableInstant
extends Comparable<ReadableInstant> {
    public Chronology getChronology();

    public long getMillis();
}

