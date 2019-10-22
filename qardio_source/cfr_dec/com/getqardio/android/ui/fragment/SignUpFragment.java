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
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.EditText
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$10;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$11;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$7;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$8;
import com.getqardio.android.ui.fragment.SignUpFragment$$Lambda$9;
import com.getqardio.android.utils.Constants;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.Validator;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.shared.utils.SingleEvent;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class SignUpFragment
extends Fragment {
    private Callback callback;
    private CheckBox cbMarketing;
    private CheckBox cbReadPrivacyPolicy;
    private Button createAccountButton;
    private EditText emailEdit;
    private SingleEvent eventLock = new SingleEvent();
    private EditText nameEdit;
    private EditText passwordEdit;
    private TextView privacyPolicyTextView;
    private View rootView;
    private boolean showEmailErrors;
    private boolean showNameErrors;
    private boolean showPasswordErrors;
    private BroadcastReceiver signUpReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if (Boolean.valueOf(intent.getBooleanExtra("com.getqardio.android.Notifications.CreateNewUser.Result", false)).booleanValue()) {
                SignUpFragment.this.successfulCreateNewAccount();
                return;
            }
            SignUpFragment.this.failedCreateNewAccount(ErrorHelper.getErrorsFromIntent(intent));
        }
    };
    private TextView termOfServiceTextView;

    static /* synthetic */ void access$lambda$0(SignUpFragment signUpFragment) {
        signUpFragment.doCreateNewAccount();
    }

    private void clearView(EditText editText) {
        if (editText != null) {
            if (editText.isFocused()) {
                editText.clearFocus();
            }
            editText.getEditableText().clear();
            editText.setError(null);
        }
    }

    private void doCreateNewAccount() {
        this.toggleMarketingNotification(this.cbMarketing.isChecked());
        this.emailEdit.setError(null);
        this.showPasswordErrors = true;
        this.callback.displayProgressDialog();
        String string2 = this.nameEdit.getText().toString().trim();
        String string3 = this.emailEdit.getText().toString().toLowerCase();
        String string4 = this.passwordEdit.getText().toString();
        boolean bl = this.cbMarketing.isChecked();
        AuthHelper.createNewUser((Context)this.getActivity(), string3, string4, string2, bl);
    }

    private void failedCreateNewAccount(List<BaseError> object) {
        ErrorHelper.logErrorsInDebug(object);
        this.callback.dismissProgressDialog();
        object = object.iterator();
        while (object.hasNext()) {
            BaseError baseError = (BaseError)object.next();
            if (1 == baseError.id) {
                this.emailEdit.setError((CharSequence)this.getString(2131362049));
                continue;
            }
            if (7 == baseError.id) {
                this.nameEdit.setError((CharSequence)baseError.defaultMessageText);
                continue;
            }
            if (3 == baseError.id) {
                this.emailEdit.setError((CharSequence)baseError.defaultMessageText);
                continue;
            }
            if (8 == baseError.id) {
                this.emailEdit.setError((CharSequence)baseError.defaultMessageText);
                continue;
            }
            this.emailEdit.setError((CharSequence)baseError.defaultMessageText);
        }
    }

    private boolean isChangedDataValid() {
        boolean bl = true & this.validateName(false) & this.validateEmail(false) & this.validatePassword(false) & this.cbReadPrivacyPolicy.isChecked();
        this.createAccountButton.setEnabled(bl);
        return bl;
    }

    private void setInitialEditState() {
        this.nameEdit.setText(null);
        this.emailEdit.setText(null);
        this.passwordEdit.setText(null);
        this.emailEdit.setError(null);
        this.passwordEdit.setError(null);
        this.passwordEdit.setText(null);
        this.showNameErrors = false;
        this.showNameErrors = false;
        this.showPasswordErrors = false;
        this.isChangedDataValid();
    }

    private void successfulCreateNewAccount() {
        this.callback.dismissProgressDialog();
        this.callback.displayInitProfile();
    }

    private void toggleMarketingNotification(boolean bl) {
        PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit().putBoolean("enable_marketing_content", bl).apply();
    }

    private boolean validateEmail(boolean bl) {
        return Validator.isEmailValid(this.emailEdit, 2131362004, 2131361964, bl);
    }

    private boolean validateName(boolean bl) {
        return Validator.isNameValid(this.nameEdit, true, 2131362002, bl);
    }

    private boolean validatePassword(boolean bl) {
        return Validator.isEditValid(Constants.Regexp.PASSWORD_PATTERN, this.passwordEdit, 2131361884, 2131361884, bl);
    }

    public void hideErrorMarks() {
        this.showEmailErrors = false;
        this.showNameErrors = false;
        this.showPasswordErrors = false;
        this.clearView(this.nameEdit);
        this.clearView(this.emailEdit);
        this.clearView(this.passwordEdit);
    }

    /* synthetic */ void lambda$null$6() {
        this.callback.displayTermsOfService();
    }

    /* synthetic */ void lambda$null$8() {
        this.callback.displayPrivacyPolicy();
    }

    /* synthetic */ void lambda$onActivityCreated$2(View view, boolean bl) {
        if (!bl && !this.showNameErrors) {
            this.showNameErrors = true;
            this.validateName(true);
        }
    }

    /* synthetic */ void lambda$onActivityCreated$3(View view, boolean bl) {
        if (!bl && !this.showEmailErrors) {
            this.showEmailErrors = true;
            this.validateEmail(true);
        }
    }

    /* synthetic */ void lambda$onActivityCreated$4(View view, boolean bl) {
        if (!bl && !this.showPasswordErrors) {
            this.showPasswordErrors = true;
            this.validatePassword(true);
        }
    }

    /* synthetic */ void lambda$onActivityCreated$5(View view) {
        this.eventLock.run(SignUpFragment$$Lambda$11.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onActivityCreated$7(View view) {
        this.eventLock.run(SignUpFragment$$Lambda$10.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onActivityCreated$9(View view) {
        this.eventLock.run(SignUpFragment$$Lambda$9.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onCreateView$0(CompoundButton compoundButton, boolean bl) {
        this.toggleMarketingNotification(bl);
    }

    /* synthetic */ void lambda$onCreateView$1(CompoundButton compoundButton, boolean bl) {
        this.isChangedDataValid();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131361894);
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Create Account");
        this.nameEdit = (EditText)this.rootView.findViewById(2131821376);
        this.nameEdit.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                SignUpFragment.this.validateName(SignUpFragment.this.showNameErrors);
                SignUpFragment.this.isChangedDataValid();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.nameEdit.setOnFocusChangeListener(SignUpFragment$$Lambda$3.lambdaFactory$(this));
        this.emailEdit = (EditText)this.rootView.findViewById(2131821368);
        this.emailEdit.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                SignUpFragment.this.validateEmail(SignUpFragment.this.showEmailErrors);
                SignUpFragment.this.isChangedDataValid();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.emailEdit.setOnFocusChangeListener(SignUpFragment$$Lambda$4.lambdaFactory$(this));
        this.passwordEdit = (EditText)this.rootView.findViewById(2131821372);
        this.passwordEdit.setTypeface(Typeface.DEFAULT);
        this.passwordEdit.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                SignUpFragment.this.validatePassword(SignUpFragment.this.showPasswordErrors);
                SignUpFragment.this.isChangedDataValid();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.passwordEdit.setOnFocusChangeListener(SignUpFragment$$Lambda$5.lambdaFactory$(this));
        this.createAccountButton = (Button)this.rootView.findViewById(2131821377);
        this.createAccountButton.setOnClickListener(SignUpFragment$$Lambda$6.lambdaFactory$(this));
        this.termOfServiceTextView = (TextView)this.rootView.findViewById(2131821379);
        this.termOfServiceTextView.setPaintFlags(this.termOfServiceTextView.getPaintFlags() | 8);
        this.termOfServiceTextView.setOnClickListener(SignUpFragment$$Lambda$7.lambdaFactory$(this));
        this.privacyPolicyTextView = (TextView)this.rootView.findViewById(2131821380);
        this.privacyPolicyTextView.setPaintFlags(this.privacyPolicyTextView.getPaintFlags() | 8);
        this.privacyPolicyTextView.setOnClickListener(SignUpFragment$$Lambda$8.lambdaFactory$(this));
        this.setInitialEditState();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.callback = (Callback)activity;
            return;
        }
        catch (ClassCastException classCastException) {
            classCastException.printStackTrace();
            return;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968833, viewGroup, false);
        this.cbMarketing = (CheckBox)this.rootView.findViewById(2131821382);
        this.cbMarketing.setOnCheckedChangeListener(SignUpFragment$$Lambda$1.lambdaFactory$(this));
        this.cbReadPrivacyPolicy = (CheckBox)this.rootView.findViewById(2131821381);
        this.cbReadPrivacyPolicy.setOnCheckedChangeListener(SignUpFragment$$Lambda$2.lambdaFactory$(this));
        return this.rootView;
    }

    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.signUpReceiver);
    }

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.signUpReceiver, new IntentFilter("com.getqardio.android.Notifications.CreateNewUser"));
    }

    public static interface Callback {
        public void dismissProgressDialog();

        public void displayInitProfile();

        public void displayPrivacyPolicy();

        public void displayProgressDialog();

        public void displayTermsOfService();
    }

}

