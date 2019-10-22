/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.handshake;

import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakedataImpl1;

public class HandshakeImpl1Client
extends HandshakedataImpl1
implements ClientHandshakeBuilder {
    private String resourceDescriptor = "*";

    @Override
    public String getResourceDescriptor() {
        return this.resourceDescriptor;
    }

    @Override
    public void setResourceDescriptor(String string2) throws IllegalArgumentException {
        if (string2 == null) {
            throw new IllegalArgumentException("http resource descriptor must not be null");
        }
        this.resourceDescriptor = string2;
    }
}

