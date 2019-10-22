/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgg;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgu;
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
import com.google.android.gms.internal.zzcke;
import com.google.android.gms.internal.zzckf;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class zzckc
extends zzcjl {
    protected zzckf zzjhn;
    private volatile AppMeasurement.zzb zzjho;
    private AppMeasurement.zzb zzjhp;
    private long zzjhq;
    private final Map<Activity, zzckf> zzjhr = new ArrayMap<Activity, zzckf>();
    private final CopyOnWriteArrayList<AppMeasurement.zza> zzjhs = new CopyOnWriteArrayList();
    private boolean zzjht;
    private AppMeasurement.zzb zzjhu;
    private String zzjhv;

    public zzckc(zzcim zzcim2) {
        super(zzcim2);
    }

    /*
     * Exception decompiling
     */
    private final void zza(Activity var1_1, zzckf var2_3, boolean var3_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 8[UNCONDITIONALDOLOOP]
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

    static /* synthetic */ void zza(zzckc zzckc2, zzckf zzckf2) {
        zzckc2.zza(zzckf2);
    }

    private final void zza(zzckf zzckf2) {
        ((zzcjk)this).zzawk().zzaj(((zzcjk)this).zzws().elapsedRealtime());
        if (((zzcjk)this).zzaww().zzbs(zzckf2.zzjib)) {
            zzckf2.zzjib = false;
        }
    }

    public static void zza(AppMeasurement.zzb zzb2, Bundle bundle) {
        if (bundle != null && zzb2 != null && !bundle.containsKey("_sc")) {
            if (zzb2.zziwk != null) {
                bundle.putString("_sn", zzb2.zziwk);
            }
            bundle.putString("_sc", zzb2.zziwl);
            bundle.putLong("_si", zzb2.zziwm);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String zzjy(String object) {
        Object object2 = ((String)object).split("\\.");
        if (((String[])object2).length == 0) {
            return ((String)object).substring(0, 36);
        }
        object = object2 = object2[((String[])object2).length - 1];
        if (((String)object2).length() <= 36) return object;
        return ((String)object2).substring(0, 36);
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzjhr.remove((Object)activity);
    }

    public final void onActivityPaused(Activity object) {
        object = this.zzq((Activity)object);
        this.zzjhp = this.zzjho;
        this.zzjhq = ((zzcjk)this).zzws().elapsedRealtime();
        this.zzjho = null;
        ((zzcjk)this).zzawx().zzg(new zzcke(this, (zzckf)object));
    }

    public final void onActivityResumed(Activity object) {
        this.zza((Activity)object, this.zzq((Activity)object), false);
        object = ((zzcjk)this).zzawk();
        long l = ((zzcjk)object).zzws().elapsedRealtime();
        ((zzcjk)object).zzawx().zzg(new zzcgg((zzcgd)object, l));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final void onActivitySaveInstanceState(Activity object, Bundle bundle) {
        zzckf zzckf2;
        void var2_3;
        if (var2_3 == null || (zzckf2 = this.zzjhr.get(object)) == null) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putLong("id", zzckf2.zziwm);
        bundle2.putString("name", zzckf2.zziwk);
        bundle2.putString("referrer_name", zzckf2.zziwl);
        var2_3.putBundle("com.google.firebase.analytics.screen_service", bundle2);
    }

    public final void registerOnScreenChangeCallback(AppMeasurement.zza zza2) {
        if (zza2 == null) {
            ((zzcjk)this).zzawy().zzazf().log("Attempting to register null OnScreenChangeCallback");
            return;
        }
        this.zzjhs.remove(zza2);
        this.zzjhs.add(zza2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setCurrentScreen(Activity activity, String object, String object2) {
        if (activity == null) {
            ((zzcjk)this).zzawy().zzazf().log("setCurrentScreen must be called with a non-null activity");
            return;
        }
        ((zzcjk)this).zzawx();
        if (!zzcih.zzau()) {
            ((zzcjk)this).zzawy().zzazf().log("setCurrentScreen must be called from the main thread");
            return;
        }
        if (this.zzjht) {
            ((zzcjk)this).zzawy().zzazf().log("Cannot call setCurrentScreen from onScreenChangeCallback");
            return;
        }
        if (this.zzjho == null) {
            ((zzcjk)this).zzawy().zzazf().log("setCurrentScreen cannot be called while no activity active");
            return;
        }
        if (this.zzjhr.get((Object)activity) == null) {
            ((zzcjk)this).zzawy().zzazf().log("setCurrentScreen must be called with an activity in the activity lifecycle");
            return;
        }
        Object object3 = object2;
        if (object2 == null) {
            object3 = zzckc.zzjy(activity.getClass().getCanonicalName());
        }
        boolean bl = this.zzjho.zziwl.equals(object3);
        boolean bl2 = zzclq.zzas(this.zzjho.zziwk, (String)object);
        if (bl && bl2) {
            ((zzcjk)this).zzawy().zzazg().log("setCurrentScreen cannot be called with the same class and name");
            return;
        }
        if (object != null && (((String)object).length() <= 0 || ((String)object).length() > 100)) {
            ((zzcjk)this).zzawy().zzazf().zzj("Invalid screen name length in setCurrentScreen. Length", ((String)object).length());
            return;
        }
        if (object3 != null && (((String)object3).length() <= 0 || ((String)object3).length() > 100)) {
            ((zzcjk)this).zzawy().zzazf().zzj("Invalid class name length in setCurrentScreen. Length", ((String)object3).length());
            return;
        }
        zzcho zzcho2 = ((zzcjk)this).zzawy().zzazj();
        object2 = object == null ? "null" : object;
        zzcho2.zze("Setting current screen to name, class", object2, object3);
        object = new zzckf((String)object, (String)object3, ((zzcjk)this).zzawu().zzbay());
        this.zzjhr.put(activity, (zzckf)object);
        this.zza(activity, (zzckf)object, true);
    }

    public final void unregisterOnScreenChangeCallback(AppMeasurement.zza zza2) {
        this.zzjhs.remove(zza2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zza(String string2, AppMeasurement.zzb zzb2) {
        ((zzcjk)this).zzve();
        synchronized (this) {
            if (this.zzjhv == null || this.zzjhv.equals(string2) || zzb2 != null) {
                this.zzjhv = string2;
                this.zzjhu = zzb2;
            }
            return;
        }
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    public final zzckf zzbao() {
        this.zzxf();
        ((zzcjk)this).zzve();
        return this.zzjhn;
    }

    public final AppMeasurement.zzb zzbap() {
        AppMeasurement.zzb zzb2 = this.zzjho;
        if (zzb2 == null) {
            return null;
        }
        return new AppMeasurement.zzb(zzb2);
    }

    final zzckf zzq(Activity activity) {
        zzckf zzckf2;
        zzbq.checkNotNull(activity);
        zzckf zzckf3 = zzckf2 = this.zzjhr.get((Object)activity);
        if (zzckf2 == null) {
            zzckf3 = new zzckf(null, zzckc.zzjy(activity.getClass().getCanonicalName()), ((zzcjk)this).zzawu().zzbay());
            this.zzjhr.put(activity, zzckf3);
        }
        return zzckf3;
    }
}

