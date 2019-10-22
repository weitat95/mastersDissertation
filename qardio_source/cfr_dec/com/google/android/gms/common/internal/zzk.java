/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.internal.zzax;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzd;

public final class zzk
extends zzax {
    private zzd zzfzc;
    private final int zzfzd;

    public zzk(zzd zzd2, int n) {
        this.zzfzc = zzd2;
        this.zzfzd = n;
    }

    @Override
    public final void zza(int n, Bundle bundle) {
        Log.wtf((String)"GmsClient", (String)"received deprecated onAccountValidationComplete callback, ignoring", (Throwable)new Exception());
    }

    @Override
    public final void zza(int n, IBinder iBinder, Bundle bundle) {
        zzbq.checkNotNull(this.zzfzc, "onPostInitComplete can be called only once per call to getRemoteService");
        this.zzfzc.zza(n, iBinder, bundle, this.zzfzd);
        this.zzfzc = null;
    }
}

