/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.Loader
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.os.Handler
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.baseble.QardioBaseManager;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.QardioBaseSettingsHelper;
import com.getqardio.android.ui.activity.QBChooseModeActivity;
import com.getqardio.android.ui.fragment.OnBoardingDeviceAddressProvider;
import com.getqardio.android.ui.fragment.OnBoardingProfileProvider;
import com.getqardio.android.ui.fragment.QBModeStepOnStateFragment;
import com.getqardio.android.ui.fragment.QBOnSettingsReadyFragment;
import com.getqardio.android.ui.fragment.QBStepOnModeHostFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.QBStepOnModeHostFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.QBStepOnModeHostFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.QBStepOnModeHostFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.QBStepOnModeHostFragment$$Lambda$5;
import com.getqardio.android.utils.PregnancyUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.analytics.AnalyticsUtil;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.getqardio.android.utils.analytics.IdentifyHelper;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wizard.OnDoneClickListener;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

public class QBStepOnModeHostFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
OnBoardingProfileProvider,
OnDoneClickListener {
    private BroadcastReceiver baseReceiver;
    private LocalBroadcastManager broadcastManager;
    private boolean configDone;
    private ConfigState configState;
    private boolean connected = false;
    private Handler handler = new Handler();
    private boolean pregnancyHideWeight = true;
    private Profile profile;
    private QardioBaseManager qardioBaseManager;
    private QardioBaseDevice.BaseMode selectedMode;
    private String userProfiles;

    public QBStepOnModeHostFragment() {
        this.baseReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context object, Intent intent) {
                Timber.d("onReceive action - %s", intent.getAction());
                object = intent.getAction();
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 421757567: {
                        if (!((String)object).equals("com.qardio.base.DISCONNECTED")) break;
                        n = 0;
                        break;
                    }
                    case 1424235813: {
                        if (!((String)object).equals("com.qardio.base.CONNECTED")) break;
                        n = 1;
                        break;
                    }
                    case -598500176: {
                        if (!((String)object).equals("com.qardio.base.CONFIGURATION_MODE")) break;
                        n = 2;
                        break;
                    }
                    case -1094350772: {
                        if (!((String)object).equals("com.qardio.base.QB_USER_CONFIG")) break;
                        n = 3;
                        break;
                    }
                    case -53910355: {
                        if (!((String)object).equals("com.qardio.base.STATE")) break;
                        n = 4;
                        break;
                    }
                    case -1611280380: {
                        if (!((String)object).equals("com.qardio.base.QB_USER_CONFIG_WRITTEN")) break;
                        n = 5;
                        break;
                    }
                    case 814720241: {
                        if (!((String)object).equals("com.qardio.base.QB_CONNECTION_ERROR")) break;
                        n = 6;
                        break;
                    }
                }
                switch (n) {
                    case 1: {
                        object = intent.getStringExtra("com.qardio.base.DATA");
                        QBStepOnModeHostFragment.this.handleConnection((String)object);
                        QBStepOnModeHostFragment.this.connected = true;
                        return;
                    }
                    case 2: {
                        QBStepOnModeHostFragment.this.qardioBaseManager.enableEngineeringNotifications();
                        QBStepOnModeHostFragment.this.configState = ConfigState.READ_USER_PROFILE;
                        QBStepOnModeHostFragment.this.qardioBaseManager.readUserProfiles();
                        return;
                    }
                    case 3: {
                        object = intent.getStringExtra("com.qardio.base.DATA");
                        QBStepOnModeHostFragment.this.handleUserProfiles((String)object);
                        return;
                    }
                    case 4: {
                        Timber.d("state - %d", intent.getIntExtra("com.qardio.base.DATA", 0));
                        return;
                    }
                    case 5: {
                        if (QBStepOnModeHostFragment.this.configState == ConfigState.WRITE_USER_PROFILE) {
                            QBStepOnModeHostFragment.this.configState = ConfigState.USER_PROFILE_WRITTEN;
                            QBStepOnModeHostFragment.this.readProfilesDelayed();
                        }
                        if (QBStepOnModeHostFragment.this.configState == ConfigState.WRITTEN_PROFILE_CONFIRMED) {
                            QBStepOnModeHostFragment.this.finishProfileWrite();
                            QBStepOnModeHostFragment.this.configState = ConfigState.READ_FOR_SETTINGS;
                            QBStepOnModeHostFragment.this.readProfilesDelayed();
                            return;
                        }
                    }
                    default: {
                        return;
                    }
                    case 6: 
                }
                Timber.e("Connection error: %s", intent.getStringExtra("com.qardio.base.QB_ERROR_MSG"));
                QBStepOnModeHostFragment.this.handleConnectionError();
            }
        };
    }

    static /* synthetic */ boolean access$lambda$0(QBStepOnModeHostFragment qBStepOnModeHostFragment) {
        return qBStepOnModeHostFragment.shouldContinue();
    }

    static /* synthetic */ boolean access$lambda$1(QBStepOnModeHostFragment qBStepOnModeHostFragment) {
        return qBStepOnModeHostFragment.shouldContinue();
    }

    private void changeFragment(Fragment fragment) {
        if (this.getActivity() != null) {
            this.getChildFragmentManager().beginTransaction().replace(2131820778, fragment).commit();
        }
    }

    private void changeMode() {
        this.startActivityForResult(QBChooseModeActivity.createStartIntent((Context)this.getActivity()), 1);
    }

    private void finishProfileWrite() {
        Timber.d("finishProfileWrite", new Object[0]);
        long l = CustomApplication.getApplication().getCurrentUserId();
        if (this.selectedMode != QardioBaseDevice.BaseMode.MODE_PREGNANCY && DataHelper.PregnancyDataHelper.isPregnancyModeActive((Context)this.getActivity(), l)) {
            PregnancyUtils.stopPregnancyModeAsync((Context)this.getActivity(), l);
        }
        if (this.selectedMode == QardioBaseDevice.BaseMode.MODE_PREGNANCY) {
            DataHelper.PregnancyDataHelper.setPregnancyModeActive((Context)this.getActivity(), l, true);
        }
    }

    private void handleConnection(String string2) {
        ((OnBoardingDeviceAddressProvider)this.getActivity()).setDeviceAddress(string2);
        this.qardioBaseManager.enableStateNotifications();
        new Handler().postDelayed(QBStepOnModeHostFragment$$Lambda$3.lambdaFactory$(this), 1000L);
    }

    private void handleConnectionError() {
        this.qardioBaseManager.disconnect();
        Object object = this.getActivity();
        if (object != null) {
            object = DataHelper.DeviceAddressHelper.getDeviceAddress((Context)object, CustomApplication.getApplication().getCurrentUserId());
            this.qardioBaseManager.scanAndConnect((String)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void handleUserProfiles(String string2) {
        Timber.d("handleUserProfiles - %s, configState - %s", new Object[]{string2, this.configState});
        switch (2.$SwitchMap$com$getqardio$android$ui$fragment$QBStepOnModeHostFragment$ConfigState[this.configState.ordinal()]) {
            case 1: {
                this.userProfiles = string2;
                this.configState = ConfigState.CONFIRM_USER_PROFILE;
                this.qardioBaseManager.readUserProfiles();
                return;
            }
            case 2: {
                if (string2 != null) {
                    this.userProfiles = string2;
                    this.configState = ConfigState.CONFIRM_USER_PROFILE;
                    this.qardioBaseManager.readUserProfiles();
                    return;
                }
                if (this.getActivity() == null && this.getActivity().isFinishing()) return;
                {
                    this.getActivity().finish();
                    return;
                }
            }
            default: {
                return;
            }
            case 3: {
                this.qardioBaseManager.disableConfigurationMode();
                this.setBaseSettings(string2);
                this.handler.postDelayed(QBStepOnModeHostFragment$$Lambda$1.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(2L));
                return;
            }
            case 4: {
                if (this.userProfiles == null && string2 == null) {
                    this.configState = ConfigState.READ_PROFILE_FAILED;
                    this.qardioBaseManager.readUserProfiles();
                    return;
                }
                if (this.userProfiles == null) {
                    this.userProfiles = string2;
                }
                if (string2 != null && this.userProfiles.length() < string2.length()) {
                    this.userProfiles = string2;
                }
                this.configState = ConfigState.PROFILE_READ_CONFIRMED;
                this.writeModeConfig(this.selectedMode, this.userProfiles, this.pregnancyHideWeight);
                return;
            }
            case 5: {
                if (string2.length() > 40 && QardioBaseUtils.indexOf(string2, this.profile.refId) != -1) {
                    this.configState = ConfigState.WRITTEN_PROFILE_CONFIRMED;
                    this.userProfiles = string2;
                    string2 = QardioBaseUtils.createImpedanceAndHapticSetting(this.selectedMode);
                    if (string2 == null) return;
                    Timber.d("handleUserProfiles writeProfile - %s", string2);
                    this.qardioBaseManager.writeProfile((JSONObject)string2);
                    return;
                } else {
                    break;
                }
            }
        }
        if (string2.length() >= 40 && QardioBaseUtils.indexOf(string2, this.profile.refId) == -1) {
            Timber.d("disableConfigurationMode", new Object[0]);
            this.qardioBaseManager.disableConfigurationMode();
            this.setBaseSettings(string2);
            this.handler.postDelayed(QBStepOnModeHostFragment$$Lambda$2.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(2L));
            return;
        }
        try {
            this.configState = ConfigState.PROFILE_WRITE_FAILED;
            Timber.d("handleUserProfiles PROFILE_WRITE_FAILED", new Object[0]);
            this.qardioBaseManager.writeProfile(new JSONObject(this.userProfiles));
            return;
        }
        catch (JSONException jSONException) {
            Timber.e(jSONException, "Cannot create JSON Object from user profile config", new Object[0]);
            return;
        }
    }

    public static QBStepOnModeHostFragment newInstance() {
        return new QBStepOnModeHostFragment();
    }

    private void readProfilesDelayed() {
        Timber.d("readProfilesDelayed", new Object[0]);
        this.handler.postDelayed(QBStepOnModeHostFragment$$Lambda$5.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(1L));
    }

    private void setBaseSettings(String string2) {
        Activity activity = this.getActivity();
        if (activity != null) {
            QardioBaseSettingsHelper.setCurrentBaseSettings((Context)activity, JSONParser.parseQardioBaseSettings(string2));
        }
    }

    private boolean shouldContinue() {
        boolean bl = true;
        if (this.configDone) {
            this.changeFragment(QBOnSettingsReadyFragment.newInstance(true, 2131362369));
            bl = false;
        }
        return bl;
    }

    private void startConnectionTimeout() {
        this.handler.postDelayed(QBStepOnModeHostFragment$$Lambda$4.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(20L));
    }

    private void writeModeConfig(QardioBaseDevice.BaseMode object, String string2, boolean bl) {
        Object object2;
        Timber.d("writeModeConfig - %s, userProfiles - %s, pregnancyHideWeight - %s", object, string2, bl);
        this.configState = ConfigState.WRITE_USER_PROFILE;
        QardioBaseDevice.BaseMode baseMode = object2 = object;
        if (object == QardioBaseDevice.BaseMode.MODE_PREGNANCY) {
            baseMode = object2;
            if (!bl) {
                baseMode = QardioBaseDevice.BaseMode.MODE_WEIGHT_ONLY;
            }
        }
        object = AnalyticsUtil.getQardioBaseMode((Context)this.getActivity(), baseMode);
        QardioBaseDeviceAnalyticsTracker.trackModeChanged((Context)this.getActivity(), (String)object);
        object = new CustomTraits();
        object2 = CustomApplication.getApplication().getCurrentUserTrackingId();
        if (object2 != null) {
            IdentifyHelper.identify((Context)this.getActivity(), (String)object2, (CustomTraits)object);
        }
        if (!TextUtils.isEmpty((CharSequence)string2) && (object = QardioBaseUtils.createModeCommand(baseMode, QardioBaseUtils.indexOf(string2, this.profile.refId), QardioBaseUtils.noProfiles(string2))) != null) {
            Timber.d("write profile - %s", object);
            this.qardioBaseManager.writeProfile((JSONObject)object);
        }
    }

    @Override
    public Profile getProfile() {
        return this.profile;
    }

    /* synthetic */ void lambda$handleConnection$0() {
        this.qardioBaseManager.enableConfigurationMode();
    }

    /* synthetic */ void lambda$readProfilesDelayed$2() {
        this.qardioBaseManager.readUserProfiles();
    }

    /* synthetic */ void lambda$startConnectionTimeout$1() {
        if (!this.connected) {
            this.qardioBaseManager.disconnect();
            Activity activity = this.getActivity();
            if (activity != null) {
                activity.finish();
            }
            this.connected = false;
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n2 == -1) {
            this.selectedMode = (QardioBaseDevice.BaseMode)((Object)intent.getSerializableExtra("com.getqardio.android.extra.SELECTED_MODE"));
            if (this.selectedMode == QardioBaseDevice.BaseMode.MODE_PREGNANCY && intent.hasExtra("com.getqardio.android.extra.HIDE_WEIGHT")) {
                this.pregnancyHideWeight = intent.getBooleanExtra("com.getqardio.android.extra.HIDE_WEIGHT", true);
            }
            this.configDone = true;
            return;
        } else {
            intent = this.getActivity();
            if (intent == null) return;
            {
                this.qardioBaseManager.disconnect();
                intent.finish();
                return;
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.qardioBaseManager = new QardioBaseManager((Context)this.getActivity());
        Timber.d("onCreate", new Object[0]);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        return DataHelper.ProfileHelper.getProfileLoader((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), DataHelper.ProfileHelper.PROFILE_SCREEN_PROJECTION);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130968800, viewGroup, false);
    }

    @Override
    public void onDoneClick() {
        if (this.getActivity() != null) {
            this.getActivity().finish();
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            this.profile = DataHelper.ProfileHelper.parseProfile(cursor);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onPause() {
        super.onPause();
        this.broadcastManager.unregisterReceiver(this.baseReceiver);
        this.qardioBaseManager.stopScan();
    }

    public void onResume() {
        super.onResume();
        Object object = new IntentFilter();
        object.addAction("com.qardio.base.QB_USER_CONFIG_WRITTEN");
        object.addAction("com.qardio.base.CONNECTED");
        object.addAction("com.qardio.base.DISCONNECTED");
        object.addAction("com.qardio.base.CONFIGURATION_MODE");
        object.addAction("com.qardio.base.QB_CONNECTION_ERROR");
        object.addAction("com.qardio.base.QB_USER_CONFIG");
        object.addAction("com.qardio.base.STATE");
        this.configState = ConfigState.START;
        this.broadcastManager.registerReceiver(this.baseReceiver, (IntentFilter)object);
        if (this.selectedMode != null) {
            this.changeFragment(QBModeStepOnStateFragment.newInstance(2131362373, 2131362372, 2130837594, true));
            this.startConnectionTimeout();
            object = this.getActivity();
            if (object != null) {
                object = DataHelper.DeviceAddressHelper.getDeviceAddress((Context)object, CustomApplication.getApplication().getCurrentUserId());
                Timber.d("scanAndConnect - %s", object);
                this.qardioBaseManager.scanAndConnect((String)object);
            }
        }
    }

    public void onStart() {
        this.broadcastManager = LocalBroadcastManager.getInstance((Context)this.getActivity());
        super.onStart();
    }

    public void onStop() {
        super.onStop();
        this.configDone = false;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.changeMode();
    }

    protected static enum ConfigState {
        START,
        READ_USER_PROFILE,
        CONFIRM_USER_PROFILE,
        READ_PROFILE_FAILED,
        PROFILE_READ_CONFIRMED,
        WRITE_USER_PROFILE,
        PROFILE_WRITE_FAILED,
        USER_PROFILE_WRITTEN,
        WRITTEN_PROFILE_CONFIRMED,
        READ_FOR_SETTINGS,
        FINNISH;

    }

}

