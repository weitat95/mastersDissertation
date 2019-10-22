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
 *  android.content.ComponentName
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
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.Toast
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
import android.content.ComponentName;
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
import android.widget.Button;
import android.widget.Toast;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.baseble.QardioBaseManager;
import com.getqardio.android.datamodel.FirmwareDescription;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.QardioBaseSettings;
import com.getqardio.android.exceptions.CommandException;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.request.DeviceAssociationsRequestHandler;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.FirmwareUpdateHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.provider.QardioBaseSettingsHelper;
import com.getqardio.android.ui.dialog.QBFirmwareUpdateProgress;
import com.getqardio.android.ui.fragment.OnBoardingProfileProvider;
import com.getqardio.android.ui.fragment.QBFirmwareUpdateErrorFragment;
import com.getqardio.android.ui.fragment.QBFirmwareUpdateSuccessFragment;
import com.getqardio.android.ui.fragment.QBInstallFirmwareUpdateFragment;
import com.getqardio.android.ui.fragment.QBOnSettingsReadyFragment;
import com.getqardio.android.ui.fragment.QBOnboardingReadyFragment;
import com.getqardio.android.ui.fragment.QBSettingsStepOnStateFragment;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment$$Lambda$7;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment$$Lambda$8;
import com.getqardio.android.ui.fragment.QBStepOnSettingHostFragment$6$$Lambda$1;
import com.getqardio.android.util.BaseUserUtil;
import com.getqardio.android.util.HexUtil;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.wizard.OnDoneClickListener;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

