/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Stream;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http2Codec
implements HttpCodec {
    private static final ByteString CONNECTION = ByteString.encodeUtf8("connection");
    private static final ByteString ENCODING;
    private static final ByteString HOST;
    private static final List<ByteString> HTTP_2_SKIPPED_REQUEST_HEADERS;
    private static final List<ByteString> HTTP_2_SKIPPED_RESPONSE_HEADERS;
    private static final ByteString KEEP_ALIVE;
    private static final ByteString PROXY_CONNECTION;
    private static final ByteString TE;
    private static final ByteString TRANSFER_ENCODING;
    private static final ByteString UPGRADE;
    private final OkHttpClient client;
    private final Http2Connection connection;
    private Http2Stream stream;
    final StreamAllocation streamAllocation;

    static {
        HOST = ByteString.encodeUtf8("host");
        KEEP_ALIVE = ByteString.encodeUtf8("keep-alive");
        PROXY_CONNECTION = ByteString.encodeUtf8("proxy-connection");
        TRANSFER_ENCODING = ByteString.encodeUtf8("transfer-encoding");
        TE = ByteString.encodeUtf8("te");
        ENCODING = ByteString.encodeUtf8("encoding");
        UPGRADE = ByteString.encodeUtf8("upgrade");
        HTTP_2_SKIPPED_REQUEST_HEADERS = Util.immutableList(CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TE, TRANSFER_ENCODING, ENCODING, UPGRADE, Header.TARGET_METHOD, Header.TARGET_PATH, Header.TARGET_SCHEME, Header.TARGET_AUTHORITY);
        HTTP_2_SKIPPED_RESPONSE_HEADERS = Util.immutableList(CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TE, TRANSFER_ENCODING, ENCODING, UPGRADE);
    }

    public Http2Codec(OkHttpClient okHttpClient, StreamAllocation streamAllocation, Http2Connection http2Connection) {
        this.client = okHttpClient;
        this.streamAllocation = streamAllocation;
        this.connection = http2Connection;
    }

    public static List<Header> http2HeadersList(Request object) {
        Headers headers = ((Request)object).headers();
        ArrayList<Header> arrayList = new ArrayList<Header>(headers.size() + 4);
        arrayList.add(new Header(Header.TARGET_METHOD, ((Request)object).method()));
        arrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(((Request)object).url())));
        String string2 = ((Request)object).header("Host");
        if (string2 != null) {
            arrayList.add(new Header(Header.TARGET_AUTHORITY, string2));
        }
        arrayList.add(new Header(Header.TARGET_SCHEME, ((Request)object).url().scheme()));
        int n = headers.size();
        for (int i = 0; i < n; ++i) {
            object = ByteString.encodeUtf8(headers.name(i).toLowerCase(Locale.US));
            if (HTTP_2_SKIPPED_REQUEST_HEADERS.contains(object)) continue;
            arrayList.add(new Header((ByteString)object, headers.value(i)));
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Response.Builder readHttp2HeadersList(List<Header> list) throws IOException {
        Header header = null;
        Headers.Builder builder = new Headers.Builder();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            Headers.Builder builder2;
            Object object = list.get(i);
            if (object == null) {
                builder2 = builder;
                object = header;
                if (header != null) {
                    builder2 = builder;
                    object = header;
                    if (((StatusLine)header).code == 100) {
                        object = null;
                        builder2 = new Headers.Builder();
                    }
                }
            } else {
                ByteString byteString = ((Header)object).name;
                String string2 = ((Header)object).value.utf8();
                if (byteString.equals(Header.RESPONSE_STATUS)) {
                    object = StatusLine.parse("HTTP/1.1 " + string2);
                    builder2 = builder;
                } else {
                    builder2 = builder;
                    object = header;
                    if (!HTTP_2_SKIPPED_RESPONSE_HEADERS.contains(byteString)) {
                        Internal.instance.addLenient(builder, byteString.utf8(), string2);
                        builder2 = builder;
                        object = header;
                    }
                }
            }
            builder = builder2;
            header = object;
        }
        if (header == null) {
            throw new ProtocolException("Expected ':status' header not present");
        }
        return new Response.Builder().protocol(Protocol.HTTP_2).code(((StatusLine)header).code).message(((StatusLine)header).message).headers(builder.build());
    }

    @Override
    public void cancel() {
        if (this.stream != null) {
            this.stream.closeLater(ErrorCode.CANCEL);
        }
    }

    @Override
    public Sink createRequestBody(Request request, long l) {
        return this.stream.getSink();
    }

    @Override
    public void finishRequest() throws IOException {
        this.stream.getSink().close();
    }

    @Override
    public void flushRequest() throws IOException {
        this.connection.flush();
    }

    @Override
    public ResponseBody openResponseBody(Response response) throws IOException {
        StreamFinishingSource streamFinishingSource = new StreamFinishingSource(this.stream.getSource());
        return new RealResponseBody(response.headers(), Okio.buffer(streamFinishingSource));
    }

    @Override
    public Response.Builder readResponseHeaders(boolean bl) throws IOException {
        Response.Builder builder;
        Response.Builder builder2 = builder = Http2Codec.readHttp2HeadersList(this.stream.takeResponseHeaders());
        if (bl) {
            builder2 = builder;
            if (Internal.instance.code(builder) == 100) {
                builder2 = null;
            }
        }
        return builder2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeRequestHeaders(Request list) throws IOException {
        if (this.stream != null) {
            return;
        }
        boolean bl = ((Request)((Object)list)).body() != null;
        list = Http2Codec.http2HeadersList(list);
        this.stream = this.connection.newStream(list, bl);
        this.stream.readTimeout().timeout(this.client.readTimeoutMillis(), TimeUnit.MILLISECONDS);
        this.stream.writeTimeout().timeout(this.client.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
    }

    class StreamFinishingSource
    extends ForwardingSource {
        public StreamFinishingSource(Source source) {
            super(source);
        }

        @Override
        public void close() throws IOException {
            Http2Codec.this.streamAllocation.streamFinished(false, Http2Codec.this);
            super.close();
        }
    }

}

