/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.crashlytics.android.core;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

@SuppressLint(value={"CommitPrefEdits"})
class PreferenceManager {
    private final PreferenceStore preferenceStore;

    public PreferenceManager(PreferenceStore preferenceStore) {
        this.preferenceStore = preferenceStore;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static PreferenceManager create(PreferenceStore preferenceStore, CrashlyticsCore object) {
        if (!preferenceStore.get().getBoolean("preferences_migration_complete", false)) {
            object = new PreferenceStoreImpl((Kit)object);
            boolean bl = !preferenceStore.get().contains("always_send_reports_opt_in") && object.get().contains("always_send_reports_opt_in");
            if (bl) {
                boolean bl2 = object.get().getBoolean("always_send_reports_opt_in", false);
                preferenceStore.save(preferenceStore.edit().putBoolean("always_send_reports_opt_in", bl2));
            }
            preferenceStore.save(preferenceStore.edit().putBoolean("preferences_migration_complete", true));
        }
        return new PreferenceManager(preferenceStore);
    }

    void setShouldAlwaysSendReports(boolean bl) {
        this.preferenceStore.save(this.preferenceStore.edit().putBoolean("always_send_reports_opt_in", bl));
    }

    boolean shouldAlwaysSendReports() {
        return this.preferenceStore.get().getBoolean("always_send_reports_opt_in", false);
    }
}

