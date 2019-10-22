/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3.cache;

import com.shopify.buy3.cache.ResponseCacheRecordEditor;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okhttp3.CipherSuite;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Sink;
import okio.Source;

final class ResponseHeaderRecord {
    private static final String RECEIVED_MILLIS;
    private static final String SENT_MILLIS;
    private final int code;
    private final Handshake handshake;
    private final String message;
    private final Protocol protocol;
    private final long receivedResponseMillis;
    private final String requestMethod;
    private final Headers responseHeaders;
    private final long sentRequestMillis;
    private final String url;
    private final Headers varyHeaders;

    static {
        SENT_MILLIS = Platform.get().getPrefix() + "-Sent-Millis";
        RECEIVED_MILLIS = Platform.get().getPrefix() + "-Received-Millis";
    }

    ResponseHeaderRecord(Response response) {
        this.url = response.request().url().toString();
        this.varyHeaders = HttpHeaders.varyHeaders(response);
        this.requestMethod = response.request().method();
        this.protocol = response.protocol();
        this.code = response.code();
        this.message = response.message();
        this.responseHeaders = response.headers();
        this.handshake = response.handshake();
        this.sentRequestMillis = response.sentRequestAtMillis();
        this.receivedResponseMillis = response.receivedResponseAtMillis();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    ResponseHeaderRecord(Source source) throws IOException {
        try {
            int n;
            Object object = Okio.buffer(source);
            this.url = object.readUtf8LineStrict();
            this.requestMethod = object.readUtf8LineStrict();
            Object object2 = new Headers.Builder();
            int n2 = ResponseHeaderRecord.readInt(object);
            for (n = 0; n < n2; ++n) {
                this.addHeaderLenient((Headers.Builder)object2, object.readUtf8LineStrict());
            }
            this.varyHeaders = ((Headers.Builder)object2).build();
            object2 = StatusLine.parse(object.readUtf8LineStrict());
            this.protocol = ((StatusLine)object2).protocol;
            this.code = ((StatusLine)object2).code;
            this.message = ((StatusLine)object2).message;
            object2 = new Headers.Builder();
            n2 = ResponseHeaderRecord.readInt(object);
            for (n = 0; n < n2; ++n) {
                this.addHeaderLenient((Headers.Builder)object2, object.readUtf8LineStrict());
            }
            Object object3 = ((Headers.Builder)object2).get(SENT_MILLIS);
            Object object4 = ((Headers.Builder)object2).get(RECEIVED_MILLIS);
            ((Headers.Builder)object2).removeAll(SENT_MILLIS);
            ((Headers.Builder)object2).removeAll(RECEIVED_MILLIS);
            long l = object3 != null ? Long.parseLong((String)object3) : 0L;
            this.sentRequestMillis = l;
            l = object4 != null ? Long.parseLong((String)object4) : 0L;
            this.receivedResponseMillis = l;
            this.responseHeaders = ((Headers.Builder)object2).build();
            if (this.isHttps()) {
                object2 = object.readUtf8LineStrict();
                if (((String)object2).length() > 0) {
                    throw new IOException("expected \"\" but was \"" + (String)object2 + "\"");
                }
                object2 = CipherSuite.forJavaName(object.readUtf8LineStrict());
                object3 = this.readCertificateList((BufferedSource)object);
                object4 = this.readCertificateList((BufferedSource)object);
                object = !object.exhausted() ? TlsVersion.forJavaName(object.readUtf8LineStrict()) : null;
                this.handshake = Handshake.get((TlsVersion)((Object)object), (CipherSuite)object2, (List<Certificate>)object3, (List<Certificate>)object4);
                return;
            } else {
                this.handshake = null;
            }
            return;
        }
        finally {
            source.close();
        }
    }

    private void addHeaderLenient(Headers.Builder builder, String string2) {
        int n = string2.indexOf(":", 1);
        if (n != -1) {
            builder.add(string2.substring(0, n), string2.substring(n + 1));
            return;
        }
        if (string2.startsWith(":")) {
            builder.add("", string2.substring(1));
            return;
        }
        builder.add("", string2);
    }

    private boolean isHttps() {
        return this.url.startsWith("https://");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private List<Certificate> readCertificateList(BufferedSource bufferedSource) throws IOException {
        CertificateFactory certificateFactory;
        ArrayList<Certificate> arrayList;
        int n;
        int n2 = ResponseHeaderRecord.readInt(bufferedSource);
        if (n2 == -1) {
            return Collections.emptyList();
        }
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            arrayList = new ArrayList<Certificate>(n2);
            n = 0;
        }
        catch (CertificateException certificateException) {
            throw new IOException(certificateException.getMessage());
        }
        do {
            Object object = arrayList;
            if (n >= n2) return object;
            object = bufferedSource.readUtf8LineStrict();
            Buffer buffer = new Buffer();
            buffer.write(ByteString.decodeBase64((String)object));
            arrayList.add(certificateFactory.generateCertificate(buffer.inputStream()));
            ++n;
            continue;
            break;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int readInt(BufferedSource object) throws IOException {
        long l;
        try {
            l = object.readDecimalLong();
            object = object.readUtf8LineStrict();
            if (l < 0L || l > Integer.MAX_VALUE) throw new IOException("expected an int but was \"" + l + (String)object + "\"");
        }
        catch (NumberFormatException numberFormatException) {
            throw new IOException(numberFormatException.getMessage());
        }
        if (((String)object).isEmpty()) return (int)l;
        throw new IOException("expected an int but was \"" + l + (String)object + "\"");
    }

    private void writeCertList(BufferedSink bufferedSink, List<Certificate> list) throws IOException {
        bufferedSink.writeDecimalLong(list.size()).writeByte(10);
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            try {
                bufferedSink.writeUtf8(ByteString.of(list.get(i).getEncoded()).base64()).writeByte(10);
                continue;
            }
            catch (CertificateEncodingException certificateEncodingException) {
                throw new IOException(certificateEncodingException.getMessage());
            }
        }
    }

    Response response() {
        Request request = new Request.Builder().url(this.url).method(this.requestMethod, RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "")).headers(this.varyHeaders).build();
        return new Response.Builder().request(request).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).handshake(this.handshake).sentRequestAtMillis(this.sentRequestMillis).receivedResponseAtMillis(this.receivedResponseMillis).build();
    }

