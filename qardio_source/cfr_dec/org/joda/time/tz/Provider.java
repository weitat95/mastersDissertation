/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.tz;

import java.util.Set;
import org.joda.time.DateTimeZone;

public interface Provider {
    public Set<String> getAvailableIDs();

    public DateTimeZone getZone(String var1);
}

