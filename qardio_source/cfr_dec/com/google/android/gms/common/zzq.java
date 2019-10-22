/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.zzg;
import com.google.android.gms.common.zzh;
import com.google.android.gms.common.zzi;
import com.google.android.gms.common.zzk;
import com.google.android.gms.common.zzp;
import com.google.android.gms.internal.zzbhf;

public class zzq {
    private static zzq zzflp;
    private final Context mContext;

    private zzq(Context context) {
        this.mContext = context.getApplicationContext();
    }

    static zzh zza(PackageInfo object, zzh ... arrzzh) {
        if (object.signatures == null) {
            return null;
        }
        if (object.signatures.length != 1) {
            Log.w((String)"GoogleSignatureVerifier", (String)"Package has more than one signature.");
            return null;
        }
        object = new zzi(object.signatures[0].toByteArray());
        for (int i = 0; i < arrzzh.length; ++i) {
            if (!arrzzh[i].equals(object)) continue;
            return arrzzh[i];
        }
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static boolean zza(PackageInfo object, boolean bl) {
        if (object != null && object.signatures != null) {
            void var0_2;
            void var1_4;
            if (var1_4 != false) {
                zzh zzh2 = zzq.zza(object, zzk.zzflf);
            } else {
                zzh zzh3 = zzq.zza(object, new zzh[]{zzk.zzflf[0]});
            }
            if (var0_2 != null) {
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private static boolean zzb(PackageInfo object, boolean bl) {
        boolean bl2 = false;
        if (object.signatures.length != 1) {
            Log.w((String)"GoogleSignatureVerifier", (String)"Package has more than one signature.");
            return bl2;
        } else {
            void var1_2;
            zzi zzi2 = new zzi(object.signatures[0].toByteArray());
            String string2 = object.packageName;
            boolean bl3 = var1_2 != false ? zzg.zzb(string2, zzi2) : zzg.zza(string2, zzi2);
            bl2 = bl3;
            if (bl3) return bl2;
            {
                Log.d((String)"GoogleSignatureVerifier", (String)new StringBuilder(27).append("Cert not in list. atk=").append((boolean)var1_2).toString());
                return bl3;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzq zzci(Context context) {
        zzbq.checkNotNull(context);
        synchronized (zzq.class) {
            if (zzflp == null) {
                zzg.zzcg(context);
                zzflp = new zzq(context);
            }
            return zzflp;
        }
    }

    private final boolean zzfy(String string2) {
        boolean bl;
        block6: {
            block5: {
                try {
                    string2 = zzbhf.zzdb(this.mContext).getPackageInfo(string2, 64);
                    if (string2 != null) break block5;
                    return false;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    return false;
                }
            }
            if (zzp.zzch(this.mContext)) {
                return zzq.zzb((PackageInfo)string2, true);
            }
            bl = zzq.zzb((PackageInfo)string2, false);
            if (bl) break block6;
            if (!zzq.zzb((PackageInfo)string2, true)) break block6;
            Log.w((String)"GoogleSignatureVerifier", (String)"Test-keys aren't accepted on this build.");
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean zza(PackageInfo packageInfo) {
        block6: {
            block5: {
                if (packageInfo == null) break block5;
                if (zzq.zza(packageInfo, false)) {
                    return true;
                }
                if (zzq.zza(packageInfo, true)) break block6;
            }
            return false;
        }
        if (zzp.zzch(this.mContext)) {
            return true;
        }
        Log.w((String)"GoogleSignatureVerifier", (String)"Test-keys aren't accepted on this build.");
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean zzbq(int n) {
        String[] arrstring = zzbhf.zzdb(this.mContext).getPackagesForUid(n);
        if (arrstring != null && arrstring.length != 0) {
            int n2 = arrstring.length;
            for (n = 0; n < n2; ++n) {
                if (!this.zzfy(arrstring[n])) continue;
                return true;
            }
        }
        return false;
    }
}

