/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.tz;

import org.joda.time.DateTimeZone;

public final class FixedDateTimeZone
extends DateTimeZone {
    private final String iNameKey;
    private final int iStandardOffset;
    private final int iWallOffset;

    public FixedDateTimeZone(String string2, String string3, int n, int n2) {
        super(string2);
        this.iNameKey = string3;
        this.iWallOffset = n;
        this.iStandardOffset = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof FixedDateTimeZone)) {
                    return false;
                }
                object = (FixedDateTimeZone)object;
                if (!this.getID().equals(((DateTimeZone)object).getID()) || this.iStandardOffset != ((FixedDateTimeZone)object).iStandardOffset || this.iWallOffset != ((FixedDateTimeZone)object).iWallOffset) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getNameKey(long l) {
        return this.iNameKey;
    }

    @Override
    public int getOffset(long l) {
        return this.iWallOffset;
    }

    @Override
    public int getOffsetFromLocal(long l) {
        return this.iWallOffset;
    }

    @Override
    public int getStandardOffset(long l) {
        return this.iStandardOffset;
    }

    @Override
    public int hashCode() {
        return this.getID().hashCode() + this.iStandardOffset * 37 + this.iWallOffset * 31;
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

