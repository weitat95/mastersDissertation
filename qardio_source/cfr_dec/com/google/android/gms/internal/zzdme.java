/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdmc;
import com.google.android.gms.internal.zzdmd;
import com.google.android.gms.internal.zzfjj;
import com.google.android.gms.internal.zzfjk;
import com.google.android.gms.internal.zzfjm;
import com.google.android.gms.internal.zzfjo;
import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;
import java.util.Arrays;

public final class zzdme
extends zzfjm<zzdme> {
    public byte[] zzlmn = zzfjv.zzpnv;
    public String zzlmo = "";
    public double zzlmp = 0.0;
    public float zzlmq = 0.0f;
    public long zzlmr = 0L;
    public int zzlms = 0;
    public int zzlmt = 0;
    public boolean zzlmu = false;
    public zzdmc[] zzlmv = zzdmc.zzbki();
    public zzdmd[] zzlmw = zzdmd.zzbkj();
    public String[] zzlmx = zzfjv.EMPTY_STRING_ARRAY;
    public long[] zzlmy = zzfjv.zzpnq;
    public float[] zzlmz = zzfjv.zzpnr;
    public long zzlna = 0L;

    public zzdme() {
        this.zzpnc = null;
        this.zzpfd = -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean equals(Object object) {
        block20: {
            block19: {
                if (object == this) break block19;
                if (!(object instanceof zzdme)) {
                    return false;
                }
                object = (zzdme)object;
                if (!Arrays.equals(this.zzlmn, ((zzdme)object).zzlmn)) {
                    return false;
                }
                if (this.zzlmo == null ? ((zzdme)object).zzlmo != null : !this.zzlmo.equals(((zzdme)object).zzlmo)) {
                    return false;
                }
                if (Double.doubleToLongBits(this.zzlmp) != Double.doubleToLongBits(((zzdme)object).zzlmp)) {
                    return false;
                }
                if (Float.floatToIntBits(this.zzlmq) != Float.floatToIntBits(((zzdme)object).zzlmq)) {
                    return false;
                }
                if (this.zzlmr != ((zzdme)object).zzlmr) {
                    return false;
                }
                if (this.zzlms != ((zzdme)object).zzlms) {
                    return false;
                }
                if (this.zzlmt != ((zzdme)object).zzlmt) {
                    return false;
                }
                if (this.zzlmu != ((zzdme)object).zzlmu) {
                    return false;
                }
                if (!zzfjq.equals(this.zzlmv, ((zzdme)object).zzlmv)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzlmw, ((zzdme)object).zzlmw)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzlmx, ((zzdme)object).zzlmx)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzlmy, ((zzdme)object).zzlmy)) {
                    return false;
                }
                if (!zzfjq.equals(this.zzlmz, ((zzdme)object).zzlmz)) {
                    return false;
                }
                if (this.zzlna != ((zzdme)object).zzlna) {
                    return false;
                }
                if (this.zzpnc != null && !this.zzpnc.isEmpty()) {
                    return this.zzpnc.equals(((zzdme)object).zzpnc);
                }
                if (((zzdme)object).zzpnc != null && !((zzdme)object).zzpnc.isEmpty()) break block20;
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
        int n3 = Arrays.hashCode(this.zzlmn);
        int n4 = this.zzlmo == null ? 0 : this.zzlmo.hashCode();
        long l = Double.doubleToLongBits(this.zzlmp);
        int n5 = (int)(l ^ l >>> 32);
        int n6 = Float.floatToIntBits(this.zzlmq);
        int n7 = (int)(this.zzlmr ^ this.zzlmr >>> 32);
        int n8 = this.zzlms;
        int n9 = this.zzlmt;
        int n10 = this.zzlmu ? 1231 : 1237;
        int n11 = zzfjq.hashCode(this.zzlmv);
        int n12 = zzfjq.hashCode(this.zzlmw);
        int n13 = zzfjq.hashCode(this.zzlmx);
        int n14 = zzfjq.hashCode(this.zzlmy);
        int n15 = zzfjq.hashCode(this.zzlmz);
        int n16 = (int)(this.zzlna ^ this.zzlna >>> 32);
        int n17 = n;
        if (this.zzpnc == null) return (((((((n10 + ((((((n4 + ((n2 + 527) * 31 + n3) * 31) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31) * 31 + n11) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n15) * 31 + n16) * 31 + n17;
        if (this.zzpnc.isEmpty()) {
            n17 = n;
            return (((((((n10 + ((((((n4 + ((n2 + 527) * 31 + n3) * 31) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31) * 31 + n11) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n15) * 31 + n16) * 31 + n17;
        }
        n17 = this.zzpnc.hashCode();
        return (((((((n10 + ((((((n4 + ((n2 + 527) * 31 + n3) * 31) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31) * 31 + n11) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n15) * 31 + n16) * 31 + n17;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final /* synthetic */ zzfjs zza(zzfjj zzfjj2) throws IOException {
        block19: do {
            int n;
            int n2;
            Object[] arrobject;
            int n3 = zzfjj2.zzcvt();
            switch (n3) {
                default: {
                    if (super.zza(zzfjj2, n3)) continue block19;
                }
                case 0: {
                    return this;
                }
                case 10: {
                    this.zzlmn = zzfjj2.readBytes();
                    continue block19;
                }
                case 18: {
                    this.zzlmo = zzfjj2.readString();
                    continue block19;
                }
                case 25: {
                    this.zzlmp = Double.longBitsToDouble(zzfjj2.zzcwp());
                    continue block19;
                }
                case 37: {
                    this.zzlmq = Float.intBitsToFloat(zzfjj2.zzcwo());
                    continue block19;
                }
                case 40: {
                    this.zzlmr = zzfjj2.zzcwn();
                    continue block19;
                }
                case 48: {
                    this.zzlms = zzfjj2.zzcwi();
                    continue block19;
                }
                case 56: {
                    n3 = zzfjj2.zzcwi();
                    this.zzlmt = -(n3 & 1) ^ n3 >>> 1;
                    continue block19;
                }
                case 64: {
                    this.zzlmu = zzfjj2.zzcvz();
                    continue block19;
                }
                case 74: {
                    n = zzfjv.zzb(zzfjj2, 74);
                    n3 = this.zzlmv == null ? 0 : this.zzlmv.length;
                    arrobject = new zzdmc[n + n3];
                    n = n3;
                    if (n3 != 0) {
                        System.arraycopy(this.zzlmv, 0, arrobject, 0, n3);
                        n = n3;
                    }
                    while (n < arrobject.length - 1) {
                        arrobject[n] = new zzdmc();
                        zzfjj2.zza((zzfjs)arrobject[n]);
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrobject[n] = new zzdmc();
                    zzfjj2.zza((zzfjs)arrobject[n]);
                    this.zzlmv = arrobject;
                    continue block19;
                }
                case 82: {
                    n = zzfjv.zzb(zzfjj2, 82);
                    n3 = this.zzlmw == null ? 0 : this.zzlmw.length;
                    arrobject = new zzdmd[n + n3];
                    n = n3;
                    if (n3 != 0) {
                        System.arraycopy(this.zzlmw, 0, arrobject, 0, n3);
                        n = n3;
                    }
                    while (n < arrobject.length - 1) {
                        arrobject[n] = new zzdmd();
                        zzfjj2.zza((zzfjs)arrobject[n]);
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrobject[n] = new zzdmd();
                    zzfjj2.zza((zzfjs)arrobject[n]);
                    this.zzlmw = arrobject;
                    continue block19;
                }
                case 90: {
                    n = zzfjv.zzb(zzfjj2, 90);
                    n3 = this.zzlmx == null ? 0 : this.zzlmx.length;
                    arrobject = new String[n + n3];
                    n = n3;
                    if (n3 != 0) {
                        System.arraycopy(this.zzlmx, 0, arrobject, 0, n3);
                        n = n3;
                    }
                    while (n < arrobject.length - 1) {
                        arrobject[n] = zzfjj2.readString();
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrobject[n] = zzfjj2.readString();
                    this.zzlmx = arrobject;
                    continue block19;
                }
                case 96: {
                    n = zzfjv.zzb(zzfjj2, 96);
                    n3 = this.zzlmy == null ? 0 : this.zzlmy.length;
                    arrobject = new long[n + n3];
                    n = n3;
                    if (n3 != 0) {
                        System.arraycopy(this.zzlmy, 0, arrobject, 0, n3);
                        n = n3;
                    }
                    while (n < arrobject.length - 1) {
                        arrobject[n] = zzfjj2.zzcwn();
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrobject[n] = zzfjj2.zzcwn();
                    this.zzlmy = arrobject;
                    continue block19;
                }
                case 98: {
                    n2 = zzfjj2.zzks(zzfjj2.zzcwi());
                    n3 = zzfjj2.getPosition();
                    n = 0;
                    while (zzfjj2.zzcwk() > 0) {
                        zzfjj2.zzcwn();
                        ++n;
                    }
                    zzfjj2.zzmg(n3);
                    n3 = this.zzlmy == null ? 0 : this.zzlmy.length;
                    arrobject = new long[n + n3];
                    n = n3;
                    if (n3 != 0) {
                        System.arraycopy(this.zzlmy, 0, arrobject, 0, n3);
                        n = n3;
                    }
                    while (n < arrobject.length) {
                        arrobject[n] = zzfjj2.zzcwn();
                        ++n;
                    }
                    this.zzlmy = arrobject;
                    zzfjj2.zzkt(n2);
                    continue block19;
                }
                case 104: {
                    this.zzlna = zzfjj2.zzcwn();
                    continue block19;
                }
                case 117: {
                    n = zzfjv.zzb(zzfjj2, 117);
                    n3 = this.zzlmz == null ? 0 : this.zzlmz.length;
                    arrobject = new float[n + n3];
                    n = n3;
                    if (n3 != 0) {
                        System.arraycopy(this.zzlmz, 0, arrobject, 0, n3);
                        n = n3;
                    }
                    while (n < arrobject.length - 1) {
                        arrobject[n] = Float.intBitsToFloat(zzfjj2.zzcwo());
                        zzfjj2.zzcvt();
                        ++n;
                    }
                    arrobject[n] = Float.intBitsToFloat(zzfjj2.zzcwo());
                    this.zzlmz = arrobject;
                    continue block19;
                }
                case 114: 
            }
            n3 = zzfjj2.zzcwi();
            n2 = zzfjj2.zzks(n3);
            n = n3 / 4;
            n3 = this.zzlmz == null ? 0 : this.zzlmz.length;
            arrobject = new float[n + n3];
            n = n3;
            if (n3 != 0) {
                System.arraycopy(this.zzlmz, 0, arrobject, 0, n3);
                n = n3;
            }
            while (n < arrobject.length) {
                arrobject[n] = Float.intBitsToFloat(zzfjj2.zzcwo());
                ++n;
            }
            this.zzlmz = arrobject;
            zzfjj2.zzkt(n2);
        } while (true);
    }

    @Override
    public final void zza(zzfjk zzfjk2) throws IOException {
        Object object;
        int n;
        int n2 = 0;
        if (!Arrays.equals(this.zzlmn, zzfjv.zzpnv)) {
            zzfjk2.zzc(1, this.zzlmn);
        }
        if (this.zzlmo != null && !this.zzlmo.equals("")) {
            zzfjk2.zzn(2, this.zzlmo);
        }
        if (Double.doubleToLongBits(this.zzlmp) != Double.doubleToLongBits(0.0)) {
            zzfjk2.zza(3, this.zzlmp);
        }
        if (Float.floatToIntBits(this.zzlmq) != Float.floatToIntBits(0.0f)) {
            zzfjk2.zzc(4, this.zzlmq);
        }
        if (this.zzlmr != 0L) {
            zzfjk2.zzf(5, this.zzlmr);
        }
        if (this.zzlms != 0) {
            zzfjk2.zzaa(6, this.zzlms);
        }
        if (this.zzlmt != 0) {
            n = this.zzlmt;
            zzfjk2.zzz(7, 0);
            zzfjk2.zzmi(zzfjk.zzlo(n));
        }
        if (this.zzlmu) {
            zzfjk2.zzl(8, this.zzlmu);
        }
        if (this.zzlmv != null && this.zzlmv.length > 0) {
            for (n = 0; n < this.zzlmv.length; ++n) {
                object = this.zzlmv[n];
                if (object == null) continue;
                zzfjk2.zza(9, (zzfjs)object);
            }
        }
        if (this.zzlmw != null && this.zzlmw.length > 0) {
            for (n = 0; n < this.zzlmw.length; ++n) {
                object = this.zzlmw[n];
                if (object == null) continue;
                zzfjk2.zza(10, (zzfjs)object);
            }
        }
        if (this.zzlmx != null && this.zzlmx.length > 0) {
            for (n = 0; n < this.zzlmx.length; ++n) {
                object = this.zzlmx[n];
                if (object == null) continue;
                zzfjk2.zzn(11, (String)object);
            }
        }
        if (this.zzlmy != null && this.zzlmy.length > 0) {
            for (n = 0; n < this.zzlmy.length; ++n) {
                zzfjk2.zzf(12, this.zzlmy[n]);
            }
        }
        if (this.zzlna != 0L) {
            zzfjk2.zzf(13, this.zzlna);
        }
        if (this.zzlmz != null && this.zzlmz.length > 0) {
            for (n = n2; n < this.zzlmz.length; ++n) {
                zzfjk2.zzc(14, this.zzlmz[n]);
            }
        }
        super.zza(zzfjk2);
    }

    @Override
    protected final int zzq() {
        int n;
        Object object;
        int n2 = 0;
        int n3 = n = super.zzq();
        if (!Arrays.equals(this.zzlmn, zzfjv.zzpnv)) {
            n3 = n + zzfjk.zzd(1, this.zzlmn);
        }
        n = n3;
        if (this.zzlmo != null) {
            n = n3;
            if (!this.zzlmo.equals("")) {
                n = n3 + zzfjk.zzo(2, this.zzlmo);
            }
        }
        n3 = n;
        if (Double.doubleToLongBits(this.zzlmp) != Double.doubleToLongBits(0.0)) {
            n3 = n + (zzfjk.zzlg(3) + 8);
        }
        n = n3;
        if (Float.floatToIntBits(this.zzlmq) != Float.floatToIntBits(0.0f)) {
            n = n3 + (zzfjk.zzlg(4) + 4);
        }
        int n4 = n;
        if (this.zzlmr != 0L) {
            n4 = n + zzfjk.zzc(5, this.zzlmr);
        }
        n3 = n4;
        if (this.zzlms != 0) {
            n3 = n4 + zzfjk.zzad(6, this.zzlms);
        }
        n = n3;
        if (this.zzlmt != 0) {
            n = this.zzlmt;
            n4 = zzfjk.zzlg(7);
            n = n3 + (zzfjk.zzlp(zzfjk.zzlo(n)) + n4);
        }
        n3 = n;
        if (this.zzlmu) {
            n3 = n + (zzfjk.zzlg(8) + 1);
        }
        n = n3;
        if (this.zzlmv != null) {
            n = n3;
            if (this.zzlmv.length > 0) {
                for (n = 0; n < this.zzlmv.length; ++n) {
                    object = this.zzlmv[n];
                    n4 = n3;
                    if (object != null) {
                        n4 = n3 + zzfjk.zzb(9, (zzfjs)object);
                    }
                    n3 = n4;
                }
                n = n3;
            }
        }
        n3 = n;
        if (this.zzlmw != null) {
            n3 = n;
            if (this.zzlmw.length > 0) {
                n3 = n;
                for (n = 0; n < this.zzlmw.length; ++n) {
                    object = this.zzlmw[n];
                    n4 = n3;
                    if (object != null) {
                        n4 = n3 + zzfjk.zzb(10, (zzfjs)object);
                    }
                    n3 = n4;
                }
            }
        }
        n = n3;
        if (this.zzlmx != null) {
            n = n3;
            if (this.zzlmx.length > 0) {
                n4 = 0;
                int n5 = 0;
                for (n = 0; n < this.zzlmx.length; ++n) {
                    object = this.zzlmx[n];
                    int n6 = n4;
                    int n7 = n5;
                    if (object != null) {
                        n7 = n5 + 1;
                        n6 = n4 + zzfjk.zztt((String)object);
                    }
                    n4 = n6;
                    n5 = n7;
                }
                n = n3 + n4 + n5 * 1;
            }
        }
        n3 = n;
        if (this.zzlmy != null) {
            n3 = n;
            if (this.zzlmy.length > 0) {
                n4 = 0;
                for (n3 = n2; n3 < this.zzlmy.length; ++n3) {
                    n4 += zzfjk.zzdi(this.zzlmy[n3]);
                }
                n3 = n + n4 + this.zzlmy.length * 1;
            }
        }
        n = n3;
        if (this.zzlna != 0L) {
            n = n3 + zzfjk.zzc(13, this.zzlna);
        }
        n3 = n;
        if (this.zzlmz != null) {
            n3 = n;
            if (this.zzlmz.length > 0) {
                n3 = n + this.zzlmz.length * 4 + this.zzlmz.length * 1;
            }
        }
        return n3;
    }
}