public class QBStepOnSettingHostFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
OnBoardingProfileProvider,
OnDoneClickListener {
    private BroadcastReceiver baseReceiver;
    private LocalBroadcastManager broadcastManager;
    private boolean configDone;
    private BroadcastReceiver configWifiReceiver;
    private int connectionRetries;
    private String deviceSerialNumber;
    private BroadcastReceiver firmwareUpdateReceiver;
    private Handler handler = new Handler();
    private boolean isProgressing = false;
    private boolean isWifiSecure;
    private Profile profile;
    private QardioBaseManager qardioBaseManager;
    private BroadcastReceiver resetReceiver;
    private int retries;
    private BroadcastReceiver userConfigWrittenReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context object, Intent intent) {
            Timber.d("userConfigWrittenReceiver onReceive action - %s", intent.getAction());
            object = intent.getAction();
            int n = -1;
            switch (((String)object).hashCode()) {
                case -1611280380: {
                    if (!((String)object).equals("com.qardio.base.QB_USER_CONFIG_WRITTEN")) break;
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
            QBStepOnSettingHostFragment.this.broadcastManager.unregisterReceiver(QBStepOnSettingHostFragment.this.userProfileReceiver);
            QBStepOnSettingHostFragment.this.changeFragment(QBOnboardingReadyFragment.newInstance(false));
        }
    };
    private BroadcastReceiver userProfileReceiver = new BroadcastReceiver(){

        /*
         * Exception decompiling
         */
        public void onReceive(Context var1_1, Intent var2_2) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }
    };
    private boolean wasReseted;
    private int wifiStateChecks;

    public QBStepOnSettingHostFragment() {
        this.firmwareUpdateReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context object, Intent intent) {
                int n;
                block11: {
                    n = 0;
                    Timber.d("onReceive action - %s", intent.getAction());
                    object = intent.getAction();
                    switch (((String)object).hashCode()) {
                        case 1258970562: {
                            if (((String)object).equals("com.qardio.base.QB_WIFI_CONFIG")) break block11;
                        }
                        default: {
                            break;
                        }
                        {
                        }
                        case 1356099009: {
                            if (!((String)object).equals("com.qardio.base.QB_FIRMWARE_UPDATED")) break;
                            n = 1;
                            break block11;
                        }
                    }
                    n = -1;
                }
                switch (n) {
                    default: {
                        return;
                    }
                    case 0: {
                        object = intent.getStringExtra("com.qardio.base.DATA");
                        if (!TextUtils.isEmpty((CharSequence)object) && ((String)object).length() >= 10) {
                            QBStepOnSettingHostFragment.this.enableUpdateFirmware((String)object);
                            return;
                        }
                        QBStepOnSettingHostFragment.this.qardioBaseManager.readWifiConfig();
                        return;
                    }
                    case 1: 
                }
                n = intent.getIntExtra("com.qardio.base.DATA", -1);
                QBStepOnSettingHostFragment.this.handleFirmwareUpgradeResult(n);
            }
        };
        this.configWifiReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context object, Intent intent) {
                Timber.d("onReceive action - %s", intent.getAction());
                object = intent.getAction();
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1856640497: {
                        if (!((String)object).equals("com.qardio.base.QB_WIFI_STATE")) break;
                        n = 0;
                        break;
                    }
                    case 421757567: {
                        if (!((String)object).equals("com.qardio.base.DISCONNECTED")) break;
                        n = 1;
                        break;
                    }
                    case -266285190: {
                        if (!((String)object).equals("com.qardio.base.QB_WIFI_CONFIG_WRITTEN")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        return;
                    }
                    case 0: {
                        n = intent.getIntExtra("com.qardio.base.DATA", 0);
                        QBStepOnSettingHostFragment.this.checkWifiState(n);
                        return;
                    }
                    case 1: {
                        QBStepOnSettingHostFragment.this.wifiConfigFailed();
                        return;
                    }
                    case 2: 
                }
                QBStepOnSettingHostFragment.this.readWifiStateDelayed();
            }
        };
        this.resetReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context context, Intent object) {
                Timber.d("onReceive resetReceiver action - %s", object.getAction());
                object = object.getAction();
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1167511797: {
                        if (((String)object).equals("com.getqardio.android.action.RESET_ASSOCIATION")) {
                            n = 0;
                        }
                    }
                    default: {
                        break;
                    }
                    case -138496091: {
                        if (!((String)object).equals("com.qardio.base.QB_RANDOM_WRITTEN")) break;
                        n = 1;
                    }
                }
                switch (n) {
                    default: {
                        return;
                    }
                    case 0: {
                        MeasurementHelper.Claim.removeAllClaimMeasurements(context, QBStepOnSettingHostFragment.access$1100((QBStepOnSettingHostFragment)QBStepOnSettingHostFragment.this).userId);
                        DataHelper.DeviceAssociationsHelper.deleteDeviceAssociation(context, QBStepOnSettingHostFragment.access$1100((QBStepOnSettingHostFragment)QBStepOnSettingHostFragment.this).userId, QBStepOnSettingHostFragment.this.deviceSerialNumber);
                        DataHelper.OnBoardingHelper.setOnboardingFinished(context, QBStepOnSettingHostFragment.access$1100((QBStepOnSettingHostFragment)QBStepOnSettingHostFragment.this).userId, false);
                        DataHelper.DeviceAddressHelper.deleteDeviceAddress(context, QBStepOnSettingHostFragment.access$1100((QBStepOnSettingHostFragment)QBStepOnSettingHostFragment.this).userId);
                        QBStepOnSettingHostFragment.this.showResetSuccess();
                        return;
                    }
                    case 1: 
                }
                context.startService(DeviceAssociationsRequestHandler.createRemoveDeviceAssociationsIntent(context, QBStepOnSettingHostFragment.access$1100((QBStepOnSettingHostFragment)QBStepOnSettingHostFragment.this).userId, DataHelper.DeviceIdHelper.getDeviceId(context, QBStepOnSettingHostFragment.access$1100((QBStepOnSettingHostFragment)QBStepOnSettingHostFragment.this).userId)));
            }
        };
        this.baseReceiver = new BroadcastReceiver(){

            /* synthetic */ void lambda$onReceive$0() {
                Timber.d("readSerialNumber", new Object[0]);
                QBStepOnSettingHostFragment.this.qardioBaseManager.readSerialNumber();
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context object, Intent intent) {
                Timber.d("onReceive baseReceiver - %s", intent.getAction());
                String string2 = intent.getAction();
                int n = -1;
                switch (string2.hashCode()) {
                    case 421757567: {
                        if (!string2.equals("com.qardio.base.DISCONNECTED")) break;
                        n = 0;
                        break;
                    }
                    case 1424235813: {
                        if (!string2.equals("com.qardio.base.CONNECTED")) break;
                        n = 1;
                        break;
                    }
                    case -598500176: {
                        if (!string2.equals("com.qardio.base.CONFIGURATION_MODE")) break;
                        n = 2;
                        break;
                    }
                    case 1712737337: {
                        if (!string2.equals("com.qardio.base.DEVICE_SERIAL")) break;
                        n = 3;
                        break;
                    }
                    case -165661596: {
                        if (!string2.equals("com.qardio.base.SOFTWARE_VERSION")) break;
                        n = 4;
                        break;
                    }
                    case -53910355: {
                        if (!string2.equals("com.qardio.base.STATE")) break;
                        n = 5;
                        break;
                    }
                    case 814720241: {
                        if (!string2.equals("com.qardio.base.QB_CONNECTION_ERROR")) break;
                        n = 6;
                        break;
                    }
                    case -1149325371: {
                        if (!string2.equals("com.qardio.base.QB_RESET")) break;
                        n = 7;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        return;
                    }
                    case 1: {
                        QBStepOnSettingHostFragment.this.handleConnection();
                        return;
                    }
                    case 2: {
                        Timber.d("Time: %d - Base in configuration mode", System.currentTimeMillis());
                        QBStepOnSettingHostFragment.this.qardioBaseManager.enableEngineeringNotifications();
                        QBStepOnSettingHostFragment.this.handler.postDelayed(QBStepOnSettingHostFragment$6$$Lambda$1.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(2L));
                        return;
                    }
                    case 3: {
                        object = intent.getStringExtra("com.qardio.base.DATA");
                        QBStepOnSettingHostFragment.this.deviceSerialNumber = HexUtil.intToHex((String)object);
                        QBStepOnSettingHostFragment.this.qardioBaseManager.readSoftwareVersion();
                        return;
                    }
                    case 4: {
                        object = intent.getStringExtra("com.qardio.base.DATA");
                        QBStepOnSettingHostFragment.this.handleSoftwareVersion((String)object);
                        QBStepOnSettingHostFragment.this.qardioBaseManager.readUserProfiles();
                        return;
                    }
                    case 5: {
                        n = intent.getIntExtra("com.qardio.base.DATA", 0);
                        QBStepOnSettingHostFragment.this.handleNewBaseState(n);
                        return;
                    }
                    case 6: {
                        Timber.e(intent.getStringExtra("com.qardio.base.QB_ERROR_MSG"), new Object[0]);
                        QBStepOnSettingHostFragment.this.handleConnectionError();
                        return;
                    }
                    case 7: 
                }
                QBStepOnSettingHostFragment.this.wasReseted = true;
                object.startService(DeviceAssociationsRequestHandler.createRemoveDeviceAssociationsIntent(object, QBStepOnSettingHostFragment.access$1100((QBStepOnSettingHostFragment)QBStepOnSettingHostFragment.this).userId, DataHelper.DeviceIdHelper.getDeviceId(object, QBStepOnSettingHostFragment.access$1100((QBStepOnSettingHostFragment)QBStepOnSettingHostFragment.this).userId)));
            }
        };
    }

    static /* synthetic */ Profile access$1100(QBStepOnSettingHostFragment qBStepOnSettingHostFragment) {
        return qBStepOnSettingHostFragment.profile;
    }

    static /* synthetic */ void access$300(QBStepOnSettingHostFragment qBStepOnSettingHostFragment, String string2) {
        qBStepOnSettingHostFragment.setBaseSettings(string2);
    }

    static /* synthetic */ void access$400(QBStepOnSettingHostFragment qBStepOnSettingHostFragment, String string2) {
        qBStepOnSettingHostFragment.executeAction(string2);
    }

    static /* synthetic */ void access$lambda$0(QBStepOnSettingHostFragment qBStepOnSettingHostFragment) {
        qBStepOnSettingHostFragment.scanAndConnect();
    }

    static /* synthetic */ void access$lambda$1(QBStepOnSettingHostFragment qBStepOnSettingHostFragment) {
        qBStepOnSettingHostFragment.scanAndConnect();
    }

    private void changeFragment(Fragment fragment) {
        if (this.getActivity() != null) {
            this.getChildFragmentManager().beginTransaction().replace(2131820778, fragment).commit();
        }
    }

    private void changeGoal(String string2) {
        Bundle bundle = this.getArguments();
        int n = QardioBaseUtils.indexOf(string2, this.profile.refId);
        int n2 = QardioBaseUtils.noProfiles(string2);
        if (n != -1) {
            float f = bundle.getFloat("com.getqardio.android.argument.GOAL");
            float f2 = bundle.getFloat("com.getqardio.android.argument.RATE");
            try {
                string2 = QardioBaseUtils.createGoalCommand(f, f2, n, n2);
                this.qardioBaseManager.writeProfile((JSONObject)string2);
                return;
            }
            catch (CommandException commandException) {
                Timber.e(commandException);
                this.qardioBaseManager.disconnect();
                return;
            }
        }
        this.qardioBaseManager.disconnect();
    }

    private void changeImpedance(String object) {
        Bundle bundle = this.getArguments();
        object = JSONParser.parseQardioBaseSettings((String)object);
        boolean bl = bundle.getBoolean("com.getqardio.android.argument.HAPTIC");
        Timber.d("haptic - %s", bl);
        try {
            this.qardioBaseManager.writeProfile(BaseUserUtil.createImpedanceCommand(((QardioBaseSettings)object).enableComposition, bl));
            ((QardioBaseSettings)object).enableHaptic = bl;
            QardioBaseSettingsHelper.setCurrentBaseSettings((Context)this.getActivity(), (QardioBaseSettings)object);
            this.trackHapticSetupEvent(bl);
            return;
        }
        catch (CommandException commandException) {
            Timber.e(commandException, "Failed to write profile for impedance", new Object[0]);
            return;
        }
    }

    private void changeWifi(String string2, String string3) {
        this.handler.postDelayed(QBStepOnSettingHostFragment$$Lambda$7.lambdaFactory$(this, string2, string3), TimeUnit.SECONDS.toMillis(1L));
    }

    private boolean checkWifiConfig(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return false;
        }
        try {
            boolean bl = new JSONObject(string2).optString("wifi", "0").equals("1");
            return bl;
        }
        catch (JSONException jSONException) {
            return false;
        }
    }

    private void checkWifiState(int n) {
        ++this.wifiStateChecks;
        Timber.d("checkWifiState - %d, wifiStateChecks - %d", n, this.wifiStateChecks);
        if (n == 7) {
            Toast.makeText((Context)this.getActivity(), (CharSequence)"Wifi not connected", (int)1).show();
        }
        if (n == 2 && !this.isWifiSecure || n == 4 && this.isWifiSecure || n == 6) {
            this.changeFragment(QBOnboardingReadyFragment.newInstance(false));
            return;
        }
        if (this.wifiStateChecks < 5) {
            this.readWifiStateDelayed();
            return;
        }
        Toast.makeText((Context)this.getActivity(), (CharSequence)"Wifi not connected", (int)1).show();
        this.wifiConfigFailed();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void enableUpdateFirmware(String string2) {
        if (!this.checkWifiConfig(string2)) {
            this.changeFragment(QBFirmwareUpdateErrorFragment.newInstance(1));
            return;
        } else {
            Button button;
            string2 = this.getArguments().getString("com.getqardio.android.argument.FIRMWARE_VERSION");
            if (this.getView() == null || (button = (Button)this.getView().findViewById(2131821237)) == null) return;
            {
                button.setEnabled(true);
                button.setOnClickListener(QBStepOnSettingHostFragment$$Lambda$8.lambdaFactory$(this, string2));
                return;
            }
        }
    }

    private void executeAction(String string2) {
        Timber.d("executeAction - %d, userProfiles - %s", this.getActionId(), string2);
        switch (this.getActionId()) {
            default: {
                return;
            }
            case 5: {
                this.startFirmwareUpdate();
                return;
            }
            case 0: {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.qardio.base.QB_USER_CONFIG_WRITTEN");
                this.broadcastManager.registerReceiver(this.userConfigWrittenReceiver, intentFilter);
                this.changeImpedance(string2);
                return;
            }
            case 4: {
                string2 = new IntentFilter();
                this.broadcastManager.registerReceiver(this.configWifiReceiver, (IntentFilter)string2);
                string2 = this.getArguments();
                this.isWifiSecure = string2.getBoolean("com.getqardio.android.argument.WIFI_IS_SECURE");
                this.changeWifi(string2.getString("com.getqardio.android.argument.WIFIAP"), string2.getString("com.getqardio.android.argument.WIFIPASSWORD"));
                return;
            }
            case 2: {
                this.broadcastManager.registerReceiver(this.userConfigWrittenReceiver, new IntentFilter("com.qardio.base.QB_USER_CONFIG_WRITTEN"));
                this.changeGoal(string2);
                return;
            }
            case 3: 
        }
        string2 = new IntentFilter();
        string2.addAction("com.getqardio.android.action.RESET_ASSOCIATION");
        string2.addAction("com.qardio.base.QB_RANDOM_WRITTEN");
        this.broadcastManager.registerReceiver(this.resetReceiver, (IntentFilter)string2);
        this.resetBase();
    }

    private void exitConfigMode() {
        Timber.d("exitConfigMode", new Object[0]);
        if (FirmwareUpdateHelper.getCurrentQBFirmwareUpdate((Context)this.getActivity()).isFirmwareNewerOrEqual18()) {
            Timber.d("disableConfigurationMode", new Object[0]);
            this.qardioBaseManager.disableConfigurationMode();
        }
    }

    private int getActionId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.ACTION_ID")) {
            return bundle.getInt("com.getqardio.android.argument.ACTION_ID");
        }
        return -1;
    }

    private void handleConnection() {
        Timber.d("handleConnection", new Object[0]);
        this.qardioBaseManager.enableStateNotifications();
        new Handler().postDelayed(QBStepOnSettingHostFragment$$Lambda$2.lambdaFactory$(this), 1000L);
    }

    private void handleFirmwareUpgradeResult(int n) {
        String string2 = this.getArguments().getString("com.getqardio.android.argument.FIRMWARE_VERSION");
        if (n == 0) {
            if (this.getActivity() != null && !TextUtils.isEmpty((CharSequence)this.deviceSerialNumber)) {
                this.handleSoftwareVersion(string2);
                QardioBaseDeviceAnalyticsTracker.trackFirmwareUpdated((Context)this.getActivity(), string2);
            }
            this.changeFragment(QBFirmwareUpdateSuccessFragment.newInstance(string2));
            return;
        }
        this.changeFragment(QBFirmwareUpdateErrorFragment.newInstance(4));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleNewBaseState(int n) {
        block6: {
            block5: {
                Timber.d("handleNewBaseState - %d", n);
                if (this.wasReseted && n == 12) break block5;
                if (n == 12 && this.getActivity() != null) {
                    Toast.makeText((Context)this.getActivity(), (int)2131362364, (int)1).show();
                    this.getActivity().finish();
                }
                if (n == 1 && !this.isProgressing) {
                    this.isProgressing = true;
                    return;
                }
                if (n != 1 && !this.isProgressing && this.retries <= 5) break block6;
            }
            return;
        }
        this.readStateDelayed();
        ++this.retries;
    }

    public static QBStepOnSettingHostFragment newInstanceForFirmwareUpdate(String object, String string2, String string3) {
        Bundle bundle = new Bundle(5);
        bundle.putInt("com.getqardio.android.argument.ACTION_ID", 5);
        bundle.putString("com.getqardio.android.argument.IP_ADDRESS", (String)object);
        bundle.putString("com.getqardio.android.argument.FIRMWARE_VERSION", string2);
        bundle.putString("com.getqardio.android.argument.FIRMWARE_DESCRIPTION", string3);
        bundle.putInt("com.getqardio.android.argument.FRAGMENT", 1);
        object = new QBStepOnSettingHostFragment();
        object.setArguments(bundle);
        return object;
    }

    public static QBStepOnSettingHostFragment newInstanceForGoal(float f, float f2) {
        Bundle bundle = new Bundle(3);
        bundle.putInt("com.getqardio.android.argument.ACTION_ID", 2);
        bundle.putFloat("com.getqardio.android.argument.GOAL", f);
        bundle.putFloat("com.getqardio.android.argument.RATE", f2);
        QBStepOnSettingHostFragment qBStepOnSettingHostFragment = new QBStepOnSettingHostFragment();
        qBStepOnSettingHostFragment.setArguments(bundle);
        return qBStepOnSettingHostFragment;
    }

    public static QBStepOnSettingHostFragment newInstanceForReset() {
        Bundle bundle = new Bundle(1);
        bundle.putInt("com.getqardio.android.argument.ACTION_ID", 3);
        QBStepOnSettingHostFragment qBStepOnSettingHostFragment = new QBStepOnSettingHostFragment();
        qBStepOnSettingHostFragment.setArguments(bundle);
        return qBStepOnSettingHostFragment;
    }

    public static QBStepOnSettingHostFragment newInstanceForSettings(boolean bl) {
        Bundle bundle = new Bundle(2);
        bundle.putInt("com.getqardio.android.argument.ACTION_ID", 0);
        bundle.putBoolean("com.getqardio.android.argument.HAPTIC", bl);
        QBStepOnSettingHostFragment qBStepOnSettingHostFragment = new QBStepOnSettingHostFragment();
        qBStepOnSettingHostFragment.setArguments(bundle);
        return qBStepOnSettingHostFragment;
    }

    public static QBStepOnSettingHostFragment newInstanceForWifi(String object, String string2, boolean bl) {
        Bundle bundle = new Bundle(4);
        bundle.putInt("com.getqardio.android.argument.ACTION_ID", 4);
        bundle.putString("com.getqardio.android.argument.WIFIAP", (String)object);
        bundle.putString("com.getqardio.android.argument.WIFIPASSWORD", string2);
        bundle.putBoolean("com.getqardio.android.argument.WIFI_IS_SECURE", bl);
        object = new QBStepOnSettingHostFragment();
        object.setArguments(bundle);
        return object;
    }

    private void readStateDelayed() {
        this.handler.postDelayed(QBStepOnSettingHostFragment$$Lambda$3.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(7L));
    }

    private void readWifiStateDelayed() {
        this.handler.postDelayed(QBStepOnSettingHostFragment$$Lambda$1.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(5L));
    }

    private void resetBase() {
        if (FirmwareUpdateHelper.getCurrentQBFirmwareUpdate((Context)this.getActivity()).isFirmwareNewerOrEqual18()) {
            this.qardioBaseManager.resetBase();
            return;
        }
        this.qardioBaseManager.legacyReset();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void scanAndConnect() {
        Timber.d("scanAndConnect", new Object[0]);
        if (this.getActionId() == 3) {
            this.qardioBaseManager.scanAndConnect();
            return;
        } else {
            Activity activity = this.getActivity();
            if (activity == null) return;
            {
                String string2 = DataHelper.DeviceAddressHelper.getDeviceAddress((Context)activity, CustomApplication.getApplication().getCurrentUserId());
                this.qardioBaseManager.scanAndConnect(string2);
                return;
            }
        }
    }

    private void setBaseSettings(String string2) {
        Activity activity = this.getActivity();
        if (activity != null) {
            QardioBaseSettingsHelper.setCurrentBaseSettings((Context)activity, JSONParser.parseQardioBaseSettings(string2));
        }
    }

    private boolean shouldContinue() {
        if (this.configDone) {
            this.changeFragment(QBOnSettingsReadyFragment.newInstance(false));
            return false;
        }
        return true;
    }

    private void showResetSuccess() {
        if (this.getActivity() != null && this.isAdded()) {
            this.showResetSuccessDialog();
        }
    }

    private void showResetSuccessDialog() {
        this.changeFragment(QBOnSettingsReadyFragment.newInstance(true, 2131362041));
    }

    private void startFirmwareUpdate() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.QB_FIRMWARE_UPDATED");
        intentFilter.addAction("com.qardio.base.QB_WIFI_CONFIG");
        this.broadcastManager.registerReceiver(this.firmwareUpdateReceiver, new IntentFilter(intentFilter));
        this.handler.postDelayed(QBStepOnSettingHostFragment$$Lambda$6.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(2L));
    }

    private void trackHapticSetupEvent(boolean bl) {
        if (bl) {
            QardioBaseDeviceAnalyticsTracker.trackEnabledHaptic((Context)this.getActivity());
        }
    }

    private void updateFirmware() {
        String string2 = this.getArguments().getString("com.getqardio.android.argument.IP_ADDRESS");
        this.qardioBaseManager.doFirmwareUpdate(string2);
    }

    private void wifiConfigFailed() {
        Toast.makeText((Context)this.getActivity(), (int)2131362420, (int)1).show();
        this.getActivity().finish();
    }

    @Override
    public Profile getProfile() {
        return this.profile;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void handleConnectionError() {
        ++this.connectionRetries;
        if (this.connectionRetries < 10) {
            this.qardioBaseManager.disconnect();
            this.handler.postDelayed(QBStepOnSettingHostFragment$$Lambda$5.lambdaFactory$(this), TimeUnit.MILLISECONDS.toMillis(200L));
            return;
        } else {
            if (this.getActivity() == null) return;
            {
                this.getActivity().finish();
                return;
            }
        }
    }

    public void handleSoftwareVersion(String object) {
        Timber.d("handleSoftwareVersion - %s", object);
        object = FirmwareUpdateHelper.parseBaseVersion((String)object);
        FirmwareUpdateHelper.setCurrentQBFirmwareVersion((Context)this.getActivity(), this.deviceSerialNumber, (FirmwareDescription)object);
    }

    /* synthetic */ void lambda$changeWifi$4(String string2, String string3) {
        if (this.getActivity() != null) {
            this.qardioBaseManager.writeWifiConfig(Utils.createQardioBaseWifiConfig(string2, string3, true));
        }
    }

    /* synthetic */ void lambda$enableUpdateFirmware$5(String string2, View view) {
        this.changeFragment(QBFirmwareUpdateProgress.newInstance(string2));
        this.updateFirmware();
    }

    /* synthetic */ void lambda$handleConnection$1() {
        this.qardioBaseManager.enableConfigurationMode();
    }

    /* synthetic */ void lambda$readStateDelayed$2() {
        if (!this.isProgressing) {
            Timber.d("readState", new Object[0]);
            this.qardioBaseManager.readState();
        }
    }

    /* synthetic */ void lambda$readWifiStateDelayed$0() {
        this.qardioBaseManager.readWifiState();
    }

    /* synthetic */ void lambda$startFirmwareUpdate$3() {
        this.qardioBaseManager.readWifiConfig();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362050);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2) {
            if (n2 == -1) {
                this.configDone = true;
            } else {
                intent = this.getActivity();
                if (intent != null) {
                    intent.finish();
                }
            }
        }
        if (n != 1) return;
        {
            if (n2 == -1) {
                this.configDone = true;
                return;
            } else {
                intent = this.getActivity();
                if (intent == null) return;
                {
                    intent.finish();
                    return;
                }
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.qardioBaseManager = new QardioBaseManager((Context)this.getActivity());
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        return DataHelper.ProfileHelper.getProfileLoader((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), DataHelper.ProfileHelper.PROFILE_SCREEN_PROJECTION);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130968800, viewGroup, false);
    }

    @Override
    public void onDoneClick() {
        this.exitConfigMode();
        Activity activity = this.getActivity();
        if (activity != null) {
            activity.finish();
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
        this.qardioBaseManager.stopScan();
        this.qardioBaseManager.disconnect();
        this.broadcastManager.unregisterReceiver(this.baseReceiver);
        this.broadcastManager.unregisterReceiver(this.resetReceiver);
        this.broadcastManager.unregisterReceiver(this.userConfigWrittenReceiver);
        this.broadcastManager.unregisterReceiver(this.firmwareUpdateReceiver);
        this.broadcastManager.unregisterReceiver(this.userProfileReceiver);
        this.unregisterForWifiActions(this.broadcastManager);
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.DEVICE_SERIAL");
        intentFilter.addAction("com.qardio.base.CONNECTED");
        intentFilter.addAction("com.qardio.base.SOFTWARE_VERSION");
        intentFilter.addAction("com.qardio.base.DISCONNECTED");
        intentFilter.addAction("com.qardio.base.CONFIGURATION_MODE");
        intentFilter.addAction("com.qardio.base.STATE");
        intentFilter.addAction("com.qardio.base.QB_CONNECTION_ERROR");
        intentFilter.addAction("com.qardio.base.QB_RESET");
        this.broadcastManager.registerReceiver(this.baseReceiver, intentFilter);
        this.broadcastManager.registerReceiver(this.userProfileReceiver, new IntentFilter("com.qardio.base.QB_USER_CONFIG"));
        this.registerForWifiActions(this.broadcastManager);
        if (this.shouldContinue()) {
            this.handler.postDelayed(QBStepOnSettingHostFragment$$Lambda$4.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(1L));
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
        view = this.getArguments();
        if (view.containsKey("com.getqardio.android.argument.FRAGMENT") && view.getInt("com.getqardio.android.argument.FRAGMENT") == 1) {
            this.changeFragment(QBInstallFirmwareUpdateFragment.newInstance(view.getString("com.getqardio.android.argument.FIRMWARE_VERSION"), view.getString("com.getqardio.android.argument.FIRMWARE_DESCRIPTION")));
            return;
        }
        this.changeFragment(QBSettingsStepOnStateFragment.newInstance(2131362373, 2131362372, 2130837594, true));
    }

    public void registerForWifiActions(LocalBroadcastManager localBroadcastManager) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.QB_WIFI_CONFIG_WRITTEN");
        intentFilter.addAction("com.qardio.base.QB_WIFI_STATE");
        localBroadcastManager.registerReceiver(this.configWifiReceiver, intentFilter);
    }

    public void unregisterForWifiActions(LocalBroadcastManager localBroadcastManager) {
        localBroadcastManager.unregisterReceiver(this.configWifiReceiver);
    }

}

