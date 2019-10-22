/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzchy;
import com.google.android.gms.internal.zzclq;
import java.security.SecureRandom;

public final class zzcib {
    private final long zzdyr;
    private /* synthetic */ zzchx zzjdm;
    private String zzjdo;
    private final String zzjdp;
    private final String zzjdq;

    /*
     * Enabled aggressive block sorting
     */
    private zzcib(zzchx zzchx2, String string2, long l) {
        this.zzjdm = zzchx2;
        zzbq.zzgm(string2);
        boolean bl = l > 0L;
        zzbq.checkArgument(bl);
        this.zzjdo = String.valueOf(string2).concat(":start");
        this.zzjdp = String.valueOf(string2).concat(":count");
        this.zzjdq = String.valueOf(string2).concat(":value");
        this.zzdyr = l;
    }

    /* synthetic */ zzcib(zzchx zzchx2, String string2, long l, zzchy zzchy2) {
        this(zzchx2, string2, l);
    }

    private final void zzaac() {
        this.zzjdm.zzve();
        long l = this.zzjdm.zzws().currentTimeMillis();
        SharedPreferences.Editor editor = zzchx.zza(this.zzjdm).edit();
        editor.remove(this.zzjdp);
        editor.remove(this.zzjdq);
        editor.putLong(this.zzjdo, l);
        editor.apply();
    }

    private final long zzaae() {
        return zzchx.zza(this.zzjdm).getLong(this.zzjdo, 0L);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final Pair<String, Long> zzaad() {
        this.zzjdm.zzve();
        this.zzjdm.zzve();
        long l = this.zzaae();
        if (l == 0L) {
            this.zzaac();
            l = 0L;
        } else {
            l = Math.abs(l - this.zzjdm.zzws().currentTimeMillis());
        }
        if (l < this.zzdyr) {
            return null;
        }
        if (l > this.zzdyr << 1) {
            this.zzaac();
            return null;
        }
        String string2 = zzchx.zza(this.zzjdm).getString(this.zzjdq, null);
        l = zzchx.zza(this.zzjdm).getLong(this.zzjdp, 0L);
        this.zzaac();
        if (string2 != null && l > 0L) {
            return new Pair((Object)string2, (Object)l);
        }
        return zzchx.zzjcp;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzf(String string2, long l) {
        this.zzjdm.zzve();
        if (this.zzaae() == 0L) {
            this.zzaac();
        }
        String string3 = string2;
        if (string2 == null) {
            string3 = "";
        }
        if ((l = zzchx.zza(this.zzjdm).getLong(this.zzjdp, 0L)) <= 0L) {
            string2 = zzchx.zza(this.zzjdm).edit();
            string2.putString(this.zzjdq, string3);
            string2.putLong(this.zzjdp, 1L);
            string2.apply();
            return;
        }
        boolean bl = (this.zzjdm.zzawu().zzbaz().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / (l + 1L);
        string2 = zzchx.zza(this.zzjdm).edit();
        if (bl) {
            string2.putString(this.zzjdq, string3);
        }
        string2.putLong(this.zzjdp, l + 1L);
        string2.apply();
    }
}

