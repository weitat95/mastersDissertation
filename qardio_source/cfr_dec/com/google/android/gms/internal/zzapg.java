/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzh;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class zzapg
extends zzh<zzapg> {
    private Map<Integer, Double> zzdrk = new HashMap<Integer, Double>(4);

    public final String toString() {
        HashMap<String, Double> hashMap = new HashMap<String, Double>();
        for (Map.Entry<Integer, Double> entry : this.zzdrk.entrySet()) {
            String string2 = String.valueOf(entry.getKey());
            hashMap.put(new StringBuilder(String.valueOf(string2).length() + 6).append("metric").append(string2).toString(), entry.getValue());
        }
        return zzapg.zzl(hashMap);
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        ((zzapg)zzh2).zzdrk.putAll(this.zzdrk);
    }

    public final Map<Integer, Double> zzvs() {
        return Collections.unmodifiableMap(this.zzdrk);
    }
}

