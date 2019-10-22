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
import com.google.android.gms.internal.zzarz;
import com.google.android.gms.internal.zzasa;
import java.util.UUID;

public final class zzasb {
    private final String mName;
    private final long zzdyr;
    private /* synthetic */ zzarz zzdys;

    /*
     * Enabled aggressive block sorting
     */
    private zzasb(zzarz zzarz2, String string2, long l) {
        this.zzdys = zzarz2;
        zzbq.zzgm(string2);
        boolean bl = l > 0L;
        zzbq.checkArgument(bl);
        this.mName = string2;
        this.zzdyr = l;
    }

    /* synthetic */ zzasb(zzarz zzarz2, String string2, long l, zzasa zzasa2) {
        this(zzarz2, string2, l);
    }

    private final void zzaac() {
        long l = this.zzdys.zzws().currentTimeMillis();
        SharedPreferences.Editor editor = zzarz.zza(this.zzdys).edit();
        editor.remove(this.zzaag());
        editor.remove(this.zzaah());
        editor.putLong(this.zzaaf(), l);
        editor.commit();
    }

    private final long zzaae() {
        return zzarz.zza(this.zzdys).getLong(this.zzaaf(), 0L);
    }

    private final String zzaaf() {
        return String.valueOf(this.mName).concat(":start");
    }

    private final String zzaag() {
        return String.valueOf(this.mName).concat(":count");
    }

    private final String zzaah() {
        return String.valueOf(this.mName).concat(":value");
    }

    /*
     * Enabled aggressive block sorting
     */
    public final Pair<String, Long> zzaad() {
        long l = this.zzaae();
        l = l == 0L ? 0L : Math.abs(l - this.zzdys.zzws().currentTimeMillis());
        if (l < this.zzdyr) {
            return null;
        }
        if (l > this.zzdyr << 1) {
            this.zzaac();
            return null;
        }
        String string2 = zzarz.zza(this.zzdys).getString(this.zzaah(), null);
        l = zzarz.zza(this.zzdys).getLong(this.zzaag(), 0L);
        this.zzaac();
        if (string2 != null && l > 0L) {
            return new Pair((Object)string2, (Object)l);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzeg(String string2) {
        if (this.zzaae() == 0L) {
            this.zzaac();
        }
        String string3 = string2;
        if (string2 == null) {
            string3 = "";
        }
        synchronized (this) {
            long l = zzarz.zza(this.zzdys).getLong(this.zzaag(), 0L);
            if (l <= 0L) {
                string2 = zzarz.zza(this.zzdys).edit();
                string2.putString(this.zzaah(), string3);
                string2.putLong(this.zzaag(), 1L);
                string2.apply();
                return;
            }
            boolean bl = (UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE) < Long.MAX_VALUE / (l + 1L);
            string2 = zzarz.zza(this.zzdys).edit();
            if (bl) {
                string2.putString(this.zzaah(), string3);
            }
            string2.putLong(this.zzaag(), l + 1L);
            string2.apply();
            return;
        }
    }
}

