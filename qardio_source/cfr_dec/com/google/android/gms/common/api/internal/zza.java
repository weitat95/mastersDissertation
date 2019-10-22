/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.RemoteException
 *  android.os.TransactionTooLargeException
 */
package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzae;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.util.zzq;

public abstract class zza {
    private int zzeie;

    public zza(int n) {
        this.zzeie = n;
    }

    private static Status zza(RemoteException remoteException) {
        StringBuilder stringBuilder = new StringBuilder();
        if (zzq.zzamh() && remoteException instanceof TransactionTooLargeException) {
            stringBuilder.append("TransactionTooLargeException: ");
        }
        stringBuilder.append(remoteException.getLocalizedMessage());
        return new Status(8, stringBuilder.toString());
    }

    static /* synthetic */ Status zzb(RemoteException remoteException) {
        return zza.zza(remoteException);
    }

    public abstract void zza(zzae var1, boolean var2);

    public abstract void zza(zzbo<?> var1) throws DeadObjectException;

    public abstract void zzs(Status var1);
}

