/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import io.branch.referral.Branch;
import io.branch.referral.Defines;
import io.branch.referral.DeviceInfo;
import io.branch.referral.PrefHelper;
import io.branch.referral.ServerRequestActionCompleted;
import io.branch.referral.ServerRequestCreateUrl;
import io.branch.referral.ServerRequestGetRewardHistory;
import io.branch.referral.ServerRequestGetRewards;
import io.branch.referral.ServerRequestIdentifyUserRequest;
import io.branch.referral.ServerRequestLogout;
import io.branch.referral.ServerRequestRedeemRewards;
import io.branch.referral.ServerRequestRegisterClose;
import io.branch.referral.ServerRequestRegisterInstall;
import io.branch.referral.ServerRequestRegisterOpen;
import io.branch.referral.ServerResponse;
import io.branch.referral.SystemObserver;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class ServerRequest {
    public boolean constructError_ = false;
    private boolean disableAndroidIDFetch_;
    final Set<PROCESS_WAIT_LOCK> locks_;
    private JSONObject params_;
    protected PrefHelper prefHelper_;
    long queueWaitTime_ = 0L;
    protected String requestPath_;
    boolean skipOnTimeOut = false;
    private final SystemObserver systemObserver_;
    private int waitLockCnt = 0;

    public ServerRequest(Context context, String string2) {
        this.requestPath_ = string2;
        this.prefHelper_ = PrefHelper.getInstance(context);
        this.systemObserver_ = new SystemObserver(context);
        this.params_ = new JSONObject();
        this.disableAndroidIDFetch_ = Branch.isDeviceIDFetchDisabled();
        this.locks_ = new HashSet<PROCESS_WAIT_LOCK>();
    }

    protected ServerRequest(String string2, JSONObject jSONObject, Context context) {
        this.requestPath_ = string2;
        this.params_ = jSONObject;
        this.prefHelper_ = PrefHelper.getInstance(context);
        this.systemObserver_ = new SystemObserver(context);
        this.disableAndroidIDFetch_ = Branch.isDeviceIDFetchDisabled();
        this.locks_ = new HashSet<PROCESS_WAIT_LOCK>();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ServerRequest fromJSON(JSONObject jSONObject, Context context) {
        String string2 = null;
        String string3 = "";
        String string4 = string2;
        try {
            if (jSONObject.has("REQ_POST")) {
                string4 = jSONObject.getJSONObject("REQ_POST");
            }
        }
        catch (JSONException jSONException) {
            string4 = string2;
        }
        string2 = string3;
        try {
            if (jSONObject.has("REQ_POST_PATH")) {
                string2 = jSONObject.getString("REQ_POST_PATH");
            }
        }
        catch (JSONException jSONException) {
            string2 = string3;
        }
        if (string2 != null && string2.length() > 0) {
            return ServerRequest.getExtendedServerRequest(string2, (JSONObject)string4, context);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static ServerRequest getExtendedServerRequest(String string2, JSONObject jSONObject, Context context) {
        ServerRequestActionCompleted serverRequestActionCompleted = null;
        if (string2.equalsIgnoreCase(Defines.RequestPath.CompletedAction.getPath())) {
            return new ServerRequestActionCompleted(string2, jSONObject, context);
        }
        if (string2.equalsIgnoreCase(Defines.RequestPath.GetURL.getPath())) {
            return new ServerRequestCreateUrl(string2, jSONObject, context);
        }
        if (string2.equalsIgnoreCase(Defines.RequestPath.GetCreditHistory.getPath())) {
            return new ServerRequestGetRewardHistory(string2, jSONObject, context);
        }
        if (string2.equalsIgnoreCase(Defines.RequestPath.GetCredits.getPath())) {
            return new ServerRequestGetRewards(string2, jSONObject, context);
        }
        if (string2.equalsIgnoreCase(Defines.RequestPath.IdentifyUser.getPath())) {
            return new ServerRequestIdentifyUserRequest(string2, jSONObject, context);
        }
        if (string2.equalsIgnoreCase(Defines.RequestPath.Logout.getPath())) {
            return new ServerRequestLogout(string2, jSONObject, context);
        }
        if (string2.equalsIgnoreCase(Defines.RequestPath.RedeemRewards.getPath())) {
            return new ServerRequestRedeemRewards(string2, jSONObject, context);
        }
        if (string2.equalsIgnoreCase(Defines.RequestPath.RegisterClose.getPath())) {
            return new ServerRequestRegisterClose(string2, jSONObject, context);
        }
        if (string2.equalsIgnoreCase(Defines.RequestPath.RegisterInstall.getPath())) {
            return new ServerRequestRegisterInstall(string2, jSONObject, context);
        }
        if (!string2.equalsIgnoreCase(Defines.RequestPath.RegisterOpen.getPath())) return serverRequestActionCompleted;
        return new ServerRequestRegisterOpen(string2, jSONObject, context);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isPackageInstalled(Context object) {
        PackageManager packageManager = object.getPackageManager();
        return (object = packageManager.getLaunchIntentForPackage(object.getPackageName())) != null && (object = packageManager.queryIntentActivities((Intent)object, 65536)) != null && object.size() > 0;
    }

    public void addProcessWaitLock(PROCESS_WAIT_LOCK pROCESS_WAIT_LOCK) {
        if (pROCESS_WAIT_LOCK != null) {
            this.locks_.add(pROCESS_WAIT_LOCK);
        }
    }

    public abstract void clearCallbacks();

    public JSONObject getGetParams() {
        return this.params_;
    }

    public JSONObject getPost() {
        return this.params_;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JSONObject getPostWithInstrumentationValues(ConcurrentHashMap<String, String> concurrentHashMap) {
        JSONObject jSONObject = new JSONObject();
        try {
            String string2;
            JSONObject jSONObject2;
            Iterator<E> iterator;
            try {
                if (this.params_ != null) {
                    jSONObject2 = new JSONObject(this.params_.toString());
                    iterator = jSONObject2.keys();
                    while (iterator.hasNext()) {
                        string2 = (String)iterator.next();
                        jSONObject.put(string2, jSONObject2.get(string2));
                    }
                }
                if (concurrentHashMap.size() <= 0) return jSONObject;
                jSONObject2 = new JSONObject();
                iterator = concurrentHashMap.keySet();
            }
            catch (JSONException jSONException) {
                // empty catch block
                return jSONObject;
            }
            try {}
            catch (JSONException jSONException) {
                return jSONObject;
            }
            iterator = iterator.iterator();
            do {
                if (!iterator.hasNext()) {
                    jSONObject.put(Defines.Jsonkey.Branch_Instrumentation.getKey(), (Object)jSONObject2);
                    return jSONObject;
                }
                string2 = (String)iterator.next();
                jSONObject2.put(string2, (Object)concurrentHashMap.get(string2));
                concurrentHashMap.remove(string2);
            } while (true);
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            return this.params_;
        }
    }

    public long getQueueWaitTime() {
        long l = 0L;
        if (this.queueWaitTime_ > 0L) {
            l = System.currentTimeMillis() - this.queueWaitTime_;
        }
        return l;
    }

    public final String getRequestPath() {
        return this.requestPath_;
    }

    public String getRequestUrl() {
        return this.prefHelper_.getAPIBaseUrl() + this.requestPath_;
    }

    public abstract void handleFailure(int var1, String var2);

    public boolean isGAdsParamsRequired() {
        return false;
    }

    public abstract boolean isGetRequest();

    public boolean isWaitingOnProcessToFinish() {
        return this.locks_.size() > 0;
    }

    public void onPreExecute() {
    }

    public void onRequestQueued() {
        this.queueWaitTime_ = System.currentTimeMillis();
    }

    public abstract void onRequestSucceeded(ServerResponse var1, Branch var2);

    public void removeProcessWaitLock(PROCESS_WAIT_LOCK pROCESS_WAIT_LOCK) {
        this.locks_.remove((Object)((Object)pROCESS_WAIT_LOCK));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void setPost(JSONObject jSONObject) {
        try {
            String string2;
            JSONObject jSONObject2 = new JSONObject();
            Iterator iterator = this.prefHelper_.getRequestMetadata().keys();
            while (iterator.hasNext()) {
                string2 = (String)iterator.next();
                jSONObject2.put(string2, this.prefHelper_.getRequestMetadata().get(string2));
            }
            if (jSONObject.has(Defines.Jsonkey.Metadata.getKey())) {
                iterator = jSONObject.getJSONObject(Defines.Jsonkey.Metadata.getKey()).keys();
                while (iterator.hasNext()) {
                    string2 = (String)iterator.next();
                    jSONObject2.put(string2, jSONObject.getJSONObject(Defines.Jsonkey.Metadata.getKey()).get(string2));
                }
            }
            jSONObject.put(Defines.Jsonkey.Metadata.getKey(), (Object)jSONObject2);
        }
        catch (JSONException jSONException) {
            Log.e((String)"BranchSDK", (String)"Could not merge metadata, ignoring user metadata.");
        }
        this.params_ = jSONObject;
        DeviceInfo.getInstance(this.prefHelper_.getExternDebug(), this.systemObserver_, this.disableAndroidIDFetch_).updateRequestWithDeviceParams(this.params_);
    }

    public boolean shouldRetryOnFail() {
        return false;
    }

    public JSONObject toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("REQ_POST", (Object)this.params_);
            jSONObject.put("REQ_POST_PATH", (Object)this.requestPath_);
            return jSONObject;
        }
        catch (JSONException jSONException) {
            return null;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void updateEnvironment(Context object, JSONObject jSONObject) {
        try {
            void var1_3;
            void var2_6;
            if (ServerRequest.isPackageInstalled(object)) {
                String string2 = Defines.Jsonkey.NativeApp.getKey();
            } else {
                String string3 = Defines.Jsonkey.InstantApp.getKey();
            }
            var2_6.put(Defines.Jsonkey.Environment.getKey(), (Object)var1_3);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void updateGAdsParams(SystemObserver systemObserver) {
        if (TextUtils.isEmpty((CharSequence)systemObserver.GAIDString_)) return;
        try {
            this.params_.put(Defines.Jsonkey.GoogleAdvertisingID.getKey(), (Object)systemObserver.GAIDString_);
            this.params_.put(Defines.Jsonkey.LATVal.getKey(), systemObserver.LATVal_);
            return;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            return;
        }
    }

    static enum PROCESS_WAIT_LOCK {
        FB_APP_LINK_WAIT_LOCK,
        GAID_FETCH_WAIT_LOCK,
        INTENT_PENDING_WAIT_LOCK,
        STRONG_MATCH_PENDING_WAIT_LOCK,
        INSTALL_REFERRER_FETCH_WAIT_LOCK;

    }

}

