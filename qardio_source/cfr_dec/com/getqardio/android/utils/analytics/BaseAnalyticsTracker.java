/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils.analytics;

import android.content.Context;
import com.getqardio.android.utils.analytics.AnalyticsDevice;
import com.segment.analytics.Analytics;
import com.segment.analytics.AnalyticsContext;
import com.segment.analytics.Properties;

public class BaseAnalyticsTracker {
    static void sendScreen(Context context, String string2, String string3, Properties properties) {
        Analytics.with(context).screen(string2, string3, properties);
    }

    static void trackEvent(Context context, String string2) {
        BaseAnalyticsTracker.trackEvent(context, string2, null);
    }

    static void trackEvent(Context context, String string2, Properties properties) {
        Analytics.with(context).getAnalyticsContext().put("ip", (Object)"0.0.0.0");
        Analytics.with(context).track(string2, properties);
    }

    static void trackEventWithDevice(Context context, String string2, AnalyticsDevice analyticsDevice) {
        Properties properties = new Properties();
        properties.putValue("device", analyticsDevice.name());
        BaseAnalyticsTracker.trackEvent(context, string2, properties);
    }
}

