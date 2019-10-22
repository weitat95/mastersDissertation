/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSocket;
import okhttp3.ConnectionSpec;
import okhttp3.internal.Internal;

public final class ConnectionSpecSelector {
    private final List<ConnectionSpec> connectionSpecs;
    private boolean isFallback;
    private boolean isFallbackPossible;
    private int nextModeIndex = 0;

    public ConnectionSpecSelector(List<ConnectionSpec> list) {
        this.connectionSpecs = list;
    }

    private boolean isFallbackPossible(SSLSocket sSLSocket) {
        for (int i = this.nextModeIndex; i < this.connectionSpecs.size(); ++i) {
            if (!this.connectionSpecs.get(i).isCompatible(sSLSocket)) continue;
            return true;
        }
        return false;
    }

    public ConnectionSpec configureSecureSocket(SSLSocket sSLSocket) throws IOException {
        ConnectionSpec connectionSpec;
        ConnectionSpec connectionSpec2 = null;
        int n = this.nextModeIndex;
        int n2 = this.connectionSpecs.size();
        do {
            block4: {
                block3: {
                    connectionSpec = connectionSpec2;
                    if (n >= n2) break block3;
                    connectionSpec = this.connectionSpecs.get(n);
                    if (!connectionSpec.isCompatible(sSLSocket)) break block4;
                    this.nextModeIndex = n + 1;
                }
                if (connectionSpec != null) break;
                throw new UnknownServiceException("Unable to find acceptable protocols. isFallback=" + this.isFallback + ", modes=" + this.connectionSpecs + ", supported protocols=" + Arrays.toString(sSLSocket.getEnabledProtocols()));
            }
            ++n;
        } while (true);
        this.isFallbackPossible = this.isFallbackPossible(sSLSocket);
        Internal.instance.apply(connectionSpec, sSLSocket, this.isFallback);
        return connectionSpec;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean connectionFailed(IOException iOException) {
        this.isFallback = true;
        return this.isFallbackPossible && !(iOException instanceof ProtocolException) && !(iOException instanceof InterruptedIOException) && (!(iOException instanceof SSLHandshakeException) || !(iOException.getCause() instanceof CertificateException)) && !(iOException instanceof SSLPeerUnverifiedException) && (iOException instanceof SSLHandshakeException || iOException instanceof SSLProtocolException);
    }
}

