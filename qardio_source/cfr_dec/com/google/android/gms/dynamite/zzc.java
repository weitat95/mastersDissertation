/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.zzi;
import com.google.android.gms.dynamite.zzj;

final class zzc
implements DynamiteModule.zzd {
    zzc() {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final zzj zza(Context context, String string2, zzi zzi2) throws DynamiteModule.zzc {
        zzj zzj2 = new zzj();
        zzj2.zzgxg = zzi2.zzab(context, string2);
        if (zzj2.zzgxg != 0) {
            zzj2.zzgxi = -1;
            return zzj2;
        } else {
            zzj2.zzgxh = zzi2.zzc(context, string2, true);
            if (zzj2.zzgxh == 0) return zzj2;
            {
                zzj2.zzgxi = 1;
                return zzj2;
            }
        }
    }
}

