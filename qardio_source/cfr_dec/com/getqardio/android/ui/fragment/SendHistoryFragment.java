/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.Loader
 *  android.database.Cursor
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
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
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
import android.widget.CompoundButton;
import android.widget.TextView;
import com.getqardio.android.datamodel.MeasurementsHistory;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.fragment.SendHistoryFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.SendHistoryFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.SendHistoryFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.SendHistoryFragment$$Lambda$4;
import com.getqardio.android.ui.widget.CustomEditText;
import com.getqardio.android.ui.widget.CustomSwitch;
import com.getqardio.android.ui.widget.EmailEditText;
import com.getqardio.android.ui.widget.PickContactView;
import com.getqardio.android.utils.ContactsHelper;
import com.getqardio.android.utils.Validator;
import com.getqardio.android.utils.ui.KeyboardHelper;

public class SendHistoryFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor> {
    private Button btnSend;
    private Callback callback;
    private EmailEditText etDoctorEmail;
    private CustomEditText etDoctorName;
    private CustomEditText etNote;
    private PickContactView pickContactView;
    private Profile profile;
    private View rootView;
    private BroadcastReceiver sendHistoryReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            boolean bl = SendHistoryFragment.this.switchMyDoctor.isChecked();
            intent.putExtra("com.getqardio.android.extras.SENT_TO_MY_DOCTOR", bl);
            if (SendHistoryFragment.this.callback != null) {
                SendHistoryFragment.this.callback.onSendingFinished(intent);
            }
            if (bl && SendHistoryFragment.this.profile != null) {
                DataHelper.ProfileHelper.saveProfile((Context)SendHistoryFragment.this.getActivity(), SendHistoryFragment.this.profile, true);
            }
        }
    };
    private boolean showEmailErrors;
    private boolean showNameError;
    private CustomSwitch switchMyDoctor;
    private Long userId;

    private void doSendHistory() {
        this.callback.onDisplayProgressDialog();
        this.etDoctorName.setError(null);
        this.etDoctorEmail.setError(null);
        String string2 = this.etDoctorName.getEditableText().toString().trim();
        String string3 = this.etDoctorEmail.getEditableText().toString().trim();
        String string4 = this.etNote.getEditableText().toString();
        if (this.switchMyDoctor.isChecked() && this.profile != null) {
            this.profile.doctorName = string2;
            this.profile.doctorEmail = string3;
        }
        MeasurementsHistory measurementsHistory = new MeasurementsHistory();
        measurementsHistory.targetEmail = string3;
        measurementsHistory.targetName = string2;
        measurementsHistory.userId = this.userId;
        measurementsHistory.note = string4;
        long l = MeasurementHelper.History.insertMeasurementHistory((Context)this.getActivity(), measurementsHistory);
        DataHelper.HistoryHelper.requestSendHistory((Context)this.getActivity(), this.userId, l);
    }

    private boolean isDataValid() {
        boolean bl = this.validateName(false) & this.validateEmail(false);
        this.btnSend.setEnabled(bl);
        return bl;
    }

    public static SendHistoryFragment newInstance(long l) {
        SendHistoryFragment sendHistoryFragment = new SendHistoryFragment();
        Bundle bundle = new Bundle(1);
        bundle.putLong("com.getqardio.android.extras.USER_ID", l);
        sendHistoryFragment.setArguments(bundle);
        return sendHistoryFragment;
    }

    private void setDoctorData(Profile profile) {
        boolean bl;
        String string2 = this.etDoctorEmail.getEditableText().toString();
        String string3 = this.etDoctorName.getEditableText().toString();
        boolean bl2 = bl = true;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            bl2 = bl;
            if (TextUtils.isEmpty((CharSequence)string3)) {
                bl2 = false;
            }
        }
        if (!bl2 && profile != null) {
            if (!this.switchMyDoctor.isChecked()) {
                this.switchMyDoctor.setChecked(true);
            }
            if (!TextUtils.isEmpty((CharSequence)profile.doctorEmail)) {
                this.etDoctorEmail.setText((CharSequence)profile.doctorEmail);
                this.etDoctorEmail.setSelection(this.etDoctorEmail.length());
            }
            if (!TextUtils.isEmpty((CharSequence)profile.doctorName)) {
                this.etDoctorName.setText((CharSequence)profile.doctorName);
                this.etDoctorName.setSelection(this.etDoctorName.length());
            }
        }
    }

    private void setHints(boolean bl) {
        if (bl) {
            this.etDoctorName.setHint(2131361907);
            this.etDoctorEmail.setHint(2131361904);
            return;
        }
        this.etDoctorName.setHint(2131361906);
        this.etDoctorEmail.setHint(2131361903);
    }

    private void setInitialEditState() {
        this.etDoctorEmail.setText(null);
        this.etDoctorEmail.setError(null);
        this.etDoctorName.setText(null);
        this.etDoctorName.setError(null);
        this.showNameError = false;
        this.showEmailErrors = false;
    }

    private boolean validateEmail(boolean bl) {
        return Validator.isEmailValid(this.etDoctorEmail, 2131362004, 2131361964, bl);
    }

    private boolean validateName(boolean bl) {
        return Validator.isNameValid(this.etDoctorName, true, 2131362002, bl);
    }

    /* synthetic */ void lambda$onActivityCreated$0(View view, boolean bl) {
        if (!bl && !this.showNameError) {
            this.showNameError = true;
            this.validateName(true);
        }
    }

    /* synthetic */ void lambda$onActivityCreated$1(View view, boolean bl) {
        if (!bl && !this.showEmailErrors) {
            this.showEmailErrors = true;
            this.validateEmail(true);
        }
    }

    /* synthetic */ void lambda$onActivityCreated$2(CompoundButton compoundButton, boolean bl) {
        if (bl) {
            this.setDoctorData(this.profile);
        }
        this.setHints(bl);
    }

    /* synthetic */ void lambda$onActivityCreated$3(View view) {
        KeyboardHelper.hideKeyboard((Context)this.getActivity(), view);
        this.doSendHistory();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        bundle = this.getArguments();
        if (bundle != null) {
            this.userId = bundle.getLong("com.getqardio.android.extras.USER_ID");
        }
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        this.etDoctorName = (CustomEditText)this.rootView.findViewById(2131821289);
        this.etDoctorName.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                SendHistoryFragment.this.validateName(SendHistoryFragment.this.showNameError);
                SendHistoryFragment.this.isDataValid();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.etDoctorName.setOnFocusChangeListener(SendHistoryFragment$$Lambda$1.lambdaFactory$(this));
        this.etDoctorEmail = (EmailEditText)this.rootView.findViewById(2131821290);
        this.etDoctorEmail.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                SendHistoryFragment.this.validateEmail(SendHistoryFragment.this.showEmailErrors);
                SendHistoryFragment.this.isDataValid();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.etDoctorEmail.setOnFocusChangeListener(SendHistoryFragment$$Lambda$2.lambdaFactory$(this));
        this.switchMyDoctor = (CustomSwitch)this.rootView.findViewById(2131821292);
        this.switchMyDoctor.setOnCheckedChangeListener(SendHistoryFragment$$Lambda$3.lambdaFactory$(this));
        this.setHints(this.switchMyDoctor.isChecked());
        this.btnSend = (Button)this.rootView.findViewById(2131821294);
        this.btnSend.setEnabled(false);
        this.btnSend.setOnClickListener(SendHistoryFragment$$Lambda$4.lambdaFactory$(this));
        this.etNote = (CustomEditText)this.rootView.findViewById(2131821291);
        this.pickContactView = (PickContactView)this.rootView.findViewById(2131821213);
        this.pickContactView.setCallback((PickContactView.Callback)this.getActivity());
        this.setInitialEditState();
        ((TextView)this.rootView.findViewById(2131821293)).setTypeface(Typeface.DEFAULT);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent object) {
        super.onActivityResult(n, n2, (Intent)object);
        object = ContactsHelper.onActivityResult(this.getActivity(), n, n2, (Intent)object);
        if (object != null) {
            String string2 = ((ContactsHelper.Contact)object).getEmail();
            this.etDoctorEmail.setText((CharSequence)string2);
            n = this.etDoctorEmail.getText().length();
            if (n > 0) {
                this.etDoctorEmail.setSelection(n);
                this.validateEmail(true);
                this.isDataValid();
            }
            n = !TextUtils.isEmpty((CharSequence)(object = ((ContactsHelper.Contact)object).getName())) ? ((String)object).length() : 0;
            this.etDoctorName.setText((CharSequence)object);
            this.etDoctorName.setSelection(n);
        }
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return DataHelper.ProfileHelper.getProfileLoader((Context)this.getActivity(), this.userId, DataHelper.ProfileHelper.PROFILE_SCREEN_PROJECTION);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968818, viewGroup, false);
        try {
            this.callback = (Callback)this.getActivity();
            do {
                return this.rootView;
                break;
            } while (true);
        }
        catch (ClassCastException classCastException) {
            classCastException.printStackTrace();
            return this.rootView;
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.callback = null;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == 1 && cursor.moveToFirst()) {
            this.profile = DataHelper.ProfileHelper.parseProfile(cursor);
            if (this.profile.userId.equals(this.userId)) {
                this.setDoctorData(this.profile);
            }
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.sendHistoryReceiver);
    }

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.sendHistoryReceiver, new IntentFilter("com.getqardio.android.Notifications.SendHistory"));
    }

    public static interface Callback {
        public void onDisplayProgressDialog();

        public void onSendingFinished(Intent var1);
    }

}

