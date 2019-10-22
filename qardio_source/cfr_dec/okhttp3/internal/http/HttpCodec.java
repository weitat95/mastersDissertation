/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Sink;

public interface HttpCodec {
    public void cancel();

    public Sink createRequestBody(Request var1, long var2);

    public void finishRequest() throws IOException;

    public void flushRequest() throws IOException;

    public ResponseBody openResponseBody(Response var1) throws IOException;

    public Response.Builder readResponseHeaders(boolean var1) throws IOException;

    public void writeRequestHeaders(Request var1) throws IOException;
}

