/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.Html
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.product;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.model.ProductDetails;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.product.ProductDescriptionView$$Lambda$1;
import com.getqardio.android.shopify.view.product.ProductDescriptionView$$Lambda$2;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public final class ProductDescriptionView
extends NestedScrollView {
    static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(ShopifyKeyManager.getInstance().getLocale());
    @BindView
    TextView compareAtPrice;
    @BindView
    TextView descriptionView;
    private OnAddToCartClickListener onAddToCartClickListener;
    @BindView
    TextView priceView;
    @BindView
    TextView titleView;

    public ProductDescriptionView(Context context) {
        super(context);
    }

    public ProductDescriptionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ProductDescriptionView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    private String formatMinPrice(ProductDetails object) {
        object = Util.minItem(Util.mapItems(((ProductDetails)object).variants, ProductDescriptionView$$Lambda$1.lambdaFactory$()), BigDecimal.ZERO, ProductDescriptionView$$Lambda$2.lambdaFactory$());
        return CURRENCY_FORMAT.format(object);
    }

    static /* synthetic */ BigDecimal lambda$formatMinPrice$0(ProductDetails.Variant variant) {
        return variant.price;
    }

    @OnClick
    void onAddToCartClick() {
        if (this.onAddToCartClickListener != null) {
            this.onAddToCartClickListener.onAddToCartClick();
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View)this);
    }

    public void renderProduct(ProductDetails productDetails) {
        this.titleView.setText((CharSequence)productDetails.title);
        this.priceView.setText((CharSequence)this.formatMinPrice(productDetails));
        this.descriptionView.setText((CharSequence)Html.fromHtml((String)productDetails.description));
        if (productDetails.variants != null && !productDetails.variants.isEmpty() && productDetails.variants.get((int)0).compareAtPrice != null) {
            this.compareAtPrice.setText((CharSequence)CURRENCY_FORMAT.format(productDetails.variants.get((int)0).compareAtPrice));
            this.compareAtPrice.setPaintFlags(this.compareAtPrice.getPaintFlags() | 0x10);
            this.compareAtPrice.setVisibility(0);
            return;
        }
        this.compareAtPrice.setVisibility(8);
    }

    public void renderProduct(String string2, double d) {
        this.titleView.setText((CharSequence)string2);
        this.priceView.setText((CharSequence)CURRENCY_FORMAT.format(d));
    }

    public void setOnAddToCartClickListener(OnAddToCartClickListener onAddToCartClickListener) {
        this.onAddToCartClickListener = onAddToCartClickListener;
    }

    public static interface OnAddToCartClickListener {
        public void onAddToCartClick();
    }

}

