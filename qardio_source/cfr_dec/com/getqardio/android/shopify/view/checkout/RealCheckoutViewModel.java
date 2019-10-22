/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.interactor.CartClearInteractor;
import com.getqardio.android.shopify.domain.interactor.CheckoutCompleteInteractor;
import com.getqardio.android.shopify.domain.interactor.CheckoutShippingAddressUpdateInteractor;
import com.getqardio.android.shopify.domain.interactor.CheckoutShippingLineUpdateInteractor;
import com.getqardio.android.shopify.domain.interactor.CheckoutShippingRatesInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCartClearInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCompleteInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingAddressUpdateInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingLineUpdateInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutShippingRatesInteractor;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.domain.model.Payment;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.util.WeakSingleObserver;
import com.getqardio.android.shopify.view.BaseViewModel;
import com.getqardio.android.shopify.view.LifeCycleBoundCallback;
import com.getqardio.android.shopify.view.checkout.CheckoutShippingRatesViewModel;
import com.getqardio.android.shopify.view.checkout.CheckoutViewModel;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$1;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$10;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$11;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$2;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$3;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$4;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$5;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$6;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$7;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$8;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel$$Lambda$9;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.MaskedWallet;
import com.shopify.buy3.pay.PayAddress;
import com.shopify.buy3.pay.PayCart;
import com.shopify.buy3.pay.PayHelper;
import com.shopify.buy3.pay.PaymentToken;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import timber.log.Timber;

