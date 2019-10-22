/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzbq;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzaph
extends zzh<zzaph> {
    private final Map<String, Object> zzbsr = new HashMap<String, Object>();

    public final void set(String string2, String string3) {
        zzbq.zzgm(string2);
        String string4 = string2;
        if (string2 != null) {
            string4 = string2;
            if (string2.startsWith("&")) {
                string4 = string2.substring(1);
            }
        }
        zzbq.zzh(string4, "Name can not be empty or \"&\"");
        this.zzbsr.put(string4, string3);
    }

    public final String toString() {
        return zzaph.zzl(this.zzbsr);
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        zzh2 = (zzaph)zzh2;
        zzbq.checkNotNull(zzh2);
        ((zzaph)zzh2).zzbsr.putAll(this.zzbsr);
    }

    public final Map<String, Object> zzvt() {
        return Collections.unmodifiableMap(this.zzbsr);
    }
}

