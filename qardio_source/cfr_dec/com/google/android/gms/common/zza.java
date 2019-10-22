/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 */
package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class zza
implements ServiceConnection {
    private boolean zzfkp = false;
    private final BlockingQueue<IBinder> zzfkq = new LinkedBlockingQueue<IBinder>();

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.zzfkq.add(iBinder);
    }

    public final void onServiceDisconnected(ComponentName componentName) {
    }

    public final IBinder zza(long l, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        zzbq.zzgn("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (this.zzfkp) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.zzfkp = true;
        if ((timeUnit = this.zzfkq.poll(10000L, timeUnit)) == null) {
            throw new TimeoutException("Timed out waiting for the service connection");
        }
        return timeUnit;
    }
}

