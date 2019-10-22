/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.tz;

import java.util.Collections;
import java.util.Set;
import org.joda.time.DateTimeZone;
import org.joda.time.tz.Provider;

public final class UTCProvider
implements Provider {
    private static final Set<String> AVAILABLE_IDS = Collections.singleton("UTC");

    @Override
    public Set<String> getAvailableIDs() {
        return AVAILABLE_IDS;
    }

    @Override
    public DateTimeZone getZone(String string2) {
        if ("UTC".equalsIgnoreCase(string2)) {
            return DateTimeZone.UTC;
        }
        return null;
    }
}

