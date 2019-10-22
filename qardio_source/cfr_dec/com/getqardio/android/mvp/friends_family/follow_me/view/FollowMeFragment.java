/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
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
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
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
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.friends_family.FriendsFamilyMainFragment;
import com.getqardio.android.mvp.friends_family.follow_me.DaggerFollowMeFragmentComponent;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeFragmentComponent;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeFragmentContract;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenter;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMePresenterModule;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeUiModelItem;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment$$Lambda$1;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment$$Lambda$2;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment$$Lambda$3;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment$$Lambda$4;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment$$Lambda$5;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment$$Lambda$6;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeListAdapter;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import io.reactivex.functions.Consumer;
import java.util.List;

public class FollowMeFragment
extends Fragment
implements ItemCheckedChecker,
FollowMeFragmentContract.View {
    private ActionMode actionMode;
    FollowMeListAdapter adapter;
    @BindView
    FloatingActionButton addFollowing;
    @BindView
    SwipeRefreshLayout emptySwipeToRefresh;
    @BindView
    TextView emptyTextView;
    FollowMePresenter presenter;
    @BindView
    RecyclerView recyclerView;
    @BindView
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView
    ViewFlipper viewFlipper;

    private void changeScreenState(int n) {
        this.viewFlipper.setDisplayedChild(this.viewFlipper.indexOfChild(this.viewFlipper.findViewById(n)));
    }

    private void initViews() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager((Context)this.getActivity()));
        this.recyclerView.setAdapter(this.adapter);
        this.swipeRefreshLayout.setOnRefreshListener(FollowMeFragment$$Lambda$1.lambdaFactory$(this));
        this.emptySwipeToRefresh.setOnRefreshListener(FollowMeFragment$$Lambda$2.lambdaFactory$(this));
        this.adapter.setOnItemLongClickListener(FollowMeFragment$$Lambda$3.lambdaFactory$(this));
        this.adapter.setOnItemClickListener(FollowMeFragment$$Lambda$4.lambdaFactory$(this));
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int n, int n2) {
                super.onScrolled(recyclerView, n, n2);
                if (n2 > 0) {
                    if (!FollowMeFragment.this.addFollowing.isShown() || FollowMeFragment.this.actionMode != null) return;
                    {
                        FollowMeFragment.this.addFollowing.hide();
                        return;
                    }
                } else {
                    if (n2 >= 0 || FollowMeFragment.this.addFollowing.isShown() || FollowMeFragment.this.actionMode != null) return;
                    {
                        FollowMeFragment.this.addFollowing.show();
                        return;
                    }
                }
            }
        });
        this.emptyTextView.setText(2131362426);
    }

    public static FollowMeFragment newInstance() {
        return new FollowMeFragment();
    }

    public void fetchData() {
        this.presenter.fetchData(false);
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
        this.presenter.longClickOnItem(n);
    }

    /* synthetic */ void lambda$initViews$3(Integer n) throws Exception {
        this.presenter.clickOnItem(n);
    }

    /* synthetic */ void lambda$onAddFollowingButtonClick$5() {
        if (this.isAdded()) {
            FollowMeAddFollowingDialog.newInstance().show(this.getChildFragmentManager(), "FollowMeAddFollowingDialog");
        }
    }

    /* synthetic */ void lambda$setUserVisibleHint$4() {
        if (this.isAdded()) {
            this.addFollowing.show();
        }
    }

    @OnClick
    public void onAddFollowingButtonClick() {
        new Handler().postDelayed(FollowMeFragment$$Lambda$6.lambdaFactory$(this), 200L);
    }

    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = (MvpApplication)this.getActivity().getApplication();
        DaggerFollowMeFragmentComponent.builder().repositoryComponent(((MvpApplication)((Object)object)).getApplicationComponent()).followMePresenterModule(new FollowMePresenterModule(this)).build().inject(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968716, viewGroup, false);
        ButterKnife.bind(this, (View)layoutInflater);
        this.initViews();
        if (this.actionMode != null) {
            this.addFollowing.hide();
        }
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

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.presenter.onSaveInstanceState(bundle);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.presenter.subscribe();
        this.presenter.fetchData(false);
    }

    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        this.presenter.onRestoreInstanceState(bundle);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setUserVisibleHint(boolean bl) {
        super.setUserVisibleHint(bl);
        if (bl) {
            AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Followers");
        }
        if (this.addFollowing != null && this.actionMode == null) {
            if (bl) {
                new Handler().postDelayed(FollowMeFragment$$Lambda$5.lambdaFactory$(this), 200L);
            } else {
                this.addFollowing.hide();
            }
        }
        if (!bl && this.actionMode != null) {
            this.actionMode.finish();
        }
    }

    @Override
    public void showData(List<FollowMeUiModelItem> list) {
        this.changeScreenState(2131820767);
        this.swipeRefreshLayout.setRefreshing(false);
        this.emptySwipeToRefresh.setRefreshing(false);
        this.adapter.setData(list);
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
        Snackbar.make((View)this.viewFlipper, 2131361956, 0).show();
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
                FollowMeFragment.this.presenter.deleteSelectedUsers();
                return true;
            }

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu2) {
                actionMode.getMenuInflater().inflate(2131886086, menu2);
                FollowMeFragment.this.addFollowing.hide();
                ((FriendsFamilyMainFragment)FollowMeFragment.this.getParentFragment()).enableContextualToolbarMode(true);
                return true;
            }

            public void onDestroyActionMode(ActionMode actionMode) {
                ((FriendsFamilyMainFragment)FollowMeFragment.this.getParentFragment()).enableContextualToolbarMode(false);
                FollowMeFragment.this.actionMode = null;
                FollowMeFragment.this.adapter.notifyDataSetChanged();
                FollowMeFragment.this.addFollowing.show();
                FollowMeFragment.this.presenter.cabDestroyed();
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

