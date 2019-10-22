/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.webkit.MimeTypeMap
 *  javax.annotation.Nullable
 */
package com.facebook.common.media;

import android.webkit.MimeTypeMap;
import com.facebook.common.internal.ImmutableMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class MediaUtils {
    public static final Map<String, String> ADDITIONAL_ALLOWED_MIME_TYPES = ImmutableMap.of("mkv", "video/x-matroska");

    @Nullable
    private static String extractExtension(String string2) {
        int n = string2.lastIndexOf(46);
        if (n < 0 || n == string2.length() - 1) {
            return null;
        }
        return string2.substring(n + 1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public static String extractMime(String string2) {
        String string3;
        if ((string2 = MediaUtils.extractExtension(string2)) == null) {
            return null;
        }
        String string4 = string2.toLowerCase(Locale.US);
        string2 = string3 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(string4);
        if (string3 != null) return string2;
        return ADDITIONAL_ALLOWED_MIME_TYPES.get(string4);
    }

    public static boolean isVideo(@Nullable String string2) {
        return string2 != null && string2.startsWith("video/");
    }
}

