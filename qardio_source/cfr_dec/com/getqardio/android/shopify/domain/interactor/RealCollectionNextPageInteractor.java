/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.interactor.CollectionNextPageInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$10;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$11;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$12;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$13;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$14;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$3;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$4;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$5;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$6;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$7;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$8;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor$$Lambda$9;
import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.domain.repository.CollectionRepository;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.Storefront;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import java.util.List;

public final class RealCollectionNextPageInteractor
implements CollectionNextPageInteractor {
    private final CollectionRepository repository = new CollectionRepository(ShopifySDK.graphClient());

    static /* synthetic */ void lambda$execute$10(int n, Storefront.CollectionConnectionQuery collectionConnectionQuery) {
        collectionConnectionQuery.edges(RealCollectionNextPageInteractor$$Lambda$3.lambdaFactory$(n));
    }

    static /* synthetic */ void lambda$null$0(Storefront.ImageEdgeQuery imageEdgeQuery) {
        imageEdgeQuery.node(RealCollectionNextPageInteractor$$Lambda$14.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$1(Storefront.ImageConnectionQuery imageConnectionQuery) {
        imageConnectionQuery.edges(RealCollectionNextPageInteractor$$Lambda$13.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$2(Storefront.ProductVariantQuery productVariantQuery) {
        productVariantQuery.price().sku().compareAtPrice();
    }

    static /* synthetic */ void lambda$null$3(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
        productVariantEdgeQuery.node(RealCollectionNextPageInteractor$$Lambda$12.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$4(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
        productVariantConnectionQuery.edges(RealCollectionNextPageInteractor$$Lambda$11.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$5(Storefront.ProductQuery productQuery) {
        productQuery.title().images(1, RealCollectionNextPageInteractor$$Lambda$9.lambdaFactory$()).variants(250, RealCollectionNextPageInteractor$$Lambda$10.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$6(Storefront.ProductEdgeQuery productEdgeQuery) {
        productEdgeQuery.cursor().node(RealCollectionNextPageInteractor$$Lambda$8.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$7(Storefront.ProductConnectionQuery productConnectionQuery) {
        productConnectionQuery.edges(RealCollectionNextPageInteractor$$Lambda$7.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$8(int n, Storefront.CollectionQuery collectionQuery) {
        collectionQuery.title().description().image(RealCollectionNextPageInteractor$$Lambda$5.lambdaFactory$()).products(n, RealCollectionNextPageInteractor$$Lambda$6.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$9(int n, Storefront.CollectionEdgeQuery collectionEdgeQuery) {
        collectionEdgeQuery.cursor().node(RealCollectionNextPageInteractor$$Lambda$4.lambdaFactory$(n));
    }

    @Override
    public Single<List<Collection>> execute(String string2, int n) {
        Storefront.CollectionConnectionQueryDefinition collectionConnectionQueryDefinition = RealCollectionNextPageInteractor$$Lambda$1.lambdaFactory$(n);
        return this.repository.nextPage(string2, n, collectionConnectionQueryDefinition).map(RealCollectionNextPageInteractor$$Lambda$2.lambdaFactory$());
    }
}

