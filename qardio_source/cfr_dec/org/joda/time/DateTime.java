/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableDateTime;
import org.joda.time.base.BaseDateTime;
import org.joda.time.format.DateTimeFormatter;

public final class DateTime
extends BaseDateTime
implements Serializable,
ReadableDateTime {
    public DateTime() {
    }

    public DateTime(long l, Chronology chronology) {
        super(l, chronology);
    }

    public static DateTime parse(String string2, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.parseDateTime(string2);
    }

    public DateTime withChronology(Chronology chronology) {
        if ((chronology = DateTimeUtils.getChronology(chronology)) == this.getChronology()) {
            return this;
        }
        return new DateTime(this.getMillis(), chronology);
    }

    public DateTime withZone(DateTimeZone dateTimeZone) {
        return this.withChronology(this.getChronology().withZone(dateTimeZone));
    }
}

