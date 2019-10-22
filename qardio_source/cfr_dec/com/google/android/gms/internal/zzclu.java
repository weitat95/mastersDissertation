/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjs;
import java.io.IOException;

public final class zzclu
extends zzfjm<zzclu> {
    public Integer zzjkg = null;
    public Boolean zzjkh = null;
    public String zzjki = null;
    public String zzjkj = null;
    public String zzjkk = null;

    public zzclu() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Exception decompiling
     */
    private final zzclu zzh(zzfjj var1_1) throws IOException {
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
        block11: {
            block10: {
                if (object == this) break block10;
                if (!(object instanceof zzclu)) {
                    return false;
                }
                object = (zzclu)object;
                if (this.zzjkg == null ? ((zzclu)object).zzjkg != null : !this.zzjkg.equals(((zzclu)object).zzjkg)) {
                    return false;
                }
                if (this.zzjkh == null ? ((zzclu)object).zzjkh != null : !this.zzjkh.equals(((zzclu)object).zzjkh)) {
                    return false;
                }
                if (this.zzjki == null ? ((zzclu)object).zzjki != null : !this.zzjki.equals(((zzclu)object).zzjki)) {
                    return false;
                }
                if (this.zzjkj == null ? ((zzclu)object).zzjkj != null : !this.zzjkj.equals(((zzclu)object).zzjkj)) {
                    return false;
                }
                if (this.zzjkk == null ? ((zzclu)object).zzjkk != null : !this.zzjkk.equals(((zzclu)object).zzjkk)) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzclu)object).zzpnc);
                }
                if (((zzclu)object).zzpnc != null && !((zzclu)object).zzpnc.isEmpty()) break block11;
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
        int n3 = this.zzjkg == null ? 0 : this.zzjkg;
        int n4 = this.zzjkh == null ? 0 : this.zzjkh.hashCode();
        int n5 = this.zzjki == null ? 0 : this.zzjki.hashCode();
        int n6 = this.zzjkj == null ? 0 : this.zzjkj.hashCode();
        int n7 = this.zzjkk == null ? 0 : this.zzjkk.hashCode();
        int n8 = n;
        if (this.zzpnc == null) return (n7 + (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31) * 31 + n8;
        if (this.zzpnc.isEmpty()) {
            n8 = n;
            return (n7 + (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31) * 31 + n8;
        }
        n8 = this.zzpnc.hashCode();
        return (n7 + (n6 + (n5 + (n4 + (n3 + (n2 + 527) * 31) * 31) * 31) * 31) * 31) * 31 + n8;
    }

    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        return this.zzh(zzfjj2);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzjkg != null) {
            zzfjk2.zzaa(1, this.zzjkg);
        }
        if (this.zzjkh != null) {
            zzfjk2.zzl(2, this.zzjkh);
        }
        if (this.zzjki != null) {
            zzfjk2.zzn(3, this.zzjki);
        }
        if (this.zzjkj != null) {
            zzfjk2.zzn(4, this.zzjkj);
        }
        if (this.zzjkk != null) {
            zzfjk2.zzn(5, this.zzjkk);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzjkg != null) {
            n2 = n + zzfjk.zzad(1, this.zzjkg);
        }
        n = n2;
        if (this.zzjkh != null) {
            this.zzjkh.booleanValue();
            n = n2 + (zzfjk.zzlg(2) + 1);
        }
        n2 = n;
        if (this.zzjki != null) {
            n2 = n + zzfjk.zzo(3, this.zzjki);
        }
        n = n2;
        if (this.zzjkj != null) {
            n = n2 + zzfjk.zzo(4, this.zzjkj);
        }
        n2 = n;
        if (this.zzjkk != null) {
            n2 = n + zzfjk.zzo(5, this.zzjkk);
        }
        return n2;
    }
}

