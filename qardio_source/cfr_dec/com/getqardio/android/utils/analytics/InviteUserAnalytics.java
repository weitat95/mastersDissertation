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

public class InviteUserAnalytics {
    public static void trackScreenLoaded(Context context) {
        Properties properties = new Properties();
        properties.put("name", (Object)"invite new user");
        BaseAnalyticsTracker.trackEvent(context, "loaded a screen", properties);
    }

    public static void trackUserInvite(Context context) {
        BaseAnalyticsTracker.trackEvent(context, "invited new qb user", null);
    }
}

