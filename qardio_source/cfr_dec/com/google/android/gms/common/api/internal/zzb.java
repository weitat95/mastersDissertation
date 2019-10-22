/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzae;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzb<T>
extends zza {
    protected final TaskCompletionSource<T> zzedx;

    public zzb(int n, TaskCompletionSource<T> taskCompletionSource) {
        super(n);
        this.zzedx = taskCompletionSource;
    }

    @Override
    public void zza(zzae zzae2, boolean bl) {
    }

    @Override
    public final void zza(zzbo<?> zzbo2) throws DeadObjectException {
        try {
            this.zzb(zzbo2);
            return;
        }
        catch (DeadObjectException deadObjectException) {
            ((zza)this).zzs(zza.zzb((RemoteException)((Object)deadObjectException)));
            throw deadObjectException;
        }
        catch (RemoteException remoteException) {
            ((zza)this).zzs(zza.zzb(remoteException));
            return;
        }
    }

    protected abstract void zzb(zzbo<?> var1) throws RemoteException;

    @Override
    public void zzs(Status status) {
        this.zzedx.trySetException(new ApiException(status));
    }
}

