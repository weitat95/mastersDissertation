/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.cart;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.cart.CartSubtotalListItemViewModel;

public final class CartSubtotalListItemViewModel$ItemViewHolder_ViewBinding
implements Unbinder {
    private CartSubtotalListItemViewModel.ItemViewHolder target;

    public CartSubtotalListItemViewModel$ItemViewHolder_ViewBinding(CartSubtotalListItemViewModel.ItemViewHolder itemViewHolder, View view) {
        this.target = itemViewHolder;
        itemViewHolder.subtotalView = Utils.findRequiredViewAsType(view, 2131820970, "field 'subtotalView'", TextView.class);
    }

    public void unbind() {
        CartSubtotalListItemViewModel.ItemViewHolder itemViewHolder = this.target;
        if (itemViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        itemViewHolder.subtotalView = null;
    }
}

