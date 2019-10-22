/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.internal.zzbq;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T>
implements Iterator<T> {
    protected final DataBuffer<T> zzfvu;
    protected int zzfvv;

    public zzb(DataBuffer<T> dataBuffer) {
        this.zzfvu = zzbq.checkNotNull(dataBuffer);
        this.zzfvv = -1;
    }

    @Override
    public boolean hasNext() {
        return this.zzfvv < this.zzfvu.getCount() - 1;
    }

    @Override
    public T next() {
        int n;
        if (!this.hasNext()) {
            int n2 = this.zzfvv;
            throw new NoSuchElementException(new StringBuilder(46).append("Cannot advance the iterator beyond ").append(n2).toString());
        }
        DataBuffer<T> dataBuffer = this.zzfvu;
        this.zzfvv = n = this.zzfvv + 1;
        return dataBuffer.get(n);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}

