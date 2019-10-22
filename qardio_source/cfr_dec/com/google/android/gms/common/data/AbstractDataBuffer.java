/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzb;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T>
implements DataBuffer<T> {
    protected final DataHolder zzfqt;

    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.zzfqt = dataHolder;
    }

    @Override
    public int getCount() {
        if (this.zzfqt == null) {
            return 0;
        }
        return this.zzfqt.zzfwg;
    }

    @Override
    public Iterator<T> iterator() {
        return new zzb(this);
    }

    @Override
    public void release() {
        if (this.zzfqt != null) {
            this.zzfqt.close();
        }
    }
}

