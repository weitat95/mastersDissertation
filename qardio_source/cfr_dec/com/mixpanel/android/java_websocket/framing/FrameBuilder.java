/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.framing;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import java.nio.ByteBuffer;

public interface FrameBuilder
extends Framedata {
    public void setFin(boolean var1);

    public void setOptcode(Framedata.Opcode var1);

    public void setPayload(ByteBuffer var1) throws InvalidDataException;
}

