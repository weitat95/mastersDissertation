/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.internal.util;

import io.reactivex.internal.util.NotificationLite;
import org.reactivestreams.Subscriber;

public class AppendOnlyLinkedArrayList<T> {
    final int capacity;
    final Object[] head;
    int offset;
    Object[] tail;

    public AppendOnlyLinkedArrayList(int n) {
        this.capacity = n;
        this.head = new Object[n + 1];
        this.tail = this.head;
    }

    public <U> boolean accept(Subscriber<? super U> subscriber) {
        Object[] arrobject = this.head;
        int n = this.capacity;
        block0 : while (arrobject != null) {
            int n2 = 0;
            do {
                Object object;
                if (n2 >= n || (object = arrobject[n2]) == null) {
                    arrobject = (Object[])arrobject[n];
                    continue block0;
                }
                if (NotificationLite.acceptFull(object, subscriber)) {
                    return true;
                }
                ++n2;
            } while (true);
        }
        return false;
    }

    public void add(T t) {
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

    public void setFirst(T t) {
        this.head[0] = t;
    }
}

