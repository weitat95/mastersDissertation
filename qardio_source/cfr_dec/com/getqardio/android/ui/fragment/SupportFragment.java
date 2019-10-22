/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.SupportTicket;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.SupportTicketsHelper;
import com.getqardio.android.ui.fragment.SupportFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.SupportFragment$$Lambda$2;
import com.getqardio.android.ui.widget.CustomEditText;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.logger.QardioLogger;
import com.getqardio.android.utils.ui.KeyboardHelper;

public class SupportFragment
extends Fragment
implements TextWatcher {
    private CallBack callBack;
    private CustomEditText etMessageBody;
    private CustomEditText etSubject;
    private View rootView;
    private MenuItem saveButton;
    private BroadcastReceiver sendSupportReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("com.getqardio.android.Notifications.Support.Result", true)) {
                SupportFragment.this.callBack.onSendingFinished(intent);
                return;
            }
            SupportFragment.this.callBack.onSendingFailed();
            Snackbar.make(SupportFragment.this.rootView, 2131361956, 0).show();
        }
    };
    private int subjectMaxLength;

    private void composeSupportTicket() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.etMessageBody.getEditableText().toString()).append("\n\n");
        stringBuilder.append("Qardio app version:").append(Utils.getVersionName((Context)this.getActivity())).append("\n");
        stringBuilder.append("Android device:").append(Utils.getDeviceModel()).append("\n");
        stringBuilder.append("Android version:").append(Utils.getAndroidVersion()).append("\n");
        stringBuilder.append("Language:").append(Utils.getLocale().getLanguage()).append("\n");
        stringBuilder.append("Phone battery level:").append(Utils.getBatteryLevel((Context)this.getActivity())).append("\n");
        stringBuilder.append("\n");
        QardioLogger qardioLogger = new QardioLogger((Context)this.getActivity());
        qardioLogger.getLastLogHalf(SupportFragment$$Lambda$2.lambdaFactory$(this, qardioLogger, stringBuilder));
    }

    public static SupportFragment newInstance() {
        return new SupportFragment();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onDataChanged() {
        boolean bl = true;
        if (this.saveButton == null) return;
        String string2 = this.etSubject.getEditableText().toString();
        boolean bl2 = !TextUtils.isEmpty((CharSequence)string2) && string2.length() <= this.subjectMaxLength;
        string2 = this.etMessageBody.getEditableText().toString();
        if (TextUtils.isEmpty((CharSequence)string2) || string2.length() > this.subjectMaxLength) {
            bl = false;
        }
        this.saveButton.setEnabled(bl2 & bl);
    }

    private void sendSupport() {
        this.callBack.onSendingStarted();
        this.composeSupportTicket();
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    /* synthetic */ void lambda$composeSupportTicket$1(QardioLogger object, StringBuilder stringBuilder, String object2) {
        ((QardioLogger)object).close();
        stringBuilder.append((String)object2);
        object = this.etSubject.getEditableText().toString();
        object2 = new SupportTicket();
        ((SupportTicket)object2).subject = object;
        ((SupportTicket)object2).messageBody = stringBuilder.toString();
        int n = SupportTicketsHelper.insertTicket((Context)this.getActivity(), (SupportTicket)object2);
        DataHelper.SupportHelper.requestSendSupport((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), n);
    }

    /* synthetic */ boolean lambda$onCreateOptionsMenu$0(MenuItem menuItem) {
        menuItem = this.getActivity();
        if (menuItem != null) {
            KeyboardHelper.hideKeyboard((Context)menuItem, menuItem.getCurrentFocus());
        }
        this.sendSupport();
        return true;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Support");
        this.subjectMaxLength = this.getResources().getInteger(2131623952);
        this.callBack = (CallBack)this.getParentFragment();
        this.setHasOptionsMenu(true);
        this.etSubject = (CustomEditText)this.rootView.findViewById(2131821392);
        this.etMessageBody = (CustomEditText)this.rootView.findViewById(2131821393);
        this.etSubject.addTextChangedListener((TextWatcher)this);
        this.etMessageBody.addTextChangedListener((TextWatcher)this);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menu2.clear();
        menuInflater.inflate(2131886096, menu2);
        this.saveButton = menu2.findItem(2131821493);
        this.saveButton.setEnabled(false);
        this.saveButton.setOnMenuItemClickListener(SupportFragment$$Lambda$1.lambdaFactory$(this));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968837, null, false);
        return this.rootView;
    }

    public void onDetach() {
        super.onDetach();
        this.callBack = null;
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.sendSupportReceiver);
    }

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.sendSupportReceiver, new IntentFilter("com.getqardio.android.Notifications.Support"));
    }

    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        this.onDataChanged();
    }

    public static interface CallBack {
        public void onSendingFailed();

        public void onSendingFinished(Intent var1);

        public void onSendingStarted();
    }

}

