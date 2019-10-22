/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import com.getqardio.android.shopify.view.collections.CollectionPaginatedListViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.lang.invoke.LambdaForm;

final class CollectionPaginatedListViewModel$$Lambda$1
implements ObservableTransformer {
    private final CollectionPaginatedListViewModel arg$1;

    private CollectionPaginatedListViewModel$$Lambda$1(CollectionPaginatedListViewModel collectionPaginatedListViewModel) {
        this.arg$1 = collectionPaginatedListViewModel;
    }

    public static ObservableTransformer lambdaFactory$(CollectionPaginatedListViewModel collectionPaginatedListViewModel) {
        return new CollectionPaginatedListViewModel$$Lambda$1(collectionPaginatedListViewModel);
    }

    @LambdaForm.Hidden
    public ObservableSource apply(Observable observable) {
        return this.arg$1.lambda$nextPageRequestComposer$1(observable);
    }
}

