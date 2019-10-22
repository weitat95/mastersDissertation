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
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.widget.EmailEditText;
import com.getqardio.android.utils.Validator;
import com.getqardio.android.utils.ui.KeyboardHelper;

public class SendMeasurementFragment
extends Fragment {
    private Callback callback;
    private EmailEditText emailAddressEditText;
    private View rootView;
    private Button sendButton;
    private BroadcastReceiver sendVisitorMeasurementReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if (SendMeasurementFragment.this.callback != null) {
                SendMeasurementFragment.this.callback.onSendingFinished();
            }
        }
    };
    private boolean showEmailError;

    private boolean isEmailValid() {
        boolean bl = Validator.isEmailValid(this.emailAddressEditText, 2131362001, 2131361964, this.showEmailError);
        this.sendButton.setEnabled(bl);
        return bl;
    }

    public static SendMeasurementFragment newInstance(int n) {
        SendMeasurementFragment sendMeasurementFragment = new SendMeasurementFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("com.getqardio.android.argument.VISITOR_MEASUREMENT_ID", n);
        sendMeasurementFragment.setArguments(bundle);
        return sendMeasurementFragment;
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
        this.rootView = layoutInflater.inflate(2130968820, null, false);
        return this.rootView;
    }

    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.sendVisitorMeasurementReceiver);
    }

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.sendVisitorMeasurementReceiver, new IntentFilter("com.getqardio.android.Notifications.SendVisitorMeasurement"));
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.emailAddressEditText = (EmailEditText)this.rootView.findViewById(2131821296);
        this.emailAddressEditText.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                SendMeasurementFragment.this.isEmailValid();
            }
        });
        this.emailAddressEditText.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
                if (n == 6) {
                    SendMeasurementFragment.this.showEmailError = true;
                    SendMeasurementFragment.this.emailAddressEditText.setCursorVisible(false);
                    SendMeasurementFragment.this.isEmailValid();
                }
                return false;
            }
        });
        this.emailAddressEditText.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                SendMeasurementFragment.this.emailAddressEditText.setCursorVisible(true);
                return false;
            }
        });
        this.sendButton = (Button)this.rootView.findViewById(2131821297);
        this.sendButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                KeyboardHelper.hideKeyboard((Context)SendMeasurementFragment.this.getActivity(), object);
                long l = CustomApplication.getApplication().getCurrentUserId();
                object = SendMeasurementFragment.this.emailAddressEditText.getEditableText().toString();
                int n = SendMeasurementFragment.this.getArguments().getInt("com.getqardio.android.argument.VISITOR_MEASUREMENT_ID", 0);
                if (SendMeasurementFragment.this.callback != null) {
                    SendMeasurementFragment.this.callback.onSendingStarted();
                }
                MeasurementHelper.BloodPressure.requestSaveVisitorMeasurement((Context)SendMeasurementFragment.this.getActivity(), l, n, (String)object);
            }
        });
    }

    public static interface Callback {
        public void onSendingFinished();

        public void onSendingStarted();
    }

}

