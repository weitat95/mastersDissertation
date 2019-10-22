/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.PackageManager
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzcgd;
import com.google.android.gms.internal.zzcgi;
import com.google.android.gms.internal.zzcgk;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzcgs;
import com.google.android.gms.internal.zzcgu;
import com.google.android.gms.internal.zzcha;
import com.google.android.gms.internal.zzchc;
import com.google.android.gms.internal.zzchd;
import com.google.android.gms.internal.zzche;
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
import com.google.android.gms.internal.zzckh;
import com.google.android.gms.internal.zzcki;
import com.google.android.gms.internal.zzckj;
import com.google.android.gms.internal.zzckk;
import com.google.android.gms.internal.zzckl;
import com.google.android.gms.internal.zzckm;
import com.google.android.gms.internal.zzckn;
import com.google.android.gms.internal.zzcko;
import com.google.android.gms.internal.zzckp;
import com.google.android.gms.internal.zzckq;
import com.google.android.gms.internal.zzckr;
import com.google.android.gms.internal.zzcks;
import com.google.android.gms.internal.zzckt;
import com.google.android.gms.internal.zzcku;
import com.google.android.gms.internal.zzclf;
import com.google.android.gms.internal.zzclk;
import com.google.android.gms.internal.zzcln;
import com.google.android.gms.internal.zzclq;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class zzckg
extends zzcjl {
    private final zzcku zzjic;
    private zzche zzjid;
    private volatile Boolean zzjie;
    private final zzcgs zzjif;
    private final zzclk zzjig;
    private final List<Runnable> zzjih = new ArrayList<Runnable>();
    private final zzcgs zzjii;

    protected zzckg(zzcim zzcim2) {
        super(zzcim2);
        this.zzjig = new zzclk(zzcim2.zzws());
        this.zzjic = new zzcku(this);
        this.zzjif = new zzckh(this, zzcim2);
        this.zzjii = new zzckm(this, zzcim2);
    }

    private final void onServiceDisconnected(ComponentName componentName) {
        ((zzcjk)this).zzve();
        if (this.zzjid != null) {
            this.zzjid = null;
            ((zzcjk)this).zzawy().zzazj().zzj("Disconnected from device MeasurementService", (Object)componentName);
            ((zzcjk)this).zzve();
            this.zzyc();
        }
    }

    static /* synthetic */ zzche zza(zzckg zzckg2, zzche zzche2) {
        zzckg2.zzjid = null;
        return null;
    }

    static /* synthetic */ zzcku zza(zzckg zzckg2) {
        return zzckg2.zzjic;
    }

    static /* synthetic */ void zza(zzckg zzckg2, ComponentName componentName) {
        zzckg2.onServiceDisconnected(componentName);
    }

    static /* synthetic */ void zzb(zzckg zzckg2) {
        zzckg2.zzbat();
    }

    private final void zzbat() {
        ((zzcjk)this).zzve();
        ((zzcjk)this).zzawy().zzazj().zzj("Processing queued up service tasks", this.zzjih.size());
        for (Runnable runnable : this.zzjih) {
            try {
                runnable.run();
            }
            catch (Throwable throwable) {
                ((zzcjk)this).zzawy().zzazd().zzj("Task exception while flushing queue", throwable);
            }
        }
        this.zzjih.clear();
        this.zzjii.cancel();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final zzcgi zzbr(boolean bl) {
        String string2;
        zzchh zzchh2 = ((zzcjk)this).zzawn();
        if (bl) {
            string2 = ((zzcjk)this).zzawy().zzazk();
            do {
                return zzchh2.zzjg(string2);
                break;
            } while (true);
        }
        string2 = null;
        return zzchh2.zzjg(string2);
    }

    static /* synthetic */ void zzc(zzckg zzckg2) {
        zzckg2.zzxs();
    }

    static /* synthetic */ zzche zzd(zzckg zzckg2) {
        return zzckg2.zzjid;
    }

    static /* synthetic */ void zze(zzckg zzckg2) {
        zzckg2.zzxr();
    }

    private final void zzj(Runnable runnable) throws IllegalStateException {
        ((zzcjk)this).zzve();
        if (this.isConnected()) {
            runnable.run();
            return;
        }
        if ((long)this.zzjih.size() >= 1000L) {
            ((zzcjk)this).zzawy().zzazd().log("Discarding data. Max runnable queue size reached");
            return;
        }
        this.zzjih.add(runnable);
        this.zzjii.zzs(60000L);
        this.zzyc();
    }

    private final void zzxr() {
        ((zzcjk)this).zzve();
        this.zzjig.start();
        this.zzjif.zzs(zzchc.zzjbj.get());
    }

    private final void zzxs() {
        ((zzcjk)this).zzve();
        if (!this.isConnected()) {
            return;
        }
        ((zzcjk)this).zzawy().zzazj().log("Inactivity, disconnecting from the service");
        this.disconnect();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void disconnect() {
        ((zzcjk)this).zzve();
        this.zzxf();
        try {
            zza.zzamc();
            ((zzcjk)this).getContext().unbindService((ServiceConnection)this.zzjic);
        }
        catch (IllegalStateException illegalStateException) {
        }
        catch (IllegalArgumentException illegalArgumentException) {}
        this.zzjid = null;
    }

    public final boolean isConnected() {
        ((zzcjk)this).zzve();
        this.zzxf();
        return this.zzjid != null;
    }

    protected final void resetAnalyticsData() {
        ((zzcjk)this).zzve();
        this.zzxf();
        zzcgi zzcgi2 = this.zzbr(false);
        ((zzcjk)this).zzawr().resetAnalyticsData();
        this.zzj(new zzcki(this, zzcgi2));
    }

    protected final void zza(zzche zzche2) {
        ((zzcjk)this).zzve();
        zzbq.checkNotNull(zzche2);
        this.zzjid = zzche2;
        this.zzxr();
        this.zzbat();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zza(zzche zzche2, zzbfm zzbfm2, zzcgi zzcgi2) {
        ((zzcjk)this).zzve();
        this.zzxf();
        int n = 0;
        int n2 = 100;
        do {
            int n3;
            ArrayList<zzbfm> arrayList;
            List<zzbfm> list;
            if (n < 1001 && n2 == 100) {
                arrayList = new ArrayList<zzbfm>();
                list = ((zzcjk)this).zzawr().zzeb(100);
                if (list != null) {
                    arrayList.addAll(list);
                    n2 = list.size();
                } else {
                    n2 = 0;
                }
                if (zzbfm2 != null && n2 < 100) {
                    arrayList.add(zzbfm2);
                }
                n3 = arrayList.size();
            } else {
                return;
            }
            for (int i = 0; i < n3; ++i) {
                list = arrayList.get(i);
                if ((list = (zzbfm)((Object)list)) instanceof zzcha) {
                    try {
                        zzche2.zza((zzcha)((Object)list), zzcgi2);
                    }
                    catch (RemoteException remoteException) {
                        ((zzcjk)this).zzawy().zzazd().zzj("Failed to send event to the service", (Object)remoteException);
                    }
                    continue;
                }
                if (list instanceof zzcln) {
                    try {
                        zzche2.zza((zzcln)((Object)list), zzcgi2);
                    }
                    catch (RemoteException remoteException) {
                        ((zzcjk)this).zzawy().zzazd().zzj("Failed to send attribute to the service", (Object)remoteException);
                    }
                    continue;
                }
                if (list instanceof zzcgl) {
                    try {
                        zzche2.zza((zzcgl)((Object)list), zzcgi2);
                    }
                    catch (RemoteException remoteException) {
                        ((zzcjk)this).zzawy().zzazd().zzj("Failed to send conditional property to the service", (Object)remoteException);
                    }
                    continue;
                }
                ((zzcjk)this).zzawy().zzazd().log("Discarding data. Unrecognized parcel type.");
            }
            ++n;
        } while (true);
    }

    protected final void zza(AppMeasurement.zzb zzb2) {
        ((zzcjk)this).zzve();
        this.zzxf();
        this.zzj(new zzckl(this, zzb2));
    }

    public final void zza(AtomicReference<String> atomicReference) {
        ((zzcjk)this).zzve();
        this.zzxf();
        this.zzj(new zzckj(this, atomicReference, this.zzbr(false)));
    }

    protected final void zza(AtomicReference<List<zzcgl>> atomicReference, String string2, String string3, String string4) {
        ((zzcjk)this).zzve();
        this.zzxf();
        this.zzj(new zzckq(this, atomicReference, string2, string3, string4, this.zzbr(false)));
    }

    protected final void zza(AtomicReference<List<zzcln>> atomicReference, String string2, String string3, String string4, boolean bl) {
        ((zzcjk)this).zzve();
        this.zzxf();
        this.zzj(new zzckr(this, atomicReference, string2, string3, string4, bl, this.zzbr(false)));
    }

    protected final void zza(AtomicReference<List<zzcln>> atomicReference, boolean bl) {
        ((zzcjk)this).zzve();
        this.zzxf();
        this.zzj(new zzckt(this, atomicReference, this.zzbr(false), bl));
    }

    @Override
    protected final boolean zzaxz() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void zzb(zzcln zzcln2) {
        ((zzcjk)this).zzve();
        this.zzxf();
        boolean bl = ((zzcjk)this).zzawr().zza(zzcln2);
        this.zzj(new zzcks(this, bl, zzcln2, this.zzbr(true)));
    }

    protected final void zzbaq() {
        ((zzcjk)this).zzve();
        this.zzxf();
        this.zzj(new zzckn(this, this.zzbr(true)));
    }

    protected final void zzbar() {
        ((zzcjk)this).zzve();
        this.zzxf();
        this.zzj(new zzckk(this, this.zzbr(true)));
    }

    final Boolean zzbas() {
        return this.zzjie;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void zzc(zzcha zzcha2, String string2) {
        zzbq.checkNotNull(zzcha2);
        ((zzcjk)this).zzve();
        this.zzxf();
        boolean bl = ((zzcjk)this).zzawr().zza(zzcha2);
        this.zzj(new zzcko(this, true, bl, zzcha2, this.zzbr(true), string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void zzf(zzcgl zzcgl2) {
        zzbq.checkNotNull(zzcgl2);
        ((zzcjk)this).zzve();
        this.zzxf();
        boolean bl = ((zzcjk)this).zzawr().zzc(zzcgl2);
        this.zzj(new zzckp(this, true, bl, new zzcgl(zzcgl2), this.zzbr(true), zzcgl2));
    }

    /*
     * Enabled aggressive block sorting
     */
    final void zzyc() {
        int n;
        Object object;
        int n2 = 1;
        ((zzcjk)this).zzve();
        this.zzxf();
        if (this.isConnected()) {
            return;
        }
        if (this.zzjie == null) {
            boolean bl;
            ((zzcjk)this).zzve();
            this.zzxf();
            object = ((zzcjk)this).zzawz().zzazo();
            if (object != null && ((Boolean)object).booleanValue()) {
                bl = true;
            } else {
                boolean bl2;
                if (((zzcjk)this).zzawn().zzazb() == 1) {
                    n = 1;
                    bl2 = true;
                } else {
                    ((zzcjk)this).zzawy().zzazj().log("Checking service availability");
                    object = ((zzcjk)this).zzawu();
                    n = zzf.zzafy().isGooglePlayServicesAvailable(((zzcjk)object).getContext());
                    switch (n) {
                        default: {
                            ((zzcjk)this).zzawy().zzazf().zzj("Unexpected service status", n);
                            n = 0;
                            bl2 = false;
                            break;
                        }
                        case 0: {
                            ((zzcjk)this).zzawy().zzazj().log("Service available");
                            n = 1;
                            bl2 = true;
                            break;
                        }
                        case 1: {
                            ((zzcjk)this).zzawy().zzazj().log("Service missing");
                            n = 1;
                            bl2 = false;
                            break;
                        }
                        case 18: {
                            ((zzcjk)this).zzawy().zzazf().log("Service updating");
                            n = 1;
                            bl2 = true;
                            break;
                        }
                        case 2: {
                            ((zzcjk)this).zzawy().zzazi().log("Service container out of date");
                            object = ((zzcjk)this).zzawu();
                            zzf.zzafy();
                            if (zzf.zzcf(((zzcjk)object).getContext()) < 11400) {
                                n = 1;
                                bl2 = false;
                                break;
                            }
                            object = ((zzcjk)this).zzawz().zzazo();
                            bl2 = object == null || ((Boolean)object).booleanValue();
                            n = 0;
                            break;
                        }
                        case 3: {
                            ((zzcjk)this).zzawy().zzazf().log("Service disabled");
                            n = 0;
                            bl2 = false;
                            break;
                        }
                        case 9: {
                            ((zzcjk)this).zzawy().zzazf().log("Service invalid");
                            n = 0;
                            bl2 = false;
                        }
                    }
                }
                bl = bl2;
                if (n != 0) {
                    ((zzcjk)this).zzawz().zzbm(bl2);
                    bl = bl2;
                }
            }
            this.zzjie = bl;
        }
        if (this.zzjie.booleanValue()) {
            this.zzjic.zzbau();
            return;
        }
        object = ((zzcjk)this).getContext().getPackageManager().queryIntentServices(new Intent().setClassName(((zzcjk)this).getContext(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
        n = object != null && object.size() > 0 ? n2 : 0;
        if (n != 0) {
            object = new Intent("com.google.android.gms.measurement.START");
            object.setComponent(new ComponentName(((zzcjk)this).getContext(), "com.google.android.gms.measurement.AppMeasurementService"));
            this.zzjic.zzn((Intent)object);
            return;
        }
        ((zzcjk)this).zzawy().zzazd().log("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
    }
}

