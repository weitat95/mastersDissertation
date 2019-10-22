/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Parcelable
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.util.Log
 */
package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.PowerManager;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.zzab;
import com.google.firebase.iid.zzu;
import com.google.firebase.iid.zzz;
import java.io.IOException;

final class zzaa
implements Runnable {
    private final zzu zznys;
    private final long zznzv;
    private final PowerManager.WakeLock zznzw;
    private final FirebaseInstanceId zznzx;

    zzaa(FirebaseInstanceId firebaseInstanceId, zzu zzu2, long l) {
        this.zznzx = firebaseInstanceId;
        this.zznys = zzu2;
        this.zznzv = l;
        this.zznzw = ((PowerManager)this.getContext().getSystemService("power")).newWakeLock(1, "fiid-sync");
        this.zznzw.setReferenceCounted(false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final boolean zzcjn() {
        var1_1 = this.zznzx.zzciu();
        if (var1_1 != null && !var1_1.zzro(this.zznys.zzcjg())) {
            return true;
        }
        try {
            var2_5 = this.zznzx.zzciv();
            if (var2_5 == null) {
                Log.e((String)"FirebaseInstanceId", (String)"Token retrieval failed: null");
                return false;
            }
            if (Log.isLoggable((String)"FirebaseInstanceId", (int)3)) {
                Log.d((String)"FirebaseInstanceId", (String)"Token successfully retrieved");
            }
            if (var1_1 != null) {
                if (var1_1 == null) return true;
                if (var2_5.equals(var1_1.zzldj) != false) return true;
            }
            var1_1 = this.getContext();
            var2_5 = new Intent("com.google.firebase.iid.TOKEN_REFRESH");
            var3_6 = new Intent("com.google.firebase.INSTANCE_ID_EVENT");
            var3_6.setClass((Context)var1_1, FirebaseInstanceIdReceiver.class);
            var3_6.putExtra("wrapped_intent", (Parcelable)var2_5);
            var1_1.sendBroadcast(var3_6);
            return true;
        }
        catch (IOException var1_2) {}
        ** GOTO lbl-1000
        catch (SecurityException var1_4) {}
lbl-1000:
        // 2 sources
        {
            var1_3 = (var1_3 = String.valueOf(var1_3.getMessage())).length() != 0 ? "Token retrieval failed: ".concat(var1_3) : new String("Token retrieval failed: ");
            Log.e((String)"FirebaseInstanceId", (String)var1_3);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private final boolean zzcjo() {
        do {
            FirebaseInstanceId firebaseInstanceId = this.zznzx;
            // MONITORENTER : firebaseInstanceId
            String string2 = FirebaseInstanceId.zzciw().zzcjm();
            if (string2 == null) {
                Log.d((String)"FirebaseInstanceId", (String)"topic sync succeeded");
                // MONITOREXIT : firebaseInstanceId
                return true;
            }
            // MONITOREXIT : firebaseInstanceId
            if (!this.zzrp(string2)) {
                return false;
            }
            FirebaseInstanceId.zzciw().zzri(string2);
        } while (true);
    }

    /*
     * Exception decompiling
     */
    private final boolean zzrp(String var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 7[SWITCH]
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

    final Context getContext() {
        return this.zznzx.getApp().getApplicationContext();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        block9: {
            boolean bl = true;
            this.zznzw.acquire();
            this.zznzx.zzcr(true);
            if (this.zznys.zzcjf() == 0) {
                bl = false;
            }
            if (bl) break block9;
            this.zznzx.zzcr(false);
            this.zznzw.release();
            return;
        }
        try {
            if (!this.zzcjp()) {
                new zzab(this).zzcjq();
                return;
            }
            if (this.zzcjn() && this.zzcjo()) {
                this.zznzx.zzcr(false);
                return;
            }
            this.zznzx.zzcc(this.zznzv);
            return;
        }
        finally {
            this.zznzw.release();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final boolean zzcjp() {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getContext().getSystemService("connectivity");
        if (connectivityManager == null) return false;
        connectivityManager = connectivityManager.getActiveNetworkInfo();
        if (connectivityManager == null) return false;
        if (!connectivityManager.isConnected()) return false;
        return true;
    }
}

