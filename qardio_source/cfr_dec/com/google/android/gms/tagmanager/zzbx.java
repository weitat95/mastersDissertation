/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.tagmanager;

import android.text.TextUtils;

final class zzbx {
    private final long zzdxv;
    private final long zzkfv;
    private final long zzkfw;
    private String zzkfx;

    zzbx(long l, long l2, long l3) {
        this.zzkfv = l;
        this.zzdxv = l2;
        this.zzkfw = l3;
    }

    final long zzbey() {
        return this.zzkfv;
    }

    final long zzbez() {
        return this.zzkfw;
    }

    final String zzbfa() {
        return this.zzkfx;
    }

    final void zzlp(String string2) {
        if (string2 == null || TextUtils.isEmpty((CharSequence)string2.trim())) {
            return;
        }
        this.zzkfx = string2;
    }
}

