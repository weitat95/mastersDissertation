/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.internal.zzal;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.internal.zzp;
import com.google.android.gms.maps.zzk;

public final class GoogleMap {
    private final IGoogleMapDelegate zziqy;
    private UiSettings zziqz;

    public GoogleMap(IGoogleMapDelegate iGoogleMapDelegate) {
        this.zziqy = zzbq.checkNotNull(iGoogleMapDelegate);
    }

    public final Marker addMarker(MarkerOptions object) {
        block3: {
            try {
                object = this.zziqy.addMarker((MarkerOptions)object);
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeRemoteException(remoteException);
            }
            object = new Marker((zzp)object);
            return object;
        }
        return null;
    }

    public final void animateCamera(CameraUpdate cameraUpdate) {
        try {
            this.zziqy.animateCamera(cameraUpdate.zzavz());
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    public final void clear() {
        try {
            this.zziqy.clear();
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    public final CameraPosition getCameraPosition() {
        try {
            CameraPosition cameraPosition = this.zziqy.getCameraPosition();
            return cameraPosition;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    public final Projection getProjection() {
        try {
            Projection projection = new Projection(this.zziqy.getProjection());
            return projection;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.zziqz == null) {
                this.zziqz = new UiSettings(this.zziqy.getUiSettings());
            }
            UiSettings uiSettings = this.zziqz;
            return uiSettings;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeRemoteException(remoteException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void setOnMapLoadedCallback(OnMapLoadedCallback var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.zziqy.setOnMapLoadedCallback(null);
            return;
lbl5:
            // 1 sources
            this.zziqy.setOnMapLoadedCallback(new zzk(this, var1_1));
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public static interface OnMapLoadedCallback {
        public void onMapLoaded();
    }

}

