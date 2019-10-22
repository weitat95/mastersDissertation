/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import okhttp3.Protocol;
import okhttp3.Route;

public interface Connection {
    public Protocol protocol();

    public Route route();
}

