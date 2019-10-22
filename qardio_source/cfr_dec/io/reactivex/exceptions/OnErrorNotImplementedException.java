/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.exceptions;

public final class OnErrorNotImplementedException
extends RuntimeException {
    /*
     * Enabled aggressive block sorting
     */
    public OnErrorNotImplementedException(Throwable throwable) {
        String string2 = throwable != null ? throwable.getMessage() : null;
        if (throwable == null) {
            throwable = new NullPointerException();
        }
        super(string2, throwable);
    }
}

