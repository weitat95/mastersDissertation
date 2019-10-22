/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import okhttp3.Address;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RouteDatabase;

public final class RouteSelector {
    private final Address address;
    private List<InetSocketAddress> inetSocketAddresses;
    private InetSocketAddress lastInetSocketAddress;
    private Proxy lastProxy;
    private int nextInetSocketAddressIndex;
    private int nextProxyIndex;
    private final List<Route> postponedRoutes;
    private List<Proxy> proxies = Collections.emptyList();
    private final RouteDatabase routeDatabase;

    public RouteSelector(Address address, RouteDatabase routeDatabase) {
        this.inetSocketAddresses = Collections.emptyList();
        this.postponedRoutes = new ArrayList<Route>();
        this.address = address;
        this.routeDatabase = routeDatabase;
        this.resetNextProxy(address.url(), address.proxy());
    }

    static String getHostString(InetSocketAddress inetSocketAddress) {
        InetAddress inetAddress = inetSocketAddress.getAddress();
        if (inetAddress == null) {
            return inetSocketAddress.getHostName();
        }
        return inetAddress.getHostAddress();
    }

    private boolean hasNextInetSocketAddress() {
        return this.nextInetSocketAddressIndex < this.inetSocketAddresses.size();
    }

    private boolean hasNextPostponed() {
        return !this.postponedRoutes.isEmpty();
    }

    private boolean hasNextProxy() {
        return this.nextProxyIndex < this.proxies.size();
    }

    private InetSocketAddress nextInetSocketAddress() throws IOException {
        if (!this.hasNextInetSocketAddress()) {
            throw new SocketException("No route to " + this.address.url().host() + "; exhausted inet socket addresses: " + this.inetSocketAddresses);
        }
        List<InetSocketAddress> list = this.inetSocketAddresses;
        int n = this.nextInetSocketAddressIndex;
        this.nextInetSocketAddressIndex = n + 1;
        return list.get(n);
    }

    private Route nextPostponed() {
        return this.postponedRoutes.remove(0);
    }

    private Proxy nextProxy() throws IOException {
        if (!this.hasNextProxy()) {
            throw new SocketException("No route to " + this.address.url().host() + "; exhausted proxy configurations: " + this.proxies);
        }
        Object object = this.proxies;
        int n = this.nextProxyIndex;
        this.nextProxyIndex = n + 1;
        object = object.get(n);
        this.resetNextInetSocketAddress((Proxy)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void resetNextInetSocketAddress(Proxy list) throws IOException {
        int n;
        Object object;
        this.inetSocketAddresses = new ArrayList<InetSocketAddress>();
        if (((Proxy)((Object)list)).type() == Proxy.Type.DIRECT || ((Proxy)((Object)list)).type() == Proxy.Type.SOCKS) {
            object = this.address.url().host();
            n = this.address.url().port();
        } else {
            object = ((Proxy)((Object)list)).address();
            if (!(object instanceof InetSocketAddress)) {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + object.getClass());
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress)object;
            object = RouteSelector.getHostString(inetSocketAddress);
            n = inetSocketAddress.getPort();
        }
        if (n < 1 || n > 65535) {
            throw new SocketException("No route to " + (String)object + ":" + n + "; port is out of range");
        }
        if (((Proxy)((Object)list)).type() == Proxy.Type.SOCKS) {
            this.inetSocketAddresses.add(InetSocketAddress.createUnresolved((String)object, n));
        } else {
            list = this.address.dns().lookup((String)object);
            int n2 = list.size();
            for (int i = 0; i < n2; ++i) {
                object = list.get(i);
                this.inetSocketAddresses.add(new InetSocketAddress((InetAddress)object, n));
            }
        }
        this.nextInetSocketAddressIndex = 0;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void resetNextProxy(HttpUrl list, Proxy proxy) {
        void var2_6;
        if (var2_6 != null) {
            this.proxies = Collections.singletonList(var2_6);
        } else {
            void var1_4;
            List<Proxy> list2 = this.address.proxySelector().select(((HttpUrl)((Object)list)).uri());
            if (list2 != null && !list2.isEmpty()) {
                List<Proxy> list3 = Util.immutableList(list2);
            } else {
                List<Proxy> list4 = Util.immutableList(Proxy.NO_PROXY);
            }
            this.proxies = var1_4;
        }
        this.nextProxyIndex = 0;
    }

    public void connectFailed(Route route, IOException iOException) {
        if (route.proxy().type() != Proxy.Type.DIRECT && this.address.proxySelector() != null) {
            this.address.proxySelector().connectFailed(this.address.url().uri(), route.proxy().address(), iOException);
        }
        this.routeDatabase.failed(route);
    }

    public boolean hasNext() {
        return this.hasNextInetSocketAddress() || this.hasNextProxy() || this.hasNextPostponed();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Route next() throws IOException {
        Route route;
        if (!this.hasNextInetSocketAddress()) {
            if (!this.hasNextProxy()) {
                if (this.hasNextPostponed()) return this.nextPostponed();
                throw new NoSuchElementException();
            }
            this.lastProxy = this.nextProxy();
        }
        this.lastInetSocketAddress = this.nextInetSocketAddress();
        Route route2 = route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress);
        if (!this.routeDatabase.shouldPostpone(route)) return route2;
        this.postponedRoutes.add(route);
        return this.next();
    }
}

