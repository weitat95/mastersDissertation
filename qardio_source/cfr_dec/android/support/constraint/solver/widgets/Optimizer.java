/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Guideline;

public class Optimizer {
    /*
     * Enabled aggressive block sorting
     */
    static void applyDirectResolutionHorizontalChain(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int n, ConstraintWidget constraintWidget) {
        int n2;
        int n3;
        float f;
        ConstraintWidget constraintWidget2;
        int n4 = 0;
        ConstraintWidget constraintWidget3 = null;
        int n5 = 0;
        float f2 = 0.0f;
        ConstraintWidget constraintWidget4 = constraintWidget;
        while (constraintWidget4 != null) {
            n3 = constraintWidget4.getVisibility() == 8 ? 1 : 0;
            int n6 = n5;
            f = f2;
            n2 = n4;
            if (n3 == 0) {
                n6 = n5 + 1;
                if (constraintWidget4.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    n5 = constraintWidget4.getWidth();
                    n2 = constraintWidget4.mLeft.mTarget != null ? constraintWidget4.mLeft.getMargin() : 0;
                    n3 = constraintWidget4.mRight.mTarget != null ? constraintWidget4.mRight.getMargin() : 0;
                    n2 = n4 + n5 + n2 + n3;
                    f = f2;
                } else {
                    f = f2 + constraintWidget4.mHorizontalWeight;
                    n2 = n4;
                }
            }
            ConstraintWidget constraintWidget5 = constraintWidget4;
            constraintWidget2 = constraintWidget4.mRight.mTarget != null ? constraintWidget4.mRight.mTarget.mOwner : null;
            n5 = n6;
            constraintWidget3 = constraintWidget5;
            f2 = f;
            n4 = n2;
            constraintWidget4 = constraintWidget2;
            if (constraintWidget2 == null) continue;
            if (constraintWidget2.mLeft.mTarget != null) {
                n5 = n6;
                constraintWidget3 = constraintWidget5;
                f2 = f;
                n4 = n2;
                constraintWidget4 = constraintWidget2;
                if (constraintWidget2.mLeft.mTarget == null) continue;
                n5 = n6;
                constraintWidget3 = constraintWidget5;
                f2 = f;
                n4 = n2;
                constraintWidget4 = constraintWidget2;
                if (constraintWidget2.mLeft.mTarget.mOwner == constraintWidget5) continue;
            }
            constraintWidget4 = null;
            n5 = n6;
            constraintWidget3 = constraintWidget5;
            f2 = f;
            n4 = n2;
        }
        n3 = 0;
        if (constraintWidget3 != null) {
            n2 = constraintWidget3.mRight.mTarget != null ? constraintWidget3.mRight.mTarget.mOwner.getX() : 0;
            n3 = n2;
            if (constraintWidget3.mRight.mTarget != null) {
                n3 = n2;
                if (constraintWidget3.mRight.mTarget.mOwner == constraintWidgetContainer) {
                    n3 = constraintWidgetContainer.getRight();
                }
            }
        }
        float f3 = (float)(n3 - 0) - (float)n4;
        float f4 = f3 / (float)(n5 + 1);
        f = 0.0f;
        if (n == 0) {
            f = f4;
        } else {
            f4 = f3 / (float)n;
        }
        while (constraintWidget != null) {
            float f5;
            n2 = constraintWidget.mLeft.mTarget != null ? constraintWidget.mLeft.getMargin() : 0;
            n3 = constraintWidget.mRight.mTarget != null ? constraintWidget.mRight.getMargin() : 0;
            if (constraintWidget.getVisibility() != 8) {
                linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, (int)(0.5f + (f += (float)n2)));
                f = constraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? (f2 == 0.0f ? (f += f4 - (float)n2 - (float)n3) : (f += constraintWidget.mHorizontalWeight * f3 / f2 - (float)n2 - (float)n3)) : (f += (float)constraintWidget.getWidth());
                linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, (int)(0.5f + f));
                f5 = f;
                if (n == 0) {
                    f5 = f + f4;
                }
                f5 += (float)n3;
            } else {
                f5 = f - f4 / 2.0f;
                linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, (int)(0.5f + f5));
                linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, (int)(0.5f + f5));
                f5 = f;
            }
            constraintWidget4 = constraintWidget.mRight.mTarget != null ? constraintWidget.mRight.mTarget.mOwner : null;
            constraintWidget2 = constraintWidget4;
            if (constraintWidget4 != null) {
                constraintWidget2 = constraintWidget4;
                if (constraintWidget4.mLeft.mTarget != null) {
                    constraintWidget2 = constraintWidget4;
                    if (constraintWidget4.mLeft.mTarget.mOwner != constraintWidget) {
                        constraintWidget2 = null;
                    }
                }
            }
            f = f5;
            constraintWidget = constraintWidget2;
            if (constraintWidget2 != constraintWidgetContainer) continue;
            constraintWidget = null;
            f = f5;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    static void applyDirectResolutionVerticalChain(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int n, ConstraintWidget constraintWidget) {
        int n2;
        int n3;
        float f;
        ConstraintWidget constraintWidget2;
        int n4 = 0;
        ConstraintWidget constraintWidget3 = null;
        int n5 = 0;
        float f2 = 0.0f;
        ConstraintWidget constraintWidget4 = constraintWidget;
        while (constraintWidget4 != null) {
            n3 = constraintWidget4.getVisibility() == 8 ? 1 : 0;
            int n6 = n5;
            f = f2;
            n2 = n4;
            if (n3 == 0) {
                n6 = n5 + 1;
                if (constraintWidget4.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    n5 = constraintWidget4.getHeight();
                    n2 = constraintWidget4.mTop.mTarget != null ? constraintWidget4.mTop.getMargin() : 0;
                    n3 = constraintWidget4.mBottom.mTarget != null ? constraintWidget4.mBottom.getMargin() : 0;
                    n2 = n4 + n5 + n2 + n3;
                    f = f2;
                } else {
                    f = f2 + constraintWidget4.mVerticalWeight;
                    n2 = n4;
                }
            }
            ConstraintWidget constraintWidget5 = constraintWidget4;
            constraintWidget2 = constraintWidget4.mBottom.mTarget != null ? constraintWidget4.mBottom.mTarget.mOwner : null;
            n5 = n6;
            constraintWidget3 = constraintWidget5;
            f2 = f;
            n4 = n2;
            constraintWidget4 = constraintWidget2;
            if (constraintWidget2 == null) continue;
            if (constraintWidget2.mTop.mTarget != null) {
                n5 = n6;
                constraintWidget3 = constraintWidget5;
                f2 = f;
                n4 = n2;
                constraintWidget4 = constraintWidget2;
                if (constraintWidget2.mTop.mTarget == null) continue;
                n5 = n6;
                constraintWidget3 = constraintWidget5;
                f2 = f;
                n4 = n2;
                constraintWidget4 = constraintWidget2;
                if (constraintWidget2.mTop.mTarget.mOwner == constraintWidget5) continue;
            }
            constraintWidget4 = null;
            n5 = n6;
            constraintWidget3 = constraintWidget5;
            f2 = f;
            n4 = n2;
        }
        n3 = 0;
        if (constraintWidget3 != null) {
            n2 = constraintWidget3.mBottom.mTarget != null ? constraintWidget3.mBottom.mTarget.mOwner.getX() : 0;
            n3 = n2;
            if (constraintWidget3.mBottom.mTarget != null) {
                n3 = n2;
                if (constraintWidget3.mBottom.mTarget.mOwner == constraintWidgetContainer) {
                    n3 = constraintWidgetContainer.getBottom();
                }
            }
        }
        float f3 = (float)(n3 - 0) - (float)n4;
        float f4 = f3 / (float)(n5 + 1);
        f = 0.0f;
        if (n == 0) {
            f = f4;
        } else {
            f4 = f3 / (float)n;
        }
        while (constraintWidget != null) {
            float f5;
            n2 = constraintWidget.mTop.mTarget != null ? constraintWidget.mTop.getMargin() : 0;
            n3 = constraintWidget.mBottom.mTarget != null ? constraintWidget.mBottom.getMargin() : 0;
            if (constraintWidget.getVisibility() != 8) {
                linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, (int)(0.5f + (f += (float)n2)));
                f = constraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT ? (f2 == 0.0f ? (f += f4 - (float)n2 - (float)n3) : (f += constraintWidget.mVerticalWeight * f3 / f2 - (float)n2 - (float)n3)) : (f += (float)constraintWidget.getHeight());
                linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, (int)(0.5f + f));
                f5 = f;
                if (n == 0) {
                    f5 = f + f4;
                }
                f5 += (float)n3;
            } else {
                f5 = f - f4 / 2.0f;
                linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, (int)(0.5f + f5));
                linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, (int)(0.5f + f5));
                f5 = f;
            }
            constraintWidget4 = constraintWidget.mBottom.mTarget != null ? constraintWidget.mBottom.mTarget.mOwner : null;
            constraintWidget2 = constraintWidget4;
            if (constraintWidget4 != null) {
                constraintWidget2 = constraintWidget4;
                if (constraintWidget4.mTop.mTarget != null) {
                    constraintWidget2 = constraintWidget4;
                    if (constraintWidget4.mTop.mTarget.mOwner != constraintWidget) {
                        constraintWidget2 = null;
                    }
                }
            }
            f = f5;
            constraintWidget = constraintWidget2;
            if (constraintWidget2 != constraintWidgetContainer) continue;
            constraintWidget = null;
            f = f5;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    static void checkHorizontalSimpleDependency(ConstraintWidgetContainer object, LinearSystem linearSystem, ConstraintWidget constraintWidget) {
        int n;
        float f;
        if (constraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            constraintWidget.mHorizontalResolution = 1;
            return;
        } else {
            if (((ConstraintWidgetContainer)object).mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && constraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
                constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
                int n2 = constraintWidget.mLeft.mMargin;
                int n3 = ((ConstraintWidget)object).getWidth() - constraintWidget.mRight.mMargin;
                linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, n2);
                linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, n3);
                constraintWidget.setHorizontalDimension(n2, n3);
                constraintWidget.mHorizontalResolution = 2;
                return;
            }
            if (constraintWidget.mLeft.mTarget != null && constraintWidget.mRight.mTarget != null) {
                if (constraintWidget.mLeft.mTarget.mOwner == object && constraintWidget.mRight.mTarget.mOwner == object) {
                    int n4 = constraintWidget.mLeft.getMargin();
                    int n5 = constraintWidget.mRight.getMargin();
                    if (((ConstraintWidgetContainer)object).mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        n5 = ((ConstraintWidget)object).getWidth() - n5;
                    } else {
                        int n6 = constraintWidget.getWidth();
                        n4 += (int)((float)(((ConstraintWidget)object).getWidth() - n4 - n5 - n6) * constraintWidget.mHorizontalBiasPercent + 0.5f);
                        n5 = n4 + constraintWidget.getWidth();
                    }
                    constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
                    constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
                    linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, n4);
                    linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, n5);
                    constraintWidget.mHorizontalResolution = 2;
                    constraintWidget.setHorizontalDimension(n4, n5);
                    return;
                }
                constraintWidget.mHorizontalResolution = 1;
                return;
            }
            if (constraintWidget.mLeft.mTarget != null && constraintWidget.mLeft.mTarget.mOwner == object) {
                int n7 = constraintWidget.mLeft.getMargin();
                int n8 = n7 + constraintWidget.getWidth();
                constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
                constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
                linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, n7);
                linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, n8);
                constraintWidget.mHorizontalResolution = 2;
                constraintWidget.setHorizontalDimension(n7, n8);
                return;
            }
            if (constraintWidget.mRight.mTarget != null && constraintWidget.mRight.mTarget.mOwner == object) {
                constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
                constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
                int n9 = ((ConstraintWidget)object).getWidth() - constraintWidget.mRight.getMargin();
                int n10 = n9 - constraintWidget.getWidth();
                linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, n10);
                linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, n9);
                constraintWidget.mHorizontalResolution = 2;
                constraintWidget.setHorizontalDimension(n10, n9);
                return;
            }
            if (constraintWidget.mLeft.mTarget != null && constraintWidget.mLeft.mTarget.mOwner.mHorizontalResolution == 2) {
                object = constraintWidget.mLeft.mTarget.mSolverVariable;
                constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
                constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
                int n11 = (int)(((SolverVariable)object).computedValue + (float)constraintWidget.mLeft.getMargin() + 0.5f);
                int n12 = n11 + constraintWidget.getWidth();
                linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, n11);
                linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, n12);
                constraintWidget.mHorizontalResolution = 2;
                constraintWidget.setHorizontalDimension(n11, n12);
                return;
            }
            if (constraintWidget.mRight.mTarget != null && constraintWidget.mRight.mTarget.mOwner.mHorizontalResolution == 2) {
                object = constraintWidget.mRight.mTarget.mSolverVariable;
                constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
                constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
                int n13 = (int)(((SolverVariable)object).computedValue - (float)constraintWidget.mRight.getMargin() + 0.5f);
                int n14 = n13 - constraintWidget.getWidth();
                linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, n14);
                linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, n13);
                constraintWidget.mHorizontalResolution = 2;
                constraintWidget.setHorizontalDimension(n14, n13);
                return;
            }
            n = constraintWidget.mLeft.mTarget != null ? 1 : 0;
            int n15 = constraintWidget.mRight.mTarget != null ? 1 : 0;
            if (n != 0 || n15 != 0) return;
            {
                if (!(constraintWidget instanceof Guideline)) {
                    constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
                    constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
                    n = constraintWidget.getX();
                    n15 = constraintWidget.getWidth();
                    linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, n);
                    linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, n + n15);
                    constraintWidget.mHorizontalResolution = 2;
                    return;
                }
                Guideline guideline = (Guideline)constraintWidget;
                if (guideline.getOrientation() != 1) return;
                {
                    constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
                    constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
                    f = guideline.getRelativeBegin() != -1 ? (float)guideline.getRelativeBegin() : (guideline.getRelativeEnd() != -1 ? (float)(((ConstraintWidget)object).getWidth() - guideline.getRelativeEnd()) : (float)((ConstraintWidget)object).getWidth() * guideline.getRelativePercent());
                }
            }
        }
        n = (int)(0.5f + f);
        linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, n);
        linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, n);
        constraintWidget.mHorizontalResolution = 2;
        constraintWidget.mVerticalResolution = 2;
        constraintWidget.setHorizontalDimension(n, n);
        constraintWidget.setVerticalDimension(0, ((ConstraintWidget)object).getHeight());
    }

    static void checkMatchParent(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ConstraintWidget constraintWidget) {
        int n;
        int n2;
        if (constraintWidgetContainer.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && constraintWidget.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
            constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
            n = constraintWidget.mLeft.mMargin;
            n2 = constraintWidgetContainer.getWidth() - constraintWidget.mRight.mMargin;
            linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, n);
            linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, n2);
            constraintWidget.setHorizontalDimension(n, n2);
            constraintWidget.mHorizontalResolution = 2;
        }
        if (constraintWidgetContainer.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && constraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
            constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
            n = constraintWidget.mTop.mMargin;
            n2 = constraintWidgetContainer.getHeight() - constraintWidget.mBottom.mMargin;
            linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n);
            linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n2);
            if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
                constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
                linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + n);
            }
            constraintWidget.setVerticalDimension(n, n2);
            constraintWidget.mVerticalResolution = 2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static void checkVerticalSimpleDependency(ConstraintWidgetContainer object, LinearSystem linearSystem, ConstraintWidget constraintWidget) {
        if (constraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            constraintWidget.mVerticalResolution = 1;
            return;
        }
        if (((ConstraintWidgetContainer)object).mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && constraintWidget.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
            constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
            int n = constraintWidget.mTop.mMargin;
            int n2 = ((ConstraintWidget)object).getHeight() - constraintWidget.mBottom.mMargin;
            linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n);
            linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n2);
            if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
                constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
                linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + n);
            }
            constraintWidget.setVerticalDimension(n, n2);
            constraintWidget.mVerticalResolution = 2;
            return;
        }
        if (constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget != null) {
            if (constraintWidget.mTop.mTarget.mOwner == object && constraintWidget.mBottom.mTarget.mOwner == object) {
                int n = constraintWidget.mTop.getMargin();
                int n3 = constraintWidget.mBottom.getMargin();
                if (((ConstraintWidgetContainer)object).mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    n3 = n + constraintWidget.getHeight();
                } else {
                    int n4 = constraintWidget.getHeight();
                    int n5 = ((ConstraintWidget)object).getHeight();
                    n = (int)((float)n + (float)(n5 - n - n3 - n4) * constraintWidget.mVerticalBiasPercent + 0.5f);
                    n3 = n + constraintWidget.getHeight();
                }
                constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
                constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
                linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n);
                linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n3);
                if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
                    constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
                    linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + n);
                }
                constraintWidget.mVerticalResolution = 2;
                constraintWidget.setVerticalDimension(n, n3);
                return;
            }
            constraintWidget.mVerticalResolution = 1;
            return;
        }
        if (constraintWidget.mTop.mTarget != null && constraintWidget.mTop.mTarget.mOwner == object) {
            int n = constraintWidget.mTop.getMargin();
            int n6 = n + constraintWidget.getHeight();
            constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
            constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
            linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n);
            linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n6);
            if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
                constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
                linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + n);
            }
            constraintWidget.mVerticalResolution = 2;
            constraintWidget.setVerticalDimension(n, n6);
            return;
        }
        if (constraintWidget.mBottom.mTarget != null && constraintWidget.mBottom.mTarget.mOwner == object) {
            constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
            constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
            int n = ((ConstraintWidget)object).getHeight() - constraintWidget.mBottom.getMargin();
            int n7 = n - constraintWidget.getHeight();
            linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n7);
            linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n);
            if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
                constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
                linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + n7);
            }
            constraintWidget.mVerticalResolution = 2;
            constraintWidget.setVerticalDimension(n7, n);
            return;
        }
        if (constraintWidget.mTop.mTarget != null && constraintWidget.mTop.mTarget.mOwner.mVerticalResolution == 2) {
            object = constraintWidget.mTop.mTarget.mSolverVariable;
            constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
            constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
            int n = (int)(((SolverVariable)object).computedValue + (float)constraintWidget.mTop.getMargin() + 0.5f);
            int n8 = n + constraintWidget.getHeight();
            linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n);
            linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n8);
            if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
                constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
                linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + n);
            }
            constraintWidget.mVerticalResolution = 2;
            constraintWidget.setVerticalDimension(n, n8);
            return;
        }
        if (constraintWidget.mBottom.mTarget != null && constraintWidget.mBottom.mTarget.mOwner.mVerticalResolution == 2) {
            object = constraintWidget.mBottom.mTarget.mSolverVariable;
            constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
            constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
            int n = (int)(((SolverVariable)object).computedValue - (float)constraintWidget.mBottom.getMargin() + 0.5f);
            int n9 = n - constraintWidget.getHeight();
            linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n9);
            linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n);
            if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
                constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
                linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + n9);
            }
            constraintWidget.mVerticalResolution = 2;
            constraintWidget.setVerticalDimension(n9, n);
            return;
        }
        if (constraintWidget.mBaseline.mTarget != null && constraintWidget.mBaseline.mTarget.mOwner.mVerticalResolution == 2) {
            object = constraintWidget.mBaseline.mTarget.mSolverVariable;
            constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
            constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
            int n = (int)(((SolverVariable)object).computedValue - (float)constraintWidget.mBaselineDistance + 0.5f);
            int n10 = n + constraintWidget.getHeight();
            linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n);
            linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n10);
            constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
            linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + n);
            constraintWidget.mVerticalResolution = 2;
            constraintWidget.setVerticalDimension(n, n10);
            return;
        }
        int n = constraintWidget.mBaseline.mTarget != null ? 1 : 0;
        int n11 = constraintWidget.mTop.mTarget != null ? 1 : 0;
        boolean bl = constraintWidget.mBottom.mTarget != null;
        if (n != 0) return;
        if (n11 != 0) return;
        if (bl) return;
        if (constraintWidget instanceof Guideline) {
            Guideline guideline = (Guideline)constraintWidget;
            if (guideline.getOrientation() != 0) return;
            constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
            constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
            float f = guideline.getRelativeBegin() != -1 ? (float)guideline.getRelativeBegin() : (guideline.getRelativeEnd() != -1 ? (float)(((ConstraintWidget)object).getHeight() - guideline.getRelativeEnd()) : (float)((ConstraintWidget)object).getHeight() * guideline.getRelativePercent());
            n = (int)(0.5f + f);
            linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n);
            linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n);
            constraintWidget.mVerticalResolution = 2;
            constraintWidget.mHorizontalResolution = 2;
            constraintWidget.setVerticalDimension(n, n);
            constraintWidget.setHorizontalDimension(0, ((ConstraintWidget)object).getWidth());
            return;
        }
        constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
        constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
        n = constraintWidget.getY();
        n11 = constraintWidget.getHeight();
        linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, n);
        linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, n + n11);
        if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
            constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
            linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + n);
        }
        constraintWidget.mVerticalResolution = 2;
    }
}

