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
 */
package com.getqardio.android.shopify.view.products;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import com.getqardio.android.shopify.view.ScreenRouter;
import com.getqardio.android.shopify.view.cart.CartClickActionEvent;
import com.getqardio.android.shopify.view.products.ProductListActivity$$Lambda$1;
import com.getqardio.android.shopify.view.products.ProductListView;
import com.getqardio.android.shopify.view.products.RealProductListViewModel;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;

public final class ProductListActivity
extends AppCompatActivity {
    public static final String EXTRAS_COLLECTION_ID = "collection_id";
    public static final String EXTRAS_COLLECTION_IMAGE_URL = "collection_image_url";
    public static final String EXTRAS_COLLECTION_TITLE = "collection_title";
    @BindView
    ShopifyDraweeView collectionImageView;
    @BindView
    ProductListView productListView;
    @BindView
    Toolbar toolbarView;

    private void initViewModels(String object) {
        object = ViewModelProviders.of(this, new ViewModelProvider.Factory((String)object){
            final /* synthetic */ String val$collectionId;
            {
                this.val$collectionId = string2;
            }

            @Override
            public <T extends ViewModel> T create(Class<T> class_) {
                if (class_.equals(RealProductListViewModel.class)) {
                    return (T)new RealProductListViewModel(this.val$collectionId);
                }
                return null;
            }
        }).get(RealProductListViewModel.class);
        this.productListView.bindViewModel((BasePaginatedListViewModel<Product>)object);
    }

    /* synthetic */ void lambda$onCreateOptionsMenu$0(View view) {
        ScreenRouter.route((Context)this, new CartClickActionEvent());
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(2130968610);
        ButterKnife.bind(this);
        object = this.getIntent().getStringExtra(EXTRAS_COLLECTION_ID);
        String string2 = this.getIntent().getStringExtra(EXTRAS_COLLECTION_TITLE);
        String string3 = this.getIntent().getStringExtra(EXTRAS_COLLECTION_IMAGE_URL);
        Util.checkNotNull(object, "collectionId == null");
        Util.checkNotNull(string2, "collectionTitle == null");
        this.setSupportActionBar(this.toolbarView);
        this.getSupportActionBar().setTitle(string2);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.collectionImageView.loadShopifyImage(string3);
        this.initViewModels((String)object);
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131886087, menu2);
        menu2.findItem(2131821475).getActionView().setOnClickListener(ProductListActivity$$Lambda$1.lambdaFactory$(this));
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

}

