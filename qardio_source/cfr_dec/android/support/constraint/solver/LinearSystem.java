/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver;

import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.Goal;
import android.support.constraint.solver.Pools;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem {
    private static int POOL_SIZE = 1000;
    private int TABLE_SIZE;
    private boolean[] mAlreadyTestedCandidates;
    final Cache mCache;
    private Goal mGoal = new Goal();
    private int mMaxColumns = this.TABLE_SIZE = 32;
    private int mMaxRows;
    int mNumColumns = 1;
    private int mNumRows = 0;
    private SolverVariable[] mPoolVariables;
    private int mPoolVariablesCount = 0;
    private ArrayRow[] mRows = null;
    private HashMap<String, SolverVariable> mVariables = null;
    int mVariablesID = 0;
    private ArrayRow[] tempClientsCopy;

    public LinearSystem() {
        this.mAlreadyTestedCandidates = new boolean[this.TABLE_SIZE];
        this.mMaxRows = this.TABLE_SIZE;
        this.mPoolVariables = new SolverVariable[POOL_SIZE];
        this.tempClientsCopy = new ArrayRow[this.TABLE_SIZE];
        this.mRows = new ArrayRow[this.TABLE_SIZE];
        this.releaseRows();
        this.mCache = new Cache();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private SolverVariable acquireSolverVariable(SolverVariable.Type object) {
        void var1_3;
        SolverVariable[] arrsolverVariable = this.mCache.solverVariablePool.acquire();
        if (arrsolverVariable == null) {
            SolverVariable solverVariable = new SolverVariable((SolverVariable.Type)((Object)object));
        } else {
            arrsolverVariable.reset();
            arrsolverVariable.setType((SolverVariable.Type)((Object)object));
            SolverVariable[] arrsolverVariable2 = arrsolverVariable;
        }
        if (this.mPoolVariablesCount >= POOL_SIZE) {
            this.mPoolVariables = Arrays.copyOf(this.mPoolVariables, POOL_SIZE *= 2);
        }
        arrsolverVariable = this.mPoolVariables;
        int n = this.mPoolVariablesCount;
        this.mPoolVariablesCount = n + 1;
        arrsolverVariable[n] = var1_3;
        return var1_3;
    }

    private void addError(ArrayRow arrayRow) {
        arrayRow.addError(this.createErrorVariable(), this.createErrorVariable());
    }

    private void addSingleError(ArrayRow arrayRow, int n) {
        arrayRow.addSingleError(this.createErrorVariable(), n);
    }

    private void computeValues() {
        for (int i = 0; i < this.mNumRows; ++i) {
            ArrayRow arrayRow = this.mRows[i];
            arrayRow.variable.computedValue = arrayRow.constantValue;
        }
    }

    public static ArrayRow createRowCentering(LinearSystem object, SolverVariable solverVariable, SolverVariable solverVariable2, int n, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int n2, boolean bl) {
        ArrayRow arrayRow = ((LinearSystem)object).createRow();
        arrayRow.createRowCentering(solverVariable, solverVariable2, n, f, solverVariable3, solverVariable4, n2);
        if (bl) {
            solverVariable = ((LinearSystem)object).createErrorVariable();
            object = ((LinearSystem)object).createErrorVariable();
            solverVariable.strength = 4;
            ((SolverVariable)object).strength = 4;
            arrayRow.addError(solverVariable, (SolverVariable)object);
        }
        return arrayRow;
    }

    public static ArrayRow createRowDimensionPercent(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, float f, boolean bl) {
        ArrayRow arrayRow = linearSystem.createRow();
        if (bl) {
            linearSystem.addError(arrayRow);
        }
        return arrayRow.createRowDimensionPercent(solverVariable, solverVariable2, solverVariable3, f);
    }

    public static ArrayRow createRowEquals(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, int n, boolean bl) {
        ArrayRow arrayRow = linearSystem.createRow();
        arrayRow.createRowEquals(solverVariable, solverVariable2, n);
        if (bl) {
            linearSystem.addSingleError(arrayRow, 1);
        }
        return arrayRow;
    }

    public static ArrayRow createRowGreaterThan(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, int n, boolean bl) {
        SolverVariable solverVariable3 = linearSystem.createSlackVariable();
        ArrayRow arrayRow = linearSystem.createRow();
        arrayRow.createRowGreaterThan(solverVariable, solverVariable2, solverVariable3, n);
        if (bl) {
            linearSystem.addSingleError(arrayRow, (int)(-1.0f * arrayRow.variables.get(solverVariable3)));
        }
        return arrayRow;
    }

    public static ArrayRow createRowLowerThan(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, int n, boolean bl) {
        SolverVariable solverVariable3 = linearSystem.createSlackVariable();
        ArrayRow arrayRow = linearSystem.createRow();
        arrayRow.createRowLowerThan(solverVariable, solverVariable2, solverVariable3, n);
        if (bl) {
            linearSystem.addSingleError(arrayRow, (int)(-1.0f * arrayRow.variables.get(solverVariable3)));
        }
        return arrayRow;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    private int enforceBFS(Goal var1_1) throws Exception {
        block21: {
            var7_2 = 0;
            var8_3 = 0;
            var6_4 = 0;
            do {
                var5_5 = var8_3;
                if (var6_4 >= this.mNumRows) break;
                if (this.mRows[var6_4].variable.mType != SolverVariable.Type.UNRESTRICTED && this.mRows[var6_4].constantValue < 0.0f) {
                    var5_5 = 1;
                    break;
                }
                ++var6_4;
            } while (true);
            var6_4 = var7_2;
            if (var5_5 == 0) break block21;
            var11_6 = false;
            var5_5 = 0;
            block1: do {
                var6_4 = var5_5;
                if (var11_6) {
                    break block21;
                }
                var15_15 = var5_5 + 1;
                var2_7 = Float.MAX_VALUE;
                var7_2 = 0;
                var6_4 = -1;
                var5_5 = -1;
                var8_3 = 0;
                block2: do {
                    if (var8_3 < this.mNumRows) {
                        var16_16 = this.mRows[var8_3];
                        if (var16_16.variable.mType == SolverVariable.Type.UNRESTRICTED) {
                            var13_13 = var7_2;
                            var12_12 = var6_4;
                            var10_11 = var5_5;
                            var3_8 = var2_7;
                        } else {
                            var3_8 = var2_7;
                            var10_11 = var5_5;
                            var12_12 = var6_4;
                            var13_13 = var7_2;
                            if (var16_16.constantValue < 0.0f) {
                                var9_10 = 1;
                                break block1;
                            }
                        }
                    } else {
                        if (var6_4 != -1) {
                            var16_16 = this.mRows[var6_4];
                            var16_16.variable.definitionId = -1;
                            var16_16.pivot(this.mCache.mIndexedVariables[var5_5]);
                            var16_16.variable.definitionId = var6_4;
                            for (var5_5 = 0; var5_5 < this.mNumRows; ++var5_5) {
                                this.mRows[var5_5].updateRowWithEquation(var16_16);
                            }
                            var1_1.updateFromSystem(this);
                            var5_5 = var15_15;
                            continue block1;
                        }
                        var11_6 = true;
                        var5_5 = var15_15;
                        continue block1;
                    }
                    do {
                        ++var8_3;
                        var2_7 = var3_8;
                        var5_5 = var10_11;
                        var6_4 = var12_12;
                        var7_2 = var13_13;
                        continue block2;
                        break;
                    } while (true);
                    break;
                } while (true);
                break;
            } while (true);
            do {
                block23: {
                    block22: {
                        var3_8 = var2_7;
                        var10_11 = var5_5;
                        var12_12 = var6_4;
                        var13_13 = var7_2;
                        if (var9_10 >= this.mNumColumns) ** continue;
                        var17_17 = this.mCache.mIndexedVariables[var9_10];
                        var4_9 = var16_16.variables.get(var17_17);
                        if (!(var4_9 <= 0.0f)) break block22;
                        var12_12 = var7_2;
                        var13_13 = var6_4;
                        var14_14 = var5_5;
                        var3_8 = var2_7;
                        break block23;
                    }
                    var12_12 = 0;
                    var10_11 = var5_5;
                    var5_5 = var12_12;
                    do {
                        block25: {
                            block24: {
                                var3_8 = var2_7;
                                var14_14 = var10_11;
                                var13_13 = var6_4;
                                var12_12 = var7_2;
                                if (var5_5 >= 6) break;
                                var3_8 = var17_17.strengthVector[var5_5] / var4_9;
                                if (var3_8 < var2_7 && var5_5 == var7_2) break block24;
                                var12_12 = var7_2;
                                if (var5_5 <= var7_2) break block25;
                            }
                            var2_7 = var3_8;
                            var6_4 = var8_3;
                            var10_11 = var9_10;
                            var12_12 = var5_5;
                        }
                        ++var5_5;
                        var7_2 = var12_12;
                    } while (true);
                }
                ++var9_10;
                var2_7 = var3_8;
                var5_5 = var14_14;
                var6_4 = var13_13;
                var7_2 = var12_12;
            } while (true);
        }
        for (var5_5 = 0; !(var5_5 >= this.mNumRows || this.mRows[var5_5].variable.mType != SolverVariable.Type.UNRESTRICTED && this.mRows[var5_5].constantValue < 0.0f); ++var5_5) {
        }
        return var6_4;
    }

    private void increaseTableSize() {
        this.TABLE_SIZE *= 2;
        this.mRows = Arrays.copyOf(this.mRows, this.TABLE_SIZE);
        this.mCache.mIndexedVariables = Arrays.copyOf(this.mCache.mIndexedVariables, this.TABLE_SIZE);
        this.mAlreadyTestedCandidates = new boolean[this.TABLE_SIZE];
        this.mMaxColumns = this.TABLE_SIZE;
        this.mMaxRows = this.TABLE_SIZE;
        this.mGoal.variables.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int optimize(Goal goal) {
        int n;
        int n2 = 0;
        int n3 = 0;
        for (n = 0; n < this.mNumColumns; ++n) {
            this.mAlreadyTestedCandidates[n] = false;
        }
        int n4 = 0;
        n = n2;
        while (n == 0) {
            float f;
            int n5 = n3 + 1;
            Object object = goal.getPivotCandidate();
            n2 = n;
            Object object2 = object;
            n3 = n4++;
            if (object != null) {
                if (this.mAlreadyTestedCandidates[((SolverVariable)object).id]) {
                    object2 = null;
                    n3 = n4;
                    n2 = n;
                } else {
                    this.mAlreadyTestedCandidates[object.id] = true;
                    n2 = n;
                    object2 = object;
                    n3 = n4;
                    if (n4 >= this.mNumColumns) {
                        n2 = 1;
                        object2 = object;
                        n3 = n4;
                    }
                }
            }
            if (object2 != null) {
                f = Float.MAX_VALUE;
                n4 = -1;
            } else {
                n = 1;
                n4 = n3;
                n3 = n5;
                continue;
            }
            for (n = 0; n < this.mNumRows; ++n) {
                float f2;
                int n6;
                object = this.mRows[n];
                if (object.variable.mType == SolverVariable.Type.UNRESTRICTED) {
                    n6 = n4;
                    f2 = f;
                } else {
                    f2 = f;
                    n6 = n4;
                    if (((ArrayRow)object).hasVariable((SolverVariable)object2)) {
                        float f3 = ((ArrayRow)object).variables.get((SolverVariable)object2);
                        f2 = f;
                        n6 = n4;
                        if (f3 < 0.0f) {
                            f3 = -((ArrayRow)object).constantValue / f3;
                            f2 = f;
                            n6 = n4;
                            if (f3 < f) {
                                f2 = f3;
                                n6 = n;
                            }
                        }
                    }
                }
                f = f2;
                n4 = n6;
            }
            if (n4 > -1) {
                object = this.mRows[n4];
                object.variable.definitionId = -1;
                ((ArrayRow)object).pivot((SolverVariable)object2);
                object.variable.definitionId = n4;
                for (n = 0; n < this.mNumRows; ++n) {
                    this.mRows[n].updateRowWithEquation((ArrayRow)object);
                }
                goal.updateFromSystem(this);
                try {
                    this.enforceBFS(goal);
                    n = n2;
                    n4 = n3;
                    n3 = n5;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    n = n2;
                    n4 = n3;
                    n3 = n5;
                }
                continue;
            }
            n = 1;
            n4 = n3;
            n3 = n5;
        }
        return n3;
    }

    private void releaseRows() {
        for (int i = 0; i < this.mRows.length; ++i) {
            ArrayRow arrayRow = this.mRows[i];
            if (arrayRow != null) {
                this.mCache.arrayRowPool.release(arrayRow);
            }
            this.mRows[i] = null;
        }
    }

    private void updateRowFromVariables(ArrayRow arrayRow) {
        if (this.mNumRows > 0) {
            arrayRow.variables.updateFromSystem(arrayRow, this.mRows);
            if (arrayRow.variables.currentSize == 0) {
                arrayRow.isSimpleDefinition = true;
            }
        }
    }

    public void addCentering(SolverVariable solverVariable, SolverVariable solverVariable2, int n, float f, SolverVariable solverVariable3, SolverVariable solverVariable4, int n2, int n3) {
        ArrayRow arrayRow = this.createRow();
        arrayRow.createRowCentering(solverVariable, solverVariable2, n, f, solverVariable3, solverVariable4, n2);
        solverVariable = this.createErrorVariable();
        solverVariable2 = this.createErrorVariable();
        solverVariable.strength = n3;
        solverVariable2.strength = n3;
        arrayRow.addError(solverVariable, solverVariable2);
        this.addConstraint(arrayRow);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addConstraint(ArrayRow arrayRow) {
        block10: {
            block11: {
                if (arrayRow == null) break block10;
                if (this.mNumRows + 1 >= this.mMaxRows || this.mNumColumns + 1 >= this.mMaxColumns) {
                    this.increaseTableSize();
                }
                if (arrayRow.isSimpleDefinition) break block11;
                this.updateRowFromVariables(arrayRow);
                arrayRow.ensurePositiveConstant();
                arrayRow.pickRowVariable();
                if (!arrayRow.hasKeyVariable()) break block10;
            }
            if (this.mRows[this.mNumRows] != null) {
                this.mCache.arrayRowPool.release(this.mRows[this.mNumRows]);
            }
            if (!arrayRow.isSimpleDefinition) {
                arrayRow.updateClientEquations();
            }
            this.mRows[this.mNumRows] = arrayRow;
            arrayRow.variable.definitionId = this.mNumRows++;
            int n = arrayRow.variable.mClientEquationsCount;
            if (n > 0) {
                int n2;
                while (this.tempClientsCopy.length < n) {
                    this.tempClientsCopy = new ArrayRow[this.tempClientsCopy.length * 2];
                }
                ArrayRow[] arrarrayRow = this.tempClientsCopy;
                for (n2 = 0; n2 < n; ++n2) {
                    arrarrayRow[n2] = arrayRow.variable.mClientEquations[n2];
                }
                for (n2 = 0; n2 < n; ++n2) {
                    ArrayRow arrayRow2 = arrarrayRow[n2];
                    if (arrayRow2 == arrayRow) continue;
                    arrayRow2.variables.updateFromRow(arrayRow2, arrayRow);
                    arrayRow2.updateClientEquations();
                }
            }
        }
    }

    public ArrayRow addEquality(SolverVariable solverVariable, SolverVariable solverVariable2, int n, int n2) {
        ArrayRow arrayRow = this.createRow();
        arrayRow.createRowEquals(solverVariable, solverVariable2, n);
        solverVariable = this.createErrorVariable();
        solverVariable2 = this.createErrorVariable();
        solverVariable.strength = n2;
        solverVariable2.strength = n2;
        arrayRow.addError(solverVariable, solverVariable2);
        this.addConstraint(arrayRow);
        return arrayRow;
    }

    public void addEquality(SolverVariable solverVariable, int n) {
        int n2 = solverVariable.definitionId;
        if (solverVariable.definitionId != -1) {
            ArrayRow arrayRow = this.mRows[n2];
            if (arrayRow.isSimpleDefinition) {
                arrayRow.constantValue = n;
                return;
            }
            arrayRow = this.createRow();
            arrayRow.createRowEquals(solverVariable, n);
            this.addConstraint(arrayRow);
            return;
        }
        ArrayRow arrayRow = this.createRow();
        arrayRow.createRowDefinition(solverVariable, n);
        this.addConstraint(arrayRow);
    }

    public void addGreaterThan(SolverVariable solverVariable, SolverVariable solverVariable2, int n, int n2) {
        ArrayRow arrayRow = this.createRow();
        SolverVariable solverVariable3 = this.createSlackVariable();
        solverVariable3.strength = n2;
        arrayRow.createRowGreaterThan(solverVariable, solverVariable2, solverVariable3, n);
        this.addConstraint(arrayRow);
    }

    public void addLowerThan(SolverVariable solverVariable, SolverVariable solverVariable2, int n, int n2) {
        ArrayRow arrayRow = this.createRow();
        SolverVariable solverVariable3 = this.createSlackVariable();
        solverVariable3.strength = n2;
        arrayRow.createRowLowerThan(solverVariable, solverVariable2, solverVariable3, n);
        this.addConstraint(arrayRow);
    }

    public SolverVariable createErrorVariable() {
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
        }
        SolverVariable solverVariable = this.acquireSolverVariable(SolverVariable.Type.ERROR);
        ++this.mVariablesID;
        ++this.mNumColumns;
        solverVariable.id = this.mVariablesID;
        this.mCache.mIndexedVariables[this.mVariablesID] = solverVariable;
        return solverVariable;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public SolverVariable createObjectVariable(Object object) {
        if (object == null) {
            return null;
        }
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
        }
        SolverVariable solverVariable = null;
        if (!(object instanceof ConstraintAnchor)) return solverVariable;
        SolverVariable solverVariable2 = solverVariable = ((ConstraintAnchor)object).getSolverVariable();
        if (solverVariable == null) {
            ((ConstraintAnchor)object).resetSolverVariable(this.mCache);
            solverVariable2 = ((ConstraintAnchor)object).getSolverVariable();
        }
        if (solverVariable2.id != -1 && solverVariable2.id <= this.mVariablesID) {
            solverVariable = solverVariable2;
            if (this.mCache.mIndexedVariables[solverVariable2.id] != null) return solverVariable;
        }
        if (solverVariable2.id != -1) {
            solverVariable2.reset();
        }
        ++this.mVariablesID;
        ++this.mNumColumns;
        solverVariable2.id = this.mVariablesID;
        solverVariable2.mType = SolverVariable.Type.UNRESTRICTED;
        this.mCache.mIndexedVariables[this.mVariablesID] = solverVariable2;
        return solverVariable2;
    }

    public ArrayRow createRow() {
        ArrayRow arrayRow = this.mCache.arrayRowPool.acquire();
        if (arrayRow == null) {
            return new ArrayRow(this.mCache);
        }
        arrayRow.reset();
        return arrayRow;
    }

    public SolverVariable createSlackVariable() {
        if (this.mNumColumns + 1 >= this.mMaxColumns) {
            this.increaseTableSize();
        }
        SolverVariable solverVariable = this.acquireSolverVariable(SolverVariable.Type.SLACK);
        ++this.mVariablesID;
        ++this.mNumColumns;
        solverVariable.id = this.mVariablesID;
        this.mCache.mIndexedVariables[this.mVariablesID] = solverVariable;
        return solverVariable;
    }

    public Cache getCache() {
        return this.mCache;
    }

    public int getObjectVariableValue(Object object) {
        if ((object = ((ConstraintAnchor)object).getSolverVariable()) != null) {
            return (int)(((SolverVariable)object).computedValue + 0.5f);
        }
        return 0;
    }

    ArrayRow getRow(int n) {
        return this.mRows[n];
    }

    public void minimize() throws Exception {
        this.minimizeGoal(this.mGoal);
    }

    void minimizeGoal(Goal goal) throws Exception {
        goal.updateFromSystem(this);
        this.enforceBFS(goal);
        this.optimize(goal);
        this.computeValues();
    }

    public void reset() {
        int n;
        for (n = 0; n < this.mCache.mIndexedVariables.length; ++n) {
            SolverVariable solverVariable = this.mCache.mIndexedVariables[n];
            if (solverVariable == null) continue;
            solverVariable.reset();
        }
        this.mCache.solverVariablePool.releaseAll(this.mPoolVariables, this.mPoolVariablesCount);
        this.mPoolVariablesCount = 0;
        Arrays.fill(this.mCache.mIndexedVariables, null);
        if (this.mVariables != null) {
            this.mVariables.clear();
        }
        this.mVariablesID = 0;
        this.mGoal.variables.clear();
        this.mNumColumns = 1;
        for (n = 0; n < this.mNumRows; ++n) {
            this.mRows[n].used = false;
        }
        this.releaseRows();
        this.mNumRows = 0;
    }
}

