/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.handshake;

import com.mixpanel.android.java_websocket.handshake.HandshakedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;

public class HandshakeImpl1Server
extends HandshakedataImpl1
implements ServerHandshakeBuilder {
    private short httpstatus;
    private String httpstatusmessage;

    @Override
    public String getHttpStatusMessage() {
        return this.httpstatusmessage;
    }

    @Override
    public void setHttpStatus(short s) {
        this.httpstatus = s;
    }

    @Override
    public void setHttpStatusMessage(String string2) {
        this.httpstatusmessage = string2;
    }
}

