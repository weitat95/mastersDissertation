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
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzche;
import com.google.android.gms.internal.zzchg;

public final class zzchl
extends zzd<zzche> {
    public zzchl(Context context, Looper looper, zzf zzf2, zzg zzg2) {
        super(context, looper, 93, zzf2, zzg2, null);
    }

    @Override
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
        if (iInterface instanceof zzche) {
            return (zzche)iInterface;
        }
        return new zzchg(iBinder);
    }

    @Override
    protected final String zzhi() {
        return "com.google.android.gms.measurement.START";
    }

    @Override
    protected final String zzhj() {
        return "com.google.android.gms.measurement.internal.IMeasurementService";
    }
}

