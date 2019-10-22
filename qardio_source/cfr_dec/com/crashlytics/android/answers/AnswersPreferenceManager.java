/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

class AnswersPreferenceManager {
    private final PreferenceStore prefStore;

    AnswersPreferenceManager(PreferenceStore preferenceStore) {
        this.prefStore = preferenceStore;
    }

    public static AnswersPreferenceManager build(Context context) {
        return new AnswersPreferenceManager(new PreferenceStoreImpl(context, "settings"));
    }

    @SuppressLint(value={"CommitPrefEdits"})
    public boolean hasAnalyticsLaunched() {
        return this.prefStore.get().getBoolean("analytics_launched", false);
    }

    @SuppressLint(value={"CommitPrefEdits"})
    public void setAnalyticsLaunched() {
        this.prefStore.save(this.prefStore.edit().putBoolean("analytics_launched", true));
    }
}

