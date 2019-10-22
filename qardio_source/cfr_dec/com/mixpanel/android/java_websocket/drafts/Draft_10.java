/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.mixpanel.android.java_websocket.drafts;

import android.annotation.SuppressLint;
import com.mixpanel.android.java_websocket.WebSocket;
import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidFrameException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.LimitExedeedException;
import com.mixpanel.android.java_websocket.framing.CloseFrameBuilder;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.framing.FramedataImpl1;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.HandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.handshake.ServerHandshake;
import com.mixpanel.android.java_websocket.handshake.ServerHandshakeBuilder;
import com.mixpanel.android.java_websocket.util.Base64;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@SuppressLint(value={"Assert", "UseValueOf"})
public class Draft_10
extends Draft {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Framedata fragmentedframe = null;
    private ByteBuffer incompleteframe;
    private final Random reuseableRandom = new Random();

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Draft_10.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private byte fromOpcode(Framedata.Opcode opcode) {
        if (opcode == Framedata.Opcode.CONTINUOUS) {
            return 0;
        }
        if (opcode == Framedata.Opcode.TEXT) {
            return 1;
        }
        if (opcode == Framedata.Opcode.BINARY) {
            return 2;
        }
        if (opcode == Framedata.Opcode.CLOSING) {
            return 8;
        }
        if (opcode == Framedata.Opcode.PING) {
            return 9;
        }
        if (opcode == Framedata.Opcode.PONG) {
            return 10;
        }
        throw new RuntimeException("Don't know how to handle " + opcode.toString());
    }

    private String generateFinalKey(String string2) {
        MessageDigest messageDigest;
        string2 = string2.trim();
        string2 = string2 + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        try {
            messageDigest = MessageDigest.getInstance("SHA1");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
        return Base64.encodeBytes(messageDigest.digest(string2.getBytes()));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int readVersion(Handshakedata object) {
        int n = -1;
        if (((String)(object = object.getFieldValue("Sec-WebSocket-Version"))).length() <= 0) return n;
        try {
            return new Integer(((String)object).trim());
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }

    private byte[] toByteArray(long l, int n) {
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = (byte)(l >>> n * 8 - 8 - i * 8);
        }
        return arrby;
    }

    private Framedata.Opcode toOpcode(byte by) throws InvalidFrameException {
        switch (by) {
            default: {
                throw new InvalidFrameException("unknow optcode " + (short)by);
            }
            case 0: {
                return Framedata.Opcode.CONTINUOUS;
            }
            case 1: {
                return Framedata.Opcode.TEXT;
            }
            case 2: {
                return Framedata.Opcode.BINARY;
            }
            case 8: {
                return Framedata.Opcode.CLOSING;
            }
            case 9: {
                return Framedata.Opcode.PING;
            }
            case 10: 
        }
        return Framedata.Opcode.PONG;
    }

    @Override
    public Draft.HandshakeState acceptHandshakeAsClient(ClientHandshake clientHandshake, ServerHandshake object) throws InvalidHandshakeException {
        if (!clientHandshake.hasFieldValue("Sec-WebSocket-Key") || !object.hasFieldValue("Sec-WebSocket-Accept")) {
            return Draft.HandshakeState.NOT_MATCHED;
        }
        object = object.getFieldValue("Sec-WebSocket-Accept");
        if (this.generateFinalKey(clientHandshake.getFieldValue("Sec-WebSocket-Key")).equals(object)) {
            return Draft.HandshakeState.MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft.HandshakeState acceptHandshakeAsServer(ClientHandshake clientHandshake) throws InvalidHandshakeException {
        int n = Draft_10.readVersion(clientHandshake);
        if (n == 7 || n == 8) {
            if (this.basicAccept(clientHandshake)) {
                return Draft.HandshakeState.MATCHED;
            }
            return Draft.HandshakeState.NOT_MATCHED;
        }
        return Draft.HandshakeState.NOT_MATCHED;
    }

    @Override
    public Draft copyInstance() {
        return new Draft_10();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public ByteBuffer createBinaryFrame(Framedata object) {
        ByteBuffer byteBuffer = object.getPayloadData();
        boolean bl = this.role == WebSocket.Role.CLIENT;
        int n = byteBuffer.remaining() <= 125 ? 1 : (byteBuffer.remaining() <= 65535 ? 2 : 8);
        Object object2 = n > 1 ? n + 1 : n;
        int n2 = bl ? 4 : 0;
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(n2 + (object2 + 1) + byteBuffer.remaining());
        n2 = this.fromOpcode(object.getOpcode());
        object2 = object.isFin() ? -128 : 0;
        byteBuffer2.put((byte)((byte)object2 | n2));
        object = this.toByteArray(byteBuffer.remaining(), n);
        if (!$assertionsDisabled && ((Object)object).length != n) {
            throw new AssertionError();
        }
        if (n == 1) {
            object2 = object[0];
            n = bl ? -128 : 0;
            byteBuffer2.put((byte)(n | object2));
        } else if (n == 2) {
            n = bl ? -128 : 0;
            byteBuffer2.put((byte)(n | 0x7E));
            byteBuffer2.put((byte[])object);
        } else {
            if (n != 8) {
                throw new RuntimeException("Size representation not supported/specified");
            }
            n = bl ? -128 : 0;
            byteBuffer2.put((byte)(n | 0x7F));
            byteBuffer2.put((byte[])object);
        }
        if (bl) {
            object = ByteBuffer.allocate(4);
            ((ByteBuffer)object).putInt(this.reuseableRandom.nextInt());
            byteBuffer2.put(((ByteBuffer)object).array());
            n = 0;
            while (byteBuffer.hasRemaining()) {
                byteBuffer2.put((byte)(byteBuffer.get() ^ ((ByteBuffer)object).get(n % 4)));
                ++n;
            }
        } else {
            byteBuffer2.put(byteBuffer);
        }
        if (!$assertionsDisabled && byteBuffer2.remaining() != 0) {
            throw new AssertionError(byteBuffer2.remaining());
        }
        byteBuffer2.flip();
        return byteBuffer2;
    }

    @Override
    public Draft.CloseHandshakeType getCloseHandshakeType() {
        return Draft.CloseHandshakeType.TWOWAY;
    }

    @Override
    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder clientHandshakeBuilder) {
        clientHandshakeBuilder.put("Upgrade", "websocket");
        clientHandshakeBuilder.put("Connection", "Upgrade");
        clientHandshakeBuilder.put("Sec-WebSocket-Version", "8");
        byte[] arrby = new byte[16];
        this.reuseableRandom.nextBytes(arrby);
        clientHandshakeBuilder.put("Sec-WebSocket-Key", Base64.encodeBytes(arrby));
        return clientHandshakeBuilder;
    }

    @Override
    public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake object, ServerHandshakeBuilder serverHandshakeBuilder) throws InvalidHandshakeException {
        serverHandshakeBuilder.put("Upgrade", "websocket");
        serverHandshakeBuilder.put("Connection", object.getFieldValue("Connection"));
        serverHandshakeBuilder.setHttpStatusMessage("Switching Protocols");
        object = object.getFieldValue("Sec-WebSocket-Key");
        if (object == null) {
            throw new InvalidHandshakeException("missing Sec-WebSocket-Key");
        }
        serverHandshakeBuilder.put("Sec-WebSocket-Accept", this.generateFinalKey((String)object));
        return serverHandshakeBuilder;
    }

    @Override
    public void reset() {
        this.incompleteframe = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<Framedata> translateFrame(ByteBuffer byteBuffer) throws LimitExedeedException, InvalidDataException {
        List<Framedata> list;
        LinkedList<Framedata> linkedList = new LinkedList<Framedata>();
        if (this.incompleteframe != null) {
            try {
                byteBuffer.mark();
                int n = byteBuffer.remaining();
                int n2 = this.incompleteframe.remaining();
                if (n2 > n) {
                    this.incompleteframe.put(byteBuffer.array(), byteBuffer.position(), n);
                    byteBuffer.position(byteBuffer.position() + n);
                    return Collections.emptyList();
                }
                this.incompleteframe.put(byteBuffer.array(), byteBuffer.position(), n2);
                byteBuffer.position(byteBuffer.position() + n2);
                linkedList.add(this.translateSingleFrame((ByteBuffer)this.incompleteframe.duplicate().position(0)));
                this.incompleteframe = null;
            }
            catch (IncompleteException incompleteException) {
                this.incompleteframe.limit();
                list = ByteBuffer.allocate(this.checkAlloc(incompleteException.getPreferedSize()));
                if (!$assertionsDisabled && ((Buffer)((Object)list)).limit() <= this.incompleteframe.limit()) {
                    throw new AssertionError();
                }
                this.incompleteframe.rewind();
                ((ByteBuffer)((Object)list)).put(this.incompleteframe);
                this.incompleteframe = list;
                return this.translateFrame(byteBuffer);
            }
        }
        do {
            list = linkedList;
            if (!byteBuffer.hasRemaining()) return list;
            byteBuffer.mark();
            try {
                linkedList.add(this.translateSingleFrame(byteBuffer));
            }
            catch (IncompleteException incompleteException) {
                byteBuffer.reset();
                this.incompleteframe = ByteBuffer.allocate(this.checkAlloc(incompleteException.getPreferedSize()));
                this.incompleteframe.put(byteBuffer);
                return linkedList;
            }
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Framedata translateSingleFrame(ByteBuffer object) throws IncompleteException, InvalidDataException {
        byte[] arrby;
        int n = ((Buffer)object).remaining();
        int n2 = 2;
        if (n < 2) {
            throw new IncompleteException(2);
        }
        int n3 = ((ByteBuffer)object).get();
        boolean bl = n3 >> 8 != 0;
        int n4 = (n3 & 0x7F) >> 4;
        if (n4 != 0) {
            throw new InvalidFrameException("bad rsv " + n4);
        }
        n4 = ((ByteBuffer)object).get();
        boolean bl2 = (n4 & 0xFFFFFF80) != 0;
        n4 = (byte)(n4 & 0x7F);
        Framedata.Opcode opcode = this.toOpcode((byte)(n3 & 0xF));
        if (!(bl || opcode != Framedata.Opcode.PING && opcode != Framedata.Opcode.PONG && opcode != Framedata.Opcode.CLOSING)) {
            throw new InvalidFrameException("control frames may no be fragmented");
        }
        if (n4 < 0 || n4 > 125) {
            if (opcode == Framedata.Opcode.PING || opcode == Framedata.Opcode.PONG || opcode == Framedata.Opcode.CLOSING) {
                throw new InvalidFrameException("more than 125 octets");
            }
            if (n4 == 126) {
                n2 = 2 + 2;
                if (n < n2) {
                    throw new IncompleteException(n2);
                }
                arrby = new byte[3];
                arrby[1] = ((ByteBuffer)object).get();
                arrby[2] = ((ByteBuffer)object).get();
                n4 = new BigInteger(arrby).intValue();
            } else {
                n2 = 2 + 8;
                if (n < n2) {
                    throw new IncompleteException(n2);
                }
                arrby = new byte[8];
                for (n4 = 0; n4 < 8; ++n4) {
                    arrby[n4] = ((ByteBuffer)object).get();
                }
                long l = new BigInteger(arrby).longValue();
                if (l > Integer.MAX_VALUE) {
                    throw new LimitExedeedException("Payloadsize is to big...");
                }
                n4 = (int)l;
            }
        }
        if (n < (n2 = n2 + (n3 = bl2 ? 4 : 0) + n4)) {
            throw new IncompleteException(n2);
        }
        arrby = ByteBuffer.allocate(this.checkAlloc(n4));
        if (bl2) {
            byte[] arrby2 = new byte[4];
            ((ByteBuffer)object).get(arrby2);
            for (n2 = 0; n2 < n4; ++n2) {
                arrby.put((byte)(((ByteBuffer)object).get() ^ arrby2[n2 % 4]));
            }
        } else {
            arrby.put(((ByteBuffer)object).array(), ((Buffer)object).position(), arrby.limit());
            ((Buffer)object).position(((Buffer)object).position() + arrby.limit());
        }
        if (opcode == Framedata.Opcode.CLOSING) {
            object = new CloseFrameBuilder();
        } else {
            object = new FramedataImpl1();
            object.setFin(bl);
            object.setOptcode(opcode);
        }
        arrby.flip();
        object.setPayload((ByteBuffer)arrby);
        return object;
    }

    private class IncompleteException
    extends Throwable {
        private int preferedsize;

        public IncompleteException(int n) {
            this.preferedsize = n;
        }

        public int getPreferedSize() {
            return this.preferedsize;
        }
    }

}

