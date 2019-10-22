/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;
import okhttp3.internal.tls.CertificateChainCleaner;
import okio.ByteString;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    private final CertificateChainCleaner certificateChainCleaner;
    private final Set<Pin> pins;

    CertificatePinner(Set<Pin> set, CertificateChainCleaner certificateChainCleaner) {
        this.pins = set;
        this.certificateChainCleaner = certificateChainCleaner;
    }

    public static String pin(Certificate certificate) {
        if (!(certificate instanceof X509Certificate)) {
            throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
        }
        return "sha256/" + CertificatePinner.sha256((X509Certificate)certificate).base64();
    }

    static ByteString sha1(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha1();
    }

    static ByteString sha256(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha256();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void check(String object, List<Certificate> object2) throws SSLPeerUnverifiedException {
        List<Pin> list = this.findMatchingPins((String)object);
        if (!list.isEmpty()) {
            List<Certificate> list2 = object2;
            if (this.certificateChainCleaner != null) {
                list2 = this.certificateChainCleaner.clean((List<Certificate>)object2, (String)object);
            }
            int n = 0;
            int n2 = list2.size();
            block0: do {
                int n3;
                X509Certificate x509Certificate;
                int n4;
                Object object3;
                if (n < n2) {
                    x509Certificate = (X509Certificate)list2.get(n);
                    object3 = null;
                    object2 = null;
                    n4 = list.size();
                } else {
                    object2 = new StringBuilder().append("Certificate pinning failure!").append("\n  Peer certificate chain:");
                    n3 = list2.size();
                    for (n = 0; n < n3; ++n) {
                        object3 = (X509Certificate)list2.get(n);
                        ((StringBuilder)object2).append("\n    ").append(CertificatePinner.pin((Certificate)object3)).append(": ").append(((X509Certificate)object3).getSubjectDN().getName());
                    }
                    ((StringBuilder)object2).append("\n  Pinned certificates for ").append((String)object).append(":");
                    n = 0;
                    n3 = list.size();
                    do {
                        if (n >= n3) {
                            throw new SSLPeerUnverifiedException(((StringBuilder)object2).toString());
                        }
                        object = list.get(n);
                        ((StringBuilder)object2).append("\n    ").append(object);
                        ++n;
                    } while (true);
                }
                for (n3 = 0; n3 < n4; ++n3) {
                    Object object4;
                    Pin pin = list.get(n3);
                    if (pin.hashAlgorithm.equals("sha256/")) {
                        object4 = object2;
                        if (object2 == null) {
                            object4 = CertificatePinner.sha256(x509Certificate);
                        }
                        if (pin.hash.equals(object4)) break block0;
                        object2 = object4;
                        continue;
                    }
                    if (!pin.hashAlgorithm.equals("sha1/")) {
                        throw new AssertionError();
                    }
                    object4 = object3;
                    if (object3 == null) {
                        object4 = CertificatePinner.sha1(x509Certificate);
                    }
                    object3 = object4;
                    if (!pin.hash.equals(object4)) continue;
                    return;
                }
                ++n;
            } while (true);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CertificatePinner)) return false;
        if (!Util.equal(this.certificateChainCleaner, ((CertificatePinner)object).certificateChainCleaner)) return false;
        if (!this.pins.equals(((CertificatePinner)object).pins)) return false;
        return true;
    }

    List<Pin> findMatchingPins(String string2) {
        List<Pin> list = Collections.emptyList();
        for (Pin pin : this.pins) {
            if (!pin.matches(string2)) continue;
            List<Pin> list2 = list;
            if (list.isEmpty()) {
                list2 = new ArrayList<Pin>();
            }
            list2.add(pin);
            list = list2;
        }
        return list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        if (this.certificateChainCleaner != null) {
            n = this.certificateChainCleaner.hashCode();
            do {
                return n * 31 + this.pins.hashCode();
                break;
            } while (true);
        }
        n = 0;
        return n * 31 + this.pins.hashCode();
    }

    CertificatePinner withCertificateChainCleaner(CertificateChainCleaner certificateChainCleaner) {
        if (Util.equal(this.certificateChainCleaner, certificateChainCleaner)) {
            return this;
        }
        return new CertificatePinner(this.pins, certificateChainCleaner);
    }

    public static final class Builder {
        private final List<Pin> pins = new ArrayList<Pin>();

        public Builder add(String string2, String ... arrstring) {
            if (string2 == null) {
                throw new NullPointerException("pattern == null");
            }
            for (String string3 : arrstring) {
                this.pins.add(new Pin(string2, string3));
            }
            return this;
        }

        public CertificatePinner build() {
            return new CertificatePinner(new LinkedHashSet<Pin>(this.pins), null);
        }
    }

    static final class Pin {
        final String canonicalHostname;
        final ByteString hash;
        final String hashAlgorithm;
        final String pattern;

        /*
         * Enabled aggressive block sorting
         */
        Pin(String string2, String string3) {
            this.pattern = string2;
            string2 = string2.startsWith("*.") ? HttpUrl.parse("http://" + string2.substring("*.".length())).host() : HttpUrl.parse("http://" + string2).host();
            this.canonicalHostname = string2;
            if (string3.startsWith("sha1/")) {
                this.hashAlgorithm = "sha1/";
                this.hash = ByteString.decodeBase64(string3.substring("sha1/".length()));
            } else {
                if (!string3.startsWith("sha256/")) {
                    throw new IllegalArgumentException("pins must start with 'sha256/' or 'sha1/': " + string3);
                }
                this.hashAlgorithm = "sha256/";
                this.hash = ByteString.decodeBase64(string3.substring("sha256/".length()));
            }
            if (this.hash == null) {
                throw new IllegalArgumentException("pins must be base64: " + string3);
            }
        }

        public boolean equals(Object object) {
            return object instanceof Pin && this.pattern.equals(((Pin)object).pattern) && this.hashAlgorithm.equals(((Pin)object).hashAlgorithm) && this.hash.equals(((Pin)object).hash);
        }

        public int hashCode() {
            return ((this.pattern.hashCode() + 527) * 31 + this.hashAlgorithm.hashCode()) * 31 + this.hash.hashCode();
        }

        boolean matches(String string2) {
            if (this.pattern.startsWith("*.")) {
                return string2.regionMatches(false, string2.indexOf(46) + 1, this.canonicalHostname, 0, this.canonicalHostname.length());
            }
            return string2.equals(this.canonicalHostname);
        }

        public String toString() {
            return this.hashAlgorithm + this.hash.base64();
        }
    }

}

