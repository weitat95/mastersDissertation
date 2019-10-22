/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver;

import android.support.constraint.solver.ArrayRow;
import java.util.Arrays;

public class SolverVariable {
    private static int uniqueId = 1;
    public float computedValue;
    int definitionId = -1;
    public int id = -1;
    ArrayRow[] mClientEquations;
    int mClientEquationsCount = 0;
    private String mName;
    Type mType;
    public int strength = 0;
    float[] strengthVector = new float[6];

    public SolverVariable(Type type) {
        this.mClientEquations = new ArrayRow[8];
        this.mType = type;
    }

    void addClientEquation(ArrayRow arrayRow) {
        for (int i = 0; i < this.mClientEquationsCount; ++i) {
            if (this.mClientEquations[i] != arrayRow) continue;
            return;
        }
        if (this.mClientEquationsCount >= this.mClientEquations.length) {
            this.mClientEquations = Arrays.copyOf(this.mClientEquations, this.mClientEquations.length * 2);
        }
        this.mClientEquations[this.mClientEquationsCount] = arrayRow;
        ++this.mClientEquationsCount;
    }

    void clearStrengths() {
        for (int i = 0; i < 6; ++i) {
            this.strengthVector[i] = 0.0f;
        }
    }

    void removeClientEquation(ArrayRow arrayRow) {
        int n = 0;
        do {
            block5: {
                block4: {
                    if (n >= this.mClientEquationsCount) break block4;
                    if (this.mClientEquations[n] != arrayRow) break block5;
                    for (int i = 0; i < this.mClientEquationsCount - n - 1; ++i) {
                        this.mClientEquations[n + i] = this.mClientEquations[n + i + 1];
                    }
                    --this.mClientEquationsCount;
                }
                return;
            }
            ++n;
        } while (true);
    }

    public void reset() {
        this.mName = null;
        this.mType = Type.UNKNOWN;
        this.strength = 0;
        this.id = -1;
        this.definitionId = -1;
        this.computedValue = 0.0f;
        this.mClientEquationsCount = 0;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    /*
     * Enabled aggressive block sorting
     */
    String strengthsToString() {
        String string2 = this + "[";
        int n = 0;
        while (n < this.strengthVector.length) {
            string2 = string2 + this.strengthVector[n];
            string2 = n < this.strengthVector.length - 1 ? string2 + ", " : string2 + "] ";
            ++n;
        }
        return string2;
    }

    public String toString() {
        return "" + this.mName;
    }

    public static enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN;

    }

}

