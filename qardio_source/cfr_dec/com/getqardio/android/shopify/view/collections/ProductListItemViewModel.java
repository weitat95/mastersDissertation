/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.getqardio.android.shopify.view.collections;

import android.view.View;
import butterknife.BindView;
import butterknife.OnClick;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;

final class ProductListItemViewModel
extends ListItemViewModel<Product> {
    ProductListItemViewModel(Product product) {
        super(product, 2130968667);
    }

    @Override
    public ListItemViewHolder<Product, ListItemViewModel<Product>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
        return new ItemViewHolder(onClickListener);
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<Product, ListItemViewModel<Product>> {
        @BindView
        ShopifyDraweeView imageView;

        ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
        }

        @Override
        public void bindModel(ListItemViewModel<Product> listItemViewModel, int n) {
            super.bindModel(listItemViewModel, n);
            this.imageView.loadShopifyImage(listItemViewModel.payload().image);
        }

        @OnClick
        void onImageClick(View view) {
            this.onClickListener().onClick(this.itemModel());
        }
    }

}

