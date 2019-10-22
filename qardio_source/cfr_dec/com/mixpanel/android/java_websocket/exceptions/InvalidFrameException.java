/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.exceptions;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;

public class InvalidFrameException
extends InvalidDataException {
    public InvalidFrameException() {
        super(1002);
    }

    public InvalidFrameException(String string2) {
        super(1002, string2);
    }

    public InvalidFrameException(Throwable throwable) {
        super(1002, throwable);
    }
}

