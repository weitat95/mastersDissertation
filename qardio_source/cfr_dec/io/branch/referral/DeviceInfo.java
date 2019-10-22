/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import io.branch.referral.Defines;
import io.branch.referral.SystemObserver;
import org.json.JSONException;
import org.json.JSONObject;

class DeviceInfo {
    private static DeviceInfo thisInstance_ = null;
    private final String appVersion_;
    private final String brandName_;
    private final String countryCode_;
    private final String hardwareID_;
    private final boolean isHardwareIDReal_;
    private final boolean isWifiConnected_;
    private final String languageCode_;
    private final String localIpAddr_;
    private final String modelName_;
    private final String osName_;
    private final int osVersion_;
    private final String packageName_;
    private final int screenDensity_;
    private final int screenHeight_;
    private final int screenWidth_;

    /*
     * Enabled aggressive block sorting
     */
    private DeviceInfo(boolean bl, SystemObserver systemObserver, boolean bl2) {
        this.hardwareID_ = bl2 ? systemObserver.getUniqueID(true) : systemObserver.getUniqueID(bl);
        this.isHardwareIDReal_ = systemObserver.hasRealHardwareId();
        this.brandName_ = systemObserver.getPhoneBrand();
        this.modelName_ = systemObserver.getPhoneModel();
        DisplayMetrics displayMetrics = systemObserver.getScreenDisplay();
        this.screenDensity_ = displayMetrics.densityDpi;
        this.screenHeight_ = displayMetrics.heightPixels;
        this.screenWidth_ = displayMetrics.widthPixels;
        this.isWifiConnected_ = systemObserver.getWifiConnected();
        this.localIpAddr_ = SystemObserver.getLocalIPAddress();
        this.osName_ = systemObserver.getOS();
        this.osVersion_ = systemObserver.getOSVersion();
        this.packageName_ = systemObserver.getPackageName();
        this.appVersion_ = systemObserver.getAppVersion();
        this.countryCode_ = systemObserver.getISO2CountryCode();
        this.languageCode_ = systemObserver.getISO2LanguageCode();
    }

    public static DeviceInfo getInstance() {
        return thisInstance_;
    }

    public static DeviceInfo getInstance(boolean bl, SystemObserver systemObserver, boolean bl2) {
        if (thisInstance_ == null) {
            thisInstance_ = new DeviceInfo(bl, systemObserver, bl2);
        }
        return thisInstance_;
    }

    public String getAppVersion() {
        return this.appVersion_;
    }

    public String getHardwareID() {
        if (this.hardwareID_.equals("bnc_no_value")) {
            return null;
        }
        return this.hardwareID_;
    }

    public String getOsName() {
        return this.osName_;
    }

    public boolean isHardwareIDReal() {
        return this.isHardwareIDReal_;
    }

    public void updateRequestWithDeviceParams(JSONObject jSONObject) {
        try {
            if (!this.hardwareID_.equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.HardwareID.getKey(), (Object)this.hardwareID_);
                jSONObject.put(Defines.Jsonkey.IsHardwareIDReal.getKey(), this.isHardwareIDReal_);
            }
            if (!this.brandName_.equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.Brand.getKey(), (Object)this.brandName_);
            }
            if (!this.modelName_.equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.Model.getKey(), (Object)this.modelName_);
            }
            jSONObject.put(Defines.Jsonkey.ScreenDpi.getKey(), this.screenDensity_);
            jSONObject.put(Defines.Jsonkey.ScreenHeight.getKey(), this.screenHeight_);
            jSONObject.put(Defines.Jsonkey.ScreenWidth.getKey(), this.screenWidth_);
            jSONObject.put(Defines.Jsonkey.WiFi.getKey(), this.isWifiConnected_);
            if (!this.osName_.equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.OS.getKey(), (Object)this.osName_);
            }
            jSONObject.put(Defines.Jsonkey.OSVersion.getKey(), this.osVersion_);
            if (!TextUtils.isEmpty((CharSequence)this.countryCode_)) {
                jSONObject.put(Defines.Jsonkey.Country.getKey(), (Object)this.countryCode_);
            }
            if (!TextUtils.isEmpty((CharSequence)this.languageCode_)) {
                jSONObject.put(Defines.Jsonkey.Language.getKey(), (Object)this.languageCode_);
            }
            if (!TextUtils.isEmpty((CharSequence)this.localIpAddr_)) {
                jSONObject.put(Defines.Jsonkey.LocalIP.getKey(), (Object)this.localIpAddr_);
            }
            return;
        }
        catch (JSONException jSONException) {
            return;
        }
    }
}

