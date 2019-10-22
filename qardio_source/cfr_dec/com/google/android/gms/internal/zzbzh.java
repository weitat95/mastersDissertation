/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzbwx;
import com.google.android.gms.internal.zzbyz;

final class zzbzh
extends zzbwx {
    private final zzn<DataReadResult> zzgbw;
    private int zzhfg = 0;
    private DataReadResult zzhfh = null;

    private zzbzh(zzn<DataReadResult> zzn2) {
        this.zzgbw = zzn2;
    }

    /* synthetic */ zzbzh(zzn zzn2, zzbyz zzbyz2) {
        this(zzn2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zza(DataReadResult dataReadResult) {
        synchronized (this) {
            if (Log.isLoggable((String)"Fitness", (int)2)) {
                int n = this.zzhfg;
                Log.v((String)"Fitness", (String)new StringBuilder(33).append("Received batch result ").append(n).toString());
            }
            if (this.zzhfh == null) {
                this.zzhfh = dataReadResult;
            } else {
                this.zzhfh.zzb(dataReadResult);
            }
            ++this.zzhfg;
            if (this.zzhfg == this.zzhfh.zzaqx()) {
                this.zzgbw.setResult(this.zzhfh);
            }
            return;
        }
    }
}

