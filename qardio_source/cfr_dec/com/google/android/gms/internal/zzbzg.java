/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.internal.zzbwu;
import com.google.android.gms.internal.zzbzf;

final class zzbzg
extends zzbwu {
    private /* synthetic */ zzbzf zzhff;

    zzbzg(zzbzf zzbzf2) {
        this.zzhff = zzbzf2;
    }

    @Override
    public final void zza(DailyTotalResult dailyTotalResult) throws RemoteException {
        ((BasePendingResult)this.zzhff).setResult(dailyTotalResult);
    }
}

