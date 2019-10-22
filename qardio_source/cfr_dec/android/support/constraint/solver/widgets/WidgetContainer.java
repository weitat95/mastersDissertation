/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;

public class WidgetContainer
extends ConstraintWidget {
    protected ArrayList<ConstraintWidget> mChildren = new ArrayList();

    public void add(ConstraintWidget constraintWidget) {
        this.mChildren.add(constraintWidget);
        if (constraintWidget.getParent() != null) {
            ((WidgetContainer)constraintWidget.getParent()).remove(constraintWidget);
        }
        constraintWidget.setParent(this);
    }

    public ConstraintWidgetContainer getRootConstraintContainer() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2 = this.getParent();
        ConstraintWidgetContainer constraintWidgetContainer = null;
        ConstraintWidget constraintWidget3 = constraintWidget2;
        if (this instanceof ConstraintWidgetContainer) {
            constraintWidgetContainer = (ConstraintWidgetContainer)this;
            constraintWidget3 = constraintWidget2;
        }
        while ((constraintWidget = constraintWidget3) != null) {
            constraintWidget3 = constraintWidget2 = constraintWidget.getParent();
            if (!(constraintWidget instanceof ConstraintWidgetContainer)) continue;
            constraintWidgetContainer = (ConstraintWidgetContainer)constraintWidget;
            constraintWidget3 = constraintWidget2;
        }
        return constraintWidgetContainer;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void layout() {
        this.updateDrawPosition();
        if (this.mChildren != null) {
            int n = this.mChildren.size();
            for (int i = 0; i < n; ++i) {
                ConstraintWidget constraintWidget = this.mChildren.get(i);
                if (!(constraintWidget instanceof WidgetContainer)) continue;
                ((WidgetContainer)constraintWidget).layout();
            }
        }
    }

    public void remove(ConstraintWidget constraintWidget) {
        this.mChildren.remove(constraintWidget);
        constraintWidget.setParent(null);
    }

    public void removeAllChildren() {
        this.mChildren.clear();
    }

    @Override
    public void reset() {
        this.mChildren.clear();
        super.reset();
    }

    @Override
    public void resetSolverVariables(Cache cache) {
        super.resetSolverVariables(cache);
        int n = this.mChildren.size();
        for (int i = 0; i < n; ++i) {
            this.mChildren.get(i).resetSolverVariables(cache);
        }
    }

    @Override
    public void setOffset(int n, int n2) {
        super.setOffset(n, n2);
        n2 = this.mChildren.size();
        for (n = 0; n < n2; ++n) {
            this.mChildren.get(n).setOffset(this.getRootX(), this.getRootY());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void updateDrawPosition() {
        super.updateDrawPosition();
        if (this.mChildren != null) {
            int n = this.mChildren.size();
            for (int i = 0; i < n; ++i) {
                ConstraintWidget constraintWidget = this.mChildren.get(i);
                constraintWidget.setOffset(this.getDrawX(), this.getDrawY());
                if (constraintWidget instanceof ConstraintWidgetContainer) continue;
                constraintWidget.updateDrawPosition();
            }
        }
    }
}

