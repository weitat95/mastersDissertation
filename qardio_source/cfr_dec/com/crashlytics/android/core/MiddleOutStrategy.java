/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.StackTraceTrimmingStrategy;

class MiddleOutStrategy
implements StackTraceTrimmingStrategy {
    private final int trimmedSize;

    public MiddleOutStrategy(int n) {
        this.trimmedSize = n;
    }

    @Override
    public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] arrstackTraceElement) {
        if (arrstackTraceElement.length <= this.trimmedSize) {
            return arrstackTraceElement;
        }
        int n = this.trimmedSize / 2;
        int n2 = this.trimmedSize - n;
        StackTraceElement[] arrstackTraceElement2 = new StackTraceElement[this.trimmedSize];
        System.arraycopy(arrstackTraceElement, 0, arrstackTraceElement2, 0, n2);
        System.arraycopy(arrstackTraceElement, arrstackTraceElement.length - n, arrstackTraceElement2, n2, n);
        return arrstackTraceElement2;
    }
}

