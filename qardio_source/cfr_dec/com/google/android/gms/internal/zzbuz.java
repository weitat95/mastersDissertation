/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbva;
import com.google.android.gms.internal.zzbvb;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzbuz {
    public static final Set<String> zzheg = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("altitude", "duration", "food_item", "meal_type", "repetitions", "resistance", "resistance_type", "debug_session", "google.android.fitness.SessionV2")));
    private static final zzbuz zzhej = new zzbuz();
    private final Map<String, Map<String, zzbvb>> zzheh;
    private final Map<String, zzbvb> zzhei;

    private zzbuz() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("latitude", new zzbvb(-90.0, 90.0, null));
        hashMap.put("longitude", new zzbvb(-180.0, 180.0, null));
        hashMap.put("accuracy", new zzbvb(0.0, 10000.0, null));
        hashMap.put("bpm", new zzbvb(0.0, 1000.0, null));
        hashMap.put("altitude", new zzbvb(-100000.0, 100000.0, null));
        hashMap.put("percentage", new zzbvb(0.0, 100.0, null));
        hashMap.put("confidence", new zzbvb(0.0, 100.0, null));
        hashMap.put("duration", new zzbvb(0.0, 9.223372036854776E18, null));
        hashMap.put("height", new zzbvb(0.0, 3.0, null));
        hashMap.put("weight", new zzbvb(0.0, 1000.0, null));
        hashMap.put("speed", new zzbvb(0.0, 11000.0, null));
        this.zzhei = Collections.unmodifiableMap(hashMap);
        hashMap = new HashMap();
        hashMap.put("com.google.step_count.delta", zzbuz.zzd("steps", new zzbvb(0.0, 1.0E-8, null)));
        hashMap.put("com.google.calories.consumed", zzbuz.zzd("calories", new zzbvb(0.0, 1.0E-6, null)));
        hashMap.put("com.google.calories.expended", zzbuz.zzd("calories", new zzbvb(0.0, 5.555555555555555E-10, null)));
        hashMap.put("com.google.distance.delta", zzbuz.zzd("distance", new zzbvb(0.0, 1.0E-7, null)));
        this.zzheh = Collections.unmodifiableMap(hashMap);
    }

    public static zzbuz zzaqs() {
        return zzhej;
    }

    private static <K, V> Map<K, V> zzd(K k, V v) {
        HashMap<K, V> hashMap = new HashMap<K, V>();
        hashMap.put(k, v);
        return hashMap;
    }

    final zzbvb zzhn(String string2) {
        return this.zzhei.get(string2);
    }

    final zzbvb zzz(String object, String string2) {
        if ((object = this.zzheh.get(object)) != null) {
            return (zzbvb)object.get(string2);
        }
        return null;
    }
}

