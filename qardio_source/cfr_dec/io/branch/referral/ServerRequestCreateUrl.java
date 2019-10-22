/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.content.Context;
import io.branch.referral.Base64;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.BranchLinkData;
import io.branch.referral.Defines;
import io.branch.referral.ExtendedAnswerProvider;
import io.branch.referral.PrefHelper;
import io.branch.referral.ServerRequest;
import io.branch.referral.ServerResponse;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import org.json.JSONObject;

class ServerRequestCreateUrl
extends ServerRequest {
    private Branch.BranchLinkCreateListener callback_;
    private boolean defaultToLongUrl_ = true;
    private boolean isAsync_ = true;
    private boolean isReqStartedFromBranchShareSheet_;
    private BranchLinkData linkPost_;

    public ServerRequestCreateUrl(String string2, JSONObject jSONObject, Context context) {
        super(string2, jSONObject, context);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String generateLongUrlWithParams(String string2) {
        String string3 = string2 + "?";
        Object object = this.linkPost_.getTags();
        string2 = string3;
        if (object != null) {
            object = object.iterator();
            do {
                string2 = string3;
                if (!object.hasNext()) break;
                string2 = (String)object.next();
                if (string2 == null || string2.length() <= 0) continue;
                string3 = string3 + (Object)((Object)Defines.LinkParam.Tags) + "=" + string2 + "&";
            } while (true);
        }
        object = this.linkPost_.getAlias();
        string3 = string2;
        if (object != null) {
            string3 = string2;
            if (((String)object).length() > 0) {
                string3 = string2 + (Object)((Object)Defines.LinkParam.Alias) + "=" + (String)object + "&";
            }
        }
        object = this.linkPost_.getChannel();
        string2 = string3;
        if (object != null) {
            string2 = string3;
            if (((String)object).length() > 0) {
                string2 = string3 + (Object)((Object)Defines.LinkParam.Channel) + "=" + (String)object + "&";
            }
        }
        object = this.linkPost_.getFeature();
        string3 = string2;
        if (object != null) {
            string3 = string2;
            if (((String)object).length() > 0) {
                string3 = string2 + (Object)((Object)Defines.LinkParam.Feature) + "=" + (String)object + "&";
            }
        }
        object = this.linkPost_.getStage();
        string2 = string3;
        if (object != null) {
            string2 = string3;
            if (((String)object).length() > 0) {
                string2 = string3 + (Object)((Object)Defines.LinkParam.Stage) + "=" + (String)object + "&";
            }
        }
        object = this.linkPost_.getCampaign();
        string3 = string2;
        if (object != null) {
            string3 = string2;
            if (((String)object).length() > 0) {
                string3 = string2 + (Object)((Object)Defines.LinkParam.Campaign) + "=" + (String)object + "&";
            }
        }
        long l = this.linkPost_.getType();
        string2 = string3 + (Object)((Object)Defines.LinkParam.Type) + "=" + l + "&";
        l = this.linkPost_.getDuration();
        string3 = string2 + (Object)((Object)Defines.LinkParam.Duration) + "=" + l + "&";
        object = this.linkPost_.getParams();
        string2 = string3;
        if (object == null) return string2;
        string2 = string3;
        if (((String)object).length() <= 0) return string2;
        string2 = Base64.encodeToString(((String)object).getBytes(), 2);
        try {
            string2 = URLEncoder.encode(string2, "UTF8");
            return string3 + "source=android&data=" + string2;
        }
        catch (Exception exception) {
            this.callback_.onLinkCreate(null, new BranchError("Trouble creating a URL.", -116));
            return string3;
        }
    }

    private void updateShareEventToFabric(String string2) {
        string2 = this.linkPost_.getLinkDataJsonObject();
        if (this.isReqStartedFromBranchShareSheet() && string2 != null) {
            new ExtendedAnswerProvider().provideData("Branch Share", (JSONObject)string2, this.prefHelper_.getIdentityID());
        }
    }

    @Override
    public void clearCallbacks() {
        this.callback_ = null;
    }

    public BranchLinkData getLinkPost() {
        return this.linkPost_;
    }

    public String getLongUrl() {
        if (!this.prefHelper_.getUserURL().equals("bnc_no_value")) {
            return this.generateLongUrlWithParams(this.prefHelper_.getUserURL());
        }
        return this.generateLongUrlWithParams("https://bnc.lt/a/" + this.prefHelper_.getBranchKey());
    }

    public void handleDuplicateURLError() {
        if (this.callback_ != null) {
            this.callback_.onLinkCreate(null, new BranchError("Trouble creating a URL.", -105));
        }
    }

    @Override
    public void handleFailure(int n, String string2) {
        if (this.callback_ != null) {
            String string3 = null;
            if (this.defaultToLongUrl_) {
                string3 = this.getLongUrl();
            }
            this.callback_.onLinkCreate(string3, new BranchError("Trouble creating a URL. " + string2, n));
        }
    }

    @Override
    public boolean isGetRequest() {
        return false;
    }

    boolean isReqStartedFromBranchShareSheet() {
        return this.isReqStartedFromBranchShareSheet_;
    }

    @Override
    public void onRequestSucceeded(ServerResponse object, Branch branch) {
        try {
            object = ((ServerResponse)object).getObject().getString("url");
            if (this.callback_ != null) {
                this.callback_.onLinkCreate((String)object, null);
            }
            this.updateShareEventToFabric((String)object);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }
}

