/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgl;
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
import com.google.android.gms.internal.zzcjo;
import com.google.android.gms.internal.zzcjp;
import com.google.android.gms.internal.zzcjq;
import com.google.android.gms.internal.zzcjr;
import com.google.android.gms.internal.zzcjs;
import com.google.android.gms.internal.zzcjt;
import com.google.android.gms.internal.zzcju;
import com.google.android.gms.internal.zzcjv;
import com.google.android.gms.internal.zzcjw;
import com.google.android.gms.internal.zzcjx;
import com.google.android.gms.internal.zzcjy;
import com.google.android.gms.internal.zzcjz;
import com.google.android.gms.internal.zzcka;
import com.google.android.gms.internal.zzckb;
import com.google.android.gms.internal.zzckc;
import com.google.android.gms.internal.zzckf;
import com.google.android.gms.internal.zzckg;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzcln;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public final class zzcjn
extends zzcjl {
    protected zzckb zzjgx;
    private AppMeasurement.EventInterceptor zzjgy;
    private final Set<AppMeasurement.OnEventListener> zzjgz = new CopyOnWriteArraySet<AppMeasurement.OnEventListener>();
    private boolean zzjha;
    private final AtomicReference<String> zzjhb = new AtomicReference();

    protected zzcjn(zzcim zzcim2) {
        super(zzcim2);
    }

    static /* synthetic */ void zza(zzcjn zzcjn2, AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzcjn2.zzb(conditionalUserProperty);
    }

    static /* synthetic */ void zza(zzcjn zzcjn2, String string2, String string3, long l, Bundle bundle, boolean bl, boolean bl2, boolean bl3, String string4) {
        zzcjn2.zzb(string2, string3, l, bundle, bl, bl2, bl3, string4);
    }

    static /* synthetic */ void zza(zzcjn zzcjn2, String string2, String string3, Object object, long l) {
        zzcjn2.zza(string2, string3, object, l);
    }

    static /* synthetic */ void zza(zzcjn zzcjn2, boolean bl) {
        zzcjn2.zzbp(bl);
    }

    private final void zza(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        long l = ((zzcjk)this).zzws().currentTimeMillis();
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgm(conditionalUserProperty.mName);
        zzbq.zzgm(conditionalUserProperty.mOrigin);
        zzbq.checkNotNull(conditionalUserProperty.mValue);
        conditionalUserProperty.mCreationTimestamp = l;
        String string2 = conditionalUserProperty.mName;
        Object object = conditionalUserProperty.mValue;
        if (((zzcjk)this).zzawu().zzkd(string2) != 0) {
            ((zzcjk)this).zzawy().zzazd().zzj("Invalid conditional user property name", ((zzcjk)this).zzawt().zzjj(string2));
            return;
        }
        if (((zzcjk)this).zzawu().zzl(string2, object) != 0) {
            ((zzcjk)this).zzawy().zzazd().zze("Invalid conditional user property value", ((zzcjk)this).zzawt().zzjj(string2), object);
            return;
        }
        Object object2 = ((zzcjk)this).zzawu().zzm(string2, object);
        if (object2 == null) {
            ((zzcjk)this).zzawy().zzazd().zze("Unable to normalize conditional user property value", ((zzcjk)this).zzawt().zzjj(string2), object);
            return;
        }
        conditionalUserProperty.mValue = object2;
        l = conditionalUserProperty.mTriggerTimeout;
        if (!(TextUtils.isEmpty((CharSequence)conditionalUserProperty.mTriggerEventName) || l <= 15552000000L && l >= 1L)) {
            ((zzcjk)this).zzawy().zzazd().zze("Invalid conditional user property timeout", ((zzcjk)this).zzawt().zzjj(string2), l);
            return;
        }
        l = conditionalUserProperty.mTimeToLive;
        if (l > 15552000000L || l < 1L) {
            ((zzcjk)this).zzawy().zzazd().zze("Invalid conditional user property time to live", ((zzcjk)this).zzawt().zzjj(string2), l);
            return;
        }
        ((zzcjk)this).zzawx().zzg(new zzcjp(this, conditionalUserProperty));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final void zza(String var1_1, String var2_2, long var3_3, Bundle var5_4, boolean var6_5, boolean var7_6, boolean var8_7, String var9_8) {
        if (var5_4 != null) {
            var11_9 = new Bundle((Bundle)var5_4);
            var12_10 = var11_9.keySet().iterator();
        } else {
            var5_4 = new Bundle();
            do {
                this.zzawx().zzg(new zzcjv(this, var1_1, var2_2, var3_3, (Bundle)var5_4, var6_5, var7_6, var8_7, var9_8));
                return;
                break;
            } while (true);
        }
        block1: do {
            var5_4 = var11_9;
            if (!var12_10.hasNext()) ** continue;
            var5_4 = (String)var12_10.next();
            var13_12 = var11_9.get((String)var5_4);
            if (var13_12 instanceof Bundle) {
                var11_9.putBundle((String)var5_4, new Bundle((Bundle)var13_12));
                continue;
            }
            if (var13_12 instanceof Parcelable[]) {
                var5_4 = (Parcelable[])var13_12;
                var10_11 = 0;
                do {
                    if (var10_11 >= ((Object)var5_4).length) continue block1;
                    if (var5_4[var10_11] instanceof Bundle) {
                        var5_4[var10_11] = new Bundle((Bundle)var5_4[var10_11]);
                    }
                    ++var10_11;
                } while (true);
            }
            if (!(var13_12 instanceof ArrayList)) continue;
            var5_4 = (ArrayList)var13_12;
            var10_11 = 0;
            do {
                if (var10_11 < var5_4.size()) ** break;
                continue block1;
                var13_12 = var5_4.get(var10_11);
                if (var13_12 instanceof Bundle) {
                    var5_4.set(var10_11, new Bundle((Bundle)var13_12));
                }
                ++var10_11;
            } while (true);
            break;
        } while (true);
    }

    private final void zza(String string2, String string3, long l, Object object) {
        ((zzcjk)this).zzawx().zzg(new zzcjw(this, string2, string3, object, l));
    }

    private final void zza(String string2, String string3, Bundle bundle, boolean bl, boolean bl2, boolean bl3, String string4) {
        this.zza(string2, string3, ((zzcjk)this).zzws().currentTimeMillis(), bundle, true, bl2, bl3, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zza(String object, String string2, Object object2, long l) {
        zzbq.zzgm((String)object);
        zzbq.zzgm(string2);
        ((zzcjk)this).zzve();
        this.zzxf();
        if (!this.zziwf.isEnabled()) {
            ((zzcjk)this).zzawy().zzazi().log("User property not set since app measurement is disabled");
            return;
        } else {
            if (!this.zziwf.zzazv()) return;
            {
                ((zzcjk)this).zzawy().zzazi().zze("Setting user property (FE)", ((zzcjk)this).zzawt().zzjh(string2), object2);
                object = new zzcln(string2, l, object2, (String)object);
                ((zzcjk)this).zzawp().zzb((zzcln)object);
                return;
            }
        }
    }

    private final void zza(String string2, String string3, String string4, Bundle bundle) {
        long l = ((zzcjk)this).zzws().currentTimeMillis();
        zzbq.zzgm(string3);
        AppMeasurement.ConditionalUserProperty conditionalUserProperty = new AppMeasurement.ConditionalUserProperty();
        conditionalUserProperty.mAppId = string2;
        conditionalUserProperty.mName = string3;
        conditionalUserProperty.mCreationTimestamp = l;
        if (string4 != null) {
            conditionalUserProperty.mExpiredEventName = string4;
            conditionalUserProperty.mExpiredEventParams = bundle;
        }
        ((zzcjk)this).zzawx().zzg(new zzcjq(this, conditionalUserProperty));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Map<String, Object> zzb(String object, String iterator, String object2, boolean bl) {
        if (((zzcjk)this).zzawx().zzazs()) {
            ((zzcjk)this).zzawy().zzazd().log("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        }
        ((zzcjk)this).zzawx();
        if (zzcih.zzau()) {
            ((zzcjk)this).zzawy().zzazd().log("Cannot get user properties from main thread");
            return Collections.emptyMap();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zziwf.zzawx().zzg(new zzcjs(this, atomicReference, (String)object, (String)((Object)iterator), (String)object2, bl));
            try {
                atomicReference.wait(5000L);
            }
            catch (InterruptedException interruptedException) {
                ((zzcjk)this).zzawy().zzazf().zzj("Interrupted waiting for get user properties", interruptedException);
            }
        }
        iterator = (List)atomicReference.get();
        if (iterator == null) {
            ((zzcjk)this).zzawy().zzazf().log("Timed out waiting for get user properties");
            return Collections.emptyMap();
        }
        object = new ArrayMap(iterator.size());
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            object2 = (zzcln)iterator.next();
            object.put(((zzcln)object2).name, ((zzcln)object2).getValue());
        }
        return object;
    }

    static /* synthetic */ void zzb(zzcjn zzcjn2, AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzcjn2.zzc(conditionalUserProperty);
    }

    private final void zzb(AppMeasurement.ConditionalUserProperty object) {
        zzcha zzcha2;
        zzcha zzcha3;
        zzcha zzcha4;
        ((zzcjk)this).zzve();
        this.zzxf();
        zzbq.checkNotNull(object);
        zzbq.zzgm(((AppMeasurement.ConditionalUserProperty)object).mName);
        zzbq.zzgm(((AppMeasurement.ConditionalUserProperty)object).mOrigin);
        zzbq.checkNotNull(((AppMeasurement.ConditionalUserProperty)object).mValue);
        if (!this.zziwf.isEnabled()) {
            ((zzcjk)this).zzawy().zzazi().log("Conditional property not sent since Firebase Analytics is disabled");
            return;
        }
        zzcln zzcln2 = new zzcln(((AppMeasurement.ConditionalUserProperty)object).mName, ((AppMeasurement.ConditionalUserProperty)object).mTriggeredTimestamp, ((AppMeasurement.ConditionalUserProperty)object).mValue, ((AppMeasurement.ConditionalUserProperty)object).mOrigin);
        try {
            zzcha3 = ((zzcjk)this).zzawu().zza(((AppMeasurement.ConditionalUserProperty)object).mTriggeredEventName, ((AppMeasurement.ConditionalUserProperty)object).mTriggeredEventParams, ((AppMeasurement.ConditionalUserProperty)object).mOrigin, 0L, true, false);
            zzcha2 = ((zzcjk)this).zzawu().zza(((AppMeasurement.ConditionalUserProperty)object).mTimedOutEventName, ((AppMeasurement.ConditionalUserProperty)object).mTimedOutEventParams, ((AppMeasurement.ConditionalUserProperty)object).mOrigin, 0L, true, false);
            zzcha4 = ((zzcjk)this).zzawu().zza(((AppMeasurement.ConditionalUserProperty)object).mExpiredEventName, ((AppMeasurement.ConditionalUserProperty)object).mExpiredEventParams, ((AppMeasurement.ConditionalUserProperty)object).mOrigin, 0L, true, false);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
        object = new zzcgl(((AppMeasurement.ConditionalUserProperty)object).mAppId, ((AppMeasurement.ConditionalUserProperty)object).mOrigin, zzcln2, ((AppMeasurement.ConditionalUserProperty)object).mCreationTimestamp, false, ((AppMeasurement.ConditionalUserProperty)object).mTriggerEventName, zzcha2, ((AppMeasurement.ConditionalUserProperty)object).mTriggerTimeout, zzcha3, ((AppMeasurement.ConditionalUserProperty)object).mTimeToLive, zzcha4);
        ((zzcjk)this).zzawp().zzf((zzcgl)object);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private final void zzb(String var1_1, String var2_2, long var3_3, Bundle var5_4, boolean var6_5, boolean var7_6, boolean var8_7, String var9_8) {
        block23: {
            zzbq.zzgm(var1_1);
            zzbq.zzgm(var2_2);
            zzbq.checkNotNull(var5_4);
            this.zzve();
            this.zzxf();
            if (!this.zziwf.isEnabled()) {
                this.zzawy().zzazi().log("Event not sent since app measurement is disabled");
lbl11:
                // 3 sources
                do {
                    return;
                    break;
                } while (true);
            }
            if (!this.zzjha) {
                this.zzjha = true;
                var18_9 = Class.forName("com.google.android.gms.tagmanager.TagManagerService");
                var18_9.getDeclaredMethod("initialize", new Class[]{Context.class}).invoke(null, new Object[]{this.getContext()});
            }
lbl20:
            // 4 sources
            do {
                var16_12 = "am".equals(var1_1);
                var17_13 = zzclq.zzki(var2_2);
                if (var6_5 && this.zzjgy != null && !var17_13 && !var16_12) {
                    this.zzawy().zzazi().zze("Passing event to registered event handler (FE)", this.zzawt().zzjh(var2_2), this.zzawt().zzx((Bundle)var5_4));
                    this.zzjgy.interceptEvent(var1_1, var2_2, (Bundle)var5_4, var3_3);
                    return;
                }
                break block23;
                break;
            } while (true);
            {
                catch (Exception var18_10) {
                    try {
                        this.zzawy().zzazf().zzj("Failed to invoke Tag Manager's initialize() method", var18_10);
                    }
                    catch (ClassNotFoundException var18_11) {
                        this.zzawy().zzazh().log("Tag Manager is not found and thus will not be used");
                    }
                    ** continue;
                }
            }
        }
        if (!this.zziwf.zzazv()) ** GOTO lbl11
        var11_14 = this.zzawu().zzkb(var2_2);
        if (var11_14 != 0) {
            this.zzawu();
            var1_1 = zzclq.zza(var2_2, 40, true);
            if (var2_2 != null) {
                var10_15 = var2_2.length();
lbl44:
                // 2 sources
                do {
                    this.zziwf.zzawu().zza(var9_8, var11_14, "_ev", var1_1, var10_15);
                    return;
                    break;
                } while (true);
            }
            var10_15 = 0;
            ** continue;
        }
        var20_17 = Collections.singletonList("_o");
        var18_9 = this.zzawu().zza(var2_2, (Bundle)var5_4, (List<String>)var20_17, var8_7, true);
        var19_18 = new ArrayList<Bundle>();
        var19_18.add(var18_9);
        var14_19 = this.zzawu().zzbaz().nextLong();
        var10_16 = 0;
        var5_4 = var18_9.keySet().toArray(new String[var5_4.size()]);
        Arrays.sort((Object[])var5_4);
        for (Object var21_22 : var5_4) {
            var22_23 = var18_9.get((String)var21_22);
            this.zzawu();
            var22_23 = zzclq.zzaf(var22_23);
            if (var22_23 == null) continue;
            var18_9.putInt((String)var21_22, var22_23.length);
            for (var12_21 = 0; var12_21 < var22_23.length; ++var12_21) {
                var23_24 = var22_23[var12_21];
                var23_24 = this.zzawu().zza("_ep", var23_24, (List<String>)var20_17, var8_7, false);
                var23_24.putString("_en", var2_2);
                var23_24.putLong("_eid", var14_19);
                var23_24.putString("_gn", (String)var21_22);
                var23_24.putInt("_ll", var22_23.length);
                var23_24.putInt("_i", var12_21);
                var19_18.add(var23_24);
            }
            var10_16 = var22_23.length + var10_16;
        }
        if (var10_16 != 0) {
            var18_9.putLong("_eid", var14_19);
            var18_9.putInt("_epc", var10_16);
        }
        if ((var20_17 = this.zzawq().zzbao()) != null && !var18_9.containsKey("_sc")) {
            var20_17.zzjib = true;
        }
        block10: for (var10_16 = 0; var10_16 < var19_18.size(); ++var10_16) {
            block25: {
                block24: {
                    var18_9 = (Bundle)var19_18.get(var10_16);
                    if (var10_16 == 0) break block24;
                    var11_14 = 1;
lbl87:
                    // 2 sources
                    while (var11_14 != 0) {
                        var5_4 = "_ep";
lbl89:
                        // 2 sources
                        do {
                            var18_9.putString("_o", var1_1);
                            if (!var18_9.containsKey("_sc")) {
                                zzckc.zza((AppMeasurement.zzb)var20_17, var18_9);
                            }
                            if (var7_6) {
                                var18_9 = this.zzawu().zzy(var18_9);
                            }
                            this.zzawy().zzazi().zze("Logging event (FE)", this.zzawt().zzjh(var2_2), this.zzawt().zzx(var18_9));
                            var5_4 = new zzcha((String)var5_4, new zzcgx(var18_9), var1_1, var3_3);
                            this.zzawp().zzc((zzcha)var5_4, var9_8);
                            if (var16_12) continue block10;
                            var5_4 = this.zzjgz.iterator();
                            while (var5_4.hasNext()) {
                                ((AppMeasurement.OnEventListener)var5_4.next()).onEvent(var1_1, var2_2, new Bundle(var18_9), var3_3);
                            }
                            continue block10;
                            break;
                        } while (true);
                    }
                    break block25;
                }
                var11_14 = 0;
                ** GOTO lbl87
            }
            var5_4 = var2_2;
            ** continue;
        }
        ** while (this.zzawq().zzbao() == null || !"_ae".equals((Object)var2_2))
lbl112:
        // 1 sources
        this.zzaww().zzbs(true);
    }

    private final void zzbp(boolean bl) {
        ((zzcjk)this).zzve();
        this.zzxf();
        ((zzcjk)this).zzawy().zzazi().zzj("Setting app measurement enabled (FE)", bl);
        ((zzcjk)this).zzawz().setMeasurementEnabled(bl);
        ((zzcjk)this).zzawp().zzbaq();
    }

    private final void zzc(AppMeasurement.ConditionalUserProperty object) {
        zzcha zzcha2;
        ((zzcjk)this).zzve();
        this.zzxf();
        zzbq.checkNotNull(object);
        zzbq.zzgm(((AppMeasurement.ConditionalUserProperty)object).mName);
        if (!this.zziwf.isEnabled()) {
            ((zzcjk)this).zzawy().zzazi().log("Conditional property not cleared since Firebase Analytics is disabled");
            return;
        }
        zzcln zzcln2 = new zzcln(((AppMeasurement.ConditionalUserProperty)object).mName, 0L, null, null);
        try {
            zzcha2 = ((zzcjk)this).zzawu().zza(((AppMeasurement.ConditionalUserProperty)object).mExpiredEventName, ((AppMeasurement.ConditionalUserProperty)object).mExpiredEventParams, ((AppMeasurement.ConditionalUserProperty)object).mOrigin, ((AppMeasurement.ConditionalUserProperty)object).mCreationTimestamp, true, false);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
        object = new zzcgl(((AppMeasurement.ConditionalUserProperty)object).mAppId, ((AppMeasurement.ConditionalUserProperty)object).mOrigin, zzcln2, ((AppMeasurement.ConditionalUserProperty)object).mCreationTimestamp, ((AppMeasurement.ConditionalUserProperty)object).mActive, ((AppMeasurement.ConditionalUserProperty)object).mTriggerEventName, null, ((AppMeasurement.ConditionalUserProperty)object).mTriggerTimeout, null, ((AppMeasurement.ConditionalUserProperty)object).mTimeToLive, zzcha2);
        ((zzcjk)this).zzawp().zzf((zzcgl)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final List<AppMeasurement.ConditionalUserProperty> zzk(String string2, String string3, String object) {
        if (((zzcjk)this).zzawx().zzazs()) {
            ((zzcjk)this).zzawy().zzazd().log("Cannot get conditional user properties from analytics worker thread");
            return Collections.emptyList();
        }
        ((zzcjk)this).zzawx();
        if (zzcih.zzau()) {
            ((zzcjk)this).zzawy().zzazd().log("Cannot get conditional user properties from main thread");
            return Collections.emptyList();
        }
        Iterator iterator = new AtomicReference();
        synchronized (iterator) {
            this.zziwf.zzawx().zzg(new zzcjr(this, (AtomicReference)((Object)iterator), string2, string3, (String)object));
            try {
                iterator.wait(5000L);
            }
            catch (InterruptedException interruptedException) {
                ((zzcjk)this).zzawy().zzazf().zze("Interrupted waiting for get conditional user properties", string2, interruptedException);
            }
        }
        iterator = (List)((AtomicReference)((Object)iterator)).get();
        if (iterator == null) {
            ((zzcjk)this).zzawy().zzazf().zzj("Timed out waiting for get conditional user properties", string2);
            return Collections.emptyList();
        }
        object = new ArrayList(iterator.size());
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            zzcgl zzcgl2 = (zzcgl)iterator.next();
            AppMeasurement.ConditionalUserProperty conditionalUserProperty = new AppMeasurement.ConditionalUserProperty();
            conditionalUserProperty.mAppId = string2;
            conditionalUserProperty.mOrigin = string3;
            conditionalUserProperty.mCreationTimestamp = zzcgl2.zziyh;
            conditionalUserProperty.mName = zzcgl2.zziyg.name;
            conditionalUserProperty.mValue = zzcgl2.zziyg.getValue();
            conditionalUserProperty.mActive = zzcgl2.zziyi;
            conditionalUserProperty.mTriggerEventName = zzcgl2.zziyj;
            if (zzcgl2.zziyk != null) {
                conditionalUserProperty.mTimedOutEventName = zzcgl2.zziyk.name;
                if (zzcgl2.zziyk.zzizt != null) {
                    conditionalUserProperty.mTimedOutEventParams = zzcgl2.zziyk.zzizt.zzayx();
                }
            }
            conditionalUserProperty.mTriggerTimeout = zzcgl2.zziyl;
            if (zzcgl2.zziym != null) {
                conditionalUserProperty.mTriggeredEventName = zzcgl2.zziym.name;
                if (zzcgl2.zziym.zzizt != null) {
                    conditionalUserProperty.mTriggeredEventParams = zzcgl2.zziym.zzizt.zzayx();
                }
            }
            conditionalUserProperty.mTriggeredTimestamp = zzcgl2.zziyg.zzjji;
            conditionalUserProperty.mTimeToLive = zzcgl2.zziyn;
            if (zzcgl2.zziyo != null) {
                conditionalUserProperty.mExpiredEventName = zzcgl2.zziyo.name;
                if (zzcgl2.zziyo.zzizt != null) {
                    conditionalUserProperty.mExpiredEventParams = zzcgl2.zziyo.zzizt.zzayx();
                }
            }
            object.add(conditionalUserProperty);
        }
        return object;
    }

    public final void clearConditionalUserProperty(String string2, String string3, Bundle bundle) {
        this.zza(null, string2, string3, bundle);
    }

    public final void clearConditionalUserPropertyAs(String string2, String string3, String string4, Bundle bundle) {
        zzbq.zzgm(string2);
        ((zzcjk)this).zzawi();
        this.zza(string2, string3, string4, bundle);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final Task<String> getAppInstanceId() {
        String string2;
        try {
            string2 = ((zzcjk)this).zzawz().zzazn();
            if (string2 == null) return Tasks.call(((zzcjk)this).zzawx().zzazt(), new zzcjy(this));
        }
        catch (Exception exception) {
            ((zzcjk)this).zzawy().zzazf().log("Failed to schedule task for getAppInstanceId");
            return Tasks.forException(exception);
        }
        return Tasks.forResult(string2);
    }

    public final List<AppMeasurement.ConditionalUserProperty> getConditionalUserProperties(String string2, String string3) {
        return this.zzk(null, string2, string3);
    }

    public final List<AppMeasurement.ConditionalUserProperty> getConditionalUserPropertiesAs(String string2, String string3, String string4) {
        zzbq.zzgm(string2);
        ((zzcjk)this).zzawi();
        return this.zzk(string2, string3, string4);
    }

    public final Map<String, Object> getUserProperties(String string2, String string3, boolean bl) {
        return this.zzb(null, string2, string3, bl);
    }

    public final Map<String, Object> getUserPropertiesAs(String string2, String string3, String string4, boolean bl) {
        zzbq.zzgm(string2);
        ((zzcjk)this).zzawi();
        return this.zzb(string2, string3, string4, bl);
    }

    public final void registerOnMeasurementEventListener(AppMeasurement.OnEventListener onEventListener) {
        this.zzxf();
        zzbq.checkNotNull(onEventListener);
        if (!this.zzjgz.add(onEventListener)) {
            ((zzcjk)this).zzawy().zzazf().log("OnEventListener already registered");
        }
    }

    public final void resetAnalyticsData() {
        ((zzcjk)this).zzawx().zzg(new zzcka(this));
    }

    public final void setConditionalUserProperty(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzbq.checkNotNull(conditionalUserProperty);
        conditionalUserProperty = new AppMeasurement.ConditionalUserProperty(conditionalUserProperty);
        if (!TextUtils.isEmpty((CharSequence)conditionalUserProperty.mAppId)) {
            ((zzcjk)this).zzawy().zzazf().log("Package name should be null when calling setConditionalUserProperty");
        }
        conditionalUserProperty.mAppId = null;
        this.zza(conditionalUserProperty);
    }

    public final void setConditionalUserPropertyAs(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzbq.checkNotNull(conditionalUserProperty);
        zzbq.zzgm(conditionalUserProperty.mAppId);
        ((zzcjk)this).zzawi();
        this.zza(new AppMeasurement.ConditionalUserProperty(conditionalUserProperty));
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void setEventInterceptor(AppMeasurement.EventInterceptor eventInterceptor) {
        ((zzcjk)this).zzve();
        this.zzxf();
        if (eventInterceptor != null && eventInterceptor != this.zzjgy) {
            boolean bl = this.zzjgy == null;
            zzbq.zza(bl, "EventInterceptor already set.");
        }
        this.zzjgy = eventInterceptor;
    }

    public final void setMeasurementEnabled(boolean bl) {
        this.zzxf();
        ((zzcjk)this).zzawx().zzg(new zzcjo(this, bl));
    }

    public final void setMinimumSessionDuration(long l) {
        ((zzcjk)this).zzawx().zzg(new zzcjt(this, l));
    }

    public final void setSessionTimeoutDuration(long l) {
        ((zzcjk)this).zzawx().zzg(new zzcju(this, l));
    }

    public final void unregisterOnMeasurementEventListener(AppMeasurement.OnEventListener onEventListener) {
        this.zzxf();
        zzbq.checkNotNull(onEventListener);
        if (!this.zzjgz.remove(onEventListener)) {
            ((zzcjk)this).zzawy().zzazf().log("OnEventListener had not been registered");
        }
    }

    public final void zza(String string2, String string3, Bundle bundle, long l) {
        this.zza(string2, string3, l, bundle, false, true, true, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zza(String string2, String string3, Bundle bundle, boolean bl) {
        bl = this.zzjgy == null || zzclq.zzki(string3);
        this.zza(string2, string3, bundle, true, bl, true, null);
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    public final String zzazn() {
        return this.zzjhb.get();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzb(String string2, String string3, Object object) {
        int n = 0;
        int n2 = 0;
        zzbq.zzgm(string2);
        long l = ((zzcjk)this).zzws().currentTimeMillis();
        int n3 = ((zzcjk)this).zzawu().zzkd(string3);
        if (n3 != 0) {
            ((zzcjk)this).zzawu();
            string2 = zzclq.zza(string3, 24, true);
            n = n2;
            if (string3 != null) {
                n = string3.length();
            }
            this.zziwf.zzawu().zza(n3, "_ev", string2, n);
            return;
        } else {
            if (object == null) {
                this.zza(string2, string3, l, null);
                return;
            }
            n2 = ((zzcjk)this).zzawu().zzl(string3, object);
            if (n2 != 0) {
                ((zzcjk)this).zzawu();
                string2 = zzclq.zza(string3, 24, true);
                if (object instanceof String || object instanceof CharSequence) {
                    n = String.valueOf(object).length();
                }
                this.zziwf.zzawu().zza(n2, "_ev", string2, n);
                return;
            }
            object = ((zzcjk)this).zzawu().zzm(string3, object);
            if (object == null) return;
            {
                this.zza(string2, string3, l, object);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final String zzbd(long l) {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            ((zzcjk)this).zzawx().zzg(new zzcjz(this, atomicReference));
            try {
                atomicReference.wait(l);
                return (String)atomicReference.get();
            }
            catch (InterruptedException interruptedException) {
                ((zzcjk)this).zzawy().zzazf().log("Interrupted waiting for app instance id");
                return null;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final List<zzcln> zzbq(boolean bl) {
        this.zzxf();
        ((zzcjk)this).zzawy().zzazi().log("Fetching user attributes (FE)");
        if (((zzcjk)this).zzawx().zzazs()) {
            ((zzcjk)this).zzawy().zzazd().log("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        }
        ((zzcjk)this).zzawx();
        if (zzcih.zzau()) {
            ((zzcjk)this).zzawy().zzazd().log("Cannot get all user properties from main thread");
            return Collections.emptyList();
        }
        Object object = new AtomicReference();
        synchronized (object) {
            this.zziwf.zzawx().zzg(new zzcjx(this, (AtomicReference)object, bl));
            try {
                object.wait(5000L);
            }
            catch (InterruptedException interruptedException) {
                ((zzcjk)this).zzawy().zzazf().zzj("Interrupted waiting for get user properties", interruptedException);
            }
        }
        List list = (List)((AtomicReference)object).get();
        object = list;
        if (list != null) return object;
        ((zzcjk)this).zzawy().zzazf().log("Timed out waiting for get user properties");
        return Collections.emptyList();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void zzc(String string2, String string3, Bundle bundle) {
        boolean bl = this.zzjgy == null || zzclq.zzki(string3);
        this.zza(string2, string3, bundle, true, bl, false, null);
    }

    final void zzjp(String string2) {
        this.zzjhb.set(string2);
    }
}

