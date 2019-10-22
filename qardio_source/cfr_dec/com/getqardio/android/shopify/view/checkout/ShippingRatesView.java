/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.checkout;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.view.checkout.CheckoutShippingRatesViewModel;
import com.getqardio.android.shopify.view.checkout.ShippingRateSelectDialog;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView$$Lambda$1;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView$$Lambda$2;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView$$Lambda$3;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView$$Lambda$4;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView$$Lambda$5;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public final class ShippingRatesView
extends ConstraintLayout
implements LifecycleOwner {
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(ShopifyKeyManager.getInstance().getLocale());
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @BindView
    TextView priceView;
    @BindView
    TextView shippingLineView;
    private CheckoutShippingRatesViewModel viewModel;

    public ShippingRatesView(Context context) {
        super(context);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public ShippingRatesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public ShippingRatesView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    static /* synthetic */ String lambda$bindViewModel$0(Checkout.ShippingRate shippingRate) {
        if (shippingRate != null) {
            return shippingRate.title;
        }
        return null;
    }

    public void bindViewModel(CheckoutShippingRatesViewModel checkoutShippingRatesViewModel) {
        if (this.viewModel != null) {
            throw new IllegalStateException("Already bound");
        }
        this.viewModel = checkoutShippingRatesViewModel;
        Transformations.map(checkoutShippingRatesViewModel.selectedShippingRateLiveData(), ShippingRatesView$$Lambda$1.lambdaFactory$()).observe(this, ShippingRatesView$$Lambda$2.lambdaFactory$(this));
        Transformations.map(checkoutShippingRatesViewModel.selectedShippingRateLiveData(), ShippingRatesView$$Lambda$3.lambdaFactory$(this)).observe(this, ShippingRatesView$$Lambda$4.lambdaFactory$(this));
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    /* synthetic */ void lambda$bindViewModel$1(String string2) {
        if (string2 != null) {
            this.shippingLineView.setText((CharSequence)string2);
            return;
        }
        this.shippingLineView.setText(2131362474);
    }

    /* synthetic */ String lambda$bindViewModel$2(Checkout.ShippingRate shippingRate) {
        if (shippingRate != null) {
            return CURRENCY_FORMAT.format(shippingRate.price);
        }
        return this.getResources().getString(2131362475);
    }

    /* synthetic */ void lambda$bindViewModel$3(String string2) {
        this.priceView.setText((CharSequence)string2);
    }

    /* synthetic */ void lambda$onChangeClick$4(Checkout.ShippingRate shippingRate) {
        this.viewModel.selectShippingRate(shippingRate);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @OnClick
    void onChangeClick() {
        Checkout.ShippingRates shippingRates = this.viewModel.shippingRatesLiveData().getValue();
        if (shippingRates == null || shippingRates.shippingRates.isEmpty()) {
            this.viewModel.fetchShippingRates();
            return;
        }
        new ShippingRateSelectDialog(this.getContext()).show(shippingRates, ShippingRatesView$$Lambda$5.lambdaFactory$(this));
    }

    protected void onDetachedFromWindow() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        super.onDetachedFromWindow();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View)this);
    }
}

