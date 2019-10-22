/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.database.ContentObserver
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 */
package com.getqardio.android.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.friends_family.i_follow.DaggerIFollowComponent;
import com.getqardio.android.mvp.friends_family.i_follow.IFollowComponent;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.provider.WearableDataHelper;
import com.getqardio.android.service.WearableCommunicationService$$Lambda$1;
import com.getqardio.android.service.WearableCommunicationService$$Lambda$2;
import com.getqardio.android.service.WearableCommunicationService$$Lambda$3;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.SynchronousGoogleApiClient;
import com.getqardio.android.utils.Utils;
import com.getqardio.shared.wearable.Contract;
import com.getqardio.shared.wearable.datamodel.BPMeasurementsDescription;
import com.getqardio.shared.wearable.datamodel.FollowingData;
import com.getqardio.shared.wearable.datamodel.WeightMeasurementsDescription;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.data.zzg;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WearableCommunicationService
extends Service {
    private PBContentObserver bpObserver;
    private SynchronousGoogleApiClient googleApiClient = new SynchronousGoogleApiClient();
    private volatile ServiceHandler serviceHandler;
    private volatile Looper serviceLooper;
    private long userId;
    WearableDataHelper wearableDataHelper;
    private WeightContentObserver weightObserver;

    private void doLogout() {
        this.doSendUserState();
        this.stopSelf();
    }

    private void doSaveWearBpMeasurements() {
        boolean bl = false;
        boolean bl2 = false;
        GoogleApiClient googleApiClient = this.googleApiClient.getGoogleApiClient((Context)this);
        if (googleApiClient.isConnected()) {
            DataItemBuffer dataItemBuffer = Wearable.DataApi.getDataItems(googleApiClient).await();
            int n = 0;
            do {
                bl = bl2;
                if (n >= dataItemBuffer.getCount()) break;
                String string2 = ((DataItem)dataItemBuffer.get(n)).getUri().getPath();
                bl = bl2;
                if (Contract.WEAR_BP_MEASUREMENTS_PATTERN.matcher(string2).matches()) {
                    string2 = com.getqardio.shared.wearable.Utils.bytes2Bundle(((DataItem)dataItemBuffer.get(n)).getData());
                    if (string2 != null) {
                        BPMeasurement bPMeasurement = new BPMeasurement();
                        bPMeasurement.measureDate = new Date(string2.getLong("com.getqardio.shared.wearable.key.DATE"));
                        bPMeasurement.sys = string2.getInt("com.getqardio.shared.wearable.key.SYS");
                        bPMeasurement.dia = string2.getInt("com.getqardio.shared.wearable.key.DIA");
                        bPMeasurement.pulse = string2.getInt("com.getqardio.shared.wearable.key.PULSE");
                        bPMeasurement.irregularHeartBeat = string2.getBoolean("com.getqardio.shared.wearable.key.IHB");
                        bPMeasurement.deviceId = string2.getString("com.getqardio.shared.wearable.key.DEVICE_ID", null);
                        bPMeasurement.timezone = String.format("UTC%s", DateUtils.getTimeZoneOffset(bPMeasurement.measureDate));
                        bPMeasurement.source = 0;
                        MeasurementHelper.BloodPressure.addMeasurement((Context)this, this.userId, bPMeasurement, false);
                        bl2 = true;
                    }
                    Wearable.DataApi.deleteDataItems(googleApiClient, ((DataItem)dataItemBuffer.get(n)).getUri());
                    bl = bl2;
                }
                ++n;
                bl2 = bl;
            } while (true);
        }
        if (bl) {
            MeasurementHelper.BloodPressure.requestBPMeasurementsSync((Context)this, this.userId);
        }
    }

    private void doSendAllData() {
        this.doSendUserState();
        this.onBPChanged();
        this.onWeightChanged();
        this.doSendFollowingsData();
    }

    private void doSendFollowingsData() {
        GoogleApiClient googleApiClient = this.googleApiClient.getGoogleApiClient((Context)this);
        if (googleApiClient.isConnected()) {
            new Handler(Looper.getMainLooper()).post(WearableCommunicationService$$Lambda$1.lambdaFactory$(this, googleApiClient));
        }
    }

    private void doSendRefuseDisconnect(String string2) {
        GoogleApiClient googleApiClient = this.googleApiClient.getGoogleApiClient((Context)this);
        if (googleApiClient.isConnected()) {
            Wearable.MessageApi.sendMessage(googleApiClient, string2, "/phone/refuse_disconnect", new byte[0]);
        }
    }

    private void doSendUserState() {
        GoogleApiClient googleApiClient = this.googleApiClient.getGoogleApiClient((Context)this);
        if (googleApiClient.isConnected()) {
            Bundle bundle = new Bundle(1);
            bundle.putBoolean("com.getqardio.shared.wearable.key.IS_USER_LOGGED_IN", CustomApplication.getApplication().isUserLoggedIn());
            PutDataRequest putDataRequest = PutDataRequest.create("/user/state");
            putDataRequest.setUrgent();
            putDataRequest.setData(com.getqardio.shared.wearable.Utils.bundle2Bytes(bundle));
            Wearable.DataApi.putDataItem(googleApiClient, putDataRequest);
        }
    }

    private void enqueueAction(int n, Object object) {
        if (this.serviceHandler == null) {
            return;
        }
        Message message = this.serviceHandler.obtainMessage();
        message.arg1 = n;
        message.obj = object;
        this.serviceHandler.sendMessage(message);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static BPMeasurementsDescription[] getOldBPMeasurementsDescriptions(GoogleApiClient object) {
        Object object2;
        Object object3 = object2 = null;
        if (object == null) return object3;
        if (!((GoogleApiClient)object).isConnected()) {
            return object2;
        }
        object = Wearable.DataApi.getDataItems((GoogleApiClient)object).await();
        int n = 0;
        do {
            object3 = object2;
            if (n >= ((zzg)object).getCount()) return object3;
            if ("/user/measurements/bp".equals(((DataItem)((zzg)object).get(n)).getUri().getPath())) {
                Bundle bundle = com.getqardio.shared.wearable.Utils.bytes2Bundle(((DataItem)((zzg)object).get(n)).getData());
                object3 = object2;
                if (bundle == null) return object3;
                bundle.setClassLoader(BPMeasurementsDescription.class.getClassLoader());
                object = new BPMeasurementsDescription[3];
                if (bundle.containsKey("com.getqardio.shared.wearable.key.USER_LAST_MEASUREMENT")) {
                    object[0] = (BPMeasurementsDescription)bundle.getParcelable("com.getqardio.shared.wearable.key.USER_LAST_MEASUREMENT");
                }
                if (bundle.containsKey("com.getqardio.shared.wearable.key.USER_WEEKLY_MEASUREMENT")) {
                    object[1] = (BPMeasurementsDescription)bundle.getParcelable("com.getqardio.shared.wearable.key.USER_WEEKLY_MEASUREMENT");
                }
                object3 = object;
                if (!bundle.containsKey("com.getqardio.shared.wearable.key.USER_MONTHLY_MEASUREMENT")) return object3;
                object[2] = (BPMeasurementsDescription)bundle.getParcelable("com.getqardio.shared.wearable.key.USER_MONTHLY_MEASUREMENT");
                return object;
            }
            ++n;
        } while (true);
    }

    private static WeightMeasurementsDescription getOldLastWeightMeasurement(GoogleApiClient object) {
        if (object == null || !((GoogleApiClient)object).isConnected()) {
            return null;
        }
        object = Wearable.DataApi.getDataItems((GoogleApiClient)object).await();
        for (int i = 0; i < ((zzg)object).getCount(); ++i) {
            if (!"/user/measurements/weight/last".equals(((DataItem)((zzg)object).get(i)).getUri().getPath())) continue;
            Bundle bundle = com.getqardio.shared.wearable.Utils.bytes2Bundle(((DataItem)((zzg)object).get(i)).getData());
            if (bundle == null) {
                return null;
            }
            bundle.setClassLoader(WeightMeasurementsDescription.class.getClassLoader());
            if (!bundle.containsKey("com.getqardio.shared.wearable.key.USER_LAST_MEASUREMENT")) continue;
            return (WeightMeasurementsDescription)bundle.getParcelable("com.getqardio.shared.wearable.key.USER_LAST_MEASUREMENT");
        }
        return null;
    }

    private void initThread() {
        HandlerThread handlerThread = new HandlerThread("WearCommunicationService");
        handlerThread.start();
        this.serviceLooper = handlerThread.getLooper();
        this.serviceHandler = new ServiceHandler(this.serviceLooper);
    }

    static /* synthetic */ void lambda$null$0(GoogleApiClient googleApiClient, List object) throws Exception {
        Bundle bundle = new Bundle(1);
        bundle.putParcelableArray("com.getqardio.shared.wearable.key.FOLLOWING_MEASUREMENTS", (Parcelable[])object.toArray(new FollowingData[object.size()]));
        object = PutDataRequest.create("/followings/measurements");
        ((PutDataRequest)object).setUrgent();
        ((PutDataRequest)object).setData(com.getqardio.shared.wearable.Utils.bundle2Bytes(bundle));
        Wearable.DataApi.putDataItem(googleApiClient, (PutDataRequest)object);
    }

    public static void login(Context context) {
        Intent intent = new Intent(context, WearableCommunicationService.class);
        intent.putExtra("com.getqardio.android.extra.ACTION", 3);
        WearableCommunicationService.startService(context, intent);
    }

    public static void logout(Context context) {
        Intent intent = new Intent(context, WearableCommunicationService.class);
        intent.putExtra("com.getqardio.android.extra.ACTION", 2);
        WearableCommunicationService.startService(context, intent);
    }

    private void onAction(int n, Object object) {
        switch (n) {
            default: {
                return;
            }
            case 0: {
                this.doSendFollowingsData();
                return;
            }
            case 1: {
                this.doSendAllData();
                return;
            }
            case 2: {
                this.doLogout();
                return;
            }
            case 3: {
                this.doSendAllData();
                return;
            }
            case 4: {
                this.doSaveWearBpMeasurements();
                return;
            }
            case 5: 
        }
        this.doSendRefuseDisconnect((String)object);
    }

    private void onBPChanged() {
        GoogleApiClient googleApiClient = this.googleApiClient.getGoogleApiClient((Context)this);
        Object[] arrobject = WearableDataHelper.getBPMeasurementDescriptions((Context)this, this.userId);
        if (!Arrays.equals(arrobject, WearableCommunicationService.getOldBPMeasurementsDescriptions(googleApiClient))) {
            WearableCommunicationService.sendBPMeasurements(googleApiClient, (BPMeasurementsDescription[])arrobject);
        }
    }

    private void onWeightChanged() {
        GoogleApiClient googleApiClient = this.googleApiClient.getGoogleApiClient((Context)this);
        WeightMeasurementsDescription weightMeasurementsDescription = WearableDataHelper.getLastWeightMeasurementDescription((Context)this, this.userId);
        if (!Utils.equals(weightMeasurementsDescription, WearableCommunicationService.getOldLastWeightMeasurement(googleApiClient))) {
            WearableCommunicationService.sendLastWeightMeasurement(googleApiClient, weightMeasurementsDescription);
        }
    }

    private void registerContentObservers() {
        Uri uri = MeasurementHelper.Weight.buildMeasurementsUri(this.userId);
        Uri uri2 = MeasurementHelper.BloodPressure.buildMeasurementsUri(this.userId);
        this.weightObserver = new WeightContentObserver();
        this.getContentResolver().registerContentObserver(uri, true, (ContentObserver)this.weightObserver);
        this.bpObserver = new PBContentObserver();
        this.getContentResolver().registerContentObserver(uri2, true, (ContentObserver)this.bpObserver);
    }

    public static void saveWearBpMeasurements(Context context) {
        Intent intent = new Intent(context, WearableCommunicationService.class);
        intent.putExtra("com.getqardio.android.extra.ACTION", 4);
        WearableCommunicationService.startService(context, intent);
    }

    public static void sendAllData(Context context) {
        Intent intent = new Intent(context, WearableCommunicationService.class);
        intent.putExtra("com.getqardio.android.extra.ACTION", 1);
        WearableCommunicationService.startService(context, intent);
    }

    private static void sendBPMeasurements(GoogleApiClient googleApiClient, BPMeasurementsDescription[] object) {
        if (googleApiClient == null || !googleApiClient.isConnected()) {
            return;
        }
        Bundle bundle = new Bundle(3);
        if (object != null) {
            if (object[0] != null) {
                bundle.putParcelable("com.getqardio.shared.wearable.key.USER_LAST_MEASUREMENT", (Parcelable)object[0]);
            }
            if (object[1] != null) {
                bundle.putParcelable("com.getqardio.shared.wearable.key.USER_WEEKLY_MEASUREMENT", (Parcelable)object[1]);
            }
            if (object[2] != null) {
                bundle.putParcelable("com.getqardio.shared.wearable.key.USER_MONTHLY_MEASUREMENT", (Parcelable)object[2]);
            }
        }
        object = PutDataRequest.create("/user/measurements/bp");
        ((PutDataRequest)object).setUrgent();
        ((PutDataRequest)object).setData(com.getqardio.shared.wearable.Utils.bundle2Bytes(bundle));
        Wearable.DataApi.putDataItem(googleApiClient, (PutDataRequest)object);
    }

    private static void sendLastWeightMeasurement(GoogleApiClient googleApiClient, WeightMeasurementsDescription object) {
        if (googleApiClient == null || !googleApiClient.isConnected()) {
            return;
        }
        if (object != null) {
            Bundle bundle = new Bundle(1);
            bundle.putParcelable("com.getqardio.shared.wearable.key.USER_LAST_MEASUREMENT", (Parcelable)object);
            object = PutDataRequest.create("/user/measurements/weight/last");
            ((PutDataRequest)object).setUrgent();
            ((PutDataRequest)object).setData(com.getqardio.shared.wearable.Utils.bundle2Bytes(bundle));
            Wearable.DataApi.putDataItem(googleApiClient, (PutDataRequest)object);
            return;
        }
        object = new Uri.Builder().scheme("wear").path("/user/measurements/weight/last").build();
        Wearable.DataApi.deleteDataItems(googleApiClient, (Uri)object);
    }

    public static void sendRefuseDisconnect(Context context, String string2) {
        Intent intent = new Intent(context, WearableCommunicationService.class);
        intent.putExtra("com.getqardio.android.extra.ACTION", 5);
        intent.putExtra("com.getqardio.android.extra.NODE_ID", string2);
        WearableCommunicationService.startService(context, intent);
    }

    public static void start(Context context) {
        WearableCommunicationService.startService(context, new Intent(context, WearableCommunicationService.class));
    }

    private static void startService(Context context, Intent intent) {
        if (CustomApplication.getApplication().hasWatch()) {
            context.startService(intent);
        }
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, WearableCommunicationService.class));
    }

    private void stopThread() {
        if (this.serviceLooper != null) {
            this.serviceLooper.quit();
        }
    }

    private void unregisterContentObservers() {
        if (this.weightObserver != null) {
            this.getContentResolver().unregisterContentObserver((ContentObserver)this.weightObserver);
        }
        if (this.bpObserver != null) {
            this.getContentResolver().unregisterContentObserver((ContentObserver)this.bpObserver);
        }
    }

    /* synthetic */ void lambda$doSendFollowingsData$1(GoogleApiClient googleApiClient) {
        this.wearableDataHelper.getFollowingData((Context)this, this.userId).toList().subscribe(WearableCommunicationService$$Lambda$2.lambdaFactory$(googleApiClient), WearableCommunicationService$$Lambda$3.lambdaFactory$());
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Long l = CustomApplication.getApplication().getCurrentUserId();
        if (l == null) {
            this.stopSelf();
            return;
        }
        this.userId = l;
        this.initThread();
        this.registerContentObservers();
        this.enqueueAction(4, null);
        DaggerIFollowComponent.builder().repositoryComponent(((MvpApplication)this.getApplicationContext()).getApplicationComponent()).build().inject(this);
    }

    public void onDestroy() {
        super.onDestroy();
        this.unregisterContentObservers();
        this.stopThread();
        this.googleApiClient.disconnect();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onStart(Intent intent, int n) {
        if (intent == null) return;
        {
            n = intent.getIntExtra("com.getqardio.android.extra.ACTION", -1);
            if (n == 5) {
                this.enqueueAction(n, intent.getStringExtra("com.getqardio.android.extra.NODE_ID"));
                return;
            } else {
                if (n == -1) return;
                {
                    this.enqueueAction(n, null);
                    return;
                }
            }
        }
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        this.onStart(intent, n2);
        return 1;
    }

    private class PBContentObserver
    extends ContentObserver {
        PBContentObserver() {
            super(new Handler(WearableCommunicationService.this.serviceLooper));
        }

        public void onChange(boolean bl) {
            this.onChange(bl, null);
        }

        public void onChange(boolean bl, Uri uri) {
            WearableCommunicationService.this.onBPChanged();
        }
    }

    private final class ServiceHandler
    extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            WearableCommunicationService.this.onAction(message.arg1, message.obj);
        }
    }

    private class WeightContentObserver
    extends ContentObserver {
        WeightContentObserver() {
            super(new Handler(WearableCommunicationService.this.serviceLooper));
        }

        public void onChange(boolean bl) {
            this.onChange(bl, null);
        }

        public void onChange(boolean bl, Uri uri) {
            WearableCommunicationService.this.onWeightChanged();
        }
    }

}

