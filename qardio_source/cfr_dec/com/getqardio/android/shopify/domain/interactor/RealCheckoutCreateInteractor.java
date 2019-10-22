/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.interactor.CheckoutCreateFragment;
import com.getqardio.android.shopify.domain.interactor.CheckoutCreateInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCreateInteractor$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCreateInteractor$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCreateInteractor$$Lambda$3;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCreateInteractor$$Lambda$4;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.domain.model.UserMessageError;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.getqardio.android.shopify.domain.repository.UserError;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.Storefront;
import com.shopify.graphql.support.ID;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.List;

public final class RealCheckoutCreateInteractor
implements CheckoutCreateInteractor {
    private final CheckoutRepository repository = new CheckoutRepository(ShopifySDK.graphClient());

    static /* synthetic */ Storefront.CheckoutLineItemInput lambda$execute$0(Checkout.LineItem lineItem) {
        return new Storefront.CheckoutLineItemInput(lineItem.quantity, new ID(lineItem.variantId));
    }

    static /* synthetic */ void lambda$execute$1(Storefront.CheckoutCreatePayloadQuery checkoutCreatePayloadQuery) {
        checkoutCreatePayloadQuery.checkout(new CheckoutCreateFragment());
    }

    static /* synthetic */ SingleSource lambda$execute$2(Throwable throwable) throws Exception {
        Throwable throwable2 = throwable;
        if (throwable instanceof UserError) {
            throwable2 = new UserMessageError(throwable.getMessage());
        }
        return Single.error(throwable2);
    }

    @Override
    public Single<Checkout> execute(List<Checkout.LineItem> object) {
        Util.checkNotEmpty(object, "lineItems can't be empty");
        object = Util.mapItems(object, RealCheckoutCreateInteractor$$Lambda$1.lambdaFactory$());
        object = new Storefront.CheckoutCreateInput().setLineItems((List<Storefront.CheckoutLineItemInput>)object);
        return this.repository.create((Storefront.CheckoutCreateInput)object, RealCheckoutCreateInteractor$$Lambda$2.lambdaFactory$()).map(RealCheckoutCreateInteractor$$Lambda$3.lambdaFactory$()).onErrorResumeNext(RealCheckoutCreateInteractor$$Lambda$4.lambdaFactory$());
    }
}

