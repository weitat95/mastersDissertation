/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;

public final class MultipartBody
extends RequestBody {
    public static final MediaType ALTERNATIVE;
    private static final byte[] COLONSPACE;
    private static final byte[] CRLF;
    private static final byte[] DASHDASH;
    public static final MediaType DIGEST;
    public static final MediaType FORM;
    public static final MediaType MIXED;
    public static final MediaType PARALLEL;
    private final ByteString boundary;
    private long contentLength = -1L;
    private final MediaType contentType;
    private final MediaType originalType;
    private final List<Part> parts;

    static {
        MIXED = MediaType.parse("multipart/mixed");
        ALTERNATIVE = MediaType.parse("multipart/alternative");
        DIGEST = MediaType.parse("multipart/digest");
        PARALLEL = MediaType.parse("multipart/parallel");
        FORM = MediaType.parse("multipart/form-data");
        COLONSPACE = new byte[]{58, 32};
        CRLF = new byte[]{13, 10};
        DASHDASH = new byte[]{45, 45};
    }

    MultipartBody(ByteString byteString, MediaType mediaType, List<Part> list) {
        this.boundary = byteString;
        this.originalType = mediaType;
        this.contentType = MediaType.parse(mediaType + "; boundary=" + byteString.utf8());
        this.parts = Util.immutableList(list);
    }

    /*
     * Enabled aggressive block sorting
     */
    private long writeOrCountBytes(BufferedSink bufferedSink, boolean bl) throws IOException {
        long l;
        long l2 = 0L;
        Buffer buffer = null;
        if (bl) {
            buffer = new Buffer();
            bufferedSink = buffer;
        }
        int n = this.parts.size();
        for (int i = 0; i < n; ++i) {
            Object object = this.parts.get(i);
            Object object2 = ((Part)object).headers;
            object = ((Part)object).body;
            bufferedSink.write(DASHDASH);
            bufferedSink.write(this.boundary);
            bufferedSink.write(CRLF);
            if (object2 != null) {
                int n2 = ((Headers)object2).size();
                for (int j = 0; j < n2; ++j) {
                    bufferedSink.writeUtf8(((Headers)object2).name(j)).write(COLONSPACE).writeUtf8(((Headers)object2).value(j)).write(CRLF);
                }
            }
            if ((object2 = ((RequestBody)object).contentType()) != null) {
                bufferedSink.writeUtf8("Content-Type: ").writeUtf8(((MediaType)object2).toString()).write(CRLF);
            }
            if ((l = ((RequestBody)object).contentLength()) != -1L) {
                bufferedSink.writeUtf8("Content-Length: ").writeDecimalLong(l).write(CRLF);
            } else if (bl) {
                buffer.clear();
                return -1L;
            }
            bufferedSink.write(CRLF);
            if (bl) {
                l2 += l;
            } else {
                ((RequestBody)object).writeTo(bufferedSink);
            }
            bufferedSink.write(CRLF);
        }
        bufferedSink.write(DASHDASH);
        bufferedSink.write(this.boundary);
        bufferedSink.write(DASHDASH);
        bufferedSink.write(CRLF);
        l = l2;
        if (bl) {
            l = l2 + buffer.size();
            buffer.clear();
        }
        return l;
    }

    @Override
    public long contentLength() throws IOException {
        long l = this.contentLength;
        if (l != -1L) {
            return l;
        }
        this.contentLength = l = this.writeOrCountBytes(null, true);
        return l;
    }

    @Override
    public MediaType contentType() {
        return this.contentType;
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        this.writeOrCountBytes(bufferedSink, false);
    }

    public static final class Builder {
        private final ByteString boundary;
        private final List<Part> parts;
        private MediaType type = MIXED;

        public Builder() {
            this(UUID.randomUUID().toString());
        }

        public Builder(String string2) {
            this.parts = new ArrayList<Part>();
            this.boundary = ByteString.encodeUtf8(string2);
        }

        public Builder addPart(Headers headers, RequestBody requestBody) {
            return this.addPart(Part.create(headers, requestBody));
        }

        public Builder addPart(Part part) {
            if (part == null) {
                throw new NullPointerException("part == null");
            }
            this.parts.add(part);
            return this;
        }

        public MultipartBody build() {
            if (this.parts.isEmpty()) {
                throw new IllegalStateException("Multipart body must have at least one part.");
            }
            return new MultipartBody(this.boundary, this.type, this.parts);
        }

        public Builder setType(MediaType mediaType) {
            if (mediaType == null) {
                throw new NullPointerException("type == null");
            }
            if (!mediaType.type().equals("multipart")) {
                throw new IllegalArgumentException("multipart != " + mediaType);
            }
            this.type = mediaType;
            return this;
        }
    }

    public static final class Part {
        final RequestBody body;
        final Headers headers;

        private Part(Headers headers, RequestBody requestBody) {
            this.headers = headers;
            this.body = requestBody;
        }

        public static Part create(Headers headers, RequestBody requestBody) {
            if (requestBody == null) {
                throw new NullPointerException("body == null");
            }
            if (headers != null && headers.get("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            }
            if (headers != null && headers.get("Content-Length") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Length");
            }
            return new Part(headers, requestBody);
        }
    }

}

