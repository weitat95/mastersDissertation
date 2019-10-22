/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package io.branch.referral;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServerResponse {
    private Object post_;
    private int statusCode_;
    private String tag_;

    public ServerResponse(String string2, int n) {
        this.tag_ = string2;
        this.statusCode_ = n;
    }

    public JSONArray getArray() {
        if (this.post_ instanceof JSONArray) {
            return (JSONArray)this.post_;
        }
        return null;
    }

    public String getFailReason() {
        String string2;
        block7: {
            JSONObject jSONObject;
            String string3;
            String string4 = string3 = "";
            try {
                jSONObject = this.getObject();
                string2 = string3;
                if (jSONObject == null) break block7;
                string2 = string3;
                string4 = string3;
            }
            catch (Exception exception) {
                return string4;
            }
            if (!jSONObject.has("error")) break block7;
            string2 = string3;
            string4 = string3;
            if (!jSONObject.getJSONObject("error").has("message")) break block7;
            string4 = string3;
            string2 = string3 = jSONObject.getJSONObject("error").getString("message");
            if (string3 == null) break block7;
            string2 = string3;
            string4 = string3;
            if (string3.trim().length() <= 0) break block7;
            string4 = string3;
            string2 = string3 + ".";
        }
        return string2;
    }

    public JSONObject getObject() {
        if (this.post_ instanceof JSONObject) {
            return (JSONObject)this.post_;
        }
        return new JSONObject();
    }

    public int getStatusCode() {
        return this.statusCode_;
    }

    public void setPost(Object object) {
        this.post_ = object;
    }
}

