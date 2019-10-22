/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.OrderedCollectionChangeSet;

public interface OrderedRealmCollectionChangeListener<T> {
    public void onChange(T var1, OrderedCollectionChangeSet var2);
}

