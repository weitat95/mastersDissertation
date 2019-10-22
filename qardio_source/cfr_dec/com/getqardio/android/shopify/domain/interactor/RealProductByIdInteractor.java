/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.interactor.ProductByIdInteractor;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$10;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$3;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$4;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$5;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$6;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$7;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$8;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor$$Lambda$9;
import com.getqardio.android.shopify.domain.model.ProductDetails;
import com.getqardio.android.shopify.domain.repository.ProductRepository;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.Storefront;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public final class RealProductByIdInteractor
implements ProductByIdInteractor {
    private final ProductRepository repository = new ProductRepository(ShopifySDK.graphClient());

    static /* synthetic */ void lambda$execute$7(Storefront.ProductQuery productQuery) {
        productQuery.title().descriptionHtml().tags().images(250, RealProductByIdInteractor$$Lambda$3.lambdaFactory$()).options(RealProductByIdInteractor$$Lambda$4.lambdaFactory$()).variants(250, RealProductByIdInteractor$$Lambda$5.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$0(Storefront.ImageEdgeQuery imageEdgeQuery) {
        imageEdgeQuery.node(RealProductByIdInteractor$$Lambda$10.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$1(Storefront.ImageConnectionQuery imageConnectionQuery) {
        imageConnectionQuery.edges(RealProductByIdInteractor$$Lambda$9.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$2(Storefront.ProductOptionQuery productOptionQuery) {
        productOptionQuery.name().values();
    }

    static /* synthetic */ void lambda$null$3(Storefront.SelectedOptionQuery selectedOptionQuery) {
        selectedOptionQuery.name().value();
    }

    static /* synthetic */ void lambda$null$4(Storefront.ProductVariantQuery productVariantQuery) {
        productVariantQuery.title().availableForSale().selectedOptions(RealProductByIdInteractor$$Lambda$8.lambdaFactory$()).price().compareAtPrice();
    }

    static /* synthetic */ void lambda$null$5(Storefront.ProductVariantEdgeQuery productVariantEdgeQuery) {
        productVariantEdgeQuery.node(RealProductByIdInteractor$$Lambda$7.lambdaFactory$());
    }

    static /* synthetic */ void lambda$null$6(Storefront.ProductVariantConnectionQuery productVariantConnectionQuery) {
        productVariantConnectionQuery.edges(RealProductByIdInteractor$$Lambda$6.lambdaFactory$());
    }

    @Override
    public Single<ProductDetails> execute(String string2) {
        Util.checkNotNull(string2, "productId == null");
        Storefront.ProductQueryDefinition productQueryDefinition = RealProductByIdInteractor$$Lambda$1.lambdaFactory$();
        return this.repository.product(string2, productQueryDefinition).map(RealProductByIdInteractor$$Lambda$2.lambdaFactory$());
    }
}

