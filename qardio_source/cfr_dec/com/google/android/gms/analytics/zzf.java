/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzh;
import java.util.Comparator;

final class zzf
implements Comparator<zzh> {
    zzf(zze zze2) {
    }

    @Override
    public final /* synthetic */ int compare(Object object, Object object2) {
        object = (zzh)object;
        object2 = (zzh)object2;
        return object.getClass().getCanonicalName().compareTo(object2.getClass().getCanonicalName());
    }
}

