/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzh;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProductAction {
    private Map<String, String> zzdsl;

    public final Map<String, String> build() {
        return new HashMap<String, String>(this.zzdsl);
    }

    public String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : this.zzdsl.entrySet()) {
            if (entry.getKey().startsWith("&")) {
                hashMap.put(entry.getKey().substring(1), entry.getValue());
                continue;
            }
            hashMap.put(entry.getKey(), entry.getValue());
        }
        return zzh.zzr(hashMap);
    }
}

