/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Handler
 *  android.preference.PreferenceManager
 *  android.text.TextUtils
 */
package com.getqardio.android;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.datamodel.User;
import com.getqardio.android.device_related_services.common.SDKInitiatorImpl;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.service.WearableCommunicationService;
import com.getqardio.android.shealth.OnSHealthConnectionErrorListener;
import com.getqardio.android.shealth.ShealthPermissionKeys;
import com.getqardio.android.shealth.ShealthUtils;
import com.getqardio.android.ui.activity.SignActivity;
import com.getqardio.android.utils.CipherManager;
import com.getqardio.android.utils.Constants;
import com.getqardio.android.utils.GooglePlayServicesUtils;
import com.getqardio.android.utils.RateAppManager;
import com.getqardio.android.utils.logger.CrashlyticsTree;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.qardio.ble.bpcollector.mobiledevice.BLEStatus;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthDevice;
import com.samsung.android.sdk.healthdata.HealthDeviceManager;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import com.samsung.android.sdk.shealth.Shealth;
import com.segment.analytics.Analytics;
import com.segment.analytics.android.integrations.mixpanel.MixpanelIntegration;
import com.segment.analytics.integrations.Integration;
import io.fabric.sdk.android.Fabric;
import java.util.HashSet;
import java.util.Set;
import timber.log.Timber;

