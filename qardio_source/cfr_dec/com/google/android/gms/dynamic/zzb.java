/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.dynamic;

import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.dynamic.zzi;
import com.google.android.gms.dynamic.zzo;
import java.util.Iterator;

final class zzb
implements zzo<T> {
    private /* synthetic */ zza zzgwh;

    zzb(zza zza2) {
        this.zzgwh = zza2;
    }

    @Override
    public final void zza(T object) {
        zza.zza(this.zzgwh, object);
        object = zza.zza(this.zzgwh).iterator();
        while (object.hasNext()) {
            ((zzi)object.next()).zzb(zza.zzb(this.zzgwh));
        }
        zza.zza(this.zzgwh).clear();
        zza.zza(this.zzgwh, null);
    }
}

