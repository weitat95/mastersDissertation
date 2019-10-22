/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzao;
import com.google.android.gms.common.api.internal.zzba;
import com.google.android.gms.common.api.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzj;
import java.lang.ref.WeakReference;

final class zzaq
implements zzj {
    private final Api<?> zzfin;
    private final boolean zzfpg;
    private final WeakReference<zzao> zzfrm;

    public zzaq(zzao zzao2, Api<?> api, boolean bl) {
        this.zzfrm = new WeakReference<zzao>(zzao2);
        this.zzfin = api;
        this.zzfpg = bl;
    }

    static /* synthetic */ boolean zza(zzaq zzaq2) {
        return zzaq2.zzfpg;
    }

    @Override
    public final void zzf(ConnectionResult connectionResult) {
        zzao zzao2;
        block8: {
            boolean bl = false;
            zzao2 = (zzao)this.zzfrm.get();
            if (zzao2 == null) {
                return;
            }
            if (Looper.myLooper() == ((GoogleApiClient)zzao.zzd((zzao)zzao2).zzfpi).getLooper()) {
                bl = true;
            }
            zzbq.zza(bl, "onReportServiceBinding must be called on the GoogleApiClient handler thread");
            zzao.zzc(zzao2).lock();
            bl = zzao.zza(zzao2, 0);
            if (bl) break block8;
            zzao.zzc(zzao2).unlock();
            return;
        }
        try {
            if (!connectionResult.isSuccess()) {
                zzao.zza(zzao2, connectionResult, this.zzfin, this.zzfpg);
            }
            if (zzao.zzk(zzao2)) {
                zzao.zzj(zzao2);
            }
            return;
        }
        finally {
            zzao.zzc(zzao2).unlock();
        }
    }
}

