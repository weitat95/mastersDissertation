/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Point
 *  android.os.Bundle
 *  android.view.View
 */
package com.getqardio.android.device_related_services.map;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import com.getqardio.android.device_related_services.map.MapUiHelper;
import com.getqardio.android.device_related_services.map.QLatLng;
import com.getqardio.android.device_related_services.map.QPoint;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapUiHelperImpl
implements MapUiHelper {
    private boolean isMapLoaded;
    private MapView mapView;

    public static MapUiHelper getInstance() {
        return new MapUiHelperImpl();
    }

    @Override
    public void addPin(final QLatLng qLatLng, final int n, final boolean bl) {
        this.mapView.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (bl) {
                    googleMap.clear();
                }
                if (qLatLng == null) {
                    return;
                }
                LatLng latLng = new LatLng(qLatLng.getLatitude(), qLatLng.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(n)));
            }
        });
    }

    @Override
    public void convertLocationToScreenPosition(final QLatLng qLatLng, final MapUiHelper.LocationConverted locationConverted) {
        this.mapView.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap object) {
                LatLng latLng = new LatLng(qLatLng.getLatitude(), qLatLng.getLongitude());
                object = ((GoogleMap)object).getProjection().toScreenLocation(latLng);
                object = new QPoint(((Point)object).x, ((Point)object).y);
                locationConverted.locationConverted((QPoint)object);
            }
        });
    }

    @Override
    public void getCameraPosition(final MapUiHelper.CameraPositionGot cameraPositionGot) {
        this.mapView.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap object) {
                object = ((GoogleMap)object).getCameraPosition();
                Object object2 = ((CameraPosition)object).target;
                object2 = new QLatLng(((LatLng)object2).latitude, ((LatLng)object2).longitude);
                cameraPositionGot.cameraPositionGot((QLatLng)object2, ((CameraPosition)object).zoom);
            }
        });
    }

    @Override
    public View getMapView(Context context, Bundle bundle, final MapUiHelper.MapInitiated mapInitiated) {
        MapsInitializer.initialize(context);
        this.mapView = new MapView(context);
        this.mapView.onCreate(bundle);
        this.mapView.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.getUiSettings().setTiltGesturesEnabled(false);
                if (mapInitiated != null) {
                    mapInitiated.mapInitiated();
                }
            }
        });
        return this.mapView;
    }

    @Override
    public boolean isMapLoaded() {
        return this.isMapLoaded;
    }

    @Override
    public void listenOnMapLoaded(final MapUiHelper.MapLoaded mapLoaded) {
        this.mapView.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (googleMap != null) {
                    googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback(){

                        @Override
                        public void onMapLoaded() {
                            MapUiHelperImpl.this.isMapLoaded = true;
                            mapLoaded.mapLoaded();
                        }
                    });
                }
            }

        });
    }

    @Override
    public void onDestroy() {
        if (this.mapView != null) {
            this.mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        this.mapView.onLowMemory();
    }

    @Override
    public void onPause() {
        this.mapView.onPause();
    }

    @Override
    public void onResume() {
        this.mapView.onResume();
    }

    @Override
    public void zoomCamera(final QLatLng qLatLng, final float f) {
        this.mapView.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(qLatLng.getLatitude(), qLatLng.getLongitude()), f));
            }
        });
    }

    @Override
    public void zoomCameraToCoordinatesCenter(final List<QLatLng> list, final int n) {
        this.mapView.getMapAsync(new OnMapReadyCallback(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Object object = new LatLngBounds.Builder();
                for (QLatLng qLatLng : list) {
                    ((LatLngBounds.Builder)object).include(new LatLng(qLatLng.getLatitude(), qLatLng.getLongitude()));
                }
                object = ((LatLngBounds.Builder)object).build();
                object = list.size() == 1 ? CameraUpdateFactory.newLatLngZoom(((LatLngBounds)object).northeast, 12.0f) : CameraUpdateFactory.newLatLngBounds((LatLngBounds)object, n);
                googleMap.animateCamera((CameraUpdate)object);
            }
        });
    }

}

