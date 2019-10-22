/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.CachedSettingsIo;
import io.fabric.sdk.android.services.settings.SettingsCacheBehavior;
import io.fabric.sdk.android.services.settings.SettingsController;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.SettingsJsonTransform;
import io.fabric.sdk.android.services.settings.SettingsRequest;
import io.fabric.sdk.android.services.settings.SettingsSpiCall;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultSettingsController
implements SettingsController {
    private final CachedSettingsIo cachedSettingsIo;
    private final CurrentTimeProvider currentTimeProvider;
    private final Kit kit;
    private final PreferenceStore preferenceStore;
    private final SettingsJsonTransform settingsJsonTransform;
    private final SettingsRequest settingsRequest;
    private final SettingsSpiCall settingsSpiCall;

    public DefaultSettingsController(Kit kit, SettingsRequest settingsRequest, CurrentTimeProvider currentTimeProvider, SettingsJsonTransform settingsJsonTransform, CachedSettingsIo cachedSettingsIo, SettingsSpiCall settingsSpiCall) {
        this.kit = kit;
        this.settingsRequest = settingsRequest;
        this.currentTimeProvider = currentTimeProvider;
        this.settingsJsonTransform = settingsJsonTransform;
        this.cachedSettingsIo = cachedSettingsIo;
        this.settingsSpiCall = settingsSpiCall;
        this.preferenceStore = new PreferenceStoreImpl(this.kit);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SettingsData getCachedSettingsData(SettingsCacheBehavior object) {
        block7: {
            Object object2;
            Object object3 = object2 = null;
            try {
                if (SettingsCacheBehavior.SKIP_CACHE_LOOKUP.equals(object)) break block7;
                object3 = object2;
                JSONObject jSONObject = this.cachedSettingsIo.readCachedSettings();
                if (jSONObject != null) {
                    object3 = object2;
                    SettingsData settingsData = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, jSONObject);
                    if (settingsData != null) {
                        object3 = object2;
                        this.logSettings(jSONObject, "Loaded cached settings: ");
                        object3 = object2;
                        long l = this.currentTimeProvider.getCurrentTimeMillis();
                        object3 = object2;
                        if (!SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION.equals(object)) {
                            object3 = object2;
                            if (settingsData.isExpired(l)) {
                                object3 = object2;
                                Fabric.getLogger().d("Fabric", "Cached settings have expired.");
                                return null;
                            }
                        }
                        object3 = object = settingsData;
                        Fabric.getLogger().d("Fabric", "Returning cached settings.");
                        return object;
                    }
                    object3 = object2;
                    Fabric.getLogger().e("Fabric", "Failed to transform cached settings data.", null);
                    return null;
                }
            }
            catch (Exception exception) {
                Fabric.getLogger().e("Fabric", "Failed to get cached settings", exception);
                return object3;
            }
            object3 = object2;
            Fabric.getLogger().d("Fabric", "No cached settings data found.");
        }
        return null;
    }

    private void logSettings(JSONObject jSONObject, String string2) throws JSONException {
        Fabric.getLogger().d("Fabric", string2 + jSONObject.toString());
    }

    boolean buildInstanceIdentifierChanged() {
        return !this.getStoredBuildInstanceIdentifier().equals(this.getBuildInstanceIdentifierFromContext());
    }

    String getBuildInstanceIdentifierFromContext() {
        return CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(this.kit.getContext()));
    }

    String getStoredBuildInstanceIdentifier() {
        return this.preferenceStore.get().getString("existing_instance_identifier", "");
    }

    @Override
    public SettingsData loadSettingsData() {
        return this.loadSettingsData(SettingsCacheBehavior.USE_CACHE);
    }

    @Override
    public SettingsData loadSettingsData(SettingsCacheBehavior object) {
        Object object2;
        block13: {
            JSONObject jSONObject;
            Object object3;
            block12: {
                JSONObject jSONObject2 = null;
                object3 = jSONObject = null;
                object2 = jSONObject2;
                try {
                    if (Fabric.isDebuggable()) break block12;
                    object3 = jSONObject;
                    object2 = jSONObject2;
                }
                catch (Exception exception) {
                    Fabric.getLogger().e("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", exception);
                    return object2;
                }
                if (this.buildInstanceIdentifierChanged()) break block12;
                object2 = jSONObject2;
                object3 = this.getCachedSettingsData((SettingsCacheBehavior)((Object)object));
            }
            object2 = object3;
            if (object3 == null) {
                object2 = object3;
                jSONObject = this.settingsSpiCall.invoke(this.settingsRequest);
                object2 = object3;
                if (jSONObject == null) break block13;
                object2 = object3;
                object = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, jSONObject);
                object2 = object;
                this.cachedSettingsIo.writeCachedSettings(((SettingsData)object).expiresAtMillis, jSONObject);
                object2 = object;
                this.logSettings(jSONObject, "Loaded settings: ");
                object2 = object;
                this.setStoredBuildInstanceIdentifier(this.getBuildInstanceIdentifierFromContext());
                object2 = object;
            }
        }
        object = object2;
        if (object2 == null) {
            object = this.getCachedSettingsData(SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION);
        }
        return object;
    }

    @SuppressLint(value={"CommitPrefEdits"})
    boolean setStoredBuildInstanceIdentifier(String string2) {
        SharedPreferences.Editor editor = this.preferenceStore.edit();
        editor.putString("existing_instance_identifier", string2);
        return this.preferenceStore.save(editor);
    }
}

