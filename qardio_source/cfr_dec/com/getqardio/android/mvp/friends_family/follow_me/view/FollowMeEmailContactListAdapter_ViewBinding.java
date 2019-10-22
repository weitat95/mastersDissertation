/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeEmailContactListAdapter;

public class FollowMeEmailContactListAdapter_ViewBinding
implements Unbinder {
    private FollowMeEmailContactListAdapter target;

    public FollowMeEmailContactListAdapter_ViewBinding(FollowMeEmailContactListAdapter followMeEmailContactListAdapter, View view) {
        this.target = followMeEmailContactListAdapter;
        followMeEmailContactListAdapter.image = Utils.findRequiredViewAsType(view, 2131820710, "field 'image'", ImageView.class);
        followMeEmailContactListAdapter.email = Utils.findRequiredViewAsType(view, 2131821061, "field 'email'", TextView.class);
        followMeEmailContactListAdapter.name = Utils.findRequiredViewAsType(view, 2131821060, "field 'name'", TextView.class);
    }
}

