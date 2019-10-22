/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.app.Activity;
import android.content.Context;
import io.branch.indexing.ContentDiscoverer;
import io.branch.indexing.ContentDiscoveryManifest;
import io.branch.referral.Branch;
import io.branch.referral.BranchViewHandler;
import io.branch.referral.Defines;
import io.branch.referral.ExtendedAnswerProvider;
import io.branch.referral.PrefHelper;
import io.branch.referral.ServerRequest;
import io.branch.referral.ServerRequestRegisterInstall;
import io.branch.referral.ServerResponse;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

abstract class ServerRequestInitSession
extends ServerRequest {
    private final ContentDiscoveryManifest contentDiscoveryManifest_;
    private final Context context_;

    ServerRequestInitSession(Context context, String string2) {
        super(context, string2);
        this.context_ = context;
        this.contentDiscoveryManifest_ = ContentDiscoveryManifest.getInstance(this.context_);
    }

    ServerRequestInitSession(String string2, JSONObject jSONObject, Context context) {
        super(string2, jSONObject, context);
        this.context_ = context;
        this.contentDiscoveryManifest_ = ContentDiscoveryManifest.getInstance(this.context_);
    }

    static boolean isInitSessionAction(String string2) {
        block3: {
            boolean bl;
            block2: {
                bl = false;
                if (string2 == null) break block2;
                if (!string2.equalsIgnoreCase("open") && !string2.equalsIgnoreCase("install")) break block3;
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public abstract String getRequestActionName();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean handleBranchViewIfAvailable(ServerResponse serverResponse) {
        if (serverResponse == null) return false;
        if (serverResponse.getObject() == null) return false;
        if (!serverResponse.getObject().has(Defines.Jsonkey.BranchViewData.getKey())) return false;
        try {
            serverResponse = serverResponse.getObject().getJSONObject(Defines.Jsonkey.BranchViewData.getKey());
            String string2 = this.getRequestActionName();
            if (Branch.getInstance().currentActivityReference_ == null) return BranchViewHandler.getInstance().markInstallOrOpenBranchViewPending((JSONObject)serverResponse, string2);
            if (Branch.getInstance().currentActivityReference_.get() == null) return BranchViewHandler.getInstance().markInstallOrOpenBranchViewPending((JSONObject)serverResponse, string2);
            Activity activity = (Activity)Branch.getInstance().currentActivityReference_.get();
            boolean bl = true;
            if (activity instanceof Branch.IBranchViewControl) {
                bl = !((Branch.IBranchViewControl)activity).skipBranchViewsOnThisActivity();
            }
            if (!bl) return BranchViewHandler.getInstance().markInstallOrOpenBranchViewPending((JSONObject)serverResponse, string2);
            return BranchViewHandler.getInstance().showBranchView((JSONObject)serverResponse, string2, (Context)activity, Branch.getInstance());
        }
        catch (JSONException jSONException) {
            // empty catch block
        }
        return false;
    }

    public abstract boolean hasCallBack();

    @Override
    public boolean isGAdsParamsRequired() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void onInitSessionCompleted(ServerResponse serverResponse, Branch branch) {
        if (this.contentDiscoveryManifest_ == null) return;
        this.contentDiscoveryManifest_.onBranchInitialised(serverResponse.getObject());
        if (branch.currentActivityReference_ == null) return;
        try {
            ContentDiscoverer.getInstance().onSessionStarted((Activity)branch.currentActivityReference_.get(), branch.sessionReferredLink_);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    @Override
    public void onPreExecute() {
        JSONObject jSONObject = this.getPost();
        try {
            if (!this.prefHelper_.getAppLink().equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.AndroidAppLinkURL.getKey(), (Object)this.prefHelper_.getAppLink());
            }
            if (!this.prefHelper_.getPushIdentifier().equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.AndroidPushIdentifier.getKey(), (Object)this.prefHelper_.getPushIdentifier());
            }
            if (!this.prefHelper_.getExternalIntentUri().equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.External_Intent_URI.getKey(), (Object)this.prefHelper_.getExternalIntentUri());
            }
            if (!this.prefHelper_.getExternalIntentExtra().equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.External_Intent_Extra.getKey(), (Object)this.prefHelper_.getExternalIntentExtra());
            }
            if (this.contentDiscoveryManifest_ != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("mv", (Object)this.contentDiscoveryManifest_.getManifestVersion());
                jSONObject2.put("pn", (Object)this.context_.getPackageName());
                jSONObject.put("cd", (Object)jSONObject2);
            }
            return;
        }
        catch (JSONException jSONException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onRequestSucceeded(ServerResponse object, Branch branch) {
        try {
            this.prefHelper_.setLinkClickIdentifier("bnc_no_value");
            this.prefHelper_.setGoogleSearchInstallIdentifier("bnc_no_value");
            this.prefHelper_.setGooglePlayReferrer("bnc_no_value");
            this.prefHelper_.setExternalIntentUri("bnc_no_value");
            this.prefHelper_.setExternalIntentExtra("bnc_no_value");
            this.prefHelper_.setAppLink("bnc_no_value");
            this.prefHelper_.setPushIdentifier("bnc_no_value");
            this.prefHelper_.setIsAppLinkTriggeredInit(false);
            this.prefHelper_.setInstallReferrerParams("bnc_no_value");
            this.prefHelper_.setIsFullAppConversion(false);
            if (((ServerResponse)object).getObject() == null || !((ServerResponse)object).getObject().has(Defines.Jsonkey.Data.getKey()) || !(branch = new JSONObject(((ServerResponse)object).getObject().getString(Defines.Jsonkey.Data.getKey()))).optBoolean(Defines.Jsonkey.Clicked_Branch_Link.getKey())) return;
            {
                object = this instanceof ServerRequestRegisterInstall ? "Branch Install" : "Branch Open";
                new ExtendedAnswerProvider().provideData((String)object, (JSONObject)branch, this.prefHelper_.getIdentityID());
                return;
            }
        }
        catch (JSONException jSONException) {
            // empty catch block
        }
    }

    @Override
    protected void setPost(JSONObject jSONObject) {
        super.setPost(jSONObject);
        this.updateEnvironment(this.context_, jSONObject);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void updateLinkReferrerParams() {
        String string2 = this.prefHelper_.getLinkClickIdentifier();
        if (!string2.equals("bnc_no_value")) {
            try {
                this.getPost().put(Defines.Jsonkey.LinkIdentifier.getKey(), (Object)string2);
            }
            catch (JSONException jSONException) {}
        }
        if (!(string2 = this.prefHelper_.getGoogleSearchInstallIdentifier()).equals("bnc_no_value")) {
            try {
                this.getPost().put(Defines.Jsonkey.GoogleSearchInstallReferrer.getKey(), (Object)string2);
            }
            catch (JSONException jSONException) {}
        }
        if (!(string2 = this.prefHelper_.getGooglePlayReferrer()).equals("bnc_no_value")) {
            try {
                this.getPost().put(Defines.Jsonkey.GooglePlayInstallReferrer.getKey(), (Object)string2);
            }
            catch (JSONException jSONException) {}
        }
        if (!this.prefHelper_.isFullAppConversion()) return;
        try {
            this.getPost().put(Defines.Jsonkey.AndroidAppLinkURL.getKey(), (Object)this.prefHelper_.getAppLink());
            this.getPost().put(Defines.Jsonkey.IsFullAppConv.getKey(), true);
            return;
        }
        catch (JSONException jSONException) {
            return;
        }
    }
}

