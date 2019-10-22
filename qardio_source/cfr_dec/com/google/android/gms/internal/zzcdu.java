/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.IInterface
 */
package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.IInterface;
import com.google.android.gms.internal.zzcdt;
import com.google.android.gms.internal.zzcez;
import com.google.android.gms.internal.zzcfu;

final class zzcdu
implements zzcfu<zzcez> {
    private /* synthetic */ zzcdt zziku;

    zzcdu(zzcdt zzcdt2) {
        this.zziku = zzcdt2;
    }

    @Override
    public final void zzakm() {
        zzcdt.zza(this.zziku);
    }

    @Override
    public final /* synthetic */ IInterface zzakn() throws DeadObjectException {
        return (zzcez)this.zziku.zzakn();
    }
}

