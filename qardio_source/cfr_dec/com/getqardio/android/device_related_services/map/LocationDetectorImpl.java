/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.location.Location
 *  android.os.Bundle
 */
package com.getqardio.android.device_related_services.map;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import com.getqardio.android.device_related_services.map.LocationDetector;
import com.getqardio.android.device_related_services.map.LocationDetectorImpl$$Lambda$1;
import com.getqardio.android.device_related_services.map.LocationDetectorImpl$1$$Lambda$1;
import com.getqardio.android.device_related_services.map.QLatLng;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import timber.log.Timber;

public class LocationDetectorImpl
implements LocationDetector {
    private static LocationDetector instance;
    private GoogleApiClient locationClient;

    private LocationDetectorImpl() {
    }

    public static LocationDetector getInstance() {
        if (instance == null) {
            instance = new LocationDetectorImpl();
        }
        return instance;
    }

    static /* synthetic */ void lambda$startTrackLocation$0(ConnectionResult connectionResult) {
        Timber.e("Location connection failed: %s", connectionResult.toString());
    }

    @Override
    public void startTrackLocation(final LocationDetector.LocationListener locationListener, final Activity activity) {
        if (this.locationClient != null) {
            this.locationClient.disconnect();
            this.locationClient = null;
        }
        this.locationClient = new GoogleApiClient.Builder((Context)activity).addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks(){

            static /* synthetic */ void lambda$onConnected$0(LocationDetector.LocationListener locationListener2, Location location) {
                locationListener2.onLocationChanged(new QLatLng(location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onConnected(Bundle object) {
                object = LocationRequest.create();
                ((LocationRequest)object).setInterval(30000L);
                ((LocationRequest)object).setFastestInterval(5000L);
                ((LocationRequest)object).setPriority(102);
                if (ActivityCompat.checkSelfPermission((Context)activity, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission((Context)activity, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    return;
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(LocationDetectorImpl.this.locationClient, (LocationRequest)object, LocationDetectorImpl$1$$Lambda$1.lambdaFactory$(locationListener));
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onConnectionSuspended(int n) {
                if (n == 2) {
                    Timber.i("Connection lost.  Cause: Network Lost.", new Object[0]);
                    return;
                } else {
                    if (n != 1) return;
                    {
                        Timber.i("Connection lost.  Reason: Service Disconnected", new Object[0]);
                        return;
                    }
                }
            }
        }).addOnConnectionFailedListener(LocationDetectorImpl$$Lambda$1.lambdaFactory$()).build();
        this.locationClient.connect();
    }

    @Override
    public void stopTrackLocation() {
        if (this.locationClient != null) {
            this.locationClient.disconnect();
            this.locationClient = null;
        }
    }

}

