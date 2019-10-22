/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Handler
 *  org.json.JSONObject
 */
package com.getqardio.android.handler;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.baseble.QardioBaseManager;
import com.getqardio.android.datamodel.FirmwareDescription;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.exceptions.CommandException;
import com.getqardio.android.handler.QBOnboardingManager$$Lambda$1;
import com.getqardio.android.handler.QBOnboardingManager$$Lambda$2;
import com.getqardio.android.handler.QBOnboardingManager$$Lambda$3;
import com.getqardio.android.handler.QBOnboardingManager$$Lambda$4;
import com.getqardio.android.handler.QBOnboardingManager$$Lambda$5;
import com.getqardio.android.handler.QBOnboardingManager$$Lambda$6;
import com.getqardio.android.handler.QBOnboardingManager$$Lambda$7;
import com.getqardio.android.handler.QBOnboardingManager$$Lambda$8;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.FirmwareUpdateHelper;
import com.getqardio.android.ui.activity.QBOnboardingActivity;
import com.getqardio.android.util.BaseUserUtil;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wifi.WifiAp;
import com.getqardio.android.utils.wifi.WifiSecurityState;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import timber.log.Timber;

public class QBOnboardingManager {
    private final Activity activity;
    private BaseCommHandler baseCommHandler;
    private BroadcastReceiver baseConfigReceiver;
    private String baseSerialNumber;
    private BroadcastReceiver baseWifiReceiver;
    private int countReadWifiState = 0;
    private Handler handler = new Handler();
    private QardioBaseDevice.BaseMode mode;
    private int noProfiles;
    private Profile profile;
    private int progress;
    private int progressStepPercentage;
    private QardioBaseManager qardioBaseManager;
    private boolean secureWifi;
    private boolean skipWifiConfig;
    private WifiAp wifiAp;
    private boolean wifiConnected;
    private RefreshWifiFromBaseListener wifiFromBaseListener;
    private String wifiPassword;

