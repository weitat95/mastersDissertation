/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.widget;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.widget.CartBadgeView;

public final class CartBadgeView_ViewBinding
implements Unbinder {
    private CartBadgeView target;

    public CartBadgeView_ViewBinding(CartBadgeView cartBadgeView) {
        this(cartBadgeView, (View)cartBadgeView);
    }

    public CartBadgeView_ViewBinding(CartBadgeView cartBadgeView, View view) {
        this.target = cartBadgeView;
        cartBadgeView.countView = Utils.findRequiredViewAsType(view, 2131820968, "field 'countView'", TextView.class);
    }

    public void unbind() {
        CartBadgeView cartBadgeView = this.target;
        if (cartBadgeView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        cartBadgeView.countView = null;
    }
}

