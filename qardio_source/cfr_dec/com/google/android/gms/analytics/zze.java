/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.text.TextUtils
 *  android.util.LogPrinter
 */
package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import android.util.LogPrinter;
import com.google.android.gms.analytics.zzf;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.analytics.zzm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class zze
implements zzm {
    private static final Uri zzdpg;
    private final LogPrinter zzdph = new LogPrinter(4, "GA/LogCatTransport");

    static {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("local");
        zzdpg = builder.build();
    }

    @Override
    public final void zzb(zzg object) {
        ArrayList<zzh> arrayList = new ArrayList<zzh>(((zzg)object).zzut());
        Collections.sort(arrayList, new zzf(this));
        object = new StringBuilder();
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            Object object2 = arrayList.get(n2);
            int n3 = n2 + 1;
            object2 = object2.toString();
            n2 = n3;
            if (TextUtils.isEmpty((CharSequence)object2)) continue;
            if (((StringBuilder)object).length() != 0) {
                ((StringBuilder)object).append(", ");
            }
            ((StringBuilder)object).append((String)object2);
            n2 = n3;
        }
        this.zzdph.println(((StringBuilder)object).toString());
    }

    @Override
    public final Uri zzup() {
        return zzdpg;
    }
}

