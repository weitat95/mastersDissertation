/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.checkout;

import android.content.Context;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.util.Util;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public final class TotalSummaryView
extends ConstraintLayout {
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(ShopifyKeyManager.getInstance().getLocale());
    @BindView
    TextView shippingView;
    @BindView
    TextView subtotalView;
    @BindView
    TextView taxView;
    @BindView
    TextView totalView;

    public TotalSummaryView(Context context) {
        super(context);
    }

    public TotalSummaryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TotalSummaryView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View)this);
    }

    public void render(BigDecimal bigDecimal, BigDecimal bigDecimal2, BigDecimal bigDecimal3, BigDecimal bigDecimal4) {
        this.subtotalView.setText((CharSequence)CURRENCY_FORMAT.format(Util.checkNotNull(bigDecimal, "subtotal == null")));
        this.shippingView.setText((CharSequence)CURRENCY_FORMAT.format(Util.checkNotNull(bigDecimal2, "shipping == null")));
        this.taxView.setText((CharSequence)CURRENCY_FORMAT.format(Util.checkNotNull(bigDecimal3, "tax == null")));
        this.totalView.setText((CharSequence)this.getResources().getString(2131362483, new Object[]{CURRENCY_FORMAT.format(Util.checkNotNull(bigDecimal4, "total == null"))}));
    }
}

