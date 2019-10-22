/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;

public final class zzclw
extends zzfjm<zzclw> {
    public Integer zzjko = null;
    public String zzjkp = null;
    public Boolean zzjkq = null;
    public String[] zzjkr = zzfjv.EMPTY_STRING_ARRAY;

    public zzclw() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Exception decompiling
     */
    private final zzclw zzi(zzfjj var1_1) throws IOException {
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

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block10: {
            block9: {
                if (object == this) break block9;
                if (!(object instanceof zzclw)) {
                    return false;
                }
                object = (zzclw)object;
                if (this.zzjko == null ? ((zzclw)object).zzjko != null : !this.zzjko.equals(((zzclw)object).zzjko)) {
                    return false;
                }
                if (this.zzjkp == null ? ((zzclw)object).zzjkp != null : !this.zzjkp.equals(((zzclw)object).zzjkp)) {
                    return false;
                }
                if (this.zzjkq == null ? ((zzclw)object).zzjkq != null : !this.zzjkq.equals(((zzclw)object).zzjkq)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzjkr, ((zzclw)object).zzjkr)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzclw)object).zzpnc);
                }
                if (((zzclw)object).zzpnc != null && !((zzclw)object).zzpnc.isEmpty()) break block10;
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
        int n3 = this.zzjko == null ? 0 : this.zzjko;
        int n4 = this.zzjkp == null ? 0 : this.zzjkp.hashCode();
        int n5 = this.zzjkq == null ? 0 : this.zzjkq.hashCode();
        int n6 = zzfjq.hashCode(this.zzjkr);
        int n7 = n;
        if (this.zzpnc == null) return ((n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31 + n6) * 31 + n7;
        if (this.zzpnc.isEmpty()) {
            n7 = n;
            return ((n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31 + n6) * 31 + n7;
        }
        n7 = this.zzpnc.hashCode();
        return ((n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31 + n6) * 31 + n7;
    }

    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        return this.zzi(zzfjj2);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzjko != null) {
            zzfjk2.zzaa(1, this.zzjko);
        }
        if (this.zzjkp != null) {
            zzfjk2.zzn(2, this.zzjkp);
        }
        if (this.zzjkq != null) {
            zzfjk2.zzl(3, this.zzjkq);
        }
        if (this.zzjkr != null && this.zzjkr.length > 0) {
            for (int i = 0; i < this.zzjkr.length; ++i) {
                String string2 = this.zzjkr[i];
                if (string2 == null) continue;
                zzfjk2.zzn(4, string2);
            }
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = 0;
        int n3 = n = super.zzq();
        if (this.zzjko != null) {
            n3 = n + zzfjk.zzad(1, this.zzjko);
        }
        n = n3;
        if (this.zzjkp != null) {
            n = n3 + zzfjk.zzo(2, this.zzjkp);
        }
        n3 = n;
        if (this.zzjkq != null) {
            this.zzjkq.booleanValue();
            n3 = n + (zzfjk.zzlg(3) + 1);
        }
        n = n3;
        if (this.zzjkr != null) {
            n = n3;
            if (this.zzjkr.length > 0) {
                int n4 = 0;
                int n5 = 0;
                for (n = n2; n < this.zzjkr.length; ++n) {
                    String string2 = this.zzjkr[n];
                    int n6 = n4;
                    n2 = n5;
                    if (string2 != null) {
                        n2 = n5 + 1;
                        n6 = n4 + zzfjk.zztt(string2);
                    }
                    n4 = n6;
                    n5 = n2;
                }
                n = n3 + n4 + n5 * 1;
            }
        }
        return n;
    }
}

