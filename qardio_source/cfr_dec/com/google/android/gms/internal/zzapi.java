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

public final class zzapi
extends zzh<zzapi> {
    public int zzchl;
    public int zzchm;
    private String zzdrl;
    public int zzdrm;
    public int zzdrn;
    public int zzdro;

    public final String getLanguage() {
        return this.zzdrl;
    }

    public final void setLanguage(String string2) {
        this.zzdrl = string2;
    }

    public final String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("language", this.zzdrl);
        hashMap.put("screenColors", this.zzdrm);
        hashMap.put("screenWidth", this.zzchl);
        hashMap.put("screenHeight", this.zzchm);
        hashMap.put("viewportWidth", this.zzdrn);
        hashMap.put("viewportHeight", this.zzdro);
        return zzapi.zzl(hashMap);
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        zzh2 = (zzapi)zzh2;
        if (this.zzdrm != 0) {
            ((zzapi)zzh2).zzdrm = this.zzdrm;
        }
        if (this.zzchl != 0) {
            ((zzapi)zzh2).zzchl = this.zzchl;
        }
        if (this.zzchm != 0) {
            ((zzapi)zzh2).zzchm = this.zzchm;
        }
        if (this.zzdrn != 0) {
            ((zzapi)zzh2).zzdrn = this.zzdrn;
        }
        if (this.zzdro != 0) {
            ((zzapi)zzh2).zzdro = this.zzdro;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrl)) {
            ((zzapi)zzh2).zzdrl = this.zzdrl;
        }
    }
}

