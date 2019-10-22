/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.dynamite;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamite.zze;
import com.google.android.gms.dynamite.zzf;
import com.google.android.gms.dynamite.zzg;
import com.google.android.gms.dynamite.zzh;
import com.google.android.gms.dynamite.zzi;
import com.google.android.gms.dynamite.zzj;
import com.google.android.gms.dynamite.zzk;
import com.google.android.gms.dynamite.zzl;
import com.google.android.gms.dynamite.zzm;
import com.google.android.gms.dynamite.zzn;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class DynamiteModule {
    private static Boolean zzgwq;
    private static zzk zzgwr;
    private static zzm zzgws;
    private static String zzgwt;
    private static final ThreadLocal<zza> zzgwu;
    private static final zzi zzgwv;
    public static final zzd zzgww;
    private static zzd zzgwx;
    public static final zzd zzgwy;
    public static final zzd zzgwz;
    public static final zzd zzgxa;
    public static final zzd zzgxb;
    private final Context zzgxc;

    static {
        zzgwu = new ThreadLocal();
        zzgwv = new com.google.android.gms.dynamite.zza();
        zzgww = new com.google.android.gms.dynamite.zzb();
        zzgwx = new com.google.android.gms.dynamite.zzc();
        zzgwy = new com.google.android.gms.dynamite.zzd();
        zzgwz = new zze();
        zzgxa = new zzf();
        zzgxb = new zzg();
    }

    private DynamiteModule(Context context) {
        this.zzgxc = zzbq.checkNotNull(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Context zza(Context context, String string2, int n, Cursor cursor, zzm zzm2) {
        try {
            return (Context)com.google.android.gms.dynamic.zzn.zzx(zzm2.zza(com.google.android.gms.dynamic.zzn.zzz(context), string2, n, com.google.android.gms.dynamic.zzn.zzz(cursor)));
        }
        catch (Exception exception) {
            String string3 = String.valueOf(exception.toString());
            string3 = string3.length() != 0 ? "Failed to load DynamiteLoader: ".concat(string3) : new String("Failed to load DynamiteLoader: ");
            Log.e((String)"DynamiteModule", (String)string3);
            return null;
        }
    }

    /*
     * Exception decompiling
     */
    public static DynamiteModule zza(Context var0, zzd var1_2, String var2_3) throws zzc {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 7[CATCHBLOCK]
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
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static DynamiteModule zza(Context context, String string2, int n) throws zzc {
        // MONITORENTER : com.google.android.gms.dynamite.DynamiteModule.class
        Boolean bl = zzgwq;
        // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
        if (bl == null) {
            throw new zzc("Failed to determine which loading route to use.", null);
        }
        if (bl == false) return DynamiteModule.zzb(context, string2, n);
        return DynamiteModule.zzc(context, string2, n);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void zza(ClassLoader object) throws zzc {
        void var0_2;
        try {
            IInterface iInterface;
            object = (IBinder)((ClassLoader)object).loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            object = object == null ? null : ((iInterface = object.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2")) instanceof zzm ? (zzm)iInterface : new zzn((IBinder)object));
            zzgws = object;
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new zzc("Failed to instantiate dynamite loader", (Throwable)var0_2, null);
        }
        catch (InstantiationException instantiationException) {
            throw new zzc("Failed to instantiate dynamite loader", (Throwable)var0_2, null);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new zzc("Failed to instantiate dynamite loader", (Throwable)var0_2, null);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new zzc("Failed to instantiate dynamite loader", (Throwable)var0_2, null);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new zzc("Failed to instantiate dynamite loader", (Throwable)var0_2, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int zzab(Context object, String string2) {
        try {
            AnnotatedElement annotatedElement = object.getApplicationContext().getClassLoader().loadClass(new StringBuilder(String.valueOf("com.google.android.gms.dynamite.descriptors.").length() + 1 + String.valueOf(string2).length() + String.valueOf("ModuleDescriptor").length()).append("com.google.android.gms.dynamite.descriptors.").append(string2).append(".").append("ModuleDescriptor").toString());
            object = ((Class)annotatedElement).getDeclaredField("MODULE_ID");
            annotatedElement = ((Class)annotatedElement).getDeclaredField("MODULE_VERSION");
            if (((Field)object).get(null).equals(string2)) return ((Field)annotatedElement).getInt(null);
            object = String.valueOf(((Field)object).get(null));
            Log.e((String)"DynamiteModule", (String)new StringBuilder(String.valueOf(object).length() + 51 + String.valueOf(string2).length()).append("Module descriptor id '").append((String)object).append("' didn't match expected id '").append(string2).append("'").toString());
            return 0;
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.w((String)"DynamiteModule", (String)new StringBuilder(String.valueOf(string2).length() + 45).append("Local module descriptor class for ").append(string2).append(" not found.").toString());
            return 0;
        }
        catch (Exception exception) {
            String string3 = String.valueOf(exception.getMessage());
            string3 = string3.length() != 0 ? "Failed to load module descriptor class: ".concat(string3) : new String("Failed to load module descriptor class: ");
            Log.e((String)"DynamiteModule", (String)string3);
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static DynamiteModule zzad(Context context, String string2) {
        string2 = (string2 = String.valueOf(string2)).length() != 0 ? "Selected local version of ".concat(string2) : new String("Selected local version of ");
        Log.i((String)"DynamiteModule", (String)string2);
        return new DynamiteModule(context.getApplicationContext());
    }

    private static DynamiteModule zzb(Context object, String string2, int n) throws zzc {
        Log.i((String)"DynamiteModule", (String)new StringBuilder(String.valueOf(string2).length() + 51).append("Selected remote version of ").append(string2).append(", version >= ").append(n).toString());
        zzk zzk2 = DynamiteModule.zzdf(object);
        if (zzk2 == null) {
            throw new zzc("Failed to create IDynamiteLoader.", null);
        }
        try {
            object = zzk2.zza(com.google.android.gms.dynamic.zzn.zzz(object), string2, n);
        }
        catch (RemoteException remoteException) {
            throw new zzc("Failed to load remote module.", remoteException, null);
        }
        if (com.google.android.gms.dynamic.zzn.zzx((IObjectWrapper)object) == null) {
            throw new zzc("Failed to load remote module.", null);
        }
        return new DynamiteModule((Context)com.google.android.gms.dynamic.zzn.zzx((IObjectWrapper)object));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static int zzc(Context var0, String var1_3, boolean var2_4) {
        block28: {
            block30: {
                block29: {
                    block26: {
                        // MONITORENTER : com.google.android.gms.dynamite.DynamiteModule.class
                        var6_5 = DynamiteModule.zzgwq;
                        var5_6 = var6_5;
                        if (var6_5 != null) break block28;
                        try {
                            var6_5 = var0.getApplicationContext().getClassLoader().loadClass(DynamiteLoaderClassLoader.class.getName());
                            var5_6 = var6_5.getDeclaredField("sClassLoader");
                            // MONITORENTER : var6_5
                        }
                        catch (ClassNotFoundException var5_7) {}
                        var7_11 /* !! */  = (ClassLoader)var5_6.get(null);
                        if (var7_11 /* !! */  == null) ** GOTO lbl17
                        if (var7_11 /* !! */  == ClassLoader.getSystemClassLoader()) {
                            var5_6 = Boolean.FALSE;
                        } else {
                            DynamiteModule.zza(var7_11 /* !! */ );
                            break block26;
lbl17:
                            // 1 sources
                            if ("com.google.android.gms".equals(var0.getApplicationContext().getPackageName())) {
                                var5_6.set(null, ClassLoader.getSystemClassLoader());
                                var5_6 = Boolean.FALSE;
                            } else {
                                block27: {
                                    var3_13 = DynamiteModule.zze(var0, var1_3, var2_4);
                                    if (DynamiteModule.zzgwt != null && !(var4_14 = DynamiteModule.zzgwt.isEmpty())) break block27;
                                    // MONITOREXIT : var6_5
                                    // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
                                    return var3_13;
                                }
                                try {
                                    var7_11 /* !! */  = new zzh(DynamiteModule.zzgwt, ClassLoader.getSystemClassLoader());
                                    DynamiteModule.zza(var7_11 /* !! */ );
                                    var5_6.set(null, var7_11 /* !! */ );
                                    DynamiteModule.zzgwq = Boolean.TRUE;
                                    // MONITOREXIT : var6_5
                                }
                                catch (zzc var7_12) {
                                    var5_6.set(null, ClassLoader.getSystemClassLoader());
                                    var5_6 = Boolean.FALSE;
                                }
                                // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
                                return var3_13;
                            }
                        }
                        break block29;
                        ** GOTO lbl-1000
                        catch (zzc var5_8) {}
                    }
                    var5_6 = Boolean.TRUE;
                }
                // MONITOREXIT : var6_5
                break block30;
                catch (NoSuchFieldException var5_9) {
                    ** GOTO lbl-1000
                }
                catch (IllegalAccessException var5_10) {}
lbl-1000:
                // 3 sources
                {
                    var5_6 = String.valueOf(var5_6);
                    Log.w((String)"DynamiteModule", (String)new StringBuilder(String.valueOf(var5_6).length() + 30).append("Failed to load module via V2: ").append((String)var5_6).toString());
                    var5_6 = Boolean.FALSE;
                }
            }
            DynamiteModule.zzgwq = var5_6;
        }
        // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
        if (var5_6.booleanValue() == false) return DynamiteModule.zzd(var0, var1_3, var2_4);
        try {
            return DynamiteModule.zze(var0, var1_3, var2_4);
        }
        catch (zzc var0_1) {
            var0_2 = String.valueOf(var0_1.getMessage());
            var0_2 = var0_2.length() != 0 ? "Failed to retrieve remote module version: ".concat(var0_2) : new String("Failed to retrieve remote module version: ");
            Log.w((String)"DynamiteModule", (String)var0_2);
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static DynamiteModule zzc(Context context, String string2, int n) throws zzc {
        Log.i((String)"DynamiteModule", (String)new StringBuilder(String.valueOf(string2).length() + 51).append("Selected remote version of ").append(string2).append(", version >= ").append(n).toString());
        // MONITORENTER : com.google.android.gms.dynamite.DynamiteModule.class
        zzm zzm2 = zzgws;
        // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
        if (zzm2 == null) {
            throw new zzc("DynamiteLoaderV2 was not cached.", null);
        }
        zza zza2 = zzgwu.get();
        if (zza2 == null) throw new zzc("No result cursor", null);
        if (zza2.zzgxd == null) {
            throw new zzc("No result cursor", null);
        }
        if ((context = DynamiteModule.zza(context.getApplicationContext(), string2, n, zza2.zzgxd, zzm2)) != null) return new DynamiteModule(context);
        throw new zzc("Failed to get module context", null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int zzd(Context context, String string2, boolean bl) {
        zzk zzk2 = DynamiteModule.zzdf(context);
        if (zzk2 == null) {
            return 0;
        }
        try {
            return zzk2.zza(com.google.android.gms.dynamic.zzn.zzz(context), string2, bl);
        }
        catch (RemoteException remoteException) {
            String string3 = String.valueOf(remoteException.getMessage());
            string3 = string3.length() != 0 ? "Failed to retrieve remote module version: ".concat(string3) : new String("Failed to retrieve remote module version: ");
            Log.w((String)"DynamiteModule", (String)string3);
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static zzk zzdf(Context object) {
        block8: {
            // MONITORENTER : com.google.android.gms.dynamite.DynamiteModule.class
            if (zzgwr != null) {
                object = zzgwr;
                // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
                return object;
            }
            if (com.google.android.gms.common.zzf.zzafy().isGooglePlayServicesAvailable((Context)object) != 0) {
                // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
                return null;
            }
            try {
                IInterface iInterface;
                object = (IBinder)object.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                object = object == null ? null : ((iInterface = object.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader")) instanceof zzk ? (zzk)iInterface : new zzl((IBinder)object));
                if (object == null) break block8;
                zzgwr = object;
            }
            catch (Exception exception) {
                String string2 = String.valueOf(exception.getMessage());
                string2 = string2.length() != 0 ? "Failed to load IDynamiteLoader from GmsCore: ".concat(string2) : new String("Failed to load IDynamiteLoader from GmsCore: ");
                Log.e((String)"DynamiteModule", (String)string2);
            }
            // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
            return object;
        }
        // MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
        return null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int zze(Context var0, String var1_4, boolean var2_5) throws zzc {
        block25: {
            block24: {
                var4_6 = var0 /* !! */ .getContentResolver();
                var0 /* !! */  = var2_5 != false ? "api_force_staging" : "api";
                var0 /* !! */  = var4_6.query(Uri.parse((String)new StringBuilder(String.valueOf("content://com.google.android.gms.chimera/").length() + 1 + String.valueOf((Object)var0 /* !! */ ).length() + String.valueOf(var1_4).length()).append("content://com.google.android.gms.chimera/").append((String)var0 /* !! */ ).append("/").append(var1_4).toString()), null, null, null, null);
                if (var0 /* !! */  == null) ** GOTO lbl24
                var1_4 = var0 /* !! */ ;
                ** try [egrp 1[TRYBLOCK] [5 : 90->141)] { 
lbl-1000:
                // 1 sources
                {
                    catch (Throwable var0_1) lbl-1000:
                    // 2 sources
                    {
                        do {
                            if (var1_4 != null) {
                                var1_4.close();
                            }
                            throw var0_2;
                            break;
                        } while (true);
                    }
                }
lbl-1000:
                // 1 sources
                {
                    block27: {
                        try {}
                        catch (Exception var4_7) lbl-1000:
                        // 2 sources
                        {
                            do {
                                var1_4 = var0 /* !! */ ;
                                if (var4_8 instanceof zzc) {
                                    var1_4 = var0 /* !! */ ;
                                    throw var4_8;
                                }
                                break block24;
                                break;
                            } while (true);
                        }
                        if (var0 /* !! */ .moveToFirst()) break block27;
lbl24:
                        // 2 sources
                        var1_4 = var0 /* !! */ ;
                        Log.w((String)"DynamiteModule", (String)"Failed to retrieve remote module version.");
                        var1_4 = var0 /* !! */ ;
                        throw new zzc("Failed to connect to dynamite module ContentResolver.", null);
                    }
                    var1_4 = var0 /* !! */ ;
                    {
                        var3_11 = var0 /* !! */ .getInt(0);
                        var4_6 = var0 /* !! */ ;
                        if (var3_11 <= 0) break block25;
                        var1_4 = var0 /* !! */ ;
                        ** synchronized (DynamiteModule.class)
                    }
lbl-1000:
                    // 1 sources
                    {
                        ** try [egrp 4[TRYBLOCK] [16 : 183->196)] { 
lbl37:
                        // 1 sources
                        ** break block26
lbl38:
                        // 1 sources
                        catch (Throwable var4_9) {}
                        {
                            // MONITOREXIT [6, 9, 12] lbl38 : MonitorExitStatement: MONITOREXIT : com.google.android.gms.dynamite.DynamiteModule.class
                            var1_4 = var0 /* !! */ ;
                        }
                    }
                    {
                        throw var4_9;
                    }
                }
            }
            var1_4 = var0 /* !! */ ;
            throw new zzc("V2 version check failed", (Throwable)var4_8, null);
            catch (Throwable var0_3) {
                var1_4 = null;
                ** continue;
            }
            catch (Exception var4_10) {
                var0 /* !! */  = null;
                ** continue;
            }
lbl-1000:
            // 1 sources
            {
                DynamiteModule.zzgwt = var0 /* !! */ .getString(2);
            }
            var1_4 = var0 /* !! */ ;
            {
                var5_12 = DynamiteModule.zzgwu.get();
                var4_6 = var0 /* !! */ ;
                if (var5_12 == null) break block25;
                var1_4 = var0 /* !! */ ;
                var4_6 = var0 /* !! */ ;
                if (var5_12.zzgxd != null) break block25;
                var1_4 = var0 /* !! */ ;
                var5_12.zzgxd = var0 /* !! */ ;
                var4_6 = null;
            }
        }
        if (var4_6 != null) {
            var4_6.close();
        }
        return var3_11;
    }

    public final Context zzaqb() {
        return this.zzgxc;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final IBinder zzhb(String var1_1) throws zzc {
        try {
            return (IBinder)this.zzgxc.getClassLoader().loadClass(var1_1).newInstance();
        }
        catch (ClassNotFoundException var2_3) {}
        ** GOTO lbl-1000
        catch (InstantiationException var2_5) {
            ** GOTO lbl-1000
        }
        catch (IllegalAccessException var2_6) {}
lbl-1000:
        // 3 sources
        {
            if ((var1_1 = String.valueOf(var1_1)).length() != 0) {
                var1_1 = "Failed to instantiate module class: ".concat(var1_1);
                throw new zzc(var1_1, (Throwable)var2_4, null);
            }
            var1_1 = new String("Failed to instantiate module class: ");
            throw new zzc(var1_1, (Throwable)var2_4, null);
        }
    }

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    static final class zza {
        public Cursor zzgxd;

        private zza() {
        }

        /* synthetic */ zza(com.google.android.gms.dynamite.zza zza2) {
            this();
        }
    }

    static final class zzb
    implements zzi {
        private final int zzgxe;
        private final int zzgxf;

        public zzb(int n, int n2) {
            this.zzgxe = n;
            this.zzgxf = 0;
        }

        @Override
        public final int zzab(Context context, String string2) {
            return this.zzgxe;
        }

        @Override
        public final int zzc(Context context, String string2, boolean bl) {
            return 0;
        }
    }

    public static final class zzc
    extends Exception {
        private zzc(String string2) {
            super(string2);
        }

        /* synthetic */ zzc(String string2, com.google.android.gms.dynamite.zza zza2) {
            this(string2);
        }

        private zzc(String string2, Throwable throwable) {
            super(string2, throwable);
        }

        /* synthetic */ zzc(String string2, Throwable throwable, com.google.android.gms.dynamite.zza zza2) {
            this(string2, throwable);
        }
    }

    public static interface zzd {
        public zzj zza(Context var1, String var2, zzi var3) throws zzc;
    }

}

