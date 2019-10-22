/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.SharedPreferences$OnSharedPreferenceChangeListener
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.mixpanel.android.mpmetrics.SuperPropertyUpdate;
import com.mixpanel.android.util.MPLog;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint(value={"CommitPrefEdits"})
class PersistentIdentity {
    private static Boolean sIsFirstAppLaunch;
    private static Integer sPreviousVersionCode;
    private static boolean sReferrerPrefsDirty;
    private static final Object sReferrerPrefsLock;
    private String mEventsDistinctId;
    private boolean mIdentitiesLoaded;
    private final Future<SharedPreferences> mLoadReferrerPreferences;
    private final Future<SharedPreferences> mLoadStoredPreferences;
    private final Future<SharedPreferences> mMixpanelPreferences;
    private String mPeopleDistinctId;
    private final SharedPreferences.OnSharedPreferenceChangeListener mReferrerChangeListener;
    private Map<String, String> mReferrerPropertiesCache;
    private JSONObject mSuperPropertiesCache;
    private final Future<SharedPreferences> mTimeEventsPreferences;
    private JSONArray mWaitingPeopleRecords;

    static {
        sReferrerPrefsDirty = true;
        sReferrerPrefsLock = new Object();
    }

    public PersistentIdentity(Future<SharedPreferences> future, Future<SharedPreferences> future2, Future<SharedPreferences> future3, Future<SharedPreferences> future4) {
        this.mLoadReferrerPreferences = future;
        this.mLoadStoredPreferences = future2;
        this.mTimeEventsPreferences = future3;
        this.mMixpanelPreferences = future4;
        this.mSuperPropertiesCache = null;
        this.mReferrerPropertiesCache = null;
        this.mIdentitiesLoaded = false;
        this.mReferrerChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onSharedPreferenceChanged(SharedPreferences object, String string2) {
                object = sReferrerPrefsLock;
                synchronized (object) {
                    PersistentIdentity.this.readReferrerProperties();
                    sReferrerPrefsDirty = false;
                    return;
                }
            }
        };
    }

    private JSONObject getSuperPropertiesCache() {
        if (this.mSuperPropertiesCache == null) {
            this.readSuperProperties();
        }
        return this.mSuperPropertiesCache;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readIdentities() {
        void var1_3;
        Object var1_1 = null;
        try {
            SharedPreferences sharedPreferences;
            SharedPreferences sharedPreferences2 = sharedPreferences = this.mLoadStoredPreferences.get();
        }
        catch (ExecutionException executionException) {
            MPLog.e("MixpanelAPI.PIdentity", "Cannot read distinct ids from sharedPreferences.", executionException.getCause());
        }
        catch (InterruptedException interruptedException) {
            MPLog.e("MixpanelAPI.PIdentity", "Cannot read distinct ids from sharedPreferences.", interruptedException);
        }
        if (var1_3 == null) {
            return;
        }
        this.mEventsDistinctId = var1_3.getString("events_distinct_id", null);
        this.mPeopleDistinctId = var1_3.getString("people_distinct_id", null);
        this.mWaitingPeopleRecords = null;
        String string2 = var1_3.getString("waiting_array", null);
        if (string2 != null) {
            try {
                this.mWaitingPeopleRecords = new JSONArray(string2);
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.PIdentity", "Could not interpret waiting people JSON record " + string2);
            }
        }
        if (this.mEventsDistinctId == null) {
            this.mEventsDistinctId = UUID.randomUUID().toString();
            this.writeIdentities();
        }
        this.mIdentitiesLoaded = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readReferrerProperties() {
        this.mReferrerPropertiesCache = new HashMap<String, String>();
        try {
            SharedPreferences sharedPreferences = this.mLoadReferrerPreferences.get();
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this.mReferrerChangeListener);
            sharedPreferences.registerOnSharedPreferenceChangeListener(this.mReferrerChangeListener);
            for (Map.Entry entry : sharedPreferences.getAll().entrySet()) {
                String string2 = (String)entry.getKey();
                entry = entry.getValue();
                this.mReferrerPropertiesCache.put(string2, entry.toString());
            }
            return;
        }
        catch (ExecutionException executionException) {
            MPLog.e("MixpanelAPI.PIdentity", "Cannot load referrer properties from shared preferences.", executionException.getCause());
            return;
        }
        catch (InterruptedException interruptedException) {
            MPLog.e("MixpanelAPI.PIdentity", "Cannot load referrer properties from shared preferences.", interruptedException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readSuperProperties() {
        try {
            String string2 = this.mLoadStoredPreferences.get().getString("super_properties", "{}");
            MPLog.v("MixpanelAPI.PIdentity", "Loading Super Properties " + string2);
            this.mSuperPropertiesCache = new JSONObject(string2);
            return;
        }
        catch (ExecutionException executionException) {
            MPLog.e("MixpanelAPI.PIdentity", "Cannot load superProperties from SharedPreferences.", executionException.getCause());
            return;
        }
        catch (InterruptedException interruptedException) {
            MPLog.e("MixpanelAPI.PIdentity", "Cannot load superProperties from SharedPreferences.", interruptedException);
            return;
        }
        catch (JSONException jSONException) {
            MPLog.e("MixpanelAPI.PIdentity", "Cannot parse stored superProperties");
            this.storeSuperProperties();
            return;
        }
        finally {
            if (this.mSuperPropertiesCache != null) return;
            this.mSuperPropertiesCache = new JSONObject();
        }
    }

    private void storeSuperProperties() {
        if (this.mSuperPropertiesCache == null) {
            MPLog.e("MixpanelAPI.PIdentity", "storeSuperProperties should not be called with uninitialized superPropertiesCache.");
            return;
        }
        String string2 = this.mSuperPropertiesCache.toString();
        MPLog.v("MixpanelAPI.PIdentity", "Storing Super Properties " + string2);
        try {
            SharedPreferences.Editor editor = this.mLoadStoredPreferences.get().edit();
            editor.putString("super_properties", string2);
            PersistentIdentity.writeEdits(editor);
            return;
        }
        catch (ExecutionException executionException) {
            MPLog.e("MixpanelAPI.PIdentity", "Cannot store superProperties in shared preferences.", executionException.getCause());
            return;
        }
        catch (InterruptedException interruptedException) {
            MPLog.e("MixpanelAPI.PIdentity", "Cannot store superProperties in shared preferences.", interruptedException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static JSONArray waitingPeopleRecordsForSending(SharedPreferences sharedPreferences) {
        JSONArray jSONArray;
        block5: {
            JSONArray jSONArray2 = null;
            String string2 = sharedPreferences.getString("people_distinct_id", null);
            String string3 = sharedPreferences.getString("waiting_array", null);
            jSONArray = jSONArray2;
            if (string3 == null) break block5;
            jSONArray = jSONArray2;
            if (string2 == null) break block5;
            try {
                jSONArray2 = new JSONArray(string3);
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.PIdentity", "Waiting people records were unreadable.");
                return null;
            }
            jSONArray = new JSONArray();
            for (int i = 0; i < jSONArray2.length(); ++i) {
                try {
                    string3 = jSONArray2.getJSONObject(i);
                    string3.put("$distinct_id", (Object)string2);
                    jSONArray.put((Object)string3);
                    continue;
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.PIdentity", "Unparsable object found in waiting people records", jSONException);
                }
            }
            sharedPreferences = sharedPreferences.edit();
            sharedPreferences.remove("waiting_array");
            PersistentIdentity.writeEdits((SharedPreferences.Editor)sharedPreferences);
        }
        return jSONArray;
    }

    private static void writeEdits(SharedPreferences.Editor editor) {
        editor.apply();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeIdentities() {
        try {
            SharedPreferences.Editor editor = this.mLoadStoredPreferences.get().edit();
            editor.putString("events_distinct_id", this.mEventsDistinctId);
            editor.putString("people_distinct_id", this.mPeopleDistinctId);
            if (this.mWaitingPeopleRecords == null) {
                editor.remove("waiting_array");
            } else {
                editor.putString("waiting_array", this.mWaitingPeopleRecords.toString());
            }
            PersistentIdentity.writeEdits(editor);
            return;
        }
        catch (ExecutionException executionException) {
            MPLog.e("MixpanelAPI.PIdentity", "Can't write distinct ids to shared preferences.", executionException.getCause());
            return;
        }
        catch (InterruptedException interruptedException) {
            MPLog.e("MixpanelAPI.PIdentity", "Can't write distinct ids to shared preferences.", interruptedException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void writeReferrerPrefs(Context context, String object, Map<String, String> object2) {
        Object object3 = sReferrerPrefsLock;
        synchronized (object3) {
            context = context.getSharedPreferences((String)object, 0).edit();
            context.clear();
            object = object2.entrySet().iterator();
            do {
                if (!object.hasNext()) {
                    PersistentIdentity.writeEdits((SharedPreferences.Editor)context);
                    sReferrerPrefsDirty = true;
                    return;
                }
                object2 = (Map.Entry)object.next();
                context.putString((String)object2.getKey(), (String)object2.getValue());
            } while (true);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void addSuperPropertiesToObject(JSONObject jSONObject) {
        synchronized (this) {
            JSONObject jSONObject2 = this.getSuperPropertiesCache();
            Iterator iterator = jSONObject2.keys();
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                try {
                    jSONObject.put(string2, jSONObject2.get(string2));
                }
                catch (JSONException jSONException) {
                    try {
                        MPLog.e("MixpanelAPI.PIdentity", "Object read from one JSON Object cannot be written to another", jSONException);
                    }
                    catch (Throwable throwable) {
                        throw throwable;
                        return;
                    }
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void clearPreferences() {
        synchronized (this) {
            try {
                SharedPreferences.Editor editor = this.mLoadStoredPreferences.get().edit();
                editor.clear();
                PersistentIdentity.writeEdits(editor);
                this.readSuperProperties();
                this.readIdentities();
                return;
            }
            catch (ExecutionException executionException) {
                throw new RuntimeException(executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                throw new RuntimeException(interruptedException.getCause());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void clearPushId() {
        synchronized (this) {
            try {
                SharedPreferences.Editor editor = this.mLoadStoredPreferences.get().edit();
                editor.remove("push_id");
                PersistentIdentity.writeEdits(editor);
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Can't write push id to shared preferences", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Can't write push id to shared preferences", interruptedException);
            }
            return;
        }
    }

    public String getEventsDistinctId() {
        synchronized (this) {
            if (!this.mIdentitiesLoaded) {
                this.readIdentities();
            }
            String string2 = this.mEventsDistinctId;
            return string2;
        }
    }

    public String getPeopleDistinctId() {
        synchronized (this) {
            if (!this.mIdentitiesLoaded) {
                this.readIdentities();
            }
            String string2 = this.mPeopleDistinctId;
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getPushId() {
        synchronized (this) {
            String string2 = null;
            try {
                String string3 = this.mLoadStoredPreferences.get().getString("push_id", null);
                return string3;
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Can't write push id to shared preferences", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Can't write push id to shared preferences", interruptedException);
            }
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<String, String> getReferrerProperties() {
        Object object = sReferrerPrefsLock;
        synchronized (object) {
            if (sReferrerPrefsDirty || this.mReferrerPropertiesCache == null) {
                this.readReferrerProperties();
                sReferrerPrefsDirty = false;
            }
            return this.mReferrerPropertiesCache;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public HashSet<Integer> getSeenCampaignIds() {
        synchronized (this) {
            HashSet<Integer> hashSet = new HashSet<Integer>();
            try {
                StringTokenizer stringTokenizer = new StringTokenizer(this.mLoadStoredPreferences.get().getString("seen_campaign_ids", ""), ",");
                while (stringTokenizer.hasMoreTokens()) {
                    hashSet.add(Integer.valueOf(stringTokenizer.nextToken()));
                }
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't read Mixpanel shared preferences.", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't read Mixpanel shared preferences.", interruptedException);
            }
            return hashSet;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<String, Long> getTimeEvents() {
        HashMap<String, Long> hashMap = new HashMap<String, Long>();
        try {
            for (Map.Entry entry : this.mTimeEventsPreferences.get().getAll().entrySet()) {
                hashMap.put((String)entry.getKey(), Long.valueOf(entry.getValue().toString()));
            }
            return hashMap;
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
            return hashMap;
        }
        catch (ExecutionException executionException) {
            executionException.printStackTrace();
            return hashMap;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isFirstIntegration(String string2) {
        synchronized (this) {
            boolean bl = false;
            try {
                boolean bl2 = this.mMixpanelPreferences.get().getBoolean(string2, false);
                return bl2;
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't read internal Mixpanel shared preferences.", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't read internal Mixpanel from shared preferences.", interruptedException);
            }
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isFirstLaunch(boolean bl) {
        boolean bl2 = false;
        synchronized (this) {
            Boolean bl3 = sIsFirstAppLaunch;
            if (bl3 != null) return sIsFirstAppLaunch;
            try {
                if (this.mMixpanelPreferences.get().getBoolean("has_launched", false)) {
                    sIsFirstAppLaunch = false;
                    return sIsFirstAppLaunch;
                } else {
                    if (!bl) {
                        bl2 = true;
                    }
                    sIsFirstAppLaunch = bl2;
                }
                return sIsFirstAppLaunch;
            }
            catch (ExecutionException executionException) {
                sIsFirstAppLaunch = false;
                return sIsFirstAppLaunch;
            }
            catch (InterruptedException interruptedException) {
                sIsFirstAppLaunch = false;
            }
            return sIsFirstAppLaunch;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isNewVersion(String object) {
        synchronized (this) {
            if (object == null) {
                return false;
            }
            object = Integer.valueOf((String)object);
            try {
                SharedPreferences.Editor editor;
                if (sPreviousVersionCode == null && (sPreviousVersionCode = Integer.valueOf(this.mMixpanelPreferences.get().getInt("latest_version_code", -1))) == -1) {
                    sPreviousVersionCode = object;
                    editor = this.mMixpanelPreferences.get().edit();
                    editor.putInt("latest_version_code", ((Integer)object).intValue());
                    PersistentIdentity.writeEdits(editor);
                }
                if (sPreviousVersionCode >= (Integer)object) return false;
                editor = this.mMixpanelPreferences.get().edit();
                editor.putInt("latest_version_code", ((Integer)object).intValue());
                PersistentIdentity.writeEdits(editor);
                return true;
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't write internal Mixpanel shared preferences.", executionException.getCause());
                return false;
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't write internal Mixpanel from shared preferences.", interruptedException);
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerSuperProperties(JSONObject jSONObject) {
        synchronized (this) {
            JSONObject jSONObject2 = this.getSuperPropertiesCache();
            Iterator iterator = jSONObject.keys();
            do {
                if (!iterator.hasNext()) {
                    this.storeSuperProperties();
                    return;
                }
                String string2 = (String)iterator.next();
                try {
                    jSONObject2.put(string2, jSONObject.get(string2));
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.PIdentity", "Exception registering super property.", jSONException);
                    continue;
                }
                break;
            } while (true);
        }
    }

    public void removeTimeEvent(String string2) {
        try {
            SharedPreferences.Editor editor = this.mTimeEventsPreferences.get().edit();
            editor.remove(string2);
            PersistentIdentity.writeEdits(editor);
            return;
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
            return;
        }
        catch (ExecutionException executionException) {
            executionException.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void saveCampaignAsSeen(Integer n) {
        synchronized (this) {
            try {
                Object object = this.mLoadStoredPreferences.get();
                SharedPreferences.Editor editor = object.edit();
                object = object.getString("seen_campaign_ids", "");
                editor.putString("seen_campaign_ids", (String)object + n + ",");
                PersistentIdentity.writeEdits(editor);
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Can't write campaign d to shared preferences", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Can't write campaign id to shared preferences", interruptedException);
            }
            return;
        }
    }

    public void setEventsDistinctId(String string2) {
        synchronized (this) {
            if (!this.mIdentitiesLoaded) {
                this.readIdentities();
            }
            this.mEventsDistinctId = string2;
            this.writeIdentities();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setHasLaunched() {
        synchronized (this) {
            try {
                SharedPreferences.Editor editor = this.mMixpanelPreferences.get().edit();
                editor.putBoolean("has_launched", true);
                PersistentIdentity.writeEdits(editor);
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't write internal Mixpanel shared preferences.", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't write internal Mixpanel shared preferences.", interruptedException);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setIsIntegrated(String string2) {
        synchronized (this) {
            try {
                SharedPreferences.Editor editor = this.mMixpanelPreferences.get().edit();
                editor.putBoolean(string2, true);
                PersistentIdentity.writeEdits(editor);
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't write internal Mixpanel shared preferences.", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't write internal Mixpanel from shared preferences.", interruptedException);
            }
            return;
        }
    }

    public void setPeopleDistinctId(String string2) {
        synchronized (this) {
            if (!this.mIdentitiesLoaded) {
                this.readIdentities();
            }
            this.mPeopleDistinctId = string2;
            this.writeIdentities();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void storePushId(String string2) {
        synchronized (this) {
            try {
                SharedPreferences.Editor editor = this.mLoadStoredPreferences.get().edit();
                editor.putString("push_id", string2);
                PersistentIdentity.writeEdits(editor);
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Can't write push id to shared preferences", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Can't write push id to shared preferences", interruptedException);
            }
            return;
        }
    }

    public void storeWaitingPeopleRecord(JSONObject jSONObject) {
        synchronized (this) {
            if (!this.mIdentitiesLoaded) {
                this.readIdentities();
            }
            if (this.mWaitingPeopleRecords == null) {
                this.mWaitingPeopleRecords = new JSONArray();
            }
            this.mWaitingPeopleRecords.put((Object)jSONObject);
            this.writeIdentities();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void updateSuperProperties(SuperPropertyUpdate superPropertyUpdate) {
        // MONITORENTER : this
        JSONObject jSONObject = this.getSuperPropertiesCache();
        JSONObject jSONObject2 = new JSONObject();
        try {
            Iterator iterator = jSONObject.keys();
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                jSONObject2.put(string2, jSONObject.get(string2));
            }
        }
        catch (JSONException jSONException) {
            MPLog.e("MixpanelAPI.PIdentity", "Can't copy from one JSONObject to another", jSONException);
            // MONITOREXIT : this
            return;
        }
        if ((superPropertyUpdate = superPropertyUpdate.update(jSONObject2)) == null) {
            MPLog.w("MixpanelAPI.PIdentity", "An update to Mixpanel's super properties returned null, and will have no effect.");
            return;
        }
        this.mSuperPropertiesCache = superPropertyUpdate;
        this.storeSuperProperties();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JSONArray waitingPeopleRecordsForSending() {
        synchronized (this) {
            JSONArray jSONArray = null;
            JSONArray jSONArray2 = null;
            try {
                JSONArray jSONArray3;
                jSONArray2 = jSONArray3 = PersistentIdentity.waitingPeopleRecordsForSending(this.mLoadStoredPreferences.get());
                jSONArray = jSONArray3;
                this.readIdentities();
                return jSONArray3;
            }
            catch (ExecutionException executionException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't read waiting people records from shared preferences.", executionException.getCause());
            }
            catch (InterruptedException interruptedException) {
                MPLog.e("MixpanelAPI.PIdentity", "Couldn't read waiting people records from shared preferences.", interruptedException);
                return jSONArray;
            }
            return jSONArray2;
        }
    }

}

