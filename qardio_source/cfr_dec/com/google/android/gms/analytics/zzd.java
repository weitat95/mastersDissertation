/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.internal.zzaru;

public final class zzd {
    public static String zzaj(int n) {
        return zzd.zzc("cd", n);
    }

    public static String zzal(int n) {
        return zzd.zzc("cm", n);
    }

    public static String zzam(int n) {
        return zzd.zzc("&pr", n);
    }

    public static String zzan(int n) {
        return zzd.zzc("pr", n);
    }

    public static String zzao(int n) {
        return zzd.zzc("&promo", n);
    }

    public static String zzap(int n) {
        return zzd.zzc("promo", n);
    }

    public static String zzaq(int n) {
        return zzd.zzc("pi", n);
    }

    public static String zzar(int n) {
        return zzd.zzc("&il", n);
    }

    public static String zzas(int n) {
        return zzd.zzc("il", n);
    }

    private static String zzc(String string2, int n) {
        if (n <= 0) {
            zzaru.zzf("index out of range for prefix", string2);
            return "";
        }
        return new StringBuilder(String.valueOf(string2).length() + 11).append(string2).append(n).toString();
    }
}

