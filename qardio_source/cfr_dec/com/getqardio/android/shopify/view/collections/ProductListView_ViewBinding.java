/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.shopify.view.collections;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.collections.ProductListView;

public final class ProductListView_ViewBinding
implements Unbinder {
    private ProductListView target;

    public ProductListView_ViewBinding(ProductListView productListView) {
        this(productListView, (View)productListView);
    }

    public ProductListView_ViewBinding(ProductListView productListView, View view) {
        this.target = productListView;
        productListView.listView = Utils.findRequiredViewAsType(view, 2131820758, "field 'listView'", RecyclerView.class);
    }

    public void unbind() {
        ProductListView productListView = this.target;
        if (productListView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        productListView.listView = null;
    }
}

