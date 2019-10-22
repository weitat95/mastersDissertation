/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import java.util.LinkedList;
import java.util.Queue;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
class Bucket<V> {
    final Queue mFreeList;
    private int mInUseLength;
    public final int mItemSize;
    public final int mMaxLength;

    /*
     * Enabled aggressive block sorting
     */
    public Bucket(int n, int n2, int n3) {
        boolean bl = true;
        boolean bl2 = n > 0;
        Preconditions.checkState(bl2);
        bl2 = n2 >= 0;
        Preconditions.checkState(bl2);
        bl2 = n3 >= 0 ? bl : false;
        Preconditions.checkState(bl2);
        this.mItemSize = n;
        this.mMaxLength = n2;
        this.mFreeList = new LinkedList();
        this.mInUseLength = n3;
    }

    void addToFreeList(V v) {
        this.mFreeList.add(v);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void decrementInUseCount() {
        boolean bl = this.mInUseLength > 0;
        Preconditions.checkState(bl);
        --this.mInUseLength;
    }

    @Nullable
    public V get() {
        V v = this.pop();
        if (v != null) {
            ++this.mInUseLength;
        }
        return v;
    }

    int getFreeListSize() {
        return this.mFreeList.size();
    }

    public int getInUseCount() {
        return this.mInUseLength;
    }

    public void incrementInUseCount() {
        ++this.mInUseLength;
    }

    public boolean isMaxLengthExceeded() {
        return this.mInUseLength + this.getFreeListSize() > this.mMaxLength;
    }

    @Nullable
    public V pop() {
        return (V)this.mFreeList.poll();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void release(V v) {
        Preconditions.checkNotNull(v);
        boolean bl = this.mInUseLength > 0;
        Preconditions.checkState(bl);
        --this.mInUseLength;
        this.addToFreeList(v);
    }
}

