/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Binder
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Binder;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzx;
import com.google.android.gms.common.zzp;
import com.google.android.gms.common.zzq;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzchf;
import com.google.android.gms.internal.zzchk;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcim;
import com.google.android.gms.internal.zzcis;
import com.google.android.gms.internal.zzcit;
import com.google.android.gms.internal.zzciu;
import com.google.android.gms.internal.zzciv;
import com.google.android.gms.internal.zzciw;
import com.google.android.gms.internal.zzcix;
import com.google.android.gms.internal.zzciy;
import com.google.android.gms.internal.zzciz;
import com.google.android.gms.internal.zzcja;
import com.google.android.gms.internal.zzcjb;
import com.google.android.gms.internal.zzcjc;
import com.google.android.gms.internal.zzcjd;
import com.google.android.gms.internal.zzcje;
import com.google.android.gms.internal.zzcjf;
import com.google.android.gms.internal.zzcjg;
import com.google.android.gms.internal.zzcjh;
import com.google.android.gms.internal.zzcji;
import com.google.android.gms.internal.zzcjj;
import com.google.android.gms.internal.zzcln;
import com.google.android.gms.internal.zzclp;
import com.google.android.gms.internal.zzclq;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public final class zzcir
extends zzchf {
    private final zzcim zziwf;
    private Boolean zzjgl;
    private String zzjgm;

    public zzcir(zzcim zzcim2) {
        this(zzcim2, null);
    }

    private zzcir(zzcim zzcim2, String string2) {
        zzbq.checkNotNull(zzcim2);
        this.zziwf = zzcim2;
        this.zzjgm = null;
    }

    static /* synthetic */ zzcim zza(zzcir zzcir2) {
        return zzcir2.zziwf;
    }

    private final void zzb(zzcgi zzcgi2, boolean bl) {
        zzbq.checkNotNull(zzcgi2);
        this.zzf(zzcgi2.packageName, false);
        this.zziwf.zzawu().zzkg(zzcgi2.zzixs);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzf(String string2, boolean bl) {
        block12: {
            block11: {
                block8: {
                    block10: {
                        block9: {
                            boolean bl2 = false;
                            if (TextUtils.isEmpty((CharSequence)string2)) {
                                this.zziwf.zzawy().zzazd().log("Measurement Service called without app package");
                                throw new SecurityException("Measurement Service called without app package");
                            }
                            if (!bl) break block11;
                            try {
                                if (this.zzjgl != null) break block8;
                                if ("com.google.android.gms".equals(this.zzjgm) || zzx.zzf(this.zziwf.getContext(), Binder.getCallingUid())) break block9;
                                bl = bl2;
                                if (!zzq.zzci(this.zziwf.getContext()).zzbq(Binder.getCallingUid())) break block10;
                            }
                            catch (SecurityException securityException) {
                                this.zziwf.zzawy().zzazd().zzj("Measurement Service called with invalid calling package. appId", zzchm.zzjk(string2));
                                throw securityException;
                            }
                        }
                        bl = true;
                    }
                    this.zzjgl = bl;
                }
                if (this.zzjgl.booleanValue()) break block12;
            }
            if (this.zzjgm == null && zzp.zzb(this.zziwf.getContext(), Binder.getCallingUid(), string2)) {
                this.zzjgm = string2;
            }
            if (!string2.equals(this.zzjgm)) {
                throw new SecurityException(String.format("Unknown calling package name '%s'.", string2));
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final List<zzcln> zza(zzcgi var1_1, boolean var2_2) {
        this.zzb(var1_1, false);
        var3_3 = this.zziwf.zzawx().zzc(new zzcjh(this, var1_1));
        try {
            var4_7 = var3_3.get();
            var3_3 = new ArrayList<E>(var4_7.size());
            var4_7 = var4_7.iterator();
            while (var4_7.hasNext() != false) {
                var5_8 = (zzclp)var4_7.next();
                if (!var2_2 && zzclq.zzki(var5_8.mName)) continue;
                var3_3.add(new zzcln(var5_8));
            }
            return var3_3;
        }
        catch (InterruptedException var3_4) {}
        ** GOTO lbl-1000
        catch (ExecutionException var3_6) {}
lbl-1000:
        // 2 sources
        {
            this.zziwf.zzawy().zzazd().zze("Failed to get user attributes. appId", zzchm.zzjk(var1_1.packageName), var3_5);
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
    @Override
    public final List<zzcgl> zza(String var1_1, String var2_5, zzcgi var3_6) {
        this.zzb(var3_6, false);
        var1_1 = this.zziwf.zzawx().zzc(new zzciz(this, var3_6, (String)var1_1, var2_5));
        try {
            return var1_1.get();
        }
        catch (InterruptedException var1_2) {}
        ** GOTO lbl-1000
        catch (ExecutionException var1_4) {}
lbl-1000:
        // 2 sources
        {
            this.zziwf.zzawy().zzazd().zzj("Failed to get conditional user properties", var1_3);
            return Collections.emptyList();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final List<zzcln> zza(String var1_1, String var2_2, String var3_6, boolean var4_7) {
        this.zzf(var1_1, true);
        var2_2 = this.zziwf.zzawx().zzc(new zzciy(this, var1_1, (String)var2_2, (String)var3_6));
        try {
            var3_6 = (List)var2_2.get();
            var2_2 = new ArrayList<zzcln>(var3_6.size());
            var3_6 = var3_6.iterator();
            while (var3_6.hasNext() != false) {
                var5_8 = (zzclp)var3_6.next();
                if (!var4_7 && zzclq.zzki(var5_8.mName)) continue;
                var2_2.add(new zzcln(var5_8));
            }
            return var2_2;
        }
        catch (InterruptedException var2_3) {}
        ** GOTO lbl-1000
        catch (ExecutionException var2_5) {}
lbl-1000:
        // 2 sources
        {
            this.zziwf.zzawy().zzazd().zze("Failed to get user attributes. appId", zzchm.zzjk(var1_1), var2_4);
            return Collections.emptyList();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final List<zzcln> zza(String var1_1, String var2_5, boolean var3_6, zzcgi var4_7) {
        this.zzb(var4_7, false);
        var1_1 = this.zziwf.zzawx().zzc(new zzcix(this, var4_7, (String)var1_1, (String)var2_5));
        try {
            var2_5 = (List)var1_1.get();
            var1_1 = new ArrayList<zzcln>(var2_5.size());
            var2_5 = var2_5.iterator();
            while (var2_5.hasNext() != false) {
                var5_8 = (zzclp)var2_5.next();
                if (!var3_6 && zzclq.zzki(var5_8.mName)) continue;
                var1_1.add(new zzcln(var5_8));
            }
            return var1_1;
        }
        catch (InterruptedException var1_2) {}
        ** GOTO lbl-1000
        catch (ExecutionException var1_4) {}
lbl-1000:
        // 2 sources
        {
            this.zziwf.zzawy().zzazd().zze("Failed to get user attributes. appId", zzchm.zzjk(var4_7.packageName), var1_3);
            return Collections.emptyList();
        }
    }

    @Override
    public final void zza(long l, String string2, String string3, String string4) {
        this.zziwf.zzawx().zzg(new zzcjj(this, string3, string4, string2, l));
    }

    @Override
    public final void zza(zzcgi object) {
        this.zzb((zzcgi)object, false);
        object = new zzcji(this, (zzcgi)object);
        if (this.zziwf.zzawx().zzazs()) {
            object.run();
            return;
        }
        this.zziwf.zzawx().zzg((Runnable)object);
    }

    @Override
    public final void zza(zzcgl zzcgl2, zzcgi zzcgi2) {
        zzbq.checkNotNull(zzcgl2);
        zzbq.checkNotNull(zzcgl2.zziyg);
        this.zzb(zzcgi2, false);
        zzcgl zzcgl3 = new zzcgl(zzcgl2);
        zzcgl3.packageName = zzcgi2.packageName;
        if (zzcgl2.zziyg.getValue() == null) {
            this.zziwf.zzawx().zzg(new zzcit(this, zzcgl3, zzcgi2));
            return;
        }
        this.zziwf.zzawx().zzg(new zzciu(this, zzcgl3, zzcgi2));
    }

    @Override
    public final void zza(zzcha zzcha2, zzcgi zzcgi2) {
        zzbq.checkNotNull(zzcha2);
        this.zzb(zzcgi2, false);
        this.zziwf.zzawx().zzg(new zzcjc(this, zzcha2, zzcgi2));
    }

    @Override
    public final void zza(zzcha zzcha2, String string2, String string3) {
        zzbq.checkNotNull(zzcha2);
        zzbq.zzgm(string2);
        this.zzf(string2, true);
        this.zziwf.zzawx().zzg(new zzcjd(this, zzcha2, string2));
    }

    @Override
    public final void zza(zzcln zzcln2, zzcgi zzcgi2) {
        zzbq.checkNotNull(zzcln2);
        this.zzb(zzcgi2, false);
        if (zzcln2.getValue() == null) {
            this.zziwf.zzawx().zzg(new zzcjf(this, zzcln2, zzcgi2));
            return;
        }
        this.zziwf.zzawx().zzg(new zzcjg(this, zzcln2, zzcgi2));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final byte[] zza(zzcha var1_1, String var2_2) {
        zzbq.zzgm(var2_2);
        zzbq.checkNotNull(var1_1);
        this.zzf(var2_2, true);
        this.zziwf.zzawy().zzazi().zzj("Log and bundle. event", this.zziwf.zzawt().zzjh(var1_1.name));
        var3_3 = this.zziwf.zzws().nanoTime() / 1000000L;
        var7_4 = this.zziwf.zzawx().zzd(new zzcje(this, var1_1, var2_2));
        try {
            var7_4 = var8_8 = var7_4.get();
            if (var8_8 == null) {
                this.zziwf.zzawy().zzazd().zzj("Log and bundle returned null. appId", zzchm.zzjk(var2_2));
                var7_4 = new byte[]{};
            }
            var5_9 = this.zziwf.zzws().nanoTime() / 1000000L;
            this.zziwf.zzawy().zzazi().zzd("Log and bundle processed. event, size, time_ms", this.zziwf.zzawt().zzjh(var1_1.name), var7_4.length, var5_9 - var3_3);
            return var7_4;
        }
        catch (InterruptedException var7_5) {}
        ** GOTO lbl-1000
        catch (ExecutionException var7_7) {}
lbl-1000:
        // 2 sources
        {
            this.zziwf.zzawy().zzazd().zzd("Failed to log and bundle. appId, event, error", zzchm.zzjk(var2_2), this.zziwf.zzawt().zzjh(var1_1.name), var7_6);
            return null;
        }
    }

    @Override
    public final void zzb(zzcgi zzcgi2) {
        this.zzb(zzcgi2, false);
        this.zziwf.zzawx().zzg(new zzcis(this, zzcgi2));
    }

    @Override
    public final void zzb(zzcgl zzcgl2) {
        zzbq.checkNotNull(zzcgl2);
        zzbq.checkNotNull(zzcgl2.zziyg);
        this.zzf(zzcgl2.packageName, true);
        zzcgl zzcgl3 = new zzcgl(zzcgl2);
        if (zzcgl2.zziyg.getValue() == null) {
            this.zziwf.zzawx().zzg(new zzciv(this, zzcgl3));
            return;
        }
        this.zziwf.zzawx().zzg(new zzciw(this, zzcgl3));
    }

    @Override
    public final String zzc(zzcgi zzcgi2) {
        this.zzb(zzcgi2, false);
        return this.zziwf.zzjx(zzcgi2.packageName);
    }

    @Override
    public final void zzd(zzcgi zzcgi2) {
        this.zzf(zzcgi2.packageName, false);
        this.zziwf.zzawx().zzg(new zzcjb(this, zzcgi2));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final List<zzcgl> zzj(String var1_1, String var2_5, String var3_6) {
        this.zzf((String)var1_1, true);
        var1_1 = this.zziwf.zzawx().zzc(new zzcja(this, (String)var1_1, var2_5, var3_6));
        try {
            return var1_1.get();
        }
        catch (InterruptedException var1_2) {}
        ** GOTO lbl-1000
        catch (ExecutionException var1_4) {}
lbl-1000:
        // 2 sources
        {
            this.zziwf.zzawy().zzazd().zzj("Failed to get conditional user properties", var1_3);
            return Collections.emptyList();
        }
    }
}

