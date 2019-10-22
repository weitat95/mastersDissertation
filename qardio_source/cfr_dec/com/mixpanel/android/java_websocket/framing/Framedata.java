/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.framing;

import java.nio.ByteBuffer;

public interface Framedata {
    public Opcode getOpcode();

    public ByteBuffer getPayloadData();

    public boolean getTransfereMasked();

    public boolean isFin();

    public static enum Opcode {
        CONTINUOUS,
        TEXT,
        BINARY,
        PING,
        PONG,
        CLOSING;

    }

}

