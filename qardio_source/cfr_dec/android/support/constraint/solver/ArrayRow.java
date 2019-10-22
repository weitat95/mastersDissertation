/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver;

import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;

public class ArrayRow {
    float constantValue = 0.0f;
    boolean isSimpleDefinition = false;
    boolean used = false;
    SolverVariable variable = null;
    final ArrayLinkedVariables variables;

    public ArrayRow(Cache cache) {
        this.variables = new ArrayLinkedVariables(this, cache);
    }

    public ArrayRow addError(SolverVariable solverVariable, SolverVariable solverVariable2) {
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        return this;
    }

    ArrayRow addSingleError(SolverVariable solverVariable, int n) {
        this.variables.put(solverVariable, n);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    ArrayRow createRowCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int n, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int n2) {
        if (solverVariable2 == solverVariable3) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable2, -2.0f);
            return this;
        } else if (f == 0.5f) {
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable3, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            if (n <= 0 && n2 <= 0) return this;
            {
                this.constantValue = -n + n2;
                return this;
            }
        } else {
            if (f <= 0.0f) {
                this.variables.put(solverVariable, -1.0f);
                this.variables.put(solverVariable2, 1.0f);
                this.constantValue = n;
                return this;
            }
            if (f >= 1.0f) {
                this.variables.put(solverVariable3, -1.0f);
                this.variables.put(solverVariable4, 1.0f);
                this.constantValue = n2;
                return this;
            }
            this.variables.put(solverVariable, (1.0f - f) * 1.0f);
            this.variables.put(solverVariable2, (1.0f - f) * -1.0f);
            this.variables.put(solverVariable3, -1.0f * f);
            this.variables.put(solverVariable4, 1.0f * f);
            if (n <= 0 && n2 <= 0) return this;
            {
                this.constantValue = (float)(-n) * (1.0f - f) + (float)n2 * f;
                return this;
            }
        }
    }

    ArrayRow createRowDefinition(SolverVariable solverVariable, int n) {
        this.variable = solverVariable;
        solverVariable.computedValue = n;
        this.constantValue = n;
        this.isSimpleDefinition = true;
        return this;
    }

    ArrayRow createRowDimensionPercent(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, float f) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, 1.0f - f);
        this.variables.put(solverVariable3, f);
        return this;
    }

    public ArrayRow createRowDimensionRatio(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f) {
        this.variables.put(solverVariable, -1.0f);
        this.variables.put(solverVariable2, 1.0f);
        this.variables.put(solverVariable3, f);
        this.variables.put(solverVariable4, -f);
        return this;
    }

    public ArrayRow createRowEqualDimension(float f, float f2, float f3, SolverVariable solverVariable, int n, SolverVariable solverVariable2, int n2, SolverVariable solverVariable3, int n3, SolverVariable solverVariable4, int n4) {
        if (f2 == 0.0f || f == f3) {
            this.constantValue = -n - n2 + n3 + n4;
            this.variables.put(solverVariable, 1.0f);
            this.variables.put(solverVariable2, -1.0f);
            this.variables.put(solverVariable4, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
            return this;
        }
        f = f / f2 / (f3 / f2);
        this.constantValue = (float)(-n - n2) + (float)n3 * f + (float)n4 * f;
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        this.variables.put(solverVariable4, f);
        this.variables.put(solverVariable3, -f);
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, int n) {
        if (n < 0) {
            this.constantValue = n * -1;
            this.variables.put(solverVariable, 1.0f);
            return this;
        }
        this.constantValue = n;
        this.variables.put(solverVariable, -1.0f);
        return this;
    }

    public ArrayRow createRowEquals(SolverVariable solverVariable, SolverVariable solverVariable2, int n) {
        int n2 = 0;
        int n3 = 0;
        if (n != 0) {
            n2 = n;
            n = n3;
            n3 = n2;
            if (n2 < 0) {
                n3 = n2 * -1;
                n = 1;
            }
            this.constantValue = n3;
            n2 = n;
        }
        if (n2 == 0) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            return this;
        }
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        return this;
    }

    public ArrayRow createRowGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int n) {
        int n2 = 0;
        int n3 = 0;
        if (n != 0) {
            n2 = n;
            n = n3;
            n3 = n2;
            if (n2 < 0) {
                n3 = n2 * -1;
                n = 1;
            }
            this.constantValue = n3;
            n2 = n;
        }
        if (n2 == 0) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, 1.0f);
            return this;
        }
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        this.variables.put(solverVariable3, -1.0f);
        return this;
    }

    public ArrayRow createRowLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int n) {
        int n2 = 0;
        int n3 = 0;
        if (n != 0) {
            n2 = n;
            n = n3;
            n3 = n2;
            if (n2 < 0) {
                n3 = n2 * -1;
                n = 1;
            }
            this.constantValue = n3;
            n2 = n;
        }
        if (n2 == 0) {
            this.variables.put(solverVariable, -1.0f);
            this.variables.put(solverVariable2, 1.0f);
            this.variables.put(solverVariable3, -1.0f);
            return this;
        }
        this.variables.put(solverVariable, 1.0f);
        this.variables.put(solverVariable2, -1.0f);
        this.variables.put(solverVariable3, 1.0f);
        return this;
    }

    void ensurePositiveConstant() {
        if (this.constantValue < 0.0f) {
            this.constantValue *= -1.0f;
            this.variables.invert();
        }
    }

    boolean hasKeyVariable() {
        return !(this.variable == null || this.variable.mType != SolverVariable.Type.UNRESTRICTED && this.constantValue < 0.0f);
    }

    boolean hasVariable(SolverVariable solverVariable) {
        return this.variables.containsKey(solverVariable);
    }

    void pickRowVariable() {
        SolverVariable solverVariable = this.variables.pickPivotCandidate();
        if (solverVariable != null) {
            this.pivot(solverVariable);
        }
        if (this.variables.currentSize == 0) {
            this.isSimpleDefinition = true;
        }
    }

    void pivot(SolverVariable solverVariable) {
        if (this.variable != null) {
            this.variables.put(this.variable, -1.0f);
            this.variable = null;
        }
        float f = this.variables.remove(solverVariable) * -1.0f;
        this.variable = solverVariable;
        if (f == 1.0f) {
            return;
        }
        this.constantValue /= f;
        this.variables.divideByAmount(f);
    }

    public void reset() {
        this.variable = null;
        this.variables.clear();
        this.constantValue = 0.0f;
        this.isSimpleDefinition = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    String toReadableString() {
        String string2 = this.variable == null ? "" + "0" : "" + this.variable;
        Object object = string2 + " = ";
        boolean bl = false;
        string2 = object;
        if (this.constantValue != 0.0f) {
            string2 = (String)object + this.constantValue;
            bl = true;
        }
        int n = this.variables.currentSize;
        for (int i = 0; i < n; ++i) {
            float f;
            object = this.variables.getVariable(i);
            if (object == null) continue;
            float f2 = this.variables.getVariableValue(i);
            String string3 = ((SolverVariable)object).toString();
            if (!bl) {
                f = f2;
                object = string2;
                if (f2 < 0.0f) {
                    object = string2 + "- ";
                    f = f2 * -1.0f;
                }
            } else if (f2 > 0.0f) {
                object = string2 + " + ";
                f = f2;
            } else {
                object = string2 + " - ";
                f = f2 * -1.0f;
            }
            string2 = f == 1.0f ? (String)object + string3 : (String)object + f + " " + string3;
            bl = true;
        }
        object = string2;
        if (bl) return object;
        return string2 + "0.0";
    }

    public String toString() {
        return this.toReadableString();
    }

    void updateClientEquations() {
        this.variables.updateClientEquations(this);
    }

    boolean updateRowWithEquation(ArrayRow arrayRow) {
        this.variables.updateFromRow(this, arrayRow);
        return true;
    }
}

