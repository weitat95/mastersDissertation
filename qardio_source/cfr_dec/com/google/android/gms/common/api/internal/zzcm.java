/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.internal.zzbq;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzcm {
    private final Set<zzci<?>> zzfab = Collections.newSetFromMap(new WeakHashMap());

    public static <L> zzci<L> zzb(L l, Looper looper, String string2) {
        zzbq.checkNotNull(l, "Listener must not be null");
        zzbq.checkNotNull(looper, "Looper must not be null");
        zzbq.checkNotNull(string2, "Listener type must not be null");
        return new zzci<L>(looper, l, string2);
    }

    public final void release() {
        Iterator<zzci<?>> iterator = this.zzfab.iterator();
        while (iterator.hasNext()) {
            iterator.next().clear();
        }
        this.zzfab.clear();
    }
}

