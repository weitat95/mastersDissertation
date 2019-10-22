/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.exceptions;

public class IncompleteHandshakeException
extends RuntimeException {
    private int newsize;

    public IncompleteHandshakeException() {
        this.newsize = 0;
    }

    public IncompleteHandshakeException(int n) {
        this.newsize = n;
    }

    public int getPreferedSize() {
        return this.newsize;
    }
}

