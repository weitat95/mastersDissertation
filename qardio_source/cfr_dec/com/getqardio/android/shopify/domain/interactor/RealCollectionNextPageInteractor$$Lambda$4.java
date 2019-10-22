/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$4
implements Storefront.CollectionQueryDefinition {
    private final int arg$1;

    private RealCollectionNextPageInteractor$$Lambda$4(int n) {
        this.arg$1 = n;
    }

    public static Storefront.CollectionQueryDefinition lambdaFactory$(int n) {
        return new RealCollectionNextPageInteractor$$Lambda$4(n);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CollectionQuery collectionQuery) {
        RealCollectionNextPageInteractor.lambda$null$8(this.arg$1, collectionQuery);
    }
}

