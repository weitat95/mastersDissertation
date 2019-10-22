/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmChangeListener;
import io.realm.internal.CollectionChangeSet;
import io.realm.internal.Keep;
import io.realm.internal.LinkView;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.ObserverPairList;
import io.realm.internal.SharedRealm;
import io.realm.internal.SortDescriptor;
import io.realm.internal.Table;
import io.realm.internal.TableQuery;
import io.realm.internal.UncheckedRow;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.NoSuchElementException;

@Keep
public class Collection
implements NativeObject {
    public static final byte AGGREGATE_FUNCTION_AVERAGE = 3;
    public static final byte AGGREGATE_FUNCTION_MAXIMUM = 2;
    public static final byte AGGREGATE_FUNCTION_MINIMUM = 1;
    public static final byte AGGREGATE_FUNCTION_SUM = 4;
    private static final String CLOSED_REALM_MESSAGE = "This Realm instance has already been closed, making it unusable.";
    public static final byte MODE_EMPTY = 0;
    public static final byte MODE_LINKVIEW = 3;
    public static final byte MODE_QUERY = 2;
    public static final byte MODE_TABLE = 1;
    public static final byte MODE_TABLEVIEW = 4;
    private static final long nativeFinalizerPtr = Collection.nativeGetFinalizerPtr();
    private final NativeContext context;
    private boolean isSnapshot = false;
    private boolean loaded;
    private final long nativePtr;
    private final ObserverPairList<CollectionObserverPair> observerPairs = new ObserverPairList();
    private final SharedRealm sharedRealm;
    private final Table table;

    public Collection(SharedRealm sharedRealm, LinkView linkView, SortDescriptor sortDescriptor) {
        this.nativePtr = Collection.nativeCreateResultsFromLinkView(sharedRealm.getNativePtr(), linkView.getNativePtr(), sortDescriptor);
        this.sharedRealm = sharedRealm;
        this.context = sharedRealm.context;
        this.table = linkView.getTargetTable();
        this.context.addReference(this);
        this.loaded = true;
    }

    private Collection(SharedRealm sharedRealm, Table table, long l) {
        this(sharedRealm, table, l, false);
    }

    private Collection(SharedRealm sharedRealm, Table table, long l, boolean bl) {
        this.sharedRealm = sharedRealm;
        this.context = sharedRealm.context;
        this.table = table;
        this.nativePtr = l;
        this.context.addReference(this);
        this.loaded = bl;
    }

    public Collection(SharedRealm sharedRealm, TableQuery tableQuery) {
        this(sharedRealm, tableQuery, null, null);
    }

    public Collection(SharedRealm sharedRealm, TableQuery tableQuery, SortDescriptor sortDescriptor) {
        this(sharedRealm, tableQuery, sortDescriptor, null);
    }

    public Collection(SharedRealm sharedRealm, TableQuery tableQuery, SortDescriptor sortDescriptor, SortDescriptor sortDescriptor2) {
        tableQuery.validateQuery();
        this.nativePtr = Collection.nativeCreateResults(sharedRealm.getNativePtr(), tableQuery.getNativePtr(), sortDescriptor, sortDescriptor2);
        this.sharedRealm = sharedRealm;
        this.context = sharedRealm.context;
        this.table = tableQuery.getTable();
        this.context.addReference(this);
        this.loaded = false;
    }

    public static Collection createBacklinksCollection(SharedRealm sharedRealm, UncheckedRow uncheckedRow, Table table, String string2) {
        return new Collection(sharedRealm, table, Collection.nativeCreateResultsFromBacklinks(sharedRealm.getNativePtr(), uncheckedRow.getNativePtr(), table.getNativePtr(), table.getColumnIndex(string2)), true);
    }

    private static native Object nativeAggregate(long var0, long var2, byte var4);

    private static native void nativeClear(long var0);

    private static native boolean nativeContains(long var0, long var2);

    private static native long nativeCreateResults(long var0, long var2, SortDescriptor var4, SortDescriptor var5);

    private static native long nativeCreateResultsFromBacklinks(long var0, long var2, long var4, long var6);

    private static native long nativeCreateResultsFromLinkView(long var0, long var2, SortDescriptor var4);

    private static native long nativeCreateSnapshot(long var0);

    private static native void nativeDelete(long var0, long var2);

    private static native boolean nativeDeleteFirst(long var0);

    private static native boolean nativeDeleteLast(long var0);

    private static native long nativeDistinct(long var0, SortDescriptor var2);

    private static native long nativeFirstRow(long var0);

    private static native long nativeGetFinalizerPtr();

    private static native byte nativeGetMode(long var0);

    private static native long nativeGetRow(long var0, int var2);

    private static native long nativeIndexOf(long var0, long var2);

    private static native long nativeIndexOfBySourceRowIndex(long var0, long var2);

    private static native boolean nativeIsValid(long var0);

    private static native long nativeLastRow(long var0);

    private static native long nativeSize(long var0);

    private static native long nativeSort(long var0, SortDescriptor var2);

    private native void nativeStartListening(long var1);

    private native void nativeStopListening(long var1);

    private static native long nativeWhere(long var0);

    /*
     * Enabled aggressive block sorting
     */
    private void notifyChangeListeners(long l) {
        if (l == 0L && this.isLoaded()) {
            return;
        }
        boolean bl = this.loaded;
        this.loaded = true;
        ObserverPairList<CollectionObserverPair> observerPairList = this.observerPairs;
        CollectionChangeSet collectionChangeSet = l == 0L || !bl ? null : new CollectionChangeSet(l);
        observerPairList.foreach(new Callback(collectionChangeSet));
    }

    public <T> void addListener(T object, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        if (this.observerPairs.isEmpty()) {
            this.nativeStartListening(this.nativePtr);
        }
        object = new CollectionObserverPair<T>(object, orderedRealmCollectionChangeListener);
        this.observerPairs.add((CollectionObserverPair)object);
    }

    public <T> void addListener(T t, RealmChangeListener<T> realmChangeListener) {
        this.addListener(t, new RealmChangeListenerWrapper<T>(realmChangeListener));
    }

    public Date aggregateDate(Aggregate aggregate, long l) {
        return (Date)Collection.nativeAggregate(this.nativePtr, l, aggregate.getValue());
    }

    public Number aggregateNumber(Aggregate aggregate, long l) {
        return (Number)Collection.nativeAggregate(this.nativePtr, l, aggregate.getValue());
    }

    public void clear() {
        Collection.nativeClear(this.nativePtr);
    }

    public boolean contains(UncheckedRow uncheckedRow) {
        return Collection.nativeContains(this.nativePtr, uncheckedRow.getNativePtr());
    }

    public Collection createSnapshot() {
        if (this.isSnapshot) {
            return this;
        }
        Collection collection = new Collection(this.sharedRealm, this.table, Collection.nativeCreateSnapshot(this.nativePtr));
        collection.isSnapshot = true;
        return collection;
    }

    public void delete(long l) {
        Collection.nativeDelete(this.nativePtr, l);
    }

    public boolean deleteFirst() {
        return Collection.nativeDeleteFirst(this.nativePtr);
    }

    public boolean deleteLast() {
        return Collection.nativeDeleteLast(this.nativePtr);
    }

    public Collection distinct(SortDescriptor sortDescriptor) {
        return new Collection(this.sharedRealm, this.table, Collection.nativeDistinct(this.nativePtr, sortDescriptor));
    }

    public UncheckedRow firstUncheckedRow() {
        long l = Collection.nativeFirstRow(this.nativePtr);
        if (l != 0L) {
            return this.table.getUncheckedRowByPointer(l);
        }
        return null;
    }

    public Mode getMode() {
        return Mode.getByValue(Collection.nativeGetMode(this.nativePtr));
    }

    @Override
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }

    public Table getTable() {
        return this.table;
    }

    public UncheckedRow getUncheckedRow(int n) {
        return this.table.getUncheckedRowByPointer(Collection.nativeGetRow(this.nativePtr, n));
    }

    public int indexOf(long l) {
        if ((l = Collection.nativeIndexOfBySourceRowIndex(this.nativePtr, l)) > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)l;
    }

    public int indexOf(UncheckedRow uncheckedRow) {
        long l = Collection.nativeIndexOf(this.nativePtr, uncheckedRow.getNativePtr());
        if (l > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)l;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public boolean isValid() {
        return Collection.nativeIsValid(this.nativePtr);
    }

    public UncheckedRow lastUncheckedRow() {
        long l = Collection.nativeLastRow(this.nativePtr);
        if (l != 0L) {
            return this.table.getUncheckedRowByPointer(l);
        }
        return null;
    }

    public void load() {
        if (this.loaded) {
            return;
        }
        this.notifyChangeListeners(0L);
    }

    public void removeAllListeners() {
        this.observerPairs.clear();
        this.nativeStopListening(this.nativePtr);
    }

    public <T> void removeListener(T t, OrderedRealmCollectionChangeListener<T> orderedRealmCollectionChangeListener) {
        this.observerPairs.remove(t, orderedRealmCollectionChangeListener);
        if (this.observerPairs.isEmpty()) {
            this.nativeStopListening(this.nativePtr);
        }
    }

    public <T> void removeListener(T t, RealmChangeListener<T> realmChangeListener) {
        this.removeListener(t, new RealmChangeListenerWrapper<T>(realmChangeListener));
    }

    public long size() {
        return Collection.nativeSize(this.nativePtr);
    }

    public Collection sort(SortDescriptor sortDescriptor) {
        return new Collection(this.sharedRealm, this.table, Collection.nativeSort(this.nativePtr, sortDescriptor));
    }

    public TableQuery where() {
        long l = Collection.nativeWhere(this.nativePtr);
        return new TableQuery(this.context, this.table, l);
    }

    public static enum Aggregate {
        MINIMUM(1),
        MAXIMUM(2),
        AVERAGE(3),
        SUM(4);

        private final byte value;

        private Aggregate(byte by) {
            this.value = by;
        }

        public byte getValue() {
            return this.value;
        }
    }

    private static class Callback
    implements ObserverPairList.Callback<CollectionObserverPair> {
        private final OrderedCollectionChangeSet changeSet;

        Callback(OrderedCollectionChangeSet orderedCollectionChangeSet) {
            this.changeSet = orderedCollectionChangeSet;
        }

        @Override
        public void onCalled(CollectionObserverPair collectionObserverPair, Object object) {
            collectionObserverPair.onChange(object, this.changeSet);
        }
    }

    private static class CollectionObserverPair<T>
    extends ObserverPairList.ObserverPair<T, Object> {
        public CollectionObserverPair(T t, Object object) {
            super(t, object);
        }

        public void onChange(T t, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (this.listener instanceof OrderedRealmCollectionChangeListener) {
                ((OrderedRealmCollectionChangeListener)this.listener).onChange(t, orderedCollectionChangeSet);
                return;
            }
            if (this.listener instanceof RealmChangeListener) {
                ((RealmChangeListener)this.listener).onChange(t);
                return;
            }
            throw new RuntimeException("Unsupported listener type: " + this.listener);
        }
    }

    public static abstract class Iterator<T>
    implements java.util.Iterator<T> {
        Collection iteratorCollection;
        protected int pos = -1;

        public Iterator(Collection collection) {
            if (collection.sharedRealm.isClosed()) {
                throw new IllegalStateException(Collection.CLOSED_REALM_MESSAGE);
            }
            this.iteratorCollection = collection;
            if (collection.isSnapshot) {
                return;
            }
            if (collection.sharedRealm.isInTransaction()) {
                this.detach();
                return;
            }
            this.iteratorCollection.sharedRealm.addIterator(this);
        }

        void checkValid() {
            if (this.iteratorCollection == null) {
                throw new ConcurrentModificationException("No outside changes to a Realm is allowed while iterating a living Realm collection.");
            }
        }

        protected abstract T convertRowToObject(UncheckedRow var1);

        void detach() {
            this.iteratorCollection = this.iteratorCollection.createSnapshot();
        }

        T get(int n) {
            return this.convertRowToObject(this.iteratorCollection.getUncheckedRow(n));
        }

        @Override
        public boolean hasNext() {
            this.checkValid();
            return (long)(this.pos + 1) < this.iteratorCollection.size();
        }

        void invalidate() {
            this.iteratorCollection = null;
        }

        @Override
        public T next() {
            this.checkValid();
            ++this.pos;
            if ((long)this.pos >= this.iteratorCollection.size()) {
                throw new NoSuchElementException("Cannot access index " + this.pos + " when size is " + this.iteratorCollection.size() + ". Remember to check hasNext() before using next().");
            }
            return this.get(this.pos);
        }

        @Deprecated
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported by RealmResults iterators.");
        }
    }

    public static abstract class ListIterator<T>
    extends Iterator<T>
    implements java.util.ListIterator<T> {
        public ListIterator(Collection collection, int n) {
            super(collection);
            if (n >= 0 && (long)n <= this.iteratorCollection.size()) {
                this.pos = n - 1;
                return;
            }
            throw new IndexOutOfBoundsException("Starting location must be a valid index: [0, " + (this.iteratorCollection.size() - 1L) + "]. Yours was " + n);
        }

        @Deprecated
        @Override
        public void add(T t) {
            throw new UnsupportedOperationException("Adding an element is not supported. Use Realm.createObject() instead.");
        }

        @Override
        public boolean hasPrevious() {
            this.checkValid();
            return this.pos >= 0;
        }

        @Override
        public int nextIndex() {
            this.checkValid();
            return this.pos + 1;
        }

        @Override
        public T previous() {
            Object t;
            this.checkValid();
            try {
                t = this.get(this.pos);
                --this.pos;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new NoSuchElementException("Cannot access index less than zero. This was " + this.pos + ". Remember to check hasPrevious() before using previous().");
            }
            return t;
        }

        @Override
        public int previousIndex() {
            this.checkValid();
            return this.pos;
        }

        @Deprecated
        @Override
        public void set(T t) {
            throw new UnsupportedOperationException("Replacing and element is not supported.");
        }
    }

    public static enum Mode {
        EMPTY,
        TABLE,
        QUERY,
        LINKVIEW,
        TABLEVIEW;


        static Mode getByValue(byte by) {
            switch (by) {
                default: {
                    throw new IllegalArgumentException("Invalid value: " + by);
                }
                case 0: {
                    return EMPTY;
                }
                case 1: {
                    return TABLE;
                }
                case 2: {
                    return QUERY;
                }
                case 3: {
                    return LINKVIEW;
                }
                case 4: 
            }
            return TABLEVIEW;
        }
    }

    private static class RealmChangeListenerWrapper<T>
    implements OrderedRealmCollectionChangeListener<T> {
        private final RealmChangeListener<T> listener;

        RealmChangeListenerWrapper(RealmChangeListener<T> realmChangeListener) {
            this.listener = realmChangeListener;
        }

        public boolean equals(Object object) {
            return object instanceof RealmChangeListenerWrapper && this.listener == ((RealmChangeListenerWrapper)object).listener;
        }

        public int hashCode() {
            return this.listener.hashCode();
        }

        @Override
        public void onChange(T t, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            this.listener.onChange(t);
        }
    }

}

