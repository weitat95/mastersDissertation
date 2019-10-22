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

public final class zzbs
extends zzfjm<zzbs> {
    private static volatile zzbs[] zzyk;
    public String string = "";
    public int type = 1;
    public zzbs[] zzyl = zzbs.zzx();
    public zzbs[] zzym = zzbs.zzx();
    public zzbs[] zzyn = zzbs.zzx();
    public String zzyo = "";
    public String zzyp = "";
    public long zzyq = 0L;
    public boolean zzyr = false;
    public zzbs[] zzys = zzbs.zzx();
    public int[] zzyt = zzfjv.zzpnp;
    public boolean zzyu = false;

    public zzbs() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    private static int zze(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException(new StringBuilder(40).append(n).append(" is not a valid enum Escaping").toString());
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
        }
        return n;
    }

    /*
     * Exception decompiling
     */
    private final zzbs zzf(zzfjj var1_1) throws IOException {
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
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzbs[] zzx() {
        if (zzyk == null) {
            Object object = zzfjq.zzpnk;
            synchronized (object) {
                if (zzyk == null) {
                    zzyk = new zzbs[0];
                }
            }
        }
        return zzyk;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block18: {
            block17: {
                if (object == this) break block17;
                if (!(object instanceof zzbs)) {
                    return false;
                }
                object = (zzbs)object;
                if (this.type != ((zzbs)object).type) {
                    return false;
                }
                if (this.string == null ? ((zzbs)object).string != null : !this.string.equals(((zzbs)object).string)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzyl, ((zzbs)object).zzyl)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzym, ((zzbs)object).zzym)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzyn, ((zzbs)object).zzyn)) {
                    return false;
                }
                if (this.zzyo == null ? ((zzbs)object).zzyo != null : !this.zzyo.equals(((zzbs)object).zzyo)) {
                    return false;
                }
                if (this.zzyp == null ? ((zzbs)object).zzyp != null : !this.zzyp.equals(((zzbs)object).zzyp)) {
                    return false;
                }
                if (this.zzyq != ((zzbs)object).zzyq) {
                    return false;
                }
                if (this.zzyr != ((zzbs)object).zzyr) {
                    return false;
                }
                if (!zzfjq.equals(this.zzys, ((zzbs)object).zzys)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzyt, ((zzbs)object).zzyt)) {
                    return false;
                }
                if (this.zzyu != ((zzbs)object).zzyu) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzbs)object).zzpnc);
                }
                if (((zzbs)object).zzpnc != null && !((zzbs)object).zzpnc.isEmpty()) break block18;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int hashCode() {
        int n = 1231;
        int n2 = 0;
        int n3 = this.getClass().getName().hashCode();
        int n4 = this.type;
        int n5 = this.string == null ? 0 : this.string.hashCode();
        int n6 = zzfjq.hashCode(this.zzyl);
        int n7 = zzfjq.hashCode(this.zzym);
        int n8 = zzfjq.hashCode(this.zzyn);
        int n9 = this.zzyo == null ? 0 : this.zzyo.hashCode();
        int n10 = this.zzyp == null ? 0 : this.zzyp.hashCode();
        int n11 = (int)(this.zzyq ^ this.zzyq >>> 32);
        int n12 = this.zzyr ? 1231 : 1237;
        int n13 = zzfjq.hashCode(this.zzys);
        int n14 = zzfjq.hashCode(this.zzyt);
        if (!this.zzyu) {
            n = 1237;
        }
        int n15 = n2;
        if (this.zzpnc == null) return ((((n12 + ((n10 + (n9 + ((((n5 + ((n3 + 527) * 31 + n4) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31) * 31) * 31 + n11) * 31) * 31 + n13) * 31 + n14) * 31 + n) * 31 + n15;
        if (this.zzpnc.isEmpty()) {
            n15 = n2;
            return ((((n12 + ((n10 + (n9 + ((((n5 + ((n3 + 527) * 31 + n4) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31) * 31) * 31 + n11) * 31) * 31 + n13) * 31 + n14) * 31 + n) * 31 + n15;
        }
        n15 = this.zzpnc.hashCode();
        return ((((n12 + ((n10 + (n9 + ((((n5 + ((n3 + 527) * 31 + n4) * 31) * 31 + n6) * 31 + n7) * 31 + n8) * 31) * 31) * 31 + n11) * 31) * 31 + n13) * 31 + n14) * 31 + n) * 31 + n15;
    }

    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        return this.zzf(zzfjj2);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        int n;
        zzbs zzbs2;
        int n2 = 0;
        zzfjk2.zzaa(1, this.type);
        if (this.string != null && !this.string.equals("")) {
            zzfjk2.zzn(2, this.string);
        }
        if (this.zzyl != null && this.zzyl.length > 0) {
            for (n = 0; n < this.zzyl.length; ++n) {
                zzbs2 = this.zzyl[n];
                if (zzbs2 == null) continue;
                zzfjk2.zza(3, zzbs2);
            }
        }
        if (this.zzym != null && this.zzym.length > 0) {
            for (n = 0; n < this.zzym.length; ++n) {
                zzbs2 = this.zzym[n];
                if (zzbs2 == null) continue;
                zzfjk2.zza(4, zzbs2);
            }
        }
        if (this.zzyn != null && this.zzyn.length > 0) {
            for (n = 0; n < this.zzyn.length; ++n) {
                zzbs2 = this.zzyn[n];
                if (zzbs2 == null) continue;
                zzfjk2.zza(5, zzbs2);
            }
        }
        if (this.zzyo != null && !this.zzyo.equals("")) {
            zzfjk2.zzn(6, this.zzyo);
        }
        if (this.zzyp != null && !this.zzyp.equals("")) {
            zzfjk2.zzn(7, this.zzyp);
        }
        if (this.zzyq != 0L) {
            zzfjk2.zzf(8, this.zzyq);
        }
        if (this.zzyu) {
            zzfjk2.zzl(9, this.zzyu);
        }
        if (this.zzyt != null && this.zzyt.length > 0) {
            for (n = 0; n < this.zzyt.length; ++n) {
                zzfjk2.zzaa(10, this.zzyt[n]);
            }
        }
        if (this.zzys != null && this.zzys.length > 0) {
            for (n = n2; n < this.zzys.length; ++n) {
                zzbs2 = this.zzys[n];
                if (zzbs2 == null) continue;
                zzfjk2.zza(11, zzbs2);
            }
        }
        if (this.zzyr) {
            zzfjk2.zzl(12, this.zzyr);
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        zzbs zzbs2;
        int n2;
        int n3 = 0;
        int n4 = n2 = super.zzq() + zzfjk.zzad(1, this.type);
        if (this.string != null) {
            n4 = n2;
            if (!this.string.equals("")) {
                n4 = n2 + zzfjk.zzo(2, this.string);
            }
        }
        n2 = n4;
        if (this.zzyl != null) {
            n2 = n4;
            if (this.zzyl.length > 0) {
                for (n2 = 0; n2 < this.zzyl.length; ++n2) {
                    zzbs2 = this.zzyl[n2];
                    n = n4;
                    if (zzbs2 != null) {
                        n = n4 + zzfjk.zzb(3, zzbs2);
                    }
                    n4 = n;
                }
                n2 = n4;
            }
        }
        n4 = n2;
        if (this.zzym != null) {
            n4 = n2;
            if (this.zzym.length > 0) {
                n4 = n2;
                for (n2 = 0; n2 < this.zzym.length; ++n2) {
                    zzbs2 = this.zzym[n2];
                    n = n4;
                    if (zzbs2 != null) {
                        n = n4 + zzfjk.zzb(4, zzbs2);
                    }
                    n4 = n;
                }
            }
        }
        n2 = n4;
        if (this.zzyn != null) {
            n2 = n4;
            if (this.zzyn.length > 0) {
                for (n2 = 0; n2 < this.zzyn.length; ++n2) {
                    zzbs2 = this.zzyn[n2];
                    n = n4;
                    if (zzbs2 != null) {
                        n = n4 + zzfjk.zzb(5, zzbs2);
                    }
                    n4 = n;
                }
                n2 = n4;
            }
        }
        n4 = n2;
        if (this.zzyo != null) {
            n4 = n2;
            if (!this.zzyo.equals("")) {
                n4 = n2 + zzfjk.zzo(6, this.zzyo);
            }
        }
        n2 = n4;
        if (this.zzyp != null) {
            n2 = n4;
            if (!this.zzyp.equals("")) {
                n2 = n4 + zzfjk.zzo(7, this.zzyp);
            }
        }
        n4 = n2;
        if (this.zzyq != 0L) {
            n4 = n2 + zzfjk.zzc(8, this.zzyq);
        }
        n2 = n4;
        if (this.zzyu) {
            n2 = n4 + (zzfjk.zzlg(9) + 1);
        }
        n4 = n2;
        if (this.zzyt != null) {
            n4 = n2;
            if (this.zzyt.length > 0) {
                n = 0;
                for (n4 = 0; n4 < this.zzyt.length; ++n4) {
                    n += zzfjk.zzlh(this.zzyt[n4]);
                }
                n4 = n2 + n + this.zzyt.length * 1;
            }
        }
        n2 = n4;
        if (this.zzys != null) {
            n2 = n4;
            if (this.zzys.length > 0) {
                n = n3;
                do {
                    n2 = n4;
                    if (n >= this.zzys.length) break;
                    zzbs2 = this.zzys[n];
                    n2 = n4;
                    if (zzbs2 != null) {
                        n2 = n4 + zzfjk.zzb(11, zzbs2);
                    }
                    ++n;
                    n4 = n2;
                } while (true);
            }
        }
        n4 = n2;
        if (this.zzyr) {
            n4 = n2 + (zzfjk.zzlg(12) + 1);
        }
        return n4;
    }
}

