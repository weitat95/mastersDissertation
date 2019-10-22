/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.cart;

import com.getqardio.android.shopify.domain.interactor.CartAddItemInteractor;
import com.getqardio.android.shopify.domain.interactor.CartFetchInteractor;
import com.getqardio.android.shopify.domain.interactor.CartRemoveItemInteractor;
import com.getqardio.android.shopify.domain.interactor.CartWatchInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCartAddItemInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCartFetchInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCartRemoveItemInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCartWatchInteractor;
import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.cart.CartListItemViewModel;
import com.getqardio.android.shopify.view.cart.CartListViewModel$$Lambda$1;
import com.getqardio.android.shopify.view.cart.CartListViewModel$$Lambda$2;
import com.getqardio.android.shopify.view.cart.CartListViewModel$$Lambda$3;
import com.getqardio.android.shopify.view.cart.CartSubtotalListItemViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartListViewModel
extends BasePaginatedListViewModel<CartItem>
implements CartListItemViewModel.OnChangeQuantityClickListener {
    private final CartAddItemInteractor cartAddItemInteractor;
    private final CartFetchInteractor cartFetchInteractor;
    private final CartRemoveItemInteractor cartRemoveItemInteractor;
    private final CartWatchInteractor cartWatchInteractor = new RealCartWatchInteractor();

    public CartListViewModel() {
        this.cartAddItemInteractor = new RealCartAddItemInteractor();
        this.cartRemoveItemInteractor = new RealCartRemoveItemInteractor();
        this.cartFetchInteractor = new RealCartFetchInteractor();
    }

    @Override
    protected List<ListItemViewModel> convertAndMerge(List<CartItem> list, List<ListItemViewModel> list2) {
        list2 = new ArrayList<ListItemViewModel>();
        Iterator<CartItem> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(new CartListItemViewModel(iterator.next(), this));
        }
        if (!list.isEmpty()) {
            list2.add(new CartSubtotalListItemViewModel(this.cartFetchInteractor.execute()));
        }
        return list2;
    }

    /* synthetic */ ObservableSource lambda$nextPageRequestComposer$1(Observable observable) {
        return observable.flatMap(CartListViewModel$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ ObservableSource lambda$null$0(String string2) throws Exception {
        return this.cartWatchInteractor.execute().map(CartListViewModel$$Lambda$3.lambdaFactory$());
    }

    @Override
    protected ObservableTransformer<String, List<CartItem>> nextPageRequestComposer() {
        return CartListViewModel$$Lambda$1.lambdaFactory$(this);
    }

    @Override
    public void onAddCartItemClick(CartItem cartItem) {
        this.cartAddItemInteractor.execute(cartItem);
    }

    @Override
    public void onRemoveCartItemClick(CartItem cartItem) {
        this.cartRemoveItemInteractor.execute(cartItem);
    }
}

