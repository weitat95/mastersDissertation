/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.google.android.gms.tagmanager.zzdj;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class zzei {
    private static zzei zzkhm;
    private volatile String zzkdd = null;
    private volatile zza zzkhn = zza.zzkhq;
    private volatile String zzkho = null;
    private volatile String zzkhp = null;

    zzei() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static zzei zzbfo() {
        synchronized (zzei.class) {
            if (zzkhm != null) return zzkhm;
            zzkhm = new zzei();
            return zzkhm;
        }
    }

    private static String zzlu(String string2) {
        return string2.split("&")[0].split("=")[1];
    }

    final String getContainerId() {
        return this.zzkdd;
    }

    final zza zzbfp() {
        return this.zzkhn;
    }

    final String zzbfq() {
        return this.zzkho;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final boolean zzq(Uri object) {
        boolean bl = true;
        synchronized (this) {
            block9: {
                String string2;
                block8: {
                    String string3;
                    try {
                        string2 = URLDecoder.decode(object.toString(), "UTF-8");
                        if (!string2.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) break block8;
                        string3 = String.valueOf(string2);
                        string3 = string3.length() != 0 ? "Container preview url: ".concat(string3) : new String("Container preview url: ");
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        return false;
                    }
                    zzdj.v(string3);
                    this.zzkhn = string2.matches(".*?&gtm_debug=x$") ? zza.zzkhs : zza.zzkhr;
                    this.zzkhp = object.getQuery().replace("&gtm_debug=x", "");
                    if (this.zzkhn == zza.zzkhr || this.zzkhn == zza.zzkhs) {
                        object = String.valueOf("/r?");
                        string3 = String.valueOf(this.zzkhp);
                        object = string3.length() != 0 ? ((String)object).concat(string3) : new String((String)object);
                        this.zzkho = object;
                    }
                    this.zzkdd = zzei.zzlu(this.zzkhp);
                    break block9;
                }
                if (string2.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                    if (!zzei.zzlu(object.getQuery()).equals(this.zzkdd)) return false;
                    object = String.valueOf(this.zzkdd);
                    object = ((String)object).length() != 0 ? "Exit preview mode for container: ".concat((String)object) : new String("Exit preview mode for container: ");
                    zzdj.v((String)object);
                    this.zzkhn = zza.zzkhq;
                    this.zzkho = null;
                } else {
                    object = String.valueOf(string2);
                    object = ((String)object).length() != 0 ? "Invalid preview uri: ".concat((String)object) : new String("Invalid preview uri: ");
                    zzdj.zzcu((String)object);
                    return false;
                }
            }
            return bl;
        }
    }

    static final class zza
    extends Enum<zza> {
        public static final /* enum */ zza zzkhq = new zza();
        public static final /* enum */ zza zzkhr = new zza();
        public static final /* enum */ zza zzkhs = new zza();
        private static final /* synthetic */ zza[] zzkht;

        static {
            zzkht = new zza[]{zzkhq, zzkhr, zzkhs};
        }

        public static zza[] values() {
            return (zza[])zzkht.clone();
        }
    }

}

