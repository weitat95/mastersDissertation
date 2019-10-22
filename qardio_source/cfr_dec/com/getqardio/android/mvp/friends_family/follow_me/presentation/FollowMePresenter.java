/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import android.os.Bundle;
import android.text.TextUtils;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.mvp.common.ui.MultiChoiceMode;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.mvp.friends_family.common.FFTypes;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeFragmentContract;
import com.getqardio.android.mvp.friends_family.follow_me.model.FollowMeUserRepository;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter$$Lambda$1;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter$$Lambda$2;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter$$Lambda$3;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter$$Lambda$4;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter$$Lambda$5;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter$$Lambda$6;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter$$Lambda$7;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter$$Lambda$8;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeUiModelItem;
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
import java.util.Iterator;
import java.util.List;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import timber.log.Timber;

public final class FollowMePresenter {
    private final MultiChoiceMode multiChoiceMode;
    private final FollowMeUserRepository repository;
    private final CompositeDisposable subscriptions = new CompositeDisposable();
    private List<FollowMeUiModelItem> users = new ArrayList<FollowMeUiModelItem>();
    private final FollowMeFragmentContract.View view;

    FollowMePresenter(FollowMeUserRepository followMeUserRepository, FollowMeFragmentContract.View view, MultiChoiceMode multiChoiceMode) {
        this.repository = followMeUserRepository;
        this.view = view;
        this.multiChoiceMode = multiChoiceMode;
    }

    private void addSubscription(Disposable disposable) {
        this.subscriptions.add(disposable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private List<FollowMeUiModelItem> buildListPresentation(List<FollowMeUser> list) {
        ArrayList<FollowMeUiModelItem> arrayList = new ArrayList<FollowMeUiModelItem>();
        int n = 0;
        while (n < list.size()) {
            if (n == 0) {
                if (list.get(0).getAccessStatus() == FFTypes.Status.PENDING) {
                    arrayList.add(new FollowMeUiModelItem(FollowMeUiModelItem.Type.HEADER_PENDING, list.get(n)));
                } else {
                    arrayList.add(new FollowMeUiModelItem(FollowMeUiModelItem.Type.HEADER_APPROVED, list.get(n)));
                }
            } else if (list.get(n - 1).getAccessStatus() != list.get(n).getAccessStatus()) {
                arrayList.add(new FollowMeUiModelItem(FollowMeUiModelItem.Type.HEADER_PENDING, list.get(n)));
            }
            arrayList.add(new FollowMeUiModelItem(FollowMeUiModelItem.Type.ITEM, list.get(n)));
            ++n;
        }
        return arrayList;
    }

    private int countUsersIgnoringHeaders() {
        int n = 0;
        Iterator<FollowMeUiModelItem> iterator = this.users.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getType() != FollowMeUiModelItem.Type.ITEM) continue;
            ++n;
        }
        return n;
    }

    private Long getUserId() {
        return CustomApplication.getApplication().getCurrentUserId();
    }

    static /* synthetic */ void lambda$deleteSelectedUsers$6(FollowMeUser followMeUser) throws Exception {
        Timber.d("user deleted", new Object[0]);
    }

    static /* synthetic */ int lambda$sortUsers$3(FollowMeUser followMeUser, FollowMeUser followMeUser2) {
        if (followMeUser.getAccessStatus() == followMeUser2.getAccessStatus()) {
            return followMeUser.getWatcherEmail().compareTo(followMeUser2.getWatcherEmail());
        }
        if (followMeUser.getAccessStatus() == FFTypes.Status.APPROVED) {
            return -1;
        }
        return 1;
    }

    private void sortUsers(List<FollowMeUser> list) {
        Collections.sort(list, FollowMePresenter$$Lambda$4.lambdaFactory$());
    }

    public void cabDestroyed() {
        this.multiChoiceMode.clearChecks();
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

    public void deleteSelectedUsers() {
        Long l = this.getUserId();
        if (l == null) {
            return;
        }
        this.view.showProgress();
        ArrayList<FollowMeUiModelItem> arrayList = new ArrayList<FollowMeUiModelItem>();
        for (int i = 0; i < this.users.size(); ++i) {
            if (!this.isItemChecked(i)) continue;
            arrayList.add(this.users.get(i));
        }
        this.users.removeAll(arrayList);
        this.addSubscription(Observable.fromIterable(arrayList).flatMap(FollowMePresenter$$Lambda$5.lambdaFactory$(this, l)).doOnTerminate(FollowMePresenter$$Lambda$6.lambdaFactory$(this)).subscribe(FollowMePresenter$$Lambda$7.lambdaFactory$(), FollowMePresenter$$Lambda$8.lambdaFactory$()));
        this.view.stopContextualActionBarMode();
        this.multiChoiceMode.clearChecks();
    }

    public void fetchData(boolean bl) {
        Long l = this.getUserId();
        if (l == null) {
            return;
        }
        this.view.showProgress();
        this.addSubscription(this.repository.getFollowMeUsers(l).switchIfEmpty(FollowMePresenter$$Lambda$1.lambdaFactory$(this)).subscribe(FollowMePresenter$$Lambda$2.lambdaFactory$(this), FollowMePresenter$$Lambda$3.lambdaFactory$(this, bl)));
    }

    public int getAmountOfSelectedItems() {
        return this.multiChoiceMode.getCheckedCount();
    }

    public boolean isItemChecked(int n) {
        return this.multiChoiceMode.isChecked(n);
    }

    /* synthetic */ ObservableSource lambda$deleteSelectedUsers$4(Long l, FollowMeUiModelItem followMeUiModelItem) throws Exception {
        return this.repository.deleteFollowMeUser(l, followMeUiModelItem.getFollowMeUser()).toObservable();
    }

    /* synthetic */ void lambda$deleteSelectedUsers$5() throws Exception {
        this.fetchData(false);
    }

    /* synthetic */ void lambda$fetchData$0(Subscriber subscriber) {
        this.view.showEmpty();
        this.users = new ArrayList<FollowMeUiModelItem>();
        subscriber.onComplete();
    }

    /* synthetic */ void lambda$fetchData$1(List list) throws Exception {
        Timber.d("users - %s", TextUtils.join((CharSequence)", ", (Iterable)list));
        this.sortUsers((List<FollowMeUser>)list);
        list = this.buildListPresentation((List<FollowMeUser>)list);
        this.users = list;
        this.view.showData(list);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$fetchData$2(boolean bl, Throwable throwable) throws Exception {
        Timber.e(throwable, "users - %d", this.users.size());
        if (this.countUsersIgnoringHeaders() == 0) {
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

    public void onRestoreInstanceState(Bundle bundle) {
        this.multiChoiceMode.onRestoreInstanceState(bundle);
    }

    public void onSaveInstanceState(Bundle bundle) {
        this.multiChoiceMode.onSaveInstanceState(bundle);
    }

    public void subscribe() {
    }

    public void unsubscribe() {
        RxUtil.unsubscribe(this.subscriptions);
        this.view.stopContextualActionBarMode();
        this.multiChoiceMode.clearChecks();
    }
}

