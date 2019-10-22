/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.framing;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidFrameException;
import com.mixpanel.android.java_websocket.framing.CloseFrame;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.FramedataImpl1;
import com.mixpanel.android.java_websocket.util.Charsetfunctions;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class CloseFrameBuilder
extends FramedataImpl1
implements CloseFrame {
    static final ByteBuffer emptybytebuffer = ByteBuffer.allocate(0);
    private int code;
    private String reason;

    public CloseFrameBuilder() {
        super(Framedata.Opcode.CLOSING);
        this.setFin(true);
    }

    public CloseFrameBuilder(int n) throws InvalidDataException {
        super(Framedata.Opcode.CLOSING);
        this.setFin(true);
        this.setCodeAndMessage(n, "");
    }

    public CloseFrameBuilder(int n, String string2) throws InvalidDataException {
        super(Framedata.Opcode.CLOSING);
        this.setFin(true);
        this.setCodeAndMessage(n, string2);
    }

    private void initCloseCode() throws InvalidFrameException {
        this.code = 1005;
        ByteBuffer byteBuffer = super.getPayloadData();
        byteBuffer.mark();
        if (byteBuffer.remaining() >= 2) {
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(4);
            byteBuffer2.position(2);
            byteBuffer2.putShort(byteBuffer.getShort());
            byteBuffer2.position(0);
            this.code = byteBuffer2.getInt();
            if (this.code == 1006 || this.code == 1015 || this.code == 1005 || this.code > 4999 || this.code < 1000 || this.code == 1004) {
                throw new InvalidFrameException("closecode must not be sent over the wire: " + this.code);
            }
        }
        byteBuffer.reset();
    }

    private void initMessage() throws InvalidDataException {
        if (this.code == 1005) {
            this.reason = Charsetfunctions.stringUtf8(super.getPayloadData());
            return;
        }
        ByteBuffer byteBuffer = super.getPayloadData();
        int n = byteBuffer.position();
        try {
            byteBuffer.position(byteBuffer.position() + 2);
            this.reason = Charsetfunctions.stringUtf8(byteBuffer);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidFrameException(illegalArgumentException);
        }
        finally {
            byteBuffer.position(n);
        }
    }

    private void setCodeAndMessage(int n, String arrby) throws InvalidDataException {
        Object object = arrby;
        if (arrby == null) {
            object = "";
        }
        int n2 = n;
        if (n == 1015) {
            n2 = 1005;
            object = "";
        }
        if (n2 == 1005) {
            if (((String)object).length() > 0) {
                throw new InvalidDataException(1002, "A close frame must have a closecode if it has a reason");
            }
        } else {
            arrby = Charsetfunctions.utf8Bytes((String)object);
            object = ByteBuffer.allocate(4);
            ((ByteBuffer)object).putInt(n2);
            ((Buffer)object).position(2);
            ByteBuffer byteBuffer = ByteBuffer.allocate(arrby.length + 2);
            byteBuffer.put((ByteBuffer)object);
            byteBuffer.put(arrby);
            byteBuffer.rewind();
            this.setPayload(byteBuffer);
        }
    }

    @Override
    public int getCloseCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.reason;
    }

    @Override
    public ByteBuffer getPayloadData() {
        if (this.code == 1005) {
            return emptybytebuffer;
        }
        return super.getPayloadData();
    }

    @Override
    public void setPayload(ByteBuffer byteBuffer) throws InvalidDataException {
        super.setPayload(byteBuffer);
        this.initCloseCode();
        this.initMessage();
    }

    @Override
    public String toString() {
        return super.toString() + "code: " + this.code;
    }
}

