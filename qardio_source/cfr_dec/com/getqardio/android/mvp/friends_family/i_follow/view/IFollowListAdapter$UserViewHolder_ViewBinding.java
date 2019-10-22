/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.friends_family.i_follow.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.common.ui.widget.MeasurementFlatIndicator;
import com.getqardio.android.mvp.friends_family.i_follow.view.IFollowListAdapter;

public class IFollowListAdapter$UserViewHolder_ViewBinding
implements Unbinder {
    private IFollowListAdapter.UserViewHolder target;
    private View view2131821071;
    private View view2131821073;
    private View view2131821074;
    private View view2131821079;

    public IFollowListAdapter$UserViewHolder_ViewBinding(final IFollowListAdapter.UserViewHolder userViewHolder, View view) {
        this.target = userViewHolder;
        userViewHolder.imageView = Utils.findRequiredViewAsType(view, 2131820710, "field 'imageView'", ImageView.class);
        userViewHolder.nameView = Utils.findRequiredViewAsType(view, 2131821060, "field 'nameView'", TextView.class);
        userViewHolder.emailView = Utils.findRequiredViewAsType(view, 2131821061, "field 'emailView'", TextView.class);
        userViewHolder.bpView = Utils.findRequiredViewAsType(view, 2131821084, "field 'bpView'", TextView.class);
        userViewHolder.bpDateView = Utils.findRequiredViewAsType(view, 2131821080, "field 'bpDateView'", TextView.class);
        userViewHolder.weightView = Utils.findRequiredViewAsType(view, 2131821076, "field 'weightView'", TextView.class);
        View view2 = Utils.findRequiredView(view, 2131821073, "field 'notificationButton' and method 'notificationSettingsClicked'");
        userViewHolder.notificationButton = Utils.castView(view2, 2131821073, "field 'notificationButton'", ImageButton.class);
        this.view2131821073 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                userViewHolder.notificationSettingsClicked();
            }
        });
        userViewHolder.bpBlockView = view2 = Utils.findRequiredView(view, 2131821079, "field 'bpBlockView', method 'onBloodPressureCLick', and method 'onNameEmailAreaLongClick'");
        this.view2131821079 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                userViewHolder.onBloodPressureCLick();
            }
        });
        view2.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                return userViewHolder.onNameEmailAreaLongClick();
            }
        });
        userViewHolder.weightDateView = Utils.findRequiredViewAsType(view, 2131821075, "field 'weightDateView'", TextView.class);
        userViewHolder.weightBlockView = view2 = Utils.findRequiredView(view, 2131821074, "field 'weightBlockView', method 'onWeightClick', and method 'onNameEmailAreaLongClick'");
        this.view2131821074 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                userViewHolder.onWeightClick();
            }
        });
        view2.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                return userViewHolder.onNameEmailAreaLongClick();
            }
        });
        userViewHolder.nameEmailAreaView = view2 = Utils.findRequiredView(view, 2131821071, "field 'nameEmailAreaView', method 'onNameEmailAreaClick', and method 'onNameEmailAreaLongClick'");
        this.view2131821071 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                userViewHolder.onNameEmailAreaClick();
            }
        });
        view2.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                return userViewHolder.onNameEmailAreaLongClick();
            }
        });
        userViewHolder.bpIndicatorView = Utils.findRequiredViewAsType(view, 2131821081, "field 'bpIndicatorView'", MeasurementFlatIndicator.class);
        userViewHolder.bmiIndicatorView = Utils.findRequiredViewAsType(view, 2131821077, "field 'bmiIndicatorView'", MeasurementFlatIndicator.class);
        userViewHolder.checkIconView = Utils.findRequiredView(view, 2131821072, "field 'checkIconView'");
        userViewHolder.pulseView = Utils.findRequiredViewAsType(view, 2131820930, "field 'pulseView'", TextView.class);
        userViewHolder.pulseImg = Utils.findRequiredViewAsType(view, 2131821083, "field 'pulseImg'", ImageView.class);
        userViewHolder.pulseLabel = Utils.findRequiredView(view, 2131821082, "field 'pulseLabel'");
        userViewHolder.separatorView = Utils.findRequiredView(view, 2131821078, "field 'separatorView'");
        userViewHolder.rootView = Utils.findRequiredView(view, 2131820754, "field 'rootView'");
    }

}

