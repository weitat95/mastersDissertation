/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.engine.bitmap_recycle;

import com.bumptech.glide.load.engine.bitmap_recycle.Poolable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GroupedLinkedMap<K extends Poolable, V> {
    private final LinkedEntry<K, V> head = new LinkedEntry();
    private final Map<K, LinkedEntry<K, V>> keyToEntry = new HashMap<K, LinkedEntry<K, V>>();

    GroupedLinkedMap() {
    }

    private void makeHead(LinkedEntry<K, V> linkedEntry) {
        GroupedLinkedMap.removeEntry(linkedEntry);
        linkedEntry.prev = this.head;
        linkedEntry.next = this.head.next;
        GroupedLinkedMap.updateEntry(linkedEntry);
    }

    private void makeTail(LinkedEntry<K, V> linkedEntry) {
        GroupedLinkedMap.removeEntry(linkedEntry);
        linkedEntry.prev = this.head.prev;
        linkedEntry.next = this.head;
        GroupedLinkedMap.updateEntry(linkedEntry);
    }

    private static <K, V> void removeEntry(LinkedEntry<K, V> linkedEntry) {
        linkedEntry.prev.next = linkedEntry.next;
        linkedEntry.next.prev = linkedEntry.prev;
    }

    private static <K, V> void updateEntry(LinkedEntry<K, V> linkedEntry) {
        linkedEntry.next.prev = linkedEntry;
        linkedEntry.prev.next = linkedEntry;
    }

    /*
     * Enabled aggressive block sorting
     */
    public V get(K object) {
        LinkedEntry<Object, V> linkedEntry = this.keyToEntry.get(object);
        if (linkedEntry == null) {
            linkedEntry = new LinkedEntry(object);
            this.keyToEntry.put(object, linkedEntry);
            object = linkedEntry;
        } else {
            object.offer();
            object = linkedEntry;
        }
        this.makeHead((LinkedEntry<K, V>)object);
        return ((LinkedEntry)object).removeLast();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void put(K object, V v) {
        LinkedEntry<Object, V> linkedEntry = this.keyToEntry.get(object);
        if (linkedEntry == null) {
            linkedEntry = new LinkedEntry(object);
            this.makeTail(linkedEntry);
            this.keyToEntry.put(object, linkedEntry);
            object = linkedEntry;
        } else {
            object.offer();
            object = linkedEntry;
        }
        ((LinkedEntry)object).add(v);
    }

    public V removeLast() {
        LinkedEntry linkedEntry = this.head.prev;
        while (!linkedEntry.equals(this.head)) {
            Object v = linkedEntry.removeLast();
            if (v != null) {
                return v;
            }
            GroupedLinkedMap.removeEntry(linkedEntry);
            this.keyToEntry.remove(linkedEntry.key);
            ((Poolable)linkedEntry.key).offer();
            linkedEntry = linkedEntry.prev;
        }
        return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("GroupedLinkedMap( ");
        LinkedEntry linkedEntry = this.head.next;
        boolean bl = false;
        while (!linkedEntry.equals(this.head)) {
            bl = true;
            stringBuilder.append('{').append(linkedEntry.key).append(':').append(linkedEntry.size()).append("}, ");
            linkedEntry = linkedEntry.next;
        }
        if (bl) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        return stringBuilder.append(" )").toString();
    }

    private static class LinkedEntry<K, V> {
        private final K key;
        LinkedEntry<K, V> next;
        LinkedEntry<K, V> prev = this;
        private List<V> values;

        public LinkedEntry() {
            this(null);
        }

        public LinkedEntry(K k) {
            this.next = this;
            this.key = k;
        }

        public void add(V v) {
            if (this.values == null) {
                this.values = new ArrayList<V>();
            }
            this.values.add(v);
        }

        public V removeLast() {
            int n = this.size();
            if (n > 0) {
                return this.values.remove(n - 1);
            }
            return null;
        }

        public int size() {
            if (this.values != null) {
                return this.values.size();
            }
            return 0;
        }
    }

}

