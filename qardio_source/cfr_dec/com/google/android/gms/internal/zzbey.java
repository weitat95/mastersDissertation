/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbez;
import com.google.android.gms.internal.zzbfa;
import com.google.android.gms.internal.zzbfb;
import com.google.android.gms.internal.zzbfc;
import com.google.android.gms.internal.zzbfd;
import com.google.android.gms.internal.zzbfe;

public class zzbey<T> {
    private static String READ_PERMISSION;
    private static final Object sLock;
    private static zzbfe zzfvo;
    private static int zzfvp;
    private String zzbhb;
    private T zzbhc;
    private T zzfvq = null;

    static {
        sLock = new Object();
        zzfvo = null;
        zzfvp = 0;
        READ_PERMISSION = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    }

    protected zzbey(String string2, T t) {
        this.zzbhb = string2;
        this.zzbhc = t;
    }

    public static zzbey<Float> zza(String string2, Float f) {
        return new zzbfc(string2, f);
    }

    public static zzbey<Integer> zza(String string2, Integer n) {
        return new zzbfb(string2, n);
    }

    public static zzbey<Long> zza(String string2, Long l) {
        return new zzbfa(string2, l);
    }

    public static zzbey<Boolean> zze(String string2, boolean bl) {
        return new zzbez(string2, bl);
    }

    public static zzbey<String> zzs(String string2, String string3) {
        return new zzbfd(string2, string3);
    }
}

