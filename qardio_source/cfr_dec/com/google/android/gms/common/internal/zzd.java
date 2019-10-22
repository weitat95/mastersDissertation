/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzam;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.common.internal.zzaw;
import com.google.android.gms.common.internal.zzay;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzn;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzf;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class zzd<T extends IInterface> {
    private static String[] zzfyy = new String[]{"service_esmobile", "service_googleme"};
    private final Context mContext;
    final Handler mHandler;
    private final Object mLock = new Object();
    private final Looper zzall;
    private final zzf zzfqc;
    private int zzfyd;
    private long zzfye;
    private long zzfyf;
    private int zzfyg;
    private long zzfyh;
    private zzam zzfyi;
    private final zzag zzfyj;
    private final Object zzfyk = new Object();
    private zzay zzfyl;
    protected zzj zzfym;
    private T zzfyn;
    private final ArrayList<zzi<?>> zzfyo = new ArrayList();
    private zzl zzfyp;
    private int zzfyq = 1;
    private final com.google.android.gms.common.internal.zzf zzfyr;
    private final zzg zzfys;
    private final int zzfyt;
    private final String zzfyu;
    private ConnectionResult zzfyv = null;
    private boolean zzfyw = false;
    protected AtomicInteger zzfyx = new AtomicInteger(0);

    protected zzd(Context context, Looper looper, int n, com.google.android.gms.common.internal.zzf zzf2, zzg zzg2, String string2) {
        this(context, looper, zzag.zzco(context), zzf.zzafy(), n, zzbq.checkNotNull(zzf2), zzbq.checkNotNull(zzg2), null);
    }

    protected zzd(Context context, Looper looper, zzag zzag2, zzf zzf2, int n, com.google.android.gms.common.internal.zzf zzf3, zzg zzg2, String string2) {
        this.mContext = zzbq.checkNotNull(context, "Context must not be null");
        this.zzall = zzbq.checkNotNull(looper, "Looper must not be null");
        this.zzfyj = zzbq.checkNotNull(zzag2, "Supervisor must not be null");
        this.zzfqc = zzbq.checkNotNull(zzf2, "API availability must not be null");
        this.mHandler = new zzh(this, looper);
        this.zzfyt = n;
        this.zzfyr = zzf3;
        this.zzfys = zzg2;
        this.zzfyu = string2;
    }

    static /* synthetic */ ConnectionResult zza(zzd zzd2, ConnectionResult connectionResult) {
        zzd2.zzfyv = connectionResult;
        return connectionResult;
    }

    static /* synthetic */ zzay zza(zzd zzd2, zzay zzay2) {
        zzd2.zzfyl = zzay2;
        return zzay2;
    }

    static /* synthetic */ Object zza(zzd zzd2) {
        return zzd2.zzfyk;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zza(int n, T object) {
        boolean bl;
        boolean bl2 = true;
        boolean bl3 = n == 4;
        if (bl3 != (bl = object != null)) {
            bl2 = false;
        }
        zzbq.checkArgument(bl2);
        Object object2 = this.mLock;
        synchronized (object2) {
            this.zzfyq = n;
            this.zzfyn = object;
            switch (n) {
                case 2: 
                case 3: {
                    String string2;
                    String string3;
                    zzl zzl2;
                    String string4;
                    if (this.zzfyp != null && this.zzfyi != null) {
                        object = this.zzfyi.zzalo();
                        string4 = this.zzfyi.getPackageName();
                        Log.e((String)"GmsClient", (String)new StringBuilder(String.valueOf(object).length() + 70 + String.valueOf(string4).length()).append("Calling connect() while still connected, missing disconnect() for ").append((String)object).append(" on ").append(string4).toString());
                        this.zzfyj.zza(this.zzfyi.zzalo(), this.zzfyi.getPackageName(), this.zzfyi.zzalk(), this.zzfyp, this.zzaki());
                        this.zzfyx.incrementAndGet();
                    }
                    this.zzfyp = new zzl(this, this.zzfyx.get());
                    this.zzfyi = new zzam(this.zzakh(), this.zzhi(), false, 129);
                    object = this.zzfyj;
                    string4 = this.zzfyi.zzalo();
                    if (((zzag)object).zza(new zzah(string4, string3 = this.zzfyi.getPackageName(), n = this.zzfyi.zzalk()), zzl2 = this.zzfyp, string2 = this.zzaki())) break;
                    object = this.zzfyi.zzalo();
                    string4 = this.zzfyi.getPackageName();
                    Log.e((String)"GmsClient", (String)new StringBuilder(String.valueOf(object).length() + 34 + String.valueOf(string4).length()).append("unable to connect to service: ").append((String)object).append(" on ").append(string4).toString());
                    this.zza(16, null, this.zzfyx.get());
                    break;
                }
                case 4: {
                    this.zza(object);
                    break;
                }
                case 1: {
                    if (this.zzfyp == null) break;
                    this.zzfyj.zza(this.zzhi(), this.zzakh(), 129, this.zzfyp, this.zzaki());
                    this.zzfyp = null;
                    break;
                }
                default: {
                    break;
                }
            }
            return;
        }
    }

    static /* synthetic */ void zza(zzd zzd2, int n) {
        zzd2.zzcf(16);
    }

    static /* synthetic */ void zza(zzd zzd2, int n, IInterface iInterface) {
        zzd2.zza(n, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean zza(int n, int n2, T t) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzfyq != n) {
                return false;
            }
            this.zza(n2, t);
            return true;
        }
    }

    static /* synthetic */ boolean zza(zzd zzd2, int n, int n2, IInterface iInterface) {
        return zzd2.zza(n, n2, (T)iInterface);
    }

    private final String zzaki() {
        if (this.zzfyu == null) {
            return this.mContext.getClass().getName();
        }
        return this.zzfyu;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean zzakk() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzfyq != 3) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final boolean zzakq() {
        if (this.zzfyw || TextUtils.isEmpty((CharSequence)this.zzhj()) || TextUtils.isEmpty(null)) {
            return false;
        }
        try {
            Class.forName(this.zzhj());
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    static /* synthetic */ boolean zzb(zzd zzd2) {
        return zzd2.zzakq();
    }

    static /* synthetic */ boolean zzc(zzd zzd2) {
        return zzd2.zzfyw;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final void zzcf(int n) {
        if (this.zzakk()) {
            n = 5;
            this.zzfyw = true;
        } else {
            n = 4;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(n, this.zzfyx.get(), 16));
    }

    static /* synthetic */ ConnectionResult zzd(zzd zzd2) {
        return zzd2.zzfyv;
    }

    static /* synthetic */ com.google.android.gms.common.internal.zzf zze(zzd zzd2) {
        return zzd2.zzfyr;
    }

    static /* synthetic */ ArrayList zzf(zzd zzd2) {
        return zzd2.zzfyo;
    }

    static /* synthetic */ zzg zzg(zzd zzd2) {
        return zzd2.zzfys;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void disconnect() {
        this.zzfyx.incrementAndGet();
        Object object = this.zzfyo;
        synchronized (object) {
            int n = this.zzfyo.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.zzfyo.clear();
                    // MONITOREXIT [4, 6, 7] lbl10 : MonitorExitStatement: MONITOREXIT : var3_1
                    object = this.zzfyk;
                    synchronized (object) {
                        this.zzfyl = null;
                    }
                    this.zza(1, null);
                    return;
                }
                this.zzfyo.get(n2).removeListener();
                ++n2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void dump(String object, FileDescriptor object2, PrintWriter printWriter, String[] object3) {
        Object object4;
        int n;
        long l;
        object3 = this.mLock;
        synchronized (object3) {
            n = this.zzfyq;
            object2 = this.zzfyn;
        }
        object3 = this.zzfyk;
        synchronized (object3) {
            object4 = this.zzfyl;
        }
        printWriter.append((CharSequence)object).append("mConnectState=");
        switch (n) {
            default: {
                printWriter.print("UNKNOWN");
                break;
            }
            case 2: {
                printWriter.print("REMOTE_CONNECTING");
                break;
            }
            case 3: {
                printWriter.print("LOCAL_CONNECTING");
                break;
            }
            case 4: {
                printWriter.print("CONNECTED");
                break;
            }
            case 5: {
                printWriter.print("DISCONNECTING");
                break;
            }
            case 1: {
                printWriter.print("DISCONNECTED");
            }
        }
        printWriter.append(" mService=");
        if (object2 == null) {
            printWriter.append("null");
        } else {
            printWriter.append(this.zzhj()).append("@").append(Integer.toHexString(System.identityHashCode((Object)object2.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (object4 == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode((Object)object4.asBinder())));
        }
        object2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzfyf > 0L) {
            object3 = printWriter.append((CharSequence)object).append("lastConnectedTime=");
            l = this.zzfyf;
            object4 = ((DateFormat)object2).format(new Date(this.zzfyf));
            ((PrintWriter)object3).println(new StringBuilder(String.valueOf(object4).length() + 21).append(l).append(" ").append((String)object4).toString());
        }
        if (this.zzfye > 0L) {
            printWriter.append((CharSequence)object).append("lastSuspendedCause=");
            switch (this.zzfyd) {
                default: {
                    printWriter.append(String.valueOf(this.zzfyd));
                    break;
                }
                case 1: {
                    printWriter.append("CAUSE_SERVICE_DISCONNECTED");
                    break;
                }
                case 2: {
                    printWriter.append("CAUSE_NETWORK_LOST");
                }
            }
            object3 = printWriter.append(" lastSuspendedTime=");
            l = this.zzfye;
            object4 = ((DateFormat)object2).format(new Date(this.zzfye));
            ((PrintWriter)object3).println(new StringBuilder(String.valueOf(object4).length() + 21).append(l).append(" ").append((String)object4).toString());
        }
        if (this.zzfyh > 0L) {
            printWriter.append((CharSequence)object).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzfyg));
            object = printWriter.append(" lastFailedTime=");
            l = this.zzfyh;
            object2 = ((DateFormat)object2).format(new Date(this.zzfyh));
            ((PrintWriter)object).println(new StringBuilder(String.valueOf(object2).length() + 21).append(l).append(" ").append((String)object2).toString());
        }
    }

    public Account getAccount() {
        return null;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean isConnected() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzfyq != 4) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean isConnecting() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzfyq == 2) return true;
            if (this.zzfyq != 3) return false;
            return true;
        }
    }

    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzfyg = connectionResult.getErrorCode();
        this.zzfyh = System.currentTimeMillis();
    }

    protected void onConnectionSuspended(int n) {
        this.zzfyd = n;
        this.zzfye = System.currentTimeMillis();
    }

    protected final void zza(int n, Bundle bundle, int n2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, n2, -1, (Object)new zzo(this, n, null)));
    }

    protected void zza(int n, IBinder iBinder, Bundle bundle, int n2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, n2, -1, (Object)new zzn(this, n, iBinder, bundle)));
    }

    protected void zza(T t) {
        this.zzfyf = System.currentTimeMillis();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void zza(zzan var1_1, Set<Scope> var2_7) {
        block14: {
            var4_8 = this.zzaap();
            var3_9 = new zzz(this.zzfyt);
            var3_9.zzfzt = this.mContext.getPackageName();
            var3_9.zzfzw = var4_8;
            if (var2_7 != null) {
                var3_9.zzfzv = var2_7.toArray(new Scope[var2_7.size()]);
            }
            if (this.zzaay()) {
                var2_7 = this.getAccount() != null ? this.getAccount() : new Account("<<default account>>", "com.google");
                var3_9.zzfzx = var2_7;
                if (var1_1 != null) {
                    var3_9.zzfzu = var1_1.asBinder();
                }
            } else if (this.zzako()) {
                var3_9.zzfzx = this.getAccount();
            }
            var3_9.zzfzy = this.zzakl();
            try {
                var1_1 = this.zzfyk;
                // MONITORENTER : var1_1
                if (this.zzfyl == null) break block14;
            }
            catch (DeadObjectException var1_2) {
                Log.w((String)"GmsClient", (String)"IGmsServiceBroker.getService failed", (Throwable)var1_2);
                this.zzce(1);
                return;
            }
            catch (SecurityException var1_3) {
                throw var1_3;
            }
            catch (RemoteException var1_4) {}
            this.zzfyl.zza(new zzk(this, this.zzfyx.get()), var3_9);
            // MONITOREXIT : var1_1
            return;
        }
        Log.w((String)"GmsClient", (String)"mServiceBroker is null, client disconnected");
        return;
        ** GOTO lbl-1000
        catch (RuntimeException var1_6) {}
lbl-1000:
        // 2 sources
        {
            Log.w((String)"GmsClient", (String)"IGmsServiceBroker.getService failed", (Throwable)var1_5);
            this.zza(8, null, null, this.zzfyx.get());
            return;
        }
    }

    public void zza(zzj zzj2) {
        this.zzfym = zzbq.checkNotNull(zzj2, "Connection progress callbacks cannot be null.");
        this.zza(2, null);
    }

    protected final void zza(zzj zzj2, int n, PendingIntent pendingIntent) {
        this.zzfym = zzbq.checkNotNull(zzj2, "Connection progress callbacks cannot be null.");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzfyx.get(), n, (Object)pendingIntent));
    }

    public void zza(zzp zzp2) {
        zzp2.zzajf();
    }

    protected Bundle zzaap() {
        return new Bundle();
    }

    public boolean zzaay() {
        return false;
    }

    public boolean zzabj() {
        return false;
    }

    public Bundle zzafi() {
        return null;
    }

    public boolean zzagg() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final IBinder zzagh() {
        Object object = this.zzfyk;
        synchronized (object) {
            if (this.zzfyl != null) return this.zzfyl.asBinder();
            return null;
        }
    }

    public final String zzagi() {
        if (this.isConnected() && this.zzfyi != null) {
            return this.zzfyi.getPackageName();
        }
        throw new RuntimeException("Failed to connect when checking package");
    }

    protected String zzakh() {
        return "com.google.android.gms";
    }

    public final void zzakj() {
        int n = this.zzfqc.isGooglePlayServicesAvailable(this.mContext);
        if (n != 0) {
            this.zza(1, null);
            this.zza(new zzm(this), n, null);
            return;
        }
        this.zza(new zzm(this));
    }

    public zzc[] zzakl() {
        return new zzc[0];
    }

    protected final void zzakm() {
        if (!this.isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final T zzakn() throws DeadObjectException {
        Object object = this.mLock;
        synchronized (object) {
            if (this.zzfyq == 5) {
                throw new DeadObjectException();
            }
            this.zzakm();
            boolean bl = this.zzfyn != null;
            zzbq.zza(bl, "Client is connected but service is null");
            T t = this.zzfyn;
            return t;
        }
    }

    public boolean zzako() {
        return false;
    }

    protected Set<Scope> zzakp() {
        return Collections.EMPTY_SET;
    }

    public final void zzce(int n) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6, this.zzfyx.get(), n));
    }

    protected abstract T zzd(IBinder var1);

    protected abstract String zzhi();

    protected abstract String zzhj();
}

