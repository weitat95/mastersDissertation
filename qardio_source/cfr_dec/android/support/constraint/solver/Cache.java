/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Pools;
import android.support.constraint.solver.SolverVariable;

public class Cache {
    Pools.Pool<ArrayRow> arrayRowPool = new Pools.SimplePool<ArrayRow>(256);
    SolverVariable[] mIndexedVariables;
    Pools.Pool<SolverVariable> solverVariablePool = new Pools.SimplePool<SolverVariable>(256);

    public Cache() {
        this.mIndexedVariables = new SolverVariable[32];
    }
}

