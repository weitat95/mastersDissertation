/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.widget.FrameLayout
 */
package com.getqardio.android.shopify.view.collections;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.view.ScreenRouter;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter;
import com.getqardio.android.shopify.view.collections.CollectionProductClickActionEvent;
import com.getqardio.android.shopify.view.collections.ProductListItemViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ProductListView
extends FrameLayout
implements RecyclerViewAdapter.OnItemClickListener {
    @BindView
    RecyclerView listView;
    private final RecyclerViewAdapter listViewAdapter = new RecyclerViewAdapter(this);

    public ProductListView(Context context) {
        super(context);
    }

    public ProductListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ProductListView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    private int prefetchItemCount() {
        return this.getResources().getDisplayMetrics().widthPixels / this.getResources().getDimensionPixelOffset(2131427622) + 1;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View)this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), 0, false);
        linearLayoutManager.setInitialPrefetchItemCount(this.prefetchItemCount());
        linearLayoutManager.setItemPrefetchEnabled(true);
        this.listView.setLayoutManager(linearLayoutManager);
        this.listView.setAdapter(this.listViewAdapter);
        final int n = this.getResources().getDimensionPixelOffset(2131427465);
        this.listView.addItemDecoration(new RecyclerView.ItemDecoration(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                int n3 = recyclerView.getChildAdapterPosition(view);
                if (n3 == -1) {
                    return;
                }
                int n2 = n3 == 0 ? n / 2 : n / 4;
                rect.left = n2;
                n2 = n3 == recyclerView.getAdapter().getItemCount() ? n / 2 : n / 4;
                rect.right = n2;
            }
        });
    }

    @Override
    public void onItemClick(ListItemViewModel listItemViewModel) {
        ScreenRouter.route(this.getContext(), new CollectionProductClickActionEvent((Product)listItemViewModel.payload()));
    }

    public void setItems(List<Product> object) {
        this.listViewAdapter.clearItems();
        ArrayList<ListItemViewModel> arrayList = new ArrayList<ListItemViewModel>();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(new ProductListItemViewModel((Product)object.next()));
        }
        this.listViewAdapter.addItems(arrayList);
    }

}

