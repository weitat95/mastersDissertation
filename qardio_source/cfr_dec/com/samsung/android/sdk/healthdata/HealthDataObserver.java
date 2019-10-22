/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.samsung.android.sdk.healthdata;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.IDataWatcher;
import com.samsung.android.sdk.healthdata.IHealthDataObserver;
import com.samsung.android.sdk.internal.healthdata.ErrorUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public abstract class HealthDataObserver {
    private static final ArrayList<HealthDataObserver> b = new ArrayList();
    private final Handler a;
    private final IHealthDataObserver.Stub c = new IHealthDataObserver.Stub(this){
        private /* synthetic */ HealthDataObserver a;
        {
            this.a = healthDataObserver;
        }

        @Override
        public final void onChange(String string2) {
            if (this.a.a != null) {
                this.a.a.sendMessage(this.a.a.obtainMessage(0, (Object)string2));
                return;
            }
            this.a.onChange(string2);
        }
    };

    /*
     * Enabled aggressive block sorting
     */
    public HealthDataObserver(Handler handler) {
        handler = handler != null ? handler.getLooper() : Looper.myLooper();
        if (handler == null) {
            this.a = null;
            return;
        }
        this.a = new a(this, (Looper)handler);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void addObserver(HealthDataStore healthDataStore, String string2, HealthDataObserver healthDataObserver) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("Data type is invalid");
        }
        HealthDataObserver.b(healthDataObserver);
        ArrayList<HealthDataObserver> arrayList = b;
        synchronized (arrayList) {
            if (!b.contains(healthDataObserver)) {
                b.add(healthDataObserver);
            }
            Object object = HealthDataStore.getInterface(healthDataStore);
            try {
                object = object.getIDataWatcher();
                if (object == null) {
                    throw new IllegalStateException("IDataWatcher is null");
                }
            }
            catch (RemoteException remoteException) {
                Log.d((String)"Health.Observer", (String)(string2 + " registration failed: " + remoteException.toString()));
                throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
            }
            {
                object.registerDataObserver2(healthDataStore.a().getPackageName(), string2, healthDataObserver.c);
                return;
            }
        }
    }

    private static void b(HealthDataObserver healthDataObserver) {
        if (healthDataObserver == null) {
            throw new IllegalArgumentException("Invalid observer instance");
        }
        if (healthDataObserver.a == null || healthDataObserver.a.getLooper() == null) {
            throw new IllegalStateException("This thread has no looper");
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void removeObserver(HealthDataStore healthDataStore, HealthDataObserver healthDataObserver) {
        HealthDataObserver.b(healthDataObserver);
        ArrayList<HealthDataObserver> arrayList = b;
        synchronized (arrayList) {
            b.remove(healthDataObserver);
            Object object = HealthDataStore.getInterface(healthDataStore);
            try {
                object = object.getIDataWatcher();
                if (object == null) {
                    throw new IllegalStateException("IDataWatcher is null");
                }
            }
            catch (RemoteException remoteException) {
                Log.d((String)"Health.Observer", (String)("Object unregistration failed: " + remoteException.toString()));
                throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
            }
            {
                object.unregisterDataObserver2(healthDataStore.a().getPackageName(), healthDataObserver.c);
                return;
            }
        }
    }

    public abstract void onChange(String var1);

    static final class a
    extends Handler {
        private final WeakReference<HealthDataObserver> a;

        public a(HealthDataObserver healthDataObserver, Looper looper) {
            super(looper);
            this.a = new WeakReference<HealthDataObserver>(healthDataObserver);
        }

        public final void handleMessage(Message message) {
            HealthDataObserver healthDataObserver = (HealthDataObserver)this.a.get();
            if (healthDataObserver != null) {
                healthDataObserver.onChange((String)message.obj);
            }
        }
    }

}

