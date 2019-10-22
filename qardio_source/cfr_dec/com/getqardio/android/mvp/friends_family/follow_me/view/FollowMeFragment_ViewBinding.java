/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 *  android.widget.ViewFlipper
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment;

public class FollowMeFragment_ViewBinding
implements Unbinder {
    private FollowMeFragment target;
    private View view2131821011;
    private View view2131821070;

    public FollowMeFragment_ViewBinding(final FollowMeFragment followMeFragment, View view) {
        this.target = followMeFragment;
        followMeFragment.swipeRefreshLayout = Utils.findRequiredViewAsType(view, 2131820767, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
        followMeFragment.emptyTextView = Utils.findRequiredViewAsType(view, 2131821008, "field 'emptyTextView'", TextView.class);
        followMeFragment.emptySwipeToRefresh = Utils.findRequiredViewAsType(view, 2131821069, "field 'emptySwipeToRefresh'", SwipeRefreshLayout.class);
        followMeFragment.recyclerView = Utils.findRequiredViewAsType(view, 2131821067, "field 'recyclerView'", RecyclerView.class);
        followMeFragment.viewFlipper = Utils.findRequiredViewAsType(view, 2131821062, "field 'viewFlipper'", ViewFlipper.class);
        View view2 = Utils.findRequiredView(view, 2131821070, "field 'addFollowing' and method 'onAddFollowingButtonClick'");
        followMeFragment.addFollowing = Utils.castView(view2, 2131821070, "field 'addFollowing'", FloatingActionButton.class);
        this.view2131821070 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                followMeFragment.onAddFollowingButtonClick();
            }
        });
        this.view2131821011 = view = Utils.findRequiredView(view, 2131821011, "method 'onRetryClick'");
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                followMeFragment.onRetryClick();
            }
        });
    }

}

