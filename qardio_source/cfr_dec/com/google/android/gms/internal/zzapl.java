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

public final class zzapl
extends zzh<zzapl> {
    public String zzdrs;
    public boolean zzdrt;

    public final String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("description", this.zzdrs);
        hashMap.put("fatal", this.zzdrt);
        return zzapl.zzl(hashMap);
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        zzh2 = (zzapl)zzh2;
        if (!TextUtils.isEmpty((CharSequence)this.zzdrs)) {
            ((zzapl)zzh2).zzdrs = this.zzdrs;
        }
        if (this.zzdrt) {
            ((zzapl)zzh2).zzdrt = this.zzdrt;
        }
    }
}

