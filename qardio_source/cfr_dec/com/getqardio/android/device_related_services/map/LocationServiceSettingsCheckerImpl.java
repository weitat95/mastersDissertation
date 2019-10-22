/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.location.LocationManager
 *  android.widget.Toast
 */
package com.getqardio.android.device_related_services.map;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.getqardio.android.device_related_services.map.LocationServiceSettingsChecker;
import com.getqardio.android.device_related_services.map.LocationServiceSettingsCheckerImpl$$Lambda$1;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsApi;

public class LocationServiceSettingsCheckerImpl
implements LocationServiceSettingsChecker {
    private static LocationServiceSettingsCheckerImpl instance;
    private GoogleApiClient googleApiClient;
    private Status status;

    private LocationServiceSettingsCheckerImpl() {
    }

    public static LocationServiceSettingsChecker getInstance() {
        if (instance == null) {
            instance = new LocationServiceSettingsCheckerImpl();
        }
        return instance;
    }

    @Override
    public void enableLocation(FragmentActivity fragmentActivity, int n) {
        this.googleApiClient = new GoogleApiClient.Builder((Context)fragmentActivity).addApi(LocationServices.API).build();
        this.googleApiClient.connect();
        Object object = LocationRequest.create();
        ((LocationRequest)object).setPriority(104);
        object = new LocationSettingsRequest.Builder().addLocationRequest((LocationRequest)object);
        ((LocationSettingsRequest.Builder)object).setAlwaysShow(true);
        ((LocationSettingsRequest.Builder)object).setNeedBle(true);
        LocationServices.SettingsApi.checkLocationSettings(this.googleApiClient, ((LocationSettingsRequest.Builder)object).build()).setResultCallback(LocationServiceSettingsCheckerImpl$$Lambda$1.lambdaFactory$(this, fragmentActivity, n));
    }

    @Override
    public boolean isLocationAvailable(Context context) {
        return (context = (LocationManager)context.getSystemService("location")).isProviderEnabled("gps") || context.isProviderEnabled("network");
    }

    /* synthetic */ void lambda$enableLocation$0(FragmentActivity fragmentActivity, int n, LocationSettingsResult locationSettingsResult) {
        this.status = locationSettingsResult.getStatus();
        switch (this.status.getStatusCode()) {
            default: {
                return;
            }
            case 6: {
                try {
                    this.status.startResolutionForResult(fragmentActivity, n);
                    return;
                }
                catch (IntentSender.SendIntentException sendIntentException) {
                    return;
                }
            }
            case 8502: 
        }
        Toast.makeText((Context)fragmentActivity, (int)2131362392, (int)1).show();
    }

    @Override
    public void stopChecking() {
        if (this.googleApiClient != null && this.googleApiClient.isConnected()) {
            this.googleApiClient.disconnect();
        }
    }
}

