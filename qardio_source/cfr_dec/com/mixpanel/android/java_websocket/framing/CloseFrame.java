/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.framing;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidFrameException;
import com.mixpanel.android.java_websocket.framing.Framedata;

public interface CloseFrame
extends Framedata {
    public int getCloseCode() throws InvalidFrameException;

    public String getMessage() throws InvalidDataException;
}

