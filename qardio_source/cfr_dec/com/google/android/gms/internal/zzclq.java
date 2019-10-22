/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ServiceInfo
 *  android.content.pm.Signature
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzbfo;
import com.google.android.gms.internal.zzbhf;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzcgx;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzcmb;
import com.google.android.gms.internal.zzcmc;
import com.google.android.gms.internal.zzcmd;
import com.google.android.gms.internal.zzcmg;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.measurement.AppMeasurement;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.security.auth.x500.X500Principal;

public final class zzclq
extends zzcjl {
    private static String[] zzjjn = new String[]{"firebase_"};
    private SecureRandom zzjjo;
    private final AtomicLong zzjjp = new AtomicLong(0L);
    private int zzjjq;

    zzclq(zzcim zzcim2) {
        super(zzcim2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final int zza(String var1_1, Object var2_2, boolean var3_3) {
        block4: {
            block5: {
                if (!var3_3) break block4;
                if (!(var2_2 instanceof Parcelable[])) break block5;
                var4_4 = ((Parcelable[])var2_2).length;
                ** GOTO lbl8
            }
            if (var2_2 instanceof ArrayList) {
                var4_4 = ((ArrayList)var2_2).size();
lbl8:
                // 2 sources
                if (var4_4 > 1000) {
                    this.zzawy().zzazf().zzd("Parameter array is too long; discarded. Value kind, name, array length", "param", var1_1, var4_4);
                    return 17;
                }
                var4_4 = 1;
            } else {
                var4_4 = 1;
            }
            if (var4_4 == 0) {
                return 17;
            }
        }
        var3_3 = zzclq.zzki(var1_1) != false ? this.zza("param", var1_1, 256, var2_2, var3_3) : this.zza("param", var1_1, 100, var2_2, var3_3);
        if (var3_3 == false) return 4;
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Object zza(int n, Object object, boolean bl) {
        if (object == null) {
            return null;
        }
        Object object2 = object;
        if (object instanceof Long) return object2;
        object2 = object;
        if (object instanceof Double) return object2;
        if (object instanceof Integer) {
            return (long)((Integer)object).intValue();
        }
        if (object instanceof Byte) {
            return (long)((Byte)object).byteValue();
        }
        if (object instanceof Short) {
            return (long)((Short)object).shortValue();
        }
        if (object instanceof Boolean) {
            long l;
            if (((Boolean)object).booleanValue()) {
                l = 1L;
                do {
                    return l;
                    break;
                } while (true);
            }
            l = 0L;
            return l;
        }
        if (object instanceof Float) {
            return ((Float)object).doubleValue();
        }
        if (object instanceof String) return zzclq.zza(String.valueOf(object), n, bl);
        if (object instanceof Character) return zzclq.zza(String.valueOf(object), n, bl);
        if (!(object instanceof CharSequence)) return null;
        return zzclq.zza(String.valueOf(object), n, bl);
    }

    public static Object zza(zzcmb arrzzcmc, String string2) {
        for (zzcmc zzcmc2 : arrzzcmc.zzjlh) {
            if (!zzcmc2.name.equals(string2)) continue;
            if (zzcmc2.zzgcc != null) {
                return zzcmc2.zzgcc;
            }
            if (zzcmc2.zzjll != null) {
                return zzcmc2.zzjll;
            }
            if (zzcmc2.zzjjl == null) continue;
            return zzcmc2.zzjjl;
        }
        return null;
    }

    public static String zza(String string2, int n, boolean bl) {
        block3: {
            String string3;
            block2: {
                string3 = string2;
                if (string2.codePointCount(0, string2.length()) <= n) break block2;
                if (!bl) break block3;
                string3 = String.valueOf(string2.substring(0, string2.offsetByCodePoints(0, n))).concat("...");
            }
            return string3;
        }
        return null;
    }

    public static String zza(String string2, String[] arrstring, String[] arrstring2) {
        zzbq.checkNotNull(arrstring);
        zzbq.checkNotNull(arrstring2);
        int n = Math.min(arrstring.length, arrstring2.length);
        for (int i = 0; i < n; ++i) {
            if (!zzclq.zzas(string2, arrstring[i])) continue;
            return arrstring2[i];
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final boolean zza(String object, String string2, int n, Object object2, boolean bl) {
        if (object2 == null) {
            return true;
        }
        if (object2 instanceof Long) return true;
        if (object2 instanceof Float) return true;
        if (object2 instanceof Integer) return true;
        if (object2 instanceof Byte) return true;
        if (object2 instanceof Short) return true;
        if (object2 instanceof Boolean) return true;
        if (object2 instanceof Double) return true;
        if (object2 instanceof String || object2 instanceof Character || object2 instanceof CharSequence) {
            if (((String)(object2 = String.valueOf(object2))).codePointCount(0, ((String)object2).length()) <= n) return true;
            ((zzcjk)this).zzawy().zzazf().zzd("Value is too long; discarded. Value kind, name, value length", object, string2, ((String)object2).length());
            return false;
        }
        if (object2 instanceof Bundle) {
            if (bl) return true;
        }
        if (!(object2 instanceof Parcelable[]) || !bl) {
            if (!(object2 instanceof ArrayList)) return false;
            if (!bl) return false;
            object = (ArrayList)object2;
            int n2 = ((ArrayList)object).size();
            n = 0;
            do {
                if (n >= n2) return true;
                object2 = ((ArrayList)object).get(n);
                ++n;
            } while (object2 instanceof Bundle);
            ((zzcjk)this).zzawy().zzazf().zze("All ArrayList elements must be of type Bundle. Value type, name", object2.getClass(), string2);
            return false;
        }
        object = (Parcelable[])object2;
        int n3 = ((Object)object).length;
        n = 0;
        while (n < n3) {
            object2 = object[n];
            if (!(object2 instanceof Bundle)) {
                ((zzcjk)this).zzawy().zzazf().zze("All Parcelable[] elements must be of type Bundle. Value type, name", object2.getClass(), string2);
                return false;
            }
            ++n;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final boolean zza(String string2, String[] arrstring, String string3) {
        int n;
        block6: {
            block5: {
                if (string3 == null) {
                    ((zzcjk)this).zzawy().zzazd().zzj("Name is required and can't be null. Type", string2);
                    return false;
                }
                zzbq.checkNotNull(string3);
                for (n = 0; n < zzjjn.length; ++n) {
                    if (!string3.startsWith(zzjjn[n])) continue;
                    n = 1;
                    break block5;
                }
                n = 0;
            }
            if (n != 0) {
                ((zzcjk)this).zzawy().zzazd().zze("Name starts with reserved prefix. Type, name", string2, string3);
                return false;
            }
            if (arrstring == null) return true;
            zzbq.checkNotNull(arrstring);
            n = 0;
            while (n < arrstring.length) {
                if (!zzclq.zzas(string3, arrstring[n])) {
                    ++n;
                    continue;
                }
                break block6;
            }
            return true;
        }
        n = 1;
        if (n == 0) return true;
        ((zzcjk)this).zzawy().zzazd().zze("Name is reserved. Type, name", string2, string3);
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean zza(long[] arrl, int n) {
        return n < arrl.length << 6 && (arrl[n / 64] & 1L << n % 64) != 0L;
    }

    static byte[] zza(Parcelable arrby) {
        if (arrby == null) {
            return null;
        }
        Parcel parcel = Parcel.obtain();
        try {
            arrby.writeToParcel(parcel, 0);
            arrby = parcel.marshall();
            return arrby;
        }
        finally {
            parcel.recycle();
        }
    }

    public static long[] zza(BitSet bitSet) {
        int n = (bitSet.length() + 63) / 64;
        long[] arrl = new long[n];
        for (int i = 0; i < n; ++i) {
            arrl[i] = 0L;
            for (int j = 0; j < 64 && (i << 6) + j < bitSet.length(); ++j) {
                if (!bitSet.get((i << 6) + j)) continue;
                arrl[i] = arrl[i] | 1L << j;
            }
        }
        return arrl;
    }

    /*
     * Enabled aggressive block sorting
     */
    static zzcmc[] zza(zzcmc[] arrzzcmc, String string2, Object object) {
        zzcmc[] arrzzcmc2;
        int n = arrzzcmc.length;
        for (int i = 0; i < n; ++i) {
            arrzzcmc2 = arrzzcmc[i];
            if (!Objects.equals(arrzzcmc2.name, string2)) continue;
            arrzzcmc2.zzjll = null;
            arrzzcmc2.zzgcc = null;
            arrzzcmc2.zzjjl = null;
            if (object instanceof Long) {
                arrzzcmc2.zzjll = (Long)object;
                return arrzzcmc;
            } else {
                if (object instanceof String) {
                    arrzzcmc2.zzgcc = (String)object;
                    return arrzzcmc;
                }
                if (!(object instanceof Double)) return arrzzcmc;
                {
                    arrzzcmc2.zzjjl = (Double)object;
                    return arrzzcmc;
                }
            }
        }
        arrzzcmc2 = new zzcmc[arrzzcmc.length + 1];
        System.arraycopy(arrzzcmc, 0, arrzzcmc2, 0, arrzzcmc.length);
        zzcmc zzcmc2 = new zzcmc();
        zzcmc2.name = string2;
        if (object instanceof Long) {
            zzcmc2.zzjll = (Long)object;
        } else if (object instanceof String) {
            zzcmc2.zzgcc = (String)object;
        } else if (object instanceof Double) {
            zzcmc2.zzjjl = (Double)object;
        }
        arrzzcmc2[arrzzcmc.length] = zzcmc2;
        return arrzzcmc2;
    }

    public static Bundle[] zzaf(Object object) {
        if (object instanceof Bundle) {
            return new Bundle[]{(Bundle)object};
        }
        if (object instanceof Parcelable[]) {
            return (Bundle[])Arrays.copyOf((Parcelable[])object, ((Parcelable[])object).length, Bundle[].class);
        }
        if (object instanceof ArrayList) {
            object = (ArrayList)object;
            return ((ArrayList)object).toArray((T[])new Bundle[((ArrayList)object).size()]);
        }
        return null;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Object zzag(Object object) {
        ObjectOutputStream objectOutputStream;
        Closeable closeable;
        void var0_1;
        block11: {
            if (object == null) {
                return null;
            }
            closeable = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream((OutputStream)closeable);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            closeable = new ObjectInputStream(new ByteArrayInputStream(((ByteArrayOutputStream)closeable).toByteArray()));
            try {
                object = ((ObjectInputStream)closeable).readObject();
            }
            catch (Throwable throwable) {
                break block11;
            }
            try {
                objectOutputStream.close();
                ((ObjectInputStream)closeable).close();
                return object;
            }
            catch (IOException iOException) {
                return null;
            }
            catch (ClassNotFoundException classNotFoundException) {
                return null;
            }
            catch (Throwable throwable) {
                closeable = null;
            }
            break block11;
            catch (Throwable throwable) {
                closeable = null;
                objectOutputStream = null;
            }
        }
        if (objectOutputStream != null) {
            objectOutputStream.close();
        }
        if (closeable == null) throw var0_1;
        ((ObjectInputStream)closeable).close();
        throw var0_1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean zzag(Context context, String string2) {
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            context = zzbhf.zzdb(context).getPackageInfo(string2, 64);
            if (context == null) return true;
            if (context.signatures == null) return true;
            if (context.signatures.length <= 0) return true;
            context = context.signatures[0];
            return ((X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(context.toByteArray()))).getSubjectX500Principal().equals(x500Principal);
        }
        catch (CertificateException certificateException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Error obtaining certificate", certificateException);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Package name not found", (Object)nameNotFoundException);
            return true;
        }
    }

    private final boolean zzaq(String string2, String string3) {
        int n;
        if (string3 == null) {
            ((zzcjk)this).zzawy().zzazd().zzj("Name is required and can't be null. Type", string2);
            return false;
        }
        if (string3.length() == 0) {
            ((zzcjk)this).zzawy().zzazd().zzj("Name is required and can't be empty. Type", string2);
            return false;
        }
        int n2 = string3.codePointAt(0);
        if (!Character.isLetter(n2)) {
            ((zzcjk)this).zzawy().zzazd().zze("Name must start with a letter. Type, name", string2, string3);
            return false;
        }
        int n3 = string3.length();
        for (n2 = Character.charCount(n2); n2 < n3; n2 += Character.charCount(n)) {
            n = string3.codePointAt(n2);
            if (n == 95 || Character.isLetterOrDigit(n)) continue;
            ((zzcjk)this).zzawy().zzazd().zze("Name must consist of letters, digits or _ (underscores). Type, name", string2, string3);
            return false;
        }
        return true;
    }

    private final boolean zzar(String string2, String string3) {
        int n;
        if (string3 == null) {
            ((zzcjk)this).zzawy().zzazd().zzj("Name is required and can't be null. Type", string2);
            return false;
        }
        if (string3.length() == 0) {
            ((zzcjk)this).zzawy().zzazd().zzj("Name is required and can't be empty. Type", string2);
            return false;
        }
        int n2 = string3.codePointAt(0);
        if (!Character.isLetter(n2) && n2 != 95) {
            ((zzcjk)this).zzawy().zzazd().zze("Name must start with a letter or _ (underscore). Type, name", string2, string3);
            return false;
        }
        int n3 = string3.length();
        for (n2 = Character.charCount(n2); n2 < n3; n2 += Character.charCount(n)) {
            n = string3.codePointAt(n2);
            if (n == 95 || Character.isLetterOrDigit(n)) continue;
            ((zzcjk)this).zzawy().zzazd().zze("Name must consist of letters, digits or _ (underscores). Type, name", string2, string3);
            return false;
        }
        return true;
    }

    public static boolean zzas(String string2, String string3) {
        if (string2 == null && string3 == null) {
            return true;
        }
        if (string2 == null) {
            return false;
        }
        return string2.equals(string3);
    }

    private static void zzb(Bundle bundle, Object object) {
        zzbq.checkNotNull(bundle);
        if (object != null && (object instanceof String || object instanceof CharSequence)) {
            bundle.putLong("_el", (long)String.valueOf(object).length());
        }
    }

    private final boolean zzb(String string2, int n, String string3) {
        if (string3 == null) {
            ((zzcjk)this).zzawy().zzazd().zzj("Name is required and can't be null. Type", string2);
            return false;
        }
        if (string3.codePointCount(0, string3.length()) > n) {
            ((zzcjk)this).zzawy().zzazd().zzd("Name is too long. Type, maximum supported length, name", string2, n, string3);
            return false;
        }
        return true;
    }

    private static boolean zzd(Bundle bundle, int n) {
        if (bundle.getLong("_err") == 0L) {
            bundle.putLong("_err", (long)n);
            return true;
        }
        return false;
    }

    static boolean zzd(zzcha zzcha2, zzcgi zzcgi2) {
        zzbq.checkNotNull(zzcha2);
        zzbq.checkNotNull(zzcgi2);
        return !TextUtils.isEmpty((CharSequence)zzcgi2.zzixs);
    }

    static MessageDigest zzek(String string2) {
        for (int i = 0; i < 2; ++i) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(string2);
                if (messageDigest == null) continue;
                return messageDigest;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                // empty catch block
            }
        }
        return null;
    }

    static boolean zzjz(String string2) {
        boolean bl = false;
        zzbq.zzgm(string2);
        if (string2.charAt(0) != '_' || string2.equals("_ep")) {
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final int zzke(String string2) {
        block5: {
            block4: {
                if (!this.zzaq("event param", string2)) break block4;
                if (!this.zza("event param", null, string2)) {
                    return 14;
                }
                if (this.zzb("event param", 40, string2)) break block5;
            }
            return 3;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final int zzkf(String string2) {
        block5: {
            block4: {
                if (!this.zzar("event param", string2)) break block4;
                if (!this.zza("event param", null, string2)) {
                    return 14;
                }
                if (this.zzb("event param", 40, string2)) break block5;
            }
            return 3;
        }
        return 0;
    }

    private static int zzkh(String string2) {
        if ("_ldl".equals(string2)) {
            return 2048;
        }
        return 36;
    }

    public static boolean zzki(String string2) {
        return !TextUtils.isEmpty((CharSequence)string2) && string2.startsWith("_");
    }

    static boolean zzkk(String string2) {
        return string2 != null && string2.matches("(\\+|-)?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && string2.length() <= 310;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean zzkn(String string2) {
        boolean bl = true;
        zzbq.zzgm(string2);
        int n = -1;
        switch (string2.hashCode()) {
            case 94660: {
                if (!string2.equals("_in")) break;
                n = 0;
                break;
            }
            case 95027: {
                if (!string2.equals("_ui")) break;
                n = 1;
                break;
            }
            case 95025: {
                if (!string2.equals("_ug")) break;
                n = 2;
                break;
            }
        }
        switch (n) {
            default: {
                return false;
            }
            case 0: 
            case 1: 
            case 2: 
        }
        return bl;
    }

    public static boolean zzo(Intent object) {
        return "android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(object = object.getStringExtra("android.intent.extra.REFERRER_NAME")) || "https://www.google.com".equals(object) || "android-app://com.google.appcrawler".equals(object);
    }

    /*
     * Enabled aggressive block sorting
     */
    static long zzs(byte[] arrby) {
        int n = 0;
        zzbq.checkNotNull(arrby);
        boolean bl = arrby.length > 0;
        zzbq.checkState(bl);
        long l = 0L;
        for (int i = arrby.length - 1; i >= 0 && i >= arrby.length - 8; l += ((long)arrby[i] & 0xFFL) << n, n += 8, --i) {
        }
        return l;
    }

    public static boolean zzt(Context context, String string2) {
        block6: {
            PackageManager packageManager;
            block5: {
                packageManager = context.getPackageManager();
                if (packageManager != null) break block5;
                return false;
            }
            context = packageManager.getServiceInfo(new ComponentName(context, string2), 4);
            if (context == null) break block6;
            try {
                boolean bl = context.enabled;
                if (bl) {
                    return true;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                // empty catch block
            }
        }
        return false;
    }

    public final Bundle zza(String string2, Bundle bundle, List<String> list, boolean bl, boolean bl2) {
        Bundle bundle2 = null;
        if (bundle != null) {
            bundle2 = new Bundle(bundle);
            Iterator iterator = bundle.keySet().iterator();
            int n = 0;
            while (iterator.hasNext()) {
                String string3 = (String)iterator.next();
                int n2 = 0;
                int n3 = 0;
                if (list == null || !list.contains(string3)) {
                    if (bl) {
                        n3 = this.zzke(string3);
                    }
                    n2 = n3;
                    if (n3 == 0) {
                        n2 = this.zzkf(string3);
                    }
                }
                if (n2 != 0) {
                    if (zzclq.zzd(bundle2, n2)) {
                        bundle2.putString("_ev", zzclq.zza(string3, 40, true));
                        if (n2 == 3) {
                            zzclq.zzb(bundle2, string3);
                        }
                    }
                    bundle2.remove(string3);
                    continue;
                }
                n2 = this.zza(string3, bundle.get(string3), bl2);
                if (n2 != 0 && !"_ev".equals(string3)) {
                    if (zzclq.zzd(bundle2, n2)) {
                        bundle2.putString("_ev", zzclq.zza(string3, 40, true));
                        zzclq.zzb(bundle2, bundle.get(string3));
                    }
                    bundle2.remove(string3);
                    continue;
                }
                n2 = n++;
                if (zzclq.zzjz(string3)) {
                    n2 = n;
                    if (n > 25) {
                        String string4 = new StringBuilder(48).append("Event can't contain more then 25 params").toString();
                        ((zzcjk)this).zzawy().zzazd().zze(string4, ((zzcjk)this).zzawt().zzjh(string2), ((zzcjk)this).zzawt().zzx(bundle));
                        zzclq.zzd(bundle2, 5);
                        bundle2.remove(string3);
                        continue;
                    }
                }
                n = n2;
            }
        }
        return bundle2;
    }

    /*
     * Enabled aggressive block sorting
     */
    final zzcha zza(String string2, Bundle bundle, String string3, long l, boolean bl, boolean bl2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return null;
        }
        if (this.zzkb(string2) != 0) {
            ((zzcjk)this).zzawy().zzazd().zzj("Invalid conditional property event name", ((zzcjk)this).zzawt().zzjj(string2));
            throw new IllegalArgumentException();
        }
        bundle = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle.putString("_o", string3);
        return new zzcha(string2, new zzcgx(this.zzy(this.zza(string2, bundle, Collections.singletonList("_o"), false, false))), string3, l);
    }

    public final void zza(int n, String string2, String string3, int n2) {
        this.zza(null, n, string2, string3, n2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final void zza(Bundle object, String string2, Object object2) {
        void var3_6;
        void var1_3;
        void var2_5;
        block9: {
            block8: {
                if (object == null) break block8;
                if (var3_6 instanceof Long) {
                    object.putLong((String)var2_5, ((Long)var3_6).longValue());
                    return;
                }
                if (var3_6 instanceof String) {
                    object.putString((String)var2_5, String.valueOf(var3_6));
                    return;
                }
                if (var3_6 instanceof Double) {
                    object.putDouble((String)var2_5, ((Double)var3_6).doubleValue());
                    return;
                }
                if (var2_5 != null) break block9;
            }
            return;
        }
        if (var3_6 != null) {
            String string3 = var3_6.getClass().getSimpleName();
        } else {
            Object var1_4 = null;
        }
        ((zzcjk)this).zzawy().zzazg().zze("Not putting event parameter. Invalid value type. name, type", ((zzcjk)this).zzawt().zzji((String)var2_5), var1_3);
    }

    public final void zza(zzcmc zzcmc2, Object object) {
        zzbq.checkNotNull(object);
        zzcmc2.zzgcc = null;
        zzcmc2.zzjll = null;
        zzcmc2.zzjjl = null;
        if (object instanceof String) {
            zzcmc2.zzgcc = (String)object;
            return;
        }
        if (object instanceof Long) {
            zzcmc2.zzjll = (Long)object;
            return;
        }
        if (object instanceof Double) {
            zzcmc2.zzjjl = (Double)object;
            return;
        }
        ((zzcjk)this).zzawy().zzazd().zzj("Ignoring invalid (type) event param value", object);
    }

    public final void zza(zzcmg zzcmg2, Object object) {
        zzbq.checkNotNull(object);
        zzcmg2.zzgcc = null;
        zzcmg2.zzjll = null;
        zzcmg2.zzjjl = null;
        if (object instanceof String) {
            zzcmg2.zzgcc = (String)object;
            return;
        }
        if (object instanceof Long) {
            zzcmg2.zzjll = (Long)object;
            return;
        }
        if (object instanceof Double) {
            zzcmg2.zzjjl = (Double)object;
            return;
        }
        ((zzcjk)this).zzawy().zzazd().zzj("Ignoring invalid (type) user attribute value", object);
    }

    public final void zza(String string2, int n, String string3, String string4, int n2) {
        string2 = new Bundle();
        zzclq.zzd((Bundle)string2, n);
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            string2.putString(string3, string4);
        }
        if (n == 6 || n == 7 || n == 2) {
            string2.putLong("_el", (long)n2);
        }
        this.zziwf.zzawm().zzc("auto", "_err", (Bundle)string2);
    }

    final long zzaf(Context context, String string2) {
        ((zzcjk)this).zzve();
        zzbq.checkNotNull(context);
        zzbq.zzgm(string2);
        PackageManager packageManager = context.getPackageManager();
        MessageDigest messageDigest = zzclq.zzek("MD5");
        if (messageDigest == null) {
            ((zzcjk)this).zzawy().zzazd().log("Could not get MD5 instance");
            return -1L;
        }
        if (packageManager != null) {
            try {
                if (!this.zzag(context, string2)) {
                    context = zzbhf.zzdb(context).getPackageInfo(((zzcjk)this).getContext().getPackageName(), 64);
                    if (context.signatures != null && context.signatures.length > 0) {
                        return zzclq.zzs(messageDigest.digest(context.signatures[0].toByteArray()));
                    }
                    ((zzcjk)this).zzawy().zzazf().log("Could not get signatures");
                    return -1L;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                ((zzcjk)this).zzawy().zzazd().zzj("Package name not found", (Object)nameNotFoundException);
            }
        }
        return 0L;
    }

    @Override
    protected final boolean zzaxz() {
        return true;
    }

    @Override
    protected final void zzayy() {
        long l;
        ((zzcjk)this).zzve();
        SecureRandom secureRandom = new SecureRandom();
        long l2 = l = secureRandom.nextLong();
        if (l == 0L) {
            l2 = l = secureRandom.nextLong();
            if (l == 0L) {
                ((zzcjk)this).zzawy().zzazf().log("Utils falling back to Random for random id");
                l2 = l;
            }
        }
        this.zzjjp.set(l2);
    }

    final <T extends Parcelable> T zzb(byte[] parcelable, Parcelable.Creator<T> creator) {
        if (parcelable == null) {
            return null;
        }
        Parcel parcel = Parcel.obtain();
        try {
            parcel.unmarshall((byte[])parcelable, 0, ((byte[])parcelable).length);
            parcel.setDataPosition(0);
            parcelable = (Parcelable)creator.createFromParcel(parcel);
            return (T)parcelable;
        }
        catch (zzbfo zzbfo2) {
            ((zzcjk)this).zzawy().zzazd().log("Failed to load parcelable from buffer");
            return null;
        }
        finally {
            parcel.recycle();
        }
    }

    public final byte[] zzb(zzcmd zzcmd2) {
        try {
            byte[] arrby = new byte[zzcmd2.zzho()];
            zzfjk zzfjk2 = zzfjk.zzo(arrby, 0, arrby.length);
            ((zzfjs)zzcmd2).zza(zzfjk2);
            zzfjk2.zzcwt();
            return arrby;
        }
        catch (IOException iOException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Data loss. Failed to serialize batch", iOException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final long zzbay() {
        if (this.zzjjp.get() == 0L) {
            AtomicLong atomicLong = this.zzjjp;
            synchronized (atomicLong) {
                int n;
                long l = new Random(System.nanoTime() ^ ((zzcjk)this).zzws().currentTimeMillis()).nextLong();
                this.zzjjq = n = this.zzjjq + 1;
                long l2 = n;
                return l + l2;
            }
        }
        AtomicLong atomicLong = this.zzjjp;
        synchronized (atomicLong) {
            this.zzjjp.compareAndSet(-1L, 1L);
            return this.zzjjp.getAndIncrement();
        }
    }

    final SecureRandom zzbaz() {
        ((zzcjk)this).zzve();
        if (this.zzjjo == null) {
            this.zzjjo = new SecureRandom();
        }
        return this.zzjjo;
    }

    public final boolean zzeb(String string2) {
        ((zzcjk)this).zzve();
        if (zzbhf.zzdb(((zzcjk)this).getContext()).checkCallingOrSelfPermission(string2) == 0) {
            return true;
        }
        ((zzcjk)this).zzawy().zzazi().zzj("Permission not granted", string2);
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean zzf(long l, long l2) {
        return l == 0L || l2 <= 0L || Math.abs(((zzcjk)this).zzws().currentTimeMillis() - l) > l2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final Object zzk(String string2, Object object) {
        int n = 256;
        if ("_ev".equals(string2)) {
            return zzclq.zza(256, object, true);
        }
        if (zzclq.zzki(string2)) {
            do {
                return zzclq.zza(n, object, false);
                break;
            } while (true);
        }
        n = 100;
        return zzclq.zza(n, object, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int zzka(String string2) {
        block5: {
            block4: {
                if (!this.zzaq("event", string2)) break block4;
                if (!this.zza("event", AppMeasurement.Event.zziwg, string2)) {
                    return 13;
                }
                if (this.zzb("event", 40, string2)) break block5;
            }
            return 2;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int zzkb(String string2) {
        block5: {
            block4: {
                if (!this.zzar("event", string2)) break block4;
                if (!this.zza("event", AppMeasurement.Event.zziwg, string2)) {
                    return 13;
                }
                if (this.zzb("event", 40, string2)) break block5;
            }
            return 2;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int zzkc(String string2) {
        block5: {
            block4: {
                if (!this.zzaq("user property", string2)) break block4;
                if (!this.zza("user property", AppMeasurement.UserProperty.zziwn, string2)) {
                    return 15;
                }
                if (this.zzb("user property", 24, string2)) break block5;
            }
            return 6;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int zzkd(String string2) {
        block5: {
            block4: {
                if (!this.zzar("user property", string2)) break block4;
                if (!this.zza("user property", AppMeasurement.UserProperty.zziwn, string2)) {
                    return 15;
                }
                if (this.zzb("user property", 24, string2)) break block5;
            }
            return 6;
        }
        return 0;
    }

    public final boolean zzkg(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            ((zzcjk)this).zzawy().zzazd().log("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
            return false;
        }
        zzbq.checkNotNull(string2);
        if (!string2.matches("^1:\\d+:android:[a-f0-9]+$")) {
            ((zzcjk)this).zzawy().zzazd().zzj("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", string2);
            return false;
        }
        return true;
    }

    public final boolean zzkj(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return false;
        }
        return ((zzcjk)this).zzaxa().zzayd().equals(string2);
    }

    final boolean zzkl(String string2) {
        return "1".equals(((zzcjk)this).zzawv().zzam(string2, "measurement.upload.blacklist_internal"));
    }

    final boolean zzkm(String string2) {
        return "1".equals(((zzcjk)this).zzawv().zzam(string2, "measurement.upload.blacklist_public"));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int zzl(String string2, Object object) {
        boolean bl = "_ldl".equals(string2) ? this.zza("user property referrer", string2, zzclq.zzkh(string2), object, false) : this.zza("user property", string2, zzclq.zzkh(string2), object, false);
        if (bl) {
            return 0;
        }
        return 7;
    }

    public final Object zzm(String string2, Object object) {
        if ("_ldl".equals(string2)) {
            return zzclq.zza(zzclq.zzkh(string2), object, true);
        }
        return zzclq.zza(zzclq.zzkh(string2), object, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Bundle zzp(Uri object) {
        String string2;
        Bundle bundle;
        block16: {
            Bundle bundle2;
            block15: {
                String string3;
                String string4;
                String string5;
                String string6;
                bundle2 = null;
                if (object == null) break block15;
                try {
                    if (object.isHierarchical()) {
                        string6 = object.getQueryParameter("utm_campaign");
                        string5 = object.getQueryParameter("utm_source");
                        string4 = object.getQueryParameter("utm_medium");
                        string3 = object.getQueryParameter("gclid");
                    } else {
                        string3 = null;
                        string4 = null;
                        string5 = null;
                        string6 = null;
                    }
                }
                catch (UnsupportedOperationException unsupportedOperationException) {
                    ((zzcjk)this).zzawy().zzazf().zzj("Install referrer url isn't a hierarchical URI", unsupportedOperationException);
                    return null;
                }
                if (TextUtils.isEmpty((CharSequence)string6) && TextUtils.isEmpty((CharSequence)string5) && TextUtils.isEmpty((CharSequence)string4) && TextUtils.isEmpty((CharSequence)string3)) break block15;
                bundle = new Bundle();
                if (!TextUtils.isEmpty((CharSequence)string6)) {
                    bundle.putString("campaign", string6);
                }
                if (!TextUtils.isEmpty((CharSequence)string5)) {
                    bundle.putString("source", string5);
                }
                if (!TextUtils.isEmpty((CharSequence)string4)) {
                    bundle.putString("medium", string4);
                }
                if (!TextUtils.isEmpty((CharSequence)string3)) {
                    bundle.putString("gclid", string3);
                }
                if (!TextUtils.isEmpty((CharSequence)(string3 = object.getQueryParameter("utm_term")))) {
                    bundle.putString("term", string3);
                }
                if (!TextUtils.isEmpty((CharSequence)(string3 = object.getQueryParameter("utm_content")))) {
                    bundle.putString("content", string3);
                }
                if (!TextUtils.isEmpty((CharSequence)(string3 = object.getQueryParameter("aclid")))) {
                    bundle.putString("aclid", string3);
                }
                if (!TextUtils.isEmpty((CharSequence)(string3 = object.getQueryParameter("cp1")))) {
                    bundle.putString("cp1", string3);
                }
                string2 = object.getQueryParameter("anid");
                bundle2 = bundle;
                if (!TextUtils.isEmpty((CharSequence)string2)) break block16;
            }
            return bundle2;
        }
        bundle.putString("anid", string2);
        return bundle;
    }

    public final byte[] zzq(byte[] arrby) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(arrby);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            arrby = byteArrayOutputStream.toByteArray();
            return arrby;
        }
        catch (IOException iOException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Failed to gzip content", iOException);
            throw iOException;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final byte[] zzr(byte[] object) throws IOException {
        GZIPInputStream gZIPInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            int n;
            object = new ByteArrayInputStream((byte[])object);
            gZIPInputStream = new GZIPInputStream((InputStream)object);
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] arrby = new byte[1024];
            while ((n = gZIPInputStream.read(arrby)) > 0) {
                byteArrayOutputStream.write(arrby, 0, n);
            }
        }
        catch (IOException iOException) {
            ((zzcjk)this).zzawy().zzazd().zzj("Failed to ungzip content", iOException);
            throw iOException;
        }
        {
            gZIPInputStream.close();
            ((ByteArrayInputStream)object).close();
            return byteArrayOutputStream.toByteArray();
        }
    }

    final Bundle zzy(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String string2 : bundle.keySet()) {
                Object object = this.zzk(string2, bundle.get(string2));
                if (object == null) {
                    ((zzcjk)this).zzawy().zzazf().zzj("Param value can't be null", ((zzcjk)this).zzawt().zzji(string2));
                    continue;
                }
                this.zza(bundle2, string2, object);
            }
        }
        return bundle2;
    }
}

