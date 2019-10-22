/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

final class zzdvo
extends WeakReference<Throwable> {
    private final int zzmal;

    public zzdvo(Throwable throwable, ReferenceQueue<Throwable> referenceQueue) {
        super(throwable, null);
        if (throwable == null) {
            throw new NullPointerException("The referent cannot be null");
        }
        this.zzmal = System.identityHashCode(throwable);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals(Object object) {
        boolean bl = true;
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        boolean bl2 = bl;
        if (this == object) return bl2;
        object = (zzdvo)object;
        if (this.zzmal != ((zzdvo)object).zzmal) return false;
        bl2 = bl;
        if (this.get() == ((Reference)object).get()) return bl2;
        return false;
    }

    public final int hashCode() {
        return this.zzmal;
    }
}

