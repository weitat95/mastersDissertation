/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.ObjectChangeSet;
import io.realm.RealmModel;

public interface RealmObjectChangeListener<T extends RealmModel> {
    public void onChange(T var1, ObjectChangeSet var2);
}

