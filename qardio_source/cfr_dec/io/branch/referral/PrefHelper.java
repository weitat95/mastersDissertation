/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PrefHelper {
    private static boolean BNC_Dev_Debug = false;
    private static boolean BNC_Logging = false;
    private static String Branch_Key = null;
    private static PrefHelper prefHelper_;
    private static JSONObject savedAnalyticsData_;
    private SharedPreferences appSharedPrefs_;
    private Context context_;
    private SharedPreferences.Editor prefsEditor_;
    private JSONObject requestMetadata;

    public PrefHelper() {
    }

    private PrefHelper(Context context) {
        this.appSharedPrefs_ = context.getSharedPreferences("branch_referral_shared_pref", 0);
        this.prefsEditor_ = this.appSharedPrefs_.edit();
        this.context_ = context;
        this.requestMetadata = new JSONObject();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void Debug(String string2, String string3) {
        if (prefHelper_ != null) {
            prefHelper_.log(string2, string3);
            return;
        } else {
            if (!BNC_Dev_Debug && !BNC_Logging) return;
            {
                Log.i((String)string2, (String)string3);
                return;
            }
        }
    }

    private void clearPrefOnBranchKeyChange() {
        String string2 = this.getLinkClickID();
        String string3 = this.getLinkClickIdentifier();
        String string4 = this.getAppLink();
        String string5 = this.getPushIdentifier();
        this.prefsEditor_.clear();
        this.setLinkClickID(string2);
        this.setLinkClickIdentifier(string3);
        this.setAppLink(string4);
        this.setPushIdentifier(string5);
        PrefHelper.prefHelper_.prefsEditor_.apply();
    }

    private ArrayList<String> deserializeString(String string2) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Collections.addAll(arrayList, string2.split(","));
        return arrayList;
    }

    private ArrayList<String> getActions() {
        String string2 = this.getString("bnc_actions");
        if (string2.equals("bnc_no_value")) {
            return new ArrayList<String>();
        }
        return this.deserializeString(string2);
    }

    private ArrayList<String> getBuckets() {
        String string2 = this.getString("bnc_buckets");
        if (string2.equals("bnc_no_value")) {
            return new ArrayList<String>();
        }
        return this.deserializeString(string2);
    }

    public static PrefHelper getInstance(Context context) {
        if (prefHelper_ == null) {
            prefHelper_ = new PrefHelper(context);
        }
        return prefHelper_;
    }

    private String serializeArrayList(ArrayList<String> object) {
        String string2 = "";
        Iterator<String> iterator = ((ArrayList)object).iterator();
        object = string2;
        while (iterator.hasNext()) {
            string2 = iterator.next();
            object = (String)object + string2 + ",";
        }
        return ((String)object).substring(0, ((String)object).length() - 1);
    }

    private void setActions(ArrayList<String> arrayList) {
        if (arrayList.size() == 0) {
            this.setString("bnc_actions", "bnc_no_value");
            return;
        }
        this.setString("bnc_actions", this.serializeArrayList(arrayList));
    }

    private void setBuckets(ArrayList<String> arrayList) {
        if (arrayList.size() == 0) {
            this.setString("bnc_buckets", "bnc_no_value");
            return;
        }
        this.setString("bnc_buckets", this.serializeArrayList(arrayList));
    }

    public void clearBranchAnalyticsData() {
        savedAnalyticsData_ = null;
        this.setString("bnc_branch_analytical_data", "");
    }

    public void clearIsReferrable() {
        this.setInteger("bnc_is_referrable", 0);
    }

    public void clearUserValues() {
        Iterator<String> iterator = this.getBuckets().iterator();
        while (iterator.hasNext()) {
            this.setCreditCount(iterator.next(), 0);
        }
        this.setBuckets(new ArrayList<String>());
        for (String string2 : this.getActions()) {
            this.setActionTotalCount(string2, 0);
            this.setActionUniqueCount(string2, 0);
        }
        this.setActions(new ArrayList<String>());
    }

    public String getAPIBaseUrl() {
        return "https://api.branch.io/";
    }

    public String getAppLink() {
        return this.getString("bnc_app_link");
    }

    public String getAppVersion() {
        return this.getString("bnc_app_version");
    }

    public boolean getBool(String string2) {
        return PrefHelper.prefHelper_.appSharedPrefs_.getBoolean(string2, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public JSONObject getBranchAnalyticsData() {
        JSONObject jSONObject;
        if (savedAnalyticsData_ != null) {
            return savedAnalyticsData_;
        }
        String string2 = this.getString("bnc_branch_analytical_data");
        JSONObject jSONObject2 = jSONObject = new JSONObject();
        if (TextUtils.isEmpty((CharSequence)string2)) return jSONObject2;
        jSONObject2 = jSONObject;
        if (string2.equals("bnc_no_value")) return jSONObject2;
        try {
            return new JSONObject(string2);
        }
        catch (JSONException jSONException) {
            return jSONObject;
        }
    }

    public String getBranchKey() {
        if (Branch_Key == null) {
            Branch_Key = this.getString("bnc_branch_key");
        }
        return Branch_Key;
    }

    public int getBranchViewUsageCount(String string2) {
        return this.getInteger("bnc_branch_view_use_" + string2, 0);
    }

    public int getCreditCount(String string2) {
        return this.getInteger("bnc_credit_base_" + string2);
    }

    public String getDeviceFingerPrintID() {
        return this.getString("bnc_device_fingerprint_id");
    }

    public boolean getExternDebug() {
        return BNC_Dev_Debug;
    }

    public String getExternalIntentExtra() {
        return this.getString("bnc_external_intent_extra");
    }

    public String getExternalIntentUri() {
        return this.getString("bnc_external_intent_uri");
    }

    public String getGooglePlayReferrer() {
        return this.getString("bnc_google_play_install_referrer_extras");
    }

    public String getGoogleSearchInstallIdentifier() {
        return this.getString("bnc_google_search_install_identifier");
    }

    public String getIdentityID() {
        return this.getString("bnc_identity_id");
    }

    public String getInstallParams() {
        return this.getString("bnc_install_params");
    }

    public int getInteger(String string2) {
        return this.getInteger(string2, 0);
    }

    public int getInteger(String string2, int n) {
        return PrefHelper.prefHelper_.appSharedPrefs_.getInt(string2, n);
    }

    public boolean getIsAppLinkTriggeredInit() {
        return this.getBool("bnc_triggered_by_fb_app_link");
    }

    public int getIsReferrable() {
        return this.getInteger("bnc_is_referrable");
    }

    public long getLastStrongMatchTime() {
        return this.getLong("bnc_branch_strong_match_time");
    }

    public String getLinkClickID() {
        return this.getString("bnc_link_click_id");
    }

    public String getLinkClickIdentifier() {
        return this.getString("bnc_link_click_identifier");
    }

    public long getLong(String string2) {
        return PrefHelper.prefHelper_.appSharedPrefs_.getLong(string2, 0L);
    }

    public String getPushIdentifier() {
        return this.getString("bnc_push_identifier");
    }

    public JSONObject getRequestMetadata() {
        return this.requestMetadata;
    }

    public int getRetryCount() {
        return this.getInteger("bnc_retry_count", 3);
    }

    public int getRetryInterval() {
        return this.getInteger("bnc_retry_interval", 1000);
    }

    public String getSessionID() {
        return this.getString("bnc_session_id");
    }

    public String getSessionParams() {
        return this.getString("bnc_session_params");
    }

    public String getString(String string2) {
        return PrefHelper.prefHelper_.appSharedPrefs_.getString(string2, "bnc_no_value");
    }

    public int getTimeout() {
        return this.getInteger("bnc_timeout", 5500);
    }

    public String getUserURL() {
        return this.getString("bnc_user_url");
    }

    public boolean isFullAppConversion() {
        return this.getBool("bnc_is_full_app_conversion");
    }

    public void log(String string2, String string3) {
        if (BNC_Dev_Debug || BNC_Logging) {
            Log.i((String)string2, (String)string3);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String readBranchKey(boolean bl) {
        void var2_11;
        void var2_8;
        void var3_23;
        Object var5_2 = null;
        Object var2_4 = null;
        String string2 = bl ? "io.branch.sdk.BranchKey" : "io.branch.sdk.BranchKey.test";
        if (!bl) {
            this.setExternDebug();
        }
        Object var3_15 = var5_2;
        try {
            ApplicationInfo applicationInfo = this.context_.getPackageManager().getApplicationInfo(this.context_.getPackageName(), 128);
            Object var3_16 = var5_2;
            if (applicationInfo.metaData != null) {
                String string3;
                Object var3_17 = var5_2;
                String string4 = string3 = applicationInfo.metaData.getString(string2);
                if (string3 == null) {
                    String string5 = string3;
                    if (!bl) {
                        String string6 = string3;
                        String string7 = applicationInfo.metaData.getString("io.branch.sdk.BranchKey");
                    }
                }
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Object var2_13 = var3_15;
        }
        void var3_20 = var2_8;
        if (TextUtils.isEmpty((CharSequence)var2_8)) {
            try {
                Resources resources = this.context_.getResources();
                String string8 = resources.getString(resources.getIdentifier(string2, "string", this.context_.getPackageName()));
            }
            catch (Exception exception) {
                void var3_25 = var2_8;
            }
        }
        void var2_9 = var3_23;
        if (var3_23 == null) {
            return var2_11;
        }
        return var2_11;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void saveBranchAnalyticsData(JSONObject jSONObject) {
        String string2 = this.getSessionID();
        if (!string2.equals("bnc_no_value")) {
            if (savedAnalyticsData_ == null) {
                savedAnalyticsData_ = this.getBranchAnalyticsData();
            }
            try {
                JSONArray jSONArray;
                if (savedAnalyticsData_.has(string2)) {
                    jSONArray = savedAnalyticsData_.getJSONArray(string2);
                } else {
                    jSONArray = new JSONArray();
                    savedAnalyticsData_.put(string2, (Object)jSONArray);
                }
                jSONArray.put((Object)jSONObject);
                this.setString("bnc_branch_analytical_data", savedAnalyticsData_.toString());
                return;
            }
            catch (JSONException jSONException) {
                // empty catch block
            }
        }
    }

    public void saveLastStrongMatchTime(long l) {
        this.setLong("bnc_branch_strong_match_time", l);
    }

    public void setActionTotalCount(String string2, int n) {
        ArrayList<String> arrayList = this.getActions();
        if (!arrayList.contains(string2)) {
            arrayList.add(string2);
            this.setActions(arrayList);
        }
        this.setInteger("bnc_total_base_" + string2, n);
    }

    public void setActionUniqueCount(String string2, int n) {
        this.setInteger("bnc_balance_base_" + string2, n);
    }

    public void setAppLink(String string2) {
        this.setString("bnc_app_link", string2);
    }

    public void setAppVersion(String string2) {
        this.setString("bnc_app_version", string2);
    }

    public void setBool(String string2, Boolean bl) {
        PrefHelper.prefHelper_.prefsEditor_.putBoolean(string2, bl.booleanValue());
        PrefHelper.prefHelper_.prefsEditor_.apply();
    }

    public boolean setBranchKey(String string2) {
        Branch_Key = string2;
        String string3 = this.getString("bnc_branch_key");
        if (string2 == null || string3 == null || !string3.equals(string2)) {
            this.clearPrefOnBranchKeyChange();
            this.setString("bnc_branch_key", string2);
            return true;
        }
        return false;
    }

    public void setCreditCount(String string2, int n) {
        ArrayList<String> arrayList = this.getBuckets();
        if (!arrayList.contains(string2)) {
            arrayList.add(string2);
            this.setBuckets(arrayList);
        }
        this.setInteger("bnc_credit_base_" + string2, n);
    }

    public void setDeviceFingerPrintID(String string2) {
        this.setString("bnc_device_fingerprint_id", string2);
    }

    public void setExternDebug() {
        BNC_Dev_Debug = true;
    }

    public void setExternalIntentExtra(String string2) {
        this.setString("bnc_external_intent_extra", string2);
    }

    public void setExternalIntentUri(String string2) {
        this.setString("bnc_external_intent_uri", string2);
    }

    public void setGooglePlayReferrer(String string2) {
        this.setString("bnc_google_play_install_referrer_extras", string2);
    }

    public void setGoogleSearchInstallIdentifier(String string2) {
        this.setString("bnc_google_search_install_identifier", string2);
    }

    public void setIdentity(String string2) {
        this.setString("bnc_identity", string2);
    }

    public void setIdentityID(String string2) {
        this.setString("bnc_identity_id", string2);
    }

    public void setInstallParams(String string2) {
        this.setString("bnc_install_params", string2);
    }

    public void setInstallReferrerParams(String string2) {
        this.setString("bnc_install_referrer", string2);
    }

    public void setInteger(String string2, int n) {
        PrefHelper.prefHelper_.prefsEditor_.putInt(string2, n);
        PrefHelper.prefHelper_.prefsEditor_.apply();
    }

    public void setIsAppLinkTriggeredInit(Boolean bl) {
        this.setBool("bnc_triggered_by_fb_app_link", bl);
    }

    public void setIsFullAppConversion(boolean bl) {
        this.setBool("bnc_is_full_app_conversion", bl);
    }

    public void setIsReferrable() {
        this.setInteger("bnc_is_referrable", 1);
    }

    public void setLinkClickID(String string2) {
        this.setString("bnc_link_click_id", string2);
    }

    public void setLinkClickIdentifier(String string2) {
        this.setString("bnc_link_click_identifier", string2);
    }

    public void setLogging(boolean bl) {
        BNC_Logging = bl;
    }

    public void setLong(String string2, long l) {
        PrefHelper.prefHelper_.prefsEditor_.putLong(string2, l);
        PrefHelper.prefHelper_.prefsEditor_.apply();
    }

    public void setPushIdentifier(String string2) {
        this.setString("bnc_push_identifier", string2);
    }

    public void setSessionID(String string2) {
        this.setString("bnc_session_id", string2);
    }

    public void setSessionParams(String string2) {
        this.setString("bnc_session_params", string2);
    }

    public void setString(String string2, String string3) {
        PrefHelper.prefHelper_.prefsEditor_.putString(string2, string3);
        PrefHelper.prefHelper_.prefsEditor_.apply();
    }

    public void setUserURL(String string2) {
        this.setString("bnc_user_url", string2);
    }

    public void updateBranchViewUsageCount(String string2) {
        this.setInteger("bnc_branch_view_use_" + string2, this.getBranchViewUsageCount(string2) + 1);
    }
}

