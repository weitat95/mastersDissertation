/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.google.android.gms.internal;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchx;

public final class zzcia {
    private final String zzbhb;
    private long zzdrr;
    private boolean zzjdl;
    private /* synthetic */ zzchx zzjdm;
    private final long zzjdn;

    public zzcia(zzchx zzchx2, String string2, long l) {
        this.zzjdm = zzchx2;
        zzbq.zzgm(string2);
        this.zzbhb = string2;
        this.zzjdn = l;
    }

    public final long get() {
        if (!this.zzjdl) {
            this.zzjdl = true;
            this.zzdrr = zzchx.zza(this.zzjdm).getLong(this.zzbhb, this.zzjdn);
        }
        return this.zzdrr;
    }

    public final void set(long l) {
        SharedPreferences.Editor editor = zzchx.zza(this.zzjdm).edit();
        editor.putLong(this.zzbhb, l);
        editor.apply();
        this.zzdrr = l;
    }
}

