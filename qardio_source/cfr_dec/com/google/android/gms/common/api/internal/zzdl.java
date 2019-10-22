/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 */
package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zzdk;
import com.google.android.gms.common.api.internal.zzdm;
import com.google.android.gms.common.api.zze;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

final class zzdl
implements IBinder.DeathRecipient,
zzdm {
    private final WeakReference<BasePendingResult<?>> zzfvl;
    private final WeakReference<zze> zzfvm;
    private final WeakReference<IBinder> zzfvn;

    private zzdl(BasePendingResult<?> basePendingResult, zze zze2, IBinder iBinder) {
        this.zzfvm = new WeakReference<zze>(zze2);
        this.zzfvl = new WeakReference(basePendingResult);
        this.zzfvn = new WeakReference<IBinder>(iBinder);
    }

    /* synthetic */ zzdl(BasePendingResult basePendingResult, zze zze2, IBinder iBinder, zzdk zzdk2) {
        this(basePendingResult, null, iBinder);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final void zzajv() {
        BasePendingResult basePendingResult = (BasePendingResult)this.zzfvl.get();
        zze zze2 = (zze)this.zzfvm.get();
        if (zze2 != null && basePendingResult != null) {
            zze2.remove(((PendingResult)basePendingResult).zzagv());
        }
        if ((basePendingResult = (IBinder)this.zzfvn.get()) == null) return;
        try {
            basePendingResult.unlinkToDeath((IBinder.DeathRecipient)this, 0);
            return;
        }
        catch (NoSuchElementException noSuchElementException) {
            return;
        }
    }

    public final void binderDied() {
        this.zzajv();
    }

    @Override
    public final void zzc(BasePendingResult<?> basePendingResult) {
        this.zzajv();
    }
}

