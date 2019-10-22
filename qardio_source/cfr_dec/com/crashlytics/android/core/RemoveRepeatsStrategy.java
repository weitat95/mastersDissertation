/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.StackTraceTrimmingStrategy;
import java.util.HashMap;

class RemoveRepeatsStrategy
implements StackTraceTrimmingStrategy {
    private final int maxRepetitions;

    public RemoveRepeatsStrategy() {
        this(1);
    }

    public RemoveRepeatsStrategy(int n) {
        this.maxRepetitions = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isRepeatingSequence(StackTraceElement[] arrstackTraceElement, int n, int n2) {
        int n3 = n2 - n;
        if (n2 + n3 <= arrstackTraceElement.length) {
            int n4 = 0;
            do {
                if (n4 >= n3) {
                    return true;
                }
                if (!arrstackTraceElement[n + n4].equals(arrstackTraceElement[n2 + n4])) break;
                ++n4;
            } while (true);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static StackTraceElement[] trimRepeats(StackTraceElement[] arrstackTraceElement, int n) {
        HashMap<StackTraceElement, Integer> hashMap = new HashMap<StackTraceElement, Integer>();
        StackTraceElement[] arrstackTraceElement2 = new StackTraceElement[arrstackTraceElement.length];
        int n2 = 0;
        int n3 = 1;
        int n4 = 0;
        do {
            int n5;
            if (n4 >= arrstackTraceElement.length) {
                arrstackTraceElement = new StackTraceElement[n2];
                System.arraycopy(arrstackTraceElement2, 0, arrstackTraceElement, 0, arrstackTraceElement.length);
                return arrstackTraceElement;
            }
            StackTraceElement stackTraceElement = arrstackTraceElement[n4];
            Integer n6 = (Integer)hashMap.get(stackTraceElement);
            if (n6 == null || !RemoveRepeatsStrategy.isRepeatingSequence(arrstackTraceElement, n6, n4)) {
                n3 = 1;
                arrstackTraceElement2[n2] = arrstackTraceElement[n4];
                n5 = n2 + 1;
                n2 = n4;
            } else {
                int n7 = n4 - n6;
                int n8 = n3;
                n5 = n2;
                if (n3 < n) {
                    System.arraycopy(arrstackTraceElement, n4, arrstackTraceElement2, n2, n7);
                    n5 = n2 + n7;
                    n8 = n3 + 1;
                }
                n2 = n4 + (n7 - 1);
                n3 = n8;
            }
            hashMap.put(stackTraceElement, n4);
            n4 = n2 + 1;
            n2 = n5;
        } while (true);
    }

    @Override
    public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] arrstackTraceElement) {
        StackTraceElement[] arrstackTraceElement2 = RemoveRepeatsStrategy.trimRepeats(arrstackTraceElement, this.maxRepetitions);
        if (arrstackTraceElement2.length < arrstackTraceElement.length) {
            return arrstackTraceElement2;
        }
        return arrstackTraceElement;
    }
}

