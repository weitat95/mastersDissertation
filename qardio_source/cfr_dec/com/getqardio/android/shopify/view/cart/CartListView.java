/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.FrameLayout
 */
package com.getqardio.android.shopify.view.cart;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter;
import com.getqardio.android.shopify.view.cart.CartListView$$Lambda$1;
import com.getqardio.android.shopify.view.cart.CartListView$$Lambda$2;
import java.util.List;

public final class CartListView
extends FrameLayout
implements LifecycleOwner {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @BindView
    RecyclerView listView;
    private final RecyclerViewAdapter listViewAdapter = new RecyclerViewAdapter();

    public CartListView(Context context) {
        super(context);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    public CartListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    static /* synthetic */ void access$lambda$0(CartListView cartListView, List list) {
        cartListView.swapItems(list);
    }

    private void showDefaultErrorMessage() {
        Snackbar snackbar = Snackbar.make((View)this, 2131362490, 0);
        snackbar.getView().setBackgroundResource(2131689599);
        snackbar.show();
    }

    private void swapItems(List<ListItemViewModel> list) {
        this.listViewAdapter.swapItemsAndNotify(list);
    }

    public void bindViewModel(BasePaginatedListViewModel<CartItem> basePaginatedListViewModel) {
        Util.checkNotNull(basePaginatedListViewModel, "viewModel == null");
        basePaginatedListViewModel.reset();
        basePaginatedListViewModel.errorErrorCallback().observe(this, CartListView$$Lambda$1.lambdaFactory$(this));
        basePaginatedListViewModel.listItemsLiveData().observe(this, CartListView$$Lambda$2.lambdaFactory$(this));
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    /* synthetic */ void lambda$bindViewModel$0(UserErrorCallback.Error error) {
        if (error != null) {
            this.showDefaultErrorMessage();
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
        this.listView.setHasFixedSize(true);
        this.listView.setAdapter(this.listViewAdapter);
        ((SimpleItemAnimator)this.listView.getItemAnimator()).setSupportsChangeAnimations(false);
    }
}

