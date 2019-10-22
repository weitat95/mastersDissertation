/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.text.TextUtils
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzv;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.zza;
import com.google.firebase.iid.zzz;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Set;

final class zzy {
    private Context zzair;
    private SharedPreferences zzige;

    public zzy(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private zzy(Context object, String string2) {
        this.zzair = object;
        this.zzige = object.getSharedPreferences(string2, 0);
        object = String.valueOf(string2);
        string2 = String.valueOf("-no-backup");
        object = string2.length() != 0 ? ((String)object).concat(string2) : new String((String)object);
        object = new File(zzv.getNoBackupFilesDir(this.zzair), (String)object);
        if (((File)object).exists()) return;
        try {
            if (!((File)object).createNewFile() || this.isEmpty()) return;
            {
                Log.i((String)"FirebaseInstanceId", (String)"App restored, clearing state");
                this.zzavj();
                FirebaseInstanceId.getInstance().zzciy();
                return;
            }
        }
        catch (IOException iOException) {
            String string3;
            if (!Log.isLoggable((String)"FirebaseInstanceId", (int)3)) return;
            {
                string3 = String.valueOf(iOException.getMessage());
                string3 = string3.length() != 0 ? "Error creating file in no backup dir: ".concat(string3) : new String("Error creating file in no backup dir: ");
            }
            Log.d((String)"FirebaseInstanceId", (String)string3);
            return;
        }
    }

    private final boolean isEmpty() {
        synchronized (this) {
            boolean bl = this.zzige.getAll().isEmpty();
            return bl;
        }
    }

    private static String zzbm(String string2, String string3) {
        return new StringBuilder(String.valueOf(string2).length() + String.valueOf("|S|").length() + String.valueOf(string3).length()).append(string2).append("|S|").append(string3).toString();
    }

    private final void zzhz(String string2) {
        SharedPreferences.Editor editor = this.zzige.edit();
        for (String string3 : this.zzige.getAll().keySet()) {
            if (!string3.startsWith(string2)) continue;
            editor.remove(string3);
        }
        editor.commit();
    }

    private static String zzo(String string2, String string3, String string4) {
        return new StringBuilder(String.valueOf(string2).length() + 1 + String.valueOf("|T|").length() + String.valueOf(string3).length() + String.valueOf(string4).length()).append(string2).append("|T|").append(string3).append("|").append(string4).toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void zza(String string2, String string3, String string4, String string5, String string6) {
        synchronized (this) {
            block6: {
                string5 = zzz.zzc(string5, string6, System.currentTimeMillis());
                if (string5 != null) break block6;
                do {
                    return;
                    break;
                } while (true);
            }
            string6 = this.zzige.edit();
            string6.putString(zzy.zzo(string2, string3, string4), string5);
            string6.commit();
            return;
        }
    }

    public final void zzavj() {
        synchronized (this) {
            this.zzige.edit().clear().commit();
            return;
        }
    }

    public final String zzcjm() {
        String string2 = null;
        synchronized (this) {
            String string3;
            block6: {
                String[] arrstring = this.zzige.getString("topic_operaion_queue", null);
                string3 = string2;
                if (arrstring == null) break block6;
                arrstring = arrstring.split(",");
                string3 = string2;
                if (arrstring.length <= 1) break block6;
                string3 = string2;
                if (TextUtils.isEmpty((CharSequence)arrstring[1])) break block6;
                string3 = arrstring[1];
            }
            return string3;
        }
    }

    public final void zzia(String string2) {
        synchronized (this) {
            this.zzhz(String.valueOf(string2).concat("|T|"));
            return;
        }
    }

    public final zzz zzp(String object, String string2, String string3) {
        synchronized (this) {
            object = zzz.zzrn(this.zzige.getString(zzy.zzo((String)object, string2, string3), null));
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean zzri(String string2) {
        synchronized (this) {
            String string3 = this.zzige.getString("topic_operaion_queue", "");
            String string4 = String.valueOf(",");
            String string5 = String.valueOf(string2);
            string4 = string5.length() != 0 ? string4.concat(string5) : new String(string4);
            if (!string3.startsWith(string4)) return false;
            string4 = String.valueOf(",");
            string2 = (string2 = String.valueOf(string2)).length() != 0 ? string4.concat(string2) : new String(string4);
            string2 = string3.substring(string2.length());
            this.zzige.edit().putString("topic_operaion_queue", string2).apply();
            return true;
        }
    }

    final KeyPair zzrk(String string2) {
        synchronized (this) {
            KeyPair keyPair = zza.zzavc();
            long l = System.currentTimeMillis();
            SharedPreferences.Editor editor = this.zzige.edit();
            editor.putString(zzy.zzbm(string2, "|P|"), Base64.encodeToString((byte[])keyPair.getPublic().getEncoded(), (int)11));
            editor.putString(zzy.zzbm(string2, "|K|"), Base64.encodeToString((byte[])keyPair.getPrivate().getEncoded(), (int)11));
            editor.putString(zzy.zzbm(string2, "cre"), Long.toString(l));
            editor.commit();
            return keyPair;
        }
    }

    final void zzrl(String string2) {
        synchronized (this) {
            this.zzhz(String.valueOf(string2).concat("|"));
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final KeyPair zzrm(String var1_1) {
        // MONITORENTER : this
        var2_11 = this.zzige.getString(zzy.zzbm((String)var1_1 /* !! */ , "|P|"), null);
        var3_12 = this.zzige.getString(zzy.zzbm((String)var1_1 /* !! */ , "|K|"), null);
        if (var2_11 == null || var3_12 == null) {
            var1_2 = null;
            // MONITOREXIT : this
            return var1_3;
        }
        try {
            var1_4 = Base64.decode((String)var2_11, (int)8);
            var2_11 = Base64.decode((String)var3_12, (int)8);
            var3_12 = KeyFactory.getInstance("RSA");
            var1_5 = new KeyPair(var3_12.generatePublic(new X509EncodedKeySpec(var1_4)), var3_12.generatePrivate(new PKCS8EncodedKeySpec(var2_11)));
            return var1_3;
        }
        catch (InvalidKeySpecException var1_6) {}
        ** GOTO lbl-1000
        catch (NoSuchAlgorithmException var1_10) {}
lbl-1000:
        // 2 sources
        {
            var1_8 = String.valueOf(var1_7);
            Log.w((String)"FirebaseInstanceId", (String)new StringBuilder(String.valueOf(var1_8).length() + 19).append("Invalid key stored ").append(var1_8).toString());
            FirebaseInstanceId.getInstance().zzciy();
            var1_9 = null;
            return var1_3;
        }
    }
}