    public QBOnboardingManager(Activity activity, QardioBaseManager qardioBaseManager) {
        this.baseConfigReceiver = new BroadcastReceiver(){

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context object, Intent object2) {
                String string2 = object2.getAction();
                Timber.d("BroadcastReceiver onReceive action - %s", string2);
                int n = -1;
                switch (string2.hashCode()) {
                    case 1712737337: {
                        if (!string2.equals("com.qardio.base.DEVICE_SERIAL")) break;
                        n = 0;
                        break;
                    }
                    case -165661596: {
                        if (!string2.equals("com.qardio.base.SOFTWARE_VERSION")) break;
                        n = 1;
                        break;
                    }
                    case -1094350772: {
                        if (!string2.equals("com.qardio.base.QB_USER_CONFIG")) break;
                        n = 2;
                        break;
                    }
                    case -1611280380: {
                        if (!string2.equals("com.qardio.base.QB_USER_CONFIG_WRITTEN")) break;
                        n = 3;
                        break;
                    }
                    case -138496091: {
                        if (!string2.equals("com.qardio.base.QB_RANDOM_WRITTEN")) break;
                        n = 4;
                        break;
                    }
                    case 421757567: {
                        if (!string2.equals("com.qardio.base.DISCONNECTED")) break;
                        n = 5;
                        break;
                    }
                    case -53910355: {
                        if (!string2.equals("com.qardio.base.STATE")) break;
                        n = 6;
                        break;
                    }
                    case -1160944514: {
                        if (!string2.equals("com.qardio.base.QB_ERROR")) break;
                        n = 7;
                        break;
                    }
                    case 1097085014: {
                        if (!string2.equals("com.qardio.base.request.SET_TIMESTAMP")) break;
                        n = 8;
                        break;
                    }
                }
                switch (n) {
                    case 0: {
                        QBOnboardingManager.this.baseSerialNumber = object2.getStringExtra("com.qardio.base.DATA");
                        Timber.d("baseSerialNumber - %s", QBOnboardingManager.this.baseSerialNumber);
                        QBOnboardingManager.this.readSoftwareVersionDelay();
                        QBOnboardingManager.this.baseCommHandler.setProgress(QBOnboardingManager.this.progress, BaseCommHandler.Page.CONNECTING_TO_BASE);
                        return;
                    }
                    case 1: {
                        object = object2.getStringExtra("com.qardio.base.DATA");
                        QBOnboardingManager.this.handleSoftwareVersion((String)object);
                        if (QBOnboardingManager.this.skipWifiConfig) {
                            QBOnboardingManager.this.readUserProfilesDelayed();
                            QBOnboardingManager.this.baseCommHandler.setProgress(QBOnboardingManager.this.progress, BaseCommHandler.Page.SETTING_UP_PROFILE);
                            return;
                        }
                        QBOnboardingManager.this.writeWifiDelay(Utils.createQardioBaseWifiConfig(QBOnboardingManager.access$700((QBOnboardingManager)QBOnboardingManager.this).ssid, QBOnboardingManager.this.wifiPassword, true));
                        QBOnboardingManager.this.baseCommHandler.setProgress(QBOnboardingManager.this.progress, BaseCommHandler.Page.SETTING_UP_WIFI);
                        return;
                    }
                    case 2: {
                        object = object2.getStringExtra("com.qardio.base.DATA");
                        QBOnboardingManager.this.progress = QBOnboardingManager.this.progress + QBOnboardingManager.this.progressStepPercentage;
                        QBOnboardingManager.this.baseCommHandler.setProgress(QBOnboardingManager.this.progress, BaseCommHandler.Page.SETTING_UP_PROFILE);
                        QBOnboardingManager.this.handleUserProfiles((String)object);
                        return;
                    }
                    case 3: {
                        QBOnboardingManager.this.writeUniqueId();
                        QBOnboardingManager.this.writeTimestamp();
                        QBOnboardingManager.this.baseCommHandler.setProgress(QBOnboardingManager.this.progress, BaseCommHandler.Page.SETTING_UP_PROFILE);
                        return;
                    }
                    case 4: {
                        object = object2.getStringExtra("com.qardio.base.DATA");
                        QBOnboardingManager.this.storeUniqueId((String)object);
                        if (QBOnboardingManager.this.isVersion18OrGreater()) {
                            QBOnboardingManager.this.writeDisableConfigurationModeDelayed();
                            if (QBOnboardingManager.this.activity != null && QBOnboardingManager.this.activity instanceof QBOnboardingActivity) {
                                ((QBOnboardingActivity)QBOnboardingManager.this.activity).setNotToDisconnect(true);
                            }
                        }
                        QBOnboardingManager.this.onboardingFinished((Context)QBOnboardingManager.this.activity);
                        Timber.d("DONE", new Object[0]);
                        return;
                    }
                    case 5: {
                        QBOnboardingManager.this.handleDisconnect();
                        return;
                    }
                    case 6: {
                        n = object2.getIntExtra("com.qardio.base.DATA", 1);
                        Timber.d("state - %d", n);
                        if (n != 1 && !QBOnboardingManager.this.isVersion18OrGreater()) {
                            QBOnboardingManager.this.baseCommHandler.onboardingFailed(BaseCommHandler.FailReason.ERROR_OTHER);
                            return;
                        }
                        if (QBOnboardingManager.this.isVersion18OrGreater() && n == 0) {
                            QBOnboardingManager.this.onboardingFinished(object);
                            return;
                        }
                    }
                    default: {
                        return;
                    }
                    case 7: {
                        object = object2.getStringExtra("com.qardio.base.QB_ERROR_MSG");
                        object2 = object2.getIntExtra("com.qardio.base.QB_ERROR_CODE", -1);
                        Timber.d("Connection error: %s, code - %d", object, object2);
                        QBOnboardingManager.this.handleErrorCode((Integer)object2);
                        return;
                    }
                    case 8: 
                }
                QBOnboardingManager.this.writeTimestamp();
            }
        };
        this.baseWifiReceiver = new BroadcastReceiver(){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            public void onReceive(Context var1_1, Intent var2_2) {
                block16: {
                    var4_3 = var2_2 /* !! */ .getAction();
                    Timber.d("baseWifiReceiver onReceive action - %s", new Object[]{var4_3});
                    switch (var4_3.hashCode()) {
                        case -266285190: {
                            if (!var4_3.equals("com.qardio.base.QB_WIFI_CONFIG_WRITTEN")) ** GOTO lbl16
                            var3_4 = 0;
                            break block16;
                        }
                        case 1856640497: {
                            if (!var4_3.equals("com.qardio.base.QB_WIFI_STATE")) ** GOTO lbl16
                            var3_4 = 1;
                            break block16;
                        }
                        case 2114610836: {
                            if (!var4_3.equals("com.qardio.base.QB_WIFI_SCAN_RESULTS")) ** GOTO lbl16
                            var3_4 = 2;
                            break block16;
                        }
lbl16:
                        // 4 sources
                        default: {
                            ** GOTO lbl-1000
                        }
                        case 1356099009: 
                    }
                    if (var4_3.equals("com.qardio.base.QB_FIRMWARE_UPDATED")) {
                        var3_4 = 3;
                    } else lbl-1000:
                    // 2 sources
                    {
                        var3_4 = -1;
                    }
                }
                switch (var3_4) {
                    default: {
                        return;
                    }
                    case 0: {
                        QBOnboardingManager.access$2000(QBOnboardingManager.this);
                        return;
                    }
                    case 1: {
                        QBOnboardingManager.access$300(QBOnboardingManager.this).setProgress(QBOnboardingManager.access$200(QBOnboardingManager.this), BaseCommHandler.Page.CONNECTING_WIFI);
                        var3_4 = var2_2 /* !! */ .getIntExtra("com.qardio.base.DATA", 0);
                        Timber.d("state - %d", new Object[]{var3_4});
                        QBOnboardingManager.access$2100(QBOnboardingManager.this, var3_4);
                        return;
                    }
                    case 2: {
                        var1_1 /* !! */  = var2_2 /* !! */ .getStringExtra("com.qardio.base.DATA");
                        Timber.d("scanResult - %s", new Object[]{var1_1 /* !! */ });
                        if (QBOnboardingManager.access$2200(QBOnboardingManager.this) != null) {
                            QBOnboardingManager.access$2200(QBOnboardingManager.this).sendNewListFromBase(QardioBaseUtils.convertWifiResult((String)var1_1 /* !! */ ));
                            return;
                        }
                        QBOnboardingManager.access$300(QBOnboardingManager.this).setProgress(QBOnboardingManager.access$200(QBOnboardingManager.this), BaseCommHandler.Page.CONNECTING_WIFI);
                        QBOnboardingManager.access$2300(QBOnboardingManager.this, (String)var1_1 /* !! */ );
                        return;
                    }
                    case 3: 
                }
                var3_4 = var2_2 /* !! */ .getIntExtra("com.qardio.base.DATA", -1);
                Timber.d("updateResult - %d", new Object[]{var3_4});
                if (var3_4 == 0) {
                    var2_2 /* !! */  = FirmwareUpdateHelper.getServerQBFirmwareVersion((Context)QBOnboardingManager.access$1600(QBOnboardingManager.this));
                    FirmwareUpdateHelper.setCurrentQBFirmwareVersion(var1_1 /* !! */ , QBOnboardingManager.access$000(QBOnboardingManager.this), (FirmwareDescription)var2_2 /* !! */ );
                    QBOnboardingManager.access$1700(QBOnboardingManager.this, var1_1 /* !! */ );
                    Timber.d("DONE", new Object[0]);
                    QBOnboardingManager.this.zeroing();
                    return;
                }
                QBOnboardingManager.access$300(QBOnboardingManager.this).upgradingFailed();
            }
        };
        this.activity = activity;
        this.qardioBaseManager = qardioBaseManager;
    }

    static /* synthetic */ void access$2000(QBOnboardingManager qBOnboardingManager) {
        qBOnboardingManager.readWifiStateDelayed();
    }

    static /* synthetic */ void access$2100(QBOnboardingManager qBOnboardingManager, int n) {
        qBOnboardingManager.checkWifiState(n);
    }

    static /* synthetic */ RefreshWifiFromBaseListener access$2200(QBOnboardingManager qBOnboardingManager) {
        return qBOnboardingManager.wifiFromBaseListener;
    }

    static /* synthetic */ void access$2300(QBOnboardingManager qBOnboardingManager, String string2) {
        qBOnboardingManager.showBaseWifiList(string2);
    }

    static /* synthetic */ WifiAp access$700(QBOnboardingManager qBOnboardingManager) {
        return qBOnboardingManager.wifiAp;
    }

    private void checkBirthDate() {
        if (this.profile.dob == null) {
            Date date = new Date();
            date.setYear(date.getYear() - 35);
            this.profile.dob = date;
        }
    }

    private void checkWifiState(int n) {
        ++this.countReadWifiState;
        Timber.d("checkWifiState countReadWifiState - %d", this.countReadWifiState);
        if (n == 7) {
            this.wifiConnected = false;
        }
        if (n == 2 && !this.secureWifi || n == 4 && this.secureWifi || n == 6) {
            this.wifiConnected = true;
            this.countReadWifiState = 0;
            FirmwareUpdateHelper.getCurrentQBFirmwareUpdate((Context)this.activity);
            this.readUserProfilesDelayed();
            this.baseCommHandler.setProgress(this.progress, BaseCommHandler.Page.SETTING_UP_PROFILE);
            return;
        }
        if (n == 2 && this.countReadWifiState < 5) {
            this.readWifiStateDelayed();
            return;
        }
        this.readWifiBaseScanDelayed();
    }

    private void handleDisconnect() {
        this.baseCommHandler.onboardingFailed(BaseCommHandler.FailReason.ERROR_OTHER);
    }

    private void handleErrorCode(Integer n) {
        switch (n) {
            default: {
                return;
            }
            case 14: 
        }
        this.baseCommHandler.onboardingFailed(BaseCommHandler.FailReason.ERROR_TO_USERS_LIMIT_REACHED);
    }

    private FirmwareDescription handleSoftwareVersion(String object) {
        Timber.d("handleSoftwareVersion - %s", object);
        object = FirmwareUpdateHelper.parseBaseVersion((String)object);
        if (object != null) {
            FirmwareUpdateHelper.setCurrentQBFirmwareVersion((Context)this.activity, this.baseSerialNumber, (FirmwareDescription)object);
        }
        return object;
    }

    private void handleUserProfiles(String string2) {
        int n;
        Timber.d("handleUserProfiles - %s", string2);
        this.noProfiles = QardioBaseUtils.noProfiles(string2);
        int n2 = n = -1;
        if (this.profile != null) {
            n2 = n;
            if (this.profile.refId != null) {
                n2 = QardioBaseUtils.indexOf(string2, this.profile.refId);
            }
        }
        this.sendUserProfile(n2, this.noProfiles);
    }

    private boolean isVersion18OrGreater() {
        return FirmwareUpdateHelper.getCurrentQBFirmwareUpdate((Context)this.activity).isFirmwareNewerOrEqual18();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onboardingFinished(Context object) {
        QardioBaseDeviceAnalyticsTracker.trackSetupExtraQbProfile((Context)object);
        this.baseCommHandler.setProgress(100, BaseCommHandler.Page.SETUP_COMPLETED);
        object = this.baseCommHandler;
        boolean bl = !this.wifiConnected;
        object.onboardingFinished(bl);
    }

    private void readSoftwareVersionDelay() {
        this.progress += this.progressStepPercentage;
        Timber.d("readSoftwareVersionDelay", new Object[0]);
        this.handler.postDelayed(QBOnboardingManager$$Lambda$4.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(1L));
    }

    private void readUserProfilesDelayed() {
        this.progress += this.progressStepPercentage;
        Timber.d("readUserProfilesDelayed", new Object[0]);
        this.handler.postDelayed(QBOnboardingManager$$Lambda$5.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(1L));
    }

    private void readWifiBaseScanDelayed() {
        Timber.d("readWifiBaseScanDelayed", new Object[0]);
        this.handler.postDelayed(QBOnboardingManager$$Lambda$3.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(2L));
    }

    private void readWifiStateDelayed() {
        Timber.d("wifiStateDelayed", new Object[0]);
        this.handler.postDelayed(QBOnboardingManager$$Lambda$2.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(5L));
    }

    private void sendUserProfile(int n, int n2) {
        Timber.d("sendUserProfile index - %d, total - %d", n, n2);
        String string2 = CustomApplication.getApplication().getCurrentUserToken().replace("-", "");
        this.checkBirthDate();
        try {
            this.writeUserProfileDelay(BaseUserUtil.createOnboardCommand(QardioBaseUtils.baseUserFromProfile(this.profile, string2), QardioBaseUtils.maskFromMode(this.mode), QardioBaseUtils.impedanceFromMode(this.mode), true, n, n2));
            return;
        }
        catch (CommandException commandException) {
            Timber.e(commandException, "Failed to create Add command for profiles", new Object[0]);
            return;
        }
    }

    private void showBaseWifiList(String string2) {
        this.baseCommHandler.goToWifiList(string2);
    }

    private void storeUniqueId(String string2) {
        long l = CustomApplication.getApplication().getCurrentUserId();
        DataHelper.DeviceIdHelper.setDeviceId((Context)this.activity, l, string2);
        DataHelper.DeviceSnHelper.setDeviceSn((Context)this.activity, l, this.baseSerialNumber);
        if (DataHelper.DeviceAssociationsHelper.deleteDeviceAssociation((Context)this.activity, l, this.baseSerialNumber)) {
            new Handler().postDelayed(QBOnboardingManager$$Lambda$1.lambdaFactory$(this, l, string2), 500L);
            return;
        }
        DataHelper.DeviceAssociationsHelper.addDeviceAssociation((Context)this.activity, l, string2, this.baseSerialNumber, this.baseCommHandler.getDeviceAddress());
    }

    private void writeTimestamp() {
        Timber.d("writeTimestamp", new Object[0]);
        this.qardioBaseManager.writeTimestamp(System.currentTimeMillis());
    }

    private void writeUniqueId() {
        Timber.d("writeUniqueId", new Object[0]);
        this.progress += this.progressStepPercentage;
        this.qardioBaseManager.writeUniqueId(false);
    }

    private void writeUserProfileDelay(JSONObject jSONObject) {
        this.progress += this.progressStepPercentage;
        Timber.d("writeUserProfileDelay - %s", jSONObject.toString());
        this.handler.postDelayed(QBOnboardingManager$$Lambda$6.lambdaFactory$(this, jSONObject), TimeUnit.SECONDS.toMillis(1L));
    }

    private void writeWifiDelay(JSONObject jSONObject) {
        this.progress += this.progressStepPercentage;
        Timber.d("writeWifiDelay - %s", jSONObject.toString());
        this.handler.postDelayed(QBOnboardingManager$$Lambda$7.lambdaFactory$(this, jSONObject), TimeUnit.SECONDS.toMillis(1L));
    }

    /* synthetic */ void lambda$readSoftwareVersionDelay$3() {
        this.qardioBaseManager.readSoftwareVersion();
    }

    /* synthetic */ void lambda$readUserProfilesDelayed$4() {
        this.qardioBaseManager.readUserProfiles();
    }

    /* synthetic */ void lambda$readWifiBaseScanDelayed$2() {
        this.qardioBaseManager.readWifiScan();
    }

    /* synthetic */ void lambda$readWifiStateDelayed$1() {
        this.qardioBaseManager.readWifiState();
    }

    /* synthetic */ void lambda$storeUniqueId$0(long l, String string2) {
        if (this.activity != null && !this.activity.isDestroyed()) {
            DataHelper.DeviceAssociationsHelper.addDeviceAssociation((Context)this.activity, l, string2, this.baseSerialNumber, this.baseCommHandler.getDeviceAddress());
        }
    }

    /* synthetic */ void lambda$writeDisableConfigurationModeDelayed$7() {
        this.qardioBaseManager.disableConfigurationMode();
    }

    /* synthetic */ void lambda$writeUserProfileDelay$5(JSONObject jSONObject) {
        this.qardioBaseManager.writeProfile(jSONObject);
    }

    /* synthetic */ void lambda$writeWifiDelay$6(JSONObject jSONObject) {
        this.qardioBaseManager.writeWifiConfig(jSONObject);
    }

    public void readState() {
        Timber.d("readState", new Object[0]);
        this.qardioBaseManager.readState();
    }

    public void registerForActions() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.DISCONNECTED");
        intentFilter.addAction("com.qardio.base.DEVICE_SERIAL");
        intentFilter.addAction("com.qardio.base.SOFTWARE_VERSION");
        intentFilter.addAction("com.qardio.base.STATE");
        intentFilter.addAction("com.qardio.base.QB_USER_CONFIG");
        intentFilter.addAction("com.qardio.base.QB_USER_CONFIG_WRITTEN");
        intentFilter.addAction("com.qardio.base.QB_RANDOM_WRITTEN");
        intentFilter.addAction("com.qardio.base.QB_ERROR");
        intentFilter.addAction("com.qardio.base.QB_TIMESTAMP");
        LocalBroadcastManager.getInstance((Context)this.activity).registerReceiver(this.baseConfigReceiver, intentFilter);
    }

    public void registerForWifiActions() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qardio.base.QB_WIFI_CONFIG");
        intentFilter.addAction("com.qardio.base.QB_WIFI_CONFIG_WRITTEN");
        intentFilter.addAction("com.qardio.base.QB_WIFI_STATE");
        intentFilter.addAction("com.qardio.base.QB_WIFI_SCAN_RESULTS");
        intentFilter.addAction("com.qardio.base.QB_FIRMWARE_UPDATED");
        LocalBroadcastManager.getInstance((Context)this.activity).registerReceiver(this.baseWifiReceiver, intentFilter);
    }

    public void setBaseCommHandler(BaseCommHandler baseCommHandler) {
        this.baseCommHandler = baseCommHandler;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setScanWifiFromBaseListener(RefreshWifiFromBaseListener refreshWifiFromBaseListener) {
        this.wifiFromBaseListener = refreshWifiFromBaseListener;
        this.qardioBaseManager.readWifiScan();
    }

    public void setSelectedMode(QardioBaseDevice.BaseMode baseMode) {
        this.mode = baseMode;
    }

    public void setSkipWifiConfig() {
        this.skipWifiConfig = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setWifiAndPasswordFromPhone(WifiAp wifiAp, String string2) {
        this.wifiAp = wifiAp;
        boolean bl = wifiAp.sec == WifiSecurityState.SECURE;
        this.secureWifi = bl;
        this.wifiPassword = string2;
    }

    public void startOnboarding() {
        Timber.d("readSerialNumber", new Object[0]);
        this.baseCommHandler.setProgress(0, BaseCommHandler.Page.CONNECTING_TO_BASE);
        this.qardioBaseManager.readSerialNumber();
        if (this.skipWifiConfig) {
            this.progressStepPercentage = 25;
            return;
        }
        this.progressStepPercentage = 14;
    }

    public void unregisterForActions() {
        LocalBroadcastManager.getInstance((Context)this.activity).unregisterReceiver(this.baseConfigReceiver);
    }

    public void unregisterForWifiActions() {
        LocalBroadcastManager.getInstance((Context)this.activity).unregisterReceiver(this.baseWifiReceiver);
    }

    public void writeDisableConfigurationModeDelayed() {
        Timber.d("writeDisableConfigurationModeDelayed", new Object[0]);
        this.handler.postDelayed(QBOnboardingManager$$Lambda$8.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(1L));
    }

    public void zeroing() {
        Timber.d("zeroing", new Object[0]);
        this.qardioBaseManager.zeroing();
    }

    public static interface BaseCommHandler {
        public String getDeviceAddress();

        public void goToWifiList(String var1);

        public void onboardingFailed(FailReason var1);

        public void onboardingFinished(boolean var1);

        public void setProgress(int var1, Page var2);

        public void upgradingFailed();

        public static enum FailReason {
            ERROR_TO_USERS_LIMIT_REACHED,
            ERROR_OTHER;

        }

        public static enum Page {
            CONNECTING_TO_BASE,
            SETTING_UP_WIFI,
            CONNECTING_WIFI,
            UPDATING_FIRMWARE,
            SETTING_UP_PROFILE,
            SETUP_COMPLETED;

        }

    }

    public static interface RefreshWifiFromBaseListener {
        public void sendNewListFromBase(List<WifiAp> var1);
    }

}

