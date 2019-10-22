/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Rectangle;
import java.util.ArrayList;

public class Guideline
extends ConstraintWidget {
    private ConstraintAnchor mAnchor = this.mTop;
    private Rectangle mHead = new Rectangle();
    private int mHeadSize = 8;
    private boolean mIsPositionRelaxed = false;
    private int mMinimumPosition = 0;
    private int mOrientation = 0;
    protected int mRelativeBegin = -1;
    protected int mRelativeEnd = -1;
    protected float mRelativePercent = -1.0f;

    public Guideline() {
        this.mAnchors.clear();
        this.mAnchors.add(this.mAnchor);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void addToSolver(LinearSystem linearSystem, int n) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        block7: {
            block6: {
                ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer)this.getParent();
                if (constraintWidgetContainer == null) break block6;
                constraintAnchor2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.LEFT);
                constraintAnchor = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.RIGHT);
                if (this.mOrientation == 0) {
                    constraintAnchor2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.TOP);
                    constraintAnchor = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BOTTOM);
                }
                if (this.mRelativeBegin != -1) {
                    linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, linearSystem.createObjectVariable(this.mAnchor), linearSystem.createObjectVariable(constraintAnchor2), this.mRelativeBegin, false));
                    return;
                }
                if (this.mRelativeEnd != -1) {
                    linearSystem.addConstraint(LinearSystem.createRowEquals(linearSystem, linearSystem.createObjectVariable(this.mAnchor), linearSystem.createObjectVariable(constraintAnchor), -this.mRelativeEnd, false));
                    return;
                }
                if (this.mRelativePercent != -1.0f) break block7;
            }
            return;
        }
        linearSystem.addConstraint(LinearSystem.createRowDimensionPercent(linearSystem, linearSystem.createObjectVariable(this.mAnchor), linearSystem.createObjectVariable(constraintAnchor2), linearSystem.createObjectVariable(constraintAnchor), this.mRelativePercent, this.mIsPositionRelaxed));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (1.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[type.ordinal()]) {
            case 1: 
            case 2: {
                if (this.mOrientation != 1) return null;
                return this.mAnchor;
            }
            default: {
                return null;
            }
            case 3: 
            case 4: {
                if (this.mOrientation != 0) return null;
                return this.mAnchor;
            }
        }
    }

    @Override
    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getRelativeBegin() {
        return this.mRelativeBegin;
    }

    public int getRelativeEnd() {
        return this.mRelativeEnd;
    }

    public float getRelativePercent() {
        return this.mRelativePercent;
    }

    public void setGuideBegin(int n) {
        if (n > -1) {
            this.mRelativePercent = -1.0f;
            this.mRelativeBegin = n;
            this.mRelativeEnd = -1;
        }
    }

    public void setGuideEnd(int n) {
        if (n > -1) {
            this.mRelativePercent = -1.0f;
            this.mRelativeBegin = -1;
            this.mRelativeEnd = n;
        }
    }

    public void setGuidePercent(float f) {
        if (f > -1.0f) {
            this.mRelativePercent = f;
            this.mRelativeBegin = -1;
            this.mRelativeEnd = -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setOrientation(int n) {
        if (this.mOrientation == n) {
            return;
        }
        this.mOrientation = n;
        this.mAnchors.clear();
        this.mAnchor = this.mOrientation == 1 ? this.mLeft : this.mTop;
        this.mAnchors.add(this.mAnchor);
    }

    @Override
    public void updateFromSolver(LinearSystem linearSystem, int n) {
        if (this.getParent() == null) {
            return;
        }
        n = linearSystem.getObjectVariableValue(this.mAnchor);
        if (this.mOrientation == 1) {
            this.setX(n);
            this.setY(0);
            this.setHeight(this.getParent().getHeight());
            this.setWidth(0);
            return;
        }
        this.setX(0);
        this.setY(n);
        this.setWidth(this.getParent().getWidth());
        this.setHeight(0);
    }

}

