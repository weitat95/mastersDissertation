/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap.com.google.gson.internal.LinkedTreeMap
 *  com.google.gson.internal.LinkedTreeMap.com.google.gson.internal.LinkedTreeMap$EntrySet
 *  com.google.gson.internal.LinkedTreeMap.com.google.gson.internal.LinkedTreeMap$KeySet
 *  com.google.gson.internal.LinkedTreeMap.com.google.gson.internal.LinkedTreeMap$LinkedTreeMapIterator
 */
package com.google.gson.internal;

import com.google.gson.internal.LinkedTreeMap.com.google.gson.internal.LinkedTreeMap;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedTreeMap<K, V>
extends AbstractMap<K, V>
implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Comparator<Comparable> NATURAL_ORDER;
    Comparator<? super K> comparator;
    private com.google.gson.internal.LinkedTreeMap$EntrySet entrySet;
    final Node<K, V> header = new Node();
    private com.google.gson.internal.LinkedTreeMap$KeySet keySet;
    int modCount = 0;
    Node<K, V> root;
    int size = 0;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !LinkedTreeMap.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        NATURAL_ORDER = new Comparator<Comparable>(){

            @Override
            public int compare(Comparable comparable, Comparable comparable2) {
                return comparable.compareTo(comparable2);
            }
        };
    }

    public LinkedTreeMap() {
        this(NATURAL_ORDER);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public LinkedTreeMap(Comparator<? super K> comparator) {
        void var1_2;
        if (comparator == null) {
            Comparator<Comparable> comparator2 = NATURAL_ORDER;
        }
        this.comparator = var1_2;
    }

    private boolean equal(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void rebalance(Node<K, V> node, boolean bl) {
        while (node != null) {
            Node node2;
            Node node3;
            int n;
            Node node4 = node.left;
            int n2 = node4 != null ? node4.height : 0;
            int n3 = n2 - (n = (node2 = node.right) != null ? node2.height : 0);
            if (n3 == -2) {
                node4 = node2.left;
                node3 = node2.right;
                n2 = node3 != null ? node3.height : 0;
                n = node4 != null ? node4.height : 0;
                n2 = n - n2;
                if (n2 == -1 || n2 == 0 && !bl) {
                    this.rotateLeft(node);
                } else {
                    if (!$assertionsDisabled && n2 != 1) {
                        throw new AssertionError();
                    }
                    this.rotateRight(node2);
                    this.rotateLeft(node);
                }
                if (bl) {
                    return;
                }
            } else if (n3 == 2) {
                node2 = node4.left;
                node3 = node4.right;
                n2 = node3 != null ? node3.height : 0;
                n = node2 != null ? node2.height : 0;
                n2 = n - n2;
                if (n2 == 1 || n2 == 0 && !bl) {
                    this.rotateRight(node);
                } else {
                    if (!$assertionsDisabled && n2 != -1) {
                        throw new AssertionError();
                    }
                    this.rotateLeft(node4);
                    this.rotateRight(node);
                }
                if (bl) return;
            } else if (n3 == 0) {
                node.height = n2 + 1;
                if (bl) {
                    return;
                }
            } else {
                if (!$assertionsDisabled && n3 != -1 && n3 != 1) {
                    throw new AssertionError();
                }
                node.height = Math.max(n2, n) + 1;
                if (!bl) {
                    return;
                }
            }
            node = node.parent;
        }
    }

    private void replaceInParent(Node<K, V> node, Node<K, V> node2) {
        Node node3 = node.parent;
        node.parent = null;
        if (node2 != null) {
            node2.parent = node3;
        }
        if (node3 != null) {
            if (node3.left == node) {
                node3.left = node2;
                return;
            }
            if (!$assertionsDisabled && node3.right != node) {
                throw new AssertionError();
            }
            node3.right = node2;
            return;
        }
        this.root = node2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void rotateLeft(Node<K, V> node) {
        int n = 0;
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node3.left;
        Node node5 = node3.right;
        node.right = node4;
        if (node4 != null) {
            node4.parent = node;
        }
        this.replaceInParent(node, node3);
        node3.left = node;
        node.parent = node3;
        int n2 = node2 != null ? node2.height : 0;
        int n3 = node4 != null ? node4.height : 0;
        n3 = node.height = Math.max(n2, n3) + 1;
        n2 = n;
        if (node5 != null) {
            n2 = node5.height;
        }
        node3.height = Math.max(n3, n2) + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void rotateRight(Node<K, V> node) {
        int n = 0;
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node2.left;
        Node node5 = node2.right;
        node.left = node5;
        if (node5 != null) {
            node5.parent = node;
        }
        this.replaceInParent(node, node2);
        node2.right = node;
        node.parent = node2;
        int n2 = node3 != null ? node3.height : 0;
        int n3 = node5 != null ? node5.height : 0;
        n3 = node.height = Math.max(n2, n3) + 1;
        n2 = n;
        if (node4 != null) {
            n2 = node4.height;
        }
        node2.height = Math.max(n3, n2) + 1;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
        ++this.modCount;
        Node<K, V> node = this.header;
        node.prev = node;
        node.next = node;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.findByObject(object) != null;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Object object = this.entrySet;
        if (object != null) {
            return object;
        }
        this.entrySet = object = new EntrySet();
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    Node<K, V> find(K var1_1, boolean var2_2) {
        block8: {
            block6: {
                var7_6 = null;
                var8_7 = this.comparator;
                var4_8 = this.root;
                var3_9 = 0;
                var5_10 = var4_8;
                if (var4_8 == null) ** GOTO lbl14
                var6_11 = var8_7 == LinkedTreeMap.NATURAL_ORDER ? (Comparable)var1_1 : null;
                do {
                    block7: {
                        if ((var3_9 = var6_11 != null ? var6_11.compareTo(var4_8.key) : var8_7.compare(var1_1, var4_8.key)) == 0) {
                            return var4_8;
                        }
                        var5_10 = var3_9 < 0 ? var4_8.left : var4_8.right;
                        if (var5_10 != null) break block7;
                        var5_10 = var4_8;
lbl14:
                        // 2 sources
                        var4_8 = var7_6;
                        if (var2_5 == false) return var4_8;
                        var4_8 = this.header;
                        if (var5_10 == null) {
                            if (var8_7 != LinkedTreeMap.NATURAL_ORDER || var1_1 instanceof Comparable) break;
                            throw new ClassCastException(var1_1.getClass().getName() + " is not Comparable");
                        }
                        break block6;
                    }
                    var4_8 = var5_10;
                } while (true);
                var1_2 = new Node<Object, V>(var5_10, var1_1, var4_8, var4_8.prev);
                this.root = var1_2;
                break block8;
            }
            var1_4 = new Node<Object, V>(var5_10, var1_1, var4_8, var4_8.prev);
            if (var3_9 < 0) {
                var5_10.left = var1_4;
            } else {
                var5_10.right = var1_4;
            }
            this.rebalance(var5_10, true);
        }
        ++this.size;
        ++this.modCount;
        return var1_3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Node<K, V> findByEntry(Map.Entry<?, ?> entry) {
        Node<K, V> node = this.findByObject(entry.getKey());
        if (node == null) return null;
        if (!this.equal(node.value, entry.getValue())) return null;
        boolean bl = true;
        if (!bl) return null;
        return node;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Node<K, V> findByObject(Object object) {
        Node<Object, V> node = null;
        if (object == null) return node;
        try {
            return this.find(object, false);
        }
        catch (ClassCastException classCastException) {
            return null;
        }
    }

    @Override
    public V get(Object node) {
        if ((node = this.findByObject(node)) != null) {
            return node.value;
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        Object object = this.keySet;
        if (object != null) {
            return object;
        }
        this.keySet = object = new KeySet();
        return object;
    }

    @Override
    public V put(K object, V v) {
        if (object == null) {
            throw new NullPointerException("key == null");
        }
        object = this.find(object, true);
        Object v2 = ((Node)object).value;
        ((Node)object).value = v;
        return v2;
    }

    @Override
    public V remove(Object node) {
        if ((node = this.removeInternalByKey(node)) != null) {
            return node.value;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    void removeInternal(Node<K, V> node, boolean bl) {
        if (bl) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node.parent;
        if (node2 != null && node3 != null) {
            node2 = node2.height > node3.height ? node2.last() : node3.first();
            this.removeInternal(node2, false);
            int n = 0;
            node3 = node.left;
            if (node3 != null) {
                n = node3.height;
                node2.left = node3;
                node3.parent = node2;
                node.left = null;
            }
            int n2 = 0;
            node3 = node.right;
            if (node3 != null) {
                n2 = node3.height;
                node2.right = node3;
                node3.parent = node2;
                node.right = null;
            }
            node2.height = Math.max(n, n2) + 1;
            this.replaceInParent(node, node2);
            return;
        }
        if (node2 != null) {
            this.replaceInParent(node, node2);
            node.left = null;
        } else if (node3 != null) {
            this.replaceInParent(node, node3);
            node.right = null;
        } else {
            this.replaceInParent(node, null);
        }
        this.rebalance(node4, false);
        --this.size;
        ++this.modCount;
    }

    Node<K, V> removeInternalByKey(Object node) {
        if ((node = this.findByObject(node)) != null) {
            this.removeInternal(node, true);
        }
        return node;
    }

    @Override
    public int size() {
        return this.size;
    }

    class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public void clear() {
            LinkedTreeMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof Map.Entry && LinkedTreeMap.this.findByEntry((Map.Entry)object) != null;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new com.google.gson.internal.LinkedTreeMap$LinkedTreeMapIterator<Map.Entry<K, V>>(){

                public Map.Entry<K, V> next() {
                    return this.nextNode();
                }
            };
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean remove(Object node) {
            if (!(node instanceof Map.Entry) || (node = LinkedTreeMap.this.findByEntry(node)) == null) {
                return false;
            }
            LinkedTreeMap.this.removeInternal(node, true);
            return true;
        }

        @Override
        public int size() {
            return LinkedTreeMap.this.size;
        }

    }

    final class KeySet
    extends AbstractSet<K> {
        KeySet() {
        }

        @Override
        public void clear() {
            LinkedTreeMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return LinkedTreeMap.this.containsKey(object);
        }

        @Override
        public Iterator<K> iterator() {
            return new com.google.gson.internal.LinkedTreeMap$LinkedTreeMapIterator<K>(){

                public K next() {
                    return this.nextNode().key;
                }
            };
        }

        @Override
        public boolean remove(Object object) {
            return LinkedTreeMap.this.removeInternalByKey(object) != null;
        }

        @Override
        public int size() {
            return LinkedTreeMap.this.size;
        }

    }

    private abstract class LinkedTreeMapIterator<T>
    implements Iterator<T> {
        int expectedModCount;
        Node<K, V> lastReturned;
        Node<K, V> next;

        LinkedTreeMapIterator() {
            this.next = LinkedTreeMap.this.header.next;
            this.lastReturned = null;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }

        @Override
        public final boolean hasNext() {
            return this.next != LinkedTreeMap.this.header;
        }

        final Node<K, V> nextNode() {
            Node<K, V> node = this.next;
            if (node == LinkedTreeMap.this.header) {
                throw new NoSuchElementException();
            }
            if (LinkedTreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.next = node.next;
            this.lastReturned = node;
            return node;
        }

        @Override
        public final void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            LinkedTreeMap.this.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }
    }

    static final class Node<K, V>
    implements Map.Entry<K, V> {
        int height;
        final K key;
        Node<K, V> left;
        Node<K, V> next;
        Node<K, V> parent;
        Node<K, V> prev;
        Node<K, V> right;
        V value;

        Node() {
            this.key = null;
            this.prev = this;
            this.next = this;
        }

        Node(Node<K, V> node, K k, Node<K, V> node2, Node<K, V> node3) {
            this.parent = node;
            this.key = k;
            this.height = 1;
            this.next = node2;
            this.prev = node3;
            node3.next = this;
            node2.prev = this;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (!(object instanceof Map.Entry)) return bl2;
            object = (Map.Entry)object;
            if (this.key == null) {
                bl2 = bl;
                if (object.getKey() != null) return bl2;
            } else {
                bl2 = bl;
                if (!this.key.equals(object.getKey())) return bl2;
            }
            if (this.value == null) {
                bl2 = bl;
                if (object.getValue() != null) return bl2;
                return true;
            } else {
                bl2 = bl;
                if (!this.value.equals(object.getValue())) return bl2;
            }
            return true;
        }

        public Node<K, V> first() {
            Node<K, V> node = this;
            Node<K, V> node2 = node.left;
            while (node2 != null) {
                node = node2;
                node2 = node.left;
            }
            return node;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int hashCode() {
            int n = 0;
            int n2 = this.key == null ? 0 : this.key.hashCode();
            if (this.value == null) {
                return n2 ^ n;
            }
            n = this.value.hashCode();
            return n2 ^ n;
        }

        public Node<K, V> last() {
            Node<K, V> node = this;
            Node<K, V> node2 = node.right;
            while (node2 != null) {
                node = node2;
                node2 = node.right;
            }
            return node;
        }

        @Override
        public V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }
    }

}

