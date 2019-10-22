/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.mixpanel.android.java_websocket.client;

import android.annotation.SuppressLint;
import com.mixpanel.android.java_websocket.WebSocket;
import com.mixpanel.android.java_websocket.WebSocketAdapter;
import com.mixpanel.android.java_websocket.WebSocketImpl;
import com.mixpanel.android.java_websocket.WebSocketListener;
import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeImpl1Client;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.HandshakedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

@SuppressLint(value={"Assert"})
public abstract class WebSocketClient
extends WebSocketAdapter
implements WebSocket,
Runnable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private CountDownLatch closeLatch;
    private CountDownLatch connectLatch;
    private int connectTimeout = 0;
    private Draft draft;
    private WebSocketImpl engine = null;
    private Map<String, String> headers;
    private InputStream istream;
    private OutputStream ostream;
    private Proxy proxy = Proxy.NO_PROXY;
    private Socket socket = null;
    protected URI uri = null;
    private Thread writeThread;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !WebSocketClient.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public WebSocketClient(URI uRI, Draft draft, Map<String, String> map, int n) {
        this.connectLatch = new CountDownLatch(1);
        this.closeLatch = new CountDownLatch(1);
        if (uRI == null) {
            throw new IllegalArgumentException();
        }
        if (draft == null) {
            throw new IllegalArgumentException("null as draft is permitted for `WebSocketServer` only!");
        }
        this.uri = uRI;
        this.draft = draft;
        this.headers = map;
        this.connectTimeout = n;
        this.engine = new WebSocketImpl(this, draft);
    }

    private int getPort() {
        String string2;
        block5: {
            int n;
            block4: {
                int n2;
                n = n2 = this.uri.getPort();
                if (n2 != -1) break block4;
                string2 = this.uri.getScheme();
                if (!string2.equals("wss")) break block5;
                n = 443;
            }
            return n;
        }
        if (string2.equals("ws")) {
            return 80;
        }
        throw new RuntimeException("unkonow scheme" + string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void sendHandshake() throws InvalidHandshakeException {
        Object object = this.uri.getPath();
        String string2 = this.uri.getQuery();
        if (object == null || ((String)object).length() == 0) {
            object = "/";
        }
        Object object2 = object;
        if (string2 != null) {
            object2 = (String)object + "?" + string2;
        }
        int n = this.getPort();
        StringBuilder stringBuilder = new StringBuilder().append(this.uri.getHost());
        object = n != 80 ? ":" + n : "";
        String string3 = stringBuilder.append((String)object).toString();
        object = new HandshakeImpl1Client();
        ((HandshakeImpl1Client)object).setResourceDescriptor((String)object2);
        ((HandshakedataImpl1)object).put("Host", string3);
        if (this.headers != null) {
            for (Map.Entry entry : this.headers.entrySet()) {
                ((HandshakedataImpl1)object).put((String)entry.getKey(), (String)entry.getValue());
            }
        }
        this.engine.startHandshake((ClientHandshakeBuilder)object);
    }

    public void connect() {
        if (this.writeThread != null) {
            throw new IllegalStateException("WebSocketClient objects are not reuseable");
        }
        this.writeThread = new Thread(this);
        this.writeThread.start();
    }

    public boolean connectBlocking() throws InterruptedException {
        this.connect();
        this.connectLatch.await();
        return this.engine.isOpen();
    }

    @Override
    public InetSocketAddress getLocalSocketAddress() {
        return this.engine.getLocalSocketAddress();
    }

    @Override
    public InetSocketAddress getLocalSocketAddress(WebSocket webSocket) {
        if (this.socket != null) {
            return (InetSocketAddress)this.socket.getLocalSocketAddress();
        }
        return null;
    }

    public boolean isClosed() {
        return this.engine.isClosed();
    }

    public boolean isClosing() {
        return this.engine.isClosing();
    }

    public boolean isFlushAndClose() {
        return this.engine.isFlushAndClose();
    }

    public boolean isOpen() {
        return this.engine.isOpen();
    }

    public abstract void onClose(int var1, String var2, boolean var3);

    public void onCloseInitiated(int n, String string2) {
    }

    public void onClosing(int n, String string2, boolean bl) {
    }

    public abstract void onError(Exception var1);

    public void onFragment(Framedata framedata) {
    }

    public abstract void onMessage(String var1);

    public void onMessage(ByteBuffer byteBuffer) {
    }

    public abstract void onOpen(ServerHandshake var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void onWebsocketClose(WebSocket webSocket, int n, String string2, boolean bl) {
        this.connectLatch.countDown();
        this.closeLatch.countDown();
        if (this.writeThread != null) {
            this.writeThread.interrupt();
        }
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        }
        catch (IOException iOException) {
            this.onWebsocketError(this, iOException);
        }
        this.onClose(n, string2, bl);
    }

    @Override
    public void onWebsocketCloseInitiated(WebSocket webSocket, int n, String string2) {
        this.onCloseInitiated(n, string2);
    }

    @Override
    public void onWebsocketClosing(WebSocket webSocket, int n, String string2, boolean bl) {
        this.onClosing(n, string2, bl);
    }

    @Override
    public final void onWebsocketError(WebSocket webSocket, Exception exception) {
        this.onError(exception);
    }

    @Override
    public final void onWebsocketMessage(WebSocket webSocket, String string2) {
        this.onMessage(string2);
    }

    @Override
    public final void onWebsocketMessage(WebSocket webSocket, ByteBuffer byteBuffer) {
        this.onMessage(byteBuffer);
    }

    @Override
    public void onWebsocketMessageFragment(WebSocket webSocket, Framedata framedata) {
        this.onFragment(framedata);
    }

    @Override
    public final void onWebsocketOpen(WebSocket webSocket, Handshakedata handshakedata) {
        this.connectLatch.countDown();
        this.onOpen((ServerHandshake)handshakedata);
    }

    @Override
    public final void onWriteDemand(WebSocket webSocket) {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        block12: {
            if (this.socket == null) {
                this.socket = new Socket(this.proxy);
            } else if (this.socket.isClosed()) {
                throw new IOException();
            }
            if (!this.socket.isBound()) {
                this.socket.connect(new InetSocketAddress(this.uri.getHost(), this.getPort()), this.connectTimeout);
            }
            this.istream = this.socket.getInputStream();
            this.ostream = this.socket.getOutputStream();
            this.sendHandshake();
            this.writeThread = new Thread(new WebsocketWriteThread());
            this.writeThread.start();
            var2_1 = new byte[WebSocketImpl.RCVBUF];
            while (!this.isClosed() && (var1_5 = this.istream.read(var2_1)) != -1) {
                this.engine.decode(ByteBuffer.wrap(var2_1, 0, var1_5));
            }
            break block12;
            catch (Exception var2_3) {
                this.onWebsocketError(this.engine, var2_3);
                this.engine.closeConnection(-1, var2_3.getMessage());
            }
            return;
        }
        try {
            this.engine.eot();
            ** GOTO lbl34
        }
        catch (IOException var2_2) {
            block13: {
                this.engine.eot();
                break block13;
                catch (RuntimeException var2_4) {
                    this.onError(var2_4);
                    this.engine.closeConnection(1006, var2_4.getMessage());
                }
            }
            if (WebSocketClient.$assertionsDisabled != false) return;
            if (this.socket.isClosed() != false) return;
            throw new AssertionError();
        }
    }

    public void sendFragmentedFrame(Framedata.Opcode opcode, ByteBuffer byteBuffer, boolean bl) {
        this.engine.sendFragmentedFrame(opcode, byteBuffer, bl);
    }

    @Override
    public void sendFrame(Framedata framedata) {
        this.engine.sendFrame(framedata);
    }

    public void setSocket(Socket socket) {
        if (this.socket != null) {
            throw new IllegalStateException("socket has already been set");
        }
        this.socket = socket;
    }

    private class WebsocketWriteThread
    implements Runnable {
        private WebsocketWriteThread() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Thread.currentThread().setName("WebsocketWriteThread");
            try {
                while (!Thread.interrupted()) {
                    ByteBuffer byteBuffer = WebSocketClient.access$100((WebSocketClient)WebSocketClient.this).outQueue.take();
                    WebSocketClient.this.ostream.write(byteBuffer.array(), 0, byteBuffer.limit());
                    WebSocketClient.this.ostream.flush();
                }
                return;
            }
            catch (IOException iOException) {
                WebSocketClient.this.engine.eot();
                return;
            }
            catch (InterruptedException interruptedException) {
                return;
            }
        }
    }

}

