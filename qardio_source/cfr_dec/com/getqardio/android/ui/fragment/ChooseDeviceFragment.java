/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getqardio.android.shopify.ShopifyAnalytics;
import com.getqardio.android.ui.fragment.ChooseDeviceFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.ChooseDeviceFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.ChooseDeviceFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.ChooseDeviceFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.ChooseDeviceFragment$$Lambda$5;
import com.getqardio.android.utils.ui.ActivityUtils;

public class ChooseDeviceFragment
extends Fragment {
    private Callback callback;

    private void initViews(View view) {
        Handler handler = new Handler();
        view.findViewById(2131820991).setOnClickListener(ChooseDeviceFragment$$Lambda$1.lambdaFactory$(this, handler));
        view.findViewById(2131820992).setOnClickListener(ChooseDeviceFragment$$Lambda$2.lambdaFactory$(this, handler));
        view.findViewById(2131820993).setOnClickListener(ChooseDeviceFragment$$Lambda$3.lambdaFactory$(this));
    }

    public static ChooseDeviceFragment newInstance() {
        return new ChooseDeviceFragment();
    }

    /* synthetic */ void lambda$initViews$1(Handler handler, View view) {
        handler.postDelayed(ChooseDeviceFragment$$Lambda$5.lambdaFactory$(this), 300L);
    }

    /* synthetic */ void lambda$initViews$3(Handler handler, View view) {
        handler.postDelayed(ChooseDeviceFragment$$Lambda$4.lambdaFactory$(this), 300L);
    }

    /* synthetic */ void lambda$initViews$4(View view) {
        ShopifyAnalytics.getInstance().trackClickBuyNow("choose device");
        if (this.callback != null) {
            this.callback.onDeviceSelected(2131821480);
        }
    }

    /* synthetic */ void lambda$null$0() {
        if (this.callback != null) {
            this.callback.onDeviceSelected(2131821476);
        }
    }

    /* synthetic */ void lambda$null$2() {
        if (this.callback != null) {
            this.callback.onDeviceSelected(2131821477);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.callback = (Callback)activity;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968661, viewGroup, false);
        this.initViews((View)layoutInflater);
        return layoutInflater;
    }

    public void onDetach() {
        this.callback = null;
        super.onDetach();
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362194);
    }

    public static interface Callback {
        public void onDeviceSelected(int var1);
    }

}

