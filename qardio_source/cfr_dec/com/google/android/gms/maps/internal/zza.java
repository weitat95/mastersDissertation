/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.maps.internal;

public final class zza {
    public static Boolean zza(byte by) {
        switch (by) {
            default: {
                return null;
            }
            case 1: {
                return Boolean.TRUE;
            }
            case 0: 
        }
        return Boolean.FALSE;
    }

    public static byte zzb(Boolean bl) {
        if (bl != null) {
            if (bl.booleanValue()) {
                return 1;
            }
            return 0;
        }
        return -1;
    }
}

