/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeListAdapter;

public class FollowMeListAdapter$UserViewHolder_ViewBinding
implements Unbinder {
    private FollowMeListAdapter.UserViewHolder target;
    private View view2131820754;

    public FollowMeListAdapter$UserViewHolder_ViewBinding(final FollowMeListAdapter.UserViewHolder userViewHolder, View view) {
        this.target = userViewHolder;
        userViewHolder.image = Utils.findRequiredViewAsType(view, 2131820710, "field 'image'", ImageView.class);
        userViewHolder.name = Utils.findRequiredViewAsType(view, 2131821060, "field 'name'", TextView.class);
        userViewHolder.email = Utils.findRequiredViewAsType(view, 2131821061, "field 'email'", TextView.class);
        View view2 = Utils.findRequiredView(view, 2131820754, "field 'root', method 'clickOnClickableArea', and method 'clickLongOnClickableArea'");
        userViewHolder.root = Utils.castView(view2, 2131820754, "field 'root'", LinearLayout.class);
        this.view2131820754 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                userViewHolder.clickOnClickableArea();
            }
        });
        view2.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                return userViewHolder.clickLongOnClickableArea();
            }
        });
        userViewHolder.checkIcon = Utils.findRequiredView(view, 2131821072, "field 'checkIcon'");
    }

}

