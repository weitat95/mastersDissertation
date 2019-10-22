/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.os.Bundle;

public class MediaBrowserCompatUtils {
    /*
     * Enabled aggressive block sorting
     */
    public static boolean areSameOptions(Bundle bundle, Bundle bundle2) {
        if (bundle == bundle2) return true;
        if (bundle == null) {
            if (bundle2.getInt("android.media.browse.extra.PAGE", -1) == -1 && bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1) == -1) return true;
            return false;
        }
        if (bundle2 == null) {
            if (bundle.getInt("android.media.browse.extra.PAGE", -1) == -1 && bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1) == -1) return true;
            return false;
        }
        if (bundle.getInt("android.media.browse.extra.PAGE", -1) != bundle2.getInt("android.media.browse.extra.PAGE", -1) || bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1) != bundle2.getInt("android.media.browse.extra.PAGE_SIZE", -1)) return false;
        return true;
    }
}

