/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.getqardio.android.shopify.view.products;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import com.getqardio.android.shopify.view.ProgressLiveData;
import com.getqardio.android.shopify.view.ScreenRouter;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter;
import com.getqardio.android.shopify.view.products.ProductClickActionEvent;
import com.getqardio.android.shopify.view.products.ProductListView$$Lambda$1;
import com.getqardio.android.shopify.view.products.ProductListView$$Lambda$2;
import com.getqardio.android.shopify.view.products.ProductListView$$Lambda$3;
import com.getqardio.android.shopify.view.products.ProductListView$$Lambda$4;
import com.shopify.buy3.GraphNetworkError;
import java.util.List;

public final class ProductListView
extends SwipeRefreshLayout
implements LifecycleOwner,
RecyclerViewAdapter.OnItemClickListener {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @BindView
    RecyclerView listView;
    private final RecyclerViewAdapter listViewAdapter = new RecyclerViewAdapter(this);
    private BasePaginatedListViewModel<Product> viewModel;

    public ProductListView(Context context) {
        super(context);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public ProductListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    static /* synthetic */ void access$lambda$0(ProductListView productListView, List list) {
        productListView.swapItems(list);
    }

    private String nextPageCursor() {
        for (int i = this.listViewAdapter.getItemCount(); i >= 0; --i) {
            ListItemViewModel listItemViewModel = this.listViewAdapter.itemAt(i);
            if (listItemViewModel == null) continue;
            return ((Product)listItemViewModel.payload()).cursor;
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean shouldRequestNextPage(int n) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager)this.listView.getLayoutManager();
        if (n == 0) {
            if (gridLayoutManager.findLastVisibleItemPosition() > this.listViewAdapter.getItemCount() - 2) return true;
            return false;
        }
        if (gridLayoutManager.findLastVisibleItemPosition() < this.listViewAdapter.getItemCount() - 2) return false;
        return true;
    }

    private void showDefaultErrorMessage() {
        Snackbar snackbar = Snackbar.make((View)this, 2131362490, 0);
        snackbar.getView().setBackgroundResource(2131689551);
        snackbar.show();
    }

    private void showNetworkErrorMessage() {
        Snackbar snackbar = Snackbar.make((View)this, 2131362518, 0);
        snackbar.getView().setBackgroundResource(2131689551);
        snackbar.show();
    }

    private void swapItems(List<ListItemViewModel> list) {
        this.listViewAdapter.swapItemsAndNotify(list);
    }

    public void bindViewModel(BasePaginatedListViewModel<Product> basePaginatedListViewModel) {
        this.viewModel = Util.checkNotNull(basePaginatedListViewModel, "viewModel == null");
        basePaginatedListViewModel.reset();
        basePaginatedListViewModel.progressLiveData().observe(this, ProductListView$$Lambda$1.lambdaFactory$(this));
        basePaginatedListViewModel.errorErrorCallback().observe(this, ProductListView$$Lambda$2.lambdaFactory$(this));
        basePaginatedListViewModel.listItemsLiveData().observe(this, ProductListView$$Lambda$3.lambdaFactory$(this));
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    /* synthetic */ void lambda$bindViewModel$0(ProgressLiveData.Progress progress) {
        if (progress != null) {
            this.setRefreshing(progress.show);
        }
    }

    /* synthetic */ void lambda$bindViewModel$1(UserErrorCallback.Error error) {
        block3: {
            block2: {
                if (error == null) break block2;
                if (!(error.t instanceof GraphNetworkError)) break block3;
                this.showNetworkErrorMessage();
            }
            return;
        }
        this.showDefaultErrorMessage();
    }

    /* synthetic */ void lambda$onFinishInflate$2() {
        if (this.viewModel != null) {
            this.viewModel.reset();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    protected void onDetachedFromWindow() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        super.onDetachedFromWindow();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View)this);
        this.listView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        this.listView.setHasFixedSize(true);
        this.listView.setAdapter(this.listViewAdapter);
        this.listView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int n) {
                if (ProductListView.this.viewModel != null && ProductListView.this.shouldRequestNextPage(n)) {
                    ProductListView.this.viewModel.nextPage(ProductListView.this.nextPageCursor());
                }
            }
        });
        this.setOnRefreshListener(ProductListView$$Lambda$4.lambdaFactory$(this));
        final int n = this.getResources().getDimensionPixelOffset(2131427465);
        this.listView.addItemDecoration(new RecyclerView.ItemDecoration(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State object) {
                object = (GridLayoutManager)recyclerView.getLayoutManager();
                int n3 = recyclerView.getChildAdapterPosition(view);
                if (n3 == -1) {
                    return;
                }
                int n2 = n3 % 2 == 0 ? n / 2 : n / 4;
                rect.left = n2;
                n2 = n3 % 2 == 0 ? n / 4 : n / 2;
                rect.right = n2;
                if (n3 / ((GridLayoutManager)object).getSpanCount() > 0) {
                    rect.top = n / 4;
                }
                rect.bottom = n / 4;
            }
        });
    }

    @Override
    public void onItemClick(ListItemViewModel listItemViewModel) {
        if (listItemViewModel.payload() instanceof Product) {
            ScreenRouter.route(this.getContext(), new ProductClickActionEvent((Product)listItemViewModel.payload()));
        }
    }

}

