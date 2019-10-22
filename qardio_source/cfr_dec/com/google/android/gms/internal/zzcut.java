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
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzcuq;
import com.google.android.gms.internal.zzcur;

public final class zzcut
extends zzab<zzcuq> {
    public zzcut(Context context, Looper looper, zzr zzr2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 51, zzr2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.phenotype.internal.IPhenotypeService");
        if (iInterface instanceof zzcuq) {
            return (zzcuq)iInterface;
        }
        return new zzcur(iBinder);
    }

    @Override
    protected final String zzhi() {
        return "com.google.android.gms.phenotype.service.START";
    }

    @Override
    protected final String zzhj() {
        return "com.google.android.gms.phenotype.internal.IPhenotypeService";
    }
}

