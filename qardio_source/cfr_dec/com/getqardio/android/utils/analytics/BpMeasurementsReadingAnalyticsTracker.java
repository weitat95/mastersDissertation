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

public class BpMeasurementsReadingAnalyticsTracker {
    /*
     * Enabled aggressive block sorting
     */
    private static void trackMeasurement(Context context, String string2, int n, int n2, boolean bl, Properties object) {
        Object object2 = object;
        if (object == null) {
            object2 = new Properties();
        }
        ((Properties)object2).putValue("multi-measurement", n);
        if (n2 > 0) {
            ((Properties)object2).putValue("pause", n2);
        }
        object = bl ? "yes" : "no";
        ((Properties)object2).putValue("visitor", object);
        BaseAnalyticsTracker.trackEvent(context, string2, (Properties)object2);
    }

    public static void trackMeasurementCancelled(Context context, int n, int n2, boolean bl, boolean bl2) {
        BpMeasurementsReadingAnalyticsTracker.trackMeasurementWithErrorKey(context, "cancelled QA measurement", n, n2, bl, bl2);
    }

    public static void trackMeasurementCompleted(Context context, int n, int n2, boolean bl, boolean bl2) {
        BpMeasurementsReadingAnalyticsTracker.trackMeasurementWithErrorKey(context, "completed QA measurement", n, n2, bl, bl2);
    }

    public static void trackMeasurementStarted(Context context, int n, int n2, boolean bl) {
        BpMeasurementsReadingAnalyticsTracker.trackMeasurement(context, "started QA measurement", n, n2, bl, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void trackMeasurementWithErrorKey(Context context, String string2, int n, int n2, boolean bl, boolean bl2) {
        Properties properties = new Properties(1);
        String string3 = bl ? "yes" : "no";
        properties.putValue("error", string3);
        BpMeasurementsReadingAnalyticsTracker.trackMeasurement(context, string2, n, n2, bl2, properties);
    }
}

