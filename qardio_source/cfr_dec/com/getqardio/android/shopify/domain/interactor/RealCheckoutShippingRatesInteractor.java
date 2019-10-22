/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.interactor.CheckoutShippingRatesFragment;
import com.getqardio.android.shopify.domain.interactor.CheckoutShippingRatesInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingRatesInteractor$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingRatesInteractor$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingRatesInteractor$$Lambda$3;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingRatesInteractor$$Lambda$4;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.getqardio.android.shopify.util.NotReadyException;
import com.getqardio.android.shopify.util.RxRetryHandler;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.GraphHttpError;
import com.shopify.buy3.GraphNetworkError;
import com.shopify.buy3.Storefront;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public final class RealCheckoutShippingRatesInteractor
implements CheckoutShippingRatesInteractor {
    private final CheckoutRepository repository = new CheckoutRepository(ShopifySDK.graphClient());

    static /* synthetic */ void lambda$execute$0(Storefront.CheckoutQuery checkoutQuery) {
        checkoutQuery.availableShippingRates(new CheckoutShippingRatesFragment());
    }

    static /* synthetic */ SingleSource lambda$execute$1(Checkout.ShippingRates shippingRates) throws Exception {
        if (shippingRates.ready) {
            return Single.just(shippingRates);
        }
        return Single.error(new NotReadyException("Shipping rates not available yet"));
    }

    static /* synthetic */ boolean lambda$execute$2(Throwable throwable) throws Exception {
        return throwable instanceof NotReadyException || throwable instanceof GraphHttpError || throwable instanceof GraphNetworkError;
    }

    @Override
    public Single<Checkout.ShippingRates> execute(String string2) {
        Util.checkNotBlank(string2, "checkoutId can't be empty");
        return this.repository.shippingRates(string2, RealCheckoutShippingRatesInteractor$$Lambda$1.lambdaFactory$()).map(RealCheckoutShippingRatesInteractor$$Lambda$2.lambdaFactory$()).flatMap(RealCheckoutShippingRatesInteractor$$Lambda$3.lambdaFactory$()).retryWhen(RxRetryHandler.exponentialBackoff(500L, TimeUnit.MILLISECONDS, 1.2f).maxRetries(10).when(RealCheckoutShippingRatesInteractor$$Lambda$4.lambdaFactory$()).build());
    }
}

