/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.Task;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DependencyPriorityBlockingQueue<E extends Dependency & Task>
extends PriorityBlockingQueue<E> {
    final Queue<E> blockedQueue = new LinkedList();
    private final ReentrantLock lock = new ReentrantLock();

    boolean canProcess(E e) {
        return e.areDependenciesMet();
    }

    @Override
    public void clear() {
        try {
            this.lock.lock();
            this.blockedQueue.clear();
            super.clear();
            return;
        }
        finally {
            this.lock.unlock();
        }
    }

    <T> T[] concatenate(T[] arrT, T[] arrT2) {
        int n = arrT.length;
        int n2 = arrT2.length;
        Object[] arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), n + n2);
        System.arraycopy(arrT, 0, arrobject, 0, n);
        System.arraycopy(arrT2, 0, arrobject, n, n2);
        return arrobject;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean contains(Object object) {
        try {
            boolean bl;
            this.lock.lock();
            bl = super.contains(object) || (bl = this.blockedQueue.contains(object));
            return bl;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public int drainTo(Collection<? super E> collection) {
        int n;
        int n2;
        try {
            this.lock.lock();
            n2 = super.drainTo(collection);
            n = this.blockedQueue.size();
            while (!this.blockedQueue.isEmpty()) {
                collection.add(this.blockedQueue.poll());
            }
        }
        finally {
            this.lock.unlock();
        }
        return n2 + n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int drainTo(Collection<? super E> collection, int n) {
        try {
            int n2;
            this.lock.lock();
            for (n2 = super.drainTo(collection, n); !this.blockedQueue.isEmpty() && n2 <= n; ++n2) {
                collection.add(this.blockedQueue.poll());
            }
            this.lock.unlock();
            return n2;
        }
        catch (Throwable throwable) {
            this.lock.unlock();
            throw throwable;
        }
    }

    E get(int n, Long l, TimeUnit timeUnit) throws InterruptedException {
        E e;
        while ((e = this.performOperation(n, l, timeUnit)) != null && !this.canProcess(e)) {
            this.offerBlockedResult(n, e);
        }
        return e;
    }

    boolean offerBlockedResult(int n, E e) {
        try {
            this.lock.lock();
            if (n == 1) {
                super.remove(e);
            }
            boolean bl = this.blockedQueue.offer(e);
            return bl;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public E peek() {
        E e;
        try {
            e = this.get(1, null, null);
        }
        catch (InterruptedException interruptedException) {
            return null;
        }
        return e;
    }

    E performOperation(int n, Long l, TimeUnit timeUnit) throws InterruptedException {
        switch (n) {
            default: {
                return null;
            }
            case 0: {
                return (E)((Dependency)super.take());
            }
            case 1: {
                return (E)((Dependency)super.peek());
            }
            case 2: {
                return (E)((Dependency)super.poll());
            }
            case 3: 
        }
        return (E)((Dependency)super.poll(l, timeUnit));
    }

    @Override
    public E poll() {
        E e;
        try {
            e = this.get(2, null, null);
        }
        catch (InterruptedException interruptedException) {
            return null;
        }
        return e;
    }

    @Override
    public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.get(3, l, timeUnit);
    }

    public void recycleBlockedQueue() {
        try {
            this.lock.lock();
            Iterator iterator = this.blockedQueue.iterator();
            while (iterator.hasNext()) {
                Dependency dependency = (Dependency)iterator.next();
                if (!this.canProcess(dependency)) continue;
                super.offer(dependency);
                iterator.remove();
            }
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean remove(Object object) {
        try {
            boolean bl;
            this.lock.lock();
            bl = super.remove(object) || (bl = this.blockedQueue.remove(object));
            return bl;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        try {
            this.lock.lock();
            boolean bl = super.removeAll(collection);
            boolean bl2 = this.blockedQueue.removeAll(collection);
            return bl | bl2;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public int size() {
        try {
            this.lock.lock();
            int n = this.blockedQueue.size();
            int n2 = super.size();
            return n + n2;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public E take() throws InterruptedException {
        return this.get(0, null, null);
    }

    @Override
    public Object[] toArray() {
        try {
            this.lock.lock();
            Object[] arrobject = this.concatenate(super.toArray(), this.blockedQueue.toArray());
            return arrobject;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        try {
            this.lock.lock();
            arrT = this.concatenate(super.toArray(arrT), this.blockedQueue.toArray(arrT));
            return arrT;
        }
        finally {
            this.lock.unlock();
        }
    }
}

