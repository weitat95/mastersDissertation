/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.shopify.view.collections;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.collections.ProductListView;
import com.getqardio.android.shopify.view.collections.ProductsListItemViewModel;

public final class ProductsListItemViewModel$ItemViewHolder_ViewBinding
implements Unbinder {
    private ProductsListItemViewModel.ItemViewHolder target;

    public ProductsListItemViewModel$ItemViewHolder_ViewBinding(ProductsListItemViewModel.ItemViewHolder itemViewHolder, View view) {
        this.target = itemViewHolder;
        itemViewHolder.productListView = Utils.findRequiredViewAsType(view, 2131820786, "field 'productListView'", ProductListView.class);
    }

    public void unbind() {
        ProductsListItemViewModel.ItemViewHolder itemViewHolder = this.target;
        if (itemViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        itemViewHolder.productListView = null;
    }
}

