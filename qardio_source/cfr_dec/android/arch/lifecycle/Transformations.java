/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;

public class Transformations {
    public static <X, Y> LiveData<Y> map(LiveData<X> liveData, final Function<X, Y> function) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(liveData, new Observer<X>(){

            @Override
            public void onChanged(X x) {
                mediatorLiveData.setValue(function.apply(x));
            }
        });
        return mediatorLiveData;
    }

}

