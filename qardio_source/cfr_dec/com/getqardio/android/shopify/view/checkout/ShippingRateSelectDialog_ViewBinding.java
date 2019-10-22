/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.Window
 */
package com.getqardio.android.shopify.view.checkout;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.checkout.ShippingRateSelectDialog;

public final class ShippingRateSelectDialog_ViewBinding
implements Unbinder {
    private ShippingRateSelectDialog target;

    public ShippingRateSelectDialog_ViewBinding(ShippingRateSelectDialog shippingRateSelectDialog) {
        this(shippingRateSelectDialog, shippingRateSelectDialog.getWindow().getDecorView());
    }

    public ShippingRateSelectDialog_ViewBinding(ShippingRateSelectDialog shippingRateSelectDialog, View view) {
        this.target = shippingRateSelectDialog;
        shippingRateSelectDialog.toolbarView = Utils.findRequiredViewAsType(view, 2131820755, "field 'toolbarView'", Toolbar.class);
        shippingRateSelectDialog.listView = Utils.findRequiredViewAsType(view, 2131820758, "field 'listView'", RecyclerView.class);
    }

    public void unbind() {
        ShippingRateSelectDialog shippingRateSelectDialog = this.target;
        if (shippingRateSelectDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        shippingRateSelectDialog.toolbarView = null;
        shippingRateSelectDialog.listView = null;
    }
}

