/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.interactor.CheckoutFragment;
import com.getqardio.android.shopify.domain.interactor.CheckoutShippingLineUpdateInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingLineUpdateInteractor$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingLineUpdateInteractor$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingLineUpdateInteractor$$Lambda$3;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.domain.model.UserMessageError;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.getqardio.android.shopify.domain.repository.UserError;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.Storefront;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public final class RealCheckoutShippingLineUpdateInteractor
implements CheckoutShippingLineUpdateInteractor {
    private final CheckoutRepository repository = new CheckoutRepository(ShopifySDK.graphClient());

    static /* synthetic */ void lambda$execute$0(Storefront.CheckoutShippingLineUpdatePayloadQuery checkoutShippingLineUpdatePayloadQuery) {
        checkoutShippingLineUpdatePayloadQuery.checkout(new CheckoutFragment());
    }

    static /* synthetic */ SingleSource lambda$execute$1(Throwable throwable) throws Exception {
        Throwable throwable2 = throwable;
        if (throwable instanceof UserError) {
            throwable2 = new UserMessageError(throwable.getMessage());
        }
        return Single.error(throwable2);
    }

    @Override
    public Single<Checkout> execute(String string2, String string3) {
        Util.checkNotBlank(string2, "checkoutId can't be empty");
        Util.checkNotBlank(string3, "shippingRateHandle can't be empty");
        return this.repository.updateShippingLine(string2, string3, RealCheckoutShippingLineUpdateInteractor$$Lambda$1.lambdaFactory$()).map(RealCheckoutShippingLineUpdateInteractor$$Lambda$2.lambdaFactory$()).onErrorResumeNext(RealCheckoutShippingLineUpdateInteractor$$Lambda$3.lambdaFactory$());
    }
}

