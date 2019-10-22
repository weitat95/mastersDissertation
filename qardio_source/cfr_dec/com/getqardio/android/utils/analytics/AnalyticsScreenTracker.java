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

public class AnalyticsScreenTracker {
    public static void sendScreen(Context context, String string2) {
        BaseAnalyticsTracker.sendScreen(context, "Screen", string2, null);
    }

    public static void sendScreenWithMeasurementType(Context context, String string2, String string3) {
        Properties properties = new Properties();
        properties.putValue("Measurement type", string3);
        BaseAnalyticsTracker.sendScreen(context, "Screen", string2, properties);
    }
}

