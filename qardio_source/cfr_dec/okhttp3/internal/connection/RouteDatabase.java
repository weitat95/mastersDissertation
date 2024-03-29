/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.connection;

import java.util.LinkedHashSet;
import java.util.Set;
import okhttp3.Route;

public final class RouteDatabase {
    private final Set<Route> failedRoutes = new LinkedHashSet<Route>();

    public void connected(Route route) {
        synchronized (this) {
            this.failedRoutes.remove(route);
            return;
        }
    }

    public void failed(Route route) {
        synchronized (this) {
            this.failedRoutes.add(route);
            return;
        }
    }

    public boolean shouldPostpone(Route route) {
        synchronized (this) {
            boolean bl = this.failedRoutes.contains(route);
            return bl;
        }
    }
}

