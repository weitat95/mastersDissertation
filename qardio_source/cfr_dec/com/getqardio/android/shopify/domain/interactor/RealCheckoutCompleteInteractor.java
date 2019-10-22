/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.domain.interactor;

import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.interactor.CheckoutCompleteInteractor;
import com.getqardio.android.shopify.domain.interactor.CheckoutFragment;
import com.getqardio.android.shopify.domain.interactor.PaymentFragment;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor$$Lambda$1;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor$$Lambda$2;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor$$Lambda$3;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor$$Lambda$4;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor$$Lambda$5;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor$$Lambda$6;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor$$Lambda$7;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor$$Lambda$8;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor$$Lambda$9;
import com.getqardio.android.shopify.domain.model.Payment;
import com.getqardio.android.shopify.domain.model.UserMessageError;
import com.getqardio.android.shopify.domain.repository.CheckoutRepository;
import com.getqardio.android.shopify.domain.repository.UserError;
import com.getqardio.android.shopify.util.NotReadyException;
import com.getqardio.android.shopify.util.RxRetryHandler;
import com.getqardio.android.shopify.util.Util;
import com.shopify.buy3.GraphClient;
import com.shopify.buy3.GraphHttpError;
import com.shopify.buy3.GraphNetworkError;
import com.shopify.buy3.Storefront;
import com.shopify.buy3.pay.PayAddress;
import com.shopify.buy3.pay.PayCart;
import com.shopify.buy3.pay.PaymentToken;
import com.shopify.graphql.support.ID;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public final class RealCheckoutCompleteInteractor
implements CheckoutCompleteInteractor {
    private final CheckoutRepository repository = new CheckoutRepository(ShopifySDK.graphClient());

    static /* synthetic */ void lambda$execute$0(Storefront.CheckoutEmailUpdatePayloadQuery checkoutEmailUpdatePayloadQuery) {
        checkoutEmailUpdatePayloadQuery.checkout(new CheckoutFragment());
    }

    static /* synthetic */ SingleSource lambda$execute$7(Throwable throwable) throws Exception {
        Throwable throwable2 = throwable;
        if (throwable instanceof UserError) {
            throwable2 = new UserMessageError(throwable.getMessage());
        }
        return Single.error(throwable2);
    }

    static /* synthetic */ void lambda$null$1(Storefront.CheckoutCompleteWithTokenizedPaymentPayloadQuery checkoutCompleteWithTokenizedPaymentPayloadQuery) {
        checkoutCompleteWithTokenizedPaymentPayloadQuery.payment(new PaymentFragment());
    }

    static /* synthetic */ void lambda$null$3(Storefront.NodeQuery nodeQuery) {
        nodeQuery.onPayment(new PaymentFragment());
    }

    static /* synthetic */ SingleSource lambda$null$4(Storefront.Payment payment) throws Exception {
        if (payment.getReady().booleanValue()) {
            return Single.just(payment);
        }
        return Single.error(new NotReadyException("Payment transaction is not finished"));
    }

    static /* synthetic */ boolean lambda$null$5(Throwable throwable) throws Exception {
        return throwable instanceof NotReadyException || throwable instanceof GraphHttpError || throwable instanceof GraphNetworkError;
    }

    @Override
    public Single<Payment> execute(String string2, PayCart object, PaymentToken paymentToken, String string3, PayAddress object2) {
        Util.checkNotBlank(string2, "checkoutId can't be empty");
        Util.checkNotNull(object, "payCart == null");
        Util.checkNotNull(paymentToken, "paymentToken == null");
        Util.checkNotBlank(string3, "email can't be empty");
        Util.checkNotNull(object2, "billingAddress == null");
        object2 = new Storefront.MailingAddressInput().setAddress1(((PayAddress)object2).address1).setAddress2(((PayAddress)object2).address2).setCity(((PayAddress)object2).city).setCountry(((PayAddress)object2).country).setFirstName(((PayAddress)object2).firstName).setLastName(((PayAddress)object2).lastName).setPhone(((PayAddress)object2).phone).setProvince(((PayAddress)object2).province).setZip(((PayAddress)object2).zip);
        object = new Storefront.TokenizedPaymentInput(((PayCart)object).totalPrice, paymentToken.token, (Storefront.MailingAddressInput)object2, "android_pay", paymentToken.token).setIdentifier(paymentToken.publicKeyHash);
        return this.repository.updateEmail(string2, string3, RealCheckoutCompleteInteractor$$Lambda$1.lambdaFactory$()).flatMap(RealCheckoutCompleteInteractor$$Lambda$2.lambdaFactory$(this, string2, (Storefront.TokenizedPaymentInput)object)).flatMap(RealCheckoutCompleteInteractor$$Lambda$3.lambdaFactory$(this)).map(RealCheckoutCompleteInteractor$$Lambda$4.lambdaFactory$()).onErrorResumeNext(RealCheckoutCompleteInteractor$$Lambda$5.lambdaFactory$());
    }

    /* synthetic */ SingleSource lambda$execute$2(String string2, Storefront.TokenizedPaymentInput tokenizedPaymentInput, Storefront.Checkout checkout) throws Exception {
        return this.repository.complete(string2, tokenizedPaymentInput, RealCheckoutCompleteInteractor$$Lambda$9.lambdaFactory$());
    }

    /* synthetic */ SingleSource lambda$execute$6(Storefront.Payment payment) throws Exception {
        if (payment.getReady().booleanValue()) {
            return Single.just(payment);
        }
        return this.repository.paymentById(payment.getId().toString(), RealCheckoutCompleteInteractor$$Lambda$6.lambdaFactory$()).flatMap(RealCheckoutCompleteInteractor$$Lambda$7.lambdaFactory$()).retryWhen(RxRetryHandler.exponentialBackoff(500L, TimeUnit.MILLISECONDS, 1.2f).maxRetries(10).when(RealCheckoutCompleteInteractor$$Lambda$8.lambdaFactory$()).build());
    }
}

