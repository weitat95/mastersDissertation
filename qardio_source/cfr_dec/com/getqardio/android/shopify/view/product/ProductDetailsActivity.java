/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 */
package com.getqardio.android.shopify.view.product;

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
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.ShopifyAnalytics;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.model.ProductDetails;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.ProgressLiveData;
import com.getqardio.android.shopify.view.ScreenRouter;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.cart.CartClickActionEvent;
import com.getqardio.android.shopify.view.product.ProductDescriptionView;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity$$Lambda$1;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity$$Lambda$2;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity$$Lambda$3;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity$$Lambda$4;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity$$Lambda$5;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity$$Lambda$6;
import com.getqardio.android.shopify.view.product.ProductViewModel;
import com.getqardio.android.shopify.view.product.RealProductViewModel;
import com.getqardio.android.shopify.view.widget.image.ImageGalleryView;
import com.shopify.buy3.GraphNetworkError;
import java.util.Arrays;
import java.util.List;

public final class ProductDetailsActivity
extends AppCompatActivity
implements LifecycleRegistryOwner {
    public static final String EXTRAS_PRODUCT_ID = "product_id";
    public static final String EXTRAS_PRODUCT_IMAGE_URL = "product_image_url";
    public static final String EXTRAS_PRODUCT_PRICE = "product_price";
    public static final String EXTRAS_PRODUCT_SKU = "product_sku";
    public static final String EXTRAS_PRODUCT_TITLE = "product_title";
    @BindView
    Button addToCart;
    @BindView
    ImageGalleryView imageGalleryView;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @BindView
    ProductDescriptionView productDescriptionView;
    private ProductViewModel productViewModel;
    @BindView
    View rootView;
    @BindView
    SwipeRefreshLayout swipeRefreshLayoutView;
    @BindView
    Toolbar toolbarView;

    static /* synthetic */ void access$lambda$0(ProductDetailsActivity productDetailsActivity, ProductDetails productDetails) {
        productDetailsActivity.renderProduct(productDetails);
    }

    private void initViewModels(final String string2) {
        this.productViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory(){

            @Override
            public <T extends ViewModel> T create(Class<T> class_) {
                if (class_.equals(RealProductViewModel.class)) {
                    return (T)new RealProductViewModel(string2);
                }
                return null;
            }
        }).get(RealProductViewModel.class);
        this.productViewModel.productLiveData().observe(this, ProductDetailsActivity$$Lambda$4.lambdaFactory$(this));
        this.productViewModel.progressLiveData().observe(this, ProductDetailsActivity$$Lambda$5.lambdaFactory$(this));
        this.productViewModel.errorErrorCallback().observe(this.getLifecycle(), ProductDetailsActivity$$Lambda$6.lambdaFactory$(this));
    }

    private void renderProduct(ProductDetails productDetails) {
        this.imageGalleryView.renderImages(productDetails.images);
        this.productDescriptionView.renderProduct(productDetails);
    }

    private void showDefaultErrorMessage() {
        Snackbar snackbar = Snackbar.make(this.rootView, 2131362490, 0);
        snackbar.getView().setBackgroundResource(2131689551);
        snackbar.show();
    }

    private void showNetworkErrorMessage() {
        Snackbar snackbar = Snackbar.make(this.rootView, 2131362518, 0);
        snackbar.getView().setBackgroundResource(2131689551);
        snackbar.show();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.lifecycleRegistry;
    }

    /* synthetic */ void lambda$initViewModels$3(ProgressLiveData.Progress progress) {
        if (progress != null) {
            this.swipeRefreshLayoutView.setRefreshing(progress.show);
        }
    }

    /* synthetic */ void lambda$initViewModels$4(UserErrorCallback.Error error) {
        block3: {
            block2: {
                if (error == null) break block2;
                if (!(error.t instanceof GraphNetworkError)) break block3;
                this.showNetworkErrorMessage();
            }
            return;
        }
        this.showDefaultErrorMessage();
    }

    /* synthetic */ void lambda$onCreate$1() {
        this.productViewModel.refetch();
    }

    /* synthetic */ void lambda$onCreate$2(String string2, View view) {
        ShopifyAnalytics.getInstance().trackAddToCart(string2, ShopifyKeyManager.getInstance().getStoreFromCountry());
        this.productViewModel.addToCart();
    }

    /* synthetic */ void lambda$onCreateOptionsMenu$0(View view) {
        ScreenRouter.route((Context)this, new CartClickActionEvent());
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(2130968609);
        ButterKnife.bind(this);
        object = this.getIntent().getStringExtra(EXTRAS_PRODUCT_ID);
        String string2 = this.getIntent().getStringExtra(EXTRAS_PRODUCT_SKU);
        String string3 = this.getIntent().getStringExtra(EXTRAS_PRODUCT_TITLE);
        String string4 = this.getIntent().getStringExtra(EXTRAS_PRODUCT_IMAGE_URL);
        double d = this.getIntent().getDoubleExtra(EXTRAS_PRODUCT_PRICE, 0.0);
        Util.checkNotNull(object, "productId == null");
        Util.checkNotNull(string3, "productTitle == null");
        ShopifyAnalytics.getInstance().trackViewProduct(string2, ShopifyKeyManager.getInstance().getStoreFromCountry());
        this.setSupportActionBar(this.toolbarView);
        this.getSupportActionBar().setTitle(string3);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.initViewModels((String)object);
        this.imageGalleryView.renderImages(Arrays.asList(string4));
        this.swipeRefreshLayoutView.setOnRefreshListener(ProductDetailsActivity$$Lambda$2.lambdaFactory$(this));
        this.productDescriptionView.renderProduct(string3, d);
        this.addToCart.setOnClickListener(ProductDetailsActivity$$Lambda$3.lambdaFactory$(this, string2));
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131886087, menu2);
        menu2.findItem(2131821475).getActionView().setOnClickListener(ProductDetailsActivity$$Lambda$1.lambdaFactory$(this));
        return true;
    }

    @Override
    protected void onDestroy() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        super.onDestroy();
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

