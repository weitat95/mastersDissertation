/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view;

import com.getqardio.android.shopify.domain.model.UserMessageError;
import com.getqardio.android.shopify.util.RequestRegister;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.ProgressLiveData;
import com.getqardio.android.shopify.view.UserErrorCallback;
import com.getqardio.android.shopify.view.ViewModel;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel
extends android.arch.lifecycle.ViewModel
implements ViewModel {
    private final UserErrorCallback errorCallback;
    private final ProgressLiveData progressLiveData = new ProgressLiveData();
    private final RequestRegister<Integer> requestRegister;

    public BaseViewModel() {
        this.errorCallback = new UserErrorCallback();
        this.requestRegister = new RequestRegister();
    }

    @Override
    public void cancelAllRequests() {
        this.requestRegister.deleteAll();
    }

    @Override
    public void cancelRequest(int n) {
        this.requestRegister.delete(n);
    }

    @Override
    public UserErrorCallback errorErrorCallback() {
        return this.errorCallback;
    }

    protected void hideProgress(int n) {
        this.progressLiveData.hide(n);
    }

    protected void notifyUserError(int n, Throwable throwable) {
        this.errorCallback.notify(n, throwable, null);
    }

    protected void notifyUserError(int n, Throwable throwable, String string2) {
        if (string2 == null && throwable instanceof UserMessageError) {
            this.errorCallback.notify(n, throwable, throwable.getMessage());
            return;
        }
        this.errorCallback.notify(n, throwable, string2);
    }

    @Override
    protected void onCleared() {
        this.requestRegister.dispose();
    }

    @Override
    public ProgressLiveData progressLiveData() {
        return this.progressLiveData;
    }

    protected void registerRequest(int n, Disposable disposable) {
        this.cancelRequest(n);
        this.requestRegister.add(n, Util.checkNotNull(disposable, "disposable == null"));
    }

    protected void showProgress(int n) {
        this.progressLiveData.show(n);
    }
}

