/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.OrderedRealmCollection;
import io.realm.ProxyState;
import io.realm.RealmModel;
import io.realm.internal.Collection;
import io.realm.internal.InvalidRow;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.UncheckedRow;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;

abstract class OrderedRealmCollectionImpl<E extends RealmModel>
extends AbstractList<E>
implements OrderedRealmCollection<E> {
    final String className;
    final Class<E> classSpec;
    final Collection collection;
    final BaseRealm realm;

    OrderedRealmCollectionImpl(BaseRealm baseRealm, Collection collection, Class<E> class_) {
        this(baseRealm, collection, class_, null);
    }

    private OrderedRealmCollectionImpl(BaseRealm baseRealm, Collection collection, Class<E> class_, String string2) {
        this.realm = baseRealm;
        this.collection = collection;
        this.classSpec = class_;
        this.className = string2;
    }

    OrderedRealmCollectionImpl(BaseRealm baseRealm, Collection collection, String string2) {
        this(baseRealm, collection, null, string2);
    }

    @Deprecated
    @Override
    public void add(int n, E e) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Deprecated
    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Deprecated
    @Override
    public boolean addAll(int n, java.util.Collection<? extends E> collection) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Deprecated
    @Override
    public boolean addAll(java.util.Collection<? extends E> collection) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean contains(Object object) {
        if (!this.isLoaded()) return false;
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm() == InvalidRow.INSTANCE) {
            return false;
        }
        Iterator<E> iterator = this.iterator();
        do {
            if (!iterator.hasNext()) return false;
        } while (!((RealmModel)iterator.next()).equals(object));
        return true;
    }

    @Override
    public E get(int n) {
        this.realm.checkIfValid();
        return this.realm.get(this.classSpec, this.className, this.collection.getUncheckedRow(n));
    }

    @Override
    public Iterator<E> iterator() {
        return new RealmCollectionIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new RealmCollectionListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        return new RealmCollectionListIterator(n);
    }

    @Deprecated
    @Override
    public E remove(int n) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Deprecated
    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Deprecated
    @Override
    public boolean removeAll(java.util.Collection<?> collection) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Deprecated
    @Override
    public boolean retainAll(java.util.Collection<?> collection) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Deprecated
    @Override
    public E set(int n, E e) {
        throw new UnsupportedOperationException("This method is not supported by 'RealmResults' or 'OrderedRealmCollectionSnapshot'.");
    }

    @Override
    public int size() {
        if (this.isLoaded()) {
            long l = this.collection.size();
            if (l > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            return (int)l;
        }
        return 0;
    }

    private class RealmCollectionIterator
    extends Collection.Iterator<E> {
        RealmCollectionIterator() {
            super(OrderedRealmCollectionImpl.this.collection);
        }

        @Override
        protected E convertRowToObject(UncheckedRow uncheckedRow) {
            return OrderedRealmCollectionImpl.this.realm.get(OrderedRealmCollectionImpl.this.classSpec, OrderedRealmCollectionImpl.this.className, uncheckedRow);
        }
    }

    private class RealmCollectionListIterator
    extends Collection.ListIterator<E> {
        RealmCollectionListIterator(int n) {
            super(OrderedRealmCollectionImpl.this.collection, n);
        }

        @Override
        protected E convertRowToObject(UncheckedRow uncheckedRow) {
            return OrderedRealmCollectionImpl.this.realm.get(OrderedRealmCollectionImpl.this.classSpec, OrderedRealmCollectionImpl.this.className, uncheckedRow);
        }
    }

}

