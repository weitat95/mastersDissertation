/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

public final class zzaqt
extends Enum<zzaqt> {
    public static final /* enum */ zzaqt zzdux = new zzaqt();
    private static /* enum */ zzaqt zzduy = new zzaqt();
    private static /* enum */ zzaqt zzduz = new zzaqt();
    private static /* enum */ zzaqt zzdva = new zzaqt();
    public static final /* enum */ zzaqt zzdvb = new zzaqt();
    private static /* enum */ zzaqt zzdvc = new zzaqt();
    private static final /* synthetic */ zzaqt[] zzdvd;

    static {
        zzdvd = new zzaqt[]{zzdux, zzduy, zzduz, zzdva, zzdvb, zzdvc};
    }

    public static zzaqt[] values() {
        return (zzaqt[])zzdvd.clone();
    }

    public static zzaqt zzed(String string2) {
        if ("BATCH_BY_SESSION".equalsIgnoreCase(string2)) {
            return zzduy;
        }
        if ("BATCH_BY_TIME".equalsIgnoreCase(string2)) {
            return zzduz;
        }
        if ("BATCH_BY_BRUTE_FORCE".equalsIgnoreCase(string2)) {
            return zzdva;
        }
        if ("BATCH_BY_COUNT".equalsIgnoreCase(string2)) {
            return zzdvb;
        }
        if ("BATCH_BY_SIZE".equalsIgnoreCase(string2)) {
            return zzdvc;
        }
        return zzdux;
    }
}

