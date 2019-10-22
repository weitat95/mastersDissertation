/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.preference.PreferenceManager
 */
package com.getqardio.android.utils.wizard;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.getqardio.android.CustomApplication;

public class OnboardingPrefsManager {
    private static SharedPreferences getDefaultPreferences() {
        return PreferenceManager.getDefaultSharedPreferences((Context)CustomApplication.getApplication());
    }

    private static boolean getValue(SharedPreferences sharedPreferences, String string2, boolean bl) {
        return sharedPreferences.getBoolean(string2, bl);
    }

    public static boolean isOnboardingDiscovered() {
        return OnboardingPrefsManager.getValue(OnboardingPrefsManager.getDefaultPreferences(), "com.getqardio.android.preferences.IS_ONBOARDING_SHOWN", false);
    }

    public static boolean isOutroOnboardingDiscovered() {
        return OnboardingPrefsManager.getValue(OnboardingPrefsManager.getDefaultPreferences(), "com.getqardio.android.preferences.PREF_IS_OUTRO_SHOWN", false);
    }

    private static void putValue(SharedPreferences sharedPreferences, String string2, boolean bl) {
        sharedPreferences = sharedPreferences.edit();
        sharedPreferences.putBoolean(string2, bl);
        sharedPreferences.apply();
    }

    public static void updateOnboardingDiscovered() {
        OnboardingPrefsManager.putValue(OnboardingPrefsManager.getDefaultPreferences(), "com.getqardio.android.preferences.IS_ONBOARDING_SHOWN", true);
    }

    public static void updateOutroOnboardingDiscovered() {
        OnboardingPrefsManager.putValue(OnboardingPrefsManager.getDefaultPreferences(), "com.getqardio.android.preferences.PREF_IS_OUTRO_SHOWN", true);
    }
}

