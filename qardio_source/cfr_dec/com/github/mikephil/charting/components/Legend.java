/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 */
package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

public class Legend
extends ComponentBase {
    private List<Boolean> mCalculatedLabelBreakPoints;
    private List<FSize> mCalculatedLabelSizes;
    private List<FSize> mCalculatedLineSizes;
    private LegendDirection mDirection;
    private boolean mDrawInside = false;
    private LegendEntry[] mEntries = new LegendEntry[0];
    private LegendEntry[] mExtraEntries;
    private DashPathEffect mFormLineDashEffect = null;
    private float mFormLineWidth = 3.0f;
    private float mFormSize = 8.0f;
    private float mFormToTextSpace = 5.0f;
    private LegendHorizontalAlignment mHorizontalAlignment = LegendHorizontalAlignment.LEFT;
    private boolean mIsLegendCustom = false;
    private float mMaxSizePercent = 0.95f;
    public float mNeededHeight = 0.0f;
    public float mNeededWidth = 0.0f;
    private LegendOrientation mOrientation;
    private LegendForm mShape;
    private float mStackSpace = 3.0f;
    public float mTextHeightMax = 0.0f;
    public float mTextWidthMax = 0.0f;
    private LegendVerticalAlignment mVerticalAlignment = LegendVerticalAlignment.BOTTOM;
    private boolean mWordWrapEnabled = false;
    private float mXEntrySpace = 6.0f;
    private float mYEntrySpace = 0.0f;

    public Legend() {
        this.mOrientation = LegendOrientation.HORIZONTAL;
        this.mDirection = LegendDirection.LEFT_TO_RIGHT;
        this.mShape = LegendForm.SQUARE;
        this.mCalculatedLabelSizes = new ArrayList<FSize>(16);
        this.mCalculatedLabelBreakPoints = new ArrayList<Boolean>(16);
        this.mCalculatedLineSizes = new ArrayList<FSize>(16);
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(3.0f);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void calculateDimensions(Paint var1_1, ViewPortHandler var2_2) {
        block29: {
            var9_3 = Utils.convertDpToPixel(this.mFormSize);
            var12_4 = Utils.convertDpToPixel(this.mStackSpace);
            var13_5 = Utils.convertDpToPixel(this.mFormToTextSpace);
            var8_6 = Utils.convertDpToPixel(this.mXEntrySpace);
            var11_7 = Utils.convertDpToPixel(this.mYEntrySpace);
            var21_8 = this.mWordWrapEnabled;
            var22_9 = this.mEntries;
            var20_10 = var22_9.length;
            this.mTextWidthMax = this.getMaximumEntryWidth(var1_1);
            this.mTextHeightMax = this.getMaximumEntryHeight(var1_1);
            switch (1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mOrientation.ordinal()]) {
                case 1: {
                    var3_11 = 0.0f;
                    var5_13 = 0.0f;
                    var4_15 = 0.0f;
                    var14_17 = Utils.getLineHeight(var1_1);
                    var17_19 = false;
                    for (var19_21 = 0; var19_21 < var20_10; ++var19_21) {
                        var2_2 = var22_9[var19_21];
                        var18_29 = var2_2.form != LegendForm.NONE;
                        var6_23 = Float.isNaN(var2_2.formSize) != false ? var9_3 : Utils.convertDpToPixel(var2_2.formSize);
                        var2_2 = var2_2.label;
                        if (!var17_19) {
                            var4_15 = 0.0f;
                        }
                        var8_6 = var4_15;
                        if (var18_29) {
                            var7_25 = var4_15;
                            if (var17_19) {
                                var7_25 = var4_15 + var12_4;
                            }
                            var8_6 = var7_25 + var6_23;
                        }
                        if (var2_2 != null) {
                            if (var18_29 && !var17_19) {
                                var4_15 = var8_6 + var13_5;
                                var18_29 = var17_19;
                                var7_25 = var3_11;
                                var6_23 = var5_13;
                            } else {
                                var6_23 = var5_13;
                                var7_25 = var3_11;
                                var18_29 = var17_19;
                                var4_15 = var8_6;
                                if (var17_19) {
                                    var7_25 = Math.max(var3_11, var8_6);
                                    var6_23 = var5_13 + (var14_17 + var11_7);
                                    var4_15 = 0.0f;
                                    var18_29 = false;
                                }
                            }
                            var3_11 = var4_15 + (float)Utils.calcTextWidth(var1_1, (String)var2_2);
                            var8_6 = var6_23;
                            var10_27 = var7_25;
                            var17_19 = var18_29;
                            var4_15 = var3_11;
                            if (var19_21 < var20_10 - 1) {
                                var8_6 = var6_23 + (var14_17 + var11_7);
                                var4_15 = var3_11;
                                var17_19 = var18_29;
                                var10_27 = var7_25;
                            }
                        } else {
                            var18_29 = true;
                            var6_23 = var8_6 + var6_23;
                            var8_6 = var5_13;
                            var10_27 = var3_11;
                            var17_19 = var18_29;
                            var4_15 = var6_23;
                            if (var19_21 < var20_10 - 1) {
                                var4_15 = var6_23 + var12_4;
                                var8_6 = var5_13;
                                var10_27 = var3_11;
                                var17_19 = var18_29;
                            }
                        }
                        var3_11 = Math.max(var10_27, var4_15);
                        var5_13 = var8_6;
                    }
                    this.mNeededWidth = var3_11;
                    this.mNeededHeight = var5_13;
                }
                default: {
                    break;
                }
                case 2: {
                    var10_28 = Utils.getLineHeight(var1_1);
                    var14_18 = Utils.getLineSpacing(var1_1);
                    var15_31 = var2_2.contentWidth();
                    var16_32 = this.mMaxSizePercent;
                    var4_16 = 0.0f;
                    var6_24 = 0.0f;
                    var5_14 = 0.0f;
                    var19_22 = -1;
                    this.mCalculatedLabelBreakPoints.clear();
                    this.mCalculatedLabelSizes.clear();
                    this.mCalculatedLineSizes.clear();
                    break block29;
                }
            }
lbl89:
            // 2 sources
            do {
                this.mNeededHeight += this.mYOffset;
                this.mNeededWidth += this.mXOffset;
                return;
                break;
            } while (true);
        }
        for (var17_20 = 0; var17_20 < var20_10; ++var17_20) {
            block31: {
                block30: {
                    var2_2 = var22_9[var17_20];
                    var18_30 = var2_2.form != LegendForm.NONE ? 1 : 0;
                    var3_12 = Float.isNaN(var2_2.formSize) != false ? var9_3 : Utils.convertDpToPixel(var2_2.formSize);
                    var2_2 = var2_2.label;
                    this.mCalculatedLabelBreakPoints.add(false);
                    var5_14 = var19_22 == -1 ? 0.0f : (var5_14 += var12_4);
                    if (var2_2 != null) {
                        this.mCalculatedLabelSizes.add(Utils.calcTextSize(var1_1, (String)var2_2));
                        var3_12 = var18_30 != 0 ? var13_5 + var3_12 : 0.0f;
                        var3_12 = var5_14 + var3_12 + this.mCalculatedLabelSizes.get((int)var17_20).width;
                        var18_30 = var19_22;
                    } else {
                        this.mCalculatedLabelSizes.add(FSize.getInstance(0.0f, 0.0f));
                        if (var18_30 == 0) {
                            var3_12 = 0.0f;
                        }
                        var5_14 += var3_12;
                        var3_12 = var5_14;
                        var18_30 = var19_22;
                        if (var19_22 == -1) {
                            var18_30 = var17_20;
                            var3_12 = var5_14;
                        }
                    }
                    if (var2_2 != null) break block30;
                    var7_26 = var6_24;
                    var5_14 = var4_16;
                    if (var17_20 != var20_10 - 1) break block31;
                }
                var5_14 = var6_24 == 0.0f ? 0.0f : var8_6;
                if (!var21_8 || var6_24 == 0.0f || var15_31 * var16_32 - var6_24 >= var5_14 + var3_12) {
                    var5_14 = var6_24 + (var5_14 + var3_12);
                    var6_24 = var4_16;
                    var4_16 = var5_14;
                } else {
                    this.mCalculatedLineSizes.add(FSize.getInstance(var6_24, var10_28));
                    var6_24 = Math.max(var4_16, var6_24);
                    var23_33 = this.mCalculatedLabelBreakPoints;
                    var19_22 = var18_30 > -1 ? var18_30 : var17_20;
                    var23_33.set(var19_22, true);
                    var4_16 = var3_12;
                }
                var7_26 = var4_16;
                var5_14 = var6_24;
                if (var17_20 == var20_10 - 1) {
                    this.mCalculatedLineSizes.add(FSize.getInstance(var4_16, var10_28));
                    var5_14 = Math.max(var6_24, var4_16);
                    var7_26 = var4_16;
                }
            }
            if (var2_2 != null) {
                var18_30 = -1;
            }
            var6_24 = var7_26;
            var4_16 = var5_14;
            var5_14 = var3_12;
            var19_22 = var18_30;
        }
        this.mNeededWidth = var4_16;
        var3_12 = this.mCalculatedLineSizes.size();
        var17_20 = this.mCalculatedLineSizes.size() == 0 ? 0 : this.mCalculatedLineSizes.size() - 1;
        this.mNeededHeight = (float)var17_20 * (var14_18 + var11_7) + var10_28 * var3_12;
        ** while (true)
    }

    public List<Boolean> getCalculatedLabelBreakPoints() {
        return this.mCalculatedLabelBreakPoints;
    }

    public List<FSize> getCalculatedLabelSizes() {
        return this.mCalculatedLabelSizes;
    }

    public List<FSize> getCalculatedLineSizes() {
        return this.mCalculatedLineSizes;
    }

    public LegendDirection getDirection() {
        return this.mDirection;
    }

    public LegendEntry[] getEntries() {
        return this.mEntries;
    }

    public LegendEntry[] getExtraEntries() {
        return this.mExtraEntries;
    }

    public LegendForm getForm() {
        return this.mShape;
    }

    public DashPathEffect getFormLineDashEffect() {
        return this.mFormLineDashEffect;
    }

    public float getFormLineWidth() {
        return this.mFormLineWidth;
    }

    public float getFormSize() {
        return this.mFormSize;
    }

    public float getFormToTextSpace() {
        return this.mFormToTextSpace;
    }

    public LegendHorizontalAlignment getHorizontalAlignment() {
        return this.mHorizontalAlignment;
    }

    public float getMaxSizePercent() {
        return this.mMaxSizePercent;
    }

    /*
     * Enabled aggressive block sorting
     */
    public float getMaximumEntryHeight(Paint paint) {
        float f = 0.0f;
        LegendEntry[] arrlegendEntry = this.mEntries;
        int n = arrlegendEntry.length;
        int n2 = 0;
        while (n2 < n) {
            float f2;
            String string2 = arrlegendEntry[n2].label;
            if (string2 == null) {
                f2 = f;
            } else {
                float f3 = Utils.calcTextHeight(paint, string2);
                f2 = f;
                if (f3 > f) {
                    f2 = f3;
                }
            }
            ++n2;
            f = f2;
        }
        return f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public float getMaximumEntryWidth(Paint paint) {
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = Utils.convertDpToPixel(this.mFormToTextSpace);
        LegendEntry[] arrlegendEntry = this.mEntries;
        int n = arrlegendEntry.length;
        int n2 = 0;
        while (n2 < n) {
            String string2;
            LegendEntry legendEntry = arrlegendEntry[n2];
            float f4 = Float.isNaN(legendEntry.formSize) ? this.mFormSize : legendEntry.formSize;
            float f5 = Utils.convertDpToPixel(f4);
            f4 = f2;
            if (f5 > f2) {
                f4 = f5;
            }
            if ((string2 = legendEntry.label) == null) {
                f2 = f;
            } else {
                f5 = Utils.calcTextWidth(paint, string2);
                f2 = f;
                if (f5 > f) {
                    f2 = f5;
                }
            }
            ++n2;
            f = f2;
            f2 = f4;
        }
        return f + f2 + f3;
    }

    public LegendOrientation getOrientation() {
        return this.mOrientation;
    }

    public float getStackSpace() {
        return this.mStackSpace;
    }

    public LegendVerticalAlignment getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    public float getXEntrySpace() {
        return this.mXEntrySpace;
    }

    public float getYEntrySpace() {
        return this.mYEntrySpace;
    }

    public boolean isDrawInsideEnabled() {
        return this.mDrawInside;
    }

    public boolean isLegendCustom() {
        return this.mIsLegendCustom;
    }

    public void setEntries(List<LegendEntry> list) {
        this.mEntries = list.toArray(new LegendEntry[list.size()]);
    }

    public static enum LegendDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT;

    }

    public static enum LegendForm {
        NONE,
        EMPTY,
        DEFAULT,
        SQUARE,
        CIRCLE,
        LINE;

    }

    public static enum LegendHorizontalAlignment {
        LEFT,
        CENTER,
        RIGHT;

    }

    public static enum LegendOrientation {
        HORIZONTAL,
        VERTICAL;

    }

    @Deprecated
    public static enum LegendPosition {
        RIGHT_OF_CHART,
        RIGHT_OF_CHART_CENTER,
        RIGHT_OF_CHART_INSIDE,
        LEFT_OF_CHART,
        LEFT_OF_CHART_CENTER,
        LEFT_OF_CHART_INSIDE,
        BELOW_CHART_LEFT,
        BELOW_CHART_RIGHT,
        BELOW_CHART_CENTER,
        ABOVE_CHART_LEFT,
        ABOVE_CHART_RIGHT,
        ABOVE_CHART_CENTER,
        PIECHART_CENTER;

    }

    public static enum LegendVerticalAlignment {
        TOP,
        CENTER,
        BOTTOM;

    }

}

