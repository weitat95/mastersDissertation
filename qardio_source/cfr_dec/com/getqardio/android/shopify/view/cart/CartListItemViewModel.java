/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.cart;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

final class CartListItemViewModel
extends ListItemViewModel<CartItem> {
    private final OnChangeQuantityClickListener onChangeQuantityClickListener;

    CartListItemViewModel(CartItem cartItem, OnChangeQuantityClickListener onChangeQuantityClickListener) {
        super(cartItem, 2130968657);
        this.onChangeQuantityClickListener = Util.checkNotNull(onChangeQuantityClickListener, "onChangeQuantityClickListener == null");
    }

    @Override
    public ListItemViewHolder<CartItem, ListItemViewModel<CartItem>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
        return new ItemViewHolder(this.onChangeQuantityClickListener, onClickListener);
    }

    @Override
    public boolean equalsByContent(ListItemViewModel object) {
        if (object instanceof CartListItemViewModel) {
            object = (CartItem)((CartListItemViewModel)object).payload();
            return ((CartItem)this.payload()).equalsByContent((CartItem)object);
        }
        return false;
    }

    @Override
    public boolean equalsById(ListItemViewModel object) {
        if (object instanceof CartListItemViewModel) {
            object = (CartItem)((CartListItemViewModel)object).payload();
            return ((CartItem)this.payload()).equalsById((CartItem)object);
        }
        return false;
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<CartItem, ListItemViewModel<CartItem>> {
        static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(ShopifyKeyManager.getInstance().getLocale());
        @BindView
        View dividerView;
        @BindView
        ShopifyDraweeView imageView;
        final OnChangeQuantityClickListener onChangeQuantityClickListener;
        @BindView
        TextView priceView;
        @BindView
        TextView quantityView;
        @BindView
        TextView titleView;
        @BindView
        TextView variantView;

        ItemViewHolder(OnChangeQuantityClickListener onChangeQuantityClickListener, ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
            this.onChangeQuantityClickListener = Util.checkNotNull(onChangeQuantityClickListener, "onChangeQuantityClickListener == null");
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void bindModel(ListItemViewModel<CartItem> view, int n) {
            super.bindModel(view, n);
            this.imageView.loadShopifyImage(((CartItem)view.payload()).image);
            this.titleView.setText((CharSequence)((CartItem)view.payload()).productTitle);
            this.variantView.setText((CharSequence)((CartItem)view.payload()).variantTitle);
            this.quantityView.setText((CharSequence)String.valueOf(((CartItem)view.payload()).quantity));
            this.priceView.setText((CharSequence)CURRENCY_FORMAT.format((double)((CartItem)view.payload()).quantity * ((CartItem)view.payload()).price.doubleValue()));
            view = this.dividerView;
            n = n == 0 ? 4 : 0;
            view.setVisibility(n);
        }

        @OnClick
        void onDecrementQuantityClick() {
            this.onChangeQuantityClickListener.onRemoveCartItemClick((CartItem)((ListItemViewModel)this.itemModel()).payload());
        }

        @OnClick
        void onIncrementQuantityClick() {
            this.onChangeQuantityClickListener.onAddCartItemClick((CartItem)((ListItemViewModel)this.itemModel()).payload());
        }
    }

    static interface OnChangeQuantityClickListener {
        public void onAddCartItemClick(CartItem var1);

        public void onRemoveCartItemClick(CartItem var1);
    }

}

