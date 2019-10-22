/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 */
package com.getqardio.android.device_related_services.map;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.getqardio.android.device_related_services.map.QLatLng;
import com.getqardio.android.device_related_services.map.QPoint;
import java.util.List;

public interface MapUiHelper {
    public void addPin(QLatLng var1, int var2, boolean var3);

    public void convertLocationToScreenPosition(QLatLng var1, LocationConverted var2);

    public void getCameraPosition(CameraPositionGot var1);

    public View getMapView(Context var1, Bundle var2, MapInitiated var3);

    public boolean isMapLoaded();

    public void listenOnMapLoaded(MapLoaded var1);

    public void onDestroy();

    public void onLowMemory();

    public void onPause();

    public void onResume();

    public void zoomCamera(QLatLng var1, float var2);

    public void zoomCameraToCoordinatesCenter(List<QLatLng> var1, int var2);

    public static interface CameraPositionGot {
        public void cameraPositionGot(QLatLng var1, float var2);
    }

    public static interface LocationConverted {
        public void locationConverted(QPoint var1);
    }

    public static interface MapInitiated {
        public void mapInitiated();
    }

    public static interface MapLoaded {
        public void mapLoaded();
    }

}

