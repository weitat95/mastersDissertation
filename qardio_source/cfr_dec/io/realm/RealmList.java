/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  io.realm.RealmList.io.realm.RealmList
 *  io.realm.RealmList.io.realm.RealmList$RealmItr
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.DynamicRealmObject;
import io.realm.OrderedRealmCollection;
import io.realm.ProxyState;
import io.realm.Realm;
import io.realm.RealmList.io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.internal.Collection;
import io.realm.internal.InvalidRow;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.SortDescriptor;
import io.realm.internal.Table;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.NoSuchElementException;

public class RealmList<E extends RealmModel>
extends AbstractList<E>
implements OrderedRealmCollection<E> {
    protected String className;
    protected Class<E> clazz;
    private final Collection collection;
    protected BaseRealm realm;
    private List<E> unmanagedList;
    final LinkView view;

    public RealmList() {
        this.collection = null;
        this.view = null;
        this.unmanagedList = new ArrayList();
    }

    RealmList(Class<E> class_, LinkView linkView, BaseRealm baseRealm) {
        this.collection = new Collection(baseRealm.sharedRealm, linkView, null);
        this.clazz = class_;
        this.view = linkView;
        this.realm = baseRealm;
    }

    static /* synthetic */ int access$100(RealmList realmList) {
        return realmList.modCount;
    }

    private void checkValidObject(E e) {
        if (e == null) {
            throw new IllegalArgumentException("RealmList does not accept null values");
        }
    }

    private void checkValidView() {
        this.realm.checkIfValid();
        if (this.view == null || !this.view.isAttached()) {
            throw new IllegalStateException("Realm instance has been closed or this object or its parent has been deleted.");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private E copyToRealmIfNeeded(E e) {
        Realm realm;
        if (e instanceof RealmObjectProxy) {
            Object object = (RealmObjectProxy)e;
            if (object instanceof DynamicRealmObject) {
                String string2 = this.view.getTargetTable().getClassName();
                if (object.realmGet$proxyState().getRealm$realm() == this.realm) {
                    object = ((DynamicRealmObject)e).getType();
                    if (string2.equals(object)) return e;
                    {
                        throw new IllegalArgumentException(String.format(Locale.US, "The object has a different type from list's. Type of the list is '%s', type of object is '%s'.", string2, object));
                    }
                }
                if (this.realm.threadId != object.realmGet$proxyState().getRealm$realm().threadId) throw new IllegalStateException("Cannot copy an object to a Realm instance created in another thread.");
                {
                    throw new IllegalArgumentException("Cannot copy DynamicRealmObject between Realm instances.");
                }
            }
            if (object.realmGet$proxyState().getRow$realm() != null && object.realmGet$proxyState().getRealm$realm().getPath().equals(this.realm.getPath())) {
                if (this.realm != object.realmGet$proxyState().getRealm$realm()) throw new IllegalArgumentException("Cannot copy an object from another Realm instance.");
                return e;
            }
        }
        if (!(realm = (Realm)this.realm).getTable(e.getClass()).hasPrimaryKey()) return realm.copyToRealm(e);
        return realm.copyToRealmOrUpdate(e);
    }

    private boolean isAttached() {
        return this.view != null && this.view.isAttached();
    }

    private E lastImpl(boolean bl, E e) {
        if (this.isManaged()) {
            this.checkValidView();
            if (!this.view.isEmpty()) {
                return (E)this.get((int)this.view.size() - 1);
            }
        } else if (this.unmanagedList != null && !this.unmanagedList.isEmpty()) {
            return (E)((RealmModel)this.unmanagedList.get(this.unmanagedList.size() - 1));
        }
        if (bl) {
            throw new IndexOutOfBoundsException("The list is empty.");
        }
        return e;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void add(int n, E object) {
        this.checkValidObject(object);
        if (this.isManaged()) {
            this.checkValidView();
            if (n < 0 || n > this.size()) {
                throw new IndexOutOfBoundsException("Invalid index " + n + ", size is " + this.size());
            }
            object = (RealmObjectProxy)this.copyToRealmIfNeeded(object);
            this.view.insert(n, object.realmGet$proxyState().getRow$realm().getIndex());
        } else {
            this.unmanagedList.add(n, object);
        }
        ++this.modCount;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean add(E object) {
        this.checkValidObject(object);
        if (this.isManaged()) {
            this.checkValidView();
            object = (RealmObjectProxy)this.copyToRealmIfNeeded(object);
            this.view.add(object.realmGet$proxyState().getRow$realm().getIndex());
        } else {
            this.unmanagedList.add(object);
        }
        ++this.modCount;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void clear() {
        if (this.isManaged()) {
            this.checkValidView();
            this.view.clear();
        } else {
            this.unmanagedList.clear();
        }
        ++this.modCount;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean contains(Object object) {
        if (!this.isManaged()) return this.unmanagedList.contains(object);
        this.realm.checkIfValid();
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
        if (this.isManaged()) {
            this.checkValidView();
            long l = this.view.getTargetRowIndex(n);
            return this.realm.get(this.clazz, this.className, l);
        }
        return (E)((RealmModel)this.unmanagedList.get(n));
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    public boolean isManaged() {
        return this.realm != null;
    }

    @Override
    public Iterator<E> iterator() {
        if (this.isManaged()) {
            return new RealmItr();
        }
        return super.iterator();
    }

    public E last(E e) {
        return this.lastImpl(false, e);
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        if (this.isManaged()) {
            return new RealmListItr(n);
        }
        return super.listIterator(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public E remove(int n) {
        Object object;
        if (this.isManaged()) {
            this.checkValidView();
            object = this.get(n);
            this.view.remove(n);
        } else {
            object = (RealmModel)this.unmanagedList.remove(n);
        }
        ++this.modCount;
        return (E)object;
    }

    @Override
    public boolean remove(Object object) {
        if (this.isManaged() && !this.realm.isInTransaction()) {
            throw new IllegalStateException("Objects can only be removed from inside a write transaction");
        }
        return super.remove(object);
    }

    @Override
    public boolean removeAll(java.util.Collection<?> collection) {
        if (this.isManaged() && !this.realm.isInTransaction()) {
            throw new IllegalStateException("Objects can only be removed from inside a write transaction");
        }
        return super.removeAll(collection);
    }

    @Override
    public E set(int n, E object) {
        this.checkValidObject(object);
        if (this.isManaged()) {
            this.checkValidView();
            object = (RealmObjectProxy)this.copyToRealmIfNeeded(object);
            Object object2 = this.get(n);
            this.view.set(n, object.realmGet$proxyState().getRow$realm().getIndex());
            return (E)object2;
        }
        return (E)((RealmModel)this.unmanagedList.set(n, object));
    }

    @Override
    public int size() {
        if (this.isManaged()) {
            this.checkValidView();
            long l = this.view.size();
            if (l < Integer.MAX_VALUE) {
                return (int)l;
            }
            return Integer.MAX_VALUE;
        }
        return this.unmanagedList.size();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public String toString() {
        block7: {
            var3_1 = new StringBuilder();
            var2_2 = this.isManaged() != false ? this.clazz.getSimpleName() : this.getClass().getSimpleName();
            var3_1.append(var2_2);
            var3_1.append("@[");
            if (!this.isManaged() || this.isAttached()) break block7;
            var3_1.append("invalid");
            ** GOTO lbl15
        }
        var1_3 = 0;
        do {
            block8: {
                if (var1_3 < this.size()) break block8;
lbl15:
                // 2 sources
                var3_1.append("]");
                return var3_1.toString();
            }
            if (this.isManaged()) {
                var3_1.append(((RealmObjectProxy)this.get(var1_3)).realmGet$proxyState().getRow$realm().getIndex());
            } else {
                var3_1.append(System.identityHashCode(this.get(var1_3)));
            }
            if (var1_3 < this.size() - 1) {
                var3_1.append(',');
            }
            ++var1_3;
        } while (true);
    }

    private class RealmItr
    implements Iterator<E> {
        int cursor = 0;
        int expectedModCount = RealmList.access$100(RealmList.this);
        int lastRet = -1;

        private RealmItr() {
        }

        final void checkConcurrentModification() {
            if (RealmList.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            RealmList.this.realm.checkIfValid();
            this.checkConcurrentModification();
            return this.cursor != RealmList.this.size();
        }

        @Override
        public E next() {
            Object object;
            RealmList.this.realm.checkIfValid();
            this.checkConcurrentModification();
            int n = this.cursor;
            try {
                object = RealmList.this.get(n);
                this.lastRet = n;
                this.cursor = n + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                this.checkConcurrentModification();
                throw new NoSuchElementException("Cannot access index " + n + " when size is " + RealmList.this.size() + ". Remember to check hasNext() before using next().");
            }
            return (E)object;
        }

        @Override
        public void remove() {
            RealmList.this.realm.checkIfValid();
            if (this.lastRet < 0) {
                throw new IllegalStateException("Cannot call remove() twice. Must call next() in between.");
            }
            this.checkConcurrentModification();
            try {
                RealmList.this.remove(this.lastRet);
                if (this.lastRet < this.cursor) {
                    --this.cursor;
                }
                this.lastRet = -1;
                this.expectedModCount = RealmList.this.modCount;
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class RealmListItr
    extends io.realm.RealmList$RealmItr
    implements ListIterator<E> {
        RealmListItr(int n) {
            if (n >= 0 && n <= RealmList.this.size()) {
                this.cursor = n;
                return;
            }
            throw new IndexOutOfBoundsException("Starting location must be a valid index: [0, " + (RealmList.this.size() - 1) + "]. Index was " + n);
        }

        @Override
        public void add(E e) {
            RealmList.this.realm.checkIfValid();
            this.checkConcurrentModification();
            try {
                int n = this.cursor;
                RealmList.this.add(n, e);
                this.lastRet = -1;
                this.cursor = n + 1;
                this.expectedModCount = RealmList.this.modCount;
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        @Override
        public int nextIndex() {
            return this.cursor;
        }

        @Override
        public E previous() {
            Object object;
            this.checkConcurrentModification();
            int n = this.cursor - 1;
            try {
                object = RealmList.this.get(n);
                this.cursor = n;
                this.lastRet = n;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                this.checkConcurrentModification();
                throw new NoSuchElementException("Cannot access index less than zero. This was " + n + ". Remember to check hasPrevious() before using previous().");
            }
            return (E)object;
        }

        @Override
        public int previousIndex() {
            return this.cursor - 1;
        }

        @Override
        public void set(E e) {
            RealmList.this.realm.checkIfValid();
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            this.checkConcurrentModification();
            try {
                RealmList.this.set(this.lastRet, e);
                this.expectedModCount = RealmList.this.modCount;
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new ConcurrentModificationException();
            }
        }
    }

}

