/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.checkout;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.checkout.ShippingRateSelectDialog;

public final class ShippingRateSelectDialog$ItemViewHolder_ViewBinding
implements Unbinder {
    private ShippingRateSelectDialog.ItemViewHolder target;
    private View view2131820754;

    public ShippingRateSelectDialog$ItemViewHolder_ViewBinding(final ShippingRateSelectDialog.ItemViewHolder itemViewHolder, View view) {
        this.target = itemViewHolder;
        itemViewHolder.titleView = Utils.findRequiredViewAsType(view, 2131820567, "field 'titleView'", TextView.class);
        itemViewHolder.priceView = Utils.findRequiredViewAsType(view, 2131820975, "field 'priceView'", TextView.class);
        this.view2131820754 = view = Utils.findRequiredView(view, 2131820754, "method 'onRootViewClick'");
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                itemViewHolder.onRootViewClick();
            }
        });
    }

    public void unbind() {
        ShippingRateSelectDialog.ItemViewHolder itemViewHolder = this.target;
        if (itemViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        itemViewHolder.titleView = null;
        itemViewHolder.priceView = null;
        this.view2131820754.setOnClickListener(null);
        this.view2131820754 = null;
    }

}

