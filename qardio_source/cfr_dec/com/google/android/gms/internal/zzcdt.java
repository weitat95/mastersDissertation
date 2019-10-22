/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzcdu;
import com.google.android.gms.internal.zzcez;
import com.google.android.gms.internal.zzcfa;
import com.google.android.gms.internal.zzcfu;

public class zzcdt
extends zzab<zzcez> {
    private final String zziks;
    protected final zzcfu<zzcez> zzikt = new zzcdu(this);

    public zzcdt(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String string2, zzr zzr2) {
        super(context, looper, 23, zzr2, connectionCallbacks, onConnectionFailedListener);
        this.zziks = string2;
    }

    static /* synthetic */ void zza(zzcdt zzcdt2) {
        zzcdt2.zzakm();
    }

    @Override
    protected final Bundle zzaap() {
        Bundle bundle = new Bundle();
        bundle.putString("client_name", this.zziks);
        return bundle;
    }

    @Override
    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
        if (iInterface instanceof zzcez) {
            return (zzcez)iInterface;
        }
        return new zzcfa(iBinder);
    }

    @Override
    protected final String zzhi() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    @Override
    protected final String zzhj() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }
}

