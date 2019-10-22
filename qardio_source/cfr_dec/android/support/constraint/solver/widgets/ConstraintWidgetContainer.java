/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.Guideline;
import android.support.constraint.solver.widgets.Optimizer;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.constraint.solver.widgets.WidgetContainer;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintWidgetContainer
extends WidgetContainer {
    static boolean ALLOW_ROOT_GROUP = true;
    private boolean[] flags;
    protected LinearSystem mBackgroundSystem = null;
    private ConstraintWidget[] mChainEnds;
    private boolean mHeightMeasuredTooSmall = false;
    private ConstraintWidget[] mHorizontalChainsArray;
    private int mHorizontalChainsSize = 0;
    private ConstraintWidget[] mMatchConstraintsChainedWidgets;
    private int mOptimizationLevel = 2;
    int mPaddingBottom;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    private Snapshot mSnapshot;
    protected LinearSystem mSystem = new LinearSystem();
    private ConstraintWidget[] mVerticalChainsArray;
    private int mVerticalChainsSize = 0;
    private boolean mWidthMeasuredTooSmall = false;
    int mWrapHeight;
    int mWrapWidth;

    public ConstraintWidgetContainer() {
        this.mMatchConstraintsChainedWidgets = new ConstraintWidget[4];
        this.mVerticalChainsArray = new ConstraintWidget[4];
        this.mHorizontalChainsArray = new ConstraintWidget[4];
        this.flags = new boolean[3];
        this.mChainEnds = new ConstraintWidget[4];
    }

    private void addHorizontalChain(ConstraintWidget constraintWidget) {
        for (int i = 0; i < this.mHorizontalChainsSize; ++i) {
            if (this.mHorizontalChainsArray[i] != constraintWidget) continue;
            return;
        }
        if (this.mHorizontalChainsSize + 1 >= this.mHorizontalChainsArray.length) {
            this.mHorizontalChainsArray = Arrays.copyOf(this.mHorizontalChainsArray, this.mHorizontalChainsArray.length * 2);
        }
        this.mHorizontalChainsArray[this.mHorizontalChainsSize] = constraintWidget;
        ++this.mHorizontalChainsSize;
    }

    private void addVerticalChain(ConstraintWidget constraintWidget) {
        for (int i = 0; i < this.mVerticalChainsSize; ++i) {
            if (this.mVerticalChainsArray[i] != constraintWidget) continue;
            return;
        }
        if (this.mVerticalChainsSize + 1 >= this.mVerticalChainsArray.length) {
            this.mVerticalChainsArray = Arrays.copyOf(this.mVerticalChainsArray, this.mVerticalChainsArray.length * 2);
        }
        this.mVerticalChainsArray[this.mVerticalChainsSize] = constraintWidget;
        ++this.mVerticalChainsSize;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void applyHorizontalChain(LinearSystem var1_1) {
        block63: {
            block64: {
                block62: {
                    block61: {
                        var4_2 = 0;
                        block0 : while (var4_2 < this.mHorizontalChainsSize) {
                            var16_16 = this.mHorizontalChainsArray[var4_2];
                            var7_7 = this.countMatchConstraintsChainedWidgets(var1_1, this.mChainEnds, this.mHorizontalChainsArray[var4_2], 0, this.flags);
                            var10_10 = this.mChainEnds[2];
                            if (var10_10 != null) {
                                if (this.flags[1]) {
                                    var3_4 = var16_16.getDrawX();
                                    while (var10_10 != null) {
                                        var1_1.addEquality(var10_10.mLeft.mSolverVariable, var3_4);
                                        var9_9 = var10_10.mHorizontalNextWidget;
                                        var3_4 += var10_10.mLeft.getMargin() + var10_10.getWidth() + var10_10.mRight.getMargin();
                                        var10_10 = var9_9;
                                    }
                                } else {
                                    var3_4 = var16_16.mHorizontalChainStyle == 0 ? 1 : 0;
                                    var5_5 = var16_16.mHorizontalChainStyle == 2 ? 1 : 0;
                                    var6_6 = this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? 1 : 0;
                                    if ((this.mOptimizationLevel == 2 || this.mOptimizationLevel == 8) && this.flags[0] && var16_16.mHorizontalChainFixedPosition && var5_5 == 0 && var6_6 == 0 && var16_16.mHorizontalChainStyle == 0) {
                                        Optimizer.applyDirectResolutionHorizontalChain(this, var1_1, var7_7, var16_16);
                                    } else {
                                        if (var7_7 == 0 || var5_5 != 0) {
                                            var12_12 = null;
                                            var14_14 = null;
                                            var6_6 = 0;
                                            var9_9 = var10_10;
                                            break block61;
                                        }
                                        var9_9 = null;
                                        var2_3 = 0.0f;
                                        break block62;
                                    }
                                }
                            }
lbl31:
                            // 10 sources
                            do {
                                ++var4_2;
                                continue block0;
                                break;
                            } while (true);
                        }
                        return;
                    }
                    while ((var15_15 = var9_9) != null) {
                        var13_13 = var15_15.mHorizontalNextWidget;
                        if (var13_13 == null) {
                            var14_14 = this.mChainEnds[1];
                            var6_6 = 1;
                        }
                        if (var5_5 != 0) {
                            var9_9 = var15_15.mLeft;
                            var7_7 = var8_8 = var9_9.getMargin();
                            if (var12_12 != null) {
                                var7_7 = var8_8 + var12_12.mRight.getMargin();
                            }
                            var8_8 = 1;
                            if (var10_10 != var15_15) {
                                var8_8 = 3;
                            }
                            var1_1.addGreaterThan(var9_9.mSolverVariable, var9_9.mTarget.mSolverVariable, var7_7, var8_8);
                            var12_12 = var13_13;
                            if (var15_15.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                var11_11 = var15_15.mRight;
                                if (var15_15.mMatchConstraintDefaultWidth == 1) {
                                    var7_7 = Math.max(var15_15.mMatchConstraintMinWidth, var15_15.getWidth());
                                    var1_1.addEquality(var11_11.mSolverVariable, var9_9.mSolverVariable, var7_7, 3);
                                    var12_12 = var13_13;
                                } else {
                                    var1_1.addGreaterThan(var9_9.mSolverVariable, var9_9.mTarget.mSolverVariable, var9_9.mMargin, 3);
                                    var1_1.addLowerThan(var11_11.mSolverVariable, var9_9.mSolverVariable, var15_15.mMatchConstraintMinWidth, 3);
                                    var12_12 = var13_13;
                                }
                            }
                        } else if (var3_4 == 0 && var6_6 != 0 && var12_12 != null) {
                            if (var15_15.mRight.mTarget == null) {
                                var1_1.addEquality(var15_15.mRight.mSolverVariable, var15_15.getDrawRight());
                                var12_12 = var13_13;
                            } else {
                                var7_7 = var15_15.mRight.getMargin();
                                var1_1.addEquality(var15_15.mRight.mSolverVariable, var14_14.mRight.mTarget.mSolverVariable, -var7_7, 5);
                                var12_12 = var13_13;
                            }
                        } else if (var3_4 == 0 && var6_6 == 0 && var12_12 == null) {
                            if (var15_15.mLeft.mTarget == null) {
                                var1_1.addEquality(var15_15.mLeft.mSolverVariable, var15_15.getDrawX());
                                var12_12 = var13_13;
                            } else {
                                var7_7 = var15_15.mLeft.getMargin();
                                var1_1.addEquality(var15_15.mLeft.mSolverVariable, var16_16.mLeft.mTarget.mSolverVariable, var7_7, 5);
                                var12_12 = var13_13;
                            }
                        } else {
                            var17_17 = var15_15.mLeft;
                            var18_18 = var15_15.mRight;
                            var7_7 = var17_17.getMargin();
                            var8_8 = var18_18.getMargin();
                            var1_1.addGreaterThan(var17_17.mSolverVariable, var17_17.mTarget.mSolverVariable, var7_7, 1);
                            var1_1.addLowerThan(var18_18.mSolverVariable, var18_18.mTarget.mSolverVariable, -var8_8, 1);
                            var11_11 = var17_17.mTarget != null ? var17_17.mTarget.mSolverVariable : null;
                            if (var12_12 == null) {
                                var11_11 = var16_16.mLeft.mTarget != null ? var16_16.mLeft.mTarget.mSolverVariable : null;
                            }
                            var9_9 = var13_13;
                            if (var13_13 == null) {
                                var9_9 = var14_14.mRight.mTarget != null ? var14_14.mRight.mTarget.mOwner : null;
                            }
                            var12_12 = var9_9;
                            if (var9_9 != null) {
                                var13_13 = var9_9.mLeft.mSolverVariable;
                                if (var6_6 != 0) {
                                    var13_13 = var14_14.mRight.mTarget != null ? var14_14.mRight.mTarget.mSolverVariable : null;
                                }
                                var12_12 = var9_9;
                                if (var11_11 != null) {
                                    var12_12 = var9_9;
                                    if (var13_13 != null) {
                                        var1_1.addCentering(var17_17.mSolverVariable, (SolverVariable)var11_11, var7_7, 0.5f, (SolverVariable)var13_13, var18_18.mSolverVariable, var8_8, 4);
                                        var12_12 = var9_9;
                                    }
                                }
                            }
                        }
                        var9_9 = var6_6 != 0 ? null : var12_12;
                        var12_12 = var15_15;
                    }
                    if (var5_5 == 0) ** GOTO lbl31
                    var11_11 = var10_10.mLeft;
                    var12_12 = var14_14.mRight;
                    var3_4 = var11_11.getMargin();
                    var5_5 = var12_12.getMargin();
                    var9_9 = var16_16.mLeft.mTarget != null ? var16_16.mLeft.mTarget.mSolverVariable : null;
                    var10_10 = var14_14.mRight.mTarget != null ? var14_14.mRight.mTarget.mSolverVariable : null;
                    if (var9_9 == null || var10_10 == null) ** GOTO lbl31
                    var1_1.addLowerThan(var12_12.mSolverVariable, (SolverVariable)var10_10, -var5_5, 1);
                    var1_1.addCentering(var11_11.mSolverVariable, (SolverVariable)var9_9, var3_4, var16_16.mHorizontalBiasPercent, (SolverVariable)var10_10, var12_12.mSolverVariable, var5_5, 4);
                    ** GOTO lbl31
                }
                while (var10_10 != null) {
                    if (var10_10.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        var3_4 = var5_5 = var10_10.mLeft.getMargin();
                        if (var9_9 != null) {
                            var3_4 = var5_5 + var9_9.mRight.getMargin();
                        }
                        var5_5 = 3;
                        if (var10_10.mLeft.mTarget.mOwner.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            var5_5 = 2;
                        }
                        var1_1.addGreaterThan(var10_10.mLeft.mSolverVariable, var10_10.mLeft.mTarget.mSolverVariable, var3_4, var5_5);
                        var3_4 = var5_5 = var10_10.mRight.getMargin();
                        if (var10_10.mRight.mTarget.mOwner.mLeft.mTarget != null) {
                            var3_4 = var5_5;
                            if (var10_10.mRight.mTarget.mOwner.mLeft.mTarget.mOwner == var10_10) {
                                var3_4 = var5_5 + var10_10.mRight.mTarget.mOwner.mLeft.getMargin();
                            }
                        }
                        var5_5 = 3;
                        if (var10_10.mRight.mTarget.mOwner.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            var5_5 = 2;
                        }
                        var1_1.addLowerThan(var10_10.mRight.mSolverVariable, var10_10.mRight.mTarget.mSolverVariable, -var3_4, var5_5);
                    } else {
                        var2_3 += var10_10.mHorizontalWeight;
                        var3_4 = 0;
                        if (var10_10.mRight.mTarget != null) {
                            var3_4 = var5_5 = var10_10.mRight.getMargin();
                            if (var10_10 != this.mChainEnds[3]) {
                                var3_4 = var5_5 + var10_10.mRight.mTarget.mOwner.mLeft.getMargin();
                            }
                        }
                        var1_1.addGreaterThan(var10_10.mRight.mSolverVariable, var10_10.mLeft.mSolverVariable, 0, 1);
                        var1_1.addLowerThan(var10_10.mRight.mSolverVariable, var10_10.mRight.mTarget.mSolverVariable, -var3_4, 1);
                    }
                    var9_9 = var10_10;
                    var10_10 = var10_10.mHorizontalNextWidget;
                }
                if (var7_7 != 1) break block63;
                var10_10 = this.mMatchConstraintsChainedWidgets[0];
                var3_4 = var5_5 = var10_10.mLeft.getMargin();
                if (var10_10.mLeft.mTarget != null) {
                    var3_4 = var5_5 + var10_10.mLeft.mTarget.getMargin();
                }
                var5_5 = var6_6 = var10_10.mRight.getMargin();
                if (var10_10.mRight.mTarget != null) {
                    var5_5 = var6_6 + var10_10.mRight.mTarget.getMargin();
                }
                var9_9 = var16_16.mRight.mTarget.mSolverVariable;
                if (var10_10 == this.mChainEnds[3]) {
                    var9_9 = this.mChainEnds[1].mRight.mTarget.mSolverVariable;
                }
                if (var10_10.mMatchConstraintDefaultWidth != 1) break block64;
                var1_1.addGreaterThan(var16_16.mLeft.mSolverVariable, var16_16.mLeft.mTarget.mSolverVariable, var3_4, 1);
                var1_1.addLowerThan(var16_16.mRight.mSolverVariable, (SolverVariable)var9_9, -var5_5, 1);
                var1_1.addEquality(var16_16.mRight.mSolverVariable, var16_16.mLeft.mSolverVariable, var16_16.getWidth(), 2);
                ** GOTO lbl31
            }
            var1_1.addEquality(var10_10.mLeft.mSolverVariable, var10_10.mLeft.mTarget.mSolverVariable, var3_4, 1);
            var1_1.addEquality(var10_10.mRight.mSolverVariable, (SolverVariable)var9_9, -var5_5, 1);
            ** GOTO lbl31
        }
        var3_4 = 0;
        do {
            if (var3_4 < var7_7 - 1) ** break;
            ** continue;
            var11_11 = this.mMatchConstraintsChainedWidgets[var3_4];
            var12_12 = this.mMatchConstraintsChainedWidgets[var3_4 + 1];
            var13_13 = var11_11.mLeft.mSolverVariable;
            var14_14 = var11_11.mRight.mSolverVariable;
            var15_15 = var12_12.mLeft.mSolverVariable;
            var9_9 = var12_12.mRight.mSolverVariable;
            if (var12_12 == this.mChainEnds[3]) {
                var9_9 = this.mChainEnds[1].mRight.mSolverVariable;
            }
            var5_5 = var6_6 = var11_11.mLeft.getMargin();
            if (var11_11.mLeft.mTarget != null) {
                var5_5 = var6_6;
                if (var11_11.mLeft.mTarget.mOwner.mRight.mTarget != null) {
                    var5_5 = var6_6;
                    if (var11_11.mLeft.mTarget.mOwner.mRight.mTarget.mOwner == var11_11) {
                        var5_5 = var6_6 + var11_11.mLeft.mTarget.mOwner.mRight.getMargin();
                    }
                }
            }
            var1_1.addGreaterThan((SolverVariable)var13_13, var11_11.mLeft.mTarget.mSolverVariable, var5_5, 2);
            var5_5 = var6_6 = var11_11.mRight.getMargin();
            if (var11_11.mRight.mTarget != null) {
                var5_5 = var6_6;
                if (var11_11.mHorizontalNextWidget != null) {
                    var5_5 = var11_11.mHorizontalNextWidget.mLeft.mTarget != null ? var11_11.mHorizontalNextWidget.mLeft.getMargin() : 0;
                    var5_5 = var6_6 + var5_5;
                }
            }
            var1_1.addLowerThan((SolverVariable)var14_14, var11_11.mRight.mTarget.mSolverVariable, -var5_5, 2);
            if (var3_4 + 1 == var7_7 - 1) {
                var5_5 = var6_6 = var12_12.mLeft.getMargin();
                if (var12_12.mLeft.mTarget != null) {
                    var5_5 = var6_6;
                    if (var12_12.mLeft.mTarget.mOwner.mRight.mTarget != null) {
                        var5_5 = var6_6;
                        if (var12_12.mLeft.mTarget.mOwner.mRight.mTarget.mOwner == var12_12) {
                            var5_5 = var6_6 + var12_12.mLeft.mTarget.mOwner.mRight.getMargin();
                        }
                    }
                }
                var1_1.addGreaterThan((SolverVariable)var15_15, var12_12.mLeft.mTarget.mSolverVariable, var5_5, 2);
                var10_10 = var12_12.mRight;
                if (var12_12 == this.mChainEnds[3]) {
                    var10_10 = this.mChainEnds[1].mRight;
                }
                var5_5 = var6_6 = var10_10.getMargin();
                if (var10_10.mTarget != null) {
                    var5_5 = var6_6;
                    if (var10_10.mTarget.mOwner.mLeft.mTarget != null) {
                        var5_5 = var6_6;
                        if (var10_10.mTarget.mOwner.mLeft.mTarget.mOwner == var12_12) {
                            var5_5 = var6_6 + var10_10.mTarget.mOwner.mLeft.getMargin();
                        }
                    }
                }
                var1_1.addLowerThan((SolverVariable)var9_9, var10_10.mTarget.mSolverVariable, -var5_5, 2);
            }
            if (var16_16.mMatchConstraintMaxWidth > 0) {
                var1_1.addLowerThan((SolverVariable)var14_14, (SolverVariable)var13_13, var16_16.mMatchConstraintMaxWidth, 2);
            }
            var10_10 = var1_1.createRow();
            var10_10.createRowEqualDimension(var11_11.mHorizontalWeight, var2_3, var12_12.mHorizontalWeight, (SolverVariable)var13_13, var11_11.mLeft.getMargin(), (SolverVariable)var14_14, var11_11.mRight.getMargin(), (SolverVariable)var15_15, var12_12.mLeft.getMargin(), (SolverVariable)var9_9, var12_12.mRight.getMargin());
            var1_1.addConstraint((ArrayRow)var10_10);
            ++var3_4;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void applyVerticalChain(LinearSystem var1_1) {
        block67: {
            block68: {
                block66: {
                    block65: {
                        var4_2 = 0;
                        block0 : while (var4_2 < this.mVerticalChainsSize) {
                            var17_17 = this.mVerticalChainsArray[var4_2];
                            var7_7 = this.countMatchConstraintsChainedWidgets(var1_1, this.mChainEnds, this.mVerticalChainsArray[var4_2], 1, this.flags);
                            var11_11 = this.mChainEnds[2];
                            if (var11_11 != null) {
                                if (this.flags[1]) {
                                    var3_4 = var17_17.getDrawY();
                                    while (var11_11 != null) {
                                        var1_1.addEquality(var11_11.mTop.mSolverVariable, var3_4);
                                        var10_10 = var11_11.mVerticalNextWidget;
                                        var3_4 += var11_11.mTop.getMargin() + var11_11.getHeight() + var11_11.mBottom.getMargin();
                                        var11_11 = var10_10;
                                    }
                                } else {
                                    var5_5 = var17_17.mVerticalChainStyle == 0 ? 1 : 0;
                                    var6_6 = var17_17.mVerticalChainStyle == 2 ? 1 : 0;
                                    var3_4 = this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? 1 : 0;
                                    if ((this.mOptimizationLevel == 2 || this.mOptimizationLevel == 8) && this.flags[0] && var17_17.mVerticalChainFixedPosition && var6_6 == 0 && var3_4 == 0 && var17_17.mVerticalChainStyle == 0) {
                                        Optimizer.applyDirectResolutionVerticalChain(this, var1_1, var7_7, var17_17);
                                    } else {
                                        if (var7_7 == 0 || var6_6 != 0) {
                                            var13_13 = null;
                                            var15_15 = null;
                                            var7_7 = 0;
                                            var10_10 = var11_11;
                                            break block65;
                                        }
                                        var10_10 = null;
                                        var2_3 = 0.0f;
                                        break block66;
                                    }
                                }
                            }
lbl31:
                            // 10 sources
                            do {
                                ++var4_2;
                                continue block0;
                                break;
                            } while (true);
                        }
                        return;
                    }
                    while ((var16_16 = var10_10) != null) {
                        var14_14 = var16_16.mVerticalNextWidget;
                        if (var14_14 == null) {
                            var15_15 = this.mChainEnds[1];
                            var7_7 = 1;
                        }
                        if (var6_6 != 0) {
                            var18_18 = var16_16.mTop;
                            var3_4 = var8_8 = var18_18.getMargin();
                            if (var13_13 != null) {
                                var3_4 = var8_8 + var13_13.mBottom.getMargin();
                            }
                            var8_8 = 1;
                            if (var11_11 != var16_16) {
                                var8_8 = 3;
                            }
                            var10_10 = null;
                            var12_12 = null;
                            if (var18_18.mTarget != null) {
                                var10_10 = var18_18.mSolverVariable;
                                var12_12 = var18_18.mTarget.mSolverVariable;
                                var9_9 = var3_4;
                            } else {
                                var9_9 = var3_4;
                                if (var16_16.mBaseline.mTarget != null) {
                                    var10_10 = var16_16.mBaseline.mSolverVariable;
                                    var12_12 = var16_16.mBaseline.mTarget.mSolverVariable;
                                    var9_9 = var3_4 - var18_18.getMargin();
                                }
                            }
                            if (var10_10 != null && var12_12 != null) {
                                var1_1.addGreaterThan((SolverVariable)var10_10, (SolverVariable)var12_12, var9_9, var8_8);
                            }
                            var13_13 = var14_14;
                            if (var16_16.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                var10_10 = var16_16.mBottom;
                                if (var16_16.mMatchConstraintDefaultHeight == 1) {
                                    var3_4 = Math.max(var16_16.mMatchConstraintMinHeight, var16_16.getHeight());
                                    var1_1.addEquality(var10_10.mSolverVariable, var18_18.mSolverVariable, var3_4, 3);
                                    var13_13 = var14_14;
                                } else {
                                    var1_1.addGreaterThan(var18_18.mSolverVariable, var18_18.mTarget.mSolverVariable, var18_18.mMargin, 3);
                                    var1_1.addLowerThan(var10_10.mSolverVariable, var18_18.mSolverVariable, var16_16.mMatchConstraintMinHeight, 3);
                                    var13_13 = var14_14;
                                }
                            }
                        } else if (var5_5 == 0 && var7_7 != 0 && var13_13 != null) {
                            if (var16_16.mBottom.mTarget == null) {
                                var1_1.addEquality(var16_16.mBottom.mSolverVariable, var16_16.getDrawBottom());
                                var13_13 = var14_14;
                            } else {
                                var3_4 = var16_16.mBottom.getMargin();
                                var1_1.addEquality(var16_16.mBottom.mSolverVariable, var15_15.mBottom.mTarget.mSolverVariable, -var3_4, 5);
                                var13_13 = var14_14;
                            }
                        } else if (var5_5 == 0 && var7_7 == 0 && var13_13 == null) {
                            if (var16_16.mTop.mTarget == null) {
                                var1_1.addEquality(var16_16.mTop.mSolverVariable, var16_16.getDrawY());
                                var13_13 = var14_14;
                            } else {
                                var3_4 = var16_16.mTop.getMargin();
                                var1_1.addEquality(var16_16.mTop.mSolverVariable, var17_17.mTop.mTarget.mSolverVariable, var3_4, 5);
                                var13_13 = var14_14;
                            }
                        } else {
                            var18_18 = var16_16.mTop;
                            var19_19 = var16_16.mBottom;
                            var3_4 = var18_18.getMargin();
                            var8_8 = var19_19.getMargin();
                            var1_1.addGreaterThan(var18_18.mSolverVariable, var18_18.mTarget.mSolverVariable, var3_4, 1);
                            var1_1.addLowerThan(var19_19.mSolverVariable, var19_19.mTarget.mSolverVariable, -var8_8, 1);
                            var12_12 = var18_18.mTarget != null ? var18_18.mTarget.mSolverVariable : null;
                            if (var13_13 == null) {
                                var12_12 = var17_17.mTop.mTarget != null ? var17_17.mTop.mTarget.mSolverVariable : null;
                            }
                            var10_10 = var14_14;
                            if (var14_14 == null) {
                                var10_10 = var15_15.mBottom.mTarget != null ? var15_15.mBottom.mTarget.mOwner : null;
                            }
                            var13_13 = var10_10;
                            if (var10_10 != null) {
                                var14_14 = var10_10.mTop.mSolverVariable;
                                if (var7_7 != 0) {
                                    var14_14 = var15_15.mBottom.mTarget != null ? var15_15.mBottom.mTarget.mSolverVariable : null;
                                }
                                var13_13 = var10_10;
                                if (var12_12 != null) {
                                    var13_13 = var10_10;
                                    if (var14_14 != null) {
                                        var1_1.addCentering(var18_18.mSolverVariable, (SolverVariable)var12_12, var3_4, 0.5f, (SolverVariable)var14_14, var19_19.mSolverVariable, var8_8, 4);
                                        var13_13 = var10_10;
                                    }
                                }
                            }
                        }
                        var10_10 = var7_7 != 0 ? null : var13_13;
                        var13_13 = var16_16;
                    }
                    if (var6_6 == 0) ** GOTO lbl31
                    var12_12 = var11_11.mTop;
                    var13_13 = var15_15.mBottom;
                    var3_4 = var12_12.getMargin();
                    var5_5 = var13_13.getMargin();
                    var10_10 = var17_17.mTop.mTarget != null ? var17_17.mTop.mTarget.mSolverVariable : null;
                    var11_11 = var15_15.mBottom.mTarget != null ? var15_15.mBottom.mTarget.mSolverVariable : null;
                    if (var10_10 == null || var11_11 == null) ** GOTO lbl31
                    var1_1.addLowerThan(var13_13.mSolverVariable, (SolverVariable)var11_11, -var5_5, 1);
                    var1_1.addCentering(var12_12.mSolverVariable, (SolverVariable)var10_10, var3_4, var17_17.mVerticalBiasPercent, (SolverVariable)var11_11, var13_13.mSolverVariable, var5_5, 4);
                    ** GOTO lbl31
                }
                while (var11_11 != null) {
                    if (var11_11.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        var3_4 = var5_5 = var11_11.mTop.getMargin();
                        if (var10_10 != null) {
                            var3_4 = var5_5 + var10_10.mBottom.getMargin();
                        }
                        var5_5 = 3;
                        if (var11_11.mTop.mTarget.mOwner.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            var5_5 = 2;
                        }
                        var1_1.addGreaterThan(var11_11.mTop.mSolverVariable, var11_11.mTop.mTarget.mSolverVariable, var3_4, var5_5);
                        var3_4 = var5_5 = var11_11.mBottom.getMargin();
                        if (var11_11.mBottom.mTarget.mOwner.mTop.mTarget != null) {
                            var3_4 = var5_5;
                            if (var11_11.mBottom.mTarget.mOwner.mTop.mTarget.mOwner == var11_11) {
                                var3_4 = var5_5 + var11_11.mBottom.mTarget.mOwner.mTop.getMargin();
                            }
                        }
                        var5_5 = 3;
                        if (var11_11.mBottom.mTarget.mOwner.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            var5_5 = 2;
                        }
                        var1_1.addLowerThan(var11_11.mBottom.mSolverVariable, var11_11.mBottom.mTarget.mSolverVariable, -var3_4, var5_5);
                    } else {
                        var2_3 += var11_11.mVerticalWeight;
                        var3_4 = 0;
                        if (var11_11.mBottom.mTarget != null) {
                            var3_4 = var5_5 = var11_11.mBottom.getMargin();
                            if (var11_11 != this.mChainEnds[3]) {
                                var3_4 = var5_5 + var11_11.mBottom.mTarget.mOwner.mTop.getMargin();
                            }
                        }
                        var1_1.addGreaterThan(var11_11.mBottom.mSolverVariable, var11_11.mTop.mSolverVariable, 0, 1);
                        var1_1.addLowerThan(var11_11.mBottom.mSolverVariable, var11_11.mBottom.mTarget.mSolverVariable, -var3_4, 1);
                    }
                    var10_10 = var11_11;
                    var11_11 = var11_11.mVerticalNextWidget;
                }
                if (var7_7 != 1) break block67;
                var11_11 = this.mMatchConstraintsChainedWidgets[0];
                var3_4 = var5_5 = var11_11.mTop.getMargin();
                if (var11_11.mTop.mTarget != null) {
                    var3_4 = var5_5 + var11_11.mTop.mTarget.getMargin();
                }
                var5_5 = var6_6 = var11_11.mBottom.getMargin();
                if (var11_11.mBottom.mTarget != null) {
                    var5_5 = var6_6 + var11_11.mBottom.mTarget.getMargin();
                }
                var10_10 = var17_17.mBottom.mTarget.mSolverVariable;
                if (var11_11 == this.mChainEnds[3]) {
                    var10_10 = this.mChainEnds[1].mBottom.mTarget.mSolverVariable;
                }
                if (var11_11.mMatchConstraintDefaultHeight != 1) break block68;
                var1_1.addGreaterThan(var17_17.mTop.mSolverVariable, var17_17.mTop.mTarget.mSolverVariable, var3_4, 1);
                var1_1.addLowerThan(var17_17.mBottom.mSolverVariable, (SolverVariable)var10_10, -var5_5, 1);
                var1_1.addEquality(var17_17.mBottom.mSolverVariable, var17_17.mTop.mSolverVariable, var17_17.getHeight(), 2);
                ** GOTO lbl31
            }
            var1_1.addEquality(var11_11.mTop.mSolverVariable, var11_11.mTop.mTarget.mSolverVariable, var3_4, 1);
            var1_1.addEquality(var11_11.mBottom.mSolverVariable, (SolverVariable)var10_10, -var5_5, 1);
            ** GOTO lbl31
        }
        var3_4 = 0;
        do {
            if (var3_4 < var7_7 - 1) ** break;
            ** continue;
            var12_12 = this.mMatchConstraintsChainedWidgets[var3_4];
            var13_13 = this.mMatchConstraintsChainedWidgets[var3_4 + 1];
            var14_14 = var12_12.mTop.mSolverVariable;
            var15_15 = var12_12.mBottom.mSolverVariable;
            var16_16 = var13_13.mTop.mSolverVariable;
            var10_10 = var13_13.mBottom.mSolverVariable;
            if (var13_13 == this.mChainEnds[3]) {
                var10_10 = this.mChainEnds[1].mBottom.mSolverVariable;
            }
            var5_5 = var6_6 = var12_12.mTop.getMargin();
            if (var12_12.mTop.mTarget != null) {
                var5_5 = var6_6;
                if (var12_12.mTop.mTarget.mOwner.mBottom.mTarget != null) {
                    var5_5 = var6_6;
                    if (var12_12.mTop.mTarget.mOwner.mBottom.mTarget.mOwner == var12_12) {
                        var5_5 = var6_6 + var12_12.mTop.mTarget.mOwner.mBottom.getMargin();
                    }
                }
            }
            var1_1.addGreaterThan((SolverVariable)var14_14, var12_12.mTop.mTarget.mSolverVariable, var5_5, 2);
            var5_5 = var6_6 = var12_12.mBottom.getMargin();
            if (var12_12.mBottom.mTarget != null) {
                var5_5 = var6_6;
                if (var12_12.mVerticalNextWidget != null) {
                    var5_5 = var12_12.mVerticalNextWidget.mTop.mTarget != null ? var12_12.mVerticalNextWidget.mTop.getMargin() : 0;
                    var5_5 = var6_6 + var5_5;
                }
            }
            var1_1.addLowerThan((SolverVariable)var15_15, var12_12.mBottom.mTarget.mSolverVariable, -var5_5, 2);
            if (var3_4 + 1 == var7_7 - 1) {
                var5_5 = var6_6 = var13_13.mTop.getMargin();
                if (var13_13.mTop.mTarget != null) {
                    var5_5 = var6_6;
                    if (var13_13.mTop.mTarget.mOwner.mBottom.mTarget != null) {
                        var5_5 = var6_6;
                        if (var13_13.mTop.mTarget.mOwner.mBottom.mTarget.mOwner == var13_13) {
                            var5_5 = var6_6 + var13_13.mTop.mTarget.mOwner.mBottom.getMargin();
                        }
                    }
                }
                var1_1.addGreaterThan((SolverVariable)var16_16, var13_13.mTop.mTarget.mSolverVariable, var5_5, 2);
                var11_11 = var13_13.mBottom;
                if (var13_13 == this.mChainEnds[3]) {
                    var11_11 = this.mChainEnds[1].mBottom;
                }
                var5_5 = var6_6 = var11_11.getMargin();
                if (var11_11.mTarget != null) {
                    var5_5 = var6_6;
                    if (var11_11.mTarget.mOwner.mTop.mTarget != null) {
                        var5_5 = var6_6;
                        if (var11_11.mTarget.mOwner.mTop.mTarget.mOwner == var13_13) {
                            var5_5 = var6_6 + var11_11.mTarget.mOwner.mTop.getMargin();
                        }
                    }
                }
                var1_1.addLowerThan((SolverVariable)var10_10, var11_11.mTarget.mSolverVariable, -var5_5, 2);
            }
            if (var17_17.mMatchConstraintMaxHeight > 0) {
                var1_1.addLowerThan((SolverVariable)var15_15, (SolverVariable)var14_14, var17_17.mMatchConstraintMaxHeight, 2);
            }
            var11_11 = var1_1.createRow();
            var11_11.createRowEqualDimension(var12_12.mVerticalWeight, var2_3, var13_13.mVerticalWeight, (SolverVariable)var14_14, var12_12.mTop.getMargin(), (SolverVariable)var15_15, var12_12.mBottom.getMargin(), (SolverVariable)var16_16, var13_13.mTop.getMargin(), (SolverVariable)var10_10, var13_13.mBottom.getMargin());
            var1_1.addConstraint((ArrayRow)var11_11);
            ++var3_4;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int countMatchConstraintsChainedWidgets(LinearSystem linearSystem, ConstraintWidget[] arrconstraintWidget, ConstraintWidget constraintWidget, int n, boolean[] arrbl) {
        ConstraintWidget constraintWidget2;
        boolean bl;
        boolean bl2;
        ConstraintWidget constraintWidget3;
        ConstraintWidget constraintWidget4;
        ConstraintWidget constraintWidget5;
        int n2 = 0;
        int n3 = 0;
        arrbl[0] = true;
        arrbl[1] = false;
        arrconstraintWidget[0] = null;
        arrconstraintWidget[2] = null;
        arrconstraintWidget[1] = null;
        arrconstraintWidget[3] = null;
        if (n != 0) {
            bl2 = true;
            constraintWidget5 = null;
            bl = bl2;
            if (constraintWidget.mTop.mTarget != null) {
                bl = bl2;
                if (constraintWidget.mTop.mTarget.mOwner != this) {
                    bl = false;
                }
            }
            constraintWidget.mVerticalNextWidget = null;
            constraintWidget4 = null;
            if (constraintWidget.getVisibility() != 8) {
                constraintWidget4 = constraintWidget;
            }
        } else {
            ConstraintWidget constraintWidget6;
            ConstraintWidget constraintWidget7;
            boolean bl3 = true;
            ConstraintWidget constraintWidget8 = null;
            boolean bl4 = bl3;
            if (constraintWidget.mLeft.mTarget != null) {
                bl4 = bl3;
                if (constraintWidget.mLeft.mTarget.mOwner != this) {
                    bl4 = false;
                }
            }
            constraintWidget.mHorizontalNextWidget = null;
            ConstraintWidget constraintWidget9 = null;
            if (constraintWidget.getVisibility() != 8) {
                constraintWidget9 = constraintWidget;
            }
            ConstraintWidget constraintWidget10 = constraintWidget9;
            ConstraintWidget constraintWidget11 = constraintWidget;
            n = n3;
            do {
                n2 = n;
                constraintWidget6 = constraintWidget9;
                constraintWidget7 = constraintWidget10;
                if (constraintWidget11.mRight.mTarget == null) break;
                constraintWidget11.mHorizontalNextWidget = null;
                if (constraintWidget11.getVisibility() != 8) {
                    constraintWidget6 = constraintWidget9;
                    if (constraintWidget9 == null) {
                        constraintWidget6 = constraintWidget11;
                    }
                    if (constraintWidget10 != null && constraintWidget10 != constraintWidget11) {
                        constraintWidget10.mHorizontalNextWidget = constraintWidget11;
                    }
                    constraintWidget10 = constraintWidget11;
                    constraintWidget9 = constraintWidget6;
                } else {
                    linearSystem.addEquality(constraintWidget11.mLeft.mSolverVariable, constraintWidget11.mLeft.mTarget.mSolverVariable, 0, 5);
                    linearSystem.addEquality(constraintWidget11.mRight.mSolverVariable, constraintWidget11.mLeft.mSolverVariable, 0, 5);
                }
                n3 = n;
                if (constraintWidget11.getVisibility() != 8) {
                    n3 = n;
                    if (constraintWidget11.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        if (constraintWidget11.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            arrbl[0] = false;
                        }
                        n3 = n;
                        if (constraintWidget11.mDimensionRatio <= 0.0f) {
                            arrbl[0] = false;
                            if (n + 1 >= this.mMatchConstraintsChainedWidgets.length) {
                                this.mMatchConstraintsChainedWidgets = Arrays.copyOf(this.mMatchConstraintsChainedWidgets, this.mMatchConstraintsChainedWidgets.length * 2);
                            }
                            this.mMatchConstraintsChainedWidgets[n] = constraintWidget11;
                            n3 = n + 1;
                        }
                    }
                }
                if (constraintWidget11.mRight.mTarget.mOwner.mLeft.mTarget == null) {
                    constraintWidget7 = constraintWidget10;
                    constraintWidget6 = constraintWidget9;
                    n2 = n3;
                    break;
                }
                n2 = n3;
                constraintWidget6 = constraintWidget9;
                constraintWidget7 = constraintWidget10;
                if (constraintWidget11.mRight.mTarget.mOwner.mLeft.mTarget.mOwner != constraintWidget11) break;
                n2 = n3;
                constraintWidget6 = constraintWidget9;
                constraintWidget7 = constraintWidget10;
                if (constraintWidget11.mRight.mTarget.mOwner == constraintWidget11) break;
                constraintWidget8 = constraintWidget11 = constraintWidget11.mRight.mTarget.mOwner;
                n = n3;
            } while (true);
            bl3 = bl4;
            if (constraintWidget11.mRight.mTarget != null) {
                bl3 = bl4;
                if (constraintWidget11.mRight.mTarget.mOwner != this) {
                    bl3 = false;
                }
            }
            if (constraintWidget.mLeft.mTarget == null || constraintWidget8.mRight.mTarget == null) {
                arrbl[1] = true;
            }
            constraintWidget.mHorizontalChainFixedPosition = bl3;
            constraintWidget8.mHorizontalNextWidget = null;
            arrconstraintWidget[0] = constraintWidget;
            arrconstraintWidget[2] = constraintWidget6;
            arrconstraintWidget[1] = constraintWidget8;
            arrconstraintWidget[3] = constraintWidget7;
            return n2;
        }
        ConstraintWidget constraintWidget12 = constraintWidget4;
        ConstraintWidget constraintWidget13 = constraintWidget;
        n = n2;
        do {
            n2 = n;
            constraintWidget2 = constraintWidget4;
            constraintWidget3 = constraintWidget12;
            if (constraintWidget13.mBottom.mTarget == null) break;
            constraintWidget13.mVerticalNextWidget = null;
            if (constraintWidget13.getVisibility() != 8) {
                constraintWidget2 = constraintWidget4;
                if (constraintWidget4 == null) {
                    constraintWidget2 = constraintWidget13;
                }
                if (constraintWidget12 != null && constraintWidget12 != constraintWidget13) {
                    constraintWidget12.mVerticalNextWidget = constraintWidget13;
                }
                constraintWidget12 = constraintWidget13;
                constraintWidget4 = constraintWidget2;
            } else {
                linearSystem.addEquality(constraintWidget13.mTop.mSolverVariable, constraintWidget13.mTop.mTarget.mSolverVariable, 0, 5);
                linearSystem.addEquality(constraintWidget13.mBottom.mSolverVariable, constraintWidget13.mTop.mSolverVariable, 0, 5);
            }
            n3 = n;
            if (constraintWidget13.getVisibility() != 8) {
                n3 = n;
                if (constraintWidget13.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    if (constraintWidget13.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        arrbl[0] = false;
                    }
                    n3 = n;
                    if (constraintWidget13.mDimensionRatio <= 0.0f) {
                        arrbl[0] = false;
                        if (n + 1 >= this.mMatchConstraintsChainedWidgets.length) {
                            this.mMatchConstraintsChainedWidgets = Arrays.copyOf(this.mMatchConstraintsChainedWidgets, this.mMatchConstraintsChainedWidgets.length * 2);
                        }
                        this.mMatchConstraintsChainedWidgets[n] = constraintWidget13;
                        n3 = n + 1;
                    }
                }
            }
            if (constraintWidget13.mBottom.mTarget.mOwner.mTop.mTarget == null) {
                constraintWidget3 = constraintWidget12;
                constraintWidget2 = constraintWidget4;
                n2 = n3;
                break;
            }
            n2 = n3;
            constraintWidget2 = constraintWidget4;
            constraintWidget3 = constraintWidget12;
            if (constraintWidget13.mBottom.mTarget.mOwner.mTop.mTarget.mOwner != constraintWidget13) break;
            n2 = n3;
            constraintWidget2 = constraintWidget4;
            constraintWidget3 = constraintWidget12;
            if (constraintWidget13.mBottom.mTarget.mOwner == constraintWidget13) break;
            constraintWidget5 = constraintWidget13 = constraintWidget13.mBottom.mTarget.mOwner;
            n = n3;
        } while (true);
        bl2 = bl;
        if (constraintWidget13.mBottom.mTarget != null) {
            bl2 = bl;
            if (constraintWidget13.mBottom.mTarget.mOwner != this) {
                bl2 = false;
            }
        }
        if (constraintWidget.mTop.mTarget == null || constraintWidget5.mBottom.mTarget == null) {
            arrbl[1] = true;
        }
        constraintWidget.mVerticalChainFixedPosition = bl2;
        constraintWidget5.mVerticalNextWidget = null;
        arrconstraintWidget[0] = constraintWidget;
        arrconstraintWidget[2] = constraintWidget2;
        arrconstraintWidget[1] = constraintWidget5;
        arrconstraintWidget[3] = constraintWidget3;
        return n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean optimize(LinearSystem var1_1) {
        var11_2 = this.mChildren.size();
        var4_3 = 0;
        var8_4 = 0;
        var9_5 = 0;
        var10_6 = 0;
        var3_7 = 0;
        do {
            var5_9 = var9_5;
            var2_8 = var4_3;
            var6_10 = var8_4;
            var7_11 = var10_6;
            if (var3_7 >= var11_2) ** GOTO lbl39
            var12_12 = (ConstraintWidget)this.mChildren.get(var3_7);
            var12_12.mHorizontalResolution = -1;
            var12_12.mVerticalResolution = -1;
            if (var12_12.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var12_12.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                var12_12.mHorizontalResolution = 1;
                var12_12.mVerticalResolution = 1;
            }
            ++var3_7;
        } while (true);
        {
            block24: {
                if (var4_3 == 0 && var3_7 == 0) {
                    var2_8 = 1;
                    var7_11 = var8_4;
                    var6_10 = var4_3;
                    var5_9 = var3_7;
                } else {
                    var5_9 = var3_7;
                    var6_10 = var4_3;
                    var7_11 = var8_4;
                    if (var10_6 == var4_3) {
                        var5_9 = var3_7;
                        var6_10 = var4_3;
                        var7_11 = var8_4;
                        if (var9_5 == var3_7) {
                            var2_8 = 1;
                            var5_9 = var3_7;
                            var6_10 = var4_3;
                            var7_11 = var8_4;
                        }
                    }
                }
lbl39:
                // 7 sources
                var10_6 = var6_10;
                var9_5 = var5_9;
                if (var2_8 == 0) break block24;
                var5_9 = 0;
                var2_8 = 0;
                var3_7 = 0;
                do {
                    block28: {
                        block27: {
                            block26: {
                                block25: {
                                    if (var3_7 >= var11_2) {
                                        if (var5_9 != 0) return false;
                                        if (var2_8 != 0) return false;
                                        return true;
                                    }
                                    var1_1 = (ConstraintWidget)this.mChildren.get(var3_7);
                                    if (var1_1.mHorizontalResolution == 1) break block25;
                                    var4_3 = var5_9;
                                    if (var1_1.mHorizontalResolution != -1) break block26;
                                }
                                var4_3 = var5_9 + 1;
                            }
                            if (var1_1.mVerticalResolution == 1) break block27;
                            var6_10 = var2_8;
                            if (var1_1.mVerticalResolution != -1) break block28;
                        }
                        var6_10 = var2_8 + 1;
                    }
                    ++var3_7;
                    var5_9 = var4_3;
                    var2_8 = var6_10;
                } while (true);
            }
            var4_3 = 0;
            var3_7 = 0;
            var8_4 = var7_11 + 1;
            var5_9 = 0;
            do {
                if (var5_9 >= var11_2) continue block1;
                var12_12 = (ConstraintWidget)this.mChildren.get(var5_9);
                if (var12_12.mHorizontalResolution == -1) {
                    if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                        var12_12.mHorizontalResolution = 1;
                    } else {
                        Optimizer.checkHorizontalSimpleDependency(this, (LinearSystem)var1_1, var12_12);
                    }
                }
                if (var12_12.mVerticalResolution == -1) {
                    if (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                        var12_12.mVerticalResolution = 1;
                    } else {
                        Optimizer.checkVerticalSimpleDependency(this, (LinearSystem)var1_1, var12_12);
                    }
                }
                var6_10 = var4_3;
                if (var12_12.mVerticalResolution == -1) {
                    var6_10 = var4_3 + 1;
                }
                var4_3 = var3_7;
                if (var12_12.mHorizontalResolution == -1) {
                    var4_3 = var3_7 + 1;
                }
                ++var5_9;
                var3_7 = var4_3;
                var4_3 = var6_10;
            } while (true);
        }
    }

    private void resetChains() {
        this.mHorizontalChainsSize = 0;
        this.mVerticalChainsSize = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    void addChain(ConstraintWidget constraintWidget, int n) {
        if (n == 0) {
            while (constraintWidget.mLeft.mTarget != null && constraintWidget.mLeft.mTarget.mOwner.mRight.mTarget != null && constraintWidget.mLeft.mTarget.mOwner.mRight.mTarget == constraintWidget.mLeft && constraintWidget.mLeft.mTarget.mOwner != constraintWidget) {
                constraintWidget = constraintWidget.mLeft.mTarget.mOwner;
            }
            this.addHorizontalChain(constraintWidget);
            return;
        }
        if (n != 1) return;
        {
            while (constraintWidget.mTop.mTarget != null && constraintWidget.mTop.mTarget.mOwner.mBottom.mTarget != null && constraintWidget.mTop.mTarget.mOwner.mBottom.mTarget == constraintWidget.mTop && constraintWidget.mTop.mTarget.mOwner != constraintWidget) {
                constraintWidget = constraintWidget.mTop.mTarget.mOwner;
            }
        }
        this.addVerticalChain(constraintWidget);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean addChildrenToSolver(LinearSystem linearSystem, int n) {
        this.addToSolver(linearSystem, n);
        int n2 = this.mChildren.size();
        boolean bl = false;
        if (this.mOptimizationLevel == 2 || this.mOptimizationLevel == 4) {
            if (this.optimize(linearSystem)) {
                return false;
            }
        } else {
            bl = true;
        }
        for (int i = 0; i < n2; ++i) {
            ConstraintWidget constraintWidget = (ConstraintWidget)this.mChildren.get(i);
            if (constraintWidget instanceof ConstraintWidgetContainer) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = constraintWidget.mHorizontalDimensionBehaviour;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = constraintWidget.mVerticalDimensionBehaviour;
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                constraintWidget.addToSolver(linearSystem, n);
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.setHorizontalDimensionBehaviour(dimensionBehaviour);
                }
                if (dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) continue;
                constraintWidget.setVerticalDimensionBehaviour(dimensionBehaviour2);
                continue;
            }
            if (bl) {
                Optimizer.checkMatchParent(this, linearSystem, constraintWidget);
            }
            constraintWidget.addToSolver(linearSystem, n);
        }
        if (this.mHorizontalChainsSize > 0) {
            this.applyHorizontalChain(linearSystem);
        }
        if (this.mVerticalChainsSize > 0) {
            this.applyVerticalChain(linearSystem);
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void findHorizontalWrapRecursive(ConstraintWidget constraintWidget, boolean[] object) {
        int n;
        int n2;
        int n3;
        int n4;
        block33: {
            ConstraintWidget constraintWidget2;
            int n5;
            block39: {
                boolean bl;
                block38: {
                    block37: {
                        boolean bl2;
                        block35: {
                            ConstraintWidget constraintWidget3;
                            block36: {
                                ConstraintWidget constraintWidget4;
                                block34: {
                                    block32: {
                                        bl2 = false;
                                        if (constraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mDimensionRatio > 0.0f) {
                                            object[0] = false;
                                            return;
                                        }
                                        n = constraintWidget.getOptimizerWrapWidth();
                                        if (constraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mDimensionRatio > 0.0f) {
                                            object[0] = false;
                                            return;
                                        }
                                        n2 = n;
                                        constraintWidget2 = null;
                                        constraintWidget3 = null;
                                        constraintWidget.mHorizontalWrapVisited = true;
                                        if (!(constraintWidget instanceof Guideline)) break block32;
                                        object = (Guideline)constraintWidget;
                                        n3 = n;
                                        if (((Guideline)object).getOrientation() == 1) {
                                            n4 = 0;
                                            n2 = 0;
                                            if (((Guideline)object).getRelativeBegin() != -1) {
                                                n3 = ((Guideline)object).getRelativeBegin();
                                            } else {
                                                n3 = n4;
                                                if (((Guideline)object).getRelativeEnd() != -1) {
                                                    n2 = ((Guideline)object).getRelativeEnd();
                                                    n3 = n4;
                                                }
                                            }
                                        }
                                        break block33;
                                    }
                                    if (constraintWidget.mRight.isConnected() || constraintWidget.mLeft.isConnected()) break block34;
                                    n3 = n + constraintWidget.getX();
                                    break block33;
                                }
                                if (constraintWidget.mRight.mTarget != null && constraintWidget.mLeft.mTarget != null && (constraintWidget.mRight.mTarget == constraintWidget.mLeft.mTarget || constraintWidget.mRight.mTarget.mOwner == constraintWidget.mLeft.mTarget.mOwner && constraintWidget.mRight.mTarget.mOwner != constraintWidget.mParent)) {
                                    object[0] = false;
                                    return;
                                }
                                n3 = n2;
                                if (constraintWidget.mRight.mTarget != null) {
                                    constraintWidget4 = constraintWidget.mRight.mTarget.mOwner;
                                    n3 = n2 += constraintWidget.mRight.getMargin();
                                    constraintWidget3 = constraintWidget4;
                                    if (!constraintWidget4.isRoot()) {
                                        n3 = n2;
                                        constraintWidget3 = constraintWidget4;
                                        if (!constraintWidget4.mHorizontalWrapVisited) {
                                            this.findHorizontalWrapRecursive(constraintWidget4, (boolean[])object);
                                            constraintWidget3 = constraintWidget4;
                                            n3 = n2;
                                        }
                                    }
                                }
                                n4 = n;
                                if (constraintWidget.mLeft.mTarget != null) {
                                    constraintWidget4 = constraintWidget.mLeft.mTarget.mOwner;
                                    n4 = n2 = n + constraintWidget.mLeft.getMargin();
                                    constraintWidget2 = constraintWidget4;
                                    if (!constraintWidget4.isRoot()) {
                                        n4 = n2;
                                        constraintWidget2 = constraintWidget4;
                                        if (!constraintWidget4.mHorizontalWrapVisited) {
                                            this.findHorizontalWrapRecursive(constraintWidget4, (boolean[])object);
                                            constraintWidget2 = constraintWidget4;
                                            n4 = n2;
                                        }
                                    }
                                }
                                n = n3;
                                if (constraintWidget.mRight.mTarget == null) break block35;
                                n = n3;
                                if (constraintWidget3.isRoot()) break block35;
                                if (constraintWidget.mRight.mTarget.mType == ConstraintAnchor.Type.RIGHT) {
                                    n2 = n3 + (constraintWidget3.mDistToRight - constraintWidget3.getOptimizerWrapWidth());
                                } else {
                                    n2 = n3;
                                    if (constraintWidget.mRight.mTarget.getType() == ConstraintAnchor.Type.LEFT) {
                                        n2 = n3 + constraintWidget3.mDistToRight;
                                    }
                                }
                                bl = constraintWidget3.mRightHasCentered || constraintWidget3.mLeft.mTarget != null && constraintWidget3.mRight.mTarget != null && constraintWidget3.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                                constraintWidget.mRightHasCentered = bl;
                                n = n2;
                                if (!constraintWidget.mRightHasCentered) break block35;
                                if (constraintWidget3.mLeft.mTarget == null) break block36;
                                n = n2;
                                if (constraintWidget3.mLeft.mTarget.mOwner == constraintWidget) break block35;
                            }
                            n = n2 + (n2 - constraintWidget3.mDistToRight);
                        }
                        n3 = n4;
                        n2 = n;
                        if (constraintWidget.mLeft.mTarget == null) break block33;
                        n3 = n4;
                        n2 = n;
                        if (constraintWidget2.isRoot()) break block33;
                        if (constraintWidget.mLeft.mTarget.getType() == ConstraintAnchor.Type.LEFT) {
                            n5 = n4 + (constraintWidget2.mDistToLeft - constraintWidget2.getOptimizerWrapWidth());
                        } else {
                            n5 = n4;
                            if (constraintWidget.mLeft.mTarget.getType() == ConstraintAnchor.Type.RIGHT) {
                                n5 = n4 + constraintWidget2.mDistToLeft;
                            }
                        }
                        if (constraintWidget2.mLeftHasCentered) break block37;
                        bl = bl2;
                        if (constraintWidget2.mLeft.mTarget == null) break block38;
                        bl = bl2;
                        if (constraintWidget2.mRight.mTarget == null) break block38;
                        bl = bl2;
                        if (constraintWidget2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) break block38;
                    }
                    bl = true;
                }
                constraintWidget.mLeftHasCentered = bl;
                n3 = n5;
                n2 = n;
                if (!constraintWidget.mLeftHasCentered) break block33;
                if (constraintWidget2.mRight.mTarget == null) break block39;
                n3 = n5;
                n2 = n;
                if (constraintWidget2.mRight.mTarget.mOwner == constraintWidget) break block33;
            }
            n3 = n5 + (n5 - constraintWidget2.mDistToLeft);
            n2 = n;
        }
        n = n3;
        n4 = n2;
        if (constraintWidget.getVisibility() == 8) {
            n = n3 - constraintWidget.mWidth;
            n4 = n2 - constraintWidget.mWidth;
        }
        constraintWidget.mDistToLeft = n;
        constraintWidget.mDistToRight = n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void findVerticalWrapRecursive(ConstraintWidget constraintWidget, boolean[] object) {
        int n;
        int n2;
        int n3;
        int n4;
        block35: {
            int n5;
            ConstraintWidget constraintWidget2;
            block41: {
                boolean bl;
                block40: {
                    block39: {
                        boolean bl2;
                        block37: {
                            ConstraintWidget constraintWidget3;
                            block38: {
                                ConstraintWidget constraintWidget4;
                                block36: {
                                    block34: {
                                        bl2 = false;
                                        if (constraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mDimensionRatio > 0.0f) {
                                            object[0] = false;
                                            return;
                                        }
                                        n3 = n2 = constraintWidget.getOptimizerWrapHeight();
                                        n = n2;
                                        constraintWidget3 = null;
                                        constraintWidget2 = null;
                                        constraintWidget.mVerticalWrapVisited = true;
                                        if (!(constraintWidget instanceof Guideline)) break block34;
                                        object = (Guideline)constraintWidget;
                                        n2 = n;
                                        n = n3;
                                        if (((Guideline)object).getOrientation() == 0) {
                                            n3 = 0;
                                            n2 = 0;
                                            if (((Guideline)object).getRelativeBegin() != -1) {
                                                n = ((Guideline)object).getRelativeBegin();
                                            } else {
                                                n = n3;
                                                if (((Guideline)object).getRelativeEnd() != -1) {
                                                    n2 = ((Guideline)object).getRelativeEnd();
                                                    n = n3;
                                                }
                                            }
                                        }
                                        break block35;
                                    }
                                    if (constraintWidget.mBaseline.mTarget != null || constraintWidget.mTop.mTarget != null || constraintWidget.mBottom.mTarget != null) break block36;
                                    n2 = n;
                                    n = n3 += constraintWidget.getY();
                                    break block35;
                                }
                                if (constraintWidget.mBottom.mTarget != null && constraintWidget.mTop.mTarget != null && (constraintWidget.mBottom.mTarget == constraintWidget.mTop.mTarget || constraintWidget.mBottom.mTarget.mOwner == constraintWidget.mTop.mTarget.mOwner && constraintWidget.mBottom.mTarget.mOwner != constraintWidget.mParent)) {
                                    object[0] = false;
                                    return;
                                }
                                if (constraintWidget.mBaseline.isConnected()) {
                                    int n6;
                                    constraintWidget3 = constraintWidget.mBaseline.mTarget.getOwner();
                                    if (!constraintWidget3.mVerticalWrapVisited) {
                                        this.findVerticalWrapRecursive(constraintWidget3, (boolean[])object);
                                    }
                                    n3 = Math.max(constraintWidget3.mDistToTop - constraintWidget3.mHeight + n2, n2);
                                    n = n6 = Math.max(constraintWidget3.mDistToBottom - constraintWidget3.mHeight + n2, n2);
                                    n2 = n3;
                                    if (constraintWidget.getVisibility() == 8) {
                                        n2 = n3 - constraintWidget.mHeight;
                                        n = n6 - constraintWidget.mHeight;
                                    }
                                    constraintWidget.mDistToTop = n2;
                                    constraintWidget.mDistToBottom = n;
                                    return;
                                }
                                n2 = n3;
                                if (constraintWidget.mTop.isConnected()) {
                                    constraintWidget4 = constraintWidget.mTop.mTarget.getOwner();
                                    n2 = n3 += constraintWidget.mTop.getMargin();
                                    constraintWidget3 = constraintWidget4;
                                    if (!constraintWidget4.isRoot()) {
                                        n2 = n3;
                                        constraintWidget3 = constraintWidget4;
                                        if (!constraintWidget4.mVerticalWrapVisited) {
                                            this.findVerticalWrapRecursive(constraintWidget4, (boolean[])object);
                                            constraintWidget3 = constraintWidget4;
                                            n2 = n3;
                                        }
                                    }
                                }
                                n3 = n;
                                if (constraintWidget.mBottom.isConnected()) {
                                    constraintWidget4 = constraintWidget.mBottom.mTarget.getOwner();
                                    constraintWidget2 = constraintWidget4;
                                    n3 = n += constraintWidget.mBottom.getMargin();
                                    if (!constraintWidget4.isRoot()) {
                                        constraintWidget2 = constraintWidget4;
                                        n3 = n;
                                        if (!constraintWidget4.mVerticalWrapVisited) {
                                            this.findVerticalWrapRecursive(constraintWidget4, (boolean[])object);
                                            n3 = n;
                                            constraintWidget2 = constraintWidget4;
                                        }
                                    }
                                }
                                n4 = n2;
                                if (constraintWidget.mTop.mTarget == null) break block37;
                                n4 = n2;
                                if (constraintWidget3.isRoot()) break block37;
                                if (constraintWidget.mTop.mTarget.getType() == ConstraintAnchor.Type.TOP) {
                                    n = n2 + (constraintWidget3.mDistToTop - constraintWidget3.getOptimizerWrapHeight());
                                } else {
                                    n = n2;
                                    if (constraintWidget.mTop.mTarget.getType() == ConstraintAnchor.Type.BOTTOM) {
                                        n = n2 + constraintWidget3.mDistToTop;
                                    }
                                }
                                bl = constraintWidget3.mTopHasCentered || constraintWidget3.mTop.mTarget != null && constraintWidget3.mTop.mTarget.mOwner != constraintWidget && constraintWidget3.mBottom.mTarget != null && constraintWidget3.mBottom.mTarget.mOwner != constraintWidget && constraintWidget3.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                                constraintWidget.mTopHasCentered = bl;
                                n4 = n;
                                if (!constraintWidget.mTopHasCentered) break block37;
                                if (constraintWidget3.mBottom.mTarget == null) break block38;
                                n4 = n;
                                if (constraintWidget3.mBottom.mTarget.mOwner == constraintWidget) break block37;
                            }
                            n4 = n + (n - constraintWidget3.mDistToTop);
                        }
                        n2 = n3;
                        n = n4;
                        if (constraintWidget.mBottom.mTarget == null) break block35;
                        n2 = n3;
                        n = n4;
                        if (constraintWidget2.isRoot()) break block35;
                        if (constraintWidget.mBottom.mTarget.getType() == ConstraintAnchor.Type.BOTTOM) {
                            n5 = n3 + (constraintWidget2.mDistToBottom - constraintWidget2.getOptimizerWrapHeight());
                        } else {
                            n5 = n3;
                            if (constraintWidget.mBottom.mTarget.getType() == ConstraintAnchor.Type.TOP) {
                                n5 = n3 + constraintWidget2.mDistToBottom;
                            }
                        }
                        if (constraintWidget2.mBottomHasCentered) break block39;
                        bl = bl2;
                        if (constraintWidget2.mTop.mTarget == null) break block40;
                        bl = bl2;
                        if (constraintWidget2.mTop.mTarget.mOwner == constraintWidget) break block40;
                        bl = bl2;
                        if (constraintWidget2.mBottom.mTarget == null) break block40;
                        bl = bl2;
                        if (constraintWidget2.mBottom.mTarget.mOwner == constraintWidget) break block40;
                        bl = bl2;
                        if (constraintWidget2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) break block40;
                    }
                    bl = true;
                }
                constraintWidget.mBottomHasCentered = bl;
                n2 = n5;
                n = n4;
                if (!constraintWidget.mBottomHasCentered) break block35;
                if (constraintWidget2.mTop.mTarget == null) break block41;
                n2 = n5;
                n = n4;
                if (constraintWidget2.mTop.mTarget.mOwner == constraintWidget) break block35;
            }
            n2 = n5 + (n5 - constraintWidget2.mDistToBottom);
            n = n4;
        }
        n4 = n2;
        n3 = n;
        if (constraintWidget.getVisibility() == 8) {
            n3 = n - constraintWidget.mHeight;
            n4 = n2 - constraintWidget.mHeight;
        }
        constraintWidget.mDistToTop = n3;
        constraintWidget.mDistToBottom = n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void findWrapSize(ArrayList<ConstraintWidget> arrayList, boolean[] object) {
        int n;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = arrayList.size();
        object[0] = true;
        for (int i = 0; i < n8; ++i) {
            ConstraintWidget constraintWidget = arrayList.get(i);
            if (constraintWidget.isRoot()) continue;
            if (!constraintWidget.mHorizontalWrapVisited) {
                this.findHorizontalWrapRecursive(constraintWidget, (boolean[])object);
            }
            if (!constraintWidget.mVerticalWrapVisited) {
                this.findVerticalWrapRecursive(constraintWidget, (boolean[])object);
            }
            if (!object[0]) return;
            {
                n = constraintWidget.mDistToLeft + constraintWidget.mDistToRight - constraintWidget.getWidth();
                int n9 = constraintWidget.mDistToTop + constraintWidget.mDistToBottom - constraintWidget.getHeight();
                if (constraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                    n = constraintWidget.getWidth() + constraintWidget.mLeft.mMargin + constraintWidget.mRight.mMargin;
                }
                if (constraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                    n9 = constraintWidget.getHeight() + constraintWidget.mTop.mMargin + constraintWidget.mBottom.mMargin;
                }
                if (constraintWidget.getVisibility() == 8) {
                    n = 0;
                    n9 = 0;
                }
                n3 = Math.max(n3, constraintWidget.mDistToLeft);
                n4 = Math.max(n4, constraintWidget.mDistToRight);
                n5 = Math.max(n5, constraintWidget.mDistToBottom);
                n2 = Math.max(n2, constraintWidget.mDistToTop);
                n6 = Math.max(n6, n);
                n7 = Math.max(n7, n9);
                continue;
            }
        }
        n = Math.max(n3, n4);
        this.mWrapWidth = Math.max(this.mMinWidth, Math.max(n, n6));
        n = Math.max(n2, n5);
        this.mWrapHeight = Math.max(this.mMinHeight, Math.max(n, n7));
        for (n = 0; n < n8; ++n) {
            ConstraintWidget constraintWidget = arrayList.get(n);
            constraintWidget.mHorizontalWrapVisited = false;
            constraintWidget.mVerticalWrapVisited = false;
            constraintWidget.mLeftHasCentered = false;
            constraintWidget.mRightHasCentered = false;
            constraintWidget.mTopHasCentered = false;
            constraintWidget.mBottomHasCentered = false;
        }
    }

    public boolean handlesInternalConstraints() {
        return false;
    }

    public boolean isHeightMeasuredTooSmall() {
        return this.mHeightMeasuredTooSmall;
    }

    public boolean isWidthMeasuredTooSmall() {
        return this.mWidthMeasuredTooSmall;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void layout() {
        block40: {
            block42: {
                block43: {
                    block41: {
                        var5_1 = this.mX;
                        var6_2 = this.mY;
                        var7_3 = Math.max(0, this.getWidth());
                        var8_4 = Math.max(0, this.getHeight());
                        this.mWidthMeasuredTooSmall = false;
                        this.mHeightMeasuredTooSmall = false;
                        if (this.mParent != null) {
                            if (this.mSnapshot == null) {
                                this.mSnapshot = new Snapshot(this);
                            }
                            this.mSnapshot.updateFrom(this);
                            this.setX(this.mPaddingLeft);
                            this.setY(this.mPaddingTop);
                            this.resetAnchors();
                            this.resetSolverVariables(this.mSystem.getCache());
                        } else {
                            this.mX = 0;
                            this.mY = 0;
                        }
                        var11_5 = false;
                        var16_6 = this.mVerticalDimensionBehaviour;
                        var17_7 = this.mHorizontalDimensionBehaviour;
                        var10_8 = var11_5;
                        if (this.mOptimizationLevel != 2) break block40;
                        if (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) break block41;
                        var10_8 = var11_5;
                        if (this.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) break block40;
                    }
                    this.findWrapSize(this.mChildren, this.flags);
                    var11_5 = var10_8 = this.flags[0];
                    if (var7_3 <= 0) break block42;
                    var11_5 = var10_8;
                    if (var8_4 <= 0) break block42;
                    if (this.mWrapWidth > var7_3) break block43;
                    var11_5 = var10_8;
                    if (this.mWrapHeight <= var8_4) break block42;
                }
                var11_5 = false;
            }
            var10_8 = var11_5;
            if (var11_5) {
                if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    if (var7_3 > 0 && var7_3 < this.mWrapWidth) {
                        this.mWidthMeasuredTooSmall = true;
                        this.setWidth(var7_3);
                    } else {
                        this.setWidth(Math.max(this.mMinWidth, this.mWrapWidth));
                    }
                }
                var10_8 = var11_5;
                if (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    if (var8_4 > 0 && var8_4 < this.mWrapHeight) {
                        this.mHeightMeasuredTooSmall = true;
                        this.setHeight(var8_4);
                        var10_8 = var11_5;
                    } else {
                        this.setHeight(Math.max(this.mMinHeight, this.mWrapHeight));
                        var10_8 = var11_5;
                    }
                }
            }
        }
        this.resetChains();
        var9_9 = this.mChildren.size();
        for (var1_10 = 0; var1_10 < var9_9; ++var1_10) {
            var18_11 = (ConstraintWidget)this.mChildren.get(var1_10);
            if (!(var18_11 instanceof WidgetContainer)) continue;
            ((WidgetContainer)var18_11).layout();
        }
        var11_5 = true;
        var1_10 = 0;
        block3: do {
            if (var11_5) {
                var4_15 = var1_10 + 1;
                var12_16 = var11_5;
                try {
                    this.mSystem.reset();
                    var12_16 = var11_5;
                    var12_16 = var11_5 = this.addChildrenToSolver(this.mSystem, Integer.MAX_VALUE);
                    if (var11_5) {
                        var12_16 = var11_5;
                        this.mSystem.minimize();
                        var12_16 = var11_5;
                    }
                }
                catch (Exception var18_12) {
                    var18_12.printStackTrace();
                }
                if (!var12_16) break;
                this.updateChildrenFromSolver(this.mSystem, Integer.MAX_VALUE, this.flags);
            } else {
                if (this.mParent != null) {
                    var1_10 = Math.max(this.mMinWidth, this.getWidth());
                    var2_13 = Math.max(this.mMinHeight, this.getHeight());
                    this.mSnapshot.applyTo(this);
                    this.setWidth(this.mPaddingLeft + var1_10 + this.mPaddingRight);
                    this.setHeight(this.mPaddingTop + var2_13 + this.mPaddingBottom);
                } else {
                    this.mX = var5_1;
                    this.mY = var6_2;
                }
                if (var10_8) {
                    this.mHorizontalDimensionBehaviour = var17_7;
                    this.mVerticalDimensionBehaviour = var16_6;
                }
                this.resetSolverVariables(this.mSystem.getCache());
                if (this != this.getRootConstraintContainer()) return;
                this.updateDrawPosition();
                return;
            }
lbl101:
            // 4 sources
            do {
                var13_17 = false;
                var15_19 = false;
                var12_16 = var13_17;
                var11_5 = var10_8;
                if (var4_15 < 8) {
                    var12_16 = var13_17;
                    var11_5 = var10_8;
                    if (this.flags[2]) {
                        var3_14 = 0;
                        var1_10 = 0;
                        for (var2_13 = 0; var2_13 < var9_9; ++var2_13) {
                            var18_11 = (ConstraintWidget)this.mChildren.get(var2_13);
                            var3_14 = Math.max(var3_14, var18_11.mX + var18_11.getWidth());
                            var1_10 = Math.max(var1_10, var18_11.mY + var18_11.getHeight());
                        }
                        var2_13 = Math.max(this.mMinWidth, var3_14);
                        var1_10 = Math.max(this.mMinHeight, var1_10);
                        var14_18 = var15_19;
                        var13_17 = var10_8;
                        if (var17_7 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                            var14_18 = var15_19;
                            var13_17 = var10_8;
                            if (this.getWidth() < var2_13) {
                                this.setWidth(var2_13);
                                this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                var13_17 = true;
                                var14_18 = true;
                            }
                        }
                        var12_16 = var14_18;
                        var11_5 = var13_17;
                        if (var16_6 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                            var12_16 = var14_18;
                            var11_5 = var13_17;
                            if (this.getHeight() < var1_10) {
                                this.setHeight(var1_10);
                                this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                                var11_5 = true;
                                var12_16 = true;
                            }
                        }
                    }
                }
                if ((var1_10 = Math.max(this.mMinWidth, this.getWidth())) > this.getWidth()) {
                    this.setWidth(var1_10);
                    this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    var11_5 = true;
                    var12_16 = true;
                }
                var1_10 = Math.max(this.mMinHeight, this.getHeight());
                var13_17 = var11_5;
                if (var1_10 > this.getHeight()) {
                    this.setHeight(var1_10);
                    this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                    var13_17 = true;
                    var12_16 = true;
                }
                var1_10 = var4_15;
                var11_5 = var12_16;
                var10_8 = var13_17;
                if (var13_17) continue block3;
                var15_19 = var12_16;
                var14_18 = var13_17;
                if (this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    var15_19 = var12_16;
                    var14_18 = var13_17;
                    if (var7_3 > 0) {
                        var15_19 = var12_16;
                        var14_18 = var13_17;
                        if (this.getWidth() > var7_3) {
                            this.mWidthMeasuredTooSmall = true;
                            var14_18 = true;
                            this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                            this.setWidth(var7_3);
                            var15_19 = true;
                        }
                    }
                }
                var1_10 = var4_15;
                var11_5 = var15_19;
                var10_8 = var14_18;
                if (this.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) continue block3;
                var1_10 = var4_15;
                var11_5 = var15_19;
                var10_8 = var14_18;
                if (var8_4 <= 0) continue block3;
                var1_10 = var4_15;
                var11_5 = var15_19;
                var10_8 = var14_18;
                if (this.getHeight() <= var8_4) continue block3;
                this.mHeightMeasuredTooSmall = true;
                var10_8 = true;
                this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                this.setHeight(var8_4);
                var11_5 = true;
                var1_10 = var4_15;
                continue block3;
                break;
            } while (true);
            break;
        } while (true);
        this.updateFromSolver(this.mSystem, Integer.MAX_VALUE);
        var1_10 = 0;
        do {
            block44: {
                if (var1_10 >= var9_9) ** GOTO lbl101
                var18_11 = (ConstraintWidget)this.mChildren.get(var1_10);
                if (var18_11.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var18_11.getWidth() >= var18_11.getWrapWidth()) break block44;
                this.flags[2] = true;
                ** GOTO lbl101
            }
            if (var18_11.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var18_11.getHeight() < var18_11.getWrapHeight()) {
                this.flags[2] = true;
                ** continue;
            }
            ++var1_10;
        } while (true);
    }

    @Override
    public void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mPaddingTop = 0;
        this.mPaddingBottom = 0;
        super.reset();
    }

    public void setOptimizationLevel(int n) {
        this.mOptimizationLevel = n;
    }

    public void updateChildrenFromSolver(LinearSystem linearSystem, int n, boolean[] arrbl) {
        arrbl[2] = false;
        this.updateFromSolver(linearSystem, n);
        int n2 = this.mChildren.size();
        for (int i = 0; i < n2; ++i) {
            ConstraintWidget constraintWidget = (ConstraintWidget)this.mChildren.get(i);
            constraintWidget.updateFromSolver(linearSystem, n);
            if (constraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.getWidth() < constraintWidget.getWrapWidth()) {
                arrbl[2] = true;
            }
            if (constraintWidget.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.getHeight() >= constraintWidget.getWrapHeight()) continue;
            arrbl[2] = true;
        }
    }
}

