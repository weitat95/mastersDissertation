/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Bundle
 *  android.view.View
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.ShopifyAnalytics;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.ShopifySDK;
import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.domain.model.ShopSettings;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import com.getqardio.android.shopify.view.BaseViewModel;
import com.getqardio.android.shopify.view.LifeCycleBoundCallback;
import com.getqardio.android.shopify.view.ProgressDialogHelper;
import com.getqardio.android.shopify.view.ProgressLiveData;
import com.getqardio.android.shopify.view.ScreenRouter;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.cart.AndroidPayConfirmationClickActionEvent;
import com.getqardio.android.shopify.view.cart.CartActivity$$Lambda$1;
import com.getqardio.android.shopify.view.cart.CartActivity$$Lambda$2;
import com.getqardio.android.shopify.view.cart.CartActivity$$Lambda$3;
import com.getqardio.android.shopify.view.cart.CartActivity$$Lambda$4;
import com.getqardio.android.shopify.view.cart.CartActivity$$Lambda$5;
import com.getqardio.android.shopify.view.cart.CartActivity$$Lambda$6;
import com.getqardio.android.shopify.view.cart.CartActivity$$Lambda$7;
import com.getqardio.android.shopify.view.cart.CartActivity$$Lambda$8;
import com.getqardio.android.shopify.view.cart.CartDetailsViewModel;
import com.getqardio.android.shopify.view.cart.CartHeaderView;
import com.getqardio.android.shopify.view.cart.CartHeaderViewModel;
import com.getqardio.android.shopify.view.cart.CartListView;
import com.getqardio.android.shopify.view.cart.CartListViewModel;
import com.getqardio.android.shopify.view.cart.RealCartViewModel;
import com.getqardio.android.shopify.view.checkout.CheckoutViewModel;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.Wallet;
import com.shopify.buy3.pay.CardNetworkType;
import com.shopify.buy3.pay.PayCart;
import com.shopify.buy3.pay.PayHelper;
import java.util.Set;

