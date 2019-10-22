/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzcx;
import java.util.Map;

final class zzg
implements DataLayer.zzb {
    private final Context zzair;

    public zzg(Context context) {
        this.zzair = context;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void zzw(Map<String, Object> object) {
        Object v = object.get("gtm.url");
        if ((object = v == null && (object = object.get("gtm")) != null && object instanceof Map ? ((Map)object).get("url") : v) == null || !(object instanceof String) || (object = Uri.parse((String)((String)object)).getQueryParameter("referrer")) == null) {
            return;
        }
        zzcx.zzak(this.zzair, (String)object);
    }
}

