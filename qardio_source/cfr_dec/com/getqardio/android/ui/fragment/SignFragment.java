/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.getqardio.android.ui.activity.SignActivityCallback;
import com.getqardio.android.ui.fragment.SignFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.SignInFragment;
import com.getqardio.android.ui.fragment.SignUpFragment;

public class SignFragment
extends Fragment {
    private TextView actionBarTitle;
    private View backButton;
    private SignActivityCallback callback;
    private View rootView;

    public static SignFragment newInstance(boolean bl) {
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("IS_FIRST_LOGIN_ARGUMENT", bl);
        SignFragment signFragment = new SignFragment();
        signFragment.setArguments(bundle);
        return signFragment;
    }

    public void displaySignIn() {
        this.callback.setSignFragment(10);
        this.actionBarTitle.setText(2131362052);
        this.getFragmentManager().beginTransaction().replace(2131820892, (Fragment)new SignInFragment(), "SIGN_IN_FRAGMENT_TAG").commitAllowingStateLoss();
    }

    public void displaySignInWithAnimation() {
        this.callback.setSignFragment(10);
        this.actionBarTitle.setText(2131362052);
        this.getFragmentManager().beginTransaction().setCustomAnimations(2131099654, 2131099655, 2131099652, 2131099653).replace(2131820892, (Fragment)new SignInFragment(), "SIGN_IN_FRAGMENT_TAG").commitAllowingStateLoss();
    }

    public void displaySignUp() {
        this.callback.setSignFragment(11);
        this.actionBarTitle.setText(2131361894);
        this.getFragmentManager().beginTransaction().replace(2131820892, (Fragment)new SignUpFragment(), "SIGN_UP_FRAGMENT_TAG").commitAllowingStateLoss();
    }

    public void displaySignUpWithAnimation() {
        this.callback.setSignFragment(11);
        this.actionBarTitle.setText(2131361894);
        this.getFragmentManager().beginTransaction().setCustomAnimations(2131099654, 2131099655, 2131099652, 2131099653).replace(2131820892, (Fragment)new SignUpFragment(), "SIGN_UP_FRAGMENT_TAG").commitAllowingStateLoss();
    }

    public void hideErrorMarks() {
        Object object = this.getFragmentManager();
        if (object != null) {
            SignInFragment signInFragment = (SignInFragment)object.findFragmentByTag("SIGN_IN_FRAGMENT_TAG");
            if (signInFragment != null) {
                signInFragment.hideErrorMarks();
            }
            if ((object = (SignUpFragment)object.findFragmentByTag("SIGN_UP_FRAGMENT_TAG")) != null) {
                ((SignUpFragment)((Object)object)).hideErrorMarks();
            }
        }
    }

    /* synthetic */ void lambda$onActivityCreated$0(View view) {
        this.getActivity().onBackPressed();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.backButton = this.rootView.findViewById(2131821371);
        this.backButton.setOnClickListener(SignFragment$$Lambda$1.lambdaFactory$(this));
        this.actionBarTitle = (TextView)this.rootView.findViewById(2131820705);
        if (this.getArguments().getBoolean("IS_FIRST_LOGIN_ARGUMENT", false)) {
            this.displaySignUp();
            return;
        }
        this.displaySignIn();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.callback = (SignActivityCallback)activity;
            return;
        }
        catch (ClassCastException classCastException) {
            classCastException.printStackTrace();
            return;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968831, viewGroup, false);
        return this.rootView;
    }

    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.setRetainInstance(true);
    }
}

