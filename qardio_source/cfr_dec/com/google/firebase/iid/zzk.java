/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.SparseArray
 */
package com.google.firebase.iid;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.stats.zza;
import com.google.firebase.iid.zzi;
import com.google.firebase.iid.zzj;
import com.google.firebase.iid.zzl;
import com.google.firebase.iid.zzn;
import com.google.firebase.iid.zzp;
import com.google.firebase.iid.zzr;
import com.google.firebase.iid.zzs;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

final class zzk
implements ServiceConnection {
    int state = 0;
    final Messenger zznzb = new Messenger(new Handler(Looper.getMainLooper(), (Handler.Callback)new zzl(this)));
    zzp zznzc;
    final Queue<zzr<?>> zznzd = new ArrayDeque();
    final SparseArray<zzr<?>> zznze = new SparseArray();
    final /* synthetic */ zzi zznzf;

    private zzk(zzi zzi2) {
        this.zznzf = zzi2;
    }

    /* synthetic */ zzk(zzi zzi2, zzj zzj2) {
        this(zzi2);
    }

    private final void zza(zzs zzs2) {
        Iterator iterator = this.zznzd.iterator();
        while (iterator.hasNext()) {
            ((zzr)iterator.next()).zzb(zzs2);
        }
        this.zznzd.clear();
        for (int i = 0; i < this.zznze.size(); ++i) {
            ((zzr)this.zznze.valueAt(i)).zzb(zzs2);
        }
        this.zznze.clear();
    }

    private final void zzcjb() {
        zzi.zzb(this.zznzf).execute(new zzn(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            if (Log.isLoggable((String)"MessengerIpcClient", (int)2)) {
                Log.v((String)"MessengerIpcClient", (String)"Service connected");
            }
            if (iBinder == null) {
                this.zzm(0, "Null service connection");
            } else {
                try {
                    this.zznzc = new zzp(iBinder);
                    this.state = 2;
                    this.zzcjb();
                }
                catch (RemoteException remoteException) {
                    this.zzm(0, remoteException.getMessage());
                }
            }
            return;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this) {
            if (Log.isLoggable((String)"MessengerIpcClient", (int)2)) {
                Log.v((String)"MessengerIpcClient", (String)"Service disconnected");
            }
            this.zzm(2, "Service disconnected");
            return;
        }
    }

    /*
     * Exception decompiling
     */
    final boolean zzb(zzr var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
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

    final void zzcjc() {
        synchronized (this) {
            if (this.state == 2 && this.zznzd.isEmpty() && this.zznze.size() == 0) {
                if (Log.isLoggable((String)"MessengerIpcClient", (int)2)) {
                    Log.v((String)"MessengerIpcClient", (String)"Finished handling requests, unbinding");
                }
                this.state = 3;
                zza.zzamc();
                zzi.zza(this.zznzf).unbindService((ServiceConnection)this);
            }
            return;
        }
    }

    final void zzcjd() {
        synchronized (this) {
            if (this.state == 1) {
                this.zzm(1, "Timed out while binding");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final boolean zzd(Message message) {
        zzr zzr2;
        int n = message.arg1;
        if (Log.isLoggable((String)"MessengerIpcClient", (int)3)) {
            Log.d((String)"MessengerIpcClient", (String)new StringBuilder(41).append("Received response to request: ").append(n).toString());
        }
        synchronized (this) {
            zzr2 = (zzr)this.zznze.get(n);
            if (zzr2 == null) {
                Log.w((String)"MessengerIpcClient", (String)new StringBuilder(50).append("Received response for unknown request: ").append(n).toString());
                return true;
            }
            this.zznze.remove(n);
            this.zzcjc();
        }
        message = message.getData();
        if (message.getBoolean("unsupported", false)) {
            zzr2.zzb(new zzs(4, "Not supported by GmsCore"));
            return true;
        }
        zzr2.zzac((Bundle)message);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void zzic(int n) {
        synchronized (this) {
            zzr zzr2 = (zzr)this.zznze.get(n);
            if (zzr2 != null) {
                Log.w((String)"MessengerIpcClient", (String)new StringBuilder(31).append("Timing out request: ").append(n).toString());
                this.zznze.remove(n);
                zzr2.zzb(new zzs(3, "Timed out waiting for response"));
                this.zzcjc();
            }
            return;
        }
    }

    /*
     * Exception decompiling
     */
    final void zzm(int var1_1, String var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
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
}

