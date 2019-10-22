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
import io.branch.referral.Branch;
import io.branch.referral.BranchViewHandler;
import io.branch.referral.Defines;
import io.branch.referral.ServerRequest;
import io.branch.referral.ServerResponse;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

class ServerRequestActionCompleted
extends ServerRequest {
    private final BranchViewHandler.IBranchViewEvents callback_ = null;

    public ServerRequestActionCompleted(String string2, JSONObject jSONObject, Context context) {
        super(string2, jSONObject, context);
    }

    @Override
    public void clearCallbacks() {
    }

    @Override
    public void handleFailure(int n, String string2) {
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
    public void onRequestSucceeded(ServerResponse serverResponse, Branch object) {
        if (serverResponse.getObject() == null || !serverResponse.getObject().has(Defines.Jsonkey.BranchViewData.getKey()) || Branch.getInstance().currentActivityReference_ == null || Branch.getInstance().currentActivityReference_.get() == null) return;
        String string2 = "";
        Object object2 = string2;
        try {
            JSONObject jSONObject = this.getPost();
            object = string2;
            if (jSONObject != null) {
                object = string2;
                object2 = string2;
                if (jSONObject.has(Defines.Jsonkey.Event.getKey())) {
                    object2 = string2;
                    object = jSONObject.getString(Defines.Jsonkey.Event.getKey());
                }
            }
            object2 = object;
            if (Branch.getInstance().currentActivityReference_ == null) return;
            {
                object2 = object;
                string2 = (Activity)Branch.getInstance().currentActivityReference_.get();
                object2 = object;
                serverResponse = serverResponse.getObject().getJSONObject(Defines.Jsonkey.BranchViewData.getKey());
                object2 = object;
                BranchViewHandler.getInstance().showBranchView((JSONObject)serverResponse, (String)object, (Context)string2, this.callback_);
                return;
            }
        }
        catch (JSONException jSONException) {
            if (this.callback_ == null) return;
            this.callback_.onBranchViewError(-201, "Unable to show branch view. Branch view received is invalid ", (String)object2);
            return;
        }
    }

    @Override
    public boolean shouldRetryOnFail() {
        return true;
    }
}

