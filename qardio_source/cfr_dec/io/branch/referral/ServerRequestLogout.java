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
import io.branch.referral.ServerRequest;
import io.branch.referral.ServerResponse;
import org.json.JSONException;
import org.json.JSONObject;

class ServerRequestLogout
extends ServerRequest {
    private Branch.LogoutStatusListener callback_;

    public ServerRequestLogout(String string2, JSONObject jSONObject, Context context) {
        super(string2, jSONObject, context);
    }

    @Override
    public void clearCallbacks() {
        this.callback_ = null;
    }

    @Override
    public void handleFailure(int n, String string2) {
        if (this.callback_ != null) {
            this.callback_.onLogoutFinished(false, new BranchError("Logout error. " + string2, n));
        }
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
        try {
            this.prefHelper_.setSessionID(serverResponse.getObject().getString(Defines.Jsonkey.SessionID.getKey()));
            this.prefHelper_.setIdentityID(serverResponse.getObject().getString(Defines.Jsonkey.IdentityID.getKey()));
            this.prefHelper_.setUserURL(serverResponse.getObject().getString(Defines.Jsonkey.Link.getKey()));
            this.prefHelper_.setInstallParams("bnc_no_value");
            this.prefHelper_.setSessionParams("bnc_no_value");
            this.prefHelper_.setIdentity("bnc_no_value");
            this.prefHelper_.clearUserValues();
            return;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            return;
        }
        finally {
            if (this.callback_ == null) return;
            this.callback_.onLogoutFinished(true, null);
        }
    }
}

