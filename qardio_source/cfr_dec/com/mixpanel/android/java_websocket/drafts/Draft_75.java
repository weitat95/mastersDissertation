/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.drafts;

import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidFrameException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.LimitExedeedException;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.FramedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Draft_75
extends Draft {
    protected ByteBuffer currentFrame;
    protected boolean readingState = false;
    protected List<Framedata> readyframes = new LinkedList<Framedata>();
    private final Random reuseableRandom = new Random();

    @Override
    public Draft.HandshakeState acceptHandshakeAsClient(ClientHandshake clientHandshake, ServerHandshake serverHandshake) {
        if (clientHandshake.getFieldValue("WebSocket-Origin").equals(serverHandshake.getFieldValue("Origin")) && this.basicAccept(serverHandshake)) {
            return Draft.HandshakeState.MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft.HandshakeState acceptHandshakeAsServer(ClientHandshake clientHandshake) {
        if (clientHandshake.hasFieldValue("Origin") && this.basicAccept(clientHandshake)) {
            return Draft.HandshakeState.MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft copyInstance() {
        return new Draft_75();
    }

    @Override
    public ByteBuffer createBinaryFrame(Framedata object) {
        if (object.getOpcode() != Framedata.Opcode.TEXT) {
            throw new RuntimeException("only text frames supported");
        }
        object = object.getPayloadData();
        ByteBuffer byteBuffer = ByteBuffer.allocate(((Buffer)object).remaining() + 2);
        byteBuffer.put((byte)0);
        ((Buffer)object).mark();
        byteBuffer.put((ByteBuffer)object);
        ((Buffer)object).reset();
        byteBuffer.put((byte)-1);
        byteBuffer.flip();
        return byteBuffer;
    }

    public ByteBuffer createBuffer() {
        return ByteBuffer.allocate(INITIAL_FAMESIZE);
    }

    @Override
    public Draft.CloseHandshakeType getCloseHandshakeType() {
        return Draft.CloseHandshakeType.NONE;
    }

    public ByteBuffer increaseBuffer(ByteBuffer byteBuffer) throws LimitExedeedException, InvalidDataException {
        byteBuffer.flip();
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(this.checkAlloc(byteBuffer.capacity() * 2));
        byteBuffer2.put(byteBuffer);
        return byteBuffer2;
    }

    @Override
    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder clientHandshakeBuilder) throws InvalidHandshakeException {
        clientHandshakeBuilder.put("Upgrade", "WebSocket");
        clientHandshakeBuilder.put("Connection", "Upgrade");
        if (!clientHandshakeBuilder.hasFieldValue("Origin")) {
            clientHandshakeBuilder.put("Origin", "random" + this.reuseableRandom.nextInt());
        }
        return clientHandshakeBuilder;
    }

    @Override
    public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake clientHandshake, ServerHandshakeBuilder serverHandshakeBuilder) throws InvalidHandshakeException {
        serverHandshakeBuilder.setHttpStatusMessage("Web Socket Protocol Handshake");
        serverHandshakeBuilder.put("Upgrade", "WebSocket");
        serverHandshakeBuilder.put("Connection", clientHandshake.getFieldValue("Connection"));
        serverHandshakeBuilder.put("WebSocket-Origin", clientHandshake.getFieldValue("Origin"));
        serverHandshakeBuilder.put("WebSocket-Location", "ws://" + clientHandshake.getFieldValue("Host") + clientHandshake.getResourceDescriptor());
        return serverHandshakeBuilder;
    }

    @Override
    public void reset() {
        this.readingState = false;
        this.currentFrame = null;
    }

    @Override
    public List<Framedata> translateFrame(ByteBuffer object) throws InvalidDataException {
        if ((object = this.translateRegularFrame((ByteBuffer)object)) == null) {
            throw new InvalidDataException(1002);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected List<Framedata> translateRegularFrame(ByteBuffer byteBuffer) throws InvalidDataException {
        Object object;
        Object var4_2 = null;
        while (byteBuffer.hasRemaining()) {
            byte by = byteBuffer.get();
            if (by == 0) {
                if (this.readingState) {
                    throw new InvalidFrameException("unexpected START_OF_FRAME");
                }
                this.readingState = true;
                continue;
            }
            if (by == -1) {
                if (!this.readingState) {
                    throw new InvalidFrameException("unexpected END_OF_FRAME");
                }
                if (this.currentFrame != null) {
                    this.currentFrame.flip();
                    object = new FramedataImpl1();
                    ((FramedataImpl1)object).setPayload(this.currentFrame);
                    ((FramedataImpl1)object).setFin(true);
                    ((FramedataImpl1)object).setOptcode(Framedata.Opcode.TEXT);
                    this.readyframes.add((Framedata)object);
                    this.currentFrame = null;
                    byteBuffer.mark();
                }
                this.readingState = false;
                continue;
            }
            object = var4_2;
            if (!this.readingState) return object;
            {
                if (this.currentFrame == null) {
                    this.currentFrame = this.createBuffer();
                } else if (!this.currentFrame.hasRemaining()) {
                    this.currentFrame = this.increaseBuffer(this.currentFrame);
                }
                this.currentFrame.put(by);
            }
        }
        object = this.readyframes;
        this.readyframes = new LinkedList<Framedata>();
        return object;
    }
}

