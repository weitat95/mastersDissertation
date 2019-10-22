/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.content.Context;
import com.mixpanel.android.mpmetrics.InAppNotification;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.MPDbAdapter;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.viewcrawler.UpdatesFromMixpanel;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class DecideMessages {
    private static final Set<Integer> mLoadedVariants = new HashSet<Integer>();
    private Boolean mAutomaticEventsEnabled;
    private Context mContext;
    private String mDistinctId;
    private Set<String> mIntegrations;
    private final OnNewResultsListener mListener;
    private final Set<Integer> mNotificationIds;
    private final String mToken;
    private final List<InAppNotification> mUnseenNotifications;
    private final UpdatesFromMixpanel mUpdatesFromMixpanel;
    private JSONArray mVariants;

    public DecideMessages(Context context, String string2, OnNewResultsListener onNewResultsListener, UpdatesFromMixpanel updatesFromMixpanel, HashSet<Integer> hashSet) {
        this.mContext = context;
        this.mToken = string2;
        this.mListener = onNewResultsListener;
        this.mUpdatesFromMixpanel = updatesFromMixpanel;
        this.mDistinctId = null;
        this.mUnseenNotifications = new LinkedList<InAppNotification>();
        this.mNotificationIds = new HashSet<Integer>(hashSet);
        this.mVariants = null;
        this.mIntegrations = new HashSet<String>();
    }

    public String getDistinctId() {
        synchronized (this) {
            String string2 = this.mDistinctId;
            return string2;
        }
    }

    public Set<String> getIntegrations() {
        synchronized (this) {
            Set<String> set = this.mIntegrations;
            return set;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InAppNotification getNotification(boolean bl) {
        synchronized (this) {
            if (this.mUnseenNotifications.isEmpty()) {
                MPLog.v("MixpanelAPI.DecideUpdts", "No unseen notifications exist, none will be returned.");
                return null;
            }
            InAppNotification inAppNotification = this.mUnseenNotifications.remove(0);
            if (bl) {
                this.mUnseenNotifications.add(inAppNotification);
            } else {
                MPLog.v("MixpanelAPI.DecideUpdts", "Recording notification " + inAppNotification + " as seen.");
            }
            return inAppNotification;
        }
    }

    public String getToken() {
        return this.mToken;
    }

    public JSONArray getVariants() {
        synchronized (this) {
            JSONArray jSONArray = this.mVariants;
            return jSONArray;
        }
    }

    public Boolean isAutomaticEventsEnabled() {
        return this.mAutomaticEventsEnabled;
    }

    public void markNotificationAsUnseen(InAppNotification inAppNotification) {
        synchronized (this) {
            if (!MPConfig.DEBUG) {
                this.mUnseenNotifications.add(inAppNotification);
            }
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void reportResults(List<InAppNotification> list, JSONArray object, JSONArray jSONArray, boolean bl, JSONArray jSONArray2) {
        int n;
        int n2;
        int n3;
        block22: {
            // MONITORENTER : this
            n3 = 0;
            n = jSONArray.length();
            boolean bl2 = false;
            this.mUpdatesFromMixpanel.setEventBindings((JSONArray)object);
            for (InAppNotification inAppNotification : list) {
                n2 = inAppNotification.getId();
                if (this.mNotificationIds.contains(n2)) continue;
                this.mNotificationIds.add(n2);
                this.mUnseenNotifications.add(inAppNotification);
                n3 = 1;
            }
            this.mVariants = jSONArray;
            int n4 = 0;
            do {
                block21: {
                    boolean bl3 = bl2;
                    n2 = n3;
                    if (n4 < n) {
                        object = jSONArray.getJSONObject(n4);
                        boolean bl4 = mLoadedVariants.contains(object.getInt("id"));
                        if (bl4) break block21;
                        n2 = 1;
                        bl3 = true;
                    }
                    if (bl3 && this.mVariants != null) {
                        mLoadedVariants.clear();
                        break;
                    }
                    break block22;
                    catch (JSONException jSONException) {
                        MPLog.e("MixpanelAPI.DecideUpdts", "Could not convert variants[" + n4 + "] into a JSONObject while comparing the new variants", jSONException);
                    }
                }
                ++n4;
            } while (true);
            for (n3 = 0; n3 < n; ++n3) {
                try {
                    object = this.mVariants.getJSONObject(n3);
                    mLoadedVariants.add(object.getInt("id"));
                    continue;
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.DecideUpdts", "Could not convert variants[" + n3 + "] into a JSONObject while updating the map", jSONException);
                }
            }
        }
        n3 = n2;
        if (n == 0) {
            this.mVariants = new JSONArray();
            n3 = n2;
            if (mLoadedVariants.size() > 0) {
                mLoadedVariants.clear();
                n3 = 1;
            }
        }
        this.mUpdatesFromMixpanel.storeVariants(this.mVariants);
        if (this.mAutomaticEventsEnabled == null && !bl) {
            MPDbAdapter.getInstance(this.mContext).cleanupAutomaticEvents(this.mToken);
        }
        this.mAutomaticEventsEnabled = bl;
        if (jSONArray2 != null) {
            try {
                object = new HashSet();
                for (n2 = 0; n2 < jSONArray2.length(); ++n2) {
                    ((HashSet)object).add(jSONArray2.getString(n2));
                }
                if (!this.mIntegrations.equals(object)) {
                    this.mIntegrations = object;
                    this.mListener.onNewConnectIntegrations();
                }
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.DecideUpdts", "Got an integration id from " + jSONArray2.toString() + " that wasn't an int", jSONException);
            }
        }
        MPLog.v("MixpanelAPI.DecideUpdts", "New Decide content has become available. " + list.size() + " notifications and " + jSONArray.length() + " experiments have been added.");
        if (n3 != 0 && this.mListener != null) {
            this.mListener.onNewResults();
        }
        // MONITOREXIT : this
    }

    public void setDistinctId(String string2) {
        synchronized (this) {
            if (this.mDistinctId == null || !this.mDistinctId.equals(string2)) {
                this.mUnseenNotifications.clear();
            }
            this.mDistinctId = string2;
            return;
        }
    }

    public boolean shouldTrackAutomaticEvent() {
        if (this.isAutomaticEventsEnabled() == null) {
            return true;
        }
        return this.isAutomaticEventsEnabled();
    }

    public static interface OnNewResultsListener {
        public void onNewConnectIntegrations();

        public void onNewResults();
    }

}

