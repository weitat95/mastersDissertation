/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.core.internal.SafeIterableMap;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import java.util.Iterator;
import java.util.Map;

public class MediatorLiveData<T>
extends MutableLiveData<T> {
    private SafeIterableMap<LiveData<?>, Source<?>> mSources = new SafeIterableMap();

    /*
     * Enabled aggressive block sorting
     */
    public <S> void addSource(LiveData<S> source, Observer<S> observer) {
        Source<S> source2 = new Source<S>((LiveData<S>)((Object)source), observer);
        if ((source = this.mSources.putIfAbsent((LiveData<?>)((Object)source), source2)) != null && source.mObserver != observer) {
            throw new IllegalArgumentException("This source was already added with the different observer");
        }
        if (source != null || !this.hasActiveObservers()) {
            return;
        }
        source2.plug();
    }

    @Override
    protected void onActive() {
        Iterator iterator = this.mSources.iterator();
        while (iterator.hasNext()) {
            ((Source)((Map.Entry)iterator.next()).getValue()).plug();
        }
    }

    @Override
    protected void onInactive() {
        Iterator iterator = this.mSources.iterator();
        while (iterator.hasNext()) {
            ((Source)((Map.Entry)iterator.next()).getValue()).unplug();
        }
    }

    private static class Source<V> {
        final LiveData<V> mLiveData;
        final Observer<V> mObserver;
        int mVersion = -1;

        Source(LiveData<V> liveData, final Observer<V> observer) {
            this.mLiveData = liveData;
            this.mObserver = new Observer<V>(){

                @Override
                public void onChanged(V v) {
                    if (Source.this.mVersion != Source.this.mLiveData.getVersion()) {
                        Source.this.mVersion = Source.this.mLiveData.getVersion();
                        observer.onChanged(v);
                    }
                }
            };
        }

        void plug() {
            this.mLiveData.observeForever(this.mObserver);
        }

        void unplug() {
            this.mLiveData.removeObserver(this.mObserver);
        }

    }

}

