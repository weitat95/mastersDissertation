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

class ServerRequestRedeemRewards
extends ServerRequest {
    int actualNumOfCreditsToRedeem_ = 0;
    Branch.BranchReferralStateChangedListener callback_;

    public ServerRequestRedeemRewards(String string2, JSONObject jSONObject, Context context) {
        super(string2, jSONObject, context);
    }

    @Override
    public void clearCallbacks() {
        this.callback_ = null;
    }

    @Override
    public void handleFailure(int n, String string2) {
        if (this.callback_ != null) {
            this.callback_.onStateChanged(false, new BranchError("Trouble redeeming rewards. " + string2, n));
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
    public void onRequestSucceeded(ServerResponse object, Branch branch) {
        boolean bl = false;
        boolean bl2 = false;
        object = this.getPost();
        boolean bl3 = bl2;
        if (object != null) {
            bl3 = bl2;
            if (object.has(Defines.Jsonkey.Bucket.getKey())) {
                bl3 = bl2;
                if (object.has(Defines.Jsonkey.Amount.getKey())) {
                    bl2 = bl;
                    try {
                        int n = object.getInt(Defines.Jsonkey.Amount.getKey());
                        bl2 = bl;
                        object = object.getString(Defines.Jsonkey.Bucket.getKey());
                        bl3 = n > 0;
                        bl2 = bl3;
                        int n2 = this.prefHelper_.getCreditCount((String)object);
                        bl2 = bl3;
                        this.prefHelper_.setCreditCount((String)object, n2 - n);
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        bl3 = bl2;
                    }
                }
            }
        }
        if (this.callback_ != null) {
            object = bl3 ? null : new BranchError("Trouble redeeming rewards.", -107);
            this.callback_.onStateChanged(bl3, (BranchError)object);
        }
    }
}

