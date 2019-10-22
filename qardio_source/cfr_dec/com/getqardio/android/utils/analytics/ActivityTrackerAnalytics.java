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

public class ActivityTrackerAnalytics {
    public static void trackActivityTrackerEnabled(Context context, boolean bl, String string2) {
        Properties properties = new Properties();
        properties.put("screen name", (Object)string2);
        if (bl) {
            BaseAnalyticsTracker.trackEvent(context, "enabled activity tracker", properties);
            return;
        }
        BaseAnalyticsTracker.trackEvent(context, "disabled activity tracker", properties);
    }
}

