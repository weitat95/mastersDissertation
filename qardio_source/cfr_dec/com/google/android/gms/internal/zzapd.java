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

public final class zzapd
extends zzh<zzapd> {
    private String mAppId;
    private String zzdqz;
    private String zzdra;
    private String zzdrb;

    public final String getAppId() {
        return this.mAppId;
    }

    public final void setAppId(String string2) {
        this.mAppId = string2;
    }

    public final void setAppInstallerId(String string2) {
        this.zzdrb = string2;
    }

    public final void setAppName(String string2) {
        this.zzdqz = string2;
    }

    public final void setAppVersion(String string2) {
        this.zzdra = string2;
    }

    public final String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("appName", this.zzdqz);
        hashMap.put("appVersion", this.zzdra);
        hashMap.put("appId", this.mAppId);
        hashMap.put("appInstallerId", this.zzdrb);
        return zzapd.zzl(hashMap);
    }

    public final void zza(zzapd zzapd2) {
        if (!TextUtils.isEmpty((CharSequence)this.zzdqz)) {
            zzapd2.zzdqz = this.zzdqz;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdra)) {
            zzapd2.zzdra = this.zzdra;
        }
        if (!TextUtils.isEmpty((CharSequence)this.mAppId)) {
            zzapd2.mAppId = this.mAppId;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdrb)) {
            zzapd2.zzdrb = this.zzdrb;
        }
    }

    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        this.zza((zzapd)zzh2);
    }

    public final String zzvi() {
        return this.zzdqz;
    }

    public final String zzvj() {
        return this.zzdra;
    }

    public final String zzvk() {
        return this.zzdrb;
    }
}

