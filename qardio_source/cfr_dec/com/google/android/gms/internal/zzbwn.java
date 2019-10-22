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
import com.google.android.gms.internal.zzbwo;
import com.google.android.gms.internal.zzbwp;
import com.google.android.gms.internal.zzbwr;
import com.google.android.gms.internal.zzbxu;
import com.google.android.gms.internal.zzbxv;

public final class zzbwn
extends zzbvc<zzbxu> {
    public static final Api<Object> API;
    private static Api.zzf<zzbwn> zzebf;
    public static final Api<Object> zzhen;

    static {
        zzebf = new Api.zzf();
        API = new Api<Object>("Fitness.SESSIONS_API", new zzbwp(null), zzebf);
        zzhen = new Api<Object>("Fitness.SESSIONS_CLIENT", new zzbwr(null), zzebf);
    }

    private zzbwn(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 58, connectionCallbacks, onConnectionFailedListener, zzr2);
    }

    /* synthetic */ zzbwn(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzbwo zzbwo2) {
        this(context, looper, zzr2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
        if (iInterface instanceof zzbxu) {
            return (zzbxu)iInterface;
        }
        return new zzbxv(iBinder);
    }

    @Override
    public final String zzhi() {
        return "com.google.android.gms.fitness.SessionsApi";
    }

    @Override
    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi";
    }
}

