/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class HorizontalBarBuffer
extends BarBuffer {
    public HorizontalBarBuffer(int n, int n2, boolean bl) {
        super(n, n2, bl);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void feed(IBarDataSet var1_1) {
        var8_2 = var1_1.getEntryCount();
        var9_3 = this.phaseX;
        var10_4 = this.mBarWidth / 2.0f;
        var12_5 = 0;
        block0: do {
            block19: {
                if (!((float)var12_5 < var8_2 * var9_3)) {
                    this.reset();
                    return;
                }
                var14_14 = (BarEntry)var1_1.getEntryForIndex(var12_5);
                if (var14_14 == null) ** GOTO lbl35
                var11_12 = var14_14.getX();
                var2_6 = var14_14.getY();
                var15_15 = var14_14.getYVals();
                if (this.mContainsStacks && var15_15 != null) break block19;
                if (this.mInverted) {
                    var3_7 = var2_6 >= 0.0f ? var2_6 : 0.0f;
                    if (!(var2_6 <= 0.0f)) {
                        var2_6 = 0.0f;
                    }
                } else {
                    var4_8 = var2_6 >= 0.0f ? var2_6 : 0.0f;
                    var3_7 = var2_6 <= 0.0f ? var2_6 : 0.0f;
                    var2_6 = var4_8;
                }
                if (var2_6 > 0.0f) {
                    var2_6 *= this.phaseY;
                } else {
                    var3_7 *= this.phaseY;
                }
                this.addBar(var3_7, var11_12 + var10_4, var2_6, var11_12 - var10_4);
                ** GOTO lbl35
            }
            var2_6 = 0.0f;
            var4_8 = -var14_14.getNegativeSum();
            var13_13 = 0;
            do {
                block20: {
                    if (var13_13 < var15_15.length) break block20;
lbl35:
                    // 3 sources
                    ++var12_5;
                    continue block0;
                }
                var6_10 = var15_15[var13_13];
                if (var6_10 >= 0.0f) {
                    var5_9 = var2_6;
                    var6_10 = var3_7 = var2_6 + var6_10;
                    var2_6 = var5_9;
                    var5_9 = var4_8;
                } else {
                    var3_7 = var4_8;
                    var7_11 = var4_8 + Math.abs(var6_10);
                    var5_9 = var4_8 + Math.abs(var6_10);
                    var6_10 = var2_6;
                    var2_6 = var3_7;
                    var3_7 = var7_11;
                }
                if (this.mInverted) {
                    var4_8 = var2_6 >= var3_7 ? var2_6 : var3_7;
                    if (var2_6 <= var3_7) {
                        var3_7 = var4_8;
                    } else {
                        var2_6 = var3_7;
                        var3_7 = var4_8;
                    }
                } else {
                    var4_8 = var2_6 >= var3_7 ? var2_6 : var3_7;
                    if (!(var2_6 <= var3_7)) {
                        var2_6 = var3_7;
                    }
                    var3_7 = var2_6;
                    var2_6 = var4_8;
                }
                var4_8 = this.phaseY;
                this.addBar(var3_7 * this.phaseY, var11_12 + var10_4, var2_6 * var4_8, var11_12 - var10_4);
                ++var13_13;
                var4_8 = var5_9;
                var2_6 = var6_10;
            } while (true);
            break;
        } while (true);
    }
}

