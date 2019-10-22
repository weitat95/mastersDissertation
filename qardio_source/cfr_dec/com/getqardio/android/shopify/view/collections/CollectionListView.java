/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.FrameLayout
 *  android.widget.LinearLayout
 */
package com.getqardio.android.shopify.view.collections;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import com.getqardio.android.shopify.view.ProgressLiveData;
import com.getqardio.android.shopify.view.ScreenRouter;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter;
import com.getqardio.android.shopify.view.collections.CollectionClickActionEvent;
import com.getqardio.android.shopify.view.collections.CollectionListView$$Lambda$1;
import com.getqardio.android.shopify.view.collections.CollectionListView$$Lambda$2;
import com.getqardio.android.shopify.view.collections.CollectionListView$$Lambda$3;
import com.getqardio.android.shopify.view.collections.CollectionListView$$Lambda$4;
import com.shopify.buy3.GraphNetworkError;
import java.util.List;

public final class CollectionListView
extends FrameLayout
implements LifecycleOwner,
RecyclerViewAdapter.OnItemClickListener {
    private boolean isFirstLaunch = true;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @BindView
    RecyclerView listView;
    private final RecyclerViewAdapter listViewAdapter = new RecyclerViewAdapter(this);
    @BindView
    LinearLayout loadingShopify;
    @BindView
    SwipeRefreshLayout swipeRefreshLayoutView;
    private BasePaginatedListViewModel<Collection> viewModel;

    public CollectionListView(Context context) {
        super(context);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public CollectionListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public CollectionListView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    static /* synthetic */ void access$lambda$0(CollectionListView collectionListView, List list) {
        collectionListView.swapItems(list);
    }

    private String nextPageCursor() {
        for (int i = this.listViewAdapter.getItemCount(); i >= 0; --i) {
            ListItemViewModel listItemViewModel = this.listViewAdapter.itemAt(i);
            if (listItemViewModel == null || !(listItemViewModel.payload() instanceof Collection)) continue;
            return ((Collection)listItemViewModel.payload()).cursor;
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean shouldRequestNextPage(int n) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager)this.listView.getLayoutManager();
        if (n == 0) {
            if (linearLayoutManager.findLastVisibleItemPosition() > this.listViewAdapter.getItemCount() - 2) return true;
            return false;
        }
        if (linearLayoutManager.findLastVisibleItemPosition() < this.listViewAdapter.getItemCount() - 2) return false;
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

    public void bindViewModel(BasePaginatedListViewModel<Collection> basePaginatedListViewModel) {
        this.viewModel = Util.checkNotNull(basePaginatedListViewModel, "viewModel == null");
        basePaginatedListViewModel.reset();
        basePaginatedListViewModel.progressLiveData().observe(this, CollectionListView$$Lambda$1.lambdaFactory$(this));
        basePaginatedListViewModel.errorErrorCallback().observe(this, CollectionListView$$Lambda$2.lambdaFactory$(this));
        basePaginatedListViewModel.listItemsLiveData().observe(this, CollectionListView$$Lambda$3.lambdaFactory$(this));
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$bindViewModel$0(ProgressLiveData.Progress progress) {
        if (progress != null) {
            SwipeRefreshLayout swipeRefreshLayout = this.swipeRefreshLayoutView;
            boolean bl = progress.show && !this.isFirstLaunch;
            swipeRefreshLayout.setRefreshing(bl);
            swipeRefreshLayout = this.loadingShopify;
            int n = progress.show && this.isFirstLaunch ? 0 : 8;
            swipeRefreshLayout.setVisibility(n);
            this.isFirstLaunch = false;
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

    protected void onDetachedFromWindow() {
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        super.onDetachedFromWindow();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View)this);
        this.listView.setLayoutManager(new LinearLayoutManager(this.getContext(), 1, false));
        this.listView.setHasFixedSize(true);
        this.listView.setAdapter(this.listViewAdapter);
        this.listView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int n) {
                if (CollectionListView.this.viewModel != null && CollectionListView.this.shouldRequestNextPage(n)) {
                    CollectionListView.this.viewModel.nextPage(CollectionListView.this.nextPageCursor());
                }
            }
        });
        this.swipeRefreshLayoutView.setOnRefreshListener(CollectionListView$$Lambda$4.lambdaFactory$(this));
    }

    @Override
    public void onItemClick(ListItemViewModel listItemViewModel) {
        if (listItemViewModel.payload() instanceof Collection) {
            ScreenRouter.route(this.getContext(), new CollectionClickActionEvent((Collection)listItemViewModel.payload()));
        }
    }

}

