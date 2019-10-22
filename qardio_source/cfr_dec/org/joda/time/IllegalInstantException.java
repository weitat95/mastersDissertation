/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import org.joda.time.Instant;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;

public class IllegalInstantException
extends IllegalArgumentException {
    public IllegalInstantException(long l, String string2) {
        super(IllegalInstantException.createMessage(l, string2));
    }

    public IllegalInstantException(String string2) {
        super(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String createMessage(long l, String string2) {
        String string3 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").print(new Instant(l));
        if (string2 != null) {
            string2 = " (" + string2 + ")";
            do {
                return "Illegal instant due to time zone offset transition (daylight savings time 'gap'): " + string3 + string2;
                break;
            } while (true);
        }
        string2 = "";
        return "Illegal instant due to time zone offset transition (daylight savings time 'gap'): " + string3 + string2;
    }
}

