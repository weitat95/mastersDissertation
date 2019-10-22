/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.util.WeakConsumer;
import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel$$Lambda$1;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel$$Lambda$2;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel$$Lambda$3;
import com.getqardio.android.shopify.view.BaseViewModel;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import timber.log.Timber;

public abstract class BasePaginatedListViewModel<ITEM>
extends BaseViewModel {
    private static final int REQUEST_ID_FETCH_NEXT_PAGE = UUID.randomUUID().hashCode();
    private final PublishSubject<String> fetchNextPageSubject = PublishSubject.create();
    protected final MutableLiveData<List<ListItemViewModel>> listItemsLiveData = new MutableLiveData();
    private boolean reset;

    public BasePaginatedListViewModel() {
        this.listItemsLiveData.setValue(Collections.emptyList());
    }

    static /* synthetic */ void lambda$reset$0(BasePaginatedListViewModel basePaginatedListViewModel, String string2) {
        basePaginatedListViewModel.showProgress(REQUEST_ID_FETCH_NEXT_PAGE);
    }

    protected abstract List<ListItemViewModel> convertAndMerge(List<ITEM> var1, List<ListItemViewModel> var2);

    public LiveData<List<ListItemViewModel>> listItemsLiveData() {
        return this.listItemsLiveData;
    }

    public void nextPage(String string2) {
        this.fetchNextPageSubject.onNext(Util.checkNotNull(string2, "cursor == null"));
    }

    protected abstract ObservableTransformer<String, List<ITEM>> nextPageRequestComposer();

    protected void onNextPageError(Throwable throwable) {
        Timber.e(throwable);
        this.hideProgress(REQUEST_ID_FETCH_NEXT_PAGE);
        this.notifyUserError(REQUEST_ID_FETCH_NEXT_PAGE, throwable);
    }

    void onNextPageResponseInternal(List<ITEM> list) {
        this.hideProgress(REQUEST_ID_FETCH_NEXT_PAGE);
        if (this.reset) {
            this.reset = false;
            this.listItemsLiveData.setValue(Collections.<T>emptyList());
        }
        this.listItemsLiveData.setValue(this.convertAndMerge(list, (List)this.listItemsLiveData.getValue()));
    }

    public void reset() {
        this.reset = true;
        this.registerRequest(REQUEST_ID_FETCH_NEXT_PAGE, this.fetchNextPageSubject.distinct().doOnNext(WeakConsumer.forTarget(this).delegateAccept(BasePaginatedListViewModel$$Lambda$1.lambdaFactory$()).create()).compose(this.nextPageRequestComposer()).observeOn(AndroidSchedulers.mainThread()).doOnError(WeakConsumer.forTarget(this).delegateAccept(BasePaginatedListViewModel$$Lambda$2.lambdaFactory$()).create()).retry().subscribeWith(WeakObserver.forTarget(this).delegateOnNext(BasePaginatedListViewModel$$Lambda$3.lambdaFactory$()).create()));
        this.fetchNextPageSubject.onNext("");
    }
}

