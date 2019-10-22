/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import java.lang.reflect.Method;
import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableInstant;
import org.joda.time.chrono.ISOChronology;

public class DateTimeUtils {
    public static final MillisProvider SYSTEM_MILLIS_PROVIDER;
    private static volatile MillisProvider cMillisProvider;
    private static final AtomicReference<Map<String, DateTimeZone>> cZoneNames;

    static {
        cMillisProvider = SYSTEM_MILLIS_PROVIDER = new SystemMillisProvider();
        cZoneNames = new AtomicReference();
    }

    private static Map<String, DateTimeZone> buildDefaultTimeZoneNames() {
        LinkedHashMap<String, DateTimeZone> linkedHashMap = new LinkedHashMap<String, DateTimeZone>();
        linkedHashMap.put("UT", DateTimeZone.UTC);
        linkedHashMap.put("UTC", DateTimeZone.UTC);
        linkedHashMap.put("GMT", DateTimeZone.UTC);
        DateTimeUtils.put(linkedHashMap, "EST", "America/New_York");
        DateTimeUtils.put(linkedHashMap, "EDT", "America/New_York");
        DateTimeUtils.put(linkedHashMap, "CST", "America/Chicago");
        DateTimeUtils.put(linkedHashMap, "CDT", "America/Chicago");
        DateTimeUtils.put(linkedHashMap, "MST", "America/Denver");
        DateTimeUtils.put(linkedHashMap, "MDT", "America/Denver");
        DateTimeUtils.put(linkedHashMap, "PST", "America/Los_Angeles");
        DateTimeUtils.put(linkedHashMap, "PDT", "America/Los_Angeles");
        return Collections.unmodifiableMap(linkedHashMap);
    }

    public static final long currentTimeMillis() {
        return cMillisProvider.getMillis();
    }

    public static final Chronology getChronology(Chronology chronology) {
        Chronology chronology2 = chronology;
        if (chronology == null) {
            chronology2 = ISOChronology.getInstance();
        }
        return chronology2;
    }

    public static final DateFormatSymbols getDateFormatSymbols(Locale locale) {
        try {
            DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)DateFormatSymbols.class.getMethod("getInstance", Locale.class).invoke(null, locale);
            return dateFormatSymbols;
        }
        catch (Exception exception) {
            return new DateFormatSymbols(locale);
        }
    }

    public static final Map<String, DateTimeZone> getDefaultTimeZoneNames() {
        Map<String, DateTimeZone> map;
        Map<String, DateTimeZone> map2 = map = cZoneNames.get();
        if (map == null) {
            map2 = map = DateTimeUtils.buildDefaultTimeZoneNames();
            if (!cZoneNames.compareAndSet(null, map)) {
                map2 = cZoneNames.get();
            }
        }
        return map2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final Chronology getInstantChronology(ReadableInstant object) {
        if (object == null) {
            return ISOChronology.getInstance();
        }
        Chronology chronology = object.getChronology();
        object = chronology;
        if (chronology != null) return object;
        return ISOChronology.getInstance();
    }

    public static final long getInstantMillis(ReadableInstant readableInstant) {
        if (readableInstant == null) {
            return DateTimeUtils.currentTimeMillis();
        }
        return readableInstant.getMillis();
    }

    private static void put(Map<String, DateTimeZone> map, String string2, String string3) {
        try {
            map.put(string2, DateTimeZone.forID(string3));
            return;
        }
        catch (RuntimeException runtimeException) {
            return;
        }
    }

    public static interface MillisProvider {
        public long getMillis();
    }

    static class SystemMillisProvider
    implements MillisProvider {
        SystemMillisProvider() {
        }

        @Override
        public long getMillis() {
            return System.currentTimeMillis();
        }
    }

}

