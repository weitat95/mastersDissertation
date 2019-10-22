/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.Loader
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.LinearLayout
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.FirmwareDescription;
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.device_related_services.fit.GoogleFitDataHelper;
import com.getqardio.android.device_related_services.fit.GoogleFitUtils;
import com.getqardio.android.device_related_services.map.LocationServiceSettingsCheckerImpl;
import com.getqardio.android.googlefit.ActivityGoalsJobService;
import com.getqardio.android.googlefit.ActivityGoalsJobServiceCompat;
import com.getqardio.android.googlefit.GoogleFitApi;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.FirmwareUpdateHelper;
import com.getqardio.android.shealth.OnSHealthConnectionErrorListener;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.shealth.ShealthPermissionKeys;
import com.getqardio.android.ui.activity.ImageSlideshowActivity;
import com.getqardio.android.ui.activity.MainActivity;
import com.getqardio.android.ui.activity.QBOnboardingActivity;
import com.getqardio.android.ui.activity.QBStepOnActivity;
import com.getqardio.android.ui.activity.QBWifiConfigurationActivity;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$10;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$11;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$12;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$13;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$14;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$15;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$16;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$17;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$18;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$19;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$20;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$21;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$22;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$23;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$24;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$25;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$26;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$7;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$8;
import com.getqardio.android.ui.fragment.SettingsFragment$$Lambda$9;
import com.getqardio.android.ui.fragment.SettingsFragment$1$$Lambda$1;
import com.getqardio.android.ui.fragment.SettingsFragment$7$$Lambda$1;
import com.getqardio.android.ui.fragment.SettingsFragment$7$$Lambda$2;
import com.getqardio.android.ui.fragment.SettingsFragment$9$$Lambda$1;
import com.getqardio.android.ui.fragment.SettingsFragment$9$$Lambda$2;
import com.getqardio.android.ui.fragment.SettingsFragment$9$$Lambda$3;
import com.getqardio.android.ui.widget.CustomSeekBar;
import com.getqardio.android.ui.widget.CustomSwitch;
import com.getqardio.android.utils.RandomImageGenerator;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.ActivityTrackerAnalytics;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.getqardio.android.utils.analytics.GeneralAnalyticsTracker;
import com.getqardio.android.utils.permission.PermissionUtil;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import timber.log.Timber;

