/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket;

import com.mixpanel.android.java_websocket.framing.Framedata;
import java.net.InetSocketAddress;

public interface WebSocket {
    public InetSocketAddress getLocalSocketAddress();

    public void sendFrame(Framedata var1);

    public static enum READYSTATE {
        NOT_YET_CONNECTED,
        CONNECTING,
        OPEN,
        CLOSING,
        CLOSED;

    }

    public static enum Role {
        CLIENT,
        SERVER;

    }

}

