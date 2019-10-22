/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.zzbh;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.List;

public final class zzbi {
    private final Object zzddc;
    private final List<String> zzgbe;

    private zzbi(Object object) {
        this.zzddc = zzbq.checkNotNull(object);
        this.zzgbe = new ArrayList<String>();
    }

    /* synthetic */ zzbi(Object object, zzbh zzbh2) {
        this(object);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder(100).append(this.zzddc.getClass().getSimpleName()).append('{');
        int n = this.zzgbe.size();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(this.zzgbe.get(i));
            if (i >= n - 1) continue;
            stringBuilder.append(", ");
        }
        return stringBuilder.append('}').toString();
    }

    public final zzbi zzg(String string2, Object object) {
        List<String> list = this.zzgbe;
        string2 = zzbq.checkNotNull(string2);
        object = String.valueOf(object);
        list.add(new StringBuilder(String.valueOf(string2).length() + 1 + String.valueOf(object).length()).append(string2).append("=").append((String)object).toString());
        return this;
    }
}

