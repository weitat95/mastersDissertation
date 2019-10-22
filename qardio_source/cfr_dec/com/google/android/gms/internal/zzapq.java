/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzaqa;
import com.google.android.gms.internal.zzaqc;
import com.google.android.gms.internal.zzaqu;
import com.google.android.gms.internal.zzash;
import com.google.android.gms.internal.zzasl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

public final class zzapq
extends zzaqa {
    private static boolean zzdsm;
    private AdvertisingIdClient.Info zzdsn;
    private final zzash zzdso;
    private String zzdsp;
    private boolean zzdsq = false;
    private final Object zzdsr = new Object();

    zzapq(zzaqc zzaqc2) {
        super(zzaqc2);
        this.zzdso = new zzash(zzaqc2.zzws());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean zza(AdvertisingIdClient.Info object, AdvertisingIdClient.Info object2) {
        String string2 = null;
        if (TextUtils.isEmpty((CharSequence)(object2 = object2 == null ? null : ((AdvertisingIdClient.Info)object2).getId()))) {
            return true;
        }
        String string3 = this.zzxb().zzyk();
        Object object3 = this.zzdsr;
        synchronized (object3) {
            if (!this.zzdsq) {
                this.zzdsp = this.zzwl();
                this.zzdsq = true;
            } else if (TextUtils.isEmpty((CharSequence)this.zzdsp)) {
                if ((object = object == null ? string2 : ((AdvertisingIdClient.Info)object).getId()) == null) {
                    object = String.valueOf(object2);
                    object2 = String.valueOf(string3);
                    object = ((String)object2).length() != 0 ? ((String)object).concat((String)object2) : new String((String)object);
                    return this.zzdt((String)object);
                }
                object = String.valueOf(object);
                string2 = String.valueOf(string3);
                object = string2.length() != 0 ? ((String)object).concat(string2) : new String((String)object);
                this.zzdsp = zzapq.zzds((String)object);
            }
            object = String.valueOf(object2);
            string2 = String.valueOf(string3);
            object = string2.length() != 0 ? ((String)object).concat(string2) : new String((String)object);
            if (TextUtils.isEmpty((CharSequence)(object = zzapq.zzds((String)object)))) {
                return false;
            }
            if (((String)object).equals(this.zzdsp)) {
                return true;
            }
            if (!TextUtils.isEmpty((CharSequence)this.zzdsp)) {
                this.zzdu("Resetting the client id because Advertising Id changed.");
                object = this.zzxb().zzyl();
                this.zza("New client Id", object);
            } else {
                object = string3;
            }
            object2 = String.valueOf(object2);
            object = ((String)(object = String.valueOf(object))).length() != 0 ? ((String)object2).concat((String)object) : new String((String)object2);
            return this.zzdt((String)object);
        }
    }

    private static String zzds(String string2) {
        MessageDigest messageDigest = zzasl.zzek("MD5");
        if (messageDigest == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigest.digest(string2.getBytes())));
    }

    private final boolean zzdt(String string2) {
        try {
            string2 = zzapq.zzds(string2);
            this.zzdu("Storing hashed adid.");
            FileOutputStream fileOutputStream = this.getContext().openFileOutput("gaClientIdData", 0);
            fileOutputStream.write(string2.getBytes());
            fileOutputStream.close();
            this.zzdsp = string2;
            return true;
        }
        catch (IOException iOException) {
            this.zze("Error creating hash file", iOException);
            return false;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final AdvertisingIdClient.Info zzwj() {
        synchronized (this) {
            block5: {
                if (!this.zzdso.zzu(1000L)) return this.zzdsn;
                this.zzdso.start();
                AdvertisingIdClient.Info info = this.zzwk();
                if (!this.zza(this.zzdsn, info)) break block5;
                this.zzdsn = info;
                do {
                    return this.zzdsn;
                    break;
                } while (true);
            }
            this.zzdy("Failed to reset client id on adid change. Not using adid");
            this.zzdsn = new AdvertisingIdClient.Info("", false);
            return this.zzdsn;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final AdvertisingIdClient.Info zzwk() {
        AdvertisingIdClient.Info info = null;
        try {
            AdvertisingIdClient.Info info2 = AdvertisingIdClient.getAdvertisingIdInfo(this.getContext());
            return info2;
        }
        catch (IllegalStateException illegalStateException) {
            this.zzdx("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        }
        catch (Throwable throwable) {
            if (zzdsm) return info;
            zzdsm = true;
            this.zzd("Error getting advertiser id", throwable);
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final String zzwl() {
        var2_1 = null;
        var4_6 = this.getContext().openFileInput("gaClientIdData");
        var3_8 = new byte[128];
        var1_13 = var4_6.read(var3_8, 0, 128);
        if (var4_6.available() > 0) {
            this.zzdx("Hash file seems corrupted, deleting it.");
            var4_6.close();
            this.getContext().deleteFile("gaClientIdData");
            return null;
        }
        if (var1_13 <= 0) {
            this.zzdu("Hash file is empty.");
            var4_6.close();
            return null;
        }
        var3_9 = new String(var3_8, 0, var1_13);
        var4_6.close();
        return var3_9;
        catch (IOException var3_10) {}
        ** GOTO lbl-1000
        catch (IOException var4_7) {
            var2_3 = var3_9;
            var3_12 = var4_7;
        }
lbl-1000:
        // 2 sources
        {
            this.zzd("Error reading Hash file, deleting it", var3_11);
            this.getContext().deleteFile("gaClientIdData");
            return var2_2;
        }
        catch (FileNotFoundException var2_4) {
            return var3_9;
        }
        catch (FileNotFoundException var2_5) {
            return null;
        }
    }

    @Override
    protected final void zzvf() {
    }

    public final boolean zzwb() {
        boolean bl = false;
        this.zzxf();
        AdvertisingIdClient.Info info = this.zzwj();
        boolean bl2 = bl;
        if (info != null) {
            bl2 = bl;
            if (!info.isLimitAdTrackingEnabled()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String zzwi() {
        this.zzxf();
        Object object = this.zzwj();
        object = object != null ? ((AdvertisingIdClient.Info)object).getId() : null;
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        return object;
    }
}

