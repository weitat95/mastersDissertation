/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbs;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzea;
import com.google.android.gms.tagmanager.zzgk;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

final class zzgo {
    private static zzea<zzbs> zza(zzea<zzbs> zzea2) {
        try {
            zzea<zzbs> zzea3 = new zzea<zzbs>(zzgk.zzam(zzgo.zzmg(zzgk.zzb(zzea2.getObject()))), zzea2.zzbfk());
            return zzea3;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            zzdj.zzb("Escape URI: unsupported encoding", unsupportedEncodingException);
            return zzea2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static zzea<zzbs> zza(zzea<zzbs> zzea2, int ... arrn) {
        int n = arrn.length;
        int n2 = 0;
        while (n2 < n) {
            int n3 = arrn[n2];
            if (!(zzgk.zzg(zzea2.getObject()) instanceof String)) {
                zzdj.e("Escaping can only be applied to strings.");
            } else {
                switch (n3) {
                    default: {
                        zzdj.e(new StringBuilder(39).append("Unsupported Value Escaping: ").append(n3).toString());
                        break;
                    }
                    case 12: {
                        zzea2 = zzgo.zza(zzea2);
                    }
                }
            }
            ++n2;
        }
        return zzea2;
    }

    static String zzmg(String string2) throws UnsupportedEncodingException {
        return URLEncoder.encode(string2, "UTF-8").replaceAll("\\+", "%20");
    }
}

