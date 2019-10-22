/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageView
 *  android.widget.ProgressBar
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.ui.fragment.QBProgressOnboardingFragment;
import com.getqardio.android.ui.widget.NonSwipeableViewPager;

public class QBProgressOnboardingFragment_ViewBinding
implements Unbinder {
    private QBProgressOnboardingFragment target;
    private View view2131821216;
    private View view2131821218;
    private View view2131821219;

    public QBProgressOnboardingFragment_ViewBinding(final QBProgressOnboardingFragment qBProgressOnboardingFragment, View view) {
        View view2;
        this.target = qBProgressOnboardingFragment;
        qBProgressOnboardingFragment.wifiConfFailed = Utils.findRequiredView(view, 2131821215, "field 'wifiConfFailed'");
        qBProgressOnboardingFragment.upgradeFailed = Utils.findRequiredView(view, 2131821217, "field 'upgradeFailed'");
        qBProgressOnboardingFragment.smileImage = Utils.findRequiredViewAsType(view, 2131821221, "field 'smileImage'", ImageView.class);
        qBProgressOnboardingFragment.progressBar = Utils.findRequiredViewAsType(view, 2131821222, "field 'progressBar'", ProgressBar.class);
        qBProgressOnboardingFragment.pager = Utils.findRequiredViewAsType(view, 2131821095, "field 'pager'", NonSwipeableViewPager.class);
        this.view2131821216 = view2 = Utils.findRequiredView(view, 2131821216, "method 'onWifiTryAgainClick'");
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                qBProgressOnboardingFragment.onWifiTryAgainClick();
            }
        });
        this.view2131821218 = view2 = Utils.findRequiredView(view, 2131821218, "method 'onUpgradeTryAgainClick'");
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                qBProgressOnboardingFragment.onUpgradeTryAgainClick();
            }
        });
        this.view2131821219 = view = Utils.findRequiredView(view, 2131821219, "method 'onUpgradeSkipClick'");
        view.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                qBProgressOnboardingFragment.onUpgradeSkipClick();
            }
        });
    }

}

