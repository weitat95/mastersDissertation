/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import org.joda.time.DateTimeZone;

final class UTCDateTimeZone
extends DateTimeZone {
    static final DateTimeZone INSTANCE = new UTCDateTimeZone();

    public UTCDateTimeZone() {
        super("UTC");
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof UTCDateTimeZone;
    }

    @Override
    public String getNameKey(long l) {
        return "UTC";
    }

    @Override
    public int getOffset(long l) {
        return 0;
    }

    @Override
    public int getOffsetFromLocal(long l) {
        return 0;
    }

    @Override
    public int getStandardOffset(long l) {
        return 0;
    }

    @Override
    public int hashCode() {
        return this.getID().hashCode();
    }

    @Override
    public boolean isFixed() {
        return true;
    }

    @Override
    public long nextTransition(long l) {
        return l;
    }

    @Override
    public long previousTransition(long l) {
        return l;
    }
}

