/*
 * Decompiled with CFR 0.147.
 */
package io.realm.exceptions;

import io.realm.internal.Keep;

@Keep
public final class RealmException
extends RuntimeException {
    public RealmException(String string2) {
        super(string2);
    }

    public RealmException(String string2, Throwable throwable) {
        super(string2, throwable);
    }
}

