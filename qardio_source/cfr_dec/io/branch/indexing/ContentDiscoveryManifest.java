/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.indexing;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentDiscoveryManifest {
    private static ContentDiscoveryManifest thisInstance_;
    private final String PREF_KEY;
    private JSONObject cdManifestObject_;
    private JSONArray contentPaths_;
    private boolean isCDEnabled_ = false;
    private String manifestVersion_;
    private int maxPacketSize_ = 0;
    private int maxTextLen_ = 0;
    private int maxViewHistoryLength_ = 1;
    private SharedPreferences sharedPref;

    private ContentDiscoveryManifest(Context context) {
        this.PREF_KEY = "BNC_CD_MANIFEST";
        this.sharedPref = context.getSharedPreferences("bnc_content_discovery_manifest_storage", 0);
        this.retrieve(context);
    }

    public static ContentDiscoveryManifest getInstance(Context context) {
        if (thisInstance_ == null) {
            thisInstance_ = new ContentDiscoveryManifest(context);
        }
        return thisInstance_;
    }

    private void persist() {
        this.sharedPref.edit().putString("BNC_CD_MANIFEST", this.cdManifestObject_.toString()).apply();
    }

    private void retrieve(Context object) {
        object = this.sharedPref.getString("BNC_CD_MANIFEST", null);
        if (object != null) {
            try {
                this.cdManifestObject_ = new JSONObject((String)object);
                if (this.cdManifestObject_.has("mv")) {
                    this.manifestVersion_ = this.cdManifestObject_.getString("mv");
                }
                if (this.cdManifestObject_.has("m")) {
                    this.contentPaths_ = this.cdManifestObject_.getJSONArray("m");
                }
                return;
            }
            catch (JSONException jSONException) {
                this.cdManifestObject_ = new JSONObject();
                return;
            }
        }
        this.cdManifestObject_ = new JSONObject();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    CDPathProperties getCDPathProperties(Activity object) {
        Object var4_4;
        void var3_10;
        Object var3_5 = var4_4 = null;
        if (this.contentPaths_ == null) return var3_10;
        String string2 = "/" + object.getClass().getSimpleName();
        int n = 0;
        do {
            block3: {
                Object var3_7 = var4_4;
                try {
                    if (n >= this.contentPaths_.length()) return var3_10;
                    JSONObject jSONObject = this.contentPaths_.getJSONObject(n);
                    if (!jSONObject.has("p") || !jSONObject.getString("p").equals(string2)) break block3;
                    CDPathProperties cDPathProperties = new CDPathProperties(jSONObject);
                    return var3_10;
                }
                catch (JSONException jSONException) {
                    return null;
                }
            }
            ++n;
        } while (true);
    }

    public String getManifestVersion() {
        if (TextUtils.isEmpty((CharSequence)this.manifestVersion_)) {
            return "-1";
        }
        return this.manifestVersion_;
    }

    int getMaxPacketSize() {
        return this.maxPacketSize_;
    }

    int getMaxTextLen() {
        return this.maxTextLen_;
    }

    int getMaxViewHistorySize() {
        return this.maxViewHistoryLength_;
    }

    boolean isCDEnabled() {
        return this.isCDEnabled_;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onBranchInitialised(JSONObject jSONObject) {
        if (!jSONObject.has("cd")) {
            this.isCDEnabled_ = false;
            return;
        }
        this.isCDEnabled_ = true;
        try {
            int n;
            jSONObject = jSONObject.getJSONObject("cd");
            if (jSONObject.has("mv")) {
                this.manifestVersion_ = jSONObject.getString("mv");
            }
            if (jSONObject.has("mhl")) {
                this.maxViewHistoryLength_ = jSONObject.getInt("mhl");
            }
            if (jSONObject.has("m")) {
                this.contentPaths_ = jSONObject.getJSONArray("m");
            }
            if (jSONObject.has("mtl") && (n = jSONObject.getInt("mtl")) > 0) {
                this.maxTextLen_ = n;
            }
            if (jSONObject.has("mps")) {
                this.maxPacketSize_ = jSONObject.getInt("mps");
            }
            this.cdManifestObject_.put("mv", (Object)this.manifestVersion_);
            this.cdManifestObject_.put("m", (Object)this.contentPaths_);
            this.persist();
            return;
        }
        catch (JSONException jSONException) {
            return;
        }
    }

    class CDPathProperties {
        private int discoveryRepeatInterval_;
        private boolean isClearText_;
        private int maxDiscoveryRepeat_;
        final JSONObject pathInfo_;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        CDPathProperties(JSONObject jSONObject) {
            this.pathInfo_ = jSONObject;
            this.maxDiscoveryRepeat_ = 15;
            if (jSONObject.has("h")) {
                try {
                    boolean bl = !jSONObject.getBoolean("h");
                    this.isClearText_ = bl;
                }
                catch (JSONException jSONException) {
                    jSONException.printStackTrace();
                }
            }
            try {
                if (jSONObject.has("dri")) {
                    this.discoveryRepeatInterval_ = jSONObject.getInt("dri");
                }
                if (jSONObject.has("mdr")) {
                    this.maxDiscoveryRepeat_ = jSONObject.getInt("mdr");
                }
                return;
            }
            catch (JSONException jSONException) {
                jSONException.printStackTrace();
                return;
            }
        }

        int getDiscoveryRepeatInterval() {
            return this.discoveryRepeatInterval_;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        JSONArray getFilteredElements() {
            JSONArray jSONArray = null;
            if (!this.pathInfo_.has("ck")) return jSONArray;
            try {
                return this.pathInfo_.getJSONArray("ck");
            }
            catch (JSONException jSONException) {
                jSONException.printStackTrace();
                return null;
            }
        }

        int getMaxDiscoveryRepeatNumber() {
            return this.maxDiscoveryRepeat_;
        }

        boolean isClearTextRequested() {
            return this.isClearText_;
        }

        boolean isSkipContentDiscovery() {
            JSONArray jSONArray = this.getFilteredElements();
            return jSONArray != null && jSONArray.length() == 0;
        }
    }

}

