/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.zip.GZIPInputStream;

public class HttpRequest {
    private static ConnectionFactory CONNECTION_FACTORY;
    private static final String[] EMPTY_STRINGS;
    private int bufferSize = 8192;
    private HttpURLConnection connection = null;
    private String httpProxyHost;
    private int httpProxyPort;
    private boolean ignoreCloseExceptions = true;
    private boolean multipart;
    private RequestOutputStream output;
    private final String requestMethod;
    private boolean uncompress = false;
    public final URL url;

    static {
        EMPTY_STRINGS = new String[0];
        CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
    }

    public HttpRequest(CharSequence charSequence, String string2) throws HttpRequestException {
        try {
            this.url = new URL(charSequence.toString());
            this.requestMethod = string2;
            return;
        }
        catch (MalformedURLException malformedURLException) {
            throw new HttpRequestException(malformedURLException);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static StringBuilder addParamPrefix(String string2, StringBuilder stringBuilder) {
        int n = string2.indexOf(63);
        int n2 = stringBuilder.length() - 1;
        if (n == -1) {
            stringBuilder.append('?');
            return stringBuilder;
        } else {
            if (n >= n2 || string2.charAt(n2) == '&') return stringBuilder;
            {
                stringBuilder.append('&');
                return stringBuilder;
            }
        }
    }

    private static StringBuilder addPathSeparator(String string2, StringBuilder stringBuilder) {
        if (string2.indexOf(58) + 2 == string2.lastIndexOf(47)) {
            stringBuilder.append('/');
        }
        return stringBuilder;
    }

    public static String append(CharSequence charSequence, Map<?, ?> object) {
        Object object2 = charSequence.toString();
        if (object == null || object.isEmpty()) {
            return object2;
        }
        charSequence = new StringBuilder((String)object2);
        HttpRequest.addPathSeparator((String)object2, (StringBuilder)charSequence);
        HttpRequest.addParamPrefix((String)object2, (StringBuilder)charSequence);
        object = object.entrySet().iterator();
        object2 = (Map.Entry)object.next();
        ((StringBuilder)charSequence).append(object2.getKey().toString());
        ((StringBuilder)charSequence).append('=');
        object2 = object2.getValue();
        if (object2 != null) {
            ((StringBuilder)charSequence).append(object2);
        }
        while (object.hasNext()) {
            ((StringBuilder)charSequence).append('&');
            object2 = (Map.Entry)object.next();
            ((StringBuilder)charSequence).append(object2.getKey().toString());
            ((StringBuilder)charSequence).append('=');
            if ((object2 = object2.getValue()) == null) continue;
            ((StringBuilder)charSequence).append(object2);
        }
        return ((StringBuilder)charSequence).toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private HttpURLConnection createConnection() {
        try {
            HttpURLConnection httpURLConnection = this.httpProxyHost != null ? CONNECTION_FACTORY.create(this.url, this.createProxy()) : CONNECTION_FACTORY.create(this.url);
            httpURLConnection.setRequestMethod(this.requestMethod);
            return httpURLConnection;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    private Proxy createProxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.httpProxyHost, this.httpProxyPort));
    }

    public static HttpRequest delete(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, "DELETE");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String encode(CharSequence object) throws HttpRequestException {
        int n;
        URL uRL;
        Object object2;
        block4: {
            try {
                uRL = new URL(object.toString());
                object2 = uRL.getHost();
                n = uRL.getPort();
                object = object2;
                if (n == -1) break block4;
                object = (String)object2 + ':' + Integer.toString(n);
            }
            catch (IOException iOException) {
                throw new HttpRequestException(iOException);
            }
        }
        try {}
        catch (URISyntaxException uRISyntaxException) {
            object2 = new IOException("Parsing URI failed");
            ((Throwable)object2).initCause(uRISyntaxException);
            throw new HttpRequestException((IOException)object2);
        }
        object2 = new URI(uRL.getProtocol(), (String)object, uRL.getPath(), uRL.getQuery(), null).toASCIIString();
        n = ((String)object2).indexOf(63);
        object = object2;
        if (n <= 0) return object;
        object = object2;
        if (n + 1 >= ((String)object2).length()) return object;
        return ((String)object2).substring(0, n + 1) + ((String)object2).substring(n + 1).replace("+", "%2B");
    }

    public static HttpRequest get(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, "GET");
    }

    public static HttpRequest get(CharSequence object, Map<?, ?> object2, boolean bl) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.get((CharSequence)object);
    }

