/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;

final class zzclp {
    final String mAppId;
    final String mName;
    final String mOrigin;
    final Object mValue;
    final long zzjjm;

    zzclp(String string2, String string3, String string4, long l, Object object) {
        zzbq.zzgm(string2);
        zzbq.zzgm(string4);
        zzbq.checkNotNull(object);
        this.mAppId = string2;
        this.mOrigin = string3;
        this.mName = string4;
        this.zzjjm = l;
        this.mValue = object;
    }
}

