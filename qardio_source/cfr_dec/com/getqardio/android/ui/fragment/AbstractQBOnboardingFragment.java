/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment$$Lambda$2;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public abstract class AbstractQBOnboardingFragment
extends Fragment {
    private ImageView background;
    protected OnDoneClickListener onDoneClickListener;
    protected View rootView;

    protected int getLayoutBackground() {
        return 2130837939;
    }

    protected abstract int getLayoutResource();

    protected void hideCloseOnBoardingButton() {
        this.rootView.findViewById(2131821054).setVisibility(4);
    }

    protected boolean isTransitionEnabled() {
        return true;
    }

    /* synthetic */ void lambda$onCreateView$0(View view) {
        this.getActivity().onBackPressed();
    }

    /* synthetic */ void lambda$onResume$1(int n) {
        try {
            Glide.with(this.getActivity()).load(n).asBitmap().centerCrop().preload(this.background.getWidth(), this.background.getHeight());
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.onDoneClickListener = (OnDoneClickListener)this.getParentFragment();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968709, viewGroup, false);
        if (this.getLayoutResource() >= 0) {
            this.background = (ImageView)this.rootView.findViewById(2131821053);
            Glide.with(this).load(this.getLayoutBackground()).asBitmap().centerCrop().into(this.background);
        }
        this.rootView.findViewById(2131821054).setOnClickListener(AbstractQBOnboardingFragment$$Lambda$1.lambdaFactory$(this));
        layoutInflater.inflate(this.getLayoutResource(), (ViewGroup)this.rootView.findViewById(2131821055), true);
        return this.rootView;
    }

    public void onResume() {
        super.onResume();
        int n = this.predictNextImage();
        if (n != -1 && this.background != null) {
            this.background.post(AbstractQBOnboardingFragment$$Lambda$2.lambdaFactory$(this, n));
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        view = (TextView)view.findViewById(2131820997);
        if (view != null) {
            view.setVisibility(4);
        }
    }

    protected int predictNextImage() {
        return -1;
    }
}

