/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.security.cert.Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import okhttp3.CipherSuite;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;

public final class Handshake {
    private final CipherSuite cipherSuite;
    private final List<Certificate> localCertificates;
    private final List<Certificate> peerCertificates;
    private final TlsVersion tlsVersion;

    private Handshake(TlsVersion tlsVersion, CipherSuite cipherSuite, List<Certificate> list, List<Certificate> list2) {
        this.tlsVersion = tlsVersion;
        this.cipherSuite = cipherSuite;
        this.peerCertificates = list;
        this.localCertificates = list2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Handshake get(SSLSession object) {
        void var1_8;
        void var1_10;
        void var0_3;
        Certificate[] arrcertificate;
        String string2 = object.getCipherSuite();
        if (string2 == null) {
            throw new IllegalStateException("cipherSuite == null");
        }
        CipherSuite cipherSuite = CipherSuite.forJavaName(string2);
        String string3 = object.getProtocol();
        if (string3 == null) {
            throw new IllegalStateException("tlsVersion == null");
        }
        TlsVersion tlsVersion = TlsVersion.forJavaName(string3);
        try {
            Certificate[] arrcertificate2 = object.getPeerCertificates();
        }
        catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
            Object var1_12 = null;
        }
        if (var1_8 != null) {
            List list = Util.immutableList(var1_8);
        } else {
            List list = Collections.emptyList();
        }
        if ((arrcertificate = object.getLocalCertificates()) != null) {
            List<Certificate> list = Util.immutableList(arrcertificate);
            return new Handshake(tlsVersion, cipherSuite, (List<Certificate>)var1_10, (List<Certificate>)var0_3);
        }
        List list = Collections.emptyList();
        return new Handshake(tlsVersion, cipherSuite, (List<Certificate>)var1_10, (List<Certificate>)var0_3);
    }

    public static Handshake get(TlsVersion tlsVersion, CipherSuite cipherSuite, List<Certificate> list, List<Certificate> list2) {
        if (cipherSuite == null) {
            throw new NullPointerException("cipherSuite == null");
        }
        return new Handshake(tlsVersion, cipherSuite, Util.immutableList(list), Util.immutableList(list2));
    }

    public CipherSuite cipherSuite() {
        return this.cipherSuite;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof Handshake)) break block2;
                object = (Handshake)object;
                if (Util.equal(this.cipherSuite, ((Handshake)object).cipherSuite) && this.cipherSuite.equals(((Handshake)object).cipherSuite) && this.peerCertificates.equals(((Handshake)object).peerCertificates) && this.localCertificates.equals(((Handshake)object).localCertificates)) break block3;
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        if (this.tlsVersion != null) {
            n = this.tlsVersion.hashCode();
            do {
                return (((n + 527) * 31 + this.cipherSuite.hashCode()) * 31 + this.peerCertificates.hashCode()) * 31 + this.localCertificates.hashCode();
                break;
            } while (true);
        }
        n = 0;
        return (((n + 527) * 31 + this.cipherSuite.hashCode()) * 31 + this.peerCertificates.hashCode()) * 31 + this.localCertificates.hashCode();
    }

    public List<Certificate> localCertificates() {
        return this.localCertificates;
    }

    public List<Certificate> peerCertificates() {
        return this.peerCertificates;
    }

    public TlsVersion tlsVersion() {
        return this.tlsVersion;
    }
}

