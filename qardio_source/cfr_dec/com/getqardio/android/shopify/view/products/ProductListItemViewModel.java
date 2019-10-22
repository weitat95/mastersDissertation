/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.products;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

final class ProductListItemViewModel
extends ListItemViewModel<Product> {
    ProductListItemViewModel(Product product) {
        super(product, 2130968778);
    }

    @Override
    public ListItemViewHolder<Product, ListItemViewModel<Product>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
        return new ItemViewHolder(onClickListener);
    }

    @Override
    public boolean equalsByContent(ListItemViewModel object) {
        if (object instanceof ProductListItemViewModel) {
            object = (Product)((ProductListItemViewModel)object).payload();
            return ((Product)this.payload()).equals(object);
        }
        return false;
    }

    @Override
    public boolean equalsById(ListItemViewModel object) {
        if (object instanceof ProductListItemViewModel) {
            object = (Product)((ProductListItemViewModel)object).payload();
            return ((Product)this.payload()).equalsById((Product)object);
        }
        return false;
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<Product, ListItemViewModel<Product>> {
        static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(ShopifyKeyManager.getInstance().getLocale());
        @BindView
        TextView compareAtPrice;
        @BindView
        ShopifyDraweeView imageView;
        @BindView
        TextView priceView;
        @BindView
        TextView titleView;

        ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
        }

        @Override
        public void bindModel(ListItemViewModel<Product> listItemViewModel, int n) {
            super.bindModel(listItemViewModel, n);
            this.imageView.loadShopifyImage(listItemViewModel.payload().image);
            this.titleView.setText((CharSequence)listItemViewModel.payload().title);
            this.priceView.setText((CharSequence)CURRENCY_FORMAT.format(listItemViewModel.payload().price));
            if (listItemViewModel.payload().compareAtPrice != null) {
                this.compareAtPrice.setText((CharSequence)CURRENCY_FORMAT.format(listItemViewModel.payload().compareAtPrice));
                this.compareAtPrice.setPaintFlags(this.compareAtPrice.getPaintFlags() | 0x10);
                this.compareAtPrice.setVisibility(0);
                return;
            }
            this.compareAtPrice.setVisibility(8);
        }

        @OnClick
        void onClick() {
            this.onClickListener().onClick(this.itemModel());
        }
    }

}

