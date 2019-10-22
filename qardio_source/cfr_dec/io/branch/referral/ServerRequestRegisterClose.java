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
import io.branch.indexing.ContentDiscoverer;
import io.branch.referral.Branch;
import io.branch.referral.Defines;
import io.branch.referral.DeviceInfo;
import io.branch.referral.PrefHelper;
import io.branch.referral.ServerRequest;
import io.branch.referral.ServerResponse;
import org.json.JSONException;
import org.json.JSONObject;

class ServerRequestRegisterClose
extends ServerRequest {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ServerRequestRegisterClose(Context context) {
        super(context, Defines.RequestPath.RegisterClose.getPath());
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Defines.Jsonkey.DeviceFingerprintID.getKey(), (Object)this.prefHelper_.getDeviceFingerPrintID());
            jSONObject.put(Defines.Jsonkey.IdentityID.getKey(), (Object)this.prefHelper_.getIdentityID());
            jSONObject.put(Defines.Jsonkey.SessionID.getKey(), (Object)this.prefHelper_.getSessionID());
            if (!this.prefHelper_.getLinkClickID().equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.LinkClickID.getKey(), (Object)this.prefHelper_.getLinkClickID());
            }
            if ((context = ContentDiscoverer.getInstance().getContentDiscoverDataForCloseRequest(context)) != null) {
                jSONObject.put(Defines.Jsonkey.ContentDiscovery.getKey(), (Object)context);
            }
            if (DeviceInfo.getInstance() != null) {
                jSONObject.put(Defines.Jsonkey.AppVersion.getKey(), (Object)DeviceInfo.getInstance().getAppVersion());
            }
            this.setPost(jSONObject);
            return;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            this.constructError_ = true;
            return;
        }
    }

    public ServerRequestRegisterClose(String string2, JSONObject jSONObject, Context context) {
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

    @Override
    public void onRequestSucceeded(ServerResponse serverResponse, Branch branch) {
        this.prefHelper_.setSessionParams("bnc_no_value");
    }
}

