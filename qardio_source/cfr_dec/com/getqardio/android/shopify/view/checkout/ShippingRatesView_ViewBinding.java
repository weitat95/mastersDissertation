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
import com.getqardio.android.shopify.view.checkout.ShippingRatesView;

public final class ShippingRatesView_ViewBinding
implements Unbinder {
    private ShippingRatesView target;
    private View view2131820982;

    public ShippingRatesView_ViewBinding(ShippingRatesView shippingRatesView) {
        this(shippingRatesView, (View)shippingRatesView);
    }

    public ShippingRatesView_ViewBinding(final ShippingRatesView shippingRatesView, View view) {
        this.target = shippingRatesView;
        shippingRatesView.shippingLineView = Utils.findRequiredViewAsType(view, 2131820983, "field 'shippingLineView'", TextView.class);
        shippingRatesView.priceView = Utils.findRequiredViewAsType(view, 2131820975, "field 'priceView'", TextView.class);
        this.view2131820982 = view = Utils.findRequiredView(view, 2131820982, "method 'onChangeClick'");
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                shippingRatesView.onChangeClick();
            }
        });
    }

    public void unbind() {
        ShippingRatesView shippingRatesView = this.target;
        if (shippingRatesView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        shippingRatesView.shippingLineView = null;
        shippingRatesView.priceView = null;
        this.view2131820982.setOnClickListener(null);
        this.view2131820982 = null;
    }

}

