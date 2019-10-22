/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.ServiceMethod;
import retrofit2.Utils;

final class OkHttpCall<T>
implements Call<T> {
    private final Object[] args;
    private volatile boolean canceled;
    private Throwable creationFailure;
    private boolean executed;
    private okhttp3.Call rawCall;
    private final ServiceMethod<T> serviceMethod;

    OkHttpCall(ServiceMethod<T> serviceMethod, Object[] arrobject) {
        this.serviceMethod = serviceMethod;
        this.args = arrobject;
    }

    private okhttp3.Call createRawCall() throws IOException {
        Object object = this.serviceMethod.toRequest(this.args);
        if ((object = this.serviceMethod.callFactory.newCall((Request)object)) == null) {
            throw new NullPointerException("Call.Factory returned null.");
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void cancel() {
        this.canceled = true;
        // MONITORENTER : this
        okhttp3.Call call = this.rawCall;
        // MONITOREXIT : this
        if (call == null) return;
        call.cancel();
    }

    @Override
    public OkHttpCall<T> clone() {
        return new OkHttpCall<T>(this.serviceMethod, this.args);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public Response<T> execute() throws IOException {
        // MONITORENTER : this
        if (this.executed) {
            throw new IllegalStateException("Already executed.");
        }
        this.executed = true;
        if (this.creationFailure != null) {
            if (this.creationFailure instanceof IOException == false) throw (RuntimeException)this.creationFailure;
            throw (IOException)this.creationFailure;
        }
        var1_2 = var2_1 = this.rawCall;
        if (var2_1 == null) {
            this.rawCall = var1_2 = this.createRawCall();
            // MONITOREXIT : this
        }
        if (this.canceled == false) return this.parseResponse(var1_2.execute());
        var1_2.cancel();
        return this.parseResponse(var1_2.execute());
        catch (IOException var1_3) {}
        ** GOTO lbl-1000
        catch (RuntimeException var1_5) {}
lbl-1000:
        // 2 sources
        {
            this.creationFailure = var1_4;
            throw var1_4;
        }
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    Response<T> parseResponse(okhttp3.Response object) throws IOException {
        Object object2 = ((okhttp3.Response)object).body();
        okhttp3.Response response = ((okhttp3.Response)object).newBuilder().body(new NoContentResponseBody(((ResponseBody)object2).contentType(), ((ResponseBody)object2).contentLength())).build();
        int n = response.code();
        if (n < 200 || n >= 300) {
            try {
                object = Response.error(Utils.buffer((ResponseBody)object2), response);
                return object;
            }
            finally {
                ((ResponseBody)object2).close();
            }
        }
        if (n == 204 || n == 205) {
            return Response.success(null, response);
        }
        object = new ExceptionCatchingRequestBody((ResponseBody)object2);
        try {
            object2 = Response.success(this.serviceMethod.toResponse((ResponseBody)object), response);
            return object2;
        }
        catch (RuntimeException runtimeException) {
            ((ExceptionCatchingRequestBody)object).throwIfCaught();
            throw runtimeException;
        }
    }

    static final class ExceptionCatchingRequestBody
    extends ResponseBody {
        private final ResponseBody delegate;
        IOException thrownException;

        ExceptionCatchingRequestBody(ResponseBody responseBody) {
            this.delegate = responseBody;
        }

        @Override
        public void close() {
            this.delegate.close();
        }

        @Override
        public long contentLength() {
            return this.delegate.contentLength();
        }

        @Override
        public MediaType contentType() {
            return this.delegate.contentType();
        }

        @Override
        public BufferedSource source() {
            return Okio.buffer(new ForwardingSource(this.delegate.source()){

                @Override
                public long read(Buffer buffer, long l) throws IOException {
                    try {
                        l = super.read(buffer, l);
                        return l;
                    }
                    catch (IOException iOException) {
                        ExceptionCatchingRequestBody.this.thrownException = iOException;
                        throw iOException;
                    }
                }
            });
        }

        void throwIfCaught() throws IOException {
            if (this.thrownException != null) {
                throw this.thrownException;
            }
        }

    }

    static final class NoContentResponseBody
    extends ResponseBody {
        private final long contentLength;
        private final MediaType contentType;

        NoContentResponseBody(MediaType mediaType, long l) {
            this.contentType = mediaType;
            this.contentLength = l;
        }

        @Override
        public long contentLength() {
            return this.contentLength;
        }

        @Override
        public MediaType contentType() {
            return this.contentType;
        }

        @Override
        public BufferedSource source() {
            throw new IllegalStateException("Cannot read raw response body of a converted body.");
        }
    }

}

