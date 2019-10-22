/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdme;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import java.io.IOException;

public final class zzdmd
extends zzfjm<zzdmd> {
    private static volatile zzdmd[] zzlml;
    public int type = 1;
    public zzdme zzlmm = null;

    public zzdmd() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzdmd[] zzbkj() {
        if (zzlml == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzlml == null) {
                    zzlml = new zzdmd[0];
                }
            }
        }
        return zzlml;
    }

    /*
     * Exception decompiling
     */
    private final zzdmd zzj(zzfjj var1_1) throws IOException {
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

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block8: {
            block7: {
                if (object == this) break block7;
                if (!(object instanceof zzdmd)) {
                    return false;
                }
                object = (zzdmd)object;
                if (this.type != ((zzdmd)object).type) {
                    return false;
                }
                if (this.zzlmm == null ? ((zzdmd)object).zzlmm != null : !this.zzlmm.equals(((zzdmd)object).zzlmm)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzdmd)object).zzpnc);
                }
                if (((zzdmd)object).zzpnc != null && !((zzdmd)object).zzpnc.isEmpty()) break block8;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int hashCode() {
        int n = 0;
        int n2 = this.getClass().getName().hashCode();
        int n3 = this.type;
        zzdme zzdme2 = this.zzlmm;
        int n4 = zzdme2 == null ? 0 : zzdme2.hashCode();
        int n5 = n;
        if (this.zzpnc == null) return (n4 + ((n2 + 527) * 31 + n3) * 31) * 31 + n5;
        if (this.zzpnc.isEmpty()) {
            n5 = n;
            return (n4 + ((n2 + 527) * 31 + n3) * 31) * 31 + n5;
        }
        n5 = this.zzpnc.hashCode();
        return (n4 + ((n2 + 527) * 31 + n3) * 31) * 31 + n5;
    }

    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        return this.zzj(zzfjj2);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        zzfjk2.zzaa(1, this.type);
        if (this.zzlmm != null) {
            zzfjk2.zza(2, this.zzlmm);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq() + zzfjk.zzad(1, this.type);
        if (this.zzlmm != null) {
            n2 = n + zzfjk.zzb(2, this.zzlmm);
        }
        return n2;
    }
}

