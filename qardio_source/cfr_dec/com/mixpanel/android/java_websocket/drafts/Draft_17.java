/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.drafts;

import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.drafts.Draft_10;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;

public class Draft_17
extends Draft_10 {
    @Override
    public Draft.HandshakeState acceptHandshakeAsServer(ClientHandshake clientHandshake) throws InvalidHandshakeException {
        if (Draft_17.readVersion(clientHandshake) == 13) {
            return Draft.HandshakeState.MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft copyInstance() {
        return new Draft_17();
    }

    @Override
    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder clientHandshakeBuilder) {
        super.postProcessHandshakeRequestAsClient(clientHandshakeBuilder);
        clientHandshakeBuilder.put("Sec-WebSocket-Version", "13");
        return clientHandshakeBuilder;
    }
}

