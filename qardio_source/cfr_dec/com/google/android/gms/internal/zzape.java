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

public final class zzape
extends zzh<zzape> {
    private String mName;
    private String zzbsw;
    private String zzbuz;
    private String zzdrc;
    private String zzdrd;
    private String zzdre;
    private String zzdrf;
    private String zzdrg;
    private String zzdrh;
    private String zzdri;

    public final String getContent() {
        return this.zzbsw;
    }

    public final String getId() {
        return this.zzbuz;
    }

    public final String getName() {
        return this.mName;
    }

    public final String getSource() {
        return this.zzdrc;
    }

    public final void setName(String string2) {
        this.mName = string2;
    }

    public final String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("name", this.mName);
        hashMap.put("source", this.zzdrc);
        hashMap.put("medium", this.zzdrd);
        hashMap.put("keyword", this.zzdre);
        hashMap.put("content", this.zzbsw);
        hashMap.put("id", this.zzbuz);
        hashMap.put("adNetworkId", this.zzdrf);
        hashMap.put("gclid", this.zzdrg);
        hashMap.put("dclid", this.zzdrh);
        hashMap.put("aclid", this.zzdri);
        return zzape.zzl(hashMap);
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        zzh2 = (zzape)zzh2;
        if (!TextUtils.isEmpty((CharSequence)this.mName)) {
            ((zzape)zzh2).mName = this.mName;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrc)) {
            ((zzape)zzh2).zzdrc = this.zzdrc;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrd)) {
            ((zzape)zzh2).zzdrd = this.zzdrd;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdre)) {
            ((zzape)zzh2).zzdre = this.zzdre;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzbsw)) {
            ((zzape)zzh2).zzbsw = this.zzbsw;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzbuz)) {
            ((zzape)zzh2).zzbuz = this.zzbuz;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrf)) {
            ((zzape)zzh2).zzdrf = this.zzdrf;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrg)) {
            ((zzape)zzh2).zzdrg = this.zzdrg;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrh)) {
            ((zzape)zzh2).zzdrh = this.zzdrh;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdri)) {
            ((zzape)zzh2).zzdri = this.zzdri;
        }
    }

    public final void zzdg(String string2) {
        this.zzdrc = string2;
    }

    public final void zzdh(String string2) {
        this.zzdrd = string2;
    }

    public final void zzdi(String string2) {
        this.zzdre = string2;
    }

    public final void zzdj(String string2) {
        this.zzbsw = string2;
    }

    public final void zzdk(String string2) {
        this.zzbuz = string2;
    }

    public final void zzdl(String string2) {
        this.zzdrf = string2;
    }

    public final void zzdm(String string2) {
        this.zzdrg = string2;
    }

    public final void zzdn(String string2) {
        this.zzdrh = string2;
    }

    public final void zzdo(String string2) {
        this.zzdri = string2;
    }

    public final String zzvl() {
        return this.zzdrd;
    }

    public final String zzvm() {
        return this.zzdre;
    }

    public final String zzvn() {
        return this.zzdrf;
    }

    public final String zzvo() {
        return this.zzdrg;
    }

    public final String zzvp() {
        return this.zzdrh;
    }

    public final String zzvq() {
        return this.zzdri;
    }
}

