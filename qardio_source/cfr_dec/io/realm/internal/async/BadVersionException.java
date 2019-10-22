/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal.async;

import io.realm.internal.Keep;

@Keep
public class BadVersionException
extends Exception {
    public BadVersionException(String string2) {
        super(string2);
    }

    public BadVersionException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

