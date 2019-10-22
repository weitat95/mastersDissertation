/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzdk;
import com.google.android.gms.common.api.internal.zzdl;
import com.google.android.gms.common.api.internal.zzdm;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.api.zze;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzdj {
    public static final Status zzfvg = new Status(8, "The connection to Google Play services was lost");
    private static final BasePendingResult<?>[] zzfvh = new BasePendingResult[0];
    private final Map<Api.zzc<?>, Api.zze> zzfsb;
    final Set<BasePendingResult<?>> zzfvi = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zzdm zzfvj = new zzdk(this);

    public zzdj(Map<Api.zzc<?>, Api.zze> map) {
        this.zzfsb = map;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void release() {
        BasePendingResult<?> basePendingResult;
        block10: {
            BasePendingResult<?>[] arrbasePendingResult = this.zzfvi.toArray(zzfvh);
            int n = arrbasePendingResult.length;
            int n2 = 0;
            while (n2 < n) {
                basePendingResult = arrbasePendingResult[n2];
                basePendingResult.zza((zzdm)null);
                if (((PendingResult)basePendingResult).zzagv() == null) {
                    if (basePendingResult.zzahh()) {
                        this.zzfvi.remove(basePendingResult);
                    }
                } else {
                    ((PendingResult)basePendingResult).setResultCallback(null);
                    IBinder iBinder = this.zzfsb.get(((zzm)basePendingResult).zzagf()).zzagh();
                    if (basePendingResult.isReady()) {
                        basePendingResult.zza(new zzdl(basePendingResult, null, iBinder, null));
                    } else {
                        if (iBinder == null || !iBinder.isBinderAlive()) break block10;
                        zzdl zzdl2 = new zzdl(basePendingResult, null, iBinder, null);
                        basePendingResult.zza(zzdl2);
                        iBinder.linkToDeath((IBinder.DeathRecipient)zzdl2, 0);
                    }
                    this.zzfvi.remove(basePendingResult);
                }
                ++n2;
            }
            return;
            catch (RemoteException remoteException) {
                ((PendingResult)basePendingResult).cancel();
                ((PendingResult)basePendingResult).zzagv().intValue();
                throw new NullPointerException();
            }
        }
        basePendingResult.zza((zzdm)null);
        ((PendingResult)basePendingResult).cancel();
        ((PendingResult)basePendingResult).zzagv().intValue();
        throw new NullPointerException();
    }

    public final void zzaju() {
        BasePendingResult<?>[] arrbasePendingResult = this.zzfvi.toArray(zzfvh);
        int n = arrbasePendingResult.length;
        for (int i = 0; i < n; ++i) {
            arrbasePendingResult[i].zzv(zzfvg);
        }
    }

    final void zzb(BasePendingResult<? extends Result> basePendingResult) {
        this.zzfvi.add(basePendingResult);
        basePendingResult.zza(this.zzfvj);
    }
}

