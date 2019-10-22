/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.exceptions;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;

public class InvalidHandshakeException
extends InvalidDataException {
    public InvalidHandshakeException() {
        super(1002);
    }

    public InvalidHandshakeException(String string2) {
        super(1002, string2);
    }
}

