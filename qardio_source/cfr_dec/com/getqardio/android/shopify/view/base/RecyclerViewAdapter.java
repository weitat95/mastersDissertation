/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.getqardio.android.shopify.view.base;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter$$Lambda$1;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter$$Lambda$2;
import com.getqardio.android.shopify.view.base.RecyclerViewItemHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class RecyclerViewAdapter
extends RecyclerView.Adapter<RecyclerViewItemHolder> {
    private final RecyclerViewItemHolder.OnClickListener itemClickListener;
    private List<ListItemViewModel> items = new ArrayList<ListItemViewModel>();

    public RecyclerViewAdapter() {
        this.setHasStableIds(true);
        this.itemClickListener = RecyclerViewAdapter$$Lambda$1.lambdaFactory$();
    }

    public RecyclerViewAdapter(OnItemClickListener onItemClickListener) {
        Util.checkNotNull(onItemClickListener, "itemClickListener == null");
        onItemClickListener.getClass();
        this.itemClickListener = RecyclerViewAdapter$$Lambda$2.lambdaFactory$(onItemClickListener);
        this.setHasStableIds(true);
    }

    static /* synthetic */ void lambda$new$0(ListItemViewModel listItemViewModel) {
    }

    public void addItems(List<ListItemViewModel> list) {
        Util.checkNotNull(list, "newItems == null");
        int n = this.items.size();
        this.items.addAll(list);
        this.notifyItemRangeInserted(n, list.size());
    }

    public void clearItems() {
        int n = this.items.size();
        this.items.clear();
        this.notifyItemRangeRemoved(0, n);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public long getItemId(int n) {
        return (1L * 1000003L ^ (long)this.getItemViewType(n)) * 1000003L ^ (long)this.items.get(n).hashCode();
    }

    @Override
    public int getItemViewType(int n) {
        return this.items.get(n).viewType();
    }

    public <T> ListItemViewModel<T> itemAt(int n) {
        if (n < 0 || n >= this.items.size()) {
            return null;
        }
        return this.items.get(n);
    }

    public int itemPosition(ListItemViewModel listItemViewModel) {
        return this.items.indexOf(listItemViewModel);
    }

    public <T> ListItemViewModel<T> lastItem() {
        if (this.items.size() > 0) {
            return this.items.get(this.items.size() - 1);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder recyclerViewItemHolder, int n) {
        recyclerViewItemHolder.bindModel(this.items.get(n), n);
    }

    @Override
    public RecyclerViewItemHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new RecyclerViewItemHolder(LayoutInflater.from((Context)viewGroup.getContext()).inflate(n, viewGroup, false), this.itemClickListener);
    }

    public void swapItemsAndNotify(List<ListItemViewModel> list) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ItemsDiffCallback(this.items, list, new ItemComparator(){

            @Override
            public boolean equalsByContent(ListItemViewModel listItemViewModel, ListItemViewModel listItemViewModel2) {
                return listItemViewModel.equalsByContent(listItemViewModel2);
            }

            @Override
            public boolean equalsById(ListItemViewModel listItemViewModel, ListItemViewModel listItemViewModel2) {
                return listItemViewModel.equalsById(listItemViewModel2);
            }
        }));
        this.items = new ArrayList<ListItemViewModel>(list);
        diffResult.dispatchUpdatesTo(this);
    }

    public void swapItemsAndNotify(List<ListItemViewModel> list, ItemComparator object) {
        object = DiffUtil.calculateDiff(new ItemsDiffCallback(this.items, list, (ItemComparator)object));
        this.items = new ArrayList<ListItemViewModel>(list);
        ((DiffUtil.DiffResult)object).dispatchUpdatesTo(this);
    }

    public static interface ItemComparator {
        public boolean equalsByContent(ListItemViewModel var1, ListItemViewModel var2);

        public boolean equalsById(ListItemViewModel var1, ListItemViewModel var2);
    }

    private static final class ItemsDiffCallback
    extends DiffUtil.Callback {
        private final ItemComparator contentComparator;
        private final List<ListItemViewModel> newItems;
        private final List<ListItemViewModel> oldItems;

        ItemsDiffCallback(List<ListItemViewModel> list, List<ListItemViewModel> list2, ItemComparator itemComparator) {
            this.oldItems = list;
            this.newItems = list2;
            this.contentComparator = itemComparator;
        }

        @Override
        public boolean areContentsTheSame(int n, int n2) {
            return this.contentComparator.equalsByContent(this.oldItems.get(n), this.newItems.get(n2));
        }

        @Override
        public boolean areItemsTheSame(int n, int n2) {
            return this.contentComparator.equalsById(this.oldItems.get(n), this.newItems.get(n2));
        }

        @Override
        public int getNewListSize() {
            return this.newItems.size();
        }

        @Override
        public int getOldListSize() {
            return this.oldItems.size();
        }
    }

    public static interface OnItemClickListener {
        public void onItemClick(ListItemViewModel var1);
    }

}

