/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;

public class ConstraintWidget {
    public static float DEFAULT_BIAS = 0.5f;
    protected ArrayList<ConstraintAnchor> mAnchors;
    ConstraintAnchor mBaseline;
    int mBaselineDistance = 0;
    ConstraintAnchor mBottom;
    boolean mBottomHasCentered;
    ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX;
    ConstraintAnchor mCenterY;
    private Object mCompanionWidget;
    private int mContainerItemSkip = 0;
    private String mDebugName = null;
    protected float mDimensionRatio = 0.0f;
    protected int mDimensionRatioSide = -1;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    private int mDrawHeight = 0;
    private int mDrawWidth = 0;
    private int mDrawX = 0;
    private int mDrawY = 0;
    int mHeight = 0;
    float mHorizontalBiasPercent;
    boolean mHorizontalChainFixedPosition;
    int mHorizontalChainStyle = 0;
    DimensionBehaviour mHorizontalDimensionBehaviour;
    ConstraintWidget mHorizontalNextWidget = null;
    public int mHorizontalResolution = -1;
    float mHorizontalWeight = 0.0f;
    boolean mHorizontalWrapVisited;
    ConstraintAnchor mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
    boolean mLeftHasCentered;
    int mMatchConstraintDefaultHeight = 0;
    int mMatchConstraintDefaultWidth = 0;
    int mMatchConstraintMaxHeight = 0;
    int mMatchConstraintMaxWidth = 0;
    int mMatchConstraintMinHeight = 0;
    int mMatchConstraintMinWidth = 0;
    protected int mMinHeight;
    protected int mMinWidth;
    protected int mOffsetX = 0;
    protected int mOffsetY = 0;
    ConstraintWidget mParent = null;
    ConstraintAnchor mRight;
    boolean mRightHasCentered;
    private int mSolverBottom = 0;
    private int mSolverLeft = 0;
    private int mSolverRight = 0;
    private int mSolverTop = 0;
    ConstraintAnchor mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
    boolean mTopHasCentered;
    private String mType = null;
    float mVerticalBiasPercent;
    boolean mVerticalChainFixedPosition;
    int mVerticalChainStyle = 0;
    DimensionBehaviour mVerticalDimensionBehaviour;
    ConstraintWidget mVerticalNextWidget = null;
    public int mVerticalResolution = -1;
    float mVerticalWeight = 0.0f;
    boolean mVerticalWrapVisited;
    private int mVisibility = 0;
    int mWidth = 0;
    private int mWrapHeight;
    private int mWrapWidth;
    protected int mX = 0;
    protected int mY = 0;

