/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.checkout;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.checkout.TotalSummaryView;

public final class TotalSummaryView_ViewBinding
implements Unbinder {
    private TotalSummaryView target;

    public TotalSummaryView_ViewBinding(TotalSummaryView totalSummaryView) {
        this(totalSummaryView, (View)totalSummaryView);
    }

    public TotalSummaryView_ViewBinding(TotalSummaryView totalSummaryView, View view) {
        this.target = totalSummaryView;
        totalSummaryView.subtotalView = Utils.findRequiredViewAsType(view, 2131820970, "field 'subtotalView'", TextView.class);
        totalSummaryView.shippingView = Utils.findRequiredViewAsType(view, 2131820987, "field 'shippingView'", TextView.class);
        totalSummaryView.taxView = Utils.findRequiredViewAsType(view, 2131820988, "field 'taxView'", TextView.class);
        totalSummaryView.totalView = Utils.findRequiredViewAsType(view, 2131820990, "field 'totalView'", TextView.class);
    }

    public void unbind() {
        TotalSummaryView totalSummaryView = this.target;
        if (totalSummaryView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        totalSummaryView.subtotalView = null;
        totalSummaryView.shippingView = null;
        totalSummaryView.taxView = null;
        totalSummaryView.totalView = null;
    }
}

