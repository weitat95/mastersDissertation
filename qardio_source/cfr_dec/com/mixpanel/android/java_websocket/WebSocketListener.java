/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket;

import com.mixpanel.android.java_websocket.WebSocket;
import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public interface WebSocketListener {
    public String getFlashPolicy(WebSocket var1) throws InvalidDataException;

    public InetSocketAddress getLocalSocketAddress(WebSocket var1);

    public void onWebsocketClose(WebSocket var1, int var2, String var3, boolean var4);

    public void onWebsocketCloseInitiated(WebSocket var1, int var2, String var3);

    public void onWebsocketClosing(WebSocket var1, int var2, String var3, boolean var4);

    public void onWebsocketError(WebSocket var1, Exception var2);

    public void onWebsocketHandshakeReceivedAsClient(WebSocket var1, ClientHandshake var2, ServerHandshake var3) throws InvalidDataException;

    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket var1, Draft var2, ClientHandshake var3) throws InvalidDataException;

    public void onWebsocketHandshakeSentAsClient(WebSocket var1, ClientHandshake var2) throws InvalidDataException;

    public void onWebsocketMessage(WebSocket var1, String var2);

    public void onWebsocketMessage(WebSocket var1, ByteBuffer var2);

    public void onWebsocketMessageFragment(WebSocket var1, Framedata var2);

    public void onWebsocketOpen(WebSocket var1, Handshakedata var2);

    public void onWebsocketPing(WebSocket var1, Framedata var2);

    public void onWebsocketPong(WebSocket var1, Framedata var2);

    public void onWriteDemand(WebSocket var1);
}

