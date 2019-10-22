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
import com.google.android.gms.internal.zzbvf;
import com.google.android.gms.internal.zzbvg;
import com.google.android.gms.internal.zzbvi;
import com.google.android.gms.internal.zzbxi;
import com.google.android.gms.internal.zzbxj;

public final class zzbve
extends zzbvc<zzbxi> {
    public static final Api<Object> API;
    private static Api.zzf<zzbve> zzebf;
    public static final Api<Object> zzhen;

    static {
        zzebf = new Api.zzf();
        API = new Api<Object>("Fitness.BLE_API", new zzbvg(), zzebf);
        zzhen = new Api<Object>("Fitness.BLE_CLIENT", new zzbvi(null), zzebf);
    }

    private zzbve(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 59, connectionCallbacks, onConnectionFailedListener, zzr2);
    }

    /* synthetic */ zzbve(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzbvf zzbvf2) {
        this(context, looper, zzr2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitBleApi");
        if (iInterface instanceof zzbxi) {
            return (zzbxi)iInterface;
        }
        return new zzbxj(iBinder);
    }

    @Override
    public final String zzhi() {
        return "com.google.android.gms.fitness.BleApi";
    }

    @Override
    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitBleApi";
    }
}

