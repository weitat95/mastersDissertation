/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.ReadableInstant;
import org.joda.time.base.AbstractInstant;
import org.joda.time.chrono.ISOChronology;

public final class Instant
extends AbstractInstant
implements Serializable,
ReadableInstant {
    private final long iMillis;

    public Instant() {
        this.iMillis = DateTimeUtils.currentTimeMillis();
    }

    public Instant(long l) {
        this.iMillis = l;
    }

    @Override
    public Chronology getChronology() {
        return ISOChronology.getInstanceUTC();
    }

    @Override
    public long getMillis() {
        return this.iMillis;
    }
}

