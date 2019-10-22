/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.joda.convert.ToString
 */
package org.joda.time.base;

import org.joda.convert.ToString;
import org.joda.time.Chronology;
import org.joda.time.ReadableInstant;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISODateTimeFormat;

public abstract class AbstractInstant
implements ReadableInstant {
    protected AbstractInstant() {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int compareTo(ReadableInstant readableInstant) {
        long l;
        long l2;
        block5: {
            block4: {
                if (this == readableInstant) break block4;
                l = readableInstant.getMillis();
                l2 = this.getMillis();
                if (l2 != l) break block5;
            }
            return 0;
        }
        if (l2 < l) {
            return -1;
        }
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof ReadableInstant)) {
                    return false;
                }
                object = (ReadableInstant)object;
                if (this.getMillis() != object.getMillis() || !FieldUtils.equals(this.getChronology(), object.getChronology())) break block5;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (int)(this.getMillis() ^ this.getMillis() >>> 32) + this.getChronology().hashCode();
    }

    @ToString
    public String toString() {
        return ISODateTimeFormat.dateTime().print(this);
    }
}

