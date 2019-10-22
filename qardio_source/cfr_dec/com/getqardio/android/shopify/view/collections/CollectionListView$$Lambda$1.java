/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.ProgressLiveData;
import com.getqardio.android.shopify.view.collections.CollectionListView;
import java.lang.invoke.LambdaForm;

final class CollectionListView$$Lambda$1
implements Observer {
    private final CollectionListView arg$1;

    private CollectionListView$$Lambda$1(CollectionListView collectionListView) {
        this.arg$1 = collectionListView;
    }

    public static Observer lambdaFactory$(CollectionListView collectionListView) {
        return new CollectionListView$$Lambda$1(collectionListView);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        this.arg$1.lambda$bindViewModel$0((ProgressLiveData.Progress)object);
    }
}

