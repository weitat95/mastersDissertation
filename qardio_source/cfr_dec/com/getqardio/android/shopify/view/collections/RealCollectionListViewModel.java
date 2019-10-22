/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.collections;

import com.getqardio.android.shopify.domain.interactor.CollectionNextPageInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCollectionNextPageInteractor;
import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.domain.model.Product;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.collections.CollectionDescriptionSummaryListItemViewModel;
import com.getqardio.android.shopify.view.collections.CollectionDividerListItemViewModel;
import com.getqardio.android.shopify.view.collections.CollectionImageListItemViewModel;
import com.getqardio.android.shopify.view.collections.CollectionTitleListItemViewModel;
import com.getqardio.android.shopify.view.collections.ProductsListItemViewModel;
import com.getqardio.android.shopify.view.collections.RealCollectionListViewModel$$Lambda$1;
import com.getqardio.android.shopify.view.collections.RealCollectionListViewModel$$Lambda$2;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;

public final class RealCollectionListViewModel
extends BasePaginatedListViewModel<Collection> {
    private static final int PER_PAGE = 10;
    private final CollectionNextPageInteractor collectionNextPageInteractor = new RealCollectionNextPageInteractor();

    @Override
    protected List<ListItemViewModel> convertAndMerge(List<Collection> arrayList, List<ListItemViewModel> list) {
        ArrayList<ListItemViewModel> arrayList2 = new ArrayList<ListItemViewModel>();
        for (Collection collection : arrayList) {
            arrayList2.add(new CollectionTitleListItemViewModel(collection));
            arrayList2.add(new CollectionImageListItemViewModel(collection));
            arrayList2.add(new ProductsListItemViewModel(collection.products));
            arrayList2.add(new CollectionDescriptionSummaryListItemViewModel(collection));
            arrayList2.add(new CollectionDividerListItemViewModel(collection));
        }
        arrayList = new ArrayList<ListItemViewModel>(list);
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    /* synthetic */ ObservableSource lambda$nextPageRequestComposer$1(Observable observable) {
        return observable.flatMapSingle(RealCollectionListViewModel$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ SingleSource lambda$null$0(String string2) throws Exception {
        return this.collectionNextPageInteractor.execute(string2, 10);
    }

    @Override
    protected ObservableTransformer<String, List<Collection>> nextPageRequestComposer() {
        return RealCollectionListViewModel$$Lambda$1.lambdaFactory$(this);
    }
}