    void writeTo(ResponseCacheRecordEditor object) throws IOException {
        int n;
        object = Okio.buffer(object.headerSink());
        object.writeUtf8(this.url).writeByte(10);
        object.writeUtf8(this.requestMethod).writeByte(10);
        object.writeDecimalLong(this.varyHeaders.size()).writeByte(10);
        int n2 = this.varyHeaders.size();
        for (n = 0; n < n2; ++n) {
            object.writeUtf8(this.varyHeaders.name(n)).writeUtf8(": ").writeUtf8(this.varyHeaders.value(n)).writeByte(10);
        }
        object.writeUtf8(new StatusLine(this.protocol, this.code, this.message).toString()).writeByte(10);
        object.writeDecimalLong(this.responseHeaders.size() + 2).writeByte(10);
        n2 = this.responseHeaders.size();
        for (n = 0; n < n2; ++n) {
            object.writeUtf8(this.responseHeaders.name(n)).writeUtf8(": ").writeUtf8(this.responseHeaders.value(n)).writeByte(10);
        }
        object.writeUtf8(SENT_MILLIS).writeUtf8(": ").writeDecimalLong(this.sentRequestMillis).writeByte(10);
        object.writeUtf8(RECEIVED_MILLIS).writeUtf8(": ").writeDecimalLong(this.receivedResponseMillis).writeByte(10);
        if (this.isHttps()) {
            object.writeByte(10);
            object.writeUtf8(this.handshake.cipherSuite().javaName()).writeByte(10);
            this.writeCertList((BufferedSink)object, this.handshake.peerCertificates());
            this.writeCertList((BufferedSink)object, this.handshake.localCertificates());
            if (this.handshake.tlsVersion() != null) {
                object.writeUtf8(this.handshake.tlsVersion().javaName()).writeByte(10);
            }
        }
        object.close();
    }
}

