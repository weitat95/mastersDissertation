/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.IBinder
 */
package android.arch.lifecycle;

import android.app.Service;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ServiceLifecycleDispatcher;
import android.content.Intent;
import android.os.IBinder;

public class LifecycleService
extends Service
implements LifecycleOwner {
    private final ServiceLifecycleDispatcher mDispatcher = new ServiceLifecycleDispatcher(this);

    @Override
    public Lifecycle getLifecycle() {
        return this.mDispatcher.getLifecycle();
    }

    public IBinder onBind(Intent intent) {
        this.mDispatcher.onServicePreSuperOnBind();
        return null;
    }

    public void onCreate() {
        this.mDispatcher.onServicePreSuperOnCreate();
        super.onCreate();
    }

    public void onDestroy() {
        this.mDispatcher.onServicePreSuperOnDestroy();
        super.onDestroy();
    }

    public void onStart(Intent intent, int n) {
        this.mDispatcher.onServicePreSuperOnStart();
        super.onStart(intent, n);
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        return super.onStartCommand(intent, n, n2);
    }
}

