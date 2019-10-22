/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.view.cart.CartHeaderView$$Lambda$1;
import com.getqardio.android.shopify.view.cart.CartHeaderView$$Lambda$2;
import com.getqardio.android.shopify.view.cart.CartHeaderView$$Lambda$3;
import com.getqardio.android.shopify.view.cart.CartHeaderViewModel;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public final class CartHeaderView
extends FrameLayout
implements LifecycleOwner {
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(ShopifyKeyManager.getInstance().getLocale());
    @BindView
    View androidPayCheckoutView;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @BindView
    TextView subtotalView;
    private CartHeaderViewModel viewModel;

    public CartHeaderView(Context context) {
        super(context);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public CartHeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public CartHeaderView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public void bindViewModel(CartHeaderViewModel cartHeaderViewModel) {
        if (this.viewModel != null) {
            throw new IllegalStateException("Already bound");
        }
        this.viewModel = cartHeaderViewModel;
        LiveData<BigDecimal> liveData = cartHeaderViewModel.cartTotalLiveData();
        NumberFormat numberFormat = CURRENCY_FORMAT;
        numberFormat.getClass();
        Transformations.map(liveData, CartHeaderView$$Lambda$1.lambdaFactory$(numberFormat)).observe(this, CartHeaderView$$Lambda$2.lambdaFactory$(this));
        cartHeaderViewModel.googleApiClientConnectionData().observe(this, CartHeaderView$$Lambda$3.lambdaFactory$(this));
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.lifecycleRegistry;
    }

    /* synthetic */ void lambda$bindViewModel$0(String string2) {
        this.subtotalView.setText((CharSequence)string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$bindViewModel$1(Boolean bl) {
        View view = this.androidPayCheckoutView;
        int n = bl == Boolean.TRUE ? 0 : 8;
        view.setVisibility(n);
    }

    @OnClick
    void onAndroidPayCheckoutClick() {
        this.viewModel.androidPayCheckout();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    protected void onDetachedFromWindow() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        super.onDetachedFromWindow();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View)this);
        this.subtotalView.setText((CharSequence)CURRENCY_FORMAT.format(0L));
    }

    @OnClick
    void onWebCheckoutClick() {
        this.viewModel.webCheckout();
    }
}

