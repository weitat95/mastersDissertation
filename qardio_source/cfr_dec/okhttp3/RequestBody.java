/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.io.IOException;
import java.nio.charset.Charset;
import okhttp3.MediaType;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;

public abstract class RequestBody {
    public static RequestBody create(MediaType mediaType, String string2) {
        Charset charset = Util.UTF_8;
        MediaType mediaType2 = mediaType;
        if (mediaType != null) {
            Charset charset2;
            charset = charset2 = mediaType.charset();
            mediaType2 = mediaType;
            if (charset2 == null) {
                charset = Util.UTF_8;
                mediaType2 = MediaType.parse(mediaType + "; charset=utf-8");
            }
        }
        return RequestBody.create(mediaType2, string2.getBytes(charset));
    }

    public static RequestBody create(final MediaType mediaType, final ByteString byteString) {
        return new RequestBody(){

            @Override
            public long contentLength() throws IOException {
                return byteString.size();
            }

            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(byteString);
            }
        };
    }

    public static RequestBody create(MediaType mediaType, byte[] arrby) {
        return RequestBody.create(mediaType, arrby, 0, arrby.length);
    }

    public static RequestBody create(final MediaType mediaType, final byte[] arrby, final int n, final int n2) {
        if (arrby == null) {
            throw new NullPointerException("content == null");
        }
        Util.checkOffsetAndCount(arrby.length, n, n2);
        return new RequestBody(){

            @Override
            public long contentLength() {
                return n2;
            }

            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(arrby, n, n2);
            }
        };
    }

    public long contentLength() throws IOException {
        return -1L;
    }

    public abstract MediaType contentType();

    public abstract void writeTo(BufferedSink var1) throws IOException;

}

