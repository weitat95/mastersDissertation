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
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.widget.Button
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
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.getqardio.android.fcm.FCMManager;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.utils.Constants;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.Validator;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.shared.utils.SingleEvent;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class SignInFragment
extends Fragment {
    private Callback callback;
    private EditText emailEdit;
    private SingleEvent eventLock = new SingleEvent();
    private TextView forgotPassword;
    private Button loginButton;
    private BroadcastReceiver loginReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context object, Intent intent) {
            intent.getAction();
            if (Boolean.valueOf(intent.getBooleanExtra("com.getqardio.android.Notifications.Login.Result", false)).booleanValue()) {
                SignInFragment.this.successfulCreateNewAccount();
            } else {
                SignInFragment.this.failedCreateNewAccount(ErrorHelper.getErrorsFromIntent(intent));
            }
            if ((object = ((MvpApplication)object.getApplicationContext()).getFCMManager()) != null) {
                ((FCMManager)object).setRegistrationPending();
            }
        }
    };
    private EditText passwordEdit;
    private View rootView;
    private boolean showEmailErrors;
    private boolean showPasswordErrors;
    private TextView signUp;

    private void clearView(EditText editText) {
        if (editText != null) {
            if (editText.isFocused()) {
                editText.clearFocus();
            }
            editText.getEditableText().clear();
            editText.setError(null);
        }
    }

    private void doLogin() {
        this.emailEdit.setError(null);
        this.showPasswordErrors = true;
        this.callback.displayProgressDialog();
        if (!TextUtils.isEmpty((CharSequence)this.emailEdit.getText())) {
            String string2 = this.emailEdit.getText().toString().toLowerCase();
            AuthHelper.login((Context)this.getActivity(), string2, this.passwordEdit.getText().toString());
        }
    }

    private void failedCreateNewAccount(List<BaseError> object) {
        ErrorHelper.logErrorsInDebug(object);
        this.callback.dismissProgressDialog();
        object = object.iterator();
        while (object.hasNext()) {
            BaseError baseError = (BaseError)object.next();
            if (baseError.id == 1) {
                this.emailEdit.setError((CharSequence)this.getString(2131361956));
                continue;
            }
            if (baseError.id == 9) {
                this.passwordEdit.setError((CharSequence)"Invalid credentials");
                continue;
            }
            if (baseError.id == 4) {
                this.passwordEdit.setError((CharSequence)this.getString(2131362425));
                continue;
            }
            if (baseError.id == 5) {
                this.emailEdit.setError((CharSequence)baseError.defaultMessageText);
                continue;
            }
            this.emailEdit.setError((CharSequence)baseError.defaultMessageText);
        }
    }

    private boolean isChangedDataValid() {
        boolean bl = this.validateEmail(false) & this.validatePassword(false);
        this.loginButton.setEnabled(bl);
        return bl;
    }

    private void setInitialEditState() {
        this.emailEdit.setText(null);
        this.passwordEdit.setText(null);
        this.showEmailErrors = false;
        this.showPasswordErrors = false;
        this.isChangedDataValid();
    }

    private void successfulCreateNewAccount() {
        this.callback.displayStartActivity();
    }

    private boolean validateEmail(boolean bl) {
        return Validator.isEmailValid(this.emailEdit, 2131362004, 2131361964, bl);
    }

    private boolean validatePassword(boolean bl) {
        return Validator.isEditValid(Constants.Regexp.PASSWORD_PATTERN, this.passwordEdit, 2131362023, 2131361884, bl);
    }

    public void hideErrorMarks() {
        this.clearView(this.emailEdit);
        this.clearView(this.passwordEdit);
        this.showEmailErrors = false;
        this.showPasswordErrors = false;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362052);
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Sign in");
        this.loginButton = (Button)this.rootView.findViewById(2131821373);
        this.loginButton.setEnabled(false);
        this.loginButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SignInFragment.this.eventLock.run(new Runnable(){

                    @Override
                    public void run() {
                        SignInFragment.this.doLogin();
                    }
                });
            }

        });
        this.signUp = (TextView)this.rootView.findViewById(2131821375);
        this.signUp.setPaintFlags(this.signUp.getPaintFlags() | 8);
        this.signUp.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SignInFragment.this.eventLock.run(new Runnable(){

                    @Override
                    public void run() {
                        SignInFragment.this.callback.displaySignUp();
                        SignInFragment.this.setInitialEditState();
                    }
                });
            }

        });
        this.forgotPassword = (TextView)this.rootView.findViewById(2131821374);
        this.forgotPassword.setPaintFlags(this.forgotPassword.getPaintFlags() | 8);
        this.forgotPassword.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SignInFragment.this.eventLock.run(new Runnable(){

                    @Override
                    public void run() {
                        SignInFragment.this.callback.displayForgotPassword();
                    }
                });
            }

        });
        this.emailEdit = (EditText)this.rootView.findViewById(2131821368);
        this.emailEdit.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                SignInFragment.this.validateEmail(SignInFragment.this.showEmailErrors);
                SignInFragment.this.isChangedDataValid();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.emailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl) {
                if (!bl) {
                    SignInFragment.this.showEmailErrors = true;
                    SignInFragment.this.validateEmail(true);
                }
            }
        });
        this.passwordEdit = (EditText)this.rootView.findViewById(2131821372);
        this.passwordEdit.setTypeface(Typeface.DEFAULT);
        this.passwordEdit.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                SignInFragment.this.validatePassword(SignInFragment.this.showPasswordErrors);
                SignInFragment.this.isChangedDataValid();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl) {
                if (!bl) {
                    SignInFragment.this.showPasswordErrors = true;
                    SignInFragment.this.validatePassword(true);
                }
            }
        });
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
        this.rootView = layoutInflater.inflate(2130968832, viewGroup, false);
        return this.rootView;
    }

    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.loginReceiver);
    }

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.loginReceiver, new IntentFilter("com.getqardio.android.Notifications.LOGIN"));
    }

    public static interface Callback {
        public void dismissProgressDialog();

        public void displayForgotPassword();

        public void displayProgressDialog();

        public void displaySignUp();

        public void displayStartActivity();
    }

}

