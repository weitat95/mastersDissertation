/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.ActionMode
 *  android.view.ActionMode$Callback
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AbsListView
 *  android.widget.AbsListView$MultiChoiceModeListener
 *  android.widget.TextView
 *  android.widget.ViewFlipper
 */
package com.getqardio.android.mvp.friends_family.i_follow.view;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.User;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.friends_family.FnFNotificationStack;
import com.getqardio.android.mvp.friends_family.FriendsFamilyMainFragment;
import com.getqardio.android.mvp.friends_family.i_follow.DaggerIFollowComponent;
import com.getqardio.android.mvp.friends_family.i_follow.IFollowComponent;
import com.getqardio.android.mvp.friends_family.i_follow.IFollowContract;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenter;
import com.getqardio.android.mvp.friends_family.i_follow.presentation.IFollowPresenterModule;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment$$Lambda$1;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment$$Lambda$2;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment$$Lambda$3;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment$$Lambda$4;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment$$Lambda$5;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment$$Lambda$6;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment$$Lambda$7;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowListAdapter;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.activity.FriendMeasurementsHistoryActivity;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import java.util.List;

public class IFollowFragment
extends Fragment
implements ItemCheckedChecker,
IFollowContract.View {
    private ActionMode actionMode;
    IFollowListAdapter adapter;
    @BindView
    SwipeRefreshLayout emptySwipeToRefresh;
    @BindView
    TextView emptyTextView;
    IFollowPresenter presenter;
    @BindView
    RecyclerView recyclerView;
    @BindView
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView
    ViewFlipper viewFlipper;
    private Integer weightUnit;

    private void changeScreenState(int n) {
        this.viewFlipper.setDisplayedChild(this.viewFlipper.indexOfChild(this.viewFlipper.findViewById(n)));
    }

    private Long getUserId(String string2) {
        Long l = AuthHelper.getUserId((Context)this.getActivity(), string2);
        Object object = l;
        if (l == null) {
            object = new User();
            ((User)object).email = string2;
            AuthHelper.setUser((Context)this.getActivity(), (User)object);
            object = AuthHelper.getUserId((Context)this.getActivity(), string2);
        }
        return object;
    }

    private void initViews() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager((Context)this.getActivity()));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setHasFixedSize(true);
        this.swipeRefreshLayout.setOnRefreshListener(IFollowFragment$$Lambda$1.lambdaFactory$(this));
        this.emptySwipeToRefresh.setOnRefreshListener(IFollowFragment$$Lambda$2.lambdaFactory$(this));
        this.adapter.setItemOnClickListener(IFollowFragment$$Lambda$3.lambdaFactory$(this));
        this.adapter.setItemOnLongClickListener(IFollowFragment$$Lambda$4.lambdaFactory$(this));
        this.adapter.setItemOnBpClickListener(IFollowFragment$$Lambda$5.lambdaFactory$(this));
        this.adapter.setItemOnWeightClickListener(IFollowFragment$$Lambda$6.lambdaFactory$(this));
        this.adapter.setItemNotificationOnOffClickListener(IFollowFragment$$Lambda$7.lambdaFactory$(this));
        this.emptyTextView.setText(2131362427);
    }

    public static IFollowFragment newInstance() {
        return new IFollowFragment();
    }

    @Override
    public boolean isAnyItemChecked() {
        return this.presenter.getAmountOfSelectedItems() > 0;
    }

    @Override
    public boolean isItemChecked(int n) {
        return this.presenter.isItemChecked(n);
    }

    /* synthetic */ void lambda$initViews$0() {
        this.presenter.fetchData(true);
    }

    /* synthetic */ void lambda$initViews$1() {
        this.presenter.fetchData(true);
    }

    /* synthetic */ void lambda$initViews$2(Integer n) throws Exception {
        this.presenter.clickOnItem(n);
    }

    /* synthetic */ void lambda$initViews$3(Integer n) throws Exception {
        this.presenter.longClickOnItem(n);
    }

    /* synthetic */ void lambda$initViews$4(Integer n) throws Exception {
        this.presenter.clickOnBpItem(n);
    }

    /* synthetic */ void lambda$initViews$5(Integer n) throws Exception {
        this.presenter.clickOnWeightItem(n);
    }

    /* synthetic */ void lambda$initViews$6(Integer n, Boolean bl) throws Exception {
        this.presenter.clickOnNotification(n, bl);
    }

    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = (MvpApplication)this.getActivity().getApplication();
        this.weightUnit = DataHelper.ProfileHelper.getWeightUnit((Context)this.getActivity(), ((CustomApplication)((Object)object)).getCurrentUserId());
        DaggerIFollowComponent.builder().repositoryComponent(((MvpApplication)((Object)object)).getApplicationComponent()).iFollowPresenterModule(new IFollowPresenterModule(this)).build().inject(this);
        FnFNotificationStack.getInstance().clearCache();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968719, viewGroup, false);
        ButterKnife.bind(this, (View)layoutInflater);
        this.initViews();
        return layoutInflater;
    }

    public void onDestroyView() {
        this.presenter.unsubscribe();
        super.onDestroyView();
    }

    @OnClick
    void onRetryClick() {
        this.presenter.fetchData(true);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.presenter.subscribe();
        this.presenter.fetchData(false);
    }

    public void setUserVisibleHint(boolean bl) {
        super.setUserVisibleHint(bl);
        if (bl) {
            AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Following");
        }
        if (!bl && this.actionMode != null) {
            this.actionMode.finish();
        }
    }

    @Override
    public void showBpDetails(String string2) {
        Long l = this.getUserId(string2);
        this.startActivity(FriendMeasurementsHistoryActivity.getBpStartIntent((Context)this.getActivity(), l, string2));
        this.getActivity().overridePendingTransition(2131034132, 2131034131);
    }

    @Override
    public void showData(List<IFollowUser> list) {
        this.changeScreenState(2131820767);
        this.swipeRefreshLayout.setRefreshing(false);
        this.emptySwipeToRefresh.setRefreshing(false);
        this.adapter.setData(list, this.weightUnit);
    }

    @Override
    public void showEmpty() {
        this.changeScreenState(2131821069);
        this.emptySwipeToRefresh.setRefreshing(false);
    }

    @Override
    public void showError() {
        this.changeScreenState(2131821068);
        this.emptySwipeToRefresh.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        if (!this.swipeRefreshLayout.isRefreshing() && !this.emptySwipeToRefresh.isRefreshing()) {
            this.changeScreenState(2131821066);
        }
    }

    @Override
    public void showToastError() {
        Snackbar.make((View)this.swipeRefreshLayout, 2131361956, 0).show();
    }

    @Override
    public void showWeightDetails(String string2) {
        Long l = this.getUserId(string2);
        this.startActivity(FriendMeasurementsHistoryActivity.getWeightStartIntent((Context)this.getActivity(), l, string2));
        this.getActivity().overridePendingTransition(2131034132, 2131034131);
    }

    @Override
    public void startContextualActionBarMode() {
        this.actionMode = this.getActivity().startActionMode((ActionMode.Callback)new AbsListView.MultiChoiceModeListener(){

            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    default: {
                        return false;
                    }
                    case 2131821474: 
                }
                IFollowFragment.this.presenter.deleteSelectedUsers();
                return true;
            }

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu2) {
                actionMode.getMenuInflater().inflate(2131886086, menu2);
                ((FriendsFamilyMainFragment)IFollowFragment.this.getParentFragment()).enableContextualToolbarMode(true);
                return true;
            }

            public void onDestroyActionMode(ActionMode actionMode) {
                IFollowFragment.this.presenter.cabDestroyed();
                ((FriendsFamilyMainFragment)IFollowFragment.this.getParentFragment()).enableContextualToolbarMode(false);
                IFollowFragment.this.actionMode = null;
                IFollowFragment.this.adapter.notifyDataSetChanged();
            }

            public void onItemCheckedStateChanged(ActionMode actionMode, int n, long l, boolean bl) {
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu2) {
                return false;
            }
        });
    }

    @Override
    public void stopContextualActionBarMode() {
        if (this.actionMode != null) {
            this.actionMode.finish();
        }
    }

    @Override
    public void updateItemAtPosition(int n) {
        this.adapter.notifyItemChanged(n);
        if (this.actionMode != null) {
            this.actionMode.setTitle((CharSequence)String.valueOf(this.presenter.getAmountOfSelectedItems()));
        }
    }

}