public final class CartActivity
extends AppCompatActivity
implements LifecycleRegistryOwner,
GoogleApiClient.ConnectionCallbacks {
    private CartDetailsViewModel cartDetailsViewModel;
    @BindView
    CartHeaderView cartHeaderView;
    private CartHeaderViewModel cartHeaderViewModel;
    @BindView
    CartListView cartListView;
    private GoogleApiClient googleApiClient;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private ProgressDialogHelper progressDialogHelper;
    @BindView
    View rootView;
    @BindView
    Toolbar toolbarView;

    private void connectGoogleApiClient() {
        if (PayHelper.isAndroidPayEnabledInManifest((Context)this)) {
            this.googleApiClient = new GoogleApiClient.Builder((Context)this).addApi(Wallet.API, new Wallet.WalletOptions.Builder().setEnvironment(ShopifyKeyManager.getInstance().resolveAndroidPayEnvironment()).setTheme(0).build()).addConnectionCallbacks(this).build();
            this.googleApiClient.connect();
        }
    }

    private void hideProgress(int n) {
        this.progressDialogHelper.dismiss(n);
    }

    private void initViewModels() {
        BaseViewModel baseViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory((ShopSettings)ShopifySDK.getInstance((Context)this).shopSettings().getValue()){
            final /* synthetic */ ShopSettings val$shopSettings;
            {
                this.val$shopSettings = shopSettings;
            }

            @Override
            public <T extends ViewModel> T create(Class<T> class_) {
                if (class_.equals(RealCartViewModel.class)) {
                    return (T)new RealCartViewModel(this.val$shopSettings);
                }
                return null;
            }
        }).get(RealCartViewModel.class);
        this.cartHeaderViewModel = baseViewModel;
        this.cartDetailsViewModel = baseViewModel;
        this.cartDetailsViewModel.webCheckoutCallback().observe(this.getLifecycle(), (Observer<Checkout>)CartActivity$$Lambda$2.lambdaFactory$(this));
        this.cartDetailsViewModel.androidPayStartCheckoutCallback().observe(this.getLifecycle(), (Observer<PayCart>)CartActivity$$Lambda$3.lambdaFactory$(this));
        this.cartDetailsViewModel.androidPayCheckoutCallback().observe(this.getLifecycle(), (Observer<CartDetailsViewModel.AndroidPayCheckout>)CartActivity$$Lambda$4.lambdaFactory$(this));
        this.cartDetailsViewModel.progressLiveData().observe(this, CartActivity$$Lambda$5.lambdaFactory$(this));
        this.cartDetailsViewModel.errorErrorCallback().observe(this.getLifecycle(), CartActivity$$Lambda$6.lambdaFactory$(this));
        this.cartHeaderView.bindViewModel(this.cartHeaderViewModel);
        baseViewModel = ViewModelProviders.of(this).get(CartListViewModel.class);
        this.cartListView.bindViewModel((BasePaginatedListViewModel<CartItem>)baseViewModel);
    }

    static /* synthetic */ void lambda$showAlertErrorMessage$7(DialogInterface dialogInterface, int n) {
    }

    private void onWebCheckoutConfirmation(Checkout checkout) {
        this.startActivity(new Intent("android.intent.action.VIEW", ShopifyAnalytics.getInstance().appendGoogleAnalyticsTracking(checkout.webUrl)));
    }

    private void showAlertErrorMessage(String string2) {
        new AlertDialog.Builder((Context)this).setMessage(string2).setPositiveButton(17039370, CartActivity$$Lambda$8.lambdaFactory$()).show();
    }

    private void showDefaultErrorMessage() {
        Snackbar snackbar = Snackbar.make(this.rootView, 2131362490, 0);
        snackbar.getView().setBackgroundResource(2131689551);
        snackbar.show();
    }

    private void showError(int n, Throwable throwable, String string2) {
        if (string2 != null) {
            this.showAlertErrorMessage(string2);
            return;
        }
        if (throwable instanceof CheckoutViewModel.ShippingRateMissingException) {
            this.showAlertErrorMessage(this.getString(2131362478));
            return;
        }
        this.showDefaultErrorMessage();
    }

    private void showProgress(int n) {
        this.progressDialogHelper.show(n, null, this.getResources().getString(2131362531), CartActivity$$Lambda$7.lambdaFactory$(this, n));
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.lifecycleRegistry;
    }

    /* synthetic */ void lambda$initViewModels$1(Checkout checkout) {
        if (checkout != null) {
            this.onWebCheckoutConfirmation(checkout);
        }
    }

    /* synthetic */ void lambda$initViewModels$2(PayCart payCart) {
        if (this.cartHeaderViewModel.googleApiClientConnectionData().getValue() == Boolean.TRUE && payCart != null) {
            PayHelper.requestMaskedWallet(this.googleApiClient, payCart, ShopifyKeyManager.getInstance().resolveAndroidPayPublicKey());
        }
    }

    /* synthetic */ void lambda$initViewModels$3(CartDetailsViewModel.AndroidPayCheckout androidPayCheckout) {
        if (androidPayCheckout != null) {
            ScreenRouter.route((Context)this, new AndroidPayConfirmationClickActionEvent(androidPayCheckout.checkoutId, androidPayCheckout.payCart, androidPayCheckout.maskedWallet));
        }
    }

    /* synthetic */ void lambda$initViewModels$4(ProgressLiveData.Progress progress) {
        block3: {
            block2: {
                if (progress == null) break block2;
                if (!progress.show) break block3;
                this.showProgress(progress.requestId);
            }
            return;
        }
        this.hideProgress(progress.requestId);
    }

    /* synthetic */ void lambda$initViewModels$5(UserErrorCallback.Error error) {
        if (error != null) {
            this.showError(error.requestId, error.t, error.message);
        }
    }

    /* synthetic */ void lambda$onConnected$0(boolean bl) {
        if (this.lifecycleRegistry.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            this.cartDetailsViewModel.onGoogleApiClientConnectionChanged(true);
        }
    }

    /* synthetic */ void lambda$showProgress$6(int n) {
        this.cartDetailsViewModel.cancelRequest(n);
        this.cartDetailsViewModel.progressLiveData().hide(n);
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        this.cartDetailsViewModel.handleMaskedWalletResponse(n, n2, intent);
    }

    @Override
    public void onConnected(Bundle object) {
        object = (ShopSettings)ShopifySDK.getInstance((Context)this).shopSettings().getValue();
        PayHelper.isReadyToPay(this.getApplicationContext(), this.googleApiClient, object.acceptedCardBrands, CartActivity$$Lambda$1.lambdaFactory$(this));
    }

    @Override
    public void onConnectionSuspended(int n) {
        this.cartDetailsViewModel.onGoogleApiClientConnectionChanged(false);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968603);
        ButterKnife.bind(this);
        this.setSupportActionBar(this.toolbarView);
        this.getSupportActionBar().setTitle("Cart");
        this.getSupportActionBar().setHomeAsUpIndicator(2130837750);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.progressDialogHelper = new ProgressDialogHelper((Context)this);
        ShopifyAnalytics.getInstance().trackCartClick(ShopifyKeyManager.getInstance().getStoreFromCountry());
        this.initViewModels();
        this.connectGoogleApiClient();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override
    protected void onDestroy() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        super.onDestroy();
        if (this.progressDialogHelper != null) {
            this.progressDialogHelper.dismiss();
            this.progressDialogHelper = null;
        }
        if (this.googleApiClient != null) {
            this.googleApiClient.disconnect();
            this.googleApiClient = null;
        }
    }

    @Override
    protected void onPause() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        super.onPause();
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.cartDetailsViewModel.restoreState(bundle.getBundle(RealCartViewModel.class.getName()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle(RealCartViewModel.class.getName(), this.cartDetailsViewModel.saveState());
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    protected void onStop() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        super.onStop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

}

