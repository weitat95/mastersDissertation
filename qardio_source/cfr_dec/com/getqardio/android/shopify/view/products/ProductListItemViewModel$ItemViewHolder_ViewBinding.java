/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.products;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.products.ProductListItemViewModel;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;

public final class ProductListItemViewModel$ItemViewHolder_ViewBinding
implements Unbinder {
    private ProductListItemViewModel.ItemViewHolder target;
    private View view2131820567;
    private View view2131820710;
    private View view2131820975;

    public ProductListItemViewModel$ItemViewHolder_ViewBinding(final ProductListItemViewModel.ItemViewHolder itemViewHolder, View view) {
        this.target = itemViewHolder;
        View view2 = Utils.findRequiredView(view, 2131820710, "field 'imageView' and method 'onClick'");
        itemViewHolder.imageView = Utils.castView(view2, 2131820710, "field 'imageView'", ShopifyDraweeView.class);
        this.view2131820710 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                itemViewHolder.onClick();
            }
        });
        view2 = Utils.findRequiredView(view, 2131820567, "field 'titleView' and method 'onClick'");
        itemViewHolder.titleView = Utils.castView(view2, 2131820567, "field 'titleView'", TextView.class);
        this.view2131820567 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                itemViewHolder.onClick();
            }
        });
        view2 = Utils.findRequiredView(view, 2131820975, "field 'priceView' and method 'onClick'");
        itemViewHolder.priceView = Utils.castView(view2, 2131820975, "field 'priceView'", TextView.class);
        this.view2131820975 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                itemViewHolder.onClick();
            }
        });
        itemViewHolder.compareAtPrice = Utils.findRequiredViewAsType(view, 2131821206, "field 'compareAtPrice'", TextView.class);
    }

    public void unbind() {
        ProductListItemViewModel.ItemViewHolder itemViewHolder = this.target;
        if (itemViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        itemViewHolder.imageView = null;
        itemViewHolder.titleView = null;
        itemViewHolder.priceView = null;
        itemViewHolder.compareAtPrice = null;
        this.view2131820710.setOnClickListener(null);
        this.view2131820710 = null;
        this.view2131820567.setOnClickListener(null);
        this.view2131820567 = null;
        this.view2131820975.setOnClickListener(null);
        this.view2131820975 = null;
    }

}

