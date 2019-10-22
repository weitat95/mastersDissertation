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
import com.getqardio.android.shopify.view.cart.CartHeaderView;

public final class CartHeaderView_ViewBinding
implements Unbinder {
    private CartHeaderView target;
    private View view2131820972;
    private View view2131820973;

    public CartHeaderView_ViewBinding(CartHeaderView cartHeaderView) {
        this(cartHeaderView, (View)cartHeaderView);
    }

    public CartHeaderView_ViewBinding(final CartHeaderView cartHeaderView, View view) {
        View view2;
        this.target = cartHeaderView;
        cartHeaderView.androidPayCheckoutView = view2 = Utils.findRequiredView(view, 2131820972, "field 'androidPayCheckoutView' and method 'onAndroidPayCheckoutClick'");
        this.view2131820972 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                cartHeaderView.onAndroidPayCheckoutClick();
            }
        });
        cartHeaderView.subtotalView = Utils.findRequiredViewAsType(view, 2131820970, "field 'subtotalView'", TextView.class);
        this.view2131820973 = view = Utils.findRequiredView(view, 2131820973, "method 'onWebCheckoutClick'");
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                cartHeaderView.onWebCheckoutClick();
            }
        });
    }

    public void unbind() {
        CartHeaderView cartHeaderView = this.target;
        if (cartHeaderView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        cartHeaderView.androidPayCheckoutView = null;
        cartHeaderView.subtotalView = null;
        this.view2131820972.setOnClickListener(null);
        this.view2131820972 = null;
        this.view2131820973.setOnClickListener(null);
        this.view2131820973 = null;
    }

}

