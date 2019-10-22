/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.Window
 */
package com.getqardio.android.shopify.view.products;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.products.ProductListActivity;
import com.getqardio.android.shopify.view.products.ProductListView;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;

public final class ProductListActivity_ViewBinding
implements Unbinder {
    private ProductListActivity target;

    public ProductListActivity_ViewBinding(ProductListActivity productListActivity) {
        this(productListActivity, productListActivity.getWindow().getDecorView());
    }

    public ProductListActivity_ViewBinding(ProductListActivity productListActivity, View view) {
        this.target = productListActivity;
        productListActivity.toolbarView = Utils.findRequiredViewAsType(view, 2131820755, "field 'toolbarView'", Toolbar.class);
        productListActivity.collectionImageView = Utils.findRequiredViewAsType(view, 2131820785, "field 'collectionImageView'", ShopifyDraweeView.class);
        productListActivity.productListView = Utils.findRequiredViewAsType(view, 2131820786, "field 'productListView'", ProductListView.class);
    }

    public void unbind() {
        ProductListActivity productListActivity = this.target;
        if (productListActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        productListActivity.toolbarView = null;
        productListActivity.collectionImageView = null;
        productListActivity.productListView = null;
    }
}