public class CustomApplication
extends MultiDexApplication {
    private static CustomApplication application;
    private Analytics analytics;
    private final HealthDataObserver bpHealthDataObserver = new HealthDataObserver(null){

        @Override
        public void onChange(String string2) {
            if ("com.samsung.health.blood_pressure".equals(string2) && CustomApplication.this.getCurrentUserId() != null) {
                CustomApplication.this.setSHealthBPSyncPending(true);
            }
        }
    };
    private int bpHistoryTab = -1;
    private int bpTab = 1;
    private String currentUserEmail;
    private Long currentUserId;
    private String currentUserToken;
    private boolean googlePlayServiceAvailable;
    private boolean hasWatch = false;
    private HealthDataStore healthDataStore;
    private boolean isSHealthBPSyncPending;
    private boolean isSHealthWeightSyncPending;
    private MixpanelAPI mixpanelApi;
    private SharedPreferences preferences;
    private int qbHistoryTab = 2131821427;
    private int qbTab = 1;
    private RateAppManager rateAppManager;
    private boolean supportSHealth;
    private Tracker tracker;
    private String trackingId;
    private final HealthDataObserver weightHealthDataObserver = new HealthDataObserver(null){

        @Override
        public void onChange(String string2) {
            if ("com.samsung.health.weight".equals(string2) && CustomApplication.this.getCurrentUserId() != null) {
                CustomApplication.this.setSHealthWeightSyncPending(true);
            }
        }
    };

    public static CustomApplication getApplication() {
        return application;
    }

    private void initHealthDataService(Context context) {
        HealthDataService healthDataService = new HealthDataService();
        try {
            healthDataService.initialize(context);
            return;
        }
        catch (Exception exception) {
            Timber.d("This device does not support Samsung Digital Health", new Object[0]);
            return;
        }
    }

    private void initSdk() {
        SDKInitiatorImpl.newInstance().init((Context)this);
    }

    private void initShealth(Context context) {
        Shealth shealth = new Shealth();
        this.supportSHealth = true;
        try {
            shealth.initialize(context);
            return;
        }
        catch (Exception exception) {
            Timber.d("Cannot initialize Shealth sdk.", new Object[0]);
            return;
        }
    }

    private static void onReloginFailed() {
        CustomApplication.getApplication().setCurrentUserToken(null);
        CustomApplication.getApplication().setCurrentUserTrackingId(null);
        CustomApplication customApplication = CustomApplication.getApplication();
        Intent intent = SignActivity.getStartIntent((Context)customApplication, false);
        intent.setFlags(268468224);
        customApplication.startActivity(intent);
    }

    private void registerAnalytics() {
        Timber.v("Analytics write key: %s", Constants.ANALYTICS_WRITE_KEY);
        this.analytics = new Analytics.Builder((Context)this, Constants.ANALYTICS_WRITE_KEY).use(MixpanelIntegration.FACTORY).build();
        Analytics.setSingletonInstance(this.analytics);
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance((Context)this).registerReceiver(new ReloginFailedReceiver(), new IntentFilter("com.getqardio.android.Notifications.RELOGIN_FAILED"));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void registerDataObserver(HealthPermissionManager.PermissionKey permissionKey, String string2, HealthDataObserver healthDataObserver) {
        if (!this.supportSHealth) return;
        Timber.i("Register SHealth data observer", new Object[0]);
        try {
            if (!ShealthUtils.isPermissionAcquired(this.healthDataStore, permissionKey)) return;
            HealthDataObserver.addObserver(this.healthDataStore, string2, healthDataObserver);
            return;
        }
        catch (IllegalStateException illegalStateException) {
            Timber.e(illegalStateException, "Cannot register data observer", new Object[0]);
            return;
        }
        catch (SecurityException securityException) {
            Timber.e(securityException, "Permission READ is not acquired", new Object[0]);
            return;
        }
    }

    private void registerDevice() {
        Timber.i("Registering device in SHealth SDK", new Object[0]);
        HealthDevice healthDevice = new HealthDevice.Builder().setModel("Qardio").setManufacturer("Qardio").setDeviceSeed("2ABF2888ARM").setGroup(360001).build();
        HealthDeviceManager healthDeviceManager = new HealthDeviceManager(this.healthDataStore);
        try {
            healthDeviceManager.registerDevice(healthDevice);
            return;
        }
        catch (Exception exception) {
            Timber.e(exception, "Unable to register device", new Object[0]);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void unregisterDataObserver(HealthDataObserver healthDataObserver) {
        if (!this.supportSHealth) return;
        Timber.i("Unregister SHealth data observer", new Object[0]);
        if (healthDataObserver == null) return;
        try {
            if (this.healthDataStore == null) return;
            HealthDataObserver.removeObserver(this.healthDataStore, healthDataObserver);
            return;
        }
        catch (IllegalStateException illegalStateException) {
            Timber.e(illegalStateException, "Cannot unregister SHealth data observer", new Object[0]);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateUserEmailAndId() {
        synchronized (this) {
            if (this.currentUserToken == null) {
                this.currentUserId = null;
                this.currentUserEmail = null;
            } else {
                User user = AuthHelper.getUserEmailAndIdByToken(this.getApplicationContext(), this.currentUserToken);
                if (user == null) {
                    this.currentUserId = null;
                    this.currentUserEmail = null;
                    this.currentUserToken = null;
                } else {
                    this.currentUserId = user._id;
                    this.currentUserEmail = user.email;
                    if (this.currentUserId != null) {
                        SharedPreferences.Editor editor = this.preferences.edit();
                        editor.putLong("pref_last_logged_user_id", this.currentUserId.longValue());
                        editor.commit();
                        AuthHelper.saveOrUpdateUserInRealm(this.currentUserId, user.email, this.currentUserToken, user.tokenExpired, user.trackingId, user.password);
                    }
                }
            }
            return;
        }
    }

    public void connectToShealth() {
        Timber.i("Attempt to connect to SHealth", new Object[0]);
        this.healthDataStore = new HealthDataStore((Context)this, new HealthDataStore.ConnectionListener(){

            @Override
            public void onConnected() {
                Settings settings;
                Timber.i("Connected to SHealth", new Object[0]);
                CustomApplication.this.registerDevice();
                if (CustomApplication.this.getCurrentUserId() != null && (settings = DataHelper.SettingsHelper.getSettings((Context)CustomApplication.this, CustomApplication.this.getCurrentUserId())) != null) {
                    if (settings.allowBpImportSHealth.booleanValue()) {
                        CustomApplication.this.registerBpDataObserver();
                    }
                    if (settings.allowWeightImportSHealth.booleanValue()) {
                        CustomApplication.this.registerWeightDataObserver();
                    }
                }
            }

            @Override
            public void onConnectionFailed(HealthConnectionErrorResult healthConnectionErrorResult) {
                CustomApplication.this.supportSHealth = false;
                Timber.i("Connection to SHealth failed with error code %d", healthConnectionErrorResult.getErrorCode());
            }

            @Override
            public void onDisconnected() {
                Timber.i("Disconnected from SHealth", new Object[0]);
                CustomApplication.this.unregisterBpDataObserver();
                CustomApplication.this.unregisterWeightDataObserver();
            }
        });
        this.healthDataStore.connectService();
    }

    public void disconnectSHealth() {
        Timber.i("Disconnecting from SHealth", new Object[0]);
        if (this.healthDataStore != null) {
            this.healthDataStore.disconnectService();
        }
    }

    public void flushMixpanel() {
        this.getMixpanelApi().flush();
    }

    public int getBpHistoryTab() {
        return this.bpHistoryTab;
    }

    public int getBpTab() {
        return this.bpTab;
    }

    public String getCurrentUserEmail() {
        synchronized (this) {
            String string2 = this.currentUserEmail;
            return string2;
        }
    }

    public Long getCurrentUserId() {
        synchronized (this) {
            Long l = this.currentUserId;
            return l;
        }
    }

    public String getCurrentUserToken() {
        synchronized (this) {
            String string2 = this.currentUserToken;
            return string2;
        }
    }

    public String getCurrentUserTrackingId() {
        synchronized (this) {
            String string2 = this.trackingId;
            return string2;
        }
    }

    public HealthDataStore getHealthDataStore() {
        return this.healthDataStore;
    }

    public Long getLastUserId() {
        if (this.currentUserId != null) {
            return this.currentUserId;
        }
        long l = this.preferences.getLong("pref_last_logged_user_id", -1L);
        if (l != -1L) {
            return l;
        }
        return null;
    }

    public MixpanelAPI getMixpanelApi() {
        return this.mixpanelApi;
    }

    public int getQBTab() {
        return this.qbTab;
    }

    public int getQbHistoryTab() {
        return this.qbHistoryTab;
    }

    public RateAppManager getRateAppManager(Context context) {
        if (this.rateAppManager == null) {
            this.rateAppManager = new RateAppManager(context);
        }
        return this.rateAppManager;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Tracker getTracker() {
        synchronized (this) {
            GoogleAnalytics googleAnalytics;
            block5: {
                if (this.tracker != null) return this.tracker;
                googleAnalytics = GoogleAnalytics.getInstance((Context)this);
                googleAnalytics.enableAutoActivityReports(this);
                googleAnalytics.enableAdvertisingIdCollection(true);
                if (!"release".equals("internal")) break block5;
                this.tracker = googleAnalytics.newTracker(2131165189);
                do {
                    return this.tracker;
                    break;
                } while (true);
            }
            this.tracker = googleAnalytics.newTracker(2131165184);
            return this.tracker;
        }
    }

    public boolean hasWatch() {
        return this.hasWatch;
    }

    public boolean isGooglePlayServiceAvailableAndGoogleBuild() {
        return this.googlePlayServiceAvailable && "google".equalsIgnoreCase("google");
    }

    public boolean isGooglePlayServiceAvailableOrNonGoogleBuild() {
        return this.googlePlayServiceAvailable || !"google".equalsIgnoreCase("google");
    }

    public boolean isSHealthBPSyncPending() {
        return this.isSHealthBPSyncPending;
    }

    public boolean isSHealthWeightSyncPending() {
        return this.isSHealthWeightSyncPending;
    }

    public boolean isSupportSHealth() {
        return this.supportSHealth;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isUserLoggedIn() {
        synchronized (this) {
            boolean bl = TextUtils.isEmpty((CharSequence)this.currentUserToken);
            if (bl) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCreate() {
        Object var2_1 = null;
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Fabric.with((Context)this, new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(false).build()).build());
        this.registerAnalytics();
        application = this;
        this.preferences = PreferenceManager.getDefaultSharedPreferences((Context)this.getApplicationContext());
        this.initShealth((Context)this);
        this.initHealthDataService((Context)this);
        this.googlePlayServiceAvailable = GooglePlayServicesUtils.isPlayServicesAvailable((Context)this);
        this.connectToShealth();
        String string2 = this.preferences.getString("pref_token", null);
        string2 = string2 == null ? null : CipherManager.decrypt(this.getApplicationContext(), string2);
        this.currentUserToken = string2;
        string2 = this.preferences.getString("pref_tracking", null);
        string2 = string2 == null ? var2_1 : CipherManager.decrypt(this.getApplicationContext(), string2);
        this.trackingId = string2;
        this.updateUserEmailAndId();
        BLEStatus.getInstance((Context)this);
        this.hasWatch = this.preferences.getBoolean("has_watch", this.hasWatch);
        this.registerBroadcastReceivers();
        this.initSdk();
        Timber.plant(new CrashlyticsTree());
    }

    public void registerBpDataObserver() {
        this.registerDataObserver(ShealthPermissionKeys.READ.getBpPermissionKey(), "com.samsung.health.blood_pressure", this.bpHealthDataObserver);
    }

    public void registerWeightDataObserver() {
        this.registerDataObserver(ShealthPermissionKeys.READ.getWeightPermissionKey(), "com.samsung.health.weight", this.weightHealthDataObserver);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void requestSHealthPermissions(ShealthPermissionKeys var1_1, HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> var2_5, OnSHealthConnectionErrorListener var3_6) {
        Timber.i("Request SHealth permissions", new Object[0]);
        var4_7 = new HealthPermissionManager(this.healthDataStore);
        var5_8 = new HashSet<HealthPermissionManager.PermissionKey>();
        var5_8.add(var1_1.getBpPermissionKey());
        var5_8.add(var1_1.getWeightPermissionKey());
        try {
            var4_7.requestPermissions(var5_8).setResultListener(var2_5);
            return;
        }
        catch (IllegalStateException var1_2) {}
        ** GOTO lbl-1000
        catch (IllegalArgumentException var1_4) {}
lbl-1000:
        // 2 sources
        {
            Timber.e((Throwable)var1_3, "Cannot connect to the data store", new Object[0]);
            if (var3_6 == null) return;
            var3_6.onConnectionError();
            return;
        }
    }

    public void setBpHistoryTab(int n) {
        this.bpHistoryTab = n;
    }

    public void setBpTab(int n) {
        this.bpTab = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCurrentUserToken(String string2) {
        synchronized (this) {
            SharedPreferences.Editor editor = this.preferences.edit();
            if (string2 == null) {
                editor.remove("pref_token");
                this.bpTab = 1;
                this.qbTab = 1;
                this.bpHistoryTab = -1;
                this.qbHistoryTab = 2131821427;
            } else {
                editor.putString("pref_token", CipherManager.encrypt(this.getApplicationContext(), string2));
            }
            editor.commit();
            this.currentUserToken = string2;
            this.updateUserEmailAndId();
            if (string2 == null) {
                WearableCommunicationService.logout((Context)this);
            } else {
                WearableCommunicationService.login((Context)this);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setCurrentUserTrackingId(String string2) {
        synchronized (this) {
            SharedPreferences.Editor editor = this.preferences.edit();
            if (string2 == null) {
                editor.remove("pref_tracking");
            } else {
                editor.putString("pref_tracking", CipherManager.encrypt(this.getApplicationContext(), string2));
            }
            editor.putString("pref_tracking", CipherManager.encrypt(this.getApplicationContext(), string2));
            editor.commit();
            if (string2 != null) {
                this.trackingId = string2;
            }
            return;
        }
    }

    public void setHasWatch(boolean bl) {
        synchronized (this) {
            if (this.hasWatch != bl) {
                this.hasWatch = bl;
                SharedPreferences.Editor editor = this.preferences.edit();
                editor.putBoolean("has_watch", this.hasWatch);
                editor.commit();
            }
            return;
        }
    }

    public void setMixpanelApi(MixpanelAPI mixpanelAPI) {
        this.mixpanelApi = mixpanelAPI;
    }

    public void setQBTab(int n) {
        this.qbTab = n;
    }

    public void setQbHistoryTab(int n) {
        this.qbHistoryTab = n;
    }

    public void setSHealthBPSyncPending(boolean bl) {
        this.isSHealthBPSyncPending = bl;
    }

    public void setSHealthWeightSyncPending(boolean bl) {
        this.isSHealthWeightSyncPending = bl;
    }

    public void unregisterBpDataObserver() {
        this.unregisterDataObserver(this.bpHealthDataObserver);
    }

    public void unregisterWeightDataObserver() {
        this.unregisterDataObserver(this.weightHealthDataObserver);
    }

    private static class ReloginFailedReceiver
    extends BroadcastReceiver {
        private ReloginFailedReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            CustomApplication.onReloginFailed();
        }
    }

}

