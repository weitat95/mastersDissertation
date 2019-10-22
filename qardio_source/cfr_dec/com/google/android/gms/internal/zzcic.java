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
import com.google.android.gms.internal.zzclq;

public final class zzcic {
    private String mValue;
    private final String zzbhb;
    private boolean zzjdl;
    private /* synthetic */ zzchx zzjdm;
    private final String zzjdr;

    public zzcic(zzchx zzchx2, String string2, String string3) {
        this.zzjdm = zzchx2;
        zzbq.zzgm(string2);
        this.zzbhb = string2;
        this.zzjdr = null;
    }

    public final String zzazr() {
        if (!this.zzjdl) {
            this.zzjdl = true;
            this.mValue = zzchx.zza(this.zzjdm).getString(this.zzbhb, null);
        }
        return this.mValue;
    }

    public final void zzjq(String string2) {
        if (zzclq.zzas(string2, this.mValue)) {
            return;
        }
        SharedPreferences.Editor editor = zzchx.zza(this.zzjdm).edit();
        editor.putString(this.zzbhb, string2);
        editor.apply();
        this.mValue = string2;
    }
}

