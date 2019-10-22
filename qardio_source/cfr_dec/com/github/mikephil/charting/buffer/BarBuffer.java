/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.buffer.AbstractBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class BarBuffer
extends AbstractBuffer<IBarDataSet> {
    protected float mBarWidth = 1.0f;
    protected boolean mContainsStacks = false;
    protected int mDataSetCount = 1;
    protected int mDataSetIndex = 0;
    protected boolean mInverted = false;

    public BarBuffer(int n, int n2, boolean bl) {
        super(n);
        this.mDataSetCount = n2;
        this.mContainsStacks = bl;
    }

    protected void addBar(float f, float f2, float f3, float f4) {
        float[] arrf = this.buffer;
        int n = this.index;
        this.index = n + 1;
        arrf[n] = f;
        arrf = this.buffer;
        n = this.index;
        this.index = n + 1;
        arrf[n] = f2;
        arrf = this.buffer;
        n = this.index;
        this.index = n + 1;
        arrf[n] = f3;
        arrf = this.buffer;
        n = this.index;
        this.index = n + 1;
        arrf[n] = f4;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
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
                this.addBar(var11_12 - var10_4, var2_6, var11_12 + var10_4, var3_7);
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
                this.addBar(var11_12 - var10_4, var2_6 * this.phaseY, var11_12 + var10_4, var3_7 * this.phaseY);
                ++var13_13;
                var4_8 = var5_9;
                var2_6 = var6_10;
            } while (true);
            break;
        } while (true);
    }

    public void setBarWidth(float f) {
        this.mBarWidth = f;
    }

    public void setDataSet(int n) {
        this.mDataSetIndex = n;
    }

    public void setInverted(boolean bl) {
        this.mInverted = bl;
    }
}

