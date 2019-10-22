/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzbq;
import java.util.HashMap;

public final class zzapm
extends zzh<zzapm> {
    private String zzauv;
    private String zzdru;
    private String zzdrv;
    private String zzdrw;
    private boolean zzdrx;
    private String zzdry;
    private boolean zzdrz;
    private double zzdsa;

    public final String getUserId() {
        return this.zzauv;
    }

    public final void setClientId(String string2) {
        this.zzdrv = string2;
    }

    public final void setUserId(String string2) {
        this.zzauv = string2;
    }

    public final String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("hitType", this.zzdru);
        hashMap.put("clientId", this.zzdrv);
        hashMap.put("userId", this.zzauv);
        hashMap.put("androidAdId", this.zzdrw);
        hashMap.put("AdTargetingEnabled", this.zzdrx);
        hashMap.put("sessionControl", this.zzdry);
        hashMap.put("nonInteraction", this.zzdrz);
        hashMap.put("sampleRate", this.zzdsa);
        return zzapm.zzl(hashMap);
    }

    public final void zzai(boolean bl) {
        this.zzdrx = bl;
    }

    public final void zzaj(boolean bl) {
        this.zzdrz = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        boolean bl = true;
        zzh2 = (zzapm)zzh2;
        if (!TextUtils.isEmpty((CharSequence)this.zzdru)) {
            ((zzapm)zzh2).zzdru = this.zzdru;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrv)) {
            ((zzapm)zzh2).zzdrv = this.zzdrv;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzauv)) {
            ((zzapm)zzh2).zzauv = this.zzauv;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrw)) {
            ((zzapm)zzh2).zzdrw = this.zzdrw;
        }
        if (this.zzdrx) {
            ((zzapm)zzh2).zzdrx = true;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdry)) {
            ((zzapm)zzh2).zzdry = this.zzdry;
        }
        if (this.zzdrz) {
            ((zzapm)zzh2).zzdrz = this.zzdrz;
        }
        if (this.zzdsa != 0.0) {
            double d = this.zzdsa;
            if (!(d >= 0.0) || !(d <= 100.0)) {
                bl = false;
            }
            zzbq.checkArgument(bl, "Sample rate must be between 0% and 100%");
            ((zzapm)zzh2).zzdsa = d;
        }
    }

    public final void zzdp(String string2) {
        this.zzdru = string2;
    }

    public final void zzdq(String string2) {
        this.zzdrw = string2;
    }

    public final String zzvy() {
        return this.zzdru;
    }

    public final String zzvz() {
        return this.zzdrv;
    }

    public final String zzwa() {
        return this.zzdrw;
    }

    public final boolean zzwb() {
        return this.zzdrx;
    }

    public final String zzwc() {
        return this.zzdry;
    }

    public final boolean zzwd() {
        return this.zzdrz;
    }

    public final double zzwe() {
        return this.zzdsa;
    }
}

