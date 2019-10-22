/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils.analytics;

import android.content.Context;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.segment.analytics.Analytics;
import com.segment.analytics.Options;
import com.segment.analytics.Traits;

public class IdentifyHelper {
    public static void identify(Context context, String string2, CustomTraits customTraits) {
        if (string2 != null && !string2.isEmpty()) {
            Analytics.with(context).identify(string2, customTraits, null);
        }
    }

    public static void reset(Context context) {
        Analytics.with(context).reset();
    }
}

