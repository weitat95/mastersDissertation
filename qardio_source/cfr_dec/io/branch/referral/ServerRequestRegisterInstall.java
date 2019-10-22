/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.content.Context;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.Defines;
import io.branch.referral.PrefHelper;
import io.branch.referral.ServerRequestInitSession;
import io.branch.referral.ServerResponse;
import io.branch.referral.SystemObserver;
import org.json.JSONException;
import org.json.JSONObject;

class ServerRequestRegisterInstall
extends ServerRequestInitSession {
    Branch.BranchReferralInitListener callback_;
    final SystemObserver systemObserver_;

    public ServerRequestRegisterInstall(Context context, Branch.BranchReferralInitListener object, SystemObserver systemObserver, String string2) {
        super(context, Defines.RequestPath.RegisterInstall.getPath());
        this.systemObserver_ = systemObserver;
        this.callback_ = object;
        context = new JSONObject();
        try {
            if (!string2.equals("bnc_no_value")) {
                context.put(Defines.Jsonkey.LinkClickID.getKey(), (Object)string2);
            }
            if (!systemObserver.getAppVersion().equals("bnc_no_value")) {
                context.put(Defines.Jsonkey.AppVersion.getKey(), (Object)systemObserver.getAppVersion());
            }
            if (this.prefHelper_.getExternDebug() && !((String)(object = systemObserver.getURIScheme())).equals("bnc_no_value")) {
                context.put(Defines.Jsonkey.URIScheme.getKey(), object);
            }
            context.put(Defines.Jsonkey.FaceBookAppLinkChecked.getKey(), this.prefHelper_.getIsAppLinkTriggeredInit());
            context.put(Defines.Jsonkey.IsReferrable.getKey(), this.prefHelper_.getIsReferrable());
            context.put(Defines.Jsonkey.Update.getKey(), systemObserver.getUpdateState());
            context.put(Defines.Jsonkey.Debug.getKey(), this.prefHelper_.getExternDebug());
            this.setPost((JSONObject)context);
            return;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            this.constructError_ = true;
            return;
        }
    }

    public ServerRequestRegisterInstall(String string2, JSONObject jSONObject, Context context) {
        super(string2, jSONObject, context);
        this.systemObserver_ = new SystemObserver(context);
    }

    @Override
    public void clearCallbacks() {
        this.callback_ = null;
    }

    @Override
    public String getRequestActionName() {
        return "install";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void handleFailure(int n, String string2) {
        if (this.callback_ != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("error_message", (Object)"Trouble reaching server. Please try again in a few minutes");
            }
            catch (JSONException jSONException) {
                jSONException.printStackTrace();
            }
            this.callback_.onInitFinished(jSONObject, new BranchError("Trouble initializing Branch. " + string2, n));
        }
    }

    @Override
    public boolean hasCallBack() {
        return this.callback_ != null;
    }

    @Override
    public boolean isGetRequest() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onRequestSucceeded(ServerResponse serverResponse, Branch branch) {
        super.onRequestSucceeded(serverResponse, branch);
        try {
            Object object;
            this.prefHelper_.setUserURL(serverResponse.getObject().getString(Defines.Jsonkey.Link.getKey()));
            if (serverResponse.getObject().has(Defines.Jsonkey.Data.getKey()) && (object = new JSONObject(serverResponse.getObject().getString(Defines.Jsonkey.Data.getKey()))).has(Defines.Jsonkey.Clicked_Branch_Link.getKey()) && object.getBoolean(Defines.Jsonkey.Clicked_Branch_Link.getKey()) && this.prefHelper_.getInstallParams().equals("bnc_no_value") && this.prefHelper_.getIsReferrable() == 1) {
                object = serverResponse.getObject().getString(Defines.Jsonkey.Data.getKey());
                this.prefHelper_.setInstallParams((String)object);
            }
            if (serverResponse.getObject().has(Defines.Jsonkey.LinkClickID.getKey())) {
                this.prefHelper_.setLinkClickID(serverResponse.getObject().getString(Defines.Jsonkey.LinkClickID.getKey()));
            } else {
                this.prefHelper_.setLinkClickID("bnc_no_value");
            }
            if (serverResponse.getObject().has(Defines.Jsonkey.Data.getKey())) {
                object = serverResponse.getObject().getString(Defines.Jsonkey.Data.getKey());
                this.prefHelper_.setSessionParams((String)object);
            } else {
                this.prefHelper_.setSessionParams("bnc_no_value");
            }
            if (this.callback_ != null) {
                this.callback_.onInitFinished(branch.getLatestReferringParams(), null);
            }
            this.prefHelper_.setAppVersion(this.systemObserver_.getAppVersion());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.onInitSessionCompleted(serverResponse, branch);
    }

    public void setInitFinishedCallback(Branch.BranchReferralInitListener branchReferralInitListener) {
        if (branchReferralInitListener != null) {
            this.callback_ = branchReferralInitListener;
        }
    }
}

