/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdvo;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

final class zzdvn {
    private final ConcurrentHashMap<zzdvo, List<Throwable>> zzmaj = new ConcurrentHashMap(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzmak = new ReferenceQueue();

    zzdvn() {
    }

    public final List<Throwable> zza(Throwable object, boolean bl) {
        Reference<Throwable> reference = this.zzmak.poll();
        while (reference != null) {
            this.zzmaj.remove(reference);
            reference = this.zzmak.poll();
        }
        object = new zzdvo((Throwable)object, null);
        return this.zzmaj.get(object);
    }
}

