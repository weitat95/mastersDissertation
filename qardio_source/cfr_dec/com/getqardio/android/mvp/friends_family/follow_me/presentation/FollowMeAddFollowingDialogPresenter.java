/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import android.text.TextUtils;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.common.remote.ApiResponseException;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.mvp.friends_family.common.FFTypes;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeAddFollowingDialogContract;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenter$$Lambda$1;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenter$$Lambda$2;
import com.getqardio.android.utils.validator.EmailValidator;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class FollowMeAddFollowingDialogPresenter {
    private final FollowMeUserRepository repository;
    private final CompositeDisposable subscriptions = new CompositeDisposable();
    private final FollowMeAddFollowingDialogContract.View view;

    FollowMeAddFollowingDialogPresenter(FollowMeUserRepository followMeUserRepository, FollowMeAddFollowingDialogContract.View view) {
        this.repository = followMeUserRepository;
        this.view = view;
    }

    private Long getUserId() {
        return CustomApplication.getApplication().getCurrentUserId();
    }

    private boolean validateEmail(String string2) {
        if (string2.length() > 80) {
            this.view.showError(2131361964);
            this.view.enableSendButton(false);
            return false;
        }
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (TextUtils.isEmpty((CharSequence)string2) || !emailValidator.isValid(string2)) {
            this.view.showError(2131362001);
            this.view.enableSendButton(false);
            return false;
        }
        this.view.hideError();
        this.view.enableSendButton(true);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void emailChanged(String string2) {
        FollowMeAddFollowingDialogContract.View view = this.view;
        boolean bl = !TextUtils.isEmpty((CharSequence)string2);
        view.enableSendButton(bl);
    }

    public void invite(String string2) {
        Long l = this.getUserId();
        if (!this.validateEmail(string2) || l == null) {
            return;
        }
        this.view.showProgress();
        FollowMeUser followMeUser = new FollowMeUser();
        followMeUser.setNotificationsEnabled(true);
        followMeUser.setAccessStatus(FFTypes.Status.PENDING);
        followMeUser.setWatcherEmail(string2.toLowerCase());
        this.repository.inviteOrSaveUserToFollowMe(l, followMeUser, null).subscribe(FollowMeAddFollowingDialogPresenter$$Lambda$1.lambdaFactory$(this), FollowMeAddFollowingDialogPresenter$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$invite$0(FollowMeUser followMeUser) throws Exception {
        this.view.dismiss();
    }

    /* synthetic */ void lambda$invite$1(Throwable throwable) throws Exception {
        Timber.e(throwable);
        if (throwable instanceof ApiResponseException) {
            this.view.showInputScreen();
            this.view.showError(2131361849);
            return;
        }
        this.view.dismiss();
    }

    public void subscribe() {
        this.view.showInputScreen();
    }

    public void unsubscribe() {
        RxUtil.unsubscribe(this.subscriptions);
    }
}

