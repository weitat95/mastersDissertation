/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.zzf;

public final class zzaqb {
    public static final String VERSION = String.valueOf(zzf.GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000).replaceAll("(\\d+)(\\d)(\\d\\d)", "$1.$2.$3");
    public static final String zzdtc;

    /*
     * Enabled aggressive block sorting
     */
    static {
        String string2 = String.valueOf(VERSION);
        string2 = string2.length() != 0 ? "ma".concat(string2) : new String("ma");
        zzdtc = string2;
    }
}

