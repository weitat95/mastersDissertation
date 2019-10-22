/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver;

import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import java.util.ArrayList;

public class Goal {
    ArrayList<SolverVariable> variables = new ArrayList();

    /*
     * Enabled aggressive block sorting
     */
    private void initFromSystemErrors(LinearSystem linearSystem) {
        this.variables.clear();
        int n = 1;
        while (n < linearSystem.mNumColumns) {
            SolverVariable solverVariable = linearSystem.mCache.mIndexedVariables[n];
            for (int i = 0; i < 6; ++i) {
                solverVariable.strengthVector[i] = 0.0f;
            }
            solverVariable.strengthVector[solverVariable.strength] = 1.0f;
            if (solverVariable.mType == SolverVariable.Type.ERROR) {
                this.variables.add(solverVariable);
            }
            ++n;
        }
        return;
    }

    SolverVariable getPivotCandidate() {
        int n = this.variables.size();
        SolverVariable solverVariable = null;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            SolverVariable solverVariable2 = this.variables.get(i);
            for (int j = 5; j >= 0; --j) {
                float f = solverVariable2.strengthVector[j];
                SolverVariable solverVariable3 = solverVariable;
                int n3 = n2;
                if (solverVariable == null) {
                    solverVariable3 = solverVariable;
                    n3 = n2;
                    if (f < 0.0f) {
                        solverVariable3 = solverVariable;
                        n3 = n2;
                        if (j >= n2) {
                            n3 = j;
                            solverVariable3 = solverVariable2;
                        }
                    }
                }
                solverVariable = solverVariable3;
                n2 = n3;
                if (!(f > 0.0f)) continue;
                solverVariable = solverVariable3;
                n2 = n3;
                if (j <= n3) continue;
                n2 = j;
                solverVariable = null;
            }
        }
        return solverVariable;
    }

    public String toString() {
        String string2 = "Goal: ";
        int n = this.variables.size();
        for (int i = 0; i < n; ++i) {
            SolverVariable solverVariable = this.variables.get(i);
            string2 = string2 + solverVariable.strengthsToString();
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateFromSystem(LinearSystem linearSystem) {
        this.initFromSystemErrors(linearSystem);
        int n = this.variables.size();
        int n2 = 0;
        while (n2 < n) {
            SolverVariable solverVariable = this.variables.get(n2);
            if (solverVariable.definitionId != -1) {
                ArrayLinkedVariables arrayLinkedVariables = linearSystem.getRow((int)solverVariable.definitionId).variables;
                int n3 = arrayLinkedVariables.currentSize;
                for (int i = 0; i < n3; ++i) {
                    SolverVariable solverVariable2 = arrayLinkedVariables.getVariable(i);
                    if (solverVariable2 == null) continue;
                    float f = arrayLinkedVariables.getVariableValue(i);
                    for (int j = 0; j < 6; ++j) {
                        float[] arrf = solverVariable2.strengthVector;
                        arrf[j] = arrf[j] + solverVariable.strengthVector[j] * f;
                    }
                    if (this.variables.contains(solverVariable2)) continue;
                    this.variables.add(solverVariable2);
                }
                solverVariable.clearStrengths();
            }
            ++n2;
        }
        return;
    }
}

