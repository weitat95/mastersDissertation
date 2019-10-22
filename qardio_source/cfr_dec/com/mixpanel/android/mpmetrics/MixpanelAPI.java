/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import com.mixpanel.android.mpmetrics.AnalyticsMessages;
import com.mixpanel.android.mpmetrics.ConfigurationChecker;
import com.mixpanel.android.mpmetrics.ConnectIntegrations;
import com.mixpanel.android.mpmetrics.DecideMessages;
import com.mixpanel.android.mpmetrics.ExceptionHandler;
import com.mixpanel.android.mpmetrics.InAppNotification;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.MPDbAdapter;
import com.mixpanel.android.mpmetrics.MixpanelActivityLifecycleCallbacks;
import com.mixpanel.android.mpmetrics.OnMixpanelUpdatesReceivedListener;
import com.mixpanel.android.mpmetrics.PersistentIdentity;
import com.mixpanel.android.mpmetrics.SharedPreferencesLoader;
import com.mixpanel.android.mpmetrics.SuperPropertyUpdate;
import com.mixpanel.android.mpmetrics.Tweak;
import com.mixpanel.android.mpmetrics.Tweaks;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.viewcrawler.TrackingDebug;
import com.mixpanel.android.viewcrawler.UpdatesFromMixpanel;
import com.mixpanel.android.viewcrawler.ViewCrawler;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MixpanelAPI {
    private static final Map<String, Map<Context, MixpanelAPI>> sInstanceMap = new HashMap<String, Map<Context, MixpanelAPI>>();
    private static final SharedPreferencesLoader sPrefsLoader = new SharedPreferencesLoader();
    private static Future<SharedPreferences> sReferrerPrefs;
    private static final Tweaks sSharedTweaks;
    private final MPConfig mConfig;
    private final ConnectIntegrations mConnectIntegrations;
    private final Context mContext;
    private final DecideMessages mDecideMessages;
    private final Map<String, String> mDeviceInfo;
    private final Map<String, Long> mEventTimings;
    private final AnalyticsMessages mMessages;
    private MixpanelActivityLifecycleCallbacks mMixpanelActivityLifecycleCallbacks;
    private final PeopleImpl mPeople;
    private final PersistentIdentity mPersistentIdentity;
    private final String mToken;
    private final TrackingDebug mTrackingDebug;
    private final UpdatesFromMixpanel mUpdatesFromMixpanel;
    private final UpdatesListener mUpdatesListener;

    static {
        sSharedTweaks = new Tweaks();
    }

    MixpanelAPI(Context context, Future<SharedPreferences> future, String string2) {
        this(context, future, string2, MPConfig.getInstance(context));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    MixpanelAPI(Context object, Future<SharedPreferences> object2, String string2, MPConfig object3) {
        String string3;
        void var1_4;
        void var3_12;
        String string4;
        this.mContext = object;
        this.mToken = var3_12;
        this.mPeople = new PeopleImpl();
        this.mConfig = string3;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("$android_lib_version", "5.2.2");
        hashMap.put("$android_os", "Android");
        string3 = Build.VERSION.RELEASE == null ? "UNKNOWN" : Build.VERSION.RELEASE;
        hashMap.put("$android_os_version", string3);
        string3 = Build.MANUFACTURER == null ? "UNKNOWN" : Build.MANUFACTURER;
        hashMap.put("$android_manufacturer", string3);
        string3 = Build.BRAND == null ? "UNKNOWN" : Build.BRAND;
        hashMap.put("$android_brand", string3);
        string3 = Build.MODEL == null ? "UNKNOWN" : Build.MODEL;
        hashMap.put("$android_model", string3);
        try {
            string3 = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
            hashMap.put("$android_app_version", ((PackageInfo)string3).versionName);
            hashMap.put("$android_app_version_code", Integer.toString(((PackageInfo)string3).versionCode));
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            MPLog.e("MixpanelAPI.API", "Exception getting app version name", nameNotFoundException);
        }
        this.mDeviceInfo = Collections.unmodifiableMap(hashMap);
        this.mUpdatesFromMixpanel = this.constructUpdatesFromMixpanel((Context)object, (String)var3_12);
        this.mTrackingDebug = this.constructTrackingDebug();
        this.mPersistentIdentity = this.getPersistentIdentity((Context)object, (Future<SharedPreferences>)((Object)string4), (String)var3_12);
        this.mEventTimings = this.mPersistentIdentity.getTimeEvents();
        this.mUpdatesListener = this.constructUpdatesListener();
        this.mDecideMessages = this.constructDecideUpdates((String)var3_12, this.mUpdatesListener, this.mUpdatesFromMixpanel);
        this.mConnectIntegrations = new ConnectIntegrations(this);
        String string5 = string4 = this.mPersistentIdentity.getPeopleDistinctId();
        if (string4 == null) {
            String string6 = this.mPersistentIdentity.getEventsDistinctId();
        }
        this.mDecideMessages.setDistinctId((String)var1_4);
        this.mMessages = this.getAnalyticsMessages();
        if (this.mPersistentIdentity.isFirstLaunch(MPDbAdapter.getInstance(this.mContext).getDatabaseFile().exists())) {
            this.track("$ae_first_open", null, true);
            this.mPersistentIdentity.setHasLaunched();
        }
        if (!this.mConfig.getDisableDecideChecker()) {
            this.mMessages.installDecideCheck(this.mDecideMessages);
        }
        this.registerMixpanelActivityLifecycleCallbacks();
        if (this.sendAppOpen()) {
            this.track("$app_open", null);
        }
        if (!this.mPersistentIdentity.isFirstIntegration(this.mToken)) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("mp_lib", (Object)"Android");
                jSONObject.put("lib", (Object)"Android");
                jSONObject.put("distinct_id", (Object)var3_12);
                AnalyticsMessages.EventDescription eventDescription = new AnalyticsMessages.EventDescription("Integration", jSONObject, "85053bf24bba75239b16a601d9387e17", false);
                this.mMessages.eventsMessage(eventDescription);
                this.mMessages.postToServer(new AnalyticsMessages.FlushDescription("85053bf24bba75239b16a601d9387e17", false));
                this.mPersistentIdentity.setIsIntegrated(this.mToken);
            }
            catch (JSONException jSONException) {}
        }
        if (this.mPersistentIdentity.isNewVersion((String)hashMap.get("$android_app_version_code"))) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("$ae_updated_version", hashMap.get("$android_app_version"));
                this.track("$ae_updated", jSONObject, true);
            }
            catch (JSONException jSONException) {}
        }
        this.mUpdatesFromMixpanel.startUpdates();
        ExceptionHandler.init();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void allInstances(InstanceProcessor instanceProcessor) {
        Map<String, Map<Context, MixpanelAPI>> map = sInstanceMap;
        synchronized (map) {
            Iterator<Map<Context, MixpanelAPI>> iterator = sInstanceMap.values().iterator();
            block3 : while (iterator.hasNext()) {
                Iterator<MixpanelAPI> iterator2 = iterator.next().values().iterator();
                do {
                    if (!iterator2.hasNext()) continue block3;
                    instanceProcessor.process(iterator2.next());
                } while (true);
                break;
            }
            return;
        }
    }

    private static void checkIntentForInboundAppLink(Context context) {
        if (context instanceof Activity) {
            try {
                Class<?> class_ = Class.forName("bolts.AppLinks");
                Intent intent = ((Activity)context).getIntent();
                class_.getMethod("getTargetUrlFromInboundIntent", Context.class, Intent.class).invoke(null, new Object[]{context, intent});
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                MPLog.d("MixpanelAPI.AL", "Failed to invoke bolts.AppLinks.getTargetUrlFromInboundIntent() -- Unable to detect inbound App Links", invocationTargetException);
                return;
            }
            catch (ClassNotFoundException classNotFoundException) {
                MPLog.d("MixpanelAPI.AL", "Please install the Bolts library >= 1.1.2 to track App Links: " + classNotFoundException.getMessage());
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                MPLog.d("MixpanelAPI.AL", "Please install the Bolts library >= 1.1.2 to track App Links: " + noSuchMethodException.getMessage());
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                MPLog.d("MixpanelAPI.AL", "Unable to detect inbound App Links: " + illegalAccessException.getMessage());
                return;
            }
        }
        MPLog.d("MixpanelAPI.AL", "Context is not an instance of Activity. To detect inbound App Links, pass an instance of an Activity to getInstance.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MixpanelAPI getInstance(Context context, String string2) {
        if (string2 == null || context == null) {
            return null;
        }
        Map<String, Map<Context, MixpanelAPI>> map = sInstanceMap;
        synchronized (map) {
            Context context2 = context.getApplicationContext();
            if (sReferrerPrefs == null) {
                sReferrerPrefs = sPrefsLoader.loadPreferences(context, "com.mixpanel.android.mpmetrics.ReferralInfo", null);
            }
            Object object = sInstanceMap.get(string2);
            Map<Context, MixpanelAPI> map2 = object;
            if (object == null) {
                map2 = new HashMap<Context, MixpanelAPI>();
                sInstanceMap.put(string2, map2);
            }
            MixpanelAPI mixpanelAPI = map2.get((Object)context2);
            object = mixpanelAPI;
            if (mixpanelAPI == null) {
                object = mixpanelAPI;
                if (ConfigurationChecker.checkBasicConfiguration(context2)) {
                    object = new MixpanelAPI(context2, sReferrerPrefs, string2);
                    MixpanelAPI.registerAppLinksListeners(context, (MixpanelAPI)object);
                    map2.put(context2, (MixpanelAPI)object);
                }
            }
            MixpanelAPI.checkIntentForInboundAppLink(context);
            return object;
        }
    }

    public static Tweak<Integer> intTweak(String string2, int n) {
        return sSharedTweaks.intTweak(string2, n);
    }

    private void pushWaitingPeopleRecord() {
        JSONArray jSONArray = this.mPersistentIdentity.waitingPeopleRecordsForSending();
        if (jSONArray != null) {
            this.sendAllPeopleRecords(jSONArray);
        }
    }

    private void recordPeopleMessage(JSONObject jSONObject) {
        if (jSONObject.has("$distinct_id")) {
            this.mMessages.peopleMessage(new AnalyticsMessages.PeopleDescription(jSONObject, this.mToken));
            return;
        }
        this.mPersistentIdentity.storeWaitingPeopleRecord(jSONObject);
    }

    private static void registerAppLinksListeners(Context context, final MixpanelAPI mixpanelAPI) {
        try {
            Class<?> class_ = Class.forName("android.support.v4.content.LocalBroadcastManager");
            Method method = class_.getMethod("getInstance", Context.class);
            class_.getMethod("registerReceiver", BroadcastReceiver.class, IntentFilter.class).invoke(method.invoke(null, new Object[]{context}), new Object[]{new BroadcastReceiver(){

                public void onReceive(Context context, Intent intent) {
                    context = new JSONObject();
                    Bundle bundle = intent.getBundleExtra("event_args");
                    if (bundle != null) {
                        for (String string2 : bundle.keySet()) {
                            try {
                                context.put(string2, bundle.get(string2));
                            }
                            catch (JSONException jSONException) {
                                MPLog.e("MixpanelAPI.AL", "failed to add key \"" + string2 + "\" to properties for tracking bolts event", jSONException);
                            }
                        }
                    }
                    mixpanelAPI.track("$" + intent.getStringExtra("event_name"), (JSONObject)context);
                }
            }, new IntentFilter("com.parse.bolts.measurement_event")});
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            MPLog.d("MixpanelAPI.AL", "Failed to invoke LocalBroadcastManager.registerReceiver() -- App Links tracking will not be enabled due to this exception", invocationTargetException);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            MPLog.d("MixpanelAPI.AL", "To enable App Links tracking android.support.v4 must be installed: " + classNotFoundException.getMessage());
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            MPLog.d("MixpanelAPI.AL", "To enable App Links tracking android.support.v4 must be installed: " + noSuchMethodException.getMessage());
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            MPLog.d("MixpanelAPI.AL", "App Links tracking will not be enabled due to this exception: " + illegalAccessException.getMessage());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendAllPeopleRecords(JSONArray jSONArray) {
        int n = 0;
        while (n < jSONArray.length()) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(n);
                this.mMessages.peopleMessage(new AnalyticsMessages.PeopleDescription(jSONObject, this.mToken));
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.API", "Malformed people record stored pending identity, will not send it.", jSONException);
            }
            ++n;
        }
        return;
    }

    public static Tweak<String> stringTweak(String string2, String string3) {
        return sSharedTweaks.stringTweak(string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void alias(String string2, String string3) {
        String string4 = string3;
        if (string3 == null) {
            string4 = this.getDistinctId();
        }
        if (string2.equals(string4)) {
            MPLog.w("MixpanelAPI.API", "Attempted to alias identical distinct_ids " + string2 + ". Alias message will not be sent.");
            return;
        }
        try {
            string3 = new JSONObject();
            string3.put("alias", (Object)string2);
            string3.put("original", (Object)string4);
            this.track("$create_alias", (JSONObject)string3);
        }
        catch (JSONException jSONException) {
            MPLog.e("MixpanelAPI.API", "Failed to alias", jSONException);
        }
        this.flush();
    }

    DecideMessages constructDecideUpdates(String string2, DecideMessages.OnNewResultsListener onNewResultsListener, UpdatesFromMixpanel updatesFromMixpanel) {
        return new DecideMessages(this.mContext, string2, onNewResultsListener, updatesFromMixpanel, this.mPersistentIdentity.getSeenCampaignIds());
    }

    TrackingDebug constructTrackingDebug() {
        if (this.mUpdatesFromMixpanel instanceof ViewCrawler) {
            return (TrackingDebug)((Object)this.mUpdatesFromMixpanel);
        }
        return null;
    }

    UpdatesFromMixpanel constructUpdatesFromMixpanel(Context context, String string2) {
        if (Build.VERSION.SDK_INT < 16) {
            MPLog.i("MixpanelAPI.API", "SDK version is lower than 16. Web Configuration, A/B Testing, and Dynamic Tweaks are disabled.");
            return new NoOpUpdatesFromMixpanel(sSharedTweaks);
        }
        if (this.mConfig.getDisableViewCrawler() || Arrays.asList(this.mConfig.getDisableViewCrawlerForProjects()).contains(string2)) {
            MPLog.i("MixpanelAPI.API", "DisableViewCrawler is set to true. Web Configuration, A/B Testing, and Dynamic Tweaks are disabled.");
            return new NoOpUpdatesFromMixpanel(sSharedTweaks);
        }
        return new ViewCrawler(this.mContext, this.mToken, this, sSharedTweaks);
    }

    UpdatesListener constructUpdatesListener() {
        if (Build.VERSION.SDK_INT < 16) {
            MPLog.i("MixpanelAPI.API", "Notifications are not supported on this Android OS Version");
            return new UnsupportedUpdatesListener();
        }
        return new SupportedUpdatesListener();
    }

    public void flush() {
        this.mMessages.postToServer(new AnalyticsMessages.FlushDescription(this.mToken));
    }

    protected void flushNoDecideCheck() {
        this.mMessages.postToServer(new AnalyticsMessages.FlushDescription(this.mToken, false));
    }

    AnalyticsMessages getAnalyticsMessages() {
        return AnalyticsMessages.getInstance(this.mContext);
    }

    public Map<String, String> getDeviceInfo() {
        return this.mDeviceInfo;
    }

    public String getDistinctId() {
        return this.mPersistentIdentity.getEventsDistinctId();
    }

    public People getPeople() {
        return this.mPeople;
    }

    PersistentIdentity getPersistentIdentity(Context context, Future<SharedPreferences> future, String string2) {
        Object object = new SharedPreferencesLoader.OnPrefsLoadedListener(){

            @Override
            public void onPrefsLoaded(SharedPreferences sharedPreferences) {
                if ((sharedPreferences = PersistentIdentity.waitingPeopleRecordsForSending(sharedPreferences)) != null) {
                    MixpanelAPI.this.sendAllPeopleRecords((JSONArray)sharedPreferences);
                }
            }
        };
        String string3 = "com.mixpanel.android.mpmetrics.MixpanelAPI_" + string2;
        object = sPrefsLoader.loadPreferences(context, string3, (SharedPreferencesLoader.OnPrefsLoadedListener)object);
        string2 = "com.mixpanel.android.mpmetrics.MixpanelAPI.TimeEvents_" + string2;
        return new PersistentIdentity(future, (Future<SharedPreferences>)object, sPrefsLoader.loadPreferences(context, string2, null), sPrefsLoader.loadPreferences(context, "com.mixpanel.android.mpmetrics.Mixpanel", null));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void identify(String string2) {
        PersistentIdentity persistentIdentity = this.mPersistentIdentity;
        synchronized (persistentIdentity) {
            String string3;
            this.mPersistentIdentity.setEventsDistinctId(string2);
            string2 = string3 = this.mPersistentIdentity.getPeopleDistinctId();
            if (string3 == null) {
                string2 = this.mPersistentIdentity.getEventsDistinctId();
            }
            this.mDecideMessages.setDistinctId(string2);
            return;
        }
    }

    public boolean isAppInForeground() {
        if (Build.VERSION.SDK_INT >= 14) {
            if (this.mMixpanelActivityLifecycleCallbacks != null) {
                return this.mMixpanelActivityLifecycleCallbacks.isInForeground();
            }
        } else {
            MPLog.e("MixpanelAPI.API", "Your build version is below 14. This method will always return false.");
        }
        return false;
    }

    @Deprecated
    public void logPosts() {
        MPLog.i("MixpanelAPI.API", "MixpanelAPI.logPosts() is deprecated.\n    To get verbose debug level logging, add\n    <meta-data android:name=\"com.mixpanel.android.MPConfig.EnableDebugLogging\" value=\"true\" />\n    to the <application> section of your AndroidManifest.xml.");
    }

    void onBackground() {
        this.flush();
        this.mUpdatesFromMixpanel.applyPersistedUpdates();
    }

    @TargetApi(value=14)
    void registerMixpanelActivityLifecycleCallbacks() {
        block3: {
            block2: {
                if (Build.VERSION.SDK_INT < 14) break block2;
                if (!(this.mContext.getApplicationContext() instanceof Application)) break block3;
                Application application = (Application)this.mContext.getApplicationContext();
                this.mMixpanelActivityLifecycleCallbacks = new MixpanelActivityLifecycleCallbacks(this, this.mConfig);
                application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)this.mMixpanelActivityLifecycleCallbacks);
            }
            return;
        }
        MPLog.i("MixpanelAPI.API", "Context is not an Application, Mixpanel will not automatically show in-app notifications or A/B test experiments. We won't be able to automatically flush on an app background.");
    }

    public void registerSuperProperties(JSONObject jSONObject) {
        this.mPersistentIdentity.registerSuperProperties(jSONObject);
    }

    public void reset() {
        this.mPersistentIdentity.clearPreferences();
        this.identify(this.getDistinctId());
        this.mConnectIntegrations.reset();
        this.flush();
    }

    boolean sendAppOpen() {
        return !this.mConfig.getDisableAppOpenEvent();
    }

    public void track(String string2) {
        this.track(string2, null);
    }

    public void track(String string2, JSONObject jSONObject) {
        this.track(string2, jSONObject, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void track(String string2, JSONObject object, boolean bl) {
        block15: {
            block14: {
                Object object2;
                if (bl && !this.mDecideMessages.shouldTrackAutomaticEvent()) break block14;
                JSONObject jSONObject = this.mEventTimings;
                synchronized (jSONObject) {
                    object2 = this.mEventTimings.get(string2);
                    this.mEventTimings.remove(string2);
                    this.mPersistentIdentity.removeTimeEvent(string2);
                }
                try {
                    jSONObject = new JSONObject();
                    for (Map.Entry<String, String> entry : this.mPersistentIdentity.getReferrerProperties().entrySet()) {
                        jSONObject.put(entry.getKey(), (Object)entry.getValue());
                    }
                    this.mPersistentIdentity.addSuperPropertiesToObject(jSONObject);
                    double d = (double)System.currentTimeMillis() / 1000.0;
                    jSONObject.put("time", (long)d);
                    jSONObject.put("distinct_id", (Object)this.getDistinctId());
                    if (object2 != null) {
                        jSONObject.put("$duration", d - (double)((Long)object2).longValue() / 1000.0);
                    }
                    if (object != null) {
                        object2 = object.keys();
                        while (object2.hasNext()) {
                            String string3 = (String)object2.next();
                            jSONObject.put(string3, object.get(string3));
                        }
                    }
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.API", "Exception tracking event " + string2, jSONException);
                    return;
                }
                object = new AnalyticsMessages.EventDescription(string2, jSONObject, this.mToken, bl);
                this.mMessages.eventsMessage((AnalyticsMessages.EventDescription)object);
                if (this.mTrackingDebug != null) break block15;
            }
            return;
        }
        this.mTrackingDebug.reportTrack(string2);
    }

    public void updateSuperProperties(SuperPropertyUpdate superPropertyUpdate) {
        this.mPersistentIdentity.updateSuperProperties(superPropertyUpdate);
    }

    static interface InstanceProcessor {
        public void process(MixpanelAPI var1);
    }

    class NoOpUpdatesFromMixpanel
    implements UpdatesFromMixpanel {
        private final Tweaks mTweaks;

        public NoOpUpdatesFromMixpanel(Tweaks tweaks) {
            this.mTweaks = tweaks;
        }

        @Override
        public void applyPersistedUpdates() {
        }

        @Override
        public void setEventBindings(JSONArray jSONArray) {
        }

        @Override
        public void setVariants(JSONArray jSONArray) {
        }

        @Override
        public void startUpdates() {
        }

        @Override
        public void storeVariants(JSONArray jSONArray) {
        }
    }

    public static interface People {
        public void append(String var1, Object var2);

        public void clearPushRegistrationId();

        public void identify(String var1);

        public void increment(String var1, double var2);

        public void initPushHandling(String var1);

        public void joinExperimentIfAvailable();

        public void merge(String var1, JSONObject var2);

        public void set(String var1, Object var2);

        public void set(JSONObject var1);

        public void setPushRegistrationId(String var1);

        public void showNotificationIfAvailable(Activity var1);

        public void trackCharge(double var1, JSONObject var3);

        public void trackNotification(String var1, InAppNotification var2, JSONObject var3);

        public People withIdentity(String var1);
    }

    private class PeopleImpl
    implements People {
        private PeopleImpl() {
        }

        @TargetApi(value=19)
        private void registerForPushIdAPI19AndOlder(String string2) {
            try {
                MPLog.v("MixpanelAPI.API", "Registering a new push id");
                Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                intent.putExtra("app", (Parcelable)PendingIntent.getBroadcast((Context)MixpanelAPI.this.mContext, (int)0, (Intent)new Intent(), (int)0));
                intent.putExtra("sender", string2);
                MixpanelAPI.this.mContext.startService(intent);
                return;
            }
            catch (SecurityException securityException) {
                MPLog.w("MixpanelAPI.API", "Error registering for push notifications", securityException);
                return;
            }
        }

        @TargetApi(value=21)
        private void registerForPushIdAPI21AndUp(String string2) {
            MixpanelAPI.this.mMessages.registerForGCM(string2);
        }

        private void showGivenOrAvailableNotification(final InAppNotification inAppNotification, final Activity activity) {
            if (Build.VERSION.SDK_INT < 16) {
                MPLog.v("MixpanelAPI.API", "Will not show notifications, os version is too low.");
                return;
            }
            activity.runOnUiThread(new Runnable(){

                /*
                 * Exception decompiling
                 */
                @TargetApi(value=16)
                @Override
                public void run() {
                    // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                    // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
                    // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
                    // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
                    // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                    // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
                    // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
                    // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
                    // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
                    // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
                    // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
                    // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
                    // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
                    // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
                    // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                    // org.benf.cfr.reader.Main.main(Main.java:48)
                    throw new IllegalStateException("Decompilation failed");
                }
            });
        }

        private JSONObject stdPeopleMessage(String string2, Object object) throws JSONException {
            JSONObject jSONObject = new JSONObject();
            String string3 = this.getDistinctId();
            jSONObject.put(string2, object);
            jSONObject.put("$token", (Object)MixpanelAPI.this.mToken);
            jSONObject.put("$time", System.currentTimeMillis());
            if (string3 != null) {
                jSONObject.put("$distinct_id", (Object)string3);
            }
            return jSONObject;
        }

        @Override
        public void append(String string2, Object object) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(string2, object);
                string2 = this.stdPeopleMessage("$append", (Object)jSONObject);
                MixpanelAPI.this.recordPeopleMessage((JSONObject)string2);
                return;
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.API", "Exception appending a property", jSONException);
                return;
            }
        }

        @Override
        public void clearPushRegistrationId() {
            MixpanelAPI.this.mPersistentIdentity.clearPushId();
            this.set("$android_devices", (Object)new JSONArray());
        }

        public String getDistinctId() {
            return MixpanelAPI.this.mPersistentIdentity.getPeopleDistinctId();
        }

        public InAppNotification getNotificationIfAvailable() {
            return MixpanelAPI.this.mDecideMessages.getNotification(MixpanelAPI.this.mConfig.getTestMode());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void identify(String string2) {
            PersistentIdentity persistentIdentity = MixpanelAPI.this.mPersistentIdentity;
            synchronized (persistentIdentity) {
                MixpanelAPI.this.mPersistentIdentity.setPeopleDistinctId(string2);
                MixpanelAPI.this.mDecideMessages.setDistinctId(string2);
            }
            MixpanelAPI.this.pushWaitingPeopleRecord();
        }

        @Override
        public void increment(String string2, double d) {
            HashMap<String, Double> hashMap = new HashMap<String, Double>();
            hashMap.put(string2, d);
            this.increment(hashMap);
        }

        public void increment(Map<String, ? extends Number> jSONObject) {
            jSONObject = new JSONObject(jSONObject);
            try {
                jSONObject = this.stdPeopleMessage("$add", (Object)jSONObject);
                MixpanelAPI.this.recordPeopleMessage(jSONObject);
                return;
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.API", "Exception incrementing properties", jSONException);
                return;
            }
        }

        @Override
        public void initPushHandling(String string2) {
            if (!ConfigurationChecker.checkPushConfiguration(MixpanelAPI.this.mContext)) {
                MPLog.i("MixpanelAPI.API", "Can't register for push notification services. Push notifications will not work.");
                MPLog.i("MixpanelAPI.API", "See log tagged " + ConfigurationChecker.LOGTAG + " above for details.");
                return;
            }
            final String string3 = MixpanelAPI.this.mPersistentIdentity.getPushId();
            if (string3 == null) {
                if (Build.VERSION.SDK_INT >= 21) {
                    this.registerForPushIdAPI21AndUp(string2);
                    return;
                }
                this.registerForPushIdAPI19AndOlder(string2);
                return;
            }
            MixpanelAPI.allInstances(new InstanceProcessor(){

                @Override
                public void process(MixpanelAPI mixpanelAPI) {
                    MPLog.v("MixpanelAPI.API", "Using existing pushId " + string3);
                    mixpanelAPI.getPeople().setPushRegistrationId(string3);
                }
            });
        }

        @Override
        public void joinExperimentIfAvailable() {
            JSONArray jSONArray = MixpanelAPI.this.mDecideMessages.getVariants();
            MixpanelAPI.this.mUpdatesFromMixpanel.setVariants(jSONArray);
        }

        @Override
        public void merge(String string2, JSONObject jSONObject) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put(string2, (Object)jSONObject);
                string2 = this.stdPeopleMessage("$merge", (Object)jSONObject2);
                MixpanelAPI.this.recordPeopleMessage((JSONObject)string2);
                return;
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.API", "Exception merging a property", jSONException);
                return;
            }
        }

        @Override
        public void set(String string2, Object object) {
            try {
                this.set(new JSONObject().put(string2, object));
                return;
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.API", "set", jSONException);
                return;
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void set(JSONObject jSONObject) {
            JSONObject jSONObject2;
            try {
                jSONObject2 = new JSONObject(MixpanelAPI.this.mDeviceInfo);
                Iterator iterator = jSONObject.keys();
                while (iterator.hasNext()) {
                    String string2 = (String)iterator.next();
                    jSONObject2.put(string2, jSONObject.get(string2));
                }
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.API", "Exception setting people properties", jSONException);
                return;
            }
            {
                jSONObject = this.stdPeopleMessage("$set", (Object)jSONObject2);
                MixpanelAPI.this.recordPeopleMessage(jSONObject);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setPushRegistrationId(String string2) {
            PersistentIdentity persistentIdentity = MixpanelAPI.this.mPersistentIdentity;
            synchronized (persistentIdentity) {
                if (MixpanelAPI.this.mPersistentIdentity.getPeopleDistinctId() == null) {
                    return;
                }
                MixpanelAPI.this.mPersistentIdentity.storePushId(string2);
                JSONArray jSONArray = new JSONArray();
                jSONArray.put((Object)string2);
                this.union("$android_devices", jSONArray);
                return;
            }
        }

        @Override
        public void showNotificationIfAvailable(Activity activity) {
            if (Build.VERSION.SDK_INT < 16) {
                return;
            }
            this.showGivenOrAvailableNotification(null, activity);
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void trackCharge(double d, JSONObject jSONObject) {
            JSONObject jSONObject2;
            Object object = new Date();
            Object object2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            ((DateFormat)object2).setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                jSONObject2 = new JSONObject();
                jSONObject2.put("$amount", d);
                jSONObject2.put("$time", (Object)((DateFormat)object2).format((Date)object));
                if (jSONObject != null) {
                    object = jSONObject.keys();
                    while (object.hasNext()) {
                        object2 = (String)object.next();
                        jSONObject2.put((String)object2, jSONObject.get((String)object2));
                    }
                }
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.API", "Exception creating new charge", jSONException);
                return;
            }
            {
                this.append("$transactions", (Object)jSONObject2);
                return;
            }
        }

        @Override
        public void trackNotification(String string2, InAppNotification inAppNotification, JSONObject jSONObject) {
            inAppNotification = inAppNotification.getCampaignProperties();
            if (jSONObject != null) {
                try {
                    Iterator iterator = jSONObject.keys();
                    while (iterator.hasNext()) {
                        String string3 = (String)iterator.next();
                        inAppNotification.put(string3, jSONObject.get(string3));
                    }
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.API", "Exception merging provided properties with notification properties", jSONException);
                }
            }
            MixpanelAPI.this.track(string2, (JSONObject)inAppNotification);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void trackNotificationSeen(InAppNotification inAppNotification) {
            if (inAppNotification == null) {
                return;
            }
            MixpanelAPI.this.mPersistentIdentity.saveCampaignAsSeen(inAppNotification.getId());
            this.trackNotification("$campaign_delivery", inAppNotification, null);
            People people = MixpanelAPI.this.getPeople().withIdentity(this.getDistinctId());
            if (people == null) {
                MPLog.e("MixpanelAPI.API", "No identity found. Make sure to call getPeople().identify() before showing in-app notifications.");
                return;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            JSONObject jSONObject = inAppNotification.getCampaignProperties();
            try {
                jSONObject.put("$time", (Object)simpleDateFormat.format(new Date()));
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.API", "Exception trying to track an in-app notification seen", jSONException);
            }
            people.append("$campaigns", inAppNotification.getId());
            people.append("$notifications", (Object)jSONObject);
        }

        public void union(String string2, JSONArray jSONArray) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(string2, (Object)jSONArray);
                string2 = this.stdPeopleMessage("$union", (Object)jSONObject);
                MixpanelAPI.this.recordPeopleMessage((JSONObject)string2);
                return;
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.API", "Exception unioning a property");
                return;
            }
        }

        @Override
        public People withIdentity(final String string2) {
            if (string2 == null) {
                return null;
            }
            return new PeopleImpl(){

                @Override
                public String getDistinctId() {
                    return string2;
                }

                @Override
                public void identify(String string22) {
                    throw new RuntimeException("This MixpanelPeople object has a fixed, constant distinctId");
                }
            };
        }

    }

    private class SupportedUpdatesListener
    implements UpdatesListener,
    Runnable {
        private final Executor mExecutor;
        private final Set<OnMixpanelUpdatesReceivedListener> mListeners = Collections.newSetFromMap(new ConcurrentHashMap());

        private SupportedUpdatesListener() {
            this.mExecutor = Executors.newSingleThreadExecutor();
        }

        @Override
        public void onNewConnectIntegrations() {
            MixpanelAPI.this.mConnectIntegrations.setupIntegrations(MixpanelAPI.this.mDecideMessages.getIntegrations());
        }

        @Override
        public void onNewResults() {
            this.mExecutor.execute(this);
        }

        @Override
        public void run() {
            Iterator<OnMixpanelUpdatesReceivedListener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onMixpanelUpdatesReceived();
            }
        }
    }

    private class UnsupportedUpdatesListener
    implements UpdatesListener {
        private UnsupportedUpdatesListener() {
        }

        @Override
        public void onNewConnectIntegrations() {
        }

        @Override
        public void onNewResults() {
        }
    }

    private static interface UpdatesListener
    extends DecideMessages.OnNewResultsListener {
    }

}

