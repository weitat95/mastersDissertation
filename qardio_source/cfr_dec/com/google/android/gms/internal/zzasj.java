/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzapz;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzara;
import com.google.android.gms.internal.zzarc;
import com.google.android.gms.internal.zzask;
import java.util.Map;

final class zzasj
extends zzapz
implements zzarc<zzask> {
    private final zzask zzdzc = new zzask();

    public zzasj(zzaqc zzaqc2) {
        super(zzaqc2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void zzc(String object, boolean bl) {
        int n = 1;
        int n2 = 1;
        int n3 = 1;
        if ("ga_autoActivityTracking".equals(object)) {
            object = this.zzdzc;
            if (!bl) {
                n3 = 0;
            }
            ((zzask)object).zzdzf = n3;
            return;
        }
        if ("ga_anonymizeIp".equals(object)) {
            object = this.zzdzc;
            n3 = bl ? n : 0;
            ((zzask)object).zzdzg = n3;
            return;
        }
        if (!"ga_reportUncaughtExceptions".equals(object)) {
            this.zzd("bool configuration name not recognized", object);
            return;
        }
        object = this.zzdzc;
        n3 = bl ? n2 : 0;
        ((zzask)object).zzdzh = n3;
    }

    @Override
    public final void zzd(String string2, int n) {
        if ("ga_sessionTimeout".equals(string2)) {
            this.zzdzc.zzdze = n;
            return;
        }
        this.zzd("int configuration name not recognized", string2);
    }

    @Override
    public final void zzi(String string2, String string3) {
        this.zzdzc.zzdzi.put(string2, string3);
    }

    @Override
    public final void zzj(String string2, String string3) {
        if ("ga_trackingId".equals(string2)) {
            this.zzdzc.zzdom = string3;
            return;
        }
        if ("ga_sampleFrequency".equals(string2)) {
            try {
                this.zzdzc.zzdzd = Double.parseDouble(string3);
                return;
            }
            catch (NumberFormatException numberFormatException) {
                this.zzc("Error parsing ga_sampleFrequency value", string3, numberFormatException);
                return;
            }
        }
        this.zzd("string configuration name not recognized", string2);
    }

    @Override
    public final /* synthetic */ zzara zzyo() {
        return this.zzdzc;
    }
}

