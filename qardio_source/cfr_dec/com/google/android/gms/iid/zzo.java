/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.util.Base64
 *  android.util.Log
 */
package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzv;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;
import com.google.android.gms.iid.zza;
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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zzo {
    private Context zzair;
    private SharedPreferences zzige;

    public zzo(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private zzo(Context object, String string2) {
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
                Log.i((String)"InstanceID/Store", (String)"App restored, clearing state");
                InstanceIDListenerService.zza(this.zzair, this);
                return;
            }
        }
        catch (IOException iOException) {
            String string3;
            if (!Log.isLoggable((String)"InstanceID/Store", (int)3)) return;
            {
                string3 = String.valueOf(iOException.getMessage());
                string3 = string3.length() != 0 ? "Error creating file in no backup dir: ".concat(string3) : new String("Error creating file in no backup dir: ");
            }
            Log.d((String)"InstanceID/Store", (String)string3);
            return;
        }
    }

    private final void zza(SharedPreferences.Editor editor, String string2, String string3, String string4) {
        synchronized (this) {
            editor.putString(new StringBuilder(String.valueOf(string2).length() + String.valueOf("|S|").length() + String.valueOf(string3).length()).append(string2).append("|S|").append(string3).toString(), string4);
            return;
        }
    }

    private static String zzd(String string2, String string3, String string4) {
        return new StringBuilder(String.valueOf(string2).length() + 1 + String.valueOf("|T|").length() + String.valueOf(string3).length() + String.valueOf(string4).length()).append(string2).append("|T|").append(string3).append("|").append(string4).toString();
    }

    final String get(String string2) {
        synchronized (this) {
            string2 = this.zzige.getString(string2, null);
            return string2;
        }
    }

    final String get(String string2, String string3) {
        synchronized (this) {
            string2 = this.zzige.getString(new StringBuilder(String.valueOf(string2).length() + String.valueOf("|S|").length() + String.valueOf(string3).length()).append(string2).append("|S|").append(string3).toString(), null);
            return string2;
        }
    }

    public final boolean isEmpty() {
        return this.zzige.getAll().isEmpty();
    }

    public final void zza(String string2, String string3, String string4, String string5, String string6) {
        synchronized (this) {
            string2 = zzo.zzd(string2, string3, string4);
            string3 = this.zzige.edit();
            string3.putString(string2, string5);
            string3.putString("appVersion", string6);
            string3.putString("lastToken", Long.toString(System.currentTimeMillis() / 1000L));
            string3.commit();
            return;
        }
    }

    public final void zzavj() {
        synchronized (this) {
            this.zzige.edit().clear().commit();
            return;
        }
    }

    final KeyPair zzc(String string2, long l) {
        synchronized (this) {
            KeyPair keyPair = zza.zzavc();
            SharedPreferences.Editor editor = this.zzige.edit();
            this.zza(editor, string2, "|P|", InstanceID.zzo(keyPair.getPublic().getEncoded()));
            this.zza(editor, string2, "|K|", InstanceID.zzo(keyPair.getPrivate().getEncoded()));
            this.zza(editor, string2, "cre", Long.toString(l));
            editor.commit();
            return keyPair;
        }
    }

    public final String zze(String string2, String string3, String string4) {
        synchronized (this) {
            string2 = zzo.zzd(string2, string3, string4);
            string2 = this.zzige.getString(string2, null);
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzhz(String string2) {
        synchronized (this) {
            SharedPreferences.Editor editor = this.zzige.edit();
            Iterator iterator = this.zzige.getAll().keySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    editor.commit();
                    return;
                }
                String string3 = (String)iterator.next();
                if (!string3.startsWith(string2)) continue;
                editor.remove(string3);
            } while (true);
        }
    }

    public final void zzia(String string2) {
        this.zzhz(String.valueOf(string2).concat("|T|"));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    final KeyPair zzib(String var1_1) {
        var2_8 = this.get((String)var1_1 /* !! */ , "|P|");
        var3_9 = this.get((String)var1_1 /* !! */ , "|K|");
        if (var2_8 == null) return null;
        if (var3_9 == null) {
            return null;
        }
        try {
            var1_2 = Base64.decode((String)var2_8, (int)8);
            var2_8 = Base64.decode((String)var3_9, (int)8);
            var3_9 = KeyFactory.getInstance("RSA");
            return new KeyPair(var3_9.generatePublic(new X509EncodedKeySpec(var1_2)), var3_9.generatePrivate(new PKCS8EncodedKeySpec(var2_8)));
        }
        catch (InvalidKeySpecException var1_4) {}
        ** GOTO lbl-1000
        catch (NoSuchAlgorithmException var1_7) {}
lbl-1000:
        // 2 sources
        {
            var1_6 = String.valueOf(var1_5);
            Log.w((String)"InstanceID/Store", (String)new StringBuilder(String.valueOf(var1_6).length() + 19).append("Invalid key stored ").append(var1_6).toString());
            InstanceIDListenerService.zza(this.zzair, this);
            return null;
        }
    }
}

