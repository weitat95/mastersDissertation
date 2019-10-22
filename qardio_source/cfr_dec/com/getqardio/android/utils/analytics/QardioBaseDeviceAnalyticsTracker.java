/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils.analytics;

import android.content.Context;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.analytics.BaseAnalyticsTracker;
import com.segment.analytics.Properties;

public class QardioBaseDeviceAnalyticsTracker {
    public static void trackAddedPregnancyPhoto(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "added bump photo");
    }

    public static void trackChooseModeScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "choose mode");
    }

    public static void trackChooseWifiNetworkScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "choose wifi network");
    }

    public static void trackConfirmProfileScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "confirm profile info");
    }

    public static void trackEnabledHaptic(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "enabled haptic");
    }

    public static void trackEnterWifiPasswordScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "enter wifi password");
    }

    public static void trackFirmwareUpdated(Context context, String string2) {
        Properties properties = new Properties();
        properties.putValue("firmware version", string2);
        BaseAnalyticsTracker.trackEvent(context, "updated QB firmware", properties);
    }

    public static void trackModeChanged(Context context, String string2) {
        Properties properties = new Properties();
        properties.putValue("mode", string2);
        BaseAnalyticsTracker.trackEvent(context, "changed mode", properties);
    }

    public static void trackQB2SetOnFloor(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "QB2 set on floor");
    }

    public static void trackQB2SwitchOnScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "QB2 switch on");
    }

    public static void trackQbSelectedMode(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "selected QB mode");
    }

    public static void trackQbSetupCompleted(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "QB set up complete");
    }

    public static void trackSetupExtraQbProfile(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "set up extra QB profile");
    }

    public static void trackSetupQbWifi(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "set up QB wifi");
    }

    public static void trackStartQBSetUpScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "start QB set up");
    }

    public static void trackStepOffOptionalScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "step off optional");
    }

    public static void trackStepOffRequiredScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "step off required");
    }

    public static void trackStepOnScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "step on QB");
    }

    public static void trackTurnOnBluetoothScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "turn on bluetooth");
    }

    public static void trackUserToggleScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "user toggle");
    }

    public static void trackWifiReminderScreen(Context context) {
        AnalyticsScreenTracker.sendScreen(context, "wifi reminder");
    }
}

