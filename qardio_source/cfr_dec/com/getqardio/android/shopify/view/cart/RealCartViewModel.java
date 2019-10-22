/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.getqardio.android.shopify.ShopifyAnalytics;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.interactor.CartWatchInteractor;
import com.getqardio.android.shopify.domain.interactor.CheckoutCreateInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCartWatchInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCheckoutCreateInteractor;
import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.domain.model.ShopSettings;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.BaseViewModel;
import com.getqardio.android.shopify.view.LifeCycleBoundCallback;
import com.getqardio.android.shopify.view.cart.CartDetailsViewModel;
import com.getqardio.android.shopify.view.cart.CartHeaderViewModel;
import com.getqardio.android.shopify.view.cart.RealCartViewModel$$Lambda$1;
import com.getqardio.android.shopify.view.cart.RealCartViewModel$$Lambda$2;
import com.getqardio.android.shopify.view.cart.RealCartViewModel$$Lambda$3;
import com.getqardio.android.shopify.view.cart.RealCartViewModel$$Lambda$4;
import com.getqardio.android.shopify.view.cart.RealCartViewModel$$Lambda$5;
import com.getqardio.android.shopify.view.cart.RealCartViewModel$$Lambda$6;
import com.google.android.gms.wallet.MaskedWallet;
import com.shopify.buy3.pay.PayCart;
import com.shopify.buy3.pay.PayHelper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import java.math.BigDecimal;
import java.util.List;
import timber.log.Timber;

