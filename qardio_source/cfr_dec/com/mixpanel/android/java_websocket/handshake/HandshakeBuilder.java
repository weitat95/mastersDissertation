/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.handshake;

import com.mixpanel.android.java_websocket.handshake.Handshakedata;

public interface HandshakeBuilder
extends Handshakedata {
    public void put(String var1, String var2);

    public void setContent(byte[] var1);
}

