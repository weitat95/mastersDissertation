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
import com.google.android.gms.internal.zzbwc;
import com.google.android.gms.internal.zzbwd;
import com.google.android.gms.internal.zzbwf;
import com.google.android.gms.internal.zzbxq;
import com.google.android.gms.internal.zzbxr;

public final class zzbwb
extends zzbvc<zzbxq> {
    public static final Api<Object> API;
    private static Api.zzf<zzbwb> zzebf;
    public static final Api<Object> zzhen;

    static {
        zzebf = new Api.zzf();
        API = new Api<Object>("Fitness.RECORDING_API", new zzbwd(null), zzebf);
        zzhen = new Api<Object>("Fitness.RECORDING_CLIENT", new zzbwf(null), zzebf);
    }

    private zzbwb(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 56, connectionCallbacks, onConnectionFailedListener, zzr2);
    }

    /* synthetic */ zzbwb(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzbwc zzbwc2) {
        this(context, looper, zzr2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitRecordingApi");
        if (iInterface instanceof zzbxq) {
            return (zzbxq)iInterface;
        }
        return new zzbxr(iBinder);
    }

    @Override
    public final String zzhi() {
        return "com.google.android.gms.fitness.RecordingApi";
    }

    @Override
    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitRecordingApi";
    }
}

