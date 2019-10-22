/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.cart;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.cart.CartListItemViewModel;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;

public final class CartListItemViewModel$ItemViewHolder_ViewBinding
implements Unbinder {
    private CartListItemViewModel.ItemViewHolder target;
    private View view2131820978;
    private View view2131820980;

    public CartListItemViewModel$ItemViewHolder_ViewBinding(final CartListItemViewModel.ItemViewHolder itemViewHolder, View view) {
        View view2;
        this.target = itemViewHolder;
        itemViewHolder.imageView = Utils.findRequiredViewAsType(view, 2131820710, "field 'imageView'", ShopifyDraweeView.class);
        itemViewHolder.titleView = Utils.findRequiredViewAsType(view, 2131820567, "field 'titleView'", TextView.class);
        itemViewHolder.variantView = Utils.findRequiredViewAsType(view, 2131820976, "field 'variantView'", TextView.class);
        itemViewHolder.priceView = Utils.findRequiredViewAsType(view, 2131820975, "field 'priceView'", TextView.class);
        itemViewHolder.quantityView = Utils.findRequiredViewAsType(view, 2131820979, "field 'quantityView'", TextView.class);
        itemViewHolder.dividerView = Utils.findRequiredView(view, 2131820981, "field 'dividerView'");
        this.view2131820978 = view2 = Utils.findRequiredView(view, 2131820978, "method 'onDecrementQuantityClick'");
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                itemViewHolder.onDecrementQuantityClick();
            }
        });
        this.view2131820980 = view = Utils.findRequiredView(view, 2131820980, "method 'onIncrementQuantityClick'");
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                itemViewHolder.onIncrementQuantityClick();
            }
        });
    }

    public void unbind() {
        CartListItemViewModel.ItemViewHolder itemViewHolder = this.target;
        if (itemViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        itemViewHolder.imageView = null;
        itemViewHolder.titleView = null;
        itemViewHolder.variantView = null;
        itemViewHolder.priceView = null;
        itemViewHolder.quantityView = null;
        itemViewHolder.dividerView = null;
        this.view2131820978.setOnClickListener(null);
        this.view2131820978 = null;
        this.view2131820980.setOnClickListener(null);
        this.view2131820980 = null;
    }

}

