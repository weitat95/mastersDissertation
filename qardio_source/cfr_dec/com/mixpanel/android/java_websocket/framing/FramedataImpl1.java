/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.framing;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.framing.FrameBuilder;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.util.Charsetfunctions;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class FramedataImpl1
implements FrameBuilder {
    protected static byte[] emptyarray = new byte[0];
    protected boolean fin;
    protected Framedata.Opcode optcode;
    protected boolean transferemasked;
    private ByteBuffer unmaskedpayload;

    public FramedataImpl1() {
    }

    public FramedataImpl1(Framedata.Opcode opcode) {
        this.optcode = opcode;
        this.unmaskedpayload = ByteBuffer.wrap(emptyarray);
    }

    public FramedataImpl1(Framedata framedata) {
        this.fin = framedata.isFin();
        this.optcode = framedata.getOpcode();
        this.unmaskedpayload = framedata.getPayloadData();
        this.transferemasked = framedata.getTransfereMasked();
    }

    @Override
    public Framedata.Opcode getOpcode() {
        return this.optcode;
    }

    @Override
    public ByteBuffer getPayloadData() {
        return this.unmaskedpayload;
    }

    @Override
    public boolean getTransfereMasked() {
        return this.transferemasked;
    }

    @Override
    public boolean isFin() {
        return this.fin;
    }

    @Override
    public void setFin(boolean bl) {
        this.fin = bl;
    }

    @Override
    public void setOptcode(Framedata.Opcode opcode) {
        this.optcode = opcode;
    }

    @Override
    public void setPayload(ByteBuffer byteBuffer) throws InvalidDataException {
        this.unmaskedpayload = byteBuffer;
    }

    public String toString() {
        return "Framedata{ optcode:" + (Object)((Object)this.getOpcode()) + ", fin:" + this.isFin() + ", payloadlength:[pos:" + this.unmaskedpayload.position() + ", len:" + this.unmaskedpayload.remaining() + "], payload:" + Arrays.toString(Charsetfunctions.utf8Bytes(new String(this.unmaskedpayload.array()))) + "}";
    }
}

