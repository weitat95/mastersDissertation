/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;

public abstract class Chronology {
    public abstract DurationField centuries();

    public abstract DateTimeField centuryOfEra();

    public abstract DateTimeField clockhourOfDay();

    public abstract DateTimeField clockhourOfHalfday();

    public abstract DateTimeField dayOfMonth();

    public abstract DateTimeField dayOfWeek();

    public abstract DateTimeField dayOfYear();

    public abstract DurationField days();

    public abstract DateTimeField era();

    public abstract DurationField eras();

    public abstract DateTimeZone getZone();

    public abstract DateTimeField halfdayOfDay();

    public abstract DurationField halfdays();

    public abstract DateTimeField hourOfDay();

    public abstract DateTimeField hourOfHalfday();

    public abstract DurationField hours();

    public abstract DurationField millis();

    public abstract DateTimeField millisOfDay();

    public abstract DateTimeField millisOfSecond();

    public abstract DateTimeField minuteOfDay();

    public abstract DateTimeField minuteOfHour();

    public abstract DurationField minutes();

    public abstract DateTimeField monthOfYear();

    public abstract DurationField months();

    public abstract DateTimeField secondOfDay();

    public abstract DateTimeField secondOfMinute();

    public abstract DurationField seconds();

    public abstract DateTimeField weekOfWeekyear();

    public abstract DurationField weeks();

    public abstract DateTimeField weekyear();

    public abstract DateTimeField weekyearOfCentury();

    public abstract DurationField weekyears();

    public abstract Chronology withUTC();

    public abstract Chronology withZone(DateTimeZone var1);

    public abstract DateTimeField year();

    public abstract DateTimeField yearOfCentury();

    public abstract DateTimeField yearOfEra();

    public abstract DurationField years();
}

