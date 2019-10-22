/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;
import java.util.Arrays;

public class ArrayLinkedVariables {
    private int ROW_SIZE = 8;
    private SolverVariable candidate = null;
    int currentSize = 0;
    private int[] mArrayIndices = new int[this.ROW_SIZE];
    private int[] mArrayNextIndices = new int[this.ROW_SIZE];
    private float[] mArrayValues = new float[this.ROW_SIZE];
    private final Cache mCache;
    private boolean mDidFillOnce = false;
    private int mHead = -1;
    private int mLast = -1;
    private final ArrayRow mRow;

    ArrayLinkedVariables(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.mCache = cache;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public final void add(SolverVariable var1_1, float var2_2) {
        if (var2_2 == 0.0f) {
            return;
        }
        if (this.mHead == -1) {
            this.mHead = 0;
            this.mArrayValues[this.mHead] = var2_2;
            this.mArrayIndices[this.mHead] = var1_1.id;
            this.mArrayNextIndices[this.mHead] = -1;
            ++this.currentSize;
            if (this.mDidFillOnce != false) return;
            ++this.mLast;
            return;
        }
        var3_3 = this.mHead;
        var6_4 = -1;
        for (var4_5 = 0; var3_3 != -1 && var4_5 < this.currentSize; ++var4_5) {
            var5_6 = this.mArrayIndices[var3_3];
            if (var5_6 == var1_1.id) {
                var1_1 = this.mArrayValues;
                var1_1[var3_3] = var1_1[var3_3] + var2_2;
                if (this.mArrayValues[var3_3] != 0.0f) return;
                if (var3_3 == this.mHead) {
                    this.mHead = this.mArrayNextIndices[var3_3];
                } else {
                    this.mArrayNextIndices[var6_4] = this.mArrayNextIndices[var3_3];
                }
                this.mCache.mIndexedVariables[var5_6].removeClientEquation(this.mRow);
                if (this.mDidFillOnce) {
                    this.mLast = var3_3;
                }
                --this.currentSize;
                return;
            }
            if (this.mArrayIndices[var3_3] < var1_1.id) {
                var6_4 = var3_3;
            }
            var3_3 = this.mArrayNextIndices[var3_3];
        }
        var3_3 = this.mLast + 1;
        if (this.mDidFillOnce) {
            var3_3 = this.mArrayIndices[this.mLast] == -1 ? this.mLast : this.mArrayIndices.length;
        }
        var4_5 = var3_3;
        if (var3_3 < this.mArrayIndices.length) ** GOTO lbl46
        var4_5 = var3_3;
        if (this.currentSize >= this.mArrayIndices.length) ** GOTO lbl46
        var5_6 = 0;
        do {
            block18: {
                block17: {
                    var4_5 = var3_3;
                    if (var5_6 >= this.mArrayIndices.length) break block17;
                    if (this.mArrayIndices[var5_6] != -1) break block18;
                    var4_5 = var5_6;
                }
                var3_3 = var4_5;
                if (var4_5 >= this.mArrayIndices.length) {
                    var3_3 = this.mArrayIndices.length;
                    this.ROW_SIZE *= 2;
                    this.mDidFillOnce = false;
                    this.mLast = var3_3 - 1;
                    this.mArrayValues = Arrays.copyOf(this.mArrayValues, this.ROW_SIZE);
                    this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
                    this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
                }
                this.mArrayIndices[var3_3] = var1_1.id;
                this.mArrayValues[var3_3] = var2_2;
                if (var6_4 != -1) {
                    this.mArrayNextIndices[var3_3] = this.mArrayNextIndices[var6_4];
                    this.mArrayNextIndices[var6_4] = var3_3;
                } else {
                    this.mArrayNextIndices[var3_3] = this.mHead;
                    this.mHead = var3_3;
                }
                ++this.currentSize;
                if (!this.mDidFillOnce) {
                    ++this.mLast;
                }
                if (this.mLast < this.mArrayIndices.length) return;
                this.mDidFillOnce = true;
                this.mLast = this.mArrayIndices.length - 1;
                return;
            }
            ++var5_6;
        } while (true);
    }

    public final void clear() {
        this.mHead = -1;
        this.mLast = -1;
        this.mDidFillOnce = false;
        this.currentSize = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    final boolean containsKey(SolverVariable solverVariable) {
        if (this.mHead != -1) {
            int n = this.mHead;
            for (int i = 0; n != -1 && i < this.currentSize; ++i) {
                if (this.mArrayIndices[n] == solverVariable.id) {
                    return true;
                }
                n = this.mArrayNextIndices[n];
            }
        }
        return false;
    }

    void divideByAmount(float f) {
        int n = this.mHead;
        for (int i = 0; n != -1 && i < this.currentSize; ++i) {
            float[] arrf = this.mArrayValues;
            arrf[n] = arrf[n] / f;
            n = this.mArrayNextIndices[n];
        }
    }

    public final float get(SolverVariable solverVariable) {
        int n = this.mHead;
        for (int i = 0; n != -1 && i < this.currentSize; ++i) {
            if (this.mArrayIndices[n] == solverVariable.id) {
                return this.mArrayValues[n];
            }
            n = this.mArrayNextIndices[n];
        }
        return 0.0f;
    }

    final SolverVariable getVariable(int n) {
        int n2 = this.mHead;
        for (int i = 0; n2 != -1 && i < this.currentSize; ++i) {
            if (i == n) {
                return this.mCache.mIndexedVariables[this.mArrayIndices[n2]];
            }
            n2 = this.mArrayNextIndices[n2];
        }
        return null;
    }

    final float getVariableValue(int n) {
        int n2 = this.mHead;
        for (int i = 0; n2 != -1 && i < this.currentSize; ++i) {
            if (i == n) {
                return this.mArrayValues[n2];
            }
            n2 = this.mArrayNextIndices[n2];
        }
        return 0.0f;
    }

    void invert() {
        int n = this.mHead;
        for (int i = 0; n != -1 && i < this.currentSize; ++i) {
            float[] arrf = this.mArrayValues;
            arrf[n] = arrf[n] * -1.0f;
            n = this.mArrayNextIndices[n];
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    SolverVariable pickPivotCandidate() {
        SolverVariable solverVariable = null;
        SolverVariable solverVariable2 = null;
        int n = this.mHead;
        for (int i = 0; n != -1 && i < this.currentSize; ++i) {
            SolverVariable solverVariable3;
            SolverVariable solverVariable4;
            block12: {
                SolverVariable solverVariable5;
                block14: {
                    float f;
                    block13: {
                        float f2 = this.mArrayValues[n];
                        if (f2 < 0.0f) {
                            f = f2;
                            if (f2 > -0.001f) {
                                this.mArrayValues[n] = 0.0f;
                                f = 0.0f;
                            }
                        } else {
                            f = f2;
                            if (f2 < 0.001f) {
                                this.mArrayValues[n] = 0.0f;
                                f = 0.0f;
                            }
                        }
                        solverVariable3 = solverVariable;
                        solverVariable4 = solverVariable2;
                        if (f == 0.0f) break block12;
                        solverVariable5 = this.mCache.mIndexedVariables[this.mArrayIndices[n]];
                        if (solverVariable5.mType != SolverVariable.Type.UNRESTRICTED) break block13;
                        if (f < 0.0f) {
                            return solverVariable5;
                        }
                        solverVariable3 = solverVariable;
                        solverVariable4 = solverVariable2;
                        if (solverVariable2 == null) {
                            solverVariable4 = solverVariable5;
                            solverVariable3 = solverVariable;
                        }
                        break block12;
                    }
                    solverVariable3 = solverVariable;
                    solverVariable4 = solverVariable2;
                    if (!(f < 0.0f)) break block12;
                    if (solverVariable == null) break block14;
                    solverVariable3 = solverVariable;
                    solverVariable4 = solverVariable2;
                    if (solverVariable5.strength >= solverVariable.strength) break block12;
                }
                solverVariable3 = solverVariable5;
                solverVariable4 = solverVariable2;
            }
            n = this.mArrayNextIndices[n];
            solverVariable = solverVariable3;
            solverVariable2 = solverVariable4;
        }
        if (solverVariable2 != null) {
            return solverVariable2;
        }
        return solverVariable;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public final void put(SolverVariable var1_1, float var2_2) {
        if (var2_2 == 0.0f) {
            this.remove(var1_1);
            return;
        }
        if (this.mHead == -1) {
            this.mHead = 0;
            this.mArrayValues[this.mHead] = var2_2;
            this.mArrayIndices[this.mHead] = var1_1.id;
            this.mArrayNextIndices[this.mHead] = -1;
            ++this.currentSize;
            if (this.mDidFillOnce != false) return;
            ++this.mLast;
            return;
        }
        var3_3 = this.mHead;
        var6_4 = -1;
        for (var4_5 = 0; var3_3 != -1 && var4_5 < this.currentSize; ++var4_5) {
            if (this.mArrayIndices[var3_3] == var1_1.id) {
                this.mArrayValues[var3_3] = var2_2;
                return;
            }
            if (this.mArrayIndices[var3_3] < var1_1.id) {
                var6_4 = var3_3;
            }
            var3_3 = this.mArrayNextIndices[var3_3];
        }
        var3_3 = this.mLast + 1;
        if (this.mDidFillOnce) {
            var3_3 = this.mArrayIndices[this.mLast] == -1 ? this.mLast : this.mArrayIndices.length;
        }
        var4_5 = var3_3;
        if (var3_3 < this.mArrayIndices.length) ** GOTO lbl37
        var4_5 = var3_3;
        if (this.currentSize >= this.mArrayIndices.length) ** GOTO lbl37
        var5_6 = 0;
        do {
            block15: {
                block14: {
                    var4_5 = var3_3;
                    if (var5_6 >= this.mArrayIndices.length) break block14;
                    if (this.mArrayIndices[var5_6] != -1) break block15;
                    var4_5 = var5_6;
                }
                var3_3 = var4_5;
                if (var4_5 >= this.mArrayIndices.length) {
                    var3_3 = this.mArrayIndices.length;
                    this.ROW_SIZE *= 2;
                    this.mDidFillOnce = false;
                    this.mLast = var3_3 - 1;
                    this.mArrayValues = Arrays.copyOf(this.mArrayValues, this.ROW_SIZE);
                    this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
                    this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
                }
                this.mArrayIndices[var3_3] = var1_1.id;
                this.mArrayValues[var3_3] = var2_2;
                if (var6_4 != -1) {
                    this.mArrayNextIndices[var3_3] = this.mArrayNextIndices[var6_4];
                    this.mArrayNextIndices[var6_4] = var3_3;
                } else {
                    this.mArrayNextIndices[var3_3] = this.mHead;
                    this.mHead = var3_3;
                }
                ++this.currentSize;
                if (!this.mDidFillOnce) {
                    ++this.mLast;
                }
                if (this.currentSize < this.mArrayIndices.length) return;
                this.mDidFillOnce = true;
                return;
            }
            ++var5_6;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final float remove(SolverVariable solverVariable) {
        if (this.candidate == solverVariable) {
            this.candidate = null;
        }
        if (this.mHead != -1) {
            int n = this.mHead;
            int n2 = -1;
            for (int i = 0; n != -1 && i < this.currentSize; ++i) {
                int n3 = this.mArrayIndices[n];
                if (n3 == solverVariable.id) {
                    if (n == this.mHead) {
                        this.mHead = this.mArrayNextIndices[n];
                    } else {
                        this.mArrayNextIndices[n2] = this.mArrayNextIndices[n];
                    }
                    this.mCache.mIndexedVariables[n3].removeClientEquation(this.mRow);
                    --this.currentSize;
                    this.mArrayIndices[n] = -1;
                    if (this.mDidFillOnce) {
                        this.mLast = n;
                    }
                    return this.mArrayValues[n];
                }
                n2 = n;
                n = this.mArrayNextIndices[n];
            }
        }
        return 0.0f;
    }

    public String toString() {
        String string2 = "";
        int n = this.mHead;
        for (int i = 0; n != -1 && i < this.currentSize; ++i) {
            string2 = string2 + " -> ";
            string2 = string2 + this.mArrayValues[n] + " : ";
            string2 = string2 + this.mCache.mIndexedVariables[this.mArrayIndices[n]];
            n = this.mArrayNextIndices[n];
        }
        return string2;
    }

    void updateClientEquations(ArrayRow arrayRow) {
        int n = this.mHead;
        for (int i = 0; n != -1 && i < this.currentSize; ++i) {
            this.mCache.mIndexedVariables[this.mArrayIndices[n]].addClientEquation(arrayRow);
            n = this.mArrayNextIndices[n];
        }
    }

    void updateFromRow(ArrayRow arrayRow, ArrayRow arrayRow2) {
        int n = this.mHead;
        int n2 = 0;
        while (n != -1 && n2 < this.currentSize) {
            if (this.mArrayIndices[n] == arrayRow2.variable.id) {
                float f = this.mArrayValues[n];
                this.remove(arrayRow2.variable);
                ArrayLinkedVariables arrayLinkedVariables = arrayRow2.variables;
                n = arrayLinkedVariables.mHead;
                for (n2 = 0; n != -1 && n2 < arrayLinkedVariables.currentSize; ++n2) {
                    this.add(this.mCache.mIndexedVariables[arrayLinkedVariables.mArrayIndices[n]], arrayLinkedVariables.mArrayValues[n] * f);
                    n = arrayLinkedVariables.mArrayNextIndices[n];
                }
                arrayRow.constantValue += arrayRow2.constantValue * f;
                arrayRow2.variable.removeClientEquation(arrayRow);
                n = this.mHead;
                n2 = 0;
                continue;
            }
            n = this.mArrayNextIndices[n];
            ++n2;
        }
    }

    void updateFromSystem(ArrayRow arrayRow, ArrayRow[] arrarrayRow) {
        int n = this.mHead;
        int n2 = 0;
        while (n != -1 && n2 < this.currentSize) {
            Object object = this.mCache.mIndexedVariables[this.mArrayIndices[n]];
            if (((SolverVariable)object).definitionId != -1) {
                float f = this.mArrayValues[n];
                this.remove((SolverVariable)object);
                object = arrarrayRow[((SolverVariable)object).definitionId];
                if (!((ArrayRow)object).isSimpleDefinition) {
                    ArrayLinkedVariables arrayLinkedVariables = ((ArrayRow)object).variables;
                    n = arrayLinkedVariables.mHead;
                    for (n2 = 0; n != -1 && n2 < arrayLinkedVariables.currentSize; ++n2) {
                        this.add(this.mCache.mIndexedVariables[arrayLinkedVariables.mArrayIndices[n]], arrayLinkedVariables.mArrayValues[n] * f);
                        n = arrayLinkedVariables.mArrayNextIndices[n];
                    }
                }
                arrayRow.constantValue += ((ArrayRow)object).constantValue * f;
                ((ArrayRow)object).variable.removeClientEquation(arrayRow);
                n = this.mHead;
                n2 = 0;
                continue;
            }
            n = this.mArrayNextIndices[n];
            ++n2;
        }
    }
}

