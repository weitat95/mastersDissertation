/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import butterknife.BindView;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.collections.ProductListView;
import java.util.List;

final class ProductsListItemViewModel
extends ListItemViewModel<List<Product>> {
    ProductsListItemViewModel(List<Product> list) {
        super(list, 2130968668);
    }

    @Override
    public ListItemViewHolder<List<Product>, ListItemViewModel<List<Product>>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
        return new ItemViewHolder(onClickListener);
    }

    @Override
    public boolean equalsByContent(ListItemViewModel object) {
        if (object instanceof ProductsListItemViewModel) {
            List list = (List)this.payload();
            object = (List)((ProductsListItemViewModel)object).payload();
            if (list.size() == object.size()) {
                for (int i = 0; i < list.size(); ++i) {
                    if (((Product)list.get(i)).equals(object.get(i))) continue;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equalsById(ListItemViewModel object) {
        if (object instanceof ProductsListItemViewModel) {
            List list = (List)this.payload();
            object = (List)((ProductsListItemViewModel)object).payload();
            if (list.size() == object.size()) {
                for (int i = 0; i < list.size(); ++i) {
                    if (((Product)list.get(i)).equalsById((Product)object.get(i))) continue;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<List<Product>, ListItemViewModel<List<Product>>> {
        @BindView
        ProductListView productListView;

        ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
        }

        @Override
        public void bindModel(ListItemViewModel<List<Product>> listItemViewModel, int n) {
            super.bindModel(listItemViewModel, n);
            this.productListView.setItems(listItemViewModel.payload());
        }
    }

}

