/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzcgx;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzclq;
import java.util.Iterator;
import java.util.Set;

public final class zzcgv {
    final String mAppId;
    final String mName;
    private String mOrigin;
    final long zzfij;
    final long zzizi;
    final zzcgx zzizj;

    zzcgv(zzcim zzcim2, String string2, String string3, String string4, long l, long l2, Bundle bundle) {
        zzbq.zzgm(string3);
        zzbq.zzgm(string4);
        this.mAppId = string3;
        this.mName = string4;
        string4 = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string4 = null;
        }
        this.mOrigin = string4;
        this.zzfij = l;
        this.zzizi = l2;
        if (this.zzizi != 0L && this.zzizi > this.zzfij) {
            zzcim2.zzawy().zzazf().zzj("Event created with reverse previous/current timestamps. appId", zzchm.zzjk(string3));
        }
        this.zzizj = zzcgv.zza(zzcim2, bundle);
    }

    private zzcgv(zzcim zzcim2, String string2, String string3, String string4, long l, long l2, zzcgx zzcgx2) {
        zzbq.zzgm(string3);
        zzbq.zzgm(string4);
        zzbq.checkNotNull(zzcgx2);
        this.mAppId = string3;
        this.mName = string4;
        string4 = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string4 = null;
        }
        this.mOrigin = string4;
        this.zzfij = l;
        this.zzizi = l2;
        if (this.zzizi != 0L && this.zzizi > this.zzfij) {
            zzcim2.zzawy().zzazf().zzj("Event created with reverse previous/current timestamps. appId", zzchm.zzjk(string3));
        }
        this.zzizj = zzcgx2;
    }

    private static zzcgx zza(zzcim zzcim2, Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            bundle = new Bundle(bundle);
            Iterator iterator = bundle.keySet().iterator();
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                if (string2 == null) {
                    zzcim2.zzawy().zzazd().log("Param name can't be null");
                    iterator.remove();
                    continue;
                }
                Object object = zzcim2.zzawu().zzk(string2, bundle.get(string2));
                if (object == null) {
                    zzcim2.zzawy().zzazf().zzj("Param value can't be null", zzcim2.zzawt().zzji(string2));
                    iterator.remove();
                    continue;
                }
                zzcim2.zzawu().zza(bundle, string2, object);
            }
            return new zzcgx(bundle);
        }
        return new zzcgx(new Bundle());
    }

    public final String toString() {
        String string2 = this.mAppId;
        String string3 = this.mName;
        String string4 = String.valueOf(this.zzizj);
        return new StringBuilder(String.valueOf(string2).length() + 33 + String.valueOf(string3).length() + String.valueOf(string4).length()).append("Event{appId='").append(string2).append("', name='").append(string3).append("', params=").append(string4).append("}").toString();
    }

    final zzcgv zza(zzcim zzcim2, long l) {
        return new zzcgv(zzcim2, this.mOrigin, this.mAppId, this.mName, this.zzfij, l, this.zzizj);
    }
}

