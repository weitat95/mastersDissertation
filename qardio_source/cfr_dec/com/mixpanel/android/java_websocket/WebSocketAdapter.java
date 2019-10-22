/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket;

import com.mixpanel.android.java_websocket.WebSocket;
import com.mixpanel.android.java_websocket.WebSocketListener;
import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.FramedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.HandshakeImpl1Server;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import java.net.InetSocketAddress;

public abstract class WebSocketAdapter
implements WebSocketListener {
    @Override
    public String getFlashPolicy(WebSocket object) throws InvalidDataException {
        if ((object = object.getLocalSocketAddress()) == null) {
            throw new InvalidHandshakeException("socket not bound");
        }
        StringBuffer stringBuffer = new StringBuffer(90);
        stringBuffer.append("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"");
        stringBuffer.append(((InetSocketAddress)object).getPort());
        stringBuffer.append("\" /></cross-domain-policy>\u0000");
        return stringBuffer.toString();
    }

    @Override
    public void onWebsocketHandshakeReceivedAsClient(WebSocket webSocket, ClientHandshake clientHandshake, ServerHandshake serverHandshake) throws InvalidDataException {
    }

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket webSocket, Draft draft, ClientHandshake clientHandshake) throws InvalidDataException {
        return new HandshakeImpl1Server();
    }

    @Override
    public void onWebsocketHandshakeSentAsClient(WebSocket webSocket, ClientHandshake clientHandshake) throws InvalidDataException {
    }

    @Override
    public void onWebsocketMessageFragment(WebSocket webSocket, Framedata framedata) {
    }

    @Override
    public void onWebsocketPing(WebSocket webSocket, Framedata framedata) {
        framedata = new FramedataImpl1(framedata);
        ((FramedataImpl1)framedata).setOptcode(Framedata.Opcode.PONG);
        webSocket.sendFrame(framedata);
    }

    @Override
    public void onWebsocketPong(WebSocket webSocket, Framedata framedata) {
    }
}

