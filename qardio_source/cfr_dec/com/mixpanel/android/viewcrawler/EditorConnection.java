/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.viewcrawler;

import com.mixpanel.android.java_websocket.client.WebSocketClient;
import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.drafts.Draft_17;
import com.mixpanel.android.java_websocket.exceptions.NotSendableException;
import com.mixpanel.android.java_websocket.exceptions.WebsocketNotConnectedException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.util.MPLog;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class EditorConnection {
    private static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.allocate(0);
    private final EditorClient mClient;
    private final Editor mService;
    private final URI mURI;

    public EditorConnection(URI uRI, Editor editor, Socket socket) throws EditorConnectionException {
        this.mService = editor;
        this.mURI = uRI;
        try {
            this.mClient = new EditorClient(uRI, 5000, socket);
            this.mClient.connectBlocking();
            return;
        }
        catch (InterruptedException interruptedException) {
            throw new EditorConnectionException(interruptedException);
        }
    }

    public BufferedOutputStream getBufferedOutputStream() {
        return new BufferedOutputStream(new WebSocketOutputStream());
    }

    public boolean isConnected() {
        return this.mClient.isOpen();
    }

    public boolean isValid() {
        return !this.mClient.isClosed() && !this.mClient.isClosing() && !this.mClient.isFlushAndClose();
    }

    public static interface Editor {
        public void bindEvents(JSONObject var1);

        public void cleanup();

        public void clearEdits(JSONObject var1);

        public void performEdit(JSONObject var1);

        public void sendDeviceInfo();

        public void sendSnapshot(JSONObject var1);

        public void setTweaks(JSONObject var1);
    }

    private class EditorClient
    extends WebSocketClient {
        public EditorClient(URI uRI, int n, Socket socket) throws InterruptedException {
            super(uRI, new Draft_17(), null, n);
            this.setSocket(socket);
        }

        @Override
        public void onClose(int n, String string2, boolean bl) {
            MPLog.v("MixpanelAPI.EditorCnctn", "WebSocket closed. Code: " + n + ", reason: " + string2 + "\nURI: " + EditorConnection.this.mURI);
            EditorConnection.this.mService.cleanup();
        }

        @Override
        public void onError(Exception exception) {
            if (exception != null && exception.getMessage() != null) {
                MPLog.e("MixpanelAPI.EditorCnctn", "Websocket Error: " + exception.getMessage());
                return;
            }
            MPLog.e("MixpanelAPI.EditorCnctn", "Unknown websocket error occurred");
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onMessage(String string2) {
            String string3;
            JSONObject jSONObject;
            MPLog.v("MixpanelAPI.EditorCnctn", "Received message from editor:\n" + string2);
            try {
                jSONObject = new JSONObject(string2);
                string3 = jSONObject.getString("type");
                if (string3.equals("device_info_request")) {
                    EditorConnection.this.mService.sendDeviceInfo();
                    return;
                }
                if (string3.equals("snapshot_request")) {
                    EditorConnection.this.mService.sendSnapshot(jSONObject);
                    return;
                }
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.EditorCnctn", "Bad JSON received:" + string2, jSONException);
                return;
            }
            {
                if (string3.equals("change_request")) {
                    EditorConnection.this.mService.performEdit(jSONObject);
                    return;
                }
                if (string3.equals("event_binding_request")) {
                    EditorConnection.this.mService.bindEvents(jSONObject);
                    return;
                }
                if (string3.equals("clear_request")) {
                    EditorConnection.this.mService.clearEdits(jSONObject);
                    return;
                }
                if (string3.equals("tweak_request")) {
                    EditorConnection.this.mService.setTweaks(jSONObject);
                }
                return;
            }
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            MPLog.v("MixpanelAPI.EditorCnctn", "Websocket connected");
        }
    }

    public class EditorConnectionException
    extends IOException {
        public EditorConnectionException(Throwable throwable) {
            super(throwable.getMessage());
        }
    }

    private class WebSocketOutputStream
    extends OutputStream {
        private WebSocketOutputStream() {
        }

        @Override
        public void close() throws EditorConnectionException {
            try {
                EditorConnection.this.mClient.sendFragmentedFrame(Framedata.Opcode.TEXT, EMPTY_BYTE_BUFFER, true);
                return;
            }
            catch (WebsocketNotConnectedException websocketNotConnectedException) {
                throw new EditorConnectionException(websocketNotConnectedException);
            }
            catch (NotSendableException notSendableException) {
                throw new EditorConnectionException(notSendableException);
            }
        }

        @Override
        public void write(int n) throws EditorConnectionException {
            this.write(new byte[]{(byte)n}, 0, 1);
        }

        @Override
        public void write(byte[] arrby) throws EditorConnectionException {
            this.write(arrby, 0, arrby.length);
        }

        @Override
        public void write(byte[] object, int n, int n2) throws EditorConnectionException {
            object = ByteBuffer.wrap(object, n, n2);
            try {
                EditorConnection.this.mClient.sendFragmentedFrame(Framedata.Opcode.TEXT, (ByteBuffer)object, false);
                return;
            }
            catch (WebsocketNotConnectedException websocketNotConnectedException) {
                throw new EditorConnectionException(websocketNotConnectedException);
            }
            catch (NotSendableException notSendableException) {
                throw new EditorConnectionException(notSendableException);
            }
        }
    }

}

