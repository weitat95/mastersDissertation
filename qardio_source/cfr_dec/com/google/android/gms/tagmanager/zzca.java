/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzdvl;
import com.google.android.gms.tagmanager.zzbz;
import com.google.android.gms.tagmanager.zzcb;
import com.google.android.gms.tagmanager.zzcc;
import com.google.android.gms.tagmanager.zzdj;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

final class zzca
extends Thread
implements zzbz {
    private static zzca zzkfz;
    private volatile boolean mClosed = false;
    private final Context mContext;
    private volatile boolean zzcir = false;
    private final LinkedBlockingQueue<Runnable> zzkfy = new LinkedBlockingQueue();
    private volatile zzcc zzkga;

    /*
     * Enabled aggressive block sorting
     */
    private zzca(Context context) {
        super("GAThread");
        this.mContext = context != null ? context.getApplicationContext() : context;
        this.start();
    }

    static /* synthetic */ zzcc zza(zzca zzca2) {
        return zzca2.zzkga;
    }

    static /* synthetic */ zzcc zza(zzca zzca2, zzcc zzcc2) {
        zzca2.zzkga = zzcc2;
        return zzcc2;
    }

    static /* synthetic */ Context zzb(zzca zzca2) {
        return zzca2.mContext;
    }

    static zzca zzei(Context context) {
        if (zzkfz == null) {
            zzkfz = new zzca(context);
        }
        return zzkfz;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        do {
            Object object;
            boolean bl = this.mClosed;
            try {
                try {
                    object = this.zzkfy.take();
                    if (this.zzcir) continue;
                    object.run();
                }
                catch (InterruptedException interruptedException) {
                    zzdj.zzct(interruptedException.toString());
                }
                continue;
            }
            catch (Throwable throwable) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream);
                zzdvl.zza(throwable, printStream);
                printStream.flush();
                object = String.valueOf(new String(byteArrayOutputStream.toByteArray()));
                object = ((String)object).length() != 0 ? "Error on Google TagManager Thread: ".concat((String)object) : new String("Error on Google TagManager Thread: ");
                zzdj.e((String)object);
                zzdj.e("Google TagManager is shutting down.");
                this.zzcir = true;
                continue;
            }
            break;
        } while (true);
    }

    @Override
    public final void zzl(Runnable runnable) {
        this.zzkfy.add(runnable);
    }

    @Override
    public final void zzlq(String string2) {
        this.zzl(new zzcb(this, this, System.currentTimeMillis(), string2));
    }
}

