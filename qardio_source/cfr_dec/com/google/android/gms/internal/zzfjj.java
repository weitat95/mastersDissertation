/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjq;
import com.google.android.gms.internal.zzfjr;
import com.google.android.gms.internal.zzfjs;
import com.google.android.gms.internal.zzfjv;
import java.io.IOException;
import java.nio.charset.Charset;

public final class zzfjj {
    private final byte[] buffer;
    private int zzpfm;
    private int zzpfn = 64;
    private int zzpfo = 67108864;
    private int zzpfr;
    private int zzpft;
    private int zzpfu = Integer.MAX_VALUE;
    private final int zzpfw;
    private final int zzpmz;
    private int zzpna;
    private int zzpnb;

    private zzfjj(byte[] arrby, int n, int n2) {
        this.buffer = arrby;
        this.zzpmz = n;
        this.zzpna = n2 = n + n2;
        this.zzpfw = n2;
        this.zzpnb = n;
    }

    private final void zzcwq() {
        this.zzpna += this.zzpfr;
        int n = this.zzpna;
        if (n > this.zzpfu) {
            this.zzpfr = n - this.zzpfu;
            this.zzpna -= this.zzpfr;
            return;
        }
        this.zzpfr = 0;
    }

    private final byte zzcwr() throws IOException {
        if (this.zzpnb == this.zzpna) {
            throw zzfjr.zzdai();
        }
        byte[] arrby = this.buffer;
        int n = this.zzpnb;
        this.zzpnb = n + 1;
        return arrby[n];
    }

    private final void zzku(int n) throws IOException {
        if (n < 0) {
            throw zzfjr.zzdaj();
        }
        if (this.zzpnb + n > this.zzpfu) {
            this.zzku(this.zzpfu - this.zzpnb);
            throw zzfjr.zzdai();
        }
        if (n <= this.zzpna - this.zzpnb) {
            this.zzpnb += n;
            return;
        }
        throw zzfjr.zzdai();
    }

    public static zzfjj zzn(byte[] arrby, int n, int n2) {
        return new zzfjj(arrby, 0, n2);
    }

    public final int getPosition() {
        return this.zzpnb - this.zzpmz;
    }

    public final byte[] readBytes() throws IOException {
        int n = this.zzcwi();
        if (n < 0) {
            throw zzfjr.zzdaj();
        }
        if (n == 0) {
            return zzfjv.zzpnv;
        }
        if (n > this.zzpna - this.zzpnb) {
            throw zzfjr.zzdai();
        }
        byte[] arrby = new byte[n];
        System.arraycopy(this.buffer, this.zzpnb, arrby, 0, n);
        this.zzpnb = n + this.zzpnb;
        return arrby;
    }

    public final String readString() throws IOException {
        int n = this.zzcwi();
        if (n < 0) {
            throw zzfjr.zzdaj();
        }
        if (n > this.zzpna - this.zzpnb) {
            throw zzfjr.zzdai();
        }
        String string2 = new String(this.buffer, this.zzpnb, n, zzfjq.UTF_8);
        this.zzpnb = n + this.zzpnb;
        return string2;
    }

    public final void zza(zzfjs zzfjs2) throws IOException {
        int n = this.zzcwi();
        if (this.zzpfm >= this.zzpfn) {
            throw zzfjr.zzdal();
        }
        n = this.zzks(n);
        ++this.zzpfm;
        zzfjs2.zza(this);
        this.zzkp(0);
        --this.zzpfm;
        this.zzkt(n);
    }

    public final byte[] zzal(int n, int n2) {
        if (n2 == 0) {
            return zzfjv.zzpnv;
        }
        byte[] arrby = new byte[n2];
        int n3 = this.zzpmz;
        System.arraycopy(this.buffer, n3 + n, arrby, 0, n2);
        return arrby;
    }

    final void zzam(int n, int n2) {
        if (n > this.zzpnb - this.zzpmz) {
            n2 = this.zzpnb;
            int n3 = this.zzpmz;
            throw new IllegalArgumentException(new StringBuilder(50).append("Position ").append(n).append(" is beyond current ").append(n2 - n3).toString());
        }
        if (n < 0) {
            throw new IllegalArgumentException(new StringBuilder(24).append("Bad position ").append(n).toString());
        }
        this.zzpnb = this.zzpmz + n;
        this.zzpft = n2;
    }

