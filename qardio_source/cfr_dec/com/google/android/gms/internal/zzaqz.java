/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

public final class zzaqz
extends Enum<zzaqz> {
    private static /* enum */ zzaqz zzdvh = new zzaqz();
    public static final /* enum */ zzaqz zzdvi = new zzaqz();
    private static final /* synthetic */ zzaqz[] zzdvj;

    static {
        zzdvj = new zzaqz[]{zzdvh, zzdvi};
    }

    public static zzaqz[] values() {
        return (zzaqz[])zzdvj.clone();
    }

    public static zzaqz zzee(String string2) {
        if ("GZIP".equalsIgnoreCase(string2)) {
            return zzdvi;
        }
        return zzdvh;
    }
}

