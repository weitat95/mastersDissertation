/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.util;

import java.util.regex.Pattern;

public final class zzu {
    private static final Pattern zzgev = Pattern.compile("\\$\\{(.*?)\\}");

    public static boolean zzgs(String string2) {
        return string2 == null || string2.trim().isEmpty();
    }
}

