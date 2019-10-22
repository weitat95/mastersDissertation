/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.Window
 */
package com.getqardio.android.shopify.view.cart;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.cart.CartActivity;
import com.getqardio.android.shopify.view.cart.CartHeaderView;
import com.getqardio.android.shopify.view.cart.CartListView;

public final class CartActivity_ViewBinding
implements Unbinder {
    private CartActivity target;

    public CartActivity_ViewBinding(CartActivity cartActivity) {
        this(cartActivity, cartActivity.getWindow().getDecorView());
    }

    public CartActivity_ViewBinding(CartActivity cartActivity, View view) {
        this.target = cartActivity;
        cartActivity.rootView = Utils.findRequiredView(view, 2131820754, "field 'rootView'");
        cartActivity.cartHeaderView = Utils.findRequiredViewAsType(view, 2131820756, "field 'cartHeaderView'", CartHeaderView.class);
        cartActivity.cartListView = Utils.findRequiredViewAsType(view, 2131820757, "field 'cartListView'", CartListView.class);
        cartActivity.toolbarView = Utils.findRequiredViewAsType(view, 2131820755, "field 'toolbarView'", Toolbar.class);
    }

    public void unbind() {
        CartActivity cartActivity = this.target;
        if (cartActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        cartActivity.rootView = null;
        cartActivity.cartHeaderView = null;
        cartActivity.cartListView = null;
        cartActivity.toolbarView = null;
    }
}

