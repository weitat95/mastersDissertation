/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.shopify.view.collections;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.collections.ProductListItemViewModel;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;

public final class ProductListItemViewModel$ItemViewHolder_ViewBinding
implements Unbinder {
    private ProductListItemViewModel.ItemViewHolder target;
    private View view2131820710;

    public ProductListItemViewModel$ItemViewHolder_ViewBinding(final ProductListItemViewModel.ItemViewHolder itemViewHolder, View view) {
        this.target = itemViewHolder;
        view = Utils.findRequiredView(view, 2131820710, "field 'imageView' and method 'onImageClick'");
        itemViewHolder.imageView = Utils.castView(view, 2131820710, "field 'imageView'", ShopifyDraweeView.class);
        this.view2131820710 = view;
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                itemViewHolder.onImageClick(view);
            }
        });
    }

    public void unbind() {
        ProductListItemViewModel.ItemViewHolder itemViewHolder = this.target;
        if (itemViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        itemViewHolder.imageView = null;
        this.view2131820710.setOnClickListener(null);
        this.view2131820710 = null;
    }

}

