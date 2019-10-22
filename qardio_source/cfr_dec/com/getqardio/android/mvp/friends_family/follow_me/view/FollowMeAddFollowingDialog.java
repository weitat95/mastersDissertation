/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.DialogFragment
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.provider.ContactsContract
 *  android.provider.ContactsContract$CommonDataKinds
 *  android.provider.ContactsContract$CommonDataKinds$Email
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AutoCompleteTextView
 *  android.widget.Button
 *  android.widget.ListAdapter
 *  android.widget.ViewFlipper
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.app.Activity;
import android.app.Application;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v13.app.FragmentCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ViewFlipper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.friends_family.follow_me.DaggerFollowMeAddFollowingDialogComponent;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeAddFollowingDialogComponent;
import com.getqardio.android.mvp.friends_family.follow_me.FollowMeAddFollowingDialogContract;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenter;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeAddFollowingDialogPresenterModule;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog$$Lambda$1;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog$$Lambda$2;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog$$Lambda$3;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeAddFollowingDialog$$Lambda$4;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeContactPermissionExplanationDialog;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeEmailContactListAdapter;
import com.getqardio.android.mvp.friends_family.follow_me.view.FollowMeFragment;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.permission.PermissionUtil;
import com.getqardio.android.utils.ui.KeyboardHelper;
import com.getqardio.android.utils.ui.UIUtils;
import io.reactivex.functions.Action;

public class FollowMeAddFollowingDialog
extends DialogFragment
implements FollowMeAddFollowingDialogContract.View {
    @BindView
    AutoCompleteTextView email;
    private boolean permissionsAsked;
    FollowMeAddFollowingDialogPresenter presenter;
    @BindView
    Button send;
    @BindView
    ViewFlipper viewFlipper;

    private void changeScreenState(int n) {
        this.viewFlipper.setDisplayedChild(this.viewFlipper.indexOfChild(this.viewFlipper.findViewById(n)));
    }

    private void checkContactPermissions() {
        block3: {
            block2: {
                if (this.permissionsAsked || PermissionUtil.Contact.hasReadContactsPermission((Context)this.getActivity())) break block2;
                this.permissionsAsked = true;
                if (this.isExplanationDialogShown()) break block3;
                this.saveExplanationDialogShown();
                FollowMeContactPermissionExplanationDialog followMeContactPermissionExplanationDialog = FollowMeContactPermissionExplanationDialog.newInstance();
                followMeContactPermissionExplanationDialog.setOnOkClickListener(FollowMeAddFollowingDialog$$Lambda$3.lambdaFactory$(this));
                followMeContactPermissionExplanationDialog.setOnCancelClickListener(FollowMeAddFollowingDialog$$Lambda$4.lambdaFactory$());
                followMeContactPermissionExplanationDialog.show(this.getChildFragmentManager(), "FollowMeContactPermissionExplanationDialog");
            }
            return;
        }
        FragmentCompat.requestPermissions((Fragment)this, PermissionUtil.Contact.PERMISSIONS, 1001);
    }

    private SharedPreferences getSharedPref() {
        return this.getActivity().getSharedPreferences("FollowMeAddFollowingDialog", 0);
    }

    private void initViews() {
        this.send.setEnabled(false);
        this.email.setThreshold(0);
        if (PermissionUtil.Contact.hasReadContactsPermission((Context)this.getActivity())) {
            Object object = this.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, FollowMeEmailContactListAdapter.PROJECTION, null, null, null);
            object = new FollowMeEmailContactListAdapter((Context)this.getActivity(), (Cursor)object);
            this.email.setThreshold(0);
            this.email.setAdapter((ListAdapter)object);
            if (!TextUtils.isEmpty((CharSequence)this.email.getText())) {
                new Handler().postDelayed(FollowMeAddFollowingDialog$$Lambda$2.lambdaFactory$(this), 300L);
            }
        }
        this.email.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                FollowMeAddFollowingDialog.this.checkContactPermissions();
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                FollowMeAddFollowingDialog.this.presenter.emailChanged(String.valueOf(charSequence));
            }
        });
    }

    private boolean isExplanationDialogShown() {
        return this.getSharedPref().getBoolean("explanation_dialog_shown", false);
    }

    static /* synthetic */ void lambda$checkContactPermissions$3() throws Exception {
    }

    public static FollowMeAddFollowingDialog newInstance() {
        return new FollowMeAddFollowingDialog();
    }

    private void saveExplanationDialogShown() {
        this.getSharedPref().edit().putBoolean("explanation_dialog_shown", true).apply();
    }

    @Override
    public void dismiss() {
        ((FollowMeFragment)this.getParentFragment()).fetchData();
        super.dismiss();
    }

    @Override
    public void enableSendButton(boolean bl) {
        this.send.setEnabled(bl);
    }

    @Override
    public void hideError() {
        UIUtils.findTopTextInputLayout((View)this.email).setError(null);
    }

    /* synthetic */ void lambda$checkContactPermissions$2() throws Exception {
        FragmentCompat.requestPermissions((Fragment)this, PermissionUtil.Contact.PERMISSIONS, 1001);
    }

    /* synthetic */ void lambda$initViews$1() {
        if (this.isAdded()) {
            this.email.showDropDown();
        }
    }

    /* synthetic */ void lambda$onResume$0() {
        if (this.isAdded()) {
            KeyboardHelper.showKeyboard((Context)this.getActivity(), (View)this.email);
        }
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
    }

    @OnClick
    void onCancelClick() {
        this.dismiss();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setStyle(1, 2131493050);
        DaggerFollowMeAddFollowingDialogComponent.builder().repositoryComponent(((MvpApplication)this.getActivity().getApplication()).getApplicationComponent()).followMeAddFollowingDialogPresenterModule(new FollowMeAddFollowingDialogPresenterModule(this)).build().inject(this);
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Add a person");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968715, viewGroup);
        ButterKnife.bind(this, (View)layoutInflater);
        return layoutInflater;
    }

    public void onDestroyView() {
        this.presenter.unsubscribe();
        super.onDestroyView();
    }

    public void onResume() {
        super.onResume();
        new Handler().postDelayed(FollowMeAddFollowingDialog$$Lambda$1.lambdaFactory$(this), 300L);
        this.initViews();
    }

    @OnClick
    void onSendClick() {
        this.presenter.invite(this.email.getText().toString());
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.presenter.subscribe();
    }

    @Override
    public void showError(int n) {
        UIUtils.findTopTextInputLayout((View)this.email).setError(this.getString(n));
    }

    @Override
    public void showInputScreen() {
        this.setCancelable(true);
        this.changeScreenState(2131821063);
    }

    @Override
    public void showProgress() {
        this.setCancelable(false);
        this.changeScreenState(2131821066);
    }

}

