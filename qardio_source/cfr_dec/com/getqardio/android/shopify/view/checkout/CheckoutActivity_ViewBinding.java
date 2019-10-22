/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.Window
 */
package com.getqardio.android.shopify.view.checkout;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.checkout.CheckoutActivity;
import com.getqardio.android.shopify.view.checkout.ShippingRatesView;
import com.getqardio.android.shopify.view.checkout.TotalSummaryView;

public final class CheckoutActivity_ViewBinding
implements Unbinder {
    private CheckoutActivity target;
    private View view2131820763;

    public CheckoutActivity_ViewBinding(CheckoutActivity checkoutActivity) {
        this(checkoutActivity, checkoutActivity.getWindow().getDecorView());
    }

    public CheckoutActivity_ViewBinding(final CheckoutActivity checkoutActivity, View view) {
        this.target = checkoutActivity;
        checkoutActivity.rootView = Utils.findRequiredView(view, 2131820754, "field 'rootView'");
        checkoutActivity.toolbarView = Utils.findRequiredViewAsType(view, 2131820755, "field 'toolbarView'", Toolbar.class);
        checkoutActivity.totalSummaryView = Utils.findRequiredViewAsType(view, 2131820759, "field 'totalSummaryView'", TotalSummaryView.class);
        checkoutActivity.shippingRatesView = Utils.findRequiredViewAsType(view, 2131820761, "field 'shippingRatesView'", ShippingRatesView.class);
        checkoutActivity.confirmLayoutView = Utils.findRequiredView(view, 2131820762, "field 'confirmLayoutView'");
        this.view2131820763 = view = Utils.findRequiredView(view, 2131820763, "method 'onConfirmClick'");
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                checkoutActivity.onConfirmClick();
            }
        });
    }

    public void unbind() {
        CheckoutActivity checkoutActivity = this.target;
        if (checkoutActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        checkoutActivity.rootView = null;
        checkoutActivity.toolbarView = null;
        checkoutActivity.totalSummaryView = null;
        checkoutActivity.shippingRatesView = null;
        checkoutActivity.confirmLayoutView = null;
        this.view2131820763.setOnClickListener(null);
        this.view2131820763 = null;
    }

}

