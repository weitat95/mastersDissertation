/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.shopify.view.cart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.cart.CartListView;

public final class CartListView_ViewBinding
implements Unbinder {
    private CartListView target;

    public CartListView_ViewBinding(CartListView cartListView) {
        this(cartListView, (View)cartListView);
    }

    public CartListView_ViewBinding(CartListView cartListView, View view) {
        this.target = cartListView;
        cartListView.listView = Utils.findRequiredViewAsType(view, 2131820758, "field 'listView'", RecyclerView.class);
    }

    public void unbind() {
        CartListView cartListView = this.target;
        if (cartListView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        cartListView.listView = null;
    }
}

