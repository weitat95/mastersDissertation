/*
 * Decompiled with CFR 0.147.
 */
package android.arch.core.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class SafeIterableMap<K, V>
implements Iterable<Map.Entry<K, V>> {
    private static final Entry UNREACHABLE = new Entry<Object, Object>(new Object(), new Object());
    private Entry<K, V> mEnd;
    private WeakHashMap<ListIterator<K, V>, Boolean> mIterators = new WeakHashMap();
    private int mSize = 0;
    private Entry<K, V> mStart;

    private Entry<K, V> find(K k) {
        Entry<K, V> entry = this.mStart;
        while (entry != null && !entry.mKey.equals(k)) {
            entry = entry.mNext;
        }
        return entry;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        boolean bl = true;
        boolean bl2 = false;
        if (object == this) {
            return true;
        }
        boolean bl3 = bl2;
        if (!(object instanceof SafeIterableMap)) return bl3;
        Object object2 = (SafeIterableMap)object;
        bl3 = bl2;
        if (this.size() != ((SafeIterableMap)object2).size()) return bl3;
        object = this.iterator();
        object2 = ((SafeIterableMap)object2).iterator();
        while (((ListIterator)object).hasNext() && ((ListIterator)object2).hasNext()) {
            Object object3 = ((ListIterator)object).next();
            Object object4 = ((ListIterator)object2).next();
            if (object3 == null) {
                bl3 = bl2;
                if (object4 != null) return bl3;
            }
            if (object3 == null || object3.equals(object4)) continue;
            return false;
        }
        if (((ListIterator)object).hasNext()) return false;
        if (((ListIterator)object2).hasNext()) return false;
        return bl;
    }

    public ListIterator<K, V> iterator() {
        ListIterator<K, V> listIterator = new ListIterator<K, V>(this.mStart, this.mEnd);
        this.mIterators.put(listIterator, false);
        return listIterator;
    }

    public ListIterator<K, V> iteratorWithAdditions() {
        ListIterator<K, V> listIterator = new ListIterator<K, V>(this.mStart, UNREACHABLE);
        this.mIterators.put(listIterator, false);
        return listIterator;
    }

    public V putIfAbsent(K object, V v) {
        Entry<K, V> entry = this.find(object);
        if (entry != null) {
            return entry.mValue;
        }
        object = new Entry<K, V>(object, v);
        ++this.mSize;
        if (this.mEnd == null) {
            this.mStart = object;
            this.mEnd = this.mStart;
            return null;
        }
        this.mEnd.mNext = object;
        ((Entry)object).mPrevious = this.mEnd;
        this.mEnd = object;
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public V remove(K object) {
        Entry<K, V> entry = this.find(object);
        if (entry == null) {
            return null;
        }
        --this.mSize;
        if (!this.mIterators.isEmpty()) {
            Iterator<ListIterator<K, V>> iterator = this.mIterators.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().supportRemove(entry);
            }
        }
        if (entry.mPrevious != null) {
            entry.mPrevious.mNext = entry.mNext;
        } else {
            this.mStart = entry.mNext;
        }
        if (entry.mNext != null) {
            entry.mNext.mPrevious = entry.mPrevious;
        } else {
            this.mEnd = entry.mPrevious;
        }
        entry.mNext = null;
        entry.mPrevious = null;
        return entry.mValue;
    }

    public int size() {
        return this.mSize;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        Iterator iterator = this.iterator();
        while (((ListIterator)iterator).hasNext()) {
            stringBuilder.append(((ListIterator)iterator).next().toString());
            if (!((ListIterator)iterator).hasNext()) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static class Entry<K, V>
    implements Map.Entry<K, V> {
        final K mKey;
        Entry<K, V> mNext;
        Entry<K, V> mPrevious;
        final V mValue;

        Entry(K k, V v) {
            this.mKey = k;
            this.mValue = v;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (object == this) break block4;
                    if (!(object instanceof Entry)) {
                        return false;
                    }
                    object = (Entry)object;
                    if (!this.mKey.equals(((Entry)object).mKey) || !this.mValue.equals(((Entry)object).mValue)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public K getKey() {
            return this.mKey;
        }

        @Override
        public V getValue() {
            return this.mValue;
        }

        @Override
        public V setValue(V v) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public String toString() {
            return this.mKey + "=" + this.mValue;
        }
    }

    private static class ListIterator<K, V>
    implements Iterator<Map.Entry<K, V>> {
        Entry<K, V> mExpectedEnd;
        Entry<K, V> mNext;

        ListIterator(Entry<K, V> entry, Entry<K, V> entry2) {
            this.mExpectedEnd = entry2;
            this.mNext = entry;
        }

        private Entry<K, V> nextNode() {
            if (this.mNext == this.mExpectedEnd || this.mExpectedEnd == null) {
                return null;
            }
            return this.mNext.mNext;
        }

        @Override
        public boolean hasNext() {
            return this.mNext != null;
        }

        @Override
        public Map.Entry<K, V> next() {
            Entry<K, V> entry = this.mNext;
            this.mNext = this.nextNode();
            return entry;
        }

        void supportRemove(Entry<K, V> entry) {
            if (this.mExpectedEnd == entry && entry == this.mNext) {
                this.mNext = null;
                this.mExpectedEnd = null;
            }
            if (this.mExpectedEnd == entry) {
                this.mExpectedEnd = this.mExpectedEnd.mPrevious;
            }
            if (this.mNext == entry) {
                this.mNext = this.nextNode();
            }
        }
    }

}

