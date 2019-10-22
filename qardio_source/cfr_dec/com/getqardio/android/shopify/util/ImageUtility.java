/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 */
package com.getqardio.android.shopify.util;

import android.net.Uri;
import java.net.URI;
import timber.log.Timber;

public final class ImageUtility {
    private ImageUtility() {
    }

    public static String getImageSuffixForDimensions(int n, int n2) {
        if ((n = Math.max(n, n2)) <= 16) {
            return "_pico";
        }
        if (n <= 32) {
            return "_icon";
        }
        if (n <= 50) {
            return "_thumb";
        }
        if (n <= 100) {
            return "_small";
        }
        if (n <= 160) {
            return "_compact";
        }
        if (n <= 240) {
            return "_medium";
        }
        if (n <= 480) {
            return "_large";
        }
        if (n <= 600) {
            return "_grande";
        }
        return "_1024x1024";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getSizedImageUrl(String string2, int n, int n2) {
        if (string2 == null) {
            return null;
        }
        try {
            String string3 = new URI(string2).getPath();
            String string4 = ImageUtility.getImageSuffixForDimensions(n, n2);
            n = string3.lastIndexOf(46);
            if (-1 == n) {
                string3 = string3 + string4;
                return Uri.parse((String)string2).buildUpon().path(string3).toString();
            }
            string3 = string3.substring(0, n) + string4 + string3.substring(n);
            return Uri.parse((String)string2).buildUpon().path(string3).toString();
        }
        catch (Exception exception) {
            Timber.v(exception, "Getting sized image URL", new Object[0]);
            return null;
        }
    }
}

