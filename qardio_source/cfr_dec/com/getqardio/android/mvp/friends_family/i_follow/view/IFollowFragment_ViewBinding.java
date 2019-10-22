/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 *  android.widget.ViewFlipper
 */
package com.getqardio.android.mvp.friends_family.i_follow.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowFragment;

public class IFollowFragment_ViewBinding
implements Unbinder {
    private IFollowFragment target;
    private View view2131821011;

    public IFollowFragment_ViewBinding(final IFollowFragment iFollowFragment, View view) {
        this.target = iFollowFragment;
        iFollowFragment.swipeRefreshLayout = Utils.findRequiredViewAsType(view, 2131820767, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
        iFollowFragment.emptyTextView = Utils.findRequiredViewAsType(view, 2131821008, "field 'emptyTextView'", TextView.class);
        iFollowFragment.recyclerView = Utils.findRequiredViewAsType(view, 2131821067, "field 'recyclerView'", RecyclerView.class);
        iFollowFragment.viewFlipper = Utils.findRequiredViewAsType(view, 2131821062, "field 'viewFlipper'", ViewFlipper.class);
        iFollowFragment.emptySwipeToRefresh = Utils.findRequiredViewAsType(view, 2131821069, "field 'emptySwipeToRefresh'", SwipeRefreshLayout.class);
        this.view2131821011 = view = Utils.findRequiredView(view, 2131821011, "method 'onRetryClick'");
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                iFollowFragment.onRetryClick();
            }
        });
    }

}

