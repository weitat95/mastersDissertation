/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Message
 */
package com.google.android.gms.common.api.internal;

import android.os.Message;
import com.google.android.gms.common.api.internal.zzbm;
import com.google.android.gms.common.api.internal.zzl;

final class zzbn
implements zzl {
    private /* synthetic */ zzbm zzfti;

    zzbn(zzbm zzbm2) {
        this.zzfti = zzbm2;
    }

    @Override
    public final void zzbf(boolean bl) {
        zzbm.zza(this.zzfti).sendMessage(zzbm.zza(this.zzfti).obtainMessage(1, (Object)bl));
    }
}