public class RealCheckoutViewModel
extends BaseViewModel
implements CheckoutShippingRatesViewModel,
CheckoutViewModel {
    private final CartClearInteractor cartClearInteractor;
    private final CheckoutCompleteInteractor checkoutCompleteInteractor;
    private final String checkoutId;
    private final CheckoutShippingAddressUpdateInteractor checkoutShippingAddressUpdateInteractor;
    private final CheckoutShippingLineUpdateInteractor checkoutShippingLineUpdateInteractor;
    private final CheckoutShippingRatesInteractor checkoutShippingRatesInteractor = new RealCheckoutShippingRatesInteractor();
    private final MutableLiveData<MaskedWallet> maskedWalletLiveData;
    private boolean newMaskedWalletRequired;
    private final MutableLiveData<PayCart> payCartLiveData;
    private final MutableLiveData<Checkout.ShippingRate> pendingSelectShippingRateLiveData;
    private final MutableLiveData<Checkout.ShippingRate> selectedShippingRateLiveData;
    private final MutableLiveData<Checkout.ShippingRates> shippingRatesLiveData;
    private final LifeCycleBoundCallback<Payment> successPaymentLiveData;

    public RealCheckoutViewModel(String string2, PayCart payCart, MaskedWallet maskedWallet) {
        this.checkoutShippingAddressUpdateInteractor = new RealCheckoutShippingAddressUpdateInteractor();
        this.checkoutShippingLineUpdateInteractor = new RealCheckoutShippingLineUpdateInteractor();
        this.checkoutCompleteInteractor = new RealCheckoutCompleteInteractor();
        this.cartClearInteractor = new RealCartClearInteractor();
        this.payCartLiveData = new MutableLiveData();
        this.maskedWalletLiveData = new MutableLiveData();
        this.pendingSelectShippingRateLiveData = new MutableLiveData();
        this.selectedShippingRateLiveData = new MutableLiveData();
        this.shippingRatesLiveData = new MutableLiveData();
        this.successPaymentLiveData = new LifeCycleBoundCallback();
        this.checkoutId = Util.checkNotBlank(string2, "checkoutId can't be empty");
        this.payCartLiveData.setValue(Util.checkNotNull(payCart, "payCart == null"));
        this.maskedWalletLiveData.setValue(Util.checkNotNull(maskedWallet, "maskedWallet == null"));
        this.pendingSelectShippingRateLiveData.observeForever(RealCheckoutViewModel$$Lambda$1.lambdaFactory$(this));
        this.maskedWalletLiveData.observeForever(RealCheckoutViewModel$$Lambda$2.lambdaFactory$(this));
        this.successPaymentLiveData.observeForever(RealCheckoutViewModel$$Lambda$3.lambdaFactory$(this));
    }

    static /* synthetic */ void access$lambda$0(RealCheckoutViewModel realCheckoutViewModel, Checkout checkout) {
        realCheckoutViewModel.onUpdateCheckoutShippingAddress(checkout);
    }

    static /* synthetic */ void access$lambda$1(RealCheckoutViewModel realCheckoutViewModel, Checkout.ShippingRates shippingRates) {
        realCheckoutViewModel.onShippingRates(shippingRates);
    }

    static /* synthetic */ void access$lambda$2(RealCheckoutViewModel realCheckoutViewModel, Payment payment) {
        realCheckoutViewModel.onCompleteCheckout(payment);
    }

    private void applyShippingRate(Checkout.ShippingRate shippingRate) {
        this.showProgress(REQUEST_ID_APPLY_SHIPPING_RATE);
        this.registerRequest(REQUEST_ID_APPLY_SHIPPING_RATE, this.checkoutShippingLineUpdateInteractor.execute(this.checkoutId, shippingRate.handle).observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakSingleObserver.forTarget(this).delegateOnSuccess(RealCheckoutViewModel$$Lambda$4.lambdaFactory$()).delegateOnError(RealCheckoutViewModel$$Lambda$5.lambdaFactory$()).create()));
    }

    private void completeCheckout(FullWallet fullWallet) {
        this.newMaskedWalletRequired = true;
        PaymentToken paymentToken = PayHelper.extractPaymentToken(fullWallet, ShopifyKeyManager.getInstance().resolveAndroidPayPublicKey());
        PayAddress payAddress = PayAddress.fromUserAddress(fullWallet.getBuyerBillingAddress());
        if (paymentToken == null) {
            this.notifyUserError(-1, new RuntimeException("Failed to extract Android payment token"));
            return;
        }
        this.showProgress(REQUEST_ID_COMPLETE_CHECKOUT);
        this.registerRequest(REQUEST_ID_COMPLETE_CHECKOUT, this.checkoutCompleteInteractor.execute(this.checkoutId, this.payCartLiveData().getValue(), paymentToken, fullWallet.getEmail(), payAddress).observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakSingleObserver.forTarget(this).delegateOnSuccess(RealCheckoutViewModel$$Lambda$10.lambdaFactory$()).delegateOnError(RealCheckoutViewModel$$Lambda$11.lambdaFactory$()).create()));
    }

    private void invalidateShippingRates() {
        Util.checkNotBlank(this.checkoutId, "checkoutId can't be empty");
        this.showProgress(REQUEST_ID_FETCH_SHIPPING_RATES);
        this.registerRequest(REQUEST_ID_FETCH_SHIPPING_RATES, this.checkoutShippingRatesInteractor.execute(this.checkoutId).observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakSingleObserver.forTarget(this).delegateOnSuccess(RealCheckoutViewModel$$Lambda$8.lambdaFactory$()).delegateOnError(RealCheckoutViewModel$$Lambda$9.lambdaFactory$()).create()));
    }

    static /* synthetic */ void lambda$applyShippingRate$3(RealCheckoutViewModel realCheckoutViewModel, Checkout checkout) {
        realCheckoutViewModel.onApplyShippingRate(checkout, REQUEST_ID_APPLY_SHIPPING_RATE);
    }

    static /* synthetic */ void lambda$applyShippingRate$4(RealCheckoutViewModel realCheckoutViewModel, Throwable throwable) {
        realCheckoutViewModel.onRequestError(REQUEST_ID_APPLY_SHIPPING_RATE, throwable);
    }

    static /* synthetic */ void lambda$completeCheckout$7(RealCheckoutViewModel realCheckoutViewModel, Throwable throwable) {
        realCheckoutViewModel.onRequestError(REQUEST_ID_COMPLETE_CHECKOUT, throwable);
    }

    static /* synthetic */ void lambda$invalidateShippingRates$6(RealCheckoutViewModel realCheckoutViewModel, Throwable throwable) {
        realCheckoutViewModel.onRequestError(REQUEST_ID_FETCH_SHIPPING_RATES, throwable);
    }

    static /* synthetic */ void lambda$updateShippingAddress$5(RealCheckoutViewModel realCheckoutViewModel, Throwable throwable) {
        realCheckoutViewModel.onRequestError(REQUEST_ID_UPDATE_CHECKOUT_SHIPPING_ADDRESS, throwable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onApplyShippingRate(Checkout checkout, int n) {
        this.hideProgress(n);
        this.selectedShippingRateLiveData.setValue(checkout.shippingLine);
        MutableLiveData<PayCart> mutableLiveData = this.payCartLiveData;
        PayCart.Builder builder = ((PayCart)this.payCartLiveData.getValue()).toBuilder();
        BigDecimal bigDecimal = checkout.shippingLine != null ? checkout.shippingLine.price : null;
        mutableLiveData.setValue(builder.shippingPrice(bigDecimal).totalPrice(checkout.totalPrice).taxPrice(checkout.taxPrice).build());
    }

    private void onCompleteCheckout(Payment payment) {
        this.hideProgress(REQUEST_ID_COMPLETE_CHECKOUT);
        if (!payment.ready) {
            Timber.e("Payment transaction has not been finished yet", new Object[0]);
            this.notifyUserError(REQUEST_ID_COMPLETE_CHECKOUT, new RuntimeException("Payment transaction has not been finished yet"));
            return;
        }
        if (payment.errorMessage != null) {
            Timber.e("Payment transaction failed", new Object[0]);
            this.notifyUserError(REQUEST_ID_COMPLETE_CHECKOUT, new RuntimeException("Payment transaction failed"), payment.errorMessage);
            return;
        }
        this.successPaymentLiveData.notify(payment);
    }

    private void onRequestError(int n, Throwable throwable) {
        Timber.e(throwable);
        if (n == REQUEST_ID_UPDATE_CHECKOUT_SHIPPING_ADDRESS) {
            this.newMaskedWalletRequired = true;
        }
        this.hideProgress(n);
        this.notifyUserError(n, throwable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onShippingRates(Checkout.ShippingRates object) {
        this.hideProgress(REQUEST_ID_FETCH_SHIPPING_RATES);
        MutableLiveData<Checkout.ShippingRates> mutableLiveData = this.shippingRatesLiveData;
        Object object2 = object != null ? object : new Checkout.ShippingRates(false, Collections.<Checkout.ShippingRate>emptyList());
        mutableLiveData.setValue((Checkout.ShippingRates)object2);
        object2 = this.pendingSelectShippingRateLiveData;
        object = object != null ? Util.firstItem(((Checkout.ShippingRates)object).shippingRates) : null;
        ((MutableLiveData)object2).setValue((Checkout.ShippingRate)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onUpdateCheckoutShippingAddress(Checkout checkout) {
        MutableLiveData<PayCart> mutableLiveData = this.payCartLiveData;
        PayCart.Builder builder = ((PayCart)this.payCartLiveData.getValue()).toBuilder();
        BigDecimal bigDecimal = checkout.shippingLine != null ? checkout.shippingLine.price : null;
        mutableLiveData.setValue(builder.shippingPrice(bigDecimal).totalPrice(checkout.totalPrice).taxPrice(checkout.taxPrice).subtotal(checkout.subtotalPrice).build());
        this.invalidateShippingRates();
    }

    private void updateShippingAddress(PayAddress payAddress) {
        this.pendingSelectShippingRateLiveData.setValue(null);
        this.selectedShippingRateLiveData.setValue(null);
        this.shippingRatesLiveData.setValue(new Checkout.ShippingRates(false, Collections.<Checkout.ShippingRate>emptyList()));
        this.showProgress(REQUEST_ID_UPDATE_CHECKOUT_SHIPPING_ADDRESS);
        this.registerRequest(REQUEST_ID_UPDATE_CHECKOUT_SHIPPING_ADDRESS, this.checkoutShippingAddressUpdateInteractor.execute(Util.checkNotBlank(this.checkoutId, "checkoutId can't be empty"), payAddress).observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakSingleObserver.forTarget(this).delegateOnSuccess(RealCheckoutViewModel$$Lambda$6.lambdaFactory$()).delegateOnError(RealCheckoutViewModel$$Lambda$7.lambdaFactory$()).create()));
    }

    @Override
    public void confirmCheckout(GoogleApiClient googleApiClient) {
        Util.checkNotNull(googleApiClient, "googleApiClient == null");
        Checkout.ShippingRate shippingRate = this.selectedShippingRateLiveData().getValue();
        if (shippingRate == null) {
            this.notifyUserError(REQUEST_ID_CONFIRM_CHECKOUT, new CheckoutViewModel.ShippingRateMissingException());
            return;
        }
        this.payCartLiveData.setValue(((PayCart)this.payCartLiveData.getValue()).toBuilder().shippingPrice(shippingRate.price).build());
        if (this.newMaskedWalletRequired) {
            PayHelper.newMaskedWallet(googleApiClient, (MaskedWallet)this.maskedWalletLiveData.getValue());
            return;
        }
        PayHelper.requestFullWallet(googleApiClient, (PayCart)this.payCartLiveData.getValue(), (MaskedWallet)this.maskedWalletLiveData.getValue());
    }

    @Override
    public void fetchShippingRates() {
        MaskedWallet maskedWallet = this.maskedWalletLiveData().getValue();
        if (maskedWallet == null) {
            return;
        }
        this.updateShippingAddress(PayAddress.fromUserAddress(maskedWallet.getBuyerShippingAddress()));
    }

    @Override
    public void handleWalletResponse(int n, int n2, Intent intent, final GoogleApiClient googleApiClient) {
        PayHelper.handleWalletResponse(n, n2, intent, new PayHelper.WalletResponseHandler(){

            @Override
            public void onFullWallet(FullWallet fullWallet) {
                RealCheckoutViewModel.this.completeCheckout(fullWallet);
            }

            @Override
            public void onMaskedWallet(MaskedWallet maskedWallet) {
                RealCheckoutViewModel.this.newMaskedWalletRequired = false;
                RealCheckoutViewModel.this.maskedWalletLiveData.setValue(maskedWallet);
            }

            @Override
            public void onWalletError(int n, int n2) {
                if (n2 == 410) {
                    PayHelper.requestMaskedWallet(googleApiClient, (PayCart)RealCheckoutViewModel.this.payCartLiveData.getValue(), ShopifyKeyManager.getInstance().resolveAndroidPayPublicKey());
                    return;
                }
                RealCheckoutViewModel.this.notifyUserError(-1, new RuntimeException("Failed wallet request, errorCode: " + n2));
            }
        });
    }

    /* synthetic */ void lambda$new$0(Checkout.ShippingRate shippingRate) {
        this.cancelAllRequests();
        this.selectedShippingRateLiveData.setValue(null);
        if (shippingRate != null) {
            this.applyShippingRate(shippingRate);
        }
    }

    /* synthetic */ void lambda$new$1(MaskedWallet maskedWallet) {
        this.cancelAllRequests();
        if (maskedWallet != null) {
            this.updateShippingAddress(PayAddress.fromUserAddress(maskedWallet.getBuyerShippingAddress()));
        }
    }

    /* synthetic */ void lambda$new$2(Payment payment) {
        if (payment != null) {
            this.cartClearInteractor.execute();
        }
    }

    @Override
    public LiveData<MaskedWallet> maskedWalletLiveData() {
        return this.maskedWalletLiveData;
    }

    @Override
    public LiveData<PayCart> payCartLiveData() {
        return this.payCartLiveData;
    }

    @Override
    public void selectShippingRate(Checkout.ShippingRate shippingRate) {
        this.pendingSelectShippingRateLiveData.setValue(shippingRate);
    }

    @Override
    public LiveData<Checkout.ShippingRate> selectedShippingRateLiveData() {
        return this.selectedShippingRateLiveData;
    }

    @Override
    public LiveData<Checkout.ShippingRates> shippingRatesLiveData() {
        return this.shippingRatesLiveData;
    }

    @Override
    public LifeCycleBoundCallback<Payment> successPaymentLiveData() {
        return this.successPaymentLiveData;
    }

}

