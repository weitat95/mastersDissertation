/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.samsung.android.sdk.healthdata;

import android.os.RemoteException;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthDevice;
import com.samsung.android.sdk.healthdata.IDeviceManager;
import com.samsung.android.sdk.internal.healthdata.ErrorUtil;

public class HealthDeviceManager {
    private final HealthDataStore a;

    public HealthDeviceManager(HealthDataStore healthDataStore) {
        this.a = healthDataStore;
    }

    private IDeviceManager a() {
        IDeviceManager iDeviceManager;
        block3: {
            try {
                iDeviceManager = HealthDataStore.getInterface(this.a).getIDeviceManager();
                if (iDeviceManager != null) break block3;
            }
            catch (RemoteException remoteException) {
                throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
            }
            throw new IllegalStateException("IDeviceManager is null");
        }
        return iDeviceManager;
    }

    private static void a(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException(ErrorUtil.getNullArgumentMessage());
        }
    }

    private void b() {
        if (this.a() == null) {
            throw new IllegalStateException("Illegal store connection state");
        }
    }

    public HealthDevice getDeviceBySeed(String object) {
        HealthDeviceManager.a((String)object);
        this.b();
        IDeviceManager iDeviceManager = this.a();
        try {
            object = iDeviceManager.getRegisteredDevice((String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
        }
    }

    public HealthDevice getLocalDevice() {
        this.b();
        Object object = this.a();
        try {
            object = object.getLocalDevice();
            return object;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
        }
    }

    public String registerDevice(HealthDevice object) {
        if (object == null) {
            throw new IllegalArgumentException(ErrorUtil.getNullArgumentMessage());
        }
        IDeviceManager iDeviceManager = this.a();
        this.b();
        try {
            object = iDeviceManager.registerDevice((HealthDevice)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw new IllegalStateException(ErrorUtil.getRemoteExceptionMessage((Exception)((Object)remoteException)));
        }
    }
}

