/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.text.TextUtils
 */
package com.google.android.gms.common.internal;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.common.util.zzw;

public final class zzbq {
    public static void checkArgument(boolean bl) {
        if (!bl) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean bl, Object object) {
        if (!bl) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException("null reference");
        }
        return t;
    }

    public static <T> T checkNotNull(T t, Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t;
    }

    public static void checkState(boolean bl) {
        if (!bl) {
            throw new IllegalStateException();
        }
    }

    public static void zza(Handler handler) {
        if (Looper.myLooper() != handler.getLooper()) {
            throw new IllegalStateException("Must be called on the handler thread");
        }
    }

    public static void zza(boolean bl, Object object) {
        if (!bl) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    public static void zza(boolean bl, String string2, Object ... arrobject) {
        if (!bl) {
            throw new IllegalStateException(String.format(string2, arrobject));
        }
    }

    public static void zzb(boolean bl, String string2, Object ... arrobject) {
        if (!bl) {
            throw new IllegalArgumentException(String.format(string2, arrobject));
        }
    }

    public static void zzge(String string2) {
        if (!zzw.zzau()) {
            throw new IllegalStateException(string2);
        }
    }

    public static String zzgm(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("Given String is empty or null");
        }
        return string2;
    }

    public static void zzgn(String string2) {
        if (zzw.zzau()) {
            throw new IllegalStateException(string2);
        }
    }

    public static String zzh(String string2, Object object) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return string2;
    }
}

