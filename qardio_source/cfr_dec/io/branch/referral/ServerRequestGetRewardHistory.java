/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.content.Context;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.ServerRequest;
import io.branch.referral.ServerResponse;
import org.json.JSONArray;
import org.json.JSONObject;

class ServerRequestGetRewardHistory
extends ServerRequest {
    Branch.BranchListResponseListener callback_;

    public ServerRequestGetRewardHistory(String string2, JSONObject jSONObject, Context context) {
        super(string2, jSONObject, context);
    }

    @Override
    public void clearCallbacks() {
        this.callback_ = null;
    }

    @Override
    public void handleFailure(int n, String string2) {
        if (this.callback_ != null) {
            this.callback_.onReceivingResponse(null, new BranchError("Trouble retrieving user credit history. " + string2, n));
        }
    }

    @Override
    public boolean isGetRequest() {
        return false;
    }

    @Override
    public void onRequestSucceeded(ServerResponse serverResponse, Branch branch) {
        if (this.callback_ != null) {
            this.callback_.onReceivingResponse(serverResponse.getArray(), null);
        }
    }
}

