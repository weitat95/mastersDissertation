/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Message
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import com.google.android.gms.common.internal.zzay;
import com.google.android.gms.common.internal.zzaz;
import com.google.android.gms.common.internal.zzd;

public final class zzl
implements ServiceConnection {
    private /* synthetic */ zzd zzfza;
    private final int zzfzd;

    public zzl(zzd zzd2, int n) {
        this.zzfza = zzd2;
        this.zzfzd = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onServiceConnected(ComponentName object, IBinder iBinder) {
        if (iBinder == null) {
            zzd.zza(this.zzfza, 16);
            return;
        }
        Object object2 = zzd.zza(this.zzfza);
        synchronized (object2) {
            zzd zzd2 = this.zzfza;
            object = iBinder == null ? null : ((object = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker")) != null && object instanceof zzay ? (zzay)object : new zzaz(iBinder));
            zzd.zza(zzd2, (zzay)object);
        }
        this.zzfza.zza(0, null, this.zzfzd);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onServiceDisconnected(ComponentName object) {
        object = zzd.zza(this.zzfza);
        synchronized (object) {
            zzd.zza(this.zzfza, null);
        }
        this.zzfza.mHandler.sendMessage(this.zzfza.mHandler.obtainMessage(6, this.zzfzd, 1));
    }
}

