/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.DeadObjectException
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbz;

public abstract class zzm<R extends Result, A extends Api.zzb>
extends BasePendingResult<R>
implements zzn<R> {
    private final Api<?> zzfin;
    private final Api.zzc<A> zzfok;

    protected zzm(Api<?> api, GoogleApiClient googleApiClient) {
        super(zzbq.checkNotNull(googleApiClient, "GoogleApiClient must not be null"));
        zzbq.checkNotNull(api, "Api must not be null");
        this.zzfok = api.zzagf();
        this.zzfin = api;
    }

    private final void zzc(RemoteException remoteException) {
        this.zzu(new Status(8, remoteException.getLocalizedMessage(), null));
    }

    protected abstract void zza(A var1) throws RemoteException;

    public final Api.zzc<A> zzagf() {
        return this.zzfok;
    }

    public final Api<?> zzagl() {
        return this.zzfin;
    }

    public final void zzb(A a2) throws DeadObjectException {
        Object object = a2;
        if (a2 instanceof zzbz) {
            object = zzbz.zzals();
        }
        try {
            this.zza(object);
            return;
        }
        catch (DeadObjectException deadObjectException) {
            this.zzc((RemoteException)((Object)deadObjectException));
            throw deadObjectException;
        }
        catch (RemoteException remoteException) {
            this.zzc(remoteException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzu(Status status) {
        boolean bl = !status.isSuccess();
        zzbq.checkArgument(bl, "Failed result must not be success");
        ((BasePendingResult)this).setResult(this.zzb(status));
    }
}

