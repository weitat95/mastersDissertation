/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import com.getqardio.android.shopify.view.collections.RealCollectionListViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.lang.invoke.LambdaForm;

final class RealCollectionListViewModel$$Lambda$1
implements ObservableTransformer {
    private final RealCollectionListViewModel arg$1;

    private RealCollectionListViewModel$$Lambda$1(RealCollectionListViewModel realCollectionListViewModel) {
        this.arg$1 = realCollectionListViewModel;
    }

    public static ObservableTransformer lambdaFactory$(RealCollectionListViewModel realCollectionListViewModel) {
        return new RealCollectionListViewModel$$Lambda$1(realCollectionListViewModel);
    }

    @LambdaForm.Hidden
    public ObservableSource apply(Observable observable) {
        return this.arg$1.lambda$nextPageRequestComposer$1(observable);
    }
}

