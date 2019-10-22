/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.fitness.zzh;
import java.util.Set;

public abstract class zzbvc<T extends IInterface>
extends zzab<T> {
    protected zzbvc(Context context, Looper looper, int n, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzr zzr2) {
        super(context, looper, n, zzr2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    public final boolean zzaay() {
        return !zzi.zzcs(this.getContext());
    }

    @Override
    public final boolean zzako() {
        return true;
    }

    @Override
    protected final Set<Scope> zzb(Set<Scope> set) {
        return zzh.zzi(set);
    }

    @Override
    public abstract T zzd(IBinder var1);

    @Override
    public abstract String zzhi();

    @Override
    public abstract String zzhj();
}

