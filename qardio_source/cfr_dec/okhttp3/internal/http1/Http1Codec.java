/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http1;

import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http1Codec
implements HttpCodec {
    final OkHttpClient client;
    final BufferedSink sink;
    final BufferedSource source;
    int state = 0;
    final StreamAllocation streamAllocation;

    public Http1Codec(OkHttpClient okHttpClient, StreamAllocation streamAllocation, BufferedSource bufferedSource, BufferedSink bufferedSink) {
        this.client = okHttpClient;
        this.streamAllocation = streamAllocation;
        this.source = bufferedSource;
        this.sink = bufferedSink;
    }

    private Source getTransferStream(Response response) throws IOException {
        if (!HttpHeaders.hasBody(response)) {
            return this.newFixedLengthSource(0L);
        }
        if ("chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return this.newChunkedSource(response.request().url());
        }
        long l = HttpHeaders.contentLength(response);
        if (l != -1L) {
            return this.newFixedLengthSource(l);
        }
        return this.newUnknownLengthSource();
    }

    @Override
    public void cancel() {
        RealConnection realConnection = this.streamAllocation.connection();
        if (realConnection != null) {
            realConnection.cancel();
        }
    }

    @Override
    public Sink createRequestBody(Request request, long l) {
        if ("chunked".equalsIgnoreCase(request.header("Transfer-Encoding"))) {
            return this.newChunkedSink();
        }
        if (l != -1L) {
            return this.newFixedLengthSink(l);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    void detachTimeout(ForwardingTimeout forwardingTimeout) {
        Timeout timeout = forwardingTimeout.delegate();
        forwardingTimeout.setDelegate(Timeout.NONE);
        timeout.clearDeadline();
        timeout.clearTimeout();
    }

    @Override
    public void finishRequest() throws IOException {
        this.sink.flush();
    }

    @Override
    public void flushRequest() throws IOException {
        this.sink.flush();
    }

    public Sink newChunkedSink() {
        if (this.state != 1) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 2;
        return new ChunkedSink();
    }

    public Source newChunkedSource(HttpUrl httpUrl) throws IOException {
        if (this.state != 4) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 5;
        return new ChunkedSource(httpUrl);
    }

    public Sink newFixedLengthSink(long l) {
        if (this.state != 1) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 2;
        return new FixedLengthSink(l);
    }

    public Source newFixedLengthSource(long l) throws IOException {
        if (this.state != 4) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.state = 5;
        return new FixedLengthSource(l);
    }

    public Source newUnknownLengthSource() throws IOException {
        if (this.state != 4) {
            throw new IllegalStateException("state: " + this.state);
        }
        if (this.streamAllocation == null) {
            throw new IllegalStateException("streamAllocation == null");
        }
        this.state = 5;
        this.streamAllocation.noNewStreams();
        return new UnknownLengthSource();
    }

    @Override
    public ResponseBody openResponseBody(Response response) throws IOException {
        Source source = this.getTransferStream(response);
        return new RealResponseBody(response.headers(), Okio.buffer(source));
    }

    public Headers readHeaders() throws IOException {
        String string2;
        Headers.Builder builder = new Headers.Builder();
        while ((string2 = this.source.readUtf8LineStrict()).length() != 0) {
            Internal.instance.addLenient(builder, string2);
        }
        return builder.build();
    }

    @Override
    public Response.Builder readResponseHeaders(boolean bl) throws IOException {
        Object object;
        block5: {
            StatusLine statusLine;
            if (this.state != 1 && this.state != 3) {
                throw new IllegalStateException("state: " + this.state);
            }
            try {
                statusLine = StatusLine.parse(this.source.readUtf8LineStrict());
                object = new Response.Builder().protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message).headers(this.readHeaders());
                if (!bl) break block5;
            }
            catch (EOFException eOFException) {
                object = new IOException("unexpected end of stream on " + this.streamAllocation);
                ((Throwable)object).initCause(eOFException);
                throw object;
            }
            if (statusLine.code != 100) break block5;
            return null;
        }
        this.state = 4;
        return object;
    }

    public void writeRequest(Headers headers, String string2) throws IOException {
        if (this.state != 0) {
            throw new IllegalStateException("state: " + this.state);
        }
        this.sink.writeUtf8(string2).writeUtf8("\r\n");
        int n = headers.size();
        for (int i = 0; i < n; ++i) {
            this.sink.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8("\r\n");
        }
        this.sink.writeUtf8("\r\n");
        this.state = 1;
    }

    @Override
    public void writeRequestHeaders(Request request) throws IOException {
        String string2 = RequestLine.get(request, this.streamAllocation.connection().route().proxy().type());
        this.writeRequest(request.headers(), string2);
    }

    private abstract class AbstractSource
    implements Source {
        protected boolean closed;
        protected final ForwardingTimeout timeout;

        private AbstractSource() {
            this.timeout = new ForwardingTimeout(Http1Codec.this.source.timeout());
        }

        /*
         * Enabled aggressive block sorting
         */
        protected final void endOfInput(boolean bl) throws IOException {
            block5: {
                block4: {
                    if (Http1Codec.this.state == 6) break block4;
                    if (Http1Codec.this.state != 5) {
                        throw new IllegalStateException("state: " + Http1Codec.this.state);
                    }
                    Http1Codec.this.detachTimeout(this.timeout);
                    Http1Codec.this.state = 6;
                    if (Http1Codec.this.streamAllocation != null) break block5;
                }
                return;
            }
            StreamAllocation streamAllocation = Http1Codec.this.streamAllocation;
            bl = !bl;
            streamAllocation.streamFinished(bl, Http1Codec.this);
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }
    }

    private final class ChunkedSink
    implements Sink {
        private boolean closed;
        private final ForwardingTimeout timeout;

        ChunkedSink() {
            this.timeout = new ForwardingTimeout(Http1Codec.this.sink.timeout());
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void close() throws IOException {
            synchronized (this) {
                block6: {
                    boolean bl = this.closed;
                    if (!bl) break block6;
                    do {
                        return;
                        break;
                    } while (true);
                }
                this.closed = true;
                Http1Codec.this.sink.writeUtf8("0\r\n\r\n");
                Http1Codec.this.detachTimeout(this.timeout);
                Http1Codec.this.state = 3;
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void flush() throws IOException {
            synchronized (this) {
                block6: {
                    boolean bl = this.closed;
                    if (!bl) break block6;
                    do {
                        return;
                        break;
                    } while (true);
                }
                Http1Codec.this.sink.flush();
                return;
            }
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }

        @Override
        public void write(Buffer buffer, long l) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            if (l == 0L) {
                return;
            }
            Http1Codec.this.sink.writeHexadecimalUnsignedLong(l);
            Http1Codec.this.sink.writeUtf8("\r\n");
            Http1Codec.this.sink.write(buffer, l);
            Http1Codec.this.sink.writeUtf8("\r\n");
        }
    }

    private class ChunkedSource
    extends AbstractSource {
        private long bytesRemainingInChunk = -1L;
        private boolean hasMoreChunks = true;
        private final HttpUrl url;

        ChunkedSource(HttpUrl httpUrl) {
            this.url = httpUrl;
        }

        private void readChunkSize() throws IOException {
            if (this.bytesRemainingInChunk != -1L) {
                Http1Codec.this.source.readUtf8LineStrict();
            }
            try {
                this.bytesRemainingInChunk = Http1Codec.this.source.readHexadecimalUnsignedLong();
                String string2 = Http1Codec.this.source.readUtf8LineStrict().trim();
                if (this.bytesRemainingInChunk < 0L || !string2.isEmpty() && !string2.startsWith(";")) {
                    throw new ProtocolException("expected chunk size and optional extensions but was \"" + this.bytesRemainingInChunk + string2 + "\"");
                }
            }
            catch (NumberFormatException numberFormatException) {
                throw new ProtocolException(numberFormatException.getMessage());
            }
            if (this.bytesRemainingInChunk == 0L) {
                this.hasMoreChunks = false;
                HttpHeaders.receiveHeaders(Http1Codec.this.client.cookieJar(), this.url, Http1Codec.this.readHeaders());
                this.endOfInput(true);
            }
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                this.endOfInput(false);
            }
            this.closed = true;
        }

        @Override
        public long read(Buffer buffer, long l) throws IOException {
            if (l < 0L) {
                throw new IllegalArgumentException("byteCount < 0: " + l);
            }
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            if (!this.hasMoreChunks) {
                return -1L;
            }
            if (this.bytesRemainingInChunk == 0L || this.bytesRemainingInChunk == -1L) {
                this.readChunkSize();
                if (!this.hasMoreChunks) {
                    return -1L;
                }
            }
            if ((l = Http1Codec.this.source.read(buffer, Math.min(l, this.bytesRemainingInChunk))) == -1L) {
                this.endOfInput(false);
                throw new ProtocolException("unexpected end of stream");
            }
            this.bytesRemainingInChunk -= l;
            return l;
        }
    }

    private final class FixedLengthSink
    implements Sink {
        private long bytesRemaining;
        private boolean closed;
        private final ForwardingTimeout timeout;

        FixedLengthSink(long l) {
            this.timeout = new ForwardingTimeout(Http1Codec.this.sink.timeout());
            this.bytesRemaining = l;
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            if (this.bytesRemaining > 0L) {
                throw new ProtocolException("unexpected end of stream");
            }
            Http1Codec.this.detachTimeout(this.timeout);
            Http1Codec.this.state = 3;
        }

        @Override
        public void flush() throws IOException {
            if (this.closed) {
                return;
            }
            Http1Codec.this.sink.flush();
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }

        @Override
        public void write(Buffer buffer, long l) throws IOException {
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            Util.checkOffsetAndCount(buffer.size(), 0L, l);
            if (l > this.bytesRemaining) {
                throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + l);
            }
            Http1Codec.this.sink.write(buffer, l);
            this.bytesRemaining -= l;
        }
    }

    private class FixedLengthSource
    extends AbstractSource {
        private long bytesRemaining;

        public FixedLengthSource(long l) throws IOException {
            this.bytesRemaining = l;
            if (this.bytesRemaining == 0L) {
                this.endOfInput(true);
            }
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (this.bytesRemaining != 0L && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                this.endOfInput(false);
            }
            this.closed = true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public long read(Buffer buffer, long l) throws IOException {
            if (l < 0L) {
                throw new IllegalArgumentException("byteCount < 0: " + l);
            }
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            if (this.bytesRemaining == 0L) {
                return -1L;
            }
            long l2 = Http1Codec.this.source.read(buffer, Math.min(this.bytesRemaining, l));
            if (l2 == -1L) {
                this.endOfInput(false);
                throw new ProtocolException("unexpected end of stream");
            }
            this.bytesRemaining -= l2;
            l = l2;
            if (this.bytesRemaining != 0L) return l;
            this.endOfInput(true);
            return l2;
        }
    }

    private class UnknownLengthSource
    extends AbstractSource {
        private boolean inputExhausted;

        UnknownLengthSource() {
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (!this.inputExhausted) {
                this.endOfInput(false);
            }
            this.closed = true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public long read(Buffer buffer, long l) throws IOException {
            long l2;
            if (l < 0L) {
                throw new IllegalArgumentException("byteCount < 0: " + l);
            }
            if (this.closed) {
                throw new IllegalStateException("closed");
            }
            if (this.inputExhausted) {
                return -1L;
            }
            l = l2 = Http1Codec.this.source.read(buffer, l);
            if (l2 != -1L) return l;
            this.inputExhausted = true;
            this.endOfInput(true);
            return -1L;
        }
    }

}

