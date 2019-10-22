/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.ProxyState;
import io.realm.RealmModel;

public interface RealmObjectProxy
extends RealmModel {
    public void realm$injectObjectContext();

    public ProxyState realmGet$proxyState();

    public static class CacheData<E extends RealmModel> {
        public int minDepth;
        public final E object;

        public CacheData(int n, E e) {
            this.minDepth = n;
            this.object = e;
        }
    }

}

