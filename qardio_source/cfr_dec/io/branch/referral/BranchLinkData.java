/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import io.branch.referral.Defines;
import java.util.Collection;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class BranchLinkData
extends JSONObject {
    private String alias;
    private String campaign;
    private String channel;
    private int duration;
    private String feature;
    private String params;
    private String stage;
    private Collection<String> tags;
    private int type;

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) {
            return false;
        }
        if (((Object)((Object)this)).getClass() != object.getClass()) {
            return false;
        }
        BranchLinkData branchLinkData = (BranchLinkData)((Object)object);
        if (this.alias == null ? branchLinkData.alias != null : !this.alias.equals(branchLinkData.alias)) {
            return false;
        }
        if (this.channel == null ? branchLinkData.channel != null : !this.channel.equals(branchLinkData.channel)) {
            return false;
        }
        if (this.feature == null ? branchLinkData.feature != null : !this.feature.equals(branchLinkData.feature)) {
            return false;
        }
        if (this.params == null ? branchLinkData.params != null : !this.params.equals(branchLinkData.params)) {
            return false;
        }
        if (this.stage == null ? branchLinkData.stage != null : !this.stage.equals(branchLinkData.stage)) {
            return false;
        }
        if (this.campaign == null ? branchLinkData.campaign != null : !this.campaign.equals(branchLinkData.campaign)) {
            return false;
        }
        if (this.type != branchLinkData.type) {
            return false;
        }
        if (this.duration != branchLinkData.duration) {
            return false;
        }
        if (this.tags == null) {
            if (branchLinkData.tags == null) return true;
            return false;
        }
        if (!this.tags.toString().equals(branchLinkData.tags.toString())) return false;
        return true;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getCampaign() {
        return this.campaign;
    }

    public String getChannel() {
        return this.channel;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getFeature() {
        return this.feature;
    }

    public JSONObject getLinkDataJsonObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty((CharSequence)this.channel)) {
                jSONObject.put("~" + Defines.LinkParam.Channel.getKey(), (Object)this.channel);
            }
            if (!TextUtils.isEmpty((CharSequence)this.alias)) {
                jSONObject.put("~" + Defines.LinkParam.Alias.getKey(), (Object)this.alias);
            }
            if (!TextUtils.isEmpty((CharSequence)this.feature)) {
                jSONObject.put("~" + Defines.LinkParam.Feature.getKey(), (Object)this.feature);
            }
            if (!TextUtils.isEmpty((CharSequence)this.stage)) {
                jSONObject.put("~" + Defines.LinkParam.Stage.getKey(), (Object)this.stage);
            }
            if (!TextUtils.isEmpty((CharSequence)this.campaign)) {
                jSONObject.put("~" + Defines.LinkParam.Campaign.getKey(), (Object)this.campaign);
            }
            if (this.has(Defines.LinkParam.Tags.getKey())) {
                jSONObject.put(Defines.LinkParam.Tags.getKey(), (Object)this.getJSONArray(Defines.LinkParam.Tags.getKey()));
            }
            jSONObject.put("~" + Defines.LinkParam.Type.getKey(), this.type);
            jSONObject.put("~" + Defines.LinkParam.Duration.getKey(), this.duration);
            return jSONObject;
        }
        catch (JSONException jSONException) {
            return jSONObject;
        }
    }

    public String getParams() {
        return this.params;
    }

    public String getStage() {
        return this.stage;
    }

    public Collection<String> getTags() {
        return this.tags;
    }

    public int getType() {
        return this.type;
    }

    /*
     * Enabled aggressive block sorting
     */
    @SuppressLint(value={"DefaultLocale"})
    public int hashCode() {
        int n = 0;
        int n2 = this.type;
        int n3 = this.alias == null ? 0 : this.alias.toLowerCase().hashCode();
        int n4 = this.channel == null ? 0 : this.channel.toLowerCase().hashCode();
        int n5 = this.feature == null ? 0 : this.feature.toLowerCase().hashCode();
        int n6 = this.stage == null ? 0 : this.stage.toLowerCase().hashCode();
        int n7 = this.campaign == null ? 0 : this.campaign.toLowerCase().hashCode();
        if (this.params != null) {
            n = this.params.toLowerCase().hashCode();
        }
        n4 = n3 = 19 * (19 * (19 * (19 * (19 * (19 * (19 * (n2 + 19) + n3) + n4) + n5) + n6) + n7) + n) + this.duration;
        if (this.tags != null) {
            Iterator<String> iterator = this.tags.iterator();
            do {
                n4 = n3;
                if (!iterator.hasNext()) break;
                n3 = 19 * n3 + iterator.next().toLowerCase().hashCode();
            } while (true);
        }
        return n4;
    }
}

