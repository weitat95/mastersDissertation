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
import com.google.android.gms.internal.zzbwi;
import com.google.android.gms.internal.zzbwj;
import com.google.android.gms.internal.zzbwl;
import com.google.android.gms.internal.zzbxs;
import com.google.android.gms.internal.zzbxt;

public final class zzbwh
extends zzbvc<zzbxs> {
    public static final Api<Object> API;
    private static Api.zzf<zzbwh> zzebf;
    public static final Api<Object> zzhen;

    static {
        zzebf = new Api.zzf();
        API = new Api<Object>("Fitness.SENSORS_API", new zzbwj(null), zzebf);
        zzhen = new Api<Object>("Fitness.SENSORS_CLIENT", new zzbwl(null), zzebf);
    }

    private zzbwh(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 55, connectionCallbacks, onConnectionFailedListener, zzr2);
    }

    /* synthetic */ zzbwh(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzbwi zzbwi2) {
        this(context, looper, zzr2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitSensorsApi");
        if (iInterface instanceof zzbxs) {
            return (zzbxs)iInterface;
        }
        return new zzbxt(iBinder);
    }

    @Override
    public final String zzhi() {
        return "com.google.android.gms.fitness.SensorsApi";
    }

    @Override
    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi";
    }
}

