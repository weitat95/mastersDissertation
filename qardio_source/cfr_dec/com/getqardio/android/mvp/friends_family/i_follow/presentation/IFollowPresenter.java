/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.presentation;

import com.getqardio.android.CustomApplication;
import com.getqardio.android.mvp.common.ui.MultiChoiceMode;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.mvp.friends_family.common.FFTypes;
import com.getqardio.android.mvp.friends_family.i_follow.IFollowContract;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$1;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$10;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$2;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$3;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$4;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$5;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$6;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$7;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$8;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter$$Lambda$9;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import timber.log.Timber;

public class IFollowPresenter {
    private final MultiChoiceMode multiChoiceMode;
    private final IFollowUserRepository repository;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private List<IFollowUser> users = new ArrayList<IFollowUser>();
    private final IFollowContract.View view;

    IFollowPresenter(IFollowUserRepository iFollowUserRepository, IFollowContract.View view, MultiChoiceMode multiChoiceMode) {
        this.repository = iFollowUserRepository;
        this.view = view;
        this.multiChoiceMode = multiChoiceMode;
    }

    private Long getUserId() {
        return CustomApplication.getApplication().getCurrentUserId();
    }

    static /* synthetic */ void lambda$clickOnNotification$4(IFollowUser iFollowUser) throws Exception {
        Timber.d("notification changed", new Object[0]);
    }

    static /* synthetic */ void lambda$deleteSelectedUsers$8(IFollowUser iFollowUser) throws Exception {
        Timber.d("user deleted", new Object[0]);
    }

    static /* synthetic */ int lambda$null$1(IFollowUser iFollowUser, IFollowUser iFollowUser2) {
        return iFollowUser.buildFullName().compareTo(iFollowUser2.buildFullName());
    }

    public void cabDestroyed() {
        this.multiChoiceMode.clearChecks();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void clickOnBpItem(int n) {
        if (this.getAmountOfSelectedItems() > 0) {
            this.longClickOnItem(n);
            return;
        } else {
            IFollowUser iFollowUser = this.users.get(n);
            if (iFollowUser == null || iFollowUser.getAccessStatus() != FFTypes.Status.APPROVED) return;
            {
                this.view.showBpDetails(iFollowUser.getWatchingEmail());
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void clickOnItem(int n) {
        if (this.multiChoiceMode.getCheckedCount() == 0) {
            return;
        }
        MultiChoiceMode multiChoiceMode = this.multiChoiceMode;
        boolean bl = !this.multiChoiceMode.isChecked(n);
        multiChoiceMode.setChecked(n, bl);
        if (this.multiChoiceMode.getCheckedCount() == 0) {
            this.view.stopContextualActionBarMode();
        }
        this.view.updateItemAtPosition(n);
    }

    public void clickOnNotification(int n, boolean bl) {
        Long l = this.getUserId();
        IFollowUser iFollowUser = this.users.get(n);
        this.repository.enablePushNotifications(l, iFollowUser, bl).subscribe(IFollowPresenter$$Lambda$4.lambdaFactory$(), IFollowPresenter$$Lambda$5.lambdaFactory$(this, iFollowUser, bl, n));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void clickOnWeightItem(int n) {
        if (this.getAmountOfSelectedItems() > 0) {
            this.longClickOnItem(n);
            return;
        } else {
            IFollowUser iFollowUser = this.users.get(n);
            if (iFollowUser == null || iFollowUser.getAccessStatus() != FFTypes.Status.APPROVED) return;
            {
                this.view.showWeightDetails(iFollowUser.getWatchingEmail());
                return;
            }
        }
    }

    public void deleteSelectedUsers() {
        Long l = this.getUserId();
        this.view.showProgress();
        ArrayList<IFollowUser> arrayList = new ArrayList<IFollowUser>();
        for (int i = 0; i < this.users.size(); ++i) {
            if (!this.isItemChecked(i)) continue;
            arrayList.add(this.users.get(i));
        }
        this.users.removeAll(arrayList);
        Observable.fromIterable(arrayList).flatMap(IFollowPresenter$$Lambda$6.lambdaFactory$(this, l)).doOnTerminate(IFollowPresenter$$Lambda$7.lambdaFactory$(this)).subscribe(IFollowPresenter$$Lambda$8.lambdaFactory$(), IFollowPresenter$$Lambda$9.lambdaFactory$());
        this.view.stopContextualActionBarMode();
        this.multiChoiceMode.clearChecks();
    }

    public void fetchData(boolean bl) {
        Long l = this.getUserId();
        if (l == null) {
            return;
        }
        this.view.showProgress();
        this.subscriptions.add(this.repository.getIFollowUsers(l).switchIfEmpty(IFollowPresenter$$Lambda$1.lambdaFactory$(this)).subscribe(IFollowPresenter$$Lambda$2.lambdaFactory$(this), IFollowPresenter$$Lambda$3.lambdaFactory$(this, bl)));
    }

    public int getAmountOfSelectedItems() {
        return this.multiChoiceMode.getCheckedCount();
    }

    public boolean isItemChecked(int n) {
        return this.multiChoiceMode.isChecked(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$clickOnNotification$5(IFollowUser iFollowUser, boolean bl, int n, Throwable throwable) throws Exception {
        Timber.e(throwable);
        bl = !bl;
        iFollowUser.setNotificationsEnabled(bl);
        this.view.showToastError();
        this.view.updateItemAtPosition(n);
    }

    /* synthetic */ ObservableSource lambda$deleteSelectedUsers$6(Long l, IFollowUser iFollowUser) throws Exception {
        return this.repository.deleteIFollowUser(l, iFollowUser).toObservable();
    }

    /* synthetic */ void lambda$deleteSelectedUsers$7() throws Exception {
        this.fetchData(false);
    }

    /* synthetic */ void lambda$fetchData$0(Subscriber subscriber) {
        Timber.d("empty", new Object[0]);
        this.view.showEmpty();
        this.users = new ArrayList<IFollowUser>();
        subscriber.onComplete();
    }

    /* synthetic */ void lambda$fetchData$2(List list) throws Exception {
        Timber.d("users - %d", list.size());
        Collections.sort(list, IFollowPresenter$$Lambda$10.lambdaFactory$());
        this.users = list;
        this.view.showData(list);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$fetchData$3(boolean bl, Throwable throwable) throws Exception {
        Timber.e(throwable);
        if (this.users.isEmpty()) {
            if (!bl) {
                this.view.showEmpty();
                return;
            }
            this.view.showError();
            return;
        } else {
            if (!bl) return;
            {
                this.view.showToastError();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void longClickOnItem(int n) {
        if (this.multiChoiceMode.getCheckedCount() == 0) {
            this.view.startContextualActionBarMode();
        }
        MultiChoiceMode multiChoiceMode = this.multiChoiceMode;
        boolean bl = !this.multiChoiceMode.isChecked(n);
        multiChoiceMode.setChecked(n, bl);
        if (this.multiChoiceMode.getCheckedCount() == 0) {
            this.view.stopContextualActionBarMode();
        }
        this.view.updateItemAtPosition(n);
    }

    public void subscribe() {
    }

    public void unsubscribe() {
        RxUtil.unsubscribe(this.subscriptions);
        this.view.stopContextualActionBarMode();
        this.multiChoiceMode.clearChecks();
    }
}

