/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal;

import java.net.Socket;
import javax.net.ssl.SSLSocket;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.StreamAllocation;

public abstract class Internal {
    public static Internal instance;

    public abstract void addLenient(Headers.Builder var1, String var2);

    public abstract void addLenient(Headers.Builder var1, String var2, String var3);

    public abstract void apply(ConnectionSpec var1, SSLSocket var2, boolean var3);

    public abstract int code(Response.Builder var1);

    public abstract boolean connectionBecameIdle(ConnectionPool var1, RealConnection var2);

    public abstract Socket deduplicate(ConnectionPool var1, Address var2, StreamAllocation var3);

    public abstract RealConnection get(ConnectionPool var1, Address var2, StreamAllocation var3);

    public abstract void put(ConnectionPool var1, RealConnection var2);

    public abstract RouteDatabase routeDatabase(ConnectionPool var1);
}

