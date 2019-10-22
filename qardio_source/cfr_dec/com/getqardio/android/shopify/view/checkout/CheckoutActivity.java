/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.model.Payment;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.LifeCycleBoundCallback;
import com.getqardio.android.shopify.view.ProgressDialogHelper;
import com.getqardio.android.shopify.view.ProgressLiveData;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity$$Lambda$1;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity$$Lambda$2;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity$$Lambda$3;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity$$Lambda$4;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity$$Lambda$5;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity$$Lambda$6;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity$$Lambda$7;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity$$Lambda$8;
import com.getqardio.android.shopify.view.checkout.CheckoutShippingRatesViewModel;
import com.getqardio.android.shopify.view.checkout.CheckoutViewModel;
import com.getqardio.android.shopify.view.checkout.RealCheckoutViewModel;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView;
import com.getqardio.android.shopify.view.checkout.TotalSummaryView;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.google.android.gms.wallet.fragment.WalletFragmentStyle;
import com.shopify.buy3.pay.PayCart;
import com.shopify.buy3.pay.PayHelper;
import java.math.BigDecimal;

public final class CheckoutActivity
extends AppCompatActivity
implements LifecycleRegistryOwner {
    public static final String EXTRAS_CHECKOUT_ID = "checkout_id";
    public static final String EXTRAS_MASKED_WALLET = "masked_wallet";
    public static final String EXTRAS_PAY_CART = "pay_cart";
    private String checkoutId;
    private CheckoutViewModel checkoutViewModel;
    @BindView
    View confirmLayoutView;
    private GoogleApiClient googleApiClient;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private MaskedWallet maskedWallet;
    private PayCart payCart;
    private ProgressDialogHelper progressDialogHelper;
    @BindView
    View rootView;
    @BindView
    ShippingRatesView shippingRatesView;
    @BindView
    Toolbar toolbarView;
    @BindView
    TotalSummaryView totalSummaryView;

    private void connectGoogleApiClient() {
        if (PayHelper.isAndroidPayEnabledInManifest((Context)this)) {
            this.googleApiClient = new GoogleApiClient.Builder((Context)this).addApi(Wallet.API, new Wallet.WalletOptions.Builder().setEnvironment(ShopifyKeyManager.getInstance().resolveAndroidPayEnvironment()).setTheme(0).build()).build();
            this.googleApiClient.connect();
        }
    }

    private void hideProgress(int n) {
        this.progressDialogHelper.dismiss(n);
    }

    private void initViewModels() {
        RealCheckoutViewModel realCheckoutViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory(){

            @Override
            public <T extends ViewModel> T create(Class<T> class_) {
                if (class_.equals(RealCheckoutViewModel.class)) {
                    return (T)new RealCheckoutViewModel(CheckoutActivity.this.checkoutId, CheckoutActivity.this.payCart, CheckoutActivity.this.maskedWallet);
                }
                return null;
            }
        }).get(RealCheckoutViewModel.class);
        realCheckoutViewModel.progressLiveData().observe(this, CheckoutActivity$$Lambda$1.lambdaFactory$(this));
        realCheckoutViewModel.errorErrorCallback().observe(this.getLifecycle(), CheckoutActivity$$Lambda$2.lambdaFactory$(this));
        realCheckoutViewModel.payCartLiveData().observe(this, CheckoutActivity$$Lambda$3.lambdaFactory$(this));
        realCheckoutViewModel.maskedWalletLiveData().observe(this, CheckoutActivity$$Lambda$4.lambdaFactory$(this));
        realCheckoutViewModel.successPaymentLiveData().observe(this, (Observer<Payment>)CheckoutActivity$$Lambda$5.lambdaFactory$(this));
        this.checkoutViewModel = realCheckoutViewModel;
        this.shippingRatesView.bindViewModel(realCheckoutViewModel);
    }

    static /* synthetic */ void lambda$showAlertErrorMessage$5(DialogInterface dialogInterface, int n) {
    }

    private void showAlertErrorMessage(String string2) {
        new AlertDialog.Builder((Context)this).setMessage(string2).setPositiveButton(17039370, CheckoutActivity$$Lambda$7.lambdaFactory$()).show();
    }

    private void showCheckoutSuccessMessage() {
        new AlertDialog.Builder((Context)this).setMessage(2131362479).setPositiveButton(17039370, CheckoutActivity$$Lambda$8.lambdaFactory$(this)).show();
    }

    private void showDefaultErrorMessage() {
        Snackbar snackbar = Snackbar.make(this.rootView, 2131362490, 0);
        snackbar.getView().setBackgroundResource(2131689551);
        snackbar.getView().setMinimumHeight(this.confirmLayoutView.getHeight());
        TextView textView = (TextView)snackbar.getView().findViewById(2131821024);
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.height = this.confirmLayoutView.getHeight();
        textView.setLayoutParams(layoutParams);
        textView.setGravity(16);
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

    /*
     * Enabled aggressive block sorting
     */
    private void showProgress(int n) {
        String string2 = n == CheckoutViewModel.REQUEST_ID_UPDATE_CHECKOUT_SHIPPING_ADDRESS ? this.getString(2131362485) : (n == CheckoutShippingRatesViewModel.REQUEST_ID_FETCH_SHIPPING_RATES ? this.getResources().getString(2131362472) : (n == CheckoutViewModel.REQUEST_ID_APPLY_SHIPPING_RATE ? this.getResources().getString(2131362469) : (n == CheckoutViewModel.REQUEST_ID_COMPLETE_CHECKOUT ? this.getString(2131362470) : this.getString(2131362531))));
        this.progressDialogHelper.show(n, null, string2, CheckoutActivity$$Lambda$6.lambdaFactory$(this));
    }

    private void updateMaskedWallet(MaskedWallet maskedWallet) {
        Object object = (SupportWalletFragment)this.getSupportFragmentManager().findFragmentById(2131820760);
        if (object != null) {
            ((SupportWalletFragment)object).updateMaskedWallet(maskedWallet);
            return;
        }
        object = new WalletFragmentStyle().setMaskedWalletDetailsHeaderTextAppearance(2131493270).setMaskedWalletDetailsTextAppearance(2131493271).setMaskedWalletDetailsBackgroundColor(17170445).setMaskedWalletDetailsButtonBackgroundColor(17170445).setMaskedWalletDetailsButtonTextAppearance(2131493269);
        object = SupportWalletFragment.newInstance(WalletFragmentOptions.newBuilder().setEnvironment(ShopifyKeyManager.getInstance().resolveAndroidPayEnvironment()).setFragmentStyle((WalletFragmentStyle)object).setTheme(1).setMode(2).build());
        PayHelper.initializeWalletFragment((SupportWalletFragment)object, maskedWallet);
        this.getSupportFragmentManager().beginTransaction().replace(2131820760, (Fragment)object).commit();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.lifecycleRegistry;
    }

    /* synthetic */ void lambda$initViewModels$0(ProgressLiveData.Progress progress) {
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

    /* synthetic */ void lambda$initViewModels$1(UserErrorCallback.Error error) {
        if (error != null) {
            this.showError(error.requestId, error.t, error.message);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$initViewModels$2(PayCart payCart) {
        if (payCart == null) {
            this.totalSummaryView.render(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            return;
        }
        TotalSummaryView totalSummaryView = this.totalSummaryView;
        BigDecimal bigDecimal = payCart.subtotal;
        BigDecimal bigDecimal2 = payCart.shippingPrice != null ? payCart.shippingPrice : BigDecimal.ZERO;
        BigDecimal bigDecimal3 = payCart.taxPrice != null ? payCart.taxPrice : BigDecimal.ZERO;
        totalSummaryView.render(bigDecimal, bigDecimal2, bigDecimal3, payCart.totalPrice);
    }

    /* synthetic */ void lambda$initViewModels$3(MaskedWallet maskedWallet) {
        if (maskedWallet != null) {
            this.updateMaskedWallet(maskedWallet);
        }
    }

    /* synthetic */ void lambda$initViewModels$4(Payment payment) {
        if (payment != null) {
            this.showCheckoutSuccessMessage();
        }
    }

    /* synthetic */ void lambda$showCheckoutSuccessMessage$6(DialogInterface dialogInterface, int n) {
        this.finish();
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        this.checkoutViewModel.handleWalletResponse(n, n2, intent, this.googleApiClient);
    }

    @OnClick
    void onConfirmClick() {
        this.checkoutViewModel.confirmCheckout(this.googleApiClient);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCreate(Bundle bundle) {
        boolean bl = true;
        super.onCreate(bundle);
        this.setContentView(2130968604);
        ButterKnife.bind(this);
        this.setSupportActionBar(this.toolbarView);
        this.getSupportActionBar().setTitle(2131362484);
        this.getSupportActionBar().setHomeAsUpIndicator(2130837750);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.progressDialogHelper = new ProgressDialogHelper((Context)this);
        this.checkoutId = Util.checkNotBlank(this.getIntent().getStringExtra(EXTRAS_CHECKOUT_ID), "checkoutId can't be empty");
        bundle = this.getIntent().getParcelableExtra(EXTRAS_PAY_CART);
        if (this.payCart != null) {
            bl = false;
        }
        this.payCart = (PayCart)Util.checkNotNull(bundle, bl);
        this.maskedWallet = (MaskedWallet)this.getIntent().getParcelableExtra(EXTRAS_MASKED_WALLET);
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

    @Override
    protected void onResume() {
        super.onResume();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
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

