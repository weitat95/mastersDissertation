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

class ServerRequestIdentifyUserRequest
extends ServerRequest {
    Branch.BranchReferralInitListener callback_;
    String userId_ = null;

    public ServerRequestIdentifyUserRequest(String string2, JSONObject jSONObject, Context context) {
        super(string2, jSONObject, context);
    }

    @Override
    public void clearCallbacks() {
        this.callback_ = null;
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
            this.callback_.onInitFinished(jSONObject, new BranchError("Trouble setting the user alias. " + string2, n));
        }
    }

    @Override
    public boolean isGetRequest() {
        return false;
    }

    @Override
    public void onRequestSucceeded(ServerResponse object, Branch branch) {
        try {
            if (this.getPost() != null && this.getPost().has(Defines.Jsonkey.Identity.getKey())) {
                this.prefHelper_.setIdentity(this.getPost().getString(Defines.Jsonkey.Identity.getKey()));
            }
            this.prefHelper_.setIdentityID(((ServerResponse)object).getObject().getString(Defines.Jsonkey.IdentityID.getKey()));
            this.prefHelper_.setUserURL(((ServerResponse)object).getObject().getString(Defines.Jsonkey.Link.getKey()));
            if (((ServerResponse)object).getObject().has(Defines.Jsonkey.ReferringData.getKey())) {
                object = ((ServerResponse)object).getObject().getString(Defines.Jsonkey.ReferringData.getKey());
                this.prefHelper_.setInstallParams((String)object);
            }
            if (this.callback_ != null) {
                this.callback_.onInitFinished(branch.getFirstReferringParams(), null);
            }
            return;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            return;
        }
    }

    @Override
    public boolean shouldRetryOnFail() {
        return true;
    }
}

