/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.platform;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.platform.AndroidPlatform;
import okhttp3.internal.platform.Jdk9Platform;
import okhttp3.internal.platform.JdkWithJettyBootPlatform;
import okhttp3.internal.tls.BasicCertificateChainCleaner;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;
import okio.Buffer;

public class Platform {
    private static final Platform PLATFORM = Platform.findPlatform();
    private static final Logger logger = Logger.getLogger(OkHttpClient.class.getName());

    /*
     * Enabled aggressive block sorting
     */
    public static List<String> alpnProtocolNames(List<Protocol> list) {
        ArrayList<String> arrayList = new ArrayList<String>(list.size());
        int n = 0;
        int n2 = list.size();
        while (n < n2) {
            Protocol protocol = list.get(n);
            if (protocol != Protocol.HTTP_1_0) {
                arrayList.add(protocol.toString());
            }
            ++n;
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    static byte[] concatLengthPrefixed(List<Protocol> list) {
        Buffer buffer = new Buffer();
        int n = 0;
        int n2 = list.size();
        while (n < n2) {
            Protocol protocol = list.get(n);
            if (protocol != Protocol.HTTP_1_0) {
                buffer.writeByte(protocol.toString().length());
                buffer.writeUtf8(protocol.toString());
            }
            ++n;
        }
        return buffer.readByteArray();
    }

    private static Platform findPlatform() {
        Platform platform = AndroidPlatform.buildIfSupported();
        if (platform != null) {
            return platform;
        }
        platform = Jdk9Platform.buildIfSupported();
        if (platform != null) {
            return platform;
        }
        platform = JdkWithJettyBootPlatform.buildIfSupported();
        if (platform != null) {
            return platform;
        }
        return new Platform();
    }

    public static Platform get() {
        return PLATFORM;
    }

    public void afterHandshake(SSLSocket sSLSocket) {
    }

    public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager x509TrustManager) {
        return new BasicCertificateChainCleaner(TrustRootIndex.get(x509TrustManager));
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, String string2, List<Protocol> list) {
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int n) throws IOException {
        socket.connect(inetSocketAddress, n);
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public String getSelectedProtocol(SSLSocket sSLSocket) {
        return null;
    }

    public Object getStackTraceForCloseable(String string2) {
        if (logger.isLoggable(Level.FINE)) {
            return new Throwable(string2);
        }
        return null;
    }

    public boolean isCleartextTrafficPermitted(String string2) {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void log(int n, String string2, Throwable throwable) {
        Level level = n == 5 ? Level.WARNING : Level.INFO;
        logger.log(level, string2, throwable);
    }

    public void logCloseableLeak(String string2, Object object) {
        String string3 = string2;
        if (object == null) {
            string3 = string2 + " To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);";
        }
        this.log(5, string3, (Throwable)object);
    }
}

