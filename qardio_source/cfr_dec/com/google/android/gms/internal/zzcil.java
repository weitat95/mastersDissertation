/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import com.google.android.gms.internal.zzcih;
import com.google.android.gms.internal.zzcik;
import com.google.android.gms.internal.zzcjk;
import java.util.concurrent.BlockingQueue;

final class zzcil
extends Thread {
    private /* synthetic */ zzcih zzjeq;
    private final Object zzjet;
    private final BlockingQueue<zzcik<?>> zzjeu;

    public zzcil(String string2, BlockingQueue<zzcik<?>> blockingQueue) {
        this.zzjeq = var1_1;
        zzbq.checkNotNull(string2);
        zzbq.checkNotNull(blockingQueue);
        this.zzjet = new Object();
        this.zzjeu = blockingQueue;
        this.setName(string2);
    }

    private final void zza(InterruptedException interruptedException) {
        ((zzcjk)this.zzjeq).zzawy().zzazf().zzj(String.valueOf(this.getName()).concat(" was interrupted"), interruptedException);
    }

    /*
     * Exception decompiling
     */
    @Override
    public final void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 25[UNCONDITIONALDOLOOP]
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzrk() {
        Object object = this.zzjet;
        synchronized (object) {
            this.zzjet.notifyAll();
            return;
        }
    }
}

