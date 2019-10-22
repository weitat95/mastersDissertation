/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import io.branch.indexing.ContentDiscoverer;
import io.branch.referral.Base64;
import io.branch.referral.BranchAsyncTask;
import io.branch.referral.BranchError;
import io.branch.referral.BranchLinkData;
import io.branch.referral.BranchStrongMatchHelper;
import io.branch.referral.BranchUtil;
import io.branch.referral.BranchViewHandler;
import io.branch.referral.DeferredAppLinkDataHandler;
import io.branch.referral.Defines;
import io.branch.referral.DeviceInfo;
import io.branch.referral.InstallListener;
import io.branch.referral.PrefHelper;
import io.branch.referral.ServerRequest;
import io.branch.referral.ServerRequestCreateUrl;
import io.branch.referral.ServerRequestIdentifyUserRequest;
import io.branch.referral.ServerRequestInitSession;
import io.branch.referral.ServerRequestLogout;
import io.branch.referral.ServerRequestQueue;
import io.branch.referral.ServerRequestRegisterClose;
import io.branch.referral.ServerRequestRegisterInstall;
import io.branch.referral.ServerRequestRegisterOpen;
import io.branch.referral.ServerResponse;
import io.branch.referral.ShareLinkManager;
import io.branch.referral.SystemObserver;
import io.branch.referral.network.BranchRemoteInterface;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Branch
implements BranchViewHandler.IBranchViewEvents,
InstallListener.IInstallReferrerEvents,
SystemObserver.GAdsParamsFetchEvents {
    private static final String[] EXTERNAL_INTENT_EXTRA_KEY_WHITE_LIST;
    private static int LATCH_WAIT_UNTIL;
    private static Branch branchReferral_;
    static boolean checkInstallReferrer_;
    private static String cookieBasedMatchDomain_;
    private static CUSTOM_REFERRABLE_SETTINGS customReferrableSettings_;
    private static boolean disableDeviceIDFetch_;
    private static boolean isActivityLifeCycleCallbackRegistered_;
    private static boolean isAutoSessionMode_;
    private static boolean isLogging_;
    private static boolean isSimulatingInstalls_;
    private static long playStoreReferrerFetchTime;
    private BranchRemoteInterface branchRemoteInterface_;
    private Context context_;
    WeakReference<Activity> currentActivityReference_;
    private JSONObject deeplinkDebugParams_;
    private boolean enableFacebookAppLinkCheck_ = false;
    private List<String> externalUriWhiteList_;
    private CountDownLatch getFirstReferringParamsLatch = null;
    private CountDownLatch getLatestReferringParamsLatch = null;
    private boolean handleDelayedNewIntents_ = false;
    private boolean hasNetwork_;
    private SESSION_STATE initState_;
    private final ConcurrentHashMap<String, String> instrumentationExtraData_;
    private INTENT_STATE intentState_ = INTENT_STATE.PENDING;
    private boolean isGAParamsFetchInProgress_ = false;
    private boolean isInitReportedThroughCallBack = false;
    private Map<BranchLinkData, String> linkCache_;
    final Object lock;
    private int networkCount_;
    private boolean performCookieBasedStrongMatchingOnGAIDAvailable = false;
    private PrefHelper prefHelper_;
    private ServerRequestQueue requestQueue_;
    private Semaphore serverSema_;
    String sessionReferredLink_;
    private ShareLinkManager shareLinkManager_;
    private List<String> skipExternalUriHosts_;
    private final SystemObserver systemObserver_;

    static {
        isLogging_ = false;
        checkInstallReferrer_ = true;
        playStoreReferrerFetchTime = 1500L;
        isAutoSessionMode_ = false;
        isActivityLifeCycleCallbackRegistered_ = false;
        customReferrableSettings_ = CUSTOM_REFERRABLE_SETTINGS.USE_DEFAULT;
        cookieBasedMatchDomain_ = "app.link";
        LATCH_WAIT_UNTIL = 2500;
        EXTERNAL_INTENT_EXTRA_KEY_WHITE_LIST = new String[]{"extra_launch_uri"};
    }

    /*
     * Enabled aggressive block sorting
     */
    private Branch(Context context) {
        this.initState_ = SESSION_STATE.UNINITIALISED;
        this.prefHelper_ = PrefHelper.getInstance(context);
        this.branchRemoteInterface_ = BranchRemoteInterface.getDefaultBranchRemoteInterface(context);
        this.systemObserver_ = new SystemObserver(context);
        this.requestQueue_ = ServerRequestQueue.getInstance(context);
        this.serverSema_ = new Semaphore(1);
        this.lock = new Object();
        this.networkCount_ = 0;
        this.hasNetwork_ = true;
        this.linkCache_ = new HashMap<BranchLinkData, String>();
        this.instrumentationExtraData_ = new ConcurrentHashMap();
        this.isGAParamsFetchInProgress_ = this.systemObserver_.prefetchGAdsParams(this);
        InstallListener.setListener(this);
        if (Build.VERSION.SDK_INT >= 15) {
            this.handleDelayedNewIntents_ = true;
            this.intentState_ = INTENT_STATE.PENDING;
        } else {
            this.handleDelayedNewIntents_ = false;
            this.intentState_ = INTENT_STATE.READY;
        }
        this.externalUriWhiteList_ = new ArrayList<String>();
        this.skipExternalUriHosts_ = new ArrayList<String>();
    }

    private JSONObject appendDebugParams(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                if (this.deeplinkDebugParams_ != null) {
                    if (this.deeplinkDebugParams_.length() > 0) {
                        Log.w((String)"BranchSDK", (String)"You're currently in deep link debug mode. Please comment out 'setDeepLinkDebugMode' to receive the deep link parameters from a real Branch link");
                    }
                    Iterator iterator = this.deeplinkDebugParams_.keys();
                    while (iterator.hasNext()) {
                        String string2 = (String)iterator.next();
                        jSONObject.put(string2, this.deeplinkDebugParams_.get(string2));
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return jSONObject;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void checkForAutoDeepLinkConfiguration() {
        block12: {
            var9_1 = this.getLatestReferringParams();
            var8_2 = null;
            var7_3 = null;
            var5_4 = var8_2;
            if (var9_1.has(Defines.Jsonkey.Clicked_Branch_Link.getKey()) == false) return;
            var5_4 = var8_2;
            if (!var9_1.getBoolean(Defines.Jsonkey.Clicked_Branch_Link.getKey())) {
                return;
            }
            var5_4 = var8_2;
            if (var9_1.length() <= 0) return;
            var5_4 = var8_2;
            var6_7 /* !! */  = this.context_.getPackageManager().getApplicationInfo(this.context_.getPackageName(), 128);
            var5_4 = var8_2;
            if (var6_7 /* !! */ .metaData != null) {
                var5_4 = var8_2;
                if (var6_7 /* !! */ .metaData.getBoolean("io.branch.sdk.auto_link_disable", false) != false) return;
            }
            var5_4 = var8_2;
            var10_9 = this.context_.getPackageManager().getPackageInfo((String)this.context_.getPackageName(), (int)129).activities;
            var3_10 = 1501;
            var6_7 /* !! */  = var7_3;
            var2_11 = var3_10;
            if (var10_9 == null) ** GOTO lbl48
            var5_4 = var8_2;
            var4_12 = ((ActivityInfo[])var10_9).length;
            var1_13 = 0;
            block6: do {
                block14: {
                    block13: {
                        block16: {
                            block15: {
                                var6_7 /* !! */  = var7_3;
                                var2_11 = var3_10;
                                if (var1_13 >= var4_12) break block13;
                                var11_14 /* !! */  = var10_9[var1_13];
                                if (var11_14 /* !! */  == null) break block14;
                                var5_4 = var8_2;
                                if (var11_14 /* !! */ .metaData == null) break block14;
                                var5_4 = var8_2;
                                if (var11_14 /* !! */ .metaData.getString("io.branch.sdk.auto_link_keys") != null) break block15;
                                var5_4 = var8_2;
                                if (var11_14 /* !! */ .metaData.getString("io.branch.sdk.auto_link_path") == null) break block14;
                            }
                            var5_4 = var8_2;
                            if (this.checkForAutoDeepLinkKeys(var9_1, var11_14 /* !! */ )) break block16;
                            var5_4 = var8_2;
                            if (!this.checkForAutoDeepLinkPath(var9_1, var11_14 /* !! */ )) break block14;
                        }
                        var5_4 = var8_2;
                        var5_4 = var6_7 /* !! */  = var11_14 /* !! */ .name;
                        var2_11 = var11_14 /* !! */ .metaData.getInt("io.branch.sdk.auto_link_request_code", 1501);
                    }
                    if (var6_7 /* !! */  == null) return;
                    var5_4 = var6_7 /* !! */ ;
                    if (this.currentActivityReference_ == null) return;
                    var5_4 = var6_7 /* !! */ ;
                    var7_3 = (Activity)this.currentActivityReference_.get();
                    if (var7_3 != null) {
                        var5_4 = var6_7 /* !! */ ;
                        var8_2 = new Intent((Context)var7_3, Class.forName((String)var6_7 /* !! */ ));
                        var5_4 = var6_7 /* !! */ ;
                        var8_2.putExtra("io.branch.sdk.auto_linked", "true");
                        var5_4 = var6_7 /* !! */ ;
                        var8_2.putExtra(Defines.Jsonkey.ReferringData.getKey(), var9_1.toString());
                        var5_4 = var6_7 /* !! */ ;
                        var10_9 = var9_1.keys();
                        do {
                            var5_4 = var6_7 /* !! */ ;
                            if (!var10_9.hasNext()) break block6;
                            var5_4 = var6_7 /* !! */ ;
                            var11_14 /* !! */  = (String)var10_9.next();
                            var5_4 = var6_7 /* !! */ ;
                            var8_2.putExtra((String)var11_14 /* !! */ , var9_1.getString((String)var11_14 /* !! */ ));
                        } while (true);
                    }
                    break block12;
                }
                ++var1_13;
            } while (true);
            var5_4 = var6_7 /* !! */ ;
            var7_3.startActivityForResult((Intent)var8_2, var2_11);
            return;
        }
        var5_4 = var6_7 /* !! */ ;
        try {
            Log.w((String)"BranchSDK", (String)"No activity reference to launch deep linked activity");
            return;
        }
        catch (PackageManager.NameNotFoundException var5_5) {
            Log.i((String)"BranchSDK", (String)"Branch Warning: Please make sure Activity names set for auto deep link are correct!");
            return;
        }
        catch (ClassNotFoundException var6_8) {
            Log.i((String)"BranchSDK", (String)("Branch Warning: Please make sure Activity names set for auto deep link are correct! Error while looking for activity " + var5_4));
            return;
        }
        catch (Exception var5_6) {
            // empty catch block
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean checkForAutoDeepLinkKeys(JSONObject jSONObject, ActivityInfo arrstring) {
        boolean bl;
        boolean bl2 = bl = false;
        if (arrstring.metaData.getString("io.branch.sdk.auto_link_keys") == null) return bl2;
        arrstring = arrstring.metaData.getString("io.branch.sdk.auto_link_keys").split(",");
        int n = arrstring.length;
        int n2 = 0;
        do {
            bl2 = bl;
            if (n2 >= n) return bl2;
            if (jSONObject.has(arrstring[n2])) {
                return true;
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean checkForAutoDeepLinkPath(JSONObject arrstring, ActivityInfo activityInfo) {
        String string2;
        boolean bl = false;
        String string3 = null;
        try {
            if (arrstring.has(Defines.Jsonkey.AndroidDeepLinkPath.getKey())) {
                string2 = arrstring.getString(Defines.Jsonkey.AndroidDeepLinkPath.getKey());
            } else {
                string2 = string3;
                if (arrstring.has(Defines.Jsonkey.DeepLinkPath.getKey())) {
                    string2 = arrstring.getString(Defines.Jsonkey.DeepLinkPath.getKey());
                }
            }
        }
        catch (JSONException jSONException) {
            string2 = string3;
        }
        boolean bl2 = bl;
        if (activityInfo.metaData.getString("io.branch.sdk.auto_link_path") == null) return bl2;
        bl2 = bl;
        if (string2 == null) return bl2;
        arrstring = activityInfo.metaData.getString("io.branch.sdk.auto_link_path").split(",");
        int n = arrstring.length;
        int n2 = 0;
        do {
            bl2 = bl;
            if (n2 >= n) return bl2;
            if (this.pathMatch(arrstring[n2].trim(), string2)) {
                return true;
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean checkIntentForSessionRestart(Intent intent) {
        boolean bl = false;
        boolean bl2 = false;
        if (intent == null) return bl;
        try {
            bl2 = bl = intent.getBooleanExtra(Defines.Jsonkey.ForceNewBranchSession.getKey(), false);
        }
        catch (Throwable throwable) {}
        bl = bl2;
        if (!bl2) return bl;
        intent.putExtra(Defines.Jsonkey.ForceNewBranchSession.getKey(), false);
        return bl2;
    }

    private void closeSessionInternal() {
        this.executeClose();
        this.sessionReferredLink_ = null;
    }

    private JSONObject convertParamsStringToDictionary(String jSONObject) {
        if (jSONObject.equals("bnc_no_value")) {
            return new JSONObject();
        }
        try {
            JSONObject jSONObject2 = new JSONObject((String)jSONObject);
            return jSONObject2;
        }
        catch (JSONException jSONException) {
            jSONObject = Base64.decode(jSONObject.getBytes(), 2);
            try {
                jSONObject = new JSONObject(new String((byte[])jSONObject));
                return jSONObject;
            }
            catch (JSONException jSONException2) {
                jSONException2.printStackTrace();
                return new JSONObject();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void executeClose() {
        if (this.initState_ != SESSION_STATE.UNINITIALISED) {
            if (!this.hasNetwork_) {
                ServerRequest serverRequest = this.requestQueue_.peek();
                if (serverRequest != null && serverRequest instanceof ServerRequestRegisterInstall || serverRequest instanceof ServerRequestRegisterOpen) {
                    this.requestQueue_.dequeue();
                }
            } else if (!this.requestQueue_.containsClose()) {
                this.handleNewRequest(new ServerRequestRegisterClose(this.context_));
            }
            this.initState_ = SESSION_STATE.UNINITIALISED;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Branch getBranchInstance(Context context, boolean bl) {
        block7: {
            String string2;
            block6: {
                if (branchReferral_ != null) return branchReferral_;
                branchReferral_ = Branch.initInstance(context);
                string2 = Branch.branchReferral_.prefHelper_.readBranchKey(bl);
                if (string2 == null || string2.equalsIgnoreCase("bnc_no_value")) {
                    String string3;
                    string2 = null;
                    Resources resources = context.getResources();
                    string2 = string3 = resources.getString(resources.getIdentifier("io.branch.apiKey", "string", context.getPackageName()));
                    break block6;
                }
                bl = Branch.branchReferral_.prefHelper_.setBranchKey(string2);
                break block7;
                catch (Exception exception) {}
            }
            if (!TextUtils.isEmpty((CharSequence)string2)) {
                bl = Branch.branchReferral_.prefHelper_.setBranchKey(string2);
            } else {
                Log.i((String)"BranchSDK", (String)"Branch Warning: Please enter your branch_key in your project's Manifest file!");
                bl = Branch.branchReferral_.prefHelper_.setBranchKey("bnc_no_value");
            }
        }
        if (bl) {
            Branch.branchReferral_.linkCache_.clear();
            Branch.branchReferral_.requestQueue_.clear();
        }
        Branch.branchReferral_.context_ = context.getApplicationContext();
        if (!(context instanceof Application)) return branchReferral_;
        isAutoSessionMode_ = true;
        branchReferral_.setActivityLifeCycleObserver((Application)context);
        return branchReferral_;
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=14)
    public static Branch getInstance() {
        if (branchReferral_ == null) {
            Log.e((String)"BranchSDK", (String)"Branch instance is not created yet. Make sure you have initialised Branch. [Consider Calling getInstance(Context ctx) if you still have issue.]");
            return branchReferral_;
        }
        if (!isAutoSessionMode_) return branchReferral_;
        if (isActivityLifeCycleCallbackRegistered_) return branchReferral_;
        Log.e((String)"BranchSDK", (String)"Branch instance is not properly initialised. Make sure your Application class is extending BranchApp class. If you are not extending BranchApp class make sure you are initialising Branch in your Applications onCreate()");
        return branchReferral_;
    }

    public static Branch getInstance(Context context) {
        return Branch.getBranchInstance(context, true);
    }

    public static boolean getIsLogging() {
        return isLogging_;
    }

    public static Branch getTestInstance(Context context) {
        return Branch.getBranchInstance(context, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void handleFailure(int n, int n2) {
        ServerRequest serverRequest = n >= this.requestQueue_.getSize() ? this.requestQueue_.peekAt(this.requestQueue_.getSize() - 1) : this.requestQueue_.peekAt(n);
        this.handleFailure(serverRequest, n2);
    }

    private void handleFailure(ServerRequest serverRequest, int n) {
        if (serverRequest == null) {
            return;
        }
        serverRequest.handleFailure(n, "");
    }

    private boolean hasDeviceFingerPrint() {
        return !this.prefHelper_.getDeviceFingerPrintID().equals("bnc_no_value");
    }

    private boolean hasSession() {
        return !this.prefHelper_.getSessionID().equals("bnc_no_value");
    }

    private boolean hasUser() {
        return !this.prefHelper_.getIdentityID().equals("bnc_no_value");
    }

    private static Branch initInstance(Context context) {
        return new Branch(context.getApplicationContext());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initUserSessionInternal(BranchReferralInitListener branchReferralInitListener, Activity activity, boolean bl) {
        if (activity != null) {
            this.currentActivityReference_ = new WeakReference<Activity>(activity);
        }
        if (this.hasUser() && this.hasSession() && this.initState_ == SESSION_STATE.INITIALISED) {
            if (branchReferralInitListener == null) return;
            {
                if (!isAutoSessionMode_) {
                    branchReferralInitListener.onInitFinished(new JSONObject(), null);
                    return;
                }
                if (this.isInitReportedThroughCallBack) {
                    branchReferralInitListener.onInitFinished(new JSONObject(), null);
                    return;
                }
                branchReferralInitListener.onInitFinished(this.getLatestReferringParams(), null);
                this.isInitReportedThroughCallBack = true;
                return;
            }
        } else {
            if (bl) {
                this.prefHelper_.setIsReferrable();
            } else {
                this.prefHelper_.clearIsReferrable();
            }
            if (this.initState_ != SESSION_STATE.INITIALISING) {
                this.initState_ = SESSION_STATE.INITIALISING;
                this.initializeSession(branchReferralInitListener);
                return;
            }
            if (branchReferralInitListener == null) return;
            {
                this.requestQueue_.setInstallOrOpenCallback(branchReferralInitListener);
                return;
            }
        }
    }

    private void initializeSession(BranchReferralInitListener branchReferralInitListener) {
        if (this.prefHelper_.getBranchKey() == null || this.prefHelper_.getBranchKey().equalsIgnoreCase("bnc_no_value")) {
            this.initState_ = SESSION_STATE.UNINITIALISED;
            if (branchReferralInitListener != null) {
                branchReferralInitListener.onInitFinished(null, new BranchError("Trouble initializing Branch.", -114));
            }
            Log.i((String)"BranchSDK", (String)"Branch Warning: Please enter your branch_key in your project's res/values/strings.xml!");
            return;
        }
        if (this.prefHelper_.getBranchKey() != null && this.prefHelper_.getBranchKey().startsWith("key_test_")) {
            Log.i((String)"BranchSDK", (String)"Branch Warning: You are using your test app's Branch Key. Remember to change it to live Branch Key during deployment.");
        }
        if (!this.prefHelper_.getExternalIntentUri().equals("bnc_no_value") || !this.enableFacebookAppLinkCheck_) {
            this.registerAppInit(branchReferralInitListener, null);
            return;
        }
        if (DeferredAppLinkDataHandler.fetchDeferredAppLinkData(this.context_, new DeferredAppLinkDataHandler.AppLinkFetchEvents(){

            @Override
            public void onAppLinkFetchFinished(String string2) {
                Branch.this.prefHelper_.setIsAppLinkTriggeredInit(true);
                if (string2 != null && !TextUtils.isEmpty((CharSequence)(string2 = Uri.parse((String)string2).getQueryParameter(Defines.Jsonkey.LinkClickID.getKey())))) {
                    Branch.this.prefHelper_.setLinkClickIdentifier(string2);
                }
                Branch.this.requestQueue_.unlockProcessWait(ServerRequest.PROCESS_WAIT_LOCK.FB_APP_LINK_WAIT_LOCK);
                Branch.this.processNextQueueItem();
            }
        }).booleanValue()) {
            this.registerAppInit(branchReferralInitListener, ServerRequest.PROCESS_WAIT_LOCK.FB_APP_LINK_WAIT_LOCK);
            return;
        }
        this.registerAppInit(branchReferralInitListener, null);
    }

    private void insertRequestAtFront(ServerRequest serverRequest) {
        if (this.networkCount_ == 0) {
            this.requestQueue_.insert(serverRequest, 0);
            return;
        }
        this.requestQueue_.insert(serverRequest, 1);
    }

    public static boolean isDeviceIDFetchDisabled() {
        return disableDeviceIDFetch_;
    }

    public static boolean isSimulatingInstalls() {
        return isSimulatingInstalls_;
    }

    private void onIntentReady(Activity activity, boolean bl) {
        this.requestQueue_.unlockProcessWait(ServerRequest.PROCESS_WAIT_LOCK.INTENT_PENDING_WAIT_LOCK);
        if (bl) {
            this.readAndStripParam(activity.getIntent().getData(), activity);
            if (cookieBasedMatchDomain_ != null && this.prefHelper_.getBranchKey() != null && !this.prefHelper_.getBranchKey().equalsIgnoreCase("bnc_no_value")) {
                if (this.isGAParamsFetchInProgress_) {
                    this.performCookieBasedStrongMatchingOnGAIDAvailable = true;
                    return;
                }
                this.performCookieBasedStrongMatch();
                return;
            }
            this.processNextQueueItem();
            return;
        }
        this.processNextQueueItem();
    }

    private boolean pathMatch(String arrstring, String arrstring2) {
        boolean bl = true;
        if ((arrstring = arrstring.split("\\?")[0].split("/")).length != (arrstring2 = arrstring2.split("\\?")[0].split("/")).length) {
            return false;
        }
        int n = 0;
        do {
            block6: {
                boolean bl2;
                block5: {
                    bl2 = bl;
                    if (n >= arrstring.length) break block5;
                    bl2 = bl;
                    if (n >= arrstring2.length) break block5;
                    String string2 = arrstring[n];
                    if (string2.equals(arrstring2[n]) || string2.contains("*")) break block6;
                    bl2 = false;
                }
                return bl2;
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void performCookieBasedStrongMatch() {
        boolean bl = this.prefHelper_.getExternDebug() || Branch.isSimulatingInstalls();
        DeviceInfo deviceInfo = DeviceInfo.getInstance(bl, this.systemObserver_, disableDeviceIDFetch_);
        Activity activity = null;
        if (this.currentActivityReference_ != null) {
            activity = (Activity)this.currentActivityReference_.get();
        }
        if (activity == null) return;
        activity = activity.getApplicationContext();
        if (activity == null) return;
        this.requestQueue_.setStrongMatchWaitLock();
        BranchStrongMatchHelper.getInstance().checkForStrongMatch((Context)activity, cookieBasedMatchDomain_, deviceInfo, this.prefHelper_, this.systemObserver_, new BranchStrongMatchHelper.StrongMatchCheckEvents(){

            @Override
            public void onStrongMatchCheckFinished() {
                Branch.this.requestQueue_.unlockProcessWait(ServerRequest.PROCESS_WAIT_LOCK.STRONG_MATCH_PENDING_WAIT_LOCK);
                Branch.this.processNextQueueItem();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void processNextQueueItem() {
        block6: {
            block7: {
                try {
                    this.serverSema_.acquire();
                    if (this.networkCount_ != 0 || this.requestQueue_.getSize() <= 0) break block6;
                    this.networkCount_ = 1;
                    ServerRequest serverRequest = this.requestQueue_.peek();
                    this.serverSema_.release();
                    if (serverRequest == null) break block7;
                    if (!serverRequest.isWaitingOnProcessToFinish()) {
                        if (!(serverRequest instanceof ServerRequestRegisterInstall) && !this.hasUser()) {
                            Log.i((String)"BranchSDK", (String)"Branch Error: User session has not been initialized!");
                            this.networkCount_ = 0;
                            this.handleFailure(this.requestQueue_.getSize() - 1, -101);
                            return;
                        }
                        if (!(serverRequest instanceof ServerRequestInitSession || this.hasSession() && this.hasDeviceFingerPrint())) {
                            this.networkCount_ = 0;
                            this.handleFailure(this.requestQueue_.getSize() - 1, -101);
                            return;
                        }
                        new BranchPostTask(serverRequest).executeTask(new Void[0]);
                        return;
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    return;
                }
                this.networkCount_ = 0;
                return;
            }
            this.requestQueue_.remove(null);
            return;
        }
        this.serverSema_.release();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean readAndStripParam(Uri var1_1, Activity var2_3) {
        if (this.intentState_ != INTENT_STATE.READY) return false;
        if (var1_1 /* !! */  == null) ** GOTO lbl38
        var4_4 = 0;
        try {
            block21: {
                var5_5 = this.externalUriWhiteList_.size() > 0 ? this.externalUriWhiteList_.contains(var1_1 /* !! */ .getScheme()) : true;
                var3_6 = var4_4;
                if (this.skipExternalUriHosts_.size() > 0) {
                    var6_7 = this.skipExternalUriHosts_.iterator();
                    do {
                        var3_6 = var4_4;
                        if (!var6_7.hasNext()) break block21;
                        var7_10 = (String)var6_7.next();
                    } while ((var8_11 = var1_1 /* !! */ .getHost()) == null || !var8_11.equals(var7_10));
                    var3_6 = 1;
                }
            }
            if (var5_5 && var3_6 == 0) {
                this.sessionReferredLink_ = var1_1 /* !! */ .toString();
                this.prefHelper_.setExternalIntentUri(var1_1 /* !! */ .toString());
                if (var2_3 != null && var2_3.getIntent() != null && var2_3.getIntent().getExtras() != null && (var7_10 = (var6_7 = var2_3.getIntent().getExtras()).keySet()).size() > 0) {
                    var8_11 = new JSONObject();
                    var9_12 = Branch.EXTERNAL_INTENT_EXTRA_KEY_WHITE_LIST;
                    var4_4 = var9_12.length;
                    var3_6 = 0;
                }
            }
            ** GOTO lbl38
        }
        catch (Exception var6_9) {
            ** GOTO lbl38
        }
        do {
            if (var3_6 < var4_4) {
                var10_13 = var9_12[var3_6];
                if (var7_10.contains(var10_13)) {
                    var8_11.put(var10_13, var6_7.get(var10_13));
                }
            } else {
                if (var8_11.length() > 0) {
                    this.prefHelper_.setExternalIntentExtra(var8_11.toString());
                }
lbl38:
                // 6 sources
                if (var2_3 != null) {
                    try {
                        if (var2_3.getIntent() != null && var2_3.getIntent().getExtras() != null && !var2_3.getIntent().getExtras().getBoolean(Defines.Jsonkey.BranchLinkUsed.getKey()) && (var6_7 = var2_3.getIntent().getExtras().getString(Defines.Jsonkey.AndroidPushNotificationKey.getKey())) != null && var6_7.length() > 0) {
                            this.prefHelper_.setPushIdentifier((String)var6_7);
                            var6_7 = var2_3.getIntent();
                            var6_7.putExtra(Defines.Jsonkey.BranchLinkUsed.getKey(), true);
                            var2_3.setIntent((Intent)var6_7);
                            return false;
                        }
                    }
                    catch (Exception var6_8) {
                        // empty catch block
                    }
                }
                if (var1_1 /* !! */  == null) return false;
                if (var1_1 /* !! */ .isHierarchical() == false) return false;
                if (var2_3 == null) return false;
                if (var1_1 /* !! */ .getQueryParameter(Defines.Jsonkey.LinkClickID.getKey()) != null) {
                    this.prefHelper_.setLinkClickIdentifier(var1_1 /* !! */ .getQueryParameter(Defines.Jsonkey.LinkClickID.getKey()));
                    var7_10 = "link_click_id=" + var1_1 /* !! */ .getQueryParameter(Defines.Jsonkey.LinkClickID.getKey());
                    var6_7 = null;
                    if (var2_3.getIntent() != null) {
                        var6_7 = var2_3.getIntent().getDataString();
                    }
                    var1_1 /* !! */  = var1_1 /* !! */ .getQuery().length() == var7_10.length() ? "\\?" + (String)var7_10 : (var6_7 != null && var6_7.length() - var7_10.length() == var6_7.indexOf((String)var7_10) ? "&" + (String)var7_10 : (String)var7_10 + "&");
                    if (var6_7 != null) {
                        var1_1 /* !! */  = Uri.parse((String)var6_7.replaceFirst((String)var1_1 /* !! */ , ""));
                        var2_3.getIntent().setData(var1_1 /* !! */ );
                        return true;
                    }
                    Log.w((String)"BranchSDK", (String)"Branch Warning. URI for the launcher activity is null. Please make sure that intent data is not set to null before calling Branch#InitSession ");
                    return true;
                }
                var6_7 = var1_1 /* !! */ .getScheme();
                var7_10 = var2_3.getIntent();
                if (var6_7 == null) return false;
                if (var7_10 == null) return false;
                if ((var7_10.getFlags() & 1048576) != 0) return false;
                if (!var6_7.equalsIgnoreCase("http")) {
                    if (var6_7.equalsIgnoreCase("https") == false) return false;
                }
                if (var1_1 /* !! */ .getHost() == null) return false;
                if (var1_1 /* !! */ .getHost().length() <= 0) return false;
                if (var7_10.getBooleanExtra(Defines.Jsonkey.BranchLinkUsed.getKey(), false) != false) return false;
                this.prefHelper_.setAppLink(var1_1 /* !! */ .toString());
                var7_10.putExtra(Defines.Jsonkey.BranchLinkUsed.getKey(), true);
                var2_3.setIntent((Intent)var7_10);
                return false;
            }
            ++var3_6;
        } while (true);
        catch (Exception var1_2) {
            // empty catch block
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void registerAppInit(BranchReferralInitListener branchReferralInitListener, ServerRequest.PROCESS_WAIT_LOCK pROCESS_WAIT_LOCK) {
        ServerRequestInitSession serverRequestInitSession = this.hasUser() ? new ServerRequestRegisterOpen(this.context_, branchReferralInitListener, this.systemObserver_) : new ServerRequestRegisterInstall(this.context_, branchReferralInitListener, this.systemObserver_, InstallListener.getInstallationID());
        serverRequestInitSession.addProcessWaitLock(pROCESS_WAIT_LOCK);
        if (this.isGAParamsFetchInProgress_) {
            serverRequestInitSession.addProcessWaitLock(ServerRequest.PROCESS_WAIT_LOCK.GAID_FETCH_WAIT_LOCK);
        }
        if (this.intentState_ != INTENT_STATE.READY) {
            serverRequestInitSession.addProcessWaitLock(ServerRequest.PROCESS_WAIT_LOCK.INTENT_PENDING_WAIT_LOCK);
        }
        if (checkInstallReferrer_ && serverRequestInitSession instanceof ServerRequestRegisterInstall) {
            serverRequestInitSession.addProcessWaitLock(ServerRequest.PROCESS_WAIT_LOCK.INSTALL_REFERRER_FETCH_WAIT_LOCK);
            InstallListener.captureInstallReferrer(playStoreReferrerFetchTime);
        }
        this.registerInstallOrOpen(serverRequestInitSession, branchReferralInitListener);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void registerInstallOrOpen(ServerRequest serverRequest, BranchReferralInitListener branchReferralInitListener) {
        if (!this.requestQueue_.containsInstallOrOpen()) {
            this.insertRequestAtFront(serverRequest);
        } else {
            if (branchReferralInitListener != null) {
                this.requestQueue_.setInstallOrOpenCallback(branchReferralInitListener);
            }
            this.requestQueue_.moveInstallOrOpenToFront(serverRequest, this.networkCount_, branchReferralInitListener);
        }
        this.processNextQueueItem();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @TargetApi(value=14)
    private void setActivityLifeCycleObserver(Application var1_1) {
        try {
            var2_4 = new BranchActivityLifeCycleObserver();
            var1_1.unregisterActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)var2_4);
            var1_1.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)var2_4);
            Branch.isActivityLifeCycleCallbackRegistered_ = true;
            return;
        }
        catch (NoClassDefFoundError var1_2) {}
        ** GOTO lbl-1000
        catch (NoSuchMethodError var1_3) {}
lbl-1000:
        // 2 sources
        {
            Branch.isActivityLifeCycleCallbackRegistered_ = false;
            Branch.isAutoSessionMode_ = false;
            Log.w((String)"BranchSDK", (String)new BranchError("", -108).getMessage());
            return;
        }
    }

    private void startSession(Activity activity) {
        Uri uri = null;
        if (activity.getIntent() != null) {
            uri = activity.getIntent().getData();
        }
        this.initSessionWithData(uri, activity);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateAllRequestsInQueue() {
        try {
            for (int n = 0; n < this.requestQueue_.getSize(); ++n) {
                JSONObject jSONObject;
                ServerRequest serverRequest = this.requestQueue_.peekAt(n);
                if (serverRequest == null || (jSONObject = serverRequest.getPost()) == null) continue;
                if (jSONObject.has(Defines.Jsonkey.SessionID.getKey())) {
                    serverRequest.getPost().put(Defines.Jsonkey.SessionID.getKey(), (Object)this.prefHelper_.getSessionID());
                }
                if (jSONObject.has(Defines.Jsonkey.IdentityID.getKey())) {
                    serverRequest.getPost().put(Defines.Jsonkey.IdentityID.getKey(), (Object)this.prefHelper_.getIdentityID());
                }
                if (!jSONObject.has(Defines.Jsonkey.DeviceFingerprintID.getKey())) continue;
                serverRequest.getPost().put(Defines.Jsonkey.DeviceFingerprintID.getKey(), (Object)this.prefHelper_.getDeviceFingerPrintID());
            }
            return;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
        }
    }

    public void addExtraInstrumentationData(String string2, String string3) {
        this.instrumentationExtraData_.put(string2, string3);
    }

    public JSONObject getFirstReferringParams() {
        return this.appendDebugParams(this.convertParamsStringToDictionary(this.prefHelper_.getInstallParams()));
    }

    public JSONObject getLatestReferringParams() {
        return this.appendDebugParams(this.convertParamsStringToDictionary(this.prefHelper_.getSessionParams()));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void handleNewRequest(ServerRequest serverRequest) {
        boolean bl = true;
        if (this.initState_ != SESSION_STATE.INITIALISED && !(serverRequest instanceof ServerRequestInitSession)) {
            if (serverRequest instanceof ServerRequestLogout) {
                serverRequest.handleFailure(-101, "");
                Log.i((String)"BranchSDK", (String)"Branch is not initialized, cannot logout");
                return;
            }
            if (serverRequest instanceof ServerRequestRegisterClose) {
                Log.i((String)"BranchSDK", (String)"Branch is not initialized, cannot close session");
                return;
            }
            Activity activity = null;
            if (this.currentActivityReference_ != null) {
                activity = (Activity)this.currentActivityReference_.get();
            }
            if (customReferrableSettings_ == CUSTOM_REFERRABLE_SETTINGS.USE_DEFAULT) {
                this.initUserSessionInternal(null, activity, true);
            } else {
                if (customReferrableSettings_ != CUSTOM_REFERRABLE_SETTINGS.REFERRABLE) {
                    bl = false;
                }
                this.initUserSessionInternal(null, activity, bl);
            }
        }
        this.requestQueue_.enqueue(serverRequest);
        serverRequest.onRequestQueued();
        this.processNextQueueItem();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean initSession(BranchReferralInitListener branchReferralInitListener, Activity activity) {
        if (customReferrableSettings_ == CUSTOM_REFERRABLE_SETTINGS.USE_DEFAULT) {
            this.initUserSessionInternal(branchReferralInitListener, activity, true);
            return true;
        }
        boolean bl = customReferrableSettings_ == CUSTOM_REFERRABLE_SETTINGS.REFERRABLE;
        this.initUserSessionInternal(branchReferralInitListener, activity, bl);
        return true;
    }

    public boolean initSessionWithData(Uri uri, Activity activity) {
        this.readAndStripParam(uri, activity);
        return this.initSession(null, activity);
    }

    @Override
    public void onBranchViewAccepted(String string2, String string3) {
        if (ServerRequestInitSession.isInitSessionAction(string2)) {
            this.checkForAutoDeepLinkConfiguration();
        }
    }

    @Override
    public void onBranchViewCancelled(String string2, String string3) {
        if (ServerRequestInitSession.isInitSessionAction(string2)) {
            this.checkForAutoDeepLinkConfiguration();
        }
    }

    @Override
    public void onBranchViewError(int n, String string2, String string3) {
        if (ServerRequestInitSession.isInitSessionAction(string3)) {
            this.checkForAutoDeepLinkConfiguration();
        }
    }

    @Override
    public void onBranchViewVisible(String string2, String string3) {
    }

    @Override
    public void onGAdsFetchFinished() {
        this.isGAParamsFetchInProgress_ = false;
        this.requestQueue_.unlockProcessWait(ServerRequest.PROCESS_WAIT_LOCK.GAID_FETCH_WAIT_LOCK);
        if (this.performCookieBasedStrongMatchingOnGAIDAvailable) {
            this.performCookieBasedStrongMatch();
            this.performCookieBasedStrongMatchingOnGAIDAvailable = false;
            return;
        }
        this.processNextQueueItem();
    }

    @Override
    public void onInstallReferrerEventsFinished() {
        this.requestQueue_.unlockProcessWait(ServerRequest.PROCESS_WAIT_LOCK.INSTALL_REFERRER_FETCH_WAIT_LOCK);
        this.processNextQueueItem();
    }

    @TargetApi(value=14)
    private class BranchActivityLifeCycleObserver
    implements Application.ActivityLifecycleCallbacks {
        private int activityCnt_ = 0;

        private BranchActivityLifeCycleObserver() {
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public void onActivityCreated(Activity activity, Bundle object) {
            void var2_4;
            Branch branch = Branch.this;
            if (Branch.this.handleDelayedNewIntents_) {
                INTENT_STATE iNTENT_STATE = INTENT_STATE.PENDING;
            } else {
                INTENT_STATE iNTENT_STATE = INTENT_STATE.READY;
            }
            branch.intentState_ = (INTENT_STATE)var2_4;
            if (BranchViewHandler.getInstance().isInstallOrOpenBranchViewPending(activity.getApplicationContext())) {
                BranchViewHandler.getInstance().showPendingBranchView((Context)activity);
            }
        }

        public void onActivityDestroyed(Activity activity) {
            if (Branch.this.currentActivityReference_ != null && Branch.this.currentActivityReference_.get() == activity) {
                Branch.this.currentActivityReference_.clear();
            }
            BranchViewHandler.getInstance().onCurrentActivityDestroyed(activity);
        }

        public void onActivityPaused(Activity activity) {
            if (Branch.this.shareLinkManager_ != null) {
                Branch.this.shareLinkManager_.cancelShareLinkDialog(true);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onActivityResumed(Activity activity) {
            if (Branch.this.checkIntentForSessionRestart(activity.getIntent())) {
                Branch.this.initState_ = SESSION_STATE.UNINITIALISED;
                Branch.this.startSession(activity);
            }
            Branch.this.currentActivityReference_ = new WeakReference<Activity>(activity);
            if (Branch.this.handleDelayedNewIntents_) {
                Branch.this.intentState_ = INTENT_STATE.READY;
                boolean bl = activity.getIntent() != null && Branch.this.initState_ != SESSION_STATE.INITIALISED;
                Branch.this.onIntentReady(activity, bl);
            }
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onActivityStarted(Activity activity) {
            Branch branch = Branch.this;
            INTENT_STATE iNTENT_STATE = Branch.this.handleDelayedNewIntents_ ? INTENT_STATE.PENDING : INTENT_STATE.READY;
            branch.intentState_ = iNTENT_STATE;
            if (Branch.this.initState_ == SESSION_STATE.INITIALISED) {
                try {
                    ContentDiscoverer.getInstance().discoverContent(activity, Branch.this.sessionReferredLink_);
                }
                catch (Exception exception) {}
            }
            if (this.activityCnt_ < 1) {
                if (Branch.this.initState_ == SESSION_STATE.INITIALISED) {
                    Branch.this.initState_ = SESSION_STATE.UNINITIALISED;
                }
                if (BranchUtil.isTestModeEnabled(Branch.this.context_)) {
                    Branch.this.prefHelper_.setExternDebug();
                }
                Branch.this.prefHelper_.setLogging(Branch.getIsLogging());
                Branch.this.startSession(activity);
            } else if (Branch.this.checkIntentForSessionRestart(activity.getIntent())) {
                Branch.this.initState_ = SESSION_STATE.UNINITIALISED;
                Branch.this.startSession(activity);
            }
            ++this.activityCnt_;
        }

        public void onActivityStopped(Activity activity) {
            ContentDiscoverer.getInstance().onActivityStopped(activity);
            --this.activityCnt_;
            if (this.activityCnt_ < 1) {
                Branch.this.closeSessionInternal();
            }
        }
    }

    public static interface BranchLinkCreateListener {
        public void onLinkCreate(String var1, BranchError var2);
    }

    public static interface BranchListResponseListener {
        public void onReceivingResponse(JSONArray var1, BranchError var2);
    }

    private class BranchPostTask
    extends BranchAsyncTask<Void, Void, ServerResponse> {
        ServerRequest thisReq_;

        public BranchPostTask(ServerRequest serverRequest) {
            this.thisReq_ = serverRequest;
        }

        protected ServerResponse doInBackground(Void ... arrvoid) {
            if (this.thisReq_ instanceof ServerRequestInitSession) {
                ((ServerRequestInitSession)this.thisReq_).updateLinkReferrerParams();
            }
            Branch.this.addExtraInstrumentationData(this.thisReq_.getRequestPath() + "-" + Defines.Jsonkey.Queue_Wait_Time.getKey(), String.valueOf(this.thisReq_.getQueueWaitTime()));
            if (this.thisReq_.isGAdsParamsRequired() && !BranchUtil.isTestModeEnabled(Branch.this.context_)) {
                this.thisReq_.updateGAdsParams(Branch.this.systemObserver_);
            }
            if (this.thisReq_.isGetRequest()) {
                return Branch.this.branchRemoteInterface_.make_restful_get(this.thisReq_.getRequestUrl(), this.thisReq_.getGetParams(), this.thisReq_.getRequestPath(), Branch.this.prefHelper_.getBranchKey());
            }
            return Branch.this.branchRemoteInterface_.make_restful_post(this.thisReq_.getPostWithInstrumentationValues(Branch.this.instrumentationExtraData_), this.thisReq_.getRequestUrl(), this.thisReq_.getRequestPath(), Branch.this.prefHelper_.getBranchKey());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void onPostExecute(ServerResponse serverResponse) {
            block30: {
                block29: {
                    int n;
                    String string2;
                    block28: {
                        Object object;
                        super.onPostExecute((Object)serverResponse);
                        if (serverResponse == null) break block30;
                        try {
                            n = serverResponse.getStatusCode();
                            Branch.this.hasNetwork_ = true;
                            if (n == 200) break block28;
                            if (this.thisReq_ instanceof ServerRequestInitSession) {
                                Branch.this.initState_ = SESSION_STATE.UNINITIALISED;
                            }
                            if (n == 409) {
                                Branch.this.requestQueue_.remove(this.thisReq_);
                                if (this.thisReq_ instanceof ServerRequestCreateUrl) {
                                    ((ServerRequestCreateUrl)this.thisReq_).handleDuplicateURLError();
                                } else {
                                    Log.i((String)"BranchSDK", (String)"Branch API Error: Conflicting resource error code from API");
                                    Branch.this.handleFailure(0, n);
                                }
                                break block29;
                            }
                            Branch.this.hasNetwork_ = false;
                            object = new ArrayList<ServerRequest>();
                            for (int i = 0; i < Branch.this.requestQueue_.getSize(); ++i) {
                                ((ArrayList)object).add(Branch.this.requestQueue_.peekAt(i));
                            }
                        }
                        catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            return;
                        }
                        Object object2 = ((ArrayList)object).iterator();
                        while (object2.hasNext()) {
                            ServerRequest serverRequest = (ServerRequest)object2.next();
                            if (serverRequest != null && serverRequest.shouldRetryOnFail()) continue;
                            Branch.this.requestQueue_.remove(serverRequest);
                        }
                        Branch.this.networkCount_ = 0;
                        object = ((ArrayList)object).iterator();
                        while (object.hasNext()) {
                            object2 = (ServerRequest)object.next();
                            if (object2 == null) continue;
                            ((ServerRequest)object2).handleFailure(n, serverResponse.getFailReason());
                            if (!((ServerRequest)object2).shouldRetryOnFail()) continue;
                            ((ServerRequest)object2).clearCallbacks();
                        }
                        break block29;
                    }
                    Branch.this.hasNetwork_ = true;
                    if (this.thisReq_ instanceof ServerRequestCreateUrl) {
                        if (serverResponse.getObject() != null) {
                            string2 = serverResponse.getObject().getString("url");
                            Branch.this.linkCache_.put(((ServerRequestCreateUrl)this.thisReq_).getLinkPost(), string2);
                        }
                    } else if (this.thisReq_ instanceof ServerRequestLogout) {
                        Branch.this.linkCache_.clear();
                        Branch.this.requestQueue_.clear();
                    }
                    Branch.this.requestQueue_.dequeue();
                    if (this.thisReq_ instanceof ServerRequestInitSession || this.thisReq_ instanceof ServerRequestIdentifyUserRequest) {
                        string2 = serverResponse.getObject();
                        if (string2 != null) {
                            int n2 = 0;
                            if (string2.has(Defines.Jsonkey.SessionID.getKey())) {
                                Branch.this.prefHelper_.setSessionID(string2.getString(Defines.Jsonkey.SessionID.getKey()));
                                n2 = 1;
                            }
                            n = n2;
                            if (string2.has(Defines.Jsonkey.IdentityID.getKey())) {
                                String string3 = string2.getString(Defines.Jsonkey.IdentityID.getKey());
                                n = n2;
                                if (!Branch.this.prefHelper_.getIdentityID().equals(string3)) {
                                    Branch.this.linkCache_.clear();
                                    Branch.this.prefHelper_.setIdentityID(string2.getString(Defines.Jsonkey.IdentityID.getKey()));
                                    n = 1;
                                }
                            }
                            if (string2.has(Defines.Jsonkey.DeviceFingerprintID.getKey())) {
                                Branch.this.prefHelper_.setDeviceFingerPrintID(string2.getString(Defines.Jsonkey.DeviceFingerprintID.getKey()));
                                n = 1;
                            }
                            if (n != 0) {
                                Branch.this.updateAllRequestsInQueue();
                            }
                            if (this.thisReq_ instanceof ServerRequestInitSession) {
                                Branch.this.initState_ = SESSION_STATE.INITIALISED;
                                this.thisReq_.onRequestSucceeded(serverResponse, branchReferral_);
                                Branch.this.isInitReportedThroughCallBack = ((ServerRequestInitSession)this.thisReq_).hasCallBack();
                                if (!((ServerRequestInitSession)this.thisReq_).handleBranchViewIfAvailable(serverResponse)) {
                                    Branch.this.checkForAutoDeepLinkConfiguration();
                                }
                                if (Branch.this.getLatestReferringParamsLatch != null) {
                                    Branch.this.getLatestReferringParamsLatch.countDown();
                                }
                                if (Branch.this.getFirstReferringParamsLatch != null) {
                                    Branch.this.getFirstReferringParamsLatch.countDown();
                                }
                            } else {
                                this.thisReq_.onRequestSucceeded(serverResponse, branchReferral_);
                            }
                        }
                    } else {
                        this.thisReq_.onRequestSucceeded(serverResponse, branchReferral_);
                    }
                }
                Branch.this.networkCount_ = 0;
                if (Branch.this.hasNetwork_ && Branch.this.initState_ != SESSION_STATE.UNINITIALISED) {
                    Branch.this.processNextQueueItem();
                    return;
                }
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.thisReq_.onPreExecute();
        }
    }

    public static interface BranchReferralInitListener {
        public void onInitFinished(JSONObject var1, BranchError var2);
    }

    public static interface BranchReferralStateChangedListener {
        public void onStateChanged(boolean var1, BranchError var2);
    }

    private static enum CUSTOM_REFERRABLE_SETTINGS {
        USE_DEFAULT,
        REFERRABLE,
        NON_REFERRABLE;

    }

    public static interface IBranchViewControl {
        public boolean skipBranchViewsOnThisActivity();
    }

    private static enum INTENT_STATE {
        PENDING,
        READY;

    }

    public static interface LogoutStatusListener {
        public void onLogoutFinished(boolean var1, BranchError var2);
    }

    private static enum SESSION_STATE {
        INITIALISED,
        INITIALISING,
        UNINITIALISED;

    }

}

