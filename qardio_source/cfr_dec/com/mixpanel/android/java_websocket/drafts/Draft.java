/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.drafts;

import com.mixpanel.android.java_websocket.WebSocket;
import com.mixpanel.android.java_websocket.exceptions.IncompleteHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.LimitExedeedException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.FramedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeImpl1Client;
import com.mixpanel.android.java_websocket.handshake.HandshakeImpl1Server;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import com.mixpanel.android.java_websocket.util.Charsetfunctions;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public abstract class Draft {
    public static final byte[] FLASH_POLICY_REQUEST;
    public static int INITIAL_FAMESIZE;
    public static int MAX_FAME_SIZE;
    protected Framedata.Opcode continuousFrameType = null;
    protected WebSocket.Role role = null;

    static {
        MAX_FAME_SIZE = 1000;
        INITIAL_FAMESIZE = 64;
        FLASH_POLICY_REQUEST = Charsetfunctions.utf8Bytes("<policy-file-request/>\u0000");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static ByteBuffer readLine(ByteBuffer var0) {
        var4_1 = ByteBuffer.allocate(var0.remaining());
        var2_2 = 48;
        do lbl-1000:
        // 3 sources
        {
            var3_4 = var2_2;
            if (!var0.hasRemaining()) {
                var0.position(var0.position() - var4_1.position());
                return null;
            }
            var1_3 = var0.get();
            var4_1.put(var1_3);
            var2_2 = var1_3;
            if (var3_4 != 13) ** GOTO lbl-1000
            var2_2 = var1_3;
        } while (var1_3 != 10);
        var4_1.limit(var4_1.position() - 2);
        var4_1.position(0);
        return var4_1;
    }

    public static String readStringLine(ByteBuffer byteBuffer) {
        if ((byteBuffer = Draft.readLine(byteBuffer)) == null) {
            return null;
        }
        return Charsetfunctions.stringAscii(byteBuffer.array(), 0, byteBuffer.limit());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static HandshakeBuilder translateHandshakeHttp(ByteBuffer byteBuffer, WebSocket.Role object) throws InvalidHandshakeException, IncompleteHandshakeException {
        Object object2 = Draft.readStringLine(byteBuffer);
        if (object2 == null) {
            throw new IncompleteHandshakeException(byteBuffer.capacity() + 128);
        }
        if (((String[])(object2 = ((String)object2).split(" ", 3))).length != 3) {
            throw new InvalidHandshakeException();
        }
        if (object == WebSocket.Role.CLIENT) {
            object = new HandshakeImpl1Server();
            ServerHandshakeBuilder serverHandshakeBuilder = (ServerHandshakeBuilder)object;
            serverHandshakeBuilder.setHttpStatus(Short.parseShort(object2[1]));
            serverHandshakeBuilder.setHttpStatusMessage(object2[2]);
        } else {
            object = new HandshakeImpl1Client();
            object.setResourceDescriptor(object2[1]);
        }
        object2 = Draft.readStringLine(byteBuffer);
        while (object2 != null && ((String)object2).length() > 0) {
            if (((Object)(object2 = ((String)object2).split(":", 2))).length != 2) {
                throw new InvalidHandshakeException("not an http header");
            }
            object.put((String)object2[0], ((String)object2[1]).replaceFirst("^ +", ""));
            object2 = Draft.readStringLine(byteBuffer);
        }
        if (object2 == null) {
            throw new IncompleteHandshakeException();
        }
        return object;
    }

    public abstract HandshakeState acceptHandshakeAsClient(ClientHandshake var1, ServerHandshake var2) throws InvalidHandshakeException;

    public abstract HandshakeState acceptHandshakeAsServer(ClientHandshake var1) throws InvalidHandshakeException;

    protected boolean basicAccept(Handshakedata handshakedata) {
        return handshakedata.getFieldValue("Upgrade").equalsIgnoreCase("websocket") && handshakedata.getFieldValue("Connection").toLowerCase(Locale.ENGLISH).contains("upgrade");
    }

    public int checkAlloc(int n) throws LimitExedeedException, InvalidDataException {
        if (n < 0) {
            throw new InvalidDataException(1002, "Negative count");
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<Framedata> continuousFrame(Framedata.Opcode opcode, ByteBuffer byteBuffer, boolean bl) {
        FramedataImpl1 framedataImpl1;
        block3: {
            if (opcode != Framedata.Opcode.BINARY && opcode != Framedata.Opcode.TEXT && opcode != Framedata.Opcode.TEXT) {
                throw new IllegalArgumentException("Only Opcode.BINARY or  Opcode.TEXT are allowed");
            }
            this.continuousFrameType = this.continuousFrameType != null ? Framedata.Opcode.CONTINUOUS : opcode;
            framedataImpl1 = new FramedataImpl1(this.continuousFrameType);
            try {
                framedataImpl1.setPayload(byteBuffer);
                framedataImpl1.setFin(bl);
                if (!bl) break block3;
                this.continuousFrameType = null;
                return Collections.singletonList(framedataImpl1);
            }
            catch (InvalidDataException invalidDataException) {
                throw new RuntimeException(invalidDataException);
            }
        }
        this.continuousFrameType = opcode;
        return Collections.singletonList(framedataImpl1);
    }

    public abstract Draft copyInstance();

    public abstract ByteBuffer createBinaryFrame(Framedata var1);

    public List<ByteBuffer> createHandshake(Handshakedata handshakedata, WebSocket.Role role) {
        return this.createHandshake(handshakedata, role, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public List<ByteBuffer> createHandshake(Handshakedata object, WebSocket.Role object2, boolean bl) {
        object2 = new StringBuilder(100);
        if (object instanceof ClientHandshake) {
            ((StringBuilder)object2).append("GET ");
            ((StringBuilder)object2).append(((ClientHandshake)object).getResourceDescriptor());
            ((StringBuilder)object2).append(" HTTP/1.1");
        } else {
            if (!(object instanceof ServerHandshake)) {
                throw new RuntimeException("unknow role");
            }
            ((StringBuilder)object2).append("HTTP/1.1 101 " + ((ServerHandshake)object).getHttpStatusMessage());
        }
        ((StringBuilder)object2).append("\r\n");
        Object object3 = object.iterateHttpFields();
        while (object3.hasNext()) {
            String string2 = object3.next();
            String string3 = object.getFieldValue(string2);
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(": ");
            ((StringBuilder)object2).append(string3);
            ((StringBuilder)object2).append("\r\n");
        }
        ((StringBuilder)object2).append("\r\n");
        object2 = Charsetfunctions.asciiBytes(((StringBuilder)object2).toString());
        object = bl ? object.getContent() : null;
        int n = object == null ? 0 : ((byte[])object).length;
        object3 = ByteBuffer.allocate(n + ((Object)object2).length);
        ((ByteBuffer)object3).put((byte[])object2);
        if (object != null) {
            ((ByteBuffer)object3).put((byte[])object);
        }
        ((Buffer)object3).flip();
        return Collections.singletonList(object3);
    }

    public abstract CloseHandshakeType getCloseHandshakeType();

    public abstract ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder var1) throws InvalidHandshakeException;

    public abstract HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake var1, ServerHandshakeBuilder var2) throws InvalidHandshakeException;

    public abstract void reset();

    public void setParseMode(WebSocket.Role role) {
        this.role = role;
    }

    public abstract List<Framedata> translateFrame(ByteBuffer var1) throws InvalidDataException;

    public Handshakedata translateHandshake(ByteBuffer byteBuffer) throws InvalidHandshakeException {
        return Draft.translateHandshakeHttp(byteBuffer, this.role);
    }

    public static enum CloseHandshakeType {
        NONE,
        ONEWAY,
        TWOWAY;

    }

    public static enum HandshakeState {
        MATCHED,
        NOT_MATCHED;

    }

}

