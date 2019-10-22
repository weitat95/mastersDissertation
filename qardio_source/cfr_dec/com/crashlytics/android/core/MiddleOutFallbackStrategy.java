/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.MiddleOutStrategy;
import com.crashlytics.android.core.StackTraceTrimmingStrategy;

class MiddleOutFallbackStrategy
implements StackTraceTrimmingStrategy {
    private final int maximumStackSize;
    private final MiddleOutStrategy middleOutStrategy;
    private final StackTraceTrimmingStrategy[] trimmingStrategies;

    public MiddleOutFallbackStrategy(int n, StackTraceTrimmingStrategy ... arrstackTraceTrimmingStrategy) {
        this.maximumStackSize = n;
        this.trimmingStrategies = arrstackTraceTrimmingStrategy;
        this.middleOutStrategy = new MiddleOutStrategy(n);
    }

    @Override
    public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] arrstackTraceElement) {
        if (arrstackTraceElement.length <= this.maximumStackSize) {
            return arrstackTraceElement;
        }
        StackTraceElement[] arrstackTraceElement2 = arrstackTraceElement;
        StackTraceTrimmingStrategy[] arrstackTraceTrimmingStrategy = this.trimmingStrategies;
        int n = arrstackTraceTrimmingStrategy.length;
        int n2 = 0;
        do {
            StackTraceTrimmingStrategy stackTraceTrimmingStrategy;
            block7: {
                block6: {
                    if (n2 >= n) break block6;
                    stackTraceTrimmingStrategy = arrstackTraceTrimmingStrategy[n2];
                    if (arrstackTraceElement2.length > this.maximumStackSize) break block7;
                }
                arrstackTraceElement = arrstackTraceElement2;
                if (arrstackTraceElement2.length > this.maximumStackSize) {
                    arrstackTraceElement = this.middleOutStrategy.getTrimmedStackTrace(arrstackTraceElement2);
                }
                return arrstackTraceElement;
            }
            arrstackTraceElement2 = stackTraceTrimmingStrategy.getTrimmedStackTrace(arrstackTraceElement);
            ++n2;
        } while (true);
    }
}

