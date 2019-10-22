/*
 * Decompiled with CFR 0.147.
 */
package com.google.firebase.iid;

public final class zzs
extends Exception {
    private final int errorCode;

    public zzs(int n, String string2) {
        super(string2);
        this.errorCode = n;
    }

    public final int getErrorCode() {
        return this.errorCode;
    }
}

