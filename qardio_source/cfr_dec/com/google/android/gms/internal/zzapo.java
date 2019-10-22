/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzh;
import java.util.HashMap;

public final class zzapo
extends zzh<zzapo> {
    public String zzdrp;
    public String zzdsh;
    public String zzdsi;

    public final String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("network", this.zzdsh);
        hashMap.put("action", this.zzdrp);
        hashMap.put("target", this.zzdsi);
        return zzapo.zzl(hashMap);
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        zzh2 = (zzapo)zzh2;
        if (!TextUtils.isEmpty((CharSequence)this.zzdsh)) {
            ((zzapo)zzh2).zzdsh = this.zzdsh;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrp)) {
            ((zzapo)zzh2).zzdrp = this.zzdrp;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdsi)) {
            ((zzapo)zzh2).zzdsi = this.zzdsi;
        }
    }
}

