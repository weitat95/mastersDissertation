/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.StackTraceTrimmingStrategy;

class TrimmedThrowableData {
    public final TrimmedThrowableData cause;
    public final String className;
    public final String localizedMessage;
    public final StackTraceElement[] stacktrace;

    /*
     * Enabled aggressive block sorting
     */
    public TrimmedThrowableData(Throwable object, StackTraceTrimmingStrategy stackTraceTrimmingStrategy) {
        this.localizedMessage = ((Throwable)object).getLocalizedMessage();
        this.className = object.getClass().getName();
        this.stacktrace = stackTraceTrimmingStrategy.getTrimmedStackTrace(((Throwable)object).getStackTrace());
        object = ((Throwable)object).getCause();
        object = object != null ? new TrimmedThrowableData((Throwable)object, stackTraceTrimmingStrategy) : null;
        this.cause = object;
    }
}

