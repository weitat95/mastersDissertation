/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.interactor.CollectionProductNextPageInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$10;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$3;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$4;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$5;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$6;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$7;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$8;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor$$Lambda$9;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.domain.repository.ProductRepository;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.Storefront;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import java.util.List;

public final class RealCollectionProductNextPageInteractor
implements CollectionProductNextPageInteractor {
    private final ProductRepository repository = new ProductRepository(ShopifySDK.graphClient());

    static /* synthetic */ void lambda$execute$7(Storefront.ProductConnectionQuery productConnectionQuery) {
        productConnectionQuery.edges(RealCollectionProductNextPageInteractor$$Lambda$3.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$0(Storefront.ImageEdgeQuery imageEdgeQuery) {
        imageEdgeQuery.node(RealCollectionProductNextPageInteractor$$Lambda$10.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$1(Storefront.ImageConnectionQuery imageConnectionQuery) {
        imageConnectionQuery.edges(RealCollectionProductNextPageInteractor$$Lambda$9.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$2(Storefront.ProductVariantQuery productVariantQuery) {
        productVariantQuery.price().sku().compareAtPrice();
    }

    static /* synthetic */ void lambda$null$3(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
        productVariantEdgeQuery.node(RealCollectionProductNextPageInteractor$$Lambda$8.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$4(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
        productVariantConnectionQuery.edges(RealCollectionProductNextPageInteractor$$Lambda$7.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$5(Storefront.ProductQuery productQuery) {
        productQuery.title().images(1, RealCollectionProductNextPageInteractor$$Lambda$5.lambdaFactory$()).variants(250, RealCollectionProductNextPageInteractor$$Lambda$6.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$6(Storefront.ProductEdgeQuery productEdgeQuery) {
        productEdgeQuery.cursor().node(RealCollectionProductNextPageInteractor$$Lambda$4.lambdaFactory$());
    }

    @Override
    public Single<List<Product>> execute(String string2, String string3, int n) {
        Util.checkNotNull(string2, "collectionId == null");
        Storefront.ProductConnectionQueryDefinition productConnectionQueryDefinition = RealCollectionProductNextPageInteractor$$Lambda$1.lambdaFactory$();
        return this.repository.nextPage(string2, string3, n, productConnectionQueryDefinition).map(RealCollectionProductNextPageInteractor$$Lambda$2.lambdaFactory$());
    }
}

