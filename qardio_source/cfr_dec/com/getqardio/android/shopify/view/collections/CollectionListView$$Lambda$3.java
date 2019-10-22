/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import android.arch.lifecycle.Observer;
import com.getqardio.android.shopify.view.collections.CollectionListView;
import java.lang.invoke.LambdaForm;
import java.util.List;

final class CollectionListView$$Lambda$3
implements Observer {
    private final CollectionListView arg$1;

    private CollectionListView$$Lambda$3(CollectionListView collectionListView) {
        this.arg$1 = collectionListView;
    }

    public static Observer lambdaFactory$(CollectionListView collectionListView) {
        return new CollectionListView$$Lambda$3(collectionListView);
    }

    @LambdaForm.Hidden
    public void onChanged(Object object) {
        CollectionListView.access$lambda$0(this.arg$1, (List)object);
    }
}

