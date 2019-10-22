/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.inputmethod.InputMethodManager
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import com.getqardio.android.ui.fragment.SupportFragment;
import com.getqardio.android.ui.fragment.SupportResultFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class SupportHostFragment
extends Fragment
implements SupportFragment.CallBack {
    private Callback callback;

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(2131820778, fragment);
        fragmentTransaction.commit();
    }

    public static SupportHostFragment newInstance() {
        return new SupportHostFragment();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362059);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.callback = (Callback)activity;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130968838, viewGroup, false);
    }

    public void onPause() {
        super.onPause();
        Activity activity = this.getActivity();
        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager)activity.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onSendingFailed() {
        this.callback.onHideProgress();
    }

    @Override
    public void onSendingFinished(Intent intent) {
        this.callback.onHideProgress();
        this.changeFragment(SupportResultFragment.newInstance());
    }

    @Override
    public void onSendingStarted() {
        this.callback.onShowProgress();
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.changeFragment(SupportFragment.newInstance());
    }

    public static interface Callback {
        public void onHideProgress();

        public void onShowProgress();
    }

}

