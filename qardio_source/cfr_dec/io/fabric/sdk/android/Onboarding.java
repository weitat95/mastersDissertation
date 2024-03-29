/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package io.fabric.sdk.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AppRequestData;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.CreateAppSpiCall;
import io.fabric.sdk.android.services.settings.IconRequest;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.UpdateAppSpiCall;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

class Onboarding
extends Kit<Boolean> {
    private String applicationLabel;
    private String installerPackageName;
    private final Future<Map<String, KitInfo>> kitsFinder;
    private PackageInfo packageInfo;
    private PackageManager packageManager;
    private String packageName;
    private final Collection<Kit> providedKits;
    private final HttpRequestFactory requestFactory = new DefaultHttpRequestFactory();
    private String targetAndroidSdkVersion;
    private String versionCode;
    private String versionName;

    public Onboarding(Future<Map<String, KitInfo>> future, Collection<Kit> collection) {
        this.kitsFinder = future;
        this.providedKits = collection;
    }

    private AppRequestData buildAppRequest(IconRequest iconRequest, Collection<KitInfo> collection) {
        Object object = this.getContext();
        String string2 = new ApiKey().getValue((Context)object);
        object = CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(object));
        int n = DeliveryMechanism.determineFrom(this.installerPackageName).getId();
        return new AppRequestData(string2, this.getIdManager().getAppIdentifier(), this.versionName, this.versionCode, (String)object, this.applicationLabel, n, this.targetAndroidSdkVersion, "0", iconRequest, collection);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean performAutoConfigure(String string2, AppSettingsData appSettingsData, Collection<KitInfo> collection) {
        boolean bl = true;
        if ("new".equals(appSettingsData.status)) {
            if (this.performCreateApp(string2, appSettingsData, collection)) {
                return Settings.getInstance().loadSettingsSkippingCache();
            }
            Fabric.getLogger().e("Fabric", "Failed to create app with Crashlytics service.", null);
            return false;
        }
        if ("configured".equals(appSettingsData.status)) {
            return Settings.getInstance().loadSettingsSkippingCache();
        }
        if (!appSettingsData.updateRequired) return bl;
        Fabric.getLogger().d("Fabric", "Server says an update is required - forcing a full App update.");
        this.performUpdateApp(string2, appSettingsData, collection);
        return true;
    }

    private boolean performCreateApp(String object, AppSettingsData appSettingsData, Collection<KitInfo> collection) {
        object = this.buildAppRequest(IconRequest.build(this.getContext(), (String)object), collection);
        return new CreateAppSpiCall(this, this.getOverridenSpiEndpoint(), appSettingsData.url, this.requestFactory).invoke((AppRequestData)object);
    }

    private boolean performUpdateApp(AppSettingsData appSettingsData, IconRequest object, Collection<KitInfo> collection) {
        object = this.buildAppRequest((IconRequest)object, collection);
        return new UpdateAppSpiCall(this, this.getOverridenSpiEndpoint(), appSettingsData.url, this.requestFactory).invoke((AppRequestData)object);
    }

    private boolean performUpdateApp(String string2, AppSettingsData appSettingsData, Collection<KitInfo> collection) {
        return this.performUpdateApp(appSettingsData, IconRequest.build(this.getContext(), string2), collection);
    }

    private SettingsData retrieveSettingsData() {
        try {
            Settings.getInstance().initialize(this, this.idManager, this.requestFactory, this.versionCode, this.versionName, this.getOverridenSpiEndpoint()).loadSettingsData();
            SettingsData settingsData = Settings.getInstance().awaitSettingsData();
            return settingsData;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Fabric", "Error dealing with settings", exception);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Boolean doInBackground() {
        String string2 = CommonUtils.getAppIconHashOrNull(this.getContext());
        boolean bl = false;
        SettingsData settingsData = this.retrieveSettingsData();
        boolean bl2 = bl;
        if (settingsData == null) return bl2;
        try {
            Map<String, KitInfo> map = this.kitsFinder != null ? this.kitsFinder.get() : new HashMap<String, KitInfo>();
            map = this.mergeKits(map, this.providedKits);
            bl2 = this.performAutoConfigure(string2, settingsData.appData, map.values());
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Fabric", "Error performing auto configuration.", exception);
            bl2 = bl;
            return bl2;
        }
        return bl2;
    }

    @Override
    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    String getOverridenSpiEndpoint() {
        return CommonUtils.getStringsFileValue(this.getContext(), "com.crashlytics.ApiEndpoint");
    }

    @Override
    public String getVersion() {
        return "1.3.15.167";
    }

    Map<String, KitInfo> mergeKits(Map<String, KitInfo> map, Collection<Kit> object) {
        object = object.iterator();
        while (object.hasNext()) {
            Kit kit = (Kit)object.next();
            if (map.containsKey(kit.getIdentifier())) continue;
            map.put(kit.getIdentifier(), new KitInfo(kit.getIdentifier(), kit.getVersion(), "binary"));
        }
        return map;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean onPreExecute() {
        try {
            this.installerPackageName = this.getIdManager().getInstallerPackageName();
            this.packageManager = this.getContext().getPackageManager();
            this.packageName = this.getContext().getPackageName();
            this.packageInfo = this.packageManager.getPackageInfo(this.packageName, 0);
            this.versionCode = Integer.toString(this.packageInfo.versionCode);
            String string2 = this.packageInfo.versionName == null ? "0.0" : this.packageInfo.versionName;
            this.versionName = string2;
            this.applicationLabel = this.packageManager.getApplicationLabel(this.getContext().getApplicationInfo()).toString();
            this.targetAndroidSdkVersion = Integer.toString(this.getContext().getApplicationInfo().targetSdkVersion);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Fabric.getLogger().e("Fabric", "Failed init", nameNotFoundException);
            return false;
        }
    }
}

