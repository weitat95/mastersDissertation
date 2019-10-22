/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.text.SpannableStringBuilder
 *  android.text.style.ForegroundColorSpan
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.cart;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

final class CartSubtotalListItemViewModel
extends ListItemViewModel<Cart> {
    CartSubtotalListItemViewModel(Cart cart) {
        super(cart, 2130968658);
    }

    @Override
    public ListItemViewHolder<Cart, ListItemViewModel<Cart>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
        return new ItemViewHolder(onClickListener);
    }

    @Override
    public boolean equalsByContent(ListItemViewModel listItemViewModel) {
        return false;
    }

    @Override
    public boolean equalsById(ListItemViewModel listItemViewModel) {
        return listItemViewModel instanceof CartSubtotalListItemViewModel;
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<Cart, ListItemViewModel<Cart>> {
        static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(ShopifyKeyManager.getInstance().getLocale());
        private int colorAccent;
        @BindView
        TextView subtotalView;

        ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
        }

        @Override
        public void bindModel(ListItemViewModel<Cart> object, int n) {
            super.bindModel(object, n);
            object = CURRENCY_FORMAT.format(((ListItemViewModel)object).payload().totalPrice());
            String string2 = this.subtotalView.getResources().getString(2131362465, new Object[]{object});
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder((CharSequence)string2);
            spannableStringBuilder.setSpan((Object)new ForegroundColorSpan(this.colorAccent), string2.length() - ((String)object).length(), string2.length(), 18);
            this.subtotalView.setText((CharSequence)spannableStringBuilder);
        }

        @Override
        protected void bindView(View view) {
            super.bindView(view);
            view = view.getContext().obtainStyledAttributes(new int[]{2130772162});
            this.colorAccent = view.getColor(0, 0);
            view.recycle();
        }
    }

}

