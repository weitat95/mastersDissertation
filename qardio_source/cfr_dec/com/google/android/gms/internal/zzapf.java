/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzh;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class zzapf
extends zzh<zzapf> {
    private Map<Integer, String> zzdrj = new HashMap<Integer, String>(4);

    public final String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (Map.Entry<Integer, String> entry : this.zzdrj.entrySet()) {
            String string2 = String.valueOf(entry.getKey());
            hashMap.put(new StringBuilder(String.valueOf(string2).length() + 9).append("dimension").append(string2).toString(), entry.getValue());
        }
        return zzapf.zzl(hashMap);
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        ((zzapf)zzh2).zzdrj.putAll(this.zzdrj);
    }

    public final Map<Integer, String> zzvr() {
        return Collections.unmodifiableMap(this.zzdrj);
    }
}

