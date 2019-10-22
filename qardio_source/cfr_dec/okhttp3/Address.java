/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Authenticator;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionSpec;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.internal.Util;

public final class Address {
    final CertificatePinner certificatePinner;
    final List<ConnectionSpec> connectionSpecs;
    final Dns dns;
    final HostnameVerifier hostnameVerifier;
    final List<Protocol> protocols;
    final Proxy proxy;
    final Authenticator proxyAuthenticator;
    final ProxySelector proxySelector;
    final SocketFactory socketFactory;
    final SSLSocketFactory sslSocketFactory;
    final HttpUrl url;

    /*
     * Enabled aggressive block sorting
     */
    public Address(String string2, int n, Dns dns, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, CertificatePinner certificatePinner, Authenticator authenticator, Proxy proxy, List<Protocol> list, List<ConnectionSpec> list2, ProxySelector proxySelector) {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        String string3 = sSLSocketFactory != null ? "https" : "http";
        this.url = builder.scheme(string3).host(string2).port(n).build();
        if (dns == null) {
            throw new NullPointerException("dns == null");
        }
        this.dns = dns;
        if (socketFactory == null) {
            throw new NullPointerException("socketFactory == null");
        }
        this.socketFactory = socketFactory;
        if (authenticator == null) {
            throw new NullPointerException("proxyAuthenticator == null");
        }
        this.proxyAuthenticator = authenticator;
        if (list == null) {
            throw new NullPointerException("protocols == null");
        }
        this.protocols = Util.immutableList(list);
        if (list2 == null) {
            throw new NullPointerException("connectionSpecs == null");
        }
        this.connectionSpecs = Util.immutableList(list2);
        if (proxySelector == null) {
            throw new NullPointerException("proxySelector == null");
        }
        this.proxySelector = proxySelector;
        this.proxy = proxy;
        this.sslSocketFactory = sSLSocketFactory;
        this.hostnameVerifier = hostnameVerifier;
        this.certificatePinner = certificatePinner;
    }

    public CertificatePinner certificatePinner() {
        return this.certificatePinner;
    }

    public List<ConnectionSpec> connectionSpecs() {
        return this.connectionSpecs;
    }

    public Dns dns() {
        return this.dns;
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object instanceof Address) {
            object = (Address)object;
            bl2 = bl;
            if (this.url.equals(((Address)object).url)) {
                bl2 = bl;
                if (this.dns.equals(((Address)object).dns)) {
                    bl2 = bl;
                    if (this.proxyAuthenticator.equals(((Address)object).proxyAuthenticator)) {
                        bl2 = bl;
                        if (this.protocols.equals(((Address)object).protocols)) {
                            bl2 = bl;
                            if (this.connectionSpecs.equals(((Address)object).connectionSpecs)) {
                                bl2 = bl;
                                if (this.proxySelector.equals(((Address)object).proxySelector)) {
                                    bl2 = bl;
                                    if (Util.equal(this.proxy, ((Address)object).proxy)) {
                                        bl2 = bl;
                                        if (Util.equal(this.sslSocketFactory, ((Address)object).sslSocketFactory)) {
                                            bl2 = bl;
                                            if (Util.equal(this.hostnameVerifier, ((Address)object).hostnameVerifier)) {
                                                bl2 = bl;
                                                if (Util.equal(this.certificatePinner, ((Address)object).certificatePinner)) {
                                                    bl2 = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        int n2 = this.url.hashCode();
        int n3 = this.dns.hashCode();
        int n4 = this.proxyAuthenticator.hashCode();
        int n5 = this.protocols.hashCode();
        int n6 = this.connectionSpecs.hashCode();
        int n7 = this.proxySelector.hashCode();
        int n8 = this.proxy != null ? this.proxy.hashCode() : 0;
        int n9 = this.sslSocketFactory != null ? this.sslSocketFactory.hashCode() : 0;
        int n10 = this.hostnameVerifier != null ? this.hostnameVerifier.hashCode() : 0;
        if (this.certificatePinner != null) {
            n = this.certificatePinner.hashCode();
        }
        return (((((((((n2 + 527) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n;
    }

    public HostnameVerifier hostnameVerifier() {
        return this.hostnameVerifier;
    }

    public List<Protocol> protocols() {
        return this.protocols;
    }

    public Proxy proxy() {
        return this.proxy;
    }

    public Authenticator proxyAuthenticator() {
        return this.proxyAuthenticator;
    }

    public ProxySelector proxySelector() {
        return this.proxySelector;
    }

    public SocketFactory socketFactory() {
        return this.socketFactory;
    }

    public SSLSocketFactory sslSocketFactory() {
        return this.sslSocketFactory;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append("Address{").append(this.url.host()).append(":").append(this.url.port());
        if (this.proxy != null) {
            stringBuilder.append(", proxy=").append(this.proxy);
        } else {
            stringBuilder.append(", proxySelector=").append(this.proxySelector);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public HttpUrl url() {
        return this.url;
    }
}

