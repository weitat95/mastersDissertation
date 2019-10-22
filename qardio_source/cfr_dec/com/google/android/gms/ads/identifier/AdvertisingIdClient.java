/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.SystemClock
 *  android.util.Log
 */
package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.ads.identifier.zzb;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzfo;
import com.google.android.gms.internal.zzfp;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AdvertisingIdClient {
    private final Context mContext;
    private com.google.android.gms.common.zza zzamu;
    private zzfo zzamv;
    private boolean zzamw;
    private Object zzamx = new Object();
    private zza zzamy;
    private boolean zzamz;
    private long zzana;

    public AdvertisingIdClient(Context context) {
        this(context, 30000L, false, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public AdvertisingIdClient(Context context, long l, boolean bl, boolean bl2) {
        zzbq.checkNotNull(context);
        if (bl) {
            Context context2 = context.getApplicationContext();
            if (context2 != null) {
                context = context2;
            }
            this.mContext = context;
        } else {
            this.mContext = context;
        }
        this.zzamw = false;
        this.zzana = l;
        this.zzamz = bl2;
    }

    public static Info getAdvertisingIdInfo(Context object) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        Object object2 = new zzb((Context)object);
        boolean bl = ((zzb)object2).getBoolean("gads:ad_id_app_context:enabled", false);
        float f = ((zzb)object2).getFloat("gads:ad_id_app_context:ping_ratio", 0.0f);
        String string2 = ((zzb)object2).getString("gads:ad_id_use_shared_preference:experiment_id", "");
        object = new AdvertisingIdClient((Context)object, -1L, bl, ((zzb)object2).getBoolean("gads:ad_id_use_persistent_service:enabled", false));
        try {
            long l = SystemClock.elapsedRealtime();
            super.start(false);
            object2 = ((AdvertisingIdClient)object).getInfo();
            super.zza((Info)object2, bl, f, SystemClock.elapsedRealtime() - l, string2, null);
            return object2;
        }
        catch (Throwable throwable) {
            super.zza(null, bl, f, -1L, string2, throwable);
            throw throwable;
        }
        finally {
            ((AdvertisingIdClient)object).finish();
        }
    }

    public static boolean getIsAdIdFakeForDebugLogging(Context object) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzb zzb2 = new zzb((Context)object);
        object = new AdvertisingIdClient((Context)object, -1L, zzb2.getBoolean("gads:ad_id_app_context:enabled", false), zzb2.getBoolean("com.google.android.gms.ads.identifier.service.PERSISTENT_START", false));
        try {
            super.start(false);
            boolean bl = ((AdvertisingIdClient)object).getIsAdIdFakeForDebugLogging();
            return bl;
        }
        finally {
            ((AdvertisingIdClient)object).finish();
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean bl) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void start(boolean bl) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzbq.zzgn("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzamw) {
                this.finish();
            }
            this.zzamu = AdvertisingIdClient.zzc(this.mContext, this.zzamz);
            this.zzamv = AdvertisingIdClient.zza(this.mContext, this.zzamu);
            this.zzamw = true;
            if (bl) {
                this.zzbo();
            }
            return;
        }
    }

    private static zzfo zza(Context object, com.google.android.gms.common.zza zza2) throws IOException {
        try {
            object = zzfp.zzc(zza2.zza(10000L, TimeUnit.MILLISECONDS));
            return object;
        }
        catch (InterruptedException interruptedException) {
            throw new IOException("Interrupted exception");
        }
        catch (Throwable throwable) {
            throw new IOException(throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private final boolean zza(Info info, boolean bl, float f, long l, String string2, Throwable throwable) {
        if (Math.random() > (double)f) {
            return false;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String string3 = bl ? "1" : "0";
        hashMap.put("app_context", string3);
        if (info != null) {
            string3 = info.isLimitAdTrackingEnabled() ? "1" : "0";
            hashMap.put("limit_ad_tracking", string3);
        }
        if (info != null && info.getId() != null) {
            hashMap.put("ad_id_size", Integer.toString(info.getId().length()));
        }
        if (throwable != null) {
            hashMap.put("error", throwable.getClass().getName());
        }
        if (string2 != null && !string2.isEmpty()) {
            hashMap.put("experiment_id", string2);
        }
        hashMap.put("tag", "AdvertisingIdClient");
        hashMap.put("time_spent", Long.toString(l));
        new com.google.android.gms.ads.identifier.zza(this, hashMap).start();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void zzbo() {
        Object object = this.zzamx;
        synchronized (object) {
            if (this.zzamy != null) {
                this.zzamy.zzane.countDown();
                try {
                    this.zzamy.join();
                }
                catch (InterruptedException interruptedException) {}
            }
            if (this.zzana > 0L) {
                this.zzamy = new zza(this, this.zzana);
            }
            return;
        }
    }

    /*
     * Exception decompiling
     */
    private static com.google.android.gms.common.zza zzc(Context var0, boolean var1_3) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous, and can't clone.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:509)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
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

    protected void finalize() throws Throwable {
        this.finish();
        super.finalize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void finish() {
        zzbq.zzgn("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.mContext == null || this.zzamu == null) {
                return;
            }
            try {
                if (this.zzamw) {
                    com.google.android.gms.common.stats.zza.zzamc();
                    this.mContext.unbindService((ServiceConnection)this.zzamu);
                }
            }
            catch (Throwable throwable) {
                Log.i((String)"AdvertisingIdClient", (String)"AdvertisingIdClient unbindService failed.", (Throwable)throwable);
            }
            this.zzamw = false;
            this.zzamv = null;
            this.zzamu = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Info getInfo() throws IOException {
        zzbq.zzgn("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            Object object;
            block14: {
                if (!this.zzamw) {
                    object = this.zzamx;
                    synchronized (object) {
                        if (this.zzamy == null || !this.zzamy.zzanf) {
                            throw new IOException("AdvertisingIdClient is not connected.");
                        }
                    }
                    try {
                        this.start(false);
                        if (this.zzamw) break block14;
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                    catch (Exception exception) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.", exception);
                    }
                }
            }
            zzbq.checkNotNull(this.zzamu);
            zzbq.checkNotNull(this.zzamv);
            try {
                object = new Info(this.zzamv.getId(), this.zzamv.zzb(true));
                // MONITOREXIT [6, 8] lbl21 : MonitorExitStatement: MONITOREXIT : this
                this.zzbo();
                return object;
            }
            catch (RemoteException remoteException) {
                Log.i((String)"AdvertisingIdClient", (String)"GMS remote exception ", (Throwable)remoteException);
                throw new IOException("Remote exception");
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean getIsAdIdFakeForDebugLogging() throws IOException {
        zzbq.zzgn("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            block14: {
                if (!this.zzamw) {
                    Object object = this.zzamx;
                    synchronized (object) {
                        if (this.zzamy == null || !this.zzamy.zzanf) {
                            throw new IOException("AdvertisingIdClient is not connected.");
                        }
                    }
                    try {
                        this.start(false);
                        if (this.zzamw) break block14;
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                    catch (Exception exception) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.", exception);
                    }
                }
            }
            zzbq.checkNotNull(this.zzamu);
            zzbq.checkNotNull(this.zzamv);
            try {
                boolean bl = this.zzamv.zzbp();
                // MONITOREXIT [6, 8] lbl21 : MonitorExitStatement: MONITOREXIT : this
                this.zzbo();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.i((String)"AdvertisingIdClient", (String)"GMS remote exception ", (Throwable)remoteException);
                throw new IOException("Remote exception");
            }
        }
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        this.start(true);
    }

    public static final class Info {
        private final String zzang;
        private final boolean zzanh;

        public Info(String string2, boolean bl) {
            this.zzang = string2;
            this.zzanh = bl;
        }

        public final String getId() {
            return this.zzang;
        }

        public final boolean isLimitAdTrackingEnabled() {
            return this.zzanh;
        }

        public final String toString() {
            String string2 = this.zzang;
            boolean bl = this.zzanh;
            return new StringBuilder(String.valueOf(string2).length() + 7).append("{").append(string2).append("}").append(bl).toString();
        }
    }

    static final class zza
    extends Thread {
        private WeakReference<AdvertisingIdClient> zzanc;
        private long zzand;
        CountDownLatch zzane;
        boolean zzanf;

        public zza(AdvertisingIdClient advertisingIdClient, long l) {
            this.zzanc = new WeakReference<AdvertisingIdClient>(advertisingIdClient);
            this.zzand = l;
            this.zzane = new CountDownLatch(1);
            this.zzanf = false;
            this.start();
        }

        private final void disconnect() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient)this.zzanc.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzanf = true;
            }
        }

        @Override
        public final void run() {
            try {
                if (!this.zzane.await(this.zzand, TimeUnit.MILLISECONDS)) {
                    this.disconnect();
                }
                return;
            }
            catch (InterruptedException interruptedException) {
                this.disconnect();
                return;
            }
        }
    }

}

