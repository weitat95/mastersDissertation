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

public final class zzapp
extends zzh<zzapp> {
    public String mCategory;
    public String zzdrq;
    public String zzdsj;
    public long zzdsk;

    public final String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("variableName", this.zzdsj);
        hashMap.put("timeInMillis", this.zzdsk);
        hashMap.put("category", this.mCategory);
        hashMap.put("label", this.zzdrq);
        return zzapp.zzl(hashMap);
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        zzh2 = (zzapp)zzh2;
        if (!TextUtils.isEmpty((CharSequence)this.zzdsj)) {
            ((zzapp)zzh2).zzdsj = this.zzdsj;
        }
        if (this.zzdsk != 0L) {
            ((zzapp)zzh2).zzdsk = this.zzdsk;
        }
        if (!TextUtils.isEmpty((CharSequence)this.mCategory)) {
            ((zzapp)zzh2).mCategory = this.mCategory;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrq)) {
            ((zzapp)zzh2).zzdrq = this.zzdrq;
        }
    }
}

