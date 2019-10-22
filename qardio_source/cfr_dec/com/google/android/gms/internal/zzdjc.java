/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbs;
import java.util.Collections;
import java.util.Map;

public final class zzdjc {
    private final zzbs zzkjc;
    private final Map<String, zzbs> zzksg;

    public final String toString() {
        String string2 = String.valueOf(Collections.unmodifiableMap(this.zzksg));
        String string3 = String.valueOf(this.zzkjc);
        return new StringBuilder(String.valueOf(string2).length() + 32 + String.valueOf(string3).length()).append("Properties: ").append(string2).append(" pushAfterEvaluate: ").append(string3).toString();
    }

    public final void zza(String string2, zzbs zzbs2) {
        this.zzksg.put(string2, zzbs2);
    }

    public final zzbs zzbfy() {
        return this.zzkjc;
    }

    public final Map<String, zzbs> zzbik() {
        return Collections.unmodifiableMap(this.zzksg);
    }
}

