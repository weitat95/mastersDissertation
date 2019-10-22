/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class UiSettings {
    private final IUiSettingsDelegate zziua;

    UiSettings(IUiSettingsDelegate iUiSettingsDelegate) {
        this.zziua = iUiSettingsDelegate;
    }

    public final void setTiltGesturesEnabled(boolean bl) {
        try {
            this.zziua.setTiltGesturesEnabled(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }
}

