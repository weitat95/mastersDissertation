/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.rxrelay2;

import io.reactivex.functions.Predicate;

class AppendOnlyLinkedArrayList<T> {
    private final int capacity;
    private final Object[] head;
    private int offset;
    private Object[] tail;

    AppendOnlyLinkedArrayList(int n) {
        this.capacity = n;
        this.head = new Object[n + 1];
        this.tail = this.head;
    }

    void add(T t) {
        int n;
        int n2 = this.capacity;
        int n3 = n = this.offset;
        if (n == n2) {
            Object[] arrobject;
            this.tail[n2] = arrobject = new Object[n2 + 1];
            this.tail = arrobject;
            n3 = 0;
        }
        this.tail[n3] = t;
        this.offset = n3 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    void forEachWhile(NonThrowingPredicate<? super T> nonThrowingPredicate) {
        Object[] arrobject = this.head;
        int n = this.capacity;
        while (arrobject != null) {
            Object object;
            for (int i = 0; i < n && (object = arrobject[i]) != null && !nonThrowingPredicate.test(object); ++i) {
            }
            arrobject = (Object[])arrobject[n];
        }
        return;
    }

    public static interface NonThrowingPredicate<T>
    extends Predicate<T> {
        @Override
        public boolean test(T var1);
    }

}

