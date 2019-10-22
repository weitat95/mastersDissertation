/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.zzh;
import java.util.HashMap;
import java.util.UUID;

public final class zzapn
extends zzh<zzapn> {
    private String zzaqi;
    private int zzdsb;
    private int zzdsc;
    private String zzdsd;
    private String zzdse;
    private boolean zzdsf;
    private boolean zzdsg;

    public zzapn() {
        this(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private zzapn(boolean bl) {
        UUID uUID = UUID.randomUUID();
        int n = (int)(uUID.getLeastSignificantBits() & Integer.MAX_VALUE);
        if (n == 0) {
            int n2;
            n = n2 = (int)(uUID.getMostSignificantBits() & Integer.MAX_VALUE);
            if (n2 == 0) {
                Log.e((String)"GAv4", (String)"UUID.randomUUID() returned 0.");
                n = Integer.MAX_VALUE;
            }
        }
        this(false, n);
    }

    private zzapn(boolean bl, int n) {
        if (n == 0) {
            throw new IllegalArgumentException("Given Integer is zero");
        }
        this.zzdsb = n;
        this.zzdsg = false;
    }

    public final String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("screenName", this.zzaqi);
        hashMap.put("interstitial", this.zzdsf);
        hashMap.put("automatic", this.zzdsg);
        hashMap.put("screenId", this.zzdsb);
        hashMap.put("referrerScreenId", this.zzdsc);
        hashMap.put("referrerScreenName", this.zzdsd);
        hashMap.put("referrerUri", this.zzdse);
        return zzapn.zzl(hashMap);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ void zzb(zzh zzh2) {
        zzh2 = (zzapn)zzh2;
        if (!TextUtils.isEmpty((CharSequence)this.zzaqi)) {
            ((zzapn)zzh2).zzaqi = this.zzaqi;
        }
        if (this.zzdsb != 0) {
            ((zzapn)zzh2).zzdsb = this.zzdsb;
        }
        if (this.zzdsc != 0) {
            ((zzapn)zzh2).zzdsc = this.zzdsc;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdsd)) {
            ((zzapn)zzh2).zzdsd = this.zzdsd;
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzdse)) {
            String string2 = this.zzdse;
            ((zzapn)zzh2).zzdse = TextUtils.isEmpty((CharSequence)string2) ? null : string2;
        }
        if (this.zzdsf) {
            ((zzapn)zzh2).zzdsf = this.zzdsf;
        }
        if (this.zzdsg) {
            ((zzapn)zzh2).zzdsg = this.zzdsg;
        }
    }

    public final String zzwf() {
        return this.zzaqi;
    }

    public final int zzwg() {
        return this.zzdsb;
    }

    public final String zzwh() {
        return this.zzdse;
    }
}

