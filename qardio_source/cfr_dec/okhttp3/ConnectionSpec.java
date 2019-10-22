/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLSocket;
import okhttp3.CipherSuite;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;

public final class ConnectionSpec {
    private static final CipherSuite[] APPROVED_CIPHER_SUITES = new CipherSuite[]{CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
    public static final ConnectionSpec CLEARTEXT;
    public static final ConnectionSpec COMPATIBLE_TLS;
    public static final ConnectionSpec MODERN_TLS;
    final String[] cipherSuites;
    final boolean supportsTlsExtensions;
    final boolean tls;
    final String[] tlsVersions;

    static {
        MODERN_TLS = new Builder(true).cipherSuites(APPROVED_CIPHER_SUITES).tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
        COMPATIBLE_TLS = new Builder(MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
        CLEARTEXT = new Builder(false).build();
    }

    ConnectionSpec(Builder builder) {
        this.tls = builder.tls;
        this.cipherSuites = builder.cipherSuites;
        this.tlsVersions = builder.tlsVersions;
        this.supportsTlsExtensions = builder.supportsTlsExtensions;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean nonEmptyIntersection(String[] arrstring, String[] arrstring2) {
        if (arrstring != null && arrstring2 != null && arrstring.length != 0 && arrstring2.length != 0) {
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                if (Util.indexOf(arrstring2, arrstring[i]) == -1) continue;
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private ConnectionSpec supportedSpec(SSLSocket sSLSocket, boolean bl) {
        String[] arrstring = this.cipherSuites != null ? Util.intersect(String.class, this.cipherSuites, sSLSocket.getEnabledCipherSuites()) : sSLSocket.getEnabledCipherSuites();
        String[] arrstring2 = this.tlsVersions != null ? Util.intersect(String.class, this.tlsVersions, sSLSocket.getEnabledProtocols()) : sSLSocket.getEnabledProtocols();
        String[] arrstring3 = arrstring;
        if (bl) {
            arrstring3 = arrstring;
            if (Util.indexOf(sSLSocket.getSupportedCipherSuites(), "TLS_FALLBACK_SCSV") != -1) {
                arrstring3 = Util.concat(arrstring, "TLS_FALLBACK_SCSV");
            }
        }
        return new Builder(this).cipherSuites(arrstring3).tlsVersions(arrstring2).build();
    }

    void apply(SSLSocket sSLSocket, boolean bl) {
        ConnectionSpec connectionSpec = this.supportedSpec(sSLSocket, bl);
        if (connectionSpec.tlsVersions != null) {
            sSLSocket.setEnabledProtocols(connectionSpec.tlsVersions);
        }
        if (connectionSpec.cipherSuites != null) {
            sSLSocket.setEnabledCipherSuites(connectionSpec.cipherSuites);
        }
    }

    public List<CipherSuite> cipherSuites() {
        if (this.cipherSuites == null) {
            return null;
        }
        ArrayList<CipherSuite> arrayList = new ArrayList<CipherSuite>(this.cipherSuites.length);
        String[] arrstring = this.cipherSuites;
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(CipherSuite.forJavaName(arrstring[i]));
        }
        return Collections.unmodifiableList(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (!(object instanceof ConnectionSpec)) break block4;
                if (object == this) {
                    return true;
                }
                object = (ConnectionSpec)object;
                if (this.tls == ((ConnectionSpec)object).tls && (!this.tls || Arrays.equals(this.cipherSuites, ((ConnectionSpec)object).cipherSuites) && Arrays.equals(this.tlsVersions, ((ConnectionSpec)object).tlsVersions) && this.supportsTlsExtensions == ((ConnectionSpec)object).supportsTlsExtensions)) break block5;
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
        int n = 17;
        if (!this.tls) return n;
        int n2 = Arrays.hashCode(this.cipherSuites);
        int n3 = Arrays.hashCode(this.tlsVersions);
        if (this.supportsTlsExtensions) {
            n = 0;
            do {
                return ((n2 + 527) * 31 + n3) * 31 + n;
                break;
            } while (true);
        }
        n = 1;
        return ((n2 + 527) * 31 + n3) * 31 + n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isCompatible(SSLSocket sSLSocket) {
        return this.tls && (this.tlsVersions == null || ConnectionSpec.nonEmptyIntersection(this.tlsVersions, sSLSocket.getEnabledProtocols())) && (this.cipherSuites == null || ConnectionSpec.nonEmptyIntersection(this.cipherSuites, sSLSocket.getEnabledCipherSuites()));
    }

    public boolean isTls() {
        return this.tls;
    }

    public boolean supportsTlsExtensions() {
        return this.supportsTlsExtensions;
    }

    public List<TlsVersion> tlsVersions() {
        if (this.tlsVersions == null) {
            return null;
        }
        ArrayList<TlsVersion> arrayList = new ArrayList<TlsVersion>(this.tlsVersions.length);
        String[] arrstring = this.tlsVersions;
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(TlsVersion.forJavaName(arrstring[i]));
        }
        return Collections.unmodifiableList(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        String string2;
        if (!this.tls) {
            return "ConnectionSpec()";
        }
        String string3 = this.cipherSuites != null ? this.cipherSuites().toString() : "[all enabled]";
        if (this.tlsVersions != null) {
            string2 = this.tlsVersions().toString();
            return "ConnectionSpec(cipherSuites=" + string3 + ", tlsVersions=" + string2 + ", supportsTlsExtensions=" + this.supportsTlsExtensions + ")";
        }
        string2 = "[all enabled]";
        return "ConnectionSpec(cipherSuites=" + string3 + ", tlsVersions=" + string2 + ", supportsTlsExtensions=" + this.supportsTlsExtensions + ")";
    }

    public static final class Builder {
        String[] cipherSuites;
        boolean supportsTlsExtensions;
        boolean tls;
        String[] tlsVersions;

        public Builder(ConnectionSpec connectionSpec) {
            this.tls = connectionSpec.tls;
            this.cipherSuites = connectionSpec.cipherSuites;
            this.tlsVersions = connectionSpec.tlsVersions;
            this.supportsTlsExtensions = connectionSpec.supportsTlsExtensions;
        }

        Builder(boolean bl) {
            this.tls = bl;
        }

        public ConnectionSpec build() {
            return new ConnectionSpec(this);
        }

        public Builder cipherSuites(String ... arrstring) {
            if (!this.tls) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            if (arrstring.length == 0) {
                throw new IllegalArgumentException("At least one cipher suite is required");
            }
            this.cipherSuites = (String[])arrstring.clone();
            return this;
        }

        public Builder cipherSuites(CipherSuite ... arrcipherSuite) {
            if (!this.tls) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            String[] arrstring = new String[arrcipherSuite.length];
            for (int i = 0; i < arrcipherSuite.length; ++i) {
                arrstring[i] = arrcipherSuite[i].javaName;
            }
            return this.cipherSuites(arrstring);
        }

        public Builder supportsTlsExtensions(boolean bl) {
            if (!this.tls) {
                throw new IllegalStateException("no TLS extensions for cleartext connections");
            }
            this.supportsTlsExtensions = bl;
            return this;
        }

        public Builder tlsVersions(String ... arrstring) {
            if (!this.tls) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            }
            if (arrstring.length == 0) {
                throw new IllegalArgumentException("At least one TLS version is required");
            }
            this.tlsVersions = (String[])arrstring.clone();
            return this;
        }

        public Builder tlsVersions(TlsVersion ... arrtlsVersion) {
            if (!this.tls) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            }
            String[] arrstring = new String[arrtlsVersion.length];
            for (int i = 0; i < arrtlsVersion.length; ++i) {
                arrstring[i] = arrtlsVersion[i].javaName;
            }
            return this.tlsVersions(arrstring);
        }
    }

}

