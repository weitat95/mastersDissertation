/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.Guideline;
import java.util.HashSet;

public class ConstraintAnchor {
    private int mConnectionCreator = 0;
    private ConnectionType mConnectionType;
    int mGoneMargin = -1;
    int mGroup = Integer.MAX_VALUE;
    public int mMargin = 0;
    final ConstraintWidget mOwner;
    SolverVariable mSolverVariable;
    private Strength mStrength = Strength.NONE;
    ConstraintAnchor mTarget;
    final Type mType;

    public ConstraintAnchor(ConstraintWidget constraintWidget, Type type) {
        this.mConnectionType = ConnectionType.RELAXED;
        this.mOwner = constraintWidget;
        this.mType = type;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String toString(HashSet<ConstraintAnchor> object) {
        if (!((HashSet)object).add(this)) return "<-";
        StringBuilder stringBuilder = new StringBuilder().append(this.mOwner.getDebugName()).append(":").append(this.mType.toString());
        if (this.mTarget != null) {
            object = " connected to " + this.mTarget.toString((HashSet<ConstraintAnchor>)object);
            do {
                return stringBuilder.append((String)object).toString();
                break;
            } while (true);
        }
        object = "";
        return stringBuilder.append((String)object).toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean connect(ConstraintAnchor constraintAnchor, int n, int n2, Strength strength, int n3, boolean bl) {
        if (constraintAnchor == null) {
            this.mTarget = null;
            this.mMargin = 0;
            this.mGoneMargin = -1;
            this.mStrength = Strength.NONE;
            this.mConnectionCreator = 2;
            return true;
        }
        if (!bl && !this.isValidConnection(constraintAnchor)) {
            return false;
        }
        this.mTarget = constraintAnchor;
        this.mMargin = n > 0 ? n : 0;
        this.mGoneMargin = n2;
        this.mStrength = strength;
        this.mConnectionCreator = n3;
        return true;
    }

    public boolean connect(ConstraintAnchor constraintAnchor, int n, Strength strength, int n2) {
        return this.connect(constraintAnchor, n, -1, strength, n2, false);
    }

    public int getConnectionCreator() {
        return this.mConnectionCreator;
    }

    public ConnectionType getConnectionType() {
        return this.mConnectionType;
    }

    public int getMargin() {
        if (this.mOwner.getVisibility() == 8) {
            return 0;
        }
        if (this.mGoneMargin > -1 && this.mTarget != null && this.mTarget.mOwner.getVisibility() == 8) {
            return this.mGoneMargin;
        }
        return this.mMargin;
    }

    public ConstraintWidget getOwner() {
        return this.mOwner;
    }

    public SolverVariable getSolverVariable() {
        return this.mSolverVariable;
    }

    public Strength getStrength() {
        return this.mStrength;
    }

    public ConstraintAnchor getTarget() {
        return this.mTarget;
    }

    public Type getType() {
        return this.mType;
    }

    public boolean isConnected() {
        return this.mTarget != null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isValidConnection(ConstraintAnchor constraintAnchor) {
        boolean bl = true;
        if (constraintAnchor == null) {
            return false;
        }
        Type type = constraintAnchor.getType();
        if (type == this.mType) {
            if (this.mType == Type.CENTER) return false;
            if (this.mType != Type.BASELINE) return true;
            if (!constraintAnchor.getOwner().hasBaseline()) return false;
            if (!this.getOwner().hasBaseline()) return false;
            return true;
        }
        switch (this.mType) {
            default: {
                return false;
            }
            case CENTER: {
                if (type == Type.BASELINE) return false;
                if (type == Type.CENTER_X) return false;
                if (type == Type.CENTER_Y) return false;
                return bl;
            }
            case LEFT: 
            case RIGHT: {
                bl = type == Type.LEFT || type == Type.RIGHT;
                boolean bl2 = bl;
                if (!(constraintAnchor.getOwner() instanceof Guideline)) return bl2;
                if (bl) return true;
                if (type == Type.CENTER_X) return true;
                return false;
            }
            case TOP: 
            case BOTTOM: 
        }
        bl = type == Type.TOP || type == Type.BOTTOM;
        boolean bl3 = bl;
        if (!(constraintAnchor.getOwner() instanceof Guideline)) return bl3;
        if (bl) return true;
        if (type == Type.CENTER_Y) return true;
        return false;
    }

    public void reset() {
        this.mTarget = null;
        this.mMargin = 0;
        this.mGoneMargin = -1;
        this.mStrength = Strength.STRONG;
        this.mConnectionCreator = 0;
        this.mConnectionType = ConnectionType.RELAXED;
    }

    public void resetSolverVariable(Cache cache) {
        if (this.mSolverVariable == null) {
            this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED);
            return;
        }
        this.mSolverVariable.reset();
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.mConnectionType = connectionType;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        Object object = new HashSet();
        StringBuilder stringBuilder = new StringBuilder().append(this.mOwner.getDebugName()).append(":").append(this.mType.toString());
        if (this.mTarget != null) {
            object = " connected to " + this.mTarget.toString((HashSet<ConstraintAnchor>)object);
            do {
                return stringBuilder.append((String)object).toString();
                break;
            } while (true);
        }
        object = "";
        return stringBuilder.append((String)object).toString();
    }

    public static enum ConnectionType {
        RELAXED,
        STRICT;

    }

    public static enum Strength {
        NONE,
        STRONG,
        WEAK;

    }

    public static enum Type {
        NONE,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y;

    }

}

