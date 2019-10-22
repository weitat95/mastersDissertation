/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 */
package com.getqardio.android.provider;

import android.net.Uri;

public class AppProvideContract {
    static final Uri BASE_CONTENT_URI = Uri.parse((String)"content://com.getqardio.android");

    public static interface Tables {

        public static interface Faqs {
            public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath("faqs").build();
        }

        public static interface Users {
            public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath("users").build();
        }

    }

}

