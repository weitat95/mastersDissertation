/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjs;

public final class zzfjn<M extends zzfjm<M>, T> {
    public final int tag;
    private int type;
    protected final Class<T> zznfk;
    protected final boolean zzpnd;

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof zzfjn)) {
                    return false;
                }
                object = (zzfjn)object;
                if (this.type != ((zzfjn)object).type || this.zznfk != ((zzfjn)object).zznfk || this.tag != ((zzfjn)object).tag || this.zzpnd != ((zzfjn)object).zzpnd) break block5;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int hashCode() {
        int n;
        int n2 = this.type;
        int n3 = this.zznfk.hashCode();
        int n4 = this.tag;
        if (this.zzpnd) {
            n = 1;
            do {
                return n + (((n2 + 1147) * 31 + n3) * 31 + n4) * 31;
                break;
            } while (true);
        }
        n = 0;
        return n + (((n2 + 1147) * 31 + n3) * 31 + n4) * 31;
    }

    /*
     * Exception decompiling
     */
    protected final void zza(Object var1_1, zzfjk var2_3) {
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

    protected final int zzcs(Object object) {
        int n = this.tag >>> 3;
        switch (this.type) {
            default: {
                n = this.type;
                throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(n).toString());
            }
            case 10: {
                object = (zzfjs)object;
                return (zzfjk.zzlg(n) << 1) + ((zzfjs)object).zzho();
            }
            case 11: 
        }
        return zzfjk.zzb(n, (zzfjs)object);
    }
}

