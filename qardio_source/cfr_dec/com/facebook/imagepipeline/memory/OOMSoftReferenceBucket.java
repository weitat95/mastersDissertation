/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.facebook.imagepipeline.memory;

import com.facebook.common.references.OOMSoftReference;
import com.facebook.imagepipeline.memory.Bucket;
import java.util.LinkedList;
import java.util.Queue;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
class OOMSoftReferenceBucket<V>
extends Bucket<V> {
    private LinkedList<OOMSoftReference<V>> mSpareReferences = new LinkedList();

    public OOMSoftReferenceBucket(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    void addToFreeList(V v) {
        OOMSoftReference oOMSoftReference;
        OOMSoftReference oOMSoftReference2 = oOMSoftReference = this.mSpareReferences.poll();
        if (oOMSoftReference == null) {
            oOMSoftReference2 = new OOMSoftReference();
        }
        oOMSoftReference2.set(v);
        this.mFreeList.add(oOMSoftReference2);
    }

    @Override
    public V pop() {
        OOMSoftReference oOMSoftReference = (OOMSoftReference)this.mFreeList.poll();
        Object t = oOMSoftReference.get();
        oOMSoftReference.clear();
        this.mSpareReferences.add(oOMSoftReference);
        return (V)t;
    }
}

