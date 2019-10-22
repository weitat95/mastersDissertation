/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class zzcq<A extends Api.zzb, L> {
    private final zzci<L> zzfus;

    public final void zzajp() {
        this.zzfus.clear();
    }

    protected abstract void zzb(A var1, TaskCompletionSource<Void> var2) throws RemoteException;
}

