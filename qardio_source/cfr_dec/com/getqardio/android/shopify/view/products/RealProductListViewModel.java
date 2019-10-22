/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.products;

import com.getqardio.android.shopify.domain.interactor.CollectionProductNextPageInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCollectionProductNextPageInteractor;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.products.ProductListItemViewModel;
import com.getqardio.android.shopify.view.products.RealProductListViewModel$$Lambda$1;
import com.getqardio.android.shopify.view.products.RealProductListViewModel$$Lambda$2;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class RealProductListViewModel
extends BasePaginatedListViewModel<Product> {
    private static final int PER_PAGE = 20;
    private final String collectionId;
    private final CollectionProductNextPageInteractor collectionProductNextPageInteractor = new RealCollectionProductNextPageInteractor();

    public RealProductListViewModel(String string2) {
        this.collectionId = Util.checkNotNull(string2, "collectionId == null");
    }

    @Override
    protected List<ListItemViewModel> convertAndMerge(List<Product> arrayList, List<ListItemViewModel> list) {
        ArrayList<ProductListItemViewModel> arrayList2 = new ArrayList<ProductListItemViewModel>();
        arrayList = arrayList.iterator();
        while (arrayList.hasNext()) {
            arrayList2.add(new ProductListItemViewModel((Product)arrayList.next()));
        }
        arrayList = new ArrayList<ListItemViewModel>(list);
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    /* synthetic */ ObservableSource lambda$nextPageRequestComposer$1(Observable observable) {
        return observable.flatMapSingle(RealProductListViewModel$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ SingleSource lambda$null$0(String string2) throws Exception {
        return this.collectionProductNextPageInteractor.execute(this.collectionId, string2, 20);
    }

    @Override
    protected ObservableTransformer<String, List<Product>> nextPageRequestComposer() {
        return RealProductListViewModel$$Lambda$1.lambdaFactory$(this);
    }
}

