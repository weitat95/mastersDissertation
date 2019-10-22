/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.joda.convert.ToString
 */
package org.joda.time.base;

import org.joda.convert.ToString;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.ReadableDateTime;
import org.joda.time.base.AbstractInstant;

public abstract class AbstractDateTime
extends AbstractInstant
implements ReadableDateTime {
    protected AbstractDateTime() {
    }

    public int getWeekyear() {
        return this.getChronology().weekyear().get(this.getMillis());
    }

    public int getYear() {
        return this.getChronology().year().get(this.getMillis());
    }

    @ToString
    @Override
    public String toString() {
        return super.toString();
    }
}

