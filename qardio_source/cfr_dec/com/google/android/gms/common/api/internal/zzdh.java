/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Message
 */
package com.google.android.gms.common.api.internal;

import android.os.Message;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzdg;

final class zzdh
implements Runnable {
    private /* synthetic */ Result zzfve;
    private /* synthetic */ zzdg zzfvf;

    zzdh(zzdg zzdg2, Result result) {
        this.zzfvf = zzdg2;
        this.zzfve = result;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        try {
            BasePendingResult.zzfot.set(true);
            PendingResult pendingResult = zzdg.zzc(this.zzfvf).onSuccess(this.zzfve);
            zzdg.zzd(this.zzfvf).sendMessage(zzdg.zzd(this.zzfvf).obtainMessage(0, pendingResult));
            return;
        }
        catch (RuntimeException runtimeException) {
            zzdg.zzd(this.zzfvf).sendMessage(zzdg.zzd(this.zzfvf).obtainMessage(1, (Object)runtimeException));
            return;
        }
        finally {
            BasePendingResult.zzfot.set(false);
            zzdg.zza(this.zzfvf, this.zzfve);
            GoogleApiClient googleApiClient = (GoogleApiClient)zzdg.zze(this.zzfvf).get();
            if (googleApiClient == null) return;
            googleApiClient.zzb(this.zzfvf);
        }
    }
}

