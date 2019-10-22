/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AutoCompleteTextView
 *  android.widget.Button
 *  android.widget.ViewFlipper
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ViewFlipper;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog;

public class FollowMeAddFollowingDialog_ViewBinding
implements Unbinder {
    private FollowMeAddFollowingDialog target;
    private View view2131821064;
    private View view2131821065;

    public FollowMeAddFollowingDialog_ViewBinding(final FollowMeAddFollowingDialog followMeAddFollowingDialog, View view) {
        this.target = followMeAddFollowingDialog;
        followMeAddFollowingDialog.email = Utils.findRequiredViewAsType(view, 2131821061, "field 'email'", AutoCompleteTextView.class);
        View view2 = Utils.findRequiredView(view, 2131821065, "field 'send' and method 'onSendClick'");
        followMeAddFollowingDialog.send = Utils.castView(view2, 2131821065, "field 'send'", Button.class);
        this.view2131821065 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                followMeAddFollowingDialog.onSendClick();
            }
        });
        followMeAddFollowingDialog.viewFlipper = Utils.findRequiredViewAsType(view, 2131821062, "field 'viewFlipper'", ViewFlipper.class);
        this.view2131821064 = view = Utils.findRequiredView(view, 2131821064, "method 'onCancelClick'");
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                followMeAddFollowingDialog.onCancelClick();
            }
        });
    }

}

