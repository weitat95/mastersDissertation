/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBOnboardingGetReadyStateFragment$$Lambda$1;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBOnboardingGetReadyStateFragment
extends AbstractQBOnboardingFragment
implements View.OnClickListener {
    private onSelectQBVersionListener listener;
    private ImageView qardioBaseOneSelected;
    private ImageView qardioBaseTwoSelected;
    private Button qardioButton;
    private int version;

    public static QBOnboardingGetReadyStateFragment newInstance() {
        return new QBOnboardingGetReadyStateFragment();
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837940;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968790;
    }

    @Override
    protected boolean isTransitionEnabled() {
        return false;
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        QardioBaseDeviceAnalyticsTracker.trackStartQBSetUpScreen((Context)this.getActivity());
        this.listener.onSelectQBVersion(this.version);
        this.onDoneClickListener.onDoneClick();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onClick(View view) {
        if (view.getId() == 2131821230) {
            this.qardioBaseTwoSelected.setImageDrawable(this.getResources().getDrawable(2130837811));
            this.qardioBaseOneSelected.setImageDrawable(this.getResources().getDrawable(2130837785));
            this.version = 2;
        } else {
            this.qardioBaseTwoSelected.setImageDrawable(this.getResources().getDrawable(2130837785));
            this.qardioBaseOneSelected.setImageDrawable(this.getResources().getDrawable(2130837811));
            this.version = 1;
        }
        this.qardioButton.setEnabled(true);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.listener = (onSelectQBVersionListener)this.getParentFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        bundle = (ViewGroup)view.findViewById(2131821230);
        ((TextView)bundle.findViewById(2131821240)).setText(2131362328);
        ((TextView)bundle.findViewById(2131821241)).setText(2131362329);
        this.qardioBaseTwoSelected = (ImageView)bundle.findViewById(2131821239);
        bundle.setOnClickListener((View.OnClickListener)this);
        view = (ViewGroup)view.findViewById(2131821231);
        ((TextView)view.findViewById(2131821240)).setText(2131362327);
        ((TextView)view.findViewById(2131821241)).setText(2131362330);
        view.setOnClickListener((View.OnClickListener)this);
        this.qardioBaseOneSelected = (ImageView)view.findViewById(2131821239);
        this.qardioButton = (Button)this.rootView.findViewById(2131821232);
        this.qardioButton.setOnClickListener(QBOnboardingGetReadyStateFragment$$Lambda$1.lambdaFactory$(this));
        this.qardioButton.setEnabled(false);
    }

    @Override
    protected int predictNextImage() {
        return 2130837939;
    }

    public static interface onSelectQBVersionListener {
        public void onSelectQBVersion(int var1);
    }

}

