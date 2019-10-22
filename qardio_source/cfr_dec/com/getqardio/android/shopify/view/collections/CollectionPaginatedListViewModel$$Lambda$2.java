/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import com.getqardio.android.shopify.view.collections.CollectionPaginatedListViewModel;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.invoke.LambdaForm;

final class CollectionPaginatedListViewModel$$Lambda$2
implements Function {
    private final CollectionPaginatedListViewModel arg$1;

    private CollectionPaginatedListViewModel$$Lambda$2(CollectionPaginatedListViewModel collectionPaginatedListViewModel) {
        this.arg$1 = collectionPaginatedListViewModel;
    }

    public static Function lambdaFactory$(CollectionPaginatedListViewModel collectionPaginatedListViewModel) {
        return new CollectionPaginatedListViewModel$$Lambda$2(collectionPaginatedListViewModel);
    }

    @LambdaForm.Hidden
    public Object apply(Object object) {
        return this.arg$1.lambda$null$0((String)object);
    }
}

