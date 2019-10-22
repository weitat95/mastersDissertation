/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.zzb;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.analytics.zzm;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzapd;
import com.google.android.gms.internal.zzapi;
import com.google.android.gms.internal.zzapm;
import com.google.android.gms.internal.zzapq;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqn;
import com.google.android.gms.internal.zzaqu;
import com.google.android.gms.internal.zzarh;
import java.util.List;
import java.util.ListIterator;

public class zza
extends zzi<zza> {
    private final zzaqc zzdoh;
    private boolean zzdoi;

    public zza(zzaqc zzaqc2) {
        super(zzaqc2.zzwv(), zzaqc2.zzws());
        this.zzdoh = zzaqc2;
    }

    public final void enableAdvertisingIdCollection(boolean bl) {
        this.zzdoi = bl;
    }

    @Override
    protected final void zza(zzg object) {
        if (TextUtils.isEmpty((CharSequence)((zzapm)(object = ((zzg)object).zzb(zzapm.class))).zzvz())) {
            ((zzapm)object).setClientId(this.zzdoh.zzxl().zzyk());
        }
        if (this.zzdoi && TextUtils.isEmpty((CharSequence)((zzapm)object).zzwa())) {
            zzapq zzapq2 = this.zzdoh.zzxk();
            ((zzapm)object).zzdq(zzapq2.zzwi());
            ((zzapm)object).zzai(zzapq2.zzwb());
        }
    }

    public final void zzde(String string2) {
        zzbq.zzgm(string2);
        Uri uri = zzb.zzdf(string2);
        ListIterator<zzm> listIterator = this.zzdpt.getTransports().listIterator();
        while (listIterator.hasNext()) {
            if (!uri.equals((Object)listIterator.next().zzup())) continue;
            listIterator.remove();
        }
        this.zzdpt.getTransports().add(new zzb(this.zzdoh, string2));
    }

    final zzaqc zzum() {
        return this.zzdoh;
    }

    @Override
    public final zzg zzun() {
        zzg zzg2 = this.zzdpt.zzus();
        zzg2.zza(this.zzdoh.zzxd().zzxy());
        zzg2.zza(this.zzdoh.zzxe().zzzc());
        this.zzd(zzg2);
        return zzg2;
    }
}