    public final int zzcvt() throws IOException {
        if (this.zzpnb == this.zzpna) {
            this.zzpft = 0;
            return 0;
        }
        this.zzpft = this.zzcwi();
        if (this.zzpft == 0) {
            throw new zzfjr("Protocol message contained an invalid tag (zero).");
        }
        return this.zzpft;
    }

    public final long zzcvv() throws IOException {
        return this.zzcwn();
    }

    public final int zzcvw() throws IOException {
        return this.zzcwi();
    }

    public final boolean zzcvz() throws IOException {
        return this.zzcwi() != 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int zzcwi() throws IOException {
        int n = this.zzcwr();
        if (n < 0) {
            n &= 0x7F;
            int n2 = this.zzcwr();
            if (n2 >= 0) {
                return n | n2 << 7;
            }
            n |= (n2 & 0x7F) << 7;
            n2 = this.zzcwr();
            if (n2 >= 0) {
                return n | n2 << 14;
            }
            n |= (n2 & 0x7F) << 14;
            int n3 = this.zzcwr();
            if (n3 >= 0) {
                return n | n3 << 21;
            }
            n2 = this.zzcwr();
            n = n3 = n | (n3 & 0x7F) << 21 | n2 << 28;
            if (n2 < 0) {
                n2 = 0;
                do {
                    if (n2 >= 5) {
                        throw zzfjr.zzdak();
                    }
                    n = n3;
                    if (this.zzcwr() >= 0) break;
                    ++n2;
                } while (true);
            }
        }
        return n;
    }

    public final int zzcwk() {
        if (this.zzpfu == Integer.MAX_VALUE) {
            return -1;
        }
        int n = this.zzpnb;
        return this.zzpfu - n;
    }

    public final long zzcwn() throws IOException {
        long l = 0L;
        for (int i = 0; i < 64; i += 7) {
            byte by = this.zzcwr();
            l |= (long)(by & 0x7F) << i;
            if ((by & 0x80) != 0) continue;
            return l;
        }
        throw zzfjr.zzdak();
    }

    public final int zzcwo() throws IOException {
        return this.zzcwr() & 0xFF | (this.zzcwr() & 0xFF) << 8 | (this.zzcwr() & 0xFF) << 16 | (this.zzcwr() & 0xFF) << 24;
    }

    public final long zzcwp() throws IOException {
        byte by = this.zzcwr();
        byte by2 = this.zzcwr();
        byte by3 = this.zzcwr();
        byte by4 = this.zzcwr();
        byte by5 = this.zzcwr();
        byte by6 = this.zzcwr();
        byte by7 = this.zzcwr();
        byte by8 = this.zzcwr();
        long l = by;
        return ((long)by2 & 0xFFL) << 8 | l & 0xFFL | ((long)by3 & 0xFFL) << 16 | ((long)by4 & 0xFFL) << 24 | ((long)by5 & 0xFFL) << 32 | ((long)by6 & 0xFFL) << 40 | ((long)by7 & 0xFFL) << 48 | ((long)by8 & 0xFFL) << 56;
    }

    public final void zzkp(int n) throws zzfjr {
        if (this.zzpft != n) {
            throw new zzfjr("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzkq(int n) throws IOException {
        switch (n & 7) {
            default: {
                throw new zzfjr("Protocol message tag had invalid wire type.");
            }
            case 0: {
                this.zzcwi();
                return true;
            }
            case 1: {
                this.zzcwp();
                return true;
            }
            case 2: {
                this.zzku(this.zzcwi());
                return true;
            }
            case 3: {
                int n2;
                while ((n2 = this.zzcvt()) != 0 && this.zzkq(n2)) {
                }
                this.zzkp(n >>> 3 << 3 | 4);
                return true;
            }
            case 4: {
                return false;
            }
            case 5: 
        }
        this.zzcwo();
        return true;
    }

    public final int zzks(int n) throws zzfjr {
        if (n < 0) {
            throw zzfjr.zzdaj();
        }
        int n2 = this.zzpfu;
        if ((n = this.zzpnb + n) > n2) {
            throw zzfjr.zzdai();
        }
        this.zzpfu = n;
        this.zzcwq();
        return n2;
    }

    public final void zzkt(int n) {
        this.zzpfu = n;
        this.zzcwq();
    }

    public final void zzmg(int n) {
        this.zzam(n, this.zzpft);
    }
}

