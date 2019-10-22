/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.common.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableMap<K, V>
extends HashMap<K, V> {
    public static <K, V> Map<K, V> of(K k, V v) {
        HashMap<K, V> hashMap = new HashMap<K, V>();
        hashMap.put(k, v);
        return Collections.unmodifiableMap(hashMap);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        HashMap<K, V> hashMap = new HashMap<K, V>();
        hashMap.put(k, v);
        hashMap.put(k2, v2);
        hashMap.put(k3, v3);
        hashMap.put(k4, v4);
        return Collections.unmodifiableMap(hashMap);
    }

    public static <K, V> Map<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        HashMap<K, V> hashMap = new HashMap<K, V>();
        hashMap.put(k, v);
        hashMap.put(k2, v2);
        hashMap.put(k3, v3);
        hashMap.put(k4, v4);
        hashMap.put(k5, v5);
        return Collections.unmodifiableMap(hashMap);
    }
}

