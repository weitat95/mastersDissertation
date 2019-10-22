/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.SessionAnalyticsManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;

public class Answers
extends Kit<Boolean> {
    SessionAnalyticsManager analyticsManager;

    public static Answers getInstance() {
        return Fabric.getKit(Answers.class);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Boolean doInBackground() {
        try {
            SettingsData settingsData = Settings.getInstance().awaitSettingsData();
            if (settingsData == null) {
                Fabric.getLogger().e("Answers", "Failed to retrieve settings");
                return false;
            }
            if (settingsData.featuresData.collectAnalytics) {
                Fabric.getLogger().d("Answers", "Analytics collection enabled");
                this.analyticsManager.setAnalyticsSettingsData(settingsData.analyticsSettingsData, this.getOverridenSpiEndpoint());
                return true;
            }
            Fabric.getLogger().d("Answers", "Analytics collection disabled");
            this.analyticsManager.disable();
            return false;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Answers", "Error dealing with settings", exception);
            return false;
        }
    }

    @Override
    public String getIdentifier() {
        return "com.crashlytics.sdk.android:answers";
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(this.getContext(), "com.crashlytics.ApiEndpoint");
    }

    @Override
    public String getVersion() {
        return "1.3.11.167";
    }

    public void logCustom(CustomEvent customEvent) {
        if (customEvent == null) {
            throw new NullPointerException("event must not be null");
        }
        if (this.analyticsManager != null) {
            this.analyticsManager.onCustom(customEvent);
        }
    }

    public void onException(Crash.FatalException fatalException) {
        if (this.analyticsManager != null) {
            this.analyticsManager.onCrash(fatalException.getSessionId(), fatalException.getExceptionName());
        }
    }

    public void onException(Crash.LoggedException loggedException) {
        if (this.analyticsManager != null) {
            this.analyticsManager.onError(loggedException.getSessionId());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SuppressLint(value={"NewApi"})
    @Override
    protected boolean onPreExecute() {
        try {
            Context context = this.getContext();
            PackageManager packageManager = context.getPackageManager();
            String string2 = context.getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(string2, 0);
            String string3 = Integer.toString(packageInfo.versionCode);
            String string4 = packageInfo.versionName == null ? "0.0" : packageInfo.versionName;
            long l = Build.VERSION.SDK_INT >= 9 ? packageInfo.firstInstallTime : new File(packageManager.getApplicationInfo((String)string2, (int)0).sourceDir).lastModified();
            this.analyticsManager = SessionAnalyticsManager.build(this, context, this.getIdManager(), string3, string4, l);
            this.analyticsManager.enable();
            return true;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Answers", "Error retrieving app properties", exception);
            return false;
        }
    }
}

