/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.gifencoder;

class NeuQuant {
    protected int alphadec;
    protected int[] bias;
    protected int[] freq;
    protected int lengthcount;
    protected int[] netindex = new int[256];
    protected int[][] network;
    protected int[] radpower;
    protected int samplefac;
    protected byte[] thepicture;

    public NeuQuant(byte[] arrby, int n, int n2) {
        this.bias = new int[256];
        this.freq = new int[256];
        this.radpower = new int[32];
        this.thepicture = arrby;
        this.lengthcount = n;
        this.samplefac = n2;
        this.network = new int[256][];
        for (n = 0; n < 256; ++n) {
            this.network[n] = new int[4];
            arrby = this.network[n];
            arrby[2] = n2 = (n << 12) / 256;
            arrby[1] = n2;
            arrby[0] = n2;
            this.freq[n] = 256;
            this.bias[n] = 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void alterneigh(int n, int n2, int n3, int n4, int n5) {
        int n6;
        int n7 = n6 = n2 - n;
        if (n6 < -1) {
            n7 = -1;
        }
        int n8 = n = n2 + n;
        if (n > 256) {
            n8 = 256;
        }
        int n9 = 1;
        n6 = n2 - 1;
        n = n2 + 1;
        n2 = n9;
        while (n < n8 || n6 > n7) {
            int[] arrn = this.radpower;
            n9 = n2 + 1;
            int n10 = arrn[n2];
            if (n < n8) {
                int[][] arrn2 = this.network;
                n2 = n + 1;
                int[] arrn3 = arrn2[n];
                try {
                    arrn3[0] = arrn3[0] - (arrn3[0] - n3) * n10 / 262144;
                    arrn3[1] = arrn3[1] - (arrn3[1] - n4) * n10 / 262144;
                    arrn3[2] = arrn3[2] - (arrn3[2] - n5) * n10 / 262144;
                    n = n2;
                }
                catch (Exception exception) {
                    n = n2;
                }
            }
            if (n6 > n7) {
                int[][] arrn4 = this.network;
                n2 = n6 - 1;
                int[] arrn5 = arrn4[n6];
                try {
                    arrn5[0] = arrn5[0] - (arrn5[0] - n3) * n10 / 262144;
                    arrn5[1] = arrn5[1] - (arrn5[1] - n4) * n10 / 262144;
                    arrn5[2] = arrn5[2] - (arrn5[2] - n5) * n10 / 262144;
                    n6 = n2;
                    n2 = n9;
                }
                catch (Exception exception) {
                    n6 = n2;
                    n2 = n9;
                }
                continue;
            }
            n2 = n9;
        }
        return;
    }

    protected void altersingle(int n, int n2, int n3, int n4, int n5) {
        int[] arrn = this.network[n2];
        arrn[0] = arrn[0] - (arrn[0] - n3) * n / 1024;
        arrn[1] = arrn[1] - (arrn[1] - n4) * n / 1024;
        arrn[2] = arrn[2] - (arrn[2] - n5) * n / 1024;
    }

    public byte[] colorMap() {
        int n;
        byte[] arrby = new byte[768];
        int[] arrn = new int[256];
        for (n = 0; n < 256; ++n) {
            arrn[this.network[n][3]] = n;
        }
        n = 0;
        int n2 = 0;
        while (n < 256) {
            int n3 = arrn[n];
            int n4 = n2 + 1;
            arrby[n2] = (byte)this.network[n3][0];
            n2 = n4 + 1;
            arrby[n4] = (byte)this.network[n3][1];
            arrby[n2] = (byte)this.network[n3][2];
            ++n;
            ++n2;
        }
        return arrby;
    }

    protected int contest(int n, int n2, int n3) {
        int[] arrn;
        int n4 = Integer.MAX_VALUE;
        int n5 = Integer.MAX_VALUE;
        int n6 = -1;
        int n7 = -1;
        for (int i = 0; i < 256; ++i) {
            int n8;
            int n9;
            int n10;
            arrn = this.network[i];
            int n11 = n10 = arrn[0] - n;
            if (n10 < 0) {
                n11 = -n10;
            }
            n10 = n9 = arrn[1] - n2;
            if (n9 < 0) {
                n10 = -n9;
            }
            n9 = n8 = arrn[2] - n3;
            if (n8 < 0) {
                n9 = -n8;
            }
            n10 = n11 + n10 + n9;
            n11 = n4;
            if (n10 < n4) {
                n11 = n10;
                n6 = i;
            }
            n4 = n5;
            if ((n10 -= this.bias[i] >> 12) < n5) {
                n4 = n10;
                n7 = i;
            }
            n5 = this.freq[i] >> 10;
            arrn = this.freq;
            arrn[i] = arrn[i] - n5;
            arrn = this.bias;
            arrn[i] = arrn[i] + (n5 << 10);
            n5 = n4;
            n4 = n11;
        }
        arrn = this.freq;
        arrn[n6] = arrn[n6] + 64;
        arrn = this.bias;
        arrn[n6] = arrn[n6] - 65536;
        return n7;
    }

    public void inxbuild() {
        int n;
        int n2 = 0;
        int n3 = 0;
        for (n = 0; n < 256; ++n) {
            int[] arrn;
            int n4;
            int n5;
            int[] arrn2 = this.network[n];
            int n6 = n;
            int n7 = arrn2[1];
            for (n5 = n + 1; n5 < 256; ++n5) {
                arrn = this.network[n5];
                n4 = n7;
                if (arrn[1] < n7) {
                    n6 = n5;
                    n4 = arrn[1];
                }
                n7 = n4;
            }
            arrn = this.network[n6];
            if (n != n6) {
                n5 = arrn[0];
                arrn[0] = arrn2[0];
                arrn2[0] = n5;
                n5 = arrn[1];
                arrn[1] = arrn2[1];
                arrn2[1] = n5;
                n5 = arrn[2];
                arrn[2] = arrn2[2];
                arrn2[2] = n5;
                n5 = arrn[3];
                arrn[3] = arrn2[3];
                arrn2[3] = n5;
            }
            n4 = n2;
            n5 = n3;
            if (n7 != n2) {
                this.netindex[n2] = n3 + n >> 1;
                for (n5 = n2 + 1; n5 < n7; ++n5) {
                    this.netindex[n5] = n;
                }
                n5 = n;
                n4 = n7;
            }
            n2 = n4;
            n3 = n5;
        }
        this.netindex[n2] = n3 + 255 >> 1;
        for (n = n2 + 1; n < 256; ++n) {
            this.netindex[n] = 255;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void learn() {
        if (this.lengthcount < 1509) {
            this.samplefac = 1;
        }
        this.alphadec = (this.samplefac - 1) / 3 + 30;
        var17_1 = this.thepicture;
        var8_2 = 0;
        var15_3 = this.lengthcount;
        var16_4 = this.lengthcount / (this.samplefac * 3);
        var7_5 = var16_4 / 100;
        var9_6 = 1024;
        var4_7 = 2048;
        var2_9 = var1_8 = 2048 >> 6;
        if (var1_8 <= 1) {
            var2_9 = 0;
        }
        for (var1_8 = 0; var1_8 < var2_9; ++var1_8) {
            this.radpower[var1_8] = (var2_9 * var2_9 - var1_8 * var1_8) * 256 / (var2_9 * var2_9) * 1024;
        }
        var1_8 = this.lengthcount < 1509 ? 3 : (this.lengthcount % 499 != 0 ? 1497 : (this.lengthcount % 491 != 0 ? 1473 : (this.lengthcount % 487 != 0 ? 1461 : 1509)));
        var5_10 = 0;
        var6_11 = var2_9;
        block1: do lbl-1000:
        // 3 sources
        {
            if (var5_10 >= var16_4) return;
            var2_9 = (var17_1[var8_2 + 0] & 255) << 4;
            var3_12 = (var17_1[var8_2 + 1] & 255) << 4;
            var10_13 = (var17_1[var8_2 + 2] & 255) << 4;
            var11_14 = this.contest(var2_9, var3_12, var10_13);
            this.altersingle(var9_6, var11_14, var2_9, var3_12, var10_13);
            if (var6_11 != 0) {
                this.alterneigh(var6_11, var11_14, var2_9, var3_12, var10_13);
            }
            var2_9 = var3_12 = var8_2 + var1_8;
            if (var3_12 >= var15_3) {
                var2_9 = var3_12 - this.lengthcount;
            }
            var12_15 = var5_10 + 1;
            var10_13 = var7_5;
            if (var7_5 == 0) {
                var10_13 = 1;
            }
            var7_5 = var10_13;
            var5_10 = var12_15;
            var8_2 = var2_9;
            if (var12_15 % var10_13 != 0) ** GOTO lbl-1000
            var13_16 = var9_6 - var9_6 / this.alphadec;
            var14_17 = var4_7 - var4_7 / 30;
            var3_12 = var4_7 = var14_17 >> 6;
            if (var4_7 <= 1) {
                var3_12 = 0;
            }
            var11_14 = 0;
            do {
                var9_6 = var13_16;
                var6_11 = var3_12;
                var7_5 = var10_13;
                var5_10 = var12_15;
                var8_2 = var2_9;
                var4_7 = var14_17;
                if (var11_14 >= var3_12) continue block1;
                this.radpower[var11_14] = (var3_12 * var3_12 - var11_14 * var11_14) * 256 / (var3_12 * var3_12) * var13_16;
                ++var11_14;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int map(int n, int n2, int n3) {
        int n4 = 1000;
        int n5 = -1;
        int n6 = this.netindex[n2];
        int n7 = n6 - 1;
        while (n6 < 256 || n7 >= 0) {
            int n8;
            int[] arrn;
            int n9 = n5;
            int n10 = n4;
            int n11 = n6;
            if (n6 < 256) {
                arrn = this.network[n6];
                n9 = arrn[1] - n2;
                if (n9 >= n4) {
                    n11 = 256;
                    n10 = n4;
                    n9 = n5;
                } else {
                    ++n6;
                    n11 = n9;
                    if (n9 < 0) {
                        n11 = -n9;
                    }
                    n9 = n10 = arrn[0] - n;
                    if (n10 < 0) {
                        n9 = -n10;
                    }
                    n8 = n11 + n9;
                    n9 = n5;
                    n10 = n4;
                    n11 = n6;
                    if (n8 < n4) {
                        n11 = n9 = arrn[2] - n3;
                        if (n9 < 0) {
                            n11 = -n9;
                        }
                        n8 += n11;
                        n9 = n5;
                        n10 = n4;
                        n11 = n6;
                        if (n8 < n4) {
                            n10 = n8;
                            n9 = arrn[3];
                            n11 = n6;
                        }
                    }
                }
            }
            n5 = n9;
            n4 = n10;
            n6 = n11;
            if (n7 < 0) continue;
            arrn = this.network[n7];
            n5 = n2 - arrn[1];
            if (n5 >= n10) {
                n7 = -1;
                n5 = n9;
                n4 = n10;
                n6 = n11;
                continue;
            }
            n8 = n7 - 1;
            n7 = n5;
            if (n5 < 0) {
                n7 = -n5;
            }
            n5 = n4 = arrn[0] - n;
            if (n4 < 0) {
                n5 = -n4;
            }
            int n12 = n7 + n5;
            n5 = n9;
            n4 = n10;
            n6 = n11;
            n7 = n8;
            if (n12 >= n10) continue;
            n7 = n5 = arrn[2] - n3;
            if (n5 < 0) {
                n7 = -n5;
            }
            n12 += n7;
            n5 = n9;
            n4 = n10;
            n6 = n11;
            n7 = n8;
            if (n12 >= n10) continue;
            n4 = n12;
            n5 = arrn[3];
            n6 = n11;
            n7 = n8;
        }
        return n5;
    }

    public byte[] process() {
        this.learn();
        this.unbiasnet();
        this.inxbuild();
        return this.colorMap();
    }

    public void unbiasnet() {
        for (int i = 0; i < 256; ++i) {
            int[] arrn = this.network[i];
            arrn[0] = arrn[0] >> 4;
            arrn = this.network[i];
            arrn[1] = arrn[1] >> 4;
            arrn = this.network[i];
            arrn[2] = arrn[2] >> 4;
            this.network[i][3] = i;
        }
    }
}

