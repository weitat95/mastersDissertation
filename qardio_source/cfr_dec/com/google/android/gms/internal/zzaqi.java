/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.internal.zzaqg;
import com.google.android.gms.internal.zzaqj;
import com.google.android.gms.internal.zzaqk;
import com.google.android.gms.internal.zzarl;
import com.google.android.gms.internal.zzarm;
import com.google.android.gms.internal.zzarr;
import com.google.android.gms.internal.zzars;

public final class zzaqi
implements ServiceConnection {
    final /* synthetic */ zzaqg zzdub;
    private volatile zzarr zzduc;
    private volatile boolean zzdud;

    protected zzaqi(zzaqg zzaqg2) {
        this.zzdub = zzaqg2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void onServiceConnected(ComponentName var1_1, IBinder var2_5) {
        block28: {
            block26: {
                block23: {
                    zzbq.zzge("AnalyticsServiceConnection.onServiceConnected");
                    // MONITORENTER : this
                    if (var2_5 != null) ** GOTO lbl8
                    try {
                        block25: {
                            block24: {
                                this.zzdub.zzdy("Service connected with null binder");
                                return;
lbl8:
                                // 2 sources
                                var1_1 /* !! */  = var2_5.getInterfaceDescriptor();
                                var3_7 = "com.google.android.gms.analytics.internal.IAnalyticsService".equals((Object)var1_1 /* !! */ );
                                if (!var3_7) break block23;
                                if (var2_5 != null) break block24;
                                var1_1 /* !! */  = null;
                                break block25;
                            }
                            var1_1 /* !! */  = var2_5.queryLocalInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
                            if (!(var1_1 /* !! */  instanceof zzarr)) {
                                var1_1 /* !! */  = new zzars(var2_5);
                            }
                            var1_1 /* !! */  = (zzarr)var1_1 /* !! */ ;
                        }
                        this.zzdub.zzdu("Bound to IAnalyticsService interface");
                        break block26;
                    }
                    finally {
                        this.notifyAll();
                    }
                }
                ** try [egrp 11[TRYBLOCK] [16 : 155->165)] { 
lbl31:
                // 1 sources
                {
                    this.zzdub.zze("Got binder with a wrong descriptor", (Object)var1_1 /* !! */ );
                    var1_1 /* !! */  = null;
                }
lbl37:
                // 3 sources
                catch (RemoteException var1_2) {
                    block27: {
                        var1_1 /* !! */  = null;
                        break block27;
                        catch (RemoteException var2_6) {}
                    }
                    this.zzdub.zzdy("Service connect failed to get IAnalyticsService");
                }
            }
            if (var1_1 /* !! */  == null) {
                zza.zzamc();
                this.zzdub.getContext().unbindService((ServiceConnection)zzaqg.zza(this.zzdub));
                ** GOTO lbl57
            } else if (!this.zzdud) {
                this.zzdub.zzdx("onServiceConnected received after the timeout limit");
                this.zzdub.zzwv().zzc(new zzaqj(this, (zzarr)var1_1 /* !! */ ));
            } else {
                this.zzduc = var1_1 /* !! */ ;
            }
            break block28;
            catch (IllegalArgumentException var1_4) {}
        }
        this.notifyAll();
        // MONITOREXIT : this
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        zzbq.zzge("AnalyticsServiceConnection.onServiceDisconnected");
        this.zzdub.zzwv().zzc(new zzaqk(this, componentName));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final zzarr zzxt() {
        zzj.zzve();
        Object object = new Intent("com.google.android.gms.analytics.service.START");
        object.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.analytics.service.AnalyticsService"));
        Context context = this.zzdub.getContext();
        object.putExtra("app_package_name", context.getPackageName());
        zza zza2 = zza.zzamc();
        synchronized (this) {
            this.zzduc = null;
            this.zzdud = true;
            boolean bl = zza2.zza(context, (Intent)object, zzaqg.zza(this.zzdub), 129);
            this.zzdub.zza("Bind to service requested", bl);
            if (!bl) {
                this.zzdud = false;
                return null;
            }
            try {
                this.wait(zzarl.zzdxh.get());
            }
            catch (InterruptedException interruptedException) {
                this.zzdub.zzdx("Wait for service connect was interrupted");
            }
            this.zzdud = false;
            object = this.zzduc;
            this.zzduc = null;
            if (object == null) {
                this.zzdub.zzdy("Successfully bound to service but never got onServiceConnected callback");
            }
            return object;
        }
    }
}