    public ConstraintWidget() {
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mAnchors = new ArrayList();
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.addAnchors();
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mBaseline);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void applyConstraints(LinearSystem linearSystem, boolean bl, boolean bl2, ConstraintAnchor object, ConstraintAnchor object2, int n, int n2, int n3, int n4, float f, boolean bl3, boolean bl4, int n5, int n6, int n7) {
        SolverVariable solverVariable = linearSystem.createObjectVariable(object);
        SolverVariable solverVariable2 = linearSystem.createObjectVariable(object2);
        SolverVariable solverVariable3 = linearSystem.createObjectVariable(((ConstraintAnchor)object).getTarget());
        SolverVariable solverVariable4 = linearSystem.createObjectVariable(((ConstraintAnchor)object2).getTarget());
        int n8 = ((ConstraintAnchor)object).getMargin();
        int n9 = ((ConstraintAnchor)object2).getMargin();
        if (this.mVisibility == 8) {
            n3 = 0;
            bl2 = true;
        }
        if (solverVariable3 == null && solverVariable4 == null) {
            linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable, n));
            if (bl3) return;
            if (bl) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, solverVariable2, solverVariable, n4, true));
                return;
            }
            if (bl2) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, solverVariable2, solverVariable, n3, false));
                return;
            }
            linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable2, n2));
            return;
        }
        if (solverVariable3 != null && solverVariable4 == null) {
            linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable, solverVariable3, n8));
            if (bl) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, solverVariable2, solverVariable, n4, true));
                return;
            }
            if (bl3) return;
            {
                if (bl2) {
                    linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable2, solverVariable, n3));
                    return;
                }
                linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable2, n2));
                return;
            }
        }
        if (solverVariable3 == null && solverVariable4 != null) {
            linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable2, solverVariable4, n9 * -1));
            if (bl) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, solverVariable2, solverVariable, n4, true));
                return;
            }
            if (bl3) return;
            {
                if (bl2) {
                    linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable2, solverVariable, n3));
                    return;
                }
                linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable, n));
                return;
            }
        }
        if (bl2) {
            if (bl) {
                linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, solverVariable2, solverVariable, n4, true));
            } else {
                linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable2, solverVariable, n3));
            }
            if (((ConstraintAnchor)object).getStrength() != ((ConstraintAnchor)object2).getStrength()) {
                if (((ConstraintAnchor)object).getStrength() == ConstraintAnchor.Strength.STRONG) {
                    linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable, solverVariable3, n8));
                    object = linearSystem.createSlackVariable();
                    object2 = linearSystem.createRow();
                    ((ArrayRow)object2).createRowLowerThan(solverVariable2, solverVariable4, (SolverVariable)object, n9 * -1);
                    linearSystem.addConstraint((ArrayRow)object2);
                    return;
                }
                object = linearSystem.createSlackVariable();
                object2 = linearSystem.createRow();
                ((ArrayRow)object2).createRowGreaterThan(solverVariable, solverVariable3, (SolverVariable)object, n8);
                linearSystem.addConstraint((ArrayRow)object2);
                linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable2, solverVariable4, n9 * -1));
                return;
            }
            if (solverVariable3 == solverVariable4) {
                linearSystem.addConstraint(LinearSystem.createRowCentering(linearSystem, solverVariable, solverVariable3, 0, 0.5f, solverVariable4, solverVariable2, 0, true));
                return;
            }
            if (bl4) return;
            {
                bl = ((ConstraintAnchor)object).getConnectionType() != ConstraintAnchor.ConnectionType.STRICT;
                linearSystem.addConstraint(LinearSystem.createRowGreaterThan(linearSystem, solverVariable, solverVariable3, n8, bl));
                bl = ((ConstraintAnchor)object2).getConnectionType() != ConstraintAnchor.ConnectionType.STRICT;
                linearSystem.addConstraint(LinearSystem.createRowLowerThan(linearSystem, solverVariable2, solverVariable4, n9 * -1, bl));
                linearSystem.addConstraint(LinearSystem.createRowCentering(linearSystem, solverVariable, solverVariable3, n8, f, solverVariable4, solverVariable2, n9, false));
                return;
            }
        }
        if (bl3) {
            linearSystem.addGreaterThan(solverVariable, solverVariable3, n8, 3);
            linearSystem.addLowerThan(solverVariable2, solverVariable4, n9 * -1, 3);
            linearSystem.addConstraint(LinearSystem.createRowCentering(linearSystem, solverVariable, solverVariable3, n8, f, solverVariable4, solverVariable2, n9, true));
            return;
        }
        if (bl4) return;
        if (n5 == 1) {
            n = n3;
            if (n6 > n3) {
                n = n6;
            }
            n2 = n;
            if (n7 > 0) {
                if (n7 < n) {
                    n2 = n7;
                } else {
                    linearSystem.addLowerThan(solverVariable2, solverVariable, n7, 3);
                    n2 = n;
                }
            }
            linearSystem.addEquality(solverVariable2, solverVariable, n2, 3);
            linearSystem.addGreaterThan(solverVariable, solverVariable3, n8, 2);
            linearSystem.addLowerThan(solverVariable2, solverVariable4, -n9, 2);
            linearSystem.addCentering(solverVariable, solverVariable3, n8, f, solverVariable4, solverVariable2, n9, 4);
            return;
        }
        if (n6 == 0 && n7 == 0) {
            linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable, solverVariable3, n8));
            linearSystem.addConstraint(linearSystem.createRow().createRowEquals(solverVariable2, solverVariable4, n9 * -1));
            return;
        }
        if (n7 > 0) {
            linearSystem.addLowerThan(solverVariable2, solverVariable, n7, 3);
        }
        linearSystem.addGreaterThan(solverVariable, solverVariable3, n8, 2);
        linearSystem.addLowerThan(solverVariable2, solverVariable4, -n9, 2);
        linearSystem.addCentering(solverVariable, solverVariable3, n8, f, solverVariable4, solverVariable2, n9, 4);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addToSolver(LinearSystem linearSystem, int n) {
        int n2;
        SolverVariable solverVariable;
        SolverVariable solverVariable2;
        float f;
        Object object;
        SolverVariable solverVariable3;
        SolverVariable solverVariable4;
        block97: {
            block96: {
                Object object2;
                boolean bl;
                int n3;
                SolverVariable solverVariable5;
                boolean bl2;
                boolean bl3;
                boolean bl4;
                Object object3;
                boolean bl5;
                int n4;
                block93: {
                    block95: {
                        block94: {
                            block91: {
                                block92: {
                                    block86: {
                                        block90: {
                                            block89: {
                                                block88: {
                                                    block87: {
                                                        solverVariable3 = null;
                                                        solverVariable2 = null;
                                                        solverVariable4 = null;
                                                        solverVariable = null;
                                                        object = null;
                                                        if (n == Integer.MAX_VALUE || this.mLeft.mGroup == n) {
                                                            solverVariable3 = linearSystem.createObjectVariable(this.mLeft);
                                                        }
                                                        if (n == Integer.MAX_VALUE || this.mRight.mGroup == n) {
                                                            solverVariable2 = linearSystem.createObjectVariable(this.mRight);
                                                        }
                                                        if (n == Integer.MAX_VALUE || this.mTop.mGroup == n) {
                                                            solverVariable4 = linearSystem.createObjectVariable(this.mTop);
                                                        }
                                                        if (n == Integer.MAX_VALUE || this.mBottom.mGroup == n) {
                                                            solverVariable = linearSystem.createObjectVariable(this.mBottom);
                                                        }
                                                        if (n == Integer.MAX_VALUE || this.mBaseline.mGroup == n) {
                                                            object = linearSystem.createObjectVariable(this.mBaseline);
                                                        }
                                                        bl4 = false;
                                                        bl3 = false;
                                                        bl2 = false;
                                                        bl = false;
                                                        if (this.mParent == null) break block86;
                                                        if (this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft) break block87;
                                                        bl5 = bl3;
                                                        if (this.mRight.mTarget == null) break block88;
                                                        bl5 = bl3;
                                                        if (this.mRight.mTarget.mTarget != this.mRight) break block88;
                                                    }
                                                    ((ConstraintWidgetContainer)this.mParent).addChain(this, 0);
                                                    bl5 = true;
                                                }
                                                if (this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) break block89;
                                                bl3 = bl;
                                                if (this.mBottom.mTarget == null) break block90;
                                                bl3 = bl;
                                                if (this.mBottom.mTarget.mTarget != this.mBottom) break block90;
                                            }
                                            ((ConstraintWidgetContainer)this.mParent).addChain(this, 1);
                                            bl3 = true;
                                        }
                                        if (this.mParent.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT && !bl5) {
                                            if (this.mLeft.mTarget == null || this.mLeft.mTarget.mOwner != this.mParent) {
                                                object2 = linearSystem.createObjectVariable(this.mParent.mLeft);
                                                object3 = linearSystem.createRow();
                                                ((ArrayRow)object3).createRowGreaterThan(solverVariable3, (SolverVariable)object2, linearSystem.createSlackVariable(), 0);
                                                linearSystem.addConstraint((ArrayRow)object3);
                                            } else if (this.mLeft.mTarget != null && this.mLeft.mTarget.mOwner == this.mParent) {
                                                this.mLeft.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                                            }
                                            if (this.mRight.mTarget == null || this.mRight.mTarget.mOwner != this.mParent) {
                                                object2 = linearSystem.createObjectVariable(this.mParent.mRight);
                                                object3 = linearSystem.createRow();
                                                ((ArrayRow)object3).createRowGreaterThan((SolverVariable)object2, solverVariable2, linearSystem.createSlackVariable(), 0);
                                                linearSystem.addConstraint((ArrayRow)object3);
                                            } else if (this.mRight.mTarget != null && this.mRight.mTarget.mOwner == this.mParent) {
                                                this.mRight.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                                            }
                                        }
                                        bl4 = bl5;
                                        bl2 = bl3;
                                        if (this.mParent.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT) {
                                            bl4 = bl5;
                                            bl2 = bl3;
                                            if (!bl3) {
                                                if (this.mTop.mTarget == null || this.mTop.mTarget.mOwner != this.mParent) {
                                                    object2 = linearSystem.createObjectVariable(this.mParent.mTop);
                                                    object3 = linearSystem.createRow();
                                                    ((ArrayRow)object3).createRowGreaterThan(solverVariable4, (SolverVariable)object2, linearSystem.createSlackVariable(), 0);
                                                    linearSystem.addConstraint((ArrayRow)object3);
                                                } else if (this.mTop.mTarget != null && this.mTop.mTarget.mOwner == this.mParent) {
                                                    this.mTop.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                                                }
                                                if (this.mBottom.mTarget == null || this.mBottom.mTarget.mOwner != this.mParent) {
                                                    object2 = linearSystem.createObjectVariable(this.mParent.mBottom);
                                                    object3 = linearSystem.createRow();
                                                    ((ArrayRow)object3).createRowGreaterThan((SolverVariable)object2, solverVariable, linearSystem.createSlackVariable(), 0);
                                                    linearSystem.addConstraint((ArrayRow)object3);
                                                    bl2 = bl3;
                                                    bl4 = bl5;
                                                } else {
                                                    bl4 = bl5;
                                                    bl2 = bl3;
                                                    if (this.mBottom.mTarget != null) {
                                                        bl4 = bl5;
                                                        bl2 = bl3;
                                                        if (this.mBottom.mTarget.mOwner == this.mParent) {
                                                            this.mBottom.setConnectionType(ConstraintAnchor.ConnectionType.STRICT);
                                                            bl4 = bl5;
                                                            bl2 = bl3;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    n4 = n2 = this.mWidth;
                                    if (n2 < this.mMinWidth) {
                                        n4 = this.mMinWidth;
                                    }
                                    n3 = n2 = this.mHeight;
                                    if (n2 < this.mMinHeight) {
                                        n3 = this.mMinHeight;
                                    }
                                    bl3 = this.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT;
                                    bl = this.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT;
                                    bl5 = bl3;
                                    if (bl3) break block91;
                                    bl5 = bl3;
                                    if (this.mLeft == null) break block91;
                                    bl5 = bl3;
                                    if (this.mRight == null) break block91;
                                    if (this.mLeft.mTarget == null) break block92;
                                    bl5 = bl3;
                                    if (this.mRight.mTarget != null) break block91;
                                }
                                bl5 = true;
                            }
                            bl3 = bl;
                            if (bl) break block93;
                            bl3 = bl;
                            if (this.mTop == null) break block93;
                            bl3 = bl;
                            if (this.mBottom == null) break block93;
                            if (this.mTop.mTarget == null) break block94;
                            bl3 = bl;
                            if (this.mBottom.mTarget != null) break block93;
                        }
                        if (this.mBaselineDistance == 0) break block95;
                        bl3 = bl;
                        if (this.mBaseline == null) break block93;
                        if (this.mTop.mTarget == null) break block95;
                        bl3 = bl;
                        if (this.mBaseline.mTarget != null) break block93;
                    }
                    bl3 = true;
                }
                boolean bl6 = false;
                int n5 = this.mDimensionRatioSide;
                float f2 = this.mDimensionRatio;
                boolean bl7 = bl5;
                int n6 = n4;
                bl = bl3;
                int n7 = n3;
                f = f2;
                n2 = n5;
                boolean bl8 = bl6;
                if (this.mDimensionRatio > 0.0f) {
                    bl7 = bl5;
                    n6 = n4;
                    bl = bl3;
                    n7 = n3;
                    f = f2;
                    n2 = n5;
                    bl8 = bl6;
                    if (this.mVisibility != 8) {
                        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                            bl6 = true;
                            if (bl5 && !bl3) {
                                n2 = 0;
                                bl8 = bl6;
                                f = f2;
                                n7 = n3;
                                bl = bl3;
                                n6 = n4;
                                bl7 = bl5;
                            } else {
                                bl7 = bl5;
                                n6 = n4;
                                bl = bl3;
                                n7 = n3;
                                f = f2;
                                n2 = n5;
                                bl8 = bl6;
                                if (!bl5) {
                                    bl7 = bl5;
                                    n6 = n4;
                                    bl = bl3;
                                    n7 = n3;
                                    f = f2;
                                    n2 = n5;
                                    bl8 = bl6;
                                    if (bl3) {
                                        n5 = 1;
                                        bl7 = bl5;
                                        n6 = n4;
                                        bl = bl3;
                                        n7 = n3;
                                        f = f2;
                                        n2 = n5;
                                        bl8 = bl6;
                                        if (this.mDimensionRatioSide == -1) {
                                            f = 1.0f / f2;
                                            bl7 = bl5;
                                            n6 = n4;
                                            bl = bl3;
                                            n7 = n3;
                                            n2 = n5;
                                            bl8 = bl6;
                                        }
                                    }
                                }
                            }
                        } else if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                            n2 = 0;
                            n6 = (int)((float)this.mHeight * f2);
                            bl7 = true;
                            bl = bl3;
                            n7 = n3;
                            f = f2;
                            bl8 = bl6;
                        } else {
                            bl7 = bl5;
                            n6 = n4;
                            bl = bl3;
                            n7 = n3;
                            f = f2;
                            n2 = n5;
                            bl8 = bl6;
                            if (this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                                n2 = 1;
                                f = f2;
                                if (this.mDimensionRatioSide == -1) {
                                    f = 1.0f / f2;
                                }
                                n7 = (int)((float)this.mWidth * f);
                                bl = true;
                                bl7 = bl5;
                                n6 = n4;
                                bl8 = bl6;
                            }
                        }
                    }
                }
                bl5 = bl8 && (n2 == 0 || n2 == -1);
                bl3 = this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT && this instanceof ConstraintWidgetContainer;
                if (this.mHorizontalResolution != 2 && (n == Integer.MAX_VALUE || this.mLeft.mGroup == n && this.mRight.mGroup == n)) {
                    if (bl5 && this.mLeft.mTarget != null && this.mRight.mTarget != null) {
                        object2 = linearSystem.createObjectVariable(this.mLeft);
                        object3 = linearSystem.createObjectVariable(this.mRight);
                        solverVariable5 = linearSystem.createObjectVariable(this.mLeft.getTarget());
                        SolverVariable solverVariable6 = linearSystem.createObjectVariable(this.mRight.getTarget());
                        linearSystem.addGreaterThan((SolverVariable)object2, solverVariable5, this.mLeft.getMargin(), 3);
                        linearSystem.addLowerThan((SolverVariable)object3, solverVariable6, this.mRight.getMargin() * -1, 3);
                        if (!bl4) {
                            linearSystem.addCentering((SolverVariable)object2, solverVariable5, this.mLeft.getMargin(), this.mHorizontalBiasPercent, solverVariable6, (SolverVariable)object3, this.mRight.getMargin(), 4);
                        }
                    } else {
                        this.applyConstraints(linearSystem, bl3, bl7, this.mLeft, this.mRight, this.mX, this.mX + n6, n6, this.mMinWidth, this.mHorizontalBiasPercent, bl5, bl4, this.mMatchConstraintDefaultWidth, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth);
                    }
                }
                if (this.mVerticalResolution == 2) break block96;
                bl5 = this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT && this instanceof ConstraintWidgetContainer;
                bl3 = bl8 && (n2 == 1 || n2 == -1);
                if (this.mBaselineDistance > 0) {
                    object2 = this.mBottom;
                    if (n == Integer.MAX_VALUE || this.mBottom.mGroup == n && this.mBaseline.mGroup == n) {
                        linearSystem.addEquality((SolverVariable)object, solverVariable4, this.getBaselineDistance(), 5);
                    }
                    object = object2;
                    n4 = n7;
                    if (this.mBaseline.mTarget != null) {
                        n4 = this.mBaselineDistance;
                        object = this.mBaseline;
                    }
                    if (n == Integer.MAX_VALUE || this.mTop.mGroup == n && ((ConstraintAnchor)object).mGroup == n) {
                        if (bl3 && this.mTop.mTarget != null && this.mBottom.mTarget != null) {
                            object = linearSystem.createObjectVariable(this.mTop);
                            object2 = linearSystem.createObjectVariable(this.mBottom);
                            object3 = linearSystem.createObjectVariable(this.mTop.getTarget());
                            solverVariable5 = linearSystem.createObjectVariable(this.mBottom.getTarget());
                            linearSystem.addGreaterThan((SolverVariable)object, (SolverVariable)object3, this.mTop.getMargin(), 3);
                            linearSystem.addLowerThan((SolverVariable)object2, solverVariable5, this.mBottom.getMargin() * -1, 3);
                            if (!bl2) {
                                linearSystem.addCentering((SolverVariable)object, (SolverVariable)object3, this.mTop.getMargin(), this.mVerticalBiasPercent, solverVariable5, (SolverVariable)object2, this.mBottom.getMargin(), 4);
                            }
                        } else {
                            this.applyConstraints(linearSystem, bl5, bl, this.mTop, (ConstraintAnchor)object, this.mY, this.mY + n4, n4, this.mMinHeight, this.mVerticalBiasPercent, bl3, bl2, this.mMatchConstraintDefaultHeight, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight);
                            linearSystem.addEquality(solverVariable, solverVariable4, n7, 5);
                        }
                    }
                } else if (n == Integer.MAX_VALUE || this.mTop.mGroup == n && this.mBottom.mGroup == n) {
                    if (bl3 && this.mTop.mTarget != null && this.mBottom.mTarget != null) {
                        object = linearSystem.createObjectVariable(this.mTop);
                        object2 = linearSystem.createObjectVariable(this.mBottom);
                        object3 = linearSystem.createObjectVariable(this.mTop.getTarget());
                        solverVariable5 = linearSystem.createObjectVariable(this.mBottom.getTarget());
                        linearSystem.addGreaterThan((SolverVariable)object, (SolverVariable)object3, this.mTop.getMargin(), 3);
                        linearSystem.addLowerThan((SolverVariable)object2, solverVariable5, this.mBottom.getMargin() * -1, 3);
                        if (!bl2) {
                            linearSystem.addCentering((SolverVariable)object, (SolverVariable)object3, this.mTop.getMargin(), this.mVerticalBiasPercent, solverVariable5, (SolverVariable)object2, this.mBottom.getMargin(), 4);
                        }
                    } else {
                        this.applyConstraints(linearSystem, bl5, bl, this.mTop, this.mBottom, this.mY, this.mY + n7, n7, this.mMinHeight, this.mVerticalBiasPercent, bl3, bl2, this.mMatchConstraintDefaultHeight, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight);
                    }
                }
                if (!bl8) break block96;
                object = linearSystem.createRow();
                if (n == Integer.MAX_VALUE || this.mLeft.mGroup == n && this.mRight.mGroup == n) break block97;
            }
            return;
        }
        if (n2 == 0) {
            linearSystem.addConstraint(((ArrayRow)object).createRowDimensionRatio(solverVariable2, solverVariable3, solverVariable, solverVariable4, f));
            return;
        }
        if (n2 == 1) {
            linearSystem.addConstraint(((ArrayRow)object).createRowDimensionRatio(solverVariable, solverVariable4, solverVariable2, solverVariable3, f));
            return;
        }
        if (this.mMatchConstraintMinWidth > 0) {
            linearSystem.addGreaterThan(solverVariable2, solverVariable3, this.mMatchConstraintMinWidth, 3);
        }
        if (this.mMatchConstraintMinHeight > 0) {
            linearSystem.addGreaterThan(solverVariable, solverVariable4, this.mMatchConstraintMinHeight, 3);
        }
        ((ArrayRow)object).createRowDimensionRatio(solverVariable2, solverVariable3, solverVariable, solverVariable4, f);
        solverVariable3 = linearSystem.createErrorVariable();
        solverVariable2 = linearSystem.createErrorVariable();
        solverVariable3.strength = 4;
        solverVariable2.strength = 4;
        ((ArrayRow)object).addError(solverVariable3, solverVariable2);
        linearSystem.addConstraint((ArrayRow)object);
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[type.ordinal()]) {
            default: {
                return null;
            }
            case 1: {
                return this.mLeft;
            }
            case 2: {
                return this.mTop;
            }
            case 3: {
                return this.mRight;
            }
            case 4: {
                return this.mBottom;
            }
            case 5: {
                return this.mBaseline;
            }
            case 6: {
                return this.mCenterX;
            }
            case 7: {
                return this.mCenterY;
            }
            case 8: 
        }
        return this.mCenter;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public int getBottom() {
        return this.getY() + this.mHeight;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public int getDrawBottom() {
        return this.getDrawY() + this.mDrawHeight;
    }

    public int getDrawRight() {
        return this.getDrawX() + this.mDrawWidth;
    }

    public int getDrawX() {
        return this.mDrawX + this.mOffsetX;
    }

    public int getDrawY() {
        return this.mDrawY + this.mOffsetY;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mHorizontalDimensionBehaviour;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getOptimizerWrapHeight() {
        int n;
        int n2 = n = this.mHeight;
        if (this.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) return n2;
        if (this.mMatchConstraintDefaultHeight == 1) {
            n = Math.max(this.mMatchConstraintMinHeight, n);
        } else if (this.mMatchConstraintMinHeight > 0) {
            this.mHeight = n = this.mMatchConstraintMinHeight;
        } else {
            n = 0;
        }
        n2 = n;
        if (this.mMatchConstraintMaxHeight <= 0) return n2;
        n2 = n;
        if (this.mMatchConstraintMaxHeight >= n) return n2;
        return this.mMatchConstraintMaxHeight;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int getOptimizerWrapWidth() {
        int n;
        int n2 = n = this.mWidth;
        if (this.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) return n2;
        if (this.mMatchConstraintDefaultWidth == 1) {
            n = Math.max(this.mMatchConstraintMinWidth, n);
        } else if (this.mMatchConstraintMinWidth > 0) {
            this.mWidth = n = this.mMatchConstraintMinWidth;
        } else {
            n = 0;
        }
        n2 = n;
        if (this.mMatchConstraintMaxWidth <= 0) return n2;
        n2 = n;
        if (this.mMatchConstraintMaxWidth >= n) return n2;
        return this.mMatchConstraintMaxWidth;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public int getRight() {
        return this.getX() + this.mWidth;
    }

    protected int getRootX() {
        return this.mX + this.mOffsetX;
    }

    protected int getRootY() {
        return this.mY + this.mOffsetY;
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mVerticalDimensionBehaviour;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getWrapHeight() {
        return this.mWrapHeight;
    }

    public int getWrapWidth() {
        return this.mWrapWidth;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public boolean hasBaseline() {
        return this.mBaselineDistance > 0;
    }

    public void immediateConnect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int n, int n2) {
        this.getAnchor(type).connect(constraintWidget.getAnchor(type2), n, n2, ConstraintAnchor.Strength.STRONG, 0, true);
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mDrawX = 0;
        this.mDrawY = 0;
        this.mDrawWidth = 0;
        this.mDrawHeight = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mWrapWidth = 0;
        this.mWrapHeight = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        this.mHorizontalWeight = 0.0f;
        this.mVerticalWeight = 0.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void resetAnchors() {
        ConstraintWidget constraintWidget = this.getParent();
        if (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer)this.getParent()).handlesInternalConstraints()) {
            int n = this.mAnchors.size();
            for (int i = 0; i < n; ++i) {
                this.mAnchors.get(i).reset();
            }
        }
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    public void setBaselineDistance(int n) {
        this.mBaselineDistance = n;
    }

    public void setCompanionWidget(Object object) {
        this.mCompanionWidget = object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setDimensionRatio(String string2) {
        int n;
        float f;
        block16: {
            int n2;
            float f2;
            block15: {
                String string3;
                int n3;
                if (string2 == null || string2.length() == 0) {
                    this.mDimensionRatio = 0.0f;
                    return;
                }
                n = -1;
                f2 = 0.0f;
                int n4 = string2.length();
                n2 = string2.indexOf(44);
                if (n2 > 0 && n2 < n4 - 1) {
                    string3 = string2.substring(0, n2);
                    if (string3.equalsIgnoreCase("W")) {
                        n = 0;
                    } else if (string3.equalsIgnoreCase("H")) {
                        n = 1;
                    }
                    ++n2;
                } else {
                    n2 = 0;
                }
                if ((n3 = string2.indexOf(58)) < 0 || n3 >= n4 - 1) break block15;
                string3 = string2.substring(n2, n3);
                string2 = string2.substring(n3 + 1);
                f = f2;
                if (string3.length() <= 0) break block16;
                f = f2;
                if (string2.length() <= 0) break block16;
                float f3 = Float.parseFloat(string3);
                float f4 = Float.parseFloat(string2);
                f = f2;
                if (!(f3 > 0.0f)) break block16;
                f = f2;
                if (!(f4 > 0.0f)) break block16;
                if (n == 1) {
                    f = Math.abs(f4 / f3);
                }
                f = f3 / f4;
                try {
                    f = Math.abs(f);
                }
                catch (NumberFormatException numberFormatException) {
                    f = f2;
                }
            }
            string2 = string2.substring(n2);
            f = f2;
            if (string2.length() > 0) {
                try {
                    f = Float.parseFloat(string2);
                }
                catch (NumberFormatException numberFormatException) {
                    f = f2;
                }
            }
        }
        if (!(f > 0.0f)) return;
        {
            this.mDimensionRatio = f;
            this.mDimensionRatioSide = n;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setFrame(int n, int n2, int n3, int n4) {
        int n5 = n3 - n;
        n3 = n4 - n2;
        this.mX = n;
        this.mY = n2;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        } else {
            n = n5;
            if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.FIXED) {
                n = n5;
                if (n5 < this.mWidth) {
                    n = this.mWidth;
                }
            }
            n2 = n3;
            if (this.mVerticalDimensionBehaviour == DimensionBehaviour.FIXED) {
                n2 = n3;
                if (n3 < this.mHeight) {
                    n2 = this.mHeight;
                }
            }
            this.mWidth = n;
            this.mHeight = n2;
            if (this.mHeight < this.mMinHeight) {
                this.mHeight = this.mMinHeight;
            }
            if (this.mWidth >= this.mMinWidth) return;
            {
                this.mWidth = this.mMinWidth;
                return;
            }
        }
    }

    public void setHeight(int n) {
        this.mHeight = n;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setHorizontalBiasPercent(float f) {
        this.mHorizontalBiasPercent = f;
    }

    public void setHorizontalChainStyle(int n) {
        this.mHorizontalChainStyle = n;
    }

    public void setHorizontalDimension(int n, int n2) {
        this.mX = n;
        this.mWidth = n2 - n;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mHorizontalDimensionBehaviour = dimensionBehaviour;
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            this.setWidth(this.mWrapWidth);
        }
    }

    public void setHorizontalMatchStyle(int n, int n2, int n3) {
        this.mMatchConstraintDefaultWidth = n;
        this.mMatchConstraintMinWidth = n2;
        this.mMatchConstraintMaxWidth = n3;
    }

    public void setHorizontalWeight(float f) {
        this.mHorizontalWeight = f;
    }

    public void setMinHeight(int n) {
        if (n < 0) {
            this.mMinHeight = 0;
            return;
        }
        this.mMinHeight = n;
    }

    public void setMinWidth(int n) {
        if (n < 0) {
            this.mMinWidth = 0;
            return;
        }
        this.mMinWidth = n;
    }

    public void setOffset(int n, int n2) {
        this.mOffsetX = n;
        this.mOffsetY = n2;
    }

    public void setOrigin(int n, int n2) {
        this.mX = n;
        this.mY = n2;
    }

    public void setParent(ConstraintWidget constraintWidget) {
        this.mParent = constraintWidget;
    }

    public void setVerticalBiasPercent(float f) {
        this.mVerticalBiasPercent = f;
    }

    public void setVerticalChainStyle(int n) {
        this.mVerticalChainStyle = n;
    }

    public void setVerticalDimension(int n, int n2) {
        this.mY = n;
        this.mHeight = n2 - n;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mVerticalDimensionBehaviour = dimensionBehaviour;
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            this.setHeight(this.mWrapHeight);
        }
    }

    public void setVerticalMatchStyle(int n, int n2, int n3) {
        this.mMatchConstraintDefaultHeight = n;
        this.mMatchConstraintMinHeight = n2;
        this.mMatchConstraintMaxHeight = n3;
    }

    public void setVerticalWeight(float f) {
        this.mVerticalWeight = f;
    }

    public void setVisibility(int n) {
        this.mVisibility = n;
    }

    public void setWidth(int n) {
        this.mWidth = n;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setWrapHeight(int n) {
        this.mWrapHeight = n;
    }

    public void setWrapWidth(int n) {
        this.mWrapWidth = n;
    }

    public void setX(int n) {
        this.mX = n;
    }

    public void setY(int n) {
        this.mY = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = this.mType != null ? "type: " + this.mType + " " : "";
        stringBuilder = stringBuilder.append(string2);
        if (this.mDebugName != null) {
            string2 = "id: " + this.mDebugName + " ";
            return stringBuilder.append(string2).append("(").append(this.mX).append(", ").append(this.mY).append(") - (").append(this.mWidth).append(" x ").append(this.mHeight).append(")").append(" wrap: (").append(this.mWrapWidth).append(" x ").append(this.mWrapHeight).append(")").toString();
        }
        string2 = "";
        return stringBuilder.append(string2).append("(").append(this.mX).append(", ").append(this.mY).append(") - (").append(this.mWidth).append(" x ").append(this.mHeight).append(")").append(" wrap: (").append(this.mWrapWidth).append(" x ").append(this.mWrapHeight).append(")").toString();
    }

    public void updateDrawPosition() {
        int n = this.mX;
        int n2 = this.mY;
        int n3 = this.mX;
        int n4 = this.mWidth;
        int n5 = this.mY;
        int n6 = this.mHeight;
        this.mDrawX = n;
        this.mDrawY = n2;
        this.mDrawWidth = n3 + n4 - n;
        this.mDrawHeight = n5 + n6 - n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void updateFromSolver(LinearSystem linearSystem, int n) {
        if (n == Integer.MAX_VALUE) {
            this.setFrame(linearSystem.getObjectVariableValue(this.mLeft), linearSystem.getObjectVariableValue(this.mTop), linearSystem.getObjectVariableValue(this.mRight), linearSystem.getObjectVariableValue(this.mBottom));
            return;
        } else {
            if (n == -2) {
                this.setFrame(this.mSolverLeft, this.mSolverTop, this.mSolverRight, this.mSolverBottom);
                return;
            }
            if (this.mLeft.mGroup == n) {
                this.mSolverLeft = linearSystem.getObjectVariableValue(this.mLeft);
            }
            if (this.mTop.mGroup == n) {
                this.mSolverTop = linearSystem.getObjectVariableValue(this.mTop);
            }
            if (this.mRight.mGroup == n) {
                this.mSolverRight = linearSystem.getObjectVariableValue(this.mRight);
            }
            if (this.mBottom.mGroup != n) return;
            {
                this.mSolverBottom = linearSystem.getObjectVariableValue(this.mBottom);
                return;
            }
        }
    }

    public static enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT;

    }

}

