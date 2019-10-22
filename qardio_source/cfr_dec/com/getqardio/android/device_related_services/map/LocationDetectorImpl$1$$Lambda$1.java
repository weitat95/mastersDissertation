/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.getqardio.android.device_related_services.map;

import android.location.Location;
import com.getqardio.android.device_related_services.map.LocationDetector;
import com.getqardio.android.device_related_services.map.LocationDetectorImpl;
import com.google.android.gms.location.LocationListener;
import java.lang.invoke.LambdaForm;

final class LocationDetectorImpl$1$$Lambda$1
implements LocationListener {
    private final LocationDetector.LocationListener arg$1;

    private LocationDetectorImpl$1$$Lambda$1(LocationDetector.LocationListener locationListener) {
        this.arg$1 = locationListener;
    }

    public static LocationListener lambdaFactory$(LocationDetector.LocationListener locationListener) {
        return new LocationDetectorImpl$1$$Lambda$1(locationListener);
    }

    @LambdaForm.Hidden
    @Override
    public void onLocationChanged(Location location) {
        LocationDetectorImpl.1.lambda$onConnected$0(this.arg$1, location);
    }
}

