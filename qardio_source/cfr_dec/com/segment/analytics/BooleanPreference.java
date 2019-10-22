/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.segment.analytics;

import android.content.SharedPreferences;

public class BooleanPreference {
    private final boolean defaultValue;
    private final String key;
    private final SharedPreferences preferences;

    public BooleanPreference(SharedPreferences sharedPreferences, String string2, boolean bl) {
        this.preferences = sharedPreferences;
        this.key = string2;
        this.defaultValue = bl;
    }

    public boolean get() {
        return this.preferences.getBoolean(this.key, this.defaultValue);
    }

    public void set(boolean bl) {
        this.preferences.edit().putBoolean(this.key, bl).apply();
    }
}

