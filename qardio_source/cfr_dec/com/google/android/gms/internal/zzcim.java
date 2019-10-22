/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteException
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzbhf;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgh;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgq;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzcgv;
import com.google.android.gms.internal.zzcgw;
import com.google.android.gms.internal.zzcgx;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzchc;
import com.google.android.gms.internal.zzchd;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchi;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzchq;
import com.google.android.gms.internal.zzchs;
import com.google.android.gms.internal.zzchu;
import com.google.android.gms.internal.zzchv;
import com.google.android.gms.internal.zzchx;
import com.google.android.gms.internal.zzcia;
import com.google.android.gms.internal.zzcic;
import com.google.android.gms.internal.zzcid;
import com.google.android.gms.internal.zzcig;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcin;
import com.google.android.gms.internal.zzcio;
import com.google.android.gms.internal.zzciq;
import com.google.android.gms.internal.zzcjk;
import com.google.android.gms.internal.zzcjl;
import com.google.android.gms.internal.zzcjm;
import com.google.android.gms.internal.zzcjn;
import com.google.android.gms.internal.zzcjo;
import com.google.android.gms.internal.zzckb;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzcla;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzcll;
import com.google.android.gms.internal.zzcln;
import com.google.android.gms.internal.zzclp;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.internal.zzcly;
import com.google.android.gms.internal.zzcma;
import com.google.android.gms.internal.zzcmb;
import com.google.android.gms.internal.zzcmc;
import com.google.android.gms.internal.zzcmd;
import com.google.android.gms.internal.zzcme;
import com.google.android.gms.internal.zzcmg;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class zzcim {
    private static volatile zzcim zzjev;
    private final Context mContext;
    private final zzd zzata;
    private boolean zzdtb = false;
    private final zzcgn zzjew;
    private final zzchx zzjex;
    private final zzchm zzjey;
    private final zzcih zzjez;
    private final zzclf zzjfa;
    private final zzcig zzjfb;
    private final AppMeasurement zzjfc;
    private final FirebaseAnalytics zzjfd;
    private final zzclq zzjfe;
    private final zzchk zzjff;
    private final zzcgo zzjfg;
    private final zzchi zzjfh;
    private final zzchq zzjfi;
    private final zzckc zzjfj;
    private final zzckg zzjfk;
    private final zzcgu zzjfl;
    private final zzcjn zzjfm;
    private final zzchh zzjfn;
    private final zzchv zzjfo;
    private final zzcll zzjfp;
    private final zzcgk zzjfq;
    private final zzcgd zzjfr;
    private boolean zzjfs;
    private Boolean zzjft;
    private long zzjfu;
    private FileLock zzjfv;
    private FileChannel zzjfw;
    private List<Long> zzjfx;
    private List<Runnable> zzjfy;
    private int zzjfz;
    private int zzjga;
    private long zzjgb;
    private long zzjgc;
    private boolean zzjgd;
    private boolean zzjge;
    private boolean zzjgf;
    private final long zzjgg;

    /*
     * Enabled aggressive block sorting
     */
    private zzcim(zzcjm object) {
        zzbq.checkNotNull(object);
        this.mContext = ((zzcjm)object).mContext;
        this.zzjgb = -1L;
        this.zzata = zzh.zzamg();
        this.zzjgg = this.zzata.currentTimeMillis();
        this.zzjew = new zzcgn(this);
        object = new zzchx(this);
        ((zzcjl)object).initialize();
        this.zzjex = object;
        object = new zzchm(this);
        ((zzcjl)object).initialize();
        this.zzjey = object;
        object = new zzclq(this);
        ((zzcjl)object).initialize();
        this.zzjfe = object;
        object = new zzchk(this);
        ((zzcjl)object).initialize();
        this.zzjff = object;
        object = new zzcgu(this);
        ((zzcjl)object).initialize();
        this.zzjfl = object;
        object = new zzchh(this);
        ((zzcjl)object).initialize();
        this.zzjfn = object;
        object = new zzcgo(this);
        ((zzcjl)object).initialize();
        this.zzjfg = object;
        object = new zzchi(this);
        ((zzcjl)object).initialize();
        this.zzjfh = object;
        object = new zzcgk(this);
        ((zzcjl)object).initialize();
        this.zzjfq = object;
        this.zzjfr = new zzcgd(this);
        object = new zzchq(this);
        ((zzcjl)object).initialize();
        this.zzjfi = object;
        object = new zzckc(this);
        ((zzcjl)object).initialize();
        this.zzjfj = object;
        object = new zzckg(this);
        ((zzcjl)object).initialize();
        this.zzjfk = object;
        object = new zzcjn(this);
        ((zzcjl)object).initialize();
        this.zzjfm = object;
        object = new zzcll(this);
        ((zzcjl)object).initialize();
        this.zzjfp = object;
        this.zzjfo = new zzchv(this);
        this.zzjfc = new AppMeasurement(this);
        this.zzjfd = new FirebaseAnalytics(this);
        object = new zzclf(this);
        ((zzcjl)object).initialize();
        this.zzjfa = object;
        object = new zzcig(this);
        ((zzcjl)object).initialize();
        this.zzjfb = object;
        object = new zzcih(this);
        ((zzcjl)object).initialize();
        this.zzjez = object;
        if (this.mContext.getApplicationContext() instanceof Application) {
            object = this.zzawm();
            if (((zzcjk)object).getContext().getApplicationContext() instanceof Application) {
                Application application = (Application)((zzcjk)object).getContext().getApplicationContext();
                if (((zzcjn)object).zzjgx == null) {
                    ((zzcjn)object).zzjgx = new zzckb((zzcjn)object, null);
                }
                application.unregisterActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)((zzcjn)object).zzjgx);
                application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)((zzcjn)object).zzjgx);
                ((zzcjk)object).zzawy().zzazj().log("Registered activity lifecycle callback");
            }
        } else {
            this.zzawy().zzazf().log("Application context is not an Application");
        }
        this.zzjez.zzg(new zzcin(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final int zza(FileChannel fileChannel) {
        ByteBuffer byteBuffer;
        ((zzcjk)this.zzawx()).zzve();
        if (fileChannel != null && fileChannel.isOpen()) {
            byteBuffer = ByteBuffer.allocate(4);
            fileChannel.position(0L);
            int n = fileChannel.read(byteBuffer);
            if (n != 4) {
                if (n == -1) return 0;
                this.zzawy().zzazf().zzj("Unexpected data length. Bytes read", n);
                return 0;
            }
        } else {
            this.zzawy().zzazd().log("Bad chanel to read from");
            return 0;
        }
        try {
            byteBuffer.flip();
            return byteBuffer.getInt();
        }
        catch (IOException iOException) {
            this.zzawy().zzazd().zzj("Failed to read from channel", iOException);
            return 0;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final zzcgi zza(Context context, String string2, String string3, boolean bl, boolean bl2) {
        void var7_15;
        PackageInfo packageInfo;
        CharSequence charSequence = "Unknown";
        String string4 = "Unknown";
        int n = Integer.MIN_VALUE;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            this.zzawy().zzazd().log("PackageManager is null, can not log app install information");
            return null;
        }
        try {
            String string5 = packageManager.getInstallerPackageName(string2);
            charSequence = string5;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.zzawy().zzazd().zzj("Error retrieving installer package name. appId", zzchm.zzjk(string2));
        }
        if (charSequence == null) {
            String string6 = "manual_install";
        } else {
            String string7 = charSequence;
            if ("com.android.vending".equals(charSequence)) {
                String string8 = "";
            }
        }
        try {
            packageInfo = zzbhf.zzdb(context).getPackageInfo(string2, 0);
            charSequence = string4;
            if (packageInfo == null) return new zzcgi(string2, string3, (String)charSequence, n, (String)var7_15, 11910L, this.zzawu().zzaf(context, string2), null, bl, false, "", 0L, 0L, 0, bl2);
            charSequence = zzbhf.zzdb(context).zzgt(string2);
            charSequence = !TextUtils.isEmpty((CharSequence)charSequence) ? charSequence.toString() : "Unknown";
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            charSequence = "Unknown";
            this.zzawy().zzazd().zze("Error retrieving newly installed package info. appId, appName", zzchm.zzjk(string2), charSequence);
            return null;
        }
        string4 = packageInfo.versionName;
        n = packageInfo.versionCode;
        charSequence = string4;
        return new zzcgi(string2, string3, (String)charSequence, n, (String)var7_15, 11910L, this.zzawu().zzaf(context, string2), null, bl, false, "", 0L, 0L, 0, bl2);
        {
            catch (PackageManager.NameNotFoundException nameNotFoundException) {}
        }
    }

    static /* synthetic */ void zza(zzcim zzcim2) {
        zzcim2.zzazw();
    }

    private static void zza(zzcjk zzcjk2) {
        if (zzcjk2 == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static void zza(zzcjl zzcjl2) {
        if (zzcjl2 == null) {
            throw new IllegalStateException("Component not created");
        }
        if (!zzcjl2.isInitialized()) {
            throw new IllegalStateException("Component not initialized");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean zza(int n, FileChannel fileChannel) {
        boolean bl = true;
        ((zzcjk)this.zzawx()).zzve();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzawy().zzazd().log("Bad chanel to read from");
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(n);
        byteBuffer.flip();
        try {
            fileChannel.truncate(0L);
            fileChannel.write(byteBuffer);
            fileChannel.force(true);
            if (fileChannel.size() == 4L) return bl;
            this.zzawy().zzazd().zzj("Error writing to channel. Bytes written", fileChannel.size());
            return true;
        }
        catch (IOException iOException) {
            this.zzawy().zzazd().zzj("Failed to write to channel", iOException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean zza(zzcmb arrzzcmc, String string2, Object object) {
        if (!TextUtils.isEmpty((CharSequence)string2) && object != null) {
            for (zzcmc zzcmc2 : arrzzcmc.zzjlh) {
                if (!string2.equals(zzcmc2.name)) continue;
                if (!(object instanceof Long && object.equals(zzcmc2.zzjll) || object instanceof String && object.equals(zzcmc2.zzgcc)) && (!(object instanceof Double) || !object.equals(zzcmc2.zzjjl))) break;
                return true;
            }
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final boolean zza(String var1_1, zzcha var2_2) {
        var12_3 = var2_2.zzizt.getString("currency");
        if (!"ecommerce_purchase".equals(var2_2.name)) ** GOTO lbl11
        var3_5 = var5_4 = var2_2.zzizt.getDouble("value") * 1000000.0;
        if (var5_4 == 0.0) {
            var3_5 = (double)var2_2.zzizt.getLong("value").longValue() * 1000000.0;
        }
        if (var3_5 <= 9.223372036854776E18 && var3_5 >= -9.223372036854776E18) {
            var8_6 = Math.round(var3_5);
        } else {
            this.zzawy().zzazf().zze("Data lost. Currency value is too big. appId", zzchm.zzjk(var1_1), var3_5);
            return false;
lbl11:
            // 1 sources
            var8_6 = var2_2.zzizt.getLong("value");
        }
        if (TextUtils.isEmpty((CharSequence)var12_3) != false) return true;
        var13_7 = var12_3.toUpperCase(Locale.US);
        if (var13_7.matches("[A-Z]{3}") == false) return true;
        var12_3 = String.valueOf("_ltv_");
        var12_3 = (var13_7 = String.valueOf(var13_7)).length() != 0 ? var12_3.concat((String)var13_7) : new String(var12_3);
        var13_7 = this.zzaws().zzag(var1_1, var12_3);
        if (var13_7 == null || !(var13_7.mValue instanceof Long)) {
            var13_7 = this.zzaws();
            var7_8 = this.zzjew.zzb(var1_1, zzchc.zzjbh);
            zzbq.zzgm(var1_1);
            var13_7.zzve();
            var13_7.zzxf();
            try {
                var13_7.getWritableDatabase().execSQL("delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);", (Object[])new String[]{var1_1, var1_1, String.valueOf(var7_8 - 1)});
            }
            catch (SQLiteException var14_9) {
                var13_7.zzawy().zzazd().zze("Error pruning currencies. appId", zzchm.zzjk(var1_1), (Object)var14_9);
            }
            var2_2 = new zzclp(var1_1, var2_2.zziyf, var12_3, this.zzata.currentTimeMillis(), var8_6);
        } else {
            var10_10 = (Long)var13_7.mValue;
            var2_2 = new zzclp(var1_1, var2_2.zziyf, var12_3, this.zzata.currentTimeMillis(), var8_6 + var10_10);
        }
        if (this.zzaws().zza((zzclp)var2_2) != false) return true;
        this.zzawy().zzazd().zzd("Too many unique user properties are set. Ignoring user property. appId", zzchm.zzjk(var1_1), this.zzawt().zzjj(var2_2.mName), var2_2.mValue);
        this.zzawu().zza(var1_1, 9, null, null, 0);
        return true;
    }

    private final zzcma[] zza(String string2, zzcmg[] arrzzcmg, zzcmb[] arrzzcmb) {
        zzbq.zzgm(string2);
        return this.zzawl().zza(string2, arrzzcmb, arrzzcmg);
    }

    static void zzawi() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzazw() {
        zzcho zzcho2;
        ((zzcjk)this.zzawx()).zzve();
        this.zzjfe.zzazw();
        this.zzjex.zzazw();
        this.zzjfn.zzazw();
        this.zzawy().zzazh().zzj("App measurement is starting up, version", 11910L);
        this.zzawy().zzazh().log("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String string2 = this.zzjfn.getAppId();
        if (this.zzawu().zzkj(string2)) {
            zzcho2 = this.zzawy().zzazh();
            string2 = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
        } else {
            zzcho2 = this.zzawy().zzazh();
            string2 = (string2 = String.valueOf(string2)).length() != 0 ? "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ".concat(string2) : new String("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ");
        }
        zzcho2.log(string2);
        this.zzawy().zzazi().log("Debug-level message logging enabled");
        if (this.zzjfz != this.zzjga) {
            this.zzawy().zzazd().zze("Not all components initialized", this.zzjfz, this.zzjga);
        }
        this.zzdtb = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzb(zzcgh zzcgh2) {
        Object object;
        URL uRL;
        Object object2;
        block4: {
            block3: {
                ((zzcjk)this.zzawx()).zzve();
                if (TextUtils.isEmpty((CharSequence)zzcgh2.getGmpAppId())) {
                    this.zzb(zzcgh2.getAppId(), 204, null, null, null);
                    return;
                }
                object2 = zzcgh2.getGmpAppId();
                String string2 = zzcgh2.getAppInstanceId();
                Uri.Builder builder = new Uri.Builder();
                object = builder.scheme(zzchc.zzjah.get()).encodedAuthority(zzchc.zzjai.get());
                object2 = ((String)(object2 = String.valueOf(object2))).length() != 0 ? "config/app/".concat((String)object2) : new String("config/app/");
                object.path((String)object2).appendQueryParameter("app_instance_id", string2).appendQueryParameter("platform", "android").appendQueryParameter("gmp_version", "11910");
                string2 = builder.build().toString();
                try {
                    uRL = new URL(string2);
                    this.zzawy().zzazj().zzj("Fetching remote configuration", zzcgh2.getAppId());
                    object2 = this.zzawv().zzjs(zzcgh2.getAppId());
                    object = this.zzawv().zzjt(zzcgh2.getAppId());
                    if (object2 == null || TextUtils.isEmpty((CharSequence)object)) break block3;
                    object2 = new ArrayMap<String, Object>();
                    object2.put("If-Modified-Since", object);
                    break block4;
                }
                catch (MalformedURLException malformedURLException) {
                    this.zzawy().zzazd().zze("Failed to parse config URL. Not fetching. appId", zzchm.zzjk(zzcgh2.getAppId()), string2);
                    return;
                }
            }
            object2 = null;
        }
        this.zzjgd = true;
        object = this.zzbab();
        String string3 = zzcgh2.getAppId();
        zzciq zzciq2 = new zzciq(this);
        ((zzcjk)object).zzve();
        ((zzcjl)object).zzxf();
        zzbq.checkNotNull(uRL);
        zzbq.checkNotNull(zzciq2);
        ((zzcjk)object).zzawx().zzh(new zzchu((zzchq)object, string3, uRL, null, object2, (zzchs)zzciq2));
    }

    private final zzchv zzbac() {
        if (this.zzjfo == null) {
            throw new IllegalStateException("Network broadcast receiver not created");
        }
        return this.zzjfo;
    }

    private final zzcll zzbad() {
        zzcim.zza(this.zzjfp);
        return this.zzjfp;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean zzbae() {
        block5: {
            ((zzcjk)this.zzawx()).zzve();
            File file = new File(this.mContext.getFilesDir(), "google_app_measurement.db");
            this.zzjfw = new RandomAccessFile(file, "rw").getChannel();
            this.zzjfv = this.zzjfw.tryLock();
            if (this.zzjfv == null) break block5;
            this.zzawy().zzazj().log("Storage concurrent access okay");
            return true;
        }
        try {
            this.zzawy().zzazd().log("Storage concurrent data access panic");
            do {
                return false;
                break;
            } while (true);
        }
        catch (FileNotFoundException fileNotFoundException) {
            this.zzawy().zzazd().zzj("Failed to acquire storage lock", fileNotFoundException);
            return false;
        }
        catch (IOException iOException) {
            this.zzawy().zzazd().zzj("Failed to access storage lock file", iOException);
            return false;
        }
    }

    private final long zzbag() {
        long l;
        long l2 = this.zzata.currentTimeMillis();
        zzchx zzchx2 = this.zzawz();
        zzchx2.zzxf();
        zzchx2.zzve();
        long l3 = l = zzchx2.zzjcv.get();
        if (l == 0L) {
            l3 = 1L + (long)zzchx2.zzawu().zzbaz().nextInt(86400000);
            zzchx2.zzjcv.set(l3);
        }
        return (l3 + l2) / 1000L / 60L / 60L / 24L;
    }

    private final boolean zzbai() {
        ((zzcjk)this.zzawx()).zzve();
        this.zzxf();
        return this.zzaws().zzayk() || !TextUtils.isEmpty((CharSequence)this.zzaws().zzayf());
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzbaj() {
        long l;
        long l2;
        long l3;
        block17: {
            String string2;
            ((zzcjk)this.zzawx()).zzve();
            this.zzxf();
            if (!this.zzbam()) {
                return;
            }
            if (this.zzjgc > 0L) {
                l2 = 3600000L - Math.abs(this.zzata.elapsedRealtime() - this.zzjgc);
                if (l2 > 0L) {
                    this.zzawy().zzazj().zzj("Upload has been suspended. Will update scheduling later in approximately ms", l2);
                    this.zzbac().unregister();
                    this.zzbad().cancel();
                    return;
                }
                this.zzjgc = 0L;
            }
            if (!this.zzazv() || !this.zzbai()) {
                this.zzawy().zzazj().log("Nothing to upload or uploading impossible");
                this.zzbac().unregister();
                this.zzbad().cancel();
                return;
            }
            l = this.zzata.currentTimeMillis();
            l3 = Math.max(0L, zzchc.zzjbd.get());
            int n = this.zzaws().zzayl() || this.zzaws().zzayg() ? 1 : 0;
            l2 = n != 0 ? (!TextUtils.isEmpty((CharSequence)(string2 = this.zzjew.zzayd())) && !".none.".equals(string2) ? Math.max(0L, zzchc.zzjay.get()) : Math.max(0L, zzchc.zzjax.get())) : Math.max(0L, zzchc.zzjaw.get());
            long l4 = this.zzawz().zzjcr.get();
            long l5 = this.zzawz().zzjcs.get();
            long l6 = Math.max(this.zzaws().zzayi(), this.zzaws().zzayj());
            if (l6 == 0L) {
                l2 = 0L;
            } else {
                l6 = l - Math.abs(l6 - l);
                l4 = Math.abs(l4 - l);
                l5 = l - Math.abs(l5 - l);
                l4 = Math.max(l - l4, l5);
                l3 = l = l6 + l3;
                if (n != 0) {
                    l3 = l;
                    if (l4 > 0L) {
                        l3 = Math.min(l6, l4) + l2;
                    }
                }
                if (!this.zzawu().zzf(l4, l2)) {
                    l3 = l4 + l2;
                }
                l2 = l3;
                if (l5 != 0L) {
                    l2 = l3;
                    if (l5 >= l6) {
                        for (n = 0; n < Math.min(20, Math.max(0, zzchc.zzjbf.get())); ++n) {
                            l2 = l3 += (1L << n) * Math.max(0L, zzchc.zzjbe.get());
                            if (l3 <= l5) {
                                continue;
                            }
                            break block17;
                        }
                        l2 = 0L;
                    }
                }
            }
        }
        if (l2 == 0L) {
            this.zzawy().zzazj().log("Next upload time is 0");
            this.zzbac().unregister();
            this.zzbad().cancel();
            return;
        }
        if (!this.zzbab().zzzs()) {
            this.zzawy().zzazj().log("No network");
            this.zzbac().zzzp();
            this.zzbad().cancel();
            return;
        }
        l3 = this.zzawz().zzjct.get();
        l = Math.max(0L, zzchc.zzjau.get());
        if (!this.zzawu().zzf(l3, l)) {
            l2 = Math.max(l2, l + l3);
        }
        this.zzbac().unregister();
        l2 = l3 = l2 - this.zzata.currentTimeMillis();
        if (l3 <= 0L) {
            l2 = Math.max(0L, zzchc.zzjaz.get());
            this.zzawz().zzjcr.set(this.zzata.currentTimeMillis());
        }
        this.zzawy().zzazj().zzj("Upload scheduled in approximately ms", l2);
        this.zzbad().zzs(l2);
    }

    private final boolean zzbam() {
        ((zzcjk)this.zzawx()).zzve();
        this.zzxf();
        return this.zzjfs;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzban() {
        ((zzcjk)this.zzawx()).zzve();
        if (this.zzjgd || this.zzjge || this.zzjgf) {
            this.zzawy().zzazj().zzd("Not stopping services. fetch, network, upload", this.zzjgd, this.zzjge, this.zzjgf);
            return;
        } else {
            this.zzawy().zzazj().log("Stopping uploading service(s)");
            if (this.zzjfy == null) return;
            {
                Iterator<Runnable> iterator = this.zzjfy.iterator();
                do {
                    if (!iterator.hasNext()) {
                        this.zzjfy.clear();
                        return;
                    }
                    iterator.next().run();
                } while (true);
            }
        }
    }

    /*
     * Exception decompiling
     */
    private final void zzc(zzcha var1_1, zzcgi var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[TRYBLOCK]], but top level block is 8[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzcim zzdx(Context context) {
        zzbq.checkNotNull(context);
        zzbq.checkNotNull(context.getApplicationContext());
        if (zzjev == null) {
            synchronized (zzcim.class) {
                if (zzjev == null) {
                    zzjev = new zzcim(new zzcjm(context));
                }
            }
        }
        return zzjev;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzg(zzcgi zzcgi2) {
        zzcgh zzcgh2;
        boolean bl = true;
        ((zzcjk)this.zzawx()).zzve();
        this.zzxf();
        zzbq.checkNotNull(zzcgi2);
        zzbq.zzgm(zzcgi2.packageName);
        zzcgh zzcgh3 = this.zzaws().zzjb(zzcgi2.packageName);
        String string2 = this.zzawz().zzjn(zzcgi2.packageName);
        boolean bl2 = false;
        if (zzcgh3 == null) {
            zzcgh2 = new zzcgh(this, zzcgi2.packageName);
            zzcgh2.zzir(this.zzawn().zzayz());
            zzcgh2.zzit(string2);
            bl2 = true;
        } else {
            zzcgh2 = zzcgh3;
            if (!string2.equals(zzcgh3.zzaxc())) {
                zzcgh3.zzit(string2);
                zzcgh3.zzir(this.zzawn().zzayz());
                bl2 = true;
                zzcgh2 = zzcgh3;
            }
        }
        boolean bl3 = bl2;
        if (!TextUtils.isEmpty((CharSequence)zzcgi2.zzixs)) {
            bl3 = bl2;
            if (!zzcgi2.zzixs.equals(zzcgh2.getGmpAppId())) {
                zzcgh2.zzis(zzcgi2.zzixs);
                bl3 = true;
            }
        }
        bl2 = bl3;
        if (!TextUtils.isEmpty((CharSequence)zzcgi2.zziya)) {
            bl2 = bl3;
            if (!zzcgi2.zziya.equals(zzcgh2.zzaxd())) {
                zzcgh2.zziu(zzcgi2.zziya);
                bl2 = true;
            }
        }
        bl3 = bl2;
        if (zzcgi2.zzixu != 0L) {
            bl3 = bl2;
            if (zzcgi2.zzixu != zzcgh2.zzaxi()) {
                zzcgh2.zzao(zzcgi2.zzixu);
                bl3 = true;
            }
        }
        bl2 = bl3;
        if (!TextUtils.isEmpty((CharSequence)zzcgi2.zzifm)) {
            bl2 = bl3;
            if (!zzcgi2.zzifm.equals(zzcgh2.zzvj())) {
                zzcgh2.setAppVersion(zzcgi2.zzifm);
                bl2 = true;
            }
        }
        if (zzcgi2.zzixz != zzcgh2.zzaxg()) {
            zzcgh2.zzan(zzcgi2.zzixz);
            bl2 = true;
        }
        bl3 = bl2;
        if (zzcgi2.zzixt != null) {
            bl3 = bl2;
            if (!zzcgi2.zzixt.equals(zzcgh2.zzaxh())) {
                zzcgh2.zziv(zzcgi2.zzixt);
                bl3 = true;
            }
        }
        bl2 = bl3;
        if (zzcgi2.zzixv != zzcgh2.zzaxj()) {
            zzcgh2.zzap(zzcgi2.zzixv);
            bl2 = true;
        }
        if (zzcgi2.zzixx != zzcgh2.zzaxk()) {
            zzcgh2.setMeasurementEnabled(zzcgi2.zzixx);
            bl2 = true;
        }
        bl3 = bl2;
        if (!TextUtils.isEmpty((CharSequence)zzcgi2.zzixw)) {
            bl3 = bl2;
            if (!zzcgi2.zzixw.equals(zzcgh2.zzaxv())) {
                zzcgh2.zziw(zzcgi2.zzixw);
                bl3 = true;
            }
        }
        if (zzcgi2.zziyb != zzcgh2.zzaxx()) {
            zzcgh2.zzaz(zzcgi2.zziyb);
            bl3 = true;
        }
        if (zzcgi2.zziye != zzcgh2.zzaxy()) {
            zzcgh2.zzbl(zzcgi2.zziye);
            bl2 = bl;
        } else {
            bl2 = bl3;
        }
        if (bl2) {
            this.zzaws().zza(zzcgh2);
        }
    }

    /*
     * Exception decompiling
     */
    private final boolean zzg(String var1_1, long var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[TRYBLOCK]], but top level block is 26[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private final zzcgi zzjw(String string2) {
        zzcgh zzcgh2 = this.zzaws().zzjb(string2);
        if (zzcgh2 == null || TextUtils.isEmpty((CharSequence)zzcgh2.zzvj())) {
            this.zzawy().zzazi().zzj("No app data available; dropping", string2);
            return null;
        }
        try {
            String string3 = zzbhf.zzdb((Context)this.mContext).getPackageInfo((String)string2, (int)0).versionName;
            if (zzcgh2.zzvj() != null && !zzcgh2.zzvj().equals(string3)) {
                this.zzawy().zzazf().zzj("App version does not match; dropping. appId", zzchm.zzjk(string2));
                return null;
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            // empty catch block
        }
        return new zzcgi(string2, zzcgh2.getGmpAppId(), zzcgh2.zzvj(), zzcgh2.zzaxg(), zzcgh2.zzaxh(), zzcgh2.zzaxi(), zzcgh2.zzaxj(), null, zzcgh2.zzaxk(), false, zzcgh2.zzaxd(), zzcgh2.zzaxx(), 0L, 0, zzcgh2.zzaxy());
    }

    public final Context getContext() {
        return this.mContext;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean isEnabled() {
        boolean bl = false;
        ((zzcjk)this.zzawx()).zzve();
        this.zzxf();
        if (this.zzjew.zzaya()) {
            return false;
        }
        Boolean bl2 = this.zzjew.zziy("firebase_analytics_collection_enabled");
        if (bl2 != null) {
            bl = bl2;
            return this.zzawz().zzbn(bl);
        }
        if (zzbz.zzaji()) return this.zzawz().zzbn(bl);
        bl = true;
        return this.zzawz().zzbn(bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void start() {
        ((zzcjk)this.zzawx()).zzve();
        this.zzaws().zzayh();
        if (this.zzawz().zzjcr.get() == 0L) {
            this.zzawz().zzjcr.set(this.zzata.currentTimeMillis());
        }
        if (Long.valueOf(this.zzawz().zzjcw.get()) == 0L) {
            this.zzawy().zzazj().zzj("Persisting first open", this.zzjgg);
            this.zzawz().zzjcw.set(this.zzjgg);
        }
        if (!this.zzazv()) {
            if (this.isEnabled()) {
                if (!this.zzawu().zzeb("android.permission.INTERNET")) {
                    this.zzawy().zzazd().log("App is missing INTERNET permission");
                }
                if (!this.zzawu().zzeb("android.permission.ACCESS_NETWORK_STATE")) {
                    this.zzawy().zzazd().log("App is missing ACCESS_NETWORK_STATE permission");
                }
                if (!zzbhf.zzdb(this.mContext).zzamu()) {
                    if (!zzcid.zzbk(this.mContext)) {
                        this.zzawy().zzazd().log("AppMeasurementReceiver not registered/enabled");
                    }
                    if (!zzcla.zzk(this.mContext, false)) {
                        this.zzawy().zzazd().log("AppMeasurementService not registered/enabled");
                    }
                }
                this.zzawy().zzazd().log("Uploading is not possible. App measurement disabled");
            }
        } else {
            Object object;
            if (!TextUtils.isEmpty((CharSequence)this.zzawn().getGmpAppId())) {
                object = this.zzawz().zzazm();
                if (object == null) {
                    this.zzawz().zzjo(this.zzawn().getGmpAppId());
                } else if (!((String)object).equals(this.zzawn().getGmpAppId())) {
                    this.zzawy().zzazh().log("Rechecking which service to use due to a GMP App Id change");
                    this.zzawz().zzazp();
                    this.zzjfk.disconnect();
                    this.zzjfk.zzyc();
                    this.zzawz().zzjo(this.zzawn().getGmpAppId());
                    this.zzawz().zzjcw.set(this.zzjgg);
                    this.zzawz().zzjcx.zzjq(null);
                }
            }
            this.zzawm().zzjp(this.zzawz().zzjcx.zzazr());
            if (!TextUtils.isEmpty((CharSequence)this.zzawn().getGmpAppId())) {
                object = this.zzawm();
                ((zzcjk)object).zzve();
                ((zzcjl)object).zzxf();
                if (((zzcjn)object).zziwf.zzazv()) {
                    ((zzcjk)object).zzawp().zzbar();
                    String string2 = ((zzcjk)object).zzawz().zzazq();
                    if (!TextUtils.isEmpty((CharSequence)string2)) {
                        ((zzcjk)object).zzawo().zzxf();
                        if (!string2.equals(Build.VERSION.RELEASE)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("_po", string2);
                            ((zzcjn)object).zzc("auto", "_ou", bundle);
                        }
                    }
                }
                this.zzawp().zza(new AtomicReference<String>());
            }
        }
        this.zzbaj();
    }

    /*
     * Exception decompiling
     */
    protected final void zza(int var1_1, Throwable var2_2, byte[] var3_6) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 8[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final byte[] zza(zzcha object, String string2) {
        Object object2;
        zzcme zzcme2;
        zzcgh zzcgh2;
        long l;
        zzcmd zzcmd2;
        block18: {
            block17: {
                this.zzxf();
                ((zzcjk)this.zzawx()).zzve();
                zzcim.zzawi();
                zzbq.checkNotNull(object);
                zzbq.zzgm(string2);
                zzcmd2 = new zzcmd();
                this.zzaws().beginTransaction();
                zzcgh2 = this.zzaws().zzjb(string2);
                if (zzcgh2 != null) break block17;
                this.zzawy().zzazi().zzj("Log and bundle not available. package_name", string2);
                this.zzaws().endTransaction();
                return new byte[0];
            }
            if (zzcgh2.zzaxk()) break block18;
            this.zzawy().zzazi().zzj("Log and bundle disabled. package_name", string2);
            this.zzaws().endTransaction();
            return new byte[0];
        }
        try {
            Iterator<String> iterator;
            int n;
            if (("_iap".equals(object.name) || "ecommerce_purchase".equals(object.name)) && !this.zza(string2, (zzcha)object)) {
                this.zzawy().zzazf().zzj("Failed to handle purchase event at single event bundle creation. appId", zzchm.zzjk(string2));
            }
            zzcme2 = new zzcme();
            zzcmd2.zzjlm = new zzcme[]{zzcme2};
            zzcme2.zzjlo = 1;
            zzcme2.zzjlw = "android";
            zzcme2.zzcn = zzcgh2.getAppId();
            zzcme2.zzixt = zzcgh2.zzaxh();
            zzcme2.zzifm = zzcgh2.zzvj();
            l = zzcgh2.zzaxg();
            object2 = l == Integer.MIN_VALUE ? null : Integer.valueOf((int)l);
            zzcme2.zzjmj = object2;
            zzcme2.zzjma = zzcgh2.zzaxi();
            zzcme2.zzixs = zzcgh2.getGmpAppId();
            zzcme2.zzjmf = zzcgh2.zzaxj();
            if (this.isEnabled() && zzcgn.zzaye() && this.zzjew.zziz(zzcme2.zzcn)) {
                this.zzawn();
                zzcme2.zzjmo = null;
            }
            object2 = this.zzawz().zzjm(zzcgh2.getAppId());
            if (zzcgh2.zzaxy() && object2 != null && !TextUtils.isEmpty((CharSequence)((CharSequence)((Pair)object2).first))) {
                zzcme2.zzjmc = (String)((Pair)object2).first;
                zzcme2.zzjmd = (Boolean)((Pair)object2).second;
            }
            this.zzawo().zzxf();
            zzcme2.zzjlx = Build.MODEL;
            this.zzawo().zzxf();
            zzcme2.zzdb = Build.VERSION.RELEASE;
            zzcme2.zzjlz = (int)this.zzawo().zzayu();
            zzcme2.zzjly = this.zzawo().zzayv();
            zzcme2.zzjme = zzcgh2.getAppInstanceId();
            zzcme2.zziya = zzcgh2.zzaxd();
            object2 = this.zzaws().zzja(zzcgh2.getAppId());
            zzcme2.zzjlq = new zzcmg[object2.size()];
            for (n = 0; n < object2.size(); ++n) {
                iterator = new zzcmg();
                zzcme2.zzjlq[n] = iterator;
                ((zzcmg)iterator).name = ((zzclp)object2.get((int)n)).mName;
                ((zzcmg)iterator).zzjms = ((zzclp)object2.get((int)n)).zzjjm;
                this.zzawu().zza((zzcmg)((Object)iterator), ((zzclp)object2.get((int)n)).mValue);
            }
            object2 = object.zzizt.zzayx();
            if ("_iap".equals(object.name)) {
                object2.putLong("_c", 1L);
                this.zzawy().zzazi().log("Marking in-app purchase as real-time");
                object2.putLong("_r", 1L);
            }
            object2.putString("_o", object.zziyf);
            if (this.zzawu().zzkj(zzcme2.zzcn)) {
                this.zzawu().zza((Bundle)object2, "_dbg", 1L);
                this.zzawu().zza((Bundle)object2, "_r", 1L);
            }
            if ((iterator = this.zzaws().zzae(string2, object.name)) == null) {
                iterator = new zzcgw(string2, object.name, 1L, 0L, object.zzizu, 0L, null, null, null);
                this.zzaws().zza((zzcgw)((Object)iterator));
                l = 0L;
            } else {
                l = ((zzcgw)iterator).zzizm;
                iterator = ((zzcgw)((Object)iterator)).zzbb(object.zzizu).zzayw();
                this.zzaws().zza((zzcgw)((Object)iterator));
            }
            object = new zzcgv(this, object.zziyf, string2, object.name, object.zzizu, l, (Bundle)object2);
            object2 = new zzcmb();
            zzcme2.zzjlp = new zzcmb[]{object2};
            ((zzcmb)object2).zzjli = object.zzfij;
            ((zzcmb)object2).name = object.mName;
            ((zzcmb)object2).zzjlj = object.zzizi;
            ((zzcmb)object2).zzjlh = new zzcmc[object.zzizj.size()];
            iterator = object.zzizj.iterator();
            n = 0;
            while (iterator.hasNext()) {
                zzcmc zzcmc2;
                Object object3 = iterator.next();
                object2.zzjlh[n] = zzcmc2 = new zzcmc();
                zzcmc2.name = object3;
                object3 = object.zzizj.get((String)object3);
                this.zzawu().zza(zzcmc2, object3);
                ++n;
            }
        }
        catch (Throwable throwable) {
            this.zzaws().endTransaction();
            throw throwable;
        }
        zzcme2.zzjmi = this.zza(zzcgh2.getAppId(), zzcme2.zzjlq, zzcme2.zzjlp);
        zzcme2.zzjls = ((zzcmb)object2).zzjli;
        zzcme2.zzjlt = ((zzcmb)object2).zzjli;
        l = zzcgh2.zzaxf();
        object = l != 0L ? Long.valueOf(l) : null;
        zzcme2.zzjlv = object;
        long l2 = zzcgh2.zzaxe();
        if (l2 != 0L) {
            l = l2;
        }
        object = l != 0L ? Long.valueOf(l) : null;
        zzcme2.zzjlu = object;
        zzcgh2.zzaxo();
        zzcme2.zzjmg = (int)zzcgh2.zzaxl();
        zzcme2.zzjmb = 11910L;
        zzcme2.zzjlr = this.zzata.currentTimeMillis();
        zzcme2.zzjmh = Boolean.TRUE;
        zzcgh2.zzal(zzcme2.zzjls);
        zzcgh2.zzam(zzcme2.zzjlt);
        this.zzaws().zza(zzcgh2);
        this.zzaws().setTransactionSuccessful();
        this.zzaws().endTransaction();
        try {
            object = new byte[zzcmd2.zzho()];
            object2 = zzfjk.zzo(object, 0, ((byte[])object).length);
            ((zzfjs)zzcmd2).zza((zzfjk)object2);
            ((zzfjk)object2).zzcwt();
            return this.zzawu().zzq((byte[])object);
        }
        catch (IOException iOException) {
            this.zzawy().zzazd().zze("Data loss. Failed to bundle and serialize. appId", zzchm.zzjk(string2), iOException);
            return null;
        }
    }

    public final zzcgd zzawk() {
        zzcim.zza(this.zzjfr);
        return this.zzjfr;
    }

    public final zzcgk zzawl() {
        zzcim.zza(this.zzjfq);
        return this.zzjfq;
    }

    public final zzcjn zzawm() {
        zzcim.zza(this.zzjfm);
        return this.zzjfm;
    }

    public final zzchh zzawn() {
        zzcim.zza(this.zzjfn);
        return this.zzjfn;
    }

    public final zzcgu zzawo() {
        zzcim.zza(this.zzjfl);
        return this.zzjfl;
    }

    public final zzckg zzawp() {
        zzcim.zza(this.zzjfk);
        return this.zzjfk;
    }

    public final zzckc zzawq() {
        zzcim.zza(this.zzjfj);
        return this.zzjfj;
    }

    public final zzchi zzawr() {
        zzcim.zza(this.zzjfh);
        return this.zzjfh;
    }

    public final zzcgo zzaws() {
        zzcim.zza(this.zzjfg);
        return this.zzjfg;
    }

    public final zzchk zzawt() {
        zzcim.zza((zzcjk)this.zzjff);
        return this.zzjff;
    }

    public final zzclq zzawu() {
        zzcim.zza((zzcjk)this.zzjfe);
        return this.zzjfe;
    }

    public final zzcig zzawv() {
        zzcim.zza(this.zzjfb);
        return this.zzjfb;
    }

    public final zzclf zzaww() {
        zzcim.zza(this.zzjfa);
        return this.zzjfa;
    }

    public final zzcih zzawx() {
        zzcim.zza(this.zzjez);
        return this.zzjez;
    }

    public final zzchm zzawy() {
        zzcim.zza(this.zzjey);
        return this.zzjey;
    }

    public final zzchx zzawz() {
        zzcim.zza((zzcjk)this.zzjex);
        return this.zzjex;
    }

    public final zzcgn zzaxa() {
        return this.zzjew;
    }

    protected final boolean zzazv() {
        block4: {
            boolean bl;
            block5: {
                block6: {
                    boolean bl2 = false;
                    this.zzxf();
                    ((zzcjk)this.zzawx()).zzve();
                    if (this.zzjft != null && this.zzjfu != 0L && (this.zzjft == null || this.zzjft.booleanValue() || Math.abs(this.zzata.elapsedRealtime() - this.zzjfu) <= 1000L)) break block4;
                    this.zzjfu = this.zzata.elapsedRealtime();
                    bl = bl2;
                    if (!this.zzawu().zzeb("android.permission.INTERNET")) break block5;
                    bl = bl2;
                    if (!this.zzawu().zzeb("android.permission.ACCESS_NETWORK_STATE")) break block5;
                    if (zzbhf.zzdb(this.mContext).zzamu()) break block6;
                    bl = bl2;
                    if (!zzcid.zzbk(this.mContext)) break block5;
                    bl = bl2;
                    if (!zzcla.zzk(this.mContext, false)) break block5;
                }
                bl = true;
            }
            this.zzjft = bl;
            if (this.zzjft.booleanValue()) {
                this.zzjft = this.zzawu().zzkg(this.zzawn().getGmpAppId());
            }
        }
        return this.zzjft;
    }

    public final zzchm zzazx() {
        if (this.zzjey != null && this.zzjey.isInitialized()) {
            return this.zzjey;
        }
        return null;
    }

    final zzcih zzazy() {
        return this.zzjez;
    }

    public final AppMeasurement zzazz() {
        return this.zzjfc;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zzb(zzcgl zzcgl2, zzcgi zzcgi2) {
        block16: {
            block15: {
                boolean bl = true;
                zzbq.checkNotNull(zzcgl2);
                zzbq.zzgm(zzcgl2.packageName);
                zzbq.checkNotNull(zzcgl2.zziyf);
                zzbq.checkNotNull(zzcgl2.zziyg);
                zzbq.zzgm(zzcgl2.zziyg.name);
                ((zzcjk)this.zzawx()).zzve();
                this.zzxf();
                if (TextUtils.isEmpty((CharSequence)zzcgi2.zzixs)) {
                    return;
                }
                if (!zzcgi2.zzixx) {
                    this.zzg(zzcgi2);
                    return;
                }
                zzcgl2 = new zzcgl(zzcgl2);
                zzcgl2.zziyi = false;
                this.zzaws().beginTransaction();
                try {
                    Object object = this.zzaws().zzah(zzcgl2.packageName, zzcgl2.zziyg.name);
                    if (object != null && !((zzcgl)object).zziyf.equals(zzcgl2.zziyf)) {
                        this.zzawy().zzazf().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzawt().zzjj(zzcgl2.zziyg.name), zzcgl2.zziyf, ((zzcgl)object).zziyf);
                    }
                    if (object != null && ((zzcgl)object).zziyi) {
                        zzcgl2.zziyf = ((zzcgl)object).zziyf;
                        zzcgl2.zziyh = ((zzcgl)object).zziyh;
                        zzcgl2.zziyl = ((zzcgl)object).zziyl;
                        zzcgl2.zziyj = ((zzcgl)object).zziyj;
                        zzcgl2.zziym = ((zzcgl)object).zziym;
                        zzcgl2.zziyi = ((zzcgl)object).zziyi;
                        zzcgl2.zziyg = new zzcln(zzcgl2.zziyg.name, object.zziyg.zzjji, zzcgl2.zziyg.getValue(), object.zziyg.zziyf);
                        bl = false;
                    } else if (TextUtils.isEmpty((CharSequence)zzcgl2.zziyj)) {
                        zzcgl2.zziyg = new zzcln(zzcgl2.zziyg.name, zzcgl2.zziyh, zzcgl2.zziyg.getValue(), zzcgl2.zziyg.zziyf);
                        zzcgl2.zziyi = true;
                    } else {
                        bl = false;
                    }
                    if (zzcgl2.zziyi) {
                        object = zzcgl2.zziyg;
                        object = new zzclp(zzcgl2.packageName, zzcgl2.zziyf, ((zzcln)object).name, ((zzcln)object).zzjji, ((zzcln)object).getValue());
                        if (this.zzaws().zza((zzclp)object)) {
                            this.zzawy().zzazi().zzd("User property updated immediately", zzcgl2.packageName, this.zzawt().zzjj(((zzclp)object).mName), ((zzclp)object).mValue);
                        } else {
                            this.zzawy().zzazd().zzd("(2)Too many active user properties, ignoring", zzchm.zzjk(zzcgl2.packageName), this.zzawt().zzjj(((zzclp)object).mName), ((zzclp)object).mValue);
                        }
                        if (bl && zzcgl2.zziym != null) {
                            this.zzc(new zzcha(zzcgl2.zziym, zzcgl2.zziyh), zzcgi2);
                        }
                    }
                    if (!this.zzaws().zza(zzcgl2)) break block15;
                    this.zzawy().zzazi().zzd("Conditional property added", zzcgl2.packageName, this.zzawt().zzjj(zzcgl2.zziyg.name), zzcgl2.zziyg.getValue());
                    break block16;
                }
                catch (Throwable throwable) {
                    this.zzaws().endTransaction();
                    throw throwable;
                }
            }
            this.zzawy().zzazd().zzd("Too many conditional properties, ignoring", zzchm.zzjk(zzcgl2.packageName), this.zzawt().zzjj(zzcgl2.zziyg.name), zzcgl2.zziyg.getValue());
        }
        this.zzaws().setTransactionSuccessful();
        this.zzaws().endTransaction();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zzb(zzcha object, zzcgi zzcgi2) {
        Object object2;
        long l;
        int n;
        Object object3;
        Object object4;
        Object object5;
        int n2;
        block22: {
            zzbq.checkNotNull(zzcgi2);
            zzbq.zzgm(zzcgi2.packageName);
            ((zzcjk)this.zzawx()).zzve();
            this.zzxf();
            object4 = zzcgi2.packageName;
            l = ((zzcha)object).zzizu;
            this.zzawu();
            if (!zzclq.zzd((zzcha)object, zzcgi2)) {
                return;
            }
            if (!zzcgi2.zzixx) {
                this.zzg(zzcgi2);
                return;
            }
            this.zzaws().beginTransaction();
            object3 = this.zzaws();
            zzbq.zzgm((String)object4);
            ((zzcjk)object3).zzve();
            ((zzcjl)object3).zzxf();
            if (l < 0L) {
                ((zzcjk)object3).zzawy().zzazf().zze("Invalid time querying timed out conditional properties", zzchm.zzjk((String)object4), l);
                object3 = Collections.emptyList();
            } else {
                object3 = ((zzcgo)object3).zzc("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{object4, String.valueOf(l)});
            }
            object3 = object3.iterator();
            while (object3.hasNext()) {
                object5 = (zzcgl)object3.next();
                if (object5 == null) continue;
                this.zzawy().zzazi().zzd("User property timed out", ((zzcgl)object5).packageName, this.zzawt().zzjj(object5.zziyg.name), ((zzcgl)object5).zziyg.getValue());
                if (((zzcgl)object5).zziyk != null) {
                    this.zzc(new zzcha(((zzcgl)object5).zziyk, l), zzcgi2);
                }
                this.zzaws().zzai((String)object4, object5.zziyg.name);
            }
            object3 = this.zzaws();
            zzbq.zzgm((String)object4);
            ((zzcjk)object3).zzve();
            ((zzcjl)object3).zzxf();
            if (l < 0L) {
                ((zzcjk)object3).zzawy().zzazf().zze("Invalid time querying expired conditional properties", zzchm.zzjk((String)object4), l);
                object3 = Collections.emptyList();
            } else {
                object3 = ((zzcgo)object3).zzc("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{object4, String.valueOf(l)});
            }
            object5 = new ArrayList(object3.size());
            object3 = object3.iterator();
            while (object3.hasNext()) {
                object2 = (zzcgl)object3.next();
                if (object2 == null) continue;
                this.zzawy().zzazi().zzd("User property expired", ((zzcgl)object2).packageName, this.zzawt().zzjj(object2.zziyg.name), ((zzcgl)object2).zziyg.getValue());
                this.zzaws().zzaf((String)object4, object2.zziyg.name);
                if (((zzcgl)object2).zziyo != null) {
                    object5.add(((zzcgl)object2).zziyo);
                }
                this.zzaws().zzai((String)object4, object2.zziyg.name);
            }
            object3 = (ArrayList)object5;
            n2 = ((ArrayList)object3).size();
            for (n = 0; n < n2; ++n) {
                object5 = ((ArrayList)object3).get(n);
                this.zzc(new zzcha((zzcha)object5, l), zzcgi2);
            }
            object3 = this.zzaws();
            object5 = ((zzcha)object).name;
            zzbq.zzgm((String)object4);
            zzbq.zzgm((String)object5);
            ((zzcjk)object3).zzve();
            ((zzcjl)object3).zzxf();
            if (l < 0L) {
                ((zzcjk)object3).zzawy().zzazf().zzd("Invalid time querying triggered conditional properties", zzchm.zzjk((String)object4), ((zzcjk)object3).zzawt().zzjh((String)object5), l);
                object3 = Collections.emptyList();
            } else {
                object3 = ((zzcgo)object3).zzc("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{object4, object5, String.valueOf(l)});
            }
            object4 = new ArrayList(object3.size());
            object3 = object3.iterator();
            break block22;
            finally {
                this.zzaws().endTransaction();
            }
        }
        while (object3.hasNext()) {
            object5 = (zzcgl)object3.next();
            if (object5 == null) continue;
            object2 = ((zzcgl)object5).zziyg;
            object2 = new zzclp(((zzcgl)object5).packageName, ((zzcgl)object5).zziyf, ((zzcln)object2).name, l, ((zzcln)object2).getValue());
            if (this.zzaws().zza((zzclp)object2)) {
                this.zzawy().zzazi().zzd("User property triggered", ((zzcgl)object5).packageName, this.zzawt().zzjj(((zzclp)object2).mName), ((zzclp)object2).mValue);
            } else {
                this.zzawy().zzazd().zzd("Too many active user properties, ignoring", zzchm.zzjk(((zzcgl)object5).packageName), this.zzawt().zzjj(((zzclp)object2).mName), ((zzclp)object2).mValue);
            }
            if (((zzcgl)object5).zziym != null) {
                object4.add(((zzcgl)object5).zziym);
            }
            ((zzcgl)object5).zziyg = new zzcln((zzclp)object2);
            ((zzcgl)object5).zziyi = true;
            this.zzaws().zza((zzcgl)object5);
        }
        this.zzc((zzcha)object, zzcgi2);
        object = (ArrayList)object4;
        n2 = ((ArrayList)object).size();
        for (n = 0; n < n2; ++n) {
            object3 = ((ArrayList)object).get(n);
            this.zzc(new zzcha((zzcha)object3, l), zzcgi2);
        }
        this.zzaws().setTransactionSuccessful();
    }

    final void zzb(zzcha zzcha2, String string2) {
        zzcgh zzcgh2;
        block4: {
            zzcgh2 = this.zzaws().zzjb(string2);
            if (zzcgh2 == null || TextUtils.isEmpty((CharSequence)zzcgh2.zzvj())) {
                this.zzawy().zzazi().zzj("No app data available; dropping event", string2);
                return;
            }
            try {
                String string3 = zzbhf.zzdb((Context)this.mContext).getPackageInfo((String)string2, (int)0).versionName;
                if (zzcgh2.zzvj() != null && !zzcgh2.zzvj().equals(string3)) {
                    this.zzawy().zzazf().zzj("App version does not match; dropping event. appId", zzchm.zzjk(string2));
                    return;
                }
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                if ("_ui".equals(zzcha2.name)) break block4;
                this.zzawy().zzazf().zzj("Could not find package. appId", zzchm.zzjk(string2));
            }
        }
        this.zzb(zzcha2, new zzcgi(string2, zzcgh2.getGmpAppId(), zzcgh2.zzvj(), zzcgh2.zzaxg(), zzcgh2.zzaxh(), zzcgh2.zzaxi(), zzcgh2.zzaxj(), null, zzcgh2.zzaxk(), false, zzcgh2.zzaxd(), zzcgh2.zzaxx(), 0L, 0, zzcgh2.zzaxy()));
    }

    final void zzb(zzcjl zzcjl2) {
        ++this.zzjfz;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zzb(zzcln object, zzcgi zzcgi2) {
        block12: {
            int n;
            String string2;
            int n2;
            block13: {
                block14: {
                    int n3 = 0;
                    n2 = 0;
                    ((zzcjk)this.zzawx()).zzve();
                    this.zzxf();
                    if (TextUtils.isEmpty((CharSequence)zzcgi2.zzixs)) {
                        return;
                    }
                    if (!zzcgi2.zzixx) {
                        this.zzg(zzcgi2);
                        return;
                    }
                    n = this.zzawu().zzkd(((zzcln)object).name);
                    if (n != 0) {
                        this.zzawu();
                        String string3 = zzclq.zza(((zzcln)object).name, 24, true);
                        if (((zzcln)object).name != null) {
                            n2 = ((zzcln)object).name.length();
                        }
                        this.zzawu().zza(zzcgi2.packageName, n, "_ev", string3, n2);
                        return;
                    }
                    n = this.zzawu().zzl(((zzcln)object).name, ((zzcln)object).getValue());
                    if (n == 0) break block12;
                    this.zzawu();
                    string2 = zzclq.zza(((zzcln)object).name, 24, true);
                    object = ((zzcln)object).getValue();
                    n2 = n3;
                    if (object == null) break block13;
                    if (object instanceof String) break block14;
                    n2 = n3;
                    if (!(object instanceof CharSequence)) break block13;
                }
                n2 = String.valueOf(object).length();
            }
            this.zzawu().zza(zzcgi2.packageName, n, "_ev", string2, n2);
            return;
        }
        Object object2 = this.zzawu().zzm(((zzcln)object).name, ((zzcln)object).getValue());
        if (object2 == null) return;
        object = new zzclp(zzcgi2.packageName, ((zzcln)object).zziyf, ((zzcln)object).name, ((zzcln)object).zzjji, object2);
        this.zzawy().zzazi().zze("Setting user property", this.zzawt().zzjj(((zzclp)object).mName), object2);
        this.zzaws().beginTransaction();
        try {
            this.zzg(zzcgi2);
            boolean bl = this.zzaws().zza((zzclp)object);
            this.zzaws().setTransactionSuccessful();
            if (bl) {
                this.zzawy().zzazi().zze("User property set", this.zzawt().zzjj(((zzclp)object).mName), ((zzclp)object).mValue);
                return;
            }
            this.zzawy().zzazd().zze("Too many unique user properties are set. Ignoring user property", this.zzawt().zzjj(((zzclp)object).mName), ((zzclp)object).mValue);
            this.zzawu().zza(zzcgi2.packageName, 9, null, null, 0);
            return;
        }
        finally {
            this.zzaws().endTransaction();
        }
    }

    /*
     * Exception decompiling
     */
    final void zzb(String var1_1, int var2_4, Throwable var3_5, byte[] var4_6, Map<String, List<String>> var5_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public final FirebaseAnalytics zzbaa() {
        return this.zzjfd;
    }

    public final zzchq zzbab() {
        zzcim.zza(this.zzjfi);
        return this.zzjfi;
    }

    final long zzbaf() {
        Long l = this.zzawz().zzjcw.get();
        if (l == 0L) {
            return this.zzjgg;
        }
        return Math.min(this.zzjgg, l);
    }

    /*
     * Exception decompiling
     */
    public final void zzbah() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[TRYBLOCK]], but top level block is 7[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    final void zzbak() {
        ++this.zzjga;
    }

    /*
     * Enabled aggressive block sorting
     */
    final void zzbal() {
        ((zzcjk)this.zzawx()).zzve();
        this.zzxf();
        if (!this.zzjfs) {
            this.zzawy().zzazh().log("This instance being marked as an uploader");
            ((zzcjk)this.zzawx()).zzve();
            this.zzxf();
            if (this.zzbam() && this.zzbae()) {
                int n = this.zza(this.zzjfw);
                int n2 = this.zzawn().zzaza();
                ((zzcjk)this.zzawx()).zzve();
                if (n > n2) {
                    this.zzawy().zzazd().zze("Panic: can't downgrade version. Previous, current version", n, n2);
                } else if (n < n2) {
                    if (this.zza(n2, this.zzjfw)) {
                        this.zzawy().zzazj().zze("Storage version upgraded. Previous, current version", n, n2);
                    } else {
                        this.zzawy().zzazd().zze("Storage version upgrade failed. Previous, current version", n, n2);
                    }
                }
            }
            this.zzjfs = true;
            this.zzbaj();
        }
    }

    public final void zzbo(boolean bl) {
        this.zzbaj();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zzc(zzcgl zzcgl2, zzcgi zzcgi2) {
        zzbq.checkNotNull(zzcgl2);
        zzbq.zzgm(zzcgl2.packageName);
        zzbq.checkNotNull(zzcgl2.zziyg);
        zzbq.zzgm(zzcgl2.zziyg.name);
        ((zzcjk)this.zzawx()).zzve();
        this.zzxf();
        if (TextUtils.isEmpty((CharSequence)zzcgi2.zzixs)) {
            return;
        }
        if (!zzcgi2.zzixx) {
            this.zzg(zzcgi2);
            return;
        }
        this.zzaws().beginTransaction();
        try {
            this.zzg(zzcgi2);
            zzcgl zzcgl3 = this.zzaws().zzah(zzcgl2.packageName, zzcgl2.zziyg.name);
            if (zzcgl3 != null) {
                this.zzawy().zzazi().zze("Removing conditional user property", zzcgl2.packageName, this.zzawt().zzjj(zzcgl2.zziyg.name));
                this.zzaws().zzai(zzcgl2.packageName, zzcgl2.zziyg.name);
                if (zzcgl3.zziyi) {
                    this.zzaws().zzaf(zzcgl2.packageName, zzcgl2.zziyg.name);
                }
                if (zzcgl2.zziyo != null) {
                    Bundle bundle = null;
                    if (zzcgl2.zziyo.zzizt != null) {
                        bundle = zzcgl2.zziyo.zzizt.zzayx();
                    }
                    this.zzc(this.zzawu().zza(zzcgl2.zziyo.name, bundle, zzcgl3.zziyf, zzcgl2.zziyo.zzizu, true, false), zzcgi2);
                }
            } else {
                this.zzawy().zzazf().zze("Conditional user property doesn't exist", zzchm.zzjk(zzcgl2.packageName), this.zzawt().zzjj(zzcgl2.zziyg.name));
            }
            this.zzaws().setTransactionSuccessful();
            return;
        }
        finally {
            this.zzaws().endTransaction();
        }
    }

    final void zzc(zzcln zzcln2, zzcgi zzcgi2) {
        ((zzcjk)this.zzawx()).zzve();
        this.zzxf();
        if (TextUtils.isEmpty((CharSequence)zzcgi2.zzixs)) {
            return;
        }
        if (!zzcgi2.zzixx) {
            this.zzg(zzcgi2);
            return;
        }
        this.zzawy().zzazi().zzj("Removing user property", this.zzawt().zzjj(zzcln2.name));
        this.zzaws().beginTransaction();
        try {
            this.zzg(zzcgi2);
            this.zzaws().zzaf(zzcgi2.packageName, zzcln2.name);
            this.zzaws().setTransactionSuccessful();
            this.zzawy().zzazi().zzj("User property removed", this.zzawt().zzjj(zzcln2.name));
            return;
        }
        finally {
            this.zzaws().endTransaction();
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zzd(zzcgi zzcgi2) {
        this.zzaws().zzjb(zzcgi2.packageName);
        zzcgo zzcgo2 = this.zzaws();
        String string2 = zzcgi2.packageName;
        zzbq.zzgm(string2);
        zzcgo2.zzve();
        zzcgo2.zzxf();
        try {
            SQLiteDatabase sQLiteDatabase = zzcgo2.getWritableDatabase();
            String[] arrstring = new String[]{string2};
            int n = sQLiteDatabase.delete("apps", "app_id=?", arrstring);
            int n2 = sQLiteDatabase.delete("events", "app_id=?", arrstring);
            int n3 = sQLiteDatabase.delete("user_attributes", "app_id=?", arrstring);
            int n4 = sQLiteDatabase.delete("conditional_properties", "app_id=?", arrstring);
            int n5 = sQLiteDatabase.delete("raw_events", "app_id=?", arrstring);
            int n6 = sQLiteDatabase.delete("raw_events_metadata", "app_id=?", arrstring);
            int n7 = sQLiteDatabase.delete("queue", "app_id=?", arrstring);
            n = sQLiteDatabase.delete("audience_filter_values", "app_id=?", arrstring) + (n + 0 + n2 + n3 + n4 + n5 + n6 + n7);
            if (n > 0) {
                zzcgo2.zzawy().zzazj().zze("Reset analytics data. app, records", string2, n);
            }
        }
        catch (SQLiteException sQLiteException) {
            zzcgo2.zzawy().zzazd().zze("Error resetting analytics data. appId, error", zzchm.zzjk(string2), (Object)sQLiteException);
        }
        this.zzf(this.zza(this.mContext, zzcgi2.packageName, zzcgi2.zzixs, zzcgi2.zzixx, zzcgi2.zziye));
    }

    final void zzd(zzcgl zzcgl2) {
        zzcgi zzcgi2 = this.zzjw(zzcgl2.packageName);
        if (zzcgi2 != null) {
            this.zzb(zzcgl2, zzcgi2);
        }
    }

    final void zze(zzcgi zzcgi2) {
        ((zzcjk)this.zzawx()).zzve();
        this.zzxf();
        zzbq.zzgm(zzcgi2.packageName);
        this.zzg(zzcgi2);
    }

    final void zze(zzcgl zzcgl2) {
        zzcgi zzcgi2 = this.zzjw(zzcgl2.packageName);
        if (zzcgi2 != null) {
            this.zzc(zzcgl2, zzcgi2);
        }
    }

    /*
     * Exception decompiling
     */
    public final void zzf(zzcgi var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    final void zzi(Runnable runnable) {
        ((zzcjk)this.zzawx()).zzve();
        if (this.zzjfy == null) {
            this.zzjfy = new ArrayList<Runnable>();
        }
        this.zzjfy.add(runnable);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final String zzjx(String var1_1) {
        var2_2 = this.zzawx().zzc(new zzcio(this, var1_1));
        try {
            return var2_2.get(30000L, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException var2_3) {}
        ** GOTO lbl-1000
        catch (ExecutionException var2_5) {
            ** GOTO lbl-1000
        }
        catch (TimeoutException var2_6) {}
lbl-1000:
        // 3 sources
        {
            this.zzawy().zzazd().zze("Failed to get app instance id. appId", zzchm.zzjk(var1_1), var2_4);
            return null;
        }
    }

    public final zzd zzws() {
        return this.zzata;
    }

    final void zzxf() {
        if (!this.zzdtb) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }

    final class zza
    implements zzcgq {
        List<zzcmb> zzapa;
        private /* synthetic */ zzcim zzjgh;
        zzcme zzjgi;
        List<Long> zzjgj;
        private long zzjgk;

        private zza(zzcim zzcim2) {
            this.zzjgh = zzcim2;
        }

        /* synthetic */ zza(zzcim zzcim2, zzcin zzcin2) {
            this(zzcim2);
        }

        private static long zza(zzcmb zzcmb2) {
            return zzcmb2.zzjli / 1000L / 60L / 60L;
        }

        @Override
        public final boolean zza(long l, zzcmb zzcmb2) {
            zzbq.checkNotNull(zzcmb2);
            if (this.zzapa == null) {
                this.zzapa = new ArrayList<zzcmb>();
            }
            if (this.zzjgj == null) {
                this.zzjgj = new ArrayList<Long>();
            }
            if (this.zzapa.size() > 0 && zza.zza(this.zzapa.get(0)) != zza.zza(zzcmb2)) {
                return false;
            }
            long l2 = this.zzjgk + (long)zzcmb2.zzho();
            if (l2 >= (long)Math.max(0, zzchc.zzjal.get())) {
                return false;
            }
            this.zzjgk = l2;
            this.zzapa.add(zzcmb2);
            this.zzjgj.add(l);
            return this.zzapa.size() < Math.max(1, zzchc.zzjam.get());
        }

        @Override
        public final void zzb(zzcme zzcme2) {
            zzbq.checkNotNull(zzcme2);
            this.zzjgi = zzcme2;
        }
    }

}

