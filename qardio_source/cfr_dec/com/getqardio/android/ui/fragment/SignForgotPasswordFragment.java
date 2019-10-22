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
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.ui.fragment.SignForgotPasswordFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.SignForgotPasswordFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.SignForgotPasswordFragment$$Lambda$3;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.Validator;
import com.getqardio.android.utils.ui.ActivityUtils;
import java.util.Iterator;
import java.util.List;

public class SignForgotPasswordFragment
extends Fragment {
    private SignForgotPasswordFragmentCallback callback;
    private EditText emailEdit;
    private BroadcastReceiver forgotPasswordReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("com.getqardio.android.Notifications.ForgotPassword.Result", false)) {
                SignForgotPasswordFragment.this.successfulResetPassword();
                return;
            }
            SignForgotPasswordFragment.this.failedResetPassword(ErrorHelper.getErrorsFromIntent(intent));
        }
    };
    private Button resetPasswordButton;
    private View rootView;
    private boolean showEmailError;

    private void doResetPassword() {
        this.showEmailError = true;
        this.setEmailTextChangedListener();
        this.callback.displayProgressDialog();
        AuthHelper.forgotPassword((Context)this.getActivity(), this.emailEdit.getText().toString());
    }

    private void failedResetPassword(List<BaseError> object) {
        ErrorHelper.logErrorsInDebug(object);
        this.callback.dismissProgressDialog();
        object = object.iterator();
        do {
            BaseError baseError;
            block7: {
                block6: {
                    if (!object.hasNext()) break block6;
                    baseError = (BaseError)object.next();
                    if (1 != baseError.id) break block7;
                    this.callback.displayNetworkErrorDialog();
                }
                return;
            }
            if (5 == baseError.id) {
                this.emailEdit.setError((CharSequence)baseError.defaultMessageText);
                return;
            }
            if (11 == baseError.id) {
                this.emailEdit.setError((CharSequence)(this.getString(2131362048) + "\n" + this.getString(2131362072)));
                return;
            }
            this.emailEdit.setError((CharSequence)baseError.defaultMessageText);
        } while (true);
    }

    private boolean isEmailValid() {
        boolean bl = Validator.isEmailValid(this.emailEdit, 2131362004, 2131361964, this.showEmailError);
        this.resetPasswordButton.setEnabled(bl);
        return bl;
    }

    private void setEmailTextChangedListener() {
        this.emailEdit.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                SignForgotPasswordFragment.this.isEmailValid();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                SignForgotPasswordFragment.this.isEmailValid();
            }
        });
        this.emailEdit.setOnEditorActionListener(SignForgotPasswordFragment$$Lambda$2.lambdaFactory$(this));
        this.emailEdit.setOnTouchListener(SignForgotPasswordFragment$$Lambda$3.lambdaFactory$(this));
    }

    private void successfulResetPassword() {
        this.callback.dismissProgressDialog();
        this.callback.displayResetPasswordPassed();
    }

    /* synthetic */ void lambda$onActivityCreated$0(View view) {
        this.doResetPassword();
    }

    /* synthetic */ boolean lambda$setEmailTextChangedListener$1(TextView textView, int n, KeyEvent keyEvent) {
        if (n == 6) {
            this.showEmailError = true;
            this.emailEdit.setCursorVisible(false);
            this.isEmailValid();
        }
        return false;
    }

    /* synthetic */ boolean lambda$setEmailTextChangedListener$2(View view, MotionEvent motionEvent) {
        this.emailEdit.setCursorVisible(true);
        return false;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131361923);
        this.emailEdit = (EditText)this.rootView.findViewById(2131821368);
        this.setEmailTextChangedListener();
        this.resetPasswordButton = (Button)this.rootView.findViewById(2131821369);
        this.resetPasswordButton.setOnClickListener(SignForgotPasswordFragment$$Lambda$1.lambdaFactory$(this));
        this.resetPasswordButton.setEnabled(false);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.callback = (SignForgotPasswordFragmentCallback)activity;
            return;
        }
        catch (ClassCastException classCastException) {
            classCastException.printStackTrace();
            return;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968830, viewGroup, false);
        return this.rootView;
    }

    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.forgotPasswordReceiver);
    }

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.forgotPasswordReceiver, new IntentFilter("com.getqardio.android.Notifications.ForgotPassword"));
    }

    public static interface SignForgotPasswordFragmentCallback {
        public void dismissProgressDialog();

        public void displayNetworkErrorDialog();

        public void displayProgressDialog();

        public void displayResetPasswordPassed();
    }

}

