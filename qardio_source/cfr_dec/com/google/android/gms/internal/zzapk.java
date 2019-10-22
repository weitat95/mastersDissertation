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

public final class zzapk
extends zzh<zzapk> {
    private String mCategory;
    private String zzdrp;
    private String zzdrq;
    private long zzdrr;

    public final String getAction() {
        return this.zzdrp;
    }

    public final String getCategory() {
        return this.mCategory;
    }

    public final String getLabel() {
        return this.zzdrq;
    }

    public final long getValue() {
        return this.zzdrr;
    }

    public final String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("category", this.mCategory);
        hashMap.put("action", this.zzdrp);
        hashMap.put("label", this.zzdrq);
        hashMap.put("value", this.zzdrr);
        return zzapk.zzl(hashMap);
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        zzh2 = (zzapk)zzh2;
        if (!TextUtils.isEmpty((CharSequence)this.mCategory)) {
            ((zzapk)zzh2).mCategory = this.mCategory;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrp)) {
            ((zzapk)zzh2).zzdrp = this.zzdrp;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrq)) {
            ((zzapk)zzh2).zzdrq = this.zzdrq;
        }
        if (this.zzdrr != 0L) {
            ((zzapk)zzh2).zzdrr = this.zzdrr;
        }
    }
}

