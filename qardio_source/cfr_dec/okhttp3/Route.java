/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;

public final class Route {
    final Address address;
    final InetSocketAddress inetSocketAddress;
    final Proxy proxy;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress) {
        if (address == null) {
            throw new NullPointerException("address == null");
        }
        if (proxy == null) {
            throw new NullPointerException("proxy == null");
        }
        if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        }
        this.address = address;
        this.proxy = proxy;
        this.inetSocketAddress = inetSocketAddress;
    }

    public Address address() {
        return this.address;
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        if (object instanceof Route) {
            object = (Route)object;
            bl2 = bl;
            if (this.address.equals(((Route)object).address)) {
                bl2 = bl;
                if (this.proxy.equals(((Route)object).proxy)) {
                    bl2 = bl;
                    if (this.inetSocketAddress.equals(((Route)object).inetSocketAddress)) {
                        bl2 = true;
                    }
                }
            }
        }
        return bl2;
    }

    public int hashCode() {
        return ((this.address.hashCode() + 527) * 31 + this.proxy.hashCode()) * 31 + this.inetSocketAddress.hashCode();
    }

    public Proxy proxy() {
        return this.proxy;
    }

    public boolean requiresTunnel() {
        return this.address.sslSocketFactory != null && this.proxy.type() == Proxy.Type.HTTP;
    }

    public InetSocketAddress socketAddress() {
        return this.inetSocketAddress;
    }

    public String toString() {
        return "Route{" + this.inetSocketAddress + "}";
    }
}