public class SettingsFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor> {
    private TextView activityTrackerGoal;
    private RelativeLayout activityTrackerGoalSettings;
    private Button activityTrackerSetGoalMinusButton;
    private Button activityTrackerSetGoalPlusButton;
    private Map<ViewGroup, ValueAnimator> animators = new HashMap<ViewGroup, ValueAnimator>();
    private boolean authInProgress;
    private LocalBroadcastManager broadcastManager;
    private int currentGoal;
    private CompositeDisposable disposables = new CompositeDisposable();
    private CustomSwitch enableActivityTracker;
    private CustomSwitch enableImportantNotifications;
    private CustomSwitch enablePlaces;
    private boolean exportBpToSHealthChecked = false;
    private boolean exportWeightToSHealthChecked = false;
    private View firmwareUpdateContainer;
    private TextView firmwareUpdateCount;
    private GoogleApiClient googleApiClient;
    private boolean importBpFromSHealthChecked = false;
    private CustomSwitch importFromSHealth;
    private boolean importWeightFromSHealthChecked = false;
    private CustomSwitch integrateWithGoogleFit;
    private boolean isGooglePlayServicesAvailableAndGoogleBuild;
    private SharedPreferences mSettingsPref;
    private CustomSeekBar measurementsCount;
    private View modesContainer;
    private final BroadcastReceiver networkReceiver = new BroadcastReceiver(){

        /* synthetic */ void lambda$onReceive$0(String string2, String string3, String string4, View view) {
            block3: {
                block4: {
                    block2: {
                        view = SettingsFragment.this.getActivity();
                        if (view == null) break block2;
                        if (!SettingsFragment.this.checkPermissions()) break block3;
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(SettingsFragment.this.getActivity(), "android.permission.ACCESS_COARSE_LOCATION") && SettingsFragment.this.mSettingsPref.getBoolean("location_permission_checked", false)) break block4;
                        PermissionUtil.BlePermissions.checkCoarseLocationPermission(SettingsFragment.this.getActivity());
                    }
                    return;
                }
                SettingsFragment.this.showBlockedPermissionExplanation();
                return;
            }
            string2 = QBStepOnActivity.createStartIntentForFirmwareUpdate((Context)view, string2, string3, string4);
            SettingsFragment.this.startActivityForResult((Intent)string2, 4);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context object, Intent object2) {
            object = object2.getAction();
            int n = -1;
            switch (((String)object).hashCode()) {
                case 1688339086: {
                    if (!((String)object).equals("com.getqardio.android.action.NEW_BASE_FIRMWARE")) break;
                    n = 0;
                    break;
                }
            }
            switch (n) {
                default: {
                    return;
                }
                case 0: 
            }
            object = object2.getStringExtra("com.getqardio.android.extra.FIRMWARE_IP");
            String string2 = object2.getStringExtra("com.getqardio.android.extra.FIRMWARE_VERSION");
            object2 = object2.getStringExtra("com.getqardio.android.extra.FIRMWARE_DESCRIPTION");
            SettingsFragment.this.firmwareUpdateCount.setText((CharSequence)"!");
            SettingsFragment.this.firmwareUpdateCount.setVisibility(0);
            SettingsFragment.this.firmwareUpdateContainer.setEnabled(true);
            SettingsFragment.this.firmwareUpdateContainer.setOnClickListener(SettingsFragment$1$$Lambda$1.lambdaFactory$(this, (String)object, string2, (String)object2));
        }
    };
    private CustomSeekBar pauseSize;
    private ViewGroup pauseSizeLayout;
    private int pauseSizeLayoutHeight;
    private ViewGroup photoAlbumsLayout;
    private int photoAlbumsLayoutHeight;
    private View relaunchOnboardingContainer;
    private HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> requestReadPermissionsResultListener;
    private HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> requestWritePermissionsResultListener = new HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult>(){

        @Override
        public void onResult(HealthPermissionManager.PermissionResult object) {
            object = ((HealthPermissionManager.PermissionResult)object).getResultMap();
            boolean bl = false;
            for (HealthPermissionManager.PermissionKey permissionKey : object.keySet()) {
                boolean bl2 = (Boolean)object.get(permissionKey);
                if (!permissionKey.getPermissionType().equals((Object)HealthPermissionManager.PermissionType.WRITE)) continue;
                bl |= bl2;
                SettingsFragment.this.processWritePermissions(permissionKey, bl2);
            }
            SettingsFragment.this.saveSettings();
            SettingsFragment.this.saveSettings = true;
            SettingsFragment.this.storeInSHealth.setChecked(SettingsFragment.this.storeInSHealth.isChecked() & bl);
        }
    };
    private Button resetPairing;
    private TextView resetQardioBaseView;
    private boolean saveSettings = true;
    private boolean settingSynchronization = false;
    private Settings settings;
    private boolean settingsChangedByUser = false;
    private View showImageButton;
    private CustomSwitch showPhoto;
    private TextView showPhotoCollection;
    private TextView showPhotosFromDevice;
    private CustomSwitch storeInSHealth;
    private View wifiConfirmationContainer;

    public SettingsFragment() {
        this.requestReadPermissionsResultListener = new HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult>(){

            @Override
            public void onResult(HealthPermissionManager.PermissionResult object) {
                object = ((HealthPermissionManager.PermissionResult)object).getResultMap();
                boolean bl = false;
                for (HealthPermissionManager.PermissionKey permissionKey : object.keySet()) {
                    boolean bl2 = (Boolean)object.get(permissionKey);
                    if (!permissionKey.getPermissionType().equals((Object)HealthPermissionManager.PermissionType.READ)) continue;
                    bl |= bl2;
                    SettingsFragment.this.processReadPermissions(permissionKey, bl2);
                }
                SettingsFragment.this.saveSettings();
                SettingsFragment.this.saveSettings = true;
                SettingsFragment.this.importFromSHealth.setChecked(SettingsFragment.this.importFromSHealth.isChecked() & bl);
            }
        };
    }

    static /* synthetic */ int access$1108(SettingsFragment settingsFragment) {
        int n = settingsFragment.currentGoal;
        settingsFragment.currentGoal = n + 1;
        return n;
    }

    static /* synthetic */ int access$1110(SettingsFragment settingsFragment) {
        int n = settingsFragment.currentGoal;
        settingsFragment.currentGoal = n - 1;
        return n;
    }

    private void applyData(Settings settings) {
        this.settingSynchronization = true;
        this.showPhoto.setChecked(settings.showPhoto, false);
        this.showPhotoCollection.setSelected(settings.useFlickr());
        this.showPhotosFromDevice.setSelected(settings.usePhotosFromDevice());
        this.setMeasurementsCount(settings.measurementsNumber);
        this.setPauseSize(settings.pauseSize);
        if (this.isGooglePlayServicesAvailableAndGoogleBuild) {
            this.enablePlaces.setChecked(settings.allowLocation, false);
            this.integrateWithGoogleFit.setChecked(settings.allowIntegrationGoogleFit, false);
            this.integrateWithGoogleFit.setOnCheckedChangeListener(SettingsFragment$$Lambda$16.lambdaFactory$(this));
        }
        this.enableImportantNotifications.setChecked(this.isImportantNotificationsEnabled(), false);
        this.enableImportantNotifications.setOnCheckedChangeListener(SettingsFragment$$Lambda$17.lambdaFactory$(this));
        this.setupActivityTrackerSwitch();
        this.importBpFromSHealthChecked = settings.allowBpImportSHealth;
        this.exportBpToSHealthChecked = settings.allowBpExportSHealth;
        this.importWeightFromSHealthChecked = settings.allowWeightImportSHealth;
        this.exportWeightToSHealthChecked = settings.allowWeightExportSHealth;
        this.importFromSHealth.setChecked(settings.allowBpImportSHealth | settings.allowWeightImportSHealth, false);
        this.importFromSHealth.setOnCheckedChangeListener(SettingsFragment$$Lambda$18.lambdaFactory$(this));
        this.storeInSHealth.setChecked(settings.allowBpExportSHealth | settings.allowWeightExportSHealth, false);
        this.storeInSHealth.setOnCheckedChangeListener(SettingsFragment$$Lambda$19.lambdaFactory$(this));
        this.settingSynchronization = false;
    }

    private void changeGoogleFitIntegrationState(boolean bl) {
        if (this.integrateWithGoogleFit.isChecked() != bl) {
            this.integrateWithGoogleFit.setChecked(bl, true);
        }
    }

    private boolean checkPermissions() {
        return !PermissionUtil.BlePermissions.hasCourseLocationPermission(this.getActivity()) && !PermissionUtil.BlePermissions.hasFineLocationPermission(this.getActivity());
    }

    private void connectToDataStore(ShealthPermissionKeys shealthPermissionKeys, HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult> resultListener) {
        CustomApplication.getApplication().requestSHealthPermissions(shealthPermissionKeys, resultListener, SettingsFragment$$Lambda$22.lambdaFactory$(this));
    }

    private void connectToGoogleFit(final GoogleApiClient googleApiClient) {
        googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks(){

            static /* synthetic */ void lambda$onConnected$0(Integer n) throws Exception {
                Timber.d("done", new Object[0]);
            }

            @Override
            public void onConnected(Bundle bundle) {
                Timber.i("Connected to Google Fit!", new Object[0]);
                bundle = SettingsFragment.this.getActivity();
                Long l = CustomApplication.getApplication().getCurrentUserId();
                if (bundle != null && l != null) {
                    SettingsFragment.this.trackGoogleFitOptionChanged(true);
                    SettingsFragment.this.saveSettings();
                    GoogleFitApiImpl googleFitApiImpl = new GoogleFitApiImpl(googleApiClient);
                    SettingsFragment.this.disposables.add(googleFitApiImpl.saveBloodPressureMeasurements((Context)SettingsFragment.this.getActivity(), SettingsFragment.this.getArguments().getLong("com.getqardio.android.argument.USER_ID")).subscribe(SettingsFragment$7$$Lambda$1.lambdaFactory$(), SettingsFragment$7$$Lambda$2.lambdaFactory$()));
                    GoogleFitDataHelper.Weight.requestReadFromGoogleFit((Context)bundle, l);
                    GoogleFitDataHelper.Weight.requestSaveToGoogleFit((Context)bundle, l);
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onConnectionSuspended(int n) {
                if (n == 2) {
                    Timber.i("Connection lost.  Cause: Network Lost.", new Object[0]);
                } else if (n == 1) {
                    Timber.i("Connection lost.  Reason: Service Disconnected", new Object[0]);
                }
                SettingsFragment.this.changeGoogleFitIntegrationState(false);
            }
        });
        googleApiClient.registerConnectionFailedListener(SettingsFragment$$Lambda$23.lambdaFactory$(this));
        googleApiClient.reconnect();
    }

    private int getMeasurementsCount() {
        return this.measurementsCount.getCustomProgress();
    }

    private int getPauseSize() {
        return this.pauseSize.getCustomProgress();
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private void handleGoogleFitAuthResult(int n, boolean bl) {
        this.authInProgress = false;
        if (n == -1) {
            this.saveSettings = true;
            if (!this.googleApiClient.isConnecting() && !this.googleApiClient.isConnected()) {
                this.googleApiClient.connect();
            }
            if (bl) {
                PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putBoolean("google_fit_activity_tracker", true).apply();
                return;
            }
            this.saveSettings();
            return;
        }
        if (bl) {
            PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putBoolean("google_fit_activity_tracker", false).apply();
            this.setupActivityTrackerSwitch();
            return;
        }
        this.changeGoogleFitIntegrationState(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleOnboardingResult(int n, Intent intent) {
        Activity activity;
        boolean bl = false;
        if (n != -1 || (activity = this.getActivity()) == null) {
            return;
        }
        MainActivity mainActivity = (MainActivity)activity;
        boolean bl2 = bl;
        if (intent != null) {
            bl2 = bl;
            if (intent.getExtras() != null) {
                bl2 = bl;
                if (intent.getBooleanExtra("com.getqardio.android.argument.LAUNCHED_FROM_ONBOARDING", false)) {
                    bl2 = true;
                }
            }
        }
        mainActivity.setQbOnboardedStatus(bl2);
        ((MainActivity)activity).onNavigationDrawerItemSelected(2131821477);
    }

    private void hideLayout(boolean bl, ViewGroup viewGroup, int n) {
        if (!bl) {
            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
            layoutParams.height = 0;
            viewGroup.setAlpha(0.0f);
            viewGroup.setLayoutParams(layoutParams);
            return;
        }
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        ValueAnimator valueAnimator = this.animators.remove((Object)viewGroup);
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        float f = 1.0f * (float)viewGroup.getHeight() / (float)n;
        valueAnimator = ValueAnimator.ofFloat((float[])new float[]{f, 0.0f});
        valueAnimator.addUpdateListener(SettingsFragment$$Lambda$20.lambdaFactory$(layoutParams, n, viewGroup));
        valueAnimator.setDuration((long)((int)(500.0f * f)));
        valueAnimator.start();
        this.animators.put(viewGroup, valueAnimator);
    }

    private void hidePauseSizeLayout(boolean bl) {
        this.hideLayout(bl, this.pauseSizeLayout, this.pauseSizeLayoutHeight);
    }

    private void hidePhotoAlbumsContainer(boolean bl) {
        this.hideLayout(bl, this.photoAlbumsLayout, this.photoAlbumsLayoutHeight);
    }

    private boolean isImportantNotificationsEnabled() {
        boolean bl = true;
        Activity activity = this.getActivity();
        if (activity != null) {
            bl = PreferenceManager.getDefaultSharedPreferences((Context)activity).getBoolean("com.getqardio.android.extra.ENABLE_IMPORTANT_NOTIFICATIONS", true);
        }
        return bl;
    }

    static /* synthetic */ void lambda$hideLayout$18(ViewGroup.LayoutParams layoutParams, int n, ViewGroup viewGroup, ValueAnimator valueAnimator) {
        float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        layoutParams.height = (int)((float)n * f);
        viewGroup.setLayoutParams(layoutParams);
        viewGroup.setAlpha(f);
    }

    static /* synthetic */ void lambda$showLayout$19(ViewGroup.LayoutParams layoutParams, int n, ViewGroup viewGroup, ValueAnimator valueAnimator) {
        float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        layoutParams.height = (int)((float)n * f);
        viewGroup.setLayoutParams(layoutParams);
        viewGroup.setAlpha(f);
    }

    public static SettingsFragment newInstance(long l) {
        Bundle bundle = new Bundle(1);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setArguments(bundle);
        return settingsFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void processReadPermissions(HealthPermissionManager.PermissionKey object, boolean bl) {
        object = ((HealthPermissionManager.PermissionKey)object).getDataType();
        long l = this.getUserId();
        int n = -1;
        switch (((String)object).hashCode()) {
            case 1402297883: {
                if (((String)object).equals("com.samsung.health.blood_pressure")) {
                    n = 0;
                }
            }
            default: {
                break;
            }
            case 380618409: {
                if (!((String)object).equals("com.samsung.health.weight")) break;
                n = 1;
            }
        }
        switch (n) {
            default: {
                return;
            }
            case 0: {
                this.importBpFromSHealthChecked = bl;
                if (!bl) {
                    CustomApplication.getApplication().unregisterBpDataObserver();
                    return;
                }
                if (l != -1L) {
                    ShealthDataHelper.BpMeasurements.requestReadMeasurements((Context)CustomApplication.getApplication(), l);
                }
                CustomApplication.getApplication().registerBpDataObserver();
                return;
            }
            case 1: 
        }
        this.importWeightFromSHealthChecked = bl;
        if (!bl) {
            CustomApplication.getApplication().unregisterWeightDataObserver();
            return;
        }
        if (l != -1L) {
            ShealthDataHelper.WeightMeasurements.requestReadWeightMeasurements((Context)CustomApplication.getApplication(), l);
        }
        CustomApplication.getApplication().registerWeightDataObserver();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void processWritePermissions(HealthPermissionManager.PermissionKey object, boolean bl) {
        object = ((HealthPermissionManager.PermissionKey)object).getDataType();
        long l = this.getUserId();
        if (l == -1L) return;
        {
            int n = -1;
            switch (((String)object).hashCode()) {
                case 1402297883: {
                    if (((String)object).equals("com.samsung.health.blood_pressure")) {
                        n = 0;
                    }
                }
                default: {
                    break;
                }
                case 380618409: {
                    if (!((String)object).equals("com.samsung.health.weight")) break;
                    n = 1;
                }
            }
            switch (n) {
                case 0: {
                    this.exportBpToSHealthChecked = bl;
                    if (!bl) return;
                    {
                        ShealthDataHelper.BpMeasurements.requestSaveMeasurements((Context)CustomApplication.getApplication(), l);
                        return;
                    }
                }
                default: {
                    return;
                }
                case 1: {
                    this.exportWeightToSHealthChecked = bl;
                    if (!bl) return;
                    ShealthDataHelper.WeightMeasurements.requestSaveWeightMeasurements((Context)CustomApplication.getApplication(), l);
                    return;
                }
            }
        }
    }

    private void saveEnableNotificatiosSettingLocally() {
        boolean bl = this.enableImportantNotifications.isChecked();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit();
        editor.putBoolean("com.getqardio.android.extra.ENABLE_IMPORTANT_NOTIFICATIONS", bl);
        editor.apply();
    }

    private void saveSettings() {
        if (this.settings != null && this.settingsChanged()) {
            Settings settings = new Settings();
            settings.userId = this.settings.userId;
            settings.syncStatus = this.settings.syncStatus;
            settings.showPhoto = this.showPhoto.isChecked();
            settings.cleanAlbums();
            if (this.showPhotoCollection.isSelected()) {
                settings.setUseFlickr();
            }
            if (this.showPhotosFromDevice.isSelected()) {
                settings.setUsePhotosFromDevice();
            }
            settings.measurementsNumber = this.getMeasurementsCount();
            settings.pauseSize = this.getPauseSize();
            settings.allowLocation = this.enablePlaces.isChecked();
            settings.allowBpImportSHealth = this.importBpFromSHealthChecked;
            settings.allowBpExportSHealth = this.exportBpToSHealthChecked;
            settings.allowWeightImportSHealth = this.importWeightFromSHealthChecked;
            settings.allowWeightExportSHealth = this.exportWeightToSHealthChecked;
            settings.allowMixpanelNotifications = this.enableImportantNotifications.isChecked();
            settings.allowIntegrationGoogleFit = this.integrateWithGoogleFit.isChecked();
            DataHelper.SettingsHelper.saveSettings((Context)CustomApplication.getApplication(), settings, true);
        }
    }

    private void setMeasurementsCount(int n) {
        this.measurementsCount.setCustomProgress(n);
    }

    private void setNoFirmwareUpdateHandling() {
        this.firmwareUpdateContainer.setOnClickListener(SettingsFragment$$Lambda$4.lambdaFactory$(this));
    }

    private void setPauseSize(int n) {
        this.pauseSize.setCustomProgress(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean settingsChanged() {
        boolean bl = true;
        boolean bl2 = false;
        if (this.settings == null) return bl2;
        boolean bl3 = this.settings.showPhoto.booleanValue() != this.showPhoto.isChecked();
        boolean bl4 = this.settings.useFlickr() != this.showPhotoCollection.isSelected();
        boolean bl5 = this.settings.usePhotosFromDevice() != this.showPhotosFromDevice.isSelected();
        boolean bl6 = this.settings.measurementsNumber.intValue() != this.getMeasurementsCount();
        boolean bl7 = this.settings.pauseSize.intValue() != this.getPauseSize();
        boolean bl8 = this.settings.allowLocation.booleanValue() != this.enablePlaces.isChecked();
        boolean bl9 = this.settings.allowBpImportSHealth != this.importBpFromSHealthChecked;
        boolean bl10 = this.settings.allowWeightImportSHealth != this.importWeightFromSHealthChecked;
        boolean bl11 = this.settings.allowBpExportSHealth != this.exportBpToSHealthChecked;
        boolean bl12 = this.settings.allowWeightExportSHealth != this.exportWeightToSHealthChecked;
        boolean bl13 = this.isImportantNotificationsEnabled() != this.enableImportantNotifications.isChecked();
        if (this.settings.allowIntegrationGoogleFit.booleanValue() != this.integrateWithGoogleFit.isChecked()) return false | bl3 | bl4 | bl5 | bl6 | bl7 | bl8 | bl9 | bl10 | bl11 | bl12 | bl13 | bl;
        bl = false;
        return false | bl3 | bl4 | bl5 | bl6 | bl7 | bl8 | bl9 | bl10 | bl11 | bl12 | bl13 | bl;
    }

    private void setupActivityTrackerSwitch() {
        this.enableActivityTracker.setOnCheckedChangeListener(null);
        this.enableActivityTracker.setChecked(this.isActivityTrackerEnabled());
        this.enableActivityTracker.setOnCheckedChangeListener(SettingsFragment$$Lambda$24.lambdaFactory$(this));
    }

    private void setupComponents() {
        this.firmwareUpdateCount.setVisibility(8);
        this.wifiConfirmationContainer.setOnClickListener(SettingsFragment$$Lambda$5.lambdaFactory$(this));
        this.relaunchOnboardingContainer.setOnClickListener(SettingsFragment$$Lambda$6.lambdaFactory$(this));
        this.showActivityTrackerGoalSettings(false);
        this.activityTrackerSetGoalMinusButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SettingsFragment.access$1110(SettingsFragment.this);
                view = PreferenceManager.getDefaultSharedPreferences((Context)SettingsFragment.this.getActivity()).edit();
                view.putInt("com.getqardio.android.extra.GOAL_VALUE_FOR_ACTIVITY_TRACKER", SettingsFragment.this.currentGoal);
                view.apply();
                SettingsFragment.this.activityTrackerGoal.setText((CharSequence)String.valueOf(SettingsFragment.this.currentGoal));
            }
        });
        if (this.isGooglePlayServicesAvailableAndGoogleBuild) {
            this.enablePlaces.setOnCheckedChangeListener(SettingsFragment$$Lambda$7.lambdaFactory$(this));
        }
        this.showImageButton.setOnClickListener(SettingsFragment$$Lambda$8.lambdaFactory$(this));
        this.pauseSizeLayoutHeight = this.getResources().getDimensionPixelOffset(2131427662);
        this.photoAlbumsLayoutHeight = this.getResources().getDimensionPixelOffset(2131427663);
        this.measurementsCount.setListener(SettingsFragment$$Lambda$9.lambdaFactory$(this));
        this.pauseSize.setListener(SettingsFragment$$Lambda$10.lambdaFactory$(this));
        this.resetPairing.setOnClickListener(SettingsFragment$$Lambda$11.lambdaFactory$(this));
        this.resetQardioBaseView.setOnClickListener(SettingsFragment$$Lambda$12.lambdaFactory$(this));
        final int n = this.getResources().getDimensionPixelSize(2131427420);
        String string2 = new RandomImageGenerator(this.getActivity(), false, true).getRandomImagePath();
        Glide.with(this.getActivity()).load(string2).asBitmap().into(new SimpleTarget<Bitmap>(n, n){

            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                if (SettingsFragment.this.isAdded()) {
                    bitmap = new BitmapDrawable(SettingsFragment.this.getResources(), bitmap);
                    bitmap.setBounds(0, 0, n, n);
                    SettingsFragment.this.showPhotosFromDevice.setCompoundDrawablesRelative((Drawable)bitmap, null, null, null);
                }
            }
        });
        string2 = SettingsFragment$$Lambda$13.lambdaFactory$(this);
        this.showPhotosFromDevice.setOnClickListener((View.OnClickListener)string2);
        this.showPhotoCollection.setOnClickListener((View.OnClickListener)string2);
        this.showPhoto.setOnCheckedChangeListener(SettingsFragment$$Lambda$14.lambdaFactory$(this));
        this.modesContainer.setOnClickListener(SettingsFragment$$Lambda$15.lambdaFactory$(this));
        this.hidePhotoAlbumsContainer(false);
    }

    private void showActivityTrackerGoalSettings(boolean bl) {
        if (bl) {
            this.activityTrackerGoalSettings.setVisibility(0);
            this.currentGoal = PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getInt("com.getqardio.android.extra.GOAL_VALUE_FOR_ACTIVITY_TRACKER", 30);
            this.activityTrackerGoal.setText((CharSequence)String.valueOf(this.currentGoal));
            this.activityTrackerSetGoalPlusButton.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    SettingsFragment.access$1108(SettingsFragment.this);
                    view = PreferenceManager.getDefaultSharedPreferences((Context)SettingsFragment.this.getActivity()).edit();
                    view.putInt("com.getqardio.android.extra.GOAL_VALUE_FOR_ACTIVITY_TRACKER", SettingsFragment.this.currentGoal);
                    view.apply();
                    SettingsFragment.this.activityTrackerGoal.setText((CharSequence)String.valueOf(SettingsFragment.this.currentGoal));
                }
            });
            return;
        }
        this.activityTrackerGoalSettings.setVisibility(8);
    }

    private void showBlockedPermissionExplanation() {
        new AlertDialog.Builder((Context)this.getActivity(), 2131493366).setTitle(2131362385).setMessage(2131362212).setPositiveButton(2131362171, SettingsFragment$$Lambda$1.lambdaFactory$(this)).create().show();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showLayout(boolean bl, ViewGroup viewGroup, int n) {
        if (!bl) {
            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
            layoutParams.height = n;
            viewGroup.setAlpha(1.0f);
            viewGroup.setLayoutParams(layoutParams);
            return;
        } else {
            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
            if (viewGroup.getHeight() == n) return;
            {
                ValueAnimator valueAnimator = this.animators.remove((Object)viewGroup);
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                float f = (float)viewGroup.getHeight() * 1.0f / (float)n;
                valueAnimator = ValueAnimator.ofFloat((float[])new float[]{f, 1.0f});
                valueAnimator.addUpdateListener(SettingsFragment$$Lambda$21.lambdaFactory$(layoutParams, n, viewGroup));
                valueAnimator.setDuration((long)((int)((1.0f - f) * 500.0f)));
                valueAnimator.start();
                this.animators.put(viewGroup, valueAnimator);
                return;
            }
        }
    }

    private void showPauseSizeLayout(boolean bl) {
        this.showLayout(bl, this.pauseSizeLayout, this.pauseSizeLayoutHeight);
    }

    private void showPhotoAlbumsContainer(boolean bl) {
        this.showLayout(bl, this.photoAlbumsLayout, this.photoAlbumsLayoutHeight);
    }

    private void trackAnalyticsEvents() {
        CustomApplication.getApplication().getCurrentUserTrackingId();
        new CustomTraits().putQaMeasurementPause(this.getPauseSize()).putQaMeasurementCount(this.getMeasurementsCount()).putEnabledGoogleFit(this.integrateWithGoogleFit.isChecked()).putEnabledPlaces(this.enablePlaces.isChecked()).putEnabledImportantNotifications(this.isImportantNotificationsEnabled());
        if (this.settings != null && this.settings.allowLocation.booleanValue() != this.enablePlaces.isChecked()) {
            this.trackPlacesOptionChanged(this.enablePlaces.isChecked());
        }
    }

    private void trackGoogleFitOptionChanged(boolean bl) {
        GeneralAnalyticsTracker.trackGoogleFitChanged((Context)this.getActivity(), bl);
    }

    private void trackPlacesOptionChanged(boolean bl) {
        GeneralAnalyticsTracker.trackPlacesChanged((Context)this.getActivity(), bl);
    }

    public boolean isActivityTrackerEnabled() {
        return PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getBoolean("google_fit_activity_tracker", Boolean.FALSE.booleanValue());
    }

    /* synthetic */ void lambda$applyData$14(CompoundButton compoundButton, final boolean bl) {
        if (this.googleApiClient != null) {
            this.googleApiClient.disconnect();
        }
        this.googleApiClient = GoogleFitUtils.buildNewClient((Context)this.getActivity());
        if (bl) {
            this.connectToGoogleFit(this.googleApiClient);
            return;
        }
        this.googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks(){

            @Override
            public void onConnected(Bundle object) {
                if (!bl) {
                    if (SettingsFragment.this.googleApiClient.isConnected()) {
                        object = new GoogleFitApiImpl(SettingsFragment.this.googleApiClient);
                        if (!SettingsFragment.this.enableActivityTracker.isChecked() && !SettingsFragment.this.integrateWithGoogleFit.isChecked()) {
                            object.disconnectFromFit();
                        }
                    }
                    SettingsFragment.this.trackGoogleFitOptionChanged(false);
                    SettingsFragment.this.saveSettings();
                }
            }

            @Override
            public void onConnectionSuspended(int n) {
            }
        });
        this.googleApiClient.registerConnectionFailedListener(SettingsFragment$$Lambda$26.lambdaFactory$(this));
        this.googleApiClient.connect();
    }

    /* synthetic */ void lambda$applyData$15(CompoundButton compoundButton, boolean bl) {
        this.saveEnableNotificatiosSettingLocally();
        this.saveSettings();
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$applyData$16(CompoundButton compoundButton, boolean bl) {
        boolean bl2 = this.settingsChangedByUser;
        boolean bl3 = !this.settingSynchronization;
        this.settingsChangedByUser = bl3 | bl2;
        if (bl) {
            this.saveSettings = false;
            this.connectToDataStore(ShealthPermissionKeys.READ, this.requestReadPermissionsResultListener);
            return;
        }
        this.importBpFromSHealthChecked = false;
        this.importWeightFromSHealthChecked = false;
        CustomApplication.getApplication().unregisterBpDataObserver();
        CustomApplication.getApplication().unregisterWeightDataObserver();
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$applyData$17(CompoundButton compoundButton, boolean bl) {
        boolean bl2 = this.settingsChangedByUser;
        boolean bl3 = !this.settingSynchronization;
        this.settingsChangedByUser = bl3 | bl2;
        if (bl) {
            this.saveSettings = false;
            this.connectToDataStore(ShealthPermissionKeys.WRITE, this.requestWritePermissionsResultListener);
            return;
        }
        this.exportBpToSHealthChecked = false;
        this.exportWeightToSHealthChecked = false;
    }

    /* synthetic */ void lambda$connectToDataStore$20() {
        this.importFromSHealth.setChecked(false);
        this.settings.allowBpImportSHealth = false;
        this.settings.allowWeightImportSHealth = false;
        this.storeInSHealth.setChecked(false);
        this.settings.allowBpExportSHealth = false;
        this.settings.allowWeightExportSHealth = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    /* synthetic */ void lambda$connectToGoogleFit$21(ConnectionResult connectionResult) {
        Activity activity = this.getActivity();
        if (activity == null) return;
        {
            Timber.i("Connection failed. Cause: %s", connectionResult.toString());
            if (!connectionResult.hasResolution()) {
                GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), activity, 0).show();
                this.changeGoogleFitIntegrationState(false);
                return;
            } else {
                if (this.authInProgress) return;
                {
                    try {
                        Timber.i("Attempting to resolve failed connection", new Object[0]);
                        this.authInProgress = true;
                        connectionResult.startResolutionForResult(activity, 2);
                        return;
                    }
                    catch (IntentSender.SendIntentException sendIntentException) {
                        Timber.e(sendIntentException, "Exception while starting resolution activity", new Object[0]);
                        return;
                    }
                }
            }
        }
    }

    /* synthetic */ void lambda$null$13(ConnectionResult connectionResult) {
        this.saveSettings();
    }

    /* synthetic */ void lambda$null$22(CompoundButton compoundButton, ConnectionResult connectionResult) {
        if (connectionResult.getErrorCode() == 4) {
            try {
                if (connectionResult.hasResolution()) {
                    connectionResult.startResolutionForResult(this.getActivity(), 5);
                }
                return;
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                sendIntentException.printStackTrace();
                return;
            }
        }
        PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putBoolean("google_fit_activity_tracker", false).apply();
        compoundButton.setChecked(false);
    }

    /* synthetic */ void lambda$setNoFirmwareUpdateHandling$1(View object) {
        Activity activity;
        block6: {
            block7: {
                block5: {
                    activity = this.getActivity();
                    if (activity == null) break block5;
                    FirmwareDescription firmwareDescription = FirmwareUpdateHelper.getCurrentQBFirmwareUpdate((Context)activity);
                    object = "";
                    if (firmwareDescription != null) {
                        object = String.format(Utils.getLocale(), "%d.%d.%d", firmwareDescription.versionMajor, firmwareDescription.versionMinor, firmwareDescription.versionBugFix);
                    }
                    if (!this.checkPermissions()) break block6;
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), "android.permission.ACCESS_COARSE_LOCATION") && this.mSettingsPref.getBoolean("location_permission_checked", false)) break block7;
                    PermissionUtil.BlePermissions.checkCoarseLocationPermission(this.getActivity());
                }
                return;
            }
            this.showBlockedPermissionExplanation();
            return;
        }
        if (!LocationServiceSettingsCheckerImpl.getInstance().isLocationAvailable((Context)this.getActivity())) {
            ((MainActivity)this.getActivity()).checkLocationSettings();
            return;
        }
        this.startActivityForResult(QBStepOnActivity.createStartIntentForNoFirmwareUpdate((Context)activity, (String)object), 4);
    }

    /* synthetic */ void lambda$setupActivityTrackerSwitch$23(final CompoundButton compoundButton, final boolean bl) {
        if (this.googleApiClient != null) {
            this.googleApiClient.disconnect();
        }
        this.googleApiClient = GoogleFitUtils.buildNewActivityTrackerClient((Context)this.getActivity());
        this.googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks(){

            /* synthetic */ void lambda$onConnected$0(CompoundButton compoundButton2, Boolean bl2) throws Exception {
                PreferenceManager.getDefaultSharedPreferences((Context)SettingsFragment.this.getActivity()).edit().putBoolean("google_fit_activity_tracker", bl2.booleanValue()).apply();
                compoundButton2.setChecked(bl2.booleanValue());
            }

            /* synthetic */ void lambda$onConnected$1(GoogleFitApi googleFitApi) throws Exception {
                if (!SettingsFragment.this.enableActivityTracker.isChecked() && !SettingsFragment.this.integrateWithGoogleFit.isChecked()) {
                    googleFitApi.disconnectFromFit();
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onConnected(Bundle object) {
                object = new GoogleFitApiImpl(SettingsFragment.this.googleApiClient);
                if (bl) {
                    SettingsFragment.this.disposables.add(object.startGoogleFitSubscriptions().subscribe(SettingsFragment$9$$Lambda$1.lambdaFactory$(this, compoundButton), SettingsFragment$9$$Lambda$2.lambdaFactory$()));
                    if (Build.VERSION.SDK_INT < 21) {
                        ActivityGoalsJobServiceCompat.scheduleActivityGoalJob((Context)SettingsFragment.this.getActivity());
                        return;
                    }
                    if (ActivityGoalsJobService.areJobsScheduled((Context)SettingsFragment.this.getActivity())) return;
                    {
                        ActivityGoalsJobService.scheduleActivityGoalJob((Context)SettingsFragment.this.getActivity());
                        return;
                    }
                } else {
                    compoundButton.setChecked(false);
                    PreferenceManager.getDefaultSharedPreferences((Context)SettingsFragment.this.getActivity()).edit().putBoolean("google_fit_activity_tracker", false).apply();
                    SettingsFragment.this.disposables.add(object.endGoogleFitSubscriptions().subscribe(SettingsFragment$9$$Lambda$3.lambdaFactory$(this, (GoogleFitApi)object)));
                    if (Build.VERSION.SDK_INT < 21) {
                        ActivityGoalsJobServiceCompat.unscheduleActivityGoalJob((Context)SettingsFragment.this.getActivity());
                        return;
                    }
                    if (ActivityGoalsJobService.areJobsScheduled((Context)SettingsFragment.this.getActivity())) return;
                    {
                        ActivityGoalsJobService.unscheduleActivityGoalJob((Context)SettingsFragment.this.getActivity());
                        return;
                    }
                }
            }

            @Override
            public void onConnectionSuspended(int n) {
            }
        });
        this.googleApiClient.registerConnectionFailedListener(SettingsFragment$$Lambda$25.lambdaFactory$(this, compoundButton));
        ActivityTrackerAnalytics.trackActivityTrackerEnabled((Context)this.getActivity(), bl, "Settings");
        this.googleApiClient.connect();
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$setupComponents$10(View view) {
        boolean bl = true;
        this.settingsChangedByUser = true;
        if (view.isSelected()) {
            bl = false;
        }
        view.setSelected(bl);
        bl = view.isSelected();
        if (view.equals((Object)this.showPhotosFromDevice) && bl) {
            GeneralAnalyticsTracker.trackSelectedPhotoAlbum((Context)this.getActivity());
            if (!PermissionUtil.ExternalStorage.hasExternalStoragePermission(this.getActivity())) {
                PermissionUtil.ExternalStorage.checkExternalStoragePermission(this);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$setupComponents$11(CompoundButton compoundButton, boolean bl) {
        boolean bl2 = this.settingsChangedByUser;
        boolean bl3 = !this.settingSynchronization;
        this.settingsChangedByUser = bl3 | bl2;
        GeneralAnalyticsTracker.trackChangedPhotoSlideshow((Context)this.getActivity(), bl);
        if (bl) {
            this.showPhotoAlbumsContainer(true);
            return;
        }
        this.hidePhotoAlbumsContainer(true);
    }

    /* synthetic */ void lambda$setupComponents$12(View view) {
        if (this.checkPermissions()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), "android.permission.ACCESS_COARSE_LOCATION") || !this.mSettingsPref.getBoolean("location_permission_checked", false)) {
                PermissionUtil.BlePermissions.checkCoarseLocationPermission(this.getActivity());
                return;
            }
            this.showBlockedPermissionExplanation();
            return;
        }
        if (!LocationServiceSettingsCheckerImpl.getInstance().isLocationAvailable((Context)this.getActivity())) {
            ((MainActivity)this.getActivity()).checkLocationSettings();
            return;
        }
        this.startActivityForResult(QBStepOnActivity.createStartIntentForMode((Context)this.getActivity()), 4);
    }

    /* synthetic */ void lambda$setupComponents$2(View view) {
        block5: {
            block6: {
                block4: {
                    if (this.getActivity() == null) break block4;
                    if (!this.checkPermissions()) break block5;
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), "android.permission.ACCESS_COARSE_LOCATION") && this.mSettingsPref.getBoolean("location_permission_checked", false)) break block6;
                    PermissionUtil.BlePermissions.checkCoarseLocationPermission(this.getActivity());
                }
                return;
            }
            this.showBlockedPermissionExplanation();
            return;
        }
        if (!LocationServiceSettingsCheckerImpl.getInstance().isLocationAvailable((Context)this.getActivity())) {
            ((MainActivity)this.getActivity()).checkLocationSettings();
            return;
        }
        this.startActivityForResult(QBWifiConfigurationActivity.createStartIntentFromSettings((Context)this.getActivity()), 4);
    }

    /* synthetic */ void lambda$setupComponents$3(View view) {
        block5: {
            block6: {
                block4: {
                    view = this.getActivity();
                    if (view == null) break block4;
                    if (!this.checkPermissions()) break block5;
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), "android.permission.ACCESS_COARSE_LOCATION") && this.mSettingsPref.getBoolean("location_permission_checked", false)) break block6;
                    PermissionUtil.BlePermissions.checkCoarseLocationPermission(this.getActivity());
                }
                return;
            }
            this.showBlockedPermissionExplanation();
            return;
        }
        if (!LocationServiceSettingsCheckerImpl.getInstance().isLocationAvailable((Context)this.getActivity())) {
            ((MainActivity)this.getActivity()).checkLocationSettings();
            return;
        }
        this.startActivityForResult(QBOnboardingActivity.createStartIntent((Context)view), 3);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$setupComponents$4(CompoundButton compoundButton, boolean bl) {
        bl = this.settingsChangedByUser;
        boolean bl2 = !this.settingSynchronization;
        this.settingsChangedByUser = bl2 | bl;
    }

    /* synthetic */ void lambda$setupComponents$5(View view) {
        view = ImageSlideshowActivity.getStartIntent((Context)this.getActivity(), true, true);
        this.getActivity().startActivity((Intent)view);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$setupComponents$6(CustomSeekBar customSeekBar, int n) {
        boolean bl = this.settingsChangedByUser;
        boolean bl2 = !this.settingSynchronization;
        this.settingsChangedByUser = bl2 | bl;
        if (n > customSeekBar.getMinValue()) {
            this.showPauseSizeLayout(true);
            return;
        }
        this.pauseSize.resetProgress();
        this.hidePauseSizeLayout(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$setupComponents$7(CustomSeekBar customSeekBar, int n) {
        int n2 = this.settingsChangedByUser;
        n = !this.settingSynchronization ? 1 : 0;
        this.settingsChangedByUser = n | n2;
    }

    /* synthetic */ void lambda$setupComponents$8(View view) {
        view = this.getActivity();
        if (view != null) {
            MobileDeviceFactory.resetBond((Context)view);
        }
    }

    /* synthetic */ void lambda$setupComponents$9(View view) {
        if (this.checkPermissions()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), "android.permission.ACCESS_COARSE_LOCATION") || !this.mSettingsPref.getBoolean("location_permission_checked", false)) {
                PermissionUtil.BlePermissions.checkCoarseLocationPermission(this.getActivity());
                return;
            }
            this.showBlockedPermissionExplanation();
            return;
        }
        if (!LocationServiceSettingsCheckerImpl.getInstance().isLocationAvailable((Context)this.getActivity())) {
            ((MainActivity)this.getActivity()).checkLocationSettings();
            return;
        }
        this.startActivityForResult(QBStepOnActivity.createStartIntentForReset((Context)this.getActivity()), 4);
    }

    /* synthetic */ void lambda$showBlockedPermissionExplanation$0(DialogInterface dialogInterface, int n) {
        dialogInterface = new Intent();
        dialogInterface.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        dialogInterface.setData(Uri.fromParts((String)"package", (String)this.getActivity().getPackageName(), null));
        this.startActivity((Intent)dialogInterface);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362050);
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Settings");
        this.setHasOptionsMenu(true);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        DataHelper.SettingsHelper.requestSettingsUpdate((Context)this.getActivity(), this.getUserId());
        this.mSettingsPref = PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity().getApplicationContext());
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent intent) {
        boolean bl = true;
        switch (n) {
            default: {
                return;
            }
            case 4: {
                this.firmwareUpdateCount.setVisibility(8);
                return;
            }
            case 2: {
                this.handleGoogleFitAuthResult(n2, false);
                return;
            }
            case 5: {
                this.handleGoogleFitAuthResult(n2, true);
                return;
            }
            case 3: 
        }
        Timber.d("onActivityResult REQUEST_ON_BOARDING", new Object[0]);
        intent = new Intent();
        if (n2 != -1) {
            bl = false;
        }
        intent.putExtra("com.getqardio.android.argument.LAUNCHED_FROM_ONBOARDING", bl);
        this.handleOnboardingResult(n2, intent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: {
                long l = this.getUserId();
                if (l == -1L) return null;
                return DataHelper.SettingsHelper.getSettingsLoader((Context)this.getActivity(), l, null);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968825, viewGroup, false);
        this.isGooglePlayServicesAvailableAndGoogleBuild = CustomApplication.getApplication().isGooglePlayServiceAvailableAndGoogleBuild();
        this.showPhoto = (CustomSwitch)layoutInflater.findViewById(2131821335);
        this.measurementsCount = (CustomSeekBar)layoutInflater.findViewById(2131821342);
        this.pauseSize = (CustomSeekBar)layoutInflater.findViewById(2131821348);
        this.pauseSizeLayout = (ViewGroup)layoutInflater.findViewById(2131821346);
        this.showImageButton = layoutInflater.findViewById(2131821339);
        this.resetPairing = (Button)layoutInflater.findViewById(2131821361);
        this.enablePlaces = (CustomSwitch)layoutInflater.findViewById(2131821334);
        this.enableImportantNotifications = (CustomSwitch)layoutInflater.findViewById(2131821325);
        this.enableActivityTracker = (CustomSwitch)layoutInflater.findViewById(2131821323);
        this.enableActivityTracker.setChecked(this.isActivityTrackerEnabled());
        this.photoAlbumsLayout = (ViewGroup)layoutInflater.findViewById(2131821336);
        this.showPhotosFromDevice = (TextView)layoutInflater.findViewById(2131821338);
        this.showPhotoCollection = (TextView)layoutInflater.findViewById(2131821337);
        this.storeInSHealth = (CustomSwitch)layoutInflater.findViewById(2131821332);
        this.importFromSHealth = (CustomSwitch)layoutInflater.findViewById(2131821331);
        this.modesContainer = layoutInflater.findViewById(2131821353);
        this.firmwareUpdateContainer = layoutInflater.findViewById(2131821356);
        this.firmwareUpdateCount = (TextView)layoutInflater.findViewById(2131821357);
        this.wifiConfirmationContainer = layoutInflater.findViewById(2131821354);
        this.relaunchOnboardingContainer = layoutInflater.findViewById(2131821355);
        this.resetQardioBaseView = (TextView)layoutInflater.findViewById(2131821359);
        this.integrateWithGoogleFit = (CustomSwitch)layoutInflater.findViewById(2131821321);
        layoutInflater.findViewById(2131821320);
        viewGroup = layoutInflater.findViewById(2131821320);
        this.activityTrackerGoalSettings = (RelativeLayout)layoutInflater.findViewById(2131821326);
        this.activityTrackerSetGoalPlusButton = (Button)layoutInflater.findViewById(2131821327);
        this.activityTrackerSetGoalMinusButton = (Button)layoutInflater.findViewById(2131821328);
        this.activityTrackerGoal = (TextView)layoutInflater.findViewById(2131821329);
        bundle = (TextView)layoutInflater.findViewById(2131821362);
        if (!"release".equalsIgnoreCase("release")) {
            bundle.setText((CharSequence)String.format(Locale.getDefault(), "v%s/%d %s %s %s \n %s", Utils.getVersionName((Context)this.getActivity()), Utils.getVersionCode((Context)this.getActivity()), "google", "release", "03-12-18", "https://api.getqardio.com"));
        } else {
            bundle.setVisibility(8);
        }
        ((TextView)layoutInflater.findViewById(2131821343)).setText((CharSequence)Utils.formatInteger(1));
        ((TextView)layoutInflater.findViewById(2131821344)).setText((CharSequence)Utils.formatInteger(2));
        ((TextView)layoutInflater.findViewById(2131821345)).setText((CharSequence)Utils.formatInteger(3));
        ((TextView)layoutInflater.findViewById(2131821349)).setText((CharSequence)Utils.formatInteger(15));
        ((TextView)layoutInflater.findViewById(2131821350)).setText((CharSequence)Utils.formatInteger(30));
        ((TextView)layoutInflater.findViewById(2131821351)).setText((CharSequence)Utils.formatInteger(45));
        ((TextView)layoutInflater.findViewById(2131821352)).setText((CharSequence)Utils.formatInteger(60));
        bundle = (TextView)layoutInflater.findViewById(2131821319);
        if (!this.isGooglePlayServicesAvailableAndGoogleBuild) {
            LinearLayout linearLayout = (LinearLayout)layoutInflater.findViewById(2131821333);
            RelativeLayout relativeLayout = (RelativeLayout)layoutInflater.findViewById(2131821324);
            linearLayout.setVisibility(8);
            relativeLayout.setVisibility(8);
            viewGroup.setVisibility(8);
        } else {
            this.googleApiClient = GoogleFitUtils.buildNewClient((Context)this.getActivity());
            this.googleApiClient.connect();
        }
        viewGroup = (LinearLayout)layoutInflater.findViewById(2131821330);
        boolean bl = CustomApplication.getApplication().isSupportSHealth();
        if (bl) {
            viewGroup.setVisibility(0);
        }
        if (!this.isGooglePlayServicesAvailableAndGoogleBuild && !bl) {
            bundle.setVisibility(8);
        }
        if (Build.VERSION.SDK_INT >= 26 && (viewGroup = (RelativeLayout)layoutInflater.findViewById(2131821324)) != null) {
            viewGroup.setVisibility(8);
        }
        this.setupComponents();
        return layoutInflater;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.disposables.clear();
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        long l;
        block5: {
            block4: {
                if (loader.getId() != 1 || (l = this.getUserId()) == -1L) break block4;
                if (!cursor.moveToFirst()) break block5;
                this.settings = DataHelper.SettingsHelper.parseSettings(cursor);
                if (this.settings.userId == l && !this.settingsChangedByUser) {
                    this.applyData(this.settings);
                }
            }
            return;
        }
        this.settings = DataHelper.SettingsHelper.getDefaultSettings(l);
        this.applyData(this.settings);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onPause() {
        super.onPause();
        this.broadcastManager.unregisterReceiver(this.networkReceiver);
        this.trackAnalyticsEvents();
        if (this.saveSettings) {
            this.saveSettings();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        switch (n) {
            default: {
                return;
            }
            case 0: {
                if (arrn.length != 0 && arrn[0] == 0) return;
                this.showPhotosFromDevice.setSelected(false);
                return;
            }
        }
    }

    public void onResume() {
        super.onResume();
        this.setNoFirmwareUpdateHandling();
        this.broadcastManager.registerReceiver(this.networkReceiver, new IntentFilter("com.getqardio.android.action.NEW_BASE_FIRMWARE"));
        FirmwareUpdateHelper.loadQBLatestFirmwareInfo((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId());
    }

    public void onStart() {
        super.onStart();
        if (this.getActivity() != null) {
            this.broadcastManager = LocalBroadcastManager.getInstance((Context)this.getActivity());
        }
    }

    public void onStop() {
        super.onStop();
        if (this.googleApiClient != null) {
            this.googleApiClient.disconnect();
        }
    }

}

