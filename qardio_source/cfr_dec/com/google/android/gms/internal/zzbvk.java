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
import com.google.android.gms.internal.zzbvl;
import com.google.android.gms.internal.zzbvm;
import com.google.android.gms.internal.zzbvo;
import com.google.android.gms.internal.zzbxk;
import com.google.android.gms.internal.zzbxl;

public final class zzbvk
extends zzbvc<zzbxk> {
    public static final Api<Object> API;
    private static Api.zzf<zzbvk> zzebf;
    public static final Api<Object> zzhen;

    static {
        zzebf = new Api.zzf();
        API = new Api<Object>("Fitness.CONFIG_API", new zzbvm(null), zzebf);
        zzhen = new Api<Object>("Fitness.CONFIG_CLIENT", new zzbvo(null), zzebf);
    }

    private zzbvk(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 60, connectionCallbacks, onConnectionFailedListener, zzr2);
    }

    /* synthetic */ zzbvk(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzbvl zzbvl2) {
        this(context, looper, zzr2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitConfigApi");
        if (iInterface instanceof zzbxk) {
            return (zzbxk)iInterface;
        }
        return new zzbxl(iBinder);
    }

    @Override
    public final String zzhi() {
        return "com.google.android.gms.fitness.ConfigApi";
    }

    @Override
    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitConfigApi";
    }
}

