/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbuv;
import com.google.android.gms.internal.zzbuw;
import com.google.android.gms.internal.zzbuz;
import com.google.android.gms.internal.zzbvb;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class zzbuy {
    /*
     * Enabled aggressive block sorting
     */
    public static <DP, DT> String zza(DP DP, zzbuv<DP, DT> zzbuv2) {
        zzbuw<DT> zzbuw2 = zzbuv2.zzaqe();
        if (!zzbuw2.zzhe(zzbuv2.zzab(DP))) {
            return null;
        }
        DT DT = zzbuv2.zzaa(DP);
        int n = 0;
        do {
            block14: {
                Object object;
                Object object2;
                double d;
                block16: {
                    block15: {
                        block13: {
                            if (n >= zzbuw2.zzac(DT)) {
                                return null;
                            }
                            object2 = zzbuw2.zzg(DT, n);
                            if (zzbuv2.zzd(DP, n)) break block13;
                            if (!zzbuw2.zzf(DT, n) && !zzbuz.zzheg.contains(object2)) {
                                return String.valueOf(object2).concat(" not set");
                            }
                            break block14;
                        }
                        d = zzbuw2.zze(DT, n);
                        if (d != 1.0) break block15;
                        d = zzbuv2.zzc(DP, n);
                        break block16;
                    }
                    if (d != 2.0) break block14;
                    d = zzbuv2.zzb(DP, n);
                }
                if ((object = zzbuz.zzaqs().zzhn((String)object2)) != null && !((zzbvb)object).zzf(d)) {
                    return "Field out of range";
                }
                object = zzbuw2.zzad(DT);
                object2 = zzbuz.zzaqs().zzz((String)object, (String)object2);
                if (object2 != null) {
                    long l = zzbuv2.zza(DP, TimeUnit.NANOSECONDS);
                    if (l == 0L) {
                        if (d == 0.0) {
                            return null;
                        }
                        return "DataPoint out of range";
                    }
                    if (!((zzbvb)object2).zzf(d / (double)l)) {
                        return "DataPoint out of range";
                    }
                }
            }
            ++n;
        } while (true);
    }
}

