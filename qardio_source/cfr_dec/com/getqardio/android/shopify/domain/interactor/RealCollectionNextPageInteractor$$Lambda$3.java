/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$3
implements Storefront.CollectionEdgeQueryDefinition {
    private final int arg$1;

    private RealCollectionNextPageInteractor$$Lambda$3(int n) {
        this.arg$1 = n;
    }

    public static Storefront.CollectionEdgeQueryDefinition lambdaFactory$(int n) {
        return new RealCollectionNextPageInteractor$$Lambda$3(n);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CollectionEdgeQuery collectionEdgeQuery) {
        RealCollectionNextPageInteractor.lambda$null$9(this.arg$1, collectionEdgeQuery);
    }
}

