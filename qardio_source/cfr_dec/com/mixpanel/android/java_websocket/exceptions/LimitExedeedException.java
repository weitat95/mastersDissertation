/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.exceptions;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;

public class LimitExedeedException
extends InvalidDataException {
    public LimitExedeedException() {
        super(1009);
    }

    public LimitExedeedException(String string2) {
        super(1009, string2);
    }
}

