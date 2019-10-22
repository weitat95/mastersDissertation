/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import okhttp3.MediaType;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;

public abstract class ResponseBody
implements Closeable {
    private Reader reader;

    private Charset charset() {
        MediaType mediaType = this.contentType();
        if (mediaType != null) {
            return mediaType.charset(Util.UTF_8);
        }
        return Util.UTF_8;
    }

    public static ResponseBody create(final MediaType mediaType, final long l, final BufferedSource bufferedSource) {
        if (bufferedSource == null) {
            throw new NullPointerException("source == null");
        }
        return new ResponseBody(){

            @Override
            public long contentLength() {
                return l;
            }

            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public BufferedSource source() {
                return bufferedSource;
            }
        };
    }

    public static ResponseBody create(MediaType mediaType, byte[] arrby) {
        Buffer buffer = new Buffer().write(arrby);
        return ResponseBody.create(mediaType, arrby.length, buffer);
    }

    public final Reader charStream() {
        Reader reader = this.reader;
        if (reader != null) {
            return reader;
        }
        this.reader = reader = new BomAwareReader(this.source(), this.charset());
        return reader;
    }

    @Override
    public void close() {
        Util.closeQuietly(this.source());
    }

    public abstract long contentLength();

    public abstract MediaType contentType();

    public abstract BufferedSource source();

    public final String string() throws IOException {
        BufferedSource bufferedSource = this.source();
        try {
            String string2 = bufferedSource.readString(Util.bomAwareCharset(bufferedSource, this.charset()));
            return string2;
        }
        finally {
            Util.closeQuietly(bufferedSource);
        }
    }

    static final class BomAwareReader
    extends Reader {
        private final Charset charset;
        private boolean closed;
        private Reader delegate;
        private final BufferedSource source;

        BomAwareReader(BufferedSource bufferedSource, Charset charset) {
            this.source = bufferedSource;
            this.charset = charset;
        }

        @Override
        public void close() throws IOException {
            this.closed = true;
            if (this.delegate != null) {
                this.delegate.close();
                return;
            }
            this.source.close();
        }

        @Override
        public int read(char[] arrc, int n, int n2) throws IOException {
            if (this.closed) {
                throw new IOException("Stream closed");
            }
            Reader reader = this.delegate;
            Object object = reader;
            if (reader == null) {
                object = Util.bomAwareCharset(this.source, this.charset);
                this.delegate = object = new InputStreamReader(this.source.inputStream(), (Charset)object);
            }
            return ((Reader)object).read(arrc, n, n2);
        }
    }

}

