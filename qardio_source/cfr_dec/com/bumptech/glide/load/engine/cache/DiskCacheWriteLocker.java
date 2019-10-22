/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.load.Key;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class DiskCacheWriteLocker {
    private final Map<Key, WriteLock> locks = new HashMap<Key, WriteLock>();
    private final WriteLockPool writeLockPool = new WriteLockPool();

    DiskCacheWriteLocker() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void acquire(Key key) {
        WriteLock writeLock;
        synchronized (this) {
            WriteLock writeLock2;
            writeLock = writeLock2 = this.locks.get(key);
            if (writeLock2 == null) {
                writeLock = this.writeLockPool.obtain();
                this.locks.put(key, writeLock);
            }
            ++writeLock.interestedThreads;
        }
        writeLock.lock.lock();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void release(Key object) {
        WriteLock writeLock;
        synchronized (this) {
            int n;
            writeLock = this.locks.get(object);
            if (writeLock == null || writeLock.interestedThreads <= 0) {
                int n2;
                object = new StringBuilder().append("Cannot release a lock that is not held, key: ").append(object).append(", interestedThreads: ");
                if (writeLock == null) {
                    n2 = 0;
                    throw new IllegalArgumentException(((StringBuilder)object).append(n2).toString());
                }
                n2 = writeLock.interestedThreads;
                throw new IllegalArgumentException(((StringBuilder)object).append(n2).toString());
            }
            writeLock.interestedThreads = n = writeLock.interestedThreads - 1;
            if (n == 0) {
                WriteLock writeLock2 = this.locks.remove(object);
                if (!writeLock2.equals(writeLock)) {
                    throw new IllegalStateException("Removed the wrong lock, expected to remove: " + writeLock + ", but actually removed: " + writeLock2 + ", key: " + object);
                }
                this.writeLockPool.offer(writeLock2);
            }
        }
        writeLock.lock.unlock();
    }

    private static class WriteLock {
        int interestedThreads;
        final Lock lock = new ReentrantLock();

        private WriteLock() {
        }
    }

    private static class WriteLockPool {
        private final Queue<WriteLock> pool = new ArrayDeque<WriteLock>();

        private WriteLockPool() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        WriteLock obtain() {
            WriteLock writeLock;
            Object object = this.pool;
            synchronized (object) {
                writeLock = this.pool.poll();
            }
            object = writeLock;
            if (writeLock != null) return object;
            return new WriteLock();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void offer(WriteLock writeLock) {
            Queue<WriteLock> queue = this.pool;
            synchronized (queue) {
                if (this.pool.size() < 10) {
                    this.pool.offer(writeLock);
                }
                return;
            }
        }
    }

}

