/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.BroadcastReceiver$PendingResult
 *  android.content.Context
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcid;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzclp;
import com.google.android.gms.measurement.AppMeasurement;

final class zzcie
implements Runnable {
    private /* synthetic */ Context val$context;
    private /* synthetic */ BroadcastReceiver.PendingResult zzdop;
    private /* synthetic */ zzcim zzjdt;
    private /* synthetic */ long zzjdu;
    private /* synthetic */ Bundle zzjdv;
    private /* synthetic */ zzchm zzjdw;

    zzcie(zzcid zzcid2, zzcim zzcim2, long l, Bundle bundle, Context context, zzchm zzchm2, BroadcastReceiver.PendingResult pendingResult) {
        this.zzjdt = zzcim2;
        this.zzjdu = l;
        this.zzjdv = bundle;
        this.val$context = context;
        this.zzjdw = zzchm2;
        this.zzdop = pendingResult;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void run() {
        zzclp zzclp2 = this.zzjdt.zzaws().zzag(this.zzjdt.zzawn().getAppId(), "_fot");
        long l = zzclp2 != null && zzclp2.mValue instanceof Long ? (Long)zzclp2.mValue : 0L;
        long l2 = this.zzjdu;
        l = l > 0L && (l2 >= l || l2 <= 0L) ? --l : l2;
        if (l > 0L) {
            this.zzjdv.putLong("click_timestamp", l);
        }
        AppMeasurement.getInstance(this.val$context).logEventInternal("auto", "_cmp", this.zzjdv);
        this.zzjdw.zzazj().log("Install campaign recorded");
        if (this.zzdop != null) {
            this.zzdop.finish();
        }
    }
}