    private static String getValidCharset(String string2) {
        if (string2 != null && string2.length() > 0) {
            return string2;
        }
        return "UTF-8";
    }

    public static HttpRequest post(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, "POST");
    }

    public static HttpRequest post(CharSequence object, Map<?, ?> object2, boolean bl) {
        object = object2 = HttpRequest.append((CharSequence)object, object2);
        if (bl) {
            object = HttpRequest.encode((CharSequence)object2);
        }
        return HttpRequest.post((CharSequence)object);
    }

    public static HttpRequest put(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, "PUT");
    }

    public String body() throws HttpRequestException {
        return this.body(this.charset());
    }

    public String body(String string2) throws HttpRequestException {
        ByteArrayOutputStream byteArrayOutputStream = this.byteStream();
        try {
            this.copy(this.buffer(), byteArrayOutputStream);
            string2 = byteArrayOutputStream.toString(HttpRequest.getValidCharset(string2));
            return string2;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public BufferedInputStream buffer() throws HttpRequestException {
        return new BufferedInputStream(this.stream(), this.bufferSize);
    }

    protected ByteArrayOutputStream byteStream() {
        int n = this.contentLength();
        if (n > 0) {
            return new ByteArrayOutputStream(n);
        }
        return new ByteArrayOutputStream();
    }

    public String charset() {
        return this.parameter("Content-Type", "charset");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected HttpRequest closeOutput() throws IOException {
        block6: {
            if (this.output == null) {
                return this;
            }
            if (this.multipart) {
                this.output.write("\r\n--00content0boundary00--\r\n");
            }
            if (this.ignoreCloseExceptions) {
                this.output.close();
                ** GOTO lbl13
            } else {
                this.output.close();
            }
            break block6;
            catch (IOException var1_1) {}
        }
        this.output = null;
        return this;
    }

    protected HttpRequest closeOutputQuietly() throws HttpRequestException {
        try {
            HttpRequest httpRequest = this.closeOutput();
            return httpRequest;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public int code() throws HttpRequestException {
        try {
            this.closeOutput();
            int n = this.getConnection().getResponseCode();
            return n;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest connectTimeout(int n) {
        this.getConnection().setConnectTimeout(n);
        return this;
    }

    public String contentEncoding() {
        return this.header("Content-Encoding");
    }

    public int contentLength() {
        return this.intHeader("Content-Length");
    }

    public HttpRequest contentType(String string2) {
        return this.contentType(string2, null);
    }

    public HttpRequest contentType(String string2, String string3) {
        if (string3 != null && string3.length() > 0) {
            return this.header("Content-Type", string2 + "; charset=" + string3);
        }
        return this.header("Content-Type", string2);
    }

    protected HttpRequest copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        return (HttpRequest)new CloseOperation<HttpRequest>((Closeable)inputStream, this.ignoreCloseExceptions){

            @Override
            public HttpRequest run() throws IOException {
                int n;
                byte[] arrby = new byte[HttpRequest.this.bufferSize];
                while ((n = inputStream.read(arrby)) != -1) {
                    outputStream.write(arrby, 0, n);
                }
                return HttpRequest.this;
            }
        }.call();
    }

    public HttpURLConnection getConnection() {
        if (this.connection == null) {
            this.connection = this.createConnection();
        }
        return this.connection;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected String getParam(String string2, String string3) {
        int n;
        if (string2 == null) return null;
        if (string2.length() == 0) {
            return null;
        }
        int n2 = string2.length();
        int n3 = string2.indexOf(59) + 1;
        if (n3 == 0) return null;
        if (n3 == n2) {
            return null;
        }
        int n4 = n = string2.indexOf(59, n3);
        int n5 = n3;
        if (n == -1) {
            n4 = n2;
            n5 = n3;
        }
        while (n5 < n4) {
            String string4;
            n3 = string2.indexOf(61, n5);
            if (n3 != -1 && n3 < n4 && string3.equals(string2.substring(n5, n3).trim()) && (n5 = (string4 = string2.substring(n3 + 1, n4).trim()).length()) != 0) {
                string2 = string4;
                if (n5 <= 2) return string2;
                string2 = string4;
                if ('\"' != string4.charAt(0)) return string2;
                string2 = string4;
                if ('\"' != string4.charAt(n5 - 1)) return string2;
                return string4.substring(1, n5 - 1);
            }
            n3 = n4 + 1;
            n4 = n = string2.indexOf(59, n3);
            n5 = n3;
            if (n != -1) continue;
            n4 = n2;
            n5 = n3;
        }
        return null;
    }

    public HttpRequest header(String string2, String string3) {
        this.getConnection().setRequestProperty(string2, string3);
        return this;
    }

    public HttpRequest header(Map.Entry<String, String> entry) {
        return this.header(entry.getKey(), entry.getValue());
    }

    public String header(String string2) throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderField(string2);
    }

    public int intHeader(String string2) throws HttpRequestException {
        return this.intHeader(string2, -1);
    }

    public int intHeader(String string2, int n) throws HttpRequestException {
        this.closeOutputQuietly();
        return this.getConnection().getHeaderFieldInt(string2, n);
    }

    public String method() {
        return this.getConnection().getRequestMethod();
    }

    public boolean ok() throws HttpRequestException {
        return 200 == this.code();
    }

    protected HttpRequest openOutput() throws IOException {
        if (this.output != null) {
            return this;
        }
        this.getConnection().setDoOutput(true);
        String string2 = this.getParam(this.getConnection().getRequestProperty("Content-Type"), "charset");
        this.output = new RequestOutputStream(this.getConnection().getOutputStream(), string2, this.bufferSize);
        return this;
    }

    public String parameter(String string2, String string3) {
        return this.getParam(this.header(string2), string3);
    }

    public HttpRequest part(String string2, Number number) throws HttpRequestException {
        return this.part(string2, null, number);
    }

    public HttpRequest part(String string2, String string3) {
        return this.part(string2, null, string3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public HttpRequest part(String string2, String string3, Number object) throws HttpRequestException {
        if (object != null) {
            object = object.toString();
            do {
                return this.part(string2, string3, (String)object);
                break;
            } while (true);
        }
        object = null;
        return this.part(string2, string3, (String)object);
    }

    public HttpRequest part(String string2, String string3, String string4) throws HttpRequestException {
        return this.part(string2, string3, null, string4);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public HttpRequest part(String var1_1, String var2_7, String var3_10, File var4_11) throws HttpRequestException {
        var5_12 = null;
        var6_13 = null;
        var4_11 = new BufferedInputStream(new FileInputStream((File)var4_11));
        var1_1 = this.part((String)var1_1, var2_7, var3_10, (InputStream)var4_11);
        if (var4_11 == null) return var1_1;
        var4_11.close();
        return var1_1;
        {
            catch (IOException var2_8) {
                return var1_1;
            }
        }
        catch (IOException var1_2) {
            block11: {
                var5_12 = var6_13;
                break block11;
                catch (Throwable var1_5) {
                    var5_12 = var4_11;
                    ** GOTO lbl-1000
                }
                catch (IOException var1_6) {
                    var5_12 = var4_11;
                }
            }
            try {
                throw new HttpRequestException((IOException)var1_1);
            }
            catch (Throwable var1_3) lbl-1000:
            // 2 sources
            {
                if (var5_12 == null) throw var1_4;
                try {
                    var5_12.close();
                }
                catch (IOException var2_9) {
                    throw var1_4;
                }
                throw var1_4;
            }
        }
    }

    public HttpRequest part(String string2, String string3, String string4, InputStream inputStream) throws HttpRequestException {
        try {
            this.startPart();
            this.writePartHeader(string2, string3, string4);
            this.copy(inputStream, this.output);
            return this;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest part(String string2, String string3, String string4, String string5) throws HttpRequestException {
        try {
            this.startPart();
            this.writePartHeader(string2, string3, string4);
            this.output.write(string5);
            return this;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public HttpRequest partHeader(String string2, String string3) throws HttpRequestException {
        return this.send(string2).send(": ").send(string3).send("\r\n");
    }

    public HttpRequest send(CharSequence charSequence) throws HttpRequestException {
        try {
            this.openOutput();
            this.output.write(charSequence.toString());
            return this;
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    protected HttpRequest startPart() throws IOException {
        if (!this.multipart) {
            this.multipart = true;
            this.contentType("multipart/form-data; boundary=00content0boundary00").openOutput();
            this.output.write("--00content0boundary00\r\n");
            return this;
        }
        this.output.write("\r\n--00content0boundary00\r\n");
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputStream stream() throws HttpRequestException {
        InputStream inputStream;
        InputStream inputStream2;
        if (this.code() < 400) {
            try {
                inputStream2 = this.getConnection().getInputStream();
            }
            catch (IOException iOException) {
                throw new HttpRequestException(iOException);
            }
        }
        inputStream2 = inputStream = this.getConnection().getErrorStream();
        if (inputStream == null) {
            try {
                inputStream2 = this.getConnection().getInputStream();
            }
            catch (IOException iOException) {
                throw new HttpRequestException(iOException);
            }
        }
        if (!this.uncompress) return inputStream2;
        if (!"gzip".equals(this.contentEncoding())) {
            return inputStream2;
        }
        try {
            return new GZIPInputStream(inputStream2);
        }
        catch (IOException iOException) {
            throw new HttpRequestException(iOException);
        }
    }

    public String toString() {
        return this.method() + ' ' + this.url();
    }

    public URL url() {
        return this.getConnection().getURL();
    }

    public HttpRequest useCaches(boolean bl) {
        this.getConnection().setUseCaches(bl);
        return this;
    }

    protected HttpRequest writePartHeader(String string2, String string3, String string4) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("form-data; name=\"").append(string2);
        if (string3 != null) {
            stringBuilder.append("\"; filename=\"").append(string3);
        }
        stringBuilder.append('\"');
        this.partHeader("Content-Disposition", stringBuilder.toString());
        if (string4 != null) {
            this.partHeader("Content-Type", string4);
        }
        return this.send("\r\n");
    }

    protected static abstract class CloseOperation<V>
    extends Operation<V> {
        private final Closeable closeable;
        private final boolean ignoreCloseExceptions;

        protected CloseOperation(Closeable closeable, boolean bl) {
            this.closeable = closeable;
            this.ignoreCloseExceptions = bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected void done() throws IOException {
            if (this.closeable instanceof Flushable) {
                ((Flushable)((Object)this.closeable)).flush();
            }
            if (!this.ignoreCloseExceptions) {
                this.closeable.close();
                return;
            }
            try {
                this.closeable.close();
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }
    }

    public static interface ConnectionFactory {
        public static final ConnectionFactory DEFAULT = new ConnectionFactory(){

            @Override
            public HttpURLConnection create(URL uRL) throws IOException {
                return (HttpURLConnection)uRL.openConnection();
            }

            @Override
            public HttpURLConnection create(URL uRL, Proxy proxy) throws IOException {
                return (HttpURLConnection)uRL.openConnection(proxy);
            }
        };

        public HttpURLConnection create(URL var1) throws IOException;

        public HttpURLConnection create(URL var1, Proxy var2) throws IOException;

    }

    public static class HttpRequestException
    extends RuntimeException {
        protected HttpRequestException(IOException iOException) {
            super(iOException);
        }

        @Override
        public IOException getCause() {
            return (IOException)super.getCause();
        }
    }

    protected static abstract class Operation<V>
    implements Callable<V> {
        protected Operation() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V call() throws HttpRequestException {
            boolean bl = false;
            try {
                V v = this.run();
                return v;
            }
            catch (HttpRequestException httpRequestException) {
                bl = true;
                throw httpRequestException;
            }
            catch (IOException iOException) {
                bl = true;
                throw new HttpRequestException(iOException);
            }
            finally {
                this.done();
            }
        }

        protected abstract void done() throws IOException;

        protected abstract V run() throws HttpRequestException, IOException;
    }

    public static class RequestOutputStream
    extends BufferedOutputStream {
        private final CharsetEncoder encoder;

        public RequestOutputStream(OutputStream outputStream, String string2, int n) {
            super(outputStream, n);
            this.encoder = Charset.forName(HttpRequest.getValidCharset(string2)).newEncoder();
        }

        public RequestOutputStream write(String object) throws IOException {
            object = this.encoder.encode(CharBuffer.wrap((CharSequence)object));
            super.write(((ByteBuffer)object).array(), 0, ((Buffer)object).limit());
            return this;
        }
    }

}

