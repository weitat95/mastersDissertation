/*
 * Decompiled with CFR 0.147.
 */
package io.realm.exceptions;

import io.realm.internal.Keep;

@Keep
public final class RealmMigrationNeededException
extends RuntimeException {
    private final String canonicalRealmPath;

    public RealmMigrationNeededException(String string2, String string3) {
        super(string3);
        this.canonicalRealmPath = string2;
    }

    public RealmMigrationNeededException(String string2, String string3, Throwable throwable) {
        super(string3, throwable);
        this.canonicalRealmPath = string2;
    }

    public String getPath() {
        return this.canonicalRealmPath;
    }
}

