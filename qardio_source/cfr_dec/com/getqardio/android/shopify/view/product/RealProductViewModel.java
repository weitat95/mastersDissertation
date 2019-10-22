/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.product;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.getqardio.android.shopify.domain.interactor.CartAddItemInteractor;
import com.getqardio.android.shopify.domain.interactor.ProductByIdInteractor;
import com.getqardio.android.shopify.domain.interactor.RealCartAddItemInteractor;
import com.getqardio.android.shopify.domain.interactor.RealProductByIdInteractor;
import com.getqardio.android.shopify.domain.model.CartItem;
import com.getqardio.android.shopify.domain.model.ProductDetails;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.BaseViewModel;
import com.getqardio.android.shopify.view.product.ProductViewModel;
import com.getqardio.android.shopify.view.product.RealProductViewModel$$Lambda$1;
import com.getqardio.android.shopify.view.product.RealProductViewModel$$Lambda$2;
import com.getqardio.android.shopify.view.product.RealProductViewModel$$Lambda$3;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import java.math.BigDecimal;
import java.util.List;

public final class RealProductViewModel
extends BaseViewModel
implements ProductViewModel {
    private final CartAddItemInteractor cartAddItemInteractor;
    private final ProductByIdInteractor productByIdInteractor = new RealProductByIdInteractor();
    private final String productId;
    private final MutableLiveData<ProductDetails> productLiveData;

    public RealProductViewModel(String string2) {
        this.cartAddItemInteractor = new RealCartAddItemInteractor();
        this.productLiveData = new MutableLiveData();
        this.productId = Util.checkNotNull(string2, "productId == null");
        this.refetch();
    }

    static /* synthetic */ void access$lambda$0(RealProductViewModel realProductViewModel, ProductDetails productDetails) {
        realProductViewModel.onProductDetailsResponse(productDetails);
    }

    static /* synthetic */ void access$lambda$1(RealProductViewModel realProductViewModel, Throwable throwable) {
        realProductViewModel.onProductDetailsError(throwable);
    }

    static /* synthetic */ CartItem.Option lambda$addToCart$0(ProductDetails.SelectedOption selectedOption) {
        return new CartItem.Option(selectedOption.name, selectedOption.value);
    }

    private void onProductDetailsError(Throwable throwable) {
        this.hideProgress(REQUEST_ID_PRODUCT_DETAILS);
        this.notifyUserError(REQUEST_ID_PRODUCT_DETAILS, throwable);
    }

    private void onProductDetailsResponse(ProductDetails productDetails) {
        this.hideProgress(REQUEST_ID_PRODUCT_DETAILS);
        this.productLiveData.setValue(productDetails);
    }

    @Override
    public void addToCart() {
        Object object = (ProductDetails)this.productLiveData.getValue();
        if (object != null) {
            ProductDetails.Variant variant = Util.checkNotNull(Util.firstItem(((ProductDetails)object).variants), "can't find default variant");
            object = new CartItem(((ProductDetails)object).id, variant.id, ((ProductDetails)object).title, variant.title, variant.price, Util.mapItems(variant.selectedOptions, RealProductViewModel$$Lambda$3.lambdaFactory$()), Util.firstItem(((ProductDetails)object).images));
            this.cartAddItemInteractor.execute((CartItem)object);
        }
    }

    @Override
    public LiveData<ProductDetails> productLiveData() {
        return this.productLiveData;
    }

    @Override
    public void refetch() {
        this.showProgress(REQUEST_ID_PRODUCT_DETAILS);
        this.registerRequest(REQUEST_ID_PRODUCT_DETAILS, this.productByIdInteractor.execute(this.productId).toObservable().observeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakObserver.forTarget(this).delegateOnNext(RealProductViewModel$$Lambda$1.lambdaFactory$()).delegateOnError(RealProductViewModel$$Lambda$2.lambdaFactory$()).create()));
    }
}

