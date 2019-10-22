/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.zzh;
import java.util.Arrays;

final class zzi
extends zzh {
    private final byte[] zzflc;

    zzi(byte[] arrby) {
        super(Arrays.copyOfRange(arrby, 0, 25));
        this.zzflc = arrby;
    }

    @Override
    final byte[] getBytes() {
        return this.zzflc;
    }
}

