/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils;

import android.content.Context;
import com.google.android.gms.common.GoogleApiAvailability;
import timber.log.Timber;

public class GooglePlayServicesUtils {
    public static boolean isPlayServicesAvailable(Context context) {
        try {
            int n = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
            return n == 0;
        }
        catch (Exception exception) {
            Timber.e(exception, "Exception occurred during checking API availability", new Object[0]);
            return false;
        }
    }
}

