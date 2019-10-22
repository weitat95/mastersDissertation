/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.Application
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBOnboardingHowToChangeNameFragment$$Lambda$1;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.wizard.OnDoneClickListener;

public class QBOnboardingHowToChangeNameFragment
extends AbstractQBOnboardingFragment {
    public static QBOnboardingHowToChangeNameFragment newInstance() {
        return new QBOnboardingHowToChangeNameFragment();
    }

    @SuppressLint(value={"SetTextI18n"})
    private void setupName(View view, String string2) {
        Typeface typeface = Typeface.createFromAsset((AssetManager)this.getActivity().getAssets(), (String)"fonts/dotty.ttf");
        ((TextView)view.findViewById(2131821255)).setTypeface(typeface);
        TextView textView = (TextView)view.findViewById(2131821256);
        textView.setTypeface(typeface);
        textView.setText((CharSequence)string2);
        ((TextView)view.findViewById(2131821257)).setTypeface(typeface);
    }

    @Override
    protected int getLayoutResource() {
        return 2130968797;
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        this.onDoneClickListener.onDoneClick();
    }

    public void onActivityCreated(Bundle object) {
        super.onActivityCreated((Bundle)object);
        QardioBaseDeviceAnalyticsTracker.trackUserToggleScreen((Context)this.getActivity());
        object = ActivityUtils.getActionBar(this.getActivity());
        if (object != null) {
            ((ActionBar)object).setTitle(2131362460);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle object) {
        super.onViewCreated(view, (Bundle)object);
        this.hideCloseOnBoardingButton();
        object = ((CustomApplication)this.getActivity().getApplication()).getCurrentUserId();
        this.setupName(view, DataHelper.ProfileHelper.getProfileForUser((Context)this.getActivity(), (long)object.longValue()).qbVisibleName);
        view.findViewById(2131821251).setOnClickListener(QBOnboardingHowToChangeNameFragment$$Lambda$1.lambdaFactory$(this));
    }
}

