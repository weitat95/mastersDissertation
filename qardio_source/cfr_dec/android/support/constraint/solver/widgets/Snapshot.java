/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver.widgets;

import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

public class Snapshot {
    private ArrayList<Connection> mConnections = new ArrayList();
    private int mHeight;
    private int mWidth;
    private int mX;
    private int mY;

    public Snapshot(ConstraintWidget object) {
        this.mX = ((ConstraintWidget)object).getX();
        this.mY = ((ConstraintWidget)object).getY();
        this.mWidth = ((ConstraintWidget)object).getWidth();
        this.mHeight = ((ConstraintWidget)object).getHeight();
        object = ((ConstraintWidget)object).getAnchors();
        int n = ((ArrayList)object).size();
        for (int i = 0; i < n; ++i) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor)((ArrayList)object).get(i);
            this.mConnections.add(new Connection(constraintAnchor));
        }
    }

    public void applyTo(ConstraintWidget constraintWidget) {
        constraintWidget.setX(this.mX);
        constraintWidget.setY(this.mY);
        constraintWidget.setWidth(this.mWidth);
        constraintWidget.setHeight(this.mHeight);
        int n = this.mConnections.size();
        for (int i = 0; i < n; ++i) {
            this.mConnections.get(i).applyTo(constraintWidget);
        }
    }

    public void updateFrom(ConstraintWidget constraintWidget) {
        this.mX = constraintWidget.getX();
        this.mY = constraintWidget.getY();
        this.mWidth = constraintWidget.getWidth();
        this.mHeight = constraintWidget.getHeight();
        int n = this.mConnections.size();
        for (int i = 0; i < n; ++i) {
            this.mConnections.get(i).updateFrom(constraintWidget);
        }
    }

    static class Connection {
        private ConstraintAnchor mAnchor;
        private int mCreator;
        private int mMargin;
        private ConstraintAnchor.Strength mStrengh;
        private ConstraintAnchor mTarget;

        public Connection(ConstraintAnchor constraintAnchor) {
            this.mAnchor = constraintAnchor;
            this.mTarget = constraintAnchor.getTarget();
            this.mMargin = constraintAnchor.getMargin();
            this.mStrengh = constraintAnchor.getStrength();
            this.mCreator = constraintAnchor.getConnectionCreator();
        }

        public void applyTo(ConstraintWidget constraintWidget) {
            constraintWidget.getAnchor(this.mAnchor.getType()).connect(this.mTarget, this.mMargin, this.mStrengh, this.mCreator);
        }

        public void updateFrom(ConstraintWidget constraintWidget) {
            this.mAnchor = constraintWidget.getAnchor(this.mAnchor.getType());
            if (this.mAnchor != null) {
                this.mTarget = this.mAnchor.getTarget();
                this.mMargin = this.mAnchor.getMargin();
                this.mStrengh = this.mAnchor.getStrength();
                this.mCreator = this.mAnchor.getConnectionCreator();
                return;
            }
            this.mTarget = null;
            this.mMargin = 0;
            this.mStrengh = ConstraintAnchor.Strength.STRONG;
            this.mCreator = 0;
        }
    }

}

