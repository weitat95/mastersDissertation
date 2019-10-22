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
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzbvc;
import com.google.android.gms.internal.zzbvr;
import com.google.android.gms.internal.zzbvs;
import com.google.android.gms.internal.zzbvu;
import com.google.android.gms.internal.zzbxm;
import com.google.android.gms.internal.zzbxn;

public final class zzbvq
extends zzbvc<zzbxm> {
    public static final Api<Object> API;
    private static Api.zzf<zzbvq> zzebf;
    public static final Api<Object> zzhen;

    static {
        zzebf = new Api.zzf();
        API = new Api<Object>("Fitness.GOALS_API", new zzbvs(null), zzebf);
        zzhen = new Api<Object>("Fitness.GOALS_CLIENT", new zzbvu(null), zzebf);
    }

    private zzbvq(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 125, connectionCallbacks, onConnectionFailedListener, zzr2);
    }

    /* synthetic */ zzbvq(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzbvr zzbvr2) {
        this(context, looper, zzr2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitGoalsApi");
        if (iInterface instanceof zzbxm) {
            return (zzbxm)iInterface;
        }
        return new zzbxn(iBinder);
    }

    @Override
    public final String zzhi() {
        return "com.google.android.gms.fitness.GoalsApi";
    }

    @Override
    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitGoalsApi";
    }
}

