/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.handshake;

import com.mixpanel.android.java_websocket.handshake.HandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;

public interface ServerHandshakeBuilder
extends HandshakeBuilder,
ServerHandshake {
    public void setHttpStatus(short var1);

    public void setHttpStatusMessage(String var1);
}

