/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzaq;
import java.util.Iterator;
import java.util.List;

final class zzap
implements zzaq {
    private /* synthetic */ DataLayer zzkes;

    zzap(DataLayer dataLayer) {
        this.zzkes = dataLayer;
    }

    @Override
    public final void zzak(List<DataLayer.zza> object) {
        object = object.iterator();
        while (object.hasNext()) {
            DataLayer.zza zza2 = (DataLayer.zza)object.next();
            DataLayer.zza(this.zzkes, DataLayer.zzn(zza2.zzbhb, zza2.mValue));
        }
        DataLayer.zza(this.zzkes).countDown();
    }
}

