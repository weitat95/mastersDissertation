/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils.analytics;

import android.content.Context;
import com.getqardio.android.utils.analytics.BaseAnalyticsTracker;
import com.segment.analytics.Properties;

public class RateAppTracker {
    public static void trackNoThanks(Context context) {
        RateAppTracker.trackRatePopup(context, "no thanks");
    }

    private static void trackRatePopup(Context context, String string2) {
        Properties properties = new Properties();
        properties.putValue("response", string2);
        BaseAnalyticsTracker.trackEvent(context, "rate qardio pop up", properties);
    }

    public static void trackRateQardio(Context context) {
        RateAppTracker.trackRatePopup(context, "rate qardio");
    }

    public static void trackRemindMeLater(Context context) {
        RateAppTracker.trackRatePopup(context, "remind me later");
    }
}

