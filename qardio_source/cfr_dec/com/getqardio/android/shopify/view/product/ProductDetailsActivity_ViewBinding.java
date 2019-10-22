/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.Window
 *  android.widget.Button
 */
package com.getqardio.android.shopify.view.product;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.product.ProductDescriptionView;
import com.getqardio.android.shopify.view.product.ProductDetailsActivity;
import com.getqardio.android.shopify.view.widget.image.ImageGalleryView;

public final class ProductDetailsActivity_ViewBinding
implements Unbinder {
    private ProductDetailsActivity target;

    public ProductDetailsActivity_ViewBinding(ProductDetailsActivity productDetailsActivity) {
        this(productDetailsActivity, productDetailsActivity.getWindow().getDecorView());
    }

    public ProductDetailsActivity_ViewBinding(ProductDetailsActivity productDetailsActivity, View view) {
        this.target = productDetailsActivity;
        productDetailsActivity.rootView = Utils.findRequiredView(view, 2131820754, "field 'rootView'");
        productDetailsActivity.swipeRefreshLayoutView = Utils.findRequiredViewAsType(view, 2131820783, "field 'swipeRefreshLayoutView'", SwipeRefreshLayout.class);
        productDetailsActivity.toolbarView = Utils.findRequiredViewAsType(view, 2131820755, "field 'toolbarView'", Toolbar.class);
        productDetailsActivity.imageGalleryView = Utils.findRequiredViewAsType(view, 2131820782, "field 'imageGalleryView'", ImageGalleryView.class);
        productDetailsActivity.productDescriptionView = Utils.findRequiredViewAsType(view, 2131820784, "field 'productDescriptionView'", ProductDescriptionView.class);
        productDetailsActivity.addToCart = Utils.findRequiredViewAsType(view, 2131820779, "field 'addToCart'", Button.class);
    }

    public void unbind() {
        ProductDetailsActivity productDetailsActivity = this.target;
        if (productDetailsActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        productDetailsActivity.rootView = null;
        productDetailsActivity.swipeRefreshLayoutView = null;
        productDetailsActivity.toolbarView = null;
        productDetailsActivity.imageGalleryView = null;
        productDetailsActivity.productDescriptionView = null;
        productDetailsActivity.addToCart = null;
    }
}

