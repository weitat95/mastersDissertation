/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Http2Reader;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.http2.Http2Writer;
import okhttp3.internal.http2.Ping;
import okhttp3.internal.http2.PushObserver;
import okhttp3.internal.http2.Settings;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

public final class Http2Connection
implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static final ExecutorService executor;
    long bytesLeftInWriteWindow;
    final boolean client;
    final Set<Integer> currentPushRequests;
    final String hostname;
    int lastGoodStreamId;
    final Listener listener;
    private int nextPingId;
    int nextStreamId;
    Settings okHttpSettings;
    final Settings peerSettings;
    private Map<Integer, Ping> pings;
    private final ExecutorService pushExecutor;
    final PushObserver pushObserver;
    final ReaderRunnable readerRunnable;
    boolean receivedInitialPeerSettings;
    boolean shutdown;
    final Socket socket;
    final Map<Integer, Http2Stream> streams;
    long unacknowledgedBytesRead;
    final Http2Writer writer;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Http2Connection.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Http2Connection", true));
    }

    /*
     * Enabled aggressive block sorting
     */
    Http2Connection(Builder builder) {
        int n = 2;
        this.streams = new LinkedHashMap<Integer, Http2Stream>();
        this.unacknowledgedBytesRead = 0L;
        this.okHttpSettings = new Settings();
        this.peerSettings = new Settings();
        this.receivedInitialPeerSettings = false;
        this.currentPushRequests = new LinkedHashSet<Integer>();
        this.pushObserver = builder.pushObserver;
        this.client = builder.client;
        this.listener = builder.listener;
        int n2 = builder.client ? 1 : 2;
        this.nextStreamId = n2;
        if (builder.client) {
            this.nextStreamId += 2;
        }
        n2 = n;
        if (builder.client) {
            n2 = 1;
        }
        this.nextPingId = n2;
        if (builder.client) {
            this.okHttpSettings.set(7, 16777216);
        }
        this.hostname = builder.hostname;
        this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory(Util.format("OkHttp %s Push Observer", this.hostname), true));
        this.peerSettings.set(7, 65535);
        this.peerSettings.set(5, 16384);
        this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize();
        this.socket = builder.socket;
        this.writer = new Http2Writer(builder.sink, this.client);
        this.readerRunnable = new ReaderRunnable(new Http2Reader(builder.source, this.client));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private Http2Stream newStream(int n, List<Header> list, boolean bl) throws IOException {
        boolean bl2 = !bl;
        Http2Writer http2Writer = this.writer;
        // MONITORENTER : http2Writer
        // MONITORENTER : this
        if (this.shutdown) {
            throw new ConnectionShutdownException();
        }
        int n2 = this.nextStreamId;
        this.nextStreamId += 2;
        Http2Stream http2Stream = new Http2Stream(n2, this, bl2, false, list);
        boolean bl3 = !bl || this.bytesLeftInWriteWindow == 0L || http2Stream.bytesLeftInWriteWindow == 0L;
        if (http2Stream.isOpen()) {
            this.streams.put(n2, http2Stream);
        }
        // MONITOREXIT : this
        if (n == 0) {
            this.writer.synStream(bl2, n2, n, list);
        } else {
            if (this.client) {
                throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
            }
            this.writer.pushPromise(n, n2, list);
        }
        // MONITOREXIT : http2Writer
        if (!bl3) return http2Stream;
        this.writer.flush();
        return http2Stream;
    }

    void addBytesToWriteWindow(long l) {
        this.bytesLeftInWriteWindow += l;
        if (l > 0L) {
            this.notifyAll();
        }
    }

    @Override
    public void close() throws IOException {
        this.close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void close(ErrorCode object, ErrorCode errorCode) throws IOException {
        block21: {
            int n;
            int n2;
            if (!$assertionsDisabled && Thread.holdsLock(this)) {
                throw new AssertionError();
            }
            Object object2 = null;
            try {
                this.shutdown((ErrorCode)((Object)object));
                object = object2;
            }
            catch (IOException iOException) {}
            Http2Stream[] arrhttp2Stream = null;
            Ping[] arrping = null;
            synchronized (this) {
                if (!this.streams.isEmpty()) {
                    arrhttp2Stream = this.streams.values().toArray(new Http2Stream[this.streams.size()]);
                    this.streams.clear();
                }
                if (this.pings != null) {
                    arrping = this.pings.values().toArray(new Ping[this.pings.size()]);
                    this.pings = null;
                }
            }
            object2 = object;
            if (arrhttp2Stream != null) {
                n2 = arrhttp2Stream.length;
                n = 0;
                do {
                    block20: {
                        object2 = object;
                        if (n >= n2) break;
                        object2 = arrhttp2Stream[n];
                        try {
                            ((Http2Stream)object2).close(errorCode);
                            object2 = object;
                        }
                        catch (IOException iOException) {
                            object2 = object;
                            if (object == null) break block20;
                            object2 = iOException;
                        }
                    }
                    ++n;
                    object = object2;
                } while (true);
            }
            if (arrping != null) {
                n2 = arrping.length;
                for (n = 0; n < n2; ++n) {
                    arrping[n].cancel();
                }
            }
            try {
                this.writer.close();
                object = object2;
            }
            catch (IOException iOException) {
                object = object2;
                if (object2 != null) break block21;
                object = iOException;
            }
        }
        try {
            this.socket.close();
        }
        catch (IOException iOException) {}
        if (object != null) {
            throw object;
        }
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    Http2Stream getStream(int n) {
        synchronized (this) {
            Http2Stream http2Stream = this.streams.get(n);
            return http2Stream;
        }
    }

    public boolean isShutdown() {
        synchronized (this) {
            boolean bl = this.shutdown;
            return bl;
        }
    }

    public int maxConcurrentStreams() {
        synchronized (this) {
            int n = this.peerSettings.getMaxConcurrentStreams(Integer.MAX_VALUE);
            return n;
        }
    }

    public Http2Stream newStream(List<Header> list, boolean bl) throws IOException {
        return this.newStream(0, list, bl);
    }

    void pushDataLater(final int n, BufferedSource bufferedSource, final int n2, final boolean bl) throws IOException {
        final Buffer buffer = new Buffer();
        bufferedSource.require(n2);
        bufferedSource.read(buffer, n2);
        if (buffer.size() != (long)n2) {
            throw new IOException(buffer.size() + " != " + n2);
        }
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.hostname, n}){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void execute() {
                try {
                    boolean bl2 = Http2Connection.this.pushObserver.onData(n, buffer, n2, bl);
                    if (bl2) {
                        Http2Connection.this.writer.rstStream(n, ErrorCode.CANCEL);
                    }
                    if (!bl2 && !bl) return;
                    Http2Connection http2Connection = Http2Connection.this;
                    synchronized (http2Connection) {
                        Http2Connection.this.currentPushRequests.remove(n);
                    }
                }
                catch (IOException iOException) {
                    // empty catch block
                    return;
                }
            }
        });
    }

    void pushHeadersLater(final int n, final List<Header> list, final boolean bl) {
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.hostname, n}){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void execute() {
                var1_1 = Http2Connection.this.pushObserver.onHeaders(n, list, bl);
                if (!var1_1) ** GOTO lbl5
                try {
                    Http2Connection.this.writer.rstStream(n, ErrorCode.CANCEL);
lbl5:
                    // 2 sources
                    if (!var1_1) {
                        if (bl == false) return;
                    }
                    var2_2 = Http2Connection.this;
                    // MONITORENTER : var2_2
                    Http2Connection.this.currentPushRequests.remove(n);
                }
                catch (IOException var2_3) {
                    // empty catch block
                }
                // MONITOREXIT : var2_2
                return;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void pushRequestLater(final int n, final List<Header> list) {
        synchronized (this) {
            if (this.currentPushRequests.contains(n)) {
                this.writeSynResetLater(n, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.currentPushRequests.add(n);
        }
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.hostname, n}){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void execute() {
                if (!Http2Connection.this.pushObserver.onRequest(n, list)) return;
                try {
                    Http2Connection.this.writer.rstStream(n, ErrorCode.CANCEL);
                    Http2Connection http2Connection = Http2Connection.this;
                    synchronized (http2Connection) {
                        Http2Connection.this.currentPushRequests.remove(n);
                    }
                }
                catch (IOException iOException) {
                    // empty catch block
                    return;
                }
            }
        });
    }

    void pushResetLater(final int n, final ErrorCode errorCode) {
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.hostname, n}){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void execute() {
                Http2Connection.this.pushObserver.onReset(n, errorCode);
                Http2Connection http2Connection = Http2Connection.this;
                synchronized (http2Connection) {
                    Http2Connection.this.currentPushRequests.remove(n);
                    return;
                }
            }
        });
    }

    boolean pushedStream(int n) {
        return n != 0 && (n & 1) == 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Ping removePing(int n) {
        synchronized (this) {
            if (this.pings == null) return null;
            return this.pings.remove(n);
        }
    }

    Http2Stream removeStream(int n) {
        synchronized (this) {
            Http2Stream http2Stream = this.streams.remove(n);
            this.notifyAll();
            return http2Stream;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void shutdown(ErrorCode errorCode) throws IOException {
        Http2Writer http2Writer = this.writer;
        synchronized (http2Writer) {
            int n;
            synchronized (this) {
                if (this.shutdown) {
                    return;
                }
                this.shutdown = true;
                n = this.lastGoodStreamId;
            }
            this.writer.goAway(n, errorCode, Util.EMPTY_BYTE_ARRAY);
            return;
        }
    }

    public void start() throws IOException {
        this.start(true);
    }

    void start(boolean bl) throws IOException {
        if (bl) {
            this.writer.connectionPreface();
            this.writer.settings(this.okHttpSettings);
            int n = this.okHttpSettings.getInitialWindowSize();
            if (n != 65535) {
                this.writer.windowUpdate(0, n - 65535);
            }
        }
        new Thread(this.readerRunnable).start();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void writeData(int var1_1, boolean var2_2, Buffer var3_3, long var4_5) throws IOException {
        var8_6 = var4_5;
        if (var4_5 != 0L) ** GOTO lbl11
        this.writer.data(var2_2, var1_1, var3_3, 0);
        return;
        {
            var6_7 = Math.min((int)Math.min(var8_6, this.bytesLeftInWriteWindow), this.writer.maxDataLength());
            this.bytesLeftInWriteWindow -= (long)var6_7;
            // MONITOREXIT : this
            var10_9 = this.writer;
            var7_8 = var2_2 != false && (var8_6 -= (long)var6_7) == 0L;
            var10_9.data(var7_8, var1_1, var3_3, var6_7);
lbl11:
            // 2 sources
            if (var8_6 <= 0L) return;
            // MONITORENTER : this
            do {
                if (this.bytesLeftInWriteWindow > 0L) continue block6;
                if (!this.streams.containsKey(var1_1)) {
                    throw new IOException("stream closed");
                }
                this.wait();
            } while (true);
        }
        catch (InterruptedException var3_4) {
            throw new InterruptedIOException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void writePing(boolean bl, int n, int n2, Ping ping) throws IOException {
        Http2Writer http2Writer = this.writer;
        synchronized (http2Writer) {
            if (ping != null) {
                ping.send();
            }
            this.writer.ping(bl, n, n2);
            return;
        }
    }

    void writePingLater(final boolean bl, final int n, final int n2, final Ping ping) {
        executor.execute(new NamedRunnable("OkHttp %s ping %08x%08x", new Object[]{this.hostname, n, n2}){

            @Override
            public void execute() {
                try {
                    Http2Connection.this.writePing(bl, n, n2, ping);
                    return;
                }
                catch (IOException iOException) {
                    return;
                }
            }
        });
    }

    void writeSynReset(int n, ErrorCode errorCode) throws IOException {
        this.writer.rstStream(n, errorCode);
    }

    void writeSynResetLater(final int n, final ErrorCode errorCode) {
        executor.execute(new NamedRunnable("OkHttp %s stream %d", new Object[]{this.hostname, n}){

            @Override
            public void execute() {
                try {
                    Http2Connection.this.writeSynReset(n, errorCode);
                    return;
                }
                catch (IOException iOException) {
                    return;
                }
            }
        });
    }

    void writeWindowUpdateLater(final int n, final long l) {
        executor.execute(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.hostname, n}){

            @Override
            public void execute() {
                try {
                    Http2Connection.this.writer.windowUpdate(n, l);
                    return;
                }
                catch (IOException iOException) {
                    return;
                }
            }
        });
    }

    public static class Builder {
        boolean client;
        String hostname;
        Listener listener = Listener.REFUSE_INCOMING_STREAMS;
        PushObserver pushObserver = PushObserver.CANCEL;
        BufferedSink sink;
        Socket socket;
        BufferedSource source;

        public Builder(boolean bl) {
            this.client = bl;
        }

        public Http2Connection build() throws IOException {
            return new Http2Connection(this);
        }

        public Builder listener(Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder socket(Socket socket, String string2, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.socket = socket;
            this.hostname = string2;
            this.source = bufferedSource;
            this.sink = bufferedSink;
            return this;
        }
    }

    public static abstract class Listener {
        public static final Listener REFUSE_INCOMING_STREAMS = new Listener(){

            @Override
            public void onStream(Http2Stream http2Stream) throws IOException {
                http2Stream.close(ErrorCode.REFUSED_STREAM);
            }
        };

        public void onSettings(Http2Connection http2Connection) {
        }

        public abstract void onStream(Http2Stream var1) throws IOException;

    }

    class ReaderRunnable
    extends NamedRunnable
    implements Http2Reader.Handler {
        final Http2Reader reader;

        ReaderRunnable(Http2Reader http2Reader) {
            super("OkHttp %s", Http2Connection.this.hostname);
            this.reader = http2Reader;
        }

        private void applyAndAckSettings(final Settings settings) {
            executor.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{Http2Connection.this.hostname}){

                @Override
                public void execute() {
                    try {
                        Http2Connection.this.writer.applyAndAckSettings(settings);
                        return;
                    }
                    catch (IOException iOException) {
                        return;
                    }
                }
            });
        }

        @Override
        public void ackSettings() {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void data(boolean bl, int n, BufferedSource bufferedSource, int n2) throws IOException {
            if (Http2Connection.this.pushedStream(n)) {
                Http2Connection.this.pushDataLater(n, bufferedSource, n2, bl);
                return;
            } else {
                Http2Stream http2Stream = Http2Connection.this.getStream(n);
                if (http2Stream == null) {
                    Http2Connection.this.writeSynResetLater(n, ErrorCode.PROTOCOL_ERROR);
                    bufferedSource.skip(n2);
                    return;
                }
                http2Stream.receiveData(bufferedSource, n2);
                if (!bl) return;
                {
                    http2Stream.receiveFin();
                    return;
                }
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        protected void execute() {
            block11: {
                ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
                ErrorCode errorCode2 = ErrorCode.INTERNAL_ERROR;
                ErrorCode errorCode3 = errorCode;
                ErrorCode errorCode4 = errorCode;
                this.reader.readConnectionPreface(this);
                do {
                    errorCode3 = errorCode;
                    errorCode4 = errorCode;
                } while (this.reader.nextFrame(false, this));
                errorCode3 = errorCode;
                errorCode4 = errorCode;
                errorCode3 = errorCode = ErrorCode.NO_ERROR;
                errorCode4 = errorCode;
                ErrorCode errorCode5 = ErrorCode.CANCEL;
                try {
                    Http2Connection.this.close(errorCode, errorCode5);
                    break block11;
                }
                catch (IOException iOException) {}
                catch (IOException iOException) {
                    errorCode4 = errorCode3;
                    try {
                        errorCode4 = errorCode3 = ErrorCode.PROTOCOL_ERROR;
                        errorCode = ErrorCode.PROTOCOL_ERROR;
                    }
                    catch (Throwable throwable) {
                        try {
                            Http2Connection.this.close(errorCode4, errorCode2);
                        }
                        catch (IOException iOException2) {}
                        Util.closeQuietly(this.reader);
                        throw throwable;
                    }
                    try {
                        Http2Connection.this.close(errorCode3, errorCode);
                    }
                    catch (IOException iOException3) {}
                    Util.closeQuietly(this.reader);
                    return;
                }
            }
            Util.closeQuietly(this.reader);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void goAway(int n, ErrorCode object, ByteString arrhttp2Stream) {
            if (arrhttp2Stream.size() > 0) {
                // empty if block
            }
            object = Http2Connection.this;
            synchronized (object) {
                arrhttp2Stream = Http2Connection.this.streams.values().toArray(new Http2Stream[Http2Connection.this.streams.size()]);
                Http2Connection.this.shutdown = true;
            }
            int n2 = arrhttp2Stream.length;
            int n3 = 0;
            while (n3 < n2) {
                object = arrhttp2Stream[n3];
                if (((Http2Stream)object).getId() > n && ((Http2Stream)object).isLocallyInitiated()) {
                    ((Http2Stream)object).receiveRstStream(ErrorCode.REFUSED_STREAM);
                    Http2Connection.this.removeStream(((Http2Stream)object).getId());
                }
                ++n3;
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void headers(boolean bl, int n, int n2, List<Header> object) {
            if (Http2Connection.this.pushedStream(n)) {
                Http2Connection.this.pushHeadersLater(n, (List<Header>)object, bl);
                return;
            } else {
                Http2Stream http2Stream;
                Http2Connection http2Connection = Http2Connection.this;
                synchronized (http2Connection) {
                    if (Http2Connection.this.shutdown) {
                        return;
                    }
                    http2Stream = Http2Connection.this.getStream(n);
                    if (http2Stream == null) {
                        if (n <= Http2Connection.this.lastGoodStreamId) {
                            return;
                        }
                        if (n % 2 == Http2Connection.this.nextStreamId % 2) {
                            return;
                        }
                        object = new Http2Stream(n, Http2Connection.this, false, bl, (List<Header>)object);
                        Http2Connection.this.lastGoodStreamId = n;
                        Http2Connection.this.streams.put(n, (Http2Stream)object);
                        executor.execute(new NamedRunnable("OkHttp %s stream %d", new Object[]{Http2Connection.this.hostname, n}, (Http2Stream)object){
                            final /* synthetic */ Http2Stream val$newStream;
                            {
                                this.val$newStream = http2Stream;
                                super(string2, arrobject);
                            }

                            @Override
                            public void execute() {
                                try {
                                    Http2Connection.this.listener.onStream(this.val$newStream);
                                    return;
                                }
                                catch (IOException iOException) {
                                    Platform.get().log(4, "Http2Connection.Listener failure for " + Http2Connection.this.hostname, iOException);
                                    try {
                                        this.val$newStream.close(ErrorCode.PROTOCOL_ERROR);
                                        return;
                                    }
                                    catch (IOException iOException2) {
                                        return;
                                    }
                                }
                            }
                        });
                        return;
                    }
                }
                http2Stream.receiveHeaders((List<Header>)object);
                if (!bl) return;
                {
                    http2Stream.receiveFin();
                    return;
                }
            }
        }

        @Override
        public void ping(boolean bl, int n, int n2) {
            if (bl) {
                Ping ping = Http2Connection.this.removePing(n);
                if (ping != null) {
                    ping.receive();
                }
                return;
            }
            Http2Connection.this.writePingLater(true, n, n2, null);
        }

        @Override
        public void priority(int n, int n2, int n3, boolean bl) {
        }

        @Override
        public void pushPromise(int n, int n2, List<Header> list) {
            Http2Connection.this.pushRequestLater(n2, list);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void rstStream(int n, ErrorCode errorCode) {
            if (Http2Connection.this.pushedStream(n)) {
                Http2Connection.this.pushResetLater(n, errorCode);
                return;
            } else {
                Http2Stream http2Stream = Http2Connection.this.removeStream(n);
                if (http2Stream == null) return;
                {
                    http2Stream.receiveRstStream(errorCode);
                    return;
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void settings(boolean bl, Settings arrhttp2Stream) {
            long l = 0L;
            Http2Stream http2Stream = null;
            Http2Connection http2Connection = Http2Connection.this;
            // MONITORENTER : http2Connection
            int n = Http2Connection.this.peerSettings.getInitialWindowSize();
            if (bl) {
                Http2Connection.this.peerSettings.clear();
            }
            Http2Connection.this.peerSettings.merge((Settings)arrhttp2Stream);
            this.applyAndAckSettings((Settings)arrhttp2Stream);
            int n2 = Http2Connection.this.peerSettings.getInitialWindowSize();
            long l2 = l;
            arrhttp2Stream = http2Stream;
            if (n2 != -1) {
                l2 = l;
                arrhttp2Stream = http2Stream;
                if (n2 != n) {
                    l = n2 - n;
                    if (!Http2Connection.this.receivedInitialPeerSettings) {
                        Http2Connection.this.addBytesToWriteWindow(l);
                        Http2Connection.this.receivedInitialPeerSettings = true;
                    }
                    l2 = l;
                    arrhttp2Stream = http2Stream;
                    if (!Http2Connection.this.streams.isEmpty()) {
                        arrhttp2Stream = Http2Connection.this.streams.values().toArray(new Http2Stream[Http2Connection.this.streams.size()]);
                        l2 = l;
                    }
                }
            }
            executor.execute(new NamedRunnable("OkHttp %s settings", new Object[]{Http2Connection.this.hostname}){

                @Override
                public void execute() {
                    Http2Connection.this.listener.onSettings(Http2Connection.this);
                }
            });
            // MONITOREXIT : http2Connection
            if (arrhttp2Stream == null) return;
            if (l2 == 0L) return;
            n2 = arrhttp2Stream.length;
            n = 0;
            while (n < n2) {
                http2Stream = arrhttp2Stream[n];
                // MONITORENTER : http2Stream
                http2Stream.addBytesToWriteWindow(l2);
                // MONITOREXIT : http2Stream
                ++n;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void windowUpdate(int n, long l) {
            if (n == 0) {
                Http2Connection http2Connection = Http2Connection.this;
                synchronized (http2Connection) {
                    Http2Connection http2Connection2 = Http2Connection.this;
                    http2Connection2.bytesLeftInWriteWindow += l;
                    Http2Connection.this.notifyAll();
                    return;
                }
            }
            Http2Stream http2Stream = Http2Connection.this.getStream(n);
            if (http2Stream != null) {
                synchronized (http2Stream) {
                    http2Stream.addBytesToWriteWindow(l);
                    return;
                }
            }
        }

    }

}

