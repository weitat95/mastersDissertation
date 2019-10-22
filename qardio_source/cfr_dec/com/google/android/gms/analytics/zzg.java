/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.os.Build;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.analytics.zzm;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzg {
    private final zzd zzata;
    private final zzi zzdpi;
    private boolean zzdpj;
    private long zzdpk;
    private long zzdpl;
    private long zzdpm;
    private long zzdpn;
    private long zzdpo;
    private boolean zzdpp;
    private final Map<Class<? extends zzh>, zzh> zzdpq;
    private final List<zzm> zzdpr;

    private zzg(zzg object) {
        this.zzdpi = ((zzg)object).zzdpi;
        this.zzata = ((zzg)object).zzata;
        this.zzdpk = ((zzg)object).zzdpk;
        this.zzdpl = ((zzg)object).zzdpl;
        this.zzdpm = ((zzg)object).zzdpm;
        this.zzdpn = ((zzg)object).zzdpn;
        this.zzdpo = ((zzg)object).zzdpo;
        this.zzdpr = new ArrayList<zzm>(((zzg)object).zzdpr);
        this.zzdpq = new HashMap<Class<? extends zzh>, zzh>(((zzg)object).zzdpq.size());
        for (Map.Entry<Class<? extends zzh>, zzh> entry : ((zzg)object).zzdpq.entrySet()) {
            zzh t = zzg.zzc(entry.getKey());
            entry.getValue().zzb(t);
            this.zzdpq.put(entry.getKey(), t);
        }
    }

    zzg(zzi zzi2, zzd zzd2) {
        zzbq.checkNotNull(zzi2);
        zzbq.checkNotNull(zzd2);
        this.zzdpi = zzi2;
        this.zzata = zzd2;
        this.zzdpn = 1800000L;
        this.zzdpo = 3024000000L;
        this.zzdpq = new HashMap<Class<? extends zzh>, zzh>();
        this.zzdpr = new ArrayList<zzm>();
    }

    @TargetApi(value=19)
    private static <T extends zzh> T zzc(Class<T> object) {
        try {
            object = (zzh)((Class)object).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception exception) {
            if (exception instanceof InstantiationException) {
                throw new IllegalArgumentException("dataType doesn't have default constructor", exception);
            }
            if (exception instanceof IllegalAccessException) {
                throw new IllegalArgumentException("dataType default constructor is not accessible", exception);
            }
            if (Build.VERSION.SDK_INT >= 19 && exception instanceof ReflectiveOperationException) {
                throw new IllegalArgumentException("Linkage exception", exception);
            }
            throw new RuntimeException(exception);
        }
        return (T)object;
    }

    public final List<zzm> getTransports() {
        return this.zzdpr;
    }

    public final <T extends zzh> T zza(Class<T> class_) {
        return (T)this.zzdpq.get(class_);
    }

    public final void zza(zzh zzh2) {
        zzbq.checkNotNull(zzh2);
        Class<?> class_ = zzh2.getClass();
        if (class_.getSuperclass() != zzh.class) {
            throw new IllegalArgumentException();
        }
        zzh2.zzb(this.zzb(class_));
    }

    public final <T extends zzh> T zzb(Class<T> class_) {
        zzh zzh2;
        zzh zzh3 = zzh2 = this.zzdpq.get(class_);
        if (zzh2 == null) {
            zzh3 = zzg.zzc(class_);
            this.zzdpq.put(class_, zzh3);
        }
        return (T)zzh3;
    }

    public final void zzl(long l) {
        this.zzdpl = l;
    }

    public final zzg zzus() {
        return new zzg(this);
    }

    public final Collection<zzh> zzut() {
        return this.zzdpq.values();
    }

    public final long zzuu() {
        return this.zzdpk;
    }

    public final void zzuv() {
        this.zzdpi.zzvb().zze(this);
    }

    public final boolean zzuw() {
        return this.zzdpj;
    }

    /*
     * Enabled aggressive block sorting
     */
    final void zzux() {
        this.zzdpm = this.zzata.elapsedRealtime();
        this.zzdpk = this.zzdpl != 0L ? this.zzdpl : this.zzata.currentTimeMillis();
        this.zzdpj = true;
    }

    final zzi zzuy() {
        return this.zzdpi;
    }

    final boolean zzuz() {
        return this.zzdpp;
    }

    final void zzva() {
        this.zzdpp = true;
    }
}

