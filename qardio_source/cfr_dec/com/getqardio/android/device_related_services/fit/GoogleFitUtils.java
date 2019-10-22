/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.Log
 */
package com.getqardio.android.device_related_services.fit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.getqardio.android.device_related_services.fit.GoogleFitUtils$$Lambda$1;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.HistoryApi;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzbfm;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class GoogleFitUtils {
    private static final long DEFAULT_AWAIT_TIME;
    private static final TimeUnit DEFAULT_TIME_UNIT;
    private static final String TAG;

    static {
        TAG = GoogleFitUtils.class.getName();
        DEFAULT_TIME_UNIT = TimeUnit.MINUTES;
        DEFAULT_AWAIT_TIME = DEFAULT_TIME_UNIT.toMillis(1L);
    }

    private GoogleFitUtils() {
    }

    public static GoogleApiClient buildBloodPresureClient(Context context) {
        return new GoogleApiClient.Builder(context).addApi(Fitness.HISTORY_API).addApi(Fitness.CONFIG_API).addApi(Fitness.RECORDING_API).addScope(new Scope("https://www.googleapis.com/auth/fitness.blood_pressure.write")).build();
    }

    public static GoogleApiClient buildNewActivityTrackerClient(Context context) {
        return new GoogleApiClient.Builder(context).addApi(Fitness.HISTORY_API).addApi(Fitness.CONFIG_API).addApi(Fitness.RECORDING_API).addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE).build();
    }

    public static GoogleApiClient buildNewClient(Context context) {
        return new GoogleApiClient.Builder(context).addApi(Fitness.HISTORY_API).addApi(Fitness.CONFIG_API).addScope(Fitness.SCOPE_BODY_READ_WRITE).addScope(new Scope("https://www.googleapis.com/auth/fitness.blood_pressure.write")).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks(){

            @Override
            public void onConnected(Bundle bundle) {
                Log.i((String)TAG, (String)"Connected to Fit API");
            }

            @Override
            public void onConnectionSuspended(int n) {
                String string2 = "";
                if (n == 2) {
                    string2 = "Network lost";
                }
                if (n == 1) {
                    string2 = "disconnected";
                }
                Log.i((String)TAG, (String)("Connection suspended: " + string2));
            }
        }).addOnConnectionFailedListener(GoogleFitUtils$$Lambda$1.lambdaFactory$()).build();
    }

    public static GoogleApiClient buildOnBoardingClient(FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new GoogleApiClient.Builder((Context)fragmentActivity).addApi(Fitness.HISTORY_API).addApi(Fitness.CONFIG_API).addApi(Fitness.RECORDING_API).addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE).addScope(Fitness.SCOPE_BODY_READ_WRITE).addScope(new Scope("https://www.googleapis.com/auth/fitness.blood_pressure.write")).build();
    }

    public static void delete(GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest) {
        block3: {
            block2: {
                if (!GoogleFitUtils.isGoogleClientConnected(googleApiClient)) break block2;
                if (Fitness.HistoryApi.deleteData(googleApiClient, dataDeleteRequest).await(DEFAULT_AWAIT_TIME, DEFAULT_TIME_UNIT).isSuccess()) break block3;
                Timber.i("There was a problem deleting data", new Object[0]);
            }
            return;
        }
        Timber.i("Data delete was successful!", new Object[0]);
    }

    public static void insert(GoogleApiClient googleApiClient, DataSet dataSet) {
        block3: {
            block2: {
                if (!GoogleFitUtils.isGoogleClientConnected(googleApiClient)) break block2;
                if (Fitness.HistoryApi.insertData(googleApiClient, dataSet).await(DEFAULT_AWAIT_TIME, DEFAULT_TIME_UNIT).isSuccess()) break block3;
                Timber.d("There was a problem inserting data set", new Object[0]);
            }
            return;
        }
        Timber.d("Data insert was successful", new Object[0]);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isGoogleClientConnected(GoogleApiClient googleApiClient) {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            return true;
        }
        boolean bl = false;
        if (bl) return bl;
        Timber.i("Google Api Client is null or not connected", new Object[0]);
        return bl;
    }

    static /* synthetic */ void lambda$buildNewClient$0(ConnectionResult connectionResult) {
        Log.i((String)TAG, (String)("Connection failed. Cause: " + connectionResult.toString()));
    }

    public static List<DataSet> read(GoogleApiClient googleApiClient, DataType dataType) {
        if (GoogleFitUtils.isGoogleClientConnected(googleApiClient)) {
            return GoogleFitUtils.readInternal(googleApiClient, dataType).getDataSets();
        }
        return null;
    }

    private static DataReadResult readInternal(GoogleApiClient googleApiClient, DataType zzbfm2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long l = calendar.getTimeInMillis();
        calendar.add(3, -1);
        long l2 = calendar.getTimeInMillis();
        zzbfm2 = new DataReadRequest.Builder().read((DataType)zzbfm2).setTimeRange(l2, l, TimeUnit.MILLISECONDS).build();
        return Fitness.HistoryApi.readData(googleApiClient, (DataReadRequest)zzbfm2).await(DEFAULT_AWAIT_TIME, DEFAULT_TIME_UNIT);
    }

}

