/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.TimePickerDialog
 *  android.app.TimePickerDialog$OnTimeSetListener
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.text.format.DateFormat
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.widget.DatePicker
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.TimePicker
 *  android.widget.Toast
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.ui.activity.EditNoteActivity;
import com.getqardio.android.ui.dialog.DatePickerDialogFragment;
import com.getqardio.android.ui.dialog.TimePickerDialogFragment;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.AddBPManualMeasurementFragment$$Lambda$7;
import com.getqardio.android.ui.widget.BPResultChart;
import com.getqardio.android.ui.widget.SimpleTextWatcher;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.ui.UIUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddBPManualMeasurementFragment
extends Fragment
implements DatePickerDialog.OnDateSetListener,
TimePickerDialog.OnTimeSetListener {
    private BPResultChart bpResultChart;
    private java.text.DateFormat dateFormat;
    private EditText diastolic;
    private EditText heartRate;
    private boolean is24Hour;
    private EditText note;
    private MenuItem saveMenuItem;
    private Calendar selectedDate;
    private String selectedDateText;
    private TextView selectedDateView;
    private String selectedTimeText;
    private TextView selectedTimeView;
    private EditText systolic;
    private java.text.DateFormat timeFormat;

    /*
     * Enabled aggressive block sorting
     */
    private void addMeasurement() {
        Activity activity;
        if (!this.isDataValid() || (activity = this.getActivity()) == null) {
            return;
        }
        BPMeasurement bPMeasurement = new BPMeasurement();
        bPMeasurement.dia = Integer.valueOf(this.diastolic.getText().toString());
        bPMeasurement.sys = Integer.valueOf(this.systolic.getText().toString());
        if (!TextUtils.isEmpty((CharSequence)this.heartRate.getText())) {
            bPMeasurement.pulse = Integer.valueOf(this.heartRate.getText().toString());
        }
        bPMeasurement.irregularHeartBeat = false;
        bPMeasurement.measureDate = this.selectedDate.getTime();
        bPMeasurement.timezone = String.format("UTC%s", DateUtils.getTimeZoneOffset(bPMeasurement.measureDate));
        if (!TextUtils.isEmpty((CharSequence)this.note.getText())) {
            bPMeasurement.note = this.note.getText().toString();
        }
        bPMeasurement.source = 4;
        bPMeasurement.deviceId = "0001091";
        long l = this.getUserId();
        MeasurementHelper.BloodPressure.addMeasurement((Context)activity, l, bPMeasurement, true);
        ShealthDataHelper.BpMeasurements.requestSaveMeasurement((Context)activity, l, bPMeasurement);
        activity.finish();
    }

    private void checkDate() {
        if (this.selectedDate.getTime().after(new Date())) {
            this.selectedDate.setTime(new Date());
            Toast.makeText((Context)this.getActivity(), (int)2131362210, (int)1).show();
        }
    }

    private FieldState getDiaState(boolean bl) {
        if (TextUtils.isEmpty((CharSequence)this.diastolic.getText())) {
            return FieldState.EMPTY;
        }
        int n = Integer.parseInt(this.diastolic.getText().toString());
        int n2 = -1;
        if (!TextUtils.isEmpty((CharSequence)this.systolic.getText().toString())) {
            n2 = Integer.parseInt(this.systolic.getText().toString());
        }
        if (bl ? n > n2 : n > 300) {
            return FieldState.MORE;
        }
        if (n < 30) {
            return FieldState.LESS;
        }
        return FieldState.OK;
    }

    private FieldState getHeartRateState() {
        if (TextUtils.isEmpty((CharSequence)this.heartRate.getText())) {
            return FieldState.OK;
        }
        int n = Integer.parseInt(this.heartRate.getText().toString());
        if (n < 30) {
            return FieldState.LESS;
        }
        if (n > 220) {
            return FieldState.MORE;
        }
        return FieldState.OK;
    }

    private FieldState getSysState() {
        if (TextUtils.isEmpty((CharSequence)this.systolic.getText())) {
            return FieldState.EMPTY;
        }
        int n = Integer.parseInt(this.systolic.getText().toString());
        if (n < 40) {
            return FieldState.LESS;
        }
        if (n > 300) {
            return FieldState.MORE;
        }
        return FieldState.OK;
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private void hideError(View view) {
        this.showErrorFieldState(FieldState.OK, UIUtils.findTopTextInputLayout(view), 0, 0);
    }

    private void init(View object) {
        this.systolic = (EditText)object.findViewById(2131820880);
        this.diastolic = (EditText)object.findViewById(2131820881);
        this.heartRate = (EditText)object.findViewById(2131820882);
        this.selectedDateView = (TextView)object.findViewById(2131820883);
        this.selectedTimeView = (TextView)object.findViewById(2131820884);
        this.note = (EditText)object.findViewById(2131820885);
        this.bpResultChart = (BPResultChart)object.findViewById(2131820886);
        this.selectedDateText = this.dateFormat.format(this.selectedDate.getTime());
        this.selectedTimeText = this.timeFormat.format(this.selectedDate.getTime());
        this.selectedDateView.setText((CharSequence)this.selectedDateText);
        this.selectedDateView.setOnClickListener(AddBPManualMeasurementFragment$$Lambda$1.lambdaFactory$(this));
        this.selectedTimeView.setText((CharSequence)this.selectedTimeText);
        this.selectedTimeView.setOnClickListener(AddBPManualMeasurementFragment$$Lambda$2.lambdaFactory$(this));
        this.note.setOnFocusChangeListener(AddBPManualMeasurementFragment$$Lambda$3.lambdaFactory$(this));
        this.note.setOnClickListener(AddBPManualMeasurementFragment$$Lambda$4.lambdaFactory$(this));
        object = new SimpleTextWatcher(){

            @Override
            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                AddBPManualMeasurementFragment.this.onBPChanged();
            }
        };
        SimpleTextWatcher simpleTextWatcher = new SimpleTextWatcher(){

            @Override
            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                AddBPManualMeasurementFragment.this.setSaveMenuItemEnabled(AddBPManualMeasurementFragment.this.isAnyFieldChanged());
            }
        };
        this.systolic.setOnFocusChangeListener(AddBPManualMeasurementFragment$$Lambda$5.lambdaFactory$(this));
        this.diastolic.setOnFocusChangeListener(AddBPManualMeasurementFragment$$Lambda$6.lambdaFactory$(this));
        this.heartRate.setOnFocusChangeListener(AddBPManualMeasurementFragment$$Lambda$7.lambdaFactory$(this));
        this.systolic.addTextChangedListener((TextWatcher)new SimpleTextWatcher(){

            @Override
            public void afterTextChanged(Editable editable) {
                if (AddBPManualMeasurementFragment.this.getSysState() == FieldState.OK) {
                    AddBPManualMeasurementFragment.this.hideError((View)AddBPManualMeasurementFragment.this.systolic);
                    if (!TextUtils.isEmpty((CharSequence)UIUtils.findTopTextInputLayout((View)AddBPManualMeasurementFragment.this.diastolic).getError())) {
                        AddBPManualMeasurementFragment.this.showDiastolicError();
                    }
                    if (AddBPManualMeasurementFragment.this.getDiaState(true) == FieldState.OK) {
                        AddBPManualMeasurementFragment.this.hideError((View)AddBPManualMeasurementFragment.this.diastolic);
                        return;
                    }
                    AddBPManualMeasurementFragment.this.showDiastolicError();
                    return;
                }
                AddBPManualMeasurementFragment.this.showSystolicError();
            }
        });
        this.diastolic.addTextChangedListener((TextWatcher)new SimpleTextWatcher(){

            @Override
            public void afterTextChanged(Editable editable) {
                if (AddBPManualMeasurementFragment.this.getSysState() == FieldState.OK && AddBPManualMeasurementFragment.this.getDiaState(true) == FieldState.OK) {
                    AddBPManualMeasurementFragment.this.hideError((View)AddBPManualMeasurementFragment.this.diastolic);
                    return;
                }
                AddBPManualMeasurementFragment.this.showDiastolicError();
            }
        });
        this.heartRate.addTextChangedListener((TextWatcher)new SimpleTextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void afterTextChanged(Editable editable) {
                if (AddBPManualMeasurementFragment.this.getHeartRateState() == FieldState.OK) {
                    AddBPManualMeasurementFragment.this.hideError((View)AddBPManualMeasurementFragment.this.heartRate);
                    return;
                } else {
                    if (TextUtils.isEmpty((CharSequence)editable)) return;
                    {
                        AddBPManualMeasurementFragment.this.showHeartRateError();
                        return;
                    }
                }
            }
        });
        this.systolic.addTextChangedListener((TextWatcher)object);
        this.diastolic.addTextChangedListener((TextWatcher)object);
        this.systolic.addTextChangedListener((TextWatcher)simpleTextWatcher);
        this.diastolic.addTextChangedListener((TextWatcher)simpleTextWatcher);
        this.heartRate.addTextChangedListener((TextWatcher)simpleTextWatcher);
        this.note.addTextChangedListener((TextWatcher)simpleTextWatcher);
        this.selectedTimeView.addTextChangedListener((TextWatcher)simpleTextWatcher);
        this.selectedDateView.addTextChangedListener((TextWatcher)simpleTextWatcher);
    }

    private boolean isAnyFieldChanged() {
        return !TextUtils.isEmpty((CharSequence)this.systolic.getText()) || !TextUtils.isEmpty((CharSequence)this.diastolic.getText()) || !TextUtils.isEmpty((CharSequence)this.heartRate.getText()) || !TextUtils.isEmpty((CharSequence)this.note.getText()) || !this.selectedDateView.getText().toString().equals(this.selectedDateText) || !this.selectedTimeView.getText().toString().equals(this.selectedTimeText);
    }

    private boolean isDataValid() {
        boolean bl = true;
        if (this.getSysState() != FieldState.OK) {
            bl = false;
            this.showSystolicError();
        }
        if (this.getDiaState(true) != FieldState.OK) {
            bl = false;
            this.showDiastolicError();
        }
        if (this.getHeartRateState() != FieldState.OK) {
            bl = false;
            this.showHeartRateError();
        }
        return bl;
    }

    public static AddBPManualMeasurementFragment newInstance(long l) {
        Bundle bundle = new Bundle(1);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        AddBPManualMeasurementFragment addBPManualMeasurementFragment = new AddBPManualMeasurementFragment();
        addBPManualMeasurementFragment.setArguments(bundle);
        return addBPManualMeasurementFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onBPChanged() {
        int n = this.getSysState() == FieldState.OK ? 1 : 0;
        int n2 = n;
        if (n != 0) {
            n2 = this.getDiaState(true) == FieldState.OK ? 1 : 0;
        }
        if (n2 != 0) {
            n = Integer.parseInt(this.systolic.getText().toString());
            n2 = Integer.parseInt(this.diastolic.getText().toString());
            this.bpResultChart.setBP(n2, n);
            return;
        }
        this.bpResultChart.clear();
    }

    private void onNoteSelected(String string2) {
        this.note.setText((CharSequence)string2);
    }

    private void selectDate() {
        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(this.selectedDate.get(1), this.selectedDate.get(2), this.selectedDate.get(5));
        datePickerDialogFragment.setDateSetListener(this);
        datePickerDialogFragment.show(this.getFragmentManager(), null);
    }

    private void selectTime() {
        TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(this.selectedDate.get(11), this.selectedDate.get(12), this.is24Hour);
        timePickerDialogFragment.setTimeSetListener(this);
        timePickerDialogFragment.show(this.getFragmentManager(), null);
    }

    private void setSaveMenuItemEnabled(boolean bl) {
        UIUtils.enableMenuItemAndChangeColor(this.saveMenuItem, bl);
    }

    private void showDiastolicError() {
        if (this.getSysState() == FieldState.OK) {
            this.showErrorFieldState(this.getDiaState(true), UIUtils.findTopTextInputLayout((View)this.diastolic), 30, Integer.parseInt(this.systolic.getText().toString()));
            return;
        }
        this.showErrorFieldState(this.getDiaState(false), UIUtils.findTopTextInputLayout((View)this.diastolic), 30, 300);
    }

    private void showEditNoteScreen() {
        Activity activity = this.getActivity();
        if (activity != null) {
            this.startActivityForResult(EditNoteActivity.getStartIntent((Context)activity, this.getUserId(), this.note.getText().toString(), "bp"), 0);
        }
    }

    private void showErrorFieldState(FieldState fieldState, TextInputLayout textInputLayout, int n, int n2) {
        switch (6.$SwitchMap$com$getqardio$android$ui$fragment$AddBPManualMeasurementFragment$FieldState[fieldState.ordinal()]) {
            default: {
                return;
            }
            case 1: {
                textInputLayout.setError(this.getString(2131362287, new Object[]{String.valueOf(n2)}));
                return;
            }
            case 2: {
                textInputLayout.setError(this.getString(2131362270, new Object[]{String.valueOf(n)}));
                return;
            }
            case 3: {
                textInputLayout.setError(this.getString(2131362227));
                return;
            }
            case 4: 
        }
        textInputLayout.setError(null);
    }

    private void showHeartRateError() {
        this.showErrorFieldState(this.getHeartRateState(), UIUtils.findTopTextInputLayout((View)this.heartRate), 30, 220);
    }

    private void showSystolicError() {
        this.showErrorFieldState(this.getSysState(), UIUtils.findTopTextInputLayout((View)this.systolic), 40, 300);
    }

    private void trackScreen() {
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "BP manual measurement");
    }

    /* synthetic */ void lambda$init$0(View view) {
        this.selectDate();
    }

    /* synthetic */ void lambda$init$1(View view) {
        this.selectTime();
    }

    /* synthetic */ void lambda$init$2(View view, boolean bl) {
        if (bl) {
            this.showEditNoteScreen();
        }
    }

    /* synthetic */ void lambda$init$3(View view) {
        this.showEditNoteScreen();
    }

    /* synthetic */ void lambda$init$4(View view, boolean bl) {
        if (!bl) {
            this.showSystolicError();
        }
    }

    /* synthetic */ void lambda$init$5(View view, boolean bl) {
        if (!(bl || this.getSysState() == FieldState.OK && this.getDiaState(true) == FieldState.OK)) {
            this.showDiastolicError();
        }
    }

    /* synthetic */ void lambda$init$6(View view, boolean bl) {
        if (!bl) {
            this.showHeartRateError();
        }
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 0 && n2 == -1) {
            this.onNoteSelected(EditNoteActivity.extractNoteFromIntent(intent));
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void onCreate(Bundle object) {
        void var1_3;
        super.onCreate(object);
        this.setHasOptionsMenu(true);
        this.selectedDate = Calendar.getInstance();
        this.is24Hour = DateFormat.is24HourFormat((Context)this.getActivity());
        if (this.is24Hour) {
            String string2 = "HH:mm";
        } else {
            String string3 = "hh:mm";
        }
        this.timeFormat = new SimpleDateFormat((String)var1_3);
        this.dateFormat = new SimpleDateFormat(DateFormat.getBestDateTimePattern((Locale)Locale.getDefault(), (String)"dd.MM.yyyy"));
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menu2.clear();
        menuInflater.inflate(2131886094, menu2);
        this.saveMenuItem = menu2.findItem(2131821493);
        this.setSaveMenuItemEnabled(false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968634, viewGroup, false);
        this.init((View)layoutInflater);
        this.trackScreen();
        return layoutInflater;
    }

    public void onDateSet(DatePicker datePicker, int n, int n2, int n3) {
        this.selectedDate.set(1, n);
        this.selectedDate.set(2, n2);
        this.selectedDate.set(5, n3);
        this.checkDate();
        this.selectedDateView.setText((CharSequence)this.dateFormat.format(this.selectedDate.getTime()));
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 2131821493) {
            this.addMeasurement();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onTimeSet(TimePicker timePicker, int n, int n2) {
        this.selectedDate.set(11, n);
        this.selectedDate.set(12, n2);
        this.checkDate();
        this.selectedTimeView.setText((CharSequence)this.timeFormat.format(this.selectedDate.getTime()));
    }

    private static enum FieldState {
        OK,
        MORE,
        LESS,
        EMPTY;

    }

}

