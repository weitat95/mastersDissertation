/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.mixpanel.android.java_websocket;

import android.annotation.SuppressLint;
import com.mixpanel.android.java_websocket.WebSocket;
import com.mixpanel.android.java_websocket.WebSocketListener;
import com.mixpanel.android.java_websocket.drafts.Draft;
import com.mixpanel.android.java_websocket.drafts.Draft_10;
import com.mixpanel.android.java_websocket.drafts.Draft_17;
import com.mixpanel.android.java_websocket.drafts.Draft_75;
import com.mixpanel.android.java_websocket.drafts.Draft_76;
import com.mixpanel.android.java_websocket.exceptions.IncompleteHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import com.mixpanel.android.java_websocket.exceptions.InvalidHandshakeException;
import com.mixpanel.android.java_websocket.exceptions.WebsocketNotConnectedException;
import com.mixpanel.android.java_websocket.framing.CloseFrame;
import com.mixpanel.android.java_websocket.framing.Framedata;
import com.mixpanel.android.java_websocket.handshake.ClientHandshake;
import com.mixpanel.android.java_websocket.handshake.ClientHandshakeBuilder;
import com.mixpanel.android.java_websocket.handshake.Handshakedata;
import com.mixpanel.android.java_websocket.util.Charsetfunctions;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressLint(value={"Assert"})
public class WebSocketImpl
implements WebSocket {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static boolean DEBUG;
    public static int RCVBUF;
    public static final List<Draft> defaultdraftlist;
    public ByteChannel channel;
    private Integer closecode = null;
    private Boolean closedremotely = null;
    private String closemessage = null;
    private Framedata.Opcode current_continuous_frame_opcode = null;
    private Draft draft = null;
    private volatile boolean flushandclosestate = false;
    private ClientHandshake handshakerequest = null;
    public final BlockingQueue<ByteBuffer> inQueue;
    public SelectionKey key;
    private List<Draft> knownDrafts;
    public final BlockingQueue<ByteBuffer> outQueue;
    private WebSocket.READYSTATE readystate = WebSocket.READYSTATE.NOT_YET_CONNECTED;
    private String resourceDescriptor = null;
    private WebSocket.Role role;
    private ByteBuffer tmpHandshakeBytes = ByteBuffer.allocate(0);
    private final WebSocketListener wsl;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !WebSocketImpl.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        RCVBUF = 16384;
        DEBUG = false;
        defaultdraftlist = new ArrayList<Draft>(4);
        defaultdraftlist.add(new Draft_17());
        defaultdraftlist.add(new Draft_10());
        defaultdraftlist.add(new Draft_76());
        defaultdraftlist.add(new Draft_75());
    }

    public WebSocketImpl(WebSocketListener webSocketListener, Draft draft) {
        if (webSocketListener == null || draft == null && this.role == WebSocket.Role.SERVER) {
            throw new IllegalArgumentException("parameters must not be null");
        }
        this.outQueue = new LinkedBlockingQueue<ByteBuffer>();
        this.inQueue = new LinkedBlockingQueue<ByteBuffer>();
        this.wsl = webSocketListener;
        this.role = WebSocket.Role.CLIENT;
        if (draft != null) {
            this.draft = draft.copyInstance();
        }
    }

    /*
     * Exception decompiling
     */
    private void close(int var1_1, String var2_2, boolean var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 3[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void decodeFrames(ByteBuffer var1_1) {
        try lbl-1000:
        // 12 sources
        {
            for (Framedata var5_9 : this.draft.translateFrame((ByteBuffer)var1_1)) {
                if (WebSocketImpl.DEBUG) {
                    System.out.println("matched frame: " + var5_9);
                }
                var1_1 = var5_9.getOpcode();
                var3_8 = var5_9.isFin();
                if (var1_1 != Framedata.Opcode.CLOSING) ** GOTO lbl29
                var2_7 = 1005;
                var1_1 = "";
                if (var5_9 instanceof CloseFrame) {
                    var1_1 = (CloseFrame)var5_9;
                    var2_7 = var1_1.getCloseCode();
                    var1_1 = var1_1.getMessage();
                }
                if (this.readystate == WebSocket.READYSTATE.CLOSING) {
                    this.closeConnection(var2_7, (String)var1_1, true);
                    continue;
                }
                ** GOTO lbl-1000
            }
            return;
        }
        catch (InvalidDataException var1_2) {
            this.wsl.onWebsocketError(this, var1_2);
            this.close(var1_2);
            return;
        }
lbl-1000:
        // 1 sources
        {
            block26: {
                block25: {
                    block24: {
                        block23: {
                            if (this.draft.getCloseHandshakeType() != Draft.CloseHandshakeType.TWOWAY) break block23;
                            this.close(var2_7, (String)var1_1, true);
                            ** GOTO lbl-1000
                        }
                        this.flushAndClose(var2_7, (String)var1_1, false);
                        ** GOTO lbl-1000
lbl29:
                        // 1 sources
                        if (var1_1 != Framedata.Opcode.PING) break block24;
                        this.wsl.onWebsocketPing(this, var5_9);
                        ** GOTO lbl-1000
                    }
                    if (var1_1 != Framedata.Opcode.PONG) break block25;
                    this.wsl.onWebsocketPong(this, var5_9);
                    ** GOTO lbl-1000
                }
                if (var3_8 && var1_1 != Framedata.Opcode.CONTINUOUS) break block26;
                if (var1_1 != Framedata.Opcode.CONTINUOUS) {
                    if (this.current_continuous_frame_opcode != null) {
                        throw new InvalidDataException(1002, "Previous continuous frame sequence not completed.");
                    }
                    this.current_continuous_frame_opcode = var1_1;
                } else if (var3_8) {
                    if (this.current_continuous_frame_opcode == null) {
                        throw new InvalidDataException(1002, "Continuous frame sequence was not started.");
                    }
                    this.current_continuous_frame_opcode = null;
                } else if (this.current_continuous_frame_opcode == null) {
                    throw new InvalidDataException(1002, "Continuous frame sequence was not started.");
                }
                try {
                    this.wsl.onWebsocketMessageFragment(this, var5_9);
                }
                catch (RuntimeException var1_3) {
                    this.wsl.onWebsocketError(this, var1_3);
                }
                ** GOTO lbl-1000
            }
            if (this.current_continuous_frame_opcode != null) {
                throw new InvalidDataException(1002, "Continuous frame sequence not completed.");
            }
            var6_10 = Framedata.Opcode.TEXT;
            if (var1_1 != var6_10) ** GOTO lbl67
        }
        {
            try {
                this.wsl.onWebsocketMessage((WebSocket)this, Charsetfunctions.stringUtf8(var5_9.getPayloadData()));
            }
            catch (RuntimeException var1_4) {
                this.wsl.onWebsocketError(this, var1_4);
            }
            ** GOTO lbl-1000
lbl67:
            // 1 sources
            var6_10 = Framedata.Opcode.BINARY;
            if (var1_1 != var6_10) throw new InvalidDataException(1002, "non control or continious frame expected");
        }
        {
            try {
                this.wsl.onWebsocketMessage((WebSocket)this, var5_9.getPayloadData());
            }
            catch (RuntimeException var1_5) {
                this.wsl.onWebsocketError(this, var1_5);
            }
            ** GOTO lbl-1000
        }
    }

    /*
     * Exception decompiling
     */
    private boolean decodeHandshake(ByteBuffer var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private Draft.HandshakeState isFlashEdgeCase(ByteBuffer byteBuffer) throws IncompleteHandshakeException {
        byteBuffer.mark();
        if (byteBuffer.limit() > Draft.FLASH_POLICY_REQUEST.length) {
            return Draft.HandshakeState.NOT_MATCHED;
        }
        if (byteBuffer.limit() < Draft.FLASH_POLICY_REQUEST.length) {
            throw new IncompleteHandshakeException(Draft.FLASH_POLICY_REQUEST.length);
        }
        int n = 0;
        while (byteBuffer.hasRemaining()) {
            if (Draft.FLASH_POLICY_REQUEST[n] != byteBuffer.get()) {
                byteBuffer.reset();
                return Draft.HandshakeState.NOT_MATCHED;
            }
            ++n;
        }
        return Draft.HandshakeState.MATCHED;
    }

    private void open(Handshakedata handshakedata) {
        if (DEBUG) {
            System.out.println("open using draft: " + this.draft.getClass().getSimpleName());
        }
        this.readystate = WebSocket.READYSTATE.OPEN;
        try {
            this.wsl.onWebsocketOpen(this, handshakedata);
            return;
        }
        catch (RuntimeException runtimeException) {
            this.wsl.onWebsocketError(this, runtimeException);
            return;
        }
    }

    private void send(Collection<Framedata> object) {
        if (!this.isOpen()) {
            throw new WebsocketNotConnectedException();
        }
        object = object.iterator();
        while (object.hasNext()) {
            this.sendFrame((Framedata)object.next());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void write(ByteBuffer byteBuffer) {
        if (DEBUG) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder().append("write(").append(byteBuffer.remaining()).append("): {");
            String string2 = byteBuffer.remaining() > 1000 ? "too big to display" : new String(byteBuffer.array());
            printStream.println(stringBuilder.append(string2).append("}").toString());
        }
        this.outQueue.add(byteBuffer);
        this.wsl.onWriteDemand(this);
    }

    private void write(List<ByteBuffer> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.write((ByteBuffer)object.next());
        }
    }

    public void close(int n, String string2) {
        this.close(n, string2, false);
    }

    public void close(InvalidDataException invalidDataException) {
        this.close(invalidDataException.getCloseCode(), invalidDataException.getMessage(), false);
    }

    public void closeConnection(int n, String string2) {
        this.closeConnection(n, string2, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void closeConnection(int n, String string2, boolean bl) {
        synchronized (this) {
            Object object = this.readystate;
            WebSocket.READYSTATE rEADYSTATE = WebSocket.READYSTATE.CLOSED;
            if (object != rEADYSTATE) {
                if (this.key != null) {
                    this.key.cancel();
                }
                if ((object = this.channel) != null) {
                    try {
                        this.channel.close();
                    }
                    catch (IOException iOException) {
                        this.wsl.onWebsocketError(this, iOException);
                    }
                }
                try {
                    this.wsl.onWebsocketClose(this, n, string2, bl);
                }
                catch (RuntimeException runtimeException) {
                    this.wsl.onWebsocketError(this, runtimeException);
                }
                if (this.draft != null) {
                    this.draft.reset();
                }
                this.handshakerequest = null;
                this.readystate = WebSocket.READYSTATE.CLOSED;
                this.outQueue.clear();
            }
            return;
        }
    }

    protected void closeConnection(int n, boolean bl) {
        this.closeConnection(n, "", bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void decode(ByteBuffer byteBuffer) {
        if (!$assertionsDisabled && !byteBuffer.hasRemaining()) {
            throw new AssertionError();
        }
        if (DEBUG) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder().append("process(").append(byteBuffer.remaining()).append("): {");
            String string2 = byteBuffer.remaining() > 1000 ? "too big to display" : new String(byteBuffer.array(), byteBuffer.position(), byteBuffer.remaining());
            printStream.println(stringBuilder.append(string2).append("}").toString());
        }
        if (this.readystate != WebSocket.READYSTATE.NOT_YET_CONNECTED) {
            this.decodeFrames(byteBuffer);
        } else if (this.decodeHandshake(byteBuffer)) {
            if (!$assertionsDisabled && this.tmpHandshakeBytes.hasRemaining() == byteBuffer.hasRemaining() && byteBuffer.hasRemaining()) {
                throw new AssertionError();
            }
            if (byteBuffer.hasRemaining()) {
                this.decodeFrames(byteBuffer);
            } else if (this.tmpHandshakeBytes.hasRemaining()) {
                this.decodeFrames(this.tmpHandshakeBytes);
            }
        }
        if (!$assertionsDisabled && !this.isClosing() && !this.isFlushAndClose() && byteBuffer.hasRemaining()) {
            throw new AssertionError();
        }
    }

    public void eot() {
        if (this.getReadyState() == WebSocket.READYSTATE.NOT_YET_CONNECTED) {
            this.closeConnection(-1, true);
            return;
        }
        if (this.flushandclosestate) {
            this.closeConnection(this.closecode, this.closemessage, this.closedremotely);
            return;
        }
        if (this.draft.getCloseHandshakeType() == Draft.CloseHandshakeType.NONE) {
            this.closeConnection(1000, true);
            return;
        }
        if (this.draft.getCloseHandshakeType() == Draft.CloseHandshakeType.ONEWAY) {
            if (this.role == WebSocket.Role.SERVER) {
                this.closeConnection(1006, true);
                return;
            }
            this.closeConnection(1000, true);
            return;
        }
        this.closeConnection(1006, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void flushAndClose(int n, String string2, boolean bl) {
        synchronized (this) {
            boolean bl2 = this.flushandclosestate;
            if (!bl2) {
                this.closecode = n;
                this.closemessage = string2;
                this.closedremotely = bl;
                this.flushandclosestate = true;
                this.wsl.onWriteDemand(this);
                try {
                    this.wsl.onWebsocketClosing(this, n, string2, bl);
                }
                catch (RuntimeException runtimeException) {
                    this.wsl.onWebsocketError(this, runtimeException);
                }
                if (this.draft != null) {
                    this.draft.reset();
                }
                this.handshakerequest = null;
            }
            return;
        }
    }

    @Override
    public InetSocketAddress getLocalSocketAddress() {
        return this.wsl.getLocalSocketAddress(this);
    }

    public WebSocket.READYSTATE getReadyState() {
        return this.readystate;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean isClosed() {
        return this.readystate == WebSocket.READYSTATE.CLOSED;
    }

    public boolean isClosing() {
        return this.readystate == WebSocket.READYSTATE.CLOSING;
    }

    public boolean isFlushAndClose() {
        return this.flushandclosestate;
    }

    public boolean isOpen() {
        if ($assertionsDisabled || this.readystate != WebSocket.READYSTATE.OPEN || !this.flushandclosestate) {
            return this.readystate == WebSocket.READYSTATE.OPEN;
        }
        throw new AssertionError();
    }

    public void sendFragmentedFrame(Framedata.Opcode opcode, ByteBuffer byteBuffer, boolean bl) {
        this.send(this.draft.continuousFrame(opcode, byteBuffer, bl));
    }

    @Override
    public void sendFrame(Framedata framedata) {
        if (DEBUG) {
            System.out.println("send frame: " + framedata);
        }
        this.write(this.draft.createBinaryFrame(framedata));
    }

    public void startHandshake(ClientHandshakeBuilder clientHandshakeBuilder) throws InvalidHandshakeException {
        if (!$assertionsDisabled && this.readystate == WebSocket.READYSTATE.CONNECTING) {
            throw new AssertionError((Object)"shall only be called once");
        }
        this.handshakerequest = this.draft.postProcessHandshakeRequestAsClient(clientHandshakeBuilder);
        this.resourceDescriptor = clientHandshakeBuilder.getResourceDescriptor();
        if (!$assertionsDisabled && this.resourceDescriptor == null) {
            throw new AssertionError();
        }
        try {
            this.wsl.onWebsocketHandshakeSentAsClient(this, this.handshakerequest);
        }
        catch (InvalidDataException invalidDataException) {
            throw new InvalidHandshakeException("Handshake data rejected by client.");
        }
        catch (RuntimeException runtimeException) {
            this.wsl.onWebsocketError(this, runtimeException);
            throw new InvalidHandshakeException("rejected because of" + runtimeException);
        }
        this.write(this.draft.createHandshake(this.handshakerequest, this.role));
        return;
    }

    public String toString() {
        return super.toString();
    }
}

