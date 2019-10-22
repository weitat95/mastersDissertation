/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.os.Bundle
 */
package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import com.mixpanel.android.mpmetrics.SystemInformation;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.OfflineMode;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class MPConfig {
    public static boolean DEBUG = false;
    private static MPConfig sInstance;
    private static final Object sInstanceLock;
    private final boolean mAutoShowMixpanelUpdates;
    private final int mBulkUploadLimit;
    private final int mDataExpiration;
    private final String mDecideEndpoint;
    private final boolean mDisableAppOpenEvent;
    private final boolean mDisableDecideChecker;
    private final boolean mDisableEmulatorBindingUI;
    private final boolean mDisableGestureBindingUI;
    private final boolean mDisableViewCrawler;
    private final String[] mDisableViewCrawlerForProjects;
    private final String mEditorUrl;
    private final String mEventsEndpoint;
    private final int mFlushInterval;
    private final boolean mIgnoreInvisibleViewsEditor;
    private final int mImageCacheMaxMemoryFactor;
    private final int mMinSessionDuration;
    private final int mMinimumDatabaseLimit;
    private final String mNotificationChannelId;
    private final int mNotificationChannelImportance;
    private final String mNotificationChannelName;
    private final int mNotificationDefaults;
    private OfflineMode mOfflineMode;
    private final String mPeopleEndpoint;
    private final String mResourcePackageName;
    private SSLSocketFactory mSSLSocketFactory;
    private final int mSessionTimeoutDuration;
    private final boolean mTestMode;
    private final boolean mUseIpAddressForGeolocation;

    static {
        sInstanceLock = new Object();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    MPConfig(Bundle bundle, Context context) {
        Object object;
        try {
            object = SSLContext.getInstance("TLS");
            ((SSLContext)object).init(null, null, null);
            object = ((SSLContext)object).getSocketFactory();
        }
        catch (GeneralSecurityException generalSecurityException) {
            MPLog.i("MixpanelAPI.Conf", "System has no SSL support. Built-in events editor will not be available", generalSecurityException);
            object = null;
        }
        this.mSSLSocketFactory = object;
        DEBUG = bundle.getBoolean("com.mixpanel.android.MPConfig.EnableDebugLogging", false);
        if (DEBUG) {
            MPLog.setLevel(2);
        }
        if (bundle.containsKey("com.mixpanel.android.MPConfig.DebugFlushInterval")) {
            MPLog.w("MixpanelAPI.Conf", "We do not support com.mixpanel.android.MPConfig.DebugFlushInterval anymore. There will only be one flush interval. Please, update your AndroidManifest.xml.");
        }
        this.mBulkUploadLimit = bundle.getInt("com.mixpanel.android.MPConfig.BulkUploadLimit", 40);
        this.mFlushInterval = bundle.getInt("com.mixpanel.android.MPConfig.FlushInterval", 60000);
        this.mDataExpiration = bundle.getInt("com.mixpanel.android.MPConfig.DataExpiration", 432000000);
        this.mMinimumDatabaseLimit = bundle.getInt("com.mixpanel.android.MPConfig.MinimumDatabaseLimit", 20971520);
        this.mResourcePackageName = bundle.getString("com.mixpanel.android.MPConfig.ResourcePackageName");
        this.mDisableGestureBindingUI = bundle.getBoolean("com.mixpanel.android.MPConfig.DisableGestureBindingUI", false);
        this.mDisableEmulatorBindingUI = bundle.getBoolean("com.mixpanel.android.MPConfig.DisableEmulatorBindingUI", false);
        this.mDisableAppOpenEvent = bundle.getBoolean("com.mixpanel.android.MPConfig.DisableAppOpenEvent", true);
        this.mDisableViewCrawler = bundle.getBoolean("com.mixpanel.android.MPConfig.DisableViewCrawler", false);
        this.mDisableDecideChecker = bundle.getBoolean("com.mixpanel.android.MPConfig.DisableDecideChecker", false);
        this.mImageCacheMaxMemoryFactor = bundle.getInt("com.mixpanel.android.MPConfig.ImageCacheMaxMemoryFactor", 10);
        this.mIgnoreInvisibleViewsEditor = bundle.getBoolean("com.mixpanel.android.MPConfig.IgnoreInvisibleViewsVisualEditor", false);
        this.mAutoShowMixpanelUpdates = bundle.getBoolean("com.mixpanel.android.MPConfig.AutoShowMixpanelUpdates", true);
        this.mNotificationDefaults = bundle.getInt("com.mixpanel.android.MPConfig.NotificationDefaults", 0);
        this.mMinSessionDuration = bundle.getInt("com.mixpanel.android.MPConfig.MinimumSessionDuration", 10000);
        this.mSessionTimeoutDuration = bundle.getInt("com.mixpanel.android.MPConfig.SessionTimeoutDuration", Integer.MAX_VALUE);
        this.mUseIpAddressForGeolocation = bundle.getBoolean("com.mixpanel.android.MPConfig.UseIpAddressForGeolocation", true);
        this.mTestMode = bundle.getBoolean("com.mixpanel.android.MPConfig.TestMode", false);
        this.mNotificationChannelImportance = bundle.getInt("com.mixpanel.android.MPConfig.NotificationChannelImportance", 3);
        CharSequence charSequence = bundle.getString("com.mixpanel.android.MPConfig.NotificationChannelId");
        object = charSequence;
        if (charSequence == null) {
            object = "mp";
        }
        this.mNotificationChannelId = object;
        charSequence = bundle.getString("com.mixpanel.android.MPConfig.NotificationChannelName");
        object = charSequence;
        if (charSequence == null) {
            object = SystemInformation.getInstance(context).getAppName();
        }
        this.mNotificationChannelName = object;
        charSequence = bundle.getString("com.mixpanel.android.MPConfig.EventsEndpoint");
        object = charSequence;
        if (charSequence == null) {
            charSequence = new StringBuilder().append("https://api.mixpanel.com/track?ip=");
            object = this.mUseIpAddressForGeolocation ? "1" : "0";
            object = ((StringBuilder)charSequence).append((String)object).toString();
        }
        this.mEventsEndpoint = object;
        charSequence = bundle.getString("com.mixpanel.android.MPConfig.PeopleEndpoint");
        object = charSequence;
        if (charSequence == null) {
            object = "https://api.mixpanel.com/engage";
        }
        this.mPeopleEndpoint = object;
        charSequence = bundle.getString("com.mixpanel.android.MPConfig.DecideEndpoint");
        object = charSequence;
        if (charSequence == null) {
            object = "https://decide.mixpanel.com/decide";
        }
        this.mDecideEndpoint = object;
        charSequence = bundle.getString("com.mixpanel.android.MPConfig.EditorUrl");
        object = charSequence;
        if (charSequence == null) {
            object = "wss://switchboard.mixpanel.com/connect/";
        }
        this.mEditorUrl = object;
        int n = bundle.getInt("com.mixpanel.android.MPConfig.DisableViewCrawlerForProjects", -1);
        this.mDisableViewCrawlerForProjects = n != -1 ? context.getResources().getStringArray(n) : new String[0];
        MPLog.v("MixpanelAPI.Conf", "Mixpanel (5.2.2) configured with:\n    AutoShowMixpanelUpdates " + this.getAutoShowMixpanelUpdates() + "\n    BulkUploadLimit " + this.getBulkUploadLimit() + "\n    FlushInterval " + this.getFlushInterval() + "\n    DataExpiration " + this.getDataExpiration() + "\n    MinimumDatabaseLimit " + this.getMinimumDatabaseLimit() + "\n    DisableAppOpenEvent " + this.getDisableAppOpenEvent() + "\n    DisableViewCrawler " + this.getDisableViewCrawler() + "\n    DisableGestureBindingUI " + this.getDisableGestureBindingUI() + "\n    DisableEmulatorBindingUI " + this.getDisableEmulatorBindingUI() + "\n    EnableDebugLogging " + DEBUG + "\n    TestMode " + this.getTestMode() + "\n    EventsEndpoint " + this.getEventsEndpoint() + "\n    PeopleEndpoint " + this.getPeopleEndpoint() + "\n    DecideEndpoint " + this.getDecideEndpoint() + "\n    EditorUrl " + this.getEditorUrl() + "\n    ImageCacheMaxMemoryFactor " + this.getImageCacheMaxMemoryFactor() + "\n    DisableDecideChecker " + this.getDisableDecideChecker() + "\n    IgnoreInvisibleViewsEditor " + this.getIgnoreInvisibleViewsEditor() + "\n    NotificationDefaults " + this.getNotificationDefaults() + "\n    MinimumSessionDuration: " + this.getMinimumSessionDuration() + "\n    SessionTimeoutDuration: " + this.getSessionTimeoutDuration() + "\n    NotificationChannelId: " + this.getNotificationChannelId() + "\n    NotificationChannelName: " + this.getNotificationChannelName() + "\n    NotificationChannelImportance: " + this.getNotificationChannelImportance());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MPConfig getInstance(Context context) {
        Object object = sInstanceLock;
        synchronized (object) {
            if (sInstance == null) {
                sInstance = MPConfig.readConfig(context.getApplicationContext());
            }
            return sInstance;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static MPConfig readConfig(Context object) {
        Bundle bundle;
        String string2 = object.getPackageName();
        try {
            Bundle bundle2;
            bundle = bundle2 = object.getPackageManager().getApplicationInfo((String)string2, (int)128).metaData;
            if (bundle2 != null) return new MPConfig(bundle, (Context)object);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new RuntimeException("Can't configure Mixpanel with package name " + string2, nameNotFoundException);
        }
        bundle = new Bundle();
        return new MPConfig(bundle, (Context)object);
    }

    public boolean getAutoShowMixpanelUpdates() {
        return this.mAutoShowMixpanelUpdates;
    }

    public int getBulkUploadLimit() {
        return this.mBulkUploadLimit;
    }

    public int getDataExpiration() {
        return this.mDataExpiration;
    }

    public String getDecideEndpoint() {
        return this.mDecideEndpoint;
    }

    public boolean getDisableAppOpenEvent() {
        return this.mDisableAppOpenEvent;
    }

    public boolean getDisableDecideChecker() {
        return this.mDisableDecideChecker;
    }

    public boolean getDisableEmulatorBindingUI() {
        return this.mDisableEmulatorBindingUI;
    }

    public boolean getDisableGestureBindingUI() {
        return this.mDisableGestureBindingUI;
    }

    public boolean getDisableViewCrawler() {
        return this.mDisableViewCrawler;
    }

    public String[] getDisableViewCrawlerForProjects() {
        return this.mDisableViewCrawlerForProjects;
    }

    public String getEditorUrl() {
        return this.mEditorUrl;
    }

    public String getEventsEndpoint() {
        return this.mEventsEndpoint;
    }

    public int getFlushInterval() {
        return this.mFlushInterval;
    }

    public boolean getIgnoreInvisibleViewsEditor() {
        return this.mIgnoreInvisibleViewsEditor;
    }

    public int getImageCacheMaxMemoryFactor() {
        return this.mImageCacheMaxMemoryFactor;
    }

    public int getMinimumDatabaseLimit() {
        return this.mMinimumDatabaseLimit;
    }

    public int getMinimumSessionDuration() {
        return this.mMinSessionDuration;
    }

    public String getNotificationChannelId() {
        return this.mNotificationChannelId;
    }

    public int getNotificationChannelImportance() {
        return this.mNotificationChannelImportance;
    }

    public String getNotificationChannelName() {
        return this.mNotificationChannelName;
    }

    public int getNotificationDefaults() {
        return this.mNotificationDefaults;
    }

    public OfflineMode getOfflineMode() {
        synchronized (this) {
            OfflineMode offlineMode = this.mOfflineMode;
            return offlineMode;
        }
    }

    public String getPeopleEndpoint() {
        return this.mPeopleEndpoint;
    }

    public String getResourcePackageName() {
        return this.mResourcePackageName;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        synchronized (this) {
            SSLSocketFactory sSLSocketFactory = this.mSSLSocketFactory;
            return sSLSocketFactory;
        }
    }

    public int getSessionTimeoutDuration() {
        return this.mSessionTimeoutDuration;
    }

    public boolean getTestMode() {
        return this.mTestMode;
    }
}

