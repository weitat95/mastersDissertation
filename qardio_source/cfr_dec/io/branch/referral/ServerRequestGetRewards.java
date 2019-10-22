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
import io.branch.referral.PrefHelper;
import io.branch.referral.ServerRequest;
import io.branch.referral.ServerResponse;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

class ServerRequestGetRewards
extends ServerRequest {
    Branch.BranchReferralStateChangedListener callback_;

    public ServerRequestGetRewards(String string2, JSONObject jSONObject, Context context) {
        super(string2, jSONObject, context);
    }

    @Override
    public void clearCallbacks() {
        this.callback_ = null;
    }

    @Override
    public String getRequestUrl() {
        return super.getRequestUrl() + this.prefHelper_.getIdentityID();
    }

    @Override
    public void handleFailure(int n, String string2) {
        if (this.callback_ != null) {
            this.callback_.onStateChanged(false, new BranchError("Trouble retrieving user credits. " + string2, n));
        }
    }

    @Override
    public boolean isGetRequest() {
        return true;
    }

    @Override
    public void onRequestSucceeded(ServerResponse serverResponse, Branch object) {
        boolean bl = false;
        object = serverResponse.getObject().keys();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            boolean bl2 = bl;
            int n = serverResponse.getObject().getInt(string2);
            boolean bl3 = bl;
            bl2 = bl;
            if (n != this.prefHelper_.getCreditCount(string2)) {
                bl3 = true;
            }
            bl2 = bl3;
            try {
                this.prefHelper_.setCreditCount(string2, n);
                bl = bl3;
            }
            catch (JSONException jSONException) {
                jSONException.printStackTrace();
                bl = bl2;
            }
        }
        if (this.callback_ != null) {
            this.callback_.onStateChanged(bl, null);
        }
    }
}

