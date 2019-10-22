/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.samsung.android.sdk.shealth.tracker;

import android.text.TextUtils;
import java.util.regex.Pattern;

class Validator {
    static boolean isValidTileId(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return false;
        }
        return Pattern.matches("^[^\\s\b<>&\t\r\n\f.]*", string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean isValidTrackerId(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2) || string2.indexOf("tracker.") != 0) {
            return false;
        }
        return Pattern.matches("^[^\\s\b<>&\\.]*", string2.substring("tracker.".length()));
    }
}

