/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import butterknife.BindView;
import butterknife.OnClick;
import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;

final class CollectionImageListItemViewModel
extends ListItemViewModel<Collection> {
    CollectionImageListItemViewModel(Collection collection) {
        super(collection, 2130968666);
    }

    @Override
    public ListItemViewHolder<Collection, ListItemViewModel<Collection>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
        return new ItemViewHolder(onClickListener);
    }

    @Override
    public boolean equalsByContent(ListItemViewModel object) {
        if (object instanceof CollectionImageListItemViewModel) {
            object = (Collection)((CollectionImageListItemViewModel)object).payload();
            return ((Collection)this.payload()).equals(object);
        }
        return false;
    }

    @Override
    public boolean equalsById(ListItemViewModel object) {
        if (object instanceof CollectionImageListItemViewModel) {
            object = (Collection)((CollectionImageListItemViewModel)object).payload();
            return ((Collection)this.payload()).equalsById((Collection)object);
        }
        return false;
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<Collection, ListItemViewModel<Collection>> {
        @BindView
        ShopifyDraweeView imageView;

        ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
        }

        @Override
        public void bindModel(ListItemViewModel<Collection> listItemViewModel, int n) {
            super.bindModel(listItemViewModel, n);
            this.imageView.loadShopifyImage(listItemViewModel.payload().image);
        }

        @OnClick
        void onImageClick() {
            this.onClickListener().onClick(this.itemModel());
        }
    }

}

