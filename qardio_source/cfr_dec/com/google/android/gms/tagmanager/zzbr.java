/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbs;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class zzbr {
    private final Set<String> zzkfs;
    private final String zzkft;

    public zzbr(String string22, String ... arrstring) {
        this.zzkft = string22;
        this.zzkfs = new HashSet<String>(arrstring.length);
        for (String string22 : arrstring) {
            this.zzkfs.add(string22);
        }
    }

    public abstract boolean zzbdp();

    public Set<String> zzbex() {
        return this.zzkfs;
    }

    final boolean zzd(Set<String> set) {
        return set.containsAll(this.zzkfs);
    }

    public abstract zzbs zzv(Map<String, zzbs> var1);
}

