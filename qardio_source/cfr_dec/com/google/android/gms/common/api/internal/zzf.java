/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzae;
import com.google.android.gms.common.api.internal.zzb;
import com.google.android.gms.common.api.internal.zzbo;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.common.api.internal.zzcr;
import com.google.android.gms.common.api.internal.zzdn;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;

public final class zzf
extends zzb<Boolean> {
    private zzck<?> zzfnu;

    public zzf(zzck<?> zzck2, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zzfnu = zzck2;
    }

    @Override
    public final void zzb(zzbo<?> zzbo2) throws RemoteException {
        zzcr zzcr2 = zzbo2.zzaiy().remove(this.zzfnu);
        if (zzcr2 != null) {
            zzcr2.zzfnr.zzc(zzbo2.zzahp(), this.zzedx);
            zzcr2.zzfnq.zzajp();
            return;
        }
        this.zzedx.trySetResult(false);
    }
}

