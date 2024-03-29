/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.gifencoder;

import java.io.IOException;
import java.io.OutputStream;

class LZWEncoder {
    int ClearCode;
    int EOFCode;
    int a_count;
    byte[] accum;
    boolean clear_flg = false;
    int[] codetab;
    private int curPixel;
    int cur_accum = 0;
    int cur_bits = 0;
    int free_ent = 0;
    int g_init_bits;
    int hsize = 5003;
    int[] htab = new int[5003];
    private int imgH;
    private int imgW;
    private int initCodeSize;
    int[] masks;
    int maxbits = 12;
    int maxcode;
    int maxmaxcode = 4096;
    int n_bits;
    private byte[] pixAry;
    private int remaining;

    LZWEncoder(int n, int n2, byte[] arrby, int n3) {
        this.codetab = new int[5003];
        this.masks = new int[]{0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535};
        this.accum = new byte[256];
        this.imgW = n;
        this.imgH = n2;
        this.pixAry = arrby;
        this.initCodeSize = Math.max(2, n3);
    }

    private int nextPixel() {
        if (this.remaining == 0) {
            return -1;
        }
        --this.remaining;
        byte[] arrby = this.pixAry;
        int n = this.curPixel;
        this.curPixel = n + 1;
        return arrby[n] & 0xFF;
    }

    final int MAXCODE(int n) {
        return (1 << n) - 1;
    }

    void char_out(byte by, OutputStream outputStream) throws IOException {
        byte[] arrby = this.accum;
        int n = this.a_count;
        this.a_count = n + 1;
        arrby[n] = by;
        if (this.a_count >= 254) {
            this.flush_char(outputStream);
        }
    }

    void cl_block(OutputStream outputStream) throws IOException {
        this.cl_hash(this.hsize);
        this.free_ent = this.ClearCode + 2;
        this.clear_flg = true;
        this.output(this.ClearCode, outputStream);
    }

    void cl_hash(int n) {
        for (int i = 0; i < n; ++i) {
            this.htab[i] = -1;
        }
    }

    void compress(int n, OutputStream outputStream) throws IOException {
        int n2;
        this.g_init_bits = n;
        this.clear_flg = false;
        this.n_bits = this.g_init_bits;
        this.maxcode = this.MAXCODE(this.n_bits);
        this.ClearCode = 1 << n - 1;
        this.EOFCode = this.ClearCode + 1;
        this.free_ent = this.ClearCode + 2;
        this.a_count = 0;
        int n3 = this.nextPixel();
        int n4 = 0;
        for (n = this.hsize; n < 65536; n *= 2) {
            ++n4;
        }
        int n5 = this.hsize;
        this.cl_hash(n5);
        this.output(this.ClearCode, outputStream);
        n = n3;
        block1 : while ((n2 = this.nextPixel()) != -1) {
            n3 = n2 << 8 - n4 ^ n;
            int n6 = (n2 << this.maxbits) + n;
            if (this.htab[n3] == n6) {
                n = this.codetab[n3];
                continue;
            }
            int n7 = n3;
            if (this.htab[n3] >= 0) {
                n7 = n5 - n3;
                int n8 = n3;
                if (n3 == 0) {
                    n7 = 1;
                    n8 = n3;
                }
                do {
                    n3 = n8 -= n7;
                    if (n8 < 0) {
                        n3 = n8 + n5;
                    }
                    if (this.htab[n3] == n6) {
                        n = this.codetab[n3];
                        continue block1;
                    }
                    n8 = n3;
                } while (this.htab[n3] >= 0);
                n7 = n3;
            }
            this.output(n, outputStream);
            n = n2;
            if (this.free_ent < this.maxmaxcode) {
                int[] arrn = this.codetab;
                n3 = this.free_ent;
                this.free_ent = n3 + 1;
                arrn[n7] = n3;
                this.htab[n7] = n6;
                continue;
            }
            this.cl_block(outputStream);
        }
        this.output(n, outputStream);
        this.output(this.EOFCode, outputStream);
    }

    void encode(OutputStream outputStream) throws IOException {
        outputStream.write(this.initCodeSize);
        this.remaining = this.imgW * this.imgH;
        this.curPixel = 0;
        this.compress(this.initCodeSize + 1, outputStream);
        outputStream.write(0);
    }

    void flush_char(OutputStream outputStream) throws IOException {
        if (this.a_count > 0) {
            outputStream.write(this.a_count);
            outputStream.write(this.accum, 0, this.a_count);
            this.a_count = 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void output(int n, OutputStream outputStream) throws IOException {
        this.cur_accum &= this.masks[this.cur_bits];
        this.cur_accum = this.cur_bits > 0 ? (this.cur_accum |= n << this.cur_bits) : n;
        this.cur_bits += this.n_bits;
        while (this.cur_bits >= 8) {
            this.char_out((byte)(this.cur_accum & 0xFF), outputStream);
            this.cur_accum >>= 8;
            this.cur_bits -= 8;
        }
        if (this.free_ent > this.maxcode || this.clear_flg) {
            if (this.clear_flg) {
                int n2;
                this.n_bits = n2 = this.g_init_bits;
                this.maxcode = this.MAXCODE(n2);
                this.clear_flg = false;
            } else {
                ++this.n_bits;
                this.maxcode = this.n_bits == this.maxbits ? this.maxmaxcode : this.MAXCODE(this.n_bits);
            }
        }
        if (n == this.EOFCode) {
            while (this.cur_bits > 0) {
                this.char_out((byte)(this.cur_accum & 0xFF), outputStream);
                this.cur_accum >>= 8;
                this.cur_bits -= 8;
            }
            this.flush_char(outputStream);
        }
    }
}

