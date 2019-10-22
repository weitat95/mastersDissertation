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

public class BpAddManualMeasurementsAnalyticsTracker {
    public static void trackAddManualMeasurementClick(Context context, Screen screen) {
        Properties properties = new Properties();
        properties.putValue("type", "QA");
        properties.putValue("screen", screen.val);
        BaseAnalyticsTracker.trackEvent(context, "clicked add manual measurement", properties);
    }

    public static enum Screen {
        START("Start Blood pressure measurement"),
        HISTORY("QA history list");

        final String val;

        private Screen(String string3) {
            this.val = string3;
        }
    }

}

