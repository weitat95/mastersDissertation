/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import java.util.Arrays;

final class zzay {
    final String zzbhb;
    final byte[] zzkfd;

    zzay(String string2, byte[] arrby) {
        this.zzbhb = string2;
        this.zzkfd = arrby;
    }

    public final String toString() {
        String string2 = this.zzbhb;
        int n = Arrays.hashCode(this.zzkfd);
        return new StringBuilder(String.valueOf(string2).length() + 54).append("KeyAndSerialized: key = ").append(string2).append(" serialized hash = ").append(n).toString();
    }
}

