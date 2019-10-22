/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.zzbq;

public final class zzca {
    private final Resources zzgbt;
    private final String zzgbu;

    public zzca(Context context) {
        zzbq.checkNotNull(context);
        this.zzgbt = context.getResources();
        this.zzgbu = this.zzgbt.getResourcePackageName(R.string.common_google_play_services_unknown_issue);
    }

    public final String getString(String string2) {
        int n = this.zzgbt.getIdentifier(string2, "string", this.zzgbu);
        if (n == 0) {
            return null;
        }
        return this.zzgbt.getString(n);
    }
}

