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

public final class zzchz {
    private final String zzbhb;
    private boolean zzfmd;
    private final boolean zzjdk;
    private boolean zzjdl;
    private /* synthetic */ zzchx zzjdm;

    public zzchz(zzchx zzchx2, String string2, boolean bl) {
        this.zzjdm = zzchx2;
        zzbq.zzgm(string2);
        this.zzbhb = string2;
        this.zzjdk = true;
    }

    public final boolean get() {
        if (!this.zzjdl) {
            this.zzjdl = true;
            this.zzfmd = zzchx.zza(this.zzjdm).getBoolean(this.zzbhb, this.zzjdk);
        }
        return this.zzfmd;
    }

    public final void set(boolean bl) {
        SharedPreferences.Editor editor = zzchx.zza(this.zzjdm).edit();
        editor.putBoolean(this.zzbhb, bl);
        editor.apply();
        this.zzfmd = bl;
    }
}

