/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.samsung.android.sdk.shealth;

import android.net.Uri;

final class PluginContract {
    public static final Uri CONTENT_URI = Uri.parse((String)"content://com.samsung.android.sdk.shealth");

    public static final class TileControllerInfo {
        public static final Uri CONTENT_URI = Uri.withAppendedPath((Uri)CONTENT_URI, (String)"tile_controller");
    }

}

