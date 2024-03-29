/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.settings;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.CachedSettingsIo;
import io.fabric.sdk.android.services.settings.DefaultCachedSettingsIo;
import io.fabric.sdk.android.services.settings.DefaultSettingsController;
import io.fabric.sdk.android.services.settings.DefaultSettingsJsonTransform;
import io.fabric.sdk.android.services.settings.DefaultSettingsSpiCall;
import io.fabric.sdk.android.services.settings.SettingsCacheBehavior;
import io.fabric.sdk.android.services.settings.SettingsController;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.SettingsJsonTransform;
import io.fabric.sdk.android.services.settings.SettingsRequest;
import io.fabric.sdk.android.services.settings.SettingsSpiCall;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Settings {
    private boolean initialized = false;
    private SettingsController settingsController;
    private final AtomicReference<SettingsData> settingsData = new AtomicReference();
    private final CountDownLatch settingsDataLatch = new CountDownLatch(1);

    private Settings() {
    }

    public static Settings getInstance() {
        return LazyHolder.INSTANCE;
    }

    private void setSettingsData(SettingsData settingsData) {
        this.settingsData.set(settingsData);
        this.settingsDataLatch.countDown();
    }

    public SettingsData awaitSettingsData() {
        try {
            this.settingsDataLatch.await();
            SettingsData settingsData = this.settingsData.get();
            return settingsData;
        }
        catch (InterruptedException interruptedException) {
            Fabric.getLogger().e("Fabric", "Interrupted while waiting for settings data.");
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Settings initialize(Kit kit, IdManager idManager, HttpRequestFactory object, String string2, String string3, String string4) {
        synchronized (this) {
            block7: {
                boolean bl = this.initialized;
                if (!bl) break block7;
                do {
                    return this;
                    break;
                } while (true);
            }
            if (this.settingsController == null) {
                Context context = kit.getContext();
                String string5 = idManager.getAppIdentifier();
                String string6 = new ApiKey().getValue(context);
                String string7 = idManager.getInstallerPackageName();
                SystemCurrentTimeProvider systemCurrentTimeProvider = new SystemCurrentTimeProvider();
                DefaultSettingsJsonTransform defaultSettingsJsonTransform = new DefaultSettingsJsonTransform();
                DefaultCachedSettingsIo defaultCachedSettingsIo = new DefaultCachedSettingsIo(kit);
                String string8 = CommonUtils.getAppIconHashOrNull(context);
                object = new DefaultSettingsSpiCall(kit, string4, String.format(Locale.US, "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings", string5), (HttpRequestFactory)object);
                this.settingsController = new DefaultSettingsController(kit, new SettingsRequest(string6, idManager.getModelName(), idManager.getOsBuildVersionString(), idManager.getOsDisplayVersionString(), idManager.getAdvertisingId(), idManager.getAppInstallIdentifier(), idManager.getAndroidId(), CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(context)), string3, string2, DeliveryMechanism.determineFrom(string7).getId(), string8), systemCurrentTimeProvider, defaultSettingsJsonTransform, defaultCachedSettingsIo, (SettingsSpiCall)object);
            }
            this.initialized = true;
            return this;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean loadSettingsData() {
        synchronized (this) {
            SettingsData settingsData = this.settingsController.loadSettingsData();
            this.setSettingsData(settingsData);
            if (settingsData == null) return false;
            return true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean loadSettingsSkippingCache() {
        synchronized (this) {
            SettingsData settingsData;
            block4: {
                settingsData = this.settingsController.loadSettingsData(SettingsCacheBehavior.SKIP_CACHE_LOOKUP);
                this.setSettingsData(settingsData);
                if (settingsData != null) break block4;
                Fabric.getLogger().e("Fabric", "Failed to force reload of settings from Crashlytics.", null);
            }
            if (settingsData == null) return false;
            return true;
        }
    }

    static class LazyHolder {
        private static final Settings INSTANCE = new Settings();
    }

}

