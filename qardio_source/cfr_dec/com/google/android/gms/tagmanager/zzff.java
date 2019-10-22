/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzdjc;
import com.google.android.gms.internal.zzdjg;
import com.google.android.gms.tagmanager.zzep;
import com.google.android.gms.tagmanager.zzer;
import com.google.android.gms.tagmanager.zzfc;
import com.google.android.gms.tagmanager.zzfh;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzff
implements zzfh {
    private /* synthetic */ Map zzkix;
    private /* synthetic */ Map zzkiy;
    private /* synthetic */ Map zzkiz;
    private /* synthetic */ Map zzkja;

    zzff(zzfc zzfc2, Map map, Map map2, Map map3, Map map4) {
        this.zzkix = map;
        this.zzkiy = map2;
        this.zzkiz = map3;
        this.zzkja = map4;
    }

    @Override
    public final void zza(zzdjg zzdjg2, Set<zzdjc> collection, Set<zzdjc> set, zzer zzer2) {
        List list = (List)this.zzkix.get(zzdjg2);
        this.zzkiy.get(zzdjg2);
        if (list != null) {
            collection.addAll(list);
            zzer2.zzbfe();
        }
        collection = (List)this.zzkiz.get(zzdjg2);
        this.zzkja.get(zzdjg2);
        if (collection != null) {
            set.addAll(collection);
            zzer2.zzbff();
        }
    }
}