public final class RealCartViewModel
extends BaseViewModel
implements CartDetailsViewModel,
CartHeaderViewModel {
    private static final String STATE_KEY_CHECKOUT_ID = "checkout_id";
    private static final String STATE_KEY_PAY_CART = "pay_cart";
    private final LifeCycleBoundCallback<CartDetailsViewModel.AndroidPayCheckout> androidPayCheckoutCallback;
    private final LifeCycleBoundCallback<PayCart> androidPayStartCheckoutCallback;
    private final MutableLiveData<Cart> cartLiveData;
    private final CartWatchInteractor cartWatchInteractor = new RealCartWatchInteractor();
    private final CheckoutCreateInteractor checkoutCreateInteractor = new RealCheckoutCreateInteractor();
    private String checkoutId;
    private final MutableLiveData<Boolean> googleApiClientConnectionData;
    private PayCart payCart;
    private ShopSettings shopSettings;
    private final LifeCycleBoundCallback<Checkout> webCheckoutCallback = new LifeCycleBoundCallback();

    public RealCartViewModel(ShopSettings shopSettings) {
        this.androidPayStartCheckoutCallback = new LifeCycleBoundCallback();
        this.androidPayCheckoutCallback = new LifeCycleBoundCallback();
        this.cartLiveData = new MutableLiveData();
        this.googleApiClientConnectionData = new MutableLiveData();
        this.shopSettings = shopSettings;
        this.registerRequest(REQUEST_ID_UPDATE_CART, this.cartWatchInteractor.execute().observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakObserver.forTarget(this).delegateOnNext(RealCartViewModel$$Lambda$1.lambdaFactory$()).create()));
    }

    static /* synthetic */ void access$lambda$0(RealCartViewModel realCartViewModel, Cart cart) {
        realCartViewModel.onCartUpdated(cart);
    }

    private PayCart checkoutPayCart(Checkout checkout) {
        PayCart.Builder builder = PayCart.builder().merchantName(this.shopSettings.name).currencyCode(checkout.currency).countryCode(this.shopSettings.countryCode).phoneNumberRequired(true).shippingAddressRequired(checkout.requiresShipping);
        Util.fold(builder, checkout.lineItems, RealCartViewModel$$Lambda$6.lambdaFactory$());
        return builder.build();
    }

    private void createCheckout(int n, Cart object) {
        this.cancelRequest(REQUEST_ID_CREATE_WEB_CHECKOUT);
        this.cancelRequest(REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT);
        if (object == null || ((Cart)object).cartItems().isEmpty()) {
            return;
        }
        ShopifyAnalytics.getInstance().trackInitiateCheckout(((Cart)object).totalQuantity(), ((Cart)object).totalPrice().doubleValue(), ShopifyKeyManager.getInstance().getStoreFromCountry(), "web checkout");
        this.showProgress(n);
        object = Util.mapItems(((Cart)object).cartItems(), RealCartViewModel$$Lambda$3.lambdaFactory$());
        this.registerRequest(n, this.checkoutCreateInteractor.execute((List<Checkout.LineItem>)object).toObservable().observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakObserver.forTarget(this).delegateOnNext(RealCartViewModel$$Lambda$4.lambdaFactory$(n)).delegateOnError(RealCartViewModel$$Lambda$5.lambdaFactory$(n)).create()));
    }

    static /* synthetic */ BigDecimal lambda$cartTotalLiveData$0(Cart cart) {
        if (cart != null) {
            return cart.totalPrice();
        }
        return BigDecimal.ZERO;
    }

    static /* synthetic */ PayCart.Builder lambda$checkoutPayCart$4(PayCart.Builder builder, Checkout.LineItem lineItem) {
        return builder.addLineItem(lineItem.title, lineItem.quantity, lineItem.price);
    }

    static /* synthetic */ Checkout.LineItem lambda$createCheckout$1(CartItem cartItem) {
        return new Checkout.LineItem(cartItem.productVariantId, cartItem.variantTitle, cartItem.quantity, cartItem.price);
    }

    static /* synthetic */ void lambda$createCheckout$2(int n, RealCartViewModel realCartViewModel, Checkout checkout) {
        realCartViewModel.onCreateCheckout(n, checkout);
    }

    static /* synthetic */ void lambda$createCheckout$3(int n, RealCartViewModel realCartViewModel, Throwable throwable) {
        realCartViewModel.onCreateCheckoutError(n, throwable);
    }

    private void onCartUpdated(Cart cart) {
        this.cartLiveData.setValue(cart);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onCreateCheckout(int n, Checkout object) {
        this.hideProgress(n);
        this.checkoutId = ((Checkout)object).id;
        if (n == REQUEST_ID_CREATE_WEB_CHECKOUT) {
            this.webCheckoutCallback.notify((Checkout)object);
            return;
        } else {
            if (n != REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT) return;
            {
                LifeCycleBoundCallback<PayCart> lifeCycleBoundCallback = this.androidPayStartCheckoutCallback;
                this.payCart = object = this.checkoutPayCart((Checkout)object);
                lifeCycleBoundCallback.notify((PayCart)object);
                return;
            }
        }
    }

    private void onCreateCheckoutError(int n, Throwable throwable) {
        Timber.e(throwable);
        this.hideProgress(n);
        this.notifyUserError(n, throwable, null);
    }

    @Override
    public void androidPayCheckout() {
        this.createCheckout(REQUEST_ID_CREATE_ANDROID_PAY_CHECKOUT, (Cart)this.cartLiveData.getValue());
    }

    @Override
    public LifeCycleBoundCallback<CartDetailsViewModel.AndroidPayCheckout> androidPayCheckoutCallback() {
        return this.androidPayCheckoutCallback;
    }

    @Override
    public LifeCycleBoundCallback<PayCart> androidPayStartCheckoutCallback() {
        return this.androidPayStartCheckoutCallback;
    }

    @Override
    public LiveData<BigDecimal> cartTotalLiveData() {
        return Transformations.map(this.cartLiveData, RealCartViewModel$$Lambda$2.lambdaFactory$());
    }

    @Override
    public LiveData<Boolean> googleApiClientConnectionData() {
        return this.googleApiClientConnectionData;
    }

    @Override
    public void handleMaskedWalletResponse(int n, int n2, Intent intent) {
        PayHelper.handleWalletResponse(n, n2, intent, new PayHelper.WalletResponseHandler(){

            @Override
            public void onMaskedWallet(MaskedWallet maskedWallet) {
                RealCartViewModel.this.androidPayCheckoutCallback.notify(new CartDetailsViewModel.AndroidPayCheckout(RealCartViewModel.this.checkoutId, RealCartViewModel.this.payCart, maskedWallet));
            }

            @Override
            public void onWalletError(int n, int n2) {
                RealCartViewModel.this.notifyUserError(CartDetailsViewModel.REQUEST_ID_PREPARE_ANDROID_PAY, new RuntimeException("Failed to fetch masked wallet, errorCode: " + n2), null);
            }
        });
    }

    @Override
    public void onGoogleApiClientConnectionChanged(boolean bl) {
        this.googleApiClientConnectionData.setValue(bl);
    }

    @Override
    public void restoreState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        this.checkoutId = bundle.getString(STATE_KEY_CHECKOUT_ID);
        this.payCart = (PayCart)bundle.getParcelable(STATE_KEY_PAY_CART);
    }

    @Override
    public Bundle saveState() {
        Bundle bundle = new Bundle();
        bundle.putString(STATE_KEY_CHECKOUT_ID, this.checkoutId);
        bundle.putParcelable(STATE_KEY_PAY_CART, (Parcelable)this.payCart);
        return bundle;
    }

    @Override
    public void webCheckout() {
        this.createCheckout(REQUEST_ID_CREATE_WEB_CHECKOUT, (Cart)this.cartLiveData.getValue());
    }

    @Override
    public LifeCycleBoundCallback<Checkout> webCheckoutCallback() {
        return this.webCheckoutCallback;
    }

}

