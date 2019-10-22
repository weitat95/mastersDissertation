/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbey;

public final class zzarm<V> {
    private final V zzdxn;
    private final zzbey<V> zzdxo;

    private zzarm(zzbey<V> zzbey2, V v) {
        zzbq.checkNotNull(zzbey2);
        this.zzdxo = zzbey2;
        this.zzdxn = v;
    }

    static zzarm<Float> zza(String string2, float f, float f2) {
        return new zzarm<Float>(zzbey.zza(string2, Float.valueOf(0.5f)), Float.valueOf(0.5f));
    }

    static zzarm<Integer> zza(String string2, int n, int n2) {
        return new zzarm<Integer>(zzbey.zza(string2, n2), n);
    }

    static zzarm<Long> zza(String string2, long l, long l2) {
        return new zzarm<Long>(zzbey.zza(string2, l2), l);
    }

    static zzarm<Boolean> zza(String string2, boolean bl, boolean bl2) {
        return new zzarm<Boolean>(zzbey.zze(string2, bl2), bl);
    }

    static zzarm<String> zzc(String string2, String string3, String string4) {
        return new zzarm<String>(zzbey.zzs(string2, string4), string3);
    }

    public final V get() {
        return this.zzdxn;
    }
}

