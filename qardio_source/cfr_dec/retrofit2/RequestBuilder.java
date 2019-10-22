/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

final class RequestBuilder {
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final HttpUrl baseUrl;
    private RequestBody body;
    private MediaType contentType;
    private FormBody.Builder formBuilder;
    private final boolean hasBody;
    private final String method;
    private MultipartBody.Builder multipartBuilder;
    private String relativeUrl;
    private final Request.Builder requestBuilder;
    private HttpUrl.Builder urlBuilder;

    /*
     * Enabled aggressive block sorting
     */
    RequestBuilder(String string2, HttpUrl httpUrl, String string3, Headers headers, MediaType mediaType, boolean bl, boolean bl2, boolean bl3) {
        this.method = string2;
        this.baseUrl = httpUrl;
        this.relativeUrl = string3;
        this.requestBuilder = new Request.Builder();
        this.contentType = mediaType;
        this.hasBody = bl;
        if (headers != null) {
            this.requestBuilder.headers(headers);
        }
        if (bl2) {
            this.formBuilder = new FormBody.Builder();
            return;
        } else {
            if (!bl3) return;
            {
                this.multipartBuilder = new MultipartBody.Builder();
                this.multipartBuilder.setType(MultipartBody.FORM);
                return;
            }
        }
    }

    private static String canonicalizeForPath(String string2, boolean bl) {
        int n = 0;
        int n2 = string2.length();
        do {
            int n3;
            block4: {
                Object object;
                block3: {
                    object = string2;
                    if (n >= n2) break block3;
                    n3 = string2.codePointAt(n);
                    if (n3 >= 32 && n3 < 127 && " \"<>^`{}|\\?#".indexOf(n3) == -1 && (bl || n3 != 47 && n3 != 37)) break block4;
                    object = new Buffer();
                    ((Buffer)object).writeUtf8(string2, 0, n);
                    RequestBuilder.canonicalizeForPath((Buffer)object, string2, n, n2, bl);
                    object = ((Buffer)object).readUtf8();
                }
                return object;
            }
            n += Character.charCount(n3);
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void canonicalizeForPath(Buffer var0, String var1_1, int var2_2, int var3_3, boolean var4_4) {
        var7_5 = null;
        block0 : while (var2_2 < var3_3) {
            block9: {
                block7: {
                    block8: {
                        var5_6 = var1_1.codePointAt(var2_2);
                        if (!var4_4) break block7;
                        var9_9 = var7_5;
                        if (var5_6 == 9) break block8;
                        var9_9 = var7_5;
                        if (var5_6 == 10) break block8;
                        var9_9 = var7_5;
                        if (var5_6 == 12) break block8;
                        if (var5_6 != 13) break block7;
                        var9_9 = var7_5;
                    }
lbl14:
                    // 3 sources
                    do {
                        var2_2 += Character.charCount(var5_6);
                        var7_5 = var9_9;
                        continue block0;
                        break;
                    } while (true);
                }
                if (var5_6 >= 32 && var5_6 < 127 && " \"<>^`{}|\\?#".indexOf(var5_6) == -1 && (var4_4 || var5_6 != 47 && var5_6 != 37)) break block9;
                var8_8 = var7_5;
                if (var7_5 == null) {
                    var8_8 = new Buffer();
                }
                var8_8.writeUtf8CodePoint(var5_6);
                do {
                    var9_9 = var8_8;
                    if (var8_8.exhausted()) ** GOTO lbl14
                    var6_7 = var8_8.readByte() & 255;
                    var0.writeByte(37);
                    var0.writeByte(RequestBuilder.HEX_DIGITS[var6_7 >> 4 & 15]);
                    var0.writeByte(RequestBuilder.HEX_DIGITS[var6_7 & 15]);
                } while (true);
            }
            var0.writeUtf8CodePoint(var5_6);
            var9_9 = var7_5;
            ** continue;
        }
    }

    void addFormField(String string2, String string3, boolean bl) {
        if (bl) {
            this.formBuilder.addEncoded(string2, string3);
            return;
        }
        this.formBuilder.add(string2, string3);
    }

    void addHeader(String object, String string2) {
        if ("Content-Type".equalsIgnoreCase((String)object)) {
            object = MediaType.parse(string2);
            if (object == null) {
                throw new IllegalArgumentException("Malformed content type: " + string2);
            }
            this.contentType = object;
            return;
        }
        this.requestBuilder.addHeader((String)object, string2);
    }

    void addPart(Headers headers, RequestBody requestBody) {
        this.multipartBuilder.addPart(headers, requestBody);
    }

    void addPart(MultipartBody.Part part) {
        this.multipartBuilder.addPart(part);
    }

    void addPathParam(String string2, String string3, boolean bl) {
        if (this.relativeUrl == null) {
            throw new AssertionError();
        }
        this.relativeUrl = this.relativeUrl.replace("{" + string2 + "}", RequestBuilder.canonicalizeForPath(string3, bl));
    }

    void addQueryParam(String string2, String string3, boolean bl) {
        if (this.relativeUrl != null) {
            this.urlBuilder = this.baseUrl.newBuilder(this.relativeUrl);
            if (this.urlBuilder == null) {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.baseUrl + ", Relative: " + this.relativeUrl);
            }
            this.relativeUrl = null;
        }
        if (bl) {
            this.urlBuilder.addEncodedQueryParameter(string2, string3);
            return;
        }
        this.urlBuilder.addQueryParameter(string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    Request build() {
        Object object;
        Object object2;
        Object object3 = this.urlBuilder;
        if (object3 != null) {
            object = ((HttpUrl.Builder)object3).build();
        } else {
            object = object3 = this.baseUrl.resolve(this.relativeUrl);
            if (object3 == null) {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.baseUrl + ", Relative: " + this.relativeUrl);
            }
        }
        object3 = object2 = this.body;
        if (object2 == null) {
            if (this.formBuilder != null) {
                object3 = this.formBuilder.build();
            } else if (this.multipartBuilder != null) {
                object3 = this.multipartBuilder.build();
            } else {
                object3 = object2;
                if (this.hasBody) {
                    object3 = RequestBody.create(null, new byte[0]);
                }
            }
        }
        MediaType mediaType = this.contentType;
        object2 = object3;
        if (mediaType == null) return this.requestBuilder.url((HttpUrl)object).method(this.method, (RequestBody)object2).build();
        if (object3 != null) {
            object2 = new ContentTypeOverridingRequestBody((RequestBody)object3, mediaType);
            return this.requestBuilder.url((HttpUrl)object).method(this.method, (RequestBody)object2).build();
        }
        this.requestBuilder.addHeader("Content-Type", mediaType.toString());
        object2 = object3;
        return this.requestBuilder.url((HttpUrl)object).method(this.method, (RequestBody)object2).build();
    }

    void setBody(RequestBody requestBody) {
        this.body = requestBody;
    }

    void setRelativeUrl(Object object) {
        if (object == null) {
            throw new NullPointerException("@Url parameter is null.");
        }
        this.relativeUrl = object.toString();
    }

    private static class ContentTypeOverridingRequestBody
    extends RequestBody {
        private final MediaType contentType;
        private final RequestBody delegate;

        ContentTypeOverridingRequestBody(RequestBody requestBody, MediaType mediaType) {
            this.delegate = requestBody;
            this.contentType = mediaType;
        }

        @Override
        public long contentLength() throws IOException {
            return this.delegate.contentLength();
        }

        @Override
        public MediaType contentType() {
            return this.contentType;
        }

        @Override
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            this.delegate.writeTo(bufferedSink);
        }
    }

}

