/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.shopify.buy3.Storefront;
import java.lang.invoke.LambdaForm;

final class RealCollectionNextPageInteractor$$Lambda$1
implements Storefront.CollectionConnectionQueryDefinition {
    private final int arg$1;

    private RealCollectionNextPageInteractor$$Lambda$1(int n) {
        this.arg$1 = n;
    }

    public static Storefront.CollectionConnectionQueryDefinition lambdaFactory$(int n) {
        return new RealCollectionNextPageInteractor$$Lambda$1(n);
    }

    @LambdaForm.Hidden
    @Override
    public void define(Storefront.CollectionConnectionQuery collectionConnectionQuery) {
        RealCollectionNextPageInteractor.lambda$execute$10(this.arg$1, collectionConnectionQuery);
    }
}

