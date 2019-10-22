/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbey;

public final class zzchd<V> {
    private final String zzbhb;
    private final V zzdxn;
    private final zzbey<V> zzdxo;

    private zzchd(String string2, zzbey<V> zzbey2, V v) {
        zzbq.checkNotNull(zzbey2);
        this.zzdxo = zzbey2;
        this.zzdxn = v;
        this.zzbhb = string2;
    }

    static zzchd<Long> zzb(String string2, long l, long l2) {
        return new zzchd<Long>(string2, zzbey.zza(string2, l2), l);
    }

    static zzchd<Boolean> zzb(String string2, boolean bl, boolean bl2) {
        return new zzchd<Boolean>(string2, zzbey.zze(string2, bl2), bl);
    }

    static zzchd<String> zzi(String string2, String string3, String string4) {
        return new zzchd<String>(string2, zzbey.zzs(string2, string4), string3);
    }

    static zzchd<Integer> zzm(String string2, int n, int n2) {
        return new zzchd<Integer>(string2, zzbey.zza(string2, n2), n);
    }

    public final V get() {
        return this.zzdxn;
    }

    public final V get(V v) {
        if (v != null) {
            return v;
        }
        return this.zzdxn;
    }

    public final String getKey() {
        return this.zzbhb;
    }
}

