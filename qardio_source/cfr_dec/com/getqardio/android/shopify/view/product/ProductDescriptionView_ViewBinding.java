/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.product;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.shopify.view.product.ProductDescriptionView;

public final class ProductDescriptionView_ViewBinding
implements Unbinder {
    private ProductDescriptionView target;
    private View view2131820975;

    public ProductDescriptionView_ViewBinding(ProductDescriptionView productDescriptionView) {
        this(productDescriptionView, (View)productDescriptionView);
    }

    public ProductDescriptionView_ViewBinding(final ProductDescriptionView productDescriptionView, View view) {
        this.target = productDescriptionView;
        productDescriptionView.titleView = Utils.findRequiredViewAsType(view, 2131820567, "field 'titleView'", TextView.class);
        View view2 = Utils.findRequiredView(view, 2131820975, "field 'priceView' and method 'onAddToCartClick'");
        productDescriptionView.priceView = Utils.castView(view2, 2131820975, "field 'priceView'", TextView.class);
        this.view2131820975 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                productDescriptionView.onAddToCartClick();
            }
        });
        productDescriptionView.compareAtPrice = Utils.findRequiredViewAsType(view, 2131821206, "field 'compareAtPrice'", TextView.class);
        productDescriptionView.descriptionView = Utils.findRequiredViewAsType(view, 2131820998, "field 'descriptionView'", TextView.class);
    }

    public void unbind() {
        ProductDescriptionView productDescriptionView = this.target;
        if (productDescriptionView == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        productDescriptionView.titleView = null;
        productDescriptionView.priceView = null;
        productDescriptionView.compareAtPrice = null;
        productDescriptionView.descriptionView = null;
        this.view2131820975.setOnClickListener(null);
        this.view2131820975 = null;
    }

}

