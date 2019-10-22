/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.base;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableDateTime;
import org.joda.time.base.AbstractDateTime;
import org.joda.time.chrono.ISOChronology;

public abstract class BaseDateTime
extends AbstractDateTime
implements Serializable,
ReadableDateTime {
    private volatile Chronology iChronology;
    private volatile long iMillis;

    public BaseDateTime() {
        this(DateTimeUtils.currentTimeMillis(), ISOChronology.getInstance());
    }

    public BaseDateTime(long l, Chronology chronology) {
        this.iChronology = this.checkChronology(chronology);
        this.iMillis = this.checkInstant(l, this.iChronology);
        this.adjustForMinMax();
    }

    public BaseDateTime(long l, DateTimeZone dateTimeZone) {
        this(l, ISOChronology.getInstance(dateTimeZone));
    }

    private void adjustForMinMax() {
        if (this.iMillis == Long.MIN_VALUE || this.iMillis == Long.MAX_VALUE) {
            this.iChronology = this.iChronology.withUTC();
        }
    }

    protected Chronology checkChronology(Chronology chronology) {
        return DateTimeUtils.getChronology(chronology);
    }

    protected long checkInstant(long l, Chronology chronology) {
        return l;
    }

    @Override
    public Chronology getChronology() {
        return this.iChronology;
    }

    @Override
    public long getMillis() {
        return this.iMillis;
    }

    protected void setMillis(long l) {
        this.iMillis = this.checkInstant(l, this.iChronology);
    }
}

