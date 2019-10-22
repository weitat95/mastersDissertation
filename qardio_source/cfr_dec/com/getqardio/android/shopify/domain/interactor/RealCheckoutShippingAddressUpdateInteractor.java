/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.interactor.CheckoutFragment;
import com.getqardio.android.shopify.domain.interactor.CheckoutShippingAddressUpdateInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingAddressUpdateInteractor$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingAddressUpdateInteractor$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingAddressUpdateInteractor$$Lambda$3;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.domain.model.UserMessageError;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.getqardio.android.shopify.domain.repository.UserError;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.Storefront;
import com.shopify.buy3.pay.PayAddress;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public final class RealCheckoutShippingAddressUpdateInteractor
implements CheckoutShippingAddressUpdateInteractor {
    private final CheckoutRepository repository = new CheckoutRepository(ShopifySDK.graphClient());

    static /* synthetic */ void lambda$execute$0(Storefront.CheckoutShippingAddressUpdatePayloadQuery checkoutShippingAddressUpdatePayloadQuery) {
        checkoutShippingAddressUpdatePayloadQuery.checkout(new CheckoutFragment());
    }

    static /* synthetic */ SingleSource lambda$execute$1(Throwable throwable) throws Exception {
        Throwable throwable2 = throwable;
        if (throwable instanceof UserError) {
            throwable2 = new UserMessageError(throwable.getMessage());
        }
        return Single.error(throwable2);
    }

    @Override
    public Single<Checkout> execute(String string2, PayAddress object) {
        Util.checkNotBlank(string2, "checkoutId can't be empty");
        Util.checkNotNull(object, "address == null");
        object = new Storefront.MailingAddressInput().setAddress1(((PayAddress)object).address1).setAddress2(((PayAddress)object).address2).setCity(((PayAddress)object).city).setCountry(((PayAddress)object).country).setFirstName(((PayAddress)object).firstName).setLastName(((PayAddress)object).lastName).setPhone(((PayAddress)object).phone).setProvince(((PayAddress)object).province).setZip(((PayAddress)object).zip);
        return this.repository.updateShippingAddress(string2, (Storefront.MailingAddressInput)object, RealCheckoutShippingAddressUpdateInteractor$$Lambda$1.lambdaFactory$()).map(RealCheckoutShippingAddressUpdateInteractor$$Lambda$2.lambdaFactory$()).onErrorResumeNext(RealCheckoutShippingAddressUpdateInteractor$$Lambda$3.lambdaFactory$());
    }
}

