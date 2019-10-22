/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils.analytics;

import android.content.Context;
import com.getqardio.android.utils.analytics.AnalyticsDevice;
import com.getqardio.android.utils.analytics.BaseAnalyticsTracker;

public class GeneralAnalyticsTracker {
    public static void trackActivityTrackerHistoryScreen(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "activity tracker history screen");
    }

    public static void trackActivityTrackerTodayScreen(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "activity tracker today screen");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void trackChangedPhotoSlideshow(Context context, boolean bl) {
        String string2 = bl ? "enabled photo slideshow" : "disabled photo slideshow";
        BaseAnalyticsTracker.trackEvent(context, string2);
    }

    public static void trackChooseDevice(Context context, AnalyticsDevice analyticsDevice) {
        BaseAnalyticsTracker.trackEventWithDevice(context, "chose device", analyticsDevice);
    }

    public static void trackClickedOnMenu(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "clicked on menu");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void trackGoogleFitChanged(Context context, boolean bl) {
        String string2 = bl ? "enabled google fit" : "disabled google fit";
        BaseAnalyticsTracker.trackEvent(context, string2);
    }

    public static void trackPairingCompleted(Context context, AnalyticsDevice analyticsDevice) {
        BaseAnalyticsTracker.trackEventWithDevice(context, "pairing completed", analyticsDevice);
    }

    public static void trackPairingStarted(Context context, AnalyticsDevice analyticsDevice) {
        BaseAnalyticsTracker.trackEventWithDevice(context, "started pairing", analyticsDevice);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void trackPlacesChanged(Context context, boolean bl) {
        String string2 = bl ? "enabled places" : "disabled places";
        BaseAnalyticsTracker.trackEvent(context, string2);
    }

    public static void trackSelectedPhotoAlbum(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "selected photo album");
    }
}

