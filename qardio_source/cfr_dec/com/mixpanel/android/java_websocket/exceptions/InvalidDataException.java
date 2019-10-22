/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.exceptions;

public class InvalidDataException
extends Exception {
    private int closecode;

    public InvalidDataException(int n) {
        this.closecode = n;
    }

    public InvalidDataException(int n, String string2) {
        super(string2);
        this.closecode = n;
    }

    public InvalidDataException(int n, Throwable throwable) {
        super(throwable);
        this.closecode = n;
    }

    public int getCloseCode() {
        return this.closecode;
    }
}

