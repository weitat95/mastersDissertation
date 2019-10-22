/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Application
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.text.TextUtils;
import com.crashlytics.android.beta.ActivityLifecycleCheckForUpdatesController;
import com.crashlytics.android.beta.BuildProperties;
import com.crashlytics.android.beta.DeviceTokenLoader;
import com.crashlytics.android.beta.ImmediateCheckForUpdatesController;
import com.crashlytics.android.beta.UpdatesController;
import io.fabric.sdk.android.ActivityLifecycleManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.DeviceIdentifierProvider;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class Beta
extends Kit<Boolean>
implements DeviceIdentifierProvider {
    private final MemoryValueCache<String> deviceTokenCache = new MemoryValueCache();
    private final DeviceTokenLoader deviceTokenLoader = new DeviceTokenLoader();
    private UpdatesController updatesController;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getBetaDeviceToken(Context object, String object2) {
        boolean bl;
        void var1_4;
        Logger logger = null;
        try {
            String string2 = this.deviceTokenCache.get((Context)object, this.deviceTokenLoader);
            bl = "".equals(string2);
            if (bl) {
                Object var1_3 = null;
            }
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Beta", "Failed to load the Beta device token", exception);
            Logger logger2 = logger;
        }
        logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder().append("Beta device token present: ");
        bl = !TextUtils.isEmpty((CharSequence)var1_4);
        logger.d("Beta", stringBuilder.append(bl).toString());
        return var1_4;
    }

    private BetaSettingsData getBetaSettingsData() {
        SettingsData settingsData = Settings.getInstance().awaitSettingsData();
        if (settingsData != null) {
            return settingsData.betaSettingsData;
        }
        return null;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private BuildProperties loadBuildProperties(Context object) {
        void var1_4;
        InputStream inputStream = null;
        InputStream inputStream2 = null;
        BuildProperties buildProperties = null;
        Object var7_12 = null;
        BuildProperties buildProperties2 = buildProperties;
        InputStream inputStream3 = object.getAssets().open("crashlytics-build.properties");
        Object var1_2 = var7_12;
        if (inputStream3 != null) {
            BuildProperties buildProperties3;
            BuildProperties buildProperties4 = buildProperties;
            inputStream2 = inputStream3;
            inputStream = inputStream3;
            BuildProperties buildProperties5 = buildProperties3 = BuildProperties.fromPropertiesStream(inputStream3);
            inputStream2 = inputStream3;
            inputStream = inputStream3;
            Fabric.getLogger().d("Beta", buildProperties3.packageName + " build properties: " + buildProperties3.versionName + " (" + buildProperties3.versionCode + ")" + " - " + buildProperties3.buildId);
        }
        inputStream = var1_4;
        if (inputStream3 == null) return inputStream;
        try {
            inputStream3.close();
            return var1_4;
        }
        catch (IOException iOException) {
            Fabric.getLogger().e("Beta", "Error closing Beta build properties asset", iOException);
            return var1_4;
        }
        catch (Exception exception) {
            inputStream = inputStream2;
            try {
                Fabric.getLogger().e("Beta", "Error reading Beta build properties", exception);
                inputStream = buildProperties2;
                if (inputStream2 == null) return inputStream;
            }
            catch (Throwable throwable) {
                if (inputStream == null) throw throwable;
                try {
                    inputStream.close();
                }
                catch (IOException iOException) {
                    Fabric.getLogger().e("Beta", "Error closing Beta build properties asset", iOException);
                    throw throwable;
                }
                throw throwable;
            }
            try {
                inputStream2.close();
                return buildProperties2;
            }
            catch (IOException iOException) {
                Fabric.getLogger().e("Beta", "Error closing Beta build properties asset", iOException);
                return buildProperties2;
            }
        }
    }

    boolean canCheckForUpdates(BetaSettingsData betaSettingsData, BuildProperties buildProperties) {
        return betaSettingsData != null && !TextUtils.isEmpty((CharSequence)betaSettingsData.updateUrl) && buildProperties != null;
    }

    @TargetApi(value=14)
    UpdatesController createUpdatesController(int n, Application application) {
        if (n >= 14) {
            return new ActivityLifecycleCheckForUpdatesController(this.getFabric().getActivityLifecycleManager(), this.getFabric().getExecutorService());
        }
        return new ImmediateCheckForUpdatesController();
    }

    @Override
    protected Boolean doInBackground() {
        Fabric.getLogger().d("Beta", "Beta kit initializing...");
        Context context = this.getContext();
        IdManager idManager = this.getIdManager();
        if (TextUtils.isEmpty((CharSequence)this.getBetaDeviceToken(context, idManager.getInstallerPackageName()))) {
            Fabric.getLogger().d("Beta", "A Beta device token was not found for this app");
            return false;
        }
        Fabric.getLogger().d("Beta", "Beta device token is present, checking for app updates.");
        BetaSettingsData betaSettingsData = this.getBetaSettingsData();
        BuildProperties buildProperties = this.loadBuildProperties(context);
        if (this.canCheckForUpdates(betaSettingsData, buildProperties)) {
            this.updatesController.initialize(context, this, idManager, betaSettingsData, buildProperties, new PreferenceStoreImpl(this), new SystemCurrentTimeProvider(), new DefaultHttpRequestFactory(Fabric.getLogger()));
        }
        return true;
    }

    @Override
    public Map<IdManager.DeviceIdentifierType, String> getDeviceIdentifiers() {
        String string2 = this.getIdManager().getInstallerPackageName();
        string2 = this.getBetaDeviceToken(this.getContext(), string2);
        HashMap<IdManager.DeviceIdentifierType, String> hashMap = new HashMap<IdManager.DeviceIdentifierType, String>();
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            hashMap.put(IdManager.DeviceIdentifierType.FONT_TOKEN, string2);
        }
        return hashMap;
    }

    @Override
    public String getIdentifier() {
        return "com.crashlytics.sdk.android:beta";
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(this.getContext(), "com.crashlytics.ApiEndpoint");
    }

    @Override
    public String getVersion() {
        return "1.2.3.167";
    }

    @TargetApi(value=14)
    @Override
    protected boolean onPreExecute() {
        Application application = (Application)this.getContext().getApplicationContext();
        this.updatesController = this.createUpdatesController(Build.VERSION.SDK_INT, application);
        return true;
    }
}

