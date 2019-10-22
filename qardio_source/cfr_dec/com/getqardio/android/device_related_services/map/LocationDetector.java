/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.getqardio.android.device_related_services.map;

import android.app.Activity;
import com.getqardio.android.device_related_services.map.QLatLng;

public interface LocationDetector {
    public void startTrackLocation(LocationListener var1, Activity var2);

    public void stopTrackLocation();

    public static interface LocationListener {
        public void onLocationChanged(QLatLng var1);
    }

}

