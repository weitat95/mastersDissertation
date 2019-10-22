/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Settings;
import okhttp3.internal.http2.StreamResetException;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http2Stream {
    static final /* synthetic */ boolean $assertionsDisabled;
    long bytesLeftInWriteWindow;
    final Http2Connection connection;
    ErrorCode errorCode = null;
    private boolean hasResponseHeaders;
    final int id;
    final StreamTimeout readTimeout = new StreamTimeout();
    private final List<Header> requestHeaders;
    private List<Header> responseHeaders;
    final FramingSink sink;
    private final FramingSource source;
    long unacknowledgedBytesRead = 0L;
    final StreamTimeout writeTimeout = new StreamTimeout();

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Http2Stream.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    Http2Stream(int n, Http2Connection http2Connection, boolean bl, boolean bl2, List<Header> list) {
        if (http2Connection == null) {
            throw new NullPointerException("connection == null");
        }
        if (list == null) {
            throw new NullPointerException("requestHeaders == null");
        }
        this.id = n;
        this.connection = http2Connection;
        this.bytesLeftInWriteWindow = http2Connection.peerSettings.getInitialWindowSize();
        this.source = new FramingSource(http2Connection.okHttpSettings.getInitialWindowSize());
        this.sink = new FramingSink();
        this.source.finished = bl2;
        this.sink.finished = bl;
        this.requestHeaders = list;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean closeInternal(ErrorCode errorCode) {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            }
            if (this.source.finished && this.sink.finished) {
                return false;
            }
            this.errorCode = errorCode;
            this.notifyAll();
        }
        this.connection.removeStream(this.id);
        return true;
    }

    void addBytesToWriteWindow(long l) {
        this.bytesLeftInWriteWindow += l;
        if (l > 0L) {
            this.notifyAll();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void cancelStreamIfNecessary() throws IOException {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        // MONITORENTER : this
        boolean bl = !this.source.finished && this.source.closed && (this.sink.finished || this.sink.closed);
        boolean bl2 = this.isOpen();
        // MONITOREXIT : this
        if (bl) {
            this.close(ErrorCode.CANCEL);
            return;
        }
        if (bl2) return;
        this.connection.removeStream(this.id);
    }

    void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        }
        if (this.sink.finished) {
            throw new IOException("stream finished");
        }
        if (this.errorCode != null) {
            throw new StreamResetException(this.errorCode);
        }
    }

    public void close(ErrorCode errorCode) throws IOException {
        if (!this.closeInternal(errorCode)) {
            return;
        }
        this.connection.writeSynReset(this.id, errorCode);
    }

    public void closeLater(ErrorCode errorCode) {
        if (!this.closeInternal(errorCode)) {
            return;
        }
        this.connection.writeSynResetLater(this.id, errorCode);
    }

    public int getId() {
        return this.id;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Sink getSink() {
        synchronized (this) {
            if (!this.hasResponseHeaders && !this.isLocallyInitiated()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
            return this.sink;
        }
    }

    public Source getSource() {
        return this.source;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isLocallyInitiated() {
        boolean bl;
        if ((this.id & 1) == 1) {
            bl = true;
            do {
                return this.connection.client == bl;
                break;
            } while (true);
        }
        bl = false;
        return this.connection.client == bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isOpen() {
        boolean bl = false;
        synchronized (this) {
            boolean bl2;
            block6: {
                ErrorCode errorCode = this.errorCode;
                if (errorCode == null) break block6;
                return bl;
            }
            if (!this.source.finished) {
                if (!this.source.closed) return true;
            }
            if (!this.sink.finished) {
                if (!this.sink.closed) return true;
            }
            if (bl2 = this.hasResponseHeaders) return bl;
            return true;
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    void receiveData(BufferedSource bufferedSource, int n) throws IOException {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        this.source.receive(bufferedSource, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void receiveFin() {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        // MONITORENTER : this
        this.source.finished = true;
        boolean bl = this.isOpen();
        this.notifyAll();
        // MONITOREXIT : this
        if (bl) return;
        this.connection.removeStream(this.id);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void receiveHeaders(List<Header> list) {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        boolean bl = true;
        // MONITORENTER : this
        this.hasResponseHeaders = true;
        if (this.responseHeaders == null) {
            this.responseHeaders = list;
            bl = this.isOpen();
            this.notifyAll();
        } else {
            ArrayList<Header> arrayList = new ArrayList<Header>();
            arrayList.addAll(this.responseHeaders);
            arrayList.add(null);
            arrayList.addAll(list);
            this.responseHeaders = arrayList;
        }
        // MONITOREXIT : this
        if (bl) return;
        this.connection.removeStream(this.id);
    }

    void receiveRstStream(ErrorCode errorCode) {
        synchronized (this) {
            if (this.errorCode == null) {
                this.errorCode = errorCode;
                this.notifyAll();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<Header> takeResponseHeaders() throws IOException {
        synchronized (this) {
            if (!this.isLocallyInitiated()) {
                throw new IllegalStateException("servers cannot read response headers");
            }
            this.readTimeout.enter();
            try {
                while (this.responseHeaders == null && this.errorCode == null) {
                    this.waitForIo();
                }
            }
            finally {
                this.readTimeout.exitAndThrowIfTimedOut();
            }
            List<Header> list = this.responseHeaders;
            if (list != null) {
                this.responseHeaders = null;
                return list;
            }
            throw new StreamResetException(this.errorCode);
        }
    }

    void waitForIo() throws InterruptedIOException {
        try {
            this.wait();
            return;
        }
        catch (InterruptedException interruptedException) {
            throw new InterruptedIOException();
        }
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }

    final class FramingSink
    implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled;
        boolean closed;
        boolean finished;
        private final Buffer sendBuffer = new Buffer();

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !Http2Stream.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        FramingSink() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void emitFrame(boolean bl) throws IOException {
            long l;
            Object object = Http2Stream.this;
            synchronized (object) {
                Http2Stream.this.writeTimeout.enter();
                try {
                    while (Http2Stream.this.bytesLeftInWriteWindow <= 0L && !this.finished && !this.closed && Http2Stream.this.errorCode == null) {
                        Http2Stream.this.waitForIo();
                    }
                }
                finally {
                    Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                }
                Http2Stream.this.checkOutNotClosed();
                l = Math.min(Http2Stream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                Http2Stream http2Stream = Http2Stream.this;
                http2Stream.bytesLeftInWriteWindow -= l;
            }
            Http2Stream.this.writeTimeout.enter();
            try {
                object = Http2Stream.this.connection;
                int n = Http2Stream.this.id;
                bl = bl && l == this.sendBuffer.size();
                ((Http2Connection)object).writeData(n, bl, this.sendBuffer, l);
                return;
            }
            finally {
                Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
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
        public void close() throws IOException {
            if (!$assertionsDisabled && Thread.holdsLock(Http2Stream.this)) {
                throw new AssertionError();
            }
            Http2Stream http2Stream = Http2Stream.this;
            // MONITORENTER : http2Stream
            if (this.closed) {
                // MONITOREXIT : http2Stream
                return;
            }
            // MONITOREXIT : http2Stream
            if (!Http2Stream.this.sink.finished) {
                if (this.sendBuffer.size() > 0L) {
                    while (this.sendBuffer.size() > 0L) {
                        this.emitFrame(true);
                    }
                } else {
                    Http2Stream.this.connection.writeData(Http2Stream.this.id, true, null, 0L);
                }
            }
            http2Stream = Http2Stream.this;
            // MONITORENTER : http2Stream
            this.closed = true;
            // MONITOREXIT : http2Stream
            Http2Stream.this.connection.flush();
            Http2Stream.this.cancelStreamIfNecessary();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void flush() throws IOException {
            if (!$assertionsDisabled && Thread.holdsLock(Http2Stream.this)) {
                throw new AssertionError();
            }
            Http2Stream http2Stream = Http2Stream.this;
            synchronized (http2Stream) {
                Http2Stream.this.checkOutNotClosed();
            }
            while (this.sendBuffer.size() > 0L) {
                this.emitFrame(false);
                Http2Stream.this.connection.flush();
            }
            return;
        }

        @Override
        public Timeout timeout() {
            return Http2Stream.this.writeTimeout;
        }

        @Override
        public void write(Buffer buffer, long l) throws IOException {
            if (!$assertionsDisabled && Thread.holdsLock(Http2Stream.this)) {
                throw new AssertionError();
            }
            this.sendBuffer.write(buffer, l);
            while (this.sendBuffer.size() >= 16384L) {
                this.emitFrame(false);
            }
        }
    }

    private final class FramingSource
    implements Source {
        static final /* synthetic */ boolean $assertionsDisabled;
        boolean closed;
        boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer;
        private final Buffer receiveBuffer = new Buffer();

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !Http2Stream.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        FramingSource(long l) {
            this.readBuffer = new Buffer();
            this.maxByteCount = l;
        }

        private void checkNotClosed() throws IOException {
            if (this.closed) {
                throw new IOException("stream closed");
            }
            if (Http2Stream.this.errorCode != null) {
                throw new StreamResetException(Http2Stream.this.errorCode);
            }
        }

        private void waitUntilReadable() throws IOException {
            Http2Stream.this.readTimeout.enter();
            try {
                while (this.readBuffer.size() == 0L && !this.finished && !this.closed && Http2Stream.this.errorCode == null) {
                    Http2Stream.this.waitForIo();
                }
            }
            finally {
                Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void close() throws IOException {
            Http2Stream http2Stream = Http2Stream.this;
            synchronized (http2Stream) {
                this.closed = true;
                this.readBuffer.clear();
                Http2Stream.this.notifyAll();
            }
            Http2Stream.this.cancelStreamIfNecessary();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public long read(Buffer object, long l) throws IOException {
            if (l < 0L) {
                throw new IllegalArgumentException("byteCount < 0: " + l);
            }
            Object object2 = Http2Stream.this;
            synchronized (object2) {
                this.waitUntilReadable();
                this.checkNotClosed();
                if (this.readBuffer.size() == 0L) {
                    return -1L;
                }
                l = this.readBuffer.read((Buffer)object, Math.min(l, this.readBuffer.size()));
                object = Http2Stream.this;
                ((Http2Stream)object).unacknowledgedBytesRead += l;
                if (Http2Stream.this.unacknowledgedBytesRead >= (long)(Http2Stream.this.connection.okHttpSettings.getInitialWindowSize() / 2)) {
                    Http2Stream.this.connection.writeWindowUpdateLater(Http2Stream.this.id, Http2Stream.this.unacknowledgedBytesRead);
                    Http2Stream.this.unacknowledgedBytesRead = 0L;
                }
            }
            object = Http2Stream.this.connection;
            synchronized (object) {
                object2 = Http2Stream.this.connection;
                ((Http2Connection)object2).unacknowledgedBytesRead += l;
                if (Http2Stream.this.connection.unacknowledgedBytesRead >= (long)(Http2Stream.this.connection.okHttpSettings.getInitialWindowSize() / 2)) {
                    Http2Stream.this.connection.writeWindowUpdateLater(0, Http2Stream.this.connection.unacknowledgedBytesRead);
                    Http2Stream.this.connection.unacknowledgedBytesRead = 0L;
                }
                return l;
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        void receive(BufferedSource var1_1, long var2_2) throws IOException {
            var5_3 = var2_2;
            if (FramingSource.$assertionsDisabled) ** GOTO lbl15
            var5_3 = var2_2;
            if (!Thread.holdsLock(Http2Stream.this)) ** GOTO lbl15
            throw new AssertionError();
lbl-1000:
            // 1 sources
            {
                var5_3 -= var2_2;
                var8_6 = Http2Stream.this;
                // MONITORENTER : var8_6
                var4_4 = this.readBuffer.size() == 0L;
                this.readBuffer.writeAll(this.receiveBuffer);
                if (var4_4) {
                    Http2Stream.this.notifyAll();
                }
                // MONITOREXIT : var8_6
lbl15:
                // 3 sources
                if (var5_3 <= 0L) return;
                var8_6 = Http2Stream.this;
                // MONITORENTER : var8_6
                var7_5 = this.finished;
                var4_4 = this.readBuffer.size() + var5_3 > this.maxByteCount;
                // MONITOREXIT : var8_6
                if (var4_4) {
                    var1_1.skip(var5_3);
                    Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                }
                if (!var7_5) continue;
                var1_1.skip(var5_3);
                return;
                ** while ((var2_2 = var1_1.read((Buffer)this.receiveBuffer, (long)var5_3)) != -1L)
            }
lbl29:
            // 1 sources
            throw new EOFException();
        }

        @Override
        public Timeout timeout() {
            return Http2Stream.this.readTimeout;
        }
    }

    class StreamTimeout
    extends AsyncTimeout {
        StreamTimeout() {
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (this.exit()) {
                throw this.newTimeoutException(null);
            }
        }

        @Override
        protected IOException newTimeoutException(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        @Override
        protected void timedOut() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
        }
    }

}

