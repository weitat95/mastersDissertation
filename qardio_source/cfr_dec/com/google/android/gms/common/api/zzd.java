/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.accounts.Account;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzcz;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.common.internal.zzbq;

public final class zzd {
    private Looper zzall;
    private zzcz zzfmh;

    public final zzd zza(zzcz zzcz2) {
        zzbq.checkNotNull(zzcz2, "StatusExceptionMapper must not be null.");
        this.zzfmh = zzcz2;
        return this;
    }

    public final GoogleApi.zza zzagq() {
        if (this.zzfmh == null) {
            this.zzfmh = new zzg();
        }
        if (this.zzall == null) {
            this.zzall = Looper.getMainLooper();
        }
        return new GoogleApi.zza(this.zzfmh, null, this.zzall, null);
    }
}

