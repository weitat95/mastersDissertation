/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjr;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import com.google.android.gms.internal.zzfkt;
import java.io.IOException;

public final class zzfku
extends zzfjm<zzfku> {
    public long zzghq = 0L;
    public String zzpri = "";
    public String zzprj = "";
    public long zzprk = 0L;
    public String zzprl = "";
    public long zzprm = 0L;
    public String zzprn = "";
    public String zzpro = "";
    public String zzprp = "";
    public String zzprq = "";
    public String zzprr = "";
    public int zzprs = 0;
    public zzfkt[] zzprt = zzfkt.zzdbd();

    public zzfku() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    public static zzfku zzbi(byte[] arrby) throws zzfjr {
        return zzfjs.zza(new zzfku(), arrby);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block16: do {
            int n = zzfjj2.zzcvt();
            switch (n) {
                default: {
                    if (super.zza(zzfjj2, n)) continue block16;
                }
                case 0: {
                    return this;
                }
                case 10: {
                    this.zzpri = zzfjj2.readString();
                    continue block16;
                }
                case 18: {
                    this.zzprj = zzfjj2.readString();
                    continue block16;
                }
                case 24: {
                    this.zzprk = zzfjj2.zzcvv();
                    continue block16;
                }
                case 34: {
                    this.zzprl = zzfjj2.readString();
                    continue block16;
                }
                case 40: {
                    this.zzprm = zzfjj2.zzcvv();
                    continue block16;
                }
                case 48: {
                    this.zzghq = zzfjj2.zzcvv();
                    continue block16;
                }
                case 58: {
                    this.zzprn = zzfjj2.readString();
                    continue block16;
                }
                case 66: {
                    this.zzpro = zzfjj2.readString();
                    continue block16;
                }
                case 74: {
                    this.zzprp = zzfjj2.readString();
                    continue block16;
                }
                case 82: {
                    this.zzprq = zzfjj2.readString();
                    continue block16;
                }
                case 90: {
                    this.zzprr = zzfjj2.readString();
                    continue block16;
                }
                case 96: {
                    this.zzprs = zzfjj2.zzcvw();
                    continue block16;
                }
                case 106: 
            }
            int n2 = zzfjv.zzb(zzfjj2, 106);
            n = this.zzprt == null ? 0 : this.zzprt.length;
            zzfkt[] arrzzfkt = new zzfkt[n2 + n];
            n2 = n;
            if (n != 0) {
                System.arraycopy(this.zzprt, 0, arrzzfkt, 0, n);
                n2 = n;
            }
            while (n2 < arrzzfkt.length - 1) {
                arrzzfkt[n2] = new zzfkt();
                zzfjj2.zza(arrzzfkt[n2]);
                zzfjj2.zzcvt();
                ++n2;
            }
            arrzzfkt[n2] = new zzfkt();
            zzfjj2.zza(arrzzfkt[n2]);
            this.zzprt = arrzzfkt;
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        if (this.zzpri != null && !this.zzpri.equals("")) {
            zzfjk2.zzn(1, this.zzpri);
        }
        if (this.zzprj != null && !this.zzprj.equals("")) {
            zzfjk2.zzn(2, this.zzprj);
        }
        if (this.zzprk != 0L) {
            zzfjk2.zzf(3, this.zzprk);
        }
        if (this.zzprl != null && !this.zzprl.equals("")) {
            zzfjk2.zzn(4, this.zzprl);
        }
        if (this.zzprm != 0L) {
            zzfjk2.zzf(5, this.zzprm);
        }
        if (this.zzghq != 0L) {
            zzfjk2.zzf(6, this.zzghq);
        }
        if (this.zzprn != null && !this.zzprn.equals("")) {
            zzfjk2.zzn(7, this.zzprn);
        }
        if (this.zzpro != null && !this.zzpro.equals("")) {
            zzfjk2.zzn(8, this.zzpro);
        }
        if (this.zzprp != null && !this.zzprp.equals("")) {
            zzfjk2.zzn(9, this.zzprp);
        }
        if (this.zzprq != null && !this.zzprq.equals("")) {
            zzfjk2.zzn(10, this.zzprq);
        }
        if (this.zzprr != null && !this.zzprr.equals("")) {
            zzfjk2.zzn(11, this.zzprr);
        }
        if (this.zzprs != 0) {
            zzfjk2.zzaa(12, this.zzprs);
        }
        if (this.zzprt != null && this.zzprt.length > 0) {
            for (int i = 0; i < this.zzprt.length; ++i) {
                zzfkt zzfkt2 = this.zzprt[i];
                if (zzfkt2 == null) continue;
                zzfjk2.zza(13, zzfkt2);
            }
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        int n2 = n = super.zzq();
        if (this.zzpri != null) {
            n2 = n;
            if (!this.zzpri.equals("")) {
                n2 = n + zzfjk.zzo(1, this.zzpri);
            }
        }
        n = n2;
        if (this.zzprj != null) {
            n = n2;
            if (!this.zzprj.equals("")) {
                n = n2 + zzfjk.zzo(2, this.zzprj);
            }
        }
        n2 = n;
        if (this.zzprk != 0L) {
            n2 = n + zzfjk.zzc(3, this.zzprk);
        }
        n = n2;
        if (this.zzprl != null) {
            n = n2;
            if (!this.zzprl.equals("")) {
                n = n2 + zzfjk.zzo(4, this.zzprl);
            }
        }
        n2 = n;
        if (this.zzprm != 0L) {
            n2 = n + zzfjk.zzc(5, this.zzprm);
        }
        n = n2;
        if (this.zzghq != 0L) {
            n = n2 + zzfjk.zzc(6, this.zzghq);
        }
        n2 = n;
        if (this.zzprn != null) {
            n2 = n;
            if (!this.zzprn.equals("")) {
                n2 = n + zzfjk.zzo(7, this.zzprn);
            }
        }
        n = n2;
        if (this.zzpro != null) {
            n = n2;
            if (!this.zzpro.equals("")) {
                n = n2 + zzfjk.zzo(8, this.zzpro);
            }
        }
        n2 = n;
        if (this.zzprp != null) {
            n2 = n;
            if (!this.zzprp.equals("")) {
                n2 = n + zzfjk.zzo(9, this.zzprp);
            }
        }
        n = n2;
        if (this.zzprq != null) {
            n = n2;
            if (!this.zzprq.equals("")) {
                n = n2 + zzfjk.zzo(10, this.zzprq);
            }
        }
        int n3 = n;
        if (this.zzprr != null) {
            n3 = n;
            if (!this.zzprr.equals("")) {
                n3 = n + zzfjk.zzo(11, this.zzprr);
            }
        }
        n2 = n3;
        if (this.zzprs != 0) {
            n2 = n3 + zzfjk.zzad(12, this.zzprs);
        }
        n = n2;
        if (this.zzprt != null) {
            n = n2;
            if (this.zzprt.length > 0) {
                for (n = 0; n < this.zzprt.length; ++n) {
                    zzfkt zzfkt2 = this.zzprt[n];
                    n3 = n2;
                    if (zzfkt2 != null) {
                        n3 = n2 + zzfjk.zzb(13, zzfkt2);
                    }
                    n2 = n3;
                }
                n = n2;
            }
        }
        return n;
    }
}

