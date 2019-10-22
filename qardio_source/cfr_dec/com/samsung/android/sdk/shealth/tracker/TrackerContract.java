/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.samsung.android.sdk.shealth.tracker;

import android.net.Uri;

final class TrackerContract {
    public static final Uri CONTENT_URI = Uri.parse((String)"content://com.samsung.android.sdk.shealth");

    public static final class TileInfo {
        public static final Uri CONTENT_URI = Uri.withAppendedPath((Uri)CONTENT_URI, (String)"tile");
        public static final Uri CONTENT_URI_POST = Uri.withAppendedPath((Uri)CONTENT_URI, (String)"post");
        public static final Uri CONTENT_URI_QUERY;
        public static final Uri CONTENT_URI_REMOVE;

        static {
            CONTENT_URI_REMOVE = Uri.withAppendedPath((Uri)CONTENT_URI, (String)"remove");
            CONTENT_URI_QUERY = Uri.withAppendedPath((Uri)CONTENT_URI, (String)"query");
        }
    }

}

