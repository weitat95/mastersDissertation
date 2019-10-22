/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.OrderedRealmCollectionImpl;
import io.realm.RealmModel;
import io.realm.internal.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public class RealmResults<E extends RealmModel>
extends OrderedRealmCollectionImpl<E> {
    RealmResults(BaseRealm baseRealm, Collection collection, Class<E> class_) {
        super(baseRealm, collection, class_);
    }

    RealmResults(BaseRealm baseRealm, Collection collection, String string2) {
        super(baseRealm, collection, string2);
    }

    @Override
    public boolean isLoaded() {
        this.realm.checkIfValid();
        return this.collection.isLoaded();
    }

    public boolean load() {
        this.realm.checkIfValid();
        this.collection.load();
        return true;
    }
}

